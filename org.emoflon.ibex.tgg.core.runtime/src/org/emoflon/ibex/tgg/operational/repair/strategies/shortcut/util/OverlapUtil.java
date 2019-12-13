package org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.util;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;
import static org.emoflon.ibex.tgg.operational.repair.strategies.util.TGGFilterUtil.isAxiomatic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EClass;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.rule.ShortcutRule;
import org.emoflon.ibex.tgg.operational.repair.strategies.util.TGGFilterUtil;
import org.emoflon.ibex.tgg.util.ilp.BinaryILPProblem;
import org.emoflon.ibex.tgg.util.ilp.ILPFactory;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPLinearExpression;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPSolution;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.Objective;
import org.emoflon.ibex.tgg.util.ilp.ILPSolver;

import language.BindingType;
import language.DomainType;
import language.TGG;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

/**
 * 
 * This class calculates overlaps of all pairs of rules of a TGG to identify
 * which elements are to be preserved when we transform a source rule match to a
 * target rule match. These overlaps are then used to generated shortcut rules.
 * The overlaps themselves are calculated by formulating the overlap problem as
 * an ILP by defining matching candidates between both source and target rule.
 * 
 * @author lfritsche
 *
 */
public class OverlapUtil {
	protected final static Logger logger = Logger.getLogger(TGGOverlap.class);
	private IbexOptions options;

	private Collection<TGGOverlap> overlaps;
	private Map<Integer, NodeCandidate> id2node;
	private Map<Integer, EdgeCandidate> id2edge;
	private Map<NodeCandidate, Integer> nodeCandidate2id;
	private Map<EdgeCandidate, Integer> edgeCandidate2id;
	private Map<TGGRuleNode, Set<Integer>> node2candidate;
	private Map<TGGRuleEdge, Set<Integer>> edge2candidate;

	// TODO larsF, adrianM: probably not necessary and can be deleted
	private Map<String, TGGRuleEdge> sourceEdgeMap = new HashMap<>();
	private Map<String, TGGRuleEdge> targetEdgeMap = new HashMap<>();

	private int idCounter;
	private int nameCounter;

	public OverlapUtil(IbexOptions options) {
		this.options = options;

		overlaps = cfactory.createObjectSet();
		id2node = cfactory.createIntToObjectHashMap();
		id2edge = cfactory.createIntToObjectHashMap();
		nodeCandidate2id = cfactory.createObjectToIntHashMap();
		edgeCandidate2id = cfactory.createObjectToIntHashMap();
		node2candidate = cfactory.createObjectToObjectHashMap();
		edge2candidate = cfactory.createObjectToObjectHashMap();
		reset();
	}

	public Collection<ShortcutRule> calculateShortcutRules(TGG tgg) {
		// create copy for tgg since we have to enrich each rule with corr edges
		calculateOverlaps(tgg);
		return extractShortcutRulesFromOverlaps();
	}

	private Collection<ShortcutRule> extractShortcutRulesFromOverlaps() {
		return overlaps.stream().map(o -> new ShortcutRule(o)).collect(Collectors.toList());
	}

	private void calculateOverlaps(TGG tgg) {
		logger.debug("Creating ILP problems for ShortCut-Rules");
		// overlap all rules (also with themselves)
		for (int i = 0; i < tgg.getRules().size(); i++) {
			for (int j = i; j < tgg.getRules().size(); j++) {
				TGGRule sourceRule = tgg.getRules().get(i);
				TGGRule targetRule = tgg.getRules().get(j);

				if (sourceRule.isAbstract() || targetRule.isAbstract())
					continue;

				if (sourceRule.equals(targetRule)) {
//					overlaps.add(createReinsertMapping(sourceRule));
					if (!isAxiomatic(sourceRule))
						overlaps.add(createOverlap(sourceRule, targetRule, false));
				} else if (ruleMatches(sourceRule, targetRule)) {
					overlaps.add(createOverlap(sourceRule, targetRule, true));
					overlaps.add(createOverlap(targetRule, sourceRule, true));
					overlaps.add(createOverlap(sourceRule, targetRule, false));
					overlaps.add(createOverlap(targetRule, sourceRule, false));
				}
			}
		}
		overlaps.removeIf(o -> !containsBothDomains(o));
	}

	private boolean containsBothDomains(TGGOverlap overlap) {
		boolean containsSrc = overlap.mappings.keySet().stream()
				.anyMatch(k -> k.getDomainType() == DomainType.SRC && k.getBindingType() == BindingType.CREATE);
		boolean containsTrg = overlap.mappings.keySet().stream()
				.anyMatch(k -> k.getDomainType() == DomainType.TRG && k.getBindingType() == BindingType.CREATE);
		return containsSrc && containsTrg;
	}

