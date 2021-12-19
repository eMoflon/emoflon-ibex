package org.emoflon.ibex.gt.state;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.common.emf.EMFManipulationUtils;
import org.emoflon.ibex.common.operational.IContextPatternInterpreter;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.common.operational.PushoutApproach;
import org.emoflon.ibex.common.operational.SimpleMatch;
import org.emoflon.ibex.gt.StateModel.AttributeDelta;
import org.emoflon.ibex.gt.StateModel.ComplexParameter;
import org.emoflon.ibex.gt.StateModel.IBeXMatch;
import org.emoflon.ibex.gt.StateModel.Link;
import org.emoflon.ibex.gt.StateModel.MatchDelta;
import org.emoflon.ibex.gt.StateModel.Parameter;
import org.emoflon.ibex.gt.StateModel.RuleState;
import org.emoflon.ibex.gt.StateModel.SimpleParameter;
import org.emoflon.ibex.gt.StateModel.State;
import org.emoflon.ibex.gt.StateModel.StateContainer;
import org.emoflon.ibex.gt.StateModel.StateModelFactory;
import org.emoflon.ibex.gt.StateModel.StructuralDelta;
import org.emoflon.ibex.gt.StateModel.Value;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeAssignment;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCreatePattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDeletePattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEdge;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule;
import org.graphstream.util.parser.ParseException;

import com.google.common.collect.Lists;

public class ModelStateManager {
	
	private Resource model;
	private Resource trashResource;
	private ModelStateDeleteAdapter adapter;
	private IContextPatternInterpreter engine;
	private boolean forceNewStates;
	private StateModelFactory factory = StateModelFactory.eINSTANCE;
	private StateContainer modelStates; 
	private State currentState;
	private Map<StateID, State> allStates;
	private Map<State, BiFunction<Map<String,Object>, Boolean, Optional<IMatch>>> gtApply;
	private Map<State, Map<String, Collection<IMatch>>> matches;
	private HashMap<String,Collection<IBeXMatch>> initialMatches;
	private int addStateAction = -1;
	
	public ModelStateManager(final Resource model, final Resource trashResource, final IContextPatternInterpreter engine, Map<String, Collection<IMatch>> matches, boolean forceNewStates, boolean loaded) {
		this.model = model;
		this.trashResource = trashResource;
		this.engine = engine;
		this.forceNewStates = forceNewStates;
		init();
		if(!loaded)
			;
			initMatches(matches); 
	}
	
	public StateContainer getModelStates() {
		return modelStates;
	}

	public Resource getModel() {
		return model;
	}
	
	public HashMap<String, Collection<IBeXMatch>> getInitialMatches() {
		return initialMatches;
	}
	
	public State getCurrentState() {
		return currentState;
	}
	
	public IBeXMatch IMatchToIBeXMatch(IMatch imatch) {
		IBeXMatch ibexmatch = factory.createIBeXMatch();
		
		for(String paramName : imatch.getParameterNames()) {
			ComplexParameter param = factory.createComplexParameter();
			param.setName(paramName);
			param.setValue((EObject) imatch.get(paramName));
			ibexmatch.getParameters().add(param);
		}
		
		ibexmatch.setPatternName(imatch.getPatternName());
		return ibexmatch;
	}
	
	public IMatch IBeXMatchToIMatchByList(RuleState state) {
		Collection<IMatch> matchesForPattern =  matches.get(state).get(state.getMatch().getPatternName());
		Map<String, Parameter> parameter =  extractParameterFromMatch(state.getMatch());
		
		for(IMatch imatch : matchesForPattern) {
			if(imatch.getObjects().containsAll(parameter.values().stream()
					.filter(param -> param instanceof ComplexParameter)
					.map(param->((ComplexParameter)param).getValue())
					.collect(Collectors.toSet())))
				return imatch;
		}
		
		return null;
	}
	
	public IMatch IBeXMatchToIMatch(IBeXMatch ibexmatch) {
		IMatch imatch = new SimpleMatch(ibexmatch.getPatternName());
		Map<String, Parameter> extractedParameter = extractParameterFromMatch(ibexmatch);
		for(String paramName : extractedParameter.keySet()) {
			Parameter param = extractedParameter.get(paramName);
			if(param instanceof SimpleParameter)
				continue;
			
			imatch.put(paramName, ((ComplexParameter)param).getValue());
		}
		return imatch;
	}
	
