/**
 */
package org.emoflon.ibex.gt.StateModel;

import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.Value#getType <em>Type</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.Value#getValueAsString <em>Value As String</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getValue()
 * @model
 * @generated
 */
public interface Value extends EObject {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(EDataType)
	 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getValue_Type()
	 * @model
	 * @generated
	 */
	EDataType getType();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.gt.StateModel.Value#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(EDataType value);

	/**
	 * Returns the value of the '<em><b>Value As String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value As String</em>' attribute.
	 * @see #setValueAsString(String)
	 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getValue_ValueAsString()
	 * @model
	 * @generated
	 */
	String getValueAsString();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.gt.StateModel.Value#getValueAsString <em>Value As String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value As String</em>' attribute.
	 * @see #getValueAsString()
	 * @generated
	 */
	void setValueAsString(String value);

} // Value
