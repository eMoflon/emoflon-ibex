package org.emoflon.ibex.gt.arithmetics;

import java.util.HashMap;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.gt.engine.GraphTransformationInterpreter;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticAttribute;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticExpression;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticValueLiteral;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXBinaryExpression;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXUnaryExpression;

public class RuntimeArithmeticsExtensionCalculator {
	
	/**
	 * calculates the runtime depended value of the GTArithmetics
	 * 
	 * @param expression the GTArithmetics
	 * @param match the match for the calculation of the runtime depended parameters
	 * @return the calculated value
	 */
	public static double calculateValue(final GraphTransformationInterpreter interpreter, final IBeXArithmeticExpression expression, final IMatch match){
		if(expression instanceof IBeXUnaryExpression){
			double value = calculateValue(interpreter, ((IBeXUnaryExpression) expression).getOperand(), match);
			double result = 0.0;
			switch(((IBeXUnaryExpression) expression).getOperator()) {
				case ABSOLUTE: 		result = Math.abs(value);
									break;
				case BRACKET: 		result = value;
									break;
				case EEXPONENTIAL: 	result = Math.exp(value);
									break;
				case LOG: 	if(value > 0.0) result = Math.log10(value);
									else throw new IllegalArgumentException("The value of the log operand in the pattern "
											+ match.getPatternName() + " needs to be positive");
									break;
				case LG: 		if(value > 0.0) result = Math.log(value);
									else throw new IllegalArgumentException("The value of the ln operand in the pattern "
											+ match.getPatternName() + " needs to be positive");
									break;
				case SQRT: 			if(value >= 0.0) result = Math.sqrt(value);
									else throw new IllegalArgumentException("The value of the root operand in the pattern "
											+ match.getPatternName() + " needs to be positive");
									break;
				case COS: 			result = Math.cos(value);
									break;
				case SIN:			result = Math.sin(value);
									break;
				case TAN: 			result = Math.tan(value);
									break;
				case COUNT: {
						result = interpreter.matchStream(match.getPatternName(), new HashMap<>()).count();
					break;
				}
			
			}
			if(((IBeXUnaryExpression) expression).isNegative()) return -result;
			else return result;
		}
		if(expression instanceof IBeXBinaryExpression) {
			double left = calculateValue(interpreter, ((IBeXBinaryExpression) expression).getLeft(), match);
			double right = calculateValue(interpreter, ((IBeXBinaryExpression) expression).getRight(), match);
			switch(((IBeXBinaryExpression) expression).getOperator()) {
				case ADDITION: 		return left + right;
				case SUBTRACTION: 	return left - right;
				case MULTIPLICATION:return left * right;
				case DIVISION: 		if(right != 0.0) return left / right;
									else throw new IllegalArgumentException("division by zero in the pattern "
											+ match.getPatternName() + " not possible");
				case MODULUS: 		return left % right;
				case EXPONENTIATION: 	return Math.pow(left, right);
			case MAXIMUM:
				throw new IllegalArgumentException("Maximum function not yet supported.");
			case MINIMUM:
				throw new IllegalArgumentException("Minimum function not yet supported.");
			default:
				throw new IllegalArgumentException("Unknown operation.");
			}
		}
		if(expression instanceof IBeXArithmeticAttribute) {
			EObject node = (EObject) match.get(((IBeXArithmeticAttribute) expression).getName());
			if(!((IBeXArithmeticAttribute) expression).isNegative()) return ((Number) node.eGet(((IBeXArithmeticAttribute) expression).getAttribute())).doubleValue();
			else return - ((Number) node.eGet(((IBeXArithmeticAttribute) expression).getAttribute())).doubleValue();
		}
		
		return ((IBeXArithmeticValueLiteral) expression).getValue();
	}
}

