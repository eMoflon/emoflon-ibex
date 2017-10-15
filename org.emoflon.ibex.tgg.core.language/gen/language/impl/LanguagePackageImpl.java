/**
 */
package language.impl;

import language.BindingType;
import language.DomainType;
import language.LanguageFactory;
import language.LanguagePackage;
import language.TGGComplementRule;
import language.TGGRule;
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

import language.basic.BasicPackage;

import language.basic.expressions.ExpressionsPackage;

import language.basic.expressions.impl.ExpressionsPackageImpl;

import language.basic.impl.BasicPackageImpl;

import language.csp.CspPackage;

import language.csp.definition.DefinitionPackage;

import language.csp.definition.impl.DefinitionPackageImpl;

import language.csp.impl.CspPackageImpl;

import language.inplaceAttributes.InplaceAttributesPackage;

import language.inplaceAttributes.impl.InplaceAttributesPackageImpl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
public class LanguagePackageImpl extends EPackageImpl implements LanguagePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggRuleElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggRuleNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggRuleCorrEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggRuleEdgeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nacEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggComplementRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum domainTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum bindingTypeEEnum = null;

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
	 * @see language.LanguagePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private LanguagePackageImpl() {
		super(eNS_URI, LanguageFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link LanguagePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static LanguagePackage init() {
		if (isInited)
			return (LanguagePackage) EPackage.Registry.INSTANCE.getEPackage(LanguagePackage.eNS_URI);

		// Obtain or create and register package
		LanguagePackageImpl theLanguagePackage = (LanguagePackageImpl) (EPackage.Registry.INSTANCE
				.get(eNS_URI) instanceof LanguagePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
						: new LanguagePackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
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
		ExpressionsPackageImpl theExpressionsPackage = (ExpressionsPackageImpl) (EPackage.Registry.INSTANCE
				.getEPackage(ExpressionsPackage.eNS_URI) instanceof ExpressionsPackageImpl
						? EPackage.Registry.INSTANCE.getEPackage(ExpressionsPackage.eNS_URI)
						: ExpressionsPackage.eINSTANCE);
		EcorePackageImpl theEcorePackage = (EcorePackageImpl) (EPackage.Registry.INSTANCE
				.getEPackage(EcorePackage.eNS_URI) instanceof EcorePackageImpl
						? EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI)
						: EcorePackage.eINSTANCE);

		// Create package meta-data objects
		theLanguagePackage.createPackageContents();
		theInplaceAttributesPackage.createPackageContents();
		theCspPackage.createPackageContents();
		theDefinitionPackage.createPackageContents();
		theBasicPackage.createPackageContents();
		theExpressionsPackage.createPackageContents();
		theEcorePackage.createPackageContents();

		// Initialize created meta-data
		theLanguagePackage.initializePackageContents();
		theInplaceAttributesPackage.initializePackageContents();
		theCspPackage.initializePackageContents();
		theDefinitionPackage.initializePackageContents();
		theBasicPackage.initializePackageContents();
		theExpressionsPackage.initializePackageContents();
		theEcorePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theLanguagePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(LanguagePackage.eNS_URI, theLanguagePackage);
		return theLanguagePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTGG() {
		return tggEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGG_Src() {
		return (EReference) tggEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGG_Trg() {
		return (EReference) tggEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGG_Corr() {
		return (EReference) tggEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGG_Rules() {
		return (EReference) tggEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGG_AttributeConstraintDefinitionLibrary() {
		return (EReference) tggEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTGGRule() {
		return tggRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRule_Refines() {
		return (EReference) tggRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRule_Nacs() {
		return (EReference) tggRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRule_Nodes() {
		return (EReference) tggRuleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRule_Edges() {
		return (EReference) tggRuleEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRule_AttributeConditionLibrary() {
		return (EReference) tggRuleEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTGGRule_Abstract() {
		return (EAttribute) tggRuleEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTGGRuleElement() {
		return tggRuleElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTGGRuleElement_DomainType() {
		return (EAttribute) tggRuleElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTGGRuleElement_BindingType() {
		return (EAttribute) tggRuleElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTGGRuleNode() {
		return tggRuleNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRuleNode_IncomingEdges() {
		return (EReference) tggRuleNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRuleNode_OutgoingEdges() {
		return (EReference) tggRuleNodeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRuleNode_Type() {
		return (EReference) tggRuleNodeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRuleNode_IncomingCorrsSource() {
		return (EReference) tggRuleNodeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRuleNode_IncomingCorrsTarget() {
		return (EReference) tggRuleNodeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRuleNode_AttrExpr() {
		return (EReference) tggRuleNodeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTGGRuleCorr() {
		return tggRuleCorrEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRuleCorr_Source() {
		return (EReference) tggRuleCorrEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRuleCorr_Target() {
		return (EReference) tggRuleCorrEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTGGRuleEdge() {
		return tggRuleEdgeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRuleEdge_SrcNode() {
		return (EReference) tggRuleEdgeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRuleEdge_TrgNode() {
		return (EReference) tggRuleEdgeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRuleEdge_Type() {
		return (EReference) tggRuleEdgeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNAC() {
		return nacEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNAC_Nodes() {
		return (EReference) nacEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNAC_Edges() {
		return (EReference) nacEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNAC_AttributeConditionLibrary() {
		return (EReference) nacEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTGGComplementRule() {
		return tggComplementRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTGGComplementRule_Bounded() {
		return (EAttribute) tggComplementRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTGGComplementRule_RuleApplicationLowerBound() {
		return (EAttribute) tggComplementRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTGGComplementRule_RuleApplicationUpperBound() {
		return (EAttribute) tggComplementRuleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGComplementRule_Kernel() {
		return (EReference) tggComplementRuleEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getDomainType() {
		return domainTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getBindingType() {
		return bindingTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LanguageFactory getLanguageFactory() {
		return (LanguageFactory) getEFactoryInstance();
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
		tggEClass = createEClass(TGG);
		createEReference(tggEClass, TGG__SRC);
		createEReference(tggEClass, TGG__TRG);
		createEReference(tggEClass, TGG__CORR);
		createEReference(tggEClass, TGG__RULES);
		createEReference(tggEClass, TGG__ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY);

		tggRuleEClass = createEClass(TGG_RULE);
		createEReference(tggRuleEClass, TGG_RULE__REFINES);
		createEReference(tggRuleEClass, TGG_RULE__NACS);
		createEReference(tggRuleEClass, TGG_RULE__NODES);
		createEReference(tggRuleEClass, TGG_RULE__EDGES);
		createEReference(tggRuleEClass, TGG_RULE__ATTRIBUTE_CONDITION_LIBRARY);
		createEAttribute(tggRuleEClass, TGG_RULE__ABSTRACT);

		tggRuleElementEClass = createEClass(TGG_RULE_ELEMENT);
		createEAttribute(tggRuleElementEClass, TGG_RULE_ELEMENT__DOMAIN_TYPE);
		createEAttribute(tggRuleElementEClass, TGG_RULE_ELEMENT__BINDING_TYPE);

		tggRuleNodeEClass = createEClass(TGG_RULE_NODE);
		createEReference(tggRuleNodeEClass, TGG_RULE_NODE__INCOMING_EDGES);
		createEReference(tggRuleNodeEClass, TGG_RULE_NODE__OUTGOING_EDGES);
		createEReference(tggRuleNodeEClass, TGG_RULE_NODE__TYPE);
		createEReference(tggRuleNodeEClass, TGG_RULE_NODE__INCOMING_CORRS_SOURCE);
		createEReference(tggRuleNodeEClass, TGG_RULE_NODE__INCOMING_CORRS_TARGET);
		createEReference(tggRuleNodeEClass, TGG_RULE_NODE__ATTR_EXPR);

		tggRuleCorrEClass = createEClass(TGG_RULE_CORR);
		createEReference(tggRuleCorrEClass, TGG_RULE_CORR__SOURCE);
		createEReference(tggRuleCorrEClass, TGG_RULE_CORR__TARGET);

		tggRuleEdgeEClass = createEClass(TGG_RULE_EDGE);
		createEReference(tggRuleEdgeEClass, TGG_RULE_EDGE__SRC_NODE);
		createEReference(tggRuleEdgeEClass, TGG_RULE_EDGE__TRG_NODE);
		createEReference(tggRuleEdgeEClass, TGG_RULE_EDGE__TYPE);

		nacEClass = createEClass(NAC);
		createEReference(nacEClass, NAC__NODES);
		createEReference(nacEClass, NAC__EDGES);
		createEReference(nacEClass, NAC__ATTRIBUTE_CONDITION_LIBRARY);

		tggComplementRuleEClass = createEClass(TGG_COMPLEMENT_RULE);
		createEAttribute(tggComplementRuleEClass, TGG_COMPLEMENT_RULE__BOUNDED);
		createEAttribute(tggComplementRuleEClass, TGG_COMPLEMENT_RULE__RULE_APPLICATION_LOWER_BOUND);
		createEAttribute(tggComplementRuleEClass, TGG_COMPLEMENT_RULE__RULE_APPLICATION_UPPER_BOUND);
		createEReference(tggComplementRuleEClass, TGG_COMPLEMENT_RULE__KERNEL);

		// Create enums
		domainTypeEEnum = createEEnum(DOMAIN_TYPE);
		bindingTypeEEnum = createEEnum(BINDING_TYPE);
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
		InplaceAttributesPackage theInplaceAttributesPackage = (InplaceAttributesPackage) EPackage.Registry.INSTANCE
				.getEPackage(InplaceAttributesPackage.eNS_URI);
		CspPackage theCspPackage = (CspPackage) EPackage.Registry.INSTANCE.getEPackage(CspPackage.eNS_URI);
		BasicPackage theBasicPackage = (BasicPackage) EPackage.Registry.INSTANCE.getEPackage(BasicPackage.eNS_URI);
		EcorePackage theEcorePackage = (EcorePackage) EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);
		DefinitionPackage theDefinitionPackage = (DefinitionPackage) EPackage.Registry.INSTANCE
				.getEPackage(DefinitionPackage.eNS_URI);

		// Add subpackages
		getESubpackages().add(theInplaceAttributesPackage);
		getESubpackages().add(theCspPackage);
		getESubpackages().add(theBasicPackage);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		tggEClass.getESuperTypes().add(theBasicPackage.getTGGNamedElement());
		tggRuleEClass.getESuperTypes().add(theBasicPackage.getTGGNamedElement());
		tggRuleElementEClass.getESuperTypes().add(theBasicPackage.getTGGNamedElement());
		tggRuleNodeEClass.getESuperTypes().add(this.getTGGRuleElement());
		tggRuleCorrEClass.getESuperTypes().add(this.getTGGRuleNode());
		tggRuleEdgeEClass.getESuperTypes().add(this.getTGGRuleElement());
		nacEClass.getESuperTypes().add(theBasicPackage.getTGGNamedElement());
		tggComplementRuleEClass.getESuperTypes().add(this.getTGGRule());

		// Initialize classes, features, and operations; add parameters
		initEClass(tggEClass, language.TGG.class, "TGG", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGG_Src(), theEcorePackage.getEPackage(), null, "src", null, 0, -1, language.TGG.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGG_Trg(), theEcorePackage.getEPackage(), null, "trg", null, 0, -1, language.TGG.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGG_Corr(), theEcorePackage.getEPackage(), null, "corr", null, 0, 1, language.TGG.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGG_Rules(), this.getTGGRule(), null, "rules", null, 0, -1, language.TGG.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getTGG_AttributeConstraintDefinitionLibrary(),
				theDefinitionPackage.getTGGAttributeConstraintDefinitionLibrary(), null,
				"attributeConstraintDefinitionLibrary", null, 1, 1, language.TGG.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggRuleEClass, TGGRule.class, "TGGRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGRule_Refines(), this.getTGGRule(), null, "refines", null, 0, -1, TGGRule.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRule_Nacs(), this.getNAC(), null, "nacs", null, 0, -1, TGGRule.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getTGGRule_Nodes(), this.getTGGRuleNode(), null, "nodes", null, 0, -1, TGGRule.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRule_Edges(), this.getTGGRuleEdge(), null, "edges", null, 0, -1, TGGRule.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRule_AttributeConditionLibrary(), theCspPackage.getTGGAttributeConstraintLibrary(), null,
				"attributeConditionLibrary", null, 1, 1, TGGRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTGGRule_Abstract(), ecorePackage.getEBoolean(), "abstract", null, 0, 1, TGGRule.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggRuleElementEClass, TGGRuleElement.class, "TGGRuleElement", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTGGRuleElement_DomainType(), this.getDomainType(), "domainType", null, 0, 1,
				TGGRuleElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getTGGRuleElement_BindingType(), this.getBindingType(), "bindingType", null, 0, 1,
				TGGRuleElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(tggRuleNodeEClass, TGGRuleNode.class, "TGGRuleNode", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGRuleNode_IncomingEdges(), this.getTGGRuleEdge(), this.getTGGRuleEdge_TrgNode(),
				"incomingEdges", null, 0, -1, TGGRuleNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRuleNode_OutgoingEdges(), this.getTGGRuleEdge(), this.getTGGRuleEdge_SrcNode(),
				"outgoingEdges", null, 0, -1, TGGRuleNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRuleNode_Type(), theEcorePackage.getEClass(), null, "type", null, 0, 1, TGGRuleNode.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRuleNode_IncomingCorrsSource(), this.getTGGRuleCorr(), this.getTGGRuleCorr_Source(),
				"incomingCorrsSource", null, 0, -1, TGGRuleNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRuleNode_IncomingCorrsTarget(), this.getTGGRuleCorr(), this.getTGGRuleCorr_Target(),
				"incomingCorrsTarget", null, 0, -1, TGGRuleNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRuleNode_AttrExpr(), theInplaceAttributesPackage.getTGGInplaceAttributeExpression(), null,
				"attrExpr", null, 0, -1, TGGRuleNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggRuleCorrEClass, TGGRuleCorr.class, "TGGRuleCorr", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGRuleCorr_Source(), this.getTGGRuleNode(), this.getTGGRuleNode_IncomingCorrsSource(),
				"source", null, 0, 1, TGGRuleCorr.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRuleCorr_Target(), this.getTGGRuleNode(), this.getTGGRuleNode_IncomingCorrsTarget(),
				"target", null, 0, 1, TGGRuleCorr.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggRuleEdgeEClass, TGGRuleEdge.class, "TGGRuleEdge", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGRuleEdge_SrcNode(), this.getTGGRuleNode(), this.getTGGRuleNode_OutgoingEdges(), "srcNode",
				null, 0, 1, TGGRuleEdge.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRuleEdge_TrgNode(), this.getTGGRuleNode(), this.getTGGRuleNode_IncomingEdges(), "trgNode",
				null, 0, 1, TGGRuleEdge.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRuleEdge_Type(), theEcorePackage.getEReference(), null, "type", null, 0, 1,
				TGGRuleEdge.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(nacEClass, language.NAC.class, "NAC", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getNAC_Nodes(), this.getTGGRuleNode(), null, "nodes", null, 0, -1, language.NAC.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNAC_Edges(), this.getTGGRuleEdge(), null, "edges", null, 0, -1, language.NAC.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNAC_AttributeConditionLibrary(), theCspPackage.getTGGAttributeConstraintLibrary(), null,
				"attributeConditionLibrary", null, 1, 1, language.NAC.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggComplementRuleEClass, TGGComplementRule.class, "TGGComplementRule", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTGGComplementRule_Bounded(), ecorePackage.getEBoolean(), "bounded", null, 0, 1,
				TGGComplementRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getTGGComplementRule_RuleApplicationLowerBound(), ecorePackage.getEInt(),
				"ruleApplicationLowerBound", null, 0, 1, TGGComplementRule.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTGGComplementRule_RuleApplicationUpperBound(), ecorePackage.getEInt(),
				"ruleApplicationUpperBound", null, 0, 1, TGGComplementRule.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGComplementRule_Kernel(), this.getTGGRule(), null, "kernel", null, 0, 1,
				TGGComplementRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(domainTypeEEnum, DomainType.class, "DomainType");
		addEEnumLiteral(domainTypeEEnum, DomainType.SRC);
		addEEnumLiteral(domainTypeEEnum, DomainType.TRG);
		addEEnumLiteral(domainTypeEEnum, DomainType.CORR);

		initEEnum(bindingTypeEEnum, BindingType.class, "BindingType");
		addEEnumLiteral(bindingTypeEEnum, BindingType.CONTEXT);
		addEEnumLiteral(bindingTypeEEnum, BindingType.CREATE);

		// Create resource
		createResource(eNS_URI);
	}

} //LanguagePackageImpl
