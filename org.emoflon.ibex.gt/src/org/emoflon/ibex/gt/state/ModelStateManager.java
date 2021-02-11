package org.emoflon.ibex.gt.state;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.common.emf.EMFManipulationUtils;
import org.emoflon.ibex.common.operational.HashUtil;
import org.emoflon.ibex.common.operational.IContextPatternInterpreter;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.gt.StateModel.AttributeDelta;
import org.emoflon.ibex.gt.StateModel.Link;
import org.emoflon.ibex.gt.StateModel.RuleState;
import org.emoflon.ibex.gt.StateModel.State;
import org.emoflon.ibex.gt.StateModel.StateContainer;
import org.emoflon.ibex.gt.StateModel.StateModelFactory;
import org.emoflon.ibex.gt.StateModel.StructuralDelta;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeAssignment;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCreatePattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDeletePattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEdge;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule;

import com.google.common.collect.Lists;

public class ModelStateManager {
	private Resource model;
	private Resource trashResource;
	private IContextPatternInterpreter engine;
	private StateModelFactory factory = StateModelFactory.eINSTANCE;
	private StateContainer modelStates;
	private State currentState;
	private Map<State, Function<Boolean, Optional<IMatch>>> gtApply;
	
	public ModelStateManager(final Resource model, final Resource trashResource, final IContextPatternInterpreter engine) {
		this.model = model;
		this.trashResource = trashResource;
		this.engine = engine;
		init();
	}
	
	private void init() {
		gtApply = new HashMap<>();
		modelStates = factory.createStateContainer();
		State initialState = factory.createState();
		modelStates.getStates().add(initialState);
		modelStates.setInitialState(initialState);
		initialState.setInitial(true);
//		initialState.setHash(recalculateHash(initialState));
		currentState = initialState;
	}
	
	public State getCurrentState() {
		return currentState;
	}
	
