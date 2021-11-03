/**
 */
package org.emoflon.ibex.gt.StateModel;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attribute Delta</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.AttributeDelta#getAttribute <em>Attribute</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.AttributeDelta#getObject <em>Object</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.AttributeDelta#getOldValue <em>Old Value</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.AttributeDelta#getNewValue <em>New Value</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getAttributeDelta()
 * @model
 * @generated
 */
public interface AttributeDelta extends EObject {
	/**
	 * Returns the value of the '<em><b>Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute</em>' reference.
	 * @see #setAttribute(EAttribute)
	 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getAttributeDelta_Attribute()
	 * @model required="true"
	 * @generated
	 */
	EAttribute getAttribute();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.gt.StateModel.AttributeDelta#getAttribute <em>Attribute</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute</em>' reference.
	 * @see #getAttribute()
	 * @generated
	 */
	void setAttribute(EAttribute value);

	/**
	 * Returns the value of the '<em><b>Object</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Object</em>' reference.
	 * @see #setObject(EObject)
	 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getAttributeDelta_Object()
	 * @model required="true"
	 * @generated
	 */
	EObject getObject();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.gt.StateModel.AttributeDelta#getObject <em>Object</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Object</em>' reference.
	 * @see #getObject()
	 * @generated
	 */
	void setObject(EObject value);

	/**
	 * Returns the value of the '<em><b>Old Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Old Value</em>' containment reference.
	 * @see #setOldValue(Value)
	 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getAttributeDelta_OldValue()
	 * @model containment="true"
	 * @generated
	 */
	Value getOldValue();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.gt.StateModel.AttributeDelta#getOldValue <em>Old Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Old Value</em>' containment reference.
	 * @see #getOldValue()
	 * @generated
	 */
	void setOldValue(Value value);

	/**
	 * Returns the value of the '<em><b>New Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>New Value</em>' containment reference.
	 * @see #setNewValue(Value)
	 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getAttributeDelta_NewValue()
	 * @model containment="true"
	 * @generated
	 */
	Value getNewValue();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.gt.StateModel.AttributeDelta#getNewValue <em>New Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>New Value</em>' containment reference.
	 * @see #getNewValue()
	 * @generated
	 */
	void setNewValue(Value value);

} // AttributeDelta