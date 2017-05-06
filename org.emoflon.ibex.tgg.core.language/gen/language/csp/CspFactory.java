/**
 */
package language.csp;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see language.csp.CspPackage
 * @generated
 */
public interface CspFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CspFactory eINSTANCE = language.csp.impl.CspFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>TGG Attribute Constraint Library</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Attribute Constraint Library</em>'.
	 * @generated
	 */
	TGGAttributeConstraintLibrary createTGGAttributeConstraintLibrary();

	/**
	 * Returns a new object of class '<em>TGG Attribute Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Attribute Constraint</em>'.
	 * @generated
	 */
	TGGAttributeConstraint createTGGAttributeConstraint();

	/**
	 * Returns a new object of class '<em>TGG Attribute Variable</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Attribute Variable</em>'.
	 * @generated
	 */
	TGGAttributeVariable createTGGAttributeVariable();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	CspPackage getCspPackage();

} //CspFactory
