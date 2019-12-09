/**
 */
package IBeXLanguage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XAttribute Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link IBeXLanguage.IBeXAttributeExpression#getAttribute <em>Attribute</em>}</li>
 *   <li>{@link IBeXLanguage.IBeXAttributeExpression#getNode <em>Node</em>}</li>
 * </ul>
 * </p>
 *
 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXAttributeExpression()
 * @model
 * @generated
 */
public interface IBeXAttributeExpression extends EObject, IBeXAttributeValue {
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
	 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXAttributeExpression_Attribute()
	 * @model
	 * @generated
	 */
	EAttribute getAttribute();

	/**
	 * Sets the value of the '{@link IBeXLanguage.IBeXAttributeExpression#getAttribute <em>Attribute</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute</em>' reference.
	 * @see #getAttribute()
	 * @generated
	 */
	void setAttribute(EAttribute value);

	/**
	 * Returns the value of the '<em><b>Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Node</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Node</em>' reference.
	 * @see #setNode(IBeXNode)
	 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXAttributeExpression_Node()
	 * @model
	 * @generated
	 */
	IBeXNode getNode();

	/**
	 * Sets the value of the '{@link IBeXLanguage.IBeXAttributeExpression#getNode <em>Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Node</em>' reference.
	 * @see #getNode()
	 * @generated
	 */
	void setNode(IBeXNode value);
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} // IBeXAttributeExpression
