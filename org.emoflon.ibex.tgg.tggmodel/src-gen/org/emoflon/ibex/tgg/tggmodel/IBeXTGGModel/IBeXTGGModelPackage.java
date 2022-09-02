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
	 * The feature id for the '<em><b>Feature Config</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_MODEL__FEATURE_CONFIG = IBeXCoreModelPackage.IBE_XMODEL__FEATURE_CONFIG;

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
	 * The feature id for the '<em><b>Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_MODEL__RULES = IBeXCoreModelPackage.IBE_XMODEL_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>TGG Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_MODEL_FEATURE_COUNT = IBeXCoreModelPackage.IBE_XMODEL_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>TGG Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_MODEL_OPERATION_COUNT = IBeXCoreModelPackage.IBE_XMODEL_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleImpl <em>TGG Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleImpl
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getTGGRule()
	 * @generated
	 */
	int TGG_RULE = 1;

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
	 * The feature id for the '<em><b>Refines</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__REFINES = IBeXCoreModelPackage.IBE_XRULE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Abstract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__ABSTRACT = IBeXCoreModelPackage.IBE_XRULE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__NODES = IBeXCoreModelPackage.IBE_XRULE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__EDGES = IBeXCoreModelPackage.IBE_XRULE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Correspondence Nodes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__CORRESPONDENCE_NODES = IBeXCoreModelPackage.IBE_XRULE_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>TGG Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_FEATURE_COUNT = IBeXCoreModelPackage.IBE_XRULE_FEATURE_COUNT + 5;

	/**
	 * The number of operations of the '<em>TGG Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_OPERATION_COUNT = IBeXCoreModelPackage.IBE_XRULE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGNodeImpl <em>TGG Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGNodeImpl
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getTGGNode()
	 * @generated
	 */
	int TGG_NODE = 2;

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
	 * The number of structural features of the '<em>TGG Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_NODE_FEATURE_COUNT = IBeXCoreModelPackage.IBE_XNODE_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>TGG Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_NODE_OPERATION_COUNT = IBeXCoreModelPackage.IBE_XNODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleCorrespondenceImpl <em>TGG Rule Correspondence</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleCorrespondenceImpl
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getTGGRuleCorrespondence()
	 * @generated
	 */
	int TGG_RULE_CORRESPONDENCE = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_CORRESPONDENCE__NAME = TGG_NODE__NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_CORRESPONDENCE__TYPE = TGG_NODE__TYPE;

	/**
	 * The feature id for the '<em><b>Incoming Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_CORRESPONDENCE__INCOMING_EDGES = TGG_NODE__INCOMING_EDGES;

	/**
	 * The feature id for the '<em><b>Outgoing Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_CORRESPONDENCE__OUTGOING_EDGES = TGG_NODE__OUTGOING_EDGES;

	/**
	 * The feature id for the '<em><b>Domain Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_CORRESPONDENCE__DOMAIN_TYPE = TGG_NODE__DOMAIN_TYPE;

	/**
	 * The feature id for the '<em><b>Binding Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_CORRESPONDENCE__BINDING_TYPE = TGG_NODE__BINDING_TYPE;

	/**
	 * The feature id for the '<em><b>Incoming Correspondence</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_CORRESPONDENCE__INCOMING_CORRESPONDENCE = TGG_NODE__INCOMING_CORRESPONDENCE;

	/**
	 * The feature id for the '<em><b>Outgoing Correspondence</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_CORRESPONDENCE__OUTGOING_CORRESPONDENCE = TGG_NODE__OUTGOING_CORRESPONDENCE;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_CORRESPONDENCE__SOURCE = TGG_NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_CORRESPONDENCE__TARGET = TGG_NODE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>TGG Rule Correspondence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_CORRESPONDENCE_FEATURE_COUNT = TGG_NODE_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>TGG Rule Correspondence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_CORRESPONDENCE_OPERATION_COUNT = TGG_NODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGEdgeImpl <em>TGG Edge</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGEdgeImpl
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getTGGEdge()
	 * @generated
	 */
	int TGG_EDGE = 4;

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
	 * The feature id for the '<em><b>Src</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_EDGE__SRC = IBeXCoreModelPackage.IBE_XEDGE__SRC;

	/**
	 * The feature id for the '<em><b>Trg</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_EDGE__TRG = IBeXCoreModelPackage.IBE_XEDGE__TRG;

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
	 * The meta object id for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleElementImpl <em>TGG Rule Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleElementImpl
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getTGGRuleElement()
	 * @generated
	 */
	int TGG_RULE_ELEMENT = 5;

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
	 * The meta object id for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGAttributeConstraintLibraryImpl <em>TGG Attribute Constraint Library</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGAttributeConstraintLibraryImpl
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getTGGAttributeConstraintLibrary()
	 * @generated
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_LIBRARY = 6;

	/**
	 * The feature id for the '<em><b>Tgg Attribute Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__TGG_ATTRIBUTE_CONSTRAINTS = 0;

	/**
	 * The number of structural features of the '<em>TGG Attribute Constraint Library</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_LIBRARY_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>TGG Attribute Constraint Library</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_LIBRARY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGAttributeConstraintDefinitionLibraryImpl <em>TGG Attribute Constraint Definition Library</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGAttributeConstraintDefinitionLibraryImpl
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getTGGAttributeConstraintDefinitionLibrary()
	 * @generated
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY = 7;

	/**
	 * The feature id for the '<em><b>Tgg Attribute Constraint Definitions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY__TGG_ATTRIBUTE_CONSTRAINT_DEFINITIONS = 0;

	/**
	 * The number of structural features of the '<em>TGG Attribute Constraint Definition Library</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>TGG Attribute Constraint Definition Library</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGAttributeConstraintImpl <em>TGG Attribute Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGAttributeConstraintImpl
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getTGGAttributeConstraint()
	 * @generated
	 */
	int TGG_ATTRIBUTE_CONSTRAINT = 8;

	/**
	 * The feature id for the '<em><b>Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT__DEFINITION = 0;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT__PARAMETERS = 1;

	/**
	 * The number of structural features of the '<em>TGG Attribute Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>TGG Attribute Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGParameterValueImpl <em>TGG Parameter Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGParameterValueImpl
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getTGGParameterValue()
	 * @generated
	 */
	int TGG_PARAMETER_VALUE = 9;

	/**
	 * The feature id for the '<em><b>Parameter Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_PARAMETER_VALUE__PARAMETER_DEFINITION = 0;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_PARAMETER_VALUE__EXPRESSION = 1;

	/**
	 * The number of structural features of the '<em>TGG Parameter Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_PARAMETER_VALUE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>TGG Parameter Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_PARAMETER_VALUE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGAttributeConstraintParameterDefinitionImpl <em>TGG Attribute Constraint Parameter Definition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGAttributeConstraintParameterDefinitionImpl
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getTGGAttributeConstraintParameterDefinition()
	 * @generated
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_DEFINITION = 10;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_DEFINITION__TYPE = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_DEFINITION__NAME = 1;

	/**
	 * The number of structural features of the '<em>TGG Attribute Constraint Parameter Definition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_DEFINITION_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>TGG Attribute Constraint Parameter Definition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_DEFINITION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGAttributeConstraintBindingImpl <em>TGG Attribute Constraint Binding</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGAttributeConstraintBindingImpl
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getTGGAttributeConstraintBinding()
	 * @generated
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_BINDING = 11;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_BINDING__VALUE = 0;

	/**
	 * The number of structural features of the '<em>TGG Attribute Constraint Binding</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_BINDING_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>TGG Attribute Constraint Binding</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_BINDING_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGAttributeConstraintDefinitionImpl <em>TGG Attribute Constraint Definition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGAttributeConstraintDefinitionImpl
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getTGGAttributeConstraintDefinition()
	 * @generated
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION = 12;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__NAME = IBeXCoreModelPackage.IBE_XNAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>User Defined</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__USER_DEFINED = IBeXCoreModelPackage.IBE_XNAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Parameter Definitions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__PARAMETER_DEFINITIONS = IBeXCoreModelPackage.IBE_XNAMED_ELEMENT_FEATURE_COUNT
			+ 1;

	/**
	 * The feature id for the '<em><b>Sync Bindings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__SYNC_BINDINGS = IBeXCoreModelPackage.IBE_XNAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Gen Bindings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__GEN_BINDINGS = IBeXCoreModelPackage.IBE_XNAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>TGG Attribute Constraint Definition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_FEATURE_COUNT = IBeXCoreModelPackage.IBE_XNAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>TGG Attribute Constraint Definition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_OPERATION_COUNT = IBeXCoreModelPackage.IBE_XNAMED_ELEMENT_OPERATION_COUNT
			+ 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType <em>Domain Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getDomainType()
	 * @generated
	 */
	int DOMAIN_TYPE = 13;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType <em>Binding Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getBindingType()
	 * @generated
	 */
	int BINDING_TYPE = 14;

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
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel#getRules <em>Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Rules</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel#getRules()
	 * @see #getTGGModel()
	 * @generated
	 */
	EReference getTGGModel_Rules();

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
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getRefines <em>Refines</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Refines</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getRefines()
	 * @see #getTGGRule()
	 * @generated
	 */
	EReference getTGGRule_Refines();

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
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getNodes <em>Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Nodes</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getNodes()
	 * @see #getTGGRule()
	 * @generated
	 */
	EReference getTGGRule_Nodes();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getEdges <em>Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Edges</em>'.
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
	 * Returns the meta object for class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleCorrespondence <em>TGG Rule Correspondence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Rule Correspondence</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleCorrespondence
	 * @generated
	 */
	EClass getTGGRuleCorrespondence();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleCorrespondence#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleCorrespondence#getSource()
	 * @see #getTGGRuleCorrespondence()
	 * @generated
	 */
	EReference getTGGRuleCorrespondence_Source();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleCorrespondence#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleCorrespondence#getTarget()
	 * @see #getTGGRuleCorrespondence()
	 * @generated
	 */
	EReference getTGGRuleCorrespondence_Target();

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
	 * Returns the meta object for class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintLibrary <em>TGG Attribute Constraint Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Attribute Constraint Library</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintLibrary
	 * @generated
	 */
	EClass getTGGAttributeConstraintLibrary();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintLibrary#getTggAttributeConstraints <em>Tgg Attribute Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Tgg Attribute Constraints</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintLibrary#getTggAttributeConstraints()
	 * @see #getTGGAttributeConstraintLibrary()
	 * @generated
	 */
	EReference getTGGAttributeConstraintLibrary_TggAttributeConstraints();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintDefinitionLibrary <em>TGG Attribute Constraint Definition Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Attribute Constraint Definition Library</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintDefinitionLibrary
	 * @generated
	 */
	EClass getTGGAttributeConstraintDefinitionLibrary();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintDefinitionLibrary#getTggAttributeConstraintDefinitions <em>Tgg Attribute Constraint Definitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Tgg Attribute Constraint Definitions</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintDefinitionLibrary#getTggAttributeConstraintDefinitions()
	 * @see #getTGGAttributeConstraintDefinitionLibrary()
	 * @generated
	 */
	EReference getTGGAttributeConstraintDefinitionLibrary_TggAttributeConstraintDefinitions();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraint <em>TGG Attribute Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Attribute Constraint</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraint
	 * @generated
	 */
	EClass getTGGAttributeConstraint();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraint#getDefinition <em>Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Definition</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraint#getDefinition()
	 * @see #getTGGAttributeConstraint()
	 * @generated
	 */
	EReference getTGGAttributeConstraint_Definition();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraint#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Parameters</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraint#getParameters()
	 * @see #getTGGAttributeConstraint()
	 * @generated
	 */
	EReference getTGGAttributeConstraint_Parameters();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGParameterValue <em>TGG Parameter Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Parameter Value</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGParameterValue
	 * @generated
	 */
	EClass getTGGParameterValue();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGParameterValue#getParameterDefinition <em>Parameter Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parameter Definition</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGParameterValue#getParameterDefinition()
	 * @see #getTGGParameterValue()
	 * @generated
	 */
	EReference getTGGParameterValue_ParameterDefinition();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGParameterValue#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGParameterValue#getExpression()
	 * @see #getTGGParameterValue()
	 * @generated
	 */
	EReference getTGGParameterValue_Expression();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintParameterDefinition <em>TGG Attribute Constraint Parameter Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Attribute Constraint Parameter Definition</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintParameterDefinition
	 * @generated
	 */
	EClass getTGGAttributeConstraintParameterDefinition();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintParameterDefinition#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintParameterDefinition#getType()
	 * @see #getTGGAttributeConstraintParameterDefinition()
	 * @generated
	 */
	EReference getTGGAttributeConstraintParameterDefinition_Type();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintParameterDefinition#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintParameterDefinition#getName()
	 * @see #getTGGAttributeConstraintParameterDefinition()
	 * @generated
	 */
	EAttribute getTGGAttributeConstraintParameterDefinition_Name();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintBinding <em>TGG Attribute Constraint Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Attribute Constraint Binding</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintBinding
	 * @generated
	 */
	EClass getTGGAttributeConstraintBinding();

	/**
	 * Returns the meta object for the attribute list '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintBinding#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Value</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintBinding#getValue()
	 * @see #getTGGAttributeConstraintBinding()
	 * @generated
	 */
	EAttribute getTGGAttributeConstraintBinding_Value();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintDefinition <em>TGG Attribute Constraint Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Attribute Constraint Definition</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintDefinition
	 * @generated
	 */
	EClass getTGGAttributeConstraintDefinition();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintDefinition#isUserDefined <em>User Defined</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>User Defined</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintDefinition#isUserDefined()
	 * @see #getTGGAttributeConstraintDefinition()
	 * @generated
	 */
	EAttribute getTGGAttributeConstraintDefinition_UserDefined();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintDefinition#getParameterDefinitions <em>Parameter Definitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameter Definitions</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintDefinition#getParameterDefinitions()
	 * @see #getTGGAttributeConstraintDefinition()
	 * @generated
	 */
	EReference getTGGAttributeConstraintDefinition_ParameterDefinitions();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintDefinition#getSyncBindings <em>Sync Bindings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sync Bindings</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintDefinition#getSyncBindings()
	 * @see #getTGGAttributeConstraintDefinition()
	 * @generated
	 */
	EReference getTGGAttributeConstraintDefinition_SyncBindings();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintDefinition#getGenBindings <em>Gen Bindings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Gen Bindings</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintDefinition#getGenBindings()
	 * @see #getTGGAttributeConstraintDefinition()
	 * @generated
	 */
	EReference getTGGAttributeConstraintDefinition_GenBindings();

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
		 * The meta object literal for the '<em><b>Rules</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_MODEL__RULES = eINSTANCE.getTGGModel_Rules();

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
		 * The meta object literal for the '<em><b>Refines</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE__REFINES = eINSTANCE.getTGGRule_Refines();

		/**
		 * The meta object literal for the '<em><b>Abstract</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TGG_RULE__ABSTRACT = eINSTANCE.getTGGRule_Abstract();

		/**
		 * The meta object literal for the '<em><b>Nodes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE__NODES = eINSTANCE.getTGGRule_Nodes();

		/**
		 * The meta object literal for the '<em><b>Edges</b></em>' reference list feature.
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
		 * The meta object literal for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleCorrespondenceImpl <em>TGG Rule Correspondence</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleCorrespondenceImpl
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getTGGRuleCorrespondence()
		 * @generated
		 */
		EClass TGG_RULE_CORRESPONDENCE = eINSTANCE.getTGGRuleCorrespondence();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE_CORRESPONDENCE__SOURCE = eINSTANCE.getTGGRuleCorrespondence_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE_CORRESPONDENCE__TARGET = eINSTANCE.getTGGRuleCorrespondence_Target();

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
		 * The meta object literal for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGAttributeConstraintLibraryImpl <em>TGG Attribute Constraint Library</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGAttributeConstraintLibraryImpl
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getTGGAttributeConstraintLibrary()
		 * @generated
		 */
		EClass TGG_ATTRIBUTE_CONSTRAINT_LIBRARY = eINSTANCE.getTGGAttributeConstraintLibrary();

		/**
		 * The meta object literal for the '<em><b>Tgg Attribute Constraints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__TGG_ATTRIBUTE_CONSTRAINTS = eINSTANCE
				.getTGGAttributeConstraintLibrary_TggAttributeConstraints();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGAttributeConstraintDefinitionLibraryImpl <em>TGG Attribute Constraint Definition Library</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGAttributeConstraintDefinitionLibraryImpl
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getTGGAttributeConstraintDefinitionLibrary()
		 * @generated
		 */
		EClass TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY = eINSTANCE.getTGGAttributeConstraintDefinitionLibrary();

		/**
		 * The meta object literal for the '<em><b>Tgg Attribute Constraint Definitions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY__TGG_ATTRIBUTE_CONSTRAINT_DEFINITIONS = eINSTANCE
				.getTGGAttributeConstraintDefinitionLibrary_TggAttributeConstraintDefinitions();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGAttributeConstraintImpl <em>TGG Attribute Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGAttributeConstraintImpl
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getTGGAttributeConstraint()
		 * @generated
		 */
		EClass TGG_ATTRIBUTE_CONSTRAINT = eINSTANCE.getTGGAttributeConstraint();

		/**
		 * The meta object literal for the '<em><b>Definition</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_ATTRIBUTE_CONSTRAINT__DEFINITION = eINSTANCE.getTGGAttributeConstraint_Definition();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_ATTRIBUTE_CONSTRAINT__PARAMETERS = eINSTANCE.getTGGAttributeConstraint_Parameters();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGParameterValueImpl <em>TGG Parameter Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGParameterValueImpl
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getTGGParameterValue()
		 * @generated
		 */
		EClass TGG_PARAMETER_VALUE = eINSTANCE.getTGGParameterValue();

		/**
		 * The meta object literal for the '<em><b>Parameter Definition</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_PARAMETER_VALUE__PARAMETER_DEFINITION = eINSTANCE.getTGGParameterValue_ParameterDefinition();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_PARAMETER_VALUE__EXPRESSION = eINSTANCE.getTGGParameterValue_Expression();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGAttributeConstraintParameterDefinitionImpl <em>TGG Attribute Constraint Parameter Definition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGAttributeConstraintParameterDefinitionImpl
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getTGGAttributeConstraintParameterDefinition()
		 * @generated
		 */
		EClass TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_DEFINITION = eINSTANCE.getTGGAttributeConstraintParameterDefinition();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_DEFINITION__TYPE = eINSTANCE
				.getTGGAttributeConstraintParameterDefinition_Type();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_DEFINITION__NAME = eINSTANCE
				.getTGGAttributeConstraintParameterDefinition_Name();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGAttributeConstraintBindingImpl <em>TGG Attribute Constraint Binding</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGAttributeConstraintBindingImpl
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getTGGAttributeConstraintBinding()
		 * @generated
		 */
		EClass TGG_ATTRIBUTE_CONSTRAINT_BINDING = eINSTANCE.getTGGAttributeConstraintBinding();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TGG_ATTRIBUTE_CONSTRAINT_BINDING__VALUE = eINSTANCE.getTGGAttributeConstraintBinding_Value();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGAttributeConstraintDefinitionImpl <em>TGG Attribute Constraint Definition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGAttributeConstraintDefinitionImpl
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelPackageImpl#getTGGAttributeConstraintDefinition()
		 * @generated
		 */
		EClass TGG_ATTRIBUTE_CONSTRAINT_DEFINITION = eINSTANCE.getTGGAttributeConstraintDefinition();

		/**
		 * The meta object literal for the '<em><b>User Defined</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__USER_DEFINED = eINSTANCE
				.getTGGAttributeConstraintDefinition_UserDefined();

		/**
		 * The meta object literal for the '<em><b>Parameter Definitions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__PARAMETER_DEFINITIONS = eINSTANCE
				.getTGGAttributeConstraintDefinition_ParameterDefinitions();

		/**
		 * The meta object literal for the '<em><b>Sync Bindings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__SYNC_BINDINGS = eINSTANCE
				.getTGGAttributeConstraintDefinition_SyncBindings();

		/**
		 * The meta object literal for the '<em><b>Gen Bindings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__GEN_BINDINGS = eINSTANCE
				.getTGGAttributeConstraintDefinition_GenBindings();

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
