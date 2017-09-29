/**
 */
package runtime.impl;

import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import runtime.Protocol;
import runtime.RuntimeFactory;
import runtime.RuntimePackage;
import runtime.TGGRuleApplication;
import runtime.TempContainer;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class RuntimePackageImpl extends EPackageImpl implements RuntimePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass protocolEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggRuleApplicationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nodeMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tempContainerEClass = null;

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
	 * @see runtime.RuntimePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private RuntimePackageImpl() {
		super(eNS_URI, RuntimeFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link RuntimePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static RuntimePackage init() {
		if (isInited)
			return (RuntimePackage) EPackage.Registry.INSTANCE.getEPackage(RuntimePackage.eNS_URI);

		// Obtain or create and register package
		RuntimePackageImpl theRuntimePackage = (RuntimePackageImpl) (EPackage.Registry.INSTANCE
				.get(eNS_URI) instanceof RuntimePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
						: new RuntimePackageImpl());

		isInited = true;

		// Create package meta-data objects
		theRuntimePackage.createPackageContents();

		// Initialize created meta-data
		theRuntimePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theRuntimePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(RuntimePackage.eNS_URI, theRuntimePackage);
		return theRuntimePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProtocol() {
		return protocolEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProtocol_Steps() {
		return (EReference) protocolEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTGGRuleApplication() {
		return tggRuleApplicationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRuleApplication_CreatedSrc() {
		return (EReference) tggRuleApplicationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRuleApplication_CreatedTrg() {
		return (EReference) tggRuleApplicationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRuleApplication_CreatedCorr() {
		return (EReference) tggRuleApplicationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRuleApplication_ContextSrc() {
		return (EReference) tggRuleApplicationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRuleApplication_ContextTrg() {
		return (EReference) tggRuleApplicationEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTGGRuleApplication_Name() {
		return (EAttribute) tggRuleApplicationEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRuleApplication_Protocol() {
		return (EReference) tggRuleApplicationEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTGGRuleApplication_Final() {
		return (EAttribute) tggRuleApplicationEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRuleApplication_NodeMappings() {
		return (EReference) tggRuleApplicationEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNodeMapping() {
		return nodeMappingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNodeMapping_Key() {
		return (EAttribute) nodeMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNodeMapping_Value() {
		return (EReference) nodeMappingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTempContainer() {
		return tempContainerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTempContainer_Objects() {
		return (EReference) tempContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuntimeFactory getRuntimeFactory() {
		return (RuntimeFactory) getEFactoryInstance();
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
		protocolEClass = createEClass(PROTOCOL);
		createEReference(protocolEClass, PROTOCOL__STEPS);

		tggRuleApplicationEClass = createEClass(TGG_RULE_APPLICATION);
		createEReference(tggRuleApplicationEClass, TGG_RULE_APPLICATION__CREATED_SRC);
		createEReference(tggRuleApplicationEClass, TGG_RULE_APPLICATION__CREATED_TRG);
		createEReference(tggRuleApplicationEClass, TGG_RULE_APPLICATION__CREATED_CORR);
		createEReference(tggRuleApplicationEClass, TGG_RULE_APPLICATION__CONTEXT_SRC);
		createEReference(tggRuleApplicationEClass, TGG_RULE_APPLICATION__CONTEXT_TRG);
		createEAttribute(tggRuleApplicationEClass, TGG_RULE_APPLICATION__NAME);
		createEReference(tggRuleApplicationEClass, TGG_RULE_APPLICATION__PROTOCOL);
		createEAttribute(tggRuleApplicationEClass, TGG_RULE_APPLICATION__FINAL);
		createEReference(tggRuleApplicationEClass, TGG_RULE_APPLICATION__NODE_MAPPINGS);

		nodeMappingEClass = createEClass(NODE_MAPPING);
		createEAttribute(nodeMappingEClass, NODE_MAPPING__KEY);
		createEReference(nodeMappingEClass, NODE_MAPPING__VALUE);

		tempContainerEClass = createEClass(TEMP_CONTAINER);
		createEReference(tempContainerEClass, TEMP_CONTAINER__OBJECTS);
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

		// Initialize classes, features, and operations; add parameters
		initEClass(protocolEClass, Protocol.class, "Protocol", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getProtocol_Steps(), this.getTGGRuleApplication(), this.getTGGRuleApplication_Protocol(),
				"steps", null, 0, -1, Protocol.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggRuleApplicationEClass, TGGRuleApplication.class, "TGGRuleApplication", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGRuleApplication_CreatedSrc(), ecorePackage.getEObject(), null, "createdSrc", null, 0, -1,
				TGGRuleApplication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRuleApplication_CreatedTrg(), ecorePackage.getEObject(), null, "createdTrg", null, 0, -1,
				TGGRuleApplication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRuleApplication_CreatedCorr(), ecorePackage.getEObject(), null, "createdCorr", null, 0, -1,
				TGGRuleApplication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRuleApplication_ContextSrc(), ecorePackage.getEObject(), null, "contextSrc", null, 0, -1,
				TGGRuleApplication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRuleApplication_ContextTrg(), ecorePackage.getEObject(), null, "contextTrg", null, 0, -1,
				TGGRuleApplication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTGGRuleApplication_Name(), ecorePackage.getEString(), "name", null, 0, 1,
				TGGRuleApplication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRuleApplication_Protocol(), this.getProtocol(), this.getProtocol_Steps(), "protocol", null,
				0, 1, TGGRuleApplication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTGGRuleApplication_Final(), ecorePackage.getEBoolean(), "final", null, 0, 1,
				TGGRuleApplication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRuleApplication_NodeMappings(), this.getNodeMapping(), null, "nodeMappings", null, 0, -1,
				TGGRuleApplication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(nodeMappingEClass, Map.Entry.class, "NodeMapping", !IS_ABSTRACT, !IS_INTERFACE,
				!IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNodeMapping_Key(), ecorePackage.getEString(), "key", null, 0, 1, Map.Entry.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNodeMapping_Value(), ecorePackage.getEObject(), null, "value", null, 0, 1, Map.Entry.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tempContainerEClass, TempContainer.class, "TempContainer", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTempContainer_Objects(), ecorePackage.getEObject(), null, "objects", null, 0, -1,
				TempContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //RuntimePackageImpl
