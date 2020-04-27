/**
 */
package org.emoflon.ibex.gt.SGTPatternModel;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>GT Two Parameter Calculation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.gt.SGTPatternModel.GTTwoParameterCalculation#getLeft <em>Left</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.SGTPatternModel.GTTwoParameterCalculation#getRight <em>Right</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.SGTPatternModel.GTTwoParameterCalculation#getOperator <em>Operator</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.gt.SGTPatternModel.SGTPatternModelPackage#getGTTwoParameterCalculation()
 * @model
 * @generated
 */
public interface GTTwoParameterCalculation extends GTArithmetics {
	/**
	 * Returns the value of the '<em><b>Left</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Left</em>' containment reference.
	 * @see #setLeft(GTArithmetics)
	 * @see org.emoflon.ibex.gt.SGTPatternModel.SGTPatternModelPackage#getGTTwoParameterCalculation_Left()
	 * @model containment="true"
	 * @generated
	 */
	GTArithmetics getLeft();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.gt.SGTPatternModel.GTTwoParameterCalculation#getLeft <em>Left</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Left</em>' containment reference.
	 * @see #getLeft()
	 * @generated
	 */
	void setLeft(GTArithmetics value);

	/**
	 * Returns the value of the '<em><b>Right</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Right</em>' containment reference.
	 * @see #setRight(GTArithmetics)
	 * @see org.emoflon.ibex.gt.SGTPatternModel.SGTPatternModelPackage#getGTTwoParameterCalculation_Right()
	 * @model containment="true"
	 * @generated
	 */
	GTArithmetics getRight();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.gt.SGTPatternModel.GTTwoParameterCalculation#getRight <em>Right</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Right</em>' containment reference.
	 * @see #getRight()
	 * @generated
	 */
	void setRight(GTArithmetics value);

	/**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute.
	 * The literals are from the enumeration {@link org.emoflon.ibex.gt.SGTPatternModel.TwoParameterOperator}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see org.emoflon.ibex.gt.SGTPatternModel.TwoParameterOperator
	 * @see #setOperator(TwoParameterOperator)
	 * @see org.emoflon.ibex.gt.SGTPatternModel.SGTPatternModelPackage#getGTTwoParameterCalculation_Operator()
	 * @model
	 * @generated
	 */
	TwoParameterOperator getOperator();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.gt.SGTPatternModel.GTTwoParameterCalculation#getOperator <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operator</em>' attribute.
	 * @see org.emoflon.ibex.gt.SGTPatternModel.TwoParameterOperator
	 * @see #getOperator()
	 * @generated
	 */
	void setOperator(TwoParameterOperator value);

} // GTTwoParameterCalculation
