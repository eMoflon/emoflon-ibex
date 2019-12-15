/**
 */
package precedencegraph.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import precedencegraph.PrecedenceNode;
import precedencegraph.PrecedenceNodeContainer;
import precedencegraph.PrecedencegraphFactory;
import precedencegraph.PrecedencegraphPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PrecedencegraphPackageImpl extends EPackageImpl implements PrecedencegraphPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass precedenceNodeContainerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass precedenceNodeEClass = null;

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
	 * @see precedencegraph.PrecedencegraphPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private PrecedencegraphPackageImpl() {
		super(eNS_URI, PrecedencegraphFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link PrecedencegraphPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static PrecedencegraphPackage init() {
		if (isInited)
			return (PrecedencegraphPackage) EPackage.Registry.INSTANCE.getEPackage(PrecedencegraphPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredPrecedencegraphPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		PrecedencegraphPackageImpl thePrecedencegraphPackage = registeredPrecedencegraphPackage instanceof PrecedencegraphPackageImpl
				? (PrecedencegraphPackageImpl) registeredPrecedencegraphPackage
				: new PrecedencegraphPackageImpl();

		isInited = true;

		// Create package meta-data objects
		thePrecedencegraphPackage.createPackageContents();

		// Initialize created meta-data
		thePrecedencegraphPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		thePrecedencegraphPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(PrecedencegraphPackage.eNS_URI, thePrecedencegraphPackage);
		return thePrecedencegraphPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPrecedenceNodeContainer() {
		return precedenceNodeContainerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPrecedenceNodeContainer_Nodes() {
		return (EReference) precedenceNodeContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPrecedenceNode() {
		return precedenceNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getPrecedenceNode_Broken() {
		return (EAttribute) precedenceNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPrecedenceNode_Requires() {
		return (EReference) precedenceNodeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPrecedenceNode_RequiredBy() {
		return (EReference) precedenceNodeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPrecedenceNode_BasedOn() {
		return (EReference) precedenceNodeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPrecedenceNode_BaseFor() {
		return (EReference) precedenceNodeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getPrecedenceNode_MatchAsString() {
		return (EAttribute) precedenceNodeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PrecedencegraphFactory getPrecedencegraphFactory() {
		return (PrecedencegraphFactory) getEFactoryInstance();
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
		precedenceNodeContainerEClass = createEClass(PRECEDENCE_NODE_CONTAINER);
		createEReference(precedenceNodeContainerEClass, PRECEDENCE_NODE_CONTAINER__NODES);

		precedenceNodeEClass = createEClass(PRECEDENCE_NODE);
		createEAttribute(precedenceNodeEClass, PRECEDENCE_NODE__BROKEN);
		createEReference(precedenceNodeEClass, PRECEDENCE_NODE__REQUIRES);
		createEReference(precedenceNodeEClass, PRECEDENCE_NODE__REQUIRED_BY);
		createEReference(precedenceNodeEClass, PRECEDENCE_NODE__BASED_ON);
		createEReference(precedenceNodeEClass, PRECEDENCE_NODE__BASE_FOR);
		createEAttribute(precedenceNodeEClass, PRECEDENCE_NODE__MATCH_AS_STRING);
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
		initEClass(precedenceNodeContainerEClass, PrecedenceNodeContainer.class, "PrecedenceNodeContainer",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPrecedenceNodeContainer_Nodes(), this.getPrecedenceNode(), null, "nodes", null, 0, -1,
				PrecedenceNodeContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(precedenceNodeEClass, PrecedenceNode.class, "PrecedenceNode", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPrecedenceNode_Broken(), ecorePackage.getEBoolean(), "broken", null, 0, 1,
				PrecedenceNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getPrecedenceNode_Requires(), this.getPrecedenceNode(), this.getPrecedenceNode_RequiredBy(),
				"requires", null, 0, -1, PrecedenceNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPrecedenceNode_RequiredBy(), this.getPrecedenceNode(), this.getPrecedenceNode_Requires(),
				"requiredBy", null, 0, -1, PrecedenceNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPrecedenceNode_BasedOn(), this.getPrecedenceNode(), this.getPrecedenceNode_BaseFor(),
				"basedOn", null, 0, -1, PrecedenceNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPrecedenceNode_BaseFor(), this.getPrecedenceNode(), this.getPrecedenceNode_BasedOn(),
				"baseFor", null, 0, -1, PrecedenceNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPrecedenceNode_MatchAsString(), ecorePackage.getEString(), "matchAsString", null, 0, 1,
				PrecedenceNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //PrecedencegraphPackageImpl
