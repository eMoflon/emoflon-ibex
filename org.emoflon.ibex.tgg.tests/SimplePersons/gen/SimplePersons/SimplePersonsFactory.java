/**
 */
package SimplePersons;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see SimplePersons.SimplePersonsPackage
 * @generated
 */
public interface SimplePersonsFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SimplePersonsFactory eINSTANCE = SimplePersons.impl.SimplePersonsFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Person Register</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Person Register</em>'.
	 * @generated
	 */
	PersonRegister createPersonRegister();

	/**
	 * Returns a new object of class '<em>Male</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Male</em>'.
	 * @generated
	 */
	Male createMale();

	/**
	 * Returns a new object of class '<em>Female</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Female</em>'.
	 * @generated
	 */
	Female createFemale();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	SimplePersonsPackage getSimplePersonsPackage();

} //SimplePersonsFactory
