/**
 */
package language;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TGG Rule Corr</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link language.TGGRuleCorr#getSource <em>Source</em>}</li>
 *   <li>{@link language.TGGRuleCorr#getTarget <em>Target</em>}</li>
 * </ul>
 *
 * @see language.LanguagePackage#getTGGRuleCorr()
 * @model
 * @generated
 */
public interface TGGRuleCorr extends TGGRuleNode {
	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link language.TGGRuleNode#getIncomingCorrsSource <em>Incoming Corrs Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(TGGRuleNode)
	 * @see language.LanguagePackage#getTGGRuleCorr_Source()
	 * @see language.TGGRuleNode#getIncomingCorrsSource
	 * @model opposite="incomingCorrsSource"
	 * @generated
	 */
	TGGRuleNode getSource();

	/**
	 * Sets the value of the '{@link language.TGGRuleCorr#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(TGGRuleNode value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link language.TGGRuleNode#getIncomingCorrsTarget <em>Incoming Corrs Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(TGGRuleNode)
	 * @see language.LanguagePackage#getTGGRuleCorr_Target()
	 * @see language.TGGRuleNode#getIncomingCorrsTarget
	 * @model opposite="incomingCorrsTarget"
	 * @generated
	 */
	TGGRuleNode getTarget();

	/**
	 * Sets the value of the '{@link language.TGGRuleCorr#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(TGGRuleNode value);

} // TGGRuleCorr
