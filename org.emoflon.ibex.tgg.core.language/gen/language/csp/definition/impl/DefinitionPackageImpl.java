/**
 */
package language.csp.definition.impl;

import language.LanguagePackage;

import language.basic.BasicPackage;

import language.basic.expressions.ExpressionsPackage;

import language.basic.expressions.impl.ExpressionsPackageImpl;

import language.basic.impl.BasicPackageImpl;

import language.csp.CspPackage;

import language.csp.definition.DefinitionFactory;
import language.csp.definition.DefinitionPackage;
import language.csp.definition.TGGAttributeConstraintAdornment;
import language.csp.definition.TGGAttributeConstraintDefinition;
import language.csp.definition.TGGAttributeConstraintDefinitionLibrary;
import language.csp.definition.TGGAttributeConstraintParameterDefinition;

import language.csp.impl.CspPackageImpl;

import language.impl.LanguagePackageImpl;

import language.inplaceAttributes.InplaceAttributesPackage;

import language.inplaceAttributes.impl.InplaceAttributesPackageImpl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.impl.EcorePackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DefinitionPackageImpl extends EPackageImpl implements DefinitionPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggAttributeConstraintDefinitionLibraryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggAttributeConstraintDefinitionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggAttributeConstraintParameterDefinitionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggAttributeConstraintAdornmentEClass = null;

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
	 * @see language.csp.definition.DefinitionPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private DefinitionPackageImpl() {
		super(eNS_URI, DefinitionFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link DefinitionPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static DefinitionPackage init() {
		if (isInited)
			return (DefinitionPackage) EPackage.Registry.INSTANCE.getEPackage(DefinitionPackage.eNS_URI);

		// Obtain or create and register package
		DefinitionPackageImpl theDefinitionPackage = (DefinitionPackageImpl) (EPackage.Registry.INSTANCE
				.get(eNS_URI) instanceof DefinitionPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
						: new DefinitionPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		LanguagePackageImpl theLanguagePackage = (LanguagePackageImpl) (EPackage.Registry.INSTANCE
				.getEPackage(LanguagePackage.eNS_URI) instanceof LanguagePackageImpl
						? EPackage.Registry.INSTANCE.getEPackage(LanguagePackage.eNS_URI)
						: LanguagePackage.eINSTANCE);
		InplaceAttributesPackageImpl theInplaceAttributesPackage = (InplaceAttributesPackageImpl) (EPackage.Registry.INSTANCE
				.getEPackage(InplaceAttributesPackage.eNS_URI) instanceof InplaceAttributesPackageImpl
						? EPackage.Registry.INSTANCE.getEPackage(InplaceAttributesPackage.eNS_URI)
						: InplaceAttributesPackage.eINSTANCE);
		CspPackageImpl theCspPackage = (CspPackageImpl) (EPackage.Registry.INSTANCE
				.getEPackage(CspPackage.eNS_URI) instanceof CspPackageImpl
						? EPackage.Registry.INSTANCE.getEPackage(CspPackage.eNS_URI)
						: CspPackage.eINSTANCE);
		BasicPackageImpl theBasicPackage = (BasicPackageImpl) (EPackage.Registry.INSTANCE
				.getEPackage(BasicPackage.eNS_URI) instanceof BasicPackageImpl
						? EPackage.Registry.INSTANCE.getEPackage(BasicPackage.eNS_URI)
						: BasicPackage.eINSTANCE);
		ExpressionsPackageImpl theExpressionsPackage = (ExpressionsPackageImpl) (EPackage.Registry.INSTANCE
				.getEPackage(ExpressionsPackage.eNS_URI) instanceof ExpressionsPackageImpl
						? EPackage.Registry.INSTANCE.getEPackage(ExpressionsPackage.eNS_URI)
						: ExpressionsPackage.eINSTANCE);
		EcorePackageImpl theEcorePackage = (EcorePackageImpl) (EPackage.Registry.INSTANCE
				.getEPackage(EcorePackage.eNS_URI) instanceof EcorePackageImpl
						? EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI)
						: EcorePackage.eINSTANCE);

		// Create package meta-data objects
		theDefinitionPackage.createPackageContents();
		theLanguagePackage.createPackageContents();
		theInplaceAttributesPackage.createPackageContents();
		theCspPackage.createPackageContents();
		theBasicPackage.createPackageContents();
		theExpressionsPackage.createPackageContents();
		theEcorePackage.createPackageContents();

		// Initialize created meta-data
		theDefinitionPackage.initializePackageContents();
		theLanguagePackage.initializePackageContents();
		theInplaceAttributesPackage.initializePackageContents();
		theCspPackage.initializePackageContents();
		theBasicPackage.initializePackageContents();
		theExpressionsPackage.initializePackageContents();
		theEcorePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theDefinitionPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(DefinitionPackage.eNS_URI, theDefinitionPackage);
		return theDefinitionPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTGGAttributeConstraintDefinitionLibrary() {
		return tggAttributeConstraintDefinitionLibraryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGAttributeConstraintDefinitionLibrary_TggAttributeConstraintDefinitions() {
		return (EReference) tggAttributeConstraintDefinitionLibraryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTGGAttributeConstraintDefinition() {
		return tggAttributeConstraintDefinitionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTGGAttributeConstraintDefinition_UserDefined() {
		return (EAttribute) tggAttributeConstraintDefinitionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGAttributeConstraintDefinition_ParameterDefinitions() {
		return (EReference) tggAttributeConstraintDefinitionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGAttributeConstraintDefinition_SyncAdornments() {
		return (EReference) tggAttributeConstraintDefinitionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGAttributeConstraintDefinition_GenAdornments() {
		return (EReference) tggAttributeConstraintDefinitionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTGGAttributeConstraintParameterDefinition() {
		return tggAttributeConstraintParameterDefinitionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGAttributeConstraintParameterDefinition_Type() {
		return (EReference) tggAttributeConstraintParameterDefinitionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTGGAttributeConstraintParameterDefinition_Name() {
		return (EAttribute) tggAttributeConstraintParameterDefinitionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTGGAttributeConstraintAdornment() {
		return tggAttributeConstraintAdornmentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTGGAttributeConstraintAdornment_Value() {
		return (EAttribute) tggAttributeConstraintAdornmentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DefinitionFactory getDefinitionFactory() {
		return (DefinitionFactory) getEFactoryInstance();
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
		tggAttributeConstraintDefinitionLibraryEClass = createEClass(TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY);
		createEReference(tggAttributeConstraintDefinitionLibraryEClass,
				TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY__TGG_ATTRIBUTE_CONSTRAINT_DEFINITIONS);

		tggAttributeConstraintDefinitionEClass = createEClass(TGG_ATTRIBUTE_CONSTRAINT_DEFINITION);
		createEAttribute(tggAttributeConstraintDefinitionEClass, TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__USER_DEFINED);
		createEReference(tggAttributeConstraintDefinitionEClass,
				TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__PARAMETER_DEFINITIONS);
		createEReference(tggAttributeConstraintDefinitionEClass, TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__SYNC_ADORNMENTS);
		createEReference(tggAttributeConstraintDefinitionEClass, TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__GEN_ADORNMENTS);

		tggAttributeConstraintParameterDefinitionEClass = createEClass(TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_DEFINITION);
		createEReference(tggAttributeConstraintParameterDefinitionEClass,
				TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_DEFINITION__TYPE);
		createEAttribute(tggAttributeConstraintParameterDefinitionEClass,
				TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_DEFINITION__NAME);

		tggAttributeConstraintAdornmentEClass = createEClass(TGG_ATTRIBUTE_CONSTRAINT_ADORNMENT);
		createEAttribute(tggAttributeConstraintAdornmentEClass, TGG_ATTRIBUTE_CONSTRAINT_ADORNMENT__VALUE);
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
		BasicPackage theBasicPackage = (BasicPackage) EPackage.Registry.INSTANCE.getEPackage(BasicPackage.eNS_URI);
		EcorePackage theEcorePackage = (EcorePackage) EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		tggAttributeConstraintDefinitionEClass.getESuperTypes().add(theBasicPackage.getTGGNamedElement());

		// Initialize classes, features, and operations; add parameters
		initEClass(tggAttributeConstraintDefinitionLibraryEClass, TGGAttributeConstraintDefinitionLibrary.class,
				"TGGAttributeConstraintDefinitionLibrary", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGAttributeConstraintDefinitionLibrary_TggAttributeConstraintDefinitions(),
				this.getTGGAttributeConstraintDefinition(), null, "tggAttributeConstraintDefinitions", null, 0, -1,
				TGGAttributeConstraintDefinitionLibrary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggAttributeConstraintDefinitionEClass, TGGAttributeConstraintDefinition.class,
				"TGGAttributeConstraintDefinition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTGGAttributeConstraintDefinition_UserDefined(), theEcorePackage.getEBoolean(), "userDefined",
				"true", 0, 1, TGGAttributeConstraintDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGAttributeConstraintDefinition_ParameterDefinitions(),
				this.getTGGAttributeConstraintParameterDefinition(), null, "parameterDefinitions", null, 0, -1,
				TGGAttributeConstraintDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGAttributeConstraintDefinition_SyncAdornments(), this.getTGGAttributeConstraintAdornment(),
				null, "syncAdornments", null, 0, -1, TGGAttributeConstraintDefinition.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getTGGAttributeConstraintDefinition_GenAdornments(), this.getTGGAttributeConstraintAdornment(),
				null, "genAdornments", null, 0, -1, TGGAttributeConstraintDefinition.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggAttributeConstraintParameterDefinitionEClass, TGGAttributeConstraintParameterDefinition.class,
				"TGGAttributeConstraintParameterDefinition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGAttributeConstraintParameterDefinition_Type(), theEcorePackage.getEDataType(), null,
				"type", null, 0, 1, TGGAttributeConstraintParameterDefinition.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTGGAttributeConstraintParameterDefinition_Name(), theEcorePackage.getEString(), "name", null,
				0, 1, TGGAttributeConstraintParameterDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggAttributeConstraintAdornmentEClass, TGGAttributeConstraintAdornment.class,
				"TGGAttributeConstraintAdornment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTGGAttributeConstraintAdornment_Value(), theEcorePackage.getEString(), "value", null, 0, -1,
				TGGAttributeConstraintAdornment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
	}

} //DefinitionPackageImpl
