package org.emoflon.ibex.tgg.operational.csp;

import java.util.List;

import language.csp.definition.*;

public class RuntimeTGGAttributeConstraint {
	private static final char B = 'B';
	private static final char F = 'F';
	
	private boolean satisfied = false;
	
	public String getBindingStates(RuntimeTGGAttributeConstraintVariable... variables) {
		if (variables.length == 0) {
			throw new IllegalArgumentException("Cannot determine binding states from an empty list of variables!");
		}
		char[] result = new char[variables.length];
		for (int i = 0; i < variables.length; i++) {
			result[i] = variables[i].isBound() ? B : F;
		}

		return String.valueOf(result);
	}
	
	public void setSatisfied(boolean value){
		satisfied = value;
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
