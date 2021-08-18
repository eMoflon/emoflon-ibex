/**
 */
package org.emoflon.ibex.gt.StateModel;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Link</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.Link#getType <em>Type</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.Link#getSrc <em>Src</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.Link#getTrg <em>Trg</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getLink()
 * @model
 * @generated
 */
public interface Link extends EObject {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(EReference)
	 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getLink_Type()
	 * @model required="true"
	 * @generated
	 */
	EReference getType();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.gt.StateModel.Link#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(EReference value);

	/**
	 * Returns the value of the '<em><b>Src</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Src</em>' reference.
	 * @see #setSrc(EObject)
	 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getLink_Src()
	 * @model required="true"
	 * @generated
	 */
	EObject getSrc();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.gt.StateModel.Link#getSrc <em>Src</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Src</em>' reference.
	 * @see #getSrc()
	 * @generated
	 */
	void setSrc(EObject value);

	/**
	 * Returns the value of the '<em><b>Trg</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Trg</em>' reference.
	 * @see #setTrg(EObject)
	 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getLink_Trg()
	 * @model required="true"
	 * @generated
	 */
	EObject getTrg();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.gt.StateModel.Link#getTrg <em>Trg</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Trg</em>' reference.
	 * @see #getTrg()
	 * @generated
	 */
	void setTrg(EObject value);

} // Link
