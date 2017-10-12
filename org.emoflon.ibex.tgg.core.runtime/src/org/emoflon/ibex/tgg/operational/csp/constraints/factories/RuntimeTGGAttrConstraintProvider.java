package org.emoflon.ibex.tgg.operational.csp.constraints.factories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraint;

import language.csp.TGGAttributeConstraint;

public class RuntimeTGGAttrConstraintProvider {

	private Collection<RuntimeTGGAttrConstraintFactory> factories;
	
	public RuntimeTGGAttrConstraintProvider() {
		factories = new ArrayList<>();
		factories.add(new PredefRuntimeTGGAttrConstraintFactory());
	}
	
	public void registerFactory(RuntimeTGGAttrConstraintFactory factory) {
		factories.add(factory);
	}
	
	public RuntimeTGGAttributeConstraint createRuntimeTGGAttributeConstraint(String name, TGGAttributeConstraint constraint){
		for(RuntimeTGGAttrConstraintFactory factory : factories) {
			if(factory.containsRuntimeTGGAttributeConstraint(name)) {
				RuntimeTGGAttributeConstraint c = factory.createRuntimeTGGAttributeConstraint(name);
				c.setConstraint(constraint);
			}
		}
		throw new RuntimeException("CSP " + name + " not implemented");
	}
	
	public List<String> getAllConstraintNames() {
		return factories.stream().flatMap(factory -> factory.getConstraintNames().stream()).collect(Collectors.toList());
	}
}
