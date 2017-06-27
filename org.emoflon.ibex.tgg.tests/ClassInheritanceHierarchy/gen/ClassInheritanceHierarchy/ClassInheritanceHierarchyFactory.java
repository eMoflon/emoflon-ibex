/**
 */
package ClassInheritanceHierarchy;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see ClassInheritanceHierarchy.ClassInheritanceHierarchyPackage
 * @generated
 */
public interface ClassInheritanceHierarchyFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ClassInheritanceHierarchyFactory eINSTANCE = ClassInheritanceHierarchy.impl.ClassInheritanceHierarchyFactoryImpl
			.init();

	/**
	 * Returns a new object of class '<em>Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Attribute</em>'.
	 * @generated
	 */
	Attribute createAttribute();

	/**
	 * Returns a new object of class '<em>Class Package</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Class Package</em>'.
	 * @generated
	 */
	ClassPackage createClassPackage();

	/**
	 * Returns a new object of class '<em>Clazz</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Clazz</em>'.
	 * @generated
	 */
	Clazz createClazz();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	ClassInheritanceHierarchyPackage getClassInheritanceHierarchyPackage();

} //ClassInheritanceHierarchyFactory
