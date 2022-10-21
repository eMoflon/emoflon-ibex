/**
 */
package org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.Correspondence;
import org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.CorrespondenceSet;
import org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.Protocol;
import org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.TGGRuleApplication;
import org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.TGGRuntimeModelFactory;
import org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.TGGRuntimeModelPackage;
import org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.TempContainer;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TGGRuntimeModelPackageImpl extends EPackageImpl implements TGGRuntimeModelPackage {
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
	private EClass tempContainerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass correspondenceSetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass correspondenceEClass = null;

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
	 * @see org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.TGGRuntimeModelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private TGGRuntimeModelPackageImpl() {
		super(eNS_URI, TGGRuntimeModelFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link TGGRuntimeModelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static TGGRuntimeModelPackage init() {
		if (isInited)
			return (TGGRuntimeModelPackage) EPackage.Registry.INSTANCE.getEPackage(TGGRuntimeModelPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredTGGRuntimeModelPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		TGGRuntimeModelPackageImpl theTGGRuntimeModelPackage = registeredTGGRuntimeModelPackage instanceof TGGRuntimeModelPackageImpl
				? (TGGRuntimeModelPackageImpl) registeredTGGRuntimeModelPackage
				: new TGGRuntimeModelPackageImpl();

		isInited = true;

		// Create package meta-data objects
		theTGGRuntimeModelPackage.createPackageContents();

		// Initialize created meta-data
		theTGGRuntimeModelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theTGGRuntimeModelPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(TGGRuntimeModelPackage.eNS_URI, theTGGRuntimeModelPackage);
		return theTGGRuntimeModelPackage;
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
	public EReference getTGGRuleApplication_Protocol() {
		return (EReference) tggRuleApplicationEClass.getEStructuralFeatures().get(0);
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
	public EClass getCorrespondenceSet() {
		return correspondenceSetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCorrespondenceSet_Correspondences() {
		return (EReference) correspondenceSetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCorrespondence() {
		return correspondenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCorrespondence_Source() {
		return (EReference) correspondenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCorrespondence_Target() {
		return (EReference) correspondenceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGRuntimeModelFactory getTGGRuntimeModelFactory() {
		return (TGGRuntimeModelFactory) getEFactoryInstance();
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
		tempContainerEClass = createEClass(TEMP_CONTAINER);
		createEReference(tempContainerEClass, TEMP_CONTAINER__OBJECTS);

		protocolEClass = createEClass(PROTOCOL);
		createEReference(protocolEClass, PROTOCOL__STEPS);

		tggRuleApplicationEClass = createEClass(TGG_RULE_APPLICATION);
		createEReference(tggRuleApplicationEClass, TGG_RULE_APPLICATION__PROTOCOL);

		correspondenceSetEClass = createEClass(CORRESPONDENCE_SET);
		createEReference(correspondenceSetEClass, CORRESPONDENCE_SET__CORRESPONDENCES);

		correspondenceEClass = createEClass(CORRESPONDENCE);
		createEReference(correspondenceEClass, CORRESPONDENCE__SOURCE);
		createEReference(correspondenceEClass, CORRESPONDENCE__TARGET);
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
		initEClass(tempContainerEClass, TempContainer.class, "TempContainer", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTempContainer_Objects(), ecorePackage.getEObject(), null, "objects", null, 0, -1,
				TempContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(protocolEClass, Protocol.class, "Protocol", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getProtocol_Steps(), this.getTGGRuleApplication(), this.getTGGRuleApplication_Protocol(),
				"steps", null, 0, -1, Protocol.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggRuleApplicationEClass, TGGRuleApplication.class, "TGGRuleApplication", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGRuleApplication_Protocol(), this.getProtocol(), this.getProtocol_Steps(), "protocol", null,
				0, 1, TGGRuleApplication.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(correspondenceSetEClass, CorrespondenceSet.class, "CorrespondenceSet", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCorrespondenceSet_Correspondences(), this.getCorrespondence(), null, "correspondences", null,
				0, -1, CorrespondenceSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(correspondenceEClass, Correspondence.class, "Correspondence", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCorrespondence_Source(), ecorePackage.getEObject(), null, "source", null, 0, 1,
				Correspondence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCorrespondence_Target(), ecorePackage.getEObject(), null, "target", null, 0, 1,
				Correspondence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //TGGRuntimeModelPackageImpl
