/**
 */
package org.emoflon.ibex.gt.StateModel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>State Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.StateContainer#getStates <em>States</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.StateContainer#getInitialState <em>Initial State</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.StateContainer#getInitialMatches <em>Initial Matches</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getStateContainer()
 * @model
 * @generated
 */
public interface StateContainer extends EObject {
	/**
	 * Returns the value of the '<em><b>States</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.gt.StateModel.State}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>States</em>' containment reference list.
	 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getStateContainer_States()
	 * @model containment="true"
	 * @generated
	 */
	EList<State> getStates();

	/**
	 * Returns the value of the '<em><b>Initial State</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Initial State</em>' containment reference.
	 * @see #setInitialState(State)
	 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getStateContainer_InitialState()
	 * @model containment="true"
	 * @generated
	 */
	State getInitialState();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.gt.StateModel.StateContainer#getInitialState <em>Initial State</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Initial State</em>' containment reference.
	 * @see #getInitialState()
	 * @generated
	 */
	void setInitialState(State value);

	/**
	 * Returns the value of the '<em><b>Initial Matches</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.gt.StateModel.IBeXMatch}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Initial Matches</em>' containment reference list.
	 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getStateContainer_InitialMatches()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBeXMatch> getInitialMatches();

} // StateContainer