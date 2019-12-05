/**
 */
package language;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TGG Attribute Constraint Definition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link language.TGGAttributeConstraintDefinition#isUserDefined <em>User Defined</em>}</li>
 *   <li>{@link language.TGGAttributeConstraintDefinition#getParameterDefinitions <em>Parameter Definitions</em>}</li>
 *   <li>{@link language.TGGAttributeConstraintDefinition#getSyncAdornments <em>Sync Adornments</em>}</li>
 *   <li>{@link language.TGGAttributeConstraintDefinition#getGenAdornments <em>Gen Adornments</em>}</li>
 * </ul>
 *
 * @see language.LanguagePackage#getTGGAttributeConstraintDefinition()
 * @model
 * @generated
 */
public interface TGGAttributeConstraintDefinition extends EObject, TGGNamedElement {
	/**
	 * Returns the value of the '<em><b>User Defined</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>User Defined</em>' attribute.
	 * @see #setUserDefined(boolean)
	 * @see language.LanguagePackage#getTGGAttributeConstraintDefinition_UserDefined()
	 * @model default="true"
	 * @generated
	 */
	boolean isUserDefined();

	/**
	 * Sets the value of the '{@link language.TGGAttributeConstraintDefinition#isUserDefined <em>User Defined</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>User Defined</em>' attribute.
	 * @see #isUserDefined()
	 * @generated
	 */
	void setUserDefined(boolean value);

	/**
	 * Returns the value of the '<em><b>Parameter Definitions</b></em>' containment reference list.
	 * The list contents are of type {@link language.TGGAttributeConstraintParameterDefinition}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameter Definitions</em>' containment reference list.
	 * @see language.LanguagePackage#getTGGAttributeConstraintDefinition_ParameterDefinitions()
	 * @model containment="true"
	 * @generated
	 */
	EList<TGGAttributeConstraintParameterDefinition> getParameterDefinitions();

	/**
	 * Returns the value of the '<em><b>Sync Adornments</b></em>' containment reference list.
	 * The list contents are of type {@link language.TGGAttributeConstraintAdornment}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sync Adornments</em>' containment reference list.
	 * @see language.LanguagePackage#getTGGAttributeConstraintDefinition_SyncAdornments()
	 * @model containment="true"
	 * @generated
	 */
	EList<TGGAttributeConstraintAdornment> getSyncAdornments();

	/**
	 * Returns the value of the '<em><b>Gen Adornments</b></em>' containment reference list.
	 * The list contents are of type {@link language.TGGAttributeConstraintAdornment}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Gen Adornments</em>' containment reference list.
	 * @see language.LanguagePackage#getTGGAttributeConstraintDefinition_GenAdornments()
	 * @model containment="true"
	 * @generated
	 */
	EList<TGGAttributeConstraintAdornment> getGenAdornments();

} // TGGAttributeConstraintDefinition
