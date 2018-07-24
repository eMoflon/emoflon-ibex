package org.emoflon.ibex.tgg.util;

import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;

public class String2EPrimitive {	
	public static Object convertLiteral(String literal, EDataType type) {
		if(type.equals(EcorePackage.Literals.ESTRING) || type.getInstanceClass().isAssignableFrom(String.class)) {
			if(!(literal.startsWith("\"") && literal.endsWith("\""))) 
				throw new RuntimeException("Trimming of the string did not work. Your string: " + literal + " should start and end with \"");
			return literal.substring(1, literal.length() - 1);
		}
		
		if(type.equals(EcorePackage.Literals.ECHAR) ) {
			if(!(literal.startsWith("\'") && literal.endsWith("\'"))) 
				throw new RuntimeException("Trimming of the char did not work. Your string: " + literal + " should start and end with \'");

			return literal.length() < 2 ? '\0' : literal.charAt(1);
		}
		
		try {
			return EcoreUtil.createFromString(type, literal);	
		} catch (Exception e) {
			throw new IllegalArgumentException(type + " is not supported as a Datatype", e);
		}
	}
}
