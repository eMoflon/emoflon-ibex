/**
 */
package org.emoflon.ibex.IBeXDisjunctPatternModel.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDependentDisjunctAttribute;
import org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDependentInjectivityConstraints;
import org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctAttribute;
import org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctContextPattern;
import org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctPatternModelFactory;
import org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctPatternModelPackage;
import org.emoflon.ibex.IBeXDisjunctPatternModel.IBexDisjunctInjectivityConstraint;

import org.emoflon.ibex.gt.SGTPatternModel.SGTPatternModelPackage;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class IBeXDisjunctPatternModelPackageImpl extends EPackageImpl implements IBeXDisjunctPatternModelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXDisjunctContextPatternEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXDisjunctAttributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXDependentInjectivityConstraintsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBexDisjunctInjectivityConstraintEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXDependentDisjunctAttributeEClass = null;

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
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctPatternModelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private IBeXDisjunctPatternModelPackageImpl() {
		super(eNS_URI, IBeXDisjunctPatternModelFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link IBeXDisjunctPatternModelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static IBeXDisjunctPatternModelPackage init() {
		if (isInited) return (IBeXDisjunctPatternModelPackage)EPackage.Registry.INSTANCE.getEPackage(IBeXDisjunctPatternModelPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredIBeXDisjunctPatternModelPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		IBeXDisjunctPatternModelPackageImpl theIBeXDisjunctPatternModelPackage = registeredIBeXDisjunctPatternModelPackage instanceof IBeXDisjunctPatternModelPackageImpl ? (IBeXDisjunctPatternModelPackageImpl)registeredIBeXDisjunctPatternModelPackage : new IBeXDisjunctPatternModelPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		IBeXPatternModelPackage.eINSTANCE.eClass();
		SGTPatternModelPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theIBeXDisjunctPatternModelPackage.createPackageContents();

		// Initialize created meta-data
		theIBeXDisjunctPatternModelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theIBeXDisjunctPatternModelPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(IBeXDisjunctPatternModelPackage.eNS_URI, theIBeXDisjunctPatternModelPackage);
		return theIBeXDisjunctPatternModelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXDisjunctContextPattern() {
		return iBeXDisjunctContextPatternEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXDisjunctContextPattern_Subpatterns() {
		return (EReference)iBeXDisjunctContextPatternEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXDisjunctContextPattern_InjectivityConstraints() {
		return (EReference)iBeXDisjunctContextPatternEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXDisjunctContextPattern_AttributesConstraints() {
		return (EReference)iBeXDisjunctContextPatternEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXDisjunctAttribute() {
		return iBeXDisjunctAttributeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXDisjunctAttribute_TargetPattern() {
		return (EReference)iBeXDisjunctAttributeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXDisjunctAttribute_DisjunctAttribute() {
		return (EReference)iBeXDisjunctAttributeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXDisjunctAttribute_SourcePattern() {
		return (EReference)iBeXDisjunctAttributeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXDependentInjectivityConstraints() {
		return iBeXDependentInjectivityConstraintsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXDependentInjectivityConstraints_InjectivityConstraints() {
		return (EReference)iBeXDependentInjectivityConstraintsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXDependentInjectivityConstraints_Patterns() {
		return (EReference)iBeXDependentInjectivityConstraintsEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXDependentInjectivityConstraints_AttributeConstraints() {
		return (EReference)iBeXDependentInjectivityConstraintsEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBexDisjunctInjectivityConstraint() {
		return iBexDisjunctInjectivityConstraintEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBexDisjunctInjectivityConstraint_Pattern1() {
		return (EReference)iBexDisjunctInjectivityConstraintEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBexDisjunctInjectivityConstraint_Pattern2() {
		return (EReference)iBexDisjunctInjectivityConstraintEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBexDisjunctInjectivityConstraint_Node1() {
		return (EReference)iBexDisjunctInjectivityConstraintEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBexDisjunctInjectivityConstraint_Node2() {
		return (EReference)iBexDisjunctInjectivityConstraintEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXDependentDisjunctAttribute() {
		return iBeXDependentDisjunctAttributeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXDependentDisjunctAttribute_Attributes() {
		return (EReference)iBeXDependentDisjunctAttributeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXDependentDisjunctAttribute_DependentPatterns() {
		return (EReference)iBeXDependentDisjunctAttributeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXDependentDisjunctAttribute_InjectivityConstraints() {
		return (EReference)iBeXDependentDisjunctAttributeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXDisjunctPatternModelFactory getIBeXDisjunctPatternModelFactory() {
		return (IBeXDisjunctPatternModelFactory)getEFactoryInstance();
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
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		iBeXDisjunctContextPatternEClass = createEClass(IBE_XDISJUNCT_CONTEXT_PATTERN);
		createEReference(iBeXDisjunctContextPatternEClass, IBE_XDISJUNCT_CONTEXT_PATTERN__SUBPATTERNS);
		createEReference(iBeXDisjunctContextPatternEClass, IBE_XDISJUNCT_CONTEXT_PATTERN__INJECTIVITY_CONSTRAINTS);
		createEReference(iBeXDisjunctContextPatternEClass, IBE_XDISJUNCT_CONTEXT_PATTERN__ATTRIBUTES_CONSTRAINTS);

		iBeXDisjunctAttributeEClass = createEClass(IBE_XDISJUNCT_ATTRIBUTE);
		createEReference(iBeXDisjunctAttributeEClass, IBE_XDISJUNCT_ATTRIBUTE__TARGET_PATTERN);
		createEReference(iBeXDisjunctAttributeEClass, IBE_XDISJUNCT_ATTRIBUTE__DISJUNCT_ATTRIBUTE);
		createEReference(iBeXDisjunctAttributeEClass, IBE_XDISJUNCT_ATTRIBUTE__SOURCE_PATTERN);

		iBeXDependentInjectivityConstraintsEClass = createEClass(IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS);
		createEReference(iBeXDependentInjectivityConstraintsEClass, IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS__INJECTIVITY_CONSTRAINTS);
		createEReference(iBeXDependentInjectivityConstraintsEClass, IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS__PATTERNS);
		createEReference(iBeXDependentInjectivityConstraintsEClass, IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS__ATTRIBUTE_CONSTRAINTS);

		iBexDisjunctInjectivityConstraintEClass = createEClass(IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT);
		createEReference(iBexDisjunctInjectivityConstraintEClass, IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT__PATTERN1);
		createEReference(iBexDisjunctInjectivityConstraintEClass, IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT__PATTERN2);
		createEReference(iBexDisjunctInjectivityConstraintEClass, IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT__NODE1);
		createEReference(iBexDisjunctInjectivityConstraintEClass, IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT__NODE2);

		iBeXDependentDisjunctAttributeEClass = createEClass(IBE_XDEPENDENT_DISJUNCT_ATTRIBUTE);
		createEReference(iBeXDependentDisjunctAttributeEClass, IBE_XDEPENDENT_DISJUNCT_ATTRIBUTE__ATTRIBUTES);
		createEReference(iBeXDependentDisjunctAttributeEClass, IBE_XDEPENDENT_DISJUNCT_ATTRIBUTE__DEPENDENT_PATTERNS);
		createEReference(iBeXDependentDisjunctAttributeEClass, IBE_XDEPENDENT_DISJUNCT_ATTRIBUTE__INJECTIVITY_CONSTRAINTS);
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
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		IBeXPatternModelPackage theIBeXPatternModelPackage = (IBeXPatternModelPackage)EPackage.Registry.INSTANCE.getEPackage(IBeXPatternModelPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		iBeXDisjunctContextPatternEClass.getESuperTypes().add(theIBeXPatternModelPackage.getIBeXContext());

		// Initialize classes, features, and operations; add parameters
		initEClass(iBeXDisjunctContextPatternEClass, IBeXDisjunctContextPattern.class, "IBeXDisjunctContextPattern", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXDisjunctContextPattern_Subpatterns(), theIBeXPatternModelPackage.getIBeXContextPattern(), null, "subpatterns", null, 0, -1, IBeXDisjunctContextPattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXDisjunctContextPattern_InjectivityConstraints(), this.getIBeXDependentInjectivityConstraints(), null, "injectivityConstraints", null, 0, -1, IBeXDisjunctContextPattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXDisjunctContextPattern_AttributesConstraints(), this.getIBeXDependentDisjunctAttribute(), null, "attributesConstraints", null, 0, -1, IBeXDisjunctContextPattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXDisjunctAttributeEClass, IBeXDisjunctAttribute.class, "IBeXDisjunctAttribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXDisjunctAttribute_TargetPattern(), theIBeXPatternModelPackage.getIBeXContextPattern(), null, "targetPattern", null, 0, 1, IBeXDisjunctAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXDisjunctAttribute_DisjunctAttribute(), theIBeXPatternModelPackage.getIBeXAttributeConstraint(), null, "disjunctAttribute", null, 0, -1, IBeXDisjunctAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXDisjunctAttribute_SourcePattern(), theIBeXPatternModelPackage.getIBeXContextPattern(), null, "sourcePattern", null, 0, -1, IBeXDisjunctAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXDependentInjectivityConstraintsEClass, IBeXDependentInjectivityConstraints.class, "IBeXDependentInjectivityConstraints", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXDependentInjectivityConstraints_InjectivityConstraints(), this.getIBexDisjunctInjectivityConstraint(), null, "injectivityConstraints", null, 0, -1, IBeXDependentInjectivityConstraints.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXDependentInjectivityConstraints_Patterns(), theIBeXPatternModelPackage.getIBeXContextPattern(), null, "patterns", null, 0, -1, IBeXDependentInjectivityConstraints.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXDependentInjectivityConstraints_AttributeConstraints(), this.getIBeXDependentDisjunctAttribute(), this.getIBeXDependentDisjunctAttribute_InjectivityConstraints(), "attributeConstraints", null, 0, -1, IBeXDependentInjectivityConstraints.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBexDisjunctInjectivityConstraintEClass, IBexDisjunctInjectivityConstraint.class, "IBexDisjunctInjectivityConstraint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBexDisjunctInjectivityConstraint_Pattern1(), theIBeXPatternModelPackage.getIBeXContextPattern(), null, "pattern1", null, 0, 1, IBexDisjunctInjectivityConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBexDisjunctInjectivityConstraint_Pattern2(), theIBeXPatternModelPackage.getIBeXContextPattern(), null, "pattern2", null, 0, 1, IBexDisjunctInjectivityConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBexDisjunctInjectivityConstraint_Node1(), theIBeXPatternModelPackage.getIBeXNode(), null, "node1", null, 0, -1, IBexDisjunctInjectivityConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBexDisjunctInjectivityConstraint_Node2(), theIBeXPatternModelPackage.getIBeXNode(), null, "node2", null, 0, -1, IBexDisjunctInjectivityConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXDependentDisjunctAttributeEClass, IBeXDependentDisjunctAttribute.class, "IBeXDependentDisjunctAttribute", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXDependentDisjunctAttribute_Attributes(), this.getIBeXDisjunctAttribute(), null, "attributes", null, 0, -1, IBeXDependentDisjunctAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXDependentDisjunctAttribute_DependentPatterns(), theIBeXPatternModelPackage.getIBeXContextPattern(), null, "dependentPatterns", null, 0, -1, IBeXDependentDisjunctAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXDependentDisjunctAttribute_InjectivityConstraints(), this.getIBeXDependentInjectivityConstraints(), this.getIBeXDependentInjectivityConstraints_AttributeConstraints(), "injectivityConstraints", null, 0, 1, IBeXDependentDisjunctAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //IBeXDisjunctPatternModelPackageImpl
