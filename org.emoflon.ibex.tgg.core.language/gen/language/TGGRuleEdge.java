/**
 */
package language;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TGG Rule Edge</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link language.TGGRuleEdge#getSrcNode <em>Src Node</em>}</li>
 *   <li>{@link language.TGGRuleEdge#getTrgNode <em>Trg Node</em>}</li>
 *   <li>{@link language.TGGRuleEdge#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see language.LanguagePackage#getTGGRuleEdge()
 * @model
 * @generated
 */
public interface TGGRuleEdge extends EObject, TGGRuleElement {
	/**
	 * Returns the value of the '<em><b>Src Node</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link language.TGGRuleNode#getOutgoingEdges <em>Outgoing Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Src Node</em>' reference.
	 * @see #setSrcNode(TGGRuleNode)
	 * @see language.LanguagePackage#getTGGRuleEdge_SrcNode()
	 * @see language.TGGRuleNode#getOutgoingEdges
	 * @model opposite="outgoingEdges"
	 * @generated
	 */
	TGGRuleNode getSrcNode();

	/**
	 * Sets the value of the '{@link language.TGGRuleEdge#getSrcNode <em>Src Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Src Node</em>' reference.
	 * @see #getSrcNode()
	 * @generated
	 */
	void setSrcNode(TGGRuleNode value);

	/**
	 * Returns the value of the '<em><b>Trg Node</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link language.TGGRuleNode#getIncomingEdges <em>Incoming Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Trg Node</em>' reference.
	 * @see #setTrgNode(TGGRuleNode)
	 * @see language.LanguagePackage#getTGGRuleEdge_TrgNode()
	 * @see language.TGGRuleNode#getIncomingEdges
	 * @model opposite="incomingEdges"
	 * @generated
	 */
	TGGRuleNode getTrgNode();

	/**
	 * Sets the value of the '{@link language.TGGRuleEdge#getTrgNode <em>Trg Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Trg Node</em>' reference.
	 * @see #getTrgNode()
	 * @generated
	 */
	void setTrgNode(TGGRuleNode value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(EReference)
	 * @see language.LanguagePackage#getTGGRuleEdge_Type()
	 * @model
	 * @generated
	 */
	EReference getType();

	/**
	 * Sets the value of the '{@link language.TGGRuleEdge#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(EReference value);

} // TGGRuleEdge
