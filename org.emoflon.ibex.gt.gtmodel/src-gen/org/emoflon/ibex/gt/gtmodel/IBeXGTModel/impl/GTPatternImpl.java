/**
 */
package org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXPatternImpl;

import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTParameter;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTWatchDog;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>GT Pattern</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTPatternImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTPatternImpl#getWatchDogs <em>Watch Dogs</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GTPatternImpl extends IBeXPatternImpl implements GTPattern {
	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<GTParameter> parameters;

	/**
	 * The cached value of the '{@link #getWatchDogs() <em>Watch Dogs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWatchDogs()
	 * @generated
	 * @ordered
	 */
	protected EList<GTWatchDog> watchDogs;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GTPatternImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXGTModelPackage.Literals.GT_PATTERN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<GTParameter> getParameters() {
		if (parameters == null) {
			parameters = new EObjectContainmentEList<GTParameter>(GTParameter.class, this,
					IBeXGTModelPackage.GT_PATTERN__PARAMETERS);
		}
		return parameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<GTWatchDog> getWatchDogs() {
		if (watchDogs == null) {
			watchDogs = new EObjectContainmentEList<GTWatchDog>(GTWatchDog.class, this,
					IBeXGTModelPackage.GT_PATTERN__WATCH_DOGS);
		}
		return watchDogs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IBeXGTModelPackage.GT_PATTERN__PARAMETERS:
			return ((InternalEList<?>) getParameters()).basicRemove(otherEnd, msgs);
		case IBeXGTModelPackage.GT_PATTERN__WATCH_DOGS:
			return ((InternalEList<?>) getWatchDogs()).basicRemove(otherEnd, msgs);
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
		case IBeXGTModelPackage.GT_PATTERN__PARAMETERS:
			return getParameters();
		case IBeXGTModelPackage.GT_PATTERN__WATCH_DOGS:
			return getWatchDogs();
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
		case IBeXGTModelPackage.GT_PATTERN__PARAMETERS:
			getParameters().clear();
			getParameters().addAll((Collection<? extends GTParameter>) newValue);
			return;
		case IBeXGTModelPackage.GT_PATTERN__WATCH_DOGS:
			getWatchDogs().clear();
			getWatchDogs().addAll((Collection<? extends GTWatchDog>) newValue);
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
		case IBeXGTModelPackage.GT_PATTERN__PARAMETERS:
			getParameters().clear();
			return;
		case IBeXGTModelPackage.GT_PATTERN__WATCH_DOGS:
			getWatchDogs().clear();
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
		case IBeXGTModelPackage.GT_PATTERN__PARAMETERS:
			return parameters != null && !parameters.isEmpty();
		case IBeXGTModelPackage.GT_PATTERN__WATCH_DOGS:
			return watchDogs != null && !watchDogs.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //GTPatternImpl
