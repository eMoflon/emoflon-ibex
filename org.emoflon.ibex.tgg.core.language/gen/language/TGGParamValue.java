/**
 */
package language;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TGG Param Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link language.TGGParamValue#getParameterDefinition <em>Parameter Definition</em>}</li>
 * </ul>
 *
 * @see language.LanguagePackage#getTGGParamValue()
 * @model
 * @generated
 */
public interface TGGParamValue extends EObject {
	/**
	 * Returns the value of the '<em><b>Parameter Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameter Definition</em>' reference.
	 * @see #setParameterDefinition(TGGAttributeConstraintParameterDefinition)
	 * @see language.LanguagePackage#getTGGParamValue_ParameterDefinition()
	 * @model
	 * @generated
	 */
	TGGAttributeConstraintParameterDefinition getParameterDefinition();

	/**
	 * Sets the value of the '{@link language.TGGParamValue#getParameterDefinition <em>Parameter Definition</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parameter Definition</em>' reference.
	 * @see #getParameterDefinition()
	 * @generated
	 */
	void setParameterDefinition(TGGAttributeConstraintParameterDefinition value);

} // TGGParamValue
