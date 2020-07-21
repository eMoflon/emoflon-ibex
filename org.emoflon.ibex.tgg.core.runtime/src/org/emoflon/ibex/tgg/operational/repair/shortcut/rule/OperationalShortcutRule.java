package org.emoflon.ibex.tgg.operational.repair.shortcut.rule;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.csp.IRuntimeTGGAttrConstrContainer;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.repair.shortcut.search.SearchKey;
import org.emoflon.ibex.tgg.operational.repair.shortcut.search.SearchPlan;
import org.emoflon.ibex.tgg.operational.repair.shortcut.search.lambda.AttrCheck;
import org.emoflon.ibex.tgg.operational.repair.shortcut.search.lambda.CSPCheck;
import org.emoflon.ibex.tgg.operational.repair.shortcut.search.lambda.EdgeCheck;
import org.emoflon.ibex.tgg.operational.repair.shortcut.search.lambda.Lookup;
import org.emoflon.ibex.tgg.operational.repair.shortcut.search.lambda.NACNodeCheck;
import org.emoflon.ibex.tgg.operational.repair.shortcut.search.lambda.NodeCheck;
import org.emoflon.ibex.tgg.operational.repair.shortcut.util.SCMatch;
import org.emoflon.ibex.tgg.operational.repair.util.EMFNavigationUtil;
import org.emoflon.ibex.tgg.operational.strategies.PropagatingOperationalStrategy;
import org.emoflon.ibex.tgg.operational.strategies.modules.TGGResourceHandler;
import org.emoflon.ibex.tgg.util.String2EPrimitive;
import org.moflon.core.utilities.eMoflonEMFUtil;

