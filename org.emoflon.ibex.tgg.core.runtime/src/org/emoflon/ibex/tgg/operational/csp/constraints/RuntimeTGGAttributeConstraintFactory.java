package org.emoflon.ibex.tgg.operational.csp.constraints;

import java.util.HashMap;

import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraint;

public class RuntimeTGGAttributeConstraintFactory {

	public RuntimeTGGAttributeConstraintFactory() {
	}
	
	public RuntimeTGGAttributeConstraint createRuntimeTGGAttributeConstraint(String name) {
		switch(name) {
			case "eq": return new Eq();
			default: throw new RuntimeException("CSP not implemented");
		}
	}
}
