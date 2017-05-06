/**
 */
package language.inplaceAttributes.impl;

import language.inplaceAttributes.*;

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
public class InplaceAttributesFactoryImpl extends EFactoryImpl implements InplaceAttributesFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static InplaceAttributesFactory init() {
		try {
			InplaceAttributesFactory theInplaceAttributesFactory = (InplaceAttributesFactory) EPackage.Registry.INSTANCE
					.getEFactory(InplaceAttributesPackage.eNS_URI);
			if (theInplaceAttributesFactory != null) {
				return theInplaceAttributesFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new InplaceAttributesFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InplaceAttributesFactoryImpl() {
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
		case InplaceAttributesPackage.TGG_INPLACE_ATTRIBUTE_EXPRESSION:
			return createTGGInplaceAttributeExpression();
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
		case InplaceAttributesPackage.TGG_ATTRIBUTE_CONSTRAINT_OPERATORS:
			return createTGGAttributeConstraintOperatorsFromString(eDataType, initialValue);
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
		case InplaceAttributesPackage.TGG_ATTRIBUTE_CONSTRAINT_OPERATORS:
			return convertTGGAttributeConstraintOperatorsToString(eDataType, instanceValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGInplaceAttributeExpression createTGGInplaceAttributeExpression() {
		TGGInplaceAttributeExpressionImpl tggInplaceAttributeExpression = new TGGInplaceAttributeExpressionImpl();
		return tggInplaceAttributeExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGAttributeConstraintOperators createTGGAttributeConstraintOperatorsFromString(EDataType eDataType,
			String initialValue) {
		TGGAttributeConstraintOperators result = TGGAttributeConstraintOperators.get(initialValue);
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
	public String convertTGGAttributeConstraintOperatorsToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InplaceAttributesPackage getInplaceAttributesPackage() {
		return (InplaceAttributesPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static InplaceAttributesPackage getPackage() {
		return InplaceAttributesPackage.eINSTANCE;
	}

} //InplaceAttributesFactoryImpl
