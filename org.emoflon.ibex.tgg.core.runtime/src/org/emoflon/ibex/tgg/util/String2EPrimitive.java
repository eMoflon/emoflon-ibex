package org.emoflon.ibex.tgg.util;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EcorePackage;

public class String2EPrimitive {

	public static Object convertString(EClassifier type, String value) {
		if (type.equals(EcorePackage.Literals.EINT))
			return Integer.parseInt(value);
		if (type.equals(EcorePackage.Literals.EDOUBLE))
			return Double.parseDouble(value);
		if (type.equals(EcorePackage.Literals.EFLOAT))
			return Float.parseFloat(value);
		if (type.equals(EcorePackage.Literals.ECHAR))
			return value.length() == 0 ? null : value.charAt(0);
		if (type.equals(EcorePackage.Literals.ESTRING) || type.getInstanceClass().isAssignableFrom(String.class))
			return value.substring(1, value.length() - 1);
		if (type.equals(EcorePackage.Literals.EBOOLEAN))
			return Boolean.parseBoolean(value);

		return null;
	}
}
