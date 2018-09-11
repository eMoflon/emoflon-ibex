package org.emoflon.ibex.tgg.operational.repair.strategies.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.ShortcutRule;
import org.emoflon.ibex.tgg.util.ilp.BinaryILPProblem;
import org.emoflon.ibex.tgg.util.ilp.ILPFactory;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPLinearExpression;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPSolution;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.Objective;
import org.emoflon.ibex.tgg.util.ilp.ILPSolver;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import language.BindingType;
import language.DomainType;
import language.LanguageFactory;
import language.TGG;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;
import language.TGGRuleCorr;

public class OverlapUtil {
	protected final static Logger logger = Logger.getLogger(TGGOverlap.class);
	private IbexOptions options;

	private Collection<TGGOverlap> overlaps;
	private Map<Integer, NodeCandidate> id2node;
	private Map<Integer, EdgeCandidate> id2edge;
	private Object2IntOpenHashMap<NodeCandidate> nodeCandidate2id;
	private Object2IntOpenHashMap<EdgeCandidate> edgeCandidate2id;
	private Map<TGGRuleNode, Set<Integer>> node2candidate;
	private Map<TGGRuleEdge, Set<Integer>> edge2candidate;
	private Map<String, TGGRuleEdge> sourceEdgeMap = new HashMap<>();
	private Map<String, TGGRuleEdge> targetEdgeMap = new HashMap<>();
	

	private int idCounter;
	private int nameCounter;

	public OverlapUtil(IbexOptions options) {
		this.options = options;
		overlaps = new ObjectOpenHashSet<TGGOverlap>();
		id2node = new Int2ObjectOpenHashMap<>();
		id2edge = new Int2ObjectOpenHashMap<>();
		nodeCandidate2id = new Object2IntOpenHashMap<>();
		edgeCandidate2id = new Object2IntOpenHashMap<>();
		node2candidate = new Object2ObjectOpenHashMap<>();
		edge2candidate = new Object2ObjectOpenHashMap<>();
		reset();
	}

