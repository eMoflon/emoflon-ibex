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
 *   <li>{@link language.TGGComplementRule#isBounded <em>Bounded</em>}</li>
 *   <li>{@link language.TGGComplementRule#getRuleApplicationLowerBound <em>Rule Application Lower Bound</em>}</li>
 *   <li>{@link language.TGGComplementRule#getRuleApplicationUpperBound <em>Rule Application Upper Bound</em>}</li>
 *   <li>{@link language.TGGComplementRule#getKernel <em>Kernel</em>}</li>
 * </ul>
 *
 * @see language.LanguagePackage#getTGGComplementRule()
 * @model
 * @generated
 */
public interface TGGComplementRule extends TGGRule {
	/**
	 * Returns the value of the '<em><b>Bounded</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bounded</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bounded</em>' attribute.
	 * @see #setBounded(boolean)
	 * @see language.LanguagePackage#getTGGComplementRule_Bounded()
	 * @model
	 * @generated
	 */
	boolean isBounded();

	/**
	 * Sets the value of the '{@link language.TGGComplementRule#isBounded <em>Bounded</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Bounded</em>' attribute.
	 * @see #isBounded()
	 * @generated
	 */
	void setBounded(boolean value);

	/**
	 * Returns the value of the '<em><b>Rule Application Lower Bound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule Application Lower Bound</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule Application Lower Bound</em>' attribute.
	 * @see #setRuleApplicationLowerBound(int)
	 * @see language.LanguagePackage#getTGGComplementRule_RuleApplicationLowerBound()
	 * @model
	 * @generated
	 */
	int getRuleApplicationLowerBound();

	/**
	 * Sets the value of the '{@link language.TGGComplementRule#getRuleApplicationLowerBound <em>Rule Application Lower Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule Application Lower Bound</em>' attribute.
	 * @see #getRuleApplicationLowerBound()
	 * @generated
	 */
	void setRuleApplicationLowerBound(int value);

	/**
	 * Returns the value of the '<em><b>Rule Application Upper Bound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule Application Upper Bound</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule Application Upper Bound</em>' attribute.
	 * @see #setRuleApplicationUpperBound(int)
	 * @see language.LanguagePackage#getTGGComplementRule_RuleApplicationUpperBound()
	 * @model
	 * @generated
	 */
	int getRuleApplicationUpperBound();

	/**
	 * Sets the value of the '{@link language.TGGComplementRule#getRuleApplicationUpperBound <em>Rule Application Upper Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule Application Upper Bound</em>' attribute.
	 * @see #getRuleApplicationUpperBound()
	 * @generated
	 */
	void setRuleApplicationUpperBound(int value);

	/**
	 * Returns the value of the '<em><b>Kernel</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Kernel</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Kernel</em>' reference.
	 * @see #setKernel(TGGRule)
	 * @see language.LanguagePackage#getTGGComplementRule_Kernel()
	 * @model
	 * @generated
	 */
	TGGRule getKernel();

	/**
	 * Sets the value of the '{@link language.TGGComplementRule#getKernel <em>Kernel</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Kernel</em>' reference.
	 * @see #getKernel()
	 * @generated
	 */
	void setKernel(TGGRule value);

} // TGGComplementRule
