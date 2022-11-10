package org.emoflon.ibex.gt.build.template

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.BooleanExpression
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.BooleanBinaryExpression
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.BooleanUnaryExpression
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXBooleanValue
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNullValue
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeValue
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.RelationalExpression
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ValueExpression
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEnumValue
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXStringValue
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTIteratorAttributeReference
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ArithmeticExpression
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNodeValue
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXMatchCountValue
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTParameterValue
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.BinaryExpression
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.UnaryExpression
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.DoubleLiteral
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.IntegerLiteral
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EClassifier
import org.emoflon.ibex.common.transformation.DataTypeUtil
import org.eclipse.emf.ecore.EcorePackage
import java.util.Set
import org.eclipse.emf.ecore.EClass
import java.util.Collection
import org.eclipse.emf.ecore.EEnum

class ExpressionHelper {
	
	protected IBeXGTApiData data;
	protected Set<String> imports;
	
	new(IBeXGTApiData data, Set<String> imports) {
		this.data = data;
		this.imports = imports;
	}
	
	def String unparse(String methodContext, BooleanExpression expression) {
		if(expression instanceof BooleanBinaryExpression) {
			switch(expression.operator) {
				case AND: {
					return '''«unparse(methodContext, expression.lhs)» && «unparse(methodContext, expression.rhs)»'''
				}
				case IMPLICATION: {
					return '''(!(«unparse(methodContext, expression.lhs)») || «unparse(methodContext, expression.rhs)»)'''
				}
				case OR: {
					return '''«unparse(methodContext, expression.lhs)» || «unparse(methodContext, expression.rhs)»'''
				}
				case XOR: {
					return '''«unparse(methodContext, expression.lhs)» ^ «unparse(methodContext, expression.rhs)»'''
				}
			}
		} else if(expression instanceof BooleanUnaryExpression) {
			switch(expression.operator) {
				case BRACKET: {
					return '''(«unparse(methodContext, expression.operand)»)'''
				}
				case NEGATION: {
					return '''!«unparse(methodContext, expression.operand)»'''
				}	
			}
		} else if(expression instanceof IBeXBooleanValue) {
			return '''«expression.value.toString»'''
		} else if(expression instanceof IBeXNullValue) {
			return '''null'''
		} else if(expression instanceof IBeXAttributeValue) {
			return '''«unparse(methodContext, expression)»'''
		} else if(expression instanceof RelationalExpression) {
			switch(expression.operator) {
				case EQUAL: {
					return '''«unparse(methodContext, expression.lhs)» == «unparse(methodContext, expression.rhs)»'''
				}
				case GREATER: {
					return '''«unparse(methodContext, expression.lhs)» > «unparse(methodContext, expression.rhs)»'''
				}
				case GREATER_OR_EQUAL: {
					return '''«unparse(methodContext, expression.lhs)» >= «unparse(methodContext, expression.rhs)»'''
				}
				case OBJECT_EQUALS: {
					return '''(«unparse(methodContext, expression.lhs)»).equals(«unparse(methodContext, expression.rhs)»)'''
				}
				case OBJECT_NOT_EQUALS: {
					return '''!(«unparse(methodContext, expression.lhs)»).equals(«unparse(methodContext, expression.rhs)»)'''
				}
				case SMALLER: {
					return '''«unparse(methodContext, expression.lhs)» < «unparse(methodContext, expression.rhs)»'''
				}
				case SMALLER_OR_EQUAL: {
					return '''«unparse(methodContext, expression.lhs)» <= «unparse(methodContext, expression.rhs)»'''
				}
				case UNEQUAL: {
					return '''«unparse(methodContext, expression.lhs)» != «unparse(methodContext, expression.rhs)»'''
				}
				case OBJECT_GREATER: {
					return '''(«unparse(methodContext, expression.lhs)»).compareTo(«unparse(methodContext, expression.rhs)»)>0'''
				}
				case OBJECT_GREATER_OR_EQUAL: {
					return '''((«unparse(methodContext, expression.lhs)»).compareTo(«unparse(methodContext, expression.rhs)»)>0 || («unparse(methodContext, expression.lhs)»).equals(«unparse(methodContext, expression.rhs)»))'''
				}
				case OBJECT_SMALLER: {
					return '''(«unparse(methodContext, expression.lhs)»).compareTo(«unparse(methodContext, expression.rhs)»)<0'''
				}
				case OBJECT_SMALLER_OR_EQUAL: {
					return '''((«unparse(methodContext, expression.lhs)»).compareTo(«unparse(methodContext, expression.rhs)»)<0 || («unparse(methodContext, expression.lhs)»).equals(«unparse(methodContext, expression.rhs)»))'''
				}
			}
		} else {
			throw new UnsupportedOperationException("Unknown boolean expression type: " + expression)
		}
	}
	
