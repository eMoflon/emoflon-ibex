/**
 */
package language.basic.expressions;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see language.basic.expressions.ExpressionsPackage
 * @generated
 */
public interface ExpressionsFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ExpressionsFactory eINSTANCE = language.basic.expressions.impl.ExpressionsFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>TGG Param Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Param Value</em>'.
	 * @generated
	 */
	TGGParamValue createTGGParamValue();

	/**
	 * Returns a new object of class '<em>TGG Literal Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Literal Expression</em>'.
	 * @generated
	 */
	TGGLiteralExpression createTGGLiteralExpression();

	/**
	 * Returns a new object of class '<em>TGG Attribute Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Attribute Expression</em>'.
	 * @generated
	 */
	TGGAttributeExpression createTGGAttributeExpression();

	/**
	 * Returns a new object of class '<em>TGG Enum Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Enum Expression</em>'.
	 * @generated
	 */
	TGGEnumExpression createTGGEnumExpression();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ExpressionsPackage getExpressionsPackage();

} //ExpressionsFactory
