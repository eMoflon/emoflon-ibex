/**
 */
package org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Unary Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.UnaryExpression#getOperand <em>Operand</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.UnaryExpression#getOperator <em>Operator</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.IBeXCoreArithmeticPackage#getUnaryExpression()
 * @model
 * @generated
 */
public interface UnaryExpression extends ArithmeticExpression {
	/**
	 * Returns the value of the '<em><b>Operand</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operand</em>' containment reference.
	 * @see #setOperand(ArithmeticExpression)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.IBeXCoreArithmeticPackage#getUnaryExpression_Operand()
	 * @model containment="true" required="true"
	 * @generated
	 */
	ArithmeticExpression getOperand();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.UnaryExpression#getOperand <em>Operand</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operand</em>' containment reference.
	 * @see #getOperand()
	 * @generated
	 */
	void setOperand(ArithmeticExpression value);

	/**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute.
	 * The literals are from the enumeration {@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.UnaryOperator}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.UnaryOperator
	 * @see #setOperator(UnaryOperator)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.IBeXCoreArithmeticPackage#getUnaryExpression_Operator()
	 * @model
	 * @generated
	 */
	UnaryOperator getOperator();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.UnaryExpression#getOperator <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operator</em>' attribute.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.UnaryOperator
	 * @see #getOperator()
	 * @generated
	 */
	void setOperator(UnaryOperator value);

} // UnaryExpression
