/**
 */
package language;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TGG Attribute Constraint Adornment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link language.TGGAttributeConstraintAdornment#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see language.LanguagePackage#getTGGAttributeConstraintAdornment()
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
	 * @see language.LanguagePackage#getTGGAttributeConstraintAdornment_Value()
	 * @model unique="false"
	 * @generated
	 */
	EList<String> getValue();
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} // TGGAttributeConstraintAdornment
