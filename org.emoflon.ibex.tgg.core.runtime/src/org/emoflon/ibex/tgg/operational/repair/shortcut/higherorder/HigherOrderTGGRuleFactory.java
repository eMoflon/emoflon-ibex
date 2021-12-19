package org.emoflon.ibex.tgg.operational.repair.shortcut.higherorder;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.repair.shortcut.higherorder.HigherOrderTGGRule.ComponentSpecificRuleElement;
import org.emoflon.ibex.tgg.operational.repair.shortcut.higherorder.HigherOrderTGGRule.HigherOrderRuleComponent;
import org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer.PrecedenceGraph;
import org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer.PrecedenceNode;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.EltFilter;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.TGGMatchUtil;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.TGGMatchUtilProvider;
import org.emoflon.ibex.tgg.util.TGGFilterUtil;
import org.emoflon.ibex.tgg.util.TGGInplaceAttrExprUtil;
import org.emoflon.ibex.tgg.util.TGGModelUtils;

import language.BindingType;
import language.DomainType;
import language.TGGEnumExpression;
import language.TGGExpression;
import language.TGGInplaceAttributeExpression;
import language.TGGLiteralExpression;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class HigherOrderTGGRuleFactory {

	private final PrecedenceGraph pg;
	private final TGGMatchUtilProvider mu;

	public HigherOrderTGGRuleFactory(PrecedenceGraph pg, TGGMatchUtilProvider mu) {
		this.pg = pg;
		this.mu = mu;
	}

	public HigherOrderTGGRule createHigherOrderTGGRuleFromConsMatches(List<PrecedenceNode> nodes) {
		validateInputNodes(nodes);

		HigherOrderTGGRule higherOrderRule = new HigherOrderTGGRule();
		for (PrecedenceNode node : nodes) {
			TGGMatchUtil matchUtil = mu.get(node.getMatch());
			TGGRule rule = matchUtil.getRule();
			Map<TGGRuleElement, ComponentSpecificRuleElement> contextMapping = nodes.indexOf(node) == 0 //
					? new HashMap<>()
					: createMappingForCons(higherOrderRule, node, matchUtil);
			higherOrderRule.addComponentLast(rule, node.getMatch(), contextMapping);
		}

		return higherOrderRule;
	}

	private Map<TGGRuleElement, ComponentSpecificRuleElement> createMappingForCons(HigherOrderTGGRule higherOrderRule, PrecedenceNode node,
			TGGMatchUtil matchUtil) {
		Map<TGGRuleElement, ComponentSpecificRuleElement> contextMapping = new HashMap<>();

		Set<Object> objects = matchUtil.getObjects(new EltFilter().srcAndTrg().context());
		for (Object obj : objects) {
			TGGRuleElement ruleElement = matchUtil.getElement(obj);
			Set<PrecedenceNode> mappedNodes = pg.getNodesTranslating(obj);
			for (PrecedenceNode mappedNode : mappedNodes) {
				if (mappedNode.getMatch().getType() != PatternType.CONSISTENCY)
					continue;

				HigherOrderRuleComponent mappedComponent = higherOrderRule.getComponent(mappedNode.getMatch());
				if (mappedComponent == null)
					continue;

				TGGMatchUtil mappedMatchUtil = mu.get(mappedNode.getMatch());
				TGGRuleElement mappedRuleElement = mappedMatchUtil.getElement(obj);
				contextMapping.put(ruleElement, new ComponentSpecificRuleElement(mappedRuleElement, mappedComponent));
				break;
			}
		}

		return contextMapping;
	}

	public HigherOrderTGGRule createHigherOrderTGGRuleFromSrcTrgNodes(List<PrecedenceNode> nodes, DomainType propagationDomain) {
		validateInputNodes(nodes);

		HigherOrderTGGRule higherOrderRule = new HigherOrderTGGRule();
		for (PrecedenceNode node : nodes) {
			TGGMatchUtil matchUtil = mu.get(node.getMatch());
			TGGRule rule = matchUtil.getRule();

			Map<TGGRuleElement, Set<MatchRelatedRuleElement>> propDomainMapping = createMappingForPropagationDomain( //
					node, matchUtil, propagationDomain, nodes);
			Map<TGGRuleElement, Set<MatchRelatedRuleElement>> oppositeDomainMapping = createMappingForOppositeDomain( //
					node, matchUtil, propagationDomain, nodes);
		}

		// TODO continue

		return higherOrderRule;
	}

	private Map<TGGRuleElement, Set<MatchRelatedRuleElement>> createMappingForPropagationDomain(PrecedenceNode node, TGGMatchUtil matchUtil,
			DomainType propagationDomain, List<PrecedenceNode> nodes) {
		Map<TGGRuleElement, Set<MatchRelatedRuleElement>> contextMapping = new HashMap<>();

		Set<Object> objects = matchUtil.getObjects(new EltFilter().domains(propagationDomain).context());
		for (Object obj : objects) {
			TGGRuleElement ruleElement = matchUtil.getElement(obj);
			Set<PrecedenceNode> mappedNodes = pg.getNodesTranslating(obj);

			Set<MatchRelatedRuleElement> mappedElements = new HashSet<>();
			for (PrecedenceNode mappedNode : mappedNodes) {
				if (mappedNode.getMatch().getType() != node.getMatch().getType())
					continue;

				if (!nodes.contains(mappedNode))
					continue;

				TGGMatchUtil mappedMatchUtil = mu.get(mappedNode.getMatch());
				TGGRuleElement mappedRuleElement = mappedMatchUtil.getElement(obj);
				mappedElements.add(new MatchRelatedRuleElement(mappedRuleElement, mappedNode.getMatch()));
			}

			if (!mappedElements.isEmpty())
				contextMapping.put(ruleElement, mappedElements);
		}

		return contextMapping;
	}

	private Map<TGGRuleElement, Set<MatchRelatedRuleElement>> createMappingForOppositeDomain(PrecedenceNode node, TGGMatchUtil matchUtil,
			DomainType propagationDomain, List<PrecedenceNode> nodes) {
		DomainType oppositeDomain = TGGModelUtils.oppositeOf(propagationDomain);
		TGGRule rule = matchUtil.getRule();

		Map<TGGRule, Set<ITGGMatch>> rule2matches = new HashMap<>();
		for (PrecedenceNode requiredNode : node.getRequires()) {
			PatternType requiredNodesType = requiredNode.getMatch().getType();
			if (!(requiredNodesType == node.getMatch().getType() || requiredNodesType == PatternType.CONSISTENCY))
				continue;

			if (requiredNode.isBroken())
				continue;

			TGGRule requiredNodesRule = mu.get(requiredNode.getMatch()).getRule();
			rule2matches.computeIfAbsent(requiredNodesRule, k -> new HashSet<>()).add(requiredNode.getMatch());
		}

		Map<TGGRuleElement, Set<MatchRelatedRuleElement>> contextMapping = new HashMap<>();

		Collection<TGGRuleNode> ruleNodes = TGGFilterUtil.filterNodes(rule.getNodes(), oppositeDomain, BindingType.CONTEXT);
		for (TGGRuleNode ruleNode : ruleNodes) {
			// TODO cache node type to {rule & node}
			Set<MatchRelatedRuleElement> mappedElements = new HashSet<>();
			for (TGGRule mappedRule : rule2matches.keySet()) {
				Collection<TGGRuleNode> mappedNodes = TGGFilterUtil.filterNodes(mappedRule.getNodes(), oppositeDomain, BindingType.CREATE);
				for (TGGRuleNode mappedNode : mappedNodes) {
					if (matchesContext(ruleNode, mappedNode)) {
						for (ITGGMatch mappedMatch : rule2matches.get(mappedRule))
							mappedElements.add(new MatchRelatedRuleElement(mappedNode, mappedMatch));
					}
				}
			}

			if (!mappedElements.isEmpty())
				contextMapping.put(ruleNode, mappedElements);
		}

		Collection<TGGRuleEdge> ruleEdges = TGGFilterUtil.filterEdges(rule.getEdges(), oppositeDomain, BindingType.CONTEXT);
		for (TGGRuleEdge ruleEdge : ruleEdges) {
			Set<MatchRelatedRuleElement> mappedElements = new HashSet<>();
			for (TGGRule mappedRule : rule2matches.keySet()) {
				Collection<TGGRuleEdge> mappedEdges = TGGFilterUtil.filterEdges(mappedRule.getEdges(), oppositeDomain, BindingType.CREATE);
				for (TGGRuleEdge mappedEdge : mappedEdges) {
					if (matchesContext(ruleEdge, mappedEdge)) {
						for (ITGGMatch mappedMatch : rule2matches.get(mappedRule))
							mappedElements.add(new MatchRelatedRuleElement(mappedEdge, mappedMatch));
					}
				}
			}

			if (!mappedElements.isEmpty())
				contextMapping.put(ruleEdge, mappedElements);
		}

		return contextMapping;
	}

	private boolean matchesContext(TGGRuleNode context, TGGRuleNode matchCandidate) {
		if (!context.getType().isSuperTypeOf(matchCandidate.getType()))
			return false;

		for (TGGInplaceAttributeExpression contextAttrExpr : context.getAttrExpr()) {
			for (TGGInplaceAttributeExpression candidateAttrExpr : matchCandidate.getAttrExpr()) {
				if (!contextAttrExpr.getAttribute().equals(candidateAttrExpr.getAttribute()))
					continue;

				Object candidateAttrValue;
				TGGExpression candidateAttrValueExpr = candidateAttrExpr.getValueExpr();
				if (candidateAttrValueExpr instanceof TGGLiteralExpression)
					candidateAttrValue = ((TGGLiteralExpression) candidateAttrValueExpr).getValue();
				else if (candidateAttrValueExpr instanceof TGGEnumExpression)
					candidateAttrValue = ((TGGEnumExpression) candidateAttrValueExpr).getLiteral().getInstance();
				else
					continue;

				if (!TGGInplaceAttrExprUtil.checkInplaceAttributeCondition(contextAttrExpr, candidateAttrValue, null))
					return false;
			}
		}

		return true;
	}

	private boolean matchesContext(TGGRuleEdge context, TGGRuleEdge matchCandidate) {
		// TODO check for more restrictions?
		return context.getType().equals(matchCandidate.getType());
	}

	private void validateInputNodes(List<PrecedenceNode> nodes) {
		if (nodes.isEmpty())
			throw new RuntimeException("There must be at least one simple rule to create a higher-order TGG rule!");
	}

	private static class MatchRelatedRuleElement {
		final TGGRuleElement ruleElement;
		final ITGGMatch match;

		MatchRelatedRuleElement(TGGRuleElement ruleElement, ITGGMatch match) {
			this.ruleElement = ruleElement;
			this.match = match;
		}

		@Override
		public int hashCode() {
			return Objects.hash(match, ruleElement);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			MatchRelatedRuleElement other = (MatchRelatedRuleElement) obj;
			return Objects.equals(match, other.match) && Objects.equals(ruleElement, other.ruleElement);
		}
	}

}
