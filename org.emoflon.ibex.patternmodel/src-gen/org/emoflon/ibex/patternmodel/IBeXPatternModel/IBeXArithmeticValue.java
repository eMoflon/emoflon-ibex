/**
 */
package org.emoflon.ibex.patternmodel.IBeXPatternModel;

import org.emoflon.ibex.gt.SGTPatternModel.GTArithmetics;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XArithmetic Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticValue#getExpression <em>Expression</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXArithmeticValue()
 * @model
 * @generated
 */
public interface IBeXArithmeticValue extends IBeXAttributeValue {
	/**
	 * Returns the value of the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expression</em>' containment reference.
	 * @see #setExpression(GTArithmetics)
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXArithmeticValue_Expression()
	 * @model containment="true"
	 * @generated
	 */
	GTArithmetics getExpression();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticValue#getExpression <em>Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expression</em>' containment reference.
	 * @see #getExpression()
	 * @generated
	 */
	void setExpression(GTArithmetics value);

} // IBeXArithmeticValue
