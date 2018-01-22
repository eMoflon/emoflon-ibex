/**
 */
package language.basic.expressions.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import language.LanguagePackage;
import language.basic.BasicPackage;
import language.basic.expressions.ExpressionsFactory;
import language.basic.expressions.ExpressionsPackage;
import language.basic.expressions.TGGAttributeExpression;
import language.basic.expressions.TGGEnumExpression;
import language.basic.expressions.TGGExpression;
import language.basic.expressions.TGGLiteralExpression;
import language.basic.expressions.TGGParamValue;
import language.basic.impl.BasicPackageImpl;
import language.csp.CspPackage;
import language.csp.definition.DefinitionPackage;
import language.csp.definition.impl.DefinitionPackageImpl;
import language.csp.impl.CspPackageImpl;
import language.impl.LanguagePackageImpl;
import language.inplaceAttributes.InplaceAttributesPackage;
import language.inplaceAttributes.impl.InplaceAttributesPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ExpressionsPackageImpl extends EPackageImpl implements ExpressionsPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggParamValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggLiteralExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggAttributeExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggEnumExpressionEClass = null;

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
	 * @see language.basic.expressions.ExpressionsPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ExpressionsPackageImpl() {
		super(eNS_URI, ExpressionsFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link ExpressionsPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ExpressionsPackage init() {
		if (isInited)
			return (ExpressionsPackage) EPackage.Registry.INSTANCE.getEPackage(ExpressionsPackage.eNS_URI);

		// Obtain or create and register package
		ExpressionsPackageImpl theExpressionsPackage = (ExpressionsPackageImpl) (EPackage.Registry.INSTANCE
				.get(eNS_URI) instanceof ExpressionsPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
						: new ExpressionsPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

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
		DefinitionPackageImpl theDefinitionPackage = (DefinitionPackageImpl) (EPackage.Registry.INSTANCE
				.getEPackage(DefinitionPackage.eNS_URI) instanceof DefinitionPackageImpl
						? EPackage.Registry.INSTANCE.getEPackage(DefinitionPackage.eNS_URI)
						: DefinitionPackage.eINSTANCE);
		BasicPackageImpl theBasicPackage = (BasicPackageImpl) (EPackage.Registry.INSTANCE
				.getEPackage(BasicPackage.eNS_URI) instanceof BasicPackageImpl
						? EPackage.Registry.INSTANCE.getEPackage(BasicPackage.eNS_URI)
						: BasicPackage.eINSTANCE);

		// Create package meta-data objects
		theExpressionsPackage.createPackageContents();
		theLanguagePackage.createPackageContents();
		theInplaceAttributesPackage.createPackageContents();
		theCspPackage.createPackageContents();
		theDefinitionPackage.createPackageContents();
		theBasicPackage.createPackageContents();

		// Initialize created meta-data
		theExpressionsPackage.initializePackageContents();
		theLanguagePackage.initializePackageContents();
		theInplaceAttributesPackage.initializePackageContents();
		theCspPackage.initializePackageContents();
		theDefinitionPackage.initializePackageContents();
		theBasicPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theExpressionsPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ExpressionsPackage.eNS_URI, theExpressionsPackage);
		return theExpressionsPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTGGParamValue() {
		return tggParamValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGParamValue_ParameterDefinition() {
		return (EReference) tggParamValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTGGExpression() {
		return tggExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTGGLiteralExpression() {
		return tggLiteralExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTGGLiteralExpression_Value() {
		return (EAttribute) tggLiteralExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTGGAttributeExpression() {
		return tggAttributeExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGAttributeExpression_ObjectVar() {
		return (EReference) tggAttributeExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGAttributeExpression_Attribute() {
		return (EReference) tggAttributeExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTGGEnumExpression() {
		return tggEnumExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGEnumExpression_Eenum() {
		return (EReference) tggEnumExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGEnumExpression_Literal() {
		return (EReference) tggEnumExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExpressionsFactory getExpressionsFactory() {
		return (ExpressionsFactory) getEFactoryInstance();
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
		tggParamValueEClass = createEClass(TGG_PARAM_VALUE);
		createEReference(tggParamValueEClass, TGG_PARAM_VALUE__PARAMETER_DEFINITION);

		tggExpressionEClass = createEClass(TGG_EXPRESSION);

		tggLiteralExpressionEClass = createEClass(TGG_LITERAL_EXPRESSION);
		createEAttribute(tggLiteralExpressionEClass, TGG_LITERAL_EXPRESSION__VALUE);

		tggAttributeExpressionEClass = createEClass(TGG_ATTRIBUTE_EXPRESSION);
		createEReference(tggAttributeExpressionEClass, TGG_ATTRIBUTE_EXPRESSION__OBJECT_VAR);
		createEReference(tggAttributeExpressionEClass, TGG_ATTRIBUTE_EXPRESSION__ATTRIBUTE);

		tggEnumExpressionEClass = createEClass(TGG_ENUM_EXPRESSION);
		createEReference(tggEnumExpressionEClass, TGG_ENUM_EXPRESSION__EENUM);
		createEReference(tggEnumExpressionEClass, TGG_ENUM_EXPRESSION__LITERAL);
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
		LanguagePackage theLanguagePackage = (LanguagePackage) EPackage.Registry.INSTANCE
				.getEPackage(LanguagePackage.eNS_URI);
		EcorePackage theEcorePackage = (EcorePackage) EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		tggExpressionEClass.getESuperTypes().add(this.getTGGParamValue());
		tggLiteralExpressionEClass.getESuperTypes().add(this.getTGGExpression());
		tggAttributeExpressionEClass.getESuperTypes().add(this.getTGGExpression());
		tggEnumExpressionEClass.getESuperTypes().add(this.getTGGExpression());

		// Initialize classes, features, and operations; add parameters
		initEClass(tggParamValueEClass, TGGParamValue.class, "TGGParamValue", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGParamValue_ParameterDefinition(),
				theDefinitionPackage.getTGGAttributeConstraintParameterDefinition(), null, "parameterDefinition", null,
				0, 1, TGGParamValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggExpressionEClass, TGGExpression.class, "TGGExpression", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(tggLiteralExpressionEClass, TGGLiteralExpression.class, "TGGLiteralExpression", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTGGLiteralExpression_Value(), ecorePackage.getEString(), "value", null, 0, 1,
				TGGLiteralExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggAttributeExpressionEClass, TGGAttributeExpression.class, "TGGAttributeExpression", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGAttributeExpression_ObjectVar(), theLanguagePackage.getTGGRuleNode(), null, "objectVar",
				null, 0, 1, TGGAttributeExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGAttributeExpression_Attribute(), theEcorePackage.getEAttribute(), null, "attribute", null,
				0, 1, TGGAttributeExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggEnumExpressionEClass, TGGEnumExpression.class, "TGGEnumExpression", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGEnumExpression_Eenum(), theEcorePackage.getEEnum(), null, "eenum", null, 0, 1,
				TGGEnumExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGEnumExpression_Literal(), theEcorePackage.getEEnumLiteral(), null, "literal", null, 0, 1,
				TGGEnumExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
	}

} //ExpressionsPackageImpl
