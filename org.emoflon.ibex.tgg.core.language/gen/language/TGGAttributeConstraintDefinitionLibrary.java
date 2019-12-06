/**
 */
package language;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TGG Attribute Constraint Definition Library</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link language.TGGAttributeConstraintDefinitionLibrary#getTggAttributeConstraintDefinitions <em>Tgg Attribute Constraint Definitions</em>}</li>
 * </ul>
 * </p>
 *
 * @see language.LanguagePackage#getTGGAttributeConstraintDefinitionLibrary()
 * @model
 * @generated
 */
public interface TGGAttributeConstraintDefinitionLibrary extends EObject {
	/**
	 * Returns the value of the '<em><b>Tgg Attribute Constraint Definitions</b></em>' containment reference list.
	 * The list contents are of type {@link language.TGGAttributeConstraintDefinition}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tgg Attribute Constraint Definitions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tgg Attribute Constraint Definitions</em>' containment reference list.
	 * @see language.LanguagePackage#getTGGAttributeConstraintDefinitionLibrary_TggAttributeConstraintDefinitions()
	 * @model containment="true"
	 * @generated
	 */
	EList<TGGAttributeConstraintDefinition> getTggAttributeConstraintDefinitions();
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} // TGGAttributeConstraintDefinitionLibrary
