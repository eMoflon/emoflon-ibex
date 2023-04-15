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
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGPattern;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleElement;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleSet;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGShortcutRule;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGShortcutRuleElementMapping;

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
	private EClass tggPatternEClass = null;

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
	private EClass tggShortcutRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggShortcutRuleElementMappingEClass = null;

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
	public EAttribute getTGGRule_Abstract() {
		return (EAttribute) tggRuleEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRule_Context() {
		return (EReference) tggRuleEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRule_ContextSource() {
		return (EReference) tggRuleEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRule_ContextCorrespondence() {
		return (EReference) tggRuleEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRule_ContextTarget() {
		return (EReference) tggRuleEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRule_CreateSourceAndTarget() {
		return (EReference) tggRuleEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRule_Create() {
		return (EReference) tggRuleEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRule_CreateSource() {
		return (EReference) tggRuleEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRule_CreateCorrespondence() {
		return (EReference) tggRuleEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRule_CreateTarget() {
		return (EReference) tggRuleEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTGGRule_Axiom() {
		return (EAttribute) tggRuleEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRule_Source() {
		return (EReference) tggRuleEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRule_Correspondence() {
		return (EReference) tggRuleEClass.getEStructuralFeatures().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRule_Target() {
		return (EReference) tggRuleEClass.getEStructuralFeatures().get(17);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRule_GenericContents() {
		return (EReference) tggRuleEClass.getEStructuralFeatures().get(18);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGRule_AttributeConstraints() {
		return (EReference) tggRuleEClass.getEStructuralFeatures().get(19);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTGGRule_NoGeneratedInjectivityConstraints() {
		return (EAttribute) tggRuleEClass.getEStructuralFeatures().get(20);
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
	public EReference getTGGOperationalRule_ToBeMarked() {
		return (EReference) tggOperationalRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGOperationalRule_AlreadyMarked() {
		return (EReference) tggOperationalRuleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGOperationalRule_TggRule() {
		return (EReference) tggOperationalRuleEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTGGPattern() {
		return tggPatternEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGPattern_AttributeConstraints() {
		return (EReference) tggPatternEClass.getEStructuralFeatures().get(0);
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
	public EReference getTGGNode_AttributeAssignments() {
		return (EReference) tggNodeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGNode_ReferencedByConditions() {
		return (EReference) tggNodeEClass.getEStructuralFeatures().get(3);
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
	public EClass getTGGShortcutRule() {
		return tggShortcutRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGShortcutRule_OriginalRule() {
		return (EReference) tggShortcutRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGShortcutRule_ReplacingRule() {
		return (EReference) tggShortcutRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGShortcutRule_Mappings() {
		return (EReference) tggShortcutRuleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGShortcutRule_UnmappedOriginalElements() {
		return (EReference) tggShortcutRuleEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGShortcutRule_UnmappedReplacingElements() {
		return (EReference) tggShortcutRuleEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTGGShortcutRuleElementMapping() {
		return tggShortcutRuleElementMappingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGShortcutRuleElementMapping_Original() {
		return (EReference) tggShortcutRuleElementMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTGGShortcutRuleElementMapping_Replacing() {
		return (EReference) tggShortcutRuleElementMappingEClass.getEStructuralFeatures().get(1);
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
		createEAttribute(tggRuleEClass, TGG_RULE__ABSTRACT);
		createEAttribute(tggRuleEClass, TGG_RULE__AXIOM);
		createEReference(tggRuleEClass, TGG_RULE__CONTEXT);
		createEReference(tggRuleEClass, TGG_RULE__CONTEXT_SOURCE);
		createEReference(tggRuleEClass, TGG_RULE__CONTEXT_CORRESPONDENCE);
		createEReference(tggRuleEClass, TGG_RULE__CONTEXT_TARGET);
		createEReference(tggRuleEClass, TGG_RULE__CREATE_SOURCE_AND_TARGET);
		createEReference(tggRuleEClass, TGG_RULE__CREATE);
		createEReference(tggRuleEClass, TGG_RULE__CREATE_SOURCE);
		createEReference(tggRuleEClass, TGG_RULE__CREATE_CORRESPONDENCE);
		createEReference(tggRuleEClass, TGG_RULE__CREATE_TARGET);
		createEReference(tggRuleEClass, TGG_RULE__SOURCE);
		createEReference(tggRuleEClass, TGG_RULE__CORRESPONDENCE);
		createEReference(tggRuleEClass, TGG_RULE__TARGET);
		createEReference(tggRuleEClass, TGG_RULE__GENERIC_CONTENTS);
		createEReference(tggRuleEClass, TGG_RULE__ATTRIBUTE_CONSTRAINTS);
		createEAttribute(tggRuleEClass, TGG_RULE__NO_GENERATED_INJECTIVITY_CONSTRAINTS);

		tggOperationalRuleEClass = createEClass(TGG_OPERATIONAL_RULE);
		createEAttribute(tggOperationalRuleEClass, TGG_OPERATIONAL_RULE__OPERATIONALISATION_MODE);
		createEReference(tggOperationalRuleEClass, TGG_OPERATIONAL_RULE__TO_BE_MARKED);
		createEReference(tggOperationalRuleEClass, TGG_OPERATIONAL_RULE__ALREADY_MARKED);
		createEReference(tggOperationalRuleEClass, TGG_OPERATIONAL_RULE__TGG_RULE);

		tggPatternEClass = createEClass(TGG_PATTERN);
		createEReference(tggPatternEClass, TGG_PATTERN__ATTRIBUTE_CONSTRAINTS);

		tggRuleElementEClass = createEClass(TGG_RULE_ELEMENT);
		createEAttribute(tggRuleElementEClass, TGG_RULE_ELEMENT__DOMAIN_TYPE);
		createEAttribute(tggRuleElementEClass, TGG_RULE_ELEMENT__BINDING_TYPE);

		tggNodeEClass = createEClass(TGG_NODE);
		createEReference(tggNodeEClass, TGG_NODE__INCOMING_CORRESPONDENCE);
		createEReference(tggNodeEClass, TGG_NODE__OUTGOING_CORRESPONDENCE);
		createEReference(tggNodeEClass, TGG_NODE__ATTRIBUTE_ASSIGNMENTS);
		createEReference(tggNodeEClass, TGG_NODE__REFERENCED_BY_CONDITIONS);

		tggCorrespondenceEClass = createEClass(TGG_CORRESPONDENCE);
		createEReference(tggCorrespondenceEClass, TGG_CORRESPONDENCE__SOURCE);
		createEReference(tggCorrespondenceEClass, TGG_CORRESPONDENCE__TARGET);

		tggEdgeEClass = createEClass(TGG_EDGE);

		tggShortcutRuleEClass = createEClass(TGG_SHORTCUT_RULE);
		createEReference(tggShortcutRuleEClass, TGG_SHORTCUT_RULE__ORIGINAL_RULE);
		createEReference(tggShortcutRuleEClass, TGG_SHORTCUT_RULE__REPLACING_RULE);
		createEReference(tggShortcutRuleEClass, TGG_SHORTCUT_RULE__MAPPINGS);
		createEReference(tggShortcutRuleEClass, TGG_SHORTCUT_RULE__UNMAPPED_ORIGINAL_ELEMENTS);
		createEReference(tggShortcutRuleEClass, TGG_SHORTCUT_RULE__UNMAPPED_REPLACING_ELEMENTS);

		tggShortcutRuleElementMappingEClass = createEClass(TGG_SHORTCUT_RULE_ELEMENT_MAPPING);
		createEReference(tggShortcutRuleElementMappingEClass, TGG_SHORTCUT_RULE_ELEMENT_MAPPING__ORIGINAL);
		createEReference(tggShortcutRuleElementMappingEClass, TGG_SHORTCUT_RULE_ELEMENT_MAPPING__REPLACING);

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
		IBeXCoreArithmeticPackage theIBeXCoreArithmeticPackage = (IBeXCoreArithmeticPackage) EPackage.Registry.INSTANCE
				.getEPackage(IBeXCoreArithmeticPackage.eNS_URI);

		// Add subpackages
		getESubpackages().add(theCSPPackage);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		tggModelEClass.getESuperTypes().add(theIBeXCoreModelPackage.getIBeXModel());
		tggRuleEClass.getESuperTypes().add(theIBeXCoreModelPackage.getIBeXRule());
		tggOperationalRuleEClass.getESuperTypes().add(this.getTGGRule());
		tggPatternEClass.getESuperTypes().add(theIBeXCoreModelPackage.getIBeXPattern());
		tggNodeEClass.getESuperTypes().add(theIBeXCoreModelPackage.getIBeXNode());
		tggNodeEClass.getESuperTypes().add(this.getTGGRuleElement());
		tggCorrespondenceEClass.getESuperTypes().add(this.getTGGNode());
		tggEdgeEClass.getESuperTypes().add(theIBeXCoreModelPackage.getIBeXEdge());
		tggEdgeEClass.getESuperTypes().add(this.getTGGRuleElement());
		tggShortcutRuleEClass.getESuperTypes().add(this.getTGGOperationalRule());

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
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getTGGRule_Edges(), this.getTGGEdge(), null, "edges", null, 0, -1, TGGRule.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getTGGRule_CorrespondenceNodes(), this.getTGGCorrespondence(), null, "correspondenceNodes", null,
				0, -1, TGGRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRule_Operationalisations(), this.getTGGOperationalRule(),
				this.getTGGOperationalRule_TggRule(), "operationalisations", null, 0, -1, TGGRule.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getTGGRule_Abstract(), ecorePackage.getEBoolean(), "abstract", null, 0, 1, TGGRule.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTGGRule_Axiom(), ecorePackage.getEBoolean(), "axiom", null, 0, 1, TGGRule.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRule_Context(), theIBeXCoreModelPackage.getIBeXRuleDelta(), null, "context", null, 0, 1,
				TGGRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRule_ContextSource(), theIBeXCoreModelPackage.getIBeXRuleDelta(), null, "contextSource",
				null, 0, 1, TGGRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRule_ContextCorrespondence(), theIBeXCoreModelPackage.getIBeXRuleDelta(), null,
				"contextCorrespondence", null, 0, 1, TGGRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRule_ContextTarget(), theIBeXCoreModelPackage.getIBeXRuleDelta(), null, "contextTarget",
				null, 0, 1, TGGRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRule_CreateSourceAndTarget(), theIBeXCoreModelPackage.getIBeXRuleDelta(), null,
				"createSourceAndTarget", null, 0, 1, TGGRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRule_Create(), theIBeXCoreModelPackage.getIBeXRuleDelta(), null, "create", null, 0, 1,
				TGGRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRule_CreateSource(), theIBeXCoreModelPackage.getIBeXRuleDelta(), null, "createSource",
				null, 0, 1, TGGRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRule_CreateCorrespondence(), theIBeXCoreModelPackage.getIBeXRuleDelta(), null,
				"createCorrespondence", null, 0, 1, TGGRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRule_CreateTarget(), theIBeXCoreModelPackage.getIBeXRuleDelta(), null, "createTarget",
				null, 0, 1, TGGRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRule_Source(), theIBeXCoreModelPackage.getIBeXRuleDelta(), null, "source", null, 0, 1,
				TGGRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRule_Correspondence(), theIBeXCoreModelPackage.getIBeXRuleDelta(), null, "correspondence",
				null, 0, 1, TGGRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRule_Target(), theIBeXCoreModelPackage.getIBeXRuleDelta(), null, "target", null, 0, 1,
				TGGRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRule_GenericContents(), ecorePackage.getEObject(), null, "genericContents", null, 0, -1,
				TGGRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRule_AttributeConstraints(), theCSPPackage.getTGGAttributeConstraintSet(), null,
				"attributeConstraints", null, 0, 1, TGGRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTGGRule_NoGeneratedInjectivityConstraints(), ecorePackage.getEBoolean(),
				"noGeneratedInjectivityConstraints", null, 0, 1, TGGRule.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggOperationalRuleEClass, TGGOperationalRule.class, "TGGOperationalRule", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTGGOperationalRule_OperationalisationMode(), this.getOperationalisationMode(),
				"operationalisationMode", null, 0, 1, TGGOperationalRule.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGOperationalRule_ToBeMarked(), theIBeXCoreModelPackage.getIBeXRuleDelta(), null,
				"toBeMarked", null, 0, 1, TGGOperationalRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGOperationalRule_AlreadyMarked(), theIBeXCoreModelPackage.getIBeXRuleDelta(), null,
				"alreadyMarked", null, 0, 1, TGGOperationalRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGOperationalRule_TggRule(), this.getTGGRule(), this.getTGGRule_Operationalisations(),
				"tggRule", null, 0, 1, TGGOperationalRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggPatternEClass, TGGPattern.class, "TGGPattern", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGPattern_AttributeConstraints(), theCSPPackage.getTGGAttributeConstraintSet(), null,
				"attributeConstraints", null, 0, 1, TGGPattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

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
		initEReference(getTGGNode_AttributeAssignments(), theIBeXCoreModelPackage.getIBeXAttributeAssignment(), null,
				"attributeAssignments", null, 0, -1, TGGNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGNode_ReferencedByConditions(), theIBeXCoreArithmeticPackage.getBooleanExpression(), null,
				"referencedByConditions", null, 0, -1, TGGNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggCorrespondenceEClass, TGGCorrespondence.class, "TGGCorrespondence", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGCorrespondence_Source(), this.getTGGNode(), this.getTGGNode_OutgoingCorrespondence(),
				"source", null, 1, 1, TGGCorrespondence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGCorrespondence_Target(), this.getTGGNode(), this.getTGGNode_IncomingCorrespondence(),
				"target", null, 1, 1, TGGCorrespondence.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggEdgeEClass, TGGEdge.class, "TGGEdge", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(tggShortcutRuleEClass, TGGShortcutRule.class, "TGGShortcutRule", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGShortcutRule_OriginalRule(), this.getTGGRule(), null, "originalRule", null, 0, 1,
				TGGShortcutRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGShortcutRule_ReplacingRule(), this.getTGGRule(), null, "replacingRule", null, 0, 1,
				TGGShortcutRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGShortcutRule_Mappings(), this.getTGGShortcutRuleElementMapping(), null, "mappings", null,
				0, -1, TGGShortcutRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGShortcutRule_UnmappedOriginalElements(), this.getTGGRuleElement(), null,
				"unmappedOriginalElements", null, 0, -1, TGGShortcutRule.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getTGGShortcutRule_UnmappedReplacingElements(), this.getTGGRuleElement(), null,
				"unmappedReplacingElements", null, 0, -1, TGGShortcutRule.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(tggShortcutRuleElementMappingEClass, TGGShortcutRuleElementMapping.class,
				"TGGShortcutRuleElementMapping", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGShortcutRuleElementMapping_Original(), this.getTGGRuleElement(), null, "original", null, 0,
				1, TGGShortcutRuleElementMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGShortcutRuleElementMapping_Replacing(), this.getTGGRuleElement(), null, "replacing", null,
				0, 1, TGGShortcutRuleElementMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(operationalisationModeEEnum, OperationalisationMode.class, "OperationalisationMode");
		addEEnumLiteral(operationalisationModeEEnum, OperationalisationMode.GENERATE);
		addEEnumLiteral(operationalisationModeEEnum, OperationalisationMode.FORWARD);
		addEEnumLiteral(operationalisationModeEEnum, OperationalisationMode.BACKWARD);
		addEEnumLiteral(operationalisationModeEEnum, OperationalisationMode.CONSISTENCY_CHECK);
		addEEnumLiteral(operationalisationModeEEnum, OperationalisationMode.CHECK_ONLY);
		addEEnumLiteral(operationalisationModeEEnum, OperationalisationMode.SOURCE);
		addEEnumLiteral(operationalisationModeEEnum, OperationalisationMode.TARGET);
		addEEnumLiteral(operationalisationModeEEnum, OperationalisationMode.CONSISTENCY);

		initEEnum(domainTypeEEnum, DomainType.class, "DomainType");
		addEEnumLiteral(domainTypeEEnum, DomainType.SOURCE);
		addEEnumLiteral(domainTypeEEnum, DomainType.TARGET);
		addEEnumLiteral(domainTypeEEnum, DomainType.CORRESPONDENCE);
		addEEnumLiteral(domainTypeEEnum, DomainType.PROTOCOL);
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
