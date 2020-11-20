/**
 */
package org.emoflon.ibex.patternmodel.IBeXPatternModel;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

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
 * <!-- begin-model-doc -->
 * Model for IBeXPatterns.
 * <!-- end-model-doc -->
 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelFactory
 * @model kind="package"
 * @generated
 */
public interface IBeXPatternModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "IBeXPatternModel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "platform:/resource/org.emoflon.ibex.patternmodel/model/IBeXPatternModel.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "IBeXPatternModel";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	IBeXPatternModelPackage eINSTANCE = org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl
			.init();

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXModelImpl <em>IBe XModel</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXModelImpl
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXModel()
	 * @generated
	 */
	int IBE_XMODEL = 0;

	/**
	 * The feature id for the '<em><b>Pattern Set</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XMODEL__PATTERN_SET = 0;

	/**
	 * The feature id for the '<em><b>Rule Set</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XMODEL__RULE_SET = 1;

	/**
	 * The feature id for the '<em><b>Node Set</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XMODEL__NODE_SET = 2;

	/**
	 * The feature id for the '<em><b>Edge Set</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XMODEL__EDGE_SET = 3;

	/**
	 * The number of structural features of the '<em>IBe XModel</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XMODEL_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>IBe XModel</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XMODEL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternSetImpl <em>IBe XPattern Set</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternSetImpl
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXPatternSet()
	 * @generated
	 */
	int IBE_XPATTERN_SET = 1;

	/**
	 * The feature id for the '<em><b>Context Patterns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPATTERN_SET__CONTEXT_PATTERNS = 0;

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
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXRuleSetImpl <em>IBe XRule Set</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXRuleSetImpl
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXRuleSet()
	 * @generated
	 */
	int IBE_XRULE_SET = 2;

	/**
	 * The feature id for the '<em><b>Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XRULE_SET__RULES = 0;

	/**
	 * The number of structural features of the '<em>IBe XRule Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XRULE_SET_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>IBe XRule Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XRULE_SET_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXNodeSetImpl <em>IBe XNode Set</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXNodeSetImpl
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXNodeSet()
	 * @generated
	 */
	int IBE_XNODE_SET = 3;

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
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXEdgeSetImpl <em>IBe XEdge Set</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXEdgeSetImpl
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXEdgeSet()
	 * @generated
	 */
	int IBE_XEDGE_SET = 4;

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
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXNamedElementImpl <em>IBe XNamed Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXNamedElementImpl
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXNamedElement()
	 * @generated
	 */
	int IBE_XNAMED_ELEMENT = 5;

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
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternImpl <em>IBe XPattern</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternImpl
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXPattern()
	 * @generated
	 */
	int IBE_XPATTERN = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPATTERN__NAME = IBE_XNAMED_ELEMENT__NAME;

	/**
	 * The number of structural features of the '<em>IBe XPattern</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPATTERN_FEATURE_COUNT = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>IBe XPattern</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPATTERN_OPERATION_COUNT = IBE_XNAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXContextImpl <em>IBe XContext</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXContextImpl
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXContext()
	 * @generated
	 */
	int IBE_XCONTEXT = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONTEXT__NAME = IBE_XPATTERN__NAME;

	/**
	 * The feature id for the '<em><b>Api Pattern Dependencies</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONTEXT__API_PATTERN_DEPENDENCIES = IBE_XPATTERN_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>IBe XContext</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONTEXT_FEATURE_COUNT = IBE_XPATTERN_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>IBe XContext</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONTEXT_OPERATION_COUNT = IBE_XPATTERN_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXContextAlternativesImpl <em>IBe XContext Alternatives</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXContextAlternativesImpl
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXContextAlternatives()
	 * @generated
	 */
	int IBE_XCONTEXT_ALTERNATIVES = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONTEXT_ALTERNATIVES__NAME = IBE_XCONTEXT__NAME;

	/**
	 * The feature id for the '<em><b>Api Pattern Dependencies</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONTEXT_ALTERNATIVES__API_PATTERN_DEPENDENCIES = IBE_XCONTEXT__API_PATTERN_DEPENDENCIES;

	/**
	 * The feature id for the '<em><b>Alternative Patterns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONTEXT_ALTERNATIVES__ALTERNATIVE_PATTERNS = IBE_XCONTEXT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Context</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONTEXT_ALTERNATIVES__CONTEXT = IBE_XCONTEXT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>IBe XContext Alternatives</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONTEXT_ALTERNATIVES_FEATURE_COUNT = IBE_XCONTEXT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>IBe XContext Alternatives</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONTEXT_ALTERNATIVES_OPERATION_COUNT = IBE_XCONTEXT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXContextPatternImpl <em>IBe XContext Pattern</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXContextPatternImpl
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXContextPattern()
	 * @generated
	 */
	int IBE_XCONTEXT_PATTERN = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONTEXT_PATTERN__NAME = IBE_XCONTEXT__NAME;

	/**
	 * The feature id for the '<em><b>Api Pattern Dependencies</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONTEXT_PATTERN__API_PATTERN_DEPENDENCIES = IBE_XCONTEXT__API_PATTERN_DEPENDENCIES;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONTEXT_PATTERN__DOCUMENTATION = IBE_XCONTEXT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Attribute Constraint</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONTEXT_PATTERN__ATTRIBUTE_CONSTRAINT = IBE_XCONTEXT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Arithmetic Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONTEXT_PATTERN__ARITHMETIC_CONSTRAINTS = IBE_XCONTEXT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Injectivity Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONTEXT_PATTERN__INJECTIVITY_CONSTRAINTS = IBE_XCONTEXT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Invocations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONTEXT_PATTERN__INVOCATIONS = IBE_XCONTEXT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Local Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONTEXT_PATTERN__LOCAL_EDGES = IBE_XCONTEXT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Local Nodes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONTEXT_PATTERN__LOCAL_NODES = IBE_XCONTEXT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Signature Nodes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONTEXT_PATTERN__SIGNATURE_NODES = IBE_XCONTEXT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Csps</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONTEXT_PATTERN__CSPS = IBE_XCONTEXT_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONTEXT_PATTERN__PARAMETERS = IBE_XCONTEXT_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Optimized Disjoint</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONTEXT_PATTERN__OPTIMIZED_DISJOINT = IBE_XCONTEXT_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Subpattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONTEXT_PATTERN__SUBPATTERN = IBE_XCONTEXT_FEATURE_COUNT + 11;

	/**
	 * The number of structural features of the '<em>IBe XContext Pattern</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONTEXT_PATTERN_FEATURE_COUNT = IBE_XCONTEXT_FEATURE_COUNT + 12;

	/**
	 * The number of operations of the '<em>IBe XContext Pattern</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONTEXT_PATTERN_OPERATION_COUNT = IBE_XCONTEXT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXCreatePatternImpl <em>IBe XCreate Pattern</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXCreatePatternImpl
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXCreatePattern()
	 * @generated
	 */
	int IBE_XCREATE_PATTERN = 10;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCREATE_PATTERN__NAME = IBE_XPATTERN__NAME;

	/**
	 * The feature id for the '<em><b>Attribute Assignments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCREATE_PATTERN__ATTRIBUTE_ASSIGNMENTS = IBE_XPATTERN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Context Nodes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCREATE_PATTERN__CONTEXT_NODES = IBE_XPATTERN_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Created Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCREATE_PATTERN__CREATED_EDGES = IBE_XPATTERN_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Created Nodes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCREATE_PATTERN__CREATED_NODES = IBE_XPATTERN_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>IBe XCreate Pattern</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCREATE_PATTERN_FEATURE_COUNT = IBE_XPATTERN_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>IBe XCreate Pattern</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCREATE_PATTERN_OPERATION_COUNT = IBE_XPATTERN_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXDeletePatternImpl <em>IBe XDelete Pattern</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXDeletePatternImpl
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXDeletePattern()
	 * @generated
	 */
	int IBE_XDELETE_PATTERN = 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDELETE_PATTERN__NAME = IBE_XPATTERN__NAME;

	/**
	 * The feature id for the '<em><b>Context Nodes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDELETE_PATTERN__CONTEXT_NODES = IBE_XPATTERN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Deleted Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDELETE_PATTERN__DELETED_EDGES = IBE_XPATTERN_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Deleted Nodes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDELETE_PATTERN__DELETED_NODES = IBE_XPATTERN_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>IBe XDelete Pattern</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDELETE_PATTERN_FEATURE_COUNT = IBE_XPATTERN_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>IBe XDelete Pattern</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDELETE_PATTERN_OPERATION_COUNT = IBE_XPATTERN_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXNodeImpl <em>IBe XNode</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXNodeImpl
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXNode()
	 * @generated
	 */
	int IBE_XNODE = 12;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XNODE__NAME = IBE_XNAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Incoming Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XNODE__INCOMING_EDGES = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Outgoing Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XNODE__OUTGOING_EDGES = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XNODE__TYPE = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 2;

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
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXEdgeImpl <em>IBe XEdge</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXEdgeImpl
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXEdge()
	 * @generated
	 */
	int IBE_XEDGE = 13;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XEDGE__NAME = IBE_XNAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Source Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XEDGE__SOURCE_NODE = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XEDGE__TARGET_NODE = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XEDGE__TYPE = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 2;

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
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXParameterImpl <em>IBe XParameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXParameterImpl
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXParameter()
	 * @generated
	 */
	int IBE_XPARAMETER = 14;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPARAMETER__NAME = IBE_XNAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPARAMETER__TYPE = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>IBe XParameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPARAMETER_FEATURE_COUNT = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>IBe XParameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPARAMETER_OPERATION_COUNT = IBE_XNAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXAttributeImpl <em>IBe XAttribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXAttributeImpl
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXAttribute()
	 * @generated
	 */
	int IBE_XATTRIBUTE = 15;

	/**
	 * The number of structural features of the '<em>IBe XAttribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>IBe XAttribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXAttributeAssignmentImpl <em>IBe XAttribute Assignment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXAttributeAssignmentImpl
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXAttributeAssignment()
	 * @generated
	 */
	int IBE_XATTRIBUTE_ASSIGNMENT = 16;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_ASSIGNMENT__VALUE = IBE_XATTRIBUTE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_ASSIGNMENT__NODE = IBE_XATTRIBUTE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_ASSIGNMENT__TYPE = IBE_XATTRIBUTE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>IBe XAttribute Assignment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_ASSIGNMENT_FEATURE_COUNT = IBE_XATTRIBUTE_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>IBe XAttribute Assignment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_ASSIGNMENT_OPERATION_COUNT = IBE_XATTRIBUTE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXAttributeConstraintImpl <em>IBe XAttribute Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXAttributeConstraintImpl
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXAttributeConstraint()
	 * @generated
	 */
	int IBE_XATTRIBUTE_CONSTRAINT = 17;

	/**
	 * The feature id for the '<em><b>Relation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_CONSTRAINT__RELATION = IBE_XATTRIBUTE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Lhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_CONSTRAINT__LHS = IBE_XATTRIBUTE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Rhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_CONSTRAINT__RHS = IBE_XATTRIBUTE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>IBe XAttribute Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_CONSTRAINT_FEATURE_COUNT = IBE_XATTRIBUTE_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>IBe XAttribute Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_CONSTRAINT_OPERATION_COUNT = IBE_XATTRIBUTE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXAttributeValueImpl <em>IBe XAttribute Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXAttributeValueImpl
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXAttributeValue()
	 * @generated
	 */
	int IBE_XATTRIBUTE_VALUE = 18;

	/**
	 * The number of structural features of the '<em>IBe XAttribute Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_VALUE_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>IBe XAttribute Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_VALUE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXAttributeParameterImpl <em>IBe XAttribute Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXAttributeParameterImpl
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXAttributeParameter()
	 * @generated
	 */
	int IBE_XATTRIBUTE_PARAMETER = 19;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_PARAMETER__NAME = IBE_XATTRIBUTE_VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>IBe XAttribute Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_PARAMETER_FEATURE_COUNT = IBE_XATTRIBUTE_VALUE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>IBe XAttribute Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_PARAMETER_OPERATION_COUNT = IBE_XATTRIBUTE_VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXConstantImpl <em>IBe XConstant</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXConstantImpl
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXConstant()
	 * @generated
	 */
	int IBE_XCONSTANT = 20;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONSTANT__VALUE = IBE_XATTRIBUTE_VALUE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>String Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONSTANT__STRING_VALUE = IBE_XATTRIBUTE_VALUE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>IBe XConstant</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONSTANT_FEATURE_COUNT = IBE_XATTRIBUTE_VALUE_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>IBe XConstant</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONSTANT_OPERATION_COUNT = IBE_XATTRIBUTE_VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXEnumLiteralImpl <em>IBe XEnum Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXEnumLiteralImpl
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXEnumLiteral()
	 * @generated
	 */
	int IBE_XENUM_LITERAL = 21;

	/**
	 * The feature id for the '<em><b>Literal</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XENUM_LITERAL__LITERAL = IBE_XATTRIBUTE_VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>IBe XEnum Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XENUM_LITERAL_FEATURE_COUNT = IBE_XATTRIBUTE_VALUE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>IBe XEnum Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XENUM_LITERAL_OPERATION_COUNT = IBE_XATTRIBUTE_VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXAttributeExpressionImpl <em>IBe XAttribute Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXAttributeExpressionImpl
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXAttributeExpression()
	 * @generated
	 */
	int IBE_XATTRIBUTE_EXPRESSION = 22;

	/**
	 * The feature id for the '<em><b>Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_EXPRESSION__ATTRIBUTE = IBE_XATTRIBUTE_VALUE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_EXPRESSION__NODE = IBE_XATTRIBUTE_VALUE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>IBe XAttribute Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_EXPRESSION_FEATURE_COUNT = IBE_XATTRIBUTE_VALUE_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>IBe XAttribute Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_EXPRESSION_OPERATION_COUNT = IBE_XATTRIBUTE_VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXStochasticAttributeValueImpl <em>IBe XStochastic Attribute Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXStochasticAttributeValueImpl
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXStochasticAttributeValue()
	 * @generated
	 */
	int IBE_XSTOCHASTIC_ATTRIBUTE_VALUE = 23;

	/**
	 * The feature id for the '<em><b>Range</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XSTOCHASTIC_ATTRIBUTE_VALUE__RANGE = IBE_XATTRIBUTE_VALUE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Function</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XSTOCHASTIC_ATTRIBUTE_VALUE__FUNCTION = IBE_XATTRIBUTE_VALUE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>IBe XStochastic Attribute Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XSTOCHASTIC_ATTRIBUTE_VALUE_FEATURE_COUNT = IBE_XATTRIBUTE_VALUE_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>IBe XStochastic Attribute Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XSTOCHASTIC_ATTRIBUTE_VALUE_OPERATION_COUNT = IBE_XATTRIBUTE_VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXArithmeticValueImpl <em>IBe XArithmetic Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXArithmeticValueImpl
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXArithmeticValue()
	 * @generated
	 */
	int IBE_XARITHMETIC_VALUE = 24;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XARITHMETIC_VALUE__EXPRESSION = IBE_XATTRIBUTE_VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>IBe XArithmetic Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XARITHMETIC_VALUE_FEATURE_COUNT = IBE_XATTRIBUTE_VALUE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>IBe XArithmetic Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XARITHMETIC_VALUE_OPERATION_COUNT = IBE_XATTRIBUTE_VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXInjectivityConstraintImpl <em>IBe XInjectivity Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXInjectivityConstraintImpl
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXInjectivityConstraint()
	 * @generated
	 */
	int IBE_XINJECTIVITY_CONSTRAINT = 25;

	/**
	 * The feature id for the '<em><b>Values</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XINJECTIVITY_CONSTRAINT__VALUES = 0;

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
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternInvocationImpl <em>IBe XPattern Invocation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternInvocationImpl
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXPatternInvocation()
	 * @generated
	 */
	int IBE_XPATTERN_INVOCATION = 26;

	/**
	 * The feature id for the '<em><b>Positive</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPATTERN_INVOCATION__POSITIVE = 0;

	/**
	 * The feature id for the '<em><b>Invoked By</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPATTERN_INVOCATION__INVOKED_BY = 1;

	/**
	 * The feature id for the '<em><b>Invoked Pattern</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPATTERN_INVOCATION__INVOKED_PATTERN = 2;

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
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXNodeToNodeMappingImpl <em>IBe XNode To Node Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXNodeToNodeMappingImpl
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXNodeToNodeMapping()
	 * @generated
	 */
	int IBE_XNODE_TO_NODE_MAPPING = 27;

	/**
	 * The feature id for the '<em><b>Key</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XNODE_TO_NODE_MAPPING__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XNODE_TO_NODE_MAPPING__VALUE = 1;

	/**
	 * The number of structural features of the '<em>IBe XNode To Node Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XNODE_TO_NODE_MAPPING_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>IBe XNode To Node Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XNODE_TO_NODE_MAPPING_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXCSPImpl <em>IBe XCSP</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXCSPImpl
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXCSP()
	 * @generated
	 */
	int IBE_XCSP = 28;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCSP__NAME = 0;

	/**
	 * The feature id for the '<em><b>Package</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCSP__PACKAGE = 1;

	/**
	 * The feature id for the '<em><b>Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCSP__VALUES = 2;

	/**
	 * The number of structural features of the '<em>IBe XCSP</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCSP_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>IBe XCSP</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCSP_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXRuleImpl <em>IBe XRule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXRuleImpl
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXRule()
	 * @generated
	 */
	int IBE_XRULE = 29;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XRULE__NAME = IBE_XNAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XRULE__DOCUMENTATION = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Lhs</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XRULE__LHS = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Rhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XRULE__RHS = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Create</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XRULE__CREATE = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Delete</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XRULE__DELETE = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XRULE__PARAMETERS = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Arithmetic Constraints</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XRULE__ARITHMETIC_CONSTRAINTS = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Probability</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XRULE__PROBABILITY = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>IBe XRule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XRULE_FEATURE_COUNT = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 8;

	/**
	 * The number of operations of the '<em>IBe XRule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XRULE_OPERATION_COUNT = IBE_XNAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXProbabilityImpl <em>IBe XProbability</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXProbabilityImpl
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXProbability()
	 * @generated
	 */
	int IBE_XPROBABILITY = 30;

	/**
	 * The feature id for the '<em><b>Distribution</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPROBABILITY__DISTRIBUTION = 0;

	/**
	 * The feature id for the '<em><b>Parameter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPROBABILITY__PARAMETER = 1;

	/**
	 * The number of structural features of the '<em>IBe XProbability</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPROBABILITY_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>IBe XProbability</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPROBABILITY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXConstraint <em>IBe XConstraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXConstraint
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXConstraint()
	 * @generated
	 */
	int IBE_XCONSTRAINT = 39;

	/**
	 * The number of structural features of the '<em>IBe XConstraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONSTRAINT_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>IBe XConstraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONSTRAINT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXArithmeticConstraintImpl <em>IBe XArithmetic Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXArithmeticConstraintImpl
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXArithmeticConstraint()
	 * @generated
	 */
	int IBE_XARITHMETIC_CONSTRAINT = 31;

	/**
	 * The feature id for the '<em><b>Lhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XARITHMETIC_CONSTRAINT__LHS = IBE_XCONSTRAINT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Relation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XARITHMETIC_CONSTRAINT__RELATION = IBE_XCONSTRAINT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Rhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XARITHMETIC_CONSTRAINT__RHS = IBE_XCONSTRAINT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>IBe XArithmetic Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XARITHMETIC_CONSTRAINT_FEATURE_COUNT = IBE_XCONSTRAINT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>IBe XArithmetic Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XARITHMETIC_CONSTRAINT_OPERATION_COUNT = IBE_XCONSTRAINT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticExpression <em>IBe XArithmetic Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticExpression
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXArithmeticExpression()
	 * @generated
	 */
	int IBE_XARITHMETIC_EXPRESSION = 32;

	/**
	 * The number of structural features of the '<em>IBe XArithmetic Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XARITHMETIC_EXPRESSION_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>IBe XArithmetic Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XARITHMETIC_EXPRESSION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXUnaryExpressionImpl <em>IBe XUnary Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXUnaryExpressionImpl
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXUnaryExpression()
	 * @generated
	 */
	int IBE_XUNARY_EXPRESSION = 33;

	/**
	 * The feature id for the '<em><b>Operand</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XUNARY_EXPRESSION__OPERAND = IBE_XARITHMETIC_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XUNARY_EXPRESSION__OPERATOR = IBE_XARITHMETIC_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Negative</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XUNARY_EXPRESSION__NEGATIVE = IBE_XARITHMETIC_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>IBe XUnary Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XUNARY_EXPRESSION_FEATURE_COUNT = IBE_XARITHMETIC_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>IBe XUnary Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XUNARY_EXPRESSION_OPERATION_COUNT = IBE_XARITHMETIC_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXBinaryExpressionImpl <em>IBe XBinary Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXBinaryExpressionImpl
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXBinaryExpression()
	 * @generated
	 */
	int IBE_XBINARY_EXPRESSION = 34;

	/**
	 * The feature id for the '<em><b>Left</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XBINARY_EXPRESSION__LEFT = IBE_XARITHMETIC_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Right</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XBINARY_EXPRESSION__RIGHT = IBE_XARITHMETIC_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XBINARY_EXPRESSION__OPERATOR = IBE_XARITHMETIC_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>IBe XBinary Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XBINARY_EXPRESSION_FEATURE_COUNT = IBE_XARITHMETIC_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>IBe XBinary Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XBINARY_EXPRESSION_OPERATION_COUNT = IBE_XARITHMETIC_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXArithmeticValueLiteralImpl <em>IBe XArithmetic Value Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXArithmeticValueLiteralImpl
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXArithmeticValueLiteral()
	 * @generated
	 */
	int IBE_XARITHMETIC_VALUE_LITERAL = 35;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XARITHMETIC_VALUE_LITERAL__VALUE = IBE_XARITHMETIC_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>IBe XArithmetic Value Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XARITHMETIC_VALUE_LITERAL_FEATURE_COUNT = IBE_XARITHMETIC_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>IBe XArithmetic Value Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XARITHMETIC_VALUE_LITERAL_OPERATION_COUNT = IBE_XARITHMETIC_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXProbabilityDistributionImpl <em>IBe XProbability Distribution</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXProbabilityDistributionImpl
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXProbabilityDistribution()
	 * @generated
	 */
	int IBE_XPROBABILITY_DISTRIBUTION = 36;

	/**
	 * The feature id for the '<em><b>Mean</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPROBABILITY_DISTRIBUTION__MEAN = 0;

	/**
	 * The feature id for the '<em><b>Stddev</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPROBABILITY_DISTRIBUTION__STDDEV = 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPROBABILITY_DISTRIBUTION__TYPE = 2;

	/**
	 * The number of structural features of the '<em>IBe XProbability Distribution</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPROBABILITY_DISTRIBUTION_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>IBe XProbability Distribution</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPROBABILITY_DISTRIBUTION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXArithmeticAttributeImpl <em>IBe XArithmetic Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXArithmeticAttributeImpl
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXArithmeticAttribute()
	 * @generated
	 */
	int IBE_XARITHMETIC_ATTRIBUTE = 37;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XARITHMETIC_ATTRIBUTE__NAME = IBE_XNODE__NAME;

	/**
	 * The feature id for the '<em><b>Incoming Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XARITHMETIC_ATTRIBUTE__INCOMING_EDGES = IBE_XNODE__INCOMING_EDGES;

	/**
	 * The feature id for the '<em><b>Outgoing Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XARITHMETIC_ATTRIBUTE__OUTGOING_EDGES = IBE_XNODE__OUTGOING_EDGES;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XARITHMETIC_ATTRIBUTE__TYPE = IBE_XNODE__TYPE;

	/**
	 * The feature id for the '<em><b>Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XARITHMETIC_ATTRIBUTE__ATTRIBUTE = IBE_XNODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Negative</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XARITHMETIC_ATTRIBUTE__NEGATIVE = IBE_XNODE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>IBe XArithmetic Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XARITHMETIC_ATTRIBUTE_FEATURE_COUNT = IBE_XNODE_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>IBe XArithmetic Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XARITHMETIC_ATTRIBUTE_OPERATION_COUNT = IBE_XNODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXMatchCountImpl <em>IBe XMatch Count</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXMatchCountImpl
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXMatchCount()
	 * @generated
	 */
	int IBE_XMATCH_COUNT = 38;

	/**
	 * The feature id for the '<em><b>Operand</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XMATCH_COUNT__OPERAND = IBE_XUNARY_EXPRESSION__OPERAND;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XMATCH_COUNT__OPERATOR = IBE_XUNARY_EXPRESSION__OPERATOR;

	/**
	 * The feature id for the '<em><b>Negative</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XMATCH_COUNT__NEGATIVE = IBE_XUNARY_EXPRESSION__NEGATIVE;

	/**
	 * The feature id for the '<em><b>Invocation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XMATCH_COUNT__INVOCATION = IBE_XUNARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>IBe XMatch Count</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XMATCH_COUNT_FEATURE_COUNT = IBE_XUNARY_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>IBe XMatch Count</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XMATCH_COUNT_OPERATION_COUNT = IBE_XUNARY_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXDisjunctContextPatternImpl <em>IBe XDisjunct Context Pattern</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXDisjunctContextPatternImpl
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXDisjunctContextPattern()
	 * @generated
	 */
	int IBE_XDISJUNCT_CONTEXT_PATTERN = 40;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDISJUNCT_CONTEXT_PATTERN__NAME = IBE_XCONTEXT__NAME;

	/**
	 * The feature id for the '<em><b>Api Pattern Dependencies</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDISJUNCT_CONTEXT_PATTERN__API_PATTERN_DEPENDENCIES = IBE_XCONTEXT__API_PATTERN_DEPENDENCIES;

	/**
	 * The feature id for the '<em><b>Subpatterns</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDISJUNCT_CONTEXT_PATTERN__SUBPATTERNS = IBE_XCONTEXT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Injectivity Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDISJUNCT_CONTEXT_PATTERN__INJECTIVITY_CONSTRAINTS = IBE_XCONTEXT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Attributes Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDISJUNCT_CONTEXT_PATTERN__ATTRIBUTES_CONSTRAINTS = IBE_XCONTEXT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Non Optimized Pattern</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDISJUNCT_CONTEXT_PATTERN__NON_OPTIMIZED_PATTERN = IBE_XCONTEXT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>IBe XDisjunct Context Pattern</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDISJUNCT_CONTEXT_PATTERN_FEATURE_COUNT = IBE_XCONTEXT_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>IBe XDisjunct Context Pattern</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDISJUNCT_CONTEXT_PATTERN_OPERATION_COUNT = IBE_XCONTEXT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXDisjunctAttributeImpl <em>IBe XDisjunct Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXDisjunctAttributeImpl
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXDisjunctAttribute()
	 * @generated
	 */
	int IBE_XDISJUNCT_ATTRIBUTE = 41;

	/**
	 * The feature id for the '<em><b>Target Pattern</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDISJUNCT_ATTRIBUTE__TARGET_PATTERN = 0;

	/**
	 * The feature id for the '<em><b>Source Pattern</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDISJUNCT_ATTRIBUTE__SOURCE_PATTERN = 1;

	/**
	 * The feature id for the '<em><b>Disjunct Attribute</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDISJUNCT_ATTRIBUTE__DISJUNCT_ATTRIBUTE = 2;

	/**
	 * The number of structural features of the '<em>IBe XDisjunct Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDISJUNCT_ATTRIBUTE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>IBe XDisjunct Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDISJUNCT_ATTRIBUTE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXDependentInjectivityConstraintsImpl <em>IBe XDependent Injectivity Constraints</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXDependentInjectivityConstraintsImpl
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXDependentInjectivityConstraints()
	 * @generated
	 */
	int IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS = 42;

	/**
	 * The feature id for the '<em><b>Injectivity Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS__INJECTIVITY_CONSTRAINTS = 0;

	/**
	 * The feature id for the '<em><b>Patterns</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS__PATTERNS = 1;

	/**
	 * The feature id for the '<em><b>Attribute Constraints</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS__ATTRIBUTE_CONSTRAINTS = 2;

	/**
	 * The number of structural features of the '<em>IBe XDependent Injectivity Constraints</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>IBe XDependent Injectivity Constraints</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBexDisjunctInjectivityConstraintImpl <em>IBex Disjunct Injectivity Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBexDisjunctInjectivityConstraintImpl
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBexDisjunctInjectivityConstraint()
	 * @generated
	 */
	int IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT = 43;

	/**
	 * The feature id for the '<em><b>Pattern1</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT__PATTERN1 = 0;

	/**
	 * The feature id for the '<em><b>Pattern2</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT__PATTERN2 = 1;

	/**
	 * The feature id for the '<em><b>Node1</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT__NODE1 = 2;

	/**
	 * The feature id for the '<em><b>Node2</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT__NODE2 = 3;

	/**
	 * The number of structural features of the '<em>IBex Disjunct Injectivity Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>IBex Disjunct Injectivity Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXDependentDisjunctAttributeImpl <em>IBe XDependent Disjunct Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXDependentDisjunctAttributeImpl
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXDependentDisjunctAttribute()
	 * @generated
	 */
	int IBE_XDEPENDENT_DISJUNCT_ATTRIBUTE = 44;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDEPENDENT_DISJUNCT_ATTRIBUTE__ATTRIBUTES = 0;

	/**
	 * The feature id for the '<em><b>Dependent Patterns</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDEPENDENT_DISJUNCT_ATTRIBUTE__DEPENDENT_PATTERNS = 1;

	/**
	 * The feature id for the '<em><b>Injectivity Constraints</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDEPENDENT_DISJUNCT_ATTRIBUTE__INJECTIVITY_CONSTRAINTS = 2;

	/**
	 * The number of structural features of the '<em>IBe XDependent Disjunct Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDEPENDENT_DISJUNCT_ATTRIBUTE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>IBe XDependent Disjunct Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDEPENDENT_DISJUNCT_ATTRIBUTE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRelation <em>IBe XRelation</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRelation
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXRelation()
	 * @generated
	 */
	int IBE_XRELATION = 45;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXBinaryOperator <em>IBe XBinary Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXBinaryOperator
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXBinaryOperator()
	 * @generated
	 */
	int IBE_XBINARY_OPERATOR = 46;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXUnaryOperator <em>IBe XUnary Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXUnaryOperator
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXUnaryOperator()
	 * @generated
	 */
	int IBE_XUNARY_OPERATOR = 47;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDistributionType <em>IBe XDistribution Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDistributionType
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXDistributionType()
	 * @generated
	 */
	int IBE_XDISTRIBUTION_TYPE = 48;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDistributionRange <em>IBe XDistribution Range</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDistributionRange
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXDistributionRange()
	 * @generated
	 */
	int IBE_XDISTRIBUTION_RANGE = 49;

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXModel <em>IBe XModel</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XModel</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXModel
	 * @generated
	 */
	EClass getIBeXModel();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXModel#getPatternSet <em>Pattern Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pattern Set</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXModel#getPatternSet()
	 * @see #getIBeXModel()
	 * @generated
	 */
	EReference getIBeXModel_PatternSet();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXModel#getRuleSet <em>Rule Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rule Set</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXModel#getRuleSet()
	 * @see #getIBeXModel()
	 * @generated
	 */
	EReference getIBeXModel_RuleSet();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXModel#getNodeSet <em>Node Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Node Set</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXModel#getNodeSet()
	 * @see #getIBeXModel()
	 * @generated
	 */
	EReference getIBeXModel_NodeSet();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXModel#getEdgeSet <em>Edge Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Edge Set</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXModel#getEdgeSet()
	 * @see #getIBeXModel()
	 * @generated
	 */
	EReference getIBeXModel_EdgeSet();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternSet <em>IBe XPattern Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XPattern Set</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternSet
	 * @generated
	 */
	EClass getIBeXPatternSet();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternSet#getContextPatterns <em>Context Patterns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Context Patterns</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternSet#getContextPatterns()
	 * @see #getIBeXPatternSet()
	 * @generated
	 */
	EReference getIBeXPatternSet_ContextPatterns();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRuleSet <em>IBe XRule Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XRule Set</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRuleSet
	 * @generated
	 */
	EClass getIBeXRuleSet();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRuleSet#getRules <em>Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Rules</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRuleSet#getRules()
	 * @see #getIBeXRuleSet()
	 * @generated
	 */
	EReference getIBeXRuleSet_Rules();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNodeSet <em>IBe XNode Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XNode Set</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNodeSet
	 * @generated
	 */
	EClass getIBeXNodeSet();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNodeSet#getNodes <em>Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Nodes</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNodeSet#getNodes()
	 * @see #getIBeXNodeSet()
	 * @generated
	 */
	EReference getIBeXNodeSet_Nodes();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEdgeSet <em>IBe XEdge Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XEdge Set</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEdgeSet
	 * @generated
	 */
	EClass getIBeXEdgeSet();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEdgeSet#getEdges <em>Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Edges</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEdgeSet#getEdges()
	 * @see #getIBeXEdgeSet()
	 * @generated
	 */
	EReference getIBeXEdgeSet_Edges();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNamedElement <em>IBe XNamed Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XNamed Element</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNamedElement
	 * @generated
	 */
	EClass getIBeXNamedElement();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNamedElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNamedElement#getName()
	 * @see #getIBeXNamedElement()
	 * @generated
	 */
	EAttribute getIBeXNamedElement_Name();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPattern <em>IBe XPattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XPattern</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPattern
	 * @generated
	 */
	EClass getIBeXPattern();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext <em>IBe XContext</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XContext</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext
	 * @generated
	 */
	EClass getIBeXContext();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext#getApiPatternDependencies <em>Api Pattern Dependencies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Api Pattern Dependencies</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext#getApiPatternDependencies()
	 * @see #getIBeXContext()
	 * @generated
	 */
	EReference getIBeXContext_ApiPatternDependencies();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextAlternatives <em>IBe XContext Alternatives</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XContext Alternatives</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextAlternatives
	 * @generated
	 */
	EClass getIBeXContextAlternatives();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextAlternatives#getAlternativePatterns <em>Alternative Patterns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Alternative Patterns</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextAlternatives#getAlternativePatterns()
	 * @see #getIBeXContextAlternatives()
	 * @generated
	 */
	EReference getIBeXContextAlternatives_AlternativePatterns();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextAlternatives#getContext <em>Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Context</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextAlternatives#getContext()
	 * @see #getIBeXContextAlternatives()
	 * @generated
	 */
	EReference getIBeXContextAlternatives_Context();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern <em>IBe XContext Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XContext Pattern</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern
	 * @generated
	 */
	EClass getIBeXContextPattern();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern#getDocumentation <em>Documentation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Documentation</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern#getDocumentation()
	 * @see #getIBeXContextPattern()
	 * @generated
	 */
	EAttribute getIBeXContextPattern_Documentation();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern#getAttributeConstraint <em>Attribute Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attribute Constraint</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern#getAttributeConstraint()
	 * @see #getIBeXContextPattern()
	 * @generated
	 */
	EReference getIBeXContextPattern_AttributeConstraint();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern#getArithmeticConstraints <em>Arithmetic Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Arithmetic Constraints</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern#getArithmeticConstraints()
	 * @see #getIBeXContextPattern()
	 * @generated
	 */
	EReference getIBeXContextPattern_ArithmeticConstraints();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern#getInjectivityConstraints <em>Injectivity Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Injectivity Constraints</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern#getInjectivityConstraints()
	 * @see #getIBeXContextPattern()
	 * @generated
	 */
	EReference getIBeXContextPattern_InjectivityConstraints();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern#getInvocations <em>Invocations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Invocations</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern#getInvocations()
	 * @see #getIBeXContextPattern()
	 * @generated
	 */
	EReference getIBeXContextPattern_Invocations();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern#getLocalEdges <em>Local Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Local Edges</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern#getLocalEdges()
	 * @see #getIBeXContextPattern()
	 * @generated
	 */
	EReference getIBeXContextPattern_LocalEdges();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern#getLocalNodes <em>Local Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Local Nodes</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern#getLocalNodes()
	 * @see #getIBeXContextPattern()
	 * @generated
	 */
	EReference getIBeXContextPattern_LocalNodes();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern#getSignatureNodes <em>Signature Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Signature Nodes</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern#getSignatureNodes()
	 * @see #getIBeXContextPattern()
	 * @generated
	 */
	EReference getIBeXContextPattern_SignatureNodes();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern#getCsps <em>Csps</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Csps</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern#getCsps()
	 * @see #getIBeXContextPattern()
	 * @generated
	 */
	EReference getIBeXContextPattern_Csps();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern#getParameters()
	 * @see #getIBeXContextPattern()
	 * @generated
	 */
	EReference getIBeXContextPattern_Parameters();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern#isOptimizedDisjoint <em>Optimized Disjoint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Optimized Disjoint</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern#isOptimizedDisjoint()
	 * @see #getIBeXContextPattern()
	 * @generated
	 */
	EAttribute getIBeXContextPattern_OptimizedDisjoint();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern#isSubpattern <em>Subpattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Subpattern</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern#isSubpattern()
	 * @see #getIBeXContextPattern()
	 * @generated
	 */
	EAttribute getIBeXContextPattern_Subpattern();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCreatePattern <em>IBe XCreate Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XCreate Pattern</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCreatePattern
	 * @generated
	 */
	EClass getIBeXCreatePattern();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCreatePattern#getAttributeAssignments <em>Attribute Assignments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attribute Assignments</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCreatePattern#getAttributeAssignments()
	 * @see #getIBeXCreatePattern()
	 * @generated
	 */
	EReference getIBeXCreatePattern_AttributeAssignments();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCreatePattern#getContextNodes <em>Context Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Context Nodes</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCreatePattern#getContextNodes()
	 * @see #getIBeXCreatePattern()
	 * @generated
	 */
	EReference getIBeXCreatePattern_ContextNodes();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCreatePattern#getCreatedEdges <em>Created Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Created Edges</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCreatePattern#getCreatedEdges()
	 * @see #getIBeXCreatePattern()
	 * @generated
	 */
	EReference getIBeXCreatePattern_CreatedEdges();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCreatePattern#getCreatedNodes <em>Created Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Created Nodes</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCreatePattern#getCreatedNodes()
	 * @see #getIBeXCreatePattern()
	 * @generated
	 */
	EReference getIBeXCreatePattern_CreatedNodes();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDeletePattern <em>IBe XDelete Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XDelete Pattern</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDeletePattern
	 * @generated
	 */
	EClass getIBeXDeletePattern();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDeletePattern#getContextNodes <em>Context Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Context Nodes</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDeletePattern#getContextNodes()
	 * @see #getIBeXDeletePattern()
	 * @generated
	 */
	EReference getIBeXDeletePattern_ContextNodes();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDeletePattern#getDeletedEdges <em>Deleted Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Deleted Edges</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDeletePattern#getDeletedEdges()
	 * @see #getIBeXDeletePattern()
	 * @generated
	 */
	EReference getIBeXDeletePattern_DeletedEdges();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDeletePattern#getDeletedNodes <em>Deleted Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Deleted Nodes</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDeletePattern#getDeletedNodes()
	 * @see #getIBeXDeletePattern()
	 * @generated
	 */
	EReference getIBeXDeletePattern_DeletedNodes();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode <em>IBe XNode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XNode</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode
	 * @generated
	 */
	EClass getIBeXNode();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode#getIncomingEdges <em>Incoming Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Incoming Edges</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode#getIncomingEdges()
	 * @see #getIBeXNode()
	 * @generated
	 */
	EReference getIBeXNode_IncomingEdges();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode#getOutgoingEdges <em>Outgoing Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Outgoing Edges</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode#getOutgoingEdges()
	 * @see #getIBeXNode()
	 * @generated
	 */
	EReference getIBeXNode_OutgoingEdges();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode#getType()
	 * @see #getIBeXNode()
	 * @generated
	 */
	EReference getIBeXNode_Type();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEdge <em>IBe XEdge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XEdge</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEdge
	 * @generated
	 */
	EClass getIBeXEdge();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEdge#getSourceNode <em>Source Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source Node</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEdge#getSourceNode()
	 * @see #getIBeXEdge()
	 * @generated
	 */
	EReference getIBeXEdge_SourceNode();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEdge#getTargetNode <em>Target Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target Node</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEdge#getTargetNode()
	 * @see #getIBeXEdge()
	 * @generated
	 */
	EReference getIBeXEdge_TargetNode();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEdge#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEdge#getType()
	 * @see #getIBeXEdge()
	 * @generated
	 */
	EReference getIBeXEdge_Type();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXParameter <em>IBe XParameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XParameter</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXParameter
	 * @generated
	 */
	EClass getIBeXParameter();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXParameter#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXParameter#getType()
	 * @see #getIBeXParameter()
	 * @generated
	 */
	EReference getIBeXParameter_Type();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttribute <em>IBe XAttribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XAttribute</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttribute
	 * @generated
	 */
	EClass getIBeXAttribute();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeAssignment <em>IBe XAttribute Assignment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XAttribute Assignment</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeAssignment
	 * @generated
	 */
	EClass getIBeXAttributeAssignment();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeAssignment#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeAssignment#getValue()
	 * @see #getIBeXAttributeAssignment()
	 * @generated
	 */
	EReference getIBeXAttributeAssignment_Value();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeAssignment#getNode <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Node</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeAssignment#getNode()
	 * @see #getIBeXAttributeAssignment()
	 * @generated
	 */
	EReference getIBeXAttributeAssignment_Node();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeAssignment#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeAssignment#getType()
	 * @see #getIBeXAttributeAssignment()
	 * @generated
	 */
	EReference getIBeXAttributeAssignment_Type();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeConstraint <em>IBe XAttribute Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XAttribute Constraint</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeConstraint
	 * @generated
	 */
	EClass getIBeXAttributeConstraint();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeConstraint#getRelation <em>Relation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Relation</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeConstraint#getRelation()
	 * @see #getIBeXAttributeConstraint()
	 * @generated
	 */
	EAttribute getIBeXAttributeConstraint_Relation();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeConstraint#getLhs <em>Lhs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Lhs</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeConstraint#getLhs()
	 * @see #getIBeXAttributeConstraint()
	 * @generated
	 */
	EReference getIBeXAttributeConstraint_Lhs();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeConstraint#getRhs <em>Rhs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rhs</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeConstraint#getRhs()
	 * @see #getIBeXAttributeConstraint()
	 * @generated
	 */
	EReference getIBeXAttributeConstraint_Rhs();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeValue <em>IBe XAttribute Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XAttribute Value</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeValue
	 * @generated
	 */
	EClass getIBeXAttributeValue();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeParameter <em>IBe XAttribute Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XAttribute Parameter</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeParameter
	 * @generated
	 */
	EClass getIBeXAttributeParameter();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXConstant <em>IBe XConstant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XConstant</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXConstant
	 * @generated
	 */
	EClass getIBeXConstant();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXConstant#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXConstant#getValue()
	 * @see #getIBeXConstant()
	 * @generated
	 */
	EAttribute getIBeXConstant_Value();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXConstant#getStringValue <em>String Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>String Value</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXConstant#getStringValue()
	 * @see #getIBeXConstant()
	 * @generated
	 */
	EAttribute getIBeXConstant_StringValue();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEnumLiteral <em>IBe XEnum Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XEnum Literal</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEnumLiteral
	 * @generated
	 */
	EClass getIBeXEnumLiteral();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEnumLiteral#getLiteral <em>Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Literal</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEnumLiteral#getLiteral()
	 * @see #getIBeXEnumLiteral()
	 * @generated
	 */
	EReference getIBeXEnumLiteral_Literal();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeExpression <em>IBe XAttribute Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XAttribute Expression</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeExpression
	 * @generated
	 */
	EClass getIBeXAttributeExpression();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeExpression#getAttribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Attribute</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeExpression#getAttribute()
	 * @see #getIBeXAttributeExpression()
	 * @generated
	 */
	EReference getIBeXAttributeExpression_Attribute();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeExpression#getNode <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Node</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeExpression#getNode()
	 * @see #getIBeXAttributeExpression()
	 * @generated
	 */
	EReference getIBeXAttributeExpression_Node();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXStochasticAttributeValue <em>IBe XStochastic Attribute Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XStochastic Attribute Value</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXStochasticAttributeValue
	 * @generated
	 */
	EClass getIBeXStochasticAttributeValue();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXStochasticAttributeValue#getRange <em>Range</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Range</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXStochasticAttributeValue#getRange()
	 * @see #getIBeXStochasticAttributeValue()
	 * @generated
	 */
	EAttribute getIBeXStochasticAttributeValue_Range();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXStochasticAttributeValue#getFunction <em>Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Function</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXStochasticAttributeValue#getFunction()
	 * @see #getIBeXStochasticAttributeValue()
	 * @generated
	 */
	EReference getIBeXStochasticAttributeValue_Function();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticValue <em>IBe XArithmetic Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XArithmetic Value</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticValue
	 * @generated
	 */
	EClass getIBeXArithmeticValue();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticValue#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticValue#getExpression()
	 * @see #getIBeXArithmeticValue()
	 * @generated
	 */
	EReference getIBeXArithmeticValue_Expression();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXInjectivityConstraint <em>IBe XInjectivity Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XInjectivity Constraint</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXInjectivityConstraint
	 * @generated
	 */
	EClass getIBeXInjectivityConstraint();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXInjectivityConstraint#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Values</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXInjectivityConstraint#getValues()
	 * @see #getIBeXInjectivityConstraint()
	 * @generated
	 */
	EReference getIBeXInjectivityConstraint_Values();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternInvocation <em>IBe XPattern Invocation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XPattern Invocation</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternInvocation
	 * @generated
	 */
	EClass getIBeXPatternInvocation();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternInvocation#isPositive <em>Positive</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Positive</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternInvocation#isPositive()
	 * @see #getIBeXPatternInvocation()
	 * @generated
	 */
	EAttribute getIBeXPatternInvocation_Positive();

	/**
	 * Returns the meta object for the container reference '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternInvocation#getInvokedBy <em>Invoked By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Invoked By</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternInvocation#getInvokedBy()
	 * @see #getIBeXPatternInvocation()
	 * @generated
	 */
	EReference getIBeXPatternInvocation_InvokedBy();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternInvocation#getInvokedPattern <em>Invoked Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Invoked Pattern</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternInvocation#getInvokedPattern()
	 * @see #getIBeXPatternInvocation()
	 * @generated
	 */
	EReference getIBeXPatternInvocation_InvokedPattern();

	/**
	 * Returns the meta object for the map '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternInvocation#getMapping <em>Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Mapping</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternInvocation#getMapping()
	 * @see #getIBeXPatternInvocation()
	 * @generated
	 */
	EReference getIBeXPatternInvocation_Mapping();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>IBe XNode To Node Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XNode To Node Mapping</em>'.
	 * @see java.util.Map.Entry
	 * @model keyType="org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode"
	 *        valueType="org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode"
	 * @generated
	 */
	EClass getIBeXNodeToNodeMapping();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getIBeXNodeToNodeMapping()
	 * @generated
	 */
	EReference getIBeXNodeToNodeMapping_Key();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getIBeXNodeToNodeMapping()
	 * @generated
	 */
	EReference getIBeXNodeToNodeMapping_Value();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCSP <em>IBe XCSP</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XCSP</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCSP
	 * @generated
	 */
	EClass getIBeXCSP();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCSP#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCSP#getName()
	 * @see #getIBeXCSP()
	 * @generated
	 */
	EAttribute getIBeXCSP_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCSP#getPackage <em>Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Package</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCSP#getPackage()
	 * @see #getIBeXCSP()
	 * @generated
	 */
	EAttribute getIBeXCSP_Package();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCSP#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Values</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCSP#getValues()
	 * @see #getIBeXCSP()
	 * @generated
	 */
	EReference getIBeXCSP_Values();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule <em>IBe XRule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XRule</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule
	 * @generated
	 */
	EClass getIBeXRule();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule#getDocumentation <em>Documentation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Documentation</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule#getDocumentation()
	 * @see #getIBeXRule()
	 * @generated
	 */
	EAttribute getIBeXRule_Documentation();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule#getLhs <em>Lhs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Lhs</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule#getLhs()
	 * @see #getIBeXRule()
	 * @generated
	 */
	EReference getIBeXRule_Lhs();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule#getRhs <em>Rhs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rhs</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule#getRhs()
	 * @see #getIBeXRule()
	 * @generated
	 */
	EReference getIBeXRule_Rhs();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule#getCreate <em>Create</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Create</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule#getCreate()
	 * @see #getIBeXRule()
	 * @generated
	 */
	EReference getIBeXRule_Create();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule#getDelete <em>Delete</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Delete</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule#getDelete()
	 * @see #getIBeXRule()
	 * @generated
	 */
	EReference getIBeXRule_Delete();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Parameters</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule#getParameters()
	 * @see #getIBeXRule()
	 * @generated
	 */
	EReference getIBeXRule_Parameters();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule#getArithmeticConstraints <em>Arithmetic Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Arithmetic Constraints</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule#getArithmeticConstraints()
	 * @see #getIBeXRule()
	 * @generated
	 */
	EReference getIBeXRule_ArithmeticConstraints();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule#getProbability <em>Probability</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Probability</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule#getProbability()
	 * @see #getIBeXRule()
	 * @generated
	 */
	EReference getIBeXRule_Probability();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXProbability <em>IBe XProbability</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XProbability</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXProbability
	 * @generated
	 */
	EClass getIBeXProbability();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXProbability#getDistribution <em>Distribution</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Distribution</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXProbability#getDistribution()
	 * @see #getIBeXProbability()
	 * @generated
	 */
	EReference getIBeXProbability_Distribution();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXProbability#getParameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Parameter</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXProbability#getParameter()
	 * @see #getIBeXProbability()
	 * @generated
	 */
	EReference getIBeXProbability_Parameter();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticConstraint <em>IBe XArithmetic Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XArithmetic Constraint</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticConstraint
	 * @generated
	 */
	EClass getIBeXArithmeticConstraint();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticConstraint#getLhs <em>Lhs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Lhs</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticConstraint#getLhs()
	 * @see #getIBeXArithmeticConstraint()
	 * @generated
	 */
	EReference getIBeXArithmeticConstraint_Lhs();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticConstraint#getRelation <em>Relation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Relation</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticConstraint#getRelation()
	 * @see #getIBeXArithmeticConstraint()
	 * @generated
	 */
	EAttribute getIBeXArithmeticConstraint_Relation();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticConstraint#getRhs <em>Rhs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rhs</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticConstraint#getRhs()
	 * @see #getIBeXArithmeticConstraint()
	 * @generated
	 */
	EReference getIBeXArithmeticConstraint_Rhs();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticExpression <em>IBe XArithmetic Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XArithmetic Expression</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticExpression
	 * @generated
	 */
	EClass getIBeXArithmeticExpression();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXUnaryExpression <em>IBe XUnary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XUnary Expression</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXUnaryExpression
	 * @generated
	 */
	EClass getIBeXUnaryExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXUnaryExpression#getOperand <em>Operand</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Operand</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXUnaryExpression#getOperand()
	 * @see #getIBeXUnaryExpression()
	 * @generated
	 */
	EReference getIBeXUnaryExpression_Operand();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXUnaryExpression#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXUnaryExpression#getOperator()
	 * @see #getIBeXUnaryExpression()
	 * @generated
	 */
	EAttribute getIBeXUnaryExpression_Operator();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXUnaryExpression#isNegative <em>Negative</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Negative</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXUnaryExpression#isNegative()
	 * @see #getIBeXUnaryExpression()
	 * @generated
	 */
	EAttribute getIBeXUnaryExpression_Negative();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXBinaryExpression <em>IBe XBinary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XBinary Expression</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXBinaryExpression
	 * @generated
	 */
	EClass getIBeXBinaryExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXBinaryExpression#getLeft <em>Left</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Left</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXBinaryExpression#getLeft()
	 * @see #getIBeXBinaryExpression()
	 * @generated
	 */
	EReference getIBeXBinaryExpression_Left();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXBinaryExpression#getRight <em>Right</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Right</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXBinaryExpression#getRight()
	 * @see #getIBeXBinaryExpression()
	 * @generated
	 */
	EReference getIBeXBinaryExpression_Right();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXBinaryExpression#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXBinaryExpression#getOperator()
	 * @see #getIBeXBinaryExpression()
	 * @generated
	 */
	EAttribute getIBeXBinaryExpression_Operator();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticValueLiteral <em>IBe XArithmetic Value Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XArithmetic Value Literal</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticValueLiteral
	 * @generated
	 */
	EClass getIBeXArithmeticValueLiteral();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticValueLiteral#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticValueLiteral#getValue()
	 * @see #getIBeXArithmeticValueLiteral()
	 * @generated
	 */
	EAttribute getIBeXArithmeticValueLiteral_Value();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXProbabilityDistribution <em>IBe XProbability Distribution</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XProbability Distribution</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXProbabilityDistribution
	 * @generated
	 */
	EClass getIBeXProbabilityDistribution();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXProbabilityDistribution#getMean <em>Mean</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Mean</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXProbabilityDistribution#getMean()
	 * @see #getIBeXProbabilityDistribution()
	 * @generated
	 */
	EReference getIBeXProbabilityDistribution_Mean();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXProbabilityDistribution#getStddev <em>Stddev</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Stddev</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXProbabilityDistribution#getStddev()
	 * @see #getIBeXProbabilityDistribution()
	 * @generated
	 */
	EReference getIBeXProbabilityDistribution_Stddev();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXProbabilityDistribution#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXProbabilityDistribution#getType()
	 * @see #getIBeXProbabilityDistribution()
	 * @generated
	 */
	EAttribute getIBeXProbabilityDistribution_Type();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticAttribute <em>IBe XArithmetic Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XArithmetic Attribute</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticAttribute
	 * @generated
	 */
	EClass getIBeXArithmeticAttribute();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticAttribute#getAttribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Attribute</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticAttribute#getAttribute()
	 * @see #getIBeXArithmeticAttribute()
	 * @generated
	 */
	EReference getIBeXArithmeticAttribute_Attribute();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticAttribute#isNegative <em>Negative</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Negative</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticAttribute#isNegative()
	 * @see #getIBeXArithmeticAttribute()
	 * @generated
	 */
	EAttribute getIBeXArithmeticAttribute_Negative();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXMatchCount <em>IBe XMatch Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XMatch Count</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXMatchCount
	 * @generated
	 */
	EClass getIBeXMatchCount();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXMatchCount#getInvocation <em>Invocation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Invocation</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXMatchCount#getInvocation()
	 * @see #getIBeXMatchCount()
	 * @generated
	 */
	EReference getIBeXMatchCount_Invocation();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXConstraint <em>IBe XConstraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XConstraint</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXConstraint
	 * @generated
	 */
	EClass getIBeXConstraint();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjunctContextPattern <em>IBe XDisjunct Context Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XDisjunct Context Pattern</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjunctContextPattern
	 * @generated
	 */
	EClass getIBeXDisjunctContextPattern();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjunctContextPattern#getSubpatterns <em>Subpatterns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Subpatterns</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjunctContextPattern#getSubpatterns()
	 * @see #getIBeXDisjunctContextPattern()
	 * @generated
	 */
	EReference getIBeXDisjunctContextPattern_Subpatterns();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjunctContextPattern#getInjectivityConstraints <em>Injectivity Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Injectivity Constraints</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjunctContextPattern#getInjectivityConstraints()
	 * @see #getIBeXDisjunctContextPattern()
	 * @generated
	 */
	EReference getIBeXDisjunctContextPattern_InjectivityConstraints();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjunctContextPattern#getAttributesConstraints <em>Attributes Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attributes Constraints</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjunctContextPattern#getAttributesConstraints()
	 * @see #getIBeXDisjunctContextPattern()
	 * @generated
	 */
	EReference getIBeXDisjunctContextPattern_AttributesConstraints();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjunctContextPattern#getNonOptimizedPattern <em>Non Optimized Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Non Optimized Pattern</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjunctContextPattern#getNonOptimizedPattern()
	 * @see #getIBeXDisjunctContextPattern()
	 * @generated
	 */
	EReference getIBeXDisjunctContextPattern_NonOptimizedPattern();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjunctAttribute <em>IBe XDisjunct Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XDisjunct Attribute</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjunctAttribute
	 * @generated
	 */
	EClass getIBeXDisjunctAttribute();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjunctAttribute#getTargetPattern <em>Target Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Target Pattern</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjunctAttribute#getTargetPattern()
	 * @see #getIBeXDisjunctAttribute()
	 * @generated
	 */
	EReference getIBeXDisjunctAttribute_TargetPattern();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjunctAttribute#getSourcePattern <em>Source Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Source Pattern</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjunctAttribute#getSourcePattern()
	 * @see #getIBeXDisjunctAttribute()
	 * @generated
	 */
	EReference getIBeXDisjunctAttribute_SourcePattern();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjunctAttribute#getDisjunctAttribute <em>Disjunct Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Disjunct Attribute</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjunctAttribute#getDisjunctAttribute()
	 * @see #getIBeXDisjunctAttribute()
	 * @generated
	 */
	EReference getIBeXDisjunctAttribute_DisjunctAttribute();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDependentInjectivityConstraints <em>IBe XDependent Injectivity Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XDependent Injectivity Constraints</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDependentInjectivityConstraints
	 * @generated
	 */
	EClass getIBeXDependentInjectivityConstraints();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDependentInjectivityConstraints#getInjectivityConstraints <em>Injectivity Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Injectivity Constraints</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDependentInjectivityConstraints#getInjectivityConstraints()
	 * @see #getIBeXDependentInjectivityConstraints()
	 * @generated
	 */
	EReference getIBeXDependentInjectivityConstraints_InjectivityConstraints();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDependentInjectivityConstraints#getPatterns <em>Patterns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Patterns</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDependentInjectivityConstraints#getPatterns()
	 * @see #getIBeXDependentInjectivityConstraints()
	 * @generated
	 */
	EReference getIBeXDependentInjectivityConstraints_Patterns();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDependentInjectivityConstraints#getAttributeConstraints <em>Attribute Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Attribute Constraints</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDependentInjectivityConstraints#getAttributeConstraints()
	 * @see #getIBeXDependentInjectivityConstraints()
	 * @generated
	 */
	EReference getIBeXDependentInjectivityConstraints_AttributeConstraints();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBexDisjunctInjectivityConstraint <em>IBex Disjunct Injectivity Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBex Disjunct Injectivity Constraint</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBexDisjunctInjectivityConstraint
	 * @generated
	 */
	EClass getIBexDisjunctInjectivityConstraint();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBexDisjunctInjectivityConstraint#getPattern1 <em>Pattern1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Pattern1</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBexDisjunctInjectivityConstraint#getPattern1()
	 * @see #getIBexDisjunctInjectivityConstraint()
	 * @generated
	 */
	EReference getIBexDisjunctInjectivityConstraint_Pattern1();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBexDisjunctInjectivityConstraint#getPattern2 <em>Pattern2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Pattern2</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBexDisjunctInjectivityConstraint#getPattern2()
	 * @see #getIBexDisjunctInjectivityConstraint()
	 * @generated
	 */
	EReference getIBexDisjunctInjectivityConstraint_Pattern2();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBexDisjunctInjectivityConstraint#getNode1 <em>Node1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Node1</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBexDisjunctInjectivityConstraint#getNode1()
	 * @see #getIBexDisjunctInjectivityConstraint()
	 * @generated
	 */
	EReference getIBexDisjunctInjectivityConstraint_Node1();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBexDisjunctInjectivityConstraint#getNode2 <em>Node2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Node2</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBexDisjunctInjectivityConstraint#getNode2()
	 * @see #getIBexDisjunctInjectivityConstraint()
	 * @generated
	 */
	EReference getIBexDisjunctInjectivityConstraint_Node2();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDependentDisjunctAttribute <em>IBe XDependent Disjunct Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XDependent Disjunct Attribute</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDependentDisjunctAttribute
	 * @generated
	 */
	EClass getIBeXDependentDisjunctAttribute();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDependentDisjunctAttribute#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attributes</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDependentDisjunctAttribute#getAttributes()
	 * @see #getIBeXDependentDisjunctAttribute()
	 * @generated
	 */
	EReference getIBeXDependentDisjunctAttribute_Attributes();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDependentDisjunctAttribute#getDependentPatterns <em>Dependent Patterns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Dependent Patterns</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDependentDisjunctAttribute#getDependentPatterns()
	 * @see #getIBeXDependentDisjunctAttribute()
	 * @generated
	 */
	EReference getIBeXDependentDisjunctAttribute_DependentPatterns();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDependentDisjunctAttribute#getInjectivityConstraints <em>Injectivity Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Injectivity Constraints</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDependentDisjunctAttribute#getInjectivityConstraints()
	 * @see #getIBeXDependentDisjunctAttribute()
	 * @generated
	 */
	EReference getIBeXDependentDisjunctAttribute_InjectivityConstraints();

	/**
	 * Returns the meta object for enum '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRelation <em>IBe XRelation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>IBe XRelation</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRelation
	 * @generated
	 */
	EEnum getIBeXRelation();

	/**
	 * Returns the meta object for enum '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXBinaryOperator <em>IBe XBinary Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>IBe XBinary Operator</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXBinaryOperator
	 * @generated
	 */
	EEnum getIBeXBinaryOperator();

	/**
	 * Returns the meta object for enum '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXUnaryOperator <em>IBe XUnary Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>IBe XUnary Operator</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXUnaryOperator
	 * @generated
	 */
	EEnum getIBeXUnaryOperator();

	/**
	 * Returns the meta object for enum '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDistributionType <em>IBe XDistribution Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>IBe XDistribution Type</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDistributionType
	 * @generated
	 */
	EEnum getIBeXDistributionType();

	/**
	 * Returns the meta object for enum '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDistributionRange <em>IBe XDistribution Range</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>IBe XDistribution Range</em>'.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDistributionRange
	 * @generated
	 */
	EEnum getIBeXDistributionRange();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	IBeXPatternModelFactory getIBeXPatternModelFactory();

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
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXModelImpl <em>IBe XModel</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXModelImpl
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXModel()
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
		 * The meta object literal for the '<em><b>Rule Set</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XMODEL__RULE_SET = eINSTANCE.getIBeXModel_RuleSet();

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
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternSetImpl <em>IBe XPattern Set</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternSetImpl
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXPatternSet()
		 * @generated
		 */
		EClass IBE_XPATTERN_SET = eINSTANCE.getIBeXPatternSet();

		/**
		 * The meta object literal for the '<em><b>Context Patterns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XPATTERN_SET__CONTEXT_PATTERNS = eINSTANCE.getIBeXPatternSet_ContextPatterns();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXRuleSetImpl <em>IBe XRule Set</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXRuleSetImpl
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXRuleSet()
		 * @generated
		 */
		EClass IBE_XRULE_SET = eINSTANCE.getIBeXRuleSet();

		/**
		 * The meta object literal for the '<em><b>Rules</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XRULE_SET__RULES = eINSTANCE.getIBeXRuleSet_Rules();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXNodeSetImpl <em>IBe XNode Set</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXNodeSetImpl
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXNodeSet()
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
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXEdgeSetImpl <em>IBe XEdge Set</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXEdgeSetImpl
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXEdgeSet()
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
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXNamedElementImpl <em>IBe XNamed Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXNamedElementImpl
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXNamedElement()
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
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternImpl <em>IBe XPattern</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternImpl
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXPattern()
		 * @generated
		 */
		EClass IBE_XPATTERN = eINSTANCE.getIBeXPattern();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXContextImpl <em>IBe XContext</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXContextImpl
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXContext()
		 * @generated
		 */
		EClass IBE_XCONTEXT = eINSTANCE.getIBeXContext();

		/**
		 * The meta object literal for the '<em><b>Api Pattern Dependencies</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XCONTEXT__API_PATTERN_DEPENDENCIES = eINSTANCE.getIBeXContext_ApiPatternDependencies();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXContextAlternativesImpl <em>IBe XContext Alternatives</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXContextAlternativesImpl
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXContextAlternatives()
		 * @generated
		 */
		EClass IBE_XCONTEXT_ALTERNATIVES = eINSTANCE.getIBeXContextAlternatives();

		/**
		 * The meta object literal for the '<em><b>Alternative Patterns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XCONTEXT_ALTERNATIVES__ALTERNATIVE_PATTERNS = eINSTANCE
				.getIBeXContextAlternatives_AlternativePatterns();

		/**
		 * The meta object literal for the '<em><b>Context</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XCONTEXT_ALTERNATIVES__CONTEXT = eINSTANCE.getIBeXContextAlternatives_Context();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXContextPatternImpl <em>IBe XContext Pattern</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXContextPatternImpl
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXContextPattern()
		 * @generated
		 */
		EClass IBE_XCONTEXT_PATTERN = eINSTANCE.getIBeXContextPattern();

		/**
		 * The meta object literal for the '<em><b>Documentation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IBE_XCONTEXT_PATTERN__DOCUMENTATION = eINSTANCE.getIBeXContextPattern_Documentation();

		/**
		 * The meta object literal for the '<em><b>Attribute Constraint</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XCONTEXT_PATTERN__ATTRIBUTE_CONSTRAINT = eINSTANCE.getIBeXContextPattern_AttributeConstraint();

		/**
		 * The meta object literal for the '<em><b>Arithmetic Constraints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XCONTEXT_PATTERN__ARITHMETIC_CONSTRAINTS = eINSTANCE
				.getIBeXContextPattern_ArithmeticConstraints();

		/**
		 * The meta object literal for the '<em><b>Injectivity Constraints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XCONTEXT_PATTERN__INJECTIVITY_CONSTRAINTS = eINSTANCE
				.getIBeXContextPattern_InjectivityConstraints();

		/**
		 * The meta object literal for the '<em><b>Invocations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XCONTEXT_PATTERN__INVOCATIONS = eINSTANCE.getIBeXContextPattern_Invocations();

		/**
		 * The meta object literal for the '<em><b>Local Edges</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XCONTEXT_PATTERN__LOCAL_EDGES = eINSTANCE.getIBeXContextPattern_LocalEdges();

		/**
		 * The meta object literal for the '<em><b>Local Nodes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XCONTEXT_PATTERN__LOCAL_NODES = eINSTANCE.getIBeXContextPattern_LocalNodes();

		/**
		 * The meta object literal for the '<em><b>Signature Nodes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XCONTEXT_PATTERN__SIGNATURE_NODES = eINSTANCE.getIBeXContextPattern_SignatureNodes();

		/**
		 * The meta object literal for the '<em><b>Csps</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XCONTEXT_PATTERN__CSPS = eINSTANCE.getIBeXContextPattern_Csps();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XCONTEXT_PATTERN__PARAMETERS = eINSTANCE.getIBeXContextPattern_Parameters();

		/**
		 * The meta object literal for the '<em><b>Optimized Disjoint</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IBE_XCONTEXT_PATTERN__OPTIMIZED_DISJOINT = eINSTANCE.getIBeXContextPattern_OptimizedDisjoint();

		/**
		 * The meta object literal for the '<em><b>Subpattern</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IBE_XCONTEXT_PATTERN__SUBPATTERN = eINSTANCE.getIBeXContextPattern_Subpattern();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXCreatePatternImpl <em>IBe XCreate Pattern</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXCreatePatternImpl
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXCreatePattern()
		 * @generated
		 */
		EClass IBE_XCREATE_PATTERN = eINSTANCE.getIBeXCreatePattern();

		/**
		 * The meta object literal for the '<em><b>Attribute Assignments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XCREATE_PATTERN__ATTRIBUTE_ASSIGNMENTS = eINSTANCE.getIBeXCreatePattern_AttributeAssignments();

		/**
		 * The meta object literal for the '<em><b>Context Nodes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XCREATE_PATTERN__CONTEXT_NODES = eINSTANCE.getIBeXCreatePattern_ContextNodes();

		/**
		 * The meta object literal for the '<em><b>Created Edges</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XCREATE_PATTERN__CREATED_EDGES = eINSTANCE.getIBeXCreatePattern_CreatedEdges();

		/**
		 * The meta object literal for the '<em><b>Created Nodes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XCREATE_PATTERN__CREATED_NODES = eINSTANCE.getIBeXCreatePattern_CreatedNodes();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXDeletePatternImpl <em>IBe XDelete Pattern</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXDeletePatternImpl
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXDeletePattern()
		 * @generated
		 */
		EClass IBE_XDELETE_PATTERN = eINSTANCE.getIBeXDeletePattern();

		/**
		 * The meta object literal for the '<em><b>Context Nodes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XDELETE_PATTERN__CONTEXT_NODES = eINSTANCE.getIBeXDeletePattern_ContextNodes();

		/**
		 * The meta object literal for the '<em><b>Deleted Edges</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XDELETE_PATTERN__DELETED_EDGES = eINSTANCE.getIBeXDeletePattern_DeletedEdges();

		/**
		 * The meta object literal for the '<em><b>Deleted Nodes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XDELETE_PATTERN__DELETED_NODES = eINSTANCE.getIBeXDeletePattern_DeletedNodes();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXNodeImpl <em>IBe XNode</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXNodeImpl
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXNode()
		 * @generated
		 */
		EClass IBE_XNODE = eINSTANCE.getIBeXNode();

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
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XNODE__TYPE = eINSTANCE.getIBeXNode_Type();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXEdgeImpl <em>IBe XEdge</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXEdgeImpl
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXEdge()
		 * @generated
		 */
		EClass IBE_XEDGE = eINSTANCE.getIBeXEdge();

		/**
		 * The meta object literal for the '<em><b>Source Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XEDGE__SOURCE_NODE = eINSTANCE.getIBeXEdge_SourceNode();

		/**
		 * The meta object literal for the '<em><b>Target Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XEDGE__TARGET_NODE = eINSTANCE.getIBeXEdge_TargetNode();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XEDGE__TYPE = eINSTANCE.getIBeXEdge_Type();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXParameterImpl <em>IBe XParameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXParameterImpl
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXParameter()
		 * @generated
		 */
		EClass IBE_XPARAMETER = eINSTANCE.getIBeXParameter();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XPARAMETER__TYPE = eINSTANCE.getIBeXParameter_Type();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXAttributeImpl <em>IBe XAttribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXAttributeImpl
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXAttribute()
		 * @generated
		 */
		EClass IBE_XATTRIBUTE = eINSTANCE.getIBeXAttribute();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXAttributeAssignmentImpl <em>IBe XAttribute Assignment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXAttributeAssignmentImpl
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXAttributeAssignment()
		 * @generated
		 */
		EClass IBE_XATTRIBUTE_ASSIGNMENT = eINSTANCE.getIBeXAttributeAssignment();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XATTRIBUTE_ASSIGNMENT__VALUE = eINSTANCE.getIBeXAttributeAssignment_Value();

		/**
		 * The meta object literal for the '<em><b>Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XATTRIBUTE_ASSIGNMENT__NODE = eINSTANCE.getIBeXAttributeAssignment_Node();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XATTRIBUTE_ASSIGNMENT__TYPE = eINSTANCE.getIBeXAttributeAssignment_Type();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXAttributeConstraintImpl <em>IBe XAttribute Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXAttributeConstraintImpl
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXAttributeConstraint()
		 * @generated
		 */
		EClass IBE_XATTRIBUTE_CONSTRAINT = eINSTANCE.getIBeXAttributeConstraint();

		/**
		 * The meta object literal for the '<em><b>Relation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IBE_XATTRIBUTE_CONSTRAINT__RELATION = eINSTANCE.getIBeXAttributeConstraint_Relation();

		/**
		 * The meta object literal for the '<em><b>Lhs</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XATTRIBUTE_CONSTRAINT__LHS = eINSTANCE.getIBeXAttributeConstraint_Lhs();

		/**
		 * The meta object literal for the '<em><b>Rhs</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XATTRIBUTE_CONSTRAINT__RHS = eINSTANCE.getIBeXAttributeConstraint_Rhs();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXAttributeValueImpl <em>IBe XAttribute Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXAttributeValueImpl
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXAttributeValue()
		 * @generated
		 */
		EClass IBE_XATTRIBUTE_VALUE = eINSTANCE.getIBeXAttributeValue();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXAttributeParameterImpl <em>IBe XAttribute Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXAttributeParameterImpl
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXAttributeParameter()
		 * @generated
		 */
		EClass IBE_XATTRIBUTE_PARAMETER = eINSTANCE.getIBeXAttributeParameter();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXConstantImpl <em>IBe XConstant</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXConstantImpl
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXConstant()
		 * @generated
		 */
		EClass IBE_XCONSTANT = eINSTANCE.getIBeXConstant();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IBE_XCONSTANT__VALUE = eINSTANCE.getIBeXConstant_Value();

		/**
		 * The meta object literal for the '<em><b>String Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IBE_XCONSTANT__STRING_VALUE = eINSTANCE.getIBeXConstant_StringValue();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXEnumLiteralImpl <em>IBe XEnum Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXEnumLiteralImpl
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXEnumLiteral()
		 * @generated
		 */
		EClass IBE_XENUM_LITERAL = eINSTANCE.getIBeXEnumLiteral();

		/**
		 * The meta object literal for the '<em><b>Literal</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XENUM_LITERAL__LITERAL = eINSTANCE.getIBeXEnumLiteral_Literal();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXAttributeExpressionImpl <em>IBe XAttribute Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXAttributeExpressionImpl
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXAttributeExpression()
		 * @generated
		 */
		EClass IBE_XATTRIBUTE_EXPRESSION = eINSTANCE.getIBeXAttributeExpression();

		/**
		 * The meta object literal for the '<em><b>Attribute</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XATTRIBUTE_EXPRESSION__ATTRIBUTE = eINSTANCE.getIBeXAttributeExpression_Attribute();

		/**
		 * The meta object literal for the '<em><b>Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XATTRIBUTE_EXPRESSION__NODE = eINSTANCE.getIBeXAttributeExpression_Node();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXStochasticAttributeValueImpl <em>IBe XStochastic Attribute Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXStochasticAttributeValueImpl
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXStochasticAttributeValue()
		 * @generated
		 */
		EClass IBE_XSTOCHASTIC_ATTRIBUTE_VALUE = eINSTANCE.getIBeXStochasticAttributeValue();

		/**
		 * The meta object literal for the '<em><b>Range</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IBE_XSTOCHASTIC_ATTRIBUTE_VALUE__RANGE = eINSTANCE.getIBeXStochasticAttributeValue_Range();

		/**
		 * The meta object literal for the '<em><b>Function</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XSTOCHASTIC_ATTRIBUTE_VALUE__FUNCTION = eINSTANCE.getIBeXStochasticAttributeValue_Function();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXArithmeticValueImpl <em>IBe XArithmetic Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXArithmeticValueImpl
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXArithmeticValue()
		 * @generated
		 */
		EClass IBE_XARITHMETIC_VALUE = eINSTANCE.getIBeXArithmeticValue();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XARITHMETIC_VALUE__EXPRESSION = eINSTANCE.getIBeXArithmeticValue_Expression();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXInjectivityConstraintImpl <em>IBe XInjectivity Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXInjectivityConstraintImpl
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXInjectivityConstraint()
		 * @generated
		 */
		EClass IBE_XINJECTIVITY_CONSTRAINT = eINSTANCE.getIBeXInjectivityConstraint();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XINJECTIVITY_CONSTRAINT__VALUES = eINSTANCE.getIBeXInjectivityConstraint_Values();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternInvocationImpl <em>IBe XPattern Invocation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternInvocationImpl
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXPatternInvocation()
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
		 * The meta object literal for the '<em><b>Invoked By</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XPATTERN_INVOCATION__INVOKED_BY = eINSTANCE.getIBeXPatternInvocation_InvokedBy();

		/**
		 * The meta object literal for the '<em><b>Invoked Pattern</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XPATTERN_INVOCATION__INVOKED_PATTERN = eINSTANCE.getIBeXPatternInvocation_InvokedPattern();

		/**
		 * The meta object literal for the '<em><b>Mapping</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XPATTERN_INVOCATION__MAPPING = eINSTANCE.getIBeXPatternInvocation_Mapping();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXNodeToNodeMappingImpl <em>IBe XNode To Node Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXNodeToNodeMappingImpl
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXNodeToNodeMapping()
		 * @generated
		 */
		EClass IBE_XNODE_TO_NODE_MAPPING = eINSTANCE.getIBeXNodeToNodeMapping();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XNODE_TO_NODE_MAPPING__KEY = eINSTANCE.getIBeXNodeToNodeMapping_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XNODE_TO_NODE_MAPPING__VALUE = eINSTANCE.getIBeXNodeToNodeMapping_Value();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXCSPImpl <em>IBe XCSP</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXCSPImpl
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXCSP()
		 * @generated
		 */
		EClass IBE_XCSP = eINSTANCE.getIBeXCSP();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IBE_XCSP__NAME = eINSTANCE.getIBeXCSP_Name();

		/**
		 * The meta object literal for the '<em><b>Package</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IBE_XCSP__PACKAGE = eINSTANCE.getIBeXCSP_Package();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XCSP__VALUES = eINSTANCE.getIBeXCSP_Values();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXRuleImpl <em>IBe XRule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXRuleImpl
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXRule()
		 * @generated
		 */
		EClass IBE_XRULE = eINSTANCE.getIBeXRule();

		/**
		 * The meta object literal for the '<em><b>Documentation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IBE_XRULE__DOCUMENTATION = eINSTANCE.getIBeXRule_Documentation();

		/**
		 * The meta object literal for the '<em><b>Lhs</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XRULE__LHS = eINSTANCE.getIBeXRule_Lhs();

		/**
		 * The meta object literal for the '<em><b>Rhs</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XRULE__RHS = eINSTANCE.getIBeXRule_Rhs();

		/**
		 * The meta object literal for the '<em><b>Create</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XRULE__CREATE = eINSTANCE.getIBeXRule_Create();

		/**
		 * The meta object literal for the '<em><b>Delete</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XRULE__DELETE = eINSTANCE.getIBeXRule_Delete();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XRULE__PARAMETERS = eINSTANCE.getIBeXRule_Parameters();

		/**
		 * The meta object literal for the '<em><b>Arithmetic Constraints</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XRULE__ARITHMETIC_CONSTRAINTS = eINSTANCE.getIBeXRule_ArithmeticConstraints();

		/**
		 * The meta object literal for the '<em><b>Probability</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XRULE__PROBABILITY = eINSTANCE.getIBeXRule_Probability();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXProbabilityImpl <em>IBe XProbability</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXProbabilityImpl
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXProbability()
		 * @generated
		 */
		EClass IBE_XPROBABILITY = eINSTANCE.getIBeXProbability();

		/**
		 * The meta object literal for the '<em><b>Distribution</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XPROBABILITY__DISTRIBUTION = eINSTANCE.getIBeXProbability_Distribution();

		/**
		 * The meta object literal for the '<em><b>Parameter</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XPROBABILITY__PARAMETER = eINSTANCE.getIBeXProbability_Parameter();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXArithmeticConstraintImpl <em>IBe XArithmetic Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXArithmeticConstraintImpl
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXArithmeticConstraint()
		 * @generated
		 */
		EClass IBE_XARITHMETIC_CONSTRAINT = eINSTANCE.getIBeXArithmeticConstraint();

		/**
		 * The meta object literal for the '<em><b>Lhs</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XARITHMETIC_CONSTRAINT__LHS = eINSTANCE.getIBeXArithmeticConstraint_Lhs();

		/**
		 * The meta object literal for the '<em><b>Relation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IBE_XARITHMETIC_CONSTRAINT__RELATION = eINSTANCE.getIBeXArithmeticConstraint_Relation();

		/**
		 * The meta object literal for the '<em><b>Rhs</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XARITHMETIC_CONSTRAINT__RHS = eINSTANCE.getIBeXArithmeticConstraint_Rhs();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticExpression <em>IBe XArithmetic Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticExpression
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXArithmeticExpression()
		 * @generated
		 */
		EClass IBE_XARITHMETIC_EXPRESSION = eINSTANCE.getIBeXArithmeticExpression();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXUnaryExpressionImpl <em>IBe XUnary Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXUnaryExpressionImpl
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXUnaryExpression()
		 * @generated
		 */
		EClass IBE_XUNARY_EXPRESSION = eINSTANCE.getIBeXUnaryExpression();

		/**
		 * The meta object literal for the '<em><b>Operand</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XUNARY_EXPRESSION__OPERAND = eINSTANCE.getIBeXUnaryExpression_Operand();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IBE_XUNARY_EXPRESSION__OPERATOR = eINSTANCE.getIBeXUnaryExpression_Operator();

		/**
		 * The meta object literal for the '<em><b>Negative</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IBE_XUNARY_EXPRESSION__NEGATIVE = eINSTANCE.getIBeXUnaryExpression_Negative();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXBinaryExpressionImpl <em>IBe XBinary Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXBinaryExpressionImpl
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXBinaryExpression()
		 * @generated
		 */
		EClass IBE_XBINARY_EXPRESSION = eINSTANCE.getIBeXBinaryExpression();

		/**
		 * The meta object literal for the '<em><b>Left</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XBINARY_EXPRESSION__LEFT = eINSTANCE.getIBeXBinaryExpression_Left();

		/**
		 * The meta object literal for the '<em><b>Right</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XBINARY_EXPRESSION__RIGHT = eINSTANCE.getIBeXBinaryExpression_Right();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IBE_XBINARY_EXPRESSION__OPERATOR = eINSTANCE.getIBeXBinaryExpression_Operator();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXArithmeticValueLiteralImpl <em>IBe XArithmetic Value Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXArithmeticValueLiteralImpl
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXArithmeticValueLiteral()
		 * @generated
		 */
		EClass IBE_XARITHMETIC_VALUE_LITERAL = eINSTANCE.getIBeXArithmeticValueLiteral();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IBE_XARITHMETIC_VALUE_LITERAL__VALUE = eINSTANCE.getIBeXArithmeticValueLiteral_Value();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXProbabilityDistributionImpl <em>IBe XProbability Distribution</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXProbabilityDistributionImpl
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXProbabilityDistribution()
		 * @generated
		 */
		EClass IBE_XPROBABILITY_DISTRIBUTION = eINSTANCE.getIBeXProbabilityDistribution();

		/**
		 * The meta object literal for the '<em><b>Mean</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XPROBABILITY_DISTRIBUTION__MEAN = eINSTANCE.getIBeXProbabilityDistribution_Mean();

		/**
		 * The meta object literal for the '<em><b>Stddev</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XPROBABILITY_DISTRIBUTION__STDDEV = eINSTANCE.getIBeXProbabilityDistribution_Stddev();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IBE_XPROBABILITY_DISTRIBUTION__TYPE = eINSTANCE.getIBeXProbabilityDistribution_Type();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXArithmeticAttributeImpl <em>IBe XArithmetic Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXArithmeticAttributeImpl
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXArithmeticAttribute()
		 * @generated
		 */
		EClass IBE_XARITHMETIC_ATTRIBUTE = eINSTANCE.getIBeXArithmeticAttribute();

		/**
		 * The meta object literal for the '<em><b>Attribute</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XARITHMETIC_ATTRIBUTE__ATTRIBUTE = eINSTANCE.getIBeXArithmeticAttribute_Attribute();

		/**
		 * The meta object literal for the '<em><b>Negative</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IBE_XARITHMETIC_ATTRIBUTE__NEGATIVE = eINSTANCE.getIBeXArithmeticAttribute_Negative();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXMatchCountImpl <em>IBe XMatch Count</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXMatchCountImpl
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXMatchCount()
		 * @generated
		 */
		EClass IBE_XMATCH_COUNT = eINSTANCE.getIBeXMatchCount();

		/**
		 * The meta object literal for the '<em><b>Invocation</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XMATCH_COUNT__INVOCATION = eINSTANCE.getIBeXMatchCount_Invocation();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXConstraint <em>IBe XConstraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXConstraint
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXConstraint()
		 * @generated
		 */
		EClass IBE_XCONSTRAINT = eINSTANCE.getIBeXConstraint();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXDisjunctContextPatternImpl <em>IBe XDisjunct Context Pattern</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXDisjunctContextPatternImpl
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXDisjunctContextPattern()
		 * @generated
		 */
		EClass IBE_XDISJUNCT_CONTEXT_PATTERN = eINSTANCE.getIBeXDisjunctContextPattern();

		/**
		 * The meta object literal for the '<em><b>Subpatterns</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XDISJUNCT_CONTEXT_PATTERN__SUBPATTERNS = eINSTANCE.getIBeXDisjunctContextPattern_Subpatterns();

		/**
		 * The meta object literal for the '<em><b>Injectivity Constraints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XDISJUNCT_CONTEXT_PATTERN__INJECTIVITY_CONSTRAINTS = eINSTANCE
				.getIBeXDisjunctContextPattern_InjectivityConstraints();

		/**
		 * The meta object literal for the '<em><b>Attributes Constraints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XDISJUNCT_CONTEXT_PATTERN__ATTRIBUTES_CONSTRAINTS = eINSTANCE
				.getIBeXDisjunctContextPattern_AttributesConstraints();

		/**
		 * The meta object literal for the '<em><b>Non Optimized Pattern</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XDISJUNCT_CONTEXT_PATTERN__NON_OPTIMIZED_PATTERN = eINSTANCE
				.getIBeXDisjunctContextPattern_NonOptimizedPattern();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXDisjunctAttributeImpl <em>IBe XDisjunct Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXDisjunctAttributeImpl
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXDisjunctAttribute()
		 * @generated
		 */
		EClass IBE_XDISJUNCT_ATTRIBUTE = eINSTANCE.getIBeXDisjunctAttribute();

		/**
		 * The meta object literal for the '<em><b>Target Pattern</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XDISJUNCT_ATTRIBUTE__TARGET_PATTERN = eINSTANCE.getIBeXDisjunctAttribute_TargetPattern();

		/**
		 * The meta object literal for the '<em><b>Source Pattern</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XDISJUNCT_ATTRIBUTE__SOURCE_PATTERN = eINSTANCE.getIBeXDisjunctAttribute_SourcePattern();

		/**
		 * The meta object literal for the '<em><b>Disjunct Attribute</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XDISJUNCT_ATTRIBUTE__DISJUNCT_ATTRIBUTE = eINSTANCE.getIBeXDisjunctAttribute_DisjunctAttribute();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXDependentInjectivityConstraintsImpl <em>IBe XDependent Injectivity Constraints</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXDependentInjectivityConstraintsImpl
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXDependentInjectivityConstraints()
		 * @generated
		 */
		EClass IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS = eINSTANCE.getIBeXDependentInjectivityConstraints();

		/**
		 * The meta object literal for the '<em><b>Injectivity Constraints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS__INJECTIVITY_CONSTRAINTS = eINSTANCE
				.getIBeXDependentInjectivityConstraints_InjectivityConstraints();

		/**
		 * The meta object literal for the '<em><b>Patterns</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS__PATTERNS = eINSTANCE
				.getIBeXDependentInjectivityConstraints_Patterns();

		/**
		 * The meta object literal for the '<em><b>Attribute Constraints</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS__ATTRIBUTE_CONSTRAINTS = eINSTANCE
				.getIBeXDependentInjectivityConstraints_AttributeConstraints();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBexDisjunctInjectivityConstraintImpl <em>IBex Disjunct Injectivity Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBexDisjunctInjectivityConstraintImpl
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBexDisjunctInjectivityConstraint()
		 * @generated
		 */
		EClass IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT = eINSTANCE.getIBexDisjunctInjectivityConstraint();

		/**
		 * The meta object literal for the '<em><b>Pattern1</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT__PATTERN1 = eINSTANCE
				.getIBexDisjunctInjectivityConstraint_Pattern1();

		/**
		 * The meta object literal for the '<em><b>Pattern2</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT__PATTERN2 = eINSTANCE
				.getIBexDisjunctInjectivityConstraint_Pattern2();

		/**
		 * The meta object literal for the '<em><b>Node1</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT__NODE1 = eINSTANCE.getIBexDisjunctInjectivityConstraint_Node1();

		/**
		 * The meta object literal for the '<em><b>Node2</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT__NODE2 = eINSTANCE.getIBexDisjunctInjectivityConstraint_Node2();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXDependentDisjunctAttributeImpl <em>IBe XDependent Disjunct Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXDependentDisjunctAttributeImpl
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXDependentDisjunctAttribute()
		 * @generated
		 */
		EClass IBE_XDEPENDENT_DISJUNCT_ATTRIBUTE = eINSTANCE.getIBeXDependentDisjunctAttribute();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XDEPENDENT_DISJUNCT_ATTRIBUTE__ATTRIBUTES = eINSTANCE
				.getIBeXDependentDisjunctAttribute_Attributes();

		/**
		 * The meta object literal for the '<em><b>Dependent Patterns</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XDEPENDENT_DISJUNCT_ATTRIBUTE__DEPENDENT_PATTERNS = eINSTANCE
				.getIBeXDependentDisjunctAttribute_DependentPatterns();

		/**
		 * The meta object literal for the '<em><b>Injectivity Constraints</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XDEPENDENT_DISJUNCT_ATTRIBUTE__INJECTIVITY_CONSTRAINTS = eINSTANCE
				.getIBeXDependentDisjunctAttribute_InjectivityConstraints();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRelation <em>IBe XRelation</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRelation
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXRelation()
		 * @generated
		 */
		EEnum IBE_XRELATION = eINSTANCE.getIBeXRelation();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXBinaryOperator <em>IBe XBinary Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXBinaryOperator
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXBinaryOperator()
		 * @generated
		 */
		EEnum IBE_XBINARY_OPERATOR = eINSTANCE.getIBeXBinaryOperator();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXUnaryOperator <em>IBe XUnary Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXUnaryOperator
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXUnaryOperator()
		 * @generated
		 */
		EEnum IBE_XUNARY_OPERATOR = eINSTANCE.getIBeXUnaryOperator();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDistributionType <em>IBe XDistribution Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDistributionType
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXDistributionType()
		 * @generated
		 */
		EEnum IBE_XDISTRIBUTION_TYPE = eINSTANCE.getIBeXDistributionType();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDistributionRange <em>IBe XDistribution Range</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDistributionRange
		 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternModelPackageImpl#getIBeXDistributionRange()
		 * @generated
		 */
		EEnum IBE_XDISTRIBUTION_RANGE = eINSTANCE.getIBeXDistributionRange();

	}

} //IBeXPatternModelPackage
