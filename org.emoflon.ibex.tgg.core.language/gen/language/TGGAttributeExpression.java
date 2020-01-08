/**
 */
package language;

import org.eclipse.emf.ecore.EAttribute;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TGG Attribute Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link language.TGGAttributeExpression#getObjectVar <em>Object Var</em>}</li>
 *   <li>{@link language.TGGAttributeExpression#getAttribute <em>Attribute</em>}</li>
 * </ul>
 *
 * @see language.LanguagePackage#getTGGAttributeExpression()
 * @model
 * @generated
 */
public interface TGGAttributeExpression extends TGGExpression {
	/**
	 * Returns the value of the '<em><b>Object Var</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Object Var</em>' reference.
	 * @see #setObjectVar(TGGRuleNode)
	 * @see language.LanguagePackage#getTGGAttributeExpression_ObjectVar()
	 * @model
	 * @generated
	 */
	TGGRuleNode getObjectVar();

	/**
	 * Sets the value of the '{@link language.TGGAttributeExpression#getObjectVar <em>Object Var</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Object Var</em>' reference.
	 * @see #getObjectVar()
	 * @generated
	 */
	void setObjectVar(TGGRuleNode value);

	/**
	 * Returns the value of the '<em><b>Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute</em>' reference.
	 * @see #setAttribute(EAttribute)
	 * @see language.LanguagePackage#getTGGAttributeExpression_Attribute()
	 * @model
	 * @generated
	 */
	EAttribute getAttribute();

	/**
	 * Sets the value of the '{@link language.TGGAttributeExpression#getAttribute <em>Attribute</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute</em>' reference.
	 * @see #getAttribute()
	 * @generated
	 */
	void setAttribute(EAttribute value);

} // TGGAttributeExpression
