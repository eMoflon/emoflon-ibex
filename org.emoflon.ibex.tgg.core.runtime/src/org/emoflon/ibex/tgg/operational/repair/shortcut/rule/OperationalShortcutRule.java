package org.emoflon.ibex.tgg.operational.repair.shortcut.rule;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.tgg.operational.csp.IRuntimeTGGAttrConstrContainer;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.repair.shortcut.search.SearchKey;
import org.emoflon.ibex.tgg.operational.repair.shortcut.search.SearchPlan;
import org.emoflon.ibex.tgg.operational.repair.shortcut.search.lambda.CSPCheck;
import org.emoflon.ibex.tgg.operational.repair.shortcut.search.lambda.EdgeCheck;
import org.emoflon.ibex.tgg.operational.repair.shortcut.search.lambda.Lookup;
import org.emoflon.ibex.tgg.operational.repair.shortcut.search.lambda.NACNodeCheck;
import org.emoflon.ibex.tgg.operational.repair.shortcut.search.lambda.NodeCheck;
import org.emoflon.ibex.tgg.operational.repair.shortcut.util.SCMatch;
import org.emoflon.ibex.tgg.operational.repair.shortcut.util.SyncDirection;
import org.emoflon.ibex.tgg.operational.repair.util.EMFNavigationUtil;
import org.emoflon.ibex.tgg.operational.strategies.sync.SYNC;
import org.moflon.core.utilities.eMoflonEMFUtil;