	public Map<String, Object> extractParameterFromState(RuleState state) {
		Map<String, Object> parameter = new HashMap<>();
		for(Parameter param: state.getParameters()) {
			if(param instanceof ComplexParameter) {
				parameter.put(param.getName(), ((ComplexParameter)param).getValue());
			} else {
				SimpleParameter pParam = (SimpleParameter)param;
				Object value = null;
				try {
					value = Integer.parseInt(pParam.getValue());
				} catch (Exception e) {
					try {
						value = Double.parseDouble(pParam.getValue());
					} catch (Exception e1) {
						try {
							EPackage ePack = model.getContents().iterator().next().eClass().getEPackage();
							EFactory factory = ePack.getEFactoryInstance();
							for(EEnum enm : ePack.getEClassifiers().stream().filter(cls -> cls instanceof EEnum).map(enm -> (EEnum)enm).collect(Collectors.toList())) {
								try {
									value = factory.createFromString(enm, pParam.getValue());
									break;
								} catch(Exception e2) {}
							}
							if(value == null)
								throw new ParseException();
						} catch(Exception e2) {
							if(pParam.getValue().equalsIgnoreCase("true")) {
								value = true;
							} else if(pParam.getValue().equalsIgnoreCase("false")) {
								value = false;
							} else {
								value = pParam.getValue();
							}
						}
						
					}
				}
				parameter.put(param.getName(), value);
			}
			
		}
		return parameter;
	}
	
	public boolean testIfSameIBeXMatch(IBeXMatch first, IBeXMatch second) {
		if(!(first.getPatternName().equals(second.getPatternName())))
			return false;
		else {
			for(Parameter paramFirst : first.getParameters()) {
				for(Parameter paramSecond : second.getParameters()) {
					if(paramFirst instanceof ComplexParameter && paramSecond instanceof ComplexParameter) {
						ComplexParameter fP = (ComplexParameter)paramFirst;
						ComplexParameter sP = (ComplexParameter)paramSecond;
						if(paramFirst.getName().equals(paramSecond.getName()) && !(fP.getValue().equals(sP.getValue()))) {
							return false;
						}
					} else if(paramFirst instanceof SimpleParameter && paramSecond instanceof SimpleParameter) {
						SimpleParameter fP = (SimpleParameter)paramFirst;
						SimpleParameter sP = (SimpleParameter)paramSecond;
						if(paramFirst.getName().equals(paramSecond.getName()) && !(fP.getValue().equals(sP.getValue()))) {
							return false;
						}
					} else {
						if(paramFirst.getName().equals(paramSecond.getName())) {
							return false;
						}
					}
					
				}
			}
			return true;
		}
		
		
	}
	
