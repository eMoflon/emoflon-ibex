/**
 */
package StochasticLanguage;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see StochasticLanguage.StochasticLanguagePackage
 * @generated
 */
public interface StochasticLanguageFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	StochasticLanguageFactory eINSTANCE = StochasticLanguage.impl.StochasticLanguageFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>GT Stochastic Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>GT Stochastic Node</em>'.
	 * @generated
	 */
	GTStochasticNode createGTStochasticNode();

	/**
	 * Returns a new object of class '<em>GT Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>GT Attribute</em>'.
	 * @generated
	 */
	GTAttribute createGTAttribute();

	/**
	 * Returns a new object of class '<em>GT Stochastic Function</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>GT Stochastic Function</em>'.
	 * @generated
	 */
	GTStochasticFunction createGTStochasticFunction();

	/**
	 * Returns a new object of class '<em>GT Number</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>GT Number</em>'.
	 * @generated
	 */
	GTNumber createGTNumber();

	/**
	 * Returns a new object of class '<em>GT Two Parameter Calculation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>GT Two Parameter Calculation</em>'.
	 * @generated
	 */
	GTTwoParameterCalculation createGTTwoParameterCalculation();

	/**
	 * Returns a new object of class '<em>GT One Parameter Calculation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>GT One Parameter Calculation</em>'.
	 * @generated
	 */
	GTOneParameterCalculation createGTOneParameterCalculation();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	StochasticLanguagePackage getStochasticLanguagePackage();

} //StochasticLanguageFactory
