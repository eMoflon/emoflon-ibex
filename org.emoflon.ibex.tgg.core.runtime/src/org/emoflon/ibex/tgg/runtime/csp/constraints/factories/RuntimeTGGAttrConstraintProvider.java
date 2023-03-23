package org.emoflon.ibex.tgg.runtime.csp.constraints.factories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.emoflon.ibex.tgg.runtime.csp.RuntimeTGGAttributeConstraint;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintDefinition;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintDefinitionLibrary;

public class RuntimeTGGAttrConstraintProvider {

	private Collection<TGGAttributeConstraintDefinitionLibrary> libraries;
	private Collection<RuntimeTGGAttrConstraintFactory> factories;
	private Map<TGGAttributeConstraintDefinition, RuntimeTGGAttrConstraintFactory> constraintToFactory;
	
	public RuntimeTGGAttrConstraintProvider(Collection<TGGAttributeConstraintDefinitionLibrary> constraintDefLibraries) {
		factories = new LinkedList<>();
		libraries = new LinkedList<>();
		registerFactory(new PredefRuntimeTGGAttrConstraintFactory());
		constraintToFactory = new HashMap<>();
		for(var constraintDefLib : constraintDefLibraries)
			registerAllTGGAttributeConstraintDefinitions(constraintDefLib);
	}
	
	public RuntimeTGGAttrConstraintProvider(TGGAttributeConstraintDefinitionLibrary constraintDefLib) {
		factories = new LinkedList<>();
		libraries = new LinkedList<>();
		registerFactory(new PredefRuntimeTGGAttrConstraintFactory());
		constraintToFactory = new HashMap<>();
		registerAllTGGAttributeConstraintDefinitions(constraintDefLib);
	}
	
	/**
	 * register a new factory for creating new RuntimeTGGAttributeConstraint objects
	 */
	public void registerFactory(RuntimeTGGAttrConstraintFactory factory) {
		factories.add(factory);
		for(var library : libraries) {
			if(factory.getClass().getSimpleName().equals(library.getName())) {
				library.getTggAttributeConstraintDefinitions().stream().forEach(def -> constraintToFactory.put(def, factory));
				return;
			}
		}
	}
	
	
	
	/**
	 * registers all constraint definitions to be able to perform a more efficient lookup
	 */
	private void registerAllTGGAttributeConstraintDefinitions(TGGAttributeConstraintDefinitionLibrary library) {
		libraries.add(library);
		for(var factory : factories) {
			if(factory.getClass().getSimpleName().equals(library.getName())) {
				library.getTggAttributeConstraintDefinitions().stream().forEach(def -> constraintToFactory.put(def, factory));
				return;
			}
		}
	}
	
	/**
	 * @param name of the constraint which is to be instantiated
	 * @return a new instance of type RuntimeTGGAttributeConstraint implementing the constraint with id = name
	 */
	public RuntimeTGGAttributeConstraint createRuntimeTGGAttributeConstraint(TGGAttributeConstraintDefinition constraintDefinition){
		if(constraintToFactory.containsKey(constraintDefinition)) {
			var factory = constraintToFactory.get(constraintDefinition);
			factory.createRuntimeTGGAttributeConstraint(constraintDefinition.getName(), constraintDefinition);
		}
		
		throw new RuntimeException("CSP " + constraintDefinition.getName() + " not implemented");
	}
}
