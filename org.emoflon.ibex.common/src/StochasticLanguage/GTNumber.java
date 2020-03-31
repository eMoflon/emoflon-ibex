/**
 */
package StochasticLanguage;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>GT Number</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * The value of the parameter if it has a static value
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link StochasticLanguage.GTNumber#getNumber <em>Number</em>}</li>
 * </ul>
 *
 * @see StochasticLanguage.StochasticLanguagePackage#getGTNumber()
 * @model
 * @generated
 */
public interface GTNumber extends GTArithmetics {
	/**
	 * Returns the value of the '<em><b>Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Number</em>' attribute.
	 * @see #setNumber(double)
	 * @see StochasticLanguage.StochasticLanguagePackage#getGTNumber_Number()
	 * @model
	 * @generated
	 */
	double getNumber();

	/**
	 * Sets the value of the '{@link StochasticLanguage.GTNumber#getNumber <em>Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Number</em>' attribute.
	 * @see #getNumber()
	 * @generated
	 */
	void setNumber(double value);

} // GTNumber
