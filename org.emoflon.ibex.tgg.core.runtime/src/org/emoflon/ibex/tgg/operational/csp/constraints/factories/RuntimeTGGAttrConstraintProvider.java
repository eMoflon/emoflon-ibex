package org.emoflon.ibex.tgg.operational.csp.constraints.factories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraint;

import language.csp.definition.TGGAttributeConstraintDefinition;
import language.csp.definition.TGGAttributeConstraintDefinitionLibrary;

public class RuntimeTGGAttrConstraintProvider {

	private Collection<RuntimeTGGAttrConstraintFactory> factories;
	private Map<String, TGGAttributeConstraintDefinition> constraintDefinitions;
	
	public RuntimeTGGAttrConstraintProvider(TGGAttributeConstraintDefinitionLibrary constraintDefLib) {
		factories = new ArrayList<>();
		registerFactory(new PredefRuntimeTGGAttrConstraintFactory());
		constraintDefinitions = new HashMap<>();
		registerAllTGGAttributeConstraintDefinitions(constraintDefLib);
	}
	
	/**
	 * register a new factory for creating new RuntimeTGGAttributeConstraint objects
	 */
	public void registerFactory(RuntimeTGGAttrConstraintFactory factory) {
		factories.add(factory);
	}
	
	/**
	 * registers all constraint definitions to be able to perform a more efficient lookup
	 */
	private void registerAllTGGAttributeConstraintDefinitions(TGGAttributeConstraintDefinitionLibrary constraintDefLib) {
		constraintDefLib.getTggAttributeConstraintDefinitions().stream().forEach(def -> constraintDefinitions.put(def.getName(), def));
	}
	
	/**
	 * @param name of the constraint which is to be instantiated
	 * @return a new instance of type RuntimeTGGAttributeConstraint implementing the constraint with id = name
	 */
	public RuntimeTGGAttributeConstraint createRuntimeTGGAttributeConstraint(String name){
		TGGAttributeConstraintDefinition constraintDefinition = constraintDefinitions.get(name);
		for(RuntimeTGGAttrConstraintFactory factory : factories) {
			if(factory.containsRuntimeTGGAttributeConstraint(name)) {
				return factory.createRuntimeTGGAttributeConstraint(name, constraintDefinition);
			}
		}
		throw new RuntimeException("CSP " + name + " not implemented");
	}
	
	public TGGAttributeConstraintDefinition getTGGAttributeConstraintDefinition(String name) {
		return constraintDefinitions.get(name);
	}
	
	/**
	 * @return all constraint names of constraints which are registered together with a creator
	 */
	public Collection<String> getAllConstraintNames() {
		return factories.stream().flatMap(factory -> factory.getConstraintNames().stream()).collect(Collectors.toList());
	}

	/**
	 * @return all constraint names of those constraints which are referenced by any rule
	 */
	public Collection<String> getAllUsedConstraintNames() {
		return constraintDefinitions.keySet();
	}
}
