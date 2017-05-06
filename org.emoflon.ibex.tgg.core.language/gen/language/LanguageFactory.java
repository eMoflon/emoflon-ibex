/**
 */
package language;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see language.LanguagePackage
 * @generated
 */
public interface LanguageFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	LanguageFactory eINSTANCE = language.impl.LanguageFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>TGG</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG</em>'.
	 * @generated
	 */
	TGG createTGG();

	/**
	 * Returns a new object of class '<em>TGG Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Rule</em>'.
	 * @generated
	 */
	TGGRule createTGGRule();

	/**
	 * Returns a new object of class '<em>TGG Rule Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Rule Node</em>'.
	 * @generated
	 */
	TGGRuleNode createTGGRuleNode();

	/**
	 * Returns a new object of class '<em>TGG Rule Corr</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Rule Corr</em>'.
	 * @generated
	 */
	TGGRuleCorr createTGGRuleCorr();

	/**
	 * Returns a new object of class '<em>TGG Rule Edge</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Rule Edge</em>'.
	 * @generated
	 */
	TGGRuleEdge createTGGRuleEdge();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	LanguagePackage getLanguagePackage();

} //LanguageFactory
