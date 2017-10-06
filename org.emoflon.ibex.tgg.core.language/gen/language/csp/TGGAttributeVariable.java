/**
 */
package language.csp;

import language.basic.expressions.TGGParamValue;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TGG Attribute Variable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link language.csp.TGGAttributeVariable#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see language.csp.CspPackage#getTGGAttributeVariable()
 * @model
 * @generated
 */
public interface TGGAttributeVariable extends TGGParamValue {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see language.csp.CspPackage#getTGGAttributeVariable_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link language.csp.TGGAttributeVariable#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // TGGAttributeVariable
