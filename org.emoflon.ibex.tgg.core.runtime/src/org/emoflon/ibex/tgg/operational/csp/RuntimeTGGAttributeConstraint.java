package org.emoflon.ibex.tgg.operational.csp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import language.csp.definition.*;

public class RuntimeTGGAttributeConstraint {
	private static final char B = 'B';
	private static final char F = 'F';
	
	private boolean satisfied = false;
	
	private List<RuntimeTGGAttributeConstraintVariable> variables;
	
	
	public RuntimeTGGAttributeConstraint() {
		variables = new ArrayList<RuntimeTGGAttributeConstraintVariable>();
	}
	
	public String getBindingStates() {
		if (variables.size() == 0) {
			throw new IllegalArgumentException("Cannot determine binding states from an empty list of variables!");
		}
		char[] result = new char[variables.size()];
		for (int i = 0; i < variables.size(); i++) {
			result[i] = variables.get(i).isBound() ? B : F;
		}

		return String.valueOf(result);
	}
	
	public void setSatisfied(boolean value){
		satisfied = value;
	}
	
	public boolean isSatisfied() {
		return satisfied;
	}
 
	public List<RuntimeTGGAttributeConstraintVariable> getVariables() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TGGAttributeConstraintAdornment> getAllowedAdornments() {
		// TODO Auto-generated method stub
		return null;
	}
}
