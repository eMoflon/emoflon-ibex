package org.emoflon.ibex.tgg.operational.csp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import language.csp.definition.*;

public abstract class RuntimeTGGAttributeConstraint {
	private static final char B = 'B';
	private static final char F = 'F';
	
	private boolean satisfied = false;
	
	protected List<RuntimeTGGAttributeConstraintVariable> variables;
	
	private List<TGGAttributeConstraintAdornment> allowedAdorments;
	
	public RuntimeTGGAttributeConstraint() {
		variables = new ArrayList<>();
		allowedAdorments = new ArrayList<>();
	}
	
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
	
	public boolean isSatisfied() {
		return satisfied;
	}
 
	public List<RuntimeTGGAttributeConstraintVariable> getVariables() {
		return variables;
	}

	public List<TGGAttributeConstraintAdornment> getAllowedAdornments() {
		return allowedAdorments;
	}
	
	protected abstract void solve();
}
