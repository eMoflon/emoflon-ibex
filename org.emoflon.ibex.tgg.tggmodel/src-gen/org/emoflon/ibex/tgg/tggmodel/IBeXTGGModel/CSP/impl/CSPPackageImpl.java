/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.IBeXCoreArithmeticPackage;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.CSPFactory;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.CSPPackage;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraint;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintBinding;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintDefinition;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintDefinitionLibrary;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintParameterDefinition;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintParameterValue;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintSet;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintVariable;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGLocalVariable;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class CSPPackageImpl extends EPackageImpl implements CSPPackage {
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
	private EClass tggAttributeConstraintDefinitionLibraryEClass = null;

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
	private EClass tggAttributeConstraintBindingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggAttributeConstraintSetEClass = null;

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
	private EClass tggAttributeConstraintVariableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggAttributeConstraintParameterValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggcspEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggLocalVariableEClass = null;

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
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.CSPPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private CSPPackageImpl() {
		super(eNS_URI, CSPFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link CSPPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static CSPPackage init() {
		if (isInited)
			return (CSPPackage) EPackage.Registry.INSTANCE.getEPackage(CSPPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredCSPPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		CSPPackageImpl theCSPPackage = registeredCSPPackage instanceof CSPPackageImpl
				? (CSPPackageImpl) registeredCSPPackage
				: new CSPPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		IBeXCoreModelPackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(IBeXTGGModelPackage.eNS_URI);
		IBeXTGGModelPackageImpl theIBeXTGGModelPackage = (IBeXTGGModelPackageImpl) (registeredPackage instanceof IBeXTGGModelPackageImpl
				? registeredPackage
				: IBeXTGGModelPackage.eINSTANCE);

		// Create package meta-data objects
		theCSPPackage.createPackageContents();
		theIBeXTGGModelPackage.createPackageContents();

		// Initialize created meta-data
		theCSPPackage.initializePackageContents();
		theIBeXTGGModelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theCSPPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(CSPPackage.eNS_URI, theCSPPackage);
		return theCSPPackage;
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
	public EReference getTGGAttributeConstraintDefinition_ParameterDefinitions() {
		return (EReference) tggAttributeConstraintDefinitionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGAttributeConstraintDefinition_SyncBindings() {
		return (EReference) tggAttributeConstraintDefinitionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGAttributeConstraintDefinition_GenBindings() {
		return (EReference) tggAttributeConstraintDefinitionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGAttributeConstraintDefinition_Library() {
		return (EReference) tggAttributeConstraintDefinitionEClass.getEStructuralFeatures().get(3);
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
		return (EReference) tggAttributeConstraintDefinitionLibraryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTGGAttributeConstraintDefinitionLibrary_PackageName() {
		return (EAttribute) tggAttributeConstraintDefinitionLibraryEClass.getEStructuralFeatures().get(0);
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
	public EClass getTGGAttributeConstraintBinding() {
		return tggAttributeConstraintBindingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTGGAttributeConstraintBinding_Value() {
		return (EAttribute) tggAttributeConstraintBindingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTGGAttributeConstraintSet() {
		return tggAttributeConstraintSetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGAttributeConstraintSet_TggAttributeConstraints() {
		return (EReference) tggAttributeConstraintSetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGAttributeConstraintSet_Parameters() {
		return (EReference) tggAttributeConstraintSetEClass.getEStructuralFeatures().get(1);
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
	public EClass getTGGAttributeConstraintVariable() {
		return tggAttributeConstraintVariableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTGGAttributeConstraintVariable_Name() {
		return (EAttribute) tggAttributeConstraintVariableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTGGAttributeConstraintParameterValue() {
		return tggAttributeConstraintParameterValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGAttributeConstraintParameterValue_ParameterDefinition() {
		return (EReference) tggAttributeConstraintParameterValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGAttributeConstraintParameterValue_Expression() {
		return (EReference) tggAttributeConstraintParameterValueEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTGGAttributeConstraintParameterValue_Derived() {
		return (EAttribute) tggAttributeConstraintParameterValueEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTGGCSP() {
		return tggcspEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTGGCSP_Package() {
		return (EAttribute) tggcspEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGCSP_Values() {
		return (EReference) tggcspEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTGGLocalVariable() {
		return tggLocalVariableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CSPFactory getCSPFactory() {
		return (CSPFactory) getEFactoryInstance();
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
		tggAttributeConstraintDefinitionEClass = createEClass(TGG_ATTRIBUTE_CONSTRAINT_DEFINITION);
		createEReference(tggAttributeConstraintDefinitionEClass,
				TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__PARAMETER_DEFINITIONS);
		createEReference(tggAttributeConstraintDefinitionEClass, TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__SYNC_BINDINGS);
		createEReference(tggAttributeConstraintDefinitionEClass, TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__GEN_BINDINGS);
		createEReference(tggAttributeConstraintDefinitionEClass, TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__LIBRARY);

		tggAttributeConstraintDefinitionLibraryEClass = createEClass(TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY);
		createEAttribute(tggAttributeConstraintDefinitionLibraryEClass,
				TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY__PACKAGE_NAME);
		createEReference(tggAttributeConstraintDefinitionLibraryEClass,
				TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY__TGG_ATTRIBUTE_CONSTRAINT_DEFINITIONS);

		tggAttributeConstraintParameterDefinitionEClass = createEClass(TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_DEFINITION);
		createEReference(tggAttributeConstraintParameterDefinitionEClass,
				TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_DEFINITION__TYPE);
		createEAttribute(tggAttributeConstraintParameterDefinitionEClass,
				TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_DEFINITION__NAME);

		tggAttributeConstraintBindingEClass = createEClass(TGG_ATTRIBUTE_CONSTRAINT_BINDING);
		createEAttribute(tggAttributeConstraintBindingEClass, TGG_ATTRIBUTE_CONSTRAINT_BINDING__VALUE);

		tggAttributeConstraintSetEClass = createEClass(TGG_ATTRIBUTE_CONSTRAINT_SET);
		createEReference(tggAttributeConstraintSetEClass, TGG_ATTRIBUTE_CONSTRAINT_SET__TGG_ATTRIBUTE_CONSTRAINTS);
		createEReference(tggAttributeConstraintSetEClass, TGG_ATTRIBUTE_CONSTRAINT_SET__PARAMETERS);

		tggAttributeConstraintEClass = createEClass(TGG_ATTRIBUTE_CONSTRAINT);
		createEReference(tggAttributeConstraintEClass, TGG_ATTRIBUTE_CONSTRAINT__DEFINITION);
		createEReference(tggAttributeConstraintEClass, TGG_ATTRIBUTE_CONSTRAINT__PARAMETERS);

		tggAttributeConstraintVariableEClass = createEClass(TGG_ATTRIBUTE_CONSTRAINT_VARIABLE);
		createEAttribute(tggAttributeConstraintVariableEClass, TGG_ATTRIBUTE_CONSTRAINT_VARIABLE__NAME);

		tggAttributeConstraintParameterValueEClass = createEClass(TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_VALUE);
		createEReference(tggAttributeConstraintParameterValueEClass,
				TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_VALUE__PARAMETER_DEFINITION);
		createEReference(tggAttributeConstraintParameterValueEClass,
				TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_VALUE__EXPRESSION);
		createEAttribute(tggAttributeConstraintParameterValueEClass, TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_VALUE__DERIVED);

		tggcspEClass = createEClass(TGGCSP);
		createEAttribute(tggcspEClass, TGGCSP__PACKAGE);
		createEReference(tggcspEClass, TGGCSP__VALUES);

		tggLocalVariableEClass = createEClass(TGG_LOCAL_VARIABLE);
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
		tggAttributeConstraintDefinitionEClass.getESuperTypes().add(theIBeXCoreModelPackage.getIBeXNamedElement());
		tggAttributeConstraintDefinitionLibraryEClass.getESuperTypes()
				.add(theIBeXCoreModelPackage.getIBeXNamedElement());
		tggAttributeConstraintVariableEClass.getESuperTypes().add(theIBeXCoreArithmeticPackage.getValueExpression());
		tggcspEClass.getESuperTypes().add(theIBeXCoreModelPackage.getIBeXNamedElement());
		tggLocalVariableEClass.getESuperTypes().add(theIBeXCoreModelPackage.getIBeXNamedElement());
		tggLocalVariableEClass.getESuperTypes().add(theIBeXCoreArithmeticPackage.getArithmeticExpression());

		// Initialize classes, features, and operations; add parameters
		initEClass(tggAttributeConstraintDefinitionEClass, TGGAttributeConstraintDefinition.class,
				"TGGAttributeConstraintDefinition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGAttributeConstraintDefinition_ParameterDefinitions(),
				this.getTGGAttributeConstraintParameterDefinition(), null, "parameterDefinitions", null, 0, -1,
				TGGAttributeConstraintDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGAttributeConstraintDefinition_SyncBindings(), this.getTGGAttributeConstraintBinding(),
				null, "syncBindings", null, 0, -1, TGGAttributeConstraintDefinition.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGAttributeConstraintDefinition_GenBindings(), this.getTGGAttributeConstraintBinding(), null,
				"genBindings", null, 0, -1, TGGAttributeConstraintDefinition.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGAttributeConstraintDefinition_Library(), this.getTGGAttributeConstraintDefinitionLibrary(),
				this.getTGGAttributeConstraintDefinitionLibrary_TggAttributeConstraintDefinitions(), "library", null, 0,
				1, TGGAttributeConstraintDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggAttributeConstraintDefinitionLibraryEClass, TGGAttributeConstraintDefinitionLibrary.class,
				"TGGAttributeConstraintDefinitionLibrary", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTGGAttributeConstraintDefinitionLibrary_PackageName(), ecorePackage.getEString(),
				"packageName", null, 0, 1, TGGAttributeConstraintDefinitionLibrary.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGAttributeConstraintDefinitionLibrary_TggAttributeConstraintDefinitions(),
				this.getTGGAttributeConstraintDefinition(), this.getTGGAttributeConstraintDefinition_Library(),
				"tggAttributeConstraintDefinitions", null, 0, -1, TGGAttributeConstraintDefinitionLibrary.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggAttributeConstraintParameterDefinitionEClass, TGGAttributeConstraintParameterDefinition.class,
				"TGGAttributeConstraintParameterDefinition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGAttributeConstraintParameterDefinition_Type(), ecorePackage.getEDataType(), null, "type",
				null, 0, 1, TGGAttributeConstraintParameterDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTGGAttributeConstraintParameterDefinition_Name(), ecorePackage.getEString(), "name", null, 0,
				1, TGGAttributeConstraintParameterDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggAttributeConstraintBindingEClass, TGGAttributeConstraintBinding.class,
				"TGGAttributeConstraintBinding", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTGGAttributeConstraintBinding_Value(), ecorePackage.getEString(), "value", null, 0, -1,
				TGGAttributeConstraintBinding.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				!IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggAttributeConstraintSetEClass, TGGAttributeConstraintSet.class, "TGGAttributeConstraintSet",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGAttributeConstraintSet_TggAttributeConstraints(), this.getTGGAttributeConstraint(), null,
				"tggAttributeConstraints", null, 0, -1, TGGAttributeConstraintSet.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGAttributeConstraintSet_Parameters(), this.getTGGAttributeConstraintParameterValue(), null,
				"parameters", null, 0, -1, TGGAttributeConstraintSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggAttributeConstraintEClass, TGGAttributeConstraint.class, "TGGAttributeConstraint", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGAttributeConstraint_Definition(), this.getTGGAttributeConstraintDefinition(), null,
				"definition", null, 0, 1, TGGAttributeConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGAttributeConstraint_Parameters(), this.getTGGAttributeConstraintParameterValue(), null,
				"parameters", null, 0, -1, TGGAttributeConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggAttributeConstraintVariableEClass, TGGAttributeConstraintVariable.class,
				"TGGAttributeConstraintVariable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTGGAttributeConstraintVariable_Name(), ecorePackage.getEString(), "name", null, 0, 1,
				TGGAttributeConstraintVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggAttributeConstraintParameterValueEClass, TGGAttributeConstraintParameterValue.class,
				"TGGAttributeConstraintParameterValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGAttributeConstraintParameterValue_ParameterDefinition(),
				this.getTGGAttributeConstraintParameterDefinition(), null, "parameterDefinition", null, 0, 1,
				TGGAttributeConstraintParameterValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGAttributeConstraintParameterValue_Expression(),
				theIBeXCoreArithmeticPackage.getValueExpression(), null, "expression", null, 1, 1,
				TGGAttributeConstraintParameterValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTGGAttributeConstraintParameterValue_Derived(), ecorePackage.getEBoolean(), "derived", null,
				0, 1, TGGAttributeConstraintParameterValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggcspEClass, org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGCSP.class, "TGGCSP", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTGGCSP_Package(), ecorePackage.getEString(), "package", null, 0, 1,
				org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGCSP.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGCSP_Values(), theIBeXCoreArithmeticPackage.getValueExpression(), null, "values", null, 0,
				-1, org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGCSP.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggLocalVariableEClass, TGGLocalVariable.class, "TGGLocalVariable", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
	}

} //CSPPackageImpl
