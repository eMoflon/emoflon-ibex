package org.emoflon.ibex.tgg.compiler.builder;

import java.util.Collection;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintDefinition;

public class UserAttrCondHelper {
	public static String getFileName(String name) {
		return "UserDefined_" + name;
	}
	
	public static String getParameterString(TGGAttributeConstraintDefinition def) {
		String result = "v0";
		for(int i=1; i<def.getParameterDefinitions().size(); i++) {
			result += ", v" + i;
		}
		return result;
	}

	public static Collection<String> getAdorments(TGGAttributeConstraintDefinition definition) {
		return definition.getGenBindings().stream()
				.map(a -> a.getValue())
				.map(s -> s.stream()
						.map(st -> st.trim())
						.reduce("", (a,b) -> a + b))
				.collect(Collectors.toList());
	}
}
