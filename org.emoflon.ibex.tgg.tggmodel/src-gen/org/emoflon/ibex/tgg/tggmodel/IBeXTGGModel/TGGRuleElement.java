/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel;

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
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleElement#getDomainType <em>Domain Type</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleElement#getBindingType <em>Binding Type</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGRuleElement()
 * @model abstract="true"
 * @generated
 */
public interface TGGRuleElement extends EObject {
	/**
	 * Returns the value of the '<em><b>Domain Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Domain Type</em>' attribute.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType
	 * @see #setDomainType(DomainType)
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGRuleElement_DomainType()
	 * @model
	 * @generated
	 */
	DomainType getDomainType();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleElement#getDomainType <em>Domain Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Domain Type</em>' attribute.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType
	 * @see #getDomainType()
	 * @generated
	 */
	void setDomainType(DomainType value);

	/**
	 * Returns the value of the '<em><b>Binding Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Binding Type</em>' attribute.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType
	 * @see #setBindingType(BindingType)
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGRuleElement_BindingType()
	 * @model
	 * @generated
	 */
	BindingType getBindingType();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleElement#getBindingType <em>Binding Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Binding Type</em>' attribute.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType
	 * @see #getBindingType()
	 * @generated
	 */
	void setBindingType(BindingType value);

} // TGGRuleElement
