/**
 */
package IBeXLanguage;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XCreate Pattern</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link IBeXLanguage.IBeXCreatePattern#getAttributeAssignments <em>Attribute Assignments</em>}</li>
 *   <li>{@link IBeXLanguage.IBeXCreatePattern#getContextNodes <em>Context Nodes</em>}</li>
 *   <li>{@link IBeXLanguage.IBeXCreatePattern#getCreatedEdges <em>Created Edges</em>}</li>
 *   <li>{@link IBeXLanguage.IBeXCreatePattern#getCreatedNodes <em>Created Nodes</em>}</li>
 * </ul>
 * </p>
 *
 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXCreatePattern()
 * @model
 * @generated
 */
public interface IBeXCreatePattern extends EObject, IBeXPattern {
	/**
	 * Returns the value of the '<em><b>Attribute Assignments</b></em>' containment reference list.
	 * The list contents are of type {@link IBeXLanguage.IBeXAttributeAssignment}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Assignments</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Assignments</em>' containment reference list.
	 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXCreatePattern_AttributeAssignments()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBeXAttributeAssignment> getAttributeAssignments();

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
	 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXCreatePattern_ContextNodes()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBeXNode> getContextNodes();

	/**
	 * Returns the value of the '<em><b>Created Edges</b></em>' containment reference list.
	 * The list contents are of type {@link IBeXLanguage.IBeXEdge}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Created Edges</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Created Edges</em>' containment reference list.
	 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXCreatePattern_CreatedEdges()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBeXEdge> getCreatedEdges();

	/**
	 * Returns the value of the '<em><b>Created Nodes</b></em>' containment reference list.
	 * The list contents are of type {@link IBeXLanguage.IBeXNode}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Created Nodes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Created Nodes</em>' containment reference list.
	 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXCreatePattern_CreatedNodes()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBeXNode> getCreatedNodes();
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} // IBeXCreatePattern
