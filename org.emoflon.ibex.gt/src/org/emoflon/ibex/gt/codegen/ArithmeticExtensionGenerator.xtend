package org.emoflon.ibex.gt.codegen

import java.util.HashSet
import java.util.ArrayList
import java.util.List
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXProbability
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticExpression
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticValueLiteral
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDistributionType
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticAttribute
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXBinaryExpression
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXUnaryExpression
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXBinaryOperator
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXMatchCount

/**
 *  Enum for the different Constraint types
 */
enum ConstraintType {
	SDNEGATIVE, //when the distribution is normal and the standard deviation is negative
	UNIFORMDISTRIBUTION, //when it is a uniformdistribution and the min/max value is an attribute
	UNIFORMVALUE, // when it is a randomly generated probability and the min/max value is an attribute
	STATICPARAMETER, // when the static probability is an attribute
	EXPONENTIALMEAN, // when the distribution is an exponential and the mean is an attribute
	NOCONSTRAINT
}
/**
 * Utility class for probability class generation
 */
class ArithmeticExtensionGenerator {
	
	String packageName;
	
	new(String packageName){
		this.packageName = packageName
	}
	
	def getProbabilityDeclaration(IBeXRule rule){
		return '''Optional<Probability«getGenerics(rule)»> probability;'''		
	}
	
	/**
	 * Returns the initialization of the probability of the rule depended if the rule is static or not
	 */
	def getProbabilityInitialization(IBeXRule rule){	
		if(rule.probability === null) return 'Optional.empty()'		
		if(isStatic(rule.probability)){
			val function = rule.probability.distribution;			
			return '''Optional.of(new StaticProbability«getGenerics(rule)»(interpreter, «
			transformExpression(function.mean, false)», «
			transformExpression(function.stddev, false)», «getDistribution(function.type)», «
			IF rule.probability.parameter !== null» OptionalDouble.of(«transformExpression(rule.probability.parameter, false)
			»)«ELSE»OptionalDouble.empty()«ENDIF»))'''
		} 
		else return '''Optional.of(new «getProbabilityClassName(rule)»(interpreter))'''
	}
	
	/**
	 * if the probability is not static, then it will generate a probability class for the rule
	 */
	def generateProbabilityClass(IBeXRule rule){
		val probability = rule.probability

		var sourceCode = '''
			/**
			 * The probability class for the rule «getRuleClassName(rule)»; calculates the probability
			 * that the rule will be applied
			 */
			public class «getProbabilityClassName(rule)» extends Probability«getGenerics(rule)»{
				«IF probability.parameter!== null»
					«getDistributionFunction(probability.distribution.type)» distribution;
				«ELSE»
					
					Random rnd = new Random();
				«ENDIF»
				
				public «getProbabilityClassName(rule)»(final GraphTransformationInterpreter interpreter) {
					super(interpreter);
				}
				
				@Override
				public double getProbability(«getMatchClassName(rule)» match){
					«getProbabilityBodyWithConstraints(rule)»				
				}
				
				@Override
				public double getProbabilityGeneric(GraphTransformationMatch<?, ?> match){
					return getProbability((«getMatchClassName(rule)»)match);
				}
				
				@Override
				public double getProbability(){
					return 0;
				}
			}
		'''

		return sourceCode
	}
	
