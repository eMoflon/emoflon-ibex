package org.emoflon.ibex.tgg.operational.repair.strategies.shortcut;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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

		this.markedElements = new ArrayList<>();
		this.key2lookup = new Object2ObjectOpenHashMap<>();
		this.element2nodeCheck = new Object2ObjectOpenHashMap<>();
		this.key2edgeCheck = new Object2ObjectOpenHashMap<>();
		this.key2nacNodeCheck= new Object2ObjectOpenHashMap<>();
	}

	abstract protected void operationalize();

	public SearchPlan createSearchPlan() {
		Collection<TGGRuleNode> uncheckedNodes = scRule.getNodes().stream().filter(n -> !scRule.getEntryNodes().contains(n)).collect(Collectors.toList());
		Collection<TGGRuleEdge> uncheckedEdges = scRule.getEdges().stream().collect(Collectors.toList());
		
		Collection<SearchKey> uncheckedSearchKeys = key2lookup.keySet();
		
		List<Pair<SearchKey, Lookup>> searchPlan = new ArrayList<>();
		while(!uncheckedNodes.isEmpty()) {
			uncheckedSearchKeys = filterAndSortKeys(uncheckedSearchKeys, uncheckedNodes);
			if(uncheckedSearchKeys.isEmpty()) {
				throw new RuntimeException("Searchplan could not be generated for OperationalShortcutRule");
			}
			
			SearchKey key = uncheckedSearchKeys.iterator().next();
			searchPlan.add(Pair.of(key, key2lookup.get(key)));
			
			uncheckedNodes.remove(key.reverse ? key.sourceNode : key.targetNode);
			uncheckedEdges.remove(key.edge);
		}
		
		Map<SearchKey, EdgeCheck> key2uncheckedEdgeCheck = new HashMap<>();
		for(TGGRuleEdge edge : uncheckedEdges) {
			if(edge.getBindingType() == BindingType.NEGATIVE)
				continue;
			
			SearchKey key = new SearchKey(edge.getSrcNode(), edge.getTrgNode(), edge, false);
			key2uncheckedEdgeCheck.put(key, key2edgeCheck.get(key));
		}
		return new SearchPlan(searchPlan, element2nodeCheck, key2uncheckedEdgeCheck, key2nacNodeCheck);
	}
	
	private Collection<SearchKey> filterAndSortKeys(Collection<SearchKey> keys, Collection<TGGRuleNode> uncheckedNodes) {
		keys = keys.stream().filter(k -> uncheckedNodes.contains(k.sourceNode) ^ uncheckedNodes.contains(k.targetNode)).collect(Collectors.toList());
		keys = keys.stream().filter(k -> k.edge.getBindingType() != BindingType.NEGATIVE).collect(Collectors.toList());
		keys = keys.stream().filter(k -> 
			uncheckedNodes.contains(k.sourceNode) && !k.reverse || 
			uncheckedNodes.contains(k.targetNode) && k.reverse).collect(Collectors.toList());
		return keys;
	}
	
	protected void createConstraintChecks() {
		for (TGGRuleNode node : scRule.getNodes()) {
			createNodeCheck(node);
		}

		for (TGGRuleEdge edge : scRule.getEdges()) {
			SearchKey forwardKey = new SearchKey(edge.getSrcNode(), edge.getTrgNode(), edge, true);
			SearchKey backwardKey = new SearchKey(edge.getSrcNode(), edge.getTrgNode(), edge, false);
			
			createEdgeCheck(forwardKey);
			
			createLookup(forwardKey);
			createOppositeLookup(backwardKey);

			if(edge.getBindingType() != BindingType.NEGATIVE)
				continue;
			
			if(edge.getSrcNode().getBindingType() == BindingType.NEGATIVE) {
				createNACNodeCheck(forwardKey);
			}
			if(edge.getTrgNode().getBindingType() == BindingType.NEGATIVE) {
				createNACNodeCheck(backwardKey);
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
		key2nacNodeCheck.put(key, s -> {
			Object refTarget = s.eGet(key.edge.getType());
			if (refTarget == null) {
				return true;
			}

			if (refTarget instanceof List<?>) {
				List<EObject> list = (List<EObject>) refTarget;
				for(EObject obj : list) {
					if(nodeCheck.checkConstraint(obj))
						return false;
				}
				return true;
			}
			return !nodeCheck.checkConstraint((EObject) refTarget);
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
