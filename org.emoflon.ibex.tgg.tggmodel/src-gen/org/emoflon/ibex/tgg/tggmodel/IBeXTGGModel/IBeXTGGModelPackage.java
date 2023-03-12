/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelFactory
 * @model kind="package"
 * @generated
 */
public interface IBeXTGGModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "IBeXTGGModel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "platform:/resource/org.emoflon.ibex.tgg.tggmodel/model/IBeXTGGModel.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "IBeXTGGModel";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	IBeXTGGModelPackage eINSTANCE = org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGModelImpl <em>TGG Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGModelImpl
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getTGGModel()
	 * @generated
	 */
	int TGG_MODEL = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_MODEL__NAME = IBeXCoreModelPackage.IBE_XMODEL__NAME;

	/**
	 * The feature id for the '<em><b>Meta Data</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_MODEL__META_DATA = IBeXCoreModelPackage.IBE_XMODEL__META_DATA;

	/**
	 * The feature id for the '<em><b>Feature Config</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_MODEL__FEATURE_CONFIG = IBeXCoreModelPackage.IBE_XMODEL__FEATURE_CONFIG;

	/**
	 * The feature id for the '<em><b>Pattern Set</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_MODEL__PATTERN_SET = IBeXCoreModelPackage.IBE_XMODEL__PATTERN_SET;

	/**
	 * The feature id for the '<em><b>Node Set</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_MODEL__NODE_SET = IBeXCoreModelPackage.IBE_XMODEL__NODE_SET;

	/**
	 * The feature id for the '<em><b>Edge Set</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_MODEL__EDGE_SET = IBeXCoreModelPackage.IBE_XMODEL__EDGE_SET;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_MODEL__SOURCE = IBeXCoreModelPackage.IBE_XMODEL_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_MODEL__TARGET = IBeXCoreModelPackage.IBE_XMODEL_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Correspondence</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_MODEL__CORRESPONDENCE = IBeXCoreModelPackage.IBE_XMODEL_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Rule Set</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_MODEL__RULE_SET = IBeXCoreModelPackage.IBE_XMODEL_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Attribute Constraint Definition Libraries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_MODEL__ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARIES = IBeXCoreModelPackage.IBE_XMODEL_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>TGG Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_MODEL_FEATURE_COUNT = IBeXCoreModelPackage.IBE_XMODEL_FEATURE_COUNT + 5;

	/**
	 * The number of operations of the '<em>TGG Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_MODEL_OPERATION_COUNT = IBeXCoreModelPackage.IBE_XMODEL_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleSetImpl <em>TGG Rule Set</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleSetImpl
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getTGGRuleSet()
	 * @generated
	 */
	int TGG_RULE_SET = 1;

	/**
	 * The feature id for the '<em><b>Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_SET__RULES = 0;

	/**
	 * The number of structural features of the '<em>TGG Rule Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_SET_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>TGG Rule Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_SET_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleImpl <em>TGG Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleImpl
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getTGGRule()
	 * @generated
	 */
	int TGG_RULE = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__NAME = IBeXCoreModelPackage.IBE_XRULE__NAME;

	/**
	 * The feature id for the '<em><b>Precondition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__PRECONDITION = IBeXCoreModelPackage.IBE_XRULE__PRECONDITION;

	/**
	 * The feature id for the '<em><b>Postcondition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__POSTCONDITION = IBeXCoreModelPackage.IBE_XRULE__POSTCONDITION;

	/**
	 * The feature id for the '<em><b>Creation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__CREATION = IBeXCoreModelPackage.IBE_XRULE__CREATION;

	/**
	 * The feature id for the '<em><b>Deletion</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__DELETION = IBeXCoreModelPackage.IBE_XRULE__DELETION;

	/**
	 * The feature id for the '<em><b>Attribute Assignments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__ATTRIBUTE_ASSIGNMENTS = IBeXCoreModelPackage.IBE_XRULE__ATTRIBUTE_ASSIGNMENTS;

	/**
	 * The feature id for the '<em><b>All Nodes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__ALL_NODES = IBeXCoreModelPackage.IBE_XRULE__ALL_NODES;

	/**
	 * The feature id for the '<em><b>All Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__ALL_EDGES = IBeXCoreModelPackage.IBE_XRULE__ALL_EDGES;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__NODES = IBeXCoreModelPackage.IBE_XRULE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Edges</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__EDGES = IBeXCoreModelPackage.IBE_XRULE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Correspondence Nodes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__CORRESPONDENCE_NODES = IBeXCoreModelPackage.IBE_XRULE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Operationalisations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__OPERATIONALISATIONS = IBeXCoreModelPackage.IBE_XRULE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Attribute Constraints</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__ATTRIBUTE_CONSTRAINTS = IBeXCoreModelPackage.IBE_XRULE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Abstract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__ABSTRACT = IBeXCoreModelPackage.IBE_XRULE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Axiom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__AXIOM = IBeXCoreModelPackage.IBE_XRULE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Context</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__CONTEXT = IBeXCoreModelPackage.IBE_XRULE_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Context Source</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__CONTEXT_SOURCE = IBeXCoreModelPackage.IBE_XRULE_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Context Correspondence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__CONTEXT_CORRESPONDENCE = IBeXCoreModelPackage.IBE_XRULE_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Context Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__CONTEXT_TARGET = IBeXCoreModelPackage.IBE_XRULE_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Create Source And Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__CREATE_SOURCE_AND_TARGET = IBeXCoreModelPackage.IBE_XRULE_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Create</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__CREATE = IBeXCoreModelPackage.IBE_XRULE_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>Create Source</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__CREATE_SOURCE = IBeXCoreModelPackage.IBE_XRULE_FEATURE_COUNT + 13;

	/**
	 * The feature id for the '<em><b>Create Correspondence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__CREATE_CORRESPONDENCE = IBeXCoreModelPackage.IBE_XRULE_FEATURE_COUNT + 14;

	/**
	 * The feature id for the '<em><b>Create Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__CREATE_TARGET = IBeXCoreModelPackage.IBE_XRULE_FEATURE_COUNT + 15;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__SOURCE = IBeXCoreModelPackage.IBE_XRULE_FEATURE_COUNT + 16;

	/**
	 * The feature id for the '<em><b>Correspondence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__CORRESPONDENCE = IBeXCoreModelPackage.IBE_XRULE_FEATURE_COUNT + 17;

	/**
	 * The feature id for the '<em><b>Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__TARGET = IBeXCoreModelPackage.IBE_XRULE_FEATURE_COUNT + 18;

	/**
	 * The number of structural features of the '<em>TGG Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_FEATURE_COUNT = IBeXCoreModelPackage.IBE_XRULE_FEATURE_COUNT + 19;

	/**
	 * The number of operations of the '<em>TGG Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_OPERATION_COUNT = IBeXCoreModelPackage.IBE_XRULE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGOperationalRuleImpl <em>TGG Operational Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGOperationalRuleImpl
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getTGGOperationalRule()
	 * @generated
	 */
	int TGG_OPERATIONAL_RULE = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_OPERATIONAL_RULE__NAME = TGG_RULE__NAME;

	/**
	 * The feature id for the '<em><b>Precondition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_OPERATIONAL_RULE__PRECONDITION = TGG_RULE__PRECONDITION;

	/**
	 * The feature id for the '<em><b>Postcondition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_OPERATIONAL_RULE__POSTCONDITION = TGG_RULE__POSTCONDITION;

	/**
	 * The feature id for the '<em><b>Creation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_OPERATIONAL_RULE__CREATION = TGG_RULE__CREATION;

	/**
	 * The feature id for the '<em><b>Deletion</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_OPERATIONAL_RULE__DELETION = TGG_RULE__DELETION;

	/**
	 * The feature id for the '<em><b>Attribute Assignments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_OPERATIONAL_RULE__ATTRIBUTE_ASSIGNMENTS = TGG_RULE__ATTRIBUTE_ASSIGNMENTS;

	/**
	 * The feature id for the '<em><b>All Nodes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_OPERATIONAL_RULE__ALL_NODES = TGG_RULE__ALL_NODES;

	/**
	 * The feature id for the '<em><b>All Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_OPERATIONAL_RULE__ALL_EDGES = TGG_RULE__ALL_EDGES;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_OPERATIONAL_RULE__NODES = TGG_RULE__NODES;

	/**
	 * The feature id for the '<em><b>Edges</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_OPERATIONAL_RULE__EDGES = TGG_RULE__EDGES;

	/**
	 * The feature id for the '<em><b>Correspondence Nodes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_OPERATIONAL_RULE__CORRESPONDENCE_NODES = TGG_RULE__CORRESPONDENCE_NODES;

	/**
	 * The feature id for the '<em><b>Operationalisations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_OPERATIONAL_RULE__OPERATIONALISATIONS = TGG_RULE__OPERATIONALISATIONS;

	/**
	 * The feature id for the '<em><b>Attribute Constraints</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_OPERATIONAL_RULE__ATTRIBUTE_CONSTRAINTS = TGG_RULE__ATTRIBUTE_CONSTRAINTS;

	/**
	 * The feature id for the '<em><b>Abstract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_OPERATIONAL_RULE__ABSTRACT = TGG_RULE__ABSTRACT;

	/**
	 * The feature id for the '<em><b>Axiom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_OPERATIONAL_RULE__AXIOM = TGG_RULE__AXIOM;

	/**
	 * The feature id for the '<em><b>Context</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_OPERATIONAL_RULE__CONTEXT = TGG_RULE__CONTEXT;

	/**
	 * The feature id for the '<em><b>Context Source</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_OPERATIONAL_RULE__CONTEXT_SOURCE = TGG_RULE__CONTEXT_SOURCE;

	/**
	 * The feature id for the '<em><b>Context Correspondence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_OPERATIONAL_RULE__CONTEXT_CORRESPONDENCE = TGG_RULE__CONTEXT_CORRESPONDENCE;

	/**
	 * The feature id for the '<em><b>Context Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_OPERATIONAL_RULE__CONTEXT_TARGET = TGG_RULE__CONTEXT_TARGET;

	/**
	 * The feature id for the '<em><b>Create Source And Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_OPERATIONAL_RULE__CREATE_SOURCE_AND_TARGET = TGG_RULE__CREATE_SOURCE_AND_TARGET;

	/**
	 * The feature id for the '<em><b>Create</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_OPERATIONAL_RULE__CREATE = TGG_RULE__CREATE;

	/**
	 * The feature id for the '<em><b>Create Source</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_OPERATIONAL_RULE__CREATE_SOURCE = TGG_RULE__CREATE_SOURCE;

	/**
	 * The feature id for the '<em><b>Create Correspondence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_OPERATIONAL_RULE__CREATE_CORRESPONDENCE = TGG_RULE__CREATE_CORRESPONDENCE;

	/**
	 * The feature id for the '<em><b>Create Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_OPERATIONAL_RULE__CREATE_TARGET = TGG_RULE__CREATE_TARGET;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_OPERATIONAL_RULE__SOURCE = TGG_RULE__SOURCE;

	/**
	 * The feature id for the '<em><b>Correspondence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_OPERATIONAL_RULE__CORRESPONDENCE = TGG_RULE__CORRESPONDENCE;

	/**
	 * The feature id for the '<em><b>Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_OPERATIONAL_RULE__TARGET = TGG_RULE__TARGET;

	/**
	 * The feature id for the '<em><b>Operationalisation Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_OPERATIONAL_RULE__OPERATIONALISATION_MODE = TGG_RULE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>To Be Marked</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_OPERATIONAL_RULE__TO_BE_MARKED = TGG_RULE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Already Marked</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_OPERATIONAL_RULE__ALREADY_MARKED = TGG_RULE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>TGG Operational Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_OPERATIONAL_RULE_FEATURE_COUNT = TGG_RULE_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>TGG Operational Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_OPERATIONAL_RULE_OPERATION_COUNT = TGG_RULE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleElementImpl <em>TGG Rule Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleElementImpl
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getTGGRuleElement()
	 * @generated
	 */
	int TGG_RULE_ELEMENT = 4;

	/**
	 * The feature id for the '<em><b>Domain Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_ELEMENT__DOMAIN_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Binding Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_ELEMENT__BINDING_TYPE = 1;

	/**
	 * The number of structural features of the '<em>TGG Rule Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_ELEMENT_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>TGG Rule Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGNodeImpl <em>TGG Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGNodeImpl
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getTGGNode()
	 * @generated
	 */
	int TGG_NODE = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_NODE__NAME = IBeXCoreModelPackage.IBE_XNODE__NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_NODE__TYPE = IBeXCoreModelPackage.IBE_XNODE__TYPE;

	/**
	 * The feature id for the '<em><b>Operation Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_NODE__OPERATION_TYPE = IBeXCoreModelPackage.IBE_XNODE__OPERATION_TYPE;

	/**
	 * The feature id for the '<em><b>Incoming Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_NODE__INCOMING_EDGES = IBeXCoreModelPackage.IBE_XNODE__INCOMING_EDGES;

	/**
	 * The feature id for the '<em><b>Outgoing Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_NODE__OUTGOING_EDGES = IBeXCoreModelPackage.IBE_XNODE__OUTGOING_EDGES;

	/**
	 * The feature id for the '<em><b>Domain Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_NODE__DOMAIN_TYPE = IBeXCoreModelPackage.IBE_XNODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Binding Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_NODE__BINDING_TYPE = IBeXCoreModelPackage.IBE_XNODE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Incoming Correspondence</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_NODE__INCOMING_CORRESPONDENCE = IBeXCoreModelPackage.IBE_XNODE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Outgoing Correspondence</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_NODE__OUTGOING_CORRESPONDENCE = IBeXCoreModelPackage.IBE_XNODE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Attribute Assignments</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_NODE__ATTRIBUTE_ASSIGNMENTS = IBeXCoreModelPackage.IBE_XNODE_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>TGG Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_NODE_FEATURE_COUNT = IBeXCoreModelPackage.IBE_XNODE_FEATURE_COUNT + 5;

	/**
	 * The number of operations of the '<em>TGG Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_NODE_OPERATION_COUNT = IBeXCoreModelPackage.IBE_XNODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGCorrespondenceImpl <em>TGG Correspondence</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGCorrespondenceImpl
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getTGGCorrespondence()
	 * @generated
	 */
	int TGG_CORRESPONDENCE = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_CORRESPONDENCE__NAME = TGG_NODE__NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_CORRESPONDENCE__TYPE = TGG_NODE__TYPE;

	/**
	 * The feature id for the '<em><b>Operation Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_CORRESPONDENCE__OPERATION_TYPE = TGG_NODE__OPERATION_TYPE;

	/**
	 * The feature id for the '<em><b>Incoming Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_CORRESPONDENCE__INCOMING_EDGES = TGG_NODE__INCOMING_EDGES;

	/**
	 * The feature id for the '<em><b>Outgoing Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_CORRESPONDENCE__OUTGOING_EDGES = TGG_NODE__OUTGOING_EDGES;

	/**
	 * The feature id for the '<em><b>Domain Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_CORRESPONDENCE__DOMAIN_TYPE = TGG_NODE__DOMAIN_TYPE;

	/**
	 * The feature id for the '<em><b>Binding Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_CORRESPONDENCE__BINDING_TYPE = TGG_NODE__BINDING_TYPE;

	/**
	 * The feature id for the '<em><b>Incoming Correspondence</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_CORRESPONDENCE__INCOMING_CORRESPONDENCE = TGG_NODE__INCOMING_CORRESPONDENCE;

	/**
	 * The feature id for the '<em><b>Outgoing Correspondence</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_CORRESPONDENCE__OUTGOING_CORRESPONDENCE = TGG_NODE__OUTGOING_CORRESPONDENCE;

	/**
	 * The feature id for the '<em><b>Attribute Assignments</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_CORRESPONDENCE__ATTRIBUTE_ASSIGNMENTS = TGG_NODE__ATTRIBUTE_ASSIGNMENTS;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_CORRESPONDENCE__SOURCE = TGG_NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_CORRESPONDENCE__TARGET = TGG_NODE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>TGG Correspondence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_CORRESPONDENCE_FEATURE_COUNT = TGG_NODE_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>TGG Correspondence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_CORRESPONDENCE_OPERATION_COUNT = TGG_NODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGEdgeImpl <em>TGG Edge</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGEdgeImpl
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getTGGEdge()
	 * @generated
	 */
	int TGG_EDGE = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_EDGE__NAME = IBeXCoreModelPackage.IBE_XEDGE__NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_EDGE__TYPE = IBeXCoreModelPackage.IBE_XEDGE__TYPE;

	/**
	 * The feature id for the '<em><b>Operation Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_EDGE__OPERATION_TYPE = IBeXCoreModelPackage.IBE_XEDGE__OPERATION_TYPE;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_EDGE__SOURCE = IBeXCoreModelPackage.IBE_XEDGE__SOURCE;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_EDGE__TARGET = IBeXCoreModelPackage.IBE_XEDGE__TARGET;

	/**
	 * The feature id for the '<em><b>Domain Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_EDGE__DOMAIN_TYPE = IBeXCoreModelPackage.IBE_XEDGE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Binding Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_EDGE__BINDING_TYPE = IBeXCoreModelPackage.IBE_XEDGE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>TGG Edge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_EDGE_FEATURE_COUNT = IBeXCoreModelPackage.IBE_XEDGE_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>TGG Edge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_EDGE_OPERATION_COUNT = IBeXCoreModelPackage.IBE_XEDGE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.OperationalisationMode <em>Operationalisation Mode</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.OperationalisationMode
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getOperationalisationMode()
	 * @generated
	 */
	int OPERATIONALISATION_MODE = 8;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType <em>Domain Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getDomainType()
	 * @generated
	 */
	int DOMAIN_TYPE = 9;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType <em>Binding Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getBindingType()
	 * @generated
	 */
	int BINDING_TYPE = 10;

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel <em>TGG Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Model</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel
	 * @generated
	 */
	EClass getTGGModel();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Source</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel#getSource()
	 * @see #getTGGModel()
	 * @generated
	 */
	EReference getTGGModel_Source();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Target</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel#getTarget()
	 * @see #getTGGModel()
	 * @generated
	 */
	EReference getTGGModel_Target();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel#getCorrespondence <em>Correspondence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Correspondence</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel#getCorrespondence()
	 * @see #getTGGModel()
	 * @generated
	 */
	EReference getTGGModel_Correspondence();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel#getRuleSet <em>Rule Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rule Set</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel#getRuleSet()
	 * @see #getTGGModel()
	 * @generated
	 */
	EReference getTGGModel_RuleSet();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel#getAttributeConstraintDefinitionLibraries <em>Attribute Constraint Definition Libraries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attribute Constraint Definition Libraries</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel#getAttributeConstraintDefinitionLibraries()
	 * @see #getTGGModel()
	 * @generated
	 */
	EReference getTGGModel_AttributeConstraintDefinitionLibraries();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleSet <em>TGG Rule Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Rule Set</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleSet
	 * @generated
	 */
	EClass getTGGRuleSet();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleSet#getRules <em>Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Rules</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleSet#getRules()
	 * @see #getTGGRuleSet()
	 * @generated
	 */
	EReference getTGGRuleSet_Rules();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule <em>TGG Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Rule</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule
	 * @generated
	 */
	EClass getTGGRule();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getNodes <em>Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Nodes</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getNodes()
	 * @see #getTGGRule()
	 * @generated
	 */
	EReference getTGGRule_Nodes();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getEdges <em>Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Edges</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getEdges()
	 * @see #getTGGRule()
	 * @generated
	 */
	EReference getTGGRule_Edges();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getCorrespondenceNodes <em>Correspondence Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Correspondence Nodes</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getCorrespondenceNodes()
	 * @see #getTGGRule()
	 * @generated
	 */
	EReference getTGGRule_CorrespondenceNodes();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getOperationalisations <em>Operationalisations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Operationalisations</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getOperationalisations()
	 * @see #getTGGRule()
	 * @generated
	 */
	EReference getTGGRule_Operationalisations();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getAttributeConstraints <em>Attribute Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Attribute Constraints</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getAttributeConstraints()
	 * @see #getTGGRule()
	 * @generated
	 */
	EReference getTGGRule_AttributeConstraints();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#isAbstract <em>Abstract</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Abstract</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#isAbstract()
	 * @see #getTGGRule()
	 * @generated
	 */
	EAttribute getTGGRule_Abstract();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getContext <em>Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Context</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getContext()
	 * @see #getTGGRule()
	 * @generated
	 */
	EReference getTGGRule_Context();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getContextSource <em>Context Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Context Source</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getContextSource()
	 * @see #getTGGRule()
	 * @generated
	 */
	EReference getTGGRule_ContextSource();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getContextCorrespondence <em>Context Correspondence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Context Correspondence</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getContextCorrespondence()
	 * @see #getTGGRule()
	 * @generated
	 */
	EReference getTGGRule_ContextCorrespondence();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getContextTarget <em>Context Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Context Target</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getContextTarget()
	 * @see #getTGGRule()
	 * @generated
	 */
	EReference getTGGRule_ContextTarget();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getCreateSourceAndTarget <em>Create Source And Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Create Source And Target</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getCreateSourceAndTarget()
	 * @see #getTGGRule()
	 * @generated
	 */
	EReference getTGGRule_CreateSourceAndTarget();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getCreate <em>Create</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Create</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getCreate()
	 * @see #getTGGRule()
	 * @generated
	 */
	EReference getTGGRule_Create();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getCreateSource <em>Create Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Create Source</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getCreateSource()
	 * @see #getTGGRule()
	 * @generated
	 */
	EReference getTGGRule_CreateSource();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getCreateCorrespondence <em>Create Correspondence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Create Correspondence</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getCreateCorrespondence()
	 * @see #getTGGRule()
	 * @generated
	 */
	EReference getTGGRule_CreateCorrespondence();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getCreateTarget <em>Create Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Create Target</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getCreateTarget()
	 * @see #getTGGRule()
	 * @generated
	 */
	EReference getTGGRule_CreateTarget();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#isAxiom <em>Axiom</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Axiom</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#isAxiom()
	 * @see #getTGGRule()
	 * @generated
	 */
	EAttribute getTGGRule_Axiom();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Source</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getSource()
	 * @see #getTGGRule()
	 * @generated
	 */
	EReference getTGGRule_Source();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getCorrespondence <em>Correspondence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Correspondence</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getCorrespondence()
	 * @see #getTGGRule()
	 * @generated
	 */
	EReference getTGGRule_Correspondence();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Target</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getTarget()
	 * @see #getTGGRule()
	 * @generated
	 */
	EReference getTGGRule_Target();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule <em>TGG Operational Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Operational Rule</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule
	 * @generated
	 */
	EClass getTGGOperationalRule();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule#getOperationalisationMode <em>Operationalisation Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operationalisation Mode</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule#getOperationalisationMode()
	 * @see #getTGGOperationalRule()
	 * @generated
	 */
	EAttribute getTGGOperationalRule_OperationalisationMode();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule#getToBeMarked <em>To Be Marked</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>To Be Marked</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule#getToBeMarked()
	 * @see #getTGGOperationalRule()
	 * @generated
	 */
	EReference getTGGOperationalRule_ToBeMarked();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule#getAlreadyMarked <em>Already Marked</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Already Marked</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule#getAlreadyMarked()
	 * @see #getTGGOperationalRule()
	 * @generated
	 */
	EReference getTGGOperationalRule_AlreadyMarked();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleElement <em>TGG Rule Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Rule Element</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleElement
	 * @generated
	 */
	EClass getTGGRuleElement();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleElement#getDomainType <em>Domain Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Domain Type</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleElement#getDomainType()
	 * @see #getTGGRuleElement()
	 * @generated
	 */
	EAttribute getTGGRuleElement_DomainType();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleElement#getBindingType <em>Binding Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Binding Type</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleElement#getBindingType()
	 * @see #getTGGRuleElement()
	 * @generated
	 */
	EAttribute getTGGRuleElement_BindingType();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode <em>TGG Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Node</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode
	 * @generated
	 */
	EClass getTGGNode();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode#getIncomingCorrespondence <em>Incoming Correspondence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Incoming Correspondence</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode#getIncomingCorrespondence()
	 * @see #getTGGNode()
	 * @generated
	 */
	EReference getTGGNode_IncomingCorrespondence();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode#getOutgoingCorrespondence <em>Outgoing Correspondence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Outgoing Correspondence</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode#getOutgoingCorrespondence()
	 * @see #getTGGNode()
	 * @generated
	 */
	EReference getTGGNode_OutgoingCorrespondence();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode#getAttributeAssignments <em>Attribute Assignments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Attribute Assignments</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode#getAttributeAssignments()
	 * @see #getTGGNode()
	 * @generated
	 */
	EReference getTGGNode_AttributeAssignments();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGCorrespondence <em>TGG Correspondence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Correspondence</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGCorrespondence
	 * @generated
	 */
	EClass getTGGCorrespondence();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGCorrespondence#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGCorrespondence#getSource()
	 * @see #getTGGCorrespondence()
	 * @generated
	 */
	EReference getTGGCorrespondence_Source();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGCorrespondence#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGCorrespondence#getTarget()
	 * @see #getTGGCorrespondence()
	 * @generated
	 */
	EReference getTGGCorrespondence_Target();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGEdge <em>TGG Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Edge</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGEdge
	 * @generated
	 */
	EClass getTGGEdge();

	/**
	 * Returns the meta object for enum '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.OperationalisationMode <em>Operationalisation Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Operationalisation Mode</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.OperationalisationMode
	 * @generated
	 */
	EEnum getOperationalisationMode();

	/**
	 * Returns the meta object for enum '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType <em>Domain Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Domain Type</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType
	 * @generated
	 */
	EEnum getDomainType();

	/**
	 * Returns the meta object for enum '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType <em>Binding Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Binding Type</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType
	 * @generated
	 */
	EEnum getBindingType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	IBeXTGGModelFactory getIBeXTGGModelFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGModelImpl <em>TGG Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGModelImpl
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getTGGModel()
		 * @generated
		 */
		EClass TGG_MODEL = eINSTANCE.getTGGModel();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_MODEL__SOURCE = eINSTANCE.getTGGModel_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_MODEL__TARGET = eINSTANCE.getTGGModel_Target();

		/**
		 * The meta object literal for the '<em><b>Correspondence</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_MODEL__CORRESPONDENCE = eINSTANCE.getTGGModel_Correspondence();

		/**
		 * The meta object literal for the '<em><b>Rule Set</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_MODEL__RULE_SET = eINSTANCE.getTGGModel_RuleSet();

		/**
		 * The meta object literal for the '<em><b>Attribute Constraint Definition Libraries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_MODEL__ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARIES = eINSTANCE
				.getTGGModel_AttributeConstraintDefinitionLibraries();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleSetImpl <em>TGG Rule Set</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleSetImpl
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getTGGRuleSet()
		 * @generated
		 */
		EClass TGG_RULE_SET = eINSTANCE.getTGGRuleSet();

		/**
		 * The meta object literal for the '<em><b>Rules</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE_SET__RULES = eINSTANCE.getTGGRuleSet_Rules();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleImpl <em>TGG Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleImpl
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getTGGRule()
		 * @generated
		 */
		EClass TGG_RULE = eINSTANCE.getTGGRule();

		/**
		 * The meta object literal for the '<em><b>Nodes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE__NODES = eINSTANCE.getTGGRule_Nodes();

		/**
		 * The meta object literal for the '<em><b>Edges</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE__EDGES = eINSTANCE.getTGGRule_Edges();

		/**
		 * The meta object literal for the '<em><b>Correspondence Nodes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE__CORRESPONDENCE_NODES = eINSTANCE.getTGGRule_CorrespondenceNodes();

		/**
		 * The meta object literal for the '<em><b>Operationalisations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE__OPERATIONALISATIONS = eINSTANCE.getTGGRule_Operationalisations();

		/**
		 * The meta object literal for the '<em><b>Attribute Constraints</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE__ATTRIBUTE_CONSTRAINTS = eINSTANCE.getTGGRule_AttributeConstraints();

		/**
		 * The meta object literal for the '<em><b>Abstract</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TGG_RULE__ABSTRACT = eINSTANCE.getTGGRule_Abstract();

		/**
		 * The meta object literal for the '<em><b>Context</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE__CONTEXT = eINSTANCE.getTGGRule_Context();

		/**
		 * The meta object literal for the '<em><b>Context Source</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE__CONTEXT_SOURCE = eINSTANCE.getTGGRule_ContextSource();

		/**
		 * The meta object literal for the '<em><b>Context Correspondence</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE__CONTEXT_CORRESPONDENCE = eINSTANCE.getTGGRule_ContextCorrespondence();

		/**
		 * The meta object literal for the '<em><b>Context Target</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE__CONTEXT_TARGET = eINSTANCE.getTGGRule_ContextTarget();

		/**
		 * The meta object literal for the '<em><b>Create Source And Target</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE__CREATE_SOURCE_AND_TARGET = eINSTANCE.getTGGRule_CreateSourceAndTarget();

		/**
		 * The meta object literal for the '<em><b>Create</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE__CREATE = eINSTANCE.getTGGRule_Create();

		/**
		 * The meta object literal for the '<em><b>Create Source</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE__CREATE_SOURCE = eINSTANCE.getTGGRule_CreateSource();

		/**
		 * The meta object literal for the '<em><b>Create Correspondence</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE__CREATE_CORRESPONDENCE = eINSTANCE.getTGGRule_CreateCorrespondence();

		/**
		 * The meta object literal for the '<em><b>Create Target</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE__CREATE_TARGET = eINSTANCE.getTGGRule_CreateTarget();

		/**
		 * The meta object literal for the '<em><b>Axiom</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TGG_RULE__AXIOM = eINSTANCE.getTGGRule_Axiom();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE__SOURCE = eINSTANCE.getTGGRule_Source();

		/**
		 * The meta object literal for the '<em><b>Correspondence</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE__CORRESPONDENCE = eINSTANCE.getTGGRule_Correspondence();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE__TARGET = eINSTANCE.getTGGRule_Target();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGOperationalRuleImpl <em>TGG Operational Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGOperationalRuleImpl
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getTGGOperationalRule()
		 * @generated
		 */
		EClass TGG_OPERATIONAL_RULE = eINSTANCE.getTGGOperationalRule();

		/**
		 * The meta object literal for the '<em><b>Operationalisation Mode</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TGG_OPERATIONAL_RULE__OPERATIONALISATION_MODE = eINSTANCE
				.getTGGOperationalRule_OperationalisationMode();

		/**
		 * The meta object literal for the '<em><b>To Be Marked</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_OPERATIONAL_RULE__TO_BE_MARKED = eINSTANCE.getTGGOperationalRule_ToBeMarked();

		/**
		 * The meta object literal for the '<em><b>Already Marked</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_OPERATIONAL_RULE__ALREADY_MARKED = eINSTANCE.getTGGOperationalRule_AlreadyMarked();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleElementImpl <em>TGG Rule Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleElementImpl
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getTGGRuleElement()
		 * @generated
		 */
		EClass TGG_RULE_ELEMENT = eINSTANCE.getTGGRuleElement();

		/**
		 * The meta object literal for the '<em><b>Domain Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TGG_RULE_ELEMENT__DOMAIN_TYPE = eINSTANCE.getTGGRuleElement_DomainType();

		/**
		 * The meta object literal for the '<em><b>Binding Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TGG_RULE_ELEMENT__BINDING_TYPE = eINSTANCE.getTGGRuleElement_BindingType();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGNodeImpl <em>TGG Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGNodeImpl
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getTGGNode()
		 * @generated
		 */
		EClass TGG_NODE = eINSTANCE.getTGGNode();

		/**
		 * The meta object literal for the '<em><b>Incoming Correspondence</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_NODE__INCOMING_CORRESPONDENCE = eINSTANCE.getTGGNode_IncomingCorrespondence();

		/**
		 * The meta object literal for the '<em><b>Outgoing Correspondence</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_NODE__OUTGOING_CORRESPONDENCE = eINSTANCE.getTGGNode_OutgoingCorrespondence();

		/**
		 * The meta object literal for the '<em><b>Attribute Assignments</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_NODE__ATTRIBUTE_ASSIGNMENTS = eINSTANCE.getTGGNode_AttributeAssignments();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGCorrespondenceImpl <em>TGG Correspondence</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGCorrespondenceImpl
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getTGGCorrespondence()
		 * @generated
		 */
		EClass TGG_CORRESPONDENCE = eINSTANCE.getTGGCorrespondence();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_CORRESPONDENCE__SOURCE = eINSTANCE.getTGGCorrespondence_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_CORRESPONDENCE__TARGET = eINSTANCE.getTGGCorrespondence_Target();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGEdgeImpl <em>TGG Edge</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGEdgeImpl
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getTGGEdge()
		 * @generated
		 */
		EClass TGG_EDGE = eINSTANCE.getTGGEdge();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.OperationalisationMode <em>Operationalisation Mode</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.OperationalisationMode
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getOperationalisationMode()
		 * @generated
		 */
		EEnum OPERATIONALISATION_MODE = eINSTANCE.getOperationalisationMode();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType <em>Domain Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getDomainType()
		 * @generated
		 */
		EEnum DOMAIN_TYPE = eINSTANCE.getDomainType();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType <em>Binding Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getBindingType()
		 * @generated
		 */
		EEnum BINDING_TYPE = eINSTANCE.getBindingType();

	}

} //IBeXTGGModelPackage
