/**
 */
package language.csp.definition;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TGG Attribute Constraint Adornment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link language.csp.definition.TGGAttributeConstraintAdornment#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see language.csp.definition.DefinitionPackage#getTGGAttributeConstraintAdornment()
 * @model
 * @generated
 */
public interface TGGAttributeConstraintAdornment extends EObject {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute list.
	 * @see language.csp.definition.DefinitionPackage#getTGGAttributeConstraintAdornment_Value()
	 * @model unique="false"
	 * @generated
	 */
	EList<String> getValue();

} // TGGAttributeConstraintAdornment
