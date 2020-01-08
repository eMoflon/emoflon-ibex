/**
 */
package IBeXLanguage;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XNode</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A typed node can reference other nodes via edges.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link IBeXLanguage.IBeXNode#getIncomingEdges <em>Incoming Edges</em>}</li>
 *   <li>{@link IBeXLanguage.IBeXNode#getOutgoingEdges <em>Outgoing Edges</em>}</li>
 *   <li>{@link IBeXLanguage.IBeXNode#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXNode()
 * @model
 * @generated
 */
public interface IBeXNode extends IBeXNamedElement {
	/**
	 * Returns the value of the '<em><b>Incoming Edges</b></em>' reference list.
	 * The list contents are of type {@link IBeXLanguage.IBeXEdge}.
	 * It is bidirectional and its opposite is '{@link IBeXLanguage.IBeXEdge#getTargetNode <em>Target Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Incoming Edges</em>' reference list.
	 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXNode_IncomingEdges()
	 * @see IBeXLanguage.IBeXEdge#getTargetNode
	 * @model opposite="targetNode"
	 * @generated
	 */
	EList<IBeXEdge> getIncomingEdges();

	/**
	 * Returns the value of the '<em><b>Outgoing Edges</b></em>' reference list.
	 * The list contents are of type {@link IBeXLanguage.IBeXEdge}.
	 * It is bidirectional and its opposite is '{@link IBeXLanguage.IBeXEdge#getSourceNode <em>Source Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outgoing Edges</em>' reference list.
	 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXNode_OutgoingEdges()
	 * @see IBeXLanguage.IBeXEdge#getSourceNode
	 * @model opposite="sourceNode"
	 * @generated
	 */
	EList<IBeXEdge> getOutgoingEdges();

	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(EClass)
	 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXNode_Type()
	 * @model
	 * @generated
	 */
	EClass getType();

	/**
	 * Sets the value of the '{@link IBeXLanguage.IBeXNode#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(EClass value);

} // IBeXNode
