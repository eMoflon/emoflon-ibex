/**
 */
package language;

import language.inplaceAttributes.TGGInplaceAttributeExpression;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TGG Rule Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link language.TGGRuleNode#getIncomingEdges <em>Incoming Edges</em>}</li>
 *   <li>{@link language.TGGRuleNode#getOutgoingEdges <em>Outgoing Edges</em>}</li>
 *   <li>{@link language.TGGRuleNode#getType <em>Type</em>}</li>
 *   <li>{@link language.TGGRuleNode#getIncomingCorrsSource <em>Incoming Corrs Source</em>}</li>
 *   <li>{@link language.TGGRuleNode#getIncomingCorrsTarget <em>Incoming Corrs Target</em>}</li>
 *   <li>{@link language.TGGRuleNode#getAttrExpr <em>Attr Expr</em>}</li>
 * </ul>
 * </p>
 *
 * @see language.LanguagePackage#getTGGRuleNode()
 * @model
 * @generated
 */
public interface TGGRuleNode extends TGGRuleElement {
	/**
	 * Returns the value of the '<em><b>Incoming Edges</b></em>' reference list.
	 * The list contents are of type {@link language.TGGRuleEdge}.
	 * It is bidirectional and its opposite is '{@link language.TGGRuleEdge#getTrgNode <em>Trg Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Incoming Edges</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Incoming Edges</em>' reference list.
	 * @see language.LanguagePackage#getTGGRuleNode_IncomingEdges()
	 * @see language.TGGRuleEdge#getTrgNode
	 * @model opposite="trgNode"
	 * @generated
	 */
	EList<TGGRuleEdge> getIncomingEdges();

	/**
	 * Returns the value of the '<em><b>Outgoing Edges</b></em>' reference list.
	 * The list contents are of type {@link language.TGGRuleEdge}.
	 * It is bidirectional and its opposite is '{@link language.TGGRuleEdge#getSrcNode <em>Src Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Outgoing Edges</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outgoing Edges</em>' reference list.
	 * @see language.LanguagePackage#getTGGRuleNode_OutgoingEdges()
	 * @see language.TGGRuleEdge#getSrcNode
	 * @model opposite="srcNode"
	 * @generated
	 */
	EList<TGGRuleEdge> getOutgoingEdges();

	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(EClass)
	 * @see language.LanguagePackage#getTGGRuleNode_Type()
	 * @model
	 * @generated
	 */
	EClass getType();

	/**
	 * Sets the value of the '{@link language.TGGRuleNode#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(EClass value);

	/**
	 * Returns the value of the '<em><b>Incoming Corrs Source</b></em>' reference list.
	 * The list contents are of type {@link language.TGGRuleCorr}.
	 * It is bidirectional and its opposite is '{@link language.TGGRuleCorr#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Incoming Corrs Source</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Incoming Corrs Source</em>' reference list.
	 * @see language.LanguagePackage#getTGGRuleNode_IncomingCorrsSource()
	 * @see language.TGGRuleCorr#getSource
	 * @model opposite="source"
	 * @generated
	 */
	EList<TGGRuleCorr> getIncomingCorrsSource();

	/**
	 * Returns the value of the '<em><b>Incoming Corrs Target</b></em>' reference list.
	 * The list contents are of type {@link language.TGGRuleCorr}.
	 * It is bidirectional and its opposite is '{@link language.TGGRuleCorr#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Incoming Corrs Target</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Incoming Corrs Target</em>' reference list.
	 * @see language.LanguagePackage#getTGGRuleNode_IncomingCorrsTarget()
	 * @see language.TGGRuleCorr#getTarget
	 * @model opposite="target"
	 * @generated
	 */
	EList<TGGRuleCorr> getIncomingCorrsTarget();

	/**
	 * Returns the value of the '<em><b>Attr Expr</b></em>' containment reference list.
	 * The list contents are of type {@link language.inplaceAttributes.TGGInplaceAttributeExpression}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attr Expr</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attr Expr</em>' containment reference list.
	 * @see language.LanguagePackage#getTGGRuleNode_AttrExpr()
	 * @model containment="true"
	 * @generated
	 */
	EList<TGGInplaceAttributeExpression> getAttrExpr();
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} // TGGRuleNode