	private TGGOverlap createOverlap(TGGRule sourceRule, TGGRule targetRule, boolean mapContext) {
		reset();

		calculateNodeCandidates(sourceRule, targetRule, mapContext);
		calculateEdgeCandidates(sourceRule, targetRule, mapContext);

		int[] solution = createILPProblem(sourceRule, targetRule);

		return createOverlapFromILPSolution(sourceRule, targetRule, solution);
	}

	private TGGOverlap createOverlapFromILPSolution(TGGRule sourceRule, TGGRule targetRule, int[] solution) {
		TGGOverlap overlap = new TGGOverlap(sourceRule, targetRule);

		overlap.deletions.addAll(TGGFilterUtil.filterNodes(sourceRule.getNodes(), BindingType.CREATE));
		overlap.deletions.addAll(TGGFilterUtil.filterEdges(sourceEdgeMap.values(), BindingType.CREATE));

		overlap.creations.addAll(TGGFilterUtil.filterNodes(targetRule.getNodes(), BindingType.CREATE));
		overlap.creations.addAll(TGGFilterUtil.filterEdges(targetEdgeMap.values(), BindingType.CREATE));

		overlap.unboundSrcContext.addAll(TGGFilterUtil.filterNodes(sourceRule.getNodes(), BindingType.CONTEXT));
		overlap.unboundSrcContext.addAll(TGGFilterUtil.filterEdges(sourceEdgeMap.values(), BindingType.CONTEXT));

		overlap.unboundTrgContext.addAll(TGGFilterUtil.filterNodes(targetRule.getNodes(), BindingType.CONTEXT));
		overlap.unboundTrgContext.addAll(TGGFilterUtil.filterEdges(targetEdgeMap.values(), BindingType.CONTEXT));

		for (int i = 0; i < solution.length; i++) {
			boolean useCandidate = solution[i] == 1;
			if (!useCandidate)
				continue;

			if (id2node.containsKey(i)) {
				NodeCandidate candidate = id2node.get(i);
				processOverlapCandidate(overlap, candidate.sourceElement, candidate.targetElement);
			}
			if (id2edge.containsKey(i)) {
				EdgeCandidate candidate = id2edge.get(i);
				processOverlapCandidate(overlap, candidate.sourceElement, candidate.targetElement);
			}
		}
		return overlap;
	}

	private void processOverlapCandidate(TGGOverlap overlap, TGGRuleElement sourceElement,
			TGGRuleElement targetElement) {
		overlap.mappings.put(sourceElement, targetElement);
		overlap.revertMappings.put(targetElement, sourceElement);

		switch (sourceElement.getBindingType()) {
		case CONTEXT:
			// TODO lfritsche : implement attributes
			overlap.unboundSrcContext.remove(sourceElement);
			overlap.unboundTrgContext.remove(targetElement);
			break;
		case CREATE:
			overlap.deletions.remove(sourceElement);
			overlap.creations.remove(targetElement);
			break;
		default:
			new IllegalStateException(
					"TGGRuleElement are not allowed to have the binding type DELETE given by the user specification due to the fact that TGG rules are strictly monotonic");
		}
	}

	private List<NodeCandidate> calculateNodeCandidates(TGGRule sourceRule, TGGRule targetRule, boolean mapContext) {
		List<NodeCandidate> candidates = new ArrayList<>();
		for (TGGRuleNode sourceNode : sourceRule.getNodes()) {
			for (TGGRuleNode targetNode : targetRule.getNodes()) {
				if (typeMatches(sourceNode, targetNode, mapContext)) {
					NodeCandidate candidate = new NodeCandidate(sourceNode, targetNode);
					candidates.add(candidate);
					registerNodeCandidate(candidate);

					addNode2CandidateMapping(sourceNode, candidate);
					addNode2CandidateMapping(targetNode, candidate);
				}
			}
		}
		return candidates;
	}