	public Optional<IMatch> addNewState(final IBeXRule rule, final IMatch match, boolean doUpdate, Function<Boolean, Optional<IMatch>> applyRule) {
		RuleState newState = factory.createRuleState();
		
		newState.setInitial(false);
		newState.setMatch(match);
		newState.setRule(rule);
		
		// Store attribute assignments for context nodes
		Map<IBeXAttributeAssignment, AttributeDelta> attributeDeltas = new HashMap<>();
		rule.getCreate().getAttributeAssignments().stream()
			.filter(asgn -> match.isInMatch(asgn.getNode().getName()))
			.forEach(asgn -> attributeDeltas.put(asgn, createAttributeDelta(match, asgn)));
		
		// Find all deleted nodes as well as transitively deleted nodes (containment)
		Set<EObject> nodesToWatch = findAllDeletedNodes(match, rule.getDelete());
		
		// Find all nodes existing outside EObject containments but still within the resource, 
		// which will be placed into proper containment after rule application.
		Set<EObject> nodesInResource = findAllNodesRemovedFromResourceContainment(match, rule.getCreate());
		
		// Register adapter to catch all indirectly deleted edges
		ModelStateDeleteAdapter adapter = new ModelStateDeleteAdapter(model);
		adapter.pluginAdapter(nodesToWatch);
		
		// Let the rule play out
		Optional<IMatch> optComatch = applyRule.apply(doUpdate);
		if(!optComatch.isPresent())
			return optComatch;
		gtApply.put(newState, applyRule);
		
		IMatch comatch = optComatch.get();
		newState.setCoMatch(comatch);
		
		modelStates.getStates().add(newState);
		newState.setParent(currentState);
		currentState.getChildren().add(newState);
		currentState = newState;
		
		// Store attribute assignments for created nodes
		rule.getCreate().getAttributeAssignments().stream()
			.forEach(asgn -> {
				newState.getAttributeDeltas().add(completeAttributeDelta(comatch, asgn, attributeDeltas.get(asgn)));
			});
		
		// Store created and deleted nodes/edges
		StructuralDelta delta = factory.createStructuralDelta();
		if(addCreateDelta(delta, comatch, rule.getCreate()) || addDeleteDelta(delta, match, rule.getDelete())) {
			newState.setStructuralDelta(delta);
		}
		
		if(adapter.getRemovedLinks().size()>0) {
			delta.getDeletedLinks().addAll(adapter.getRemovedLinks());
			
			if(newState.getStructuralDelta() == null) {
				newState.setStructuralDelta(delta);
			}
		}
		
		delta.getResource2EObjectContainment().addAll(nodesInResource);
		
		// Calculate hash based on current state and all prior states -> see crypto currency
//		newState.setHash(recalculateHash(newState));
		
		adapter.removeAdapter();
		
		return optComatch;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Optional<IMatch> revertToPrevious() {
		if(currentState.isInitial())
			Optional.empty();
		
		RuleState currentRuleState = (RuleState)currentState;
		State previousState = currentRuleState.getParent();
		
		Set<EObject> createdNodes = new HashSet<>();
		
		if(currentRuleState.getStructuralDelta() != null) {
			StructuralDelta delta = currentRuleState.getStructuralDelta();
			
			
			// Restore root level nodes
			delta.getDeletedRootLevelObjects().forEach(node -> {
				model.getContents().add(node);
			});
			
			// Restore deleted nodes and edges -> restoring edges is enough, since deleted nodes are nodes whose containment edge has been removed
			LinkedList<Link> toRestore = new LinkedList<>();
			toRestore.addAll(delta.getDeletedLinks());
			// Ignore contents of the delete pattern (which is incomplete anyways) -> take the ground truth from collected emf notifications
			while(!toRestore.isEmpty()) {
				Link current = toRestore.poll();
				if(current.getType().getUpperBound()<0) {
					List<EObject> refs = (List<EObject>)current.getSrc().eGet(current.getType());
					refs.add(current.getTrg());
				} else {
					current.getSrc().eSet(current.getType(), current.getTrg());
				}
			}
			
			// Delete created edges and nodes
			Set<EMFEdge> toDelete = delta.getCreatedLinks().stream()
					.map(link -> new EMFEdge(link.getSrc(), link.getTrg(), link.getType()))
					.collect(Collectors.toSet());
			if(engine.getProperties().needs_trash_resource()) 
				EMFManipulationUtils.delete(new HashSet(), toDelete,
						node -> trashResource.getContents().add(EcoreUtil.getRootContainer(node)), false);
			else
				EMFManipulationUtils.delete(new HashSet(), toDelete, false);
			
			// Remove deleted nodes from containment
			createdNodes.addAll(delta.getCreatedObjects());
			if(engine.getProperties().needs_trash_resource()) 
				EMFManipulationUtils.delete(createdNodes, new HashSet(),
						node -> trashResource.getContents().add(EcoreUtil.getRootContainer(node)), false);
			else
				EMFManipulationUtils.delete(createdNodes, new HashSet(), false);
			
//			// Restore Resource to EObject links
			delta.getResource2EObjectContainment().forEach(node -> {
					model.getContents().add(node);
			});
		}
		
		// Restore attribute values
		currentRuleState.getAttributeDeltas().stream()
			.filter(atrDelta -> !createdNodes.contains(atrDelta.getObject()))
			.forEach(atrDelta -> {
				atrDelta.getObject().eSet(atrDelta.getAttribute(), atrDelta.getOldValue());
			});
		currentState = previousState;
		
		if(currentState instanceof RuleState) {
			return Optional.of((IMatch)((RuleState)currentState).getCoMatch());
		} else {
			return Optional.empty();
		}
	}
	
	public Optional<IMatch> moveToState(final State targetState) {
		if(!modelStates.getStates().contains(targetState)) {
			return Optional.empty();
		}
			
		Queue<State> path = findPathToTargetState(targetState);
		while(!path.isEmpty()) {
			State next = path.poll();
			if(currentState.getChildren().contains(next)) {
				moveToChildState(next);
			} else {
				revertToPrevious();
			}
		}
		
		if(currentState instanceof RuleState) {
			return Optional.of((IMatch)((RuleState)currentState).getCoMatch());
		} else {
			return Optional.empty();
		}
	}
	
	public Optional<IMatch> moveToChildState(final State childState) {
		if(!currentState.getChildren().contains(childState)) {
			return Optional.empty();
		}
		
		// Let the rule play out again
		Optional<IMatch> optComatch = gtApply.get(childState).apply(false);
		if(!optComatch.isPresent())
			return optComatch;

		currentState = childState;
		
		return optComatch;
	}
	
	private Queue<State> findPathToTargetState(final State trgState) {
		State srcState = currentState;
		if(srcState.equals(trgState)) {
			return new LinkedList<>();
		}
		
		LinkedList<State> path = new LinkedList<>();
		
		// Find common root
		State commonRoot = null;
		if(!(srcState.isInitial() || trgState.isInitial())) {
			RuleState currentSrc = (RuleState) srcState;
			RuleState currentTrg = (RuleState) trgState;
			int steps = 0;
			while(commonRoot == null) {
				if(currentSrc.getParent().equals(currentTrg)) {
					commonRoot = currentTrg;
				} else if(currentTrg.getParent().equals(currentSrc)) {
					commonRoot = currentSrc;
				} else {
					if(steps % 2 == 0) {
						if(currentSrc.getParent().isInitial()) {
							commonRoot = currentSrc.getParent();
						} else {
							currentSrc = (RuleState) currentSrc.getParent();
						}
					} else {
						if(currentTrg.getParent().isInitial()) {
							commonRoot = currentTrg.getParent();
						} else {
							currentTrg = (RuleState) currentTrg.getParent();
						}
					}
					steps++;
				}
			}
		} else {
			if(srcState.isInitial()) {
				commonRoot = srcState;
			} else {
				commonRoot = trgState;
			}
		}
		
		// Traverse to common root and remember paths
		State current = srcState;
		while(!current.equals(commonRoot)) {
			RuleState currentSrc = (RuleState) current;
			current = currentSrc.getParent();
			path.add(current);
		}
		
		LinkedList<State> trg2commonRoot = new LinkedList<>();
		current = trgState;
		while(!current.equals(commonRoot)) {
			RuleState currentTrg = (RuleState) current;
			current = currentTrg.getParent();
			trg2commonRoot.add(current);
		}
		// remove root
		trg2commonRoot.removeLast();
		// invert order
		trg2commonRoot = new LinkedList<>(Lists.reverse(trg2commonRoot));
		path.addAll(trg2commonRoot);
		path.add(trgState);
		
		return path;
	}
	
	private AttributeDelta createAttributeDelta(final IMatch match, final IBeXAttributeAssignment assignment) {
		AttributeDelta delta = factory.createAttributeDelta();
		
		delta.setAttribute(assignment.getType());
		EObject node = (EObject) match.get(assignment.getNode().getName());
		delta.setObject(node);
		delta.setOldValue(node.eGet(assignment.getType()));
		
		return delta;
	}
	
	private AttributeDelta completeAttributeDelta(final IMatch match, final IBeXAttributeAssignment assignment, AttributeDelta delta) {
		if(delta == null) {
			delta = createAttributeDelta(match, assignment);
		}
		
		EObject node = (EObject) match.get(assignment.getNode().getName());
		delta.setNewValue(node.eGet(assignment.getType()));
		
		return delta;
	}
	
	private boolean addCreateDelta(StructuralDelta delta, final IMatch match, final IBeXCreatePattern pattern) {
		if(pattern.getCreatedNodes().size() == 0 && pattern.getCreatedEdges().size() == 0) {
			return false;
		}
		
		for(IBeXNode createdNode : pattern.getCreatedNodes()) {
			delta.getCreatedObjects().add((EObject) match.get(createdNode.getName()));
		}
		
		for(IBeXEdge createdEdge : pattern.getCreatedEdges()) {
			EObject src = (EObject) match.get(createdEdge.getSourceNode().getName());
			EObject trg = (EObject) match.get(createdEdge.getTargetNode().getName());
			Link link = factory.createLink();
			link.setSrc(src);
			link.setTrg(trg);
			link.setType(createdEdge.getType());
			delta.getCreatedLinks().add(link);
			
			if(createdEdge.getType().isContainment() && trg.eContainer() instanceof Resource) {
				delta.getResource2EObjectContainment().add(trg);
			}
		}
		
		return true;
	}
	
	private boolean addDeleteDelta(StructuralDelta delta, final IMatch match, final IBeXDeletePattern pattern) {
		if(pattern.getDeletedNodes().size() == 0 && pattern.getDeletedEdges().size() == 0) {
			return false;
		}
		
		for(IBeXNode deleteNode : pattern.getDeletedNodes()) {
			EObject actualNode = (EObject) match.get(deleteNode.getName());
			if(actualNode.eContainer() instanceof Resource) {
				delta.getDeletedRootLevelObjects().add(actualNode);
			}
			delta.getDeletedObjects().add(actualNode);
		}
		
		for(IBeXEdge deletedEdge : pattern.getDeletedEdges()) {
			EObject src = (EObject) match.get(deletedEdge.getSourceNode().getName());
			EObject trg = (EObject) match.get(deletedEdge.getTargetNode().getName());
			Link link = factory.createLink();
			link.setSrc(src);
			link.setTrg(trg);
			link.setType(deletedEdge.getType());
			delta.getDeletedLinks().add(link);
		}
		
		return true;
	}
	
	private Set<EObject> findAllDeletedNodes(final IMatch match, final IBeXDeletePattern pattern) {
		Set<EObject> deletedNodes = new HashSet<>();
		if(pattern == null)
			return deletedNodes;
		
		for(IBeXNode deleteNode : pattern.getDeletedNodes()) {
			EObject current = (EObject) match.get(deleteNode.getName());
			exploreContainmentHierarchy(deletedNodes, current);
		}
		for(IBeXEdge deletedEdge : pattern.getDeletedEdges()) {
			if(deletedEdge.getType().isContainment()) {
				EObject current = (EObject) match.get(deletedEdge.getTargetNode().getName());
				if(deletedNodes.add(current)) {
					exploreContainmentHierarchy(deletedNodes, current);
				}
			}
		}
		
		return deletedNodes;
	}
	
	private Set<EObject> findAllNodesRemovedFromResourceContainment(final IMatch match, final IBeXCreatePattern pattern) {
		Set<EObject> createdNodes = new HashSet<>(pattern.getCreatedNodes());
		Set<EObject> resourceNodes = new HashSet<>();
		for(IBeXEdge createdEdge : pattern.getCreatedEdges()) {
			EObject trg = (EObject) match.get(createdEdge.getTargetNode().getName());
			if(trg == null)
				continue;
			
			if(createdEdge.getType().isContainment() && trg.eContainer() == null && !createdNodes.contains(trg) && model.getContents().contains(trg)) {
				resourceNodes.add(trg);
			}
		}
		return resourceNodes;
	}
	
	private long recalculateHash(State state) {
		if(state instanceof RuleState) {
			RuleState rState = (RuleState)state;
			List<Object> components = new LinkedList<>();
			components.add(rState.getParent().getHash());
			components.add(rState);
			components.add(rState.getMatch());
			components.add(rState.getCoMatch());
			if(rState.getAttributeDeltas() != null && rState.getAttributeDeltas().size()>0)
				components.addAll(rState.getAttributeDeltas());
			if(rState.getStructuralDelta() != null) {
				StructuralDelta delta = rState.getStructuralDelta();
				if(delta.getCreatedObjects() != null && delta.getCreatedObjects().size()>0);
					components.addAll(delta.getCreatedObjects());
				if(delta.getDeletedObjects() != null && delta.getDeletedObjects().size()>0);
					components.addAll(delta.getDeletedObjects());
				if(delta.getCreatedLinks() != null && delta.getCreatedLinks().size()>0);
					components.addAll(delta.getCreatedLinks());
				if(delta.getDeletedLinks() != null && delta.getDeletedLinks().size()>0);
					components.addAll(delta.getDeletedLinks());
			}
			return HashUtil.collectionToHash(components);
		}else {
			return HashUtil.objectToHash(state);
		}
	}
	
	private void exploreContainmentHierarchy(final Set<EObject> deletedObjects, final EObject container) {
		if(container == null) 
			return;
		
		deletedObjects.add(container);
		if(container.eContents() == null || container.eContents().isEmpty())
			return;
		
		Queue<EObject> frontier = new LinkedList<>();
		frontier.addAll(container.eContents());
		while(!frontier.isEmpty()) {
			EObject current = frontier.poll();
			deletedObjects.add(current);
			if(container.eContents() != null && !container.eContents().isEmpty())
				frontier.addAll(container.eContents());
		}
	}
}

class ModelStateDeleteAdapter extends EContentAdapter {
	
