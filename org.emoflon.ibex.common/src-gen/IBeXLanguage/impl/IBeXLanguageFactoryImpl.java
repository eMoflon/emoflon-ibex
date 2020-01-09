/**
 */
package IBeXLanguage.impl;

import IBeXLanguage.*;

import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class IBeXLanguageFactoryImpl extends EFactoryImpl implements IBeXLanguageFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static IBeXLanguageFactory init() {
		try {
			IBeXLanguageFactory theIBeXLanguageFactory = (IBeXLanguageFactory) EPackage.Registry.INSTANCE
					.getEFactory(IBeXLanguagePackage.eNS_URI);
			if (theIBeXLanguageFactory != null) {
				return theIBeXLanguageFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new IBeXLanguageFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXLanguageFactoryImpl() {
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
		case IBeXLanguagePackage.IBE_XATTRIBUTE_ASSIGNMENT:
			return createIBeXAttributeAssignment();
		case IBeXLanguagePackage.IBE_XATTRIBUTE_CONSTRAINT:
			return createIBeXAttributeConstraint();
		case IBeXLanguagePackage.IBE_XATTRIBUTE_PARAMETER:
			return createIBeXAttributeParameter();
		case IBeXLanguagePackage.IBE_XATTRIBUTE_VALUE:
			return createIBeXAttributeValue();
		case IBeXLanguagePackage.IBE_XATTRIBUTE_EXPRESSION:
			return createIBeXAttributeExpression();
		case IBeXLanguagePackage.IBE_XCONSTANT:
			return createIBeXConstant();
		case IBeXLanguagePackage.IBE_XCONTEXT_ALTERNATIVES:
			return createIBeXContextAlternatives();
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN:
			return createIBeXContextPattern();
		case IBeXLanguagePackage.IBE_XCREATE_PATTERN:
			return createIBeXCreatePattern();
		case IBeXLanguagePackage.IBE_XDELETE_PATTERN:
			return createIBeXDeletePattern();
		case IBeXLanguagePackage.IBE_XEDGE:
			return createIBeXEdge();
		case IBeXLanguagePackage.IBE_XENUM_LITERAL:
			return createIBeXEnumLiteral();
		case IBeXLanguagePackage.IBE_XNODE:
			return createIBeXNode();
		case IBeXLanguagePackage.IBE_XNODE_PAIR:
			return createIBeXNodePair();
		case IBeXLanguagePackage.IBE_XNODE_TO_NODE_MAPPING:
			return (EObject) createIBeXNodeToNodeMapping();
		case IBeXLanguagePackage.IBE_XPATTERN_INVOCATION:
			return createIBeXPatternInvocation();
		case IBeXLanguagePackage.IBE_XPATTERN_SET:
			return createIBeXPatternSet();
		case IBeXLanguagePackage.IBE_XCSP:
			return createIBeXCSP();
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
		case IBeXLanguagePackage.IBE_XRELATION:
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
		case IBeXLanguagePackage.IBE_XRELATION:
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
	public IBeXLanguagePackage getIBeXLanguagePackage() {
		return (IBeXLanguagePackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static IBeXLanguagePackage getPackage() {
		return IBeXLanguagePackage.eINSTANCE;
	}

} //IBeXLanguageFactoryImpl
