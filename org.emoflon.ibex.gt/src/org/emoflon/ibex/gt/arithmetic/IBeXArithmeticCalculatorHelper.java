package org.emoflon.ibex.gt.arithmetic;

import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EcorePackage;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.gt.engine.GraphTransformationInterpreter;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticValue;

public class IBeXArithmeticCalculatorHelper {
	
	/**
	 * calculates the arithmetic expression and casts it to the appropriate dataType
	 * 
	 * @param value the arithmetic expression
	 * @param match the IMatch
	 * @param type the EDataType
	 * @return the calculated value
	 */
	public static Object getValue(final GraphTransformationInterpreter contextInterpreter, final IBeXArithmeticValue value,
			final IMatch match, EDataType type) {
		Object object = null;
		if(type.equals(EcorePackage.Literals.EDOUBLE) || type.equals(EcorePackage.Literals.EFLOAT)) {
			object = type.getEPackage().getEFactoryInstance().createFromString(type, 
					Double.toString(RuntimeArithmeticExtensionCalculator
							.calculateValue(contextInterpreter, value.getExpression(), match)));
		}
		else{
			long intValue = Math.round(RuntimeArithmeticExtensionCalculator
					.calculateValue(contextInterpreter, value.getExpression(), match));
			object = type.getEPackage().getEFactoryInstance().createFromString(type, 
					Long.toString(intValue));
		}
		return object;
	}
}
