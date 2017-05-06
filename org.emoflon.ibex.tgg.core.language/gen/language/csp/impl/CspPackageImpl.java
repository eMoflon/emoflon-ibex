/**
 */
package language.csp.impl;

import language.LanguagePackage;

import language.basic.BasicPackage;

import language.basic.expressions.ExpressionsPackage;

import language.basic.expressions.impl.ExpressionsPackageImpl;

import language.basic.impl.BasicPackageImpl;

import language.csp.CspFactory;
import language.csp.CspPackage;
import language.csp.TGGAttributeConstraint;
import language.csp.TGGAttributeConstraintLibrary;
import language.csp.TGGAttributeVariable;

import language.csp.definition.DefinitionPackage;

import language.csp.definition.impl.DefinitionPackageImpl;

import language.impl.LanguagePackageImpl;

import language.inplaceAttributes.InplaceAttributesPackage;

import language.inplaceAttributes.impl.InplaceAttributesPackageImpl;

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
public class CspPackageImpl extends EPackageImpl implements CspPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggAttributeConstraintLibraryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggAttributeConstraintEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggAttributeVariableEClass = null;

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
	 * @see language.csp.CspPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private CspPackageImpl() {
		super(eNS_URI, CspFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link CspPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static CspPackage init() {
		if (isInited)
			return (CspPackage) EPackage.Registry.INSTANCE.getEPackage(CspPackage.eNS_URI);

		// Obtain or create and register package
		CspPackageImpl theCspPackage = (CspPackageImpl) (EPackage.Registry.INSTANCE
				.get(eNS_URI) instanceof CspPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
						: new CspPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		LanguagePackageImpl theLanguagePackage = (LanguagePackageImpl) (EPackage.Registry.INSTANCE
				.getEPackage(LanguagePackage.eNS_URI) instanceof LanguagePackageImpl
						? EPackage.Registry.INSTANCE.getEPackage(LanguagePackage.eNS_URI) : LanguagePackage.eINSTANCE);
		InplaceAttributesPackageImpl theInplaceAttributesPackage = (InplaceAttributesPackageImpl) (EPackage.Registry.INSTANCE
				.getEPackage(InplaceAttributesPackage.eNS_URI) instanceof InplaceAttributesPackageImpl
						? EPackage.Registry.INSTANCE.getEPackage(InplaceAttributesPackage.eNS_URI)
						: InplaceAttributesPackage.eINSTANCE);
		DefinitionPackageImpl theDefinitionPackage = (DefinitionPackageImpl) (EPackage.Registry.INSTANCE
				.getEPackage(DefinitionPackage.eNS_URI) instanceof DefinitionPackageImpl
						? EPackage.Registry.INSTANCE.getEPackage(DefinitionPackage.eNS_URI)
						: DefinitionPackage.eINSTANCE);
		BasicPackageImpl theBasicPackage = (BasicPackageImpl) (EPackage.Registry.INSTANCE
				.getEPackage(BasicPackage.eNS_URI) instanceof BasicPackageImpl
						? EPackage.Registry.INSTANCE.getEPackage(BasicPackage.eNS_URI) : BasicPackage.eINSTANCE);
		ExpressionsPackageImpl theExpressionsPackage = (ExpressionsPackageImpl) (EPackage.Registry.INSTANCE
				.getEPackage(ExpressionsPackage.eNS_URI) instanceof ExpressionsPackageImpl
						? EPackage.Registry.INSTANCE.getEPackage(ExpressionsPackage.eNS_URI)
						: ExpressionsPackage.eINSTANCE);

		// Create package meta-data objects
		theCspPackage.createPackageContents();
		theLanguagePackage.createPackageContents();
		theInplaceAttributesPackage.createPackageContents();
		theDefinitionPackage.createPackageContents();
		theBasicPackage.createPackageContents();
		theExpressionsPackage.createPackageContents();

		// Initialize created meta-data
		theCspPackage.initializePackageContents();
		theLanguagePackage.initializePackageContents();
		theInplaceAttributesPackage.initializePackageContents();
		theDefinitionPackage.initializePackageContents();
		theBasicPackage.initializePackageContents();
		theExpressionsPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theCspPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(CspPackage.eNS_URI, theCspPackage);
		return theCspPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTGGAttributeConstraintLibrary() {
		return tggAttributeConstraintLibraryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGAttributeConstraintLibrary_TggAttributeConstraints() {
		return (EReference) tggAttributeConstraintLibraryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGAttributeConstraintLibrary_ParameterValues() {
		return (EReference) tggAttributeConstraintLibraryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGAttributeConstraintLibrary_Sorted_BWD() {
		return (EReference) tggAttributeConstraintLibraryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGAttributeConstraintLibrary_Sorted_FWD() {
		return (EReference) tggAttributeConstraintLibraryEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGAttributeConstraintLibrary_Sorted_CC() {
		return (EReference) tggAttributeConstraintLibraryEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGAttributeConstraintLibrary_Sorted_MODELGEN() {
		return (EReference) tggAttributeConstraintLibraryEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTGGAttributeConstraint() {
		return tggAttributeConstraintEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGAttributeConstraint_Definition() {
		return (EReference) tggAttributeConstraintEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGAttributeConstraint_Parameters() {
		return (EReference) tggAttributeConstraintEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTGGAttributeVariable() {
		return tggAttributeVariableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTGGAttributeVariable_Name() {
		return (EAttribute) tggAttributeVariableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CspFactory getCspFactory() {
		return (CspFactory) getEFactoryInstance();
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
		tggAttributeConstraintLibraryEClass = createEClass(TGG_ATTRIBUTE_CONSTRAINT_LIBRARY);
		createEReference(tggAttributeConstraintLibraryEClass,
				TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__TGG_ATTRIBUTE_CONSTRAINTS);
		createEReference(tggAttributeConstraintLibraryEClass, TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__PARAMETER_VALUES);
		createEReference(tggAttributeConstraintLibraryEClass, TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__SORTED_BWD);
		createEReference(tggAttributeConstraintLibraryEClass, TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__SORTED_FWD);
		createEReference(tggAttributeConstraintLibraryEClass, TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__SORTED_CC);
		createEReference(tggAttributeConstraintLibraryEClass, TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__SORTED_MODELGEN);

		tggAttributeConstraintEClass = createEClass(TGG_ATTRIBUTE_CONSTRAINT);
		createEReference(tggAttributeConstraintEClass, TGG_ATTRIBUTE_CONSTRAINT__DEFINITION);
		createEReference(tggAttributeConstraintEClass, TGG_ATTRIBUTE_CONSTRAINT__PARAMETERS);

		tggAttributeVariableEClass = createEClass(TGG_ATTRIBUTE_VARIABLE);
		createEAttribute(tggAttributeVariableEClass, TGG_ATTRIBUTE_VARIABLE__NAME);
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
		DefinitionPackage theDefinitionPackage = (DefinitionPackage) EPackage.Registry.INSTANCE
				.getEPackage(DefinitionPackage.eNS_URI);
		ExpressionsPackage theExpressionsPackage = (ExpressionsPackage) EPackage.Registry.INSTANCE
				.getEPackage(ExpressionsPackage.eNS_URI);

		// Add subpackages
		getESubpackages().add(theDefinitionPackage);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		tggAttributeVariableEClass.getESuperTypes().add(theExpressionsPackage.getTGGParamValue());

		// Initialize classes, features, and operations; add parameters
		initEClass(tggAttributeConstraintLibraryEClass, TGGAttributeConstraintLibrary.class,
				"TGGAttributeConstraintLibrary", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGAttributeConstraintLibrary_TggAttributeConstraints(), this.getTGGAttributeConstraint(),
				null, "tggAttributeConstraints", null, 0, -1, TGGAttributeConstraintLibrary.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getTGGAttributeConstraintLibrary_ParameterValues(), theExpressionsPackage.getTGGParamValue(),
				null, "parameterValues", null, 0, -1, TGGAttributeConstraintLibrary.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGAttributeConstraintLibrary_Sorted_BWD(), this.getTGGAttributeConstraint(), null,
				"sorted_BWD", null, 0, -1, TGGAttributeConstraintLibrary.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGAttributeConstraintLibrary_Sorted_FWD(), this.getTGGAttributeConstraint(), null,
				"sorted_FWD", null, 0, -1, TGGAttributeConstraintLibrary.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGAttributeConstraintLibrary_Sorted_CC(), this.getTGGAttributeConstraint(), null,
				"sorted_CC", null, 0, -1, TGGAttributeConstraintLibrary.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGAttributeConstraintLibrary_Sorted_MODELGEN(), this.getTGGAttributeConstraint(), null,
				"sorted_MODELGEN", null, 0, -1, TGGAttributeConstraintLibrary.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggAttributeConstraintEClass, TGGAttributeConstraint.class, "TGGAttributeConstraint", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGAttributeConstraint_Definition(),
				theDefinitionPackage.getTGGAttributeConstraintDefinition(), null, "definition", null, 0, 1,
				TGGAttributeConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGAttributeConstraint_Parameters(), theExpressionsPackage.getTGGParamValue(), null,
				"parameters", null, 0, -1, TGGAttributeConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggAttributeVariableEClass, TGGAttributeVariable.class, "TGGAttributeVariable", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTGGAttributeVariable_Name(), ecorePackage.getEString(), "name", null, 0, 1,
				TGGAttributeVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
	}

} //CspPackageImpl
