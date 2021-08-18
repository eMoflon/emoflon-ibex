/**
 */
package org.emoflon.ibex.gt.StateModel.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.emoflon.ibex.gt.StateModel.AllMatches;
import org.emoflon.ibex.gt.StateModel.AttributeDelta;
import org.emoflon.ibex.gt.StateModel.IBeXMatch;
import org.emoflon.ibex.gt.StateModel.Link;
import org.emoflon.ibex.gt.StateModel.Parameter;
import org.emoflon.ibex.gt.StateModel.RuleState;
import org.emoflon.ibex.gt.StateModel.State;
import org.emoflon.ibex.gt.StateModel.StateContainer;
import org.emoflon.ibex.gt.StateModel.StateModelFactory;
import org.emoflon.ibex.gt.StateModel.StateModelPackage;
import org.emoflon.ibex.gt.StateModel.StructuralDelta;
import org.emoflon.ibex.gt.StateModel.Value;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class StateModelPackageImpl extends EPackageImpl implements StateModelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stateContainerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ruleStateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass attributeDeltaEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass structuralDeltaEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass linkEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXMatchEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass parameterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass valueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass allMatchesEClass = null;

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
	 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private StateModelPackageImpl() {
		super(eNS_URI, StateModelFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link StateModelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static StateModelPackage init() {
		if (isInited)
			return (StateModelPackage) EPackage.Registry.INSTANCE.getEPackage(StateModelPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredStateModelPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		StateModelPackageImpl theStateModelPackage = registeredStateModelPackage instanceof StateModelPackageImpl
				? (StateModelPackageImpl) registeredStateModelPackage
				: new StateModelPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		IBeXPatternModelPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theStateModelPackage.createPackageContents();

		// Initialize created meta-data
		theStateModelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theStateModelPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(StateModelPackage.eNS_URI, theStateModelPackage);
		return theStateModelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getStateContainer() {
		return stateContainerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getStateContainer_States() {
		return (EReference) stateContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getStateContainer_InitialState() {
		return (EReference) stateContainerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getState() {
		return stateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getState_Initial() {
		return (EAttribute) stateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getState_Children() {
		return (EReference) stateEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getState_Hash() {
		return (EAttribute) stateEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getState_PushoutApproach() {
		return (EAttribute) stateEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getRuleState() {
		return ruleStateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRuleState_Parent() {
		return (EReference) ruleStateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRuleState_Rule() {
		return (EReference) ruleStateEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRuleState_StructuralDelta() {
		return (EReference) ruleStateEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRuleState_AttributeDeltas() {
		return (EReference) ruleStateEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRuleState_Match() {
		return (EReference) ruleStateEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRuleState_CoMatch() {
		return (EReference) ruleStateEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRuleState_Parameters() {
		return (EReference) ruleStateEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRuleState_AllMatches() {
		return (EReference) ruleStateEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAttributeDelta() {
		return attributeDeltaEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAttributeDelta_Attribute() {
		return (EReference) attributeDeltaEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAttributeDelta_Object() {
		return (EReference) attributeDeltaEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAttributeDelta_OldValue() {
		return (EReference) attributeDeltaEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAttributeDelta_NewValue() {
		return (EReference) attributeDeltaEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getStructuralDelta() {
		return structuralDeltaEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getStructuralDelta_CreatedObjects() {
		return (EReference) structuralDeltaEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getStructuralDelta_DeletedObjects() {
		return (EReference) structuralDeltaEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getStructuralDelta_CreatedLinks() {
		return (EReference) structuralDeltaEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getStructuralDelta_DeletedLinks() {
		return (EReference) structuralDeltaEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getStructuralDelta_DeletedRootLevelObjects() {
		return (EReference) structuralDeltaEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getStructuralDelta_Resource2EObjectContainment() {
		return (EReference) structuralDeltaEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLink() {
		return linkEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLink_Type() {
		return (EReference) linkEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLink_Src() {
		return (EReference) linkEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getLink_Trg() {
		return (EReference) linkEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXMatch() {
		return iBeXMatchEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIBeXMatch_PatternName() {
		return (EAttribute) iBeXMatchEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXMatch_Parameters() {
		return (EReference) iBeXMatchEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getParameter() {
		return parameterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getParameter_Name() {
		return (EAttribute) parameterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getParameter_Parameter() {
		return (EReference) parameterEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getValue() {
		return valueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getValue_Type() {
		return (EReference) valueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getValue_ValueAsString() {
		return (EAttribute) valueEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAllMatches() {
		return allMatchesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAllMatches_PatternName() {
		return (EAttribute) allMatchesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAllMatches_AllMatchesForPattern() {
		return (EReference) allMatchesEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StateModelFactory getStateModelFactory() {
		return (StateModelFactory) getEFactoryInstance();
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
		stateContainerEClass = createEClass(STATE_CONTAINER);
		createEReference(stateContainerEClass, STATE_CONTAINER__STATES);
		createEReference(stateContainerEClass, STATE_CONTAINER__INITIAL_STATE);

		stateEClass = createEClass(STATE);
		createEAttribute(stateEClass, STATE__INITIAL);
		createEReference(stateEClass, STATE__CHILDREN);
		createEAttribute(stateEClass, STATE__HASH);
		createEAttribute(stateEClass, STATE__PUSHOUT_APPROACH);

		ruleStateEClass = createEClass(RULE_STATE);
		createEReference(ruleStateEClass, RULE_STATE__PARENT);
		createEReference(ruleStateEClass, RULE_STATE__RULE);
		createEReference(ruleStateEClass, RULE_STATE__STRUCTURAL_DELTA);
		createEReference(ruleStateEClass, RULE_STATE__ATTRIBUTE_DELTAS);
		createEReference(ruleStateEClass, RULE_STATE__MATCH);
		createEReference(ruleStateEClass, RULE_STATE__CO_MATCH);
		createEReference(ruleStateEClass, RULE_STATE__PARAMETERS);
		createEReference(ruleStateEClass, RULE_STATE__ALL_MATCHES);

		attributeDeltaEClass = createEClass(ATTRIBUTE_DELTA);
		createEReference(attributeDeltaEClass, ATTRIBUTE_DELTA__ATTRIBUTE);
		createEReference(attributeDeltaEClass, ATTRIBUTE_DELTA__OBJECT);
		createEReference(attributeDeltaEClass, ATTRIBUTE_DELTA__OLD_VALUE);
		createEReference(attributeDeltaEClass, ATTRIBUTE_DELTA__NEW_VALUE);

		structuralDeltaEClass = createEClass(STRUCTURAL_DELTA);
		createEReference(structuralDeltaEClass, STRUCTURAL_DELTA__CREATED_OBJECTS);
		createEReference(structuralDeltaEClass, STRUCTURAL_DELTA__DELETED_OBJECTS);
		createEReference(structuralDeltaEClass, STRUCTURAL_DELTA__CREATED_LINKS);
		createEReference(structuralDeltaEClass, STRUCTURAL_DELTA__DELETED_LINKS);
		createEReference(structuralDeltaEClass, STRUCTURAL_DELTA__DELETED_ROOT_LEVEL_OBJECTS);
		createEReference(structuralDeltaEClass, STRUCTURAL_DELTA__RESOURCE2_EOBJECT_CONTAINMENT);

		linkEClass = createEClass(LINK);
		createEReference(linkEClass, LINK__TYPE);
		createEReference(linkEClass, LINK__SRC);
		createEReference(linkEClass, LINK__TRG);

		iBeXMatchEClass = createEClass(IBE_XMATCH);
		createEAttribute(iBeXMatchEClass, IBE_XMATCH__PATTERN_NAME);
		createEReference(iBeXMatchEClass, IBE_XMATCH__PARAMETERS);

		parameterEClass = createEClass(PARAMETER);
		createEAttribute(parameterEClass, PARAMETER__NAME);
		createEReference(parameterEClass, PARAMETER__PARAMETER);

		valueEClass = createEClass(VALUE);
		createEReference(valueEClass, VALUE__TYPE);
		createEAttribute(valueEClass, VALUE__VALUE_AS_STRING);

		allMatchesEClass = createEClass(ALL_MATCHES);
		createEAttribute(allMatchesEClass, ALL_MATCHES__PATTERN_NAME);
		createEReference(allMatchesEClass, ALL_MATCHES__ALL_MATCHES_FOR_PATTERN);
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
		IBeXPatternModelPackage theIBeXPatternModelPackage = (IBeXPatternModelPackage) EPackage.Registry.INSTANCE
				.getEPackage(IBeXPatternModelPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		ruleStateEClass.getESuperTypes().add(this.getState());

		// Initialize classes, features, and operations; add parameters
		initEClass(stateContainerEClass, StateContainer.class, "StateContainer", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getStateContainer_States(), this.getState(), null, "states", null, 0, -1, StateContainer.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStateContainer_InitialState(), this.getState(), null, "initialState", null, 0, 1,
				StateContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stateEClass, State.class, "State", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getState_Initial(), ecorePackage.getEBoolean(), "initial", null, 0, 1, State.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getState_Children(), this.getRuleState(), null, "children", null, 0, -1, State.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getState_Hash(), ecorePackage.getELong(), "hash", null, 0, 1, State.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getState_PushoutApproach(), ecorePackage.getEInt(), "pushoutApproach", null, 0, 1, State.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ruleStateEClass, RuleState.class, "RuleState", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRuleState_Parent(), this.getState(), null, "parent", null, 0, 1, RuleState.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRuleState_Rule(), theIBeXPatternModelPackage.getIBeXRule(), null, "rule", null, 0, 1,
				RuleState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRuleState_StructuralDelta(), this.getStructuralDelta(), null, "structuralDelta", null, 0, 1,
				RuleState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRuleState_AttributeDeltas(), this.getAttributeDelta(), null, "attributeDeltas", null, 0, -1,
				RuleState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRuleState_Match(), this.getIBeXMatch(), null, "match", null, 0, 1, RuleState.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRuleState_CoMatch(), this.getIBeXMatch(), null, "coMatch", null, 0, 1, RuleState.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRuleState_Parameters(), this.getParameter(), null, "parameters", null, 0, -1, RuleState.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRuleState_AllMatches(), this.getAllMatches(), null, "allMatches", null, 0, -1,
				RuleState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(attributeDeltaEClass, AttributeDelta.class, "AttributeDelta", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAttributeDelta_Attribute(), ecorePackage.getEAttribute(), null, "attribute", null, 1, 1,
				AttributeDelta.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributeDelta_Object(), ecorePackage.getEObject(), null, "object", null, 1, 1,
				AttributeDelta.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributeDelta_OldValue(), this.getValue(), null, "oldValue", null, 0, 1,
				AttributeDelta.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAttributeDelta_NewValue(), this.getValue(), null, "newValue", null, 0, 1,
				AttributeDelta.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(structuralDeltaEClass, StructuralDelta.class, "StructuralDelta", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getStructuralDelta_CreatedObjects(), ecorePackage.getEObject(), null, "createdObjects", null, 0,
				-1, StructuralDelta.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStructuralDelta_DeletedObjects(), ecorePackage.getEObject(), null, "deletedObjects", null, 0,
				-1, StructuralDelta.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStructuralDelta_CreatedLinks(), this.getLink(), null, "createdLinks", null, 0, -1,
				StructuralDelta.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStructuralDelta_DeletedLinks(), this.getLink(), null, "deletedLinks", null, 0, -1,
				StructuralDelta.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStructuralDelta_DeletedRootLevelObjects(), ecorePackage.getEObject(), null,
				"deletedRootLevelObjects", null, 0, -1, StructuralDelta.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStructuralDelta_Resource2EObjectContainment(), ecorePackage.getEObject(), null,
				"resource2EObjectContainment", null, 0, -1, StructuralDelta.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(linkEClass, Link.class, "Link", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getLink_Type(), ecorePackage.getEReference(), null, "type", null, 1, 1, Link.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLink_Src(), ecorePackage.getEObject(), null, "src", null, 1, 1, Link.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getLink_Trg(), ecorePackage.getEObject(), null, "trg", null, 1, 1, Link.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(iBeXMatchEClass, IBeXMatch.class, "IBeXMatch", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIBeXMatch_PatternName(), ecorePackage.getEString(), "patternName", "", 0, 1, IBeXMatch.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				!IS_ORDERED);
		initEReference(getIBeXMatch_Parameters(), this.getParameter(), null, "parameters", null, 0, -1, IBeXMatch.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(parameterEClass, Parameter.class, "Parameter", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getParameter_Name(), ecorePackage.getEString(), "name", null, 0, 1, Parameter.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getParameter_Parameter(), ecorePackage.getEObject(), null, "parameter", null, 0, 1,
				Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(valueEClass, Value.class, "Value", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getValue_Type(), ecorePackage.getEDataType(), null, "Type", null, 0, 1, Value.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getValue_ValueAsString(), ecorePackage.getEString(), "ValueAsString", null, 0, 1, Value.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(allMatchesEClass, AllMatches.class, "AllMatches", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAllMatches_PatternName(), ecorePackage.getEString(), "patternName", null, 0, 1,
				AllMatches.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getAllMatches_AllMatchesForPattern(), this.getIBeXMatch(), null, "allMatchesForPattern", null, 0,
				-1, AllMatches.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //StateModelPackageImpl
