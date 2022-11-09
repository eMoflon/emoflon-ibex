/**
 */
package org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ArithmeticExpression;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ArithmeticValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.BinaryExpression;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.BinaryOperator;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.BooleanBinaryExpression;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.BooleanBinaryOperator;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.BooleanExpression;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.BooleanUnaryExpression;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.BooleanUnaryOperator;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.BooleanValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.DoubleLiteral;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.IBeXCoreArithmeticFactory;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.IBeXCoreArithmeticPackage;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.IntegerLiteral;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.RelationalExpression;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.RelationalOperator;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.UnaryExpression;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.UnaryOperator;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ValueExpression;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXCoreModelPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class IBeXCoreArithmeticPackageImpl extends EPackageImpl implements IBeXCoreArithmeticPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass valueExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass arithmeticExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass unaryExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass binaryExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass arithmeticValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass doubleLiteralEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass integerLiteralEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass booleanExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass booleanBinaryExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass booleanUnaryExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass booleanValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass relationalExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum binaryOperatorEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum unaryOperatorEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum booleanBinaryOperatorEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum booleanUnaryOperatorEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum relationalOperatorEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.IBeXCoreArithmeticPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private IBeXCoreArithmeticPackageImpl() {
		super(eNS_URI, IBeXCoreArithmeticFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link IBeXCoreArithmeticPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static IBeXCoreArithmeticPackage init() {
		if (isInited)
			return (IBeXCoreArithmeticPackage) EPackage.Registry.INSTANCE
					.getEPackage(IBeXCoreArithmeticPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredIBeXCoreArithmeticPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		IBeXCoreArithmeticPackageImpl theIBeXCoreArithmeticPackage = registeredIBeXCoreArithmeticPackage instanceof IBeXCoreArithmeticPackageImpl
				? (IBeXCoreArithmeticPackageImpl) registeredIBeXCoreArithmeticPackage
				: new IBeXCoreArithmeticPackageImpl();

		isInited = true;

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(IBeXCoreModelPackage.eNS_URI);
		IBeXCoreModelPackageImpl theIBeXCoreModelPackage = (IBeXCoreModelPackageImpl) (registeredPackage instanceof IBeXCoreModelPackageImpl
				? registeredPackage
				: IBeXCoreModelPackage.eINSTANCE);

		// Create package meta-data objects
		theIBeXCoreArithmeticPackage.createPackageContents();
		theIBeXCoreModelPackage.createPackageContents();

		// Initialize created meta-data
		theIBeXCoreArithmeticPackage.initializePackageContents();
		theIBeXCoreModelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theIBeXCoreArithmeticPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(IBeXCoreArithmeticPackage.eNS_URI, theIBeXCoreArithmeticPackage);
		return theIBeXCoreArithmeticPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getValueExpression() {
		return valueExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getValueExpression_Type() {
		return (EReference) valueExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getArithmeticExpression() {
		return arithmeticExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUnaryExpression() {
		return unaryExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getUnaryExpression_Operand() {
		return (EReference) unaryExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUnaryExpression_Operator() {
		return (EAttribute) unaryExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBinaryExpression() {
		return binaryExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBinaryExpression_Lhs() {
		return (EReference) binaryExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBinaryExpression_Rhs() {
		return (EReference) binaryExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBinaryExpression_Operator() {
		return (EAttribute) binaryExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getArithmeticValue() {
		return arithmeticValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDoubleLiteral() {
		return doubleLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDoubleLiteral_Value() {
		return (EAttribute) doubleLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIntegerLiteral() {
		return integerLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIntegerLiteral_Value() {
		return (EAttribute) integerLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBooleanExpression() {
		return booleanExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBooleanBinaryExpression() {
		return booleanBinaryExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBooleanBinaryExpression_Lhs() {
		return (EReference) booleanBinaryExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBooleanBinaryExpression_Rhs() {
		return (EReference) booleanBinaryExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBooleanBinaryExpression_Operator() {
		return (EAttribute) booleanBinaryExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBooleanUnaryExpression() {
		return booleanUnaryExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBooleanUnaryExpression_Operand() {
		return (EReference) booleanUnaryExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBooleanUnaryExpression_Operator() {
		return (EAttribute) booleanUnaryExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBooleanValue() {
		return booleanValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRelationalExpression() {
		return relationalExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRelationalExpression_Lhs() {
		return (EReference) relationalExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRelationalExpression_Rhs() {
		return (EReference) relationalExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRelationalExpression_Operator() {
		return (EAttribute) relationalExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getBinaryOperator() {
		return binaryOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getUnaryOperator() {
		return unaryOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getBooleanBinaryOperator() {
		return booleanBinaryOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getBooleanUnaryOperator() {
		return booleanUnaryOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getRelationalOperator() {
		return relationalOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXCoreArithmeticFactory getIBeXCoreArithmeticFactory() {
		return (IBeXCoreArithmeticFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		valueExpressionEClass = createEClass(VALUE_EXPRESSION);
		createEReference(valueExpressionEClass, VALUE_EXPRESSION__TYPE);

		arithmeticExpressionEClass = createEClass(ARITHMETIC_EXPRESSION);

		unaryExpressionEClass = createEClass(UNARY_EXPRESSION);
		createEReference(unaryExpressionEClass, UNARY_EXPRESSION__OPERAND);
		createEAttribute(unaryExpressionEClass, UNARY_EXPRESSION__OPERATOR);

		binaryExpressionEClass = createEClass(BINARY_EXPRESSION);
		createEReference(binaryExpressionEClass, BINARY_EXPRESSION__LHS);
		createEReference(binaryExpressionEClass, BINARY_EXPRESSION__RHS);
		createEAttribute(binaryExpressionEClass, BINARY_EXPRESSION__OPERATOR);

		arithmeticValueEClass = createEClass(ARITHMETIC_VALUE);

		doubleLiteralEClass = createEClass(DOUBLE_LITERAL);
		createEAttribute(doubleLiteralEClass, DOUBLE_LITERAL__VALUE);

		integerLiteralEClass = createEClass(INTEGER_LITERAL);
		createEAttribute(integerLiteralEClass, INTEGER_LITERAL__VALUE);

		booleanExpressionEClass = createEClass(BOOLEAN_EXPRESSION);

		booleanBinaryExpressionEClass = createEClass(BOOLEAN_BINARY_EXPRESSION);
		createEReference(booleanBinaryExpressionEClass, BOOLEAN_BINARY_EXPRESSION__LHS);
		createEReference(booleanBinaryExpressionEClass, BOOLEAN_BINARY_EXPRESSION__RHS);
		createEAttribute(booleanBinaryExpressionEClass, BOOLEAN_BINARY_EXPRESSION__OPERATOR);

		booleanUnaryExpressionEClass = createEClass(BOOLEAN_UNARY_EXPRESSION);
		createEReference(booleanUnaryExpressionEClass, BOOLEAN_UNARY_EXPRESSION__OPERAND);
		createEAttribute(booleanUnaryExpressionEClass, BOOLEAN_UNARY_EXPRESSION__OPERATOR);

		booleanValueEClass = createEClass(BOOLEAN_VALUE);

		relationalExpressionEClass = createEClass(RELATIONAL_EXPRESSION);
		createEReference(relationalExpressionEClass, RELATIONAL_EXPRESSION__LHS);
		createEReference(relationalExpressionEClass, RELATIONAL_EXPRESSION__RHS);
		createEAttribute(relationalExpressionEClass, RELATIONAL_EXPRESSION__OPERATOR);

		// Create enums
		binaryOperatorEEnum = createEEnum(BINARY_OPERATOR);
		unaryOperatorEEnum = createEEnum(UNARY_OPERATOR);
		booleanBinaryOperatorEEnum = createEEnum(BOOLEAN_BINARY_OPERATOR);
		booleanUnaryOperatorEEnum = createEEnum(BOOLEAN_UNARY_OPERATOR);
		relationalOperatorEEnum = createEEnum(RELATIONAL_OPERATOR);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		arithmeticExpressionEClass.getESuperTypes().add(this.getValueExpression());
		unaryExpressionEClass.getESuperTypes().add(this.getArithmeticExpression());
		binaryExpressionEClass.getESuperTypes().add(this.getArithmeticExpression());
		arithmeticValueEClass.getESuperTypes().add(this.getArithmeticExpression());
		doubleLiteralEClass.getESuperTypes().add(this.getArithmeticValue());
		integerLiteralEClass.getESuperTypes().add(this.getArithmeticValue());
		booleanBinaryExpressionEClass.getESuperTypes().add(this.getBooleanExpression());
		booleanUnaryExpressionEClass.getESuperTypes().add(this.getBooleanExpression());
		booleanValueEClass.getESuperTypes().add(this.getBooleanExpression());
		relationalExpressionEClass.getESuperTypes().add(this.getBooleanExpression());

		// Initialize classes, features, and operations; add parameters
		initEClass(valueExpressionEClass, ValueExpression.class, "ValueExpression", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getValueExpression_Type(), ecorePackage.getEClassifier(), null, "type", null, 0, 1,
				ValueExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(arithmeticExpressionEClass, ArithmeticExpression.class, "ArithmeticExpression", IS_ABSTRACT,
				IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(unaryExpressionEClass, UnaryExpression.class, "UnaryExpression", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getUnaryExpression_Operand(), this.getArithmeticExpression(), null, "operand", null, 1, 1,
				UnaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUnaryExpression_Operator(), this.getUnaryOperator(), "operator", null, 0, 1,
				UnaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(binaryExpressionEClass, BinaryExpression.class, "BinaryExpression", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBinaryExpression_Lhs(), this.getArithmeticExpression(), null, "lhs", null, 1, 1,
				BinaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBinaryExpression_Rhs(), this.getArithmeticExpression(), null, "rhs", null, 1, 1,
				BinaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBinaryExpression_Operator(), this.getBinaryOperator(), "operator", null, 0, 1,
				BinaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(arithmeticValueEClass, ArithmeticValue.class, "ArithmeticValue", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(doubleLiteralEClass, DoubleLiteral.class, "DoubleLiteral", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDoubleLiteral_Value(), ecorePackage.getEDouble(), "value", null, 0, 1, DoubleLiteral.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(integerLiteralEClass, IntegerLiteral.class, "IntegerLiteral", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIntegerLiteral_Value(), ecorePackage.getEInt(), "value", null, 0, 1, IntegerLiteral.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(booleanExpressionEClass, BooleanExpression.class, "BooleanExpression", IS_ABSTRACT, IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(booleanBinaryExpressionEClass, BooleanBinaryExpression.class, "BooleanBinaryExpression",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBooleanBinaryExpression_Lhs(), this.getBooleanExpression(), null, "lhs", null, 1, 1,
				BooleanBinaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBooleanBinaryExpression_Rhs(), this.getBooleanExpression(), null, "rhs", null, 1, 1,
				BooleanBinaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBooleanBinaryExpression_Operator(), this.getBooleanBinaryOperator(), "operator", null, 0, 1,
				BooleanBinaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(booleanUnaryExpressionEClass, BooleanUnaryExpression.class, "BooleanUnaryExpression", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBooleanUnaryExpression_Operand(), this.getBooleanExpression(), null, "operand", null, 1, 1,
				BooleanUnaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBooleanUnaryExpression_Operator(), this.getBooleanUnaryOperator(), "operator", null, 0, 1,
				BooleanUnaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(booleanValueEClass, BooleanValue.class, "BooleanValue", IS_ABSTRACT, IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(relationalExpressionEClass, RelationalExpression.class, "RelationalExpression", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRelationalExpression_Lhs(), this.getValueExpression(), null, "lhs", null, 1, 1,
				RelationalExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRelationalExpression_Rhs(), this.getValueExpression(), null, "rhs", null, 1, 1,
				RelationalExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRelationalExpression_Operator(), this.getRelationalOperator(), "operator", null, 0, 1,
				RelationalExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(binaryOperatorEEnum, BinaryOperator.class, "BinaryOperator");
		addEEnumLiteral(binaryOperatorEEnum, BinaryOperator.ADD);
		addEEnumLiteral(binaryOperatorEEnum, BinaryOperator.MULTIPLY);
		addEEnumLiteral(binaryOperatorEEnum, BinaryOperator.DIVIDE);
		addEEnumLiteral(binaryOperatorEEnum, BinaryOperator.MOD);
		addEEnumLiteral(binaryOperatorEEnum, BinaryOperator.POW);
		addEEnumLiteral(binaryOperatorEEnum, BinaryOperator.SUBTRACT);
		addEEnumLiteral(binaryOperatorEEnum, BinaryOperator.MIN);
		addEEnumLiteral(binaryOperatorEEnum, BinaryOperator.MAX);
		addEEnumLiteral(binaryOperatorEEnum, BinaryOperator.NORMAL_DISTRIBUTION);
		addEEnumLiteral(binaryOperatorEEnum, BinaryOperator.UNIFORM_DISTRIBUTION);
		addEEnumLiteral(binaryOperatorEEnum, BinaryOperator.LOG);

		initEEnum(unaryOperatorEEnum, UnaryOperator.class, "UnaryOperator");
		addEEnumLiteral(unaryOperatorEEnum, UnaryOperator.SQRT);
		addEEnumLiteral(unaryOperatorEEnum, UnaryOperator.ABSOLUTE);
		addEEnumLiteral(unaryOperatorEEnum, UnaryOperator.SIN);
		addEEnumLiteral(unaryOperatorEEnum, UnaryOperator.COS);
		addEEnumLiteral(unaryOperatorEEnum, UnaryOperator.TAN);
		addEEnumLiteral(unaryOperatorEEnum, UnaryOperator.BRACKET);
		addEEnumLiteral(unaryOperatorEEnum, UnaryOperator.NEGATIVE);
		addEEnumLiteral(unaryOperatorEEnum, UnaryOperator.EXPONENTIAL_DISTRIBUTION);

		initEEnum(booleanBinaryOperatorEEnum, BooleanBinaryOperator.class, "BooleanBinaryOperator");
		addEEnumLiteral(booleanBinaryOperatorEEnum, BooleanBinaryOperator.IMPLICATION);
		addEEnumLiteral(booleanBinaryOperatorEEnum, BooleanBinaryOperator.AND);
		addEEnumLiteral(booleanBinaryOperatorEEnum, BooleanBinaryOperator.OR);
		addEEnumLiteral(booleanBinaryOperatorEEnum, BooleanBinaryOperator.XOR);

		initEEnum(booleanUnaryOperatorEEnum, BooleanUnaryOperator.class, "BooleanUnaryOperator");
		addEEnumLiteral(booleanUnaryOperatorEEnum, BooleanUnaryOperator.NEGATION);
		addEEnumLiteral(booleanUnaryOperatorEEnum, BooleanUnaryOperator.BRACKET);

		initEEnum(relationalOperatorEEnum, RelationalOperator.class, "RelationalOperator");
		addEEnumLiteral(relationalOperatorEEnum, RelationalOperator.GREATER_OR_EQUAL);
		addEEnumLiteral(relationalOperatorEEnum, RelationalOperator.GREATER);
		addEEnumLiteral(relationalOperatorEEnum, RelationalOperator.EQUAL);
		addEEnumLiteral(relationalOperatorEEnum, RelationalOperator.UNEQUAL);
		addEEnumLiteral(relationalOperatorEEnum, RelationalOperator.SMALLER_OR_EQUAL);
		addEEnumLiteral(relationalOperatorEEnum, RelationalOperator.SMALLER);
		addEEnumLiteral(relationalOperatorEEnum, RelationalOperator.OBJECT_EQUALS);
		addEEnumLiteral(relationalOperatorEEnum, RelationalOperator.OBJECT_NOT_EQUALS);
		addEEnumLiteral(relationalOperatorEEnum, RelationalOperator.OBJECT_GREATER_OR_EQUAL);
		addEEnumLiteral(relationalOperatorEEnum, RelationalOperator.OBJECT_GREATER);
		addEEnumLiteral(relationalOperatorEEnum, RelationalOperator.OBJECT_SMALLER_OR_EQUAL);
		addEEnumLiteral(relationalOperatorEEnum, RelationalOperator.OBJECT_SMALLER);
	}

} //IBeXCoreArithmeticPackageImpl
