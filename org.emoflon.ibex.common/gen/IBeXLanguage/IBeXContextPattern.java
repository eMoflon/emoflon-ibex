/**
 */
package IBeXLanguage;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XContext Pattern</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link IBeXLanguage.IBeXContextPattern#getAttributeConstraint <em>Attribute Constraint</em>}</li>
 *   <li>{@link IBeXLanguage.IBeXContextPattern#getInjectivityConstraints <em>Injectivity Constraints</em>}</li>
 *   <li>{@link IBeXLanguage.IBeXContextPattern#getInvocations <em>Invocations</em>}</li>
 *   <li>{@link IBeXLanguage.IBeXContextPattern#getLocalEdges <em>Local Edges</em>}</li>
 *   <li>{@link IBeXLanguage.IBeXContextPattern#getLocalNodes <em>Local Nodes</em>}</li>
 *   <li>{@link IBeXLanguage.IBeXContextPattern#getSignatureNodes <em>Signature Nodes</em>}</li>
 * </ul>
 * </p>
 *
 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXContextPattern()
 * @model
 * @generated
 */
public interface IBeXContextPattern extends EObject, IBeXContext {
	/**
	 * Returns the value of the '<em><b>Attribute Constraint</b></em>' containment reference list.
	 * The list contents are of type {@link IBeXLanguage.IBeXAttributeConstraint}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Constraint</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Constraint</em>' containment reference list.
	 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXContextPattern_AttributeConstraint()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBeXAttributeConstraint> getAttributeConstraint();

	/**
	 * Returns the value of the '<em><b>Injectivity Constraints</b></em>' containment reference list.
	 * The list contents are of type {@link IBeXLanguage.IBeXNodePair}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Injectivity Constraints</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Injectivity Constraints</em>' containment reference list.
	 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXContextPattern_InjectivityConstraints()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBeXNodePair> getInjectivityConstraints();

	/**
	 * Returns the value of the '<em><b>Invocations</b></em>' containment reference list.
	 * The list contents are of type {@link IBeXLanguage.IBeXPatternInvocation}.
	 * It is bidirectional and its opposite is '{@link IBeXLanguage.IBeXPatternInvocation#getInvokedBy <em>Invoked By</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Invocations</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Invocations</em>' containment reference list.
	 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXContextPattern_Invocations()
	 * @see IBeXLanguage.IBeXPatternInvocation#getInvokedBy
	 * @model opposite="invokedBy" containment="true"
	 * @generated
	 */
	EList<IBeXPatternInvocation> getInvocations();

	/**
	 * Returns the value of the '<em><b>Local Edges</b></em>' containment reference list.
	 * The list contents are of type {@link IBeXLanguage.IBeXEdge}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Local Edges</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Local Edges</em>' containment reference list.
	 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXContextPattern_LocalEdges()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBeXEdge> getLocalEdges();

	/**
	 * Returns the value of the '<em><b>Local Nodes</b></em>' containment reference list.
	 * The list contents are of type {@link IBeXLanguage.IBeXNode}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Local Nodes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Local Nodes</em>' containment reference list.
	 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXContextPattern_LocalNodes()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBeXNode> getLocalNodes();

	/**
	 * Returns the value of the '<em><b>Signature Nodes</b></em>' containment reference list.
	 * The list contents are of type {@link IBeXLanguage.IBeXNode}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Signature Nodes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Signature Nodes</em>' containment reference list.
	 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXContextPattern_SignatureNodes()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBeXNode> getSignatureNodes();
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} // IBeXContextPattern
