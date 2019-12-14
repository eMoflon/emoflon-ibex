/**
 */
package GTLanguage;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>GT Rule Set</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A set of graph transformation rules. Each rule in a set must have an unique name.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link GTLanguage.GTRuleSet#getRules <em>Rules</em>}</li>
 * </ul>
 *
 * @see GTLanguage.GTLanguagePackage#getGTRuleSet()
 * @model
 * @generated
 */
public interface GTRuleSet extends EObject {
	/**
	 * Returns the value of the '<em><b>Rules</b></em>' containment reference list.
	 * The list contents are of type {@link GTLanguage.GTRule}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rules</em>' containment reference list.
	 * @see GTLanguage.GTLanguagePackage#getGTRuleSet_Rules()
	 * @model containment="true"
	 * @generated
	 */
	EList<GTRule> getRules();

} // GTRuleSet
