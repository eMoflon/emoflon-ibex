package org.emoflon.ibex.tgg.runtime.repair.shortcut.search;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
import org.emoflon.ibex.tgg.runtime.csp.IRuntimeTGGAttrConstrContainer;
import org.emoflon.ibex.tgg.runtime.interpreter.IGreenInterpreter;
import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.rule.OperationalShortcutRule;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.search.lambda.AttributeCheck;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.search.lambda.EdgeCheck;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.search.lambda.Lookup;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.search.lambda.NACNodeCheck;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.search.lambda.NodeCheck;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.util.SCMatch;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGCorrespondence;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGEdge;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode;
import org.emoflon.ibex.tgg.util.EMFNavigationUtil;
import org.emoflon.ibex.tgg.util.TGGAttributeCheckUtil;
import org.emoflon.ibex.tgg.util.debug.LoggerConfig;
import org.moflon.core.utilities.eMoflonEMFUtil;

import TGGRuntimeModel.TGGRuntimeModelPackage;

public class SearchPlanCreator {

	protected final IbexOptions options;
	protected final IGreenInterpreter greenInterpreter;
	protected final OperationalShortcutRule opSCR;

	protected Map<SearchKey, Lookup> key2lookup;
	protected Map<TGGNode, NodeCheck> elt2nodeCheck;
	protected Map<SearchKey, EdgeCheck> key2edgeCheck;
	protected Map<SearchKey, NACNodeCheck> key2nacNodeCheck;
	protected AttributeCheck attributeCheck;

	public SearchPlanCreator(IbexOptions options, IGreenInterpreter greenInterpreter, OperationalShortcutRule opScRule) {
		this.options = options;
		this.greenInterpreter = greenInterpreter;
		this.opSCR = opScRule;
		initialize();
	}

	protected void initialize() {
		this.key2lookup = cfactory.createObjectToObjectHashMap();
		this.elt2nodeCheck = cfactory.createObjectToObjectHashMap();
		this.key2edgeCheck = cfactory.createObjectToObjectHashMap();
		this.key2nacNodeCheck = cfactory.createObjectToObjectHashMap();

		createConstraintChecks();
	}

