package org.emoflon.ibex.tgg.operational.repair.shortcut.rule;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.tgg.operational.csp.IRuntimeTGGAttrConstrContainer;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.repair.shortcut.search.SearchKey;
import org.emoflon.ibex.tgg.operational.repair.shortcut.search.SearchPlan;
import org.emoflon.ibex.tgg.operational.repair.shortcut.search.lambda.CSPCheck;
import org.emoflon.ibex.tgg.operational.repair.shortcut.search.lambda.EdgeCheck;
import org.emoflon.ibex.tgg.operational.repair.shortcut.search.lambda.Lookup;
import org.emoflon.ibex.tgg.operational.repair.shortcut.search.lambda.NACNodeCheck;
import org.emoflon.ibex.tgg.operational.repair.shortcut.search.lambda.NodeCheck;
import org.emoflon.ibex.tgg.operational.repair.shortcut.util.SCMatch;
import org.emoflon.ibex.tgg.operational.repair.util.EMFNavigationUtil;
import org.emoflon.ibex.tgg.operational.strategies.PropagatingOperationalStrategy;
import org.emoflon.ibex.tgg.operational.strategies.PropagationDirection;
import org.emoflon.ibex.tgg.util.String2EPrimitive;
import org.moflon.core.utilities.eMoflonEMFUtil;

import language.BindingType;
import language.TGGAttributeExpression;
import language.TGGEnumExpression;
import language.TGGInplaceAttributeExpression;
import language.TGGLiteralExpression;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

/**
 * 
 * This class represents an operationalized shortcut rule. The pattern
 * information are stored in the shortcut rule (copied instance).
 * Operationalization means that these rules are applicable in a certain
 * translation direction (FORWARD, BACKWARD). However, the operationalize()
 * method has to be implemented by sub classes. This class also creates a
 * SearchPlan for the operationalized pattern and created Lookup and Check
 * Operations which are used by LocalPatternSearch to execute the SearchPlan.
 * The interfaces used to implement these operations are EdgeCheck, Lookup,
 * NACNodeCheck and NodeCheck.
 * 
 * @author lfritsche
 *
 */
public abstract class OperationalShortcutRule {
	protected final static Logger logger = Logger.getLogger(OperationalShortcutRule.class);

	protected PropagatingOperationalStrategy strategy;
	protected PropagationDirection direction;
	protected ShortcutRule scRule;

	protected Collection<TGGRuleElement> markedElements;
	protected Map<SearchKey, Lookup> key2lookup;
	protected Map<TGGRuleNode, NodeCheck> element2nodeCheck;
	protected Map<SearchKey, EdgeCheck> key2edgeCheck;
	protected Map<SearchKey, NACNodeCheck> key2nacNodeCheck;
	protected CSPCheck cspCheck;

	private IGreenPattern greenPattern;

	public OperationalShortcutRule(PropagatingOperationalStrategy strategy, PropagationDirection direction, ShortcutRule scRule) {
		this.scRule = scRule;
		this.direction = direction;

		this.markedElements = new HashSet<>();
		this.key2lookup = cfactory.createObjectToObjectHashMap();
		this.element2nodeCheck = cfactory.createObjectToObjectHashMap();
		this.key2edgeCheck = cfactory.createObjectToObjectHashMap();
		this.key2nacNodeCheck = cfactory.createObjectToObjectHashMap();
	}

	abstract protected void operationalize();