	/**
	 * Returns the body of the getProbability method with constraints if there are any
	 */
	private def getProbabilityBodyWithConstraints(IBeXRule rule){
		val probability = rule.probability
		val constraint = hasConstraint(probability)
		val arithmeticConstraints = getArithmeticConstraint(probability)
		if(!arithmeticConstraints.empty){
			return '''
			if(«FOR constraints: arithmeticConstraints SEPARATOR '&&'»«constraints»«ENDFOR»){
				«IF constraint != ConstraintType.NOCONSTRAINT»
					if(«FOR constraints: getStochasticConstraint(constraint, probability) SEPARATOR '&&'»«constraints»«ENDFOR»){
						«getProbabilityBody(probability)»
					}
					else{
						throw new IllegalArgumentException("«getExceptionMessage(constraint, rule)»");
				}«ELSE»
						«getProbabilityBody(probability)»
				«ENDIF»
			} else{
				throw new IllegalArgumentException("There was an error with the arithmetic expression when calculating the probability");
			}'''
		}else {
			if(constraint != ConstraintType.NOCONSTRAINT){	
				return '''
				if(«FOR constraints: getStochasticConstraint(constraint, probability) SEPARATOR '&&'»«constraints»«ENDFOR»){
					«getProbabilityBody(probability)»
				}
				else{
					throw new IllegalArgumentException("«getExceptionMessage(constraint, rule)»");
				}'''		
			}else{
				return '''«getProbabilityBody(probability)»'''
			}	
		}
				
	}
	/**
	 * Returns the body of the getProbability method which calculates the probability of the rule
	 */
	private def getProbabilityBody(IBeXProbability probability){
		val function = probability.distribution
		var sourceCode = ''''''
		if(probability.parameter !== null){
			sourceCode = '''
			distribution = new «getDistributionFunction(function.type)
			»(«transformExpression(function.mean, false)»«
			IF function.type != IBeXDistributionType.EXPONENTIAL», «transformExpression(function.stddev, false)»«ENDIF»);
			return distribution.cumulativeProbability(«transformExpression(probability.parameter, false)»);'''
		}
		else{
			val distribution = function.type
			if(distribution === IBeXDistributionType.STATIC){
				sourceCode = '''return «transformExpression(function.mean, false)»;'''
			}
			else if(distribution === IBeXDistributionType.NORMAL){
				sourceCode = '''
				double value;
				do{
					value = rnd.nextGaussian()*(«transformExpression(function.stddev, false)») + «transformExpression(function.mean, false)»;
				}while(value < 0.0 || value > 1.0);
				return value;'''
			}
			else if(distribution === IBeXDistributionType.UNIFORM){
				sourceCode = '''return «transformExpression(function.mean, false)» + rnd.nextDouble()*(«
				transformExpression(function.stddev, false)» - «transformExpression(function.mean, false)»);'''
			}
			else if(distribution === IBeXDistributionType.EXPONENTIAL){
				sourceCode = '''
				double value;
				do{
					value = Math.log(1 - rnd.nextDouble())/(-(«transformExpression(function.mean, false)»));
				}while(value < 0.0 || value > 1.0);
				return value;'''
			}
		}
		
		return sourceCode
	}
	
	/**
	 * Checks if the probability is depended of a parameter and thus not static 
	 */
	def isStatic(IBeXProbability probability){
		if(!isRuntimeDepended(probability.distribution.mean) && !isRuntimeDepended(probability.distribution.stddev)){
			if(probability.parameter === null) {
				return true;
			}
			else{
				if(!isRuntimeDepended(probability.parameter)) return true;
			}
		}	
		return false;
	}
	
	/**
	 * Checks if the probability needs to have a constraints check
	 */
	private def hasConstraint(IBeXProbability probability){
		val function = probability.distribution
		val distribution = function.type
		//checks if the sd of the normal distribution is runtime depended
		if(distribution === IBeXDistributionType.NORMAL && isRuntimeDepended(function.stddev)){
			return ConstraintType.SDNEGATIVE
		}
		else if(distribution === IBeXDistributionType.UNIFORM
			&& (isRuntimeDepended(function.stddev) || isRuntimeDepended(function.mean))){
				if(probability.parameter === null) return ConstraintType.UNIFORMVALUE
				else return ConstraintType.UNIFORMDISTRIBUTION	
		}
				
		//if the distribution is static then the probability will be a parameter
		if(distribution === IBeXDistributionType.STATIC) {
			return ConstraintType.STATICPARAMETER		
		}
		if(distribution === IBeXDistributionType.EXPONENTIAL){
			return ConstraintType.EXPONENTIALMEAN
		} 
		return ConstraintType.NOCONSTRAINT
	}
	
	/**
	 * returns the appropriate constraint check for the particular probability
	 */
	private def getStochasticConstraint(ConstraintType constraint, IBeXProbability probability){
		val function = probability.distribution
		var constraintCheck = new ArrayList()
		// the sd needs to be a positive value
		if(constraint == ConstraintType.SDNEGATIVE){
			constraintCheck.add('''(«transformExpression(function.stddev, false)») > 0.0''')
		}
		// the probability needs to be a value between 0 and 1
		else if(constraint == ConstraintType.STATICPARAMETER){
			constraintCheck.add('''(«transformExpression(function.mean, false)»)>= 0.0 && («
			transformExpression(function.mean, false)») <= 1.0''')
			
		}
		// the min value needs to be smaller than the max value
		else if(constraint == ConstraintType.UNIFORMDISTRIBUTION){
			constraintCheck.add('''(«transformExpression(function.mean, false)») <= («
				transformExpression(function.stddev, false)»)'''
			)
		}
		else if(constraint == ConstraintType.UNIFORMVALUE){
			if(isRuntimeDepended(function.mean)){
				constraintCheck.add('''(«transformExpression(function.mean, false)») >= 0.0''') 
			}if(isRuntimeDepended(function.stddev)){
				constraintCheck.add('''(«transformExpression(function.stddev, false)») <= 1.0''')
				if(isRuntimeDepended(function.mean)){
					constraintCheck.add('''(«transformExpression(function.mean, false)») <= («
						transformExpression(function.stddev, false)»)'''
					)
				}
			}
		}
		else if(constraint == ConstraintType.EXPONENTIALMEAN){
			constraintCheck.add('''(«transformExpression(function.mean, false)»)>= 0.0''')
		}
		return constraintCheck
	}
	
