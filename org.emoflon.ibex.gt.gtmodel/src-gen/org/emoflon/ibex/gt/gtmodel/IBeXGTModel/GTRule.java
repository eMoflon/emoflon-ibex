/**
 */
package org.emoflon.ibex.gt.gtmodel.IBeXGTModel;

import org.eclipse.emf.common.util.EList;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ArithmeticExpression;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRule;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>GT Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTRule#getParameters <em>Parameters</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTRule#getForEachOperations <em>For Each Operations</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTRule#getProbability <em>Probability</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelPackage#getGTRule()
 * @model
 * @generated
 */
public interface GTRule extends IBeXRule {
	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTParameter}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' containment reference list.
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelPackage#getGTRule_Parameters()
	 * @model containment="true"
	 * @generated
	 */
	EList<GTParameter> getParameters();

	/**
	 * Returns the value of the '<em><b>For Each Operations</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTForEachExpression}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>For Each Operations</em>' containment reference list.
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelPackage#getGTRule_ForEachOperations()
	 * @model containment="true"
	 * @generated
	 */
	EList<GTForEachExpression> getForEachOperations();

	/**
	 * Returns the value of the '<em><b>Probability</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Probability</em>' containment reference.
	 * @see #setProbability(ArithmeticExpression)
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelPackage#getGTRule_Probability()
	 * @model containment="true"
	 * @generated
	 */
	ArithmeticExpression getProbability();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTRule#getProbability <em>Probability</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Probability</em>' containment reference.
	 * @see #getProbability()
	 * @generated
	 */
	void setProbability(ArithmeticExpression value);

} // GTRule
