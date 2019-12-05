/**
 */
package GTLanguage.impl;

import GTLanguage.GTLanguageFactory;
import GTLanguage.GTLanguagePackage;
import GTLanguage.GTNamedElement;
import GTLanguage.GTNode;
import GTLanguage.GTParameter;
import GTLanguage.GTRule;
import GTLanguage.GTRuleSet;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class GTLanguagePackageImpl extends EPackageImpl implements GTLanguagePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass gtNamedElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass gtNodeEClass = null;

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
	private EClass gtRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass gtRuleSetEClass = null;

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
	 * @see GTLanguage.GTLanguagePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private GTLanguagePackageImpl() {
		super(eNS_URI, GTLanguageFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link GTLanguagePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static GTLanguagePackage init() {
		if (isInited)
			return (GTLanguagePackage) EPackage.Registry.INSTANCE.getEPackage(GTLanguagePackage.eNS_URI);

		// Obtain or create and register package
		Object registeredGTLanguagePackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		GTLanguagePackageImpl theGTLanguagePackage = registeredGTLanguagePackage instanceof GTLanguagePackageImpl
				? (GTLanguagePackageImpl) registeredGTLanguagePackage
				: new GTLanguagePackageImpl();

		isInited = true;

		// Create package meta-data objects
		theGTLanguagePackage.createPackageContents();

		// Initialize created meta-data
		theGTLanguagePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theGTLanguagePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(GTLanguagePackage.eNS_URI, theGTLanguagePackage);
		return theGTLanguagePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getGTNamedElement() {
		return gtNamedElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getGTNamedElement_Name() {
		return (EAttribute) gtNamedElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getGTNode() {
		return gtNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getGTNode_Type() {
		return (EReference) gtNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getGTParameter() {
		return gtParameterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getGTParameter_Type() {
		return (EReference) gtParameterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getGTRule() {
		return gtRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getGTRule_Documentation() {
		return (EAttribute) gtRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getGTRule_Executable() {
		return (EAttribute) gtRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getGTRule_Nodes() {
		return (EReference) gtRuleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getGTRule_Parameters() {
		return (EReference) gtRuleEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getGTRule_RuleNodes() {
		return (EReference) gtRuleEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getGTRuleSet() {
		return gtRuleSetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getGTRuleSet_Rules() {
		return (EReference) gtRuleSetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GTLanguageFactory getGTLanguageFactory() {
		return (GTLanguageFactory) getEFactoryInstance();
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
		gtNamedElementEClass = createEClass(GT_NAMED_ELEMENT);
		createEAttribute(gtNamedElementEClass, GT_NAMED_ELEMENT__NAME);

		gtNodeEClass = createEClass(GT_NODE);
		createEReference(gtNodeEClass, GT_NODE__TYPE);

		gtParameterEClass = createEClass(GT_PARAMETER);
		createEReference(gtParameterEClass, GT_PARAMETER__TYPE);

		gtRuleEClass = createEClass(GT_RULE);
		createEAttribute(gtRuleEClass, GT_RULE__DOCUMENTATION);
		createEAttribute(gtRuleEClass, GT_RULE__EXECUTABLE);
		createEReference(gtRuleEClass, GT_RULE__NODES);
		createEReference(gtRuleEClass, GT_RULE__PARAMETERS);
		createEReference(gtRuleEClass, GT_RULE__RULE_NODES);

		gtRuleSetEClass = createEClass(GT_RULE_SET);
		createEReference(gtRuleSetEClass, GT_RULE_SET__RULES);
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
		gtNodeEClass.getESuperTypes().add(this.getGTNamedElement());
		gtParameterEClass.getESuperTypes().add(this.getGTNamedElement());
		gtRuleEClass.getESuperTypes().add(this.getGTNamedElement());

		// Initialize classes, features, and operations; add parameters
		initEClass(gtNamedElementEClass, GTNamedElement.class, "GTNamedElement", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getGTNamedElement_Name(), ecorePackage.getEString(), "name", null, 0, 1, GTNamedElement.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(gtNodeEClass, GTNode.class, "GTNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGTNode_Type(), ecorePackage.getEClass(), null, "type", null, 0, 1, GTNode.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(gtParameterEClass, GTParameter.class, "GTParameter", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGTParameter_Type(), ecorePackage.getEDataType(), null, "type", null, 0, 1, GTParameter.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(gtRuleEClass, GTRule.class, "GTRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getGTRule_Documentation(), ecorePackage.getEString(), "documentation", null, 0, 1, GTRule.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGTRule_Executable(), ecorePackage.getEBoolean(), "executable", null, 0, 1, GTRule.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGTRule_Nodes(), this.getGTNode(), null, "nodes", null, 0, -1, GTRule.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getGTRule_Parameters(), this.getGTParameter(), null, "parameters", null, 0, -1, GTRule.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGTRule_RuleNodes(), this.getGTNode(), null, "ruleNodes", null, 0, -1, GTRule.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(gtRuleSetEClass, GTRuleSet.class, "GTRuleSet", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGTRuleSet_Rules(), this.getGTRule(), null, "rules", null, 0, -1, GTRuleSet.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //GTLanguagePackageImpl