	public SearchPlan createSearchPlan() {
		Collection<TGGRuleNode> uncheckedNodes = new ArrayList<>();
		Collection<TGGRuleNode> uncheckedRelaxedNodes = new ArrayList<>();
		scRule.getNodes().stream() //
				.filter(n -> !scRule.getMergedNodes().contains(n)) //
				.filter(n -> n.getBindingType() != BindingType.NEGATIVE) //
				.filter(n -> n.getBindingType() != BindingType.CREATE) //
				.forEach(n -> //
				(n.getBindingType() == BindingType.RELAXED ? uncheckedRelaxedNodes : uncheckedNodes).add(n));

		Collection<TGGRuleEdge> uncheckedEdges = scRule.getEdges().stream() //
				.collect(Collectors.toList());

		Collection<SearchKey> uncheckedSearchKeys = key2lookup.keySet().stream() //
				.filter(key -> key.edge.getBindingType() != BindingType.NEGATIVE) //
				.filter(key -> key.edge.getBindingType() != BindingType.CREATE) //
				.collect(Collectors.toList());

		List<Pair<SearchKey, Lookup>> searchPlan = new ArrayList<>();
		
		// first calculate the lookups to find all elements + their corresponding node checks		
		while (!uncheckedNodes.isEmpty()) {
			Collection<SearchKey> nextSearchKeys = //
					filterKeys(uncheckedSearchKeys, uncheckedNodes, uncheckedRelaxedNodes, false);
			if (nextSearchKeys.isEmpty()) {
				// TODO lfritsche: clear this up
//				throw new RuntimeException("Searchplan could not be generated for OperationalShortcutRule - " + scRule);
				logger.error("Searchplan could not be generated for OperationalShortcutRule - " + scRule.getName());
				return null;
			}

			SearchKey nextKey = nextSearchKeys.iterator().next();
			searchPlan.add(Pair.of(nextKey, key2lookup.get(nextKey)));

			uncheckedNodes.remove(nextKey.reverse ? nextKey.sourceNode : nextKey.targetNode);
			uncheckedEdges.remove(nextKey.edge);
			uncheckedSearchKeys.remove(nextKey);
		}
		
		// then calculate lookups for relaxed nodes
		while(!uncheckedRelaxedNodes.isEmpty()) {
			Collection<SearchKey> nextSearchKeys = //
					filterKeys(uncheckedSearchKeys, uncheckedNodes, uncheckedRelaxedNodes, true);
			if(nextSearchKeys.isEmpty()) {
				logger.error("Searchplan could not be generated for OperationalShortcutRule - " + scRule.getName());
				return null;
			}
			
			SearchKey nextKey = nextSearchKeys.iterator().next();
			searchPlan.add(Pair.of(nextKey, key2lookup.get(nextKey)));
			
			uncheckedRelaxedNodes.remove(nextKey.reverse ? nextKey.sourceNode : nextKey.targetNode);
			uncheckedEdges.remove(nextKey.edge);
			uncheckedSearchKeys.remove(nextKey);
		}

		// now add edge checks to check all unchecked edges
		Map<SearchKey, EdgeCheck> key2uncheckedEdgeCheck = new HashMap<>();
		for (TGGRuleEdge edge : uncheckedEdges) {
			if (edge.getBindingType() == BindingType.CREATE || edge.getBindingType() == BindingType.RELAXED)
				continue;
			
			if (edge.getBindingType() != BindingType.NEGATIVE)
				if (edge.getSrcNode().getBindingType() == BindingType.RELAXED
						|| edge.getTrgNode().getBindingType() == BindingType.RELAXED)
					continue;
			
			if (edge.getSrcNode().getBindingType() == BindingType.NEGATIVE
					|| edge.getTrgNode().getBindingType() == BindingType.NEGATIVE)
				continue;

			SearchKey key = new SearchKey(edge.getSrcNode(), edge.getTrgNode(), edge, false);
			key2uncheckedEdgeCheck.put(key, key2edgeCheck.get(key));
		}

		// add NAC checks as the last constraints that are evaluated
		Map<SearchKey, NACNodeCheck> key2nacNodeCheck = new HashMap<>();
		for (TGGRuleEdge edge : uncheckedEdges) {
			if (edge.getBindingType() != BindingType.NEGATIVE)
				continue;

			if (edge.getSrcNode().getBindingType() != BindingType.NEGATIVE
					&& edge.getTrgNode().getBindingType() != BindingType.NEGATIVE)
				continue;

			boolean reverse = edge.getSrcNode().getBindingType() == BindingType.NEGATIVE;
			SearchKey key = new SearchKey(edge.getSrcNode(), edge.getTrgNode(), edge, reverse);
			key2nacNodeCheck.put(key, this.key2nacNodeCheck.get(key));
		}

		return new SearchPlan(searchPlan, element2nodeCheck, key2uncheckedEdgeCheck, key2nacNodeCheck, cspCheck);
	}
	