	def String unparse(String methodContext, ValueExpression expression) {
		if(expression instanceof IBeXBooleanValue) {
			return '''«expression.value.toString»'''
		} else if(expression instanceof IBeXEnumValue) {
			imports.add(data.getFQN(expression.type))
			return '''«expression.type.name».«expression.literal.name»'''
		} else if(expression instanceof IBeXStringValue) {
			return '''"«expression.value»"'''
		} else if(expression instanceof IBeXNullValue) {
			return '''null'''
		} else if(expression instanceof GTIteratorAttributeReference) {
			return '''«expression.iterator.iterator.name».«unparse(expression.attribute)»'''
		} else if(expression instanceof ArithmeticExpression) {
			return unparse(methodContext, expression)
		} else {
			throw new UnsupportedOperationException("Unknown value expression type: " + expression)
		}
	}
	
	def String unparse(String methodContext, ArithmeticExpression expression) {
		if(expression instanceof IBeXAttributeValue) {
			return unparse(methodContext, expression)
		} else if(expression instanceof IBeXNodeValue) {
			return unparse(methodContext, expression)
		} else if(expression instanceof IBeXMatchCountValue) {
			if(!expression.invocation.mapping.isEmpty) {
				imports.add(data.matchPackage+"."+data.pattern2matchClassName.get(expression.invocation.invocation))
			}
			return '''(int) patternMatcher.getTypedPattern("«expression.invocation.invocation.name»").getMatches(false)«IF expression.invocation.mapping.isEmpty».size()
			«ELSE».stream().map(m -> («data.pattern2matchClassName.get(expression.invocation.invocation)»)m).filter(m -> «FOR mapping : expression.invocation.mapping SEPARATOR ' && \n'»«methodContext».«mapping.key.name.toLowerCase»().equals(m.«mapping.value.name.toLowerCase»())«ENDFOR»).count()«ENDIF»'''
		} else if(expression instanceof GTParameterValue) {
			return '''«expression.parameter.name»'''
		} else if(expression instanceof BinaryExpression) {
			switch(expression.operator) {
				case ADD: {
					return '''«getCast(expression, expression.lhs)»«unparse(methodContext, expression.lhs)» + «getCast(expression, expression.rhs)»«unparse(methodContext, expression.rhs)»'''
				}
				case DIVIDE: {
					return '''«getCast(expression, expression.lhs)»«unparse(methodContext, expression.lhs)» / «getCast(expression, expression.rhs)»«unparse(methodContext, expression.rhs)»'''
				}
				case LOG: {
					return '''(Math.log(«getCast(expression, expression.lhs)»«unparse(methodContext, expression.lhs)») / Math.log(«getCast(expression, expression.rhs)»«unparse(methodContext, expression.rhs)»))'''
				}
				case MAX: {
					return '''Math.max(«getCast(expression, expression.lhs)»«unparse(methodContext, expression.lhs)», «getCast(expression, expression.rhs)»«unparse(methodContext, expression.rhs)»)'''
				}
				case MIN: {
					return '''Math.min(«getCast(expression, expression.lhs)»«unparse(methodContext, expression.lhs)», «getCast(expression, expression.rhs)»«unparse(methodContext, expression.rhs)»)'''
				}
				case MOD: {
					return '''«getCast(expression, expression.lhs)»«unparse(methodContext, expression.lhs)» % «getCast(expression, expression.rhs)»«unparse(methodContext, expression.rhs)»'''
				}
				case MULTIPLY: {
					return '''«getCast(expression, expression.lhs)»«unparse(methodContext, expression.lhs)» * «getCast(expression, expression.rhs)»«unparse(methodContext, expression.rhs)»'''
				}
				case NORMAL_DISTRIBUTION: {
					return '''gtEngine.rndGenerator.nextGaussian(«getCast(expression, expression.lhs)»«unparse(methodContext, expression.lhs)», «getCast(expression, expression.rhs)»«unparse(methodContext, expression.rhs)»)'''
				}
				case POW: {
					return '''Math.pow(«getCast(expression, expression.lhs)»«unparse(methodContext, expression.lhs)», «getCast(expression, expression.rhs)»«unparse(methodContext, expression.rhs)»)'''
				}
				case SUBTRACT: {
					return '''«getCast(expression, expression.lhs)»«unparse(methodContext, expression.lhs)» - «getCast(expression, expression.rhs)»«unparse(methodContext, expression.rhs)»'''
				}
				case UNIFORM_DISTRIBUTION: {
					return '''(gtEngine.rndGenerator.nextDouble() * («getCast(expression, expression.rhs)»«unparse(methodContext, expression.rhs)» - «getCast(expression, expression.lhs)»«unparse(methodContext, expression.lhs)») + «getCast(expression, expression.lhs)»«unparse(methodContext, expression.lhs)»)'''
				}
			}
		} else if(expression instanceof UnaryExpression) {
			switch(expression.operator) {
				case ABSOLUTE: {
					return '''Math.abs(«getCast(expression, expression.operand)»«unparse(methodContext, expression.operand)»)'''
				}
				case BRACKET: {
					return '''(«unparse(methodContext, expression.operand)»)'''
				}
				case COS: {
					return '''Math.cos(«getCast(expression, expression.operand)»«unparse(methodContext, expression.operand)»)'''
				}
				case EXPONENTIAL_DISTRIBUTION: {
					return '''(Math.log(1 - gtEngine.rndGenerator.nextDouble()) / (-«getCast(expression, expression.operand)»«unparse(methodContext, expression.operand)»))'''
				}
				case NEGATIVE: {
					return '''-(«getCast(expression, expression.operand)»«unparse(methodContext, expression.operand)»)'''
				}
				case SIN: {
					return '''Math.sin(«getCast(expression, expression.operand)»«unparse(methodContext, expression.operand)»)'''
				}
				case SQRT: {
					return '''Math.sqrt(«getCast(expression, expression.operand)»«unparse(methodContext, expression.operand)»)'''
				}
				case TAN: {
					return '''Math.tan(«getCast(expression, expression.operand)»«unparse(methodContext, expression.operand)»)'''
				}
				
			}
		} else if(expression instanceof DoubleLiteral) {
			return '''«expression.value.toString»'''
		} else if(expression instanceof IntegerLiteral) {
			return '''«expression.value.toString»'''
		} else if(expression instanceof IBeXStringValue) {
			return '''"«expression.value»"'''
		}  else {
			throw new UnsupportedOperationException("Unknown arithmetic expression type: " + expression)
		}
	}
	
