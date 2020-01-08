/**
 */
package language;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TGG Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link language.TGGRule#getRefines <em>Refines</em>}</li>
 *   <li>{@link language.TGGRule#getNacs <em>Nacs</em>}</li>
 *   <li>{@link language.TGGRule#getNodes <em>Nodes</em>}</li>
 *   <li>{@link language.TGGRule#getEdges <em>Edges</em>}</li>
 *   <li>{@link language.TGGRule#getAttributeConditionLibrary <em>Attribute Condition Library</em>}</li>
 *   <li>{@link language.TGGRule#isAbstract <em>Abstract</em>}</li>
 * </ul>
 *
 * @see language.LanguagePackage#getTGGRule()
 * @model
 * @generated
 */
public interface TGGRule extends TGGNamedElement {
	/**
	 * Returns the value of the '<em><b>Refines</b></em>' reference list.
	 * The list contents are of type {@link language.TGGRule}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Refines</em>' reference list.
	 * @see language.LanguagePackage#getTGGRule_Refines()
	 * @model
	 * @generated
	 */
	EList<TGGRule> getRefines();

	/**
	 * Returns the value of the '<em><b>Nacs</b></em>' containment reference list.
	 * The list contents are of type {@link language.NAC}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nacs</em>' containment reference list.
	 * @see language.LanguagePackage#getTGGRule_Nacs()
	 * @model containment="true"
	 * @generated
	 */
	EList<NAC> getNacs();

	/**
	 * Returns the value of the '<em><b>Nodes</b></em>' containment reference list.
	 * The list contents are of type {@link language.TGGRuleNode}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nodes</em>' containment reference list.
	 * @see language.LanguagePackage#getTGGRule_Nodes()
	 * @model containment="true"
	 * @generated
	 */
	EList<TGGRuleNode> getNodes();

	/**
	 * Returns the value of the '<em><b>Edges</b></em>' containment reference list.
	 * The list contents are of type {@link language.TGGRuleEdge}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Edges</em>' containment reference list.
	 * @see language.LanguagePackage#getTGGRule_Edges()
	 * @model containment="true"
	 * @generated
	 */
	EList<TGGRuleEdge> getEdges();

	/**
	 * Returns the value of the '<em><b>Attribute Condition Library</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Condition Library</em>' containment reference.
	 * @see #setAttributeConditionLibrary(TGGAttributeConstraintLibrary)
	 * @see language.LanguagePackage#getTGGRule_AttributeConditionLibrary()
	 * @model containment="true" required="true"
	 * @generated
	 */
	TGGAttributeConstraintLibrary getAttributeConditionLibrary();

	/**
	 * Sets the value of the '{@link language.TGGRule#getAttributeConditionLibrary <em>Attribute Condition Library</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute Condition Library</em>' containment reference.
	 * @see #getAttributeConditionLibrary()
	 * @generated
	 */
	void setAttributeConditionLibrary(TGGAttributeConstraintLibrary value);

	/**
	 * Returns the value of the '<em><b>Abstract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Abstract</em>' attribute.
	 * @see #setAbstract(boolean)
	 * @see language.LanguagePackage#getTGGRule_Abstract()
	 * @model
	 * @generated
	 */
	boolean isAbstract();

	/**
	 * Sets the value of the '{@link language.TGGRule#isAbstract <em>Abstract</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Abstract</em>' attribute.
	 * @see #isAbstract()
	 * @generated
	 */
	void setAbstract(boolean value);

} // TGGRule
