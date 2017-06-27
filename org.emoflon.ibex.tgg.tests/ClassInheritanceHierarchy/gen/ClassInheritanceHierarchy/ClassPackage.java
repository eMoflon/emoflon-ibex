/**
 */
package ClassInheritanceHierarchy;

import org.eclipse.emf.common.util.EList;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Class Package</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link ClassInheritanceHierarchy.ClassPackage#getClasses <em>Classes</em>}</li>
 * </ul>
 * </p>
 *
 * @see ClassInheritanceHierarchy.ClassInheritanceHierarchyPackage#getClassPackage()
 * @model
 * @generated
 */
public interface ClassPackage extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Classes</b></em>' containment reference list.
	 * The list contents are of type {@link ClassInheritanceHierarchy.Clazz}.
	 * It is bidirectional and its opposite is '{@link ClassInheritanceHierarchy.Clazz#getPackage <em>Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Classes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Classes</em>' containment reference list.
	 * @see ClassInheritanceHierarchy.ClassInheritanceHierarchyPackage#getClassPackage_Classes()
	 * @see ClassInheritanceHierarchy.Clazz#getPackage
	 * @model opposite="package" containment="true"
	 * @generated
	 */
	EList<Clazz> getClasses();
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} // ClassPackage
