/**
 */
package language.basic.expressions;

import language.TGGRuleNode;

import org.eclipse.emf.ecore.EAttribute;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TGG Attribute Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link language.basic.expressions.TGGAttributeExpression#getObjectVar <em>Object Var</em>}</li>
 *   <li>{@link language.basic.expressions.TGGAttributeExpression#getAttribute <em>Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @see language.basic.expressions.ExpressionsPackage#getTGGAttributeExpression()
 * @model
 * @generated
 */
public interface TGGAttributeExpression extends TGGExpression {
	/**
	 * Returns the value of the '<em><b>Object Var</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Object Var</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Object Var</em>' reference.
	 * @see #setObjectVar(TGGRuleNode)
	 * @see language.basic.expressions.ExpressionsPackage#getTGGAttributeExpression_ObjectVar()
	 * @model
	 * @generated
	 */
	TGGRuleNode getObjectVar();

	/**
	 * Sets the value of the '{@link language.basic.expressions.TGGAttributeExpression#getObjectVar <em>Object Var</em>}' reference.
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
	 * <p>
	 * If the meaning of the '<em>Attribute</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute</em>' reference.
	 * @see #setAttribute(EAttribute)
	 * @see language.basic.expressions.ExpressionsPackage#getTGGAttributeExpression_Attribute()
	 * @model
	 * @generated
	 */
	EAttribute getAttribute();

	/**
	 * Sets the value of the '{@link language.basic.expressions.TGGAttributeExpression#getAttribute <em>Attribute</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute</em>' reference.
	 * @see #getAttribute()
	 * @generated
	 */
	void setAttribute(EAttribute value);
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} // TGGAttributeExpression
