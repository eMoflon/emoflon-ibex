/**
 */
package StochasticLanguage;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>GT One Parameter Calculation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link StochasticLanguage.GTOneParameterCalculation#getValue <em>Value</em>}</li>
 *   <li>{@link StochasticLanguage.GTOneParameterCalculation#getOperator <em>Operator</em>}</li>
 *   <li>{@link StochasticLanguage.GTOneParameterCalculation#isNegative <em>Negative</em>}</li>
 * </ul>
 *
 * @see StochasticLanguage.StochasticLanguagePackage#getGTOneParameterCalculation()
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
	 * @see StochasticLanguage.StochasticLanguagePackage#getGTOneParameterCalculation_Value()
	 * @model containment="true"
	 * @generated
	 */
	GTArithmetics getValue();

	/**
	 * Sets the value of the '{@link StochasticLanguage.GTOneParameterCalculation#getValue <em>Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' containment reference.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(GTArithmetics value);

	/**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute.
	 * The literals are from the enumeration {@link StochasticLanguage.OneParameterOperator}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see StochasticLanguage.OneParameterOperator
	 * @see #setOperator(OneParameterOperator)
	 * @see StochasticLanguage.StochasticLanguagePackage#getGTOneParameterCalculation_Operator()
	 * @model
	 * @generated
	 */
	OneParameterOperator getOperator();

	/**
	 * Sets the value of the '{@link StochasticLanguage.GTOneParameterCalculation#getOperator <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operator</em>' attribute.
	 * @see StochasticLanguage.OneParameterOperator
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
	 * @see StochasticLanguage.StochasticLanguagePackage#getGTOneParameterCalculation_Negative()
	 * @model
	 * @generated
	 */
	boolean isNegative();

	/**
	 * Sets the value of the '{@link StochasticLanguage.GTOneParameterCalculation#isNegative <em>Negative</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Negative</em>' attribute.
	 * @see #isNegative()
	 * @generated
	 */
	void setNegative(boolean value);

} // GTOneParameterCalculation
