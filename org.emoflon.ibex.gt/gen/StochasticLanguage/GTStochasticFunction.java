/**
 */
package StochasticLanguage;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>GT Stochastic Function</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A stochastic function can be used to calculate probabilities or generate values.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link StochasticLanguage.GTStochasticFunction#getMean <em>Mean</em>}</li>
 *   <li>{@link StochasticLanguage.GTStochasticFunction#getSd <em>Sd</em>}</li>
 *   <li>{@link StochasticLanguage.GTStochasticFunction#getDistribution <em>Distribution</em>}</li>
 * </ul>
 *
 * @see StochasticLanguage.StochasticLanguagePackage#getGTStochasticFunction()
 * @model
 * @generated
 */
public interface GTStochasticFunction extends EObject {
	/**
	 * Returns the value of the '<em><b>Mean</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mean</em>' containment reference.
	 * @see #setMean(GTArithmetics)
	 * @see StochasticLanguage.StochasticLanguagePackage#getGTStochasticFunction_Mean()
	 * @model containment="true"
	 * @generated
	 */
	GTArithmetics getMean();

	/**
	 * Sets the value of the '{@link StochasticLanguage.GTStochasticFunction#getMean <em>Mean</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mean</em>' containment reference.
	 * @see #getMean()
	 * @generated
	 */
	void setMean(GTArithmetics value);

	/**
	 * Returns the value of the '<em><b>Sd</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sd</em>' containment reference.
	 * @see #setSd(GTArithmetics)
	 * @see StochasticLanguage.StochasticLanguagePackage#getGTStochasticFunction_Sd()
	 * @model containment="true"
	 * @generated
	 */
	GTArithmetics getSd();

	/**
	 * Sets the value of the '{@link StochasticLanguage.GTStochasticFunction#getSd <em>Sd</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sd</em>' containment reference.
	 * @see #getSd()
	 * @generated
	 */
	void setSd(GTArithmetics value);

	/**
	 * Returns the value of the '<em><b>Distribution</b></em>' attribute.
	 * The literals are from the enumeration {@link StochasticLanguage.GTStochasticDistribution}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Distribution</em>' attribute.
	 * @see StochasticLanguage.GTStochasticDistribution
	 * @see #setDistribution(GTStochasticDistribution)
	 * @see StochasticLanguage.StochasticLanguagePackage#getGTStochasticFunction_Distribution()
	 * @model
	 * @generated
	 */
	GTStochasticDistribution getDistribution();

	/**
	 * Sets the value of the '{@link StochasticLanguage.GTStochasticFunction#getDistribution <em>Distribution</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Distribution</em>' attribute.
	 * @see StochasticLanguage.GTStochasticDistribution
	 * @see #getDistribution()
	 * @generated
	 */
	void setDistribution(GTStochasticDistribution value);

} // GTStochasticFunction
