package org.emoflon.ibex.gt.state;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.emoflon.ibex.common.operational.HashUtil;
import org.emoflon.ibex.gt.StateModel.Parameter;
import org.emoflon.ibex.gt.StateModel.RuleState;
import org.emoflon.ibex.gt.StateModel.State;

public class StateID {
	
	public final State state;
	public final long hashCode;
	public final Map<String,Object> parameters;
	
	public StateID(final State state) {
		this.state = state;
		state.setHash(calculateHashCode(state));
		hashCode = state.getHash();
		if(state instanceof RuleState) {
			RuleState rState = (RuleState) state;
			parameters = extractParameterFromState(rState);
		} else {
			parameters = null;
		}
	}
	
	private  Map<String, Object> extractParameterFromState(RuleState state) {
		Map<String, Object> parameter = new HashMap<String,Object>();
		for(Parameter param: state.getParameters()) {
			parameter.put(param.getName(), param.getParameter());
		}
		return parameter;
	}
	
	@Override
	public int hashCode() {
		return (int) hashCode;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof StateID))
			return false;
		
		StateID other = (StateID)obj;
		if(hashCode != other.hashCode)
			return false;
		
		if(state.isInitial() != other.state.isInitial())
			return false;
		
		if(state.isInitial()) {
			return state.equals(other.state);
		}
		RuleState rState = (RuleState) state;
		RuleState rStateOther = (RuleState) other.state;
		
		if(rState.getParent().getHash() != rStateOther.getParent().getHash())
			return false;
		
		if(!rState.getRule().getName().equals(rStateOther.getRule().getName()))
			return false;
		
		if(!rState.getMatch().equals(rStateOther.getMatch()))
			return false;
		
		if((parameters == null || other.parameters == null) && parameters != other.parameters)
			return false;
		
		if(parameters == null && other.parameters == null)
			return true;
		
		if(parameters.size() != other.parameters.size())
			return false;
		
		for(String name : parameters.keySet()) {
			Object otherParam = other.parameters.get(name);
			if(otherParam == null)
				return false;
			
			Object param = parameters.get(name);
			if(!param.equals(otherParam))
				return false;
		}
		
		return true;
	}
	
	// Calculate hash based on current state and all prior states -> see crypto currency
	public static long calculateHashCode(final State state) {
		if(state instanceof RuleState) {
			RuleState rState = (RuleState)state;
			List<Object> components = new LinkedList<>();
			components.add(rState.getParent().getHash());
			return HashUtil.collectionToHash(components);
		}else {
			return HashUtil.objectToHash(state);
		}
	}
}
