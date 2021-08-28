/**
 */
package org.emoflon.ibex.gt.StateModel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.emoflon.ibex.gt.StateModel.IBeXMatch;
import org.emoflon.ibex.gt.StateModel.State;
import org.emoflon.ibex.gt.StateModel.StateContainer;
import org.emoflon.ibex.gt.StateModel.StateModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>State Container</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.impl.StateContainerImpl#getStates <em>States</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.impl.StateContainerImpl#getInitialState <em>Initial State</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.impl.StateContainerImpl#getInitialMatches <em>Initial Matches</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StateContainerImpl extends MinimalEObjectImpl.Container implements StateContainer {
	/**
	 * The cached value of the '{@link #getStates() <em>States</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStates()
	 * @generated
	 * @ordered
	 */
	protected EList<State> states;

	/**
	 * The cached value of the '{@link #getInitialState() <em>Initial State</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitialState()
	 * @generated
	 * @ordered
	 */
	protected State initialState;

	/**
	 * The cached value of the '{@link #getInitialMatches() <em>Initial Matches</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitialMatches()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXMatch> initialMatches;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StateContainerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StateModelPackage.Literals.STATE_CONTAINER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<State> getStates() {
		if (states == null) {
			states = new EObjectContainmentEList<State>(State.class, this, StateModelPackage.STATE_CONTAINER__STATES);
		}
		return states;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public State getInitialState() {
		return initialState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInitialState(State newInitialState, NotificationChain msgs) {
		State oldInitialState = initialState;
		initialState = newInitialState;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					StateModelPackage.STATE_CONTAINER__INITIAL_STATE, oldInitialState, newInitialState);
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
	@Override
	public void setInitialState(State newInitialState) {
		if (newInitialState != initialState) {
			NotificationChain msgs = null;
			if (initialState != null)
				msgs = ((InternalEObject) initialState).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - StateModelPackage.STATE_CONTAINER__INITIAL_STATE, null, msgs);
			if (newInitialState != null)
				msgs = ((InternalEObject) newInitialState).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - StateModelPackage.STATE_CONTAINER__INITIAL_STATE, null, msgs);
			msgs = basicSetInitialState(newInitialState, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateModelPackage.STATE_CONTAINER__INITIAL_STATE,
					newInitialState, newInitialState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXMatch> getInitialMatches() {
		if (initialMatches == null) {
			initialMatches = new EObjectContainmentEList<IBeXMatch>(IBeXMatch.class, this,
					StateModelPackage.STATE_CONTAINER__INITIAL_MATCHES);
		}
		return initialMatches;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case StateModelPackage.STATE_CONTAINER__STATES:
			return ((InternalEList<?>) getStates()).basicRemove(otherEnd, msgs);
		case StateModelPackage.STATE_CONTAINER__INITIAL_STATE:
			return basicSetInitialState(null, msgs);
		case StateModelPackage.STATE_CONTAINER__INITIAL_MATCHES:
			return ((InternalEList<?>) getInitialMatches()).basicRemove(otherEnd, msgs);
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
		case StateModelPackage.STATE_CONTAINER__STATES:
			return getStates();
		case StateModelPackage.STATE_CONTAINER__INITIAL_STATE:
			return getInitialState();
		case StateModelPackage.STATE_CONTAINER__INITIAL_MATCHES:
			return getInitialMatches();
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
		case StateModelPackage.STATE_CONTAINER__STATES:
			getStates().clear();
			getStates().addAll((Collection<? extends State>) newValue);
			return;
		case StateModelPackage.STATE_CONTAINER__INITIAL_STATE:
			setInitialState((State) newValue);
			return;
		case StateModelPackage.STATE_CONTAINER__INITIAL_MATCHES:
			getInitialMatches().clear();
			getInitialMatches().addAll((Collection<? extends IBeXMatch>) newValue);
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
		case StateModelPackage.STATE_CONTAINER__STATES:
			getStates().clear();
			return;
		case StateModelPackage.STATE_CONTAINER__INITIAL_STATE:
			setInitialState((State) null);
			return;
		case StateModelPackage.STATE_CONTAINER__INITIAL_MATCHES:
			getInitialMatches().clear();
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
		case StateModelPackage.STATE_CONTAINER__STATES:
			return states != null && !states.isEmpty();
		case StateModelPackage.STATE_CONTAINER__INITIAL_STATE:
			return initialState != null;
		case StateModelPackage.STATE_CONTAINER__INITIAL_MATCHES:
			return initialMatches != null && !initialMatches.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //StateContainerImpl
