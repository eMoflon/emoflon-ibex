/**
 */
package org.emoflon.ibex.patternmodel.IBeXPatternModel;

import org.eclipse.emf.ecore.EAttribute;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XArithmetic Attribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticAttribute#getAttribute <em>Attribute</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticAttribute#isNegative <em>Negative</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXArithmeticAttribute()
 * @model
 * @generated
 */
public interface IBeXArithmeticAttribute extends IBeXNode, IBeXArithmeticExpression {
	/**
	 * Returns the value of the '<em><b>Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute</em>' reference.
	 * @see #setAttribute(EAttribute)
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXArithmeticAttribute_Attribute()
	 * @model required="true"
	 * @generated
	 */
	EAttribute getAttribute();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticAttribute#getAttribute <em>Attribute</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute</em>' reference.
	 * @see #getAttribute()
	 * @generated
	 */
	void setAttribute(EAttribute value);

	/**
	 * Returns the value of the '<em><b>Negative</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Negative</em>' attribute.
	 * @see #setNegative(boolean)
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXArithmeticAttribute_Negative()
	 * @model required="true"
	 * @generated
	 */
	boolean isNegative();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticAttribute#isNegative <em>Negative</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Negative</em>' attribute.
	 * @see #isNegative()
	 * @generated
	 */
	void setNegative(boolean value);

} // IBeXArithmeticAttribute
