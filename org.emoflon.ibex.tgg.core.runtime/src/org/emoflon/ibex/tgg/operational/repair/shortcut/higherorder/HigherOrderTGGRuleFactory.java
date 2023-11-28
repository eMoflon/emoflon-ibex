package org.emoflon.ibex.tgg.operational.repair.shortcut.higherorder;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
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
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleElement;
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

	public HigherOrderTGGRule createHigherOrderTGGRuleFromSrcTrgNodes( //
			List<PrecedenceNode> srcTrgNodes, //
			List<PrecedenceNode> consNodes, //
			DomainType propagationDomain //
	) {
		// when creating higher-order rules from source or target matches, there only exist fixed mappings
		// for the elements of the respective domain ("propagation domain"); for the other domain ("opposite
		// domain") a mapping is found by collecting all possible mappings & then using ILP to determine a
		// valid mapping solution

		validateInputNodes(srcTrgNodes);

		ElementsContainer elementsOfConsNodes = collectElementsFromMatches(consNodes, propagationDomain);

		Map<PrecedenceNode, Set<MatchRelatedRuleElement>> node2ruleElements = new HashMap<>();

		MatchRelatedRuleElementMap totalPropDomainMappings = new MatchRelatedRuleElementMap();
		MatchRelatedRuleElementMap totalOppositeDomainMappings = new MatchRelatedRuleElementMap();
		MatchRelatedRuleElementMap totalCorrDomainMappings = new MatchRelatedRuleElementMap();
		MatchRelatedRuleElementMap totalConsMappings = new MatchRelatedRuleElementMap();
		for (PrecedenceNode srcTrgNode : srcTrgNodes) {
			TGGMatchUtil matchUtil = mup.get(srcTrgNode.getMatch());

			totalConsMappings.putAll(createConsMappingForOppositeDomain(srcTrgNode, matchUtil, propagationDomain, elementsOfConsNodes));

			// since the src/trg node nearest to the root cannot have any context->create mappings to other
			// src/trg matches, we skip it
			if (srcTrgNodes.indexOf(srcTrgNode) == 0)
				continue;

			Set<MatchRelatedRuleElement> matchRelatedRuleElements = new HashSet<>();

			MatchRelatedRuleElementMap propDomainMapping = createMappingForPropagationDomain(srcTrgNode, matchUtil, propagationDomain, srcTrgNodes);
			matchRelatedRuleElements.addAll(propDomainMapping.keySet());
			totalPropDomainMappings.putAll(propDomainMapping);

			Map<TGGRule, Set<ITGGMatch>> rule2matches = collectRules(srcTrgNode, srcTrgNodes);
			MatchRelatedRuleElementMap oppositeDomainMapping = createMappingForOppositeDomain(srcTrgNode, matchUtil, propagationDomain, srcTrgNodes, rule2matches);
			matchRelatedRuleElements.addAll(oppositeDomainMapping.keySet());
			totalOppositeDomainMappings.putAll(oppositeDomainMapping);

			MatchRelatedRuleElementMap corrDomainMapping = createMappingForCorrDomain(srcTrgNode, matchUtil, srcTrgNodes, rule2matches);
			matchRelatedRuleElements.addAll(corrDomainMapping.keySet());
			totalCorrDomainMappings.putAll(corrDomainMapping);

			node2ruleElements.put(srcTrgNode, matchRelatedRuleElements);
		}

		ILPHigherOrderRuleMappingSolver ilpSolver = new ILPHigherOrderRuleMappingSolver( //
				mup, //
				options.ilpSolver(), //
				srcTrgNodes.stream().map(n -> n.getMatch()).toList(), //
				totalPropDomainMappings, //
				totalOppositeDomainMappings, //
				totalCorrDomainMappings, //
				totalConsMappings, //
				elementsOfConsNodes.eObject2elements //
		);
		Map<MatchRelatedRuleElement, MatchRelatedRuleElement> overallContextMapping = ilpSolver.getResult();

		// transform ILP solution to higher-order rule:
		HigherOrderTGGRule higherOrderRule = new HigherOrderTGGRule();
		for (PrecedenceNode pgNode : srcTrgNodes) {
			TGGRule rule = mup.get(pgNode.getMatch()).getRule();

			if (srcTrgNodes.indexOf(pgNode) == 0) {
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
							throw new RuntimeException("There are missing components which are tried to be referenced. Maybe component adding order is incorrect.");
						return component.getComponentSpecificRuleElement(matchRelRuleElt.ruleElement);
					}));

			// since the ILP solution doesn't include correspondence edges, we have to manually find the
			// correspondence edge mappings:
			Collection<TGGRuleNode> corrNodes = TGGFilterUtil.filterNodes(rule.getNodes(), DomainType.CORR, BindingType.CONTEXT);
			for (TGGRuleNode corrNode : corrNodes) {
				TGGRuleCorr corr = (TGGRuleCorr) corrNode;
				ComponentSpecificRuleElement mappedCompSpecCorr = contextMapping.get(corr);
				if (mappedCompSpecCorr == null)
					continue;

				TGGRuleCorr mappedCorr = (TGGRuleCorr) mappedCompSpecCorr.ruleElement;
				for (TGGRuleEdge corrEdge : corr.getOutgoingEdges()) {
					DomainType corrEdgePointDomain = corrEdge.getTrgNode().getDomainType();
					for (TGGRuleEdge mappedCorrEdge : mappedCorr.getOutgoingEdges()) {
						if (mappedCorrEdge.getTrgNode().getDomainType() == corrEdgePointDomain) {
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

	private MatchRelatedRuleElementMap createMappingForPropagationDomain( //
			PrecedenceNode pgNode, //
			TGGMatchUtil matchUtil, //
			DomainType propagationDomain, //
			List<PrecedenceNode> pgNodes //
	) {
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

	private void handleContextToContextMapping( //
			PrecedenceNode pgNode, //
			Object obj, //
			PrecedenceNode mappedPGNode, //
			Set<MatchRelatedRuleElement> mappedElements //
	) {
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

	private MatchRelatedRuleElementMap createMappingForOppositeDomain( //
			PrecedenceNode pgNode, //
			TGGMatchUtil matchUtil, //
			DomainType propagationDomain, //
			List<PrecedenceNode> pgNodes, //
			Map<TGGRule, Set<ITGGMatch>> rule2matches //
	) {
		DomainType oppositeDomain = TGGModelUtils.oppositeOf(propagationDomain);
		TGGRule rule = matchUtil.getRule();

		MatchRelatedRuleElementMap contextMapping = new MatchRelatedRuleElementMap();

		Collection<TGGRuleNode> ruleNodes = TGGFilterUtil.filterNodes(rule.getNodes(), oppositeDomain, BindingType.CONTEXT);
		for (TGGRuleNode ruleNode : ruleNodes) {
			// TODO cache node type to {rule & node}
			Set<MatchRelatedRuleElement> mappedElements = new HashSet<>();
			for (TGGRule mappedRule : rule2matches.keySet()) {
				Collection<TGGRuleNode> mappedNodes = TGGFilterUtil.filterNodes(mappedRule.getNodes(), oppositeDomain);
				for (TGGRuleNode mappedNode : mappedNodes) {
					if (matchesContext(ruleNode, mappedNode)) {
						for (ITGGMatch mappedMatch : rule2matches.get(mappedRule)) {
							// Prevents from creating invalid candidates due to PG dependency relations
							if (pgNode.transitivelyRequiredBy(pg.getNode(mappedMatch), true))
								continue;
							mappedElements.add(new MatchRelatedRuleElement(mappedNode, mappedMatch));
						}
					}
				}
			}

			if (!mappedElements.isEmpty())
				contextMapping.put(new MatchRelatedRuleElement(ruleNode, pgNode.getMatch()), mappedElements);
		}

		Collection<TGGRuleEdge> ruleEdges = TGGFilterUtil.filterEdges(rule.getEdges(), oppositeDomain, BindingType.CONTEXT);
		for (TGGRuleEdge ruleEdge : ruleEdges) {
			Set<MatchRelatedRuleElement> mappedElements = new HashSet<>();
			for (TGGRule mappedRule : rule2matches.keySet()) {
				Collection<TGGRuleEdge> mappedEdges = TGGFilterUtil.filterEdges(mappedRule.getEdges(), oppositeDomain);
				for (TGGRuleEdge mappedEdge : mappedEdges) {
					if (matchesContext(ruleEdge, mappedEdge)) {
						for (ITGGMatch mappedMatch : rule2matches.get(mappedRule)) {
							// Prevents from creating invalid candidates due to PG dependency relations
							if (pgNode.transitivelyRequiredBy(pg.getNode(mappedMatch), true))
								continue;
							mappedElements.add(new MatchRelatedRuleElement(mappedEdge, mappedMatch));
						}
					}
				}
			}

			if (!mappedElements.isEmpty())
				contextMapping.put(new MatchRelatedRuleElement(ruleEdge, pgNode.getMatch()), mappedElements);
		}

		return contextMapping;
	}

	private MatchRelatedRuleElementMap createMappingForCorrDomain( //
			PrecedenceNode pgNode, //
			TGGMatchUtil matchUtil, //
			List<PrecedenceNode> pgNodes, //
			Map<TGGRule, Set<ITGGMatch>> rule2matches //
	) {
		TGGRule rule = matchUtil.getRule();

		MatchRelatedRuleElementMap contextMapping = new MatchRelatedRuleElementMap();

		Collection<TGGRuleNode> ruleCorrs = TGGFilterUtil.filterNodes(rule.getNodes(), DomainType.CORR, BindingType.CONTEXT);
		for (TGGRuleNode ruleCorr : ruleCorrs) {
			Set<MatchRelatedRuleElement> mappedElements = new HashSet<>();
			for (TGGRule mappedRule : rule2matches.keySet()) {
				Collection<TGGRuleNode> mappedCorrs = TGGFilterUtil.filterNodes(mappedRule.getNodes(), DomainType.CORR);
				for (TGGRuleNode mappedCorr : mappedCorrs) {
					if (matchesContext((TGGRuleCorr) ruleCorr, (TGGRuleCorr) mappedCorr)) {
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

	private MatchRelatedRuleElementMap createConsMappingForOppositeDomain( //
			PrecedenceNode pgNode, //
			TGGMatchUtil matchUtil, //
			DomainType propagationDomain, //
			ElementsContainer elementsOfConsNodes //
	) {
		DomainType oppositeDomain = TGGModelUtils.oppositeOf(propagationDomain);
		TGGRule rule = matchUtil.getRule();

		MatchRelatedRuleElementMap consMapping = new MatchRelatedRuleElementMap();

		Collection<TGGRuleNode> ruleNodes = TGGFilterUtil.filterNodes(rule.getNodes(), oppositeDomain, BindingType.CREATE);
		for (TGGRuleNode ruleNode : ruleNodes) {
			Set<MatchRelatedRuleElement> mappedNodes = new HashSet<>();
			for (MatchRelatedRuleElement matchRelRuleElt : elementsOfConsNodes.nodes) {
				TGGRuleNode consNode = (TGGRuleNode) matchRelRuleElt.ruleElement;
				if (matchesContext(consNode, ruleNode)) {
					mappedNodes.add(matchRelRuleElt);
				}
			}
			if (!mappedNodes.isEmpty())
				consMapping.put(new MatchRelatedRuleElement(ruleNode, pgNode.getMatch()), mappedNodes);
		}

		Collection<TGGRuleEdge> ruleEdges = TGGFilterUtil.filterEdges(rule.getEdges(), oppositeDomain, BindingType.CREATE);
		for (TGGRuleEdge ruleEdge : ruleEdges) {
			Set<MatchRelatedRuleElement> mappedEdges = new HashSet<>();
			for (MatchRelatedRuleElement matchRelRuleElt : elementsOfConsNodes.edges) {
				TGGRuleEdge consEdge = (TGGRuleEdge) matchRelRuleElt.ruleElement;
				if (matchesContext(consEdge, ruleEdge)) {
					mappedEdges.add(matchRelRuleElt);
				}
			}
			if (!mappedEdges.isEmpty())
				consMapping.put(new MatchRelatedRuleElement(ruleEdge, pgNode.getMatch()), mappedEdges);
		}

		Collection<TGGRuleNode> ruleCorrNodes = TGGFilterUtil.filterNodes(rule.getNodes(), DomainType.CORR, BindingType.CREATE);
		for (TGGRuleNode ruleCorrNode : ruleCorrNodes) {
			Set<MatchRelatedRuleElement> mappedCorrNodes = new HashSet<>();
			for (MatchRelatedRuleElement matchRelRuleElt : elementsOfConsNodes.corrNodes) {
				TGGRuleCorr consCorrNode = (TGGRuleCorr) matchRelRuleElt.ruleElement;
				if (matchesContext(consCorrNode, (TGGRuleCorr) ruleCorrNode)) {
					mappedCorrNodes.add(matchRelRuleElt);
				}
			}
			if (!mappedCorrNodes.isEmpty())
				consMapping.put(new MatchRelatedRuleElement(ruleCorrNode, pgNode.getMatch()), mappedCorrNodes);
		}

		Collection<TGGRuleEdge> ruleCorrEdges = TGGFilterUtil.filterEdges(rule.getEdges(), DomainType.CORR, BindingType.CREATE);
		for (TGGRuleEdge ruleCorrEdge : ruleCorrEdges) {
			Set<MatchRelatedRuleElement> mappedCorrEdges = new HashSet<>();
			for (MatchRelatedRuleElement matchRelRuleElt : elementsOfConsNodes.corrEdges) {
				TGGRuleEdge consCorrEdge = (TGGRuleEdge) matchRelRuleElt.ruleElement;
				if (matchesContext(consCorrEdge, ruleCorrEdge)) {
					mappedCorrEdges.add(matchRelRuleElt);
				}
			}
			if (!mappedCorrEdges.isEmpty())
				consMapping.put(new MatchRelatedRuleElement(ruleCorrEdge, pgNode.getMatch()), mappedCorrEdges);
		}

		return consMapping;
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

	private boolean matchesContext(TGGRuleNode context, TGGRuleNode matchCandidate) {
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

	private boolean matchesContext(TGGRuleCorr context, TGGRuleCorr matchCandidate) {
		if (!context.getType().isSuperTypeOf(matchCandidate.getType()))
			return false;

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

	private ElementsContainer collectElementsFromMatches(List<PrecedenceNode> consNodes, DomainType propagationDomain) {
		Set<MatchRelatedRuleElement> nodes = new HashSet<>();
		Set<MatchRelatedRuleElement> edges = new HashSet<>();
		Set<MatchRelatedRuleElement> corrNodes = new HashSet<>();
		Set<MatchRelatedRuleElement> corrEdges = new HashSet<>();
		Map<EObject, MatchRelatedRuleElement> eObject2elements = new HashMap<>();

		DomainType oppositeDomain = TGGModelUtils.oppositeOf(propagationDomain);
		EltFilter oppositeEltFilter = new EltFilter().create().domains(oppositeDomain);
		EltFilter corrEltFilter = new EltFilter().create().corr();
		EltFilter bothEltFilter = new EltFilter().create().domains(oppositeDomain, DomainType.CORR);

		for (PrecedenceNode pgNode : consNodes) {
			ITGGMatch match = pgNode.getMatch();
			TGGMatchUtil matchUtil = mup.get(match);

			var oppNodes = matchUtil.getNodeStream(bothEltFilter).map(n -> new MatchRelatedRuleElement(n, match)).toList();
			for (MatchRelatedRuleElement oppNode : oppNodes)
				eObject2elements.put(matchUtil.getEObject((TGGRuleNode) oppNode.ruleElement), oppNode);

			nodes.addAll(matchUtil.getNodeStream(oppositeEltFilter).map(n -> new MatchRelatedRuleElement(n, match)).toList());
			edges.addAll(matchUtil.getEdgeStream(oppositeEltFilter).map(e -> new MatchRelatedRuleElement(e, match)).toList());
			corrNodes.addAll(matchUtil.getNodeStream(corrEltFilter).map(n -> new MatchRelatedRuleElement(n, match)).toList());
			corrEdges.addAll(matchUtil.getEdgeStream(corrEltFilter).map(n -> new MatchRelatedRuleElement(n, match)).toList());
		}
		return new ElementsContainer(nodes, edges, corrNodes, corrEdges, eObject2elements);
	}

	private record ElementsContainer( //
			Set<MatchRelatedRuleElement> nodes, //
			Set<MatchRelatedRuleElement> edges, //
			Set<MatchRelatedRuleElement> corrNodes, //
			Set<MatchRelatedRuleElement> corrEdges, //
			Map<EObject, MatchRelatedRuleElement> eObject2elements //
	) {
	}

	static record MatchRelatedRuleElement(TGGRuleElement ruleElement, ITGGMatch match) {
	}

	public static class MatchRelatedRuleElementMap extends HashMap<MatchRelatedRuleElement, Set<MatchRelatedRuleElement>> {
		private static final long serialVersionUID = 6577887190624934742L;
	}

}