	/**
	 * returns all the constraints originating through the arithmetic expression
	 */
	private def getArithmeticConstraint(IBeXProbability probability){
		var constraints = new ArrayList<String>();
		constraints.addAll(getArithmeticConstraint(probability.distribution.mean, false))
		constraints.addAll(getArithmeticConstraint(probability.distribution.stddev, false))
		if(probability.parameter!== null) constraints.addAll(getArithmeticConstraint(probability.distribution.stddev, false))
		return constraints
	}
	
	static def getArithmeticConstraint(IBeXArithmeticExpression expression, boolean isIMatch){
		var list = new ArrayList()
		getArithmeticConstraint(expression, list, isIMatch)
		return list
	}
	
	static def getArithmeticConstraint(IBeXArithmeticExpression lhs, IBeXArithmeticExpression rhs, boolean isIMatch){
		var list = new ArrayList()
		getArithmeticConstraint(lhs, list, isIMatch)
		getArithmeticConstraint(rhs, list, isIMatch)
		return list
	}
	
	/**
	 * returns the arithmetic constraints for the expression; can be used for IMatch matches or matches
	 * of the generated match classes
	 */
	private static def getArithmeticConstraint(IBeXArithmeticExpression expression, List<String> list, boolean isIMatch){
		if(expression instanceof IBeXArithmeticValueLiteral || expression instanceof IBeXArithmeticAttribute) return;
		if(expression instanceof IBeXUnaryExpression){
			getArithmeticConstraint(expression.operand, list, isIMatch)
			switch(expression.operator){
				case SQRT: list.add('''(«transformExpression(expression.operand, isIMatch)»)>=0.0''')
				case LOG: list.add('''(«transformExpression(expression.operand, isIMatch)»)>0.0''')
				case LG: list.add('''(«transformExpression(expression.operand, isIMatch)»)>=0.0''')
				default: return
			}
		}
		if(expression instanceof IBeXBinaryExpression){
			getArithmeticConstraint(expression.left, list, isIMatch)
			getArithmeticConstraint(expression.right, list, isIMatch)
			if(expression.operator === IBeXBinaryOperator.DIVISION && !(expression.right instanceof IBeXArithmeticValueLiteral)){
				list.add('''(«transformExpression(expression.right, isIMatch)»)!=0.0''')
			} 
		}
		return
	}
	
	/**
	 * Returns the exception message
	 */
	private def getExceptionMessage(ConstraintType constraint, IBeXRule rule){
		var message = ''
		if(constraint == ConstraintType.SDNEGATIVE){
			message = '''The standard deviation of the distribution of the rule «rule.name» needs to be positive'''
		}
		if(constraint == ConstraintType.STATICPARAMETER){
			message = '''The probability of the rule «rule.name» needs to be a value between zero and one'''
		}
		if(constraint == ConstraintType.UNIFORMVALUE){
			message = '''The intervall of the probability of the rule «rule.name» needs to be between zero and one'''
		}
		if(constraint == ConstraintType.UNIFORMDISTRIBUTION){
			message = '''The minimum value of the uniform distribution of the rule «rule.name» is higher than the maximum value'''
		}
		if(constraint == ConstraintType.EXPONENTIALMEAN){
			message = '''The mean of the exponential distribution of the rule «rule.name» needs to be positive'''
		}
		return message
	}
	
	/**
	 * Returns the imports for the probability class of the rule
	 */
	def getProbabilityImports(IBeXRule rule){
		val probability = rule.probability
		val imports = new HashSet<String>()
		imports.addAll(
			'org.emoflon.ibex.gt.arithmetic.Probability',
			'org.emoflon.ibex.gt.api.GraphTransformationMatch',
			'java.util.HashMap',
			'org.emoflon.ibex.gt.engine.GraphTransformationInterpreter',
			'''«getSubPackageName('api.matches')».«getMatchClassName(rule)»''',
			'''«getSubPackageName('api.rules')».«getRuleClassName(rule)»'''
			
		)
		if(isStatic(probability)){
			imports.add('org.emoflon.ibex.common.StochasticLanguage.GTStochasticDistribution')
		}
		else{
		if(probability.parameter === null) imports.add('java.util.Random')
		else imports.add('''org.apache.commons.math3.distribution.«getDistributionFunction(probability.distribution.type)»''')
			
		}
		return imports	
	}
	
