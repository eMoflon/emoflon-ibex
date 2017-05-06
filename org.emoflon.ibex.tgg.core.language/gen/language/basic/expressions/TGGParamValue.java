/**
 */
package language.basic.expressions;

import language.csp.definition.TGGAttributeConstraintParameterDefinition;

import org.eclipse.emf.ecore.EObject;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TGG Param Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link language.basic.expressions.TGGParamValue#getParameterDefinition <em>Parameter Definition</em>}</li>
 * </ul>
 * </p>
 *
 * @see language.basic.expressions.ExpressionsPackage#getTGGParamValue()
 * @model
 * @generated
 */
public interface TGGParamValue extends EObject {
	/**
	 * Returns the value of the '<em><b>Parameter Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameter Definition</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameter Definition</em>' reference.
	 * @see #setParameterDefinition(TGGAttributeConstraintParameterDefinition)
	 * @see language.basic.expressions.ExpressionsPackage#getTGGParamValue_ParameterDefinition()
	 * @model
	 * @generated
	 */
	TGGAttributeConstraintParameterDefinition getParameterDefinition();

	/**
	 * Sets the value of the '{@link language.basic.expressions.TGGParamValue#getParameterDefinition <em>Parameter Definition</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parameter Definition</em>' reference.
	 * @see #getParameterDefinition()
	 * @generated
	 */
	void setParameterDefinition(TGGAttributeConstraintParameterDefinition value);
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} // TGGParamValue
