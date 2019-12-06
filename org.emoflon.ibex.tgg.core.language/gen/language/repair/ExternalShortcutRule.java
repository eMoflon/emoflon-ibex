/**
 */
package language.repair;

import language.TGGRule;
import language.TGGRuleElement;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>External Shortcut Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link language.repair.ExternalShortcutRule#getSourceRule <em>Source Rule</em>}</li>
 *   <li>{@link language.repair.ExternalShortcutRule#getTargetRule <em>Target Rule</em>}</li>
 *   <li>{@link language.repair.ExternalShortcutRule#getDeletions <em>Deletions</em>}</li>
 *   <li>{@link language.repair.ExternalShortcutRule#getCreations <em>Creations</em>}</li>
 *   <li>{@link language.repair.ExternalShortcutRule#getUnboundSrcContext <em>Unbound Src Context</em>}</li>
 *   <li>{@link language.repair.ExternalShortcutRule#getUnboundTrgContext <em>Unbound Trg Context</em>}</li>
 *   <li>{@link language.repair.ExternalShortcutRule#getMapping <em>Mapping</em>}</li>
 *   <li>{@link language.repair.ExternalShortcutRule#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see language.repair.RepairPackage#getExternalShortcutRule()
 * @model
 * @generated
 */
public interface ExternalShortcutRule extends EObject {
	/**
	 * Returns the value of the '<em><b>Source Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Rule</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Rule</em>' reference.
	 * @see #setSourceRule(TGGRule)
	 * @see language.repair.RepairPackage#getExternalShortcutRule_SourceRule()
	 * @model
	 * @generated
	 */
	TGGRule getSourceRule();

	/**
	 * Sets the value of the '{@link language.repair.ExternalShortcutRule#getSourceRule <em>Source Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Rule</em>' reference.
	 * @see #getSourceRule()
	 * @generated
	 */
	void setSourceRule(TGGRule value);

	/**
	 * Returns the value of the '<em><b>Target Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target Rule</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Rule</em>' reference.
	 * @see #setTargetRule(TGGRule)
	 * @see language.repair.RepairPackage#getExternalShortcutRule_TargetRule()
	 * @model
	 * @generated
	 */
	TGGRule getTargetRule();

	/**
	 * Sets the value of the '{@link language.repair.ExternalShortcutRule#getTargetRule <em>Target Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Rule</em>' reference.
	 * @see #getTargetRule()
	 * @generated
	 */
	void setTargetRule(TGGRule value);

	/**
	 * Returns the value of the '<em><b>Deletions</b></em>' reference list.
	 * The list contents are of type {@link language.TGGRuleElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Deletions</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deletions</em>' reference list.
	 * @see language.repair.RepairPackage#getExternalShortcutRule_Deletions()
	 * @model
	 * @generated
	 */
	EList<TGGRuleElement> getDeletions();

	/**
	 * Returns the value of the '<em><b>Creations</b></em>' reference list.
	 * The list contents are of type {@link language.TGGRuleElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Creations</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Creations</em>' reference list.
	 * @see language.repair.RepairPackage#getExternalShortcutRule_Creations()
	 * @model
	 * @generated
	 */
	EList<TGGRuleElement> getCreations();

	/**
	 * Returns the value of the '<em><b>Unbound Src Context</b></em>' reference list.
	 * The list contents are of type {@link language.TGGRuleElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Unbound Src Context</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unbound Src Context</em>' reference list.
	 * @see language.repair.RepairPackage#getExternalShortcutRule_UnboundSrcContext()
	 * @model
	 * @generated
	 */
	EList<TGGRuleElement> getUnboundSrcContext();

	/**
	 * Returns the value of the '<em><b>Unbound Trg Context</b></em>' reference list.
	 * The list contents are of type {@link language.TGGRuleElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Unbound Trg Context</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unbound Trg Context</em>' reference list.
	 * @see language.repair.RepairPackage#getExternalShortcutRule_UnboundTrgContext()
	 * @model
	 * @generated
	 */
	EList<TGGRuleElement> getUnboundTrgContext();

	/**
	 * Returns the value of the '<em><b>Mapping</b></em>' containment reference list.
	 * The list contents are of type {@link language.repair.TGGRuleElementMapping}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mapping</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mapping</em>' containment reference list.
	 * @see language.repair.RepairPackage#getExternalShortcutRule_Mapping()
	 * @model containment="true"
	 * @generated
	 */
	EList<TGGRuleElementMapping> getMapping();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see language.repair.RepairPackage#getExternalShortcutRule_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link language.repair.ExternalShortcutRule#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} // ExternalShortcutRule
