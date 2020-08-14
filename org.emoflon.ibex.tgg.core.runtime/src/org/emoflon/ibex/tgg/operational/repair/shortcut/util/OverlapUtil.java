package org.emoflon.ibex.tgg.operational.repair.shortcut.util;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;
import static org.emoflon.ibex.tgg.operational.repair.util.TGGFilterUtil.isAxiomatic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.tgg.operational.debug.LoggerConfig;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.repair.shortcut.rule.ShortcutRule;
import org.emoflon.ibex.tgg.operational.repair.util.TGGFilterUtil;
import org.emoflon.ibex.tgg.util.ilp.BinaryILPProblem;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPLinearExpression;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.Objective;

import language.BindingType;
import language.DomainType;
import language.TGG;
import language.TGGRule;
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

/**
 * 
 * This class calculates overlaps of all pairs of rules of a TGG to identify
 * which elements are to be preserved when we transform a original rule match to
 * a replacing rule match. These overlaps are then used to generated shortcut
 * rules. The overlaps themselves are calculated by formulating the overlap
 * problem as an ILP by defining matching candidates between both original and
 * replacing rule.
 * 
 * @author lfritsche
 *
 */
public class OverlapUtil {

	private IbexOptions options;

	public OverlapUtil(IbexOptions options) {
		this.options = options;
	}

	public Collection<ShortcutRule> calculateShortcutRules(TGG tgg) {
		return calculateOverlaps(tgg).stream() //
				.map(overlap -> new ShortcutRule(overlap, options)) //
				.collect(Collectors.toList());
	}

	private Collection<TGGOverlap> calculateOverlaps(TGG tgg) {
		LoggerConfig.log(LoggerConfig.log_repair(), () -> "Creating ILP problems for ShortCut-Rules");

		Collection<TGGOverlap> overlaps = cfactory.createObjectSet();
		// overlap all rules (also with themselves)
		for (int i = 0; i < tgg.getRules().size(); i++) {
			for (int j = i; j < tgg.getRules().size(); j++) {
				TGGRule originalRule = tgg.getRules().get(i);
				TGGRule replacingRule = tgg.getRules().get(j);

				if (originalRule.isAbstract() || replacingRule.isAbstract())
					continue;

				if (originalRule.equals(replacingRule)) {
					if(isAxiomatic(originalRule))
						continue;
					overlaps.add(createOverlap(originalRule, replacingRule, false, OverlapCategory.MOVER));

					// only generate rules with overlapped context if injectivity is checked!
					if (options.repair.advancedOverlapStrategies() && !options.repair.disableInjectivity())
						overlaps.addAll(createAdvancedOverlaps(originalRule));
				} else if (rulesMatch(originalRule, replacingRule)) {
					boolean isOrigAxio = isAxiomatic(originalRule);
					boolean isReplAxio = isAxiomatic(replacingRule);
					if (isOrigAxio || isReplAxio) {
						overlaps.add(createOverlap(originalRule, replacingRule, false,
								isOrigAxio ? OverlapCategory.JOINER : OverlapCategory.CUTTER));
						overlaps.add(createOverlap(replacingRule, originalRule, false,
								isReplAxio ? OverlapCategory.JOINER : OverlapCategory.CUTTER));
					} else {
						// only generate rules with overlapped context if injectivity is checked!
						if(!options.repair.disableInjectivity()) {
							overlaps.add(createOverlap(originalRule, replacingRule, true, OverlapCategory.CHANGER));
							overlaps.add(createOverlap(replacingRule, originalRule, true, OverlapCategory.CHANGER));
						}
						overlaps.add(createOverlap(originalRule, replacingRule, false, OverlapCategory.COMBI));
						overlaps.add(createOverlap(replacingRule, originalRule, false, OverlapCategory.COMBI));
					}
				}
			}
		}
		filterUselessOverlaps(overlaps);
		return overlaps;
	}