	private Collection<EdgeCandidate> calculateEdgeCandidates(TGGRule sourceRule, TGGRule targetRule,
			boolean mapContext) {
		Collection<EdgeCandidate> candidates = new ArrayList<>();

//		sourceRule.getNodes().stream().flatMap(n -> n.getOutgoingEdges().stream()).forEach(e -> sourceEdgeMap.put(e.getSrcNode().getName() + "__" + e.getType().getName() + "__"  + e.getTrgNode().getName(), e));
//		targetRule.getNodes().stream().flatMap(n -> n.getOutgoingEdges().stream()).forEach(e -> targetEdgeMap.put(e.getSrcNode().getName() + "__" + e.getType().getName() + "__"  + e.getTrgNode().getName(), e));

//		createAndRegisterCorrEdges(sourceRule);
//		createAndRegisterCorrEdges(targetRule);

		sourceRule.getEdges().forEach(e -> sourceEdgeMap
				.put(e.getSrcNode().getName() + "__" + e.getType().getName() + "__" + e.getTrgNode().getName(), e));
		targetRule.getEdges().forEach(e -> targetEdgeMap
				.put(e.getSrcNode().getName() + "__" + e.getType().getName() + "__" + e.getTrgNode().getName(), e));

		for (TGGRuleEdge sourceEdge : sourceEdgeMap.values()) {
			for (TGGRuleEdge targetEdge : targetEdgeMap.values()) {
				if (typeMatches(sourceEdge, targetEdge, mapContext)) {
					if (!node2candidate.containsKey(sourceEdge.getSrcNode()))
						continue;
					if (!node2candidate.containsKey(sourceEdge.getTrgNode()))
						continue;

					if (!node2candidate.containsKey(targetEdge.getSrcNode()))
						continue;
					if (!node2candidate.containsKey(targetEdge.getTrgNode()))
						continue;

					EdgeCandidate candidate = new EdgeCandidate(sourceEdge, targetEdge);
					candidates.add(new EdgeCandidate(sourceEdge, targetEdge));
					registerEdgeCandidate(candidate);

					addEdge2CandidateMapping(sourceEdge, candidate);
					addEdge2CandidateMapping(targetEdge, candidate);
				}
			}
		}
		return candidates;
	}

	private int[] createILPProblem(TGGRule sourceRule, TGGRule targetRule) {
		BinaryILPProblem ilpProblem = ILPFactory.createBinaryILPProblem();

		defineILPExclusions(ilpProblem);
		defineILPImplications(ilpProblem);
		defineILPObjective(ilpProblem);

		try {
			logger.debug(
					"Attempting to solve ILP for SC-Rule: " + sourceRule.getName() + " -> " + targetRule.getName());
			ILPSolution ilpSolution = ILPSolver.solveBinaryILPProblem(ilpProblem, this.options.getIlpSolver());
			if (!ilpProblem.checkValidity(ilpSolution)) {
				throw new AssertionError("Invalid solution");
			}

			int[] result = new int[idCounter];
			for (int i = 0; i < idCounter; i++) {
				if (ilpSolution.getVariable("x" + i) > 0)
					result[i] = 1;
				else
					result[i] = -1;
			}

			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Solving ILP failed", e);
		}
	}

	private void reset() {
		idCounter = 0;
		nameCounter = 0;
		nodeCandidate2id.clear();
		edgeCandidate2id.clear();
		node2candidate.clear();
		edge2candidate.clear();
		sourceEdgeMap.clear();
		targetEdgeMap.clear();
		id2node.clear();
		id2edge.clear();
	}

	private void registerNodeCandidate(NodeCandidate candidate) {
		nodeCandidate2id.put(candidate, idCounter);
		id2node.put(idCounter, candidate);
		idCounter++;
	}

	private void registerEdgeCandidate(EdgeCandidate candidate) {
		edgeCandidate2id.put(candidate, idCounter);
		id2edge.put(idCounter, candidate);
		idCounter++;
	}

	private void defineILPImplications(BinaryILPProblem ilpProblem) {
		for (TGGRuleEdge edge : edge2candidate.keySet()) {
			Set<Integer> srcNodeIDs = node2candidate.getOrDefault(edge.getSrcNode(), cfactory.createIntSet());
			ilpProblem.addNegativeImplication(srcNodeIDs.stream().map(m -> "x" + m),
					edge2candidate.get(edge).stream().map(m -> "x" + m),
					"IMPL" + edge.getType().getName() + nameCounter++);

			Set<Integer> trgNodeIDs = node2candidate.getOrDefault(edge.getTrgNode(), cfactory.createIntSet());
			ilpProblem.addNegativeImplication(trgNodeIDs.stream().map(m -> "x" + m),
					edge2candidate.get(edge).stream().map(m -> "x" + m),
					"IMPL" + edge.getType().getName() + nameCounter++);
		}
	}

	private void defineILPExclusions(BinaryILPProblem ilpProblem) {
		node2candidate.keySet().forEach(node -> defineILPExclusions(ilpProblem, node));
		edge2candidate.keySet().forEach(edge -> defineILPExclusions(ilpProblem, edge));
	}

