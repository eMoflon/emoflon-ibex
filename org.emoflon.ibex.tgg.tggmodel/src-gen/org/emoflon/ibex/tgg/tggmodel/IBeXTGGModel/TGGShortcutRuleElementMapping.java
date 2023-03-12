/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TGG Shortcut Rule Element Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGShortcutRuleElementMapping#getOriginal <em>Original</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGShortcutRuleElementMapping#getReplacing <em>Replacing</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGShortcutRuleElementMapping()
 * @model
 * @generated
 */
public interface TGGShortcutRuleElementMapping extends EObject {
	/**
	 * Returns the value of the '<em><b>Original</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Original</em>' reference.
	 * @see #setOriginal(TGGRuleElement)
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGShortcutRuleElementMapping_Original()
	 * @model
	 * @generated
	 */
	TGGRuleElement getOriginal();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGShortcutRuleElementMapping#getOriginal <em>Original</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Original</em>' reference.
	 * @see #getOriginal()
	 * @generated
	 */
	void setOriginal(TGGRuleElement value);

	/**
	 * Returns the value of the '<em><b>Replacing</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Replacing</em>' reference.
	 * @see #setReplacing(TGGRuleElement)
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGShortcutRuleElementMapping_Replacing()
	 * @model
	 * @generated
	 */
	TGGRuleElement getReplacing();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGShortcutRuleElementMapping#getReplacing <em>Replacing</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Replacing</em>' reference.
	 * @see #getReplacing()
	 * @generated
	 */
	void setReplacing(TGGRuleElement value);

} // TGGShortcutRuleElementMapping
