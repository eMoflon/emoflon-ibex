/**
 */
package GTLanguage;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see GTLanguage.GTLanguagePackage
 * @generated
 */
public interface GTLanguageFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	GTLanguageFactory eINSTANCE = GTLanguage.impl.GTLanguageFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>GT Named Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>GT Named Element</em>'.
	 * @generated
	 */
	GTNamedElement createGTNamedElement();

	/**
	 * Returns a new object of class '<em>GT Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>GT Node</em>'.
	 * @generated
	 */
	GTNode createGTNode();

	/**
	 * Returns a new object of class '<em>GT Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>GT Parameter</em>'.
	 * @generated
	 */
	GTParameter createGTParameter();

	/**
	 * Returns a new object of class '<em>GT Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>GT Rule</em>'.
	 * @generated
	 */
	GTRule createGTRule();

	/**
	 * Returns a new object of class '<em>GT Rule Set</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>GT Rule Set</em>'.
	 * @generated
	 */
	GTRuleSet createGTRuleSet();

	/**
	 * Returns a new object of class '<em>GT Probability</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>GT Probability</em>'.
	 * @generated
	 */
	GTProbability createGTProbability();

	/**
	 * Returns a new object of class '<em>GT Arithmetic Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>GT Arithmetic Constraint</em>'.
	 * @generated
	 */
	GTArithmeticConstraint createGTArithmeticConstraint();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	GTLanguagePackage getGTLanguagePackage();

} //GTLanguageFactory
