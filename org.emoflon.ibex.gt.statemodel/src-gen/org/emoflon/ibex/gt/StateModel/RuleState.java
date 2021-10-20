/**
 */
package org.emoflon.ibex.gt.StateModel;

import org.eclipse.emf.common.util.EList;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Rule State</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.RuleState#getParent <em>Parent</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.RuleState#getRule <em>Rule</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.RuleState#getStructuralDelta <em>Structural Delta</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.RuleState#getAttributeDeltas <em>Attribute Deltas</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.RuleState#getMatch <em>Match</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.RuleState#getCoMatch <em>Co Match</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.RuleState#getParameters <em>Parameters</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.RuleState#getDeletedMatches <em>Deleted Matches</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.RuleState#getCreatedMatches <em>Created Matches</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.RuleState#getMatchCount <em>Match Count</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getRuleState()
 * @model
 * @generated
 */
public interface RuleState extends State {
	/**
	 * Returns the value of the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent</em>' reference.
	 * @see #setParent(State)
	 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getRuleState_Parent()
	 * @model
	 * @generated
	 */
	State getParent();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.gt.StateModel.RuleState#getParent <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(State value);

	/**
	 * Returns the value of the '<em><b>Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule</em>' reference.
	 * @see #setRule(IBeXRule)
	 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getRuleState_Rule()
	 * @model
	 * @generated
	 */
	IBeXRule getRule();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.gt.StateModel.RuleState#getRule <em>Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule</em>' reference.
	 * @see #getRule()
	 * @generated
	 */
	void setRule(IBeXRule value);

	/**
	 * Returns the value of the '<em><b>Structural Delta</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Structural Delta</em>' containment reference.
	 * @see #setStructuralDelta(StructuralDelta)
	 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getRuleState_StructuralDelta()
	 * @model containment="true"
	 * @generated
	 */
	StructuralDelta getStructuralDelta();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.gt.StateModel.RuleState#getStructuralDelta <em>Structural Delta</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Structural Delta</em>' containment reference.
	 * @see #getStructuralDelta()
	 * @generated
	 */
	void setStructuralDelta(StructuralDelta value);

	/**
	 * Returns the value of the '<em><b>Attribute Deltas</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.gt.StateModel.AttributeDelta}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Deltas</em>' containment reference list.
	 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getRuleState_AttributeDeltas()
	 * @model containment="true"
	 * @generated
	 */
	EList<AttributeDelta> getAttributeDeltas();

	/**
	 * Returns the value of the '<em><b>Match</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Match</em>' containment reference.
	 * @see #setMatch(IBeXMatch)
	 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getRuleState_Match()
	 * @model containment="true"
	 * @generated
	 */
	IBeXMatch getMatch();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.gt.StateModel.RuleState#getMatch <em>Match</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Match</em>' containment reference.
	 * @see #getMatch()
	 * @generated
	 */
	void setMatch(IBeXMatch value);

	/**
	 * Returns the value of the '<em><b>Co Match</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Co Match</em>' containment reference.
	 * @see #setCoMatch(IBeXMatch)
	 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getRuleState_CoMatch()
	 * @model containment="true"
	 * @generated
	 */
	IBeXMatch getCoMatch();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.gt.StateModel.RuleState#getCoMatch <em>Co Match</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Co Match</em>' containment reference.
	 * @see #getCoMatch()
	 * @generated
	 */
	void setCoMatch(IBeXMatch value);

	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.gt.StateModel.Parameter}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' containment reference list.
	 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getRuleState_Parameters()
	 * @model containment="true"
	 * @generated
	 */
	EList<Parameter> getParameters();

	/**
	 * Returns the value of the '<em><b>Deleted Matches</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.gt.StateModel.MatchDelta}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deleted Matches</em>' containment reference list.
	 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getRuleState_DeletedMatches()
	 * @model containment="true"
	 * @generated
	 */
	EList<MatchDelta> getDeletedMatches();

	/**
	 * Returns the value of the '<em><b>Created Matches</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.gt.StateModel.MatchDelta}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Created Matches</em>' containment reference list.
	 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getRuleState_CreatedMatches()
	 * @model containment="true"
	 * @generated
	 */
	EList<MatchDelta> getCreatedMatches();

	/**
	 * Returns the value of the '<em><b>Match Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Match Count</em>' attribute.
	 * @see #setMatchCount(long)
	 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getRuleState_MatchCount()
	 * @model
	 * @generated
	 */
	long getMatchCount();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.gt.StateModel.RuleState#getMatchCount <em>Match Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Match Count</em>' attribute.
	 * @see #getMatchCount()
	 * @generated
	 */
	void setMatchCount(long value);

} // RuleState
