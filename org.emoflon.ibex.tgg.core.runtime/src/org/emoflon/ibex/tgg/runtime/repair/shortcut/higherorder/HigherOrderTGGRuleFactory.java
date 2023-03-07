package org.emoflon.ibex.tgg.runtime.repair.shortcut.higherorder;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.higherorder.HigherOrderTGGRule.ComponentSpecificRuleElement;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.higherorder.HigherOrderTGGRule.HigherOrderRuleComponent;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.matchcontainer.PrecedenceGraph;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.matchcontainer.PrecedenceNode;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.util.EltFilter;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.util.TGGMatchUtil;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.util.TGGMatchUtilProvider;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGCorrespondence;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGEdge;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleElement;
import org.emoflon.ibex.tgg.util.TGGFilterUtil;
import org.emoflon.ibex.tgg.util.TGGInplaceAttrExprUtil;
import org.emoflon.ibex.tgg.util.TGGModelUtils;

import language.TGGEnumExpression;
import language.TGGExpression;
import language.TGGInplaceAttributeExpression;
import language.TGGLiteralExpression;
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class HigherOrderTGGRuleFactory {

	private final IbexOptions options;
	private final PrecedenceGraph pg;
	private final TGGMatchUtilProvider mup;

	public HigherOrderTGGRuleFactory(IbexOptions options, PrecedenceGraph pg, TGGMatchUtilProvider mup) {
		this.options = options;
		this.pg = pg;
		this.mup = mup;
	}

	//// MAPPING FROM CONSISTENCY ////

	public HigherOrderTGGRule createHigherOrderTGGRuleFromConsMatches(List<PrecedenceNode> pgNodes) {
		validateInputNodes(pgNodes);

		HigherOrderTGGRule higherOrderRule = new HigherOrderTGGRule();
		for (PrecedenceNode pgNode : pgNodes) {
			TGGMatchUtil matchUtil = mup.get(pgNode.getMatch());
			TGGRule rule = matchUtil.getRule();
			// the first PG-node in the list will have no predecessors in the higher-order rule, therefore we
			// don't need to create a mapping for it's context:
			Map<TGGRuleElement, ComponentSpecificRuleElement> contextMapping = pgNodes.indexOf(pgNode) == 0 //
					? new HashMap<>()
					: createMappingForCons(higherOrderRule, matchUtil);
			higherOrderRule.addComponent(rule, pgNode.getMatch(), contextMapping);
		}

		return higherOrderRule;
	}

	private Map<TGGRuleElement, ComponentSpecificRuleElement> createMappingForCons(HigherOrderTGGRule higherOrderRule, TGGMatchUtil matchUtil) {
		Map<TGGRuleElement, ComponentSpecificRuleElement> contextMapping = new HashMap<>();

		Set<Object> objects = matchUtil.getObjects(new EltFilter().context());
		// we find the mapping for each context element...
		for (Object obj : objects) {
			TGGRuleElement ruleElement = matchUtil.getElement(obj);
			// ...by getting all PG-nodes translating that element:
			Set<PrecedenceNode> mappedPGNodes = pg.getNodesTranslating(obj);
			for (PrecedenceNode mappedPGNode : mappedPGNodes) {
				if (mappedPGNode.getMatch().getType() != PatternType.CONSISTENCY)
					continue;

				// if we already created a higher-order rule component for this PG-node, we get the matching
				// rule-element & create a mapping:
				HigherOrderRuleComponent mappedComponent = higherOrderRule.getComponent(mappedPGNode.getMatch());
				if (mappedComponent != null) {
					TGGMatchUtil mappedMatchUtil = mup.get(mappedPGNode.getMatch());
					TGGRuleElement mappedRuleElement = mappedMatchUtil.getElement(obj);
					contextMapping.put(ruleElement, mappedComponent.getComponentSpecificRuleElement(mappedRuleElement));
				} else {
					// if we didn't, the mapped PG-node is not part of our higher-order rule; therefore, we have to map
					// the context element to a PG-node's context element, that is part of our higher-order rule
					// ("context to context mapping"):
					for (PrecedenceNode reqPGNode : mappedPGNode.getRequiredBy()) {
						if (reqPGNode.getMatch().getType() != PatternType.CONSISTENCY)
							continue;
						if (reqPGNode.getMatch().getObjects().contains(obj)) {
							mappedComponent = higherOrderRule.getComponent(reqPGNode.getMatch());
							if (mappedComponent == null)
								continue;
							TGGMatchUtil mappedMatchUtil = mup.get(reqPGNode.getMatch());
							TGGRuleElement mappedRuleElement = mappedMatchUtil.getElement(obj);
							contextMapping.put(ruleElement, mappedComponent.getComponentSpecificRuleElement(mappedRuleElement));
						}
					}
				}
				break;
			}
		}

		return contextMapping;
	}

	//// MAPPING FROM SRC/TRG ////

	public HigherOrderTGGRule createHigherOrderTGGRuleFromSrcTrgNodes(List<PrecedenceNode> pgNodes, DomainType propagationDomain) {
		// when creating higher-order rules from source or target matches, there only exist fixed mappings
		// for the elements of the respective domain ("propagation domain"); for the other domain ("opposite
		// domain") a mapping is found by collecting all possible mappings & then using ILP to determine a
		// valid mapping solution

		validateInputNodes(pgNodes);

		Map<PrecedenceNode, Set<MatchRelatedRuleElement>> node2ruleElements = new HashMap<>();

		MatchRelatedRuleElementMap totalPropDomainMappings = new MatchRelatedRuleElementMap();
		MatchRelatedRuleElementMap totalOppositeDomainMappings = new MatchRelatedRuleElementMap();
		MatchRelatedRuleElementMap totalCorrDomainMappings = new MatchRelatedRuleElementMap();
		for (PrecedenceNode pgNode : pgNodes) {
			if (pgNodes.indexOf(pgNode) == 0)
				continue;

			TGGMatchUtil matchUtil = mup.get(pgNode.getMatch());
			Set<MatchRelatedRuleElement> matchRelatedRuleElements = new HashSet<>();

			MatchRelatedRuleElementMap propDomainMapping = createMappingForPropagationDomain(pgNode, matchUtil, propagationDomain, pgNodes);
			matchRelatedRuleElements.addAll(propDomainMapping.keySet());
			totalPropDomainMappings.putAll(propDomainMapping);

			Map<TGGRule, Set<ITGGMatch>> rule2matches = collectRules(pgNode, pgNodes);
			MatchRelatedRuleElementMap oppositeDomainMapping = createMappingForOppositeDomain(pgNode, matchUtil, propagationDomain, pgNodes,
					rule2matches);
			matchRelatedRuleElements.addAll(oppositeDomainMapping.keySet());
			totalOppositeDomainMappings.putAll(oppositeDomainMapping);

			MatchRelatedRuleElementMap corrDomainMapping = createMappingForCorrDomain(pgNode, matchUtil, pgNodes, rule2matches);
			matchRelatedRuleElements.addAll(corrDomainMapping.keySet());
			totalCorrDomainMappings.putAll(corrDomainMapping);

			node2ruleElements.put(pgNode, matchRelatedRuleElements);
		}

		ILPHigherOrderRuleMappingSolver ilpSolver = new ILPHigherOrderRuleMappingSolver( //
				options.ilpSolver(), totalPropDomainMappings, totalOppositeDomainMappings, totalCorrDomainMappings);
		Map<MatchRelatedRuleElement, MatchRelatedRuleElement> overallContextMapping = ilpSolver.getResult();

		// transform ILP solution to higher-order rule:
		HigherOrderTGGRule higherOrderRule = new HigherOrderTGGRule();
		for (PrecedenceNode pgNode : pgNodes) {
			TGGRule rule = mup.get(pgNode.getMatch()).getRule();

			if (pgNodes.indexOf(pgNode) == 0) {
				higherOrderRule.addComponent(rule, pgNode.getMatch(), Collections.emptyMap());
				continue;
			}

			Set<MatchRelatedRuleElement> matchRelatedRuleElements = node2ruleElements.get(pgNode);
			Map<TGGRuleElement, ComponentSpecificRuleElement> contextMapping = matchRelatedRuleElements.stream() //
					.filter(e -> overallContextMapping.containsKey(e)) //
					.collect(Collectors.toMap(e -> e.ruleElement, e -> {
						MatchRelatedRuleElement matchRelRuleElt = overallContextMapping.get(e);
						HigherOrderRuleComponent component = higherOrderRule.getComponent(matchRelRuleElt.match);
						if (component == null)
							throw new RuntimeException(
									"There are missing components which are tried to be referenced. Maybe component adding order is incorrect.");
						return component.getComponentSpecificRuleElement(matchRelRuleElt.ruleElement);
					}));

			// since the ILP solution doesn't include correspondence edges, we have to manually find the
			// correspondence edge mappings:
			Collection<TGGNode> corrNodes = TGGFilterUtil.filterNodes(rule.getNodes(), DomainType.CORRESPONDENCE, BindingType.CONTEXT);
			for (TGGNode corrNode : corrNodes) {
				TGGCorrespondence corr = (TGGCorrespondence) corrNode;
				ComponentSpecificRuleElement mappedCompSpecCorr = contextMapping.get(corr);
				if (mappedCompSpecCorr == null)
					continue;

				TGGCorrespondence mappedCorr = (TGGCorrespondence) mappedCompSpecCorr.ruleElement;
				for (IBeXEdge corrEdge : corr.getOutgoingEdges()) {
					DomainType corrEdgePointDomain = ((TGGNode) corrEdge.getTarget()).getDomainType();
					for (IBeXEdge mappedCorrEdge : mappedCorr.getOutgoingEdges()) {
						if (((TGGNode) mappedCorrEdge.getTarget()).getDomainType() == corrEdgePointDomain) {
							contextMapping.put(corrEdge, mappedCompSpecCorr.component.getComponentSpecificRuleElement(mappedCorrEdge));
							break;
						}
					}
				}
			}

			higherOrderRule.addComponent(rule, pgNode.getMatch(), contextMapping);
		}

		return higherOrderRule;
	}

	private MatchRelatedRuleElementMap createMappingForPropagationDomain(PrecedenceNode pgNode, TGGMatchUtil matchUtil, DomainType propagationDomain,
			List<PrecedenceNode> pgNodes) {
		MatchRelatedRuleElementMap contextMapping = new MatchRelatedRuleElementMap();

		Set<Object> objects = matchUtil.getObjects(new EltFilter().domains(propagationDomain).context());
		for (Object obj : objects) {
			TGGRuleElement ruleElement = matchUtil.getElement(obj);
			Set<PrecedenceNode> mappedPGNodes = pg.getNodesTranslating(obj);

			Set<MatchRelatedRuleElement> mappedElements = new HashSet<>();
			for (PrecedenceNode mappedPGNode : mappedPGNodes) {
				// if there is an intact consistency match covering that object, we don't want to map the object's
				// rule node, except if there is a context to context mapping
				if (mappedPGNode.getMatch().getType() == PatternType.CONSISTENCY && !mappedPGNode.isBroken()) {
					handleContextToContextMapping(pgNode, obj, mappedPGNode, mappedElements);
					break;
				}

				if (mappedPGNode.getMatch().getType() != pgNode.getMatch().getType())
					continue;

				if (pgNodes.contains(mappedPGNode)) {
					TGGMatchUtil mappedMatchUtil = mup.get(mappedPGNode.getMatch());
					TGGRuleElement mappedRuleElement = mappedMatchUtil.getElement(obj);
					mappedElements.add(new MatchRelatedRuleElement(mappedRuleElement, mappedPGNode.getMatch()));
				} else {
					handleContextToContextMapping(pgNode, obj, mappedPGNode, mappedElements);
				}
			}

			if (!mappedElements.isEmpty())
				contextMapping.put(new MatchRelatedRuleElement(ruleElement, pgNode.getMatch()), mappedElements);
		}

		return contextMapping;
	}

	private void handleContextToContextMapping(PrecedenceNode pgNode, Object obj, PrecedenceNode mappedPGNode,
			Set<MatchRelatedRuleElement> mappedElements) {
		for (PrecedenceNode reqPGNode : mappedPGNode.getRequiredBy()) {
			if (reqPGNode.getMatch().getType() != pgNode.getMatch().getType())
				continue;
			if (reqPGNode.equals(pgNode))
				continue;
			if (reqPGNode.getMatch().getObjects().contains(obj)) {
				TGGMatchUtil reqMatchUtil = mup.get(reqPGNode.getMatch());
				TGGRuleElement mappedRuleElement = reqMatchUtil.getElement(obj);
				if (mappedRuleElement.getBindingType() == BindingType.CONTEXT)
					mappedElements.add(new MatchRelatedRuleElement(mappedRuleElement, reqPGNode.getMatch()));
			}
		}
	}

	private MatchRelatedRuleElementMap createMappingForOppositeDomain(PrecedenceNode pgNode, TGGMatchUtil matchUtil, DomainType propagationDomain,
			List<PrecedenceNode> pgNodes, Map<TGGRule, Set<ITGGMatch>> rule2matches) {
		DomainType oppositeDomain = TGGModelUtils.oppositeOf(propagationDomain);
		TGGRule rule = matchUtil.getRule();

		MatchRelatedRuleElementMap contextMapping = new MatchRelatedRuleElementMap();

		Collection<TGGNode> ruleNodes = TGGFilterUtil.filterNodes(rule.getNodes(), oppositeDomain, BindingType.CONTEXT);
		for (TGGNode ruleNode : ruleNodes) {
			// TODO cache node type to {rule & node}
			Set<MatchRelatedRuleElement> mappedElements = new HashSet<>();
			for (TGGRule mappedRule : rule2matches.keySet()) {
				Collection<TGGNode> mappedNodes = TGGFilterUtil.filterNodes(mappedRule.getNodes(), oppositeDomain);
				for (TGGNode mappedNode : mappedNodes) {
					if (matchesContext(ruleNode, mappedNode)) {
						for (ITGGMatch mappedMatch : rule2matches.get(mappedRule))
							mappedElements.add(new MatchRelatedRuleElement(mappedNode, mappedMatch));
					}
				}
			}

			if (!mappedElements.isEmpty())
				contextMapping.put(new MatchRelatedRuleElement(ruleNode, pgNode.getMatch()), mappedElements);
		}

		Collection<TGGEdge> ruleEdges = TGGFilterUtil.filterEdges(rule.getEdges(), oppositeDomain, BindingType.CONTEXT);
		for (TGGEdge ruleEdge : ruleEdges) {
			Set<MatchRelatedRuleElement> mappedElements = new HashSet<>();
			for (TGGRule mappedRule : rule2matches.keySet()) {
				Collection<TGGEdge> mappedEdges = TGGFilterUtil.filterEdges(mappedRule.getEdges(), oppositeDomain);
				for (TGGEdge mappedEdge : mappedEdges) {
					if (matchesContext(ruleEdge, mappedEdge)) {
						for (ITGGMatch mappedMatch : rule2matches.get(mappedRule))
							mappedElements.add(new MatchRelatedRuleElement(mappedEdge, mappedMatch));
					}
				}
			}

			if (!mappedElements.isEmpty())
				contextMapping.put(new MatchRelatedRuleElement(ruleEdge, pgNode.getMatch()), mappedElements);
		}

		return contextMapping;
	}

	private MatchRelatedRuleElementMap createMappingForCorrDomain(PrecedenceNode pgNode, TGGMatchUtil matchUtil, List<PrecedenceNode> pgNodes,
			Map<TGGRule, Set<ITGGMatch>> rule2matches) {
		TGGRule rule = matchUtil.getRule();

		MatchRelatedRuleElementMap contextMapping = new MatchRelatedRuleElementMap();

		Collection<TGGNode> ruleCorrs = TGGFilterUtil.filterNodes(rule.getNodes(), DomainType.CORRESPONDENCE, BindingType.CONTEXT);
		for (TGGNode ruleCorr : ruleCorrs) {
			Set<MatchRelatedRuleElement> mappedElements = new HashSet<>();
			for (TGGRule mappedRule : rule2matches.keySet()) {
				Collection<TGGNode> mappedCorrs = TGGFilterUtil.filterNodes(mappedRule.getNodes(), DomainType.CORRESPONDENCE);
				for (TGGNode mappedCorr : mappedCorrs) {
					if (matchesContext((TGGCorrespondence) ruleCorr, (TGGCorrespondence) mappedCorr)) {
						for (ITGGMatch mappedMatch : rule2matches.get(mappedRule))
							mappedElements.add(new MatchRelatedRuleElement(mappedCorr, mappedMatch));
					}
				}
			}

			if (!mappedElements.isEmpty())
				contextMapping.put(new MatchRelatedRuleElement(ruleCorr, pgNode.getMatch()), mappedElements);
		}

		return contextMapping;
	}

	private Map<TGGRule, Set<ITGGMatch>> collectRules(PrecedenceNode pgNode, List<PrecedenceNode> pgNodes) {
		// collect all rules that are candidates for element mappings
		Map<TGGRule, Set<ITGGMatch>> rule2matches = new HashMap<>();
		for (PrecedenceNode requiredPGNode : pgNode.getRequires()) {
			if (requiredPGNode.getMatch().getType() != pgNode.getMatch().getType())
				continue;

			if (!pgNodes.contains(requiredPGNode))
				continue;

			TGGRule requiredNodesRule = mup.get(requiredPGNode.getMatch()).getRule();
			rule2matches.computeIfAbsent(requiredNodesRule, k -> new HashSet<>()).add(requiredPGNode.getMatch());
		}
		return rule2matches;
	}

	private boolean matchesContext(TGGNode context, TGGNode matchCandidate) {
		if (!context.getType().isSuperTypeOf(matchCandidate.getType()))
			return false;

		for (TGGInplaceAttributeExpression contextAttrExpr : context.getAttrExpr()) {
			for (TGGInplaceAttributeExpression candidateAttrExpr : matchCandidate.getAttrExpr()) {
				if (!contextAttrExpr.getAttribute().equals(candidateAttrExpr.getAttribute()))
					continue;

				Object candidateAttrValue;
				TGGExpression candidateAttrValueExpr = candidateAttrExpr.getValueExpr();
				if (candidateAttrValueExpr instanceof TGGLiteralExpression literalExpr)
					candidateAttrValue = literalExpr.getValue();
				else if (candidateAttrValueExpr instanceof TGGEnumExpression enumExpr)
					candidateAttrValue = enumExpr.getLiteral().getInstance();
				else
					continue;

				if (!TGGInplaceAttrExprUtil.checkInplaceAttributeCondition(contextAttrExpr, candidateAttrValue, null))
					return false;
			}
		}

		return true;
	}

	private boolean matchesContext(TGGCorrespondence context, TGGCorrespondence matchCandidate) {
		if (!context.getType().isSuperTypeOf(matchCandidate.getType()))
			return false;

		return true;
	}

	private boolean matchesContext(TGGEdge context, TGGEdge matchCandidate) {
		// TODO check for more restrictions?
		return context.getType().equals(matchCandidate.getType());
	}

	private void validateInputNodes(List<PrecedenceNode> nodes) {
		if (nodes.isEmpty())
			throw new RuntimeException("There must be at least one simple rule to create a higher-order TGG rule!");
	}

	static record MatchRelatedRuleElement(TGGRuleElement ruleElement, ITGGMatch match) {
	}

	public static class MatchRelatedRuleElementMap extends HashMap<MatchRelatedRuleElement, Set<MatchRelatedRuleElement>> {
		private static final long serialVersionUID = 6577887190624934742L;
	}

}
