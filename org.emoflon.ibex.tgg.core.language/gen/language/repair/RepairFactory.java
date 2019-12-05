/**
 */
package language.repair;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see language.repair.RepairPackage
 * @generated
 */
public interface RepairFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RepairFactory eINSTANCE = language.repair.impl.RepairFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>External Shortcut Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>External Shortcut Rule</em>'.
	 * @generated
	 */
	ExternalShortcutRule createExternalShortcutRule();

	/**
	 * Returns a new object of class '<em>TGG Rule Element Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Rule Element Mapping</em>'.
	 * @generated
	 */
	TGGRuleElementMapping createTGGRuleElementMapping();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	RepairPackage getRepairPackage();

} //RepairFactory
