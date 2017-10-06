/**
 */
package language.csp.definition;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TGG Attribute Constraint Definition Library</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link language.csp.definition.TGGAttributeConstraintDefinitionLibrary#getTggAttributeConstraintDefinitions <em>Tgg Attribute Constraint Definitions</em>}</li>
 * </ul>
 *
 * @see language.csp.definition.DefinitionPackage#getTGGAttributeConstraintDefinitionLibrary()
 * @model
 * @generated
 */
public interface TGGAttributeConstraintDefinitionLibrary extends EObject {
	/**
	 * Returns the value of the '<em><b>Tgg Attribute Constraint Definitions</b></em>' containment reference list.
	 * The list contents are of type {@link language.csp.definition.TGGAttributeConstraintDefinition}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tgg Attribute Constraint Definitions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tgg Attribute Constraint Definitions</em>' containment reference list.
	 * @see language.csp.definition.DefinitionPackage#getTGGAttributeConstraintDefinitionLibrary_TggAttributeConstraintDefinitions()
	 * @model containment="true"
	 * @generated
	 */
	EList<TGGAttributeConstraintDefinition> getTggAttributeConstraintDefinitions();

} // TGGAttributeConstraintDefinitionLibrary