	/**
	 * Transforms the GTArithmetics in a String; works with IMatches as matches or the generated match class 
	 */
	static def String transformExpression(IBeXArithmeticExpression expression, boolean isIMatch){
		if(expression instanceof IBeXArithmeticValueLiteral) return '''«expression.value»''' 
		if(expression instanceof IBeXArithmeticAttribute) {
			return transformAttribute(expression, isIMatch)
		}
		if(expression instanceof IBeXUnaryExpression){
			var value = transformExpression(expression.operand, isIMatch)
			var negative = if(expression.negative) '-' else ''
			switch(expression.operator){
				case BRACKET: return negative + '''(«value»)'''
				case ABSOLUTE: return negative + '''Math.abs(«value»)'''
				case COS: return negative + '''Math.cos(«value»)'''
				case SIN: return negative + '''Math.sin(«value»)'''
				case TAN: return negative + '''Math.tan(«value»)'''
				case EEXPONENTIAL: return negative + '''Math.exp(«value»)'''
				case LOG: return negative + '''Math.log10(«value»)'''
				case LG: return negative + '''Math.log(«value»)'''
				case SQRT: return negative + '''Math.sqrt(«value»)'''
				case COUNT: {
					val countExpr = expression as IBeXMatchCount
					return '''
						interpreter.getFilteredMatchStream("«countExpr.invocation.invokedPattern.name»").parallel()
								«FOR mapping : countExpr.invocation.mapping.entrySet»
								.filter(localMatch -> match.get("«mapping.key.name»").equals(localMatch.get("«mapping.value.name»")))
							«ENDFOR»
							.count()'''
				}
			}		
		}
		if(expression instanceof IBeXBinaryExpression){
			val left = transformExpression(expression.left, isIMatch)
			val right = transformExpression(expression.right, isIMatch)
			switch(expression.operator){
				case ADDITION: return '''«left»+«right»'''
				case DIVISION: return '''«left» / «right»'''
				case EXPONENTIATION: return '''Math.pow(«left»,«right»)'''
				case MODULUS: return '''«left»%«right»'''
				case MULTIPLICATION: return '''«left»*«right»'''
				case SUBTRACTION: return '''«left»-«right»'''
				case MAXIMUM: return '''Math.max(«left»,«right»)'''
				case MINIMUM: return '''Math.min(«left»,«right»)'''
			}
		}	
	}
	 static def transformAttribute(IBeXArithmeticAttribute expression, boolean isIMatch){
	 	if(!isIMatch) return '''«IF expression.isNegative»-«ENDIF»match.get«expression.name.toFirstUpper»().get«
				expression.attribute.name.toFirstUpper»()'''
		else return '''«IF expression.isNegative»-«ENDIF»((«expression.type.name») match.get("«expression.name»")).get«
				expression.attribute.name.toFirstUpper»()'''
	 }
	def getDistribution(IBeXDistributionType distribution){
		return '''IBeXDistributionType.«distribution»'''
	}
	
	/**
	 * Returns the distribution function which is used
	 */
	private def getDistributionFunction(IBeXDistributionType distribution){
		switch(distribution){
			case NORMAL: 		return "NormalDistribution"
			case UNIFORM: 		return "UniformRealDistribution"
			case EXPONENTIAL: 	return "ExponentialDistribution"
			case STATIC: 		return ""
		}	
	}
	
	/**
	 * checks if the parameter is a number or attribute
	 */
	private def isRuntimeDepended(IBeXArithmeticExpression expression){
		if(expression instanceof IBeXArithmeticValueLiteral) return false else return true
	}
	
	
	/**
	 * Returns the name of the generics for the rule
	 */
	private def getGenerics(IBeXRule rule){
		return "<" + getMatchClassName(rule) + ", " + getRuleClassName(rule) + ">"
	}
	
	/**
	 * Returns the name of the probability class for the rule
	 */
	def getProbabilityClassName(IBeXRule rule){
		return rule.name.toFirstUpper + "RuleProbability"
	}
	
	/**
	 * Returns the name of the match class for the rule.
	 */
	private def getMatchClassName(IBeXRule rule) {
		return rule.name.toFirstUpper + "Match"
	}

	/**
	 * Returns the name of the rule class for the rule.
	 */
	private static def getRuleClassName(IBeXRule rule) {
		return rule.name.toFirstUpper + "Rule"
	}
	
	/**
	 * Returns the name of the package.
	 */
	private def getSubPackageName(String subPackage) {
		val dot = if(packageName.equals("")) "" else "."
		return '''«packageName»«dot»«subPackage»'''
	}
	
}
