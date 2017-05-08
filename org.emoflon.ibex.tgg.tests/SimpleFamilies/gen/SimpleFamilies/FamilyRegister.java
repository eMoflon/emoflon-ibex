/**
 */
package SimpleFamilies;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Family Register</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link SimpleFamilies.FamilyRegister#getFamilies <em>Families</em>}</li>
 * </ul>
 * </p>
 *
 * @see SimpleFamilies.SimpleFamiliesPackage#getFamilyRegister()
 * @model
 * @generated
 */
public interface FamilyRegister extends EObject {
	/**
	 * Returns the value of the '<em><b>Families</b></em>' containment reference list.
	 * The list contents are of type {@link SimpleFamilies.Family}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Families</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Families</em>' containment reference list.
	 * @see SimpleFamilies.SimpleFamiliesPackage#getFamilyRegister_Families()
	 * @model containment="true" upper="5"
	 * @generated
	 */
	EList<Family> getFamilies();
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} // FamilyRegister
