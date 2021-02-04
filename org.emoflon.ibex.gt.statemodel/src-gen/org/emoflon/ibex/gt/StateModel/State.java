/**
 */
package org.emoflon.ibex.gt.StateModel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>State</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.State#isInitial <em>Initial</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.State#getChildren <em>Children</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.State#getHash <em>Hash</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getState()
 * @model
 * @generated
 */
public interface State extends EObject {
	/**
	 * Returns the value of the '<em><b>Initial</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Initial</em>' attribute.
	 * @see #setInitial(boolean)
	 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getState_Initial()
	 * @model
	 * @generated
	 */
	boolean isInitial();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.gt.StateModel.State#isInitial <em>Initial</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Initial</em>' attribute.
	 * @see #isInitial()
	 * @generated
	 */
	void setInitial(boolean value);

	/**
	 * Returns the value of the '<em><b>Children</b></em>' reference list.
	 * The list contents are of type {@link org.emoflon.ibex.gt.StateModel.RuleState}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Children</em>' reference list.
	 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getState_Children()
	 * @model
	 * @generated
	 */
	EList<RuleState> getChildren();

	/**
	 * Returns the value of the '<em><b>Hash</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hash</em>' attribute.
	 * @see #setHash(long)
	 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getState_Hash()
	 * @model
	 * @generated
	 */
	long getHash();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.gt.StateModel.State#getHash <em>Hash</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hash</em>' attribute.
	 * @see #getHash()
	 * @generated
	 */
	void setHash(long value);

} // State
