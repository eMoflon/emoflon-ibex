/**
 */
package org.emoflon.ibex.gt.gtmodel.IBeXGTModel;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModel;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>GT Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTModel#getRuleSet <em>Rule Set</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelPackage#getGTModel()
 * @model
 * @generated
 */
public interface GTModel extends IBeXModel {
	/**
	 * Returns the value of the '<em><b>Rule Set</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule Set</em>' containment reference.
	 * @see #setRuleSet(GTRuleSet)
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelPackage#getGTModel_RuleSet()
	 * @model containment="true" required="true"
	 * @generated
	 */
	GTRuleSet getRuleSet();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTModel#getRuleSet <em>Rule Set</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule Set</em>' containment reference.
	 * @see #getRuleSet()
	 * @generated
	 */
	void setRuleSet(GTRuleSet value);

} // GTModel