	protected void createConstraintChecks() {
		for (TGGNode node : opSCR.getOperationalizedSCR().getNodes()) {
			createNodeCheck(node);
		}

		for (TGGEdge edge : opSCR.getOperationalizedSCR().getEdges()) {
			SearchKey forwardKey = new SearchKey((TGGNode) edge.getSource(), (TGGNode) edge.getTarget(), edge, false);
			SearchKey backwardKey = new SearchKey((TGGNode) edge.getSource(), (TGGNode) edge.getTarget(), edge, true);

			createEdgeCheck(forwardKey);

			createLookup(forwardKey);
			createOppositeLookup(backwardKey);

			if (edge.getBindingType() != BindingType.NEGATIVE)
				continue;

			if (((TGGNode) edge.getSource()).getBindingType() == BindingType.NEGATIVE) {
				createNACNodeCheck(backwardKey);
			}
			if (((TGGNode) edge.getTarget()).getBindingType() == BindingType.NEGATIVE) {
				createNACNodeCheck(forwardKey);
			}
		}

		attributeCheck = (name2candidates) -> {
			return checkAttributeConditions(name2candidates);// && checkCSPs(name2candidates);
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
				// we have to make sure that the eContaining feature is the correct one
				return edgeRef.equals(n.eContainingFeature()) ? (EObject) n.eContainer() : null;
			});
		} else {
			if (edgeRef.getEOpposite() != null) {
				key2lookup.put(key, n -> {
					return n.eGet(edgeRef.getEOpposite());
				});
			} else {
				if (key.sourceNode instanceof TGGCorrespondence) {
					key2lookup.put(key, n -> {
						if (options.project.usesSmartEMF())
							throw new IllegalStateException("If SmartEMF is used, there is no need to traverse using opposite lookups! " + "Check if lookups for derived opposite edges were created properly.");
						// make sure that we only get the correct corrs with the right type
						Collection<EObject> corrs = options.resourceHandler().getCorrCaching().getOrDefault(n, Collections.emptyList());
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
			if (((TGGNode) key.edge.getSource()).getBindingType().equals(BindingType.NEGATIVE) || ((TGGNode) key.edge.getTarget()).getBindingType().equals(BindingType.NEGATIVE))
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

	private void createNodeCheck(TGGNode key) {
		elt2nodeCheck.put(key, n -> {
			if (n == null)
				return key.getBindingType() == BindingType.RELAXED;
			return key.getType().isSuperTypeOf(n.eClass());
		});
	}

	private boolean checkAttributeConditions(Map<String, EObject> name2candidates) {
		var attributeConditions = opSCR.getOperationalizedSCR().getShortcutRule().getPrecondition().getConditions();
		for (var expression : attributeConditions) {
			if (!TGGAttributeCheckUtil.checkAttributeCondition(expression, name2candidates))
				return false;
		}
		return true;
	}

	private boolean checkCSPs(Map<String, EObject> name2candidates) {
		ITGGMatch match = new SCMatch(opSCR.getOperationalizedSCR().getName(), name2candidates);
		IRuntimeTGGAttrConstrContainer cspContainer = greenInterpreter.getAttributeConstraintContainer(match);
		return cspContainer.solve();
	}

	public SearchPlan createSearchPlan() {
		Collection<TGGNode> uncheckedNodes = new ArrayList<>();
		Collection<TGGNode> uncheckedRelaxedNodes = new ArrayList<>();
		opSCR.getOperationalizedSCR().getNodes().stream() //
				.filter(n -> !opSCR.getOperationalizedSCR().getMergedNodes().contains(n)) //
				.filter(n -> !opSCR.getOperationalizedSCR().getNewOriginalNodes().contains(n)) //
				.filter(n -> !TGGRuntimeModelPackage.eINSTANCE.getTGGRuleApplication().isSuperTypeOf(n.getType())) //
				.filter(n -> n.getBindingType() != BindingType.NEGATIVE) //
				.filter(n -> n.getBindingType() != BindingType.CREATE) //
				.filter(n -> n.getBindingType() != BindingType.RELAXED) //
				.forEach(n -> uncheckedNodes.add(n));

		LinkedList<TGGEdge> uncheckedEdges = new LinkedList<>();
		for (TGGEdge edge : opSCR.getOperationalizedSCR().getEdges()) {
			if (edge.getBindingType() == BindingType.NEGATIVE)
				uncheckedEdges.addLast(edge);
			else
				uncheckedEdges.addFirst(edge);
		}

		List<SearchKey> uncheckedSearchKeys = key2lookup.keySet().stream() //
				.filter(key -> key.edge.getBindingType() != BindingType.NEGATIVE) //
				.filter(key -> key.edge.getBindingType() != BindingType.CREATE) //
				.collect(Collectors.toList());

		List<Pair<SearchKey, Lookup>> searchPlan = new ArrayList<>();

		// first calculate the lookups to find all elements + their corresponding node
		// checks
		while (!uncheckedNodes.isEmpty()) {
			List<SearchKey> nextSearchKeys = filterKeys(uncheckedSearchKeys, uncheckedNodes, uncheckedRelaxedNodes, false);
			if (nextSearchKeys.isEmpty()) {
				LoggerConfig.log(LoggerConfig.log_repair(), () -> "!Warning! Searchplan could not be generated for: " + opSCR.getName());
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
		for (TGGEdge edge : uncheckedEdges) {
			if (edge.getBindingType() == BindingType.CREATE || edge.getBindingType() == BindingType.RELAXED)
				continue;

			if (edge.getBindingType() != BindingType.NEGATIVE)
				if (((TGGNode) edge.getSource()).getBindingType() == BindingType.RELAXED || ((TGGNode) edge.getTarget()).getBindingType() == BindingType.RELAXED)
					continue;

			if (((TGGNode) edge.getSource()).getBindingType() == BindingType.NEGATIVE || ((TGGNode) edge.getTarget()).getBindingType() == BindingType.NEGATIVE)
				continue;

			SearchKey key = new SearchKey((TGGNode) edge.getSource(), (TGGNode) edge.getTarget(), edge, false);
			key2uncheckedEdgeCheck.put(key, key2edgeCheck.get(key));
		}

		// add NAC checks as the last constraints that are evaluated
		Map<SearchKey, NACNodeCheck> key2nacNodeCheck = new HashMap<>();
		for (TGGEdge edge : uncheckedEdges) {
			if (edge.getBindingType() != BindingType.NEGATIVE)
				continue;

			if (((TGGNode) edge.getSource()).getBindingType() != BindingType.NEGATIVE && ((TGGNode) edge.getTarget()).getBindingType() != BindingType.NEGATIVE)
				continue;

			boolean reverse = ((TGGNode) edge.getSource()).getBindingType() == BindingType.NEGATIVE;
			SearchKey key = new SearchKey((TGGNode) edge.getSource(), (TGGNode) edge.getTarget(), edge, reverse);
			key2nacNodeCheck.put(key, this.key2nacNodeCheck.get(key));
		}

		return new SearchPlan(searchPlan, elt2nodeCheck, key2uncheckedEdgeCheck, key2nacNodeCheck, attributeCheck);
	}

	private List<SearchKey> filterKeys(List<SearchKey> keys, Collection<TGGNode> uncheckedNodes, Collection<TGGNode> uncheckedRelaxedNodes, boolean relaxed) {
		Collection<SearchKey> filteredKeys = keys.stream() //
				.filter(k -> validLookupKey(uncheckedNodes, uncheckedRelaxedNodes, k, false)).collect(Collectors.toList());

		// if no navigable edge was found -> allow to navigate backwards
		if (filteredKeys.isEmpty()) {
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
			return k.sourceNode instanceof TGGCorrespondence;
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
	private boolean validLookupKey(Collection<TGGNode> uncheckedNodes, Collection<TGGNode> uncheckedRelaxedNodes, SearchKey key, boolean allowBackNavigate) {
		boolean srcChecked = !uncheckedNodes.contains(key.sourceNode) && !uncheckedRelaxedNodes.contains(key.sourceNode);
		boolean trgChecked = !uncheckedNodes.contains(key.targetNode) && !uncheckedRelaxedNodes.contains(key.targetNode);

		boolean notReverse = !key.reverse && srcChecked && !trgChecked;
		boolean reverse = key.reverse && !srcChecked && trgChecked;
		boolean backward = allowBackNavigate || key.edge.getType().getEOpposite() != null || key.sourceNode instanceof TGGCorrespondence;

		return notReverse || reverse && backward;
	}

}