	public Collection<ShortcutRule> calculateShortcutRules(TGG tgg) {
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

				if (sourceRule.equals(targetRule)) {
//					overlaps.add(createReinsertMapping(sourceRule));
					overlaps.add(createMappings(copy(sourceRule), targetRule, false));
				} else if (ruleMatches(sourceRule, targetRule)) {
					overlaps.add(createMappings(sourceRule, targetRule, true));
					overlaps.add(createMappings(targetRule, sourceRule, true));
				}
			}
		}
		overlaps.removeIf(o -> !containsBothDomains(o));
	}
	
	private TGGRule copy(TGGRule rule) {
		Collection<String> linkNames = new HashSet<>();
		TGGRule newRule = EcoreUtil.copy(rule);
		Set<TGGRuleEdge> links = rule.getNodes().stream()
				.filter(n -> n instanceof TGGRuleCorr)
				.map(c -> (TGGRuleCorr) c)
				.flatMap(c -> c.getOutgoingEdges().stream())
				.collect(Collectors.toSet());
		
		for(TGGRuleEdge edge : links) {
			TGGRuleEdge newEdge = LanguageFactory.eINSTANCE.createTGGRuleEdge();
			newEdge.setName(edge.getSrcNode().getName() + "_" + edge.getType().getName() + "_" + edge.getTrgNode().getName());
			if(linkNames.contains(newEdge.getName())) 
				continue;
			
			linkNames.add(newEdge.getName());
			newEdge.setType(edge.getType());
			newEdge.setBindingType(edge.getBindingType());
			newEdge.setDomainType(edge.getDomainType());
			newEdge.setSrcNode(newRule.getNodes().stream().filter(n -> n.getName().equals(edge.getSrcNode().getName())).findFirst().get());
			newEdge.setTrgNode(newRule.getNodes().stream().filter(n -> n.getName().equals(edge.getTrgNode().getName())).findFirst().get());
			newEdge.getSrcNode().getOutgoingEdges().add(newEdge);
		}
		
		return newRule;
	}
	
	private boolean containsBothDomains(TGGOverlap overlap) {
		boolean containsSrc = overlap.mappings.keySet().stream().anyMatch(k -> k.getDomainType() == DomainType.SRC && k.getBindingType() == BindingType.CREATE);
		boolean containsTrg = overlap.mappings.keySet().stream().anyMatch(k -> k.getDomainType() == DomainType.TRG && k.getBindingType() == BindingType.CREATE);
		return containsSrc && containsTrg;
	}

	private TGGOverlap createReinsertMapping(TGGRule rule) {
		Map<TGGRuleElement, TGGRuleElement> mappings = new Object2ObjectOpenHashMap<>();
		for (TGGRuleNode node : rule.getNodes()) {
			mappings.put(node, node);
		}
		for (TGGRuleEdge edge : rule.getEdges()) {
			mappings.put(edge, edge);
		}
		return new TGGOverlap(rule, rule, mappings, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
	}
	
	private TGGOverlap createMappings(TGGRule sourceRule, TGGRule targetRule, boolean mapContext) {
		reset();

		Collection<NodeCandidate> nodeCandidates = calculateNodeCandidates(sourceRule, targetRule, mapContext);
		Collection<EdgeCandidate> edgeCandidates = calculateEdgeCandidates(sourceRule, targetRule, mapContext);

		registerNodeCandidates(nodeCandidates);
		registerEdgeCandidates(edgeCandidates);

		int[] solution = createILPProblem(sourceRule, targetRule);

		return createMappingFromILPSolution(sourceRule, targetRule, solution);
	}

	private TGGOverlap createMappingFromILPSolution(TGGRule sourceRule, TGGRule targetRule, int[] solution) {
		TGGOverlap overlap = new TGGOverlap(sourceRule, targetRule);
		
		overlap.deletions.addAll(TGGCollectionUtil.filterNodes(sourceRule.getNodes(), BindingType.CREATE));
		overlap.deletions.addAll(TGGCollectionUtil.filterEdges(sourceEdgeMap.values(), BindingType.CREATE));

		overlap.creations.addAll(TGGCollectionUtil.filterNodes(targetRule.getNodes(), BindingType.CREATE));
		overlap.creations.addAll(TGGCollectionUtil.filterEdges(targetEdgeMap.values(), BindingType.CREATE));

		overlap.unboundContext.addAll(TGGCollectionUtil.filterNodes(sourceRule.getNodes(), BindingType.CONTEXT));
		overlap.unboundContext.addAll(TGGCollectionUtil.filterEdges(sourceEdgeMap.values(), BindingType.CONTEXT));
		
		overlap.unboundContext.addAll(TGGCollectionUtil.filterNodes(targetRule.getNodes(), BindingType.CONTEXT));
		overlap.unboundContext.addAll(TGGCollectionUtil.filterEdges(targetEdgeMap.values(), BindingType.CONTEXT));

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
			overlap.unboundContext.remove(sourceElement);
			overlap.unboundContext.remove(targetElement);
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
	
						addNode2CandidateMapping(sourceNode, candidate);
						addNode2CandidateMapping(targetNode, candidate);
					}
			}
		}
		return candidates;
	}

	private Collection<EdgeCandidate> calculateEdgeCandidates(TGGRule sourceRule, TGGRule targetRule, boolean mapContext) {
		Collection<EdgeCandidate> candidates = new ArrayList<>();

		sourceRule.getNodes().stream().flatMap(n -> n.getOutgoingEdges().stream()).forEach(e -> sourceEdgeMap.put(e.getSrcNode().getName() + "__" + e.getType().getName() + "__"  + e.getTrgNode().getName(), e));
		targetRule.getNodes().stream().flatMap(n -> n.getOutgoingEdges().stream()).forEach(e -> targetEdgeMap.put(e.getSrcNode().getName() + "__" + e.getType().getName() + "__"  + e.getTrgNode().getName(), e));
		
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

	private void registerNodeCandidates(Collection<NodeCandidate> candidates) {
		for (NodeCandidate candidate : candidates) {
			nodeCandidate2id.put(candidate, idCounter);
			id2node.put(idCounter, candidate);
			idCounter++;
		}
	}

	private void registerEdgeCandidates(Collection<EdgeCandidate> candidates) {
		for (EdgeCandidate candidate : candidates) {
			edgeCandidate2id.put(candidate, idCounter);
			id2edge.put(idCounter, candidate);
			idCounter++;
		}
	}

	private void defineILPImplications(BinaryILPProblem ilpProblem) {
		for (TGGRuleEdge edge : edge2candidate.keySet()) {
			Set<Integer> nodeIDs = new IntOpenHashSet();
			nodeIDs.addAll(node2candidate.getOrDefault(edge.getSrcNode(), new ObjectOpenHashSet<>()));
			nodeIDs.addAll(node2candidate.getOrDefault(edge.getTrgNode(), new ObjectOpenHashSet<>()));

			ilpProblem.addNegativeImplication(nodeIDs.stream().map(m -> "x" + m).collect(Collectors.toList()), edge2candidate.get(edge).stream().map(m -> "x" + m).collect(Collectors.toList()),
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

		ilpProblem.addExclusion(candidates.stream().map(v -> "x" + v).collect(Collectors.toList()), "EXCL_nodeOnce_" + node.eClass().getName() + "_" + nameCounter++);
	}

	private void defineILPExclusions(BinaryILPProblem ilpProblem, TGGRuleEdge edge) {
		Set<Integer> candidates = edge2candidate.get(edge);
		if (candidates.size() <= 1)
			return;

		ilpProblem.addExclusion(candidates.stream().map(v -> "x" + v).collect(Collectors.toList()), "EXCL_nodeOnce_" + edge.eClass().getName() + "_" + nameCounter++);
	}

	private void defineILPObjective(BinaryILPProblem ilpProblem) {
		ILPLinearExpression expr = ilpProblem.createLinearExpression();

		for (int i = 0; i < idCounter; i++)
			expr.addTerm("x" + i, 1);

		ilpProblem.setObjective(expr, Objective.maximize);
	}

	private boolean ruleMatches(TGGRule sourceRule, TGGRule targetRule) {
		// TODO lfritsche : extend inheritance concept
		Set<EClass> classes = new ObjectOpenHashSet<>();
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
		Set<Integer> nodeCandidateIDs = node2candidate.getOrDefault(node, new IntOpenHashSet());
		nodeCandidateIDs.add(nodeCandidate2id.getInt(candidate));
		node2candidate.put(node, nodeCandidateIDs);
	}

	private void addEdge2CandidateMapping(TGGRuleEdge edge, EdgeCandidate candidate) {
		Set<Integer> edgeCandidateIDs = edge2candidate.getOrDefault(edge, new IntOpenHashSet());
		edgeCandidateIDs.add(edgeCandidate2id.getInt(candidate));
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
