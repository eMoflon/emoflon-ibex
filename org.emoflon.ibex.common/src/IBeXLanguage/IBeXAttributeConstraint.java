/**
 */
package IBeXLanguage;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XAttribute Constraint</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A constraint defines a relation which must hold for a certain attribute of a node.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link IBeXLanguage.IBeXAttributeConstraint#getRelation <em>Relation</em>}</li>
 * </ul>
 *
 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXAttributeConstraint()
 * @model
 * @generated
 */
public interface IBeXAttributeConstraint extends IBeXAttribute {
	/**
	 * Returns the value of the '<em><b>Relation</b></em>' attribute.
	 * The literals are from the enumeration {@link IBeXLanguage.IBeXRelation}.
	 * <!-- begin-user-doc -->
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

} // IBeXAttributeConstraint
