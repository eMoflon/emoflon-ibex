/**
 */
package SimpleFamilies;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Family</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link SimpleFamilies.Family#getFather <em>Father</em>}</li>
 *   <li>{@link SimpleFamilies.Family#getMother <em>Mother</em>}</li>
 *   <li>{@link SimpleFamilies.Family#getSons <em>Sons</em>}</li>
 *   <li>{@link SimpleFamilies.Family#getDaughters <em>Daughters</em>}</li>
 *   <li>{@link SimpleFamilies.Family#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see SimpleFamilies.SimpleFamiliesPackage#getFamily()
 * @model
 * @generated
 */
public interface Family extends EObject {
	/**
	 * Returns the value of the '<em><b>Father</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Father</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Father</em>' containment reference.
	 * @see #setFather(FamilyMember)
	 * @see SimpleFamilies.SimpleFamiliesPackage#getFamily_Father()
	 * @model containment="true"
	 * @generated
	 */
	FamilyMember getFather();

	/**
	 * Sets the value of the '{@link SimpleFamilies.Family#getFather <em>Father</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Father</em>' containment reference.
	 * @see #getFather()
	 * @generated
	 */
	void setFather(FamilyMember value);

	/**
	 * Returns the value of the '<em><b>Mother</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mother</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mother</em>' containment reference.
	 * @see #setMother(FamilyMember)
	 * @see SimpleFamilies.SimpleFamiliesPackage#getFamily_Mother()
	 * @model containment="true"
	 * @generated
	 */
	FamilyMember getMother();

	/**
	 * Sets the value of the '{@link SimpleFamilies.Family#getMother <em>Mother</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mother</em>' containment reference.
	 * @see #getMother()
	 * @generated
	 */
	void setMother(FamilyMember value);

	/**
	 * Returns the value of the '<em><b>Sons</b></em>' containment reference list.
	 * The list contents are of type {@link SimpleFamilies.FamilyMember}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sons</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sons</em>' containment reference list.
	 * @see SimpleFamilies.SimpleFamiliesPackage#getFamily_Sons()
	 * @model containment="true"
	 * @generated
	 */
	EList<FamilyMember> getSons();

	/**
	 * Returns the value of the '<em><b>Daughters</b></em>' containment reference list.
	 * The list contents are of type {@link SimpleFamilies.FamilyMember}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Daughters</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Daughters</em>' containment reference list.
	 * @see SimpleFamilies.SimpleFamiliesPackage#getFamily_Daughters()
	 * @model containment="true"
	 * @generated
	 */
	EList<FamilyMember> getDaughters();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see SimpleFamilies.SimpleFamiliesPackage#getFamily_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link SimpleFamilies.Family#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} // Family
