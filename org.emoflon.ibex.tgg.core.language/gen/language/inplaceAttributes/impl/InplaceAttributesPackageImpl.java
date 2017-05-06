/**
 */
package language.inplaceAttributes.impl;

import language.LanguagePackage;

import language.basic.BasicPackage;

import language.basic.expressions.ExpressionsPackage;

import language.basic.expressions.impl.ExpressionsPackageImpl;

import language.basic.impl.BasicPackageImpl;

import language.csp.CspPackage;

import language.csp.definition.DefinitionPackage;

import language.csp.definition.impl.DefinitionPackageImpl;

import language.csp.impl.CspPackageImpl;

import language.impl.LanguagePackageImpl;

import language.inplaceAttributes.InplaceAttributesFactory;
import language.inplaceAttributes.InplaceAttributesPackage;
import language.inplaceAttributes.TGGAttributeConstraintOperators;
import language.inplaceAttributes.TGGInplaceAttributeExpression;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class InplaceAttributesPackageImpl extends EPackageImpl implements InplaceAttributesPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggInplaceAttributeExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum tggAttributeConstraintOperatorsEEnum = null;

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
	 * @see language.inplaceAttributes.InplaceAttributesPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private InplaceAttributesPackageImpl() {
		super(eNS_URI, InplaceAttributesFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link InplaceAttributesPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static InplaceAttributesPackage init() {
		if (isInited)
			return (InplaceAttributesPackage) EPackage.Registry.INSTANCE.getEPackage(InplaceAttributesPackage.eNS_URI);

		// Obtain or create and register package
		InplaceAttributesPackageImpl theInplaceAttributesPackage = (InplaceAttributesPackageImpl) (EPackage.Registry.INSTANCE
				.get(eNS_URI) instanceof InplaceAttributesPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
						: new InplaceAttributesPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		LanguagePackageImpl theLanguagePackage = (LanguagePackageImpl) (EPackage.Registry.INSTANCE
				.getEPackage(LanguagePackage.eNS_URI) instanceof LanguagePackageImpl
						? EPackage.Registry.INSTANCE.getEPackage(LanguagePackage.eNS_URI) : LanguagePackage.eINSTANCE);
		CspPackageImpl theCspPackage = (CspPackageImpl) (EPackage.Registry.INSTANCE
				.getEPackage(CspPackage.eNS_URI) instanceof CspPackageImpl
						? EPackage.Registry.INSTANCE.getEPackage(CspPackage.eNS_URI) : CspPackage.eINSTANCE);
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
		theInplaceAttributesPackage.createPackageContents();
		theLanguagePackage.createPackageContents();
		theCspPackage.createPackageContents();
		theDefinitionPackage.createPackageContents();
		theBasicPackage.createPackageContents();
		theExpressionsPackage.createPackageContents();

		// Initialize created meta-data
		theInplaceAttributesPackage.initializePackageContents();
		theLanguagePackage.initializePackageContents();
		theCspPackage.initializePackageContents();
		theDefinitionPackage.initializePackageContents();
		theBasicPackage.initializePackageContents();
		theExpressionsPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theInplaceAttributesPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(InplaceAttributesPackage.eNS_URI, theInplaceAttributesPackage);
		return theInplaceAttributesPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTGGInplaceAttributeExpression() {
		return tggInplaceAttributeExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGInplaceAttributeExpression_Attribute() {
		return (EReference) tggInplaceAttributeExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGInplaceAttributeExpression_ValueExpr() {
		return (EReference) tggInplaceAttributeExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTGGInplaceAttributeExpression_Operator() {
		return (EAttribute) tggInplaceAttributeExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getTGGAttributeConstraintOperators() {
		return tggAttributeConstraintOperatorsEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InplaceAttributesFactory getInplaceAttributesFactory() {
		return (InplaceAttributesFactory) getEFactoryInstance();
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
		tggInplaceAttributeExpressionEClass = createEClass(TGG_INPLACE_ATTRIBUTE_EXPRESSION);
		createEReference(tggInplaceAttributeExpressionEClass, TGG_INPLACE_ATTRIBUTE_EXPRESSION__ATTRIBUTE);
		createEReference(tggInplaceAttributeExpressionEClass, TGG_INPLACE_ATTRIBUTE_EXPRESSION__VALUE_EXPR);
		createEAttribute(tggInplaceAttributeExpressionEClass, TGG_INPLACE_ATTRIBUTE_EXPRESSION__OPERATOR);

		// Create enums
		tggAttributeConstraintOperatorsEEnum = createEEnum(TGG_ATTRIBUTE_CONSTRAINT_OPERATORS);
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
		ExpressionsPackage theExpressionsPackage = (ExpressionsPackage) EPackage.Registry.INSTANCE
				.getEPackage(ExpressionsPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(tggInplaceAttributeExpressionEClass, TGGInplaceAttributeExpression.class,
				"TGGInplaceAttributeExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGInplaceAttributeExpression_Attribute(), ecorePackage.getEAttribute(), null, "attribute",
				null, 1, 1, TGGInplaceAttributeExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGInplaceAttributeExpression_ValueExpr(), theExpressionsPackage.getTGGExpression(), null,
				"valueExpr", null, 0, 1, TGGInplaceAttributeExpression.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTGGInplaceAttributeExpression_Operator(), this.getTGGAttributeConstraintOperators(),
				"operator", "", 1, 1, TGGInplaceAttributeExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(tggAttributeConstraintOperatorsEEnum, TGGAttributeConstraintOperators.class,
				"TGGAttributeConstraintOperators");
		addEEnumLiteral(tggAttributeConstraintOperatorsEEnum, TGGAttributeConstraintOperators.EQUAL);
		addEEnumLiteral(tggAttributeConstraintOperatorsEEnum, TGGAttributeConstraintOperators.UNEQUAL);
		addEEnumLiteral(tggAttributeConstraintOperatorsEEnum, TGGAttributeConstraintOperators.GR_EQUAL);
		addEEnumLiteral(tggAttributeConstraintOperatorsEEnum, TGGAttributeConstraintOperators.LE_EQUAL);
		addEEnumLiteral(tggAttributeConstraintOperatorsEEnum, TGGAttributeConstraintOperators.GREATER);
		addEEnumLiteral(tggAttributeConstraintOperatorsEEnum, TGGAttributeConstraintOperators.LESSER);
	}

} //InplaceAttributesPackageImpl
