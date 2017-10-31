package org.emoflon.ibex.tgg.operational.csp.constraints.factories;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraint;
import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraintVariable;

import language.csp.definition.TGGAttributeConstraintDefinition;

public abstract class RuntimeTGGAttrConstraintFactory {

	protected Collection<String> constraints; 
	protected Map<String, Supplier<RuntimeTGGAttributeConstraint>> creators;
	
	abstract protected void initialize();
	
	public RuntimeTGGAttrConstraintFactory() {
		initialize();
	}
	
	public RuntimeTGGAttributeConstraint createRuntimeTGGAttributeConstraint(String name, TGGAttributeConstraintDefinition constraintDefinition) {
		Supplier<RuntimeTGGAttributeConstraint> creator = creators.get(name);
		if(creator == null)
			throw new RuntimeException("CSP not implemented");
		RuntimeTGGAttributeConstraint runtimeConstraint = creator.get();
		createVariablesForRuntimeConstraint(runtimeConstraint, constraintDefinition);
		return runtimeConstraint;
	}
	
	public boolean containsRuntimeTGGAttributeConstraint(String name) {
		return constraints.contains(name);
	}
	
	public void createVariablesForRuntimeConstraint(RuntimeTGGAttributeConstraint runtimeConstraint, TGGAttributeConstraintDefinition constraintDefinition) {
		// variables are generated as free (unbound) with no value assigned
		constraintDefinition.getParameterDefinitions().stream()
			.forEach(pDef -> runtimeConstraint.getVariables().add(new RuntimeTGGAttributeConstraintVariable(false, null, pDef.getType().getInstanceTypeName())));
	}

	public Collection<String> getConstraintNames() {
		return constraints;
	}
}
