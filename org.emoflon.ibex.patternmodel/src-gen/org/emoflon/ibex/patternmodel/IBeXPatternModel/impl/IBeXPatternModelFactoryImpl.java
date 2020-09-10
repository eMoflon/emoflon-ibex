/**
 */
package org.emoflon.ibex.patternmodel.IBeXPatternModel.impl;

import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class IBeXPatternModelFactoryImpl extends EFactoryImpl implements IBeXPatternModelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static IBeXPatternModelFactory init() {
		try {
			IBeXPatternModelFactory theIBeXPatternModelFactory = (IBeXPatternModelFactory) EPackage.Registry.INSTANCE
					.getEFactory(IBeXPatternModelPackage.eNS_URI);
			if (theIBeXPatternModelFactory != null) {
				return theIBeXPatternModelFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new IBeXPatternModelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXPatternModelFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case IBeXPatternModelPackage.IBE_XMODEL:
			return createIBeXModel();
		case IBeXPatternModelPackage.IBE_XPATTERN_SET:
			return createIBeXPatternSet();
		case IBeXPatternModelPackage.IBE_XRULE_SET:
			return createIBeXRuleSet();
		case IBeXPatternModelPackage.IBE_XNODE_SET:
			return createIBeXNodeSet();
		case IBeXPatternModelPackage.IBE_XEDGE_SET:
			return createIBeXEdgeSet();
		case IBeXPatternModelPackage.IBE_XCONTEXT_ALTERNATIVES:
			return createIBeXContextAlternatives();
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN:
			return createIBeXContextPattern();
		case IBeXPatternModelPackage.IBE_XCREATE_PATTERN:
			return createIBeXCreatePattern();
		case IBeXPatternModelPackage.IBE_XDELETE_PATTERN:
			return createIBeXDeletePattern();
		case IBeXPatternModelPackage.IBE_XNODE:
			return createIBeXNode();
		case IBeXPatternModelPackage.IBE_XEDGE:
			return createIBeXEdge();
		case IBeXPatternModelPackage.IBE_XPARAMETER:
			return createIBeXParameter();
		case IBeXPatternModelPackage.IBE_XATTRIBUTE_ASSIGNMENT:
			return createIBeXAttributeAssignment();
		case IBeXPatternModelPackage.IBE_XATTRIBUTE_CONSTRAINT:
			return createIBeXAttributeConstraint();
		case IBeXPatternModelPackage.IBE_XATTRIBUTE_VALUE:
			return createIBeXAttributeValue();
		case IBeXPatternModelPackage.IBE_XATTRIBUTE_PARAMETER:
			return createIBeXAttributeParameter();
		case IBeXPatternModelPackage.IBE_XCONSTANT:
			return createIBeXConstant();
		case IBeXPatternModelPackage.IBE_XENUM_LITERAL:
			return createIBeXEnumLiteral();
		case IBeXPatternModelPackage.IBE_XATTRIBUTE_EXPRESSION:
			return createIBeXAttributeExpression();
		case IBeXPatternModelPackage.IBE_XSTOCHASTIC_ATTRIBUTE_VALUE:
			return createIBeXStochasticAttributeValue();
		case IBeXPatternModelPackage.IBE_XARITHMETIC_VALUE:
			return createIBeXArithmeticValue();
		case IBeXPatternModelPackage.IBE_XINJECTIVITY_CONSTRAINT:
			return createIBeXInjectivityConstraint();
		case IBeXPatternModelPackage.IBE_XPATTERN_INVOCATION:
			return createIBeXPatternInvocation();
		case IBeXPatternModelPackage.IBE_XNODE_TO_NODE_MAPPING:
			return (EObject) createIBeXNodeToNodeMapping();
		case IBeXPatternModelPackage.IBE_XCSP:
			return createIBeXCSP();
		case IBeXPatternModelPackage.IBE_XRULE:
			return createIBeXRule();
		case IBeXPatternModelPackage.IBE_XPROBABILITY:
			return createIBeXProbability();
		case IBeXPatternModelPackage.IBE_XARITHMETIC_CONSTRAINT:
			return createIBeXArithmeticConstraint();
		case IBeXPatternModelPackage.IBE_XUNARY_EXPRESSION:
			return createIBeXUnaryExpression();
		case IBeXPatternModelPackage.IBE_XBINARY_EXPRESSION:
			return createIBeXBinaryExpression();
		case IBeXPatternModelPackage.IBE_XARITHMETIC_VALUE_LITERAL:
			return createIBeXArithmeticValueLiteral();
		case IBeXPatternModelPackage.IBE_XPROBABILITY_DISTRIBUTION:
			return createIBeXProbabilityDistribution();
		case IBeXPatternModelPackage.IBE_XARITHMETIC_ATTRIBUTE:
			return createIBeXArithmeticAttribute();
		case IBeXPatternModelPackage.IBE_XMATCH_COUNT:
			return createIBeXMatchCount();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
		case IBeXPatternModelPackage.IBE_XRELATION:
			return createIBeXRelationFromString(eDataType, initialValue);
		case IBeXPatternModelPackage.IBE_XBINARY_OPERATOR:
			return createIBeXBinaryOperatorFromString(eDataType, initialValue);
		case IBeXPatternModelPackage.IBE_XUNARY_OPERATOR:
			return createIBeXUnaryOperatorFromString(eDataType, initialValue);
		case IBeXPatternModelPackage.IBE_XDISTRIBUTION_TYPE:
			return createIBeXDistributionTypeFromString(eDataType, initialValue);
		case IBeXPatternModelPackage.IBE_XDISTRIBUTION_RANGE:
			return createIBeXDistributionRangeFromString(eDataType, initialValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
		case IBeXPatternModelPackage.IBE_XRELATION:
			return convertIBeXRelationToString(eDataType, instanceValue);
		case IBeXPatternModelPackage.IBE_XBINARY_OPERATOR:
			return convertIBeXBinaryOperatorToString(eDataType, instanceValue);
		case IBeXPatternModelPackage.IBE_XUNARY_OPERATOR:
			return convertIBeXUnaryOperatorToString(eDataType, instanceValue);
		case IBeXPatternModelPackage.IBE_XDISTRIBUTION_TYPE:
			return convertIBeXDistributionTypeToString(eDataType, instanceValue);
		case IBeXPatternModelPackage.IBE_XDISTRIBUTION_RANGE:
			return convertIBeXDistributionRangeToString(eDataType, instanceValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXModel createIBeXModel() {
		IBeXModelImpl iBeXModel = new IBeXModelImpl();
		return iBeXModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXPatternSet createIBeXPatternSet() {
		IBeXPatternSetImpl iBeXPatternSet = new IBeXPatternSetImpl();
		return iBeXPatternSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXRuleSet createIBeXRuleSet() {
		IBeXRuleSetImpl iBeXRuleSet = new IBeXRuleSetImpl();
		return iBeXRuleSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXNodeSet createIBeXNodeSet() {
		IBeXNodeSetImpl iBeXNodeSet = new IBeXNodeSetImpl();
		return iBeXNodeSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXEdgeSet createIBeXEdgeSet() {
		IBeXEdgeSetImpl iBeXEdgeSet = new IBeXEdgeSetImpl();
		return iBeXEdgeSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXContextAlternatives createIBeXContextAlternatives() {
		IBeXContextAlternativesImpl iBeXContextAlternatives = new IBeXContextAlternativesImpl();
		return iBeXContextAlternatives;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXContextPattern createIBeXContextPattern() {
		IBeXContextPatternImpl iBeXContextPattern = new IBeXContextPatternImpl();
		return iBeXContextPattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXCreatePattern createIBeXCreatePattern() {
		IBeXCreatePatternImpl iBeXCreatePattern = new IBeXCreatePatternImpl();
		return iBeXCreatePattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXDeletePattern createIBeXDeletePattern() {
		IBeXDeletePatternImpl iBeXDeletePattern = new IBeXDeletePatternImpl();
		return iBeXDeletePattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXNode createIBeXNode() {
		IBeXNodeImpl iBeXNode = new IBeXNodeImpl();
		return iBeXNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXEdge createIBeXEdge() {
		IBeXEdgeImpl iBeXEdge = new IBeXEdgeImpl();
		return iBeXEdge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXParameter createIBeXParameter() {
		IBeXParameterImpl iBeXParameter = new IBeXParameterImpl();
		return iBeXParameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXAttributeAssignment createIBeXAttributeAssignment() {
		IBeXAttributeAssignmentImpl iBeXAttributeAssignment = new IBeXAttributeAssignmentImpl();
		return iBeXAttributeAssignment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXAttributeConstraint createIBeXAttributeConstraint() {
		IBeXAttributeConstraintImpl iBeXAttributeConstraint = new IBeXAttributeConstraintImpl();
		return iBeXAttributeConstraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXAttributeValue createIBeXAttributeValue() {
		IBeXAttributeValueImpl iBeXAttributeValue = new IBeXAttributeValueImpl();
		return iBeXAttributeValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXAttributeParameter createIBeXAttributeParameter() {
		IBeXAttributeParameterImpl iBeXAttributeParameter = new IBeXAttributeParameterImpl();
		return iBeXAttributeParameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXConstant createIBeXConstant() {
		IBeXConstantImpl iBeXConstant = new IBeXConstantImpl();
		return iBeXConstant;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXEnumLiteral createIBeXEnumLiteral() {
		IBeXEnumLiteralImpl iBeXEnumLiteral = new IBeXEnumLiteralImpl();
		return iBeXEnumLiteral;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXAttributeExpression createIBeXAttributeExpression() {
		IBeXAttributeExpressionImpl iBeXAttributeExpression = new IBeXAttributeExpressionImpl();
		return iBeXAttributeExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXStochasticAttributeValue createIBeXStochasticAttributeValue() {
		IBeXStochasticAttributeValueImpl iBeXStochasticAttributeValue = new IBeXStochasticAttributeValueImpl();
		return iBeXStochasticAttributeValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXArithmeticValue createIBeXArithmeticValue() {
		IBeXArithmeticValueImpl iBeXArithmeticValue = new IBeXArithmeticValueImpl();
		return iBeXArithmeticValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXInjectivityConstraint createIBeXInjectivityConstraint() {
		IBeXInjectivityConstraintImpl iBeXInjectivityConstraint = new IBeXInjectivityConstraintImpl();
		return iBeXInjectivityConstraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXPatternInvocation createIBeXPatternInvocation() {
		IBeXPatternInvocationImpl iBeXPatternInvocation = new IBeXPatternInvocationImpl();
		return iBeXPatternInvocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<IBeXNode, IBeXNode> createIBeXNodeToNodeMapping() {
		IBeXNodeToNodeMappingImpl iBeXNodeToNodeMapping = new IBeXNodeToNodeMappingImpl();
		return iBeXNodeToNodeMapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXCSP createIBeXCSP() {
		IBeXCSPImpl iBeXCSP = new IBeXCSPImpl();
		return iBeXCSP;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXRule createIBeXRule() {
		IBeXRuleImpl iBeXRule = new IBeXRuleImpl();
		return iBeXRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXProbability createIBeXProbability() {
		IBeXProbabilityImpl iBeXProbability = new IBeXProbabilityImpl();
		return iBeXProbability;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXArithmeticConstraint createIBeXArithmeticConstraint() {
		IBeXArithmeticConstraintImpl iBeXArithmeticConstraint = new IBeXArithmeticConstraintImpl();
		return iBeXArithmeticConstraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXUnaryExpression createIBeXUnaryExpression() {
		IBeXUnaryExpressionImpl iBeXUnaryExpression = new IBeXUnaryExpressionImpl();
		return iBeXUnaryExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXBinaryExpression createIBeXBinaryExpression() {
		IBeXBinaryExpressionImpl iBeXBinaryExpression = new IBeXBinaryExpressionImpl();
		return iBeXBinaryExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXArithmeticValueLiteral createIBeXArithmeticValueLiteral() {
		IBeXArithmeticValueLiteralImpl iBeXArithmeticValueLiteral = new IBeXArithmeticValueLiteralImpl();
		return iBeXArithmeticValueLiteral;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXProbabilityDistribution createIBeXProbabilityDistribution() {
		IBeXProbabilityDistributionImpl iBeXProbabilityDistribution = new IBeXProbabilityDistributionImpl();
		return iBeXProbabilityDistribution;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXArithmeticAttribute createIBeXArithmeticAttribute() {
		IBeXArithmeticAttributeImpl iBeXArithmeticAttribute = new IBeXArithmeticAttributeImpl();
		return iBeXArithmeticAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXMatchCount createIBeXMatchCount() {
		IBeXMatchCountImpl iBeXMatchCount = new IBeXMatchCountImpl();
		return iBeXMatchCount;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXRelation createIBeXRelationFromString(EDataType eDataType, String initialValue) {
		IBeXRelation result = IBeXRelation.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertIBeXRelationToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXBinaryOperator createIBeXBinaryOperatorFromString(EDataType eDataType, String initialValue) {
		IBeXBinaryOperator result = IBeXBinaryOperator.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertIBeXBinaryOperatorToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXUnaryOperator createIBeXUnaryOperatorFromString(EDataType eDataType, String initialValue) {
		IBeXUnaryOperator result = IBeXUnaryOperator.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertIBeXUnaryOperatorToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXDistributionType createIBeXDistributionTypeFromString(EDataType eDataType, String initialValue) {
		IBeXDistributionType result = IBeXDistributionType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertIBeXDistributionTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXDistributionRange createIBeXDistributionRangeFromString(EDataType eDataType, String initialValue) {
		IBeXDistributionRange result = IBeXDistributionRange.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertIBeXDistributionRangeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXPatternModelPackage getIBeXPatternModelPackage() {
		return (IBeXPatternModelPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static IBeXPatternModelPackage getPackage() {
		return IBeXPatternModelPackage.eINSTANCE;
	}

} //IBeXPatternModelFactoryImpl