	private TGGOverlap createOverlap(TGGRule originalRule, TGGRule replacingRule, boolean mapContext,
			OverlapCategory category) {
		ILPOverlapSolver overlapSolver = new ILPOverlapSolver( //
				calculateNodeCandidates(originalRule, replacingRule, mapContext), //
				calculateEdgeCandidates(originalRule, replacingRule, mapContext), //
				options.ilpSolver());

		return createOverlapFromILPSolution(originalRule, replacingRule, //
				overlapSolver.solvedNodeCandidates(), overlapSolver.solvedEdgeCandidates(), category);
	}

	private List<NodeCandidate> calculateNodeCandidates(TGGRule originalRule, TGGRule replacingRule,
			boolean mapContext) {
		List<NodeCandidate> candidates = new ArrayList<>();
		for (TGGRuleNode originalNode : originalRule.getNodes()) {
			for (TGGRuleNode replacingNode : replacingRule.getNodes()) {
				if (nodesMatch(originalNode, replacingNode, mapContext))
					candidates.add(new NodeCandidate(originalNode, replacingNode));
			}
		}
		return candidates;
	}

	private Collection<EdgeCandidate> calculateEdgeCandidates(TGGRule originalRule, TGGRule replacingRule,
			boolean mapContext) {
		Collection<EdgeCandidate> candidates = new ArrayList<>();
		for (TGGRuleEdge originalEdge : originalRule.getEdges()) {
			for (TGGRuleEdge replacingEdge : replacingRule.getEdges()) {
				if (edgesMatch(originalEdge, replacingEdge, mapContext))
					candidates.add(new EdgeCandidate(originalEdge, replacingEdge));
			}
		}
		return candidates;
	}

	private boolean rulesMatch(TGGRule originalRule, TGGRule replacingRule) {
		Set<EClass> originalRuleClasses = cfactory.createObjectSet();
		// TODO lfritsche : insert operationalization (FWD BWD) splitting
		originalRuleClasses.addAll(TGGFilterUtil.filterNodes(originalRule.getNodes(), BindingType.CREATE).stream() //
				.map(c -> c.getType()) //
				.collect(Collectors.toSet()));
		for (TGGRuleNode replacingNode : TGGFilterUtil.filterNodes(replacingRule.getNodes(), BindingType.CREATE)) {
			for (EClass eClass : originalRuleClasses)
				if (typesMatch(eClass, replacingNode.getType(), false))
					return true;
		}
		return false;
	}

	private boolean nodesMatch(TGGRuleNode originalNode, TGGRuleNode replacingNode, boolean mapContext) {
		if (!originalNode.getDomainType().equals(replacingNode.getDomainType())) // domain matches
			return false;
		if (!originalNode.getBindingType().equals(replacingNode.getBindingType())) // binding matches
			return false;
		boolean isContext = originalNode.getBindingType().equals(BindingType.CONTEXT);
		if (!(mapContext ? true : !isContext)) // map context
			return false;
		if (!typesMatch(originalNode.getType(), replacingNode.getType(), isContext)) // type matches
			return false;
		return true;
	}

	private boolean edgesMatch(TGGRuleEdge originalEdge, TGGRuleEdge replacingEdge, boolean mapContext) {
		boolean domainMatches = originalEdge.getDomainType().equals(replacingEdge.getDomainType());
		boolean typeMatches = originalEdge.getType().equals(replacingEdge.getType());
		boolean bindingMatches = originalEdge.getBindingType().equals(replacingEdge.getBindingType()) //
				&& (mapContext ? true : !originalEdge.getBindingType().equals(BindingType.CONTEXT));
		return domainMatches && typeMatches && bindingMatches;
	}

	private boolean typesMatch(EClass originalType, EClass replacingType, boolean areContext) {
		if (areContext)
			return originalType.isSuperTypeOf(replacingType) || replacingType.isSuperTypeOf(originalType);
		return originalType.equals(replacingType);
	}

