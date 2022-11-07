/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.CSPPackage;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.CSPPackageImpl;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelFactory;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.OperationalisationMode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGCorrespondence;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGEdge;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleElement;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleSet;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class IBeXTGGModelPackageImpl extends EPackageImpl implements IBeXTGGModelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggModelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggRuleSetEClass = null;

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
	private EClass tggOperationalRuleEClass = null;

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
	private EClass tggNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggCorrespondenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggEdgeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum operationalisationModeEEnum = null;

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
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private IBeXTGGModelPackageImpl() {
		super(eNS_URI, IBeXTGGModelFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link IBeXTGGModelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static IBeXTGGModelPackage init() {
		if (isInited)
			return (IBeXTGGModelPackage) EPackage.Registry.INSTANCE.getEPackage(IBeXTGGModelPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredIBeXTGGModelPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		IBeXTGGModelPackageImpl theIBeXTGGModelPackage = registeredIBeXTGGModelPackage instanceof IBeXTGGModelPackageImpl
				? (IBeXTGGModelPackageImpl) registeredIBeXTGGModelPackage
				: new IBeXTGGModelPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		IBeXCoreModelPackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(CSPPackage.eNS_URI);
		CSPPackageImpl theCSPPackage = (CSPPackageImpl) (registeredPackage instanceof CSPPackageImpl ? registeredPackage
				: CSPPackage.eINSTANCE);

		// Create package meta-data objects
		theIBeXTGGModelPackage.createPackageContents();
		theCSPPackage.createPackageContents();

		// Initialize created meta-data
		theIBeXTGGModelPackage.initializePackageContents();
		theCSPPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theIBeXTGGModelPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(IBeXTGGModelPackage.eNS_URI, theIBeXTGGModelPackage);
		return theIBeXTGGModelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTGGModel() {
		return tggModelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGModel_Source() {
		return (EReference) tggModelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGModel_Target() {
		return (EReference) tggModelEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGModel_Correspondence() {
		return (EReference) tggModelEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGModel_RuleSet() {
		return (EReference) tggModelEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGModel_AttributeConstraintDefinitionLibraries() {
		return (EReference) tggModelEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTGGRuleSet() {
		return tggRuleSetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRuleSet_Rules() {
		return (EReference) tggRuleSetEClass.getEStructuralFeatures().get(0);
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
	public EReference getTGGRule_Nodes() {
		return (EReference) tggRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRule_Edges() {
		return (EReference) tggRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRule_CorrespondenceNodes() {
		return (EReference) tggRuleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRule_Operationalisations() {
		return (EReference) tggRuleEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRule_AttributeConstraints() {
		return (EReference) tggRuleEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTGGOperationalRule() {
		return tggOperationalRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTGGOperationalRule_OperationalisationMode() {
		return (EAttribute) tggOperationalRuleEClass.getEStructuralFeatures().get(0);
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
	public EClass getTGGNode() {
		return tggNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGNode_IncomingCorrespondence() {
		return (EReference) tggNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGNode_OutgoingCorrespondence() {
		return (EReference) tggNodeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTGGCorrespondence() {
		return tggCorrespondenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGCorrespondence_Source() {
		return (EReference) tggCorrespondenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGCorrespondence_Target() {
		return (EReference) tggCorrespondenceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTGGEdge() {
		return tggEdgeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getOperationalisationMode() {
		return operationalisationModeEEnum;
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
	public IBeXTGGModelFactory getIBeXTGGModelFactory() {
		return (IBeXTGGModelFactory) getEFactoryInstance();
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
		tggModelEClass = createEClass(TGG_MODEL);
		createEReference(tggModelEClass, TGG_MODEL__SOURCE);
		createEReference(tggModelEClass, TGG_MODEL__TARGET);
		createEReference(tggModelEClass, TGG_MODEL__CORRESPONDENCE);
		createEReference(tggModelEClass, TGG_MODEL__RULE_SET);
		createEReference(tggModelEClass, TGG_MODEL__ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARIES);

		tggRuleSetEClass = createEClass(TGG_RULE_SET);
		createEReference(tggRuleSetEClass, TGG_RULE_SET__RULES);

		tggRuleEClass = createEClass(TGG_RULE);
		createEReference(tggRuleEClass, TGG_RULE__NODES);
		createEReference(tggRuleEClass, TGG_RULE__EDGES);
		createEReference(tggRuleEClass, TGG_RULE__CORRESPONDENCE_NODES);
		createEReference(tggRuleEClass, TGG_RULE__OPERATIONALISATIONS);
		createEReference(tggRuleEClass, TGG_RULE__ATTRIBUTE_CONSTRAINTS);

		tggOperationalRuleEClass = createEClass(TGG_OPERATIONAL_RULE);
		createEAttribute(tggOperationalRuleEClass, TGG_OPERATIONAL_RULE__OPERATIONALISATION_MODE);

		tggRuleElementEClass = createEClass(TGG_RULE_ELEMENT);
		createEAttribute(tggRuleElementEClass, TGG_RULE_ELEMENT__DOMAIN_TYPE);
		createEAttribute(tggRuleElementEClass, TGG_RULE_ELEMENT__BINDING_TYPE);

		tggNodeEClass = createEClass(TGG_NODE);
		createEReference(tggNodeEClass, TGG_NODE__INCOMING_CORRESPONDENCE);
		createEReference(tggNodeEClass, TGG_NODE__OUTGOING_CORRESPONDENCE);

		tggCorrespondenceEClass = createEClass(TGG_CORRESPONDENCE);
		createEReference(tggCorrespondenceEClass, TGG_CORRESPONDENCE__SOURCE);
		createEReference(tggCorrespondenceEClass, TGG_CORRESPONDENCE__TARGET);

		tggEdgeEClass = createEClass(TGG_EDGE);

		// Create enums
		operationalisationModeEEnum = createEEnum(OPERATIONALISATION_MODE);
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
		CSPPackage theCSPPackage = (CSPPackage) EPackage.Registry.INSTANCE.getEPackage(CSPPackage.eNS_URI);
		IBeXCoreModelPackage theIBeXCoreModelPackage = (IBeXCoreModelPackage) EPackage.Registry.INSTANCE
				.getEPackage(IBeXCoreModelPackage.eNS_URI);

		// Add subpackages
		getESubpackages().add(theCSPPackage);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		tggModelEClass.getESuperTypes().add(theIBeXCoreModelPackage.getIBeXModel());
		tggRuleEClass.getESuperTypes().add(theIBeXCoreModelPackage.getIBeXRule());
		tggOperationalRuleEClass.getESuperTypes().add(this.getTGGRule());
		tggNodeEClass.getESuperTypes().add(theIBeXCoreModelPackage.getIBeXNode());
		tggNodeEClass.getESuperTypes().add(this.getTGGRuleElement());
		tggCorrespondenceEClass.getESuperTypes().add(this.getTGGNode());
		tggEdgeEClass.getESuperTypes().add(theIBeXCoreModelPackage.getIBeXEdge());
		tggEdgeEClass.getESuperTypes().add(this.getTGGRuleElement());

		// Initialize classes, features, and operations; add parameters
		initEClass(tggModelEClass, TGGModel.class, "TGGModel", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGModel_Source(), ecorePackage.getEPackage(), null, "source", null, 0, -1, TGGModel.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGModel_Target(), ecorePackage.getEPackage(), null, "target", null, 0, -1, TGGModel.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGModel_Correspondence(), ecorePackage.getEPackage(), null, "correspondence", null, 0, 1,
				TGGModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGModel_RuleSet(), this.getTGGRuleSet(), null, "ruleSet", null, 1, 1, TGGModel.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGModel_AttributeConstraintDefinitionLibraries(),
				theCSPPackage.getTGGAttributeConstraintDefinitionLibrary(), null,
				"attributeConstraintDefinitionLibraries", null, 0, -1, TGGModel.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggRuleSetEClass, TGGRuleSet.class, "TGGRuleSet", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGRuleSet_Rules(), this.getTGGRule(), null, "rules", null, 0, -1, TGGRuleSet.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggRuleEClass, TGGRule.class, "TGGRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGRule_Nodes(), this.getTGGNode(), null, "nodes", null, 0, -1, TGGRule.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getTGGRule_Edges(), this.getTGGEdge(), null, "edges", null, 0, -1, TGGRule.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getTGGRule_CorrespondenceNodes(), this.getTGGCorrespondence(), null, "correspondenceNodes", null,
				0, -1, TGGRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRule_Operationalisations(), this.getTGGOperationalRule(), null, "operationalisations",
				null, 0, -1, TGGRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRule_AttributeConstraints(), theCSPPackage.getTGGAttributeConstraintSet(), null,
				"attributeConstraints", null, 0, 1, TGGRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggOperationalRuleEClass, TGGOperationalRule.class, "TGGOperationalRule", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTGGOperationalRule_OperationalisationMode(), this.getOperationalisationMode(),
				"operationalisationMode", null, 0, 1, TGGOperationalRule.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggRuleElementEClass, TGGRuleElement.class, "TGGRuleElement", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTGGRuleElement_DomainType(), this.getDomainType(), "domainType", null, 0, 1,
				TGGRuleElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getTGGRuleElement_BindingType(), this.getBindingType(), "bindingType", null, 0, 1,
				TGGRuleElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(tggNodeEClass, TGGNode.class, "TGGNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGNode_IncomingCorrespondence(), this.getTGGCorrespondence(),
				this.getTGGCorrespondence_Target(), "incomingCorrespondence", null, 0, -1, TGGNode.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getTGGNode_OutgoingCorrespondence(), this.getTGGCorrespondence(),
				this.getTGGCorrespondence_Source(), "outgoingCorrespondence", null, 0, -1, TGGNode.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(tggCorrespondenceEClass, TGGCorrespondence.class, "TGGCorrespondence", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGCorrespondence_Source(), this.getTGGNode(), this.getTGGNode_OutgoingCorrespondence(),
				"source", null, 1, 1, TGGCorrespondence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGCorrespondence_Target(), this.getTGGNode(), this.getTGGNode_IncomingCorrespondence(),
				"target", null, 1, 1, TGGCorrespondence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggEdgeEClass, TGGEdge.class, "TGGEdge", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Initialize enums and add enum literals
		initEEnum(operationalisationModeEEnum, OperationalisationMode.class, "OperationalisationMode");
		addEEnumLiteral(operationalisationModeEEnum, OperationalisationMode.GENERATE);
		addEEnumLiteral(operationalisationModeEEnum, OperationalisationMode.FORWARD);
		addEEnumLiteral(operationalisationModeEEnum, OperationalisationMode.BACKWARD);
		addEEnumLiteral(operationalisationModeEEnum, OperationalisationMode.CONSISTENCY_CHECK);
		addEEnumLiteral(operationalisationModeEEnum, OperationalisationMode.CHECK_ONLY);

		initEEnum(domainTypeEEnum, DomainType.class, "DomainType");
		addEEnumLiteral(domainTypeEEnum, DomainType.SOURCE);
		addEEnumLiteral(domainTypeEEnum, DomainType.TARGET);
		addEEnumLiteral(domainTypeEEnum, DomainType.CORRESPONDENCE);
		addEEnumLiteral(domainTypeEEnum, DomainType.PATTERN);

		initEEnum(bindingTypeEEnum, BindingType.class, "BindingType");
		addEEnumLiteral(bindingTypeEEnum, BindingType.CONTEXT);
		addEEnumLiteral(bindingTypeEEnum, BindingType.CREATE);
		addEEnumLiteral(bindingTypeEEnum, BindingType.DELETE);
		addEEnumLiteral(bindingTypeEEnum, BindingType.NEGATIVE);
		addEEnumLiteral(bindingTypeEEnum, BindingType.RELAXED);

		// Create resource
		createResource(eNS_URI);
	}

} //IBeXTGGModelPackageImpl
