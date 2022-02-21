/**
 */
package language.repair.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import language.LanguagePackage;
import language.impl.LanguagePackageImpl;
import language.repair.ExternalShortcutRule;
import language.repair.RepairFactory;
import language.repair.RepairPackage;
import language.repair.TGGRuleElementMapping;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class RepairPackageImpl extends EPackageImpl implements RepairPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass externalShortcutRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggRuleElementMappingEClass = null;

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
	 * @see language.repair.RepairPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private RepairPackageImpl() {
		super(eNS_URI, RepairFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link RepairPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static RepairPackage init() {
		if (isInited)
			return (RepairPackage) EPackage.Registry.INSTANCE.getEPackage(RepairPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredRepairPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		RepairPackageImpl theRepairPackage = registeredRepairPackage instanceof RepairPackageImpl ? (RepairPackageImpl) registeredRepairPackage
				: new RepairPackageImpl();

		isInited = true;

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(LanguagePackage.eNS_URI);
		LanguagePackageImpl theLanguagePackage = (LanguagePackageImpl) (registeredPackage instanceof LanguagePackageImpl ? registeredPackage
				: LanguagePackage.eINSTANCE);

		// Create package meta-data objects
		theRepairPackage.createPackageContents();
		theLanguagePackage.createPackageContents();

		// Initialize created meta-data
		theRepairPackage.initializePackageContents();
		theLanguagePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theRepairPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(RepairPackage.eNS_URI, theRepairPackage);
		return theRepairPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getExternalShortcutRule() {
		return externalShortcutRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getExternalShortcutRule_SourceRule() {
		return (EReference) externalShortcutRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getExternalShortcutRule_TargetRule() {
		return (EReference) externalShortcutRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getExternalShortcutRule_Deletions() {
		return (EReference) externalShortcutRuleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getExternalShortcutRule_Creations() {
		return (EReference) externalShortcutRuleEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getExternalShortcutRule_UnboundSrcContext() {
		return (EReference) externalShortcutRuleEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getExternalShortcutRule_UnboundTrgContext() {
		return (EReference) externalShortcutRuleEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getExternalShortcutRule_Mapping() {
		return (EReference) externalShortcutRuleEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExternalShortcutRule_Name() {
		return (EAttribute) externalShortcutRuleEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTGGRuleElementMapping() {
		return tggRuleElementMappingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTGGRuleElementMapping_SourceRuleElement() {
		return (EReference) tggRuleElementMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTGGRuleElementMapping_TargetRuleElement() {
		return (EReference) tggRuleElementMappingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RepairFactory getRepairFactory() {
		return (RepairFactory) getEFactoryInstance();
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
		externalShortcutRuleEClass = createEClass(EXTERNAL_SHORTCUT_RULE);
		createEReference(externalShortcutRuleEClass, EXTERNAL_SHORTCUT_RULE__SOURCE_RULE);
		createEReference(externalShortcutRuleEClass, EXTERNAL_SHORTCUT_RULE__TARGET_RULE);
		createEReference(externalShortcutRuleEClass, EXTERNAL_SHORTCUT_RULE__DELETIONS);
		createEReference(externalShortcutRuleEClass, EXTERNAL_SHORTCUT_RULE__CREATIONS);
		createEReference(externalShortcutRuleEClass, EXTERNAL_SHORTCUT_RULE__UNBOUND_SRC_CONTEXT);
		createEReference(externalShortcutRuleEClass, EXTERNAL_SHORTCUT_RULE__UNBOUND_TRG_CONTEXT);
		createEReference(externalShortcutRuleEClass, EXTERNAL_SHORTCUT_RULE__MAPPING);
		createEAttribute(externalShortcutRuleEClass, EXTERNAL_SHORTCUT_RULE__NAME);

		tggRuleElementMappingEClass = createEClass(TGG_RULE_ELEMENT_MAPPING);
		createEReference(tggRuleElementMappingEClass, TGG_RULE_ELEMENT_MAPPING__SOURCE_RULE_ELEMENT);
		createEReference(tggRuleElementMappingEClass, TGG_RULE_ELEMENT_MAPPING__TARGET_RULE_ELEMENT);
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
		LanguagePackage theLanguagePackage = (LanguagePackage) EPackage.Registry.INSTANCE.getEPackage(LanguagePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(externalShortcutRuleEClass, ExternalShortcutRule.class, "ExternalShortcutRule", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExternalShortcutRule_SourceRule(), theLanguagePackage.getTGGRule(), null, "sourceRule", null, 0, 1, ExternalShortcutRule.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getExternalShortcutRule_TargetRule(), theLanguagePackage.getTGGRule(), null, "targetRule", null, 0, 1, ExternalShortcutRule.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getExternalShortcutRule_Deletions(), theLanguagePackage.getTGGRuleElement(), null, "deletions", null, 0, -1,
				ExternalShortcutRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getExternalShortcutRule_Creations(), theLanguagePackage.getTGGRuleElement(), null, "creations", null, 0, -1,
				ExternalShortcutRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getExternalShortcutRule_UnboundSrcContext(), theLanguagePackage.getTGGRuleElement(), null, "unboundSrcContext", null, 0, -1,
				ExternalShortcutRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getExternalShortcutRule_UnboundTrgContext(), theLanguagePackage.getTGGRuleElement(), null, "unboundTrgContext", null, 0, -1,
				ExternalShortcutRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getExternalShortcutRule_Mapping(), this.getTGGRuleElementMapping(), null, "mapping", null, 0, -1, ExternalShortcutRule.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExternalShortcutRule_Name(), ecorePackage.getEString(), "name", null, 0, 1, ExternalShortcutRule.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggRuleElementMappingEClass, TGGRuleElementMapping.class, "TGGRuleElementMapping", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGRuleElementMapping_SourceRuleElement(), theLanguagePackage.getTGGRuleElement(), null, "sourceRuleElement", null, 0, 1,
				TGGRuleElementMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRuleElementMapping_TargetRuleElement(), theLanguagePackage.getTGGRuleElement(), null, "targetRuleElement", null, 0, 1,
				TGGRuleElementMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
	}

} //RepairPackageImpl
