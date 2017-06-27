/**
 */
package ClassInheritanceHierarchy;

import org.eclipse.emf.common.util.EList;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Clazz</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link ClassInheritanceHierarchy.Clazz#getSuperClass <em>Super Class</em>}</li>
 *   <li>{@link ClassInheritanceHierarchy.Clazz#getSubClass <em>Sub Class</em>}</li>
 *   <li>{@link ClassInheritanceHierarchy.Clazz#getPackage <em>Package</em>}</li>
 *   <li>{@link ClassInheritanceHierarchy.Clazz#getAttributes <em>Attributes</em>}</li>
 * </ul>
 * </p>
 *
 * @see ClassInheritanceHierarchy.ClassInheritanceHierarchyPackage#getClazz()
 * @model
 * @generated
 */
public interface Clazz extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Super Class</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link ClassInheritanceHierarchy.Clazz#getSubClass <em>Sub Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Super Class</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Super Class</em>' reference.
	 * @see #setSuperClass(Clazz)
	 * @see ClassInheritanceHierarchy.ClassInheritanceHierarchyPackage#getClazz_SuperClass()
	 * @see ClassInheritanceHierarchy.Clazz#getSubClass
	 * @model opposite="subClass" required="true"
	 * @generated
	 */
	Clazz getSuperClass();

	/**
	 * Sets the value of the '{@link ClassInheritanceHierarchy.Clazz#getSuperClass <em>Super Class</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Super Class</em>' reference.
	 * @see #getSuperClass()
	 * @generated
	 */
	void setSuperClass(Clazz value);

	/**
	 * Returns the value of the '<em><b>Sub Class</b></em>' reference list.
	 * The list contents are of type {@link ClassInheritanceHierarchy.Clazz}.
	 * It is bidirectional and its opposite is '{@link ClassInheritanceHierarchy.Clazz#getSuperClass <em>Super Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sub Class</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sub Class</em>' reference list.
	 * @see ClassInheritanceHierarchy.ClassInheritanceHierarchyPackage#getClazz_SubClass()
	 * @see ClassInheritanceHierarchy.Clazz#getSuperClass
	 * @model opposite="superClass"
	 * @generated
	 */
	EList<Clazz> getSubClass();

	/**
	 * Returns the value of the '<em><b>Package</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link ClassInheritanceHierarchy.ClassPackage#getClasses <em>Classes</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Package</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Package</em>' container reference.
	 * @see #setPackage(ClassPackage)
	 * @see ClassInheritanceHierarchy.ClassInheritanceHierarchyPackage#getClazz_Package()
	 * @see ClassInheritanceHierarchy.ClassPackage#getClasses
	 * @model opposite="classes" required="true" transient="false"
	 * @generated
	 */
	ClassPackage getPackage();

	/**
	 * Sets the value of the '{@link ClassInheritanceHierarchy.Clazz#getPackage <em>Package</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Package</em>' container reference.
	 * @see #getPackage()
	 * @generated
	 */
	void setPackage(ClassPackage value);

	/**
	 * Returns the value of the '<em><b>Attributes</b></em>' containment reference list.
	 * The list contents are of type {@link ClassInheritanceHierarchy.Attribute}.
	 * It is bidirectional and its opposite is '{@link ClassInheritanceHierarchy.Attribute#getClazz <em>Clazz</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attributes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attributes</em>' containment reference list.
	 * @see ClassInheritanceHierarchy.ClassInheritanceHierarchyPackage#getClazz_Attributes()
	 * @see ClassInheritanceHierarchy.Attribute#getClazz
	 * @model opposite="clazz" containment="true"
	 * @generated
	 */
	EList<Attribute> getAttributes();
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} // Clazz
