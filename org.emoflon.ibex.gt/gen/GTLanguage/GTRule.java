/**
 */
package GTLanguage;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>GT Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A rule contains parameters (optional) and nodes. Only context and deleted nodes can be bound on a rule.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link GTLanguage.GTRule#getDocumentation <em>Documentation</em>}</li>
 *   <li>{@link GTLanguage.GTRule#isExecutable <em>Executable</em>}</li>
 *   <li>{@link GTLanguage.GTRule#getNodes <em>Nodes</em>}</li>
 *   <li>{@link GTLanguage.GTRule#getParameters <em>Parameters</em>}</li>
 *   <li>{@link GTLanguage.GTRule#getRuleNodes <em>Rule Nodes</em>}</li>
 *   <li>{@link GTLanguage.GTRule#getConstraints <em>Constraints</em>}</li>
 *   <li>{@link GTLanguage.GTRule#getProbability <em>Probability</em>}</li>
 * </ul>
 *
 * @see GTLanguage.GTLanguagePackage#getGTRule()
 * @model
 * @generated
 */
public interface GTRule extends GTNamedElement {
	/**
	 * Returns the value of the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Documentation</em>' attribute.
	 * @see #setDocumentation(String)
	 * @see GTLanguage.GTLanguagePackage#getGTRule_Documentation()
	 * @model
	 * @generated
	 */
	String getDocumentation();

	/**
	 * Sets the value of the '{@link GTLanguage.GTRule#getDocumentation <em>Documentation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Documentation</em>' attribute.
	 * @see #getDocumentation()
	 * @generated
	 */
	void setDocumentation(String value);

	/**
	 * Returns the value of the '<em><b>Executable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Executable</em>' attribute.
	 * @see #setExecutable(boolean)
	 * @see GTLanguage.GTLanguagePackage#getGTRule_Executable()
	 * @model
	 * @generated
	 */
	boolean isExecutable();

	/**
	 * Sets the value of the '{@link GTLanguage.GTRule#isExecutable <em>Executable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Executable</em>' attribute.
	 * @see #isExecutable()
	 * @generated
	 */
	void setExecutable(boolean value);

	/**
	 * Returns the value of the '<em><b>Nodes</b></em>' containment reference list.
	 * The list contents are of type {@link GTLanguage.GTNode}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nodes</em>' containment reference list.
	 * @see GTLanguage.GTLanguagePackage#getGTRule_Nodes()
	 * @model containment="true"
	 * @generated
	 */
	EList<GTNode> getNodes();

	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
	 * The list contents are of type {@link GTLanguage.GTParameter}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' containment reference list.
	 * @see GTLanguage.GTLanguagePackage#getGTRule_Parameters()
	 * @model containment="true"
	 * @generated
	 */
	EList<GTParameter> getParameters();

	/**
	 * Returns the value of the '<em><b>Rule Nodes</b></em>' reference list.
	 * The list contents are of type {@link GTLanguage.GTNode}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule Nodes</em>' reference list.
	 * @see GTLanguage.GTLanguagePackage#getGTRule_RuleNodes()
	 * @model
	 * @generated
	 */
	EList<GTNode> getRuleNodes();

	/**
	 * Returns the value of the '<em><b>Probability</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Probability</em>' containment reference.
	 * @see #setProbability(GTProbability)
	 * @see GTLanguage.GTLanguagePackage#getGTRule_Probability()
	 * @model containment="true"
	 * @generated
	 */
	GTProbability getProbability();

	/**
	 * Sets the value of the '{@link GTLanguage.GTRule#getProbability <em>Probability</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Probability</em>' containment reference.
	 * @see #getProbability()
	 * @generated
	 */
	void setProbability(GTProbability value);

	/**
	 * Returns the value of the '<em><b>Constraints</b></em>' containment reference list.
	 * The list contents are of type {@link GTLanguage.GTArithmeticConstraint}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constraints</em>' containment reference list.
	 * @see GTLanguage.GTLanguagePackage#getGTRule_Constraints()
	 * @model containment="true"
	 * @generated
	 */
	EList<GTArithmeticConstraint> getConstraints();

} // GTRule
