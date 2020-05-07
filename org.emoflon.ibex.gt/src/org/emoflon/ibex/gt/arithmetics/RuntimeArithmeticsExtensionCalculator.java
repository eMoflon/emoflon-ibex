package org.emoflon.ibex.gt.arithmetics;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.gt.SGTPatternModel.GTArithmetics;
import org.emoflon.ibex.gt.SGTPatternModel.GTAttribute;
import org.emoflon.ibex.gt.SGTPatternModel.GTNumber;
import org.emoflon.ibex.gt.SGTPatternModel.GTOneParameterCalculation;
import org.emoflon.ibex.gt.SGTPatternModel.GTTwoParameterCalculation;

public class RuntimeArithmeticsExtensionCalculator {
	
	/**
	 * calculates the runtime depended value of the GTArithmetics
	 * 
	 * @param expression the GTArithmetics
	 * @param match the match for the calculation of the runtime depended parameters
	 * @return the calculated value
	 */
	public static double calculateValue(final GTArithmetics expression, final IMatch match){
		if(expression instanceof GTOneParameterCalculation){
			double value = calculateValue(((GTOneParameterCalculation) expression).getValue(), match);
			double result = 0.0;
			switch(((GTOneParameterCalculation) expression).getOperator()) {
				case ABSOLUTE: 		result = Math.abs(value);
									break;
				case BRACKET: 		result = value;
									break;
				case EEXPONENTIAL: 	result = Math.exp(value);
									break;
				case LOGARITHMUS: 	if(value > 0.0) result = Math.log10(value);
									else throw new IllegalArgumentException("The value of the log operand in the pattern "
											+ match.getPatternName() + " needs to be positive");
									break;
				case NATLOG: 		if(value > 0.0) result = Math.log(value);
									else throw new IllegalArgumentException("The value of the ln operand in the pattern "
											+ match.getPatternName() + " needs to be positive");
									break;
				case ROOT: 			if(value >= 0.0) result = Math.sqrt(value);
									else throw new IllegalArgumentException("The value of the root operand in the pattern "
											+ match.getPatternName() + " needs to be positive");
									break;
				case COS: 			result = Math.cos(value);
									break;
				case SIN:			result = Math.sin(value);
									break;
				case TAN: 			result = Math.tan(value);
									break;
			}
			if(((GTOneParameterCalculation) expression).isNegative()) return -result;
			else return result;
		}
		if(expression instanceof GTTwoParameterCalculation) {
			double left = calculateValue(((GTTwoParameterCalculation) expression).getLeft(), match);
			double right = calculateValue(((GTTwoParameterCalculation) expression).getRight(), match);
			switch(((GTTwoParameterCalculation) expression).getOperator()) {
				case ADDITION: 		return left + right;
				case SUBTRACTION: 	return left - right;
				case MULTIPLICATION:return left * right;
				case DIVISION: 		if(right != 0.0) return left / right;
									else throw new IllegalArgumentException("division by zero in the pattern "
											+ match.getPatternName() + " not possible");
				case MODULO: 		return left % right;
				case EXPONENTIAL: 	return Math.pow(left, right);
			}
		}
		if(expression instanceof GTAttribute) {
			EObject node = (EObject) match.get(((GTAttribute) expression).getName());
			if(!((GTAttribute) expression).isNegative()) return ((Number) node.eGet(((GTAttribute) expression).getAttribute())).doubleValue();
			else return - ((Number) node.eGet(((GTAttribute) expression).getAttribute())).doubleValue();
		}
		
		return ((GTNumber) expression).getNumber();
	}
}

