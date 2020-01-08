/**
 */
package language;

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
 *   <li>{@link language.TGGAttributeConstraintAdornment#getValue <em>Value</em>}</li>
 * </ul>
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
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute list.
	 * @see language.LanguagePackage#getTGGAttributeConstraintAdornment_Value()
	 * @model unique="false"
	 * @generated
	 */
	EList<String> getValue();

} // TGGAttributeConstraintAdornment
