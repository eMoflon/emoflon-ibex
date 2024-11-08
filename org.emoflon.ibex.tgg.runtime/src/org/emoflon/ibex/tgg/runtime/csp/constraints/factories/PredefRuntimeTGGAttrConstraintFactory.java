package org.emoflon.ibex.tgg.runtime.csp.constraints.factories;

import java.util.HashMap;
import java.util.HashSet;

import org.emoflon.ibex.tgg.compiler.builder.AttrCondDefLibraryProvider;
import org.emoflon.ibex.tgg.runtime.csp.constraints.Add;
import org.emoflon.ibex.tgg.runtime.csp.constraints.AddPrefix;
import org.emoflon.ibex.tgg.runtime.csp.constraints.AddSuffix;
import org.emoflon.ibex.tgg.runtime.csp.constraints.Concat;
import org.emoflon.ibex.tgg.runtime.csp.constraints.Divide;
import org.emoflon.ibex.tgg.runtime.csp.constraints.Eq;
import org.emoflon.ibex.tgg.runtime.csp.constraints.Maximum;
import org.emoflon.ibex.tgg.runtime.csp.constraints.Multiply;
import org.emoflon.ibex.tgg.runtime.csp.constraints.SetDefaultNumber;
import org.emoflon.ibex.tgg.runtime.csp.constraints.SetDefaultString;
import org.emoflon.ibex.tgg.runtime.csp.constraints.SetRandomString;
import org.emoflon.ibex.tgg.runtime.csp.constraints.SmallerOrEqual;
import org.emoflon.ibex.tgg.runtime.csp.constraints.StringToDouble;
import org.emoflon.ibex.tgg.runtime.csp.constraints.StringToInt;
import org.emoflon.ibex.tgg.runtime.csp.constraints.Sub;

public class PredefRuntimeTGGAttrConstraintFactory extends RuntimeTGGAttrConstraintFactory {

	public PredefRuntimeTGGAttrConstraintFactory() {
		super();
	}
	
	@Override
	protected void initialize() {
		creators = new HashMap<>();
		creators.put("add", () -> new Add());
		creators.put("addPrefix", () -> new AddPrefix());
		creators.put("addSuffix", () -> new AddSuffix());
		creators.put("concat", () -> new Concat());
		creators.put("divide", () -> new Divide());
		creators.put("eq_boolean", () -> new Eq());
		creators.put("eq_char", () -> new Eq());
		creators.put("eq_double", () -> new Eq());
		creators.put("eq_float", () -> new Eq());
		creators.put("eq_int", () -> new Eq());
		creators.put("eq_long", () -> new Eq());
		creators.put("eq_string", () -> new Eq());
		creators.put("maximum", () -> new Maximum());
		creators.put("multiply", () -> new Multiply());
		creators.put("setDefaultNumber", () -> new SetDefaultNumber());
		creators.put("setDefaultString", () -> new SetDefaultString());
		creators.put("smallerOrEqual", () -> new SmallerOrEqual());
		creators.put("stringToInt", () -> new StringToInt());
		creators.put("stringToDouble", () -> new StringToDouble());
		creators.put("sub", () -> new Sub());
		creators.put("setRandomString", () -> new SetRandomString());

		
		constraints = new HashSet<String>();
		constraints.addAll(creators.keySet());
	}

	@Override
	public String getLibraryName() {
		return AttrCondDefLibraryProvider.DEFAULT_ATTR_COND_LIB_NAME;
	}
	
	
}
