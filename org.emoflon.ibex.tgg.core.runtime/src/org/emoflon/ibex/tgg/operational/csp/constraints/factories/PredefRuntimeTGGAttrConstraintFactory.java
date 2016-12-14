package org.emoflon.ibex.tgg.operational.csp.constraints.factories;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Supplier;

import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraint;
import org.emoflon.ibex.tgg.operational.csp.constraints.Add;
import org.emoflon.ibex.tgg.operational.csp.constraints.AddPrefix;
import org.emoflon.ibex.tgg.operational.csp.constraints.AddSuffix;
import org.emoflon.ibex.tgg.operational.csp.constraints.Concat;
import org.emoflon.ibex.tgg.operational.csp.constraints.Divide;
import org.emoflon.ibex.tgg.operational.csp.constraints.Eq_Boolean;
import org.emoflon.ibex.tgg.operational.csp.constraints.Eq_Char;
import org.emoflon.ibex.tgg.operational.csp.constraints.Eq_Double;
import org.emoflon.ibex.tgg.operational.csp.constraints.Eq_Float;
import org.emoflon.ibex.tgg.operational.csp.constraints.Eq_Int;
import org.emoflon.ibex.tgg.operational.csp.constraints.Eq_Long;
import org.emoflon.ibex.tgg.operational.csp.constraints.Eq_String;
import org.emoflon.ibex.tgg.operational.csp.constraints.Max;
import org.emoflon.ibex.tgg.operational.csp.constraints.Multiply;
import org.emoflon.ibex.tgg.operational.csp.constraints.SetDefaultNumber;
import org.emoflon.ibex.tgg.operational.csp.constraints.SetDefaultString;
import org.emoflon.ibex.tgg.operational.csp.constraints.SmallerOrEqual;
import org.emoflon.ibex.tgg.operational.csp.constraints.StringToDouble;
import org.emoflon.ibex.tgg.operational.csp.constraints.Sub;

public class PredefRuntimeTGGAttrConstraintFactory extends RuntimeTGGAttrConstraintFactory {

	private Collection<String> constraints; 
	private Map<String, Supplier<RuntimeTGGAttributeConstraint>> creators;
	
	public PredefRuntimeTGGAttrConstraintFactory() {
		initialize();
	}
	
	private void initialize() {
		creators = new HashMap<>();
		creators.put("add", () -> new Add());
		creators.put("addPrefix", () -> new AddPrefix());
		creators.put("addSuffix", () -> new AddSuffix());
		creators.put("concat", () -> new Concat());
		creators.put("divide", () -> new Divide());
		creators.put("eq_boolean", () -> new Eq_Boolean());
		creators.put("eq_char", () -> new Eq_Char());
		creators.put("eq_double", () -> new Eq_Double());
		creators.put("eq_float", () -> new Eq_Float());
		creators.put("eq_int", () -> new Eq_Int());
		creators.put("eq_long", () -> new Eq_Long());
		creators.put("eq_string", () -> new Eq_String());
		creators.put("max", () -> new Max());
		creators.put("multiply", () -> new Multiply());
		creators.put("setDefaultNumber", () -> new SetDefaultNumber());
		creators.put("setefaultString", () -> new SetDefaultString());
		creators.put("smallerOrEqual", () -> new SmallerOrEqual());
		creators.put("stringToDouble", () -> new StringToDouble());
		creators.put("sub", () -> new Sub());

		
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
