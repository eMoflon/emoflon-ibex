package org.emoflon.ibex.gt.codegen

import GTLanguage.GTRule
import GTLanguage.GTProbability
import java.util.HashSet
import java.util.ArrayList
import java.util.List
import org.emoflon.ibex.gt.SGTPatternModel.GTStochasticDistribution
import org.emoflon.ibex.gt.SGTPatternModel.GTArithmetics
import org.emoflon.ibex.gt.SGTPatternModel.GTNumber
import org.emoflon.ibex.gt.SGTPatternModel.GTAttribute
import org.emoflon.ibex.gt.SGTPatternModel.GTTwoParameterCalculation
import org.emoflon.ibex.gt.SGTPatternModel.GTOneParameterCalculation
import org.emoflon.ibex.gt.SGTPatternModel.TwoParameterOperator

/**
 *  Enum for the different Constraint types
 */
enum ConstraintType {
	SDNEGATIVE,//when the distribution is normal and the standard deviation is negative
	UNIFORMDISTRIBUTION, //when it is a uniformdistribution and the min/max value is an attribute
	UNIFORMVALUE, // when it is a randomly generated probability and the min/max value is an attribute
	STATICPARAMETER, // when the static probability is an attribute
	EXPONENTIALMEAN, // when the distribution is an exponential and the mean is an attribute
	NOCONSTRAINT
}
/**
 * Utility class for probability class generation
 */
class JavaProbabilityFileGenerator {
	
	String packageName;
	
	new(String packageName){
		this.packageName = packageName
	}
	/**
	 * Returns the initialization of the probability of the rule depended if the rule is static or not
	 */
	def getProbability(GTRule rule){
		var declaration = '''Optional<Probability«getGenerics(rule)»> probability = '''		
		if(rule.probability === null) return declaration + 'Optional.empty();'		
		if(isStatic(rule.probability)){
			val function = rule.probability.function;			
			return declaration + '''Optional.of(new StaticProbability«getGenerics(rule)»(«
			transformExpression(function.mean, false)», «
			transformExpression(function.sd, false)», «getDistribution(function.distribution)», «
			IF rule.probability.parameter !== null» OptionalDouble.of(«transformExpression(rule.probability.parameter, false)
			»)«ELSE»OptionalDouble.empty()«ENDIF»));'''
		} 
		else return declaration + '''Optional.of(new «getProbabilityClassName(rule)»());'''
	}
	
