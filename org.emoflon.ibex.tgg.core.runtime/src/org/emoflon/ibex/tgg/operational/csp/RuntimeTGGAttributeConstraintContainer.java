package org.emoflon.ibex.tgg.operational.csp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.operational.csp.constraints.factories.RuntimeTGGAttrConstraintProvider;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;
import org.emoflon.ibex.tgg.util.String2EPrimitive;

import language.basic.expressions.TGGAttributeExpression;
import language.basic.expressions.TGGEnumExpression;
import language.basic.expressions.TGGLiteralExpression;
import language.basic.expressions.TGGParamValue;
import language.csp.TGGAttributeConstraint;
import language.csp.TGGAttributeConstraintLibrary;
import language.csp.TGGAttributeVariable;

public class RuntimeTGGAttributeConstraintContainer implements IRuntimeTGGAttrConstrContainer {

	private List<RuntimeTGGAttributeConstraint> constraints;
	private RuntimeTGGAttrConstraintProvider constraintProvider;
	protected Map<TGGParamValue, RuntimeTGGAttributeConstraintVariable> params2runtimeVariable = new HashMap<>();
	
	private IMatch match;
	private Collection<String> boundObjectNames;
		
	public RuntimeTGGAttributeConstraintContainer(TGGAttributeConstraintLibrary library, IMatch match, OperationalStrategy strategy, RuntimeTGGAttrConstraintProvider runtimeConstraintProvider) {
		this.match = match;
		this.boundObjectNames = match.parameterNames();
		this.constraintProvider = runtimeConstraintProvider;
		
		extractRuntimeParameters(library);
		extractRuntimeConstraints(library, strategy);
	}

	private void extractRuntimeConstraints(TGGAttributeConstraintLibrary library, OperationalStrategy strategy) {
		List<TGGAttributeConstraint> sortedSpecificationConstraints = strategy.getConstraints(library);
		constraints = sortedSpecificationConstraints.stream().map(c -> extractRuntimeConstraint(c)).collect(Collectors.toList());
	}
	
	private RuntimeTGGAttributeConstraint extractRuntimeConstraint(TGGAttributeConstraint c) {
		RuntimeTGGAttributeConstraint runtimeConstraint = constraintProvider.createRuntimeTGGAttributeConstraint(c.getDefinition().getName());
		runtimeConstraint.initialize(this, c);
		return runtimeConstraint;
	}

	private void extractRuntimeParameters(TGGAttributeConstraintLibrary library) {
		library.getParameterValues().stream().forEach(p -> params2runtimeVariable.put(p, new RuntimeTGGAttributeConstraintVariable(calculateBoundState(p), calculateValue(p), calculateType(p))));
	}
	
	private boolean calculateBoundState(TGGParamValue value) {
		if(value instanceof TGGAttributeExpression) {
			TGGAttributeExpression tae = (TGGAttributeExpression) value;
			return boundObjectNames.contains(tae.getObjectVar().getName());
		}
		if(value instanceof TGGAttributeVariable) {
			return false;
		}
		return true;
	}
	
	private Object calculateValue(TGGParamValue value) {
		if(value instanceof TGGAttributeExpression) {
			TGGAttributeExpression tae = (TGGAttributeExpression) value;
			String varName = tae.getObjectVar().getName();
			if(match.isInMatch(varName)) {
				EObject obj = (EObject) match.get(varName);
				return obj.eGet(tae.getAttribute());
			}
			
			return null;
		}
		if(value instanceof TGGLiteralExpression) {
			return extractLiteralValue((TGGLiteralExpression) value);
		}
		if(value instanceof TGGAttributeVariable) {
			return null;
		}
		if(value instanceof TGGEnumExpression) {
			return ((TGGEnumExpression) value).getLiteral();
		}
		throw new RuntimeException("TGGAttributeConstraintVariable value could not be recognized.");
	}
	
	private String calculateType(TGGParamValue value) {
		return value.getParameterDefinition().getType().getInstanceTypeName();
	}

	private Object extractLiteralValue(TGGLiteralExpression lExp) {
		return String2EPrimitive.convertString(lExp.getParameterDefinition().getType(), lExp.getValue());
	}

	public void addConstraint(RuntimeTGGAttributeConstraint constraint) {
		constraints.add(constraint);
	}
	
	
	public boolean solve(){
		for(RuntimeTGGAttributeConstraint constraint : constraints){
			constraint.solve();
			if(!constraint.isSatisfied())
				return false;
		}
		
		return true;
	}
	
	private Collection<Pair<TGGAttributeExpression, Object>> getBoundAttributeExpValues() {
		Collection<Pair<TGGAttributeExpression, Object>> col = constraints.stream().map(constraint -> constraint.getBoundAttrExprValues()).reduce(new ArrayList<Pair<TGGAttributeExpression, Object>>(), (a, b) -> a.addAll(b) ? a : a);
		return col == null ? new ArrayList<Pair<TGGAttributeExpression,Object>>() : col;
	}
	
	@Override
	public void applyCSPValues(IMatch comatch) {
		Collection<Pair<TGGAttributeExpression, Object>> cspValues = getBoundAttributeExpValues();

		for (Pair<TGGAttributeExpression, Object> cspVal : cspValues) {
			EObject entry = (EObject) comatch.get(cspVal.getLeft().getObjectVar().getName());
			entry.eSet(cspVal.getLeft().getAttribute(), cspVal.getRight());
		}
	}
}
