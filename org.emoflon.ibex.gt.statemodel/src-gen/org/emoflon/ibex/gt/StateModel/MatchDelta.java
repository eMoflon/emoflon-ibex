/**
 */
package org.emoflon.ibex.gt.StateModel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Match Delta</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.MatchDelta#getPatternName <em>Pattern Name</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.MatchDelta#getMatchDeltasForPattern <em>Match Deltas For Pattern</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getMatchDelta()
 * @model
 * @generated
 */
public interface MatchDelta extends EObject {
	/**
	 * Returns the value of the '<em><b>Pattern Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pattern Name</em>' attribute.
	 * @see #setPatternName(String)
	 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getMatchDelta_PatternName()
	 * @model
	 * @generated
	 */
	String getPatternName();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.gt.StateModel.MatchDelta#getPatternName <em>Pattern Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pattern Name</em>' attribute.
	 * @see #getPatternName()
	 * @generated
	 */
	void setPatternName(String value);

	/**
	 * Returns the value of the '<em><b>Match Deltas For Pattern</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.gt.StateModel.IBeXMatch}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Match Deltas For Pattern</em>' containment reference list.
	 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getMatchDelta_MatchDeltasForPattern()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBeXMatch> getMatchDeltasForPattern();

} // MatchDelta
