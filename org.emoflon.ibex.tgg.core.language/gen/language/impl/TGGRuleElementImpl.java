/**
 */
package language.impl;

import language.BindingType;
import language.DomainType;
import language.LanguagePackage;
import language.TGGRuleElement;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TGG Rule Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link language.impl.TGGRuleElementImpl#getDomainType <em>Domain Type</em>}</li>
 *   <li>{@link language.impl.TGGRuleElementImpl#getBindingType <em>Binding Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class TGGRuleElementImpl extends TGGNamedElementImpl implements TGGRuleElement {
	/**
	 * The default value of the '{@link #getDomainType() <em>Domain Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDomainType()
	 * @generated
	 * @ordered
	 */
	protected static final DomainType DOMAIN_TYPE_EDEFAULT = DomainType.SRC;

	/**
	 * The cached value of the '{@link #getDomainType() <em>Domain Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDomainType()
	 * @generated
	 * @ordered
	 */
	protected DomainType domainType = DOMAIN_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getBindingType() <em>Binding Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBindingType()
	 * @generated
	 * @ordered
	 */
	protected static final BindingType BINDING_TYPE_EDEFAULT = BindingType.CONTEXT;

	/**
	 * The cached value of the '{@link #getBindingType() <em>Binding Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBindingType()
	 * @generated
	 * @ordered
	 */
	protected BindingType bindingType = BINDING_TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TGGRuleElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LanguagePackage.Literals.TGG_RULE_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DomainType getDomainType() {
		return domainType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDomainType(DomainType newDomainType) {
		DomainType oldDomainType = domainType;
		domainType = newDomainType == null ? DOMAIN_TYPE_EDEFAULT : newDomainType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LanguagePackage.TGG_RULE_ELEMENT__DOMAIN_TYPE,
					oldDomainType, domainType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BindingType getBindingType() {
		return bindingType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBindingType(BindingType newBindingType) {
		BindingType oldBindingType = bindingType;
		bindingType = newBindingType == null ? BINDING_TYPE_EDEFAULT : newBindingType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LanguagePackage.TGG_RULE_ELEMENT__BINDING_TYPE,
					oldBindingType, bindingType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case LanguagePackage.TGG_RULE_ELEMENT__DOMAIN_TYPE:
			return getDomainType();
		case LanguagePackage.TGG_RULE_ELEMENT__BINDING_TYPE:
			return getBindingType();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case LanguagePackage.TGG_RULE_ELEMENT__DOMAIN_TYPE:
			setDomainType((DomainType) newValue);
			return;
		case LanguagePackage.TGG_RULE_ELEMENT__BINDING_TYPE:
			setBindingType((BindingType) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case LanguagePackage.TGG_RULE_ELEMENT__DOMAIN_TYPE:
			setDomainType(DOMAIN_TYPE_EDEFAULT);
			return;
		case LanguagePackage.TGG_RULE_ELEMENT__BINDING_TYPE:
			setBindingType(BINDING_TYPE_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case LanguagePackage.TGG_RULE_ELEMENT__DOMAIN_TYPE:
			return domainType != DOMAIN_TYPE_EDEFAULT;
		case LanguagePackage.TGG_RULE_ELEMENT__BINDING_TYPE:
			return bindingType != BINDING_TYPE_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (domainType: ");
		result.append(domainType);
		result.append(", bindingType: ");
		result.append(bindingType);
		result.append(')');
		return result.toString();
	}
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} //TGGRuleElementImpl
