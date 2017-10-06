/**
 */
package language.basic.expressions;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TGG Literal Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link language.basic.expressions.TGGLiteralExpression#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see language.basic.expressions.ExpressionsPackage#getTGGLiteralExpression()
 * @model
 * @generated
 */
public interface TGGLiteralExpression extends TGGExpression {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see language.basic.expressions.ExpressionsPackage#getTGGLiteralExpression_Value()
	 * @model
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link language.basic.expressions.TGGLiteralExpression#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

} // TGGLiteralExpression
