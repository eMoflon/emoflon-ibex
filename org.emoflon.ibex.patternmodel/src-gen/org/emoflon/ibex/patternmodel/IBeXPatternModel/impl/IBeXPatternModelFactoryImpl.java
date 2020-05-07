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
		case IBeXPatternModelPackage.IBE_XATTRIBUTE_ASSIGNMENT:
			return createIBeXAttributeAssignment();
		case IBeXPatternModelPackage.IBE_XATTRIBUTE_CONSTRAINT:
			return createIBeXAttributeConstraint();
		case IBeXPatternModelPackage.IBE_XATTRIBUTE_PARAMETER:
			return createIBeXAttributeParameter();
		case IBeXPatternModelPackage.IBE_XATTRIBUTE_VALUE:
			return createIBeXAttributeValue();
		case IBeXPatternModelPackage.IBE_XATTRIBUTE_EXPRESSION:
			return createIBeXAttributeExpression();
		case IBeXPatternModelPackage.IBE_XCONSTANT:
			return createIBeXConstant();
		case IBeXPatternModelPackage.IBE_XCONTEXT_ALTERNATIVES:
			return createIBeXContextAlternatives();
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN:
			return createIBeXContextPattern();
		case IBeXPatternModelPackage.IBE_XCREATE_PATTERN:
			return createIBeXCreatePattern();
		case IBeXPatternModelPackage.IBE_XDELETE_PATTERN:
			return createIBeXDeletePattern();
		case IBeXPatternModelPackage.IBE_XEDGE:
			return createIBeXEdge();
		case IBeXPatternModelPackage.IBE_XENUM_LITERAL:
			return createIBeXEnumLiteral();
		case IBeXPatternModelPackage.IBE_XNODE:
			return createIBeXNode();
		case IBeXPatternModelPackage.IBE_XNODE_PAIR:
			return createIBeXNodePair();
		case IBeXPatternModelPackage.IBE_XNODE_TO_NODE_MAPPING:
			return (EObject) createIBeXNodeToNodeMapping();
		case IBeXPatternModelPackage.IBE_XPATTERN_INVOCATION:
			return createIBeXPatternInvocation();
		case IBeXPatternModelPackage.IBE_XPATTERN_SET:
			return createIBeXPatternSet();
		case IBeXPatternModelPackage.IBE_XCSP:
			return createIBeXCSP();
		case IBeXPatternModelPackage.IBE_XSTOCHASTIC_ATTRIBUTE_VALUE:
			return createIBeXStochasticAttributeValue();
		case IBeXPatternModelPackage.IBE_XARITHMETIC_VALUE:
			return createIBeXArithmeticValue();
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
	public IBeXNodePair createIBeXNodePair() {
		IBeXNodePairImpl iBeXNodePair = new IBeXNodePairImpl();
		return iBeXNodePair;
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
	public IBeXPatternInvocation createIBeXPatternInvocation() {
		IBeXPatternInvocationImpl iBeXPatternInvocation = new IBeXPatternInvocationImpl();
		return iBeXPatternInvocation;
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