	/**
	 * if the probability is not static, then it will generate a probability class for the rule
	 */
	def generateProbabilityClass(GTRule rule){
		val probability = rule.probability

		var sourceCode = '''
		/**
		 * The probability class for the rule «getRuleClassName(rule)»; calculates the probability
		 * that the rule will be applied
		 */
		public class «getProbabilityClassName(rule)» implements Probability«getGenerics(rule)»{
			«IF probability.parameter!== null»
			«getDistributionFunction(probability.function.distribution)» distribution;
			«ELSE»
			
			Random rnd = new Random();
			«ENDIF»
			
			@Override
			public double getProbability(«getMatchClassName(rule)» match){
				«getProbabilityBodyWithConstraints(rule)»				
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
	private def getProbabilityBodyWithConstraints(GTRule rule){
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
	private def getProbabilityBody(GTProbability probability){
		val function = probability.function
		var sourceCode = ''''''
		if(probability.parameter !== null){
			sourceCode = '''
			distribution = new «getDistributionFunction(function.distribution)
			»(«transformExpression(function.mean, false)»«
			IF function.distribution != GTStochasticDistribution.EXPONENTIAL», «transformExpression(function.sd, false)»«ENDIF»);
			return distribution.cumulativeProbability(«transformExpression(probability.parameter, false)»);'''
		}
		else{
			val distribution = function.distribution
			if(distribution === GTStochasticDistribution.STATIC){
				sourceCode = '''return «transformExpression(function.mean, false)»;'''
			}
			else if(distribution === GTStochasticDistribution.NORMAL){
				sourceCode = '''
				double value;
				do{
					value = rnd.nextGaussian()*(«transformExpression(function.sd, false)») + «transformExpression(function.mean, false)»;
				}while(value < 0.0 || value > 1.0);
				return value;'''
			}
			else if(distribution === GTStochasticDistribution.UNIFORM){
				sourceCode = '''return «transformExpression(function.mean, false)» + rnd.nextDouble()*(«
				transformExpression(function.sd, false)» - «transformExpression(function.mean, false)»);'''
			}
			else if(distribution === GTStochasticDistribution.EXPONENTIAL){
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
	def isStatic(GTProbability probability){
		if(!isRuntimeDepended(probability.function.mean) && !isRuntimeDepended(probability.function.sd)){
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
	private def hasConstraint(GTProbability probability){
		val function = probability.function
		val distribution = function.distribution
		//checks if the sd of the normal distribution is runtime depended
		if(distribution === GTStochasticDistribution.NORMAL && isRuntimeDepended(function.sd)){
			return ConstraintType.SDNEGATIVE
		}
		else if(distribution === GTStochasticDistribution.UNIFORM
			&& (isRuntimeDepended(function.sd) || isRuntimeDepended(function.mean))){
				if(probability.parameter === null) return ConstraintType.UNIFORMVALUE
				else return ConstraintType.UNIFORMDISTRIBUTION	
		}
				
		//if the distribution is static then the probability will be a parameter
		if(distribution === GTStochasticDistribution.STATIC) {
			return ConstraintType.STATICPARAMETER		
		}
		if(distribution === GTStochasticDistribution.EXPONENTIAL){
			return ConstraintType.EXPONENTIALMEAN
		} 
		return ConstraintType.NOCONSTRAINT
	}
	
	/**
	 * returns the appropriate constraint check for the particular probability
	 */
	private def getStochasticConstraint(ConstraintType constraint, GTProbability probability){
		val function = probability.function
		var constraintCheck = new ArrayList()
		// the sd needs to be a positive value
		if(constraint == ConstraintType.SDNEGATIVE){
			constraintCheck.add('''(«transformExpression(function.sd, false)») > 0.0''')
		}
		// the probability needs to be a value between 0 and 1
		else if(constraint == ConstraintType.STATICPARAMETER){
			constraintCheck.add('''(«transformExpression(function.mean, false)»)>= 0.0 && («
			transformExpression(function.mean, false)») <= 1.0''')
			
		}
		// the min value needs to be smaller than the max value
		else if(constraint == ConstraintType.UNIFORMDISTRIBUTION){
			constraintCheck.add('''(«transformExpression(function.mean, false)») <= («
				transformExpression(function.sd, false)»)'''
			)
		}
		else if(constraint == ConstraintType.UNIFORMVALUE){
			if(isRuntimeDepended(function.mean)){
				constraintCheck.add('''(«transformExpression(function.mean, false)») >= 0.0''') 
			}if(isRuntimeDepended(function.sd)){
				constraintCheck.add('''(«transformExpression(function.sd, false)») <= 1.0''')
				if(isRuntimeDepended(function.mean)){
					constraintCheck.add('''(«transformExpression(function.mean, false)») <= («
						transformExpression(function.sd, false)»)'''
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
	private def getArithmeticConstraint(GTProbability probability){
		var constraints = new ArrayList<String>();
		constraints.addAll(getArithmeticConstraint(probability.function.mean, false))
		constraints.addAll(getArithmeticConstraint(probability.function.sd, false))
		if(probability.parameter!== null) constraints.addAll(getArithmeticConstraint(probability.function.sd, false))
		return constraints
	}
	
	static def getArithmeticConstraint(GTArithmetics expression, boolean isIMatch){
		var list = new ArrayList()
		getArithmeticConstraint(expression, list, isIMatch)
		return list
	}
	
	/**
	 * returns the arithmetic constraints for the expression; can be used for IMatch matches or matches
	 * of the generated match classes
	 */
	private static def getArithmeticConstraint(GTArithmetics expression, List<String> list, boolean isIMatch){
		if(expression instanceof GTNumber || expression instanceof GTAttribute) return;
		if(expression instanceof GTOneParameterCalculation){
			getArithmeticConstraint(expression.value, list, isIMatch)
			switch(expression.operator){
				case ROOT: list.add('''(«transformExpression(expression.value, isIMatch)»)>=0.0''')
				case LOGARITHMUS: list.add('''(«transformExpression(expression.value, isIMatch)»)>0.0''')
				case NATLOG: list.add('''(«transformExpression(expression.value, isIMatch)»)>=0.0''')
				default: return
			}
		}
		if(expression instanceof GTTwoParameterCalculation){
			getArithmeticConstraint(expression.left, list, isIMatch)
			getArithmeticConstraint(expression.right, list, isIMatch)
			if(expression.operator === TwoParameterOperator.DIVISION && !(expression.right instanceof GTNumber)){
				list.add('''(«transformExpression(expression.right, isIMatch)»)!=0.0''')
			} 
		}
		return
	}
	
	/**
	 * Returns the exception message
	 */
	private def getExceptionMessage(ConstraintType constraint, GTRule rule){
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
	def getProbabilityImports(GTRule rule){
		val probability = rule.probability
		val imports = new HashSet<String>()
		imports.addAll(
			'org.emoflon.ibex.gt.arithmetics.Probability',
			'''«getSubPackageName('api.matches')».«getMatchClassName(rule)»''',
			'''«getSubPackageName('api.rules')».«getRuleClassName(rule)»'''
			
		)
		if(isStatic(probability)){
			imports.add('org.emoflon.ibex.common.StochasticLanguage.GTStochasticDistribution')
		}
		else{
		if(probability.parameter === null) imports.add('java.util.Random')
		else imports.add('''org.apache.commons.math3.distribution.«getDistributionFunction(probability.function.distribution)»''')
			
		}
		return imports	
	}
	
	/**
	 * Transforms the GTArithmetics in a String; works with IMatches as matches or the generated match class 
	 */
	static def String transformExpression(GTArithmetics expression, boolean isIMatch){
		if(expression instanceof GTNumber) return '''«expression.number»''' 
		if(expression instanceof GTAttribute) {
			return transformAttribute(expression, isIMatch)
		}
		if(expression instanceof GTOneParameterCalculation){
			var value = transformExpression(expression.value, isIMatch)
			var negative = if(expression.negative) '-' else ''
			switch(expression.operator){
				case BRACKET: return negative + '''(«value»)'''
				case ABSOLUTE: return negative + '''Math.abs(«value»)'''
				case COS: return negative + '''Math.cos(«value»)'''
				case SIN: return negative + '''Math.sin(«value»)'''
				case TAN: return negative + '''Math.tan(«value»)'''
				case EEXPONENTIAL: return negative + '''Math.exp(«value»)'''
				case LOGARITHMUS: return negative + '''Math.log10(«value»)'''
				case NATLOG: return negative + '''Math.log(«value»)'''
				case ROOT: return negative + '''Math.sqrt(«value»)'''
			}		
		}
		if(expression instanceof GTTwoParameterCalculation){
			val left = transformExpression(expression.left, isIMatch)
			val right = transformExpression(expression.right, isIMatch)
			switch(expression.operator){
				case ADDITION: return '''«left»+«right»'''
				case DIVISION: return '''«left» / «right»'''
				case EXPONENTIAL: return '''Math.pow(«left»,«right»)'''
				case MODULO: return '''«left»%«right»'''
				case MULTIPLICATION: return '''«left»*«right»'''
				case SUBTRACTION: return '''«left»-«right»'''
			}
		}	
	}
	 static def transformAttribute(GTAttribute expression, boolean isIMatch){
	 	if(!isIMatch) return '''«IF expression.isNegative»-«ENDIF»match.get«expression.name.toFirstUpper»().get«
				expression.attribute.name.toFirstUpper»()'''
		else return '''«IF expression.isNegative»-«ENDIF»((«expression.type.name») match.get("«expression.name»")).get«
				expression.attribute.name.toFirstUpper»()'''
	 }
	def getDistribution(GTStochasticDistribution distribution){
		return '''GTStochasticDistribution.«distribution»'''
	}
	
	/**
	 * Returns the distribution function which is used
	 */
	private def getDistributionFunction(GTStochasticDistribution distribution){
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
	private def isRuntimeDepended(GTArithmetics expression){
		if(expression instanceof GTNumber) return false else return true
	}
	
	
	/**
	 * Returns the name of the generics for the rule
	 */
	private def getGenerics(GTRule rule){
		return "<" + getMatchClassName(rule) + ", " + getRuleClassName(rule) + ">"
	}
	
	/**
	 * Returns the name of the probability class for the rule
	 */
	def getProbabilityClassName(GTRule rule){
		return rule.name.toFirstUpper + "RuleProbability"
	}
	
	/**
	 * Returns the name of the match class for the rule.
	 */
	private def getMatchClassName(GTRule rule) {
		return rule.name.toFirstUpper + "Match"
	}

	/**
	 * Returns the name of the rule class for the rule.
	 */
	private static def getRuleClassName(GTRule rule) {
		return rule.name.toFirstUpper + if(rule.executable) "Rule" else "Pattern"
	}
	
	/**
	 * Returns the name of the package.
	 */
	private def getSubPackageName(String subPackage) {
		val dot = if(packageName.equals("")) "" else "."
		return '''«packageName»«dot»«subPackage»'''
	}
	
}