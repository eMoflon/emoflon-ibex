/**
 */
package ClassInheritanceHierarchy;

// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link ClassInheritanceHierarchy.Attribute#getClazz <em>Clazz</em>}</li>
 *   <li>{@link ClassInheritanceHierarchy.Attribute#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see ClassInheritanceHierarchy.ClassInheritanceHierarchyPackage#getAttribute()
 * @model
 * @generated
 */
public interface Attribute extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Clazz</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link ClassInheritanceHierarchy.Clazz#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Clazz</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Clazz</em>' container reference.
	 * @see #setClazz(Clazz)
	 * @see ClassInheritanceHierarchy.ClassInheritanceHierarchyPackage#getAttribute_Clazz()
	 * @see ClassInheritanceHierarchy.Clazz#getAttributes
	 * @model opposite="attributes" required="true" transient="false"
	 * @generated
	 */
	Clazz getClazz();

	/**
	 * Sets the value of the '{@link ClassInheritanceHierarchy.Attribute#getClazz <em>Clazz</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Clazz</em>' container reference.
	 * @see #getClazz()
	 * @generated
	 */
	void setClazz(Clazz value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see ClassInheritanceHierarchy.ClassInheritanceHierarchyPackage#getAttribute_Type()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link ClassInheritanceHierarchy.Attribute#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} // Attribute
