/**
 */
package IBeXLanguage;

import org.eclipse.emf.ecore.EObject;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XConstant</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link IBeXLanguage.IBeXConstant#getValue <em>Value</em>}</li>
 *   <li>{@link IBeXLanguage.IBeXConstant#getStringValue <em>String Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXConstant()
 * @model
 * @generated
 */
public interface IBeXConstant extends EObject, IBeXAttributeValue {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(Object)
	 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXConstant_Value()
	 * @model
	 * @generated
	 */
	Object getValue();

	/**
	 * Sets the value of the '{@link IBeXLanguage.IBeXConstant#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(Object value);

	/**
	 * Returns the value of the '<em><b>String Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>String Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>String Value</em>' attribute.
	 * @see #setStringValue(String)
	 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXConstant_StringValue()
	 * @model
	 * @generated
	 */
	String getStringValue();

	/**
	 * Sets the value of the '{@link IBeXLanguage.IBeXConstant#getStringValue <em>String Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>String Value</em>' attribute.
	 * @see #getStringValue()
	 * @generated
	 */
	void setStringValue(String value);
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} // IBeXConstant
