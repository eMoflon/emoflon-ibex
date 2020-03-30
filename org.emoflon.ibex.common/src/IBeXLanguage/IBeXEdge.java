/**
 */
package IBeXLanguage;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XEdge</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A typed edge connects two nodes. 
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link IBeXLanguage.IBeXEdge#getSourceNode <em>Source Node</em>}</li>
 *   <li>{@link IBeXLanguage.IBeXEdge#getTargetNode <em>Target Node</em>}</li>
 *   <li>{@link IBeXLanguage.IBeXEdge#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXEdge()
 * @model
 * @generated
 */
public interface IBeXEdge extends EObject {
	/**
	 * Returns the value of the '<em><b>Source Node</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link IBeXLanguage.IBeXNode#getOutgoingEdges <em>Outgoing Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Node</em>' reference.
	 * @see #setSourceNode(IBeXNode)
	 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXEdge_SourceNode()
	 * @see IBeXLanguage.IBeXNode#getOutgoingEdges
	 * @model opposite="outgoingEdges"
	 * @generated
	 */
	IBeXNode getSourceNode();

	/**
	 * Sets the value of the '{@link IBeXLanguage.IBeXEdge#getSourceNode <em>Source Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Node</em>' reference.
	 * @see #getSourceNode()
	 * @generated
	 */
	void setSourceNode(IBeXNode value);

	/**
	 * Returns the value of the '<em><b>Target Node</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link IBeXLanguage.IBeXNode#getIncomingEdges <em>Incoming Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Node</em>' reference.
	 * @see #setTargetNode(IBeXNode)
	 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXEdge_TargetNode()
	 * @see IBeXLanguage.IBeXNode#getIncomingEdges
	 * @model opposite="incomingEdges"
	 * @generated
	 */
	IBeXNode getTargetNode();

	/**
	 * Sets the value of the '{@link IBeXLanguage.IBeXEdge#getTargetNode <em>Target Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Node</em>' reference.
	 * @see #getTargetNode()
	 * @generated
	 */
	void setTargetNode(IBeXNode value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(EReference)
	 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXEdge_Type()
	 * @model
	 * @generated
	 */
	EReference getType();

	/**
	 * Sets the value of the '{@link IBeXLanguage.IBeXEdge#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(EReference value);

} // IBeXEdge