import language.BindingType;
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

	protected SYNC strategy;
	protected SyncDirection direction;
	protected ShortcutRule scRule;
	
	protected Collection<TGGRuleElement> markedElements;
	protected Map<SearchKey, Lookup> key2lookup;
	protected Map<TGGRuleNode, NodeCheck> element2nodeCheck;
	protected Map<SearchKey, EdgeCheck> key2edgeCheck;
	protected Map<SearchKey, NACNodeCheck> key2nacNodeCheck;
	protected CSPCheck cspCheck;

	private IGreenPattern greenPattern;

	public OperationalShortcutRule(SYNC strategy, SyncDirection direction, ShortcutRule scRule) {
		this.scRule = scRule;
		this.direction = direction;

		this.markedElements = new HashSet<>();
		this.key2lookup = cfactory.createObjectToObjectHashMap();
		this.element2nodeCheck = cfactory.createObjectToObjectHashMap();
		this.key2edgeCheck = cfactory.createObjectToObjectHashMap();
		this.key2nacNodeCheck= cfactory.createObjectToObjectHashMap();
	}

	abstract protected void operationalize();

	public SearchPlan createSearchPlan() {
		Collection<TGGRuleNode> uncheckedNodes = scRule.getNodes()
				.stream()
				.filter(n -> !scRule.getMergedNodes().contains(n) 
				&& n.getBindingType() != BindingType.NEGATIVE 
				&& n.getBindingType() != BindingType.CREATE)
				.collect(Collectors.toList());
		Collection<TGGRuleEdge> uncheckedEdges = scRule.getEdges().stream().collect(Collectors.toList());
		
		Collection<SearchKey> uncheckedSearchKeys = key2lookup.keySet();
		
		// first calculate the lookups to find all elements + their corresponding node checks
		// TODO lfritsche, adrianM: add inplace attributes
		List<Pair<SearchKey, Lookup>> searchPlan = new ArrayList<>();
		while(!uncheckedNodes.isEmpty()) {
			Collection<SearchKey> checkedSearchKeys = filterAndSortKeys(uncheckedSearchKeys, uncheckedNodes);
			if(checkedSearchKeys.isEmpty()) {
				// TODO lfritsche: clear this up
//				throw new RuntimeException("Searchplan could not be generated for OperationalShortcutRule - " + scRule);
				logger.error("Searchplan could not be generated for OperationalShortcutRule - " + scRule.getName());
				return null;
			}
			
			SearchKey key = checkedSearchKeys.iterator().next();
			searchPlan.add(Pair.of(key, key2lookup.get(key)));
			
			uncheckedNodes.remove(key.reverse ? key.sourceNode : key.targetNode);
			uncheckedEdges.remove(key.edge);
			
			uncheckedSearchKeys.remove(key);
			checkedSearchKeys.add(key);
		}
		
		// now add edge checks to check all unchecked edges
		Map<SearchKey, EdgeCheck> key2uncheckedEdgeCheck = new HashMap<>();
		for(TGGRuleEdge edge : uncheckedEdges) {
			if(edge.getBindingType() == BindingType.CREATE)
				continue;
			
			if(edge.getSrcNode().getBindingType() == BindingType.NEGATIVE || edge.getTrgNode().getBindingType() == BindingType.NEGATIVE)
				continue;
			
			SearchKey key = new SearchKey(edge.getSrcNode(), edge.getTrgNode(), edge, false);
			key2uncheckedEdgeCheck.put(key, key2edgeCheck.get(key));
		}
		
		// add NAC checks as the last constraints that are evaluated
		Map<SearchKey, NACNodeCheck> key2nacNodeCheck = new HashMap<>();
		for(TGGRuleEdge edge : uncheckedEdges) {
			if(edge.getBindingType() != BindingType.NEGATIVE)
				continue;
			
			if(edge.getSrcNode().getBindingType() != BindingType.NEGATIVE && edge.getTrgNode().getBindingType() != BindingType.NEGATIVE)
				continue;
			
			boolean reverse = edge.getSrcNode().getBindingType() != BindingType.CONTEXT;
			SearchKey key = new SearchKey(edge.getSrcNode(), edge.getTrgNode(), edge, reverse);
			key2nacNodeCheck.put(key, this.key2nacNodeCheck.get(key));
		}
		
		return new SearchPlan(searchPlan, element2nodeCheck, key2uncheckedEdgeCheck, key2nacNodeCheck, cspCheck);
	}
	
	private Collection<SearchKey> filterAndSortKeys(Collection<SearchKey> keys, Collection<TGGRuleNode> uncheckedNodes) {
		return keys.stream()
				.filter(k -> validLookupKey(uncheckedNodes, k))
				.filter(k -> k.edge.getBindingType() != BindingType.NEGATIVE)
				.filter(k -> k.edge.getBindingType() != BindingType.CREATE)
//				.filter(k -> 
//					uncheckedNodes.contains(k.sourceNode) && !k.reverse || 
//					uncheckedNodes.contains(k.targetNode) && k.reverse)
				.collect(Collectors.toList());
	}
	
	// a valid lookup key is a key where source xor target has already been checked
	private boolean validLookupKey(Collection<TGGRuleNode> uncheckedNodes, SearchKey key) {
		boolean first  =  uncheckedNodes.contains(key.sourceNode) && !uncheckedNodes.contains(key.targetNode) &&  key.reverse;
		boolean second = !uncheckedNodes.contains(key.sourceNode) &&  uncheckedNodes.contains(key.targetNode) && !key.reverse;
		return first || second;
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

			if(edge.getBindingType() != BindingType.NEGATIVE)
				continue;
			
			if(edge.getSrcNode().getBindingType() == BindingType.NEGATIVE) {
				createNACNodeCheck(backwardKey);
			}
			if(edge.getTrgNode().getBindingType() == BindingType.NEGATIVE) {
				createNACNodeCheck(forwardKey);
			}
		}
		
		cspCheck = (name2candidates) -> {
			IMatch match = new SCMatch(scRule.getName(), name2candidates);
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
				if(instanceClass != null) 
					return eMoflonEMFUtil.getOppositeReference(n, instanceClass, key.edge.getType().getName());
				return EMFNavigationUtil.getOppositeReference(n, key.sourceNode.getType(), key.edge.getType().getName());
			});
		}
	}

	@SuppressWarnings("unchecked")
	private void createEdgeCheck(SearchKey key) {
		boolean negative = key.edge.getBindingType() == BindingType.NEGATIVE;
		if (negative) {
			if(key.edge.getSrcNode().getBindingType().equals(BindingType.NEGATIVE) || key.edge.getTrgNode().getBindingType().equals(BindingType.NEGATIVE))
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
		}
		else {
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
				for(EObject obj : list) {
					if(!candidates.contains(refTarget) && nodeCheck.checkConstraint(obj))
						return false;
				}
				return true;
			}
			return candidates.contains(refTarget) || !nodeCheck.checkConstraint((EObject) refTarget);
		});
	}

	private void createNodeCheck(TGGRuleNode key) {
		// TODO lfritsche, adrianM: implement attribute handling (also in TGGOverlap)
		element2nodeCheck.put(key, n -> {
			if (n == null)
				return false;
			return key.getType().isSuperTypeOf(n.eClass());
		});
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
	
	public String getName() {
		return scRule.getSourceRule().getName() + "_OSC_" + scRule.getTargetRule().getName();
	}
	
	public IGreenPattern getGreenPattern() {
		if(greenPattern == null) {
			greenPattern = createGreenPattern();
		}
		return greenPattern;
	}

	private IGreenPattern createGreenPattern() {
		return new GreenSCPattern(strategy.getGreenFactory(scRule.getTargetRule().getName()), this);
	}
}
