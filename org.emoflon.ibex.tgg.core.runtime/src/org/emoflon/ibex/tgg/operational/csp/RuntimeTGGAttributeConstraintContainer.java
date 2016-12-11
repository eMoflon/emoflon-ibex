package org.emoflon.ibex.tgg.operational.csp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.viatra.query.runtime.api.IPatternMatch;
import org.emoflon.ibex.tgg.operational.csp.constraints.RuntimeTGGAttributeConstraintFactory;
import org.emoflon.ibex.tgg.operational.csp.solver.CodeGeneratorChain;
import org.emoflon.ibex.tgg.operational.csp.solver.SearchPlanAction;
import org.emoflon.ibex.tgg.operational.csp.solver.SimpleCombiner;

import language.basic.expressions.TGGAttributeExpression;
import language.basic.expressions.TGGEnumExpression;
import language.basic.expressions.TGGLiteralExpression;
import language.basic.expressions.TGGParamValue;
import language.csp.TGGAttributeConstraint;
import language.csp.TGGAttributeConstraintLibrary;
import language.csp.TGGAttributeVariable;

public class RuntimeTGGAttributeConstraintContainer {

	private List<RuntimeTGGAttributeConstraint> constraints;
	private RuntimeTGGAttributeConstraintFactory constraintFactory;
	private Map<TGGParamValue, RuntimeTGGAttributeConstraintVariable> params2runtimeVariable = new HashMap<>();
	
	private IPatternMatch match;
	private Collection<String> boundObjectNames;
	
	private boolean modelgen;
	
	public RuntimeTGGAttributeConstraintContainer(TGGAttributeConstraintLibrary library, IPatternMatch match, boolean modelgen) {
		this.match = match;
		this.boundObjectNames = match.parameterNames();
		this.modelgen = modelgen;
		
		constraintFactory = new RuntimeTGGAttributeConstraintFactory();
		extractRuntimeParameters(library);
		extractRuntimeConstraints(library);
	}

	private void extractRuntimeConstraints(TGGAttributeConstraintLibrary library) {
		constraints = library.getTggAttributeConstraints().stream().map(c -> extractRuntimeConstraint(c)).collect(Collectors.toList());
	}
	
	private RuntimeTGGAttributeConstraint extractRuntimeConstraint(TGGAttributeConstraint c) {
		RuntimeTGGAttributeConstraint runtimeConstraint = constraintFactory.createRuntimeTGGAttributeConstraint(c.getDefinition().getName());
		runtimeConstraint.getVariables().addAll(c.getParameters().stream().map(p -> params2runtimeVariable.get(p)).collect(Collectors.toList()));
		if(modelgen)
			runtimeConstraint.getAllowedAdornments().addAll(c.getDefinition().getGenAdornments());
		else
			runtimeConstraint.getAllowedAdornments().addAll(c.getDefinition().getSyncAdornments());
		return runtimeConstraint;
	}

	private void extractRuntimeParameters(TGGAttributeConstraintLibrary library) {
		library.getParameterValues().stream().forEach(p -> params2runtimeVariable.put(p, new RuntimeTGGAttributeConstraintVariable(calculateBoundState(p), calculateValue(p))));
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
			EObject obj = (EObject) match.get(tae.getObjectVar().getName());
			if(obj != null) 
				return obj.eGet(tae.getAttribute());
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

	// TODO!
	private Object extractLiteralValue(TGGLiteralExpression lExp) {
		// TODO Auto-generated method stub
		return null;
	}

	public void addConstraint(RuntimeTGGAttributeConstraint constraint) {
		constraints.add(constraint);
	}
	
	public Collection<RuntimeTGGAttributeConstraint> getConstraints() {
		return constraints;
	}
	
	public boolean solve(){
		if(constraints.isEmpty())
			return true;
		
		SearchPlanAction searcPlanAction = new SearchPlanAction();
		SimpleCombiner combiner = searcPlanAction.sortConstraints(constraints, new ArrayList<RuntimeTGGAttributeConstraintVariable>(params2runtimeVariable.values()));
		CodeGeneratorChain<RuntimeTGGAttributeConstraint> chain = combiner.getRoot();

		while(chain != null){
			RuntimeTGGAttributeConstraint nextConstraint = chain.getValue();
			nextConstraint.solve();
			if(!nextConstraint.isSatisfied())
				return false;
			chain = chain.getNext();
		}
		
		return true;
	}
}