	private Collection<SearchKey> filterKeys(Collection<SearchKey> keys,
			Collection<TGGRuleNode> uncheckedNodes, Collection<TGGRuleNode> uncheckedRelaxedNodes, boolean relaxed) {
		Stream<SearchKey> filteredKeys = keys.stream() //
				.filter(k -> validLookupKey(uncheckedNodes, uncheckedRelaxedNodes, k));
		if (!relaxed) {
			filteredKeys = filteredKeys //
					.filter(k -> (k.reverse ? k.sourceNode : k.targetNode).getBindingType() != BindingType.RELAXED) //
					.filter(k -> k.edge.getBindingType() != BindingType.RELAXED);
		}
		return filteredKeys.collect(Collectors.toList());
	}

	// a valid lookup key is a key where source xor target has already been checked
	private boolean validLookupKey(Collection<TGGRuleNode> uncheckedNodes, Collection<TGGRuleNode> uncheckedRelaxedNodes,
			SearchKey key) {
		boolean srcChecked = !uncheckedNodes.contains(key.sourceNode) && !uncheckedRelaxedNodes.contains(key.sourceNode);
		boolean trgChecked = !uncheckedNodes.contains(key.targetNode) && !uncheckedRelaxedNodes.contains(key.targetNode);
		
		boolean notReverse = !key.reverse &&  srcChecked && !trgChecked;
		boolean reverse    =  key.reverse && !srcChecked &&  trgChecked;
		
		return notReverse || reverse;
	}

	protected void createConstraintChecks() {
		for (TGGRuleNode node : scRule.getNodes()) {
			createNodeCheck(node);
		}

		for (TGGRuleEdge edge : scRule.getEdges()) {
			SearchKey forwardKey = new SearchKey(edge.getSrcNode(), edge.getTrgNode(), edge, false);
			SearchKey backwardKey = new SearchKey(edge.getSrcNode(), edge.getTrgNode(), edge, true);

			createEdgeCheck(forwardKey);

			createLookup(forwardKey);
			createOppositeLookup(backwardKey);

			if (edge.getBindingType() != BindingType.NEGATIVE)
				continue;

			if (edge.getSrcNode().getBindingType() == BindingType.NEGATIVE) {
				createNACNodeCheck(backwardKey);
			}
			if (edge.getTrgNode().getBindingType() == BindingType.NEGATIVE) {
				createNACNodeCheck(forwardKey);
			}
		}

		cspCheck = (name2candidates) -> {
			ITGGMatch match = new SCMatch(scRule.getName(), name2candidates);
			IRuntimeTGGAttrConstrContainer cspContainer = getGreenPattern().getAttributeConstraintContainer(match);
			return cspContainer.solve();
		};
	}

	private void createLookup(SearchKey key) {
		EReference edgeRef = key.edge.getType();
		key2lookup.put(key, n -> {
			return n.eGet(edgeRef);
		});
	}

	private void createOppositeLookup(SearchKey key) {
		EReference edgeRef = key.edge.getType();
		// create opposite ref
		if (edgeRef.isContainment()) {
			key2lookup.put(key, n -> {
				return (EObject) n.eContainer();
			});
		} else {
			// TODO lfritsche : implement reverse navigation
			key2lookup.put(key, n -> {
				Class<?> instanceClass = key.sourceNode.getType().getInstanceClass();
				if (instanceClass != null)
					return eMoflonEMFUtil.getOppositeReference(n, instanceClass, key.edge.getType().getName());
				return EMFNavigationUtil.getOppositeReference(n, key.sourceNode.getType(), key.edge.getType().getName());
			});
		}
	}

