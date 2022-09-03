/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.IBeXCoreArithmeticPackage;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelFactory;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraint;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintBinding;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintDefinition;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintDefinitionLibrary;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintLibrary;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintParameterDefinition;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGEdge;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGParameterValue;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleCorrespondence;
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
	private EClass tggNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggRuleCorrespondenceEClass = null;

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
	private EClass tggRuleElementEClass = null;

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
	private EClass tggAttributeConstraintDefinitionLibraryEClass = null;

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
	private EClass tggParameterValueEClass = null;

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
	private EClass tggAttributeConstraintDefinitionEClass = null;

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

		// Create package meta-data objects
		theIBeXTGGModelPackage.createPackageContents();

		// Initialize created meta-data
		theIBeXTGGModelPackage.initializePackageContents();

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
	public EReference getTGGRule_Refines() {
		return (EReference) tggRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTGGRule_Abstract() {
		return (EAttribute) tggRuleEClass.getEStructuralFeatures().get(1);
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
	public EReference getTGGRule_CorrespondenceNodes() {
		return (EReference) tggRuleEClass.getEStructuralFeatures().get(4);
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
	public EClass getTGGRuleCorrespondence() {
		return tggRuleCorrespondenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRuleCorrespondence_Source() {
		return (EReference) tggRuleCorrespondenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRuleCorrespondence_Target() {
		return (EReference) tggRuleCorrespondenceEClass.getEStructuralFeatures().get(1);
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
	public EClass getTGGParameterValue() {
		return tggParameterValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGParameterValue_ParameterDefinition() {
		return (EReference) tggParameterValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGParameterValue_Expression() {
		return (EReference) tggParameterValueEClass.getEStructuralFeatures().get(1);
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
	public EReference getTGGAttributeConstraintDefinition_SyncBindings() {
		return (EReference) tggAttributeConstraintDefinitionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGAttributeConstraintDefinition_GenBindings() {
		return (EReference) tggAttributeConstraintDefinitionEClass.getEStructuralFeatures().get(3);
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

		tggRuleSetEClass = createEClass(TGG_RULE_SET);
		createEReference(tggRuleSetEClass, TGG_RULE_SET__RULES);

		tggRuleEClass = createEClass(TGG_RULE);
		createEReference(tggRuleEClass, TGG_RULE__REFINES);
		createEAttribute(tggRuleEClass, TGG_RULE__ABSTRACT);
		createEReference(tggRuleEClass, TGG_RULE__NODES);
		createEReference(tggRuleEClass, TGG_RULE__EDGES);
		createEReference(tggRuleEClass, TGG_RULE__CORRESPONDENCE_NODES);

		tggNodeEClass = createEClass(TGG_NODE);
		createEReference(tggNodeEClass, TGG_NODE__INCOMING_CORRESPONDENCE);
		createEReference(tggNodeEClass, TGG_NODE__OUTGOING_CORRESPONDENCE);

		tggRuleCorrespondenceEClass = createEClass(TGG_RULE_CORRESPONDENCE);
		createEReference(tggRuleCorrespondenceEClass, TGG_RULE_CORRESPONDENCE__SOURCE);
		createEReference(tggRuleCorrespondenceEClass, TGG_RULE_CORRESPONDENCE__TARGET);

		tggEdgeEClass = createEClass(TGG_EDGE);

		tggRuleElementEClass = createEClass(TGG_RULE_ELEMENT);
		createEAttribute(tggRuleElementEClass, TGG_RULE_ELEMENT__DOMAIN_TYPE);
		createEAttribute(tggRuleElementEClass, TGG_RULE_ELEMENT__BINDING_TYPE);

		tggAttributeConstraintLibraryEClass = createEClass(TGG_ATTRIBUTE_CONSTRAINT_LIBRARY);
		createEReference(tggAttributeConstraintLibraryEClass,
				TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__TGG_ATTRIBUTE_CONSTRAINTS);

		tggAttributeConstraintDefinitionLibraryEClass = createEClass(TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY);
		createEReference(tggAttributeConstraintDefinitionLibraryEClass,
				TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY__TGG_ATTRIBUTE_CONSTRAINT_DEFINITIONS);

		tggAttributeConstraintEClass = createEClass(TGG_ATTRIBUTE_CONSTRAINT);
		createEReference(tggAttributeConstraintEClass, TGG_ATTRIBUTE_CONSTRAINT__DEFINITION);
		createEReference(tggAttributeConstraintEClass, TGG_ATTRIBUTE_CONSTRAINT__PARAMETERS);

		tggParameterValueEClass = createEClass(TGG_PARAMETER_VALUE);
		createEReference(tggParameterValueEClass, TGG_PARAMETER_VALUE__PARAMETER_DEFINITION);
		createEReference(tggParameterValueEClass, TGG_PARAMETER_VALUE__EXPRESSION);

		tggAttributeConstraintParameterDefinitionEClass = createEClass(TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_DEFINITION);
		createEReference(tggAttributeConstraintParameterDefinitionEClass,
				TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_DEFINITION__TYPE);
		createEAttribute(tggAttributeConstraintParameterDefinitionEClass,
				TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_DEFINITION__NAME);

		tggAttributeConstraintBindingEClass = createEClass(TGG_ATTRIBUTE_CONSTRAINT_BINDING);
		createEAttribute(tggAttributeConstraintBindingEClass, TGG_ATTRIBUTE_CONSTRAINT_BINDING__VALUE);

		tggAttributeConstraintDefinitionEClass = createEClass(TGG_ATTRIBUTE_CONSTRAINT_DEFINITION);
		createEAttribute(tggAttributeConstraintDefinitionEClass, TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__USER_DEFINED);
		createEReference(tggAttributeConstraintDefinitionEClass,
				TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__PARAMETER_DEFINITIONS);
		createEReference(tggAttributeConstraintDefinitionEClass, TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__SYNC_BINDINGS);
		createEReference(tggAttributeConstraintDefinitionEClass, TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__GEN_BINDINGS);

		tggcspEClass = createEClass(TGGCSP);
		createEAttribute(tggcspEClass, TGGCSP__PACKAGE);
		createEReference(tggcspEClass, TGGCSP__VALUES);

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
		IBeXCoreModelPackage theIBeXCoreModelPackage = (IBeXCoreModelPackage) EPackage.Registry.INSTANCE
				.getEPackage(IBeXCoreModelPackage.eNS_URI);
		IBeXCoreArithmeticPackage theIBeXCoreArithmeticPackage = (IBeXCoreArithmeticPackage) EPackage.Registry.INSTANCE
				.getEPackage(IBeXCoreArithmeticPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		tggModelEClass.getESuperTypes().add(theIBeXCoreModelPackage.getIBeXModel());
		tggRuleEClass.getESuperTypes().add(theIBeXCoreModelPackage.getIBeXRule());
		tggNodeEClass.getESuperTypes().add(theIBeXCoreModelPackage.getIBeXNode());
		tggNodeEClass.getESuperTypes().add(this.getTGGRuleElement());
		tggRuleCorrespondenceEClass.getESuperTypes().add(this.getTGGNode());
		tggEdgeEClass.getESuperTypes().add(theIBeXCoreModelPackage.getIBeXEdge());
		tggEdgeEClass.getESuperTypes().add(this.getTGGRuleElement());
		tggAttributeConstraintDefinitionEClass.getESuperTypes().add(theIBeXCoreModelPackage.getIBeXNamedElement());
		tggcspEClass.getESuperTypes().add(theIBeXCoreModelPackage.getIBeXNamedElement());

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

		initEClass(tggRuleSetEClass, TGGRuleSet.class, "TGGRuleSet", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGRuleSet_Rules(), this.getTGGRule(), null, "rules", null, 0, -1, TGGRuleSet.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggRuleEClass, TGGRule.class, "TGGRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGRule_Refines(), this.getTGGRule(), null, "refines", null, 0, -1, TGGRule.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTGGRule_Abstract(), ecorePackage.getEBoolean(), "abstract", null, 0, 1, TGGRule.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRule_Nodes(), this.getTGGNode(), null, "nodes", null, 0, -1, TGGRule.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getTGGRule_Edges(), this.getTGGEdge(), null, "edges", null, 0, -1, TGGRule.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getTGGRule_CorrespondenceNodes(), this.getTGGRuleCorrespondence(), null, "correspondenceNodes",
				null, 0, -1, TGGRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggNodeEClass, TGGNode.class, "TGGNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGNode_IncomingCorrespondence(), this.getTGGRuleCorrespondence(),
				this.getTGGRuleCorrespondence_Target(), "incomingCorrespondence", null, 0, -1, TGGNode.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGNode_OutgoingCorrespondence(), this.getTGGRuleCorrespondence(),
				this.getTGGRuleCorrespondence_Source(), "outgoingCorrespondence", null, 0, -1, TGGNode.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggRuleCorrespondenceEClass, TGGRuleCorrespondence.class, "TGGRuleCorrespondence", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGRuleCorrespondence_Source(), this.getTGGNode(), this.getTGGNode_OutgoingCorrespondence(),
				"source", null, 1, 1, TGGRuleCorrespondence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRuleCorrespondence_Target(), this.getTGGNode(), this.getTGGNode_IncomingCorrespondence(),
				"target", null, 1, 1, TGGRuleCorrespondence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggEdgeEClass, TGGEdge.class, "TGGEdge", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(tggRuleElementEClass, TGGRuleElement.class, "TGGRuleElement", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTGGRuleElement_DomainType(), this.getDomainType(), "domainType", null, 0, 1,
				TGGRuleElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getTGGRuleElement_BindingType(), this.getBindingType(), "bindingType", null, 0, 1,
				TGGRuleElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(tggAttributeConstraintLibraryEClass, TGGAttributeConstraintLibrary.class,
				"TGGAttributeConstraintLibrary", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGAttributeConstraintLibrary_TggAttributeConstraints(), this.getTGGAttributeConstraint(),
				null, "tggAttributeConstraints", null, 0, -1, TGGAttributeConstraintLibrary.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(tggAttributeConstraintDefinitionLibraryEClass, TGGAttributeConstraintDefinitionLibrary.class,
				"TGGAttributeConstraintDefinitionLibrary", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGAttributeConstraintDefinitionLibrary_TggAttributeConstraintDefinitions(),
				this.getTGGAttributeConstraintDefinition(), null, "tggAttributeConstraintDefinitions", null, 0, -1,
				TGGAttributeConstraintDefinitionLibrary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggAttributeConstraintEClass, TGGAttributeConstraint.class, "TGGAttributeConstraint", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGAttributeConstraint_Definition(), this.getTGGAttributeConstraintDefinition(), null,
				"definition", null, 0, 1, TGGAttributeConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGAttributeConstraint_Parameters(), this.getTGGParameterValue(), null, "parameters", null, 0,
				-1, TGGAttributeConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggParameterValueEClass, TGGParameterValue.class, "TGGParameterValue", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGParameterValue_ParameterDefinition(), this.getTGGAttributeConstraintParameterDefinition(),
				null, "parameterDefinition", null, 0, 1, TGGParameterValue.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGParameterValue_Expression(), theIBeXCoreArithmeticPackage.getValueExpression(), null,
				"expression", null, 1, 1, TGGParameterValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

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

		initEClass(tggAttributeConstraintDefinitionEClass, TGGAttributeConstraintDefinition.class,
				"TGGAttributeConstraintDefinition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTGGAttributeConstraintDefinition_UserDefined(), ecorePackage.getEBoolean(), "userDefined",
				"true", 0, 1, TGGAttributeConstraintDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
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

		initEClass(tggcspEClass, org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGCSP.class, "TGGCSP", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTGGCSP_Package(), ecorePackage.getEString(), "package", null, 0, 1,
				org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGCSP.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGCSP_Values(), theIBeXCoreArithmeticPackage.getValueExpression(), null, "values", null, 0,
				-1, org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGCSP.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(domainTypeEEnum, DomainType.class, "DomainType");
		addEEnumLiteral(domainTypeEEnum, DomainType.SRC);
		addEEnumLiteral(domainTypeEEnum, DomainType.TRG);
		addEEnumLiteral(domainTypeEEnum, DomainType.CORR);

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
