package org.emoflon.ibex.tgg.operational.repair.shortcut.util;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;
import static org.emoflon.ibex.tgg.operational.repair.util.TGGFilterUtil.isAxiomatic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EClass;
import org.emoflon.ibex.tgg.operational.debug.LoggerConfig;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.repair.shortcut.rule.ShortcutRule;
import org.emoflon.ibex.tgg.operational.repair.util.TGGFilterUtil;
import org.emoflon.ibex.tgg.util.ilp.BinaryILPProblem;
import org.emoflon.ibex.tgg.util.ilp.ILPFactory;
import org.emoflon.ibex.tgg.util.ilp.ILPFactory.SupportedILPSolver;
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
 * which elements are to be preserved when we transform a original rule match to a
 * replacing rule match. These overlaps are then used to generated shortcut rules.
 * The overlaps themselves are calculated by formulating the overlap problem as
 * an ILP by defining matching candidates between both original and replacing rule.
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
		return overlaps.stream().map(o -> new ShortcutRule(o, options.repair.relaxedSCPatternMatching()))
				.collect(Collectors.toList());
	}

	private void calculateOverlaps(TGG tgg) {
		LoggerConfig.log(LoggerConfig.log_repair(), () -> "Creating ILP problems for ShortCut-Rules");
		// overlap all rules (also with themselves)
		for (int i = 0; i < tgg.getRules().size(); i++) {
			for (int j = i; j < tgg.getRules().size(); j++) {
				TGGRule originalRule = tgg.getRules().get(i);
				TGGRule replacingRule = tgg.getRules().get(j);

				if (originalRule.isAbstract() || replacingRule.isAbstract())
					continue;

				if (originalRule.equals(replacingRule)) {
//					overlaps.add(createReinsertMapping(originalRule));
					if (!isAxiomatic(originalRule))
						overlaps.add(createOverlap(originalRule, replacingRule, false));
				} else if (ruleMatches(originalRule, replacingRule)) {
					overlaps.add(createOverlap(originalRule, replacingRule, true));
					overlaps.add(createOverlap(replacingRule, originalRule, true));
					overlaps.add(createOverlap(originalRule, replacingRule, false));
					overlaps.add(createOverlap(replacingRule, originalRule, false));
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

	private TGGOverlap createOverlapFromILPSolution(TGGRule originalRule, TGGRule replacingRule, int[] solution) {
		TGGOverlap overlap = new TGGOverlap(originalRule, replacingRule);

		overlap.deletions.addAll(TGGFilterUtil.filterNodes(originalRule.getNodes(), BindingType.CREATE));
		overlap.deletions.addAll(TGGFilterUtil.filterEdges(originalRule.getEdges(), BindingType.CREATE));

		overlap.creations.addAll(TGGFilterUtil.filterNodes(replacingRule.getNodes(), BindingType.CREATE));
		overlap.creations.addAll(TGGFilterUtil.filterEdges(replacingRule.getEdges(), BindingType.CREATE));

		overlap.unboundOriginalContext.addAll(TGGFilterUtil.filterNodes(originalRule.getNodes(), BindingType.CONTEXT));
		overlap.unboundOriginalContext.addAll(TGGFilterUtil.filterEdges(originalRule.getEdges(), BindingType.CONTEXT));

		overlap.unboundReplacingContext.addAll(TGGFilterUtil.filterNodes(replacingRule.getNodes(), BindingType.CONTEXT));
		overlap.unboundReplacingContext.addAll(TGGFilterUtil.filterEdges(replacingRule.getEdges(), BindingType.CONTEXT));

		for (int i = 0; i < solution.length; i++) {
			boolean useCandidate = solution[i] == 1;
			if (!useCandidate)
				continue;

			if (id2node.containsKey(i)) {
				NodeCandidate candidate = id2node.get(i);
				processOverlapCandidate(overlap, candidate.originalElement, candidate.replacingElement);
			}
			if (id2edge.containsKey(i)) {
				EdgeCandidate candidate = id2edge.get(i);
				processOverlapCandidate(overlap, candidate.originalElement, candidate.replacingElement);
			}
		}
		return overlap;
	}

	private void processOverlapCandidate(TGGOverlap overlap, TGGRuleElement originalElement,
			TGGRuleElement replacingElement) {
		overlap.mappings.put(originalElement, replacingElement);
		overlap.revertMappings.put(replacingElement, originalElement);

		switch (originalElement.getBindingType()) {
		case CONTEXT:
			overlap.unboundOriginalContext.remove(originalElement);
			overlap.unboundReplacingContext.remove(replacingElement);
			break;
		case CREATE:
			overlap.deletions.remove(originalElement);
			overlap.creations.remove(replacingElement);
			break;
		default:
			new IllegalStateException(
					"TGGRuleElement are not allowed to have the binding type DELETE given by the user specification due to the fact that TGG rules are strictly monotonic");
		}
	}

	private List<NodeCandidate> calculateNodeCandidates(TGGRule originalRule, TGGRule replacingRule, boolean mapContext) {
		List<NodeCandidate> candidates = new ArrayList<>();
		for (TGGRuleNode sourceNode : originalRule.getNodes()) {
			for (TGGRuleNode targetNode : replacingRule.getNodes()) {
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

	private Collection<EdgeCandidate> calculateEdgeCandidates(TGGRule originalRule, TGGRule replacingRule,
			boolean mapContext) {
		Collection<EdgeCandidate> candidates = new ArrayList<>();

//		originalRule.getNodes().stream().flatMap(n -> n.getOutgoingEdges().stream()).forEach(e -> sourceEdgeMap.put(e.getSrcNode().getName() + "__" + e.getType().getName() + "__"  + e.getTrgNode().getName(), e));
//		replacingRule.getNodes().stream().flatMap(n -> n.getOutgoingEdges().stream()).forEach(e -> targetEdgeMap.put(e.getSrcNode().getName() + "__" + e.getType().getName() + "__"  + e.getTrgNode().getName(), e));

//		createAndRegisterCorrEdges(originalRule);
//		createAndRegisterCorrEdges(replacingRule);

		for (TGGRuleEdge originalEdge : originalRule.getEdges()) {
			for (TGGRuleEdge replacingEdge : replacingRule.getEdges()) {
				if (typeMatches(originalEdge, replacingEdge, mapContext)) {
					if (!node2candidate.containsKey(originalEdge.getSrcNode()))
						continue;
					if (!node2candidate.containsKey(originalEdge.getTrgNode()))
						continue;

					if (!node2candidate.containsKey(replacingEdge.getSrcNode()))
						continue;
					if (!node2candidate.containsKey(replacingEdge.getTrgNode()))
						continue;

					EdgeCandidate candidate = new EdgeCandidate(originalEdge, replacingEdge);
					candidates.add(new EdgeCandidate(originalEdge, replacingEdge));
					registerEdgeCandidate(candidate);

					addEdge2CandidateMapping(originalEdge, candidate);
					addEdge2CandidateMapping(replacingEdge, candidate);
				}
			}
		}
		return candidates;
	}

	private int[] createILPProblem(TGGRule originalRule, TGGRule replacingRule) {
		BinaryILPProblem ilpProblem = ILPFactory.createBinaryILPProblem();

		defineILPExclusions(ilpProblem);
		defineILPImplications(ilpProblem);
		defineILPObjective(ilpProblem);

		try {
			LoggerConfig.log(LoggerConfig.log_repair(), () ->
					"Attempting to solve ILP for SC-Rule: " + originalRule.getName() + " -> " + replacingRule.getName());
			ILPSolution ilpSolution = ILPSolver.solveBinaryILPProblem(ilpProblem, SupportedILPSolver.Sat4J);
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

	private boolean ruleMatches(TGGRule originalRule, TGGRule replacingRule) {
		Set<EClass> originalRuleClasses = cfactory.createObjectSet();
		// TODO lfritsche : insert operationalisation (FWD BWD) splitting
		originalRuleClasses.addAll(TGGFilterUtil.filterNodes(originalRule.getNodes(), BindingType.CREATE).stream() //
				.map(c -> c.getType()) //
				.collect(Collectors.toSet()));
		for (TGGRuleNode replacingNode : TGGFilterUtil.filterNodes(replacingRule.getNodes(), BindingType.CREATE)) {
			for (EClass eClass : originalRuleClasses)
				if (checkInheritance(eClass, replacingNode.getType()))
					return true;
		}
		return false;
	}

	private boolean typeMatches(TGGRuleNode originalNode, TGGRuleNode replacingNode, boolean mapContext) {
		boolean domainMatches = originalNode.getDomainType().equals(replacingNode.getDomainType());
		boolean typeMatches = checkInheritance(originalNode.getType(), replacingNode.getType());
		boolean bindingMatches = originalNode.getBindingType().equals(replacingNode.getBindingType()) //
				&& (mapContext ? true : !originalNode.getBindingType().equals(BindingType.CONTEXT));
		return domainMatches && typeMatches && bindingMatches;
	}

	private boolean typeMatches(TGGRuleEdge originalEdge, TGGRuleEdge replacingEdge, boolean mapContext) {
		boolean domainMatches = originalEdge.getDomainType().equals(replacingEdge.getDomainType());
		boolean typeMatches = originalEdge.getType().equals(replacingEdge.getType());
		boolean bindingMatches = originalEdge.getBindingType().equals(replacingEdge.getBindingType()) //
				&& (mapContext ? true : !originalEdge.getBindingType().equals(BindingType.CONTEXT));
		return domainMatches && typeMatches && bindingMatches;
	}

	private boolean checkInheritance(EClass originalType, EClass replacingType) {
		return originalType.isSuperTypeOf(replacingType) || replacingType.isSuperTypeOf(originalType);
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
		public TGGRuleNode originalElement;
		public TGGRuleNode replacingElement;

		public NodeCandidate(TGGRuleNode originalElement, TGGRuleNode replacingElement) {
			this.originalElement = originalElement;
			this.replacingElement = replacingElement;
		}
	}

	class EdgeCandidate {
		public TGGRuleEdge originalElement;
		public TGGRuleEdge replacingElement;

		public EdgeCandidate(TGGRuleEdge originalElement, TGGRuleEdge replacingElement) {
			this.originalElement = originalElement;
			this.replacingElement = replacingElement;
		}
	}
}
