package org.emoflon.ibex.tgg.operational.repair.strategies.util;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.ShortcutRule;
import org.emoflon.ibex.tgg.util.ilp.BinaryILPProblem;
import org.emoflon.ibex.tgg.util.ilp.ILPFactory;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPLinearExpression;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPSolution;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.Objective;
import org.emoflon.ibex.tgg.util.ilp.ILPSolver;

import language.BindingType;
import language.DomainType;
import language.LanguageFactory;
import language.TGG;
import language.TGGComplementRule;
import language.TGGRule;
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

/**
 * 
 * This class calculates overlaps of all pairs of rules of a TGG to identify which elements
 * are to be preserved when we transform a source rule match to a target rule match.
 * These overlaps are then used to generated shortcut rules.
 * The overlaps themselves are calculated by formulating the overlap problem as an ILP by defining
 * matching candidates between both source and target rule. 
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
		return extractShortcutsFromOverlaps();
	}

	private Collection<ShortcutRule> extractShortcutsFromOverlaps() {
		return overlaps.stream().map(o -> new ShortcutRule(o)).collect(Collectors.toList());
	}

	private void calculateOverlaps(TGG tgg) {
		logger.debug("Creating ILP problems for ShortCut-Rules");
		for (int i = 0; i < tgg.getRules().size(); i++) {
			for (int j = i; j < tgg.getRules().size(); j++) {
				TGGRule sourceRule = tgg.getRules().get(i);
				TGGRule targetRule = tgg.getRules().get(j);
				
				if(sourceRule instanceof TGGComplementRule || targetRule instanceof TGGComplementRule)
					continue;
				
				if(sourceRule.isAbstract() || targetRule.isAbstract())
					continue;

				if (sourceRule.equals(targetRule)) {
//					overlaps.add(createReinsertMapping(sourceRule));
					if(!isAxiomatic(sourceRule))
						overlaps.add(createMappings(sourceRule, targetRule, false));
				} else if (ruleMatches(sourceRule, targetRule)) {
					overlaps.add(createMappings(sourceRule, targetRule, true));
					overlaps.add(createMappings(targetRule, sourceRule, true));
					overlaps.add(createMappings(sourceRule, targetRule, false));
					overlaps.add(createMappings(targetRule, sourceRule, false));
				}
			}
		}
		overlaps.removeIf(o -> !containsBothDomains(o));
	}
	
	private boolean containsBothDomains(TGGOverlap overlap) {
		boolean containsSrc = overlap.mappings.keySet().stream().anyMatch(k -> k.getDomainType() == DomainType.SRC && k.getBindingType() == BindingType.CREATE);
		boolean containsTrg = overlap.mappings.keySet().stream().anyMatch(k -> k.getDomainType() == DomainType.TRG && k.getBindingType() == BindingType.CREATE);
		return containsSrc && containsTrg;
	}

	private TGGOverlap createReinsertMapping(TGGRule rule) {
		Map<TGGRuleElement, TGGRuleElement> mappings = cfactory.createObjectToObjectHashMap();
		for (TGGRuleNode node : rule.getNodes()) {
			mappings.put(node, node);
		}
		for (TGGRuleEdge edge : rule.getEdges()) {
			mappings.put(edge, edge);
		}
		return new TGGOverlap(rule, rule, mappings, new ArrayList<>(),  new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
	}
	
	private TGGOverlap createMappings(TGGRule sourceRule, TGGRule targetRule, boolean mapContext) {
		reset();

		calculateNodeCandidates(sourceRule, targetRule, mapContext);
		calculateEdgeCandidates(sourceRule, targetRule, mapContext);

		int[] solution = createILPProblem(sourceRule, targetRule);

		return createMappingFromILPSolution(sourceRule, targetRule, solution);
	}

	private TGGOverlap createMappingFromILPSolution(TGGRule sourceRule, TGGRule targetRule, int[] solution) {
		TGGOverlap overlap = new TGGOverlap(sourceRule, targetRule);
		
		overlap.deletions.addAll(TGGCollectionUtil.filterNodes(sourceRule.getNodes(), BindingType.CREATE));
		overlap.deletions.addAll(TGGCollectionUtil.filterEdges(sourceEdgeMap.values(), BindingType.CREATE));

		overlap.creations.addAll(TGGCollectionUtil.filterNodes(targetRule.getNodes(), BindingType.CREATE));
		overlap.creations.addAll(TGGCollectionUtil.filterEdges(targetEdgeMap.values(), BindingType.CREATE));

		overlap.unboundSrcContext.addAll(TGGCollectionUtil.filterNodes(sourceRule.getNodes(), BindingType.CONTEXT));
		overlap.unboundSrcContext.addAll(TGGCollectionUtil.filterEdges(sourceEdgeMap.values(), BindingType.CONTEXT));
		
		overlap.unboundTrgContext.addAll(TGGCollectionUtil.filterNodes(targetRule.getNodes(), BindingType.CONTEXT));
		overlap.unboundTrgContext.addAll(TGGCollectionUtil.filterEdges(targetEdgeMap.values(), BindingType.CONTEXT));

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

	private void processOverlapCandidate(TGGOverlap overlap, TGGRuleElement sourceElement, TGGRuleElement targetElement) {
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
			new IllegalStateException("TGGRuleElement are not allowed to have the binding type DELETE given by the user specification due to the fact that TGG rules are strictly monotonic");
		}
	}

	private List<NodeCandidate> calculateNodeCandidates(TGGRule sourceRule, TGGRule targetRule, boolean mapContext) {
		List<NodeCandidate> candidates = new ArrayList<>();
		for (TGGRuleNode sourceNode : sourceRule.getNodes()) {
			for (TGGRuleNode targetNode : targetRule.getNodes()) {
				if(mapContext || sourceNode.getBindingType() == BindingType.CREATE && targetNode.getBindingType() == BindingType.CREATE)
					if (typeMatches(sourceNode, targetNode)) {
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

	private Collection<EdgeCandidate> calculateEdgeCandidates(TGGRule sourceRule, TGGRule targetRule, boolean mapContext) {
		Collection<EdgeCandidate> candidates = new ArrayList<>();

//		sourceRule.getNodes().stream().flatMap(n -> n.getOutgoingEdges().stream()).forEach(e -> sourceEdgeMap.put(e.getSrcNode().getName() + "__" + e.getType().getName() + "__"  + e.getTrgNode().getName(), e));
//		targetRule.getNodes().stream().flatMap(n -> n.getOutgoingEdges().stream()).forEach(e -> targetEdgeMap.put(e.getSrcNode().getName() + "__" + e.getType().getName() + "__"  + e.getTrgNode().getName(), e));

		createAndRegisterCorrEdges(sourceRule);
		createAndRegisterCorrEdges(targetRule);
		
		
		sourceRule.getEdges().forEach(e -> sourceEdgeMap.put(e.getSrcNode().getName() + "__" + e.getType().getName() + "__"  + e.getTrgNode().getName(), e));
		targetRule.getEdges().forEach(e -> targetEdgeMap.put(e.getSrcNode().getName() + "__" + e.getType().getName() + "__"  + e.getTrgNode().getName(), e));
		
		
		for (TGGRuleEdge sourceEdge : sourceEdgeMap.values()) {
			for (TGGRuleEdge targetEdge : targetEdgeMap.values()) {
				if(mapContext || sourceEdge.getBindingType() == BindingType.CREATE && targetEdge.getBindingType() == BindingType.CREATE)
					if (typeMatches(sourceEdge, targetEdge)) {
						if(!node2candidate.containsKey(sourceEdge.getSrcNode()))
							continue;
						if(!node2candidate.containsKey(sourceEdge.getTrgNode()))
							continue;
						
						if(!node2candidate.containsKey(targetEdge.getSrcNode()))
							continue;
						if(!node2candidate.containsKey(targetEdge.getTrgNode()))
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

	private void createAndRegisterCorrEdges(TGGRule rule) {
		Collection<TGGRuleCorr> corrs = rule.getNodes()
				.stream()
				.filter(n -> n instanceof TGGRuleCorr)
				.map(n -> (TGGRuleCorr) n)
				.collect(Collectors.toList());
		
		for (TGGRuleCorr corr : corrs) {
			TGGRuleEdge incomingEdge = LanguageFactory.eINSTANCE.createTGGRuleEdge();
			incomingEdge.setType((EReference) corr.getType().getEStructuralFeature("source"));
			incomingEdge.setDomainType(corr.getDomainType());
			incomingEdge.setBindingType(corr.getBindingType());
			incomingEdge.setSrcNode(corr);
			incomingEdge.setTrgNode(corr.getSource());
			incomingEdge.setName(incomingEdge.getSrcNode().getName() + "__" + incomingEdge.getType().getName() + "__"  + incomingEdge.getTrgNode().getName());
			
			TGGRuleEdge outgoingEdge = LanguageFactory.eINSTANCE.createTGGRuleEdge();
			outgoingEdge.setType((EReference) corr.getType().getEStructuralFeature("target"));
			outgoingEdge.setDomainType(corr.getDomainType());
			outgoingEdge.setBindingType(corr.getBindingType());
			outgoingEdge.setSrcNode(corr);
			outgoingEdge.setTrgNode(corr.getTarget());
			outgoingEdge.setName(outgoingEdge.getSrcNode().getName() + "__" + outgoingEdge.getType().getName() + "__"  + outgoingEdge.getTrgNode().getName());
			
			rule.getEdges().add(incomingEdge);
			rule.getEdges().add(outgoingEdge);
		}
	}

	private int[] createILPProblem(TGGRule sourceRule, TGGRule targetRule) {
		BinaryILPProblem ilpProblem = ILPFactory.createBinaryILPProblem();

		defineILPExclusions(ilpProblem);
		defineILPImplications(ilpProblem);
		defineILPObjective(ilpProblem);

		try {
			logger.debug("Attempting to solve ILP for SC-Rule: " + sourceRule.getName() + " -> " + targetRule.getName());
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
			Set<Integer> nodeIDs = cfactory.createIntSet();
			nodeIDs.addAll(node2candidate.getOrDefault(edge.getSrcNode(), cfactory.createIntSet()));
			nodeIDs.addAll(node2candidate.getOrDefault(edge.getTrgNode(), cfactory.createIntSet()));

			ilpProblem.addNegativeImplication(nodeIDs.stream().map(m -> "x" + m), edge2candidate.get(edge).stream().map(m -> "x" + m),
					"IMPL" + edge.getType().getName() + nameCounter++);
		}
	}

	private void defineILPExclusions(BinaryILPProblem ilpProblem) {
		node2candidate.keySet().stream().forEach(c -> defineILPExclusions(ilpProblem, c));
		edge2candidate.keySet().stream().forEach(c -> defineILPExclusions(ilpProblem, c));
	}

	private void defineILPExclusions(BinaryILPProblem ilpProblem, TGGRuleNode node) {
		Set<Integer> candidates = node2candidate.get(node);
		if (candidates.size() <= 1)
			return;

		ilpProblem.addExclusion(candidates.stream().map(v -> "x" + v), "EXCL_nodeOnce_" + node.eClass().getName() + "_" + nameCounter++);
	}

	private void defineILPExclusions(BinaryILPProblem ilpProblem, TGGRuleEdge edge) {
		Set<Integer> candidates = edge2candidate.get(edge);
		if (candidates.size() <= 1)
			return;

		ilpProblem.addExclusion(candidates.stream().map(v -> "x" + v), "EXCL_nodeOnce_" + edge.eClass().getName() + "_" + nameCounter++);
	}

	private void defineILPObjective(BinaryILPProblem ilpProblem) {
		ILPLinearExpression expr = ilpProblem.createLinearExpression();

		for (int i = 0; i < idCounter; i++)
			expr.addTerm("x" + i, 1);

		ilpProblem.setObjective(expr, Objective.maximize);
	}

	private boolean ruleMatches(TGGRule sourceRule, TGGRule targetRule) {
		// TODO lfritsche : extend inheritance concept
		Set<EClass> classes = cfactory.createObjectSet();
		// TODO lfritsche : insert operationalisation (FWD BWD) splitting
		classes.addAll(TGGCollectionUtil.filterNodes(sourceRule.getNodes(), BindingType.CREATE).stream().map(c -> c.getType()).collect(Collectors.toSet()));
		for (TGGRuleNode targetNode : TGGCollectionUtil.filterNodes(targetRule.getNodes(), BindingType.CREATE)) {
			if (classes.contains(targetNode.getType()))
				return true;
		}
		return false;
	}

	private boolean typeMatches(TGGRuleNode sourceNode, TGGRuleNode targetNode) {
		// TODO lfritsche : extend inheritance concept
		boolean domainMatches = sourceNode.getDomainType().equals(targetNode.getDomainType());
		boolean typeMatches = sourceNode.getType().equals(targetNode.getType());
		boolean bindingMatches = sourceNode.getBindingType().equals(targetNode.getBindingType());
		return domainMatches && typeMatches && bindingMatches;
	}

	private boolean typeMatches(TGGRuleEdge sourceEdge, TGGRuleEdge targetEdge) {
		boolean domainMatches = sourceEdge.getDomainType().equals(targetEdge.getDomainType());
		boolean typeMatches = sourceEdge.getType().equals(targetEdge.getType());
		boolean bindingMatches = sourceEdge.getBindingType().equals(targetEdge.getBindingType());
		return domainMatches && typeMatches && bindingMatches;
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
	
	private boolean isAxiomatic(TGGRule rule) {
		return rule.getNodes().stream().noneMatch(n -> n.getBindingType() == BindingType.CONTEXT) && 
				rule.getEdges().stream().noneMatch(e -> e.getBindingType() == BindingType.CONTEXT);
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
