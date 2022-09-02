/**
 */
package org.emoflon.ibex.common.coremodel.IBeXCoreModel;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.IBeXCoreArithmeticPackage;

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
 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelFactory
 * @model kind="package"
 * @generated
 */
public interface IBeXCoreModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "IBeXCoreModel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "platform:/resource/org.emoflon.ibex.common.coremodel/model/IBeXCoreModel.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "IBeXCoreModel";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	IBeXCoreModelPackage eINSTANCE = org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXCoreModelPackageImpl
			.init();

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXNamedElementImpl <em>IBe XNamed Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXNamedElementImpl
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXCoreModelPackageImpl#getIBeXNamedElement()
	 * @generated
	 */
	int IBE_XNAMED_ELEMENT = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XNAMED_ELEMENT__NAME = 0;

	/**
	 * The number of structural features of the '<em>IBe XNamed Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XNAMED_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>IBe XNamed Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XNAMED_ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXModelImpl <em>IBe XModel</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXModelImpl
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXCoreModelPackageImpl#getIBeXModel()
	 * @generated
	 */
	int IBE_XMODEL = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XMODEL__NAME = IBE_XNAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Pattern Set</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XMODEL__PATTERN_SET = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Node Set</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XMODEL__NODE_SET = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Edge Set</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XMODEL__EDGE_SET = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Feature Config</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XMODEL__FEATURE_CONFIG = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>IBe XModel</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XMODEL_FEATURE_COUNT = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>IBe XModel</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XMODEL_OPERATION_COUNT = IBE_XNAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXFeatureConfigImpl <em>IBe XFeature Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXFeatureConfigImpl
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXCoreModelPackageImpl#getIBeXFeatureConfig()
	 * @generated
	 */
	int IBE_XFEATURE_CONFIG = 2;

	/**
	 * The feature id for the '<em><b>Count Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XFEATURE_CONFIG__COUNT_REQUIRED = 0;

	/**
	 * The number of structural features of the '<em>IBe XFeature Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XFEATURE_CONFIG_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>IBe XFeature Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XFEATURE_CONFIG_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXPatternSetImpl <em>IBe XPattern Set</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXPatternSetImpl
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXCoreModelPackageImpl#getIBeXPatternSet()
	 * @generated
	 */
	int IBE_XPATTERN_SET = 3;

	/**
	 * The feature id for the '<em><b>Patterns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPATTERN_SET__PATTERNS = 0;

	/**
	 * The number of structural features of the '<em>IBe XPattern Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPATTERN_SET_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>IBe XPattern Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPATTERN_SET_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXNodeSetImpl <em>IBe XNode Set</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXNodeSetImpl
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXCoreModelPackageImpl#getIBeXNodeSet()
	 * @generated
	 */
	int IBE_XNODE_SET = 4;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XNODE_SET__NODES = 0;

	/**
	 * The number of structural features of the '<em>IBe XNode Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XNODE_SET_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>IBe XNode Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XNODE_SET_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXEdgeSetImpl <em>IBe XEdge Set</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXEdgeSetImpl
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXCoreModelPackageImpl#getIBeXEdgeSet()
	 * @generated
	 */
	int IBE_XEDGE_SET = 5;

	/**
	 * The feature id for the '<em><b>Edges</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XEDGE_SET__EDGES = 0;

	/**
	 * The number of structural features of the '<em>IBe XEdge Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XEDGE_SET_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>IBe XEdge Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XEDGE_SET_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXNodeImpl <em>IBe XNode</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXNodeImpl
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXCoreModelPackageImpl#getIBeXNode()
	 * @generated
	 */
	int IBE_XNODE = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XNODE__NAME = IBE_XNAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XNODE__TYPE = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Incoming Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XNODE__INCOMING_EDGES = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Outgoing Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XNODE__OUTGOING_EDGES = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>IBe XNode</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XNODE_FEATURE_COUNT = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>IBe XNode</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XNODE_OPERATION_COUNT = IBE_XNAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXEdgeImpl <em>IBe XEdge</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXEdgeImpl
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXCoreModelPackageImpl#getIBeXEdge()
	 * @generated
	 */
	int IBE_XEDGE = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XEDGE__NAME = IBE_XNAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XEDGE__TYPE = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Src</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XEDGE__SRC = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Trg</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XEDGE__TRG = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>IBe XEdge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XEDGE_FEATURE_COUNT = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>IBe XEdge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XEDGE_OPERATION_COUNT = IBE_XNAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXPatternImpl <em>IBe XPattern</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXPatternImpl
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXCoreModelPackageImpl#getIBeXPattern()
	 * @generated
	 */
	int IBE_XPATTERN = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPATTERN__NAME = IBE_XNAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Dependencies</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPATTERN__DEPENDENCIES = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Signature Nodes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPATTERN__SIGNATURE_NODES = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Local Nodes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPATTERN__LOCAL_NODES = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPATTERN__EDGES = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Attribute Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPATTERN__ATTRIBUTE_CONSTRAINTS = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Injectivity Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPATTERN__INJECTIVITY_CONSTRAINTS = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Invocations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPATTERN__INVOCATIONS = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>IBe XPattern</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPATTERN_FEATURE_COUNT = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 7;

	/**
	 * The number of operations of the '<em>IBe XPattern</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPATTERN_OPERATION_COUNT = IBE_XNAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXInjectivityConstraintImpl <em>IBe XInjectivity Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXInjectivityConstraintImpl
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXCoreModelPackageImpl#getIBeXInjectivityConstraint()
	 * @generated
	 */
	int IBE_XINJECTIVITY_CONSTRAINT = 9;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XINJECTIVITY_CONSTRAINT__NODES = 0;

	/**
	 * The number of structural features of the '<em>IBe XInjectivity Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XINJECTIVITY_CONSTRAINT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>IBe XInjectivity Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XINJECTIVITY_CONSTRAINT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXPatternInvocationImpl <em>IBe XPattern Invocation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXPatternInvocationImpl
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXCoreModelPackageImpl#getIBeXPatternInvocation()
	 * @generated
	 */
	int IBE_XPATTERN_INVOCATION = 10;

	/**
	 * The feature id for the '<em><b>Positive</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPATTERN_INVOCATION__POSITIVE = 0;

	/**
	 * The feature id for the '<em><b>Invoked By</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPATTERN_INVOCATION__INVOKED_BY = 1;

	/**
	 * The feature id for the '<em><b>Invocation</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPATTERN_INVOCATION__INVOCATION = 2;

	/**
	 * The feature id for the '<em><b>Mapping</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPATTERN_INVOCATION__MAPPING = 3;

	/**
	 * The number of structural features of the '<em>IBe XPattern Invocation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPATTERN_INVOCATION_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>IBe XPattern Invocation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPATTERN_INVOCATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXNodeMappingImpl <em>IBe XNode Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXNodeMappingImpl
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXCoreModelPackageImpl#getIBeXNodeMapping()
	 * @generated
	 */
	int IBE_XNODE_MAPPING = 11;

	/**
	 * The feature id for the '<em><b>Key</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XNODE_MAPPING__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XNODE_MAPPING__VALUE = 1;

	/**
	 * The number of structural features of the '<em>IBe XNode Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XNODE_MAPPING_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>IBe XNode Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XNODE_MAPPING_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXEnumValueImpl <em>IBe XEnum Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXEnumValueImpl
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXCoreModelPackageImpl#getIBeXEnumValue()
	 * @generated
	 */
	int IBE_XENUM_VALUE = 12;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XENUM_VALUE__TYPE = IBeXCoreArithmeticPackage.VALUE_EXPRESSION__TYPE;

	/**
	 * The feature id for the '<em><b>Literal</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XENUM_VALUE__LITERAL = IBeXCoreArithmeticPackage.VALUE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>IBe XEnum Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XENUM_VALUE_FEATURE_COUNT = IBeXCoreArithmeticPackage.VALUE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>IBe XEnum Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XENUM_VALUE_OPERATION_COUNT = IBeXCoreArithmeticPackage.VALUE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXStringValueImpl <em>IBe XString Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXStringValueImpl
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXCoreModelPackageImpl#getIBeXStringValue()
	 * @generated
	 */
	int IBE_XSTRING_VALUE = 13;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XSTRING_VALUE__TYPE = IBeXCoreArithmeticPackage.VALUE_EXPRESSION__TYPE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XSTRING_VALUE__VALUE = IBeXCoreArithmeticPackage.VALUE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>IBe XString Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XSTRING_VALUE_FEATURE_COUNT = IBeXCoreArithmeticPackage.VALUE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>IBe XString Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XSTRING_VALUE_OPERATION_COUNT = IBeXCoreArithmeticPackage.VALUE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXBooleanValueImpl <em>IBe XBoolean Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXBooleanValueImpl
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXCoreModelPackageImpl#getIBeXBooleanValue()
	 * @generated
	 */
	int IBE_XBOOLEAN_VALUE = 14;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XBOOLEAN_VALUE__TYPE = IBeXCoreArithmeticPackage.VALUE_EXPRESSION__TYPE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XBOOLEAN_VALUE__VALUE = IBeXCoreArithmeticPackage.VALUE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>IBe XBoolean Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XBOOLEAN_VALUE_FEATURE_COUNT = IBeXCoreArithmeticPackage.VALUE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>IBe XBoolean Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XBOOLEAN_VALUE_OPERATION_COUNT = IBeXCoreArithmeticPackage.VALUE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXAttributeValueImpl <em>IBe XAttribute Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXAttributeValueImpl
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXCoreModelPackageImpl#getIBeXAttributeValue()
	 * @generated
	 */
	int IBE_XATTRIBUTE_VALUE = 15;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_VALUE__TYPE = IBeXCoreArithmeticPackage.ARITHMETIC_VALUE__TYPE;

	/**
	 * The feature id for the '<em><b>Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_VALUE__NODE = IBeXCoreArithmeticPackage.ARITHMETIC_VALUE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_VALUE__ATTRIBUTE = IBeXCoreArithmeticPackage.ARITHMETIC_VALUE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>IBe XAttribute Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_VALUE_FEATURE_COUNT = IBeXCoreArithmeticPackage.ARITHMETIC_VALUE_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>IBe XAttribute Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_VALUE_OPERATION_COUNT = IBeXCoreArithmeticPackage.ARITHMETIC_VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXMatchCountValueImpl <em>IBe XMatch Count Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXMatchCountValueImpl
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXCoreModelPackageImpl#getIBeXMatchCountValue()
	 * @generated
	 */
	int IBE_XMATCH_COUNT_VALUE = 16;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XMATCH_COUNT_VALUE__TYPE = IBeXCoreArithmeticPackage.ARITHMETIC_VALUE__TYPE;

	/**
	 * The feature id for the '<em><b>Invocation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XMATCH_COUNT_VALUE__INVOCATION = IBeXCoreArithmeticPackage.ARITHMETIC_VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>IBe XMatch Count Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XMATCH_COUNT_VALUE_FEATURE_COUNT = IBeXCoreArithmeticPackage.ARITHMETIC_VALUE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>IBe XMatch Count Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XMATCH_COUNT_VALUE_OPERATION_COUNT = IBeXCoreArithmeticPackage.ARITHMETIC_VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXRuleImpl <em>IBe XRule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXRuleImpl
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXCoreModelPackageImpl#getIBeXRule()
	 * @generated
	 */
	int IBE_XRULE = 17;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XRULE__NAME = IBE_XNAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Precondition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XRULE__PRECONDITION = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Postcondition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XRULE__POSTCONDITION = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Creation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XRULE__CREATION = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Deletion</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XRULE__DELETION = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Attribute Assignments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XRULE__ATTRIBUTE_ASSIGNMENTS = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>All Nodes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XRULE__ALL_NODES = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>All Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XRULE__ALL_EDGES = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>IBe XRule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XRULE_FEATURE_COUNT = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 7;

	/**
	 * The number of operations of the '<em>IBe XRule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XRULE_OPERATION_COUNT = IBE_XNAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXRuleDeltaImpl <em>IBe XRule Delta</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXRuleDeltaImpl
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXCoreModelPackageImpl#getIBeXRuleDelta()
	 * @generated
	 */
	int IBE_XRULE_DELTA = 18;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XRULE_DELTA__NODES = 0;

	/**
	 * The feature id for the '<em><b>Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XRULE_DELTA__EDGES = 1;

	/**
	 * The number of structural features of the '<em>IBe XRule Delta</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XRULE_DELTA_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>IBe XRule Delta</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XRULE_DELTA_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXAttributeAssignmentImpl <em>IBe XAttribute Assignment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXAttributeAssignmentImpl
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXCoreModelPackageImpl#getIBeXAttributeAssignment()
	 * @generated
	 */
	int IBE_XATTRIBUTE_ASSIGNMENT = 19;

	/**
	 * The feature id for the '<em><b>Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_ASSIGNMENT__NODE = 0;

	/**
	 * The feature id for the '<em><b>Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_ASSIGNMENT__ATTRIBUTE = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_ASSIGNMENT__VALUE = 2;

	/**
	 * The number of structural features of the '<em>IBe XAttribute Assignment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_ASSIGNMENT_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>IBe XAttribute Assignment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_ASSIGNMENT_OPERATION_COUNT = 0;

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNamedElement <em>IBe XNamed Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XNamed Element</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNamedElement
	 * @generated
	 */
	EClass getIBeXNamedElement();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNamedElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNamedElement#getName()
	 * @see #getIBeXNamedElement()
	 * @generated
	 */
	EAttribute getIBeXNamedElement_Name();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModel <em>IBe XModel</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XModel</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModel
	 * @generated
	 */
	EClass getIBeXModel();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModel#getPatternSet <em>Pattern Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pattern Set</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModel#getPatternSet()
	 * @see #getIBeXModel()
	 * @generated
	 */
	EReference getIBeXModel_PatternSet();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModel#getNodeSet <em>Node Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Node Set</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModel#getNodeSet()
	 * @see #getIBeXModel()
	 * @generated
	 */
	EReference getIBeXModel_NodeSet();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModel#getEdgeSet <em>Edge Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Edge Set</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModel#getEdgeSet()
	 * @see #getIBeXModel()
	 * @generated
	 */
	EReference getIBeXModel_EdgeSet();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModel#getFeatureConfig <em>Feature Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Feature Config</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModel#getFeatureConfig()
	 * @see #getIBeXModel()
	 * @generated
	 */
	EReference getIBeXModel_FeatureConfig();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXFeatureConfig <em>IBe XFeature Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XFeature Config</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXFeatureConfig
	 * @generated
	 */
	EClass getIBeXFeatureConfig();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXFeatureConfig#isCountRequired <em>Count Required</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Count Required</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXFeatureConfig#isCountRequired()
	 * @see #getIBeXFeatureConfig()
	 * @generated
	 */
	EAttribute getIBeXFeatureConfig_CountRequired();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternSet <em>IBe XPattern Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XPattern Set</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternSet
	 * @generated
	 */
	EClass getIBeXPatternSet();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternSet#getPatterns <em>Patterns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Patterns</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternSet#getPatterns()
	 * @see #getIBeXPatternSet()
	 * @generated
	 */
	EReference getIBeXPatternSet_Patterns();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNodeSet <em>IBe XNode Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XNode Set</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNodeSet
	 * @generated
	 */
	EClass getIBeXNodeSet();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNodeSet#getNodes <em>Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Nodes</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNodeSet#getNodes()
	 * @see #getIBeXNodeSet()
	 * @generated
	 */
	EReference getIBeXNodeSet_Nodes();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdgeSet <em>IBe XEdge Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XEdge Set</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdgeSet
	 * @generated
	 */
	EClass getIBeXEdgeSet();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdgeSet#getEdges <em>Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Edges</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdgeSet#getEdges()
	 * @see #getIBeXEdgeSet()
	 * @generated
	 */
	EReference getIBeXEdgeSet_Edges();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode <em>IBe XNode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XNode</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode
	 * @generated
	 */
	EClass getIBeXNode();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode#getType()
	 * @see #getIBeXNode()
	 * @generated
	 */
	EReference getIBeXNode_Type();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode#getIncomingEdges <em>Incoming Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Incoming Edges</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode#getIncomingEdges()
	 * @see #getIBeXNode()
	 * @generated
	 */
	EReference getIBeXNode_IncomingEdges();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode#getOutgoingEdges <em>Outgoing Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Outgoing Edges</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode#getOutgoingEdges()
	 * @see #getIBeXNode()
	 * @generated
	 */
	EReference getIBeXNode_OutgoingEdges();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge <em>IBe XEdge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XEdge</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge
	 * @generated
	 */
	EClass getIBeXEdge();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge#getType()
	 * @see #getIBeXEdge()
	 * @generated
	 */
	EReference getIBeXEdge_Type();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge#getSrc <em>Src</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Src</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge#getSrc()
	 * @see #getIBeXEdge()
	 * @generated
	 */
	EReference getIBeXEdge_Src();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge#getTrg <em>Trg</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Trg</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge#getTrg()
	 * @see #getIBeXEdge()
	 * @generated
	 */
	EReference getIBeXEdge_Trg();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern <em>IBe XPattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XPattern</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern
	 * @generated
	 */
	EClass getIBeXPattern();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern#getDependencies <em>Dependencies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Dependencies</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern#getDependencies()
	 * @see #getIBeXPattern()
	 * @generated
	 */
	EReference getIBeXPattern_Dependencies();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern#getSignatureNodes <em>Signature Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Signature Nodes</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern#getSignatureNodes()
	 * @see #getIBeXPattern()
	 * @generated
	 */
	EReference getIBeXPattern_SignatureNodes();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern#getLocalNodes <em>Local Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Local Nodes</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern#getLocalNodes()
	 * @see #getIBeXPattern()
	 * @generated
	 */
	EReference getIBeXPattern_LocalNodes();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern#getEdges <em>Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Edges</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern#getEdges()
	 * @see #getIBeXPattern()
	 * @generated
	 */
	EReference getIBeXPattern_Edges();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern#getAttributeConstraints <em>Attribute Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attribute Constraints</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern#getAttributeConstraints()
	 * @see #getIBeXPattern()
	 * @generated
	 */
	EReference getIBeXPattern_AttributeConstraints();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern#getInjectivityConstraints <em>Injectivity Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Injectivity Constraints</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern#getInjectivityConstraints()
	 * @see #getIBeXPattern()
	 * @generated
	 */
	EReference getIBeXPattern_InjectivityConstraints();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern#getInvocations <em>Invocations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Invocations</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern#getInvocations()
	 * @see #getIBeXPattern()
	 * @generated
	 */
	EReference getIBeXPattern_Invocations();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXInjectivityConstraint <em>IBe XInjectivity Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XInjectivity Constraint</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXInjectivityConstraint
	 * @generated
	 */
	EClass getIBeXInjectivityConstraint();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXInjectivityConstraint#getNodes <em>Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Nodes</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXInjectivityConstraint#getNodes()
	 * @see #getIBeXInjectivityConstraint()
	 * @generated
	 */
	EReference getIBeXInjectivityConstraint_Nodes();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternInvocation <em>IBe XPattern Invocation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XPattern Invocation</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternInvocation
	 * @generated
	 */
	EClass getIBeXPatternInvocation();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternInvocation#isPositive <em>Positive</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Positive</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternInvocation#isPositive()
	 * @see #getIBeXPatternInvocation()
	 * @generated
	 */
	EAttribute getIBeXPatternInvocation_Positive();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternInvocation#getInvokedBy <em>Invoked By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Invoked By</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternInvocation#getInvokedBy()
	 * @see #getIBeXPatternInvocation()
	 * @generated
	 */
	EReference getIBeXPatternInvocation_InvokedBy();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternInvocation#getInvocation <em>Invocation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Invocation</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternInvocation#getInvocation()
	 * @see #getIBeXPatternInvocation()
	 * @generated
	 */
	EReference getIBeXPatternInvocation_Invocation();

	/**
	 * Returns the meta object for the map '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternInvocation#getMapping <em>Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Mapping</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternInvocation#getMapping()
	 * @see #getIBeXPatternInvocation()
	 * @generated
	 */
	EReference getIBeXPatternInvocation_Mapping();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>IBe XNode Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XNode Mapping</em>'.
	 * @see java.util.Map.Entry
	 * @model keyType="org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode" keyRequired="true"
	 *        valueType="org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode" valueRequired="true"
	 * @generated
	 */
	EClass getIBeXNodeMapping();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getIBeXNodeMapping()
	 * @generated
	 */
	EReference getIBeXNodeMapping_Key();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getIBeXNodeMapping()
	 * @generated
	 */
	EReference getIBeXNodeMapping_Value();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEnumValue <em>IBe XEnum Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XEnum Value</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEnumValue
	 * @generated
	 */
	EClass getIBeXEnumValue();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEnumValue#getLiteral <em>Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Literal</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEnumValue#getLiteral()
	 * @see #getIBeXEnumValue()
	 * @generated
	 */
	EReference getIBeXEnumValue_Literal();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXStringValue <em>IBe XString Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XString Value</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXStringValue
	 * @generated
	 */
	EClass getIBeXStringValue();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXStringValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXStringValue#getValue()
	 * @see #getIBeXStringValue()
	 * @generated
	 */
	EAttribute getIBeXStringValue_Value();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXBooleanValue <em>IBe XBoolean Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XBoolean Value</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXBooleanValue
	 * @generated
	 */
	EClass getIBeXBooleanValue();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXBooleanValue#isValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXBooleanValue#isValue()
	 * @see #getIBeXBooleanValue()
	 * @generated
	 */
	EAttribute getIBeXBooleanValue_Value();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeValue <em>IBe XAttribute Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XAttribute Value</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeValue
	 * @generated
	 */
	EClass getIBeXAttributeValue();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeValue#getNode <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Node</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeValue#getNode()
	 * @see #getIBeXAttributeValue()
	 * @generated
	 */
	EReference getIBeXAttributeValue_Node();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeValue#getAttribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Attribute</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeValue#getAttribute()
	 * @see #getIBeXAttributeValue()
	 * @generated
	 */
	EReference getIBeXAttributeValue_Attribute();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXMatchCountValue <em>IBe XMatch Count Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XMatch Count Value</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXMatchCountValue
	 * @generated
	 */
	EClass getIBeXMatchCountValue();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXMatchCountValue#getInvocation <em>Invocation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Invocation</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXMatchCountValue#getInvocation()
	 * @see #getIBeXMatchCountValue()
	 * @generated
	 */
	EReference getIBeXMatchCountValue_Invocation();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRule <em>IBe XRule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XRule</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRule
	 * @generated
	 */
	EClass getIBeXRule();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRule#getPrecondition <em>Precondition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Precondition</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRule#getPrecondition()
	 * @see #getIBeXRule()
	 * @generated
	 */
	EReference getIBeXRule_Precondition();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRule#getPostcondition <em>Postcondition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Postcondition</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRule#getPostcondition()
	 * @see #getIBeXRule()
	 * @generated
	 */
	EReference getIBeXRule_Postcondition();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRule#getCreation <em>Creation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Creation</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRule#getCreation()
	 * @see #getIBeXRule()
	 * @generated
	 */
	EReference getIBeXRule_Creation();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRule#getDeletion <em>Deletion</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Deletion</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRule#getDeletion()
	 * @see #getIBeXRule()
	 * @generated
	 */
	EReference getIBeXRule_Deletion();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRule#getAttributeAssignments <em>Attribute Assignments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attribute Assignments</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRule#getAttributeAssignments()
	 * @see #getIBeXRule()
	 * @generated
	 */
	EReference getIBeXRule_AttributeAssignments();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRule#getAllNodes <em>All Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>All Nodes</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRule#getAllNodes()
	 * @see #getIBeXRule()
	 * @generated
	 */
	EReference getIBeXRule_AllNodes();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRule#getAllEdges <em>All Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>All Edges</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRule#getAllEdges()
	 * @see #getIBeXRule()
	 * @generated
	 */
	EReference getIBeXRule_AllEdges();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRuleDelta <em>IBe XRule Delta</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XRule Delta</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRuleDelta
	 * @generated
	 */
	EClass getIBeXRuleDelta();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRuleDelta#getNodes <em>Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Nodes</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRuleDelta#getNodes()
	 * @see #getIBeXRuleDelta()
	 * @generated
	 */
	EReference getIBeXRuleDelta_Nodes();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRuleDelta#getEdges <em>Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Edges</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRuleDelta#getEdges()
	 * @see #getIBeXRuleDelta()
	 * @generated
	 */
	EReference getIBeXRuleDelta_Edges();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeAssignment <em>IBe XAttribute Assignment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XAttribute Assignment</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeAssignment
	 * @generated
	 */
	EClass getIBeXAttributeAssignment();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeAssignment#getNode <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Node</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeAssignment#getNode()
	 * @see #getIBeXAttributeAssignment()
	 * @generated
	 */
	EReference getIBeXAttributeAssignment_Node();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeAssignment#getAttribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Attribute</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeAssignment#getAttribute()
	 * @see #getIBeXAttributeAssignment()
	 * @generated
	 */
	EReference getIBeXAttributeAssignment_Attribute();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeAssignment#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeAssignment#getValue()
	 * @see #getIBeXAttributeAssignment()
	 * @generated
	 */
	EReference getIBeXAttributeAssignment_Value();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	IBeXCoreModelFactory getIBeXCoreModelFactory();

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
		 * The meta object literal for the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXNamedElementImpl <em>IBe XNamed Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXNamedElementImpl
		 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXCoreModelPackageImpl#getIBeXNamedElement()
		 * @generated
		 */
		EClass IBE_XNAMED_ELEMENT = eINSTANCE.getIBeXNamedElement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IBE_XNAMED_ELEMENT__NAME = eINSTANCE.getIBeXNamedElement_Name();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXModelImpl <em>IBe XModel</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXModelImpl
		 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXCoreModelPackageImpl#getIBeXModel()
		 * @generated
		 */
		EClass IBE_XMODEL = eINSTANCE.getIBeXModel();

		/**
		 * The meta object literal for the '<em><b>Pattern Set</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XMODEL__PATTERN_SET = eINSTANCE.getIBeXModel_PatternSet();

		/**
		 * The meta object literal for the '<em><b>Node Set</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XMODEL__NODE_SET = eINSTANCE.getIBeXModel_NodeSet();

		/**
		 * The meta object literal for the '<em><b>Edge Set</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XMODEL__EDGE_SET = eINSTANCE.getIBeXModel_EdgeSet();

		/**
		 * The meta object literal for the '<em><b>Feature Config</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XMODEL__FEATURE_CONFIG = eINSTANCE.getIBeXModel_FeatureConfig();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXFeatureConfigImpl <em>IBe XFeature Config</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXFeatureConfigImpl
		 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXCoreModelPackageImpl#getIBeXFeatureConfig()
		 * @generated
		 */
		EClass IBE_XFEATURE_CONFIG = eINSTANCE.getIBeXFeatureConfig();

		/**
		 * The meta object literal for the '<em><b>Count Required</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IBE_XFEATURE_CONFIG__COUNT_REQUIRED = eINSTANCE.getIBeXFeatureConfig_CountRequired();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXPatternSetImpl <em>IBe XPattern Set</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXPatternSetImpl
		 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXCoreModelPackageImpl#getIBeXPatternSet()
		 * @generated
		 */
		EClass IBE_XPATTERN_SET = eINSTANCE.getIBeXPatternSet();

		/**
		 * The meta object literal for the '<em><b>Patterns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XPATTERN_SET__PATTERNS = eINSTANCE.getIBeXPatternSet_Patterns();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXNodeSetImpl <em>IBe XNode Set</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXNodeSetImpl
		 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXCoreModelPackageImpl#getIBeXNodeSet()
		 * @generated
		 */
		EClass IBE_XNODE_SET = eINSTANCE.getIBeXNodeSet();

		/**
		 * The meta object literal for the '<em><b>Nodes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XNODE_SET__NODES = eINSTANCE.getIBeXNodeSet_Nodes();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXEdgeSetImpl <em>IBe XEdge Set</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXEdgeSetImpl
		 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXCoreModelPackageImpl#getIBeXEdgeSet()
		 * @generated
		 */
		EClass IBE_XEDGE_SET = eINSTANCE.getIBeXEdgeSet();

		/**
		 * The meta object literal for the '<em><b>Edges</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XEDGE_SET__EDGES = eINSTANCE.getIBeXEdgeSet_Edges();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXNodeImpl <em>IBe XNode</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXNodeImpl
		 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXCoreModelPackageImpl#getIBeXNode()
		 * @generated
		 */
		EClass IBE_XNODE = eINSTANCE.getIBeXNode();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XNODE__TYPE = eINSTANCE.getIBeXNode_Type();

		/**
		 * The meta object literal for the '<em><b>Incoming Edges</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XNODE__INCOMING_EDGES = eINSTANCE.getIBeXNode_IncomingEdges();

		/**
		 * The meta object literal for the '<em><b>Outgoing Edges</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XNODE__OUTGOING_EDGES = eINSTANCE.getIBeXNode_OutgoingEdges();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXEdgeImpl <em>IBe XEdge</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXEdgeImpl
		 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXCoreModelPackageImpl#getIBeXEdge()
		 * @generated
		 */
		EClass IBE_XEDGE = eINSTANCE.getIBeXEdge();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XEDGE__TYPE = eINSTANCE.getIBeXEdge_Type();

		/**
		 * The meta object literal for the '<em><b>Src</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XEDGE__SRC = eINSTANCE.getIBeXEdge_Src();

		/**
		 * The meta object literal for the '<em><b>Trg</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XEDGE__TRG = eINSTANCE.getIBeXEdge_Trg();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXPatternImpl <em>IBe XPattern</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXPatternImpl
		 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXCoreModelPackageImpl#getIBeXPattern()
		 * @generated
		 */
		EClass IBE_XPATTERN = eINSTANCE.getIBeXPattern();

		/**
		 * The meta object literal for the '<em><b>Dependencies</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XPATTERN__DEPENDENCIES = eINSTANCE.getIBeXPattern_Dependencies();

		/**
		 * The meta object literal for the '<em><b>Signature Nodes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XPATTERN__SIGNATURE_NODES = eINSTANCE.getIBeXPattern_SignatureNodes();

		/**
		 * The meta object literal for the '<em><b>Local Nodes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XPATTERN__LOCAL_NODES = eINSTANCE.getIBeXPattern_LocalNodes();

		/**
		 * The meta object literal for the '<em><b>Edges</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XPATTERN__EDGES = eINSTANCE.getIBeXPattern_Edges();

		/**
		 * The meta object literal for the '<em><b>Attribute Constraints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XPATTERN__ATTRIBUTE_CONSTRAINTS = eINSTANCE.getIBeXPattern_AttributeConstraints();

		/**
		 * The meta object literal for the '<em><b>Injectivity Constraints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XPATTERN__INJECTIVITY_CONSTRAINTS = eINSTANCE.getIBeXPattern_InjectivityConstraints();

		/**
		 * The meta object literal for the '<em><b>Invocations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XPATTERN__INVOCATIONS = eINSTANCE.getIBeXPattern_Invocations();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXInjectivityConstraintImpl <em>IBe XInjectivity Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXInjectivityConstraintImpl
		 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXCoreModelPackageImpl#getIBeXInjectivityConstraint()
		 * @generated
		 */
		EClass IBE_XINJECTIVITY_CONSTRAINT = eINSTANCE.getIBeXInjectivityConstraint();

		/**
		 * The meta object literal for the '<em><b>Nodes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XINJECTIVITY_CONSTRAINT__NODES = eINSTANCE.getIBeXInjectivityConstraint_Nodes();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXPatternInvocationImpl <em>IBe XPattern Invocation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXPatternInvocationImpl
		 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXCoreModelPackageImpl#getIBeXPatternInvocation()
		 * @generated
		 */
		EClass IBE_XPATTERN_INVOCATION = eINSTANCE.getIBeXPatternInvocation();

		/**
		 * The meta object literal for the '<em><b>Positive</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IBE_XPATTERN_INVOCATION__POSITIVE = eINSTANCE.getIBeXPatternInvocation_Positive();

		/**
		 * The meta object literal for the '<em><b>Invoked By</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XPATTERN_INVOCATION__INVOKED_BY = eINSTANCE.getIBeXPatternInvocation_InvokedBy();

		/**
		 * The meta object literal for the '<em><b>Invocation</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XPATTERN_INVOCATION__INVOCATION = eINSTANCE.getIBeXPatternInvocation_Invocation();

		/**
		 * The meta object literal for the '<em><b>Mapping</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XPATTERN_INVOCATION__MAPPING = eINSTANCE.getIBeXPatternInvocation_Mapping();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXNodeMappingImpl <em>IBe XNode Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXNodeMappingImpl
		 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXCoreModelPackageImpl#getIBeXNodeMapping()
		 * @generated
		 */
		EClass IBE_XNODE_MAPPING = eINSTANCE.getIBeXNodeMapping();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XNODE_MAPPING__KEY = eINSTANCE.getIBeXNodeMapping_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XNODE_MAPPING__VALUE = eINSTANCE.getIBeXNodeMapping_Value();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXEnumValueImpl <em>IBe XEnum Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXEnumValueImpl
		 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXCoreModelPackageImpl#getIBeXEnumValue()
		 * @generated
		 */
		EClass IBE_XENUM_VALUE = eINSTANCE.getIBeXEnumValue();

		/**
		 * The meta object literal for the '<em><b>Literal</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XENUM_VALUE__LITERAL = eINSTANCE.getIBeXEnumValue_Literal();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXStringValueImpl <em>IBe XString Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXStringValueImpl
		 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXCoreModelPackageImpl#getIBeXStringValue()
		 * @generated
		 */
		EClass IBE_XSTRING_VALUE = eINSTANCE.getIBeXStringValue();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IBE_XSTRING_VALUE__VALUE = eINSTANCE.getIBeXStringValue_Value();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXBooleanValueImpl <em>IBe XBoolean Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXBooleanValueImpl
		 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXCoreModelPackageImpl#getIBeXBooleanValue()
		 * @generated
		 */
		EClass IBE_XBOOLEAN_VALUE = eINSTANCE.getIBeXBooleanValue();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IBE_XBOOLEAN_VALUE__VALUE = eINSTANCE.getIBeXBooleanValue_Value();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXAttributeValueImpl <em>IBe XAttribute Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXAttributeValueImpl
		 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXCoreModelPackageImpl#getIBeXAttributeValue()
		 * @generated
		 */
		EClass IBE_XATTRIBUTE_VALUE = eINSTANCE.getIBeXAttributeValue();

		/**
		 * The meta object literal for the '<em><b>Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XATTRIBUTE_VALUE__NODE = eINSTANCE.getIBeXAttributeValue_Node();

		/**
		 * The meta object literal for the '<em><b>Attribute</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XATTRIBUTE_VALUE__ATTRIBUTE = eINSTANCE.getIBeXAttributeValue_Attribute();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXMatchCountValueImpl <em>IBe XMatch Count Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXMatchCountValueImpl
		 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXCoreModelPackageImpl#getIBeXMatchCountValue()
		 * @generated
		 */
		EClass IBE_XMATCH_COUNT_VALUE = eINSTANCE.getIBeXMatchCountValue();

		/**
		 * The meta object literal for the '<em><b>Invocation</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XMATCH_COUNT_VALUE__INVOCATION = eINSTANCE.getIBeXMatchCountValue_Invocation();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXRuleImpl <em>IBe XRule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXRuleImpl
		 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXCoreModelPackageImpl#getIBeXRule()
		 * @generated
		 */
		EClass IBE_XRULE = eINSTANCE.getIBeXRule();

		/**
		 * The meta object literal for the '<em><b>Precondition</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XRULE__PRECONDITION = eINSTANCE.getIBeXRule_Precondition();

		/**
		 * The meta object literal for the '<em><b>Postcondition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XRULE__POSTCONDITION = eINSTANCE.getIBeXRule_Postcondition();

		/**
		 * The meta object literal for the '<em><b>Creation</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XRULE__CREATION = eINSTANCE.getIBeXRule_Creation();

		/**
		 * The meta object literal for the '<em><b>Deletion</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XRULE__DELETION = eINSTANCE.getIBeXRule_Deletion();

		/**
		 * The meta object literal for the '<em><b>Attribute Assignments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XRULE__ATTRIBUTE_ASSIGNMENTS = eINSTANCE.getIBeXRule_AttributeAssignments();

		/**
		 * The meta object literal for the '<em><b>All Nodes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XRULE__ALL_NODES = eINSTANCE.getIBeXRule_AllNodes();

		/**
		 * The meta object literal for the '<em><b>All Edges</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XRULE__ALL_EDGES = eINSTANCE.getIBeXRule_AllEdges();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXRuleDeltaImpl <em>IBe XRule Delta</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXRuleDeltaImpl
		 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXCoreModelPackageImpl#getIBeXRuleDelta()
		 * @generated
		 */
		EClass IBE_XRULE_DELTA = eINSTANCE.getIBeXRuleDelta();

		/**
		 * The meta object literal for the '<em><b>Nodes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XRULE_DELTA__NODES = eINSTANCE.getIBeXRuleDelta_Nodes();

		/**
		 * The meta object literal for the '<em><b>Edges</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XRULE_DELTA__EDGES = eINSTANCE.getIBeXRuleDelta_Edges();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXAttributeAssignmentImpl <em>IBe XAttribute Assignment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXAttributeAssignmentImpl
		 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXCoreModelPackageImpl#getIBeXAttributeAssignment()
		 * @generated
		 */
		EClass IBE_XATTRIBUTE_ASSIGNMENT = eINSTANCE.getIBeXAttributeAssignment();

		/**
		 * The meta object literal for the '<em><b>Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XATTRIBUTE_ASSIGNMENT__NODE = eINSTANCE.getIBeXAttributeAssignment_Node();

		/**
		 * The meta object literal for the '<em><b>Attribute</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XATTRIBUTE_ASSIGNMENT__ATTRIBUTE = eINSTANCE.getIBeXAttributeAssignment_Attribute();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XATTRIBUTE_ASSIGNMENT__VALUE = eINSTANCE.getIBeXAttributeAssignment_Value();

	}

} //IBeXCoreModelPackage
