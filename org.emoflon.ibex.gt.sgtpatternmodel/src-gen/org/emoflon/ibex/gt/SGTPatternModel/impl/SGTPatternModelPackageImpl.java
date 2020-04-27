/**
 */
package org.emoflon.ibex.gt.SGTPatternModel.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.emoflon.ibex.gt.SGTPatternModel.GTArithmetics;
import org.emoflon.ibex.gt.SGTPatternModel.GTAttribute;
import org.emoflon.ibex.gt.SGTPatternModel.GTNumber;
import org.emoflon.ibex.gt.SGTPatternModel.GTOneParameterCalculation;
import org.emoflon.ibex.gt.SGTPatternModel.GTRelation;
import org.emoflon.ibex.gt.SGTPatternModel.GTStochasticDistribution;
import org.emoflon.ibex.gt.SGTPatternModel.GTStochasticFunction;
import org.emoflon.ibex.gt.SGTPatternModel.GTStochasticNamedElement;
import org.emoflon.ibex.gt.SGTPatternModel.GTStochasticNode;
import org.emoflon.ibex.gt.SGTPatternModel.GTStochasticRange;
import org.emoflon.ibex.gt.SGTPatternModel.GTTwoParameterCalculation;
import org.emoflon.ibex.gt.SGTPatternModel.OneParameterOperator;
import org.emoflon.ibex.gt.SGTPatternModel.SGTPatternModelFactory;
import org.emoflon.ibex.gt.SGTPatternModel.SGTPatternModelPackage;
import org.emoflon.ibex.gt.SGTPatternModel.TwoParameterOperator;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SGTPatternModelPackageImpl extends EPackageImpl implements SGTPatternModelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass gtStochasticNamedElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass gtStochasticNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass gtAttributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass gtStochasticFunctionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass gtNumberEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass gtArithmeticsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass gtTwoParameterCalculationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass gtOneParameterCalculationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum gtStochasticDistributionEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum gtStochasticRangeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum twoParameterOperatorEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum oneParameterOperatorEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum gtRelationEEnum = null;

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
	 * @see org.emoflon.ibex.gt.SGTPatternModel.SGTPatternModelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private SGTPatternModelPackageImpl() {
		super(eNS_URI, SGTPatternModelFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link SGTPatternModelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static SGTPatternModelPackage init() {
		if (isInited)
			return (SGTPatternModelPackage) EPackage.Registry.INSTANCE.getEPackage(SGTPatternModelPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredSGTPatternModelPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		SGTPatternModelPackageImpl theSGTPatternModelPackage = registeredSGTPatternModelPackage instanceof SGTPatternModelPackageImpl
				? (SGTPatternModelPackageImpl) registeredSGTPatternModelPackage
				: new SGTPatternModelPackageImpl();

		isInited = true;

		// Create package meta-data objects
		theSGTPatternModelPackage.createPackageContents();

		// Initialize created meta-data
		theSGTPatternModelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theSGTPatternModelPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(SGTPatternModelPackage.eNS_URI, theSGTPatternModelPackage);
		return theSGTPatternModelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getGTStochasticNamedElement() {
		return gtStochasticNamedElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getGTStochasticNamedElement_Name() {
		return (EAttribute) gtStochasticNamedElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getGTStochasticNode() {
		return gtStochasticNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getGTStochasticNode_Type() {
		return (EReference) gtStochasticNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getGTAttribute() {
		return gtAttributeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getGTAttribute_Attribute() {
		return (EReference) gtAttributeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getGTAttribute_Negative() {
		return (EAttribute) gtAttributeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getGTStochasticFunction() {
		return gtStochasticFunctionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getGTStochasticFunction_Mean() {
		return (EReference) gtStochasticFunctionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getGTStochasticFunction_Sd() {
		return (EReference) gtStochasticFunctionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getGTStochasticFunction_Distribution() {
		return (EAttribute) gtStochasticFunctionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getGTNumber() {
		return gtNumberEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getGTNumber_Number() {
		return (EAttribute) gtNumberEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getGTArithmetics() {
		return gtArithmeticsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getGTTwoParameterCalculation() {
		return gtTwoParameterCalculationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getGTTwoParameterCalculation_Left() {
		return (EReference) gtTwoParameterCalculationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getGTTwoParameterCalculation_Right() {
		return (EReference) gtTwoParameterCalculationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getGTTwoParameterCalculation_Operator() {
		return (EAttribute) gtTwoParameterCalculationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getGTOneParameterCalculation() {
		return gtOneParameterCalculationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getGTOneParameterCalculation_Value() {
		return (EReference) gtOneParameterCalculationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getGTOneParameterCalculation_Operator() {
		return (EAttribute) gtOneParameterCalculationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getGTOneParameterCalculation_Negative() {
		return (EAttribute) gtOneParameterCalculationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getGTStochasticDistribution() {
		return gtStochasticDistributionEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getGTStochasticRange() {
		return gtStochasticRangeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getTwoParameterOperator() {
		return twoParameterOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getOneParameterOperator() {
		return oneParameterOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getGTRelation() {
		return gtRelationEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGTPatternModelFactory getSGTPatternModelFactory() {
		return (SGTPatternModelFactory) getEFactoryInstance();
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
		gtStochasticNamedElementEClass = createEClass(GT_STOCHASTIC_NAMED_ELEMENT);
		createEAttribute(gtStochasticNamedElementEClass, GT_STOCHASTIC_NAMED_ELEMENT__NAME);

		gtStochasticNodeEClass = createEClass(GT_STOCHASTIC_NODE);
		createEReference(gtStochasticNodeEClass, GT_STOCHASTIC_NODE__TYPE);

		gtAttributeEClass = createEClass(GT_ATTRIBUTE);
		createEReference(gtAttributeEClass, GT_ATTRIBUTE__ATTRIBUTE);
		createEAttribute(gtAttributeEClass, GT_ATTRIBUTE__NEGATIVE);

		gtStochasticFunctionEClass = createEClass(GT_STOCHASTIC_FUNCTION);
		createEReference(gtStochasticFunctionEClass, GT_STOCHASTIC_FUNCTION__MEAN);
		createEReference(gtStochasticFunctionEClass, GT_STOCHASTIC_FUNCTION__SD);
		createEAttribute(gtStochasticFunctionEClass, GT_STOCHASTIC_FUNCTION__DISTRIBUTION);

		gtNumberEClass = createEClass(GT_NUMBER);
		createEAttribute(gtNumberEClass, GT_NUMBER__NUMBER);

		gtArithmeticsEClass = createEClass(GT_ARITHMETICS);

		gtTwoParameterCalculationEClass = createEClass(GT_TWO_PARAMETER_CALCULATION);
		createEReference(gtTwoParameterCalculationEClass, GT_TWO_PARAMETER_CALCULATION__LEFT);
		createEReference(gtTwoParameterCalculationEClass, GT_TWO_PARAMETER_CALCULATION__RIGHT);
		createEAttribute(gtTwoParameterCalculationEClass, GT_TWO_PARAMETER_CALCULATION__OPERATOR);

		gtOneParameterCalculationEClass = createEClass(GT_ONE_PARAMETER_CALCULATION);
		createEReference(gtOneParameterCalculationEClass, GT_ONE_PARAMETER_CALCULATION__VALUE);
		createEAttribute(gtOneParameterCalculationEClass, GT_ONE_PARAMETER_CALCULATION__OPERATOR);
		createEAttribute(gtOneParameterCalculationEClass, GT_ONE_PARAMETER_CALCULATION__NEGATIVE);

		// Create enums
		gtStochasticDistributionEEnum = createEEnum(GT_STOCHASTIC_DISTRIBUTION);
		gtStochasticRangeEEnum = createEEnum(GT_STOCHASTIC_RANGE);
		twoParameterOperatorEEnum = createEEnum(TWO_PARAMETER_OPERATOR);
		oneParameterOperatorEEnum = createEEnum(ONE_PARAMETER_OPERATOR);
		gtRelationEEnum = createEEnum(GT_RELATION);
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
		gtStochasticNodeEClass.getESuperTypes().add(this.getGTStochasticNamedElement());
		gtAttributeEClass.getESuperTypes().add(this.getGTStochasticNode());
		gtAttributeEClass.getESuperTypes().add(this.getGTArithmetics());
		gtNumberEClass.getESuperTypes().add(this.getGTArithmetics());
		gtTwoParameterCalculationEClass.getESuperTypes().add(this.getGTArithmetics());
		gtOneParameterCalculationEClass.getESuperTypes().add(this.getGTArithmetics());

		// Initialize classes, features, and operations; add parameters
		initEClass(gtStochasticNamedElementEClass, GTStochasticNamedElement.class, "GTStochasticNamedElement",
				IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getGTStochasticNamedElement_Name(), ecorePackage.getEString(), "name", null, 0, 1,
				GTStochasticNamedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(gtStochasticNodeEClass, GTStochasticNode.class, "GTStochasticNode", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGTStochasticNode_Type(), ecorePackage.getEClass(), null, "type", null, 0, 1,
				GTStochasticNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(gtAttributeEClass, GTAttribute.class, "GTAttribute", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGTAttribute_Attribute(), ecorePackage.getEAttribute(), null, "attribute", null, 0, 1,
				GTAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGTAttribute_Negative(), ecorePackage.getEBoolean(), "negative", null, 0, 1, GTAttribute.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(gtStochasticFunctionEClass, GTStochasticFunction.class, "GTStochasticFunction", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGTStochasticFunction_Mean(), this.getGTArithmetics(), null, "mean", null, 0, 1,
				GTStochasticFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGTStochasticFunction_Sd(), this.getGTArithmetics(), null, "sd", null, 0, 1,
				GTStochasticFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGTStochasticFunction_Distribution(), this.getGTStochasticDistribution(), "distribution", null,
				0, 1, GTStochasticFunction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(gtNumberEClass, GTNumber.class, "GTNumber", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getGTNumber_Number(), ecorePackage.getEDouble(), "number", null, 0, 1, GTNumber.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(gtArithmeticsEClass, GTArithmetics.class, "GTArithmetics", IS_ABSTRACT, IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(gtTwoParameterCalculationEClass, GTTwoParameterCalculation.class, "GTTwoParameterCalculation",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGTTwoParameterCalculation_Left(), this.getGTArithmetics(), null, "left", null, 0, 1,
				GTTwoParameterCalculation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGTTwoParameterCalculation_Right(), this.getGTArithmetics(), null, "right", null, 0, 1,
				GTTwoParameterCalculation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGTTwoParameterCalculation_Operator(), this.getTwoParameterOperator(), "operator", null, 0, 1,
				GTTwoParameterCalculation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(gtOneParameterCalculationEClass, GTOneParameterCalculation.class, "GTOneParameterCalculation",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGTOneParameterCalculation_Value(), this.getGTArithmetics(), null, "value", null, 0, 1,
				GTOneParameterCalculation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGTOneParameterCalculation_Operator(), this.getOneParameterOperator(), "operator", null, 0, 1,
				GTOneParameterCalculation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGTOneParameterCalculation_Negative(), ecorePackage.getEBoolean(), "negative", null, 0, 1,
				GTOneParameterCalculation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(gtStochasticDistributionEEnum, GTStochasticDistribution.class, "GTStochasticDistribution");
		addEEnumLiteral(gtStochasticDistributionEEnum, GTStochasticDistribution.NORMAL);
		addEEnumLiteral(gtStochasticDistributionEEnum, GTStochasticDistribution.UNIFORM);
		addEEnumLiteral(gtStochasticDistributionEEnum, GTStochasticDistribution.EXPONENTIAL);
		addEEnumLiteral(gtStochasticDistributionEEnum, GTStochasticDistribution.STATIC);

		initEEnum(gtStochasticRangeEEnum, GTStochasticRange.class, "GTStochasticRange");
		addEEnumLiteral(gtStochasticRangeEEnum, GTStochasticRange.NEUTRAL);
		addEEnumLiteral(gtStochasticRangeEEnum, GTStochasticRange.POSITIVE);
		addEEnumLiteral(gtStochasticRangeEEnum, GTStochasticRange.NEGATIVE);

		initEEnum(twoParameterOperatorEEnum, TwoParameterOperator.class, "TwoParameterOperator");
		addEEnumLiteral(twoParameterOperatorEEnum, TwoParameterOperator.ADDITION);
		addEEnumLiteral(twoParameterOperatorEEnum, TwoParameterOperator.MULTIPLICATION);
		addEEnumLiteral(twoParameterOperatorEEnum, TwoParameterOperator.DIVISION);
		addEEnumLiteral(twoParameterOperatorEEnum, TwoParameterOperator.MODULO);
		addEEnumLiteral(twoParameterOperatorEEnum, TwoParameterOperator.EXPONENTIAL);
		addEEnumLiteral(twoParameterOperatorEEnum, TwoParameterOperator.SUBTRACTION);

		initEEnum(oneParameterOperatorEEnum, OneParameterOperator.class, "OneParameterOperator");
		addEEnumLiteral(oneParameterOperatorEEnum, OneParameterOperator.ROOT);
		addEEnumLiteral(oneParameterOperatorEEnum, OneParameterOperator.ABSOLUTE);
		addEEnumLiteral(oneParameterOperatorEEnum, OneParameterOperator.SIN);
		addEEnumLiteral(oneParameterOperatorEEnum, OneParameterOperator.COS);
		addEEnumLiteral(oneParameterOperatorEEnum, OneParameterOperator.TAN);
		addEEnumLiteral(oneParameterOperatorEEnum, OneParameterOperator.EEXPONENTIAL);
		addEEnumLiteral(oneParameterOperatorEEnum, OneParameterOperator.LOGARITHMUS);
		addEEnumLiteral(oneParameterOperatorEEnum, OneParameterOperator.NATLOG);
		addEEnumLiteral(oneParameterOperatorEEnum, OneParameterOperator.BRACKET);

		initEEnum(gtRelationEEnum, GTRelation.class, "GTRelation");
		addEEnumLiteral(gtRelationEEnum, GTRelation.GREATER_OR_EQUAL);
		addEEnumLiteral(gtRelationEEnum, GTRelation.GREATER);
		addEEnumLiteral(gtRelationEEnum, GTRelation.EQUAL);
		addEEnumLiteral(gtRelationEEnum, GTRelation.UNEQUAL);
		addEEnumLiteral(gtRelationEEnum, GTRelation.SMALLER_OR_EQUAL);
		addEEnumLiteral(gtRelationEEnum, GTRelation.SMALLER);

		// Create resource
		createResource(eNS_URI);
	}

} //SGTPatternModelPackageImpl
