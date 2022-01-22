/**
 */
package runtime;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see runtime.RuntimePackage
 * @generated
 */
public interface RuntimeFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RuntimeFactory eINSTANCE = runtime.impl.RuntimeFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Protocol</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Protocol</em>'.
	 * @generated
	 */
	Protocol createProtocol();

	/**
	 * Returns a new object of class '<em>TGG Rule Application</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Rule Application</em>'.
	 * @generated
	 */
	TGGRuleApplication createTGGRuleApplication();

	/**
	 * Returns a new object of class '<em>Temp Container</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Temp Container</em>'.
	 * @generated
	 */
	TempContainer createTempContainer();

	/**
	 * Returns a new object of class '<em>Correspondence Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Correspondence Node</em>'.
	 * @generated
	 */
	CorrespondenceNode createCorrespondenceNode();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	RuntimePackage getRuntimePackage();

} //RuntimeFactory
