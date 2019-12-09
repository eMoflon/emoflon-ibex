/**
 */
package IBeXLanguage;

import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XEnum Literal</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link IBeXLanguage.IBeXEnumLiteral#getLiteral <em>Literal</em>}</li>
 * </ul>
 * </p>
 *
 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXEnumLiteral()
 * @model
 * @generated
 */
public interface IBeXEnumLiteral extends EObject, IBeXAttributeValue {
	/**
	 * Returns the value of the '<em><b>Literal</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Literal</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Literal</em>' reference.
	 * @see #setLiteral(EEnumLiteral)
	 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXEnumLiteral_Literal()
	 * @model
	 * @generated
	 */
	EEnumLiteral getLiteral();

	/**
	 * Sets the value of the '{@link IBeXLanguage.IBeXEnumLiteral#getLiteral <em>Literal</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Literal</em>' reference.
	 * @see #getLiteral()
	 * @generated
	 */
	void setLiteral(EEnumLiteral value);
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} // IBeXEnumLiteral
