/**
 */
package IBeXLanguage;

import org.eclipse.emf.ecore.EObject;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XNamed Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link IBeXLanguage.IBeXNamedElement#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXNamedElement()
 * @model abstract="true"
 * @generated
 */
public interface IBeXNamedElement extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXNamedElement_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link IBeXLanguage.IBeXNamedElement#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} // IBeXNamedElement
