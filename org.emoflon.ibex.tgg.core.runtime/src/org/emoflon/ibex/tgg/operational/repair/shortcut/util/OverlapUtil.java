package org.emoflon.ibex.tgg.operational.repair.shortcut.util;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;
import static org.emoflon.ibex.tgg.operational.repair.util.TGGFilterUtil.isAxiomatic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.emoflon.ibex.tgg.operational.debug.LoggerConfig;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.repair.shortcut.rule.ShortcutRule;
import org.emoflon.ibex.tgg.operational.repair.util.TGGFilterUtil;
import org.emoflon.ibex.tgg.util.ilp.ILPFactory.SupportedILPSolver;

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

	private Collection<TGGOverlap> overlaps;

	public OverlapUtil(IbexOptions options) {
		this.options = options;
		overlaps = cfactory.createObjectSet();
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
				} else if (rulesMatch(originalRule, replacingRule)) {
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

	private TGGOverlap createOverlap(TGGRule originalRule, TGGRule replacingRule, boolean mapContext) {
		LoggerConfig.log(LoggerConfig.log_repair(), () -> "Attempting to solve ILP for SC-Rule: '" //
				+ originalRule.getName() + "' -> '" + replacingRule.getName() //
				+ (mapContext ? "' with context mapping" : "'"));
		
		ILPOverlapSolver overlapSolver = new ILPOverlapSolver( //
				calculateNodeCandidates(originalRule, replacingRule, mapContext), //
				calculateEdgeCandidates(originalRule, replacingRule, mapContext), //
				SupportedILPSolver.Sat4J);

		return createOverlapFromILPSolution(originalRule, replacingRule, //
				overlapSolver.solvedNodeCandidates(), overlapSolver.solvedEdgeCandidates());
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
			Collection<NodeCandidate> solvedNodeCandidates, Collection<EdgeCandidate> solvedEdgeCandidates) {
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
