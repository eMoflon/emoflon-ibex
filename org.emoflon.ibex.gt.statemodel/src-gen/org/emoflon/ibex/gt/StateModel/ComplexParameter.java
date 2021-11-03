/**
 */
package org.emoflon.ibex.gt.StateModel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Complex Parameter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.ComplexParameter#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getComplexParameter()
 * @model
 * @generated
 */
public interface ComplexParameter extends Parameter {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' reference.
	 * @see #setValue(EObject)
	 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getComplexParameter_Value()
	 * @model
	 * @generated
	 */
	EObject getValue();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.gt.StateModel.ComplexParameter#getValue <em>Value</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' reference.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(EObject value);

} // ComplexParameter
