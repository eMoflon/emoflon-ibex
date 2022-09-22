/**
 */
package org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.IBeXCoreArithmeticPackage;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage;

import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTForEachExpression;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTModel;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTParameter;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTParameterValue;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTRule;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTRuleSet;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTWatchDog;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelFactory;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class IBeXGTModelPackageImpl extends EPackageImpl implements IBeXGTModelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass gtModelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass gtRuleSetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass gtRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass gtForEachExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass gtParameterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass gtParameterValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass gtWatchDogEClass = null;

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
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private IBeXGTModelPackageImpl() {
		super(eNS_URI, IBeXGTModelFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link IBeXGTModelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static IBeXGTModelPackage init() {
		if (isInited)
			return (IBeXGTModelPackage) EPackage.Registry.INSTANCE.getEPackage(IBeXGTModelPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredIBeXGTModelPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		IBeXGTModelPackageImpl theIBeXGTModelPackage = registeredIBeXGTModelPackage instanceof IBeXGTModelPackageImpl
				? (IBeXGTModelPackageImpl) registeredIBeXGTModelPackage
				: new IBeXGTModelPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		IBeXCoreModelPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theIBeXGTModelPackage.createPackageContents();

		// Initialize created meta-data
		theIBeXGTModelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theIBeXGTModelPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(IBeXGTModelPackage.eNS_URI, theIBeXGTModelPackage);
		return theIBeXGTModelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGTModel() {
		return gtModelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGTModel_Package() {
		return (EAttribute) gtModelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGTModel_Project() {
		return (EAttribute) gtModelEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGTModel_RuleSet() {
		return (EReference) gtModelEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGTRuleSet() {
		return gtRuleSetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGTRuleSet_Rules() {
		return (EReference) gtRuleSetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGTRule() {
		return gtRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGTRule_ForEachOperations() {
		return (EReference) gtRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGTRule_Probability() {
		return (EReference) gtRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGTRule_Parameters() {
		return (EReference) gtRuleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGTForEachExpression() {
		return gtForEachExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGTForEachExpression_Create() {
		return (EReference) gtForEachExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGTForEachExpression_Delete() {
		return (EReference) gtForEachExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGTForEachExpression_Source() {
		return (EReference) gtForEachExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGTForEachExpression_TrgIterator() {
		return (EReference) gtForEachExpressionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGTForEachExpression_Edge() {
		return (EReference) gtForEachExpressionEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGTParameter() {
		return gtParameterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGTParameter_Type() {
		return (EReference) gtParameterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGTParameterValue() {
		return gtParameterValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGTParameterValue_Parameter() {
		return (EReference) gtParameterValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGTWatchDog() {
		return gtWatchDogEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGTWatchDog_Node() {
		return (EReference) gtWatchDogEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGTWatchDog_Attribute() {
		return (EReference) gtWatchDogEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXGTModelFactory getIBeXGTModelFactory() {
		return (IBeXGTModelFactory) getEFactoryInstance();
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
		gtModelEClass = createEClass(GT_MODEL);
		createEAttribute(gtModelEClass, GT_MODEL__PACKAGE);
		createEAttribute(gtModelEClass, GT_MODEL__PROJECT);
		createEReference(gtModelEClass, GT_MODEL__RULE_SET);

		gtRuleSetEClass = createEClass(GT_RULE_SET);
		createEReference(gtRuleSetEClass, GT_RULE_SET__RULES);

		gtRuleEClass = createEClass(GT_RULE);
		createEReference(gtRuleEClass, GT_RULE__FOR_EACH_OPERATIONS);
		createEReference(gtRuleEClass, GT_RULE__PROBABILITY);
		createEReference(gtRuleEClass, GT_RULE__PARAMETERS);

		gtForEachExpressionEClass = createEClass(GT_FOR_EACH_EXPRESSION);
		createEReference(gtForEachExpressionEClass, GT_FOR_EACH_EXPRESSION__CREATE);
		createEReference(gtForEachExpressionEClass, GT_FOR_EACH_EXPRESSION__DELETE);
		createEReference(gtForEachExpressionEClass, GT_FOR_EACH_EXPRESSION__SOURCE);
		createEReference(gtForEachExpressionEClass, GT_FOR_EACH_EXPRESSION__TRG_ITERATOR);
		createEReference(gtForEachExpressionEClass, GT_FOR_EACH_EXPRESSION__EDGE);

		gtParameterEClass = createEClass(GT_PARAMETER);
		createEReference(gtParameterEClass, GT_PARAMETER__TYPE);

		gtParameterValueEClass = createEClass(GT_PARAMETER_VALUE);
		createEReference(gtParameterValueEClass, GT_PARAMETER_VALUE__PARAMETER);

		gtWatchDogEClass = createEClass(GT_WATCH_DOG);
		createEReference(gtWatchDogEClass, GT_WATCH_DOG__NODE);
		createEReference(gtWatchDogEClass, GT_WATCH_DOG__ATTRIBUTE);
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

		// Obtain other dependent packages
		IBeXCoreModelPackage theIBeXCoreModelPackage = (IBeXCoreModelPackage) EPackage.Registry.INSTANCE
				.getEPackage(IBeXCoreModelPackage.eNS_URI);
		IBeXCoreArithmeticPackage theIBeXCoreArithmeticPackage = (IBeXCoreArithmeticPackage) EPackage.Registry.INSTANCE
				.getEPackage(IBeXCoreArithmeticPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		gtModelEClass.getESuperTypes().add(theIBeXCoreModelPackage.getIBeXModel());
		gtRuleEClass.getESuperTypes().add(theIBeXCoreModelPackage.getIBeXRule());
		gtParameterEClass.getESuperTypes().add(theIBeXCoreModelPackage.getIBeXNamedElement());
		gtParameterValueEClass.getESuperTypes().add(theIBeXCoreArithmeticPackage.getArithmeticValue());

		// Initialize classes, features, and operations; add parameters
		initEClass(gtModelEClass, GTModel.class, "GTModel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getGTModel_Package(), ecorePackage.getEString(), "package", null, 0, 1, GTModel.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGTModel_Project(), ecorePackage.getEString(), "project", null, 0, 1, GTModel.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGTModel_RuleSet(), this.getGTRuleSet(), null, "ruleSet", null, 1, 1, GTModel.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(gtRuleSetEClass, GTRuleSet.class, "GTRuleSet", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGTRuleSet_Rules(), this.getGTRule(), null, "rules", null, 0, -1, GTRuleSet.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(gtRuleEClass, GTRule.class, "GTRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGTRule_ForEachOperations(), this.getGTForEachExpression(), null, "forEachOperations", null, 0,
				-1, GTRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGTRule_Probability(), theIBeXCoreArithmeticPackage.getArithmeticExpression(), null,
				"probability", null, 0, 1, GTRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGTRule_Parameters(), this.getGTParameter(), null, "parameters", null, 0, -1, GTRule.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(gtForEachExpressionEClass, GTForEachExpression.class, "GTForEachExpression", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGTForEachExpression_Create(), theIBeXCoreModelPackage.getIBeXRuleDelta(), null, "create",
				null, 0, 1, GTForEachExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGTForEachExpression_Delete(), theIBeXCoreModelPackage.getIBeXRuleDelta(), null, "delete",
				null, 0, 1, GTForEachExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGTForEachExpression_Source(), theIBeXCoreModelPackage.getIBeXNode(), null, "source", null, 1,
				1, GTForEachExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGTForEachExpression_TrgIterator(), theIBeXCoreModelPackage.getIBeXNode(), null, "trgIterator",
				null, 1, 1, GTForEachExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGTForEachExpression_Edge(), theIBeXCoreModelPackage.getIBeXEdge(), null, "edge", null, 1, 1,
				GTForEachExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(gtParameterEClass, GTParameter.class, "GTParameter", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGTParameter_Type(), ecorePackage.getEDataType(), null, "type", null, 0, 1, GTParameter.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(gtParameterValueEClass, GTParameterValue.class, "GTParameterValue", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGTParameterValue_Parameter(), this.getGTParameter(), null, "parameter", null, 1, 1,
				GTParameterValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(gtWatchDogEClass, GTWatchDog.class, "GTWatchDog", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGTWatchDog_Node(), theIBeXCoreModelPackage.getIBeXNode(), null, "node", null, 1, 1,
				GTWatchDog.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGTWatchDog_Attribute(), ecorePackage.getEAttribute(), null, "attribute", null, 1, 1,
				GTWatchDog.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //IBeXGTModelPackageImpl
