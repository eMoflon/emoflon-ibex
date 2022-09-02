/**
 */
package org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class IBeXCoreArithmeticFactoryImpl extends EFactoryImpl implements IBeXCoreArithmeticFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static IBeXCoreArithmeticFactory init() {
		try {
			IBeXCoreArithmeticFactory theIBeXCoreArithmeticFactory = (IBeXCoreArithmeticFactory) EPackage.Registry.INSTANCE
					.getEFactory(IBeXCoreArithmeticPackage.eNS_URI);
			if (theIBeXCoreArithmeticFactory != null) {
				return theIBeXCoreArithmeticFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new IBeXCoreArithmeticFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXCoreArithmeticFactoryImpl() {
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
		case IBeXCoreArithmeticPackage.UNARY_EXPRESSION:
			return createUnaryExpression();
		case IBeXCoreArithmeticPackage.BINARY_EXPRESSION:
			return createBinaryExpression();
		case IBeXCoreArithmeticPackage.DOUBLE_LITERAL:
			return createDoubleLiteral();
		case IBeXCoreArithmeticPackage.INTEGER_LITERAL:
			return createIntegerLiteral();
		case IBeXCoreArithmeticPackage.RELATIONAL_EXPRESSION:
			return createRelationalExpression();
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
		case IBeXCoreArithmeticPackage.BINARY_OPERATOR:
			return createBinaryOperatorFromString(eDataType, initialValue);
		case IBeXCoreArithmeticPackage.UNARY_OPERATOR:
			return createUnaryOperatorFromString(eDataType, initialValue);
		case IBeXCoreArithmeticPackage.RELATIONAL_OPERATOR:
			return createRelationalOperatorFromString(eDataType, initialValue);
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
		case IBeXCoreArithmeticPackage.BINARY_OPERATOR:
			return convertBinaryOperatorToString(eDataType, instanceValue);
		case IBeXCoreArithmeticPackage.UNARY_OPERATOR:
			return convertUnaryOperatorToString(eDataType, instanceValue);
		case IBeXCoreArithmeticPackage.RELATIONAL_OPERATOR:
			return convertRelationalOperatorToString(eDataType, instanceValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UnaryExpression createUnaryExpression() {
		UnaryExpressionImpl unaryExpression = new UnaryExpressionImpl();
		return unaryExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BinaryExpression createBinaryExpression() {
		BinaryExpressionImpl binaryExpression = new BinaryExpressionImpl();
		return binaryExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DoubleLiteral createDoubleLiteral() {
		DoubleLiteralImpl doubleLiteral = new DoubleLiteralImpl();
		return doubleLiteral;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IntegerLiteral createIntegerLiteral() {
		IntegerLiteralImpl integerLiteral = new IntegerLiteralImpl();
		return integerLiteral;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RelationalExpression createRelationalExpression() {
		RelationalExpressionImpl relationalExpression = new RelationalExpressionImpl();
		return relationalExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BinaryOperator createBinaryOperatorFromString(EDataType eDataType, String initialValue) {
		BinaryOperator result = BinaryOperator.get(initialValue);
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
	public String convertBinaryOperatorToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UnaryOperator createUnaryOperatorFromString(EDataType eDataType, String initialValue) {
		UnaryOperator result = UnaryOperator.get(initialValue);
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
	public String convertUnaryOperatorToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RelationalOperator createRelationalOperatorFromString(EDataType eDataType, String initialValue) {
		RelationalOperator result = RelationalOperator.get(initialValue);
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
	public String convertRelationalOperatorToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXCoreArithmeticPackage getIBeXCoreArithmeticPackage() {
		return (IBeXCoreArithmeticPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static IBeXCoreArithmeticPackage getPackage() {
		return IBeXCoreArithmeticPackage.eINSTANCE;
	}

} //IBeXCoreArithmeticFactoryImpl
