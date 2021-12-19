/**
 */
package org.emoflon.ibex.gt.StateModel;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
 * @see org.emoflon.ibex.gt.StateModel.StateModelFactory
 * @model kind="package"
 * @generated
 */
public interface StateModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "StateModel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "platform:/resource/StateModel/model/StateModel.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "StateModel";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	StateModelPackage eINSTANCE = org.emoflon.ibex.gt.StateModel.impl.StateModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.gt.StateModel.impl.StateContainerImpl <em>State Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.gt.StateModel.impl.StateContainerImpl
	 * @see org.emoflon.ibex.gt.StateModel.impl.StateModelPackageImpl#getStateContainer()
	 * @generated
	 */
	int STATE_CONTAINER = 0;

	/**
	 * The feature id for the '<em><b>States</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_CONTAINER__STATES = 0;

	/**
	 * The feature id for the '<em><b>Initial State</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_CONTAINER__INITIAL_STATE = 1;

	/**
	 * The feature id for the '<em><b>Initial Matches</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_CONTAINER__INITIAL_MATCHES = 2;

	/**
	 * The number of structural features of the '<em>State Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_CONTAINER_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>State Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_CONTAINER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.gt.StateModel.impl.StateImpl <em>State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.gt.StateModel.impl.StateImpl
	 * @see org.emoflon.ibex.gt.StateModel.impl.StateModelPackageImpl#getState()
	 * @generated
	 */
	int STATE = 1;

	/**
	 * The feature id for the '<em><b>Initial</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__INITIAL = 0;

	/**
	 * The feature id for the '<em><b>Children</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__CHILDREN = 1;

	/**
	 * The feature id for the '<em><b>Hash</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__HASH = 2;

	/**
	 * The feature id for the '<em><b>Pushout Approach</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__PUSHOUT_APPROACH = 3;

	/**
	 * The number of structural features of the '<em>State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.gt.StateModel.impl.RuleStateImpl <em>Rule State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.gt.StateModel.impl.RuleStateImpl
	 * @see org.emoflon.ibex.gt.StateModel.impl.StateModelPackageImpl#getRuleState()
	 * @generated
	 */
	int RULE_STATE = 2;

	/**
	 * The feature id for the '<em><b>Initial</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_STATE__INITIAL = STATE__INITIAL;

	/**
	 * The feature id for the '<em><b>Children</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_STATE__CHILDREN = STATE__CHILDREN;

	/**
	 * The feature id for the '<em><b>Hash</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_STATE__HASH = STATE__HASH;

	/**
	 * The feature id for the '<em><b>Pushout Approach</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_STATE__PUSHOUT_APPROACH = STATE__PUSHOUT_APPROACH;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_STATE__PARENT = STATE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_STATE__RULE = STATE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Structural Delta</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_STATE__STRUCTURAL_DELTA = STATE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Attribute Deltas</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_STATE__ATTRIBUTE_DELTAS = STATE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Match</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_STATE__MATCH = STATE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Co Match</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_STATE__CO_MATCH = STATE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_STATE__PARAMETERS = STATE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Deleted Matches</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_STATE__DELETED_MATCHES = STATE_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Created Matches</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_STATE__CREATED_MATCHES = STATE_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Match Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_STATE__MATCH_COUNT = STATE_FEATURE_COUNT + 9;

	/**
	 * The number of structural features of the '<em>Rule State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_STATE_FEATURE_COUNT = STATE_FEATURE_COUNT + 10;

	/**
	 * The number of operations of the '<em>Rule State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_STATE_OPERATION_COUNT = STATE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.gt.StateModel.impl.AttributeDeltaImpl <em>Attribute Delta</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.gt.StateModel.impl.AttributeDeltaImpl
	 * @see org.emoflon.ibex.gt.StateModel.impl.StateModelPackageImpl#getAttributeDelta()
	 * @generated
	 */
	int ATTRIBUTE_DELTA = 3;

	/**
	 * The feature id for the '<em><b>Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DELTA__ATTRIBUTE = 0;

	/**
	 * The feature id for the '<em><b>Object</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DELTA__OBJECT = 1;

	/**
	 * The feature id for the '<em><b>Old Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DELTA__OLD_VALUE = 2;

	/**
	 * The feature id for the '<em><b>New Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DELTA__NEW_VALUE = 3;

	/**
	 * The number of structural features of the '<em>Attribute Delta</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DELTA_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Attribute Delta</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DELTA_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.gt.StateModel.impl.StructuralDeltaImpl <em>Structural Delta</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.gt.StateModel.impl.StructuralDeltaImpl
	 * @see org.emoflon.ibex.gt.StateModel.impl.StateModelPackageImpl#getStructuralDelta()
	 * @generated
	 */
	int STRUCTURAL_DELTA = 4;

	/**
	 * The feature id for the '<em><b>Created Objects</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURAL_DELTA__CREATED_OBJECTS = 0;

	/**
	 * The feature id for the '<em><b>Deleted Objects</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURAL_DELTA__DELETED_OBJECTS = 1;

	/**
	 * The feature id for the '<em><b>Created Links</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURAL_DELTA__CREATED_LINKS = 2;

	/**
	 * The feature id for the '<em><b>Deleted Links</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURAL_DELTA__DELETED_LINKS = 3;

	/**
	 * The feature id for the '<em><b>Deleted Root Level Objects</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURAL_DELTA__DELETED_ROOT_LEVEL_OBJECTS = 4;

	/**
	 * The feature id for the '<em><b>Resource2 EObject Containment</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURAL_DELTA__RESOURCE2_EOBJECT_CONTAINMENT = 5;

	/**
	 * The number of structural features of the '<em>Structural Delta</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURAL_DELTA_FEATURE_COUNT = 6;

	/**
	 * The number of operations of the '<em>Structural Delta</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURAL_DELTA_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.gt.StateModel.impl.LinkImpl <em>Link</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.gt.StateModel.impl.LinkImpl
	 * @see org.emoflon.ibex.gt.StateModel.impl.StateModelPackageImpl#getLink()
	 * @generated
	 */
	int LINK = 5;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK__TYPE = 0;

	/**
	 * The feature id for the '<em><b>Src</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK__SRC = 1;

	/**
	 * The feature id for the '<em><b>Trg</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK__TRG = 2;

	/**
	 * The number of structural features of the '<em>Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.gt.StateModel.impl.IBeXMatchImpl <em>IBe XMatch</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.gt.StateModel.impl.IBeXMatchImpl
	 * @see org.emoflon.ibex.gt.StateModel.impl.StateModelPackageImpl#getIBeXMatch()
	 * @generated
	 */
	int IBE_XMATCH = 6;

	/**
	 * The feature id for the '<em><b>Pattern Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XMATCH__PATTERN_NAME = 0;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XMATCH__PARAMETERS = 1;

	/**
	 * The number of structural features of the '<em>IBe XMatch</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XMATCH_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>IBe XMatch</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XMATCH_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.gt.StateModel.impl.ParameterImpl <em>Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.gt.StateModel.impl.ParameterImpl
	 * @see org.emoflon.ibex.gt.StateModel.impl.StateModelPackageImpl#getParameter()
	 * @generated
	 */
	int PARAMETER = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__NAME = 0;

	/**
	 * The number of structural features of the '<em>Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.gt.StateModel.impl.ValueImpl <em>Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.gt.StateModel.impl.ValueImpl
	 * @see org.emoflon.ibex.gt.StateModel.impl.StateModelPackageImpl#getValue()
	 * @generated
	 */
	int VALUE = 8;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE__TYPE = 0;

	/**
	 * The feature id for the '<em><b>Value As String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE__VALUE_AS_STRING = 1;

	/**
	 * The number of structural features of the '<em>Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.gt.StateModel.impl.MatchDeltaImpl <em>Match Delta</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.gt.StateModel.impl.MatchDeltaImpl
	 * @see org.emoflon.ibex.gt.StateModel.impl.StateModelPackageImpl#getMatchDelta()
	 * @generated
	 */
	int MATCH_DELTA = 9;

	/**
	 * The feature id for the '<em><b>Pattern Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATCH_DELTA__PATTERN_NAME = 0;

	/**
	 * The feature id for the '<em><b>Match Deltas For Pattern</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATCH_DELTA__MATCH_DELTAS_FOR_PATTERN = 1;

	/**
	 * The number of structural features of the '<em>Match Delta</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATCH_DELTA_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Match Delta</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MATCH_DELTA_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.gt.StateModel.impl.ComplexParameterImpl <em>Complex Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.gt.StateModel.impl.ComplexParameterImpl
	 * @see org.emoflon.ibex.gt.StateModel.impl.StateModelPackageImpl#getComplexParameter()
	 * @generated
	 */
	int COMPLEX_PARAMETER = 10;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEX_PARAMETER__NAME = PARAMETER__NAME;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEX_PARAMETER__VALUE = PARAMETER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Complex Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEX_PARAMETER_FEATURE_COUNT = PARAMETER_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Complex Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPLEX_PARAMETER_OPERATION_COUNT = PARAMETER_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.gt.StateModel.impl.SimpleParameterImpl <em>Simple Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.gt.StateModel.impl.SimpleParameterImpl
	 * @see org.emoflon.ibex.gt.StateModel.impl.StateModelPackageImpl#getSimpleParameter()
	 * @generated
	 */
	int SIMPLE_PARAMETER = 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_PARAMETER__NAME = PARAMETER__NAME;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_PARAMETER__VALUE = PARAMETER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_PARAMETER__TYPE = PARAMETER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Simple Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_PARAMETER_FEATURE_COUNT = PARAMETER_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Simple Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_PARAMETER_OPERATION_COUNT = PARAMETER_OPERATION_COUNT + 0;

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.gt.StateModel.StateContainer <em>State Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>State Container</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.StateContainer
	 * @generated
	 */
	EClass getStateContainer();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.gt.StateModel.StateContainer#getStates <em>States</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>States</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.StateContainer#getStates()
	 * @see #getStateContainer()
	 * @generated
	 */
	EReference getStateContainer_States();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.gt.StateModel.StateContainer#getInitialState <em>Initial State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Initial State</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.StateContainer#getInitialState()
	 * @see #getStateContainer()
	 * @generated
	 */
	EReference getStateContainer_InitialState();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.gt.StateModel.StateContainer#getInitialMatches <em>Initial Matches</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Initial Matches</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.StateContainer#getInitialMatches()
	 * @see #getStateContainer()
	 * @generated
	 */
	EReference getStateContainer_InitialMatches();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.gt.StateModel.State <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>State</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.State
	 * @generated
	 */
	EClass getState();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.gt.StateModel.State#isInitial <em>Initial</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Initial</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.State#isInitial()
	 * @see #getState()
	 * @generated
	 */
	EAttribute getState_Initial();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.gt.StateModel.State#getChildren <em>Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Children</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.State#getChildren()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_Children();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.gt.StateModel.State#getHash <em>Hash</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Hash</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.State#getHash()
	 * @see #getState()
	 * @generated
	 */
	EAttribute getState_Hash();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.gt.StateModel.State#getPushoutApproach <em>Pushout Approach</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pushout Approach</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.State#getPushoutApproach()
	 * @see #getState()
	 * @generated
	 */
	EAttribute getState_PushoutApproach();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.gt.StateModel.RuleState <em>Rule State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule State</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.RuleState
	 * @generated
	 */
	EClass getRuleState();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.gt.StateModel.RuleState#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parent</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.RuleState#getParent()
	 * @see #getRuleState()
	 * @generated
	 */
	EReference getRuleState_Parent();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.gt.StateModel.RuleState#getRule <em>Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Rule</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.RuleState#getRule()
	 * @see #getRuleState()
	 * @generated
	 */
	EReference getRuleState_Rule();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.gt.StateModel.RuleState#getStructuralDelta <em>Structural Delta</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Structural Delta</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.RuleState#getStructuralDelta()
	 * @see #getRuleState()
	 * @generated
	 */
	EReference getRuleState_StructuralDelta();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.gt.StateModel.RuleState#getAttributeDeltas <em>Attribute Deltas</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attribute Deltas</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.RuleState#getAttributeDeltas()
	 * @see #getRuleState()
	 * @generated
	 */
	EReference getRuleState_AttributeDeltas();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.gt.StateModel.RuleState#getMatch <em>Match</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Match</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.RuleState#getMatch()
	 * @see #getRuleState()
	 * @generated
	 */
	EReference getRuleState_Match();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.gt.StateModel.RuleState#getCoMatch <em>Co Match</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Co Match</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.RuleState#getCoMatch()
	 * @see #getRuleState()
	 * @generated
	 */
	EReference getRuleState_CoMatch();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.gt.StateModel.RuleState#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.RuleState#getParameters()
	 * @see #getRuleState()
	 * @generated
	 */
	EReference getRuleState_Parameters();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.gt.StateModel.RuleState#getDeletedMatches <em>Deleted Matches</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Deleted Matches</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.RuleState#getDeletedMatches()
	 * @see #getRuleState()
	 * @generated
	 */
	EReference getRuleState_DeletedMatches();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.gt.StateModel.RuleState#getCreatedMatches <em>Created Matches</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Created Matches</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.RuleState#getCreatedMatches()
	 * @see #getRuleState()
	 * @generated
	 */
	EReference getRuleState_CreatedMatches();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.gt.StateModel.RuleState#getMatchCount <em>Match Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Match Count</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.RuleState#getMatchCount()
	 * @see #getRuleState()
	 * @generated
	 */
	EAttribute getRuleState_MatchCount();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.gt.StateModel.AttributeDelta <em>Attribute Delta</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute Delta</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.AttributeDelta
	 * @generated
	 */
	EClass getAttributeDelta();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.gt.StateModel.AttributeDelta#getAttribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Attribute</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.AttributeDelta#getAttribute()
	 * @see #getAttributeDelta()
	 * @generated
	 */
	EReference getAttributeDelta_Attribute();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.gt.StateModel.AttributeDelta#getObject <em>Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Object</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.AttributeDelta#getObject()
	 * @see #getAttributeDelta()
	 * @generated
	 */
	EReference getAttributeDelta_Object();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.gt.StateModel.AttributeDelta#getOldValue <em>Old Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Old Value</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.AttributeDelta#getOldValue()
	 * @see #getAttributeDelta()
	 * @generated
	 */
	EReference getAttributeDelta_OldValue();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.gt.StateModel.AttributeDelta#getNewValue <em>New Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>New Value</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.AttributeDelta#getNewValue()
	 * @see #getAttributeDelta()
	 * @generated
	 */
	EReference getAttributeDelta_NewValue();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.gt.StateModel.StructuralDelta <em>Structural Delta</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Structural Delta</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.StructuralDelta
	 * @generated
	 */
	EClass getStructuralDelta();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.gt.StateModel.StructuralDelta#getCreatedObjects <em>Created Objects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Created Objects</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.StructuralDelta#getCreatedObjects()
	 * @see #getStructuralDelta()
	 * @generated
	 */
	EReference getStructuralDelta_CreatedObjects();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.gt.StateModel.StructuralDelta#getDeletedObjects <em>Deleted Objects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Deleted Objects</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.StructuralDelta#getDeletedObjects()
	 * @see #getStructuralDelta()
	 * @generated
	 */
	EReference getStructuralDelta_DeletedObjects();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.gt.StateModel.StructuralDelta#getCreatedLinks <em>Created Links</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Created Links</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.StructuralDelta#getCreatedLinks()
	 * @see #getStructuralDelta()
	 * @generated
	 */
	EReference getStructuralDelta_CreatedLinks();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.gt.StateModel.StructuralDelta#getDeletedLinks <em>Deleted Links</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Deleted Links</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.StructuralDelta#getDeletedLinks()
	 * @see #getStructuralDelta()
	 * @generated
	 */
	EReference getStructuralDelta_DeletedLinks();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.gt.StateModel.StructuralDelta#getDeletedRootLevelObjects <em>Deleted Root Level Objects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Deleted Root Level Objects</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.StructuralDelta#getDeletedRootLevelObjects()
	 * @see #getStructuralDelta()
	 * @generated
	 */
	EReference getStructuralDelta_DeletedRootLevelObjects();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.gt.StateModel.StructuralDelta#getResource2EObjectContainment <em>Resource2 EObject Containment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Resource2 EObject Containment</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.StructuralDelta#getResource2EObjectContainment()
	 * @see #getStructuralDelta()
	 * @generated
	 */
	EReference getStructuralDelta_Resource2EObjectContainment();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.gt.StateModel.Link <em>Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Link</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.Link
	 * @generated
	 */
	EClass getLink();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.gt.StateModel.Link#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.Link#getType()
	 * @see #getLink()
	 * @generated
	 */
	EReference getLink_Type();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.gt.StateModel.Link#getSrc <em>Src</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Src</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.Link#getSrc()
	 * @see #getLink()
	 * @generated
	 */
	EReference getLink_Src();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.gt.StateModel.Link#getTrg <em>Trg</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Trg</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.Link#getTrg()
	 * @see #getLink()
	 * @generated
	 */
	EReference getLink_Trg();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.gt.StateModel.IBeXMatch <em>IBe XMatch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XMatch</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.IBeXMatch
	 * @generated
	 */
	EClass getIBeXMatch();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.gt.StateModel.IBeXMatch#getPatternName <em>Pattern Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pattern Name</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.IBeXMatch#getPatternName()
	 * @see #getIBeXMatch()
	 * @generated
	 */
	EAttribute getIBeXMatch_PatternName();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.gt.StateModel.IBeXMatch#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.IBeXMatch#getParameters()
	 * @see #getIBeXMatch()
	 * @generated
	 */
	EReference getIBeXMatch_Parameters();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.gt.StateModel.Parameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameter</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.Parameter
	 * @generated
	 */
	EClass getParameter();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.gt.StateModel.Parameter#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.Parameter#getName()
	 * @see #getParameter()
	 * @generated
	 */
	EAttribute getParameter_Name();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.gt.StateModel.Value <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Value</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.Value
	 * @generated
	 */
	EClass getValue();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.gt.StateModel.Value#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.Value#getType()
	 * @see #getValue()
	 * @generated
	 */
	EReference getValue_Type();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.gt.StateModel.Value#getValueAsString <em>Value As String</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value As String</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.Value#getValueAsString()
	 * @see #getValue()
	 * @generated
	 */
	EAttribute getValue_ValueAsString();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.gt.StateModel.MatchDelta <em>Match Delta</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Match Delta</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.MatchDelta
	 * @generated
	 */
	EClass getMatchDelta();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.gt.StateModel.MatchDelta#getPatternName <em>Pattern Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pattern Name</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.MatchDelta#getPatternName()
	 * @see #getMatchDelta()
	 * @generated
	 */
	EAttribute getMatchDelta_PatternName();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.gt.StateModel.MatchDelta#getMatchDeltasForPattern <em>Match Deltas For Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Match Deltas For Pattern</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.MatchDelta#getMatchDeltasForPattern()
	 * @see #getMatchDelta()
	 * @generated
	 */
	EReference getMatchDelta_MatchDeltasForPattern();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.gt.StateModel.ComplexParameter <em>Complex Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Complex Parameter</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.ComplexParameter
	 * @generated
	 */
	EClass getComplexParameter();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.gt.StateModel.ComplexParameter#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.ComplexParameter#getValue()
	 * @see #getComplexParameter()
	 * @generated
	 */
	EReference getComplexParameter_Value();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.gt.StateModel.SimpleParameter <em>Simple Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Simple Parameter</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.SimpleParameter
	 * @generated
	 */
	EClass getSimpleParameter();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.gt.StateModel.SimpleParameter#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.SimpleParameter#getValue()
	 * @see #getSimpleParameter()
	 * @generated
	 */
	EAttribute getSimpleParameter_Value();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.gt.StateModel.SimpleParameter#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.SimpleParameter#getType()
	 * @see #getSimpleParameter()
	 * @generated
	 */
	EReference getSimpleParameter_Type();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	StateModelFactory getStateModelFactory();

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
		 * The meta object literal for the '{@link org.emoflon.ibex.gt.StateModel.impl.StateContainerImpl <em>State Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.gt.StateModel.impl.StateContainerImpl
		 * @see org.emoflon.ibex.gt.StateModel.impl.StateModelPackageImpl#getStateContainer()
		 * @generated
		 */
		EClass STATE_CONTAINER = eINSTANCE.getStateContainer();

		/**
		 * The meta object literal for the '<em><b>States</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE_CONTAINER__STATES = eINSTANCE.getStateContainer_States();

		/**
		 * The meta object literal for the '<em><b>Initial State</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE_CONTAINER__INITIAL_STATE = eINSTANCE.getStateContainer_InitialState();

		/**
		 * The meta object literal for the '<em><b>Initial Matches</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE_CONTAINER__INITIAL_MATCHES = eINSTANCE.getStateContainer_InitialMatches();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.gt.StateModel.impl.StateImpl <em>State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.gt.StateModel.impl.StateImpl
		 * @see org.emoflon.ibex.gt.StateModel.impl.StateModelPackageImpl#getState()
		 * @generated
		 */
		EClass STATE = eINSTANCE.getState();

		/**
		 * The meta object literal for the '<em><b>Initial</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE__INITIAL = eINSTANCE.getState_Initial();

		/**
		 * The meta object literal for the '<em><b>Children</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__CHILDREN = eINSTANCE.getState_Children();

		/**
		 * The meta object literal for the '<em><b>Hash</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE__HASH = eINSTANCE.getState_Hash();

		/**
		 * The meta object literal for the '<em><b>Pushout Approach</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE__PUSHOUT_APPROACH = eINSTANCE.getState_PushoutApproach();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.gt.StateModel.impl.RuleStateImpl <em>Rule State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.gt.StateModel.impl.RuleStateImpl
		 * @see org.emoflon.ibex.gt.StateModel.impl.StateModelPackageImpl#getRuleState()
		 * @generated
		 */
		EClass RULE_STATE = eINSTANCE.getRuleState();

		/**
		 * The meta object literal for the '<em><b>Parent</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_STATE__PARENT = eINSTANCE.getRuleState_Parent();

		/**
		 * The meta object literal for the '<em><b>Rule</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_STATE__RULE = eINSTANCE.getRuleState_Rule();

		/**
		 * The meta object literal for the '<em><b>Structural Delta</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_STATE__STRUCTURAL_DELTA = eINSTANCE.getRuleState_StructuralDelta();

		/**
		 * The meta object literal for the '<em><b>Attribute Deltas</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_STATE__ATTRIBUTE_DELTAS = eINSTANCE.getRuleState_AttributeDeltas();

		/**
		 * The meta object literal for the '<em><b>Match</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_STATE__MATCH = eINSTANCE.getRuleState_Match();

		/**
		 * The meta object literal for the '<em><b>Co Match</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_STATE__CO_MATCH = eINSTANCE.getRuleState_CoMatch();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_STATE__PARAMETERS = eINSTANCE.getRuleState_Parameters();

		/**
		 * The meta object literal for the '<em><b>Deleted Matches</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_STATE__DELETED_MATCHES = eINSTANCE.getRuleState_DeletedMatches();

		/**
		 * The meta object literal for the '<em><b>Created Matches</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_STATE__CREATED_MATCHES = eINSTANCE.getRuleState_CreatedMatches();

		/**
		 * The meta object literal for the '<em><b>Match Count</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_STATE__MATCH_COUNT = eINSTANCE.getRuleState_MatchCount();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.gt.StateModel.impl.AttributeDeltaImpl <em>Attribute Delta</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.gt.StateModel.impl.AttributeDeltaImpl
		 * @see org.emoflon.ibex.gt.StateModel.impl.StateModelPackageImpl#getAttributeDelta()
		 * @generated
		 */
		EClass ATTRIBUTE_DELTA = eINSTANCE.getAttributeDelta();

		/**
		 * The meta object literal for the '<em><b>Attribute</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE_DELTA__ATTRIBUTE = eINSTANCE.getAttributeDelta_Attribute();

		/**
		 * The meta object literal for the '<em><b>Object</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE_DELTA__OBJECT = eINSTANCE.getAttributeDelta_Object();

		/**
		 * The meta object literal for the '<em><b>Old Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE_DELTA__OLD_VALUE = eINSTANCE.getAttributeDelta_OldValue();

		/**
		 * The meta object literal for the '<em><b>New Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE_DELTA__NEW_VALUE = eINSTANCE.getAttributeDelta_NewValue();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.gt.StateModel.impl.StructuralDeltaImpl <em>Structural Delta</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.gt.StateModel.impl.StructuralDeltaImpl
		 * @see org.emoflon.ibex.gt.StateModel.impl.StateModelPackageImpl#getStructuralDelta()
		 * @generated
		 */
		EClass STRUCTURAL_DELTA = eINSTANCE.getStructuralDelta();

		/**
		 * The meta object literal for the '<em><b>Created Objects</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRUCTURAL_DELTA__CREATED_OBJECTS = eINSTANCE.getStructuralDelta_CreatedObjects();

		/**
		 * The meta object literal for the '<em><b>Deleted Objects</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRUCTURAL_DELTA__DELETED_OBJECTS = eINSTANCE.getStructuralDelta_DeletedObjects();

		/**
		 * The meta object literal for the '<em><b>Created Links</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRUCTURAL_DELTA__CREATED_LINKS = eINSTANCE.getStructuralDelta_CreatedLinks();

		/**
		 * The meta object literal for the '<em><b>Deleted Links</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRUCTURAL_DELTA__DELETED_LINKS = eINSTANCE.getStructuralDelta_DeletedLinks();

		/**
		 * The meta object literal for the '<em><b>Deleted Root Level Objects</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRUCTURAL_DELTA__DELETED_ROOT_LEVEL_OBJECTS = eINSTANCE
				.getStructuralDelta_DeletedRootLevelObjects();

		/**
		 * The meta object literal for the '<em><b>Resource2 EObject Containment</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRUCTURAL_DELTA__RESOURCE2_EOBJECT_CONTAINMENT = eINSTANCE
				.getStructuralDelta_Resource2EObjectContainment();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.gt.StateModel.impl.LinkImpl <em>Link</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.gt.StateModel.impl.LinkImpl
		 * @see org.emoflon.ibex.gt.StateModel.impl.StateModelPackageImpl#getLink()
		 * @generated
		 */
		EClass LINK = eINSTANCE.getLink();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LINK__TYPE = eINSTANCE.getLink_Type();

		/**
		 * The meta object literal for the '<em><b>Src</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LINK__SRC = eINSTANCE.getLink_Src();

		/**
		 * The meta object literal for the '<em><b>Trg</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LINK__TRG = eINSTANCE.getLink_Trg();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.gt.StateModel.impl.IBeXMatchImpl <em>IBe XMatch</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.gt.StateModel.impl.IBeXMatchImpl
		 * @see org.emoflon.ibex.gt.StateModel.impl.StateModelPackageImpl#getIBeXMatch()
		 * @generated
		 */
		EClass IBE_XMATCH = eINSTANCE.getIBeXMatch();

		/**
		 * The meta object literal for the '<em><b>Pattern Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IBE_XMATCH__PATTERN_NAME = eINSTANCE.getIBeXMatch_PatternName();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XMATCH__PARAMETERS = eINSTANCE.getIBeXMatch_Parameters();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.gt.StateModel.impl.ParameterImpl <em>Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.gt.StateModel.impl.ParameterImpl
		 * @see org.emoflon.ibex.gt.StateModel.impl.StateModelPackageImpl#getParameter()
		 * @generated
		 */
		EClass PARAMETER = eINSTANCE.getParameter();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER__NAME = eINSTANCE.getParameter_Name();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.gt.StateModel.impl.ValueImpl <em>Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.gt.StateModel.impl.ValueImpl
		 * @see org.emoflon.ibex.gt.StateModel.impl.StateModelPackageImpl#getValue()
		 * @generated
		 */
		EClass VALUE = eINSTANCE.getValue();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VALUE__TYPE = eINSTANCE.getValue_Type();

		/**
		 * The meta object literal for the '<em><b>Value As String</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VALUE__VALUE_AS_STRING = eINSTANCE.getValue_ValueAsString();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.gt.StateModel.impl.MatchDeltaImpl <em>Match Delta</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.gt.StateModel.impl.MatchDeltaImpl
		 * @see org.emoflon.ibex.gt.StateModel.impl.StateModelPackageImpl#getMatchDelta()
		 * @generated
		 */
		EClass MATCH_DELTA = eINSTANCE.getMatchDelta();

		/**
		 * The meta object literal for the '<em><b>Pattern Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MATCH_DELTA__PATTERN_NAME = eINSTANCE.getMatchDelta_PatternName();

		/**
		 * The meta object literal for the '<em><b>Match Deltas For Pattern</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MATCH_DELTA__MATCH_DELTAS_FOR_PATTERN = eINSTANCE.getMatchDelta_MatchDeltasForPattern();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.gt.StateModel.impl.ComplexParameterImpl <em>Complex Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.gt.StateModel.impl.ComplexParameterImpl
		 * @see org.emoflon.ibex.gt.StateModel.impl.StateModelPackageImpl#getComplexParameter()
		 * @generated
		 */
		EClass COMPLEX_PARAMETER = eINSTANCE.getComplexParameter();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPLEX_PARAMETER__VALUE = eINSTANCE.getComplexParameter_Value();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.gt.StateModel.impl.SimpleParameterImpl <em>Simple Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.gt.StateModel.impl.SimpleParameterImpl
		 * @see org.emoflon.ibex.gt.StateModel.impl.StateModelPackageImpl#getSimpleParameter()
		 * @generated
		 */
		EClass SIMPLE_PARAMETER = eINSTANCE.getSimpleParameter();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMPLE_PARAMETER__VALUE = eINSTANCE.getSimpleParameter_Value();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SIMPLE_PARAMETER__TYPE = eINSTANCE.getSimpleParameter_Type();

	}

} //StateModelPackage
