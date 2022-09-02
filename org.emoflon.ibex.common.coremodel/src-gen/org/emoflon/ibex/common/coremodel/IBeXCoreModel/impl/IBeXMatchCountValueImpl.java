/**
 */
package org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.impl.ArithmeticValueImpl;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXMatchCountValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternInvocation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IBe XMatch Count Value</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXMatchCountValueImpl#getInvocation <em>Invocation</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IBeXMatchCountValueImpl extends ArithmeticValueImpl implements IBeXMatchCountValue {
	/**
	 * The cached value of the '{@link #getInvocation() <em>Invocation</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInvocation()
	 * @generated
	 * @ordered
	 */
	protected IBeXPatternInvocation invocation;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IBeXMatchCountValueImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXCoreModelPackage.Literals.IBE_XMATCH_COUNT_VALUE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXPatternInvocation getInvocation() {
		return invocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInvocation(IBeXPatternInvocation newInvocation, NotificationChain msgs) {
		IBeXPatternInvocation oldInvocation = invocation;
		invocation = newInvocation;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXCoreModelPackage.IBE_XMATCH_COUNT_VALUE__INVOCATION, oldInvocation, newInvocation);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInvocation(IBeXPatternInvocation newInvocation) {
		if (newInvocation != invocation) {
			NotificationChain msgs = null;
			if (invocation != null)
				msgs = ((InternalEObject) invocation).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXCoreModelPackage.IBE_XMATCH_COUNT_VALUE__INVOCATION, null, msgs);
			if (newInvocation != null)
				msgs = ((InternalEObject) newInvocation).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXCoreModelPackage.IBE_XMATCH_COUNT_VALUE__INVOCATION, null, msgs);
			msgs = basicSetInvocation(newInvocation, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXCoreModelPackage.IBE_XMATCH_COUNT_VALUE__INVOCATION, newInvocation, newInvocation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IBeXCoreModelPackage.IBE_XMATCH_COUNT_VALUE__INVOCATION:
			return basicSetInvocation(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case IBeXCoreModelPackage.IBE_XMATCH_COUNT_VALUE__INVOCATION:
			return getInvocation();
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
		case IBeXCoreModelPackage.IBE_XMATCH_COUNT_VALUE__INVOCATION:
			setInvocation((IBeXPatternInvocation) newValue);
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
		case IBeXCoreModelPackage.IBE_XMATCH_COUNT_VALUE__INVOCATION:
			setInvocation((IBeXPatternInvocation) null);
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
		case IBeXCoreModelPackage.IBE_XMATCH_COUNT_VALUE__INVOCATION:
			return invocation != null;
		}
		return super.eIsSet(featureID);
	}

} //IBeXMatchCountValueImpl