	private void defineILPExclusions(BinaryILPProblem ilpProblem, TGGRuleNode node) {
		Set<Integer> candidates = node2candidate.get(node);
		if (candidates.size() <= 1)
			return;

		ilpProblem.addExclusion(candidates.stream().map(v -> "x" + v),
				"EXCL_nodeJustOnce_" + node.eClass().getName() + "_" + nameCounter++);
	}

	private void defineILPExclusions(BinaryILPProblem ilpProblem, TGGRuleEdge edge) {
		Set<Integer> candidates = edge2candidate.get(edge);
		if (candidates.size() <= 1)
			return;

		ilpProblem.addExclusion(candidates.stream().map(v -> "x" + v),
				"EXCL_edgeJustOnce_" + edge.eClass().getName() + "_" + nameCounter++);
	}

	private void defineILPObjective(BinaryILPProblem ilpProblem) {
		ILPLinearExpression expr = ilpProblem.createLinearExpression();

		for (int i = 0; i < idCounter; i++)
			expr.addTerm("x" + i, 1);

		ilpProblem.setObjective(expr, Objective.maximize);
	}

	private boolean ruleMatches(TGGRule sourceRule, TGGRule targetRule) {
		Set<EClass> srcRuleClasses = cfactory.createObjectSet();
		// TODO lfritsche : insert operationalisation (FWD BWD) splitting
		srcRuleClasses.addAll(TGGFilterUtil.filterNodes(sourceRule.getNodes(), BindingType.CREATE).stream() //
				.map(c -> c.getType()) //
				.collect(Collectors.toSet()));
		for (TGGRuleNode targetNode : TGGFilterUtil.filterNodes(targetRule.getNodes(), BindingType.CREATE)) {
			for (EClass eClass : srcRuleClasses)
				if (checkInheritance(eClass, targetNode.getType()))
					return true;
		}
		return false;
	}

	private boolean typeMatches(TGGRuleNode sourceNode, TGGRuleNode targetNode, boolean mapContext) {
		boolean domainMatches = sourceNode.getDomainType().equals(targetNode.getDomainType());
		boolean typeMatches = checkInheritance(sourceNode.getType(), targetNode.getType());
		boolean bindingMatches = sourceNode.getBindingType().equals(targetNode.getBindingType()) //
				&& (mapContext ? true : !sourceNode.getBindingType().equals(BindingType.CONTEXT));
		return domainMatches && typeMatches && bindingMatches;
	}

	private boolean typeMatches(TGGRuleEdge sourceEdge, TGGRuleEdge targetEdge, boolean mapContext) {
		boolean domainMatches = sourceEdge.getDomainType().equals(targetEdge.getDomainType());
		boolean typeMatches = sourceEdge.getType().equals(targetEdge.getType());
		boolean bindingMatches = sourceEdge.getBindingType().equals(targetEdge.getBindingType()) //
				&& (mapContext ? true : !sourceEdge.getBindingType().equals(BindingType.CONTEXT));
		return domainMatches && typeMatches && bindingMatches;
	}

	private boolean checkInheritance(EClass srcType, EClass trgType) {
		return srcType.isSuperTypeOf(trgType) || trgType.isSuperTypeOf(srcType);
	}

	private void addNode2CandidateMapping(TGGRuleNode node, NodeCandidate candidate) {
		Set<Integer> nodeCandidateIDs = node2candidate.getOrDefault(node, cfactory.createIntSet());
		nodeCandidateIDs.add(nodeCandidate2id.get(candidate));
		node2candidate.put(node, nodeCandidateIDs);
	}

	private void addEdge2CandidateMapping(TGGRuleEdge edge, EdgeCandidate candidate) {
		Set<Integer> edgeCandidateIDs = edge2candidate.getOrDefault(edge, cfactory.createIntSet());
		edgeCandidateIDs.add(edgeCandidate2id.get(candidate));
		edge2candidate.put(edge, edgeCandidateIDs);
	}

	class NodeCandidate {
		public TGGRuleNode sourceElement;
		public TGGRuleNode targetElement;

		public NodeCandidate(TGGRuleNode sourceElement, TGGRuleNode targetElement) {
			this.sourceElement = sourceElement;
			this.targetElement = targetElement;
		}
	}

	class EdgeCandidate {
		public TGGRuleEdge sourceElement;
		public TGGRuleEdge targetElement;

		public EdgeCandidate(TGGRuleEdge sourceElement, TGGRuleEdge targetElement) {
			this.sourceElement = sourceElement;
			this.targetElement = targetElement;
		}
	}
}
