/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TGG Shortcut Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGShortcutRule#getOriginalRule <em>Original Rule</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGShortcutRule#getReplacingRule <em>Replacing Rule</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGShortcutRule#getMappings <em>Mappings</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGShortcutRule#getUnmappedOriginalElements <em>Unmapped Original Elements</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGShortcutRule#getUnmappedReplacingElements <em>Unmapped Replacing Elements</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGShortcutRule()
 * @model
 * @generated
 */
public interface TGGShortcutRule extends TGGOperationalRule {
	/**
	 * Returns the value of the '<em><b>Original Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Original Rule</em>' reference.
	 * @see #setOriginalRule(TGGRule)
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGShortcutRule_OriginalRule()
	 * @model
	 * @generated
	 */
	TGGRule getOriginalRule();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGShortcutRule#getOriginalRule <em>Original Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Original Rule</em>' reference.
	 * @see #getOriginalRule()
	 * @generated
	 */
	void setOriginalRule(TGGRule value);

	/**
	 * Returns the value of the '<em><b>Replacing Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Replacing Rule</em>' reference.
	 * @see #setReplacingRule(TGGRule)
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGShortcutRule_ReplacingRule()
	 * @model
	 * @generated
	 */
	TGGRule getReplacingRule();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGShortcutRule#getReplacingRule <em>Replacing Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Replacing Rule</em>' reference.
	 * @see #getReplacingRule()
	 * @generated
	 */
	void setReplacingRule(TGGRule value);

	/**
	 * Returns the value of the '<em><b>Mappings</b></em>' reference list.
	 * The list contents are of type {@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGShortcutRuleElementMapping}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mappings</em>' reference list.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGShortcutRule_Mappings()
	 * @model
	 * @generated
	 */
	EList<TGGShortcutRuleElementMapping> getMappings();

	/**
	 * Returns the value of the '<em><b>Unmapped Original Elements</b></em>' reference list.
	 * The list contents are of type {@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleElement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unmapped Original Elements</em>' reference list.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGShortcutRule_UnmappedOriginalElements()
	 * @model ordered="false"
	 * @generated
	 */
	EList<TGGRuleElement> getUnmappedOriginalElements();

	/**
	 * Returns the value of the '<em><b>Unmapped Replacing Elements</b></em>' reference list.
	 * The list contents are of type {@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleElement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unmapped Replacing Elements</em>' reference list.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGShortcutRule_UnmappedReplacingElements()
	 * @model ordered="false"
	 * @generated
	 */
	EList<TGGRuleElement> getUnmappedReplacingElements();

} // TGGShortcutRule
