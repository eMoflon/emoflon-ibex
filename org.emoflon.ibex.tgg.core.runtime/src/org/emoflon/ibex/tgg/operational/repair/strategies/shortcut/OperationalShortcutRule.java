package org.emoflon.ibex.tgg.operational.repair.strategies.shortcut;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.util.SearchKey;
import org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.util.SearchPlan;
import org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.util.SyncDirection;
import org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.util.lambda.EdgeCheck;
import org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.util.lambda.Lookup;
import org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.util.lambda.NACNodeCheck;
import org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.util.lambda.NodeCheck;
import org.emoflon.ibex.tgg.operational.repair.strategies.util.SCEMFUtil;
import org.moflon.core.utilities.eMoflonEMFUtil;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import language.BindingType;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public abstract class OperationalShortcutRule {
	protected SyncDirection direction;
	protected ShortcutRule scRule;
	
	protected Collection<TGGRuleElement> markedElements;
	protected Map<SearchKey, Lookup> key2lookup;
	protected Map<TGGRuleNode, NodeCheck> element2nodeCheck;
	protected Map<SearchKey, EdgeCheck> key2edgeCheck;
	protected Map<SearchKey, NACNodeCheck> key2nacNodeCheck;

	public OperationalShortcutRule(SyncDirection direction, ShortcutRule scRule) {
		this.scRule = scRule;
		this.direction = direction;

		this.markedElements = new HashSet<>();
		this.key2lookup = new Object2ObjectOpenHashMap<>();
		this.element2nodeCheck = new Object2ObjectOpenHashMap<>();
		this.key2edgeCheck = new Object2ObjectOpenHashMap<>();
		this.key2nacNodeCheck= new Object2ObjectOpenHashMap<>();
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
		
		List<Pair<SearchKey, Lookup>> searchPlan = new ArrayList<>();
		while(!uncheckedNodes.isEmpty()) {
			Collection<SearchKey> checkedSearchKeys = filterAndSortKeys(uncheckedSearchKeys, uncheckedNodes);
			if(checkedSearchKeys.isEmpty()) {
				throw new RuntimeException("Searchplan could not be generated for OperationalShortcutRule - " + scRule);
			}
			
			SearchKey key = checkedSearchKeys.iterator().next();
			searchPlan.add(Pair.of(key, key2lookup.get(key)));
			
			uncheckedNodes.remove(key.reverse ? key.sourceNode : key.targetNode);
			uncheckedEdges.remove(key.edge);
			
			uncheckedSearchKeys.remove(key);
			checkedSearchKeys.add(key);
		}
		
		Map<SearchKey, EdgeCheck> key2uncheckedEdgeCheck = new HashMap<>();
		for(TGGRuleEdge edge : uncheckedEdges) {
			if(edge.getBindingType() == BindingType.CREATE)
				continue;
			
			if(edge.getSrcNode().getBindingType() == BindingType.NEGATIVE || edge.getTrgNode().getBindingType() == BindingType.NEGATIVE)
				continue;
			
			SearchKey key = new SearchKey(edge.getSrcNode(), edge.getTrgNode(), edge, false);
			key2uncheckedEdgeCheck.put(key, key2edgeCheck.get(key));
		}
		
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
		
		return new SearchPlan(searchPlan, element2nodeCheck, key2uncheckedEdgeCheck, key2nacNodeCheck);
	}
	
	private Collection<SearchKey> filterAndSortKeys(Collection<SearchKey> keys, Collection<TGGRuleNode> uncheckedNodes) {
		return keys.stream()
				.filter(k -> validKey(uncheckedNodes, k))
				.filter(k -> k.edge.getBindingType() != BindingType.NEGATIVE)
				.filter(k -> k.edge.getBindingType() != BindingType.CREATE)
//				.filter(k -> 
//					uncheckedNodes.contains(k.sourceNode) && !k.reverse || 
//					uncheckedNodes.contains(k.targetNode) && k.reverse)
				.collect(Collectors.toList());
	}
	
	private boolean validKey(Collection<TGGRuleNode> uncheckedNodes, SearchKey key) {
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
				return SCEMFUtil.getOppositeReference(n, key.sourceNode.getType(), key.edge.getType().getName());
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
		// TODO lfritsche : implement attribute handling (also in TGGOverlap)
		// TODO lfritsche : implement inheritance concept
		element2nodeCheck.put(key, n -> {
			if (n == null)
				return false;
			return n.eClass().equals(key.getType());
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
}
