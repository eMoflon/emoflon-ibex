package org.emoflon.ibex.tgg.runtime.csp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeValue;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraint;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintBinding;
import org.emoflon.ibex.tgg.util.LoremIpsum;

public abstract class RuntimeTGGAttributeConstraint {
	private static final char B = 'B';
	private static final char F = 'F';

	private boolean satisfied = false;
	protected List<RuntimeTGGAttributeConstraintVariable> variables;
	private TGGAttributeConstraint constraint;

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

	public void setSatisfied(boolean value) {
		satisfied = value;
	}

	public boolean isSatisfied() {
		return satisfied;
	}

	public void initialize(RuntimeTGGAttributeConstraintContainer cont, TGGAttributeConstraint constraint) {
		this.constraint = constraint;

		variables = constraint.getParameters().stream().map(p -> cont.params2runtimeVariable.get(p))
				.collect(Collectors.toList());
	}
	
	public List<RuntimeTGGAttributeConstraintVariable> getVariables() {
		return variables;
	}

	public abstract void solve();

	public Collection<Pair<IBeXAttributeValue, Object>> getBoundAttrExprValues() {
		Collection<Pair<IBeXAttributeValue, Object>> tuples = new ArrayList<Pair<IBeXAttributeValue, Object>>();
		for (int i = 0; i < constraint.getParameters().size(); i++) {
			Object obj = constraint.getParameters().get(i);
			if (obj instanceof IBeXAttributeValue attrExpr) {
				tuples.add(Pair.of(attrExpr, variables.get(i).getValue()));
			}
		}
		return tuples;
	}

	public static Object generateValue(String type) {
		if (type.equals("java.lang.String"))
			return LoremIpsum.getInstance().randomWord();
		if (type.equals("int") || type.equals("java.lang.Integer"))
			return Integer.valueOf((int) (Math.random() * 1000.0));
		if (type.equals("java.lang.Long"))
			return Long.valueOf((long) (Math.random() * 1000));
		if (type.equals("java.lang.Double"))
			return Double.valueOf((Math.random() * 1000.0));
		if (type.equals("java.lang.Float"))
			return Float.valueOf((float) (Math.random() * 1000.0));
		if (type.equals("java.lang.Char"))
			return (char) ((new Random()).nextInt(26) + 'a');
		if (type.equals("java.lang.Boolean"))
			return Math.random() > 0.5;
		if(type.equals("java.util.Date"))
			return new Date((long)(Math.random() * Long.MAX_VALUE));

		throw new RuntimeException("The type " + type + " is not supported for random value generation");

	}

	public List<TGGAttributeConstraintBinding> getAllowedAdornments(boolean isModelGen) {
		if(isModelGen)
			return constraint.getDefinition().getGenBindings();
		else
			return constraint.getDefinition().getSyncBindings();
	}
}
