/**
 */
package org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.IBeXCoreArithmeticPackage;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage;

import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTForEachExpression;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTIteratorAttributeAssignment;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTIteratorAttributeReference;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTIteratorEdge;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTModel;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTParameter;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTParameterValue;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern;
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
	private EClass gtPatternEClass = null;

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
	private EClass gtIteratorEdgeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass gtIteratorAttributeAssignmentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass gtIteratorAttributeReferenceEClass = null;

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
	public EReference getGTModel_RuleSet() {
		return (EReference) gtModelEClass.getEStructuralFeatures().get(0);
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
	public EClass getGTPattern() {
		return gtPatternEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGTPattern_Parameters() {
		return (EReference) gtPatternEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGTPattern_WatchDogs() {
		return (EReference) gtPatternEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGTPattern_UsedFeatures() {
		return (EReference) gtPatternEClass.getEStructuralFeatures().get(2);
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
	public EReference getGTRule_Parameters() {
		return (EReference) gtRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGTRule_ForEachOperations() {
		return (EReference) gtRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGTRule_Probability() {
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
	public EReference getGTForEachExpression_Created() {
		return (EReference) gtForEachExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGTForEachExpression_Deleted() {
		return (EReference) gtForEachExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGTForEachExpression_AttributeAssignments() {
		return (EReference) gtForEachExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGTForEachExpression_Source() {
		return (EReference) gtForEachExpressionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGTForEachExpression_Reference() {
		return (EReference) gtForEachExpressionEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGTForEachExpression_Iterator() {
		return (EReference) gtForEachExpressionEClass.getEStructuralFeatures().get(5);
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
	public EClass getGTIteratorEdge() {
		return gtIteratorEdgeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGTIteratorEdge_Iterator() {
		return (EReference) gtIteratorEdgeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGTIteratorAttributeAssignment() {
		return gtIteratorAttributeAssignmentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGTIteratorAttributeAssignment_Iterator() {
		return (EReference) gtIteratorAttributeAssignmentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGTIteratorAttributeReference() {
		return gtIteratorAttributeReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGTIteratorAttributeReference_Iterator() {
		return (EReference) gtIteratorAttributeReferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGTIteratorAttributeReference_Attribute() {
		return (EReference) gtIteratorAttributeReferenceEClass.getEStructuralFeatures().get(1);
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
		createEReference(gtModelEClass, GT_MODEL__RULE_SET);

		gtRuleSetEClass = createEClass(GT_RULE_SET);
		createEReference(gtRuleSetEClass, GT_RULE_SET__RULES);

		gtPatternEClass = createEClass(GT_PATTERN);
		createEReference(gtPatternEClass, GT_PATTERN__PARAMETERS);
		createEReference(gtPatternEClass, GT_PATTERN__WATCH_DOGS);
		createEReference(gtPatternEClass, GT_PATTERN__USED_FEATURES);

		gtRuleEClass = createEClass(GT_RULE);
		createEReference(gtRuleEClass, GT_RULE__PARAMETERS);
		createEReference(gtRuleEClass, GT_RULE__FOR_EACH_OPERATIONS);
		createEReference(gtRuleEClass, GT_RULE__PROBABILITY);

		gtForEachExpressionEClass = createEClass(GT_FOR_EACH_EXPRESSION);
		createEReference(gtForEachExpressionEClass, GT_FOR_EACH_EXPRESSION__CREATED);
		createEReference(gtForEachExpressionEClass, GT_FOR_EACH_EXPRESSION__DELETED);
		createEReference(gtForEachExpressionEClass, GT_FOR_EACH_EXPRESSION__ATTRIBUTE_ASSIGNMENTS);
		createEReference(gtForEachExpressionEClass, GT_FOR_EACH_EXPRESSION__SOURCE);
		createEReference(gtForEachExpressionEClass, GT_FOR_EACH_EXPRESSION__REFERENCE);
		createEReference(gtForEachExpressionEClass, GT_FOR_EACH_EXPRESSION__ITERATOR);

		gtParameterEClass = createEClass(GT_PARAMETER);
		createEReference(gtParameterEClass, GT_PARAMETER__TYPE);

		gtParameterValueEClass = createEClass(GT_PARAMETER_VALUE);
		createEReference(gtParameterValueEClass, GT_PARAMETER_VALUE__PARAMETER);

		gtIteratorEdgeEClass = createEClass(GT_ITERATOR_EDGE);
		createEReference(gtIteratorEdgeEClass, GT_ITERATOR_EDGE__ITERATOR);

		gtIteratorAttributeAssignmentEClass = createEClass(GT_ITERATOR_ATTRIBUTE_ASSIGNMENT);
		createEReference(gtIteratorAttributeAssignmentEClass, GT_ITERATOR_ATTRIBUTE_ASSIGNMENT__ITERATOR);

		gtIteratorAttributeReferenceEClass = createEClass(GT_ITERATOR_ATTRIBUTE_REFERENCE);
		createEReference(gtIteratorAttributeReferenceEClass, GT_ITERATOR_ATTRIBUTE_REFERENCE__ITERATOR);
		createEReference(gtIteratorAttributeReferenceEClass, GT_ITERATOR_ATTRIBUTE_REFERENCE__ATTRIBUTE);

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
		gtPatternEClass.getESuperTypes().add(theIBeXCoreModelPackage.getIBeXPattern());
		gtRuleEClass.getESuperTypes().add(theIBeXCoreModelPackage.getIBeXRule());
		gtParameterEClass.getESuperTypes().add(theIBeXCoreModelPackage.getIBeXNamedElement());
		gtParameterValueEClass.getESuperTypes().add(theIBeXCoreArithmeticPackage.getArithmeticValue());
		gtIteratorEdgeEClass.getESuperTypes().add(theIBeXCoreModelPackage.getIBeXEdge());
		gtIteratorAttributeAssignmentEClass.getESuperTypes().add(theIBeXCoreModelPackage.getIBeXAttributeAssignment());
		gtIteratorAttributeReferenceEClass.getESuperTypes().add(theIBeXCoreArithmeticPackage.getArithmeticValue());
		gtIteratorAttributeReferenceEClass.getESuperTypes().add(theIBeXCoreArithmeticPackage.getBooleanValue());

		// Initialize classes, features, and operations; add parameters
		initEClass(gtModelEClass, GTModel.class, "GTModel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGTModel_RuleSet(), this.getGTRuleSet(), null, "ruleSet", null, 1, 1, GTModel.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(gtRuleSetEClass, GTRuleSet.class, "GTRuleSet", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGTRuleSet_Rules(), this.getGTRule(), null, "rules", null, 0, -1, GTRuleSet.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(gtPatternEClass, GTPattern.class, "GTPattern", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGTPattern_Parameters(), this.getGTParameter(), null, "parameters", null, 0, -1,
				GTPattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGTPattern_WatchDogs(), this.getGTWatchDog(), null, "watchDogs", null, 0, -1, GTPattern.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGTPattern_UsedFeatures(), theIBeXCoreModelPackage.getIBeXFeatureConfig(), null,
				"usedFeatures", null, 1, 1, GTPattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(gtRuleEClass, GTRule.class, "GTRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGTRule_Parameters(), this.getGTParameter(), null, "parameters", null, 0, -1, GTRule.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGTRule_ForEachOperations(), this.getGTForEachExpression(), null, "forEachOperations", null, 0,
				-1, GTRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGTRule_Probability(), theIBeXCoreArithmeticPackage.getArithmeticExpression(), null,
				"probability", null, 0, 1, GTRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(gtForEachExpressionEClass, GTForEachExpression.class, "GTForEachExpression", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGTForEachExpression_Created(), this.getGTIteratorEdge(), null, "created", null, 0, -1,
				GTForEachExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGTForEachExpression_Deleted(), this.getGTIteratorEdge(), null, "deleted", null, 0, -1,
				GTForEachExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGTForEachExpression_AttributeAssignments(),
				theIBeXCoreModelPackage.getIBeXAttributeAssignment(), null, "attributeAssignments", null, 0, -1,
				GTForEachExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGTForEachExpression_Source(), theIBeXCoreModelPackage.getIBeXNode(), null, "source", null, 1,
				1, GTForEachExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGTForEachExpression_Reference(), ecorePackage.getEReference(), null, "reference", null, 1, 1,
				GTForEachExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGTForEachExpression_Iterator(), theIBeXCoreModelPackage.getIBeXNode(), null, "iterator", null,
				1, 1, GTForEachExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
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

		initEClass(gtIteratorEdgeEClass, GTIteratorEdge.class, "GTIteratorEdge", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGTIteratorEdge_Iterator(), this.getGTForEachExpression(), null, "iterator", null, 1, 1,
				GTIteratorEdge.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(gtIteratorAttributeAssignmentEClass, GTIteratorAttributeAssignment.class,
				"GTIteratorAttributeAssignment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGTIteratorAttributeAssignment_Iterator(), this.getGTForEachExpression(), null, "iterator",
				null, 1, 1, GTIteratorAttributeAssignment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(gtIteratorAttributeReferenceEClass, GTIteratorAttributeReference.class,
				"GTIteratorAttributeReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGTIteratorAttributeReference_Iterator(), this.getGTForEachExpression(), null, "iterator",
				null, 1, 1, GTIteratorAttributeReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGTIteratorAttributeReference_Attribute(), ecorePackage.getEAttribute(), null, "attribute",
				null, 1, 1, GTIteratorAttributeReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

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
