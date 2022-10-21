/**
 */
package org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXFeatureConfig;
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
 *   <li>{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTPatternImpl#getUsedFeatures <em>Used Features</em>}</li>
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
	 * The cached value of the '{@link #getUsedFeatures() <em>Used Features</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUsedFeatures()
	 * @generated
	 * @ordered
	 */
	protected IBeXFeatureConfig usedFeatures;

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
	public IBeXFeatureConfig getUsedFeatures() {
		return usedFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUsedFeatures(IBeXFeatureConfig newUsedFeatures, NotificationChain msgs) {
		IBeXFeatureConfig oldUsedFeatures = usedFeatures;
		usedFeatures = newUsedFeatures;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXGTModelPackage.GT_PATTERN__USED_FEATURES, oldUsedFeatures, newUsedFeatures);
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
	public void setUsedFeatures(IBeXFeatureConfig newUsedFeatures) {
		if (newUsedFeatures != usedFeatures) {
			NotificationChain msgs = null;
			if (usedFeatures != null)
				msgs = ((InternalEObject) usedFeatures).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXGTModelPackage.GT_PATTERN__USED_FEATURES, null, msgs);
			if (newUsedFeatures != null)
				msgs = ((InternalEObject) newUsedFeatures).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXGTModelPackage.GT_PATTERN__USED_FEATURES, null, msgs);
			msgs = basicSetUsedFeatures(newUsedFeatures, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXGTModelPackage.GT_PATTERN__USED_FEATURES,
					newUsedFeatures, newUsedFeatures));
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
		case IBeXGTModelPackage.GT_PATTERN__USED_FEATURES:
			return basicSetUsedFeatures(null, msgs);
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
		case IBeXGTModelPackage.GT_PATTERN__USED_FEATURES:
			return getUsedFeatures();
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
		case IBeXGTModelPackage.GT_PATTERN__USED_FEATURES:
			setUsedFeatures((IBeXFeatureConfig) newValue);
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
		case IBeXGTModelPackage.GT_PATTERN__USED_FEATURES:
			setUsedFeatures((IBeXFeatureConfig) null);
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
		case IBeXGTModelPackage.GT_PATTERN__USED_FEATURES:
			return usedFeatures != null;
		}
		return super.eIsSet(featureID);
	}

} //GTPatternImpl
