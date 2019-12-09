/**
 */
package IBeXLanguage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XAttribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link IBeXLanguage.IBeXAttribute#getType <em>Type</em>}</li>
 *   <li>{@link IBeXLanguage.IBeXAttribute#getNode <em>Node</em>}</li>
 *   <li>{@link IBeXLanguage.IBeXAttribute#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXAttribute()
 * @model abstract="true"
 * @generated
 */
public interface IBeXAttribute extends EObject {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(EAttribute)
	 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXAttribute_Type()
	 * @model
	 * @generated
	 */
	EAttribute getType();

	/**
	 * Sets the value of the '{@link IBeXLanguage.IBeXAttribute#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(EAttribute value);

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
	 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXAttribute_Node()
	 * @model
	 * @generated
	 */
	IBeXNode getNode();

	/**
	 * Sets the value of the '{@link IBeXLanguage.IBeXAttribute#getNode <em>Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Node</em>' reference.
	 * @see #getNode()
	 * @generated
	 */
	void setNode(IBeXNode value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' containment reference.
	 * @see #setValue(IBeXAttributeValue)
	 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXAttribute_Value()
	 * @model containment="true"
	 * @generated
	 */
	IBeXAttributeValue getValue();

	/**
	 * Sets the value of the '{@link IBeXLanguage.IBeXAttribute#getValue <em>Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' containment reference.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(IBeXAttributeValue value);
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} // IBeXAttribute
