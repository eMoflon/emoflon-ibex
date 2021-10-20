/**
 */
package org.emoflon.ibex.gt.StateModel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>All Matches</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.AllMatches#getPatternName <em>Pattern Name</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.AllMatches#getAllMatchesForPattern <em>All Matches For Pattern</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getAllMatches()
 * @model
 * @generated
 */
public interface AllMatches extends EObject {
	/**
	 * Returns the value of the '<em><b>Pattern Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pattern Name</em>' attribute.
	 * @see #setPatternName(String)
	 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getAllMatches_PatternName()
	 * @model
	 * @generated
	 */
	String getPatternName();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.gt.StateModel.AllMatches#getPatternName <em>Pattern Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pattern Name</em>' attribute.
	 * @see #getPatternName()
	 * @generated
	 */
	void setPatternName(String value);

	/**
	 * Returns the value of the '<em><b>All Matches For Pattern</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.gt.StateModel.IBeXMatch}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>All Matches For Pattern</em>' containment reference list.
	 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getAllMatches_AllMatchesForPattern()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBeXMatch> getAllMatchesForPattern();

} // AllMatches
