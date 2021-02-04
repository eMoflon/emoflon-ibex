package org.emoflon.ibex.gt.state;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EObject;
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

public class ModelStateTracker {
	private StateModelFactory factory = StateModelFactory.eINSTANCE;
	private StateContainer modelStates;
	private State currentState;
	
	public ModelStateTracker() {
		init();
	}
	
	private void init() {
		modelStates = factory.createStateContainer();
		State initialState = factory.createState();
		modelStates.getStates().add(initialState);
		modelStates.setInitialState(initialState);
		initialState.setInitial(true);
		initialState.setHash(recalculateHash(initialState));
		currentState = initialState;
	}
	
	public Optional<IMatch> addNewState(final IBeXRule rule, final IMatch match, Supplier<Optional<IMatch>> applyRule) {
		RuleState newState = factory.createRuleState();
		
		newState.setInitial(false);
		newState.setMatch(match);
		newState.setRule(rule);
		Map<IBeXAttributeAssignment, AttributeDelta> attributeDeltas = new HashMap<>();
		rule.getCreate().getAttributeAssignments().stream()
			.filter(asgn -> match.isInMatch(asgn.getNode().getName()))
			.forEach(asgn -> attributeDeltas.put(asgn, createAttributeDelta(match, asgn)));

		Optional<IMatch> optComatch = applyRule.get();
		if(!optComatch.isPresent())
			return optComatch;
		
		IMatch comatch = optComatch.get();
		modelStates.getStates().add(newState);
		newState.setParent(currentState);
		currentState = newState;
		
		rule.getCreate().getAttributeAssignments().stream()
			.forEach(asgn -> {
				newState.getAttributeDeltas().add(completeAttributeDelta(comatch, asgn, attributeDeltas.get(asgn)));
			});
		
		StructuralDelta delta = factory.createStructuralDelta();
		if(addCreateDelta(delta, comatch, rule.getCreate()) || addDeleteDelta(delta, match, rule.getDelete())) {
			newState.setStructuralDelta(delta);
		}
		
		// Calculate hash based on current state and all prior states -> see crypto currency
		newState.setHash(recalculateHash(newState));
		
		return optComatch;
	}
	
	public AttributeDelta createAttributeDelta(final IMatch match, final IBeXAttributeAssignment assignment) {
		AttributeDelta delta = factory.createAttributeDelta();
		
		delta.setAttribute(assignment.getType());
		EObject node = (EObject) match.get(assignment.getNode().getName());
		delta.setObject(node);
		delta.setOldValue(node.eGet(assignment.getType()));
		
		return delta;
	}
	
	public AttributeDelta completeAttributeDelta(final IMatch match, final IBeXAttributeAssignment assignment, AttributeDelta delta) {
		if(delta == null) {
			delta = createAttributeDelta(match, assignment);
		}
		
		EObject node = (EObject) match.get(assignment.getNode().getName());
		delta.setNewValue(node.eGet(assignment.getType()));
		
		return delta;
	}
	
	public boolean addCreateDelta(StructuralDelta delta, final IMatch match, final IBeXCreatePattern pattern) {
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
	
	public boolean addDeleteDelta(StructuralDelta delta, final IMatch match, final IBeXDeletePattern pattern) {
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
	
	public long recalculateHash(State state) {
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