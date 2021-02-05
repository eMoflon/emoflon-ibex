package org.emoflon.ibex.gt.state;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.emoflon.ibex.common.operational.HashUtil;
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

public class ModelStateManager {
	private StateModelFactory factory = StateModelFactory.eINSTANCE;
	private StateContainer modelStates;
	private State currentState;
	
	public ModelStateManager() {
		init();
	}
	
	private void init() {
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
	
	public Optional<IMatch> addNewState(final IBeXRule rule, final IMatch match, Supplier<Optional<IMatch>> applyRule) {
		RuleState newState = factory.createRuleState();
		
		newState.setInitial(false);
		newState.setMatch(match);
		newState.setRule(rule);
		
		// Store attribute assignments for context nodes
		Map<IBeXAttributeAssignment, AttributeDelta> attributeDeltas = new HashMap<>();
		rule.getCreate().getAttributeAssignments().stream()
			.filter(asgn -> match.isInMatch(asgn.getNode().getName()))
			.forEach(asgn -> attributeDeltas.put(asgn, createAttributeDelta(match, asgn)));
		
		// Let the rule play out
		Optional<IMatch> optComatch = applyRule.get();
		if(!optComatch.isPresent())
			return optComatch;
		
		IMatch comatch = optComatch.get();
		modelStates.getStates().add(newState);
		newState.setParent(currentState);
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
		
		// Calculate hash based on current state and all prior states -> see crypto currency
//		newState.setHash(recalculateHash(newState));
		
		return optComatch;
	}
	
	public Optional<IMatch> revertToPrevious() {
		if(currentState.isInitial())
			Optional.empty();
		
		RuleState current = (RuleState)currentState;
		State previousState = current.getParent();
		IBeXRule r = current.getRule();
		IMatch match = (IMatch) current.getMatch();
		IMatch comatch = (IMatch) current.getCoMatch();
		
		if(current.getStructuralDelta() != null) {
			StructuralDelta delta = current.getStructuralDelta();
			// Restore deleted nodes and edges
			if(r.getDelete() != null) {
				IBeXDeletePattern delete = r.getDelete();
				for(IBeXNode deletedNode : delete.getDeletedNodes()) {
					
				}
			}
			
			// Delete created edges and nodes
				
		}
		return null;
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
		}
		
		return true;
	}
	
	private boolean addDeleteDelta(StructuralDelta delta, final IMatch match, final IBeXDeletePattern pattern) {
		if(pattern.getDeletedNodes().size() == 0 && pattern.getDeletedEdges().size() == 0) {
			return false;
		}
		
		for(IBeXNode deleteNode : pattern.getDeletedNodes()) {
			delta.getDeletedObjects().add((EObject) match.get(deleteNode.getName()));
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
		removedLinks = new HashSet<>();
		resource.eAdapters().add(this);
	}
	
	public void removeAdapter() {
		resource.eAdapters().remove(this);
		this.nodesToWatch = null;
		removedLinks = null;
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
				EObject container = (EObject) notification.getNotifier();
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
	
//	private void exploreContainmentHierarchy(EObject container) {
//		if(container == null) 
//			return;
//		
//		Queue<EObject> frontier = new LinkedList<>();
//			
//		frontier.addAll(container.eContents());
//		while(!frontier.isEmpty()) {
//			frontier = frontier.parallelStream().flatMap(child -> {
//				
//			}).collect(Collectors.toCollection(LinkedList::new));
//		}
//	}
}