	def String unparse(String methodContext, IBeXNodeValue expression) {
		return '''«methodContext».«expression.node.name.toFirstLower»()'''
	}
	
	def String unparse(String methodContext, IBeXAttributeValue expression) {
		return '''«methodContext».«expression.node.name.toFirstLower»().«unparse(expression.attribute)»'''
	}
	
	def String unparse(EAttribute attribute) {
		if(attribute.EType == EcorePackage.Literals.EBOOLEAN) {
			return '''is«attribute.name.toFirstUpper»()'''
		} else {
			return '''get«attribute.name.toFirstUpper»()'''
		}
	}
	
	def String getCast(ArithmeticExpression parent, ArithmeticExpression leaf) {
		if(parent.type == leaf.type) {
			return "";
		} else {
			return '''(«EDataType2Java(parent.type)»)'''
		}
	}
	
	def String EDataType2ExactJava(EClassifier type) {
		if(type == EcorePackage.Literals.ELONG) {
			return '''long'''
		} else if(type == EcorePackage.Literals.EINT) {
			return '''int'''
		} else if(type == EcorePackage.Literals.ESHORT) {
			return '''short'''
		} else if(type == EcorePackage.Literals.EBYTE) {
			return '''byte'''
		} else if(type == EcorePackage.Literals.ECHAR) {
			return '''char'''
		} else if(type == EcorePackage.Literals.EDOUBLE) {
			return '''double'''
		} else if(type == EcorePackage.Literals.EFLOAT) {
			return '''float'''
		} else if(type == EcorePackage.Literals.ESTRING) {
			return '''String'''
		} else if(type == EcorePackage.Literals.EBOOLEAN) {
			return '''boolean'''
		} else if(type == EcorePackage.Literals.EDATE) {
			imports.add("java.util.Date")
			return '''Date'''
		} else if(type instanceof EClass || type instanceof EEnum) {
			imports.add(data.getFQN(type))
			return '''«type.name»'''
		} else {
			throw new IllegalArgumentException("Unknown or unsupported data type: " + type)
		}
	}
	
	def String EDataType2Java(EClassifier type) {
		val simplifiedType = DataTypeUtil.simplifiyType(type)
		if(simplifiedType == EcorePackage.Literals.ELONG) {
			return '''long'''
		} else if(simplifiedType == EcorePackage.Literals.EDOUBLE) {
			return '''double'''
		} else if(simplifiedType == EcorePackage.Literals.ESTRING) {
			return '''String'''
		} else if(simplifiedType == EcorePackage.Literals.EBOOLEAN) {
			return '''boolean'''
		} else if(simplifiedType == EcorePackage.Literals.EDATE) {
			imports.add("java.util.Date")
			return '''Date'''
		} else if(type instanceof EClass || type instanceof EEnum) {
			imports.add(data.getFQN(type))
			return '''«type.name»'''
		} else {
			throw new IllegalArgumentException("Unknown or unsupported data type: " + type)
		}
	}
	
	def static String EDataType2Java(IBeXGTApiData data, EClassifier type, Collection<String> imports) {
		val simplifiedType = DataTypeUtil.simplifiyType(type)
		if(simplifiedType == EcorePackage.Literals.ELONG) {
			return '''long'''
		} else if(simplifiedType == EcorePackage.Literals.EDOUBLE) {
			return '''double'''
		} else if(simplifiedType == EcorePackage.Literals.ESTRING) {
			return '''String'''
		} else if(simplifiedType == EcorePackage.Literals.EBOOLEAN) {
			return '''boolean'''
		} else if(simplifiedType == EcorePackage.Literals.EDATE) {
			imports.add("java.util.Date")
			return '''Date'''
		} else if(type instanceof EClass || type instanceof EEnum) {
			imports.add(data.getFQN(type))
			return '''«type.name»'''
		} else {
			throw new IllegalArgumentException("Unknown or unsupported data type: " + type)
		}
	}
}