/**
 */
package language;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TGG Complement Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link language.TGGComplementRule#isAdditionalContext <em>Additional Context</em>}</li>
 *   <li>{@link language.TGGComplementRule#getLowerRABound <em>Lower RA Bound</em>}</li>
 *   <li>{@link language.TGGComplementRule#getUpperRABound <em>Upper RA Bound</em>}</li>
 * </ul>
 *
 * @see language.LanguagePackage#getTGGComplementRule()
 * @model
 * @generated
 */
public interface TGGComplementRule extends TGGRule {
	/**
	 * Returns the value of the '<em><b>Additional Context</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Additional Context</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Additional Context</em>' attribute.
	 * @see #setAdditionalContext(boolean)
	 * @see language.LanguagePackage#getTGGComplementRule_AdditionalContext()
	 * @model
	 * @generated
	 */
	boolean isAdditionalContext();

	/**
	 * Sets the value of the '{@link language.TGGComplementRule#isAdditionalContext <em>Additional Context</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Additional Context</em>' attribute.
	 * @see #isAdditionalContext()
	 * @generated
	 */
	void setAdditionalContext(boolean value);

	/**
	 * Returns the value of the '<em><b>Lower RA Bound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lower RA Bound</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lower RA Bound</em>' attribute.
	 * @see #setLowerRABound(int)
	 * @see language.LanguagePackage#getTGGComplementRule_LowerRABound()
	 * @model
	 * @generated
	 */
	int getLowerRABound();

	/**
	 * Sets the value of the '{@link language.TGGComplementRule#getLowerRABound <em>Lower RA Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lower RA Bound</em>' attribute.
	 * @see #getLowerRABound()
	 * @generated
	 */
	void setLowerRABound(int value);

	/**
	 * Returns the value of the '<em><b>Upper RA Bound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Upper RA Bound</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Upper RA Bound</em>' attribute.
	 * @see #setUpperRABound(int)
	 * @see language.LanguagePackage#getTGGComplementRule_UpperRABound()
	 * @model
	 * @generated
	 */
	int getUpperRABound();

	/**
	 * Sets the value of the '{@link language.TGGComplementRule#getUpperRABound <em>Upper RA Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Upper RA Bound</em>' attribute.
	 * @see #getUpperRABound()
	 * @generated
	 */
	void setUpperRABound(int value);

} // TGGComplementRule
