/**
 */
package org.emoflon.ibex.gt.StateModel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.emoflon.ibex.gt.StateModel.RuleState;
import org.emoflon.ibex.gt.StateModel.State;
import org.emoflon.ibex.gt.StateModel.StateModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>State</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.impl.StateImpl#isInitial <em>Initial</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.impl.StateImpl#getChildren <em>Children</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.impl.StateImpl#getHash <em>Hash</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.impl.StateImpl#getPushoutApproach <em>Pushout Approach</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StateImpl extends MinimalEObjectImpl.Container implements State {
	/**
	 * The default value of the '{@link #isInitial() <em>Initial</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isInitial()
	 * @generated
	 * @ordered
	 */
	protected static final boolean INITIAL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isInitial() <em>Initial</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isInitial()
	 * @generated
	 * @ordered
	 */
	protected boolean initial = INITIAL_EDEFAULT;

	/**
	 * The cached value of the '{@link #getChildren() <em>Children</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildren()
	 * @generated
	 * @ordered
	 */
	protected EList<RuleState> children;

	/**
	 * The default value of the '{@link #getHash() <em>Hash</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHash()
	 * @generated
	 * @ordered
	 */
	protected static final long HASH_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getHash() <em>Hash</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHash()
	 * @generated
	 * @ordered
	 */
	protected long hash = HASH_EDEFAULT;

	/**
	 * The default value of the '{@link #getPushoutApproach() <em>Pushout Approach</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPushoutApproach()
	 * @generated
	 * @ordered
	 */
	protected static final int PUSHOUT_APPROACH_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getPushoutApproach() <em>Pushout Approach</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPushoutApproach()
	 * @generated
	 * @ordered
	 */
	protected int pushoutApproach = PUSHOUT_APPROACH_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StateModelPackage.Literals.STATE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isInitial() {
		return initial;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setInitial(boolean newInitial) {
		boolean oldInitial = initial;
		initial = newInitial;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateModelPackage.STATE__INITIAL, oldInitial,
					initial));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<RuleState> getChildren() {
		if (children == null) {
			children = new EObjectResolvingEList<RuleState>(RuleState.class, this, StateModelPackage.STATE__CHILDREN);
		}
		return children;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public long getHash() {
		return hash;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setHash(long newHash) {
		long oldHash = hash;
		hash = newHash;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateModelPackage.STATE__HASH, oldHash, hash));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getPushoutApproach() {
		return pushoutApproach;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPushoutApproach(int newPushoutApproach) {
		int oldPushoutApproach = pushoutApproach;
		pushoutApproach = newPushoutApproach;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateModelPackage.STATE__PUSHOUT_APPROACH,
					oldPushoutApproach, pushoutApproach));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case StateModelPackage.STATE__INITIAL:
			return isInitial();
		case StateModelPackage.STATE__CHILDREN:
			return getChildren();
		case StateModelPackage.STATE__HASH:
			return getHash();
		case StateModelPackage.STATE__PUSHOUT_APPROACH:
			return getPushoutApproach();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case StateModelPackage.STATE__INITIAL:
			setInitial((Boolean) newValue);
			return;
		case StateModelPackage.STATE__CHILDREN:
			getChildren().clear();
			getChildren().addAll((Collection<? extends RuleState>) newValue);
			return;
		case StateModelPackage.STATE__HASH:
			setHash((Long) newValue);
			return;
		case StateModelPackage.STATE__PUSHOUT_APPROACH:
			setPushoutApproach((Integer) newValue);
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
		case StateModelPackage.STATE__INITIAL:
			setInitial(INITIAL_EDEFAULT);
			return;
		case StateModelPackage.STATE__CHILDREN:
			getChildren().clear();
			return;
		case StateModelPackage.STATE__HASH:
			setHash(HASH_EDEFAULT);
			return;
		case StateModelPackage.STATE__PUSHOUT_APPROACH:
			setPushoutApproach(PUSHOUT_APPROACH_EDEFAULT);
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
		case StateModelPackage.STATE__INITIAL:
			return initial != INITIAL_EDEFAULT;
		case StateModelPackage.STATE__CHILDREN:
			return children != null && !children.isEmpty();
		case StateModelPackage.STATE__HASH:
			return hash != HASH_EDEFAULT;
		case StateModelPackage.STATE__PUSHOUT_APPROACH:
			return pushoutApproach != PUSHOUT_APPROACH_EDEFAULT;
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
		result.append(" (initial: ");
		result.append(initial);
		result.append(", hash: ");
		result.append(hash);
		result.append(", pushoutApproach: ");
		result.append(pushoutApproach);
		result.append(')');
		return result.toString();
	}

} //StateImpl