	@SuppressWarnings("unchecked")
	private void createEdgeCheck(SearchKey key) {
		boolean negative = key.edge.getBindingType() == BindingType.NEGATIVE;
		if (negative) {
			if (key.edge.getSrcNode().getBindingType().equals(BindingType.NEGATIVE)
					|| key.edge.getTrgNode().getBindingType().equals(BindingType.NEGATIVE))
				return;

			key2edgeCheck.put(key, (s, t) -> {
				Object refTarget = s.eGet(key.edge.getType());
				if (refTarget == null) {
					return true;
				}

				if (refTarget instanceof List<?>) {
					List<EObject> list = (List<EObject>) refTarget;
					return !list.contains(t);
				}
				return !refTarget.equals(t);
			});
		} else {
			key2edgeCheck.put(key, (s, t) -> {
				Object refTarget = s.eGet(key.edge.getType());
				if (refTarget == null) {
					return false;
				}

				if (refTarget instanceof List<?>) {
					List<EObject> list = (List<EObject>) refTarget;
					return list.contains(t);
				}
				return refTarget.equals(t);
			});
		}
	}

	private void createNACNodeCheck(SearchKey key) {
		NodeCheck nodeCheck = key.reverse ? element2nodeCheck.get(key.sourceNode) : element2nodeCheck.get(key.targetNode);
		SearchKey lookupKey = new SearchKey(key.sourceNode, key.targetNode, key.edge, key.reverse);
		key2nacNodeCheck.put(key, (s, candidates) -> {
			Object refTarget = key2lookup.get(lookupKey).lookup(s);
			if (refTarget == null) {
				return true;
			}

			if (refTarget instanceof List<?>) {
				@SuppressWarnings("unchecked")
				List<EObject> list = (List<EObject>) refTarget;
				for (EObject obj : list) {
					if (!candidates.contains(obj) && nodeCheck.checkConstraint(obj))
						return false;
				}
				return true;
			}
			return candidates.contains(refTarget) || !nodeCheck.checkConstraint((EObject) refTarget);
		});
	}

	private void createNodeCheck(TGGRuleNode key) {
		element2nodeCheck.put(key, n -> {
			if (n == null)
				return key.getBindingType() == BindingType.RELAXED;
			return key.getType().isSuperTypeOf(n.eClass()) && checkInplaceAttributes(key, n);
		});
	}

	private boolean checkInplaceAttributes(TGGRuleNode key, EObject node) {
		for (TGGInplaceAttributeExpression inplAttrExpr : key.getAttrExpr()) {
			if (inplAttrExpr.getValueExpr() instanceof TGGLiteralExpression) {
				TGGLiteralExpression litExpr = (TGGLiteralExpression) inplAttrExpr.getValueExpr();
				Object literal = String2EPrimitive.convertLiteral( //
						litExpr.getValue(), inplAttrExpr.getAttribute().getEAttributeType());
				if (!literal.equals(node.eGet(inplAttrExpr.getAttribute())))
					return false;
			} else if (inplAttrExpr.getValueExpr() instanceof TGGEnumExpression) {
				TGGEnumExpression enumExpr = (TGGEnumExpression) inplAttrExpr.getValueExpr();
				if (!enumExpr.getLiteral().getInstance().equals(node.eGet(inplAttrExpr.getAttribute())))
					return false;
			} else if (inplAttrExpr.getValueExpr() instanceof TGGAttributeExpression) {
				TGGAttributeExpression attrExpr = (TGGAttributeExpression) inplAttrExpr.getValueExpr();
				// FIXME adrianm: attribute expression check (missing Match access)
			}
		}
		return true;
	}

	public ShortcutRule getScRule() {
		return scRule;
	}

	public NodeCheck getElement2nodeCheck(TGGRuleNode element) {
		return element2nodeCheck.get(element);
	}

	public Map<SearchKey, EdgeCheck> getKey2edgeCheck() {
		return key2edgeCheck;
	}

	public Map<SearchKey, Lookup> getKey2singLookup() {
		return key2lookup;
	}

	public PropagationDirection getDirection() {
		return direction;
	}

	public String getName() {
		return scRule.getSourceRule().getName() + "_OSC_" + scRule.getTargetRule().getName();
	}

	public IGreenPattern getGreenPattern() {
		if (greenPattern == null) {
			greenPattern = createGreenPattern();
		}
		return greenPattern;
	}

	private IGreenPattern createGreenPattern() {
		return new GreenSCPattern(strategy.getGreenFactory(scRule.getTargetRule().getName()), this);
	}
}
