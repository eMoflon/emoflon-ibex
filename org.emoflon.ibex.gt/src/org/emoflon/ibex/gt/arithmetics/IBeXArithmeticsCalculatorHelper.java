package org.emoflon.ibex.gt.arithmetics;

import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EcorePackage;
import org.emoflon.ibex.common.operational.IMatch;

import IBeXLanguage.IBeXArithmeticValue;

public class IBeXArithmeticsCalculatorHelper {
	
	/**
	 * calculates the arithmetic expression and casts it to the appropriate dataType
	 * 
	 * @param value the arithmetic expression
	 * @param match the IMatch
	 * @param type the EDataType
	 * @return the calculated value
	 */
	public static Object getValue(final IBeXArithmeticValue value,
			final IMatch match, EDataType type) {
		Object object = null;
		if(type.equals(EcorePackage.Literals.EDOUBLE) || type.equals(EcorePackage.Literals.EFLOAT)) {
			object = type.getEPackage().getEFactoryInstance().createFromString(type, 
					Double.toString(RuntimeArithmeticsExtensionCalculator
							.calculateValue(value.getExpression(), match)));
		}
		else{
			long intValue = Math.round(RuntimeArithmeticsExtensionCalculator
					.calculateValue(value.getExpression(), match));
			object = type.getEPackage().getEFactoryInstance().createFromString(type, 
					Long.toString(intValue));
		}
		return object;
	}
}
