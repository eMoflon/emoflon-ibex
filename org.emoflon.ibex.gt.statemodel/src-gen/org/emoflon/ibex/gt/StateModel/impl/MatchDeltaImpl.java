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
import org.emoflon.ibex.gt.StateModel.MatchDelta;
import org.emoflon.ibex.gt.StateModel.StateModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Match Delta</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.impl.MatchDeltaImpl#getPatternName <em>Pattern Name</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.impl.MatchDeltaImpl#getMatchDeltasForPattern <em>Match Deltas For Pattern</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MatchDeltaImpl extends MinimalEObjectImpl.Container implements MatchDelta {
	/**
	 * The default value of the '{@link #getPatternName() <em>Pattern Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPatternName()
	 * @generated
	 * @ordered
	 */
	protected static final String PATTERN_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPatternName() <em>Pattern Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPatternName()
	 * @generated
	 * @ordered
	 */
	protected String patternName = PATTERN_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getMatchDeltasForPattern() <em>Match Deltas For Pattern</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMatchDeltasForPattern()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXMatch> matchDeltasForPattern;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MatchDeltaImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StateModelPackage.Literals.MATCH_DELTA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getPatternName() {
		return patternName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPatternName(String newPatternName) {
		String oldPatternName = patternName;
		patternName = newPatternName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateModelPackage.MATCH_DELTA__PATTERN_NAME,
					oldPatternName, patternName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXMatch> getMatchDeltasForPattern() {
		if (matchDeltasForPattern == null) {
			matchDeltasForPattern = new EObjectContainmentEList<IBeXMatch>(IBeXMatch.class, this,
					StateModelPackage.MATCH_DELTA__MATCH_DELTAS_FOR_PATTERN);
		}
		return matchDeltasForPattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case StateModelPackage.MATCH_DELTA__MATCH_DELTAS_FOR_PATTERN:
			return ((InternalEList<?>) getMatchDeltasForPattern()).basicRemove(otherEnd, msgs);
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
		case StateModelPackage.MATCH_DELTA__PATTERN_NAME:
			return getPatternName();
		case StateModelPackage.MATCH_DELTA__MATCH_DELTAS_FOR_PATTERN:
			return getMatchDeltasForPattern();
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
		case StateModelPackage.MATCH_DELTA__PATTERN_NAME:
			setPatternName((String) newValue);
			return;
		case StateModelPackage.MATCH_DELTA__MATCH_DELTAS_FOR_PATTERN:
			getMatchDeltasForPattern().clear();
			getMatchDeltasForPattern().addAll((Collection<? extends IBeXMatch>) newValue);
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
		case StateModelPackage.MATCH_DELTA__PATTERN_NAME:
			setPatternName(PATTERN_NAME_EDEFAULT);
			return;
		case StateModelPackage.MATCH_DELTA__MATCH_DELTAS_FOR_PATTERN:
			getMatchDeltasForPattern().clear();
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
		case StateModelPackage.MATCH_DELTA__PATTERN_NAME:
			return PATTERN_NAME_EDEFAULT == null ? patternName != null : !PATTERN_NAME_EDEFAULT.equals(patternName);
		case StateModelPackage.MATCH_DELTA__MATCH_DELTAS_FOR_PATTERN:
			return matchDeltasForPattern != null && !matchDeltasForPattern.isEmpty();
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
		result.append(" (patternName: ");
		result.append(patternName);
		result.append(')');
		return result.toString();
	}

} //MatchDeltaImpl
