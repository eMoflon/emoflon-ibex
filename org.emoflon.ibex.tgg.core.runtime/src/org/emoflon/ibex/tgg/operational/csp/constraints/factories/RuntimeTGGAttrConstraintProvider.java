package org.emoflon.ibex.tgg.operational.csp.constraints.factories;

import java.util.ArrayList;
import java.util.Collection;

import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraint;

import sun.font.CreatedFontTracker;

public class RuntimeTGGAttrConstraintProvider {

	private Collection<RuntimeTGGAttrConstraintFactory> factories;
	
	public RuntimeTGGAttrConstraintProvider() {
		factories = new ArrayList<>();
		factories.add(new PredefRuntimeTGGAttrConstraintFactory());
	}
	
	public void registerFactory(RuntimeTGGAttrConstraintFactory factory) {
		factories.add(factory);
	}
	
	public RuntimeTGGAttributeConstraint createRuntimeTGGAttributeConstraint(String name){
		for(RuntimeTGGAttrConstraintFactory factory : factories) {
			if(factory.containsRuntimeTGGAttributeConstraint(name)) {
				return factory.createRuntimeTGGAttributeConstraint(name);
			}
		}
		throw new RuntimeException("CSP " + name + " not implemented");
	}
}
