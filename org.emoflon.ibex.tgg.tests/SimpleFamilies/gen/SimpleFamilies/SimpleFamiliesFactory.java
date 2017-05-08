/**
 */
package SimpleFamilies;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see SimpleFamilies.SimpleFamiliesPackage
 * @generated
 */
public interface SimpleFamiliesFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SimpleFamiliesFactory eINSTANCE = SimpleFamilies.impl.SimpleFamiliesFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Family Register</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Family Register</em>'.
	 * @generated
	 */
	FamilyRegister createFamilyRegister();

	/**
	 * Returns a new object of class '<em>Family</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Family</em>'.
	 * @generated
	 */
	Family createFamily();

	/**
	 * Returns a new object of class '<em>Family Member</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Family Member</em>'.
	 * @generated
	 */
	FamilyMember createFamilyMember();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	SimpleFamiliesPackage getSimpleFamiliesPackage();

} //SimpleFamiliesFactory