	public Optional<IMatch> addNewState(final IBeXRule rule, final IMatch match, final Map<String, Object> parameter,final Map<String, Collection<IMatch>> matches, boolean doUpdate, PushoutApproach po,
			Map<String, Collection<IMatch>> addedMatches, Map<String, Collection<IMatch>> removedMatches, BiFunction<Map<String,Object>, Boolean, Optional<IMatch>> applyRule) {
		RuleState newState = factory.createRuleState();
	
		newState.setMatchCount(matches.get(match.getPatternName()).size());

		addDeltaMatches(newState, addedMatches, removedMatches); 
		
		newState.setInitial(false);
		newState.setMatch(IMatchToIBeXMatch(match));
		newState.setRule(rule);
		newState.setPushoutApproach(po.ordinal());
		addParameterToState(newState, parameter);
		newState.setParent(currentState);
		currentState.getChildren().add(newState);
		
		if(!forceNewStates) {			
			StateID id = new StateID(newState);
			State existingState = allStates.get(id);
			if(existingState == null) {
				allStates.put(id, newState);
			} else {
				newState.setParent(null);
				currentState.getChildren().remove(newState);
				return moveToState(existingState, doUpdate);
			}
		}
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
		int currentAction = ++addStateAction;
		adapter.clear(currentAction);
		adapter.addNodesToWatch(nodesToWatch, currentAction);
		adapter.setActive();
		
		
		// Let the rule play out
		// While using autoApply method this recursive call leads to an error
		// To avoid this, set doUpdate to false manually or use normal apply method
		// Error description: states are generated in parallel not in consecutive order because 
		// "modelStates.getStates().add(newState);"
		// "currentState = newState;"
		// is never reached
		Optional<IMatch> optComatch = applyRule.apply(parameter, doUpdate);
		if(!optComatch.isPresent()) {
			newState.setParent(null);
			currentState.getChildren().remove(newState);
			return optComatch;
		}

		gtApply.put(newState, applyRule);
		
		IMatch comatch = optComatch.get();
		newState.setCoMatch(IMatchToIBeXMatch(comatch));
		
		modelStates.getStates().add(newState);
		currentState = newState;
		
		// Store attribute assignments for created nodes
		rule.getCreate().getAttributeAssignments().stream()
			.forEach(asgn -> {
				newState.getAttributeDeltas().add(completeAttributeDelta(comatch, asgn, attributeDeltas.get(asgn)));
			});
		
		// Store created and deleted nodes/edges
		StructuralDelta delta = factory.createStructuralDelta();
		if(addDeleteDelta(delta, match, rule.getDelete()) | addCreateDelta(delta, comatch, rule.getCreate()) ) {
			newState.setStructuralDelta(delta);
		}
		
		if(adapter.getRemovedLinks(currentAction).size()>0) {
			delta.getDeletedLinks().addAll(adapter.getRemovedLinks(currentAction));
			
			if(newState.getStructuralDelta() == null) {
				newState.setStructuralDelta(delta);
			}
		}
		// Maybe wrong but need to avoid that structuralDelta is null
		if(newState.getStructuralDelta() == null) {
			newState.setStructuralDelta(delta);
		}
		
		delta.getResource2EObjectContainment().addAll(nodesInResource);
		
		adapter.setInactive();
		adapter.clear(currentAction);
		
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
				if(current.getType().getUpperBound()<0 || current.getType().getUpperBound() > 1) {
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
			.forEach(atrDelta -> {atrDelta.getObject().eSet(atrDelta.getAttribute(), getObjectFromValue(atrDelta.getOldValue()));
			});
		currentState = previousState;
		
		if(currentState instanceof RuleState) {
			return Optional.of(IBeXMatchToIMatch(((RuleState)currentState).getCoMatch()));
		} else {
			return Optional.empty();
		}
	}
	
	public Optional<IMatch> moveToState(final State targetState, boolean update) {
		if(!forceNewStates) {
			StateID id = new StateID(targetState);
			if(!allStates.containsKey(id)) {
				return Optional.empty();
			}
		} else {
			if(!modelStates.getStates().contains(targetState)) {
				return Optional.empty();
			}
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
		
		if(update) {
			engine.updateMatches();
		}
		
		if(currentState instanceof RuleState) {
			return Optional.of(IBeXMatchToIMatch(((RuleState)currentState).getCoMatch()));
		} else {
			return Optional.empty();
		}
	}
	
	public Object getObjectFromValue(Value val) {
		Object obj = null;
		try {
			obj = stringToValue(factory, val.getType(), val.getValueAsString());
		} catch (Exception e) {
			
		}
		// Null check missing in calling methods 
		return obj;
	}
	
	protected void setCurrentState(State curr) {
		currentState = curr;
	}
	
	protected void setAllStates(Map<StateID, State> allStates) {
		this.allStates = allStates;
	}
	
	protected void setGTApply(Map<State, BiFunction<Map<String,Object>, Boolean, Optional<IMatch>>> gtApply) {
		this.gtApply = gtApply;
	}
	
	private void init() {
		
		gtApply = new HashMap<>();
		allStates = new HashMap<>();
		matches = new HashMap<>();
		
		modelStates = factory.createStateContainer();
		State initialState = factory.createState();
		modelStates.getStates().add(initialState);
		modelStates.setInitialState(initialState);
		initialState.setInitial(true);
		if(!forceNewStates) {
			StateID id = new StateID(initialState);
			allStates.put(id, initialState);
		}
		currentState = initialState;
		adapter = new ModelStateDeleteAdapter(model);
	}
	
	private void initMatches(Map<String, Collection<IMatch>> matches) {
		initialMatches = new HashMap<String,Collection<IBeXMatch>>();
		for(String pattern : matches.keySet()) {
			Collection<IBeXMatch> matchToPattern = new LinkedList<IBeXMatch>();
			for(IMatch match : matches.get(pattern)) {
				matchToPattern.add(IMatchToIBeXMatch(match));
			}
			initialMatches.put(pattern, matchToPattern);
			modelStates.getInitialMatches().addAll(matchToPattern);
		}
	}
	
	private void addParameterToState(RuleState state, Map<String, Object> parameter) {
		for(String key: parameter.keySet()) {
			Object parameterValue = parameter.get(key);
			if(parameterValue instanceof EObject) {
				ComplexParameter param = factory.createComplexParameter();
				param.setName(key);
				param.setValue((EObject) parameter.get(key));
				state.getParameters().add(param);
			} else {
				SimpleParameter param = factory.createSimpleParameter();
				param.setName(key);
				param.setValue(String.valueOf(parameterValue));
				state.getParameters().add(param);
			}
			
		}
	}
	

	
	private  Map<String, Parameter> extractParameterFromMatch(IBeXMatch match) {
		Map<String, Parameter> parameter = new HashMap<>();
		for(Parameter param: match.getParameters()) {
			parameter.put(param.getName(), param);
		}
		return parameter;
	}

	private void addDeltaMatches(RuleState state, Map<String, Collection<IMatch>> addedMatches, Map<String, Collection<IMatch>> removedMatches) {
		for(String pattern : addedMatches.keySet()) {
			MatchDelta createdMatches = factory.createMatchDelta();
			createdMatches.setPatternName(pattern);
			for(IMatch match : addedMatches.get(pattern)) {
					createdMatches.getMatchDeltasForPattern().add(IMatchToIBeXMatch(match));
				
			}
			state.getCreatedMatches().add(createdMatches);
		}
		for(String pattern : removedMatches.keySet()) {
			MatchDelta deletedMatches = factory.createMatchDelta();
			deletedMatches.setPatternName(pattern);
			for(IMatch match : removedMatches.get(pattern)) {
				deletedMatches.getMatchDeltasForPattern().add(IMatchToIBeXMatch(match));
			}
			state.getDeletedMatches().add(deletedMatches);
		}
		
		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Optional<IMatch> moveToChildState(final State childState) {
		if(!currentState.getChildren().contains(childState)) {
			return Optional.empty();
		}
		
		// Let the rule play out again
		RuleState rs = (RuleState)childState;
		
		Optional<IMatch> optComatch = gtApply.get(childState).apply(extractParameterFromState(rs), false);
		if(!optComatch.isPresent())
			return optComatch;
		
		// Fix creation of new nodes -> otherwise new nodes will not be identical to nodes created in previous iterations
		StructuralDelta delta = rs.getStructuralDelta();
		if(delta != null) {
			IMatch comatch = optComatch.get();
			
			// Delete new nodes that would differ from previously created nodes
			Set<EObject> removals = new HashSet<>();
			rs.getRule().getCreate().getCreatedNodes().forEach(node -> {
				removals.add((EObject) comatch.get(node.getName()));
			});
			
			if(engine.getProperties().needs_trash_resource()) 
				EMFManipulationUtils.delete(removals, new HashSet(),
						node -> trashResource.getContents().add(EcoreUtil.getRootContainer(node)), false);
			else
				EMFManipulationUtils.delete(removals, new HashSet(), false);
			IMatch trueComatch = IBeXMatchToIMatch(rs.getCoMatch());
			// Place all originally created new nodes into resource
			rs.getRule().getCreate().getCreatedNodes().forEach(node -> {
				model.getContents().add((EObject) trueComatch.get(node.getName()));
			});
			
			// Redirect orignal edges and restore O.G. containment
			rs.getRule().getCreate().getCreatedEdges().forEach(edge -> {
				EObject src = (EObject) trueComatch.get(edge.getSourceNode().getName());
				EObject trg = (EObject) trueComatch.get(edge.getTargetNode().getName());
				if(edge.getType().getUpperBound()<0 || edge.getType().getUpperBound() > 1) {
					List<EObject> refs = (List<EObject>)src.eGet(edge.getType());
					refs.add(trg);
				} else {
					src.eSet(edge.getType(), trg);
				}
			});
		}
		
		// Check and fix attribute values

		rs.getAttributeDeltas().stream()
			.filter(atrDelta -> atrDelta.getObject().eGet(atrDelta.getAttribute()) != getObjectFromValue(atrDelta.getNewValue()))
			.filter(atrDelta -> atrDelta.getObject().eGet(atrDelta.getAttribute()) != getObjectFromValue(atrDelta.getNewValue()))
			.forEach(atrDelta -> {atrDelta.getObject().eSet(atrDelta.getAttribute(), getObjectFromValue(atrDelta.getNewValue()));
				
			});
		
		currentState = childState;
		
		if(currentState instanceof RuleState) {
			return Optional.of(IBeXMatchToIMatch(((RuleState)currentState).getCoMatch()));
		} else {
			return Optional.empty();
		}
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
			
		if(trgState.equals(commonRoot))
			return path;
		
		LinkedList<State> trg2commonRoot = new LinkedList<>();
		current = trgState;
		while(!current.equals(commonRoot)) {
			RuleState currentTrg = (RuleState) current;
			current = currentTrg.getParent();
			trg2commonRoot.add(current);
		}
		// remove root
		if(!trg2commonRoot.isEmpty())
			trg2commonRoot.removeLast();
		// invert order
		trg2commonRoot = new LinkedList<>(Lists.reverse(trg2commonRoot));
		path.addAll(trg2commonRoot);
		path.add(trgState);
		
		return path;
	}
	
	private AttributeDelta createAttributeDelta(final IMatch match, final IBeXAttributeAssignment assignment) {
		AttributeDelta delta = factory.createAttributeDelta();
		Value oldVal = factory.createValue();
		
		delta.setAttribute(assignment.getType());
		EObject node = (EObject) match.get(assignment.getNode().getName());
		delta.setObject(node);

		EDataType type = assignment.getType().getEAttributeType();
		oldVal.setType(type);
		try {
			oldVal.setValueAsString(valueToString(factory, type, node.eGet(assignment.getType())));
		} catch (IOException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
		
		delta.setOldValue(oldVal);			
		return delta;
	}
	
	private AttributeDelta completeAttributeDelta(final IMatch match, final IBeXAttributeAssignment assignment, AttributeDelta delta) {
		if(delta == null) {
			delta = createAttributeDelta(match, assignment);
		}
		
		Value newVal = factory.createValue();
	
		EObject node = (EObject) match.get(assignment.getNode().getName());
		EDataType type = assignment.getType().getEAttributeType();
		newVal.setType(type);
		try {
			newVal.setValueAsString(valueToString(factory, type, node.eGet(assignment.getType())));
		} catch (IOException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}

		delta.setNewValue(newVal);			
		
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

	private static Object stringToValue(final EFactory factory, final EDataType atr, final String value) throws Exception {
		EcorePackage epack = EcorePackage.eINSTANCE;
		EFactory efac = epack.getEFactoryInstance();
		if(atr == epack.getEString()) {
			return value;
		} else if(atr == epack.getEBoolean()) {
			return ("true".equals(value)) ? true : false;
		} else if(atr == epack.getEByte()) {
			return Byte.parseByte(value);
		} else if(atr == epack.getEChar()) {
			return value.charAt(0);
		} else if(atr == epack.getEDate()) {
			return efac.createFromString(atr, value);
		} else if(atr == epack.getEDouble()) {
			return Double.parseDouble(value);
		}  else if(atr == epack.getEFloat()) {
			return Float.parseFloat(value);
		} else if(atr == epack.getEInt()) {
			return Integer.parseInt(value);
		} else if(atr == epack.getELong()) {
			return Long.parseLong(value);
		} else if(atr == epack.getEShort()) {
			return Short.parseShort(value);
		} else {
			EFactory objectFactory = atr.getEPackage().getEFactoryInstance();
			return objectFactory.createFromString(atr, value);
		}
	}
	
	private static String valueToString(final EFactory factory, final EDataType atr, final Object value) throws IOException {
		EcorePackage epack = EcorePackage.eINSTANCE;
		EFactory efac = epack.getEFactoryInstance();
		if(atr == epack.getEString()) {
			return (String) value;
		} else if(atr == epack.getEBoolean()) {
			return String.valueOf(value);
		} else if(atr == epack.getEByte()) {
			return String.valueOf(value);
		} else if(atr == epack.getEChar()) {
			return String.valueOf(value);
		} else if(atr == epack.getEDate()) {
			return efac.convertToString(atr, value);
		} else if(atr == epack.getEDouble()) {
			return String.valueOf(value);
		}  else if(atr == epack.getEFloat()) {
			return String.valueOf(value);
		} else if(atr == epack.getEInt()) {
			return String.valueOf(value);
		} else if(atr == epack.getELong()) {
			return String.valueOf(value);
		} else if(atr == epack.getEShort()) {
			return String.valueOf(value);
		} else if(atr == epack.getEFeatureMapEntry()) {
			return null;
		}else {
			EFactory objectFactory = atr.getEPackage().getEFactoryInstance();
			return objectFactory.convertToString(atr, value);
		}
	}
}