	private StateModelFactory factory = StateModelFactory.eINSTANCE;
	private final Resource resource;
	private Set<EObject> nodesToWatch;
	private Set<Link> removedLinks;
	
	public ModelStateDeleteAdapter(final Resource resource) {
		this.resource = resource;
	}
	
	public void pluginAdapter(final Set<EObject> nodesToWatch) {
		this.nodesToWatch = nodesToWatch;
		removedLinks = new LinkedHashSet<>();
		resource.eAdapters().add(this);
	}
	
	public void removeAdapter() {
		resource.eAdapters().remove(this);
		this.nodesToWatch = null;
		removedLinks = null;
	}
	
	public Set<Link> getRemovedLinks() {
		return removedLinks;
	}
	
	@Override
	public void notifyChanged(Notification notification) {	
		super.notifyChanged(notification);
		//DO Stuff..
		switch(notification.getEventType()) {
			case Notification.REMOVE: {
				Object feature = notification.getFeature();
				if(feature == null)
					break;
				
				if(nodesToWatch.contains(notification.getNotifier()) || nodesToWatch.contains(notification.getOldValue())) {
					Link link = factory.createLink();
					link.setSrc((EObject) notification.getNotifier());
					link.setTrg((EObject) notification.getOldValue());
					link.setType((EReference) feature);
					removedLinks.add(link);
				}
				break;
			}
			case Notification.REMOVING_ADAPTER: {
//				We can ignore this in most cases, since restoring the removed containment edge will also restore containment
//				TODO: Look at the deletion of root-level "Container"-Objects from resources -> this will lead to a problem, since these do not have a containment reference, they are contained in the resource itself!
				break;
			}
			case Notification.SET: {
				Object feature = notification.getFeature();
				if(feature == null || !(feature instanceof EReference))
					break;
				
				if(nodesToWatch.contains(notification.getNotifier()) || nodesToWatch.contains(notification.getOldValue())) {
					Link link = factory.createLink();
					link.setSrc((EObject) notification.getNotifier());
					link.setTrg((EObject) notification.getOldValue());
					link.setType((EReference) feature);
					removedLinks.add(link);
				}
				break;
			}
		}
	
	}
	
	
}