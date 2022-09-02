/**
 */
package org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.impl.ValueExpressionImpl;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEnumValue;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IBe XEnum Value</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXEnumValueImpl#getLiteral <em>Literal</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IBeXEnumValueImpl extends ValueExpressionImpl implements IBeXEnumValue {
	/**
	 * The cached value of the '{@link #getLiteral() <em>Literal</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLiteral()
	 * @generated
	 * @ordered
	 */
	protected EEnumLiteral literal;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IBeXEnumValueImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXCoreModelPackage.Literals.IBE_XENUM_VALUE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnumLiteral getLiteral() {
		if (literal != null && literal.eIsProxy()) {
			InternalEObject oldLiteral = (InternalEObject) literal;
			literal = (EEnumLiteral) eResolveProxy(oldLiteral);
			if (literal != oldLiteral) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							IBeXCoreModelPackage.IBE_XENUM_VALUE__LITERAL, oldLiteral, literal));
			}
		}
		return literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnumLiteral basicGetLiteral() {
		return literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLiteral(EEnumLiteral newLiteral) {
		EEnumLiteral oldLiteral = literal;
		literal = newLiteral;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXCoreModelPackage.IBE_XENUM_VALUE__LITERAL,
					oldLiteral, literal));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case IBeXCoreModelPackage.IBE_XENUM_VALUE__LITERAL:
			if (resolve)
				return getLiteral();
			return basicGetLiteral();
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
		case IBeXCoreModelPackage.IBE_XENUM_VALUE__LITERAL:
			setLiteral((EEnumLiteral) newValue);
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
		case IBeXCoreModelPackage.IBE_XENUM_VALUE__LITERAL:
			setLiteral((EEnumLiteral) null);
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
		case IBeXCoreModelPackage.IBE_XENUM_VALUE__LITERAL:
			return literal != null;
		}
		return super.eIsSet(featureID);
	}

} //IBeXEnumValueImpl
