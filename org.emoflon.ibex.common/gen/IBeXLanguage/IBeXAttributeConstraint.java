/**
 */
package IBeXLanguage;

import org.eclipse.emf.ecore.EObject;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XAttribute Constraint</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link IBeXLanguage.IBeXAttributeConstraint#getRelation <em>Relation</em>}</li>
 * </ul>
 * </p>
 *
 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXAttributeConstraint()
 * @model
 * @generated
 */
public interface IBeXAttributeConstraint extends EObject, IBeXAttribute {
	/**
	 * Returns the value of the '<em><b>Relation</b></em>' attribute.
	 * The literals are from the enumeration {@link IBeXLanguage.IBeXRelation}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Relation</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Relation</em>' attribute.
	 * @see IBeXLanguage.IBeXRelation
	 * @see #setRelation(IBeXRelation)
	 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXAttributeConstraint_Relation()
	 * @model
	 * @generated
	 */
	IBeXRelation getRelation();

	/**
	 * Sets the value of the '{@link IBeXLanguage.IBeXAttributeConstraint#getRelation <em>Relation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Relation</em>' attribute.
	 * @see IBeXLanguage.IBeXRelation
	 * @see #getRelation()
	 * @generated
	 */
	void setRelation(IBeXRelation value);
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} // IBeXAttributeConstraint
