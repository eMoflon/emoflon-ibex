/**
 */
package language.csp.definition;

import language.basic.TGGNamedElement;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TGG Attribute Constraint Definition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link language.csp.definition.TGGAttributeConstraintDefinition#isUserDefined <em>User Defined</em>}</li>
 *   <li>{@link language.csp.definition.TGGAttributeConstraintDefinition#getParameterDefinitions <em>Parameter Definitions</em>}</li>
 *   <li>{@link language.csp.definition.TGGAttributeConstraintDefinition#getSyncAdornments <em>Sync Adornments</em>}</li>
 *   <li>{@link language.csp.definition.TGGAttributeConstraintDefinition#getGenAdornments <em>Gen Adornments</em>}</li>
 * </ul>
 *
 * @see language.csp.definition.DefinitionPackage#getTGGAttributeConstraintDefinition()
 * @model
 * @generated
 */
public interface TGGAttributeConstraintDefinition extends TGGNamedElement {
	/**
	 * Returns the value of the '<em><b>User Defined</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>User Defined</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>User Defined</em>' attribute.
	 * @see #setUserDefined(boolean)
	 * @see language.csp.definition.DefinitionPackage#getTGGAttributeConstraintDefinition_UserDefined()
	 * @model default="true"
	 * @generated
	 */
	boolean isUserDefined();

	/**
	 * Sets the value of the '{@link language.csp.definition.TGGAttributeConstraintDefinition#isUserDefined <em>User Defined</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>User Defined</em>' attribute.
	 * @see #isUserDefined()
	 * @generated
	 */
	void setUserDefined(boolean value);

	/**
	 * Returns the value of the '<em><b>Parameter Definitions</b></em>' containment reference list.
	 * The list contents are of type {@link language.csp.definition.TGGAttributeConstraintParameterDefinition}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameter Definitions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameter Definitions</em>' containment reference list.
	 * @see language.csp.definition.DefinitionPackage#getTGGAttributeConstraintDefinition_ParameterDefinitions()
	 * @model containment="true"
	 * @generated
	 */
	EList<TGGAttributeConstraintParameterDefinition> getParameterDefinitions();

	/**
	 * Returns the value of the '<em><b>Sync Adornments</b></em>' containment reference list.
	 * The list contents are of type {@link language.csp.definition.TGGAttributeConstraintAdornment}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sync Adornments</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sync Adornments</em>' containment reference list.
	 * @see language.csp.definition.DefinitionPackage#getTGGAttributeConstraintDefinition_SyncAdornments()
	 * @model containment="true"
	 * @generated
	 */
	EList<TGGAttributeConstraintAdornment> getSyncAdornments();

	/**
	 * Returns the value of the '<em><b>Gen Adornments</b></em>' containment reference list.
	 * The list contents are of type {@link language.csp.definition.TGGAttributeConstraintAdornment}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Gen Adornments</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Gen Adornments</em>' containment reference list.
	 * @see language.csp.definition.DefinitionPackage#getTGGAttributeConstraintDefinition_GenAdornments()
	 * @model containment="true"
	 * @generated
	 */
	EList<TGGAttributeConstraintAdornment> getGenAdornments();

} // TGGAttributeConstraintDefinition
