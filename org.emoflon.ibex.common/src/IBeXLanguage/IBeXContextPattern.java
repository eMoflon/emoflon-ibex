/**
 */
package IBeXLanguage;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XContext Pattern</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A pattern consists of local edges and nodes, signature nodes. It can invoke other patterns and force nodes be different.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link IBeXLanguage.IBeXContextPattern#getAttributeConstraint <em>Attribute Constraint</em>}</li>
 *   <li>{@link IBeXLanguage.IBeXContextPattern#getInjectivityConstraints <em>Injectivity Constraints</em>}</li>
 *   <li>{@link IBeXLanguage.IBeXContextPattern#getInvocations <em>Invocations</em>}</li>
 *   <li>{@link IBeXLanguage.IBeXContextPattern#getLocalEdges <em>Local Edges</em>}</li>
 *   <li>{@link IBeXLanguage.IBeXContextPattern#getLocalNodes <em>Local Nodes</em>}</li>
 *   <li>{@link IBeXLanguage.IBeXContextPattern#getSignatureNodes <em>Signature Nodes</em>}</li>
 *   <li>{@link IBeXLanguage.IBeXContextPattern#getCsps <em>Csps</em>}</li>
 * </ul>
 *
 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXContextPattern()
 * @model
 * @generated
 */
public interface IBeXContextPattern extends IBeXContext {
	/**
	 * Returns the value of the '<em><b>Attribute Constraint</b></em>' containment reference list.
	 * The list contents are of type {@link IBeXLanguage.IBeXAttributeConstraint}.
	 * <!-- begin-user-doc -->
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
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Signature Nodes</em>' containment reference list.
	 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXContextPattern_SignatureNodes()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBeXNode> getSignatureNodes();

	/**
	 * Returns the value of the '<em><b>Csps</b></em>' containment reference list.
	 * The list contents are of type {@link IBeXLanguage.IBeXCSP}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Csps</em>' containment reference list.
	 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXContextPattern_Csps()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBeXCSP> getCsps();

} // IBeXContextPattern
