package org.emoflon.ibex.gt.arithmetic;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.gt.engine.GraphTransformationInterpreter;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticAttribute;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticExpression;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticValueLiteral;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXBinaryExpression;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXMatchCount;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXUnaryExpression;

public class RuntimeArithmeticExtensionCalculator {
	
	/**
	 * calculates the runtime depended value of the GTArithmetics
	 * 
	 * @param expression the GTArithmetics
	 * @param match the match for the calculation of the runtime depended parameters
	 * @return the calculated value
	 */
	public static double calculateValue(final GraphTransformationInterpreter interpreter, final IBeXArithmeticExpression expression, final IMatch match){
		if(expression instanceof IBeXUnaryExpression unaryExpression){
			double value = calculateValue(interpreter, unaryExpression.getOperand(), match);
			double result = 0.0;
			result = switch (unaryExpression.getOperator()) {
				case ABSOLUTE -> 		Math.abs(value);
				case BRACKET -> 		value;
				case EEXPONENTIAL -> 	Math.exp(value);
				case LOG -> 			{
											if (value > 0.0) yield Math.log10(value);
											else throw new IllegalArgumentException("The value of the log operand in the pattern " 
													+ match.getPatternName() + " needs to be positive");
										}
				case LG -> 				{
											if (value > 0.0) yield Math.log(value);
											else throw new IllegalArgumentException("The value of the ln operand in the pattern " 
													+ match.getPatternName() + " needs to be positive");
										}
				case SQRT -> 			{
											if (value >= 0.0) yield Math.sqrt(value);
											else throw new IllegalArgumentException("The value of the root operand in the pattern " 
													+ match.getPatternName() + " needs to be positive");
										}
				case COS -> 			Math.cos(value);
				case SIN -> 			Math.sin(value);
				case TAN -> 			Math.tan(value);
				case COUNT -> 			evaluateMatchCount(interpreter, (IBeXMatchCount) expression, match);
			};
			if(unaryExpression.isNegative()) return -result;
			else return result;
		}
		if(expression instanceof IBeXBinaryExpression binaryExpression) {
			double left = calculateValue(interpreter, binaryExpression.getLeft(), match);
			double right = calculateValue(interpreter, binaryExpression.getRight(), match);
			return switch (binaryExpression.getOperator()) {
				case ADDITION ->  		left + right;
				case SUBTRACTION ->  	left - right;
				case MULTIPLICATION -> 	left * right;
				case DIVISION -> 		{
											if (right != 0.0) yield left / right;
											else throw new IllegalArgumentException("division by zero in the pattern " + 
													match.getPatternName() + " not possible");
										}
				case MODULUS ->  		left % right;
				case EXPONENTIATION ->  Math.pow(left, right);
				case MAXIMUM -> 		Math.max(left, right);
				case MINIMUM -> 		Math.min(left, right);
				default -> 				throw new IllegalArgumentException("Unknown operation.");
			};
		}
		if(expression instanceof IBeXArithmeticAttribute arithmeticAttribute) {
			EObject node = (EObject) match.get(arithmeticAttribute.getName());
			if(!arithmeticAttribute.isNegative()) return ((Number) node.eGet(arithmeticAttribute.getAttribute())).doubleValue();
			else return - ((Number) node.eGet(arithmeticAttribute.getAttribute())).doubleValue();
		}
		
		return ((IBeXArithmeticValueLiteral) expression).getValue();
	}
	
	public static long evaluateMatchCount(final GraphTransformationInterpreter interpreter, final IBeXMatchCount expression, final IMatch match) {
			Stream<IMatch> overlappedMatches = interpreter.matchStream(expression.getInvocation().getInvokedPattern().getName(), new HashMap<>(), false);
			for(Entry<IBeXNode, IBeXNode> entry : expression.getInvocation().getMapping().entrySet()) {
				overlappedMatches = overlappedMatches.filter(localMatch -> match.get(entry.getKey().getName()).equals(localMatch.get(entry.getValue().getName())));
			}

		return overlappedMatches.count();
	}
}

