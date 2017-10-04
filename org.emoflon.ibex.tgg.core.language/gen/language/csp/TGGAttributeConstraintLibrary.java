/**
 */
package language.csp;

import language.basic.expressions.TGGParamValue;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TGG Attribute Constraint Library</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link language.csp.TGGAttributeConstraintLibrary#getTggAttributeConstraints <em>Tgg Attribute Constraints</em>}</li>
 *   <li>{@link language.csp.TGGAttributeConstraintLibrary#getParameterValues <em>Parameter Values</em>}</li>
 *   <li>{@link language.csp.TGGAttributeConstraintLibrary#getSorted_BWD <em>Sorted BWD</em>}</li>
 *   <li>{@link language.csp.TGGAttributeConstraintLibrary#getSorted_FWD <em>Sorted FWD</em>}</li>
 *   <li>{@link language.csp.TGGAttributeConstraintLibrary#getSorted_CC <em>Sorted CC</em>}</li>
 *   <li>{@link language.csp.TGGAttributeConstraintLibrary#getSorted_MODELGEN <em>Sorted MODELGEN</em>}</li>
 * </ul>
 *
 * @see language.csp.CspPackage#getTGGAttributeConstraintLibrary()
 * @model
 * @generated
 */
public interface TGGAttributeConstraintLibrary extends EObject {
	/**
	 * Returns the value of the '<em><b>Tgg Attribute Constraints</b></em>' containment reference list.
	 * The list contents are of type {@link language.csp.TGGAttributeConstraint}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tgg Attribute Constraints</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tgg Attribute Constraints</em>' containment reference list.
	 * @see language.csp.CspPackage#getTGGAttributeConstraintLibrary_TggAttributeConstraints()
	 * @model containment="true"
	 * @generated
	 */
	EList<TGGAttributeConstraint> getTggAttributeConstraints();

	/**
	 * Returns the value of the '<em><b>Parameter Values</b></em>' containment reference list.
	 * The list contents are of type {@link language.basic.expressions.TGGParamValue}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameter Values</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameter Values</em>' containment reference list.
	 * @see language.csp.CspPackage#getTGGAttributeConstraintLibrary_ParameterValues()
	 * @model containment="true"
	 * @generated
	 */
	EList<TGGParamValue> getParameterValues();

	/**
	 * Returns the value of the '<em><b>Sorted BWD</b></em>' reference list.
	 * The list contents are of type {@link language.csp.TGGAttributeConstraint}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sorted BWD</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sorted BWD</em>' reference list.
	 * @see language.csp.CspPackage#getTGGAttributeConstraintLibrary_Sorted_BWD()
	 * @model
	 * @generated
	 */
	EList<TGGAttributeConstraint> getSorted_BWD();

	/**
	 * Returns the value of the '<em><b>Sorted FWD</b></em>' reference list.
	 * The list contents are of type {@link language.csp.TGGAttributeConstraint}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sorted FWD</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sorted FWD</em>' reference list.
	 * @see language.csp.CspPackage#getTGGAttributeConstraintLibrary_Sorted_FWD()
	 * @model
	 * @generated
	 */
	EList<TGGAttributeConstraint> getSorted_FWD();

	/**
	 * Returns the value of the '<em><b>Sorted CC</b></em>' reference list.
	 * The list contents are of type {@link language.csp.TGGAttributeConstraint}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sorted CC</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sorted CC</em>' reference list.
	 * @see language.csp.CspPackage#getTGGAttributeConstraintLibrary_Sorted_CC()
	 * @model
	 * @generated
	 */
	EList<TGGAttributeConstraint> getSorted_CC();

	/**
	 * Returns the value of the '<em><b>Sorted MODELGEN</b></em>' reference list.
	 * The list contents are of type {@link language.csp.TGGAttributeConstraint}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sorted MODELGEN</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sorted MODELGEN</em>' reference list.
	 * @see language.csp.CspPackage#getTGGAttributeConstraintLibrary_Sorted_MODELGEN()
	 * @model
	 * @generated
	 */
	EList<TGGAttributeConstraint> getSorted_MODELGEN();

} // TGGAttributeConstraintLibrary
