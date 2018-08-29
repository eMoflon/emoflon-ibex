package org.emoflon.ibex.tgg.operational.csp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.operational.csp.constraints.factories.RuntimeTGGAttrConstraintProvider;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.util.String2EPrimitive;

import language.TGGAttributeConstraint;
import language.TGGAttributeExpression;
import language.TGGAttributeVariable;
import language.TGGEnumExpression;
import language.TGGLiteralExpression;
import language.TGGParamValue;

public class RuntimeTGGAttributeConstraintContainer implements IRuntimeTGGAttrConstrContainer {

	private List<RuntimeTGGAttributeConstraint> constraints;
	private RuntimeTGGAttrConstraintProvider constraintProvider;
	protected Map<TGGParamValue, RuntimeTGGAttributeConstraintVariable> params2runtimeVariable = new HashMap<>();
	
	private IMatch match;
	private Collection<String> boundObjectNames;
		
	public RuntimeTGGAttributeConstraintContainer(List<TGGParamValue> variables, List<TGGAttributeConstraint> sortedConstraints, IMatch match, RuntimeTGGAttrConstraintProvider runtimeConstraintProvider) {
		this.match = match;
		this.boundObjectNames = match.getParameterNames();
		this.constraintProvider = runtimeConstraintProvider;
		
		extractRuntimeParameters(variables);
		extractRuntimeConstraints(sortedConstraints);
	}

	private void extractRuntimeConstraints(List<TGGAttributeConstraint> sortedConstraints) {
		constraints = sortedConstraints.stream().map(c -> extractRuntimeConstraint(c)).collect(Collectors.toList());
	}
	
	private RuntimeTGGAttributeConstraint extractRuntimeConstraint(TGGAttributeConstraint c) {
		RuntimeTGGAttributeConstraint runtimeConstraint = constraintProvider.createRuntimeTGGAttributeConstraint(c.getDefinition().getName());
		runtimeConstraint.initialize(this, c);
		return runtimeConstraint;
	}

	private void extractRuntimeParameters(List<TGGParamValue> variables) {
		variables.forEach(p -> params2runtimeVariable.put(p, new RuntimeTGGAttributeConstraintVariable(calculateBoundState(p), calculateValue(p), calculateType(p))));
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
		return String2EPrimitive.convertLiteral(lExp.getValue(), lExp.getParameterDefinition().getType());
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
	
	//FIXME:  Code is hard to read, avoid pairs
	private Collection<Pair<TGGAttributeExpression, Object>> getBoundAttributeExpValues() {
		Collection<Pair<TGGAttributeExpression, Object>> col = constraints.stream()
				.map(constraint -> constraint.getBoundAttrExprValues())
				.reduce(new ArrayList<Pair<TGGAttributeExpression, Object>>(), (a, b) -> a.addAll(b) ? a : a);
		return col == null ? new ArrayList<Pair<TGGAttributeExpression,Object>>() : col;
	}
	
	@Override
	//FIXME:  Code is hard to read, avoid pairs
	public void applyCSPValues(IMatch comatch) {
		Collection<Pair<TGGAttributeExpression, Object>> cspValues = getBoundAttributeExpValues();

		for (Pair<TGGAttributeExpression, Object> cspVal : cspValues) {
			EObject entry = (EObject) comatch.get(cspVal.getLeft().getObjectVar().getName());
			EAttribute attr = cspVal.getLeft().getAttribute();
			EDataType type = attr.getEAttributeType();
			Object value = cspVal.getRight();
			
			if(value != null && type != null && attr != null) {
				Object toSet = coerceToType(type, value);
				if(!valueAlreadySet(entry, attr, toSet))
					entry.eSet(attr, toSet);
			}
		}
	}

	private boolean valueAlreadySet(EObject entry, EAttribute attr, Object toSet) {
		return toSet.equals(entry.eGet(attr));
	}

	private Object coerceToType(EDataType type, Object o) {
		assert (o != null);
		assert (type != null);
		
		if (EcoreUtil.wrapperClassFor(type.getInstanceClass()).isInstance(o))
			return o;
		else if(type.getInstanceClass().equals(int.class) && o.getClass().equals(Double.class))
			// Approximate result and squeeze into an int
			return ((Double)o).intValue();
		else if(o instanceof String && type.getInstanceClass().equals(int.class))
			return Integer.parseInt((String) o);
		else if(o instanceof String && type.getInstanceClass().equals(double.class))
			return Double.parseDouble((String) o);
		else if(o instanceof Collection)
			// Currently no handling for collections
			return o;
		else
			throw new IllegalStateException("Cannot coerce " + o.getClass() + " to " + type.getInstanceClassName());
	}
}
