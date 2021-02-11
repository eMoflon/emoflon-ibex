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
	 * The feature id for the '<em><b>Initial State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_CONTAINER__INITIAL_STATE = 1;

	/**
	 * The number of structural features of the '<em>State Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_CONTAINER_FEATURE_COUNT = 2;

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
	 * The number of structural features of the '<em>State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_FEATURE_COUNT = 3;

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
	 * The feature id for the '<em><b>Parameter</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_STATE__PARAMETER = STATE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Match</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_STATE__MATCH = STATE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Co Match</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_STATE__CO_MATCH = STATE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Structural Delta</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_STATE__STRUCTURAL_DELTA = STATE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Attribute Deltas</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_STATE__ATTRIBUTE_DELTAS = STATE_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Rule State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_STATE_FEATURE_COUNT = STATE_FEATURE_COUNT + 7;

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
	 * The feature id for the '<em><b>New Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DELTA__NEW_VALUE = 2;

	/**
	 * The feature id for the '<em><b>Old Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DELTA__OLD_VALUE = 3;

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
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.gt.StateModel.StateContainer#getInitialState <em>Initial State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Initial State</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.StateContainer#getInitialState()
	 * @see #getStateContainer()
	 * @generated
	 */
	EReference getStateContainer_InitialState();

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
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.gt.StateModel.RuleState#getParameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Parameter</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.RuleState#getParameter()
	 * @see #getRuleState()
	 * @generated
	 */
	EAttribute getRuleState_Parameter();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.gt.StateModel.RuleState#getMatch <em>Match</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Match</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.RuleState#getMatch()
	 * @see #getRuleState()
	 * @generated
	 */
	EAttribute getRuleState_Match();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.gt.StateModel.RuleState#getCoMatch <em>Co Match</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Co Match</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.RuleState#getCoMatch()
	 * @see #getRuleState()
	 * @generated
	 */
	EAttribute getRuleState_CoMatch();

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
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.gt.StateModel.AttributeDelta#getNewValue <em>New Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>New Value</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.AttributeDelta#getNewValue()
	 * @see #getAttributeDelta()
	 * @generated
	 */
	EAttribute getAttributeDelta_NewValue();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.gt.StateModel.AttributeDelta#getOldValue <em>Old Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Old Value</em>'.
	 * @see org.emoflon.ibex.gt.StateModel.AttributeDelta#getOldValue()
	 * @see #getAttributeDelta()
	 * @generated
	 */
	EAttribute getAttributeDelta_OldValue();

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
		 * The meta object literal for the '<em><b>Initial State</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE_CONTAINER__INITIAL_STATE = eINSTANCE.getStateContainer_InitialState();

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
		 * The meta object literal for the '<em><b>Parameter</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_STATE__PARAMETER = eINSTANCE.getRuleState_Parameter();

		/**
		 * The meta object literal for the '<em><b>Match</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_STATE__MATCH = eINSTANCE.getRuleState_Match();

		/**
		 * The meta object literal for the '<em><b>Co Match</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_STATE__CO_MATCH = eINSTANCE.getRuleState_CoMatch();

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
		 * The meta object literal for the '<em><b>New Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE_DELTA__NEW_VALUE = eINSTANCE.getAttributeDelta_NewValue();

		/**
		 * The meta object literal for the '<em><b>Old Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE_DELTA__OLD_VALUE = eINSTANCE.getAttributeDelta_OldValue();

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

	}

} //StateModelPackage
