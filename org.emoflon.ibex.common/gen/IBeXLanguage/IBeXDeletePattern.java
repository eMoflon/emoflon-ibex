/**
 */
package IBeXLanguage;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XDelete Pattern</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link IBeXLanguage.IBeXDeletePattern#getContextNodes <em>Context Nodes</em>}</li>
 *   <li>{@link IBeXLanguage.IBeXDeletePattern#getDeletedEdges <em>Deleted Edges</em>}</li>
 *   <li>{@link IBeXLanguage.IBeXDeletePattern#getDeletedNodes <em>Deleted Nodes</em>}</li>
 * </ul>
 * </p>
 *
 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXDeletePattern()
 * @model
 * @generated
 */
public interface IBeXDeletePattern extends EObject, IBeXPattern {
	/**
	 * Returns the value of the '<em><b>Context Nodes</b></em>' containment reference list.
	 * The list contents are of type {@link IBeXLanguage.IBeXNode}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Context Nodes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Context Nodes</em>' containment reference list.
	 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXDeletePattern_ContextNodes()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBeXNode> getContextNodes();

	/**
	 * Returns the value of the '<em><b>Deleted Edges</b></em>' containment reference list.
	 * The list contents are of type {@link IBeXLanguage.IBeXEdge}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Deleted Edges</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deleted Edges</em>' containment reference list.
	 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXDeletePattern_DeletedEdges()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBeXEdge> getDeletedEdges();

	/**
	 * Returns the value of the '<em><b>Deleted Nodes</b></em>' containment reference list.
	 * The list contents are of type {@link IBeXLanguage.IBeXNode}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Deleted Nodes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deleted Nodes</em>' containment reference list.
	 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXDeletePattern_DeletedNodes()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBeXNode> getDeletedNodes();
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} // IBeXDeletePattern