import language.BindingType;
import language.TGGAttributeExpression;
import language.TGGEnumExpression;
import language.TGGInplaceAttributeExpression;
import language.TGGLiteralExpression;
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;
import runtime.RuntimePackage;

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
	private TGGResourceHandler resourceHandler;
	protected PatternType type;
	protected ShortcutRule scRule;

	protected Collection<TGGRuleElement> markedElements;
	protected Map<SearchKey, Lookup> key2lookup;
	protected Map<TGGRuleNode, NodeCheck> elt2nodeCheck;
	protected Map<TGGRuleNode, AttrCheck> elt2inplAttrCheck;
	protected Map<SearchKey, EdgeCheck> key2edgeCheck;
	protected Map<SearchKey, NACNodeCheck> key2nacNodeCheck;
	protected CSPCheck cspCheck;

	private IGreenPattern greenPattern;


	public OperationalShortcutRule(PropagatingOperationalStrategy strategy, PatternType type,
			ShortcutRule scRule) {
		this.strategy = strategy;
		this.resourceHandler = strategy.getResourceHandler();
		this.scRule = scRule;
		this.type = type;

		this.markedElements = new HashSet<>();
		this.key2lookup = cfactory.createObjectToObjectHashMap();
		this.elt2nodeCheck = cfactory.createObjectToObjectHashMap();
		this.elt2inplAttrCheck = cfactory.createObjectToObjectHashMap();
		this.key2edgeCheck = cfactory.createObjectToObjectHashMap();
		this.key2nacNodeCheck = cfactory.createObjectToObjectHashMap();
	}

	abstract protected void operationalize();

	public SearchPlan createSearchPlan() {
		Collection<TGGRuleNode> uncheckedNodes = new ArrayList<>();
		Collection<TGGRuleNode> uncheckedRelaxedNodes = new ArrayList<>();
		scRule.getNodes().stream() //
				.filter(n -> !scRule.getMergedNodes().contains(n)) //
				.filter(n -> !scRule.getNewOriginalNodes().contains(n)) //
				.filter(n -> !RuntimePackage.eINSTANCE.getTGGRuleApplication().isSuperTypeOf(n.getType())) //
				.filter(n -> n.getBindingType() != BindingType.NEGATIVE) //
				.filter(n -> n.getBindingType() != BindingType.CREATE) //
				.filter(n -> n.getBindingType() != BindingType.RELAXED) //
				.forEach(n -> uncheckedNodes.add(n));

		LinkedList<TGGRuleEdge> uncheckedEdges = new LinkedList<>();
		for (TGGRuleEdge edge : scRule.getEdges()) {
			if(edge.getBindingType() == BindingType.NEGATIVE)
				uncheckedEdges.addLast(edge);
			else
				uncheckedEdges.addFirst(edge);
		}

		List<SearchKey> uncheckedSearchKeys = key2lookup.keySet().stream() //
				.filter(key -> key.edge.getBindingType() != BindingType.NEGATIVE) //
				.filter(key -> key.edge.getBindingType() != BindingType.CREATE) //
				.collect(Collectors.toList());

		List<Pair<SearchKey, Lookup>> searchPlan = new ArrayList<>();
		
		// first calculate the lookups to find all elements + their corresponding node checks
		while (!uncheckedNodes.isEmpty()) {
			List<SearchKey> nextSearchKeys = //
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

		return new SearchPlan(searchPlan, elt2nodeCheck, elt2inplAttrCheck, key2uncheckedEdgeCheck, key2nacNodeCheck, cspCheck);
	}

	private List<SearchKey> filterKeys(List<SearchKey> keys, Collection<TGGRuleNode> uncheckedNodes,
			Collection<TGGRuleNode> uncheckedRelaxedNodes, boolean relaxed) {
		Collection<SearchKey> filteredKeys = keys.stream() //
				.filter(k -> validLookupKey(uncheckedNodes, uncheckedRelaxedNodes, k, false)).collect(Collectors.toList());

		// if no navigable edge was found -> allow to navigate backwards
		if(filteredKeys.isEmpty()) {
			filteredKeys = keys.stream() //
			.filter(k -> validLookupKey(uncheckedNodes, uncheckedRelaxedNodes, k, true)).collect(Collectors.toList());
		}
		
		filteredKeys = optimizeNextEdges(filteredKeys);
		
		Stream<SearchKey> filteredKeysStream = filteredKeys.stream();
		if (!relaxed) {
			filteredKeysStream = filteredKeysStream //
					.filter(k -> (k.reverse ? k.sourceNode : k.targetNode).getBindingType() != BindingType.RELAXED) //
					.filter(k -> k.edge.getBindingType() != BindingType.RELAXED);
		} else {
			// sort relaxed to the end of this list
			filteredKeysStream = filteredKeysStream.sorted((a, b) -> {
				if (a.sourceNode.getBindingType() == b.sourceNode.getBindingType())
					return 0;
				if ((a.reverse ? a.targetNode : a.sourceNode).getBindingType() != BindingType.RELAXED)
					return -1;
				return 1;
			});
		}
		return filteredKeysStream.collect(Collectors.toList());
	}

	private Collection<SearchKey> optimizeNextEdges(Collection<SearchKey> keys) {
		Collection<SearchKey> corrs = keys.stream().filter(k -> {
			return k.sourceNode instanceof TGGRuleCorr;
		}).collect(Collectors.toList());
		
		Collection<SearchKey> non_containment = keys.stream().filter(k -> {
			EReference type = k.edge.getType();
			return !type.isContainment() && type.getEOpposite() != null ? !type.getEOpposite().isContainment() : true;
		}).collect(Collectors.toList());
		
		Collection<SearchKey> filteredKeys = new LinkedList<>();
		filteredKeys.addAll(corrs);
		filteredKeys.addAll(non_containment);
		
		return filteredKeys.isEmpty() ? keys : filteredKeys;
	}

	// a valid lookup key is a key where source xor target has already been checked
	private boolean validLookupKey(Collection<TGGRuleNode> uncheckedNodes, Collection<TGGRuleNode> uncheckedRelaxedNodes,
			SearchKey key, boolean allowBackNavigate) {
		boolean srcChecked = !uncheckedNodes.contains(key.sourceNode) && !uncheckedRelaxedNodes.contains(key.sourceNode);
		boolean trgChecked = !uncheckedNodes.contains(key.targetNode) && !uncheckedRelaxedNodes.contains(key.targetNode);
		
		boolean notReverse = !key.reverse &&  srcChecked && !trgChecked;
		boolean reverse    =  key.reverse && !srcChecked &&  trgChecked;
		boolean backward = allowBackNavigate || key.edge.getType().getEOpposite() != null || key.sourceNode instanceof TGGRuleCorr;
		
		return notReverse || reverse && backward;
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
			if (edgeRef.getEOpposite() != null) {
				key2lookup.put(key, n -> {
					return n.eGet(edgeRef.getEOpposite());
				});
			} else {
				if (key.sourceNode instanceof TGGRuleCorr) {
					key2lookup.put(key, n -> {
						// make sure that we only get the correct corrs with the right type
						Collection<EObject> corrs = resourceHandler.getCorrCaching().getOrDefault(n, Collections.emptyList());
						return corrs.stream().filter(c -> n.equals(c.eGet(key.edge.getType()))).collect(Collectors.toList());
					});
				} else
					key2lookup.put(key, n -> {
						Class<?> instanceClass = key.sourceNode.getType().getInstanceClass();
						if (instanceClass != null)
							return eMoflonEMFUtil.getOppositeReference(n, instanceClass, key.edge.getType().getName());
						return EMFNavigationUtil.getOppositeReference(n, key.sourceNode.getType(), key.edge.getType().getName());
					});
			}
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
		NodeCheck nodeCheck = key.reverse ? elt2nodeCheck.get(key.sourceNode) : elt2nodeCheck.get(key.targetNode);
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
		elt2nodeCheck.put(key, n -> {
			if (n == null)
				return key.getBindingType() == BindingType.RELAXED;
			return key.getType().isSuperTypeOf(n.eClass());
		});

		if (!key.getAttrExpr().isEmpty())
			elt2inplAttrCheck.put(key, (n, c) -> checkInplaceAttributes(key, n, c));
	}

	private boolean checkInplaceAttributes(TGGRuleNode key, EObject node, Map<String, EObject> candidates) {
		for (TGGInplaceAttributeExpression inplAttrExpr : key.getAttrExpr()) {
			Object subjectAttr = node.eGet(inplAttrExpr.getAttribute());

			if (inplAttrExpr.getValueExpr() instanceof TGGLiteralExpression) {
				if (subjectAttr == null)
					return false;

				TGGLiteralExpression litExpr = (TGGLiteralExpression) inplAttrExpr.getValueExpr();
				Object literal = String2EPrimitive.convertLiteral( //
						litExpr.getValue(), inplAttrExpr.getAttribute().getEAttributeType());

				switch (inplAttrExpr.getOperator()) {
				case EQUAL:
					if (!subjectAttr.equals(literal))
						return false;
					continue;
				case UNEQUAL:
					if (subjectAttr.equals(literal))
						return false;
					continue;
				default:
					break;
				}

				int compareResult = comparePrimitives(subjectAttr, literal);
				switch (inplAttrExpr.getOperator()) {
				case GREATER:
					if (!(compareResult > 0))
						return false;
					break;
				case GR_EQUAL:
					if (!(compareResult >= 0))
						return false;
					break;
				case LESSER:
					if (!(compareResult < 0))
						return false;
					break;
				case LE_EQUAL:
					if (!(compareResult <= 0))
						return false;
					break;
				default:
					break;
				}
			} else if (inplAttrExpr.getValueExpr() instanceof TGGEnumExpression) {
				if (subjectAttr == null)
					return false;

				TGGEnumExpression enumExpr = (TGGEnumExpression) inplAttrExpr.getValueExpr();
				if (!subjectAttr.equals(enumExpr.getLiteral().getInstance()))
					return false;
			} else if (inplAttrExpr.getValueExpr() instanceof TGGAttributeExpression) {
				TGGAttributeExpression attrExpr = (TGGAttributeExpression) inplAttrExpr.getValueExpr();
				EObject obj = candidates.get(attrExpr.getObjectVar().getName());
				if (obj == null)
					return false;
				Object objectAttr = obj.eGet(attrExpr.getAttribute());

				if (subjectAttr == null) {
					if (objectAttr != null)
						return false;
				} else if (!subjectAttr.equals(objectAttr))
					return false;
			}
		}
		return true;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private int comparePrimitives(Object p1, Object p2) {
		if (p1 instanceof Comparable && p2 instanceof Comparable) {
			return ((Comparable) p1).compareTo((Comparable) p2);
		}
		return 0;
	}

	public ShortcutRule getScRule() {
		return scRule;
	}

	public NodeCheck getElement2nodeCheck(TGGRuleNode element) {
		return elt2nodeCheck.get(element);
	}

	public Map<SearchKey, EdgeCheck> getKey2edgeCheck() {
		return key2edgeCheck;
	}

	public Map<SearchKey, Lookup> getKey2singLookup() {
		return key2lookup;
	}

	public PatternType getType() {
		return type;
	}

	public String getName() {
		return scRule.getOriginalRule().getName() + "_OSC_" + scRule.getReplacingRule().getName();
	}

	public IGreenPattern getGreenPattern() {
		if (greenPattern == null) {
			greenPattern = createGreenPattern();
		}
		return greenPattern;
	}

	private IGreenPattern createGreenPattern() {
		return new GreenSCPattern(strategy.getGreenFactory(scRule.getReplacingRule().getName()), this);
	}
}
