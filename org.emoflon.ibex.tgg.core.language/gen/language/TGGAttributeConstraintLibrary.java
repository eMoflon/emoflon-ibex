/**
 */
package language;

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
 *   <li>{@link language.TGGAttributeConstraintLibrary#getTggAttributeConstraints <em>Tgg Attribute Constraints</em>}</li>
 *   <li>{@link language.TGGAttributeConstraintLibrary#getParameterValues <em>Parameter Values</em>}</li>
 * </ul>
 *
 * @see language.LanguagePackage#getTGGAttributeConstraintLibrary()
 * @model
 * @generated
 */
public interface TGGAttributeConstraintLibrary extends EObject {
	/**
	 * Returns the value of the '<em><b>Tgg Attribute Constraints</b></em>' containment reference list.
	 * The list contents are of type {@link language.TGGAttributeConstraint}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tgg Attribute Constraints</em>' containment reference list.
	 * @see language.LanguagePackage#getTGGAttributeConstraintLibrary_TggAttributeConstraints()
	 * @model containment="true"
	 * @generated
	 */
	EList<TGGAttributeConstraint> getTggAttributeConstraints();

	/**
	 * Returns the value of the '<em><b>Parameter Values</b></em>' containment reference list.
	 * The list contents are of type {@link language.TGGParamValue}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameter Values</em>' containment reference list.
	 * @see language.LanguagePackage#getTGGAttributeConstraintLibrary_ParameterValues()
	 * @model containment="true"
	 * @generated
	 */
	EList<TGGParamValue> getParameterValues();

} // TGGAttributeConstraintLibrary
