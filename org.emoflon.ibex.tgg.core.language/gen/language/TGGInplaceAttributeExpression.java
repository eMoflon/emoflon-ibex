/**
 */
package language;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TGG Inplace Attribute Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link language.TGGInplaceAttributeExpression#getAttribute <em>Attribute</em>}</li>
 *   <li>{@link language.TGGInplaceAttributeExpression#getValueExpr <em>Value Expr</em>}</li>
 *   <li>{@link language.TGGInplaceAttributeExpression#getOperator <em>Operator</em>}</li>
 * </ul>
 * </p>
 *
 * @see language.LanguagePackage#getTGGInplaceAttributeExpression()
 * @model
 * @generated
 */
public interface TGGInplaceAttributeExpression extends EObject {
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
	 * @see language.LanguagePackage#getTGGInplaceAttributeExpression_Attribute()
	 * @model required="true"
	 * @generated
	 */
	EAttribute getAttribute();

	/**
	 * Sets the value of the '{@link language.TGGInplaceAttributeExpression#getAttribute <em>Attribute</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute</em>' reference.
	 * @see #getAttribute()
	 * @generated
	 */
	void setAttribute(EAttribute value);

	/**
	 * Returns the value of the '<em><b>Value Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value Expr</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value Expr</em>' containment reference.
	 * @see #setValueExpr(TGGExpression)
	 * @see language.LanguagePackage#getTGGInplaceAttributeExpression_ValueExpr()
	 * @model containment="true"
	 * @generated
	 */
	TGGExpression getValueExpr();

	/**
	 * Sets the value of the '{@link language.TGGInplaceAttributeExpression#getValueExpr <em>Value Expr</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value Expr</em>' containment reference.
	 * @see #getValueExpr()
	 * @generated
	 */
	void setValueExpr(TGGExpression value);

	/**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute.
	 * The default value is <code>"EQUAL"</code>.
	 * The literals are from the enumeration {@link language.TGGAttributeConstraintOperators}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operator</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see language.TGGAttributeConstraintOperators
	 * @see #setOperator(TGGAttributeConstraintOperators)
	 * @see language.LanguagePackage#getTGGInplaceAttributeExpression_Operator()
	 * @model default="EQUAL" required="true"
	 * @generated
	 */
	TGGAttributeConstraintOperators getOperator();

	/**
	 * Sets the value of the '{@link language.TGGInplaceAttributeExpression#getOperator <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operator</em>' attribute.
	 * @see language.TGGAttributeConstraintOperators
	 * @see #getOperator()
	 * @generated
	 */
	void setOperator(TGGAttributeConstraintOperators value);
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} // TGGInplaceAttributeExpression
