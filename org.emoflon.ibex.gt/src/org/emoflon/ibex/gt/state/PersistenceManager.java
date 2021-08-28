package org.emoflon.ibex.gt.state;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.emoflon.ibex.common.operational.IContextPatternInterpreter;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.common.operational.PushoutApproach;
import org.emoflon.ibex.gt.StateModel.Link;
import org.emoflon.ibex.gt.StateModel.RuleState;
import org.emoflon.ibex.gt.StateModel.State;
import org.emoflon.ibex.gt.StateModel.StateContainer;
import org.emoflon.ibex.gt.engine.GraphTransformationInterpreter;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule;

public abstract class PersistenceManager {
	
	public synchronized static void saveModelAndStates(Resource simModel, StateContainer states, String path) throws Exception {
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("SimulationModel", new XMIResourceFactoryImpl());
		ResourceSet rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		
		URI uri = URI.createFileURI(path);
		Resource modelResource = rs.createResource(uri);
//		simModel.getContents()
//		for(EObject modelObj : simModel.getContents()) {
//			modelResource.getContents().add(modelObj);
//		}
		
		
		
		modelResource.getContents().addAll(simModel.getContents());
		modelResource.getContents().add(states);
//		modelResource.getContents().add(states.getInitialState());
		for(State state : states.getStates()) {
			RuleState ruleState = (RuleState) state;
			modelResource.getContents().addAll(ruleState.getStructuralDelta().getCreatedObjects());
			modelResource.getContents().addAll(ruleState.getStructuralDelta().getDeletedObjects());
//			for(Link link : ruleState.getStructuralDelta().getCreatedLinks()) {
//				modelResource.getContents().add(link.getSrc());
//				modelResource.getContents().add(link.getTrg());
//			
//			}
//			modelResource.getContents().addAll(ruleState.getStructuralDelta().getCreatedLinks());
//			modelResource.getContents().add(state);
		}
		
		
//		modelResource.getContents().addAll(states.getStates());
//		modelResource.getContents().add(states);

		
		Map<Object, Object> saveOptions = ((XMIResource)modelResource).getDefaultSaveOptions();
		saveOptions.put(XMIResource.OPTION_ENCODING,"UTF-8");
		saveOptions.put(XMIResource.OPTION_USE_XMI_TYPE, Boolean.TRUE);
		saveOptions.put(XMIResource.OPTION_SAVE_TYPE_INFORMATION,Boolean.TRUE);
		saveOptions.put(XMIResource.OPTION_SCHEMA_LOCATION_IMPLEMENTATION, Boolean.TRUE);
		
		((XMIResource)modelResource).save(saveOptions);
		System.out.println("Model saved to: "+uri.path());
	}

	public synchronized static ModelStateManager loadModelAndStates(Resource model, Resource trashresource, IContextPatternInterpreter contextPatternInterpreter, GraphTransformationInterpreter graphTransformationInterpreter, String path) throws Exception {
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		ResourceSet rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		rs.setPackageRegistry(EPackage.Registry.INSTANCE);
		URI uri = URI.createFileURI(path);
		Resource modelResource = rs.getResource(uri, true);
		if(modelResource == null)
			throw new IOException("File did not contain a vaild model.");
		Resource simModel = new ResourceImpl();
		
		int i=0;
		while(!(modelResource.getContents().get(0) instanceof StateContainer)) {
			simModel.getContents().add(modelResource.getContents().get(i));
		}
		
		
		
		
		ModelStateManager stateManager = new ModelStateManager(simModel, trashresource, contextPatternInterpreter, graphTransformationInterpreter.getMatches(), false, true);
		

		stateManager.getModelStates().getStates().addAll(((StateContainer) modelResource.getContents().get(0)).getStates());
		stateManager.getModelStates().setInitialState(((StateContainer) modelResource.getContents().get(0)).getInitialState());
		
	
		
//		modelResource.getContents().stream()
//		.filter(obj -> obj instanceof State)
//		.forEach(obj -> {states.add((State) obj);
//		});
//		
		for(State state : stateManager.getModelStates().getStates()) {	
		
			RuleState rState = (RuleState) state;
			String ruleName = rState.getMatch().getPatternName();
			for(IBeXRule rule : graphTransformationInterpreter.getRuleSet().getRules()) {
				if(ruleName.equals(rule.getName())) {
					rState.setRule(rule);
					break;
				}
				
			}
		}
		


		stateManager.setCurrentState(stateManager.getModelStates().getInitialState());
	
		Map<StateID, State> allStates = new HashMap<>();
		allStates.put(new StateID(stateManager.getModelStates().getInitialState()), stateManager.getModelStates().getInitialState());
		for(State state : stateManager.getModelStates().getStates()) {	
			StateID id = new StateID(state);
			allStates.put(id, state);
		}
		
		stateManager.setAllStates(allStates);
		
		
		
//		Map<State, Map<String, Collection<IMatch>>> matches = new HashMap<>();
//		Map<String, Collection<IMatch>> matchesPerState = new HashMap<>();
//		for(State state : states) {	
//			if(!state.isInitial()) {
//				RuleState rState = (RuleState) state;
//				for(IBeXContext pattern : graphTransformationInterpreter.getPatternSet().getContextPatterns()) {
//					matchesPerState.put(pattern.getName(), graphTransformationInterpreter.matchStream(pattern.getName(), stateManager.extractParameterFromState(rState) , false).toList());
//				}
//				matches.put(state, matchesPerState);
//			}
//		}
//		stateManager.setMatches(matches);
		
		Map<State, BiFunction<Map<String,Object>, Boolean, Optional<IMatch>>> gtApply = new HashMap<>();
		for(State state : stateManager.getModelStates().getStates()) {	
			RuleState rState = (RuleState) state;
			gtApply.put(state, graphTransformationInterpreter.getApplyInternalPointer(stateManager.IBeXMatchToIMatch(rState.getMatch()), PushoutApproach.values()[rState.getPushoutApproach()], stateManager.extractParameterFromState(rState), false));
		}
		stateManager.setGTApply(gtApply);
		
//		graphTransformationInterpreter.getModel().getResources().set(0, simModel);
		
		return stateManager;
		
	}
	
}
