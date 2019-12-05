/**
 */
package language;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TGG Rule Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link language.TGGRuleElement#getDomainType <em>Domain Type</em>}</li>
 *   <li>{@link language.TGGRuleElement#getBindingType <em>Binding Type</em>}</li>
 * </ul>
 *
 * @see language.LanguagePackage#getTGGRuleElement()
 * @model abstract="true"
 * @generated
 */
public interface TGGRuleElement extends EObject, TGGNamedElement {
	/**
	 * Returns the value of the '<em><b>Domain Type</b></em>' attribute.
	 * The literals are from the enumeration {@link language.DomainType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Domain Type</em>' attribute.
	 * @see language.DomainType
	 * @see #setDomainType(DomainType)
	 * @see language.LanguagePackage#getTGGRuleElement_DomainType()
	 * @model
	 * @generated
	 */
	DomainType getDomainType();

	/**
	 * Sets the value of the '{@link language.TGGRuleElement#getDomainType <em>Domain Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Domain Type</em>' attribute.
	 * @see language.DomainType
	 * @see #getDomainType()
	 * @generated
	 */
	void setDomainType(DomainType value);

	/**
	 * Returns the value of the '<em><b>Binding Type</b></em>' attribute.
	 * The literals are from the enumeration {@link language.BindingType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Binding Type</em>' attribute.
	 * @see language.BindingType
	 * @see #setBindingType(BindingType)
	 * @see language.LanguagePackage#getTGGRuleElement_BindingType()
	 * @model
	 * @generated
	 */
	BindingType getBindingType();

	/**
	 * Sets the value of the '{@link language.TGGRuleElement#getBindingType <em>Binding Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Binding Type</em>' attribute.
	 * @see language.BindingType
	 * @see #getBindingType()
	 * @generated
	 */
	void setBindingType(BindingType value);

} // TGGRuleElement
