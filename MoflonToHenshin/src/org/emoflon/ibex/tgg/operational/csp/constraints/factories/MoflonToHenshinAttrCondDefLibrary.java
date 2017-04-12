package org.emoflon.ibex.tgg.operational.csp.constraints.factories;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Supplier;
import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraint;

import org.emoflon.ibex.tgg.operational.csp.constraints.custom.UserDefined_setCreateAttribute;
import org.emoflon.ibex.tgg.operational.csp.constraints.custom.UserDefined_setContextAttribute;
import org.emoflon.ibex.tgg.operational.csp.constraints.custom.UserDefined_setNegativeAttribute;

public class MoflonToHenshinAttrCondDefLibrary extends RuntimeTGGAttrConstraintFactory {

	private Collection<String> constraints; 
	private Map<String, Supplier<RuntimeTGGAttributeConstraint>> creators;
	
	public MoflonToHenshinAttrCondDefLibrary() {
		initialize();
	}
	
	private void initialize() {
		creators = new HashMap<>();
		creators.put("setCreateAttribute", () -> new UserDefined_setCreateAttribute());
		creators.put("setContextAttribute", () -> new UserDefined_setContextAttribute());
		creators.put("setNegativeAttribute", () -> new UserDefined_setNegativeAttribute());
		
		constraints = new HashSet<String>();
		constraints.addAll(creators.keySet());
	}
	
	@Override
	public RuntimeTGGAttributeConstraint createRuntimeTGGAttributeConstraint(String name) {
		Supplier<RuntimeTGGAttributeConstraint> creator = creators.get(name);
		if(creator == null)
			throw new RuntimeException("CSP not implemented");
		return creator.get();
	}
	
	@Override
	public boolean containsRuntimeTGGAttributeConstraint(String name) {
		return constraints.contains(name);
	}
}