	private TGGOverlap createOverlapFromILPSolution(TGGRule originalRule, TGGRule replacingRule,
			Collection<NodeCandidate> solvedNodeCandidates, Collection<EdgeCandidate> solvedEdgeCandidates,
			OverlapCategory category) {
		TGGOverlap overlap = new TGGOverlap(originalRule, replacingRule);
	
		overlap.deletions.addAll(TGGFilterUtil.filterNodes(originalRule.getNodes(), BindingType.CREATE));
		overlap.deletions.addAll(TGGFilterUtil.filterEdges(originalRule.getEdges(), BindingType.CREATE));
	
		overlap.creations.addAll(TGGFilterUtil.filterNodes(replacingRule.getNodes(), BindingType.CREATE));
		overlap.creations.addAll(TGGFilterUtil.filterEdges(replacingRule.getEdges(), BindingType.CREATE));
	
		overlap.unboundOriginalContext.addAll(TGGFilterUtil.filterNodes(originalRule.getNodes(), BindingType.CONTEXT));
		overlap.unboundOriginalContext.addAll(TGGFilterUtil.filterEdges(originalRule.getEdges(), BindingType.CONTEXT));
	
		overlap.unboundReplacingContext.addAll(TGGFilterUtil.filterNodes(replacingRule.getNodes(), BindingType.CONTEXT));
		overlap.unboundReplacingContext.addAll(TGGFilterUtil.filterEdges(replacingRule.getEdges(), BindingType.CONTEXT));
		
		solvedNodeCandidates.forEach(cdt -> processOverlapCandidate(overlap, cdt.originalNode, cdt.replacingNode));
		solvedEdgeCandidates.forEach(cdt -> processOverlapCandidate(overlap, cdt.originalEdge, cdt.replacingEdge));
		
		overlap.category = category;
		
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
					"TGGRuleElement are not allowed to have the binding type DELETE given by the user specification "
							+ "due to the fact that TGG rules are strictly monotonic");
		}
	}

	private void filterUselessOverlaps(Collection<TGGOverlap> overlaps) {
		overlaps.removeIf(o -> !containsBothDomains(o) // mapping does not contain both domains
				|| o.mappings.size() == 0 // has no mapping
				|| (o.creations.size() == 0 && o.deletions.size() == 0)); // only has context
	}

	private boolean containsBothDomains(TGGOverlap overlap) {
		boolean containsSrc = overlap.mappings.keySet().stream()
				.anyMatch(k -> k.getDomainType() == DomainType.SRC && k.getBindingType() == BindingType.CREATE);
		boolean containsTrg = overlap.mappings.keySet().stream()
				.anyMatch(k -> k.getDomainType() == DomainType.TRG && k.getBindingType() == BindingType.CREATE);
		return containsSrc && containsTrg;
	}

	private Collection<TGGOverlap> createAdvancedOverlaps(TGGRule rule) {
		Set<TGGOverlap> overlaps = new HashSet<>();
		Collection<Implication> implications = new HashSet<>();
		Collection<TGGRuleNode> flexibleNodes = new HashSet<>(rule.getNodes());

		addCorrUnionImplications(implications, rule, flexibleNodes);
		addEdgeMultiplicityImplications(implications, rule);

		for (TGGRuleNode flexNode : flexibleNodes) {
			// TODO extend advanced overlaps to created flexible nodes?
			if (flexNode.getBindingType() == BindingType.CREATE)
				continue;

			ILPOverlapSolver overlapSolver = configureAndSolveILP(rule, implications, flexNode);

			overlaps.add(createOverlapFromILPSolution(rule, rule, overlapSolver.solvedNodeCandidates(),
					overlapSolver.solvedEdgeCandidates(), OverlapCategory.ADV_MOVER));
		}

		return overlaps;
	}

	private ILPOverlapSolver configureAndSolveILP(TGGRule rule, Collection<Implication> implications,
			TGGRuleNode flexNode) {
		return new ILPOverlapSolver( //
				rule.getNodes().stream().map(n -> new NodeCandidate(n, n)).collect(Collectors.toList()), //
				rule.getEdges().stream().map(e -> new EdgeCandidate(e, e)).collect(Collectors.toList()), //
				options.ilpSolver()) {
			@Override
			protected void defineMoreILPConditions(BinaryILPProblem ilpProblem) {
				implications.forEach(i -> i.add(ilpProblem, nameCounter, node2cdts));
			}

			@Override
			protected void defineILPObjective(BinaryILPProblem ilpProblem) {
				ILPLinearExpression expr = ilpProblem.createLinearExpression();
				for (int i = 0; i < idCounter; i++) {
					double coefficient = 0;
					if (id2nodeCdt.containsKey(i)) {
						TGGRuleNode currentNode = id2nodeCdt.get(i).originalNode;
						if (flexNode.equals(currentNode) || currentNode.getBindingType() != flexNode.getBindingType())
							coefficient = 1;
						else
							coefficient = -0.0001;
					} else if (id2edgeCdt.containsKey(i)) {
						coefficient = 0.00000001;
					}
					expr.addTerm("x" + i, coefficient);
				}
				ilpProblem.setObjective(expr, Objective.maximize);
			}
		};
	}

	private void addCorrUnionImplications(Collection<Implication> implications, TGGRule rule,
			Collection<TGGRuleNode> flexibleNodes) {
		Collection<TGGRuleCorr> corrNodes = rule.getNodes().stream() //
				.filter(n -> n instanceof TGGRuleCorr) //
				.map(n -> (TGGRuleCorr) n) //
				.collect(Collectors.toSet());
		
		corrNodes.forEach(corr -> {
			flexibleNodes.remove(corr.getSource());
			flexibleNodes.remove(corr.getTarget());
			
			implications.add((problem, counter, node2cdts) -> {
				Set<Integer> srcCdts = node2cdts.get(corr.getSource());
				Set<Integer> trgCdts = node2cdts.get(corr.getTarget());
				for (int corrCdt : node2cdts.get(corr)) {
					problem.addImplication("x" + corrCdt, srcCdts.stream().map(v -> "x" + v), //
							"IMPL_ADVCORRS_" + corr.getType().getName() + counter++);
					problem.addImplication("x" + corrCdt, trgCdts.stream().map(v -> "x" + v), //
							"IMPL_ADVCORRT_" + corr.getType().getName() + counter++);
				}
			});
		});
	}
	
	private void addEdgeMultiplicityImplications(Collection<Implication> implications, TGGRule rule) {
		rule.getEdges().stream() //
				.filter(e -> e.getDomainType() == DomainType.SRC || e.getDomainType() == DomainType.TRG) //
				.filter(e -> !isInterfaceEdge(e)) //
				.forEach(edge -> {
					EReference ref = edge.getType();
					if (!ref.isMany() || ref.isContainment())
						implications.add((problem, counter, node2cdts) -> {
							Set<Integer> srcCdts = node2cdts.get(edge.getSrcNode());
							Set<Integer> trgCdts = node2cdts.get(edge.getTrgNode());
							if (!ref.isMany())
								for (int srcCdt : srcCdts) {
									problem.addImplication("x" + srcCdt, trgCdts.stream().map(v -> "x" + v), //
											"IMPL_ADVEDGES_" + edge.getType().getName() + counter++);
								}
							if (ref.isContainment())
								for (int trgCdt : trgCdts) {
									problem.addImplication("x" + trgCdt, srcCdts.stream().map(v -> "x" + v), //
											"IMPL_ADVEDGET_" + edge.getType().getName() + counter++);
								}
						});
				});
	}

	private boolean isInterfaceEdge(TGGRuleEdge edge) {
		if (edge.getBindingType() == BindingType.CREATE)
			return edge.getSrcNode().getBindingType() == BindingType.CONTEXT
					|| edge.getTrgNode().getBindingType() == BindingType.CONTEXT;
		return false;
	}
	
	@FunctionalInterface
	private interface Implication {
		void add(BinaryILPProblem ilpProblem, int nameCounter, Map<TGGRuleNode, Set<Integer>> node2cdts);
	}

	class NodeCandidate {
		public TGGRuleNode originalNode;
		public TGGRuleNode replacingNode;

		public NodeCandidate(TGGRuleNode originalNode, TGGRuleNode replacingNode) {
			this.originalNode = originalNode;
			this.replacingNode = replacingNode;
		}
	}

	class EdgeCandidate {
		public TGGRuleEdge originalEdge;
		public TGGRuleEdge replacingEdge;

		public EdgeCandidate(TGGRuleEdge originalEdge, TGGRuleEdge replacingEdge) {
			this.originalEdge = originalEdge;
			this.replacingEdge = replacingEdge;
		}
	}
}
