/**
 */
package language;

import language.basic.TGGNamedElement;

import language.csp.TGGAttributeConstraintLibrary;

import org.eclipse.emf.common.util.EList;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>NAC</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link language.NAC#getNodes <em>Nodes</em>}</li>
 *   <li>{@link language.NAC#getEdges <em>Edges</em>}</li>
 *   <li>{@link language.NAC#getAttributeConditionLibrary <em>Attribute Condition Library</em>}</li>
 * </ul>
 * </p>
 *
 * @see language.LanguagePackage#getNAC()
 * @model
 * @generated
 */
public interface NAC extends TGGNamedElement {
	/**
	 * Returns the value of the '<em><b>Nodes</b></em>' containment reference list.
	 * The list contents are of type {@link language.TGGRuleNode}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nodes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nodes</em>' containment reference list.
	 * @see language.LanguagePackage#getNAC_Nodes()
	 * @model containment="true"
	 * @generated
	 */
	EList<TGGRuleNode> getNodes();

	/**
	 * Returns the value of the '<em><b>Edges</b></em>' containment reference list.
	 * The list contents are of type {@link language.TGGRuleEdge}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Edges</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Edges</em>' containment reference list.
	 * @see language.LanguagePackage#getNAC_Edges()
	 * @model containment="true"
	 * @generated
	 */
	EList<TGGRuleEdge> getEdges();

	/**
	 * Returns the value of the '<em><b>Attribute Condition Library</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Condition Library</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Condition Library</em>' containment reference.
	 * @see #setAttributeConditionLibrary(TGGAttributeConstraintLibrary)
	 * @see language.LanguagePackage#getNAC_AttributeConditionLibrary()
	 * @model containment="true" required="true"
	 * @generated
	 */
	TGGAttributeConstraintLibrary getAttributeConditionLibrary();

	/**
	 * Sets the value of the '{@link language.NAC#getAttributeConditionLibrary <em>Attribute Condition Library</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute Condition Library</em>' containment reference.
	 * @see #getAttributeConditionLibrary()
	 * @generated
	 */
	void setAttributeConditionLibrary(TGGAttributeConstraintLibrary value);
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} // NAC
