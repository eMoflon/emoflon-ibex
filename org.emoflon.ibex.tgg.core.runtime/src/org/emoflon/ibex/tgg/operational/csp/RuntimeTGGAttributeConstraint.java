package org.emoflon.ibex.tgg.operational.csp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import language.basic.expressions.TGGAttributeExpression;
import language.csp.TGGAttributeConstraint;
import language.csp.definition.*;
import language.csp.impl.TGGAttributeVariableImpl;

public abstract class RuntimeTGGAttributeConstraint {
	private static final char B = 'B';
	private static final char F = 'F';
	
	private boolean satisfied = false;
	
	private TGGAttributeConstraint constraint;
	protected List<RuntimeTGGAttributeConstraintVariable> variables;
	
	
	public RuntimeTGGAttributeConstraint() {
		variables = new ArrayList<>();
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

	
	protected abstract void solve();
	
	public Collection<Pair<TGGAttributeExpression,Object>> getBoundAttrExprValues() {
		Collection<Pair<TGGAttributeExpression, Object>> tuples = new ArrayList<Pair<TGGAttributeExpression, Object>>(); 
		for(int i = 0; i < constraint.getParameters().size(); i++) {
			Object obj = constraint.getParameters().get(i);
			if (obj instanceof TGGAttributeExpression) {
				tuples.add(Pair.of((TGGAttributeExpression) obj, variables.get(i).getValue()));
			}
		}
		return tuples;
	}
	
	public void initialize(RuntimeTGGAttributeConstraintContainer cont, TGGAttributeConstraint constraint) {
		this.constraint = constraint;
	
		variables = constraint.getParameters().stream().map(p -> cont.params2runtimeVariable.get(p)).collect(Collectors.toList());
	}
}
