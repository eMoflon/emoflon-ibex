/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXEdgeImpl;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGEdge;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleElement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TGG Edge</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGEdgeImpl#getDomainType <em>Domain Type</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGEdgeImpl#getBindingType <em>Binding Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TGGEdgeImpl extends IBeXEdgeImpl implements TGGEdge {
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
	protected TGGEdgeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXTGGModelPackage.Literals.TGG_EDGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomainType getDomainType() {
		return domainType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDomainType(DomainType newDomainType) {
		DomainType oldDomainType = domainType;
		domainType = newDomainType == null ? DOMAIN_TYPE_EDEFAULT : newDomainType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXTGGModelPackage.TGG_EDGE__DOMAIN_TYPE,
					oldDomainType, domainType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BindingType getBindingType() {
		return bindingType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBindingType(BindingType newBindingType) {
		BindingType oldBindingType = bindingType;
		bindingType = newBindingType == null ? BINDING_TYPE_EDEFAULT : newBindingType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXTGGModelPackage.TGG_EDGE__BINDING_TYPE,
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
		case IBeXTGGModelPackage.TGG_EDGE__DOMAIN_TYPE:
			return getDomainType();
		case IBeXTGGModelPackage.TGG_EDGE__BINDING_TYPE:
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
		case IBeXTGGModelPackage.TGG_EDGE__DOMAIN_TYPE:
			setDomainType((DomainType) newValue);
			return;
		case IBeXTGGModelPackage.TGG_EDGE__BINDING_TYPE:
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
		case IBeXTGGModelPackage.TGG_EDGE__DOMAIN_TYPE:
			setDomainType(DOMAIN_TYPE_EDEFAULT);
			return;
		case IBeXTGGModelPackage.TGG_EDGE__BINDING_TYPE:
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
		case IBeXTGGModelPackage.TGG_EDGE__DOMAIN_TYPE:
			return domainType != DOMAIN_TYPE_EDEFAULT;
		case IBeXTGGModelPackage.TGG_EDGE__BINDING_TYPE:
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
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == TGGRuleElement.class) {
			switch (derivedFeatureID) {
			case IBeXTGGModelPackage.TGG_EDGE__DOMAIN_TYPE:
				return IBeXTGGModelPackage.TGG_RULE_ELEMENT__DOMAIN_TYPE;
			case IBeXTGGModelPackage.TGG_EDGE__BINDING_TYPE:
				return IBeXTGGModelPackage.TGG_RULE_ELEMENT__BINDING_TYPE;
			default:
				return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == TGGRuleElement.class) {
			switch (baseFeatureID) {
			case IBeXTGGModelPackage.TGG_RULE_ELEMENT__DOMAIN_TYPE:
				return IBeXTGGModelPackage.TGG_EDGE__DOMAIN_TYPE;
			case IBeXTGGModelPackage.TGG_RULE_ELEMENT__BINDING_TYPE:
				return IBeXTGGModelPackage.TGG_EDGE__BINDING_TYPE;
			default:
				return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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

} //TGGEdgeImpl
