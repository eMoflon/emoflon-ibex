/**
 */
package org.emoflon.ibex.gt.SGTPatternModel;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>GT One Parameter Calculation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.gt.SGTPatternModel.GTOneParameterCalculation#getValue <em>Value</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.SGTPatternModel.GTOneParameterCalculation#getOperator <em>Operator</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.SGTPatternModel.GTOneParameterCalculation#isNegative <em>Negative</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.gt.SGTPatternModel.SGTPatternModelPackage#getGTOneParameterCalculation()
 * @model
 * @generated
 */
public interface GTOneParameterCalculation extends GTArithmetics {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' containment reference.
	 * @see #setValue(GTArithmetics)
	 * @see org.emoflon.ibex.gt.SGTPatternModel.SGTPatternModelPackage#getGTOneParameterCalculation_Value()
	 * @model containment="true"
	 * @generated
	 */
	GTArithmetics getValue();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.gt.SGTPatternModel.GTOneParameterCalculation#getValue <em>Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' containment reference.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(GTArithmetics value);

	/**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute.
	 * The literals are from the enumeration {@link org.emoflon.ibex.gt.SGTPatternModel.OneParameterOperator}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see org.emoflon.ibex.gt.SGTPatternModel.OneParameterOperator
	 * @see #setOperator(OneParameterOperator)
	 * @see org.emoflon.ibex.gt.SGTPatternModel.SGTPatternModelPackage#getGTOneParameterCalculation_Operator()
	 * @model
	 * @generated
	 */
	OneParameterOperator getOperator();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.gt.SGTPatternModel.GTOneParameterCalculation#getOperator <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operator</em>' attribute.
	 * @see org.emoflon.ibex.gt.SGTPatternModel.OneParameterOperator
	 * @see #getOperator()
	 * @generated
	 */
	void setOperator(OneParameterOperator value);

	/**
	 * Returns the value of the '<em><b>Negative</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Negative</em>' attribute.
	 * @see #setNegative(boolean)
	 * @see org.emoflon.ibex.gt.SGTPatternModel.SGTPatternModelPackage#getGTOneParameterCalculation_Negative()
	 * @model
	 * @generated
	 */
	boolean isNegative();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.gt.SGTPatternModel.GTOneParameterCalculation#isNegative <em>Negative</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Negative</em>' attribute.
	 * @see #isNegative()
	 * @generated
	 */
	void setNegative(boolean value);

} // GTOneParameterCalculation
