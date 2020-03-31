/**
 */
package StochasticLanguage.impl;

import StochasticLanguage.GTArithmetics;
import StochasticLanguage.GTStochasticDistribution;
import StochasticLanguage.GTStochasticFunction;
import StochasticLanguage.StochasticLanguagePackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>GT Stochastic Function</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link StochasticLanguage.impl.GTStochasticFunctionImpl#getMean <em>Mean</em>}</li>
 *   <li>{@link StochasticLanguage.impl.GTStochasticFunctionImpl#getSd <em>Sd</em>}</li>
 *   <li>{@link StochasticLanguage.impl.GTStochasticFunctionImpl#getDistribution <em>Distribution</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GTStochasticFunctionImpl extends EObjectImpl implements GTStochasticFunction {
	/**
	 * The cached value of the '{@link #getMean() <em>Mean</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMean()
	 * @generated
	 * @ordered
	 */
	protected GTArithmetics mean;

	/**
	 * The cached value of the '{@link #getSd() <em>Sd</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSd()
	 * @generated
	 * @ordered
	 */
	protected GTArithmetics sd;

	/**
	 * The default value of the '{@link #getDistribution() <em>Distribution</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDistribution()
	 * @generated
	 * @ordered
	 */
	protected static final GTStochasticDistribution DISTRIBUTION_EDEFAULT = GTStochasticDistribution.NORMAL;

	/**
	 * The cached value of the '{@link #getDistribution() <em>Distribution</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDistribution()
	 * @generated
	 * @ordered
	 */
	protected GTStochasticDistribution distribution = DISTRIBUTION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GTStochasticFunctionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StochasticLanguagePackage.Literals.GT_STOCHASTIC_FUNCTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GTArithmetics getMean() {
		return mean;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMean(GTArithmetics newMean, NotificationChain msgs) {
		GTArithmetics oldMean = mean;
		mean = newMean;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					StochasticLanguagePackage.GT_STOCHASTIC_FUNCTION__MEAN, oldMean, newMean);
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
	public void setMean(GTArithmetics newMean) {
		if (newMean != mean) {
			NotificationChain msgs = null;
			if (mean != null)
				msgs = ((InternalEObject) mean).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - StochasticLanguagePackage.GT_STOCHASTIC_FUNCTION__MEAN, null, msgs);
			if (newMean != null)
				msgs = ((InternalEObject) newMean).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - StochasticLanguagePackage.GT_STOCHASTIC_FUNCTION__MEAN, null, msgs);
			msgs = basicSetMean(newMean, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					StochasticLanguagePackage.GT_STOCHASTIC_FUNCTION__MEAN, newMean, newMean));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GTArithmetics getSd() {
		return sd;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSd(GTArithmetics newSd, NotificationChain msgs) {
		GTArithmetics oldSd = sd;
		sd = newSd;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					StochasticLanguagePackage.GT_STOCHASTIC_FUNCTION__SD, oldSd, newSd);
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
	public void setSd(GTArithmetics newSd) {
		if (newSd != sd) {
			NotificationChain msgs = null;
			if (sd != null)
				msgs = ((InternalEObject) sd).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - StochasticLanguagePackage.GT_STOCHASTIC_FUNCTION__SD, null, msgs);
			if (newSd != null)
				msgs = ((InternalEObject) newSd).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - StochasticLanguagePackage.GT_STOCHASTIC_FUNCTION__SD, null, msgs);
			msgs = basicSetSd(newSd, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StochasticLanguagePackage.GT_STOCHASTIC_FUNCTION__SD,
					newSd, newSd));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GTStochasticDistribution getDistribution() {
		return distribution;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDistribution(GTStochasticDistribution newDistribution) {
		GTStochasticDistribution oldDistribution = distribution;
		distribution = newDistribution == null ? DISTRIBUTION_EDEFAULT : newDistribution;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					StochasticLanguagePackage.GT_STOCHASTIC_FUNCTION__DISTRIBUTION, oldDistribution, distribution));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case StochasticLanguagePackage.GT_STOCHASTIC_FUNCTION__MEAN:
			return basicSetMean(null, msgs);
		case StochasticLanguagePackage.GT_STOCHASTIC_FUNCTION__SD:
			return basicSetSd(null, msgs);
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
		case StochasticLanguagePackage.GT_STOCHASTIC_FUNCTION__MEAN:
			return getMean();
		case StochasticLanguagePackage.GT_STOCHASTIC_FUNCTION__SD:
			return getSd();
		case StochasticLanguagePackage.GT_STOCHASTIC_FUNCTION__DISTRIBUTION:
			return getDistribution();
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
		case StochasticLanguagePackage.GT_STOCHASTIC_FUNCTION__MEAN:
			setMean((GTArithmetics) newValue);
			return;
		case StochasticLanguagePackage.GT_STOCHASTIC_FUNCTION__SD:
			setSd((GTArithmetics) newValue);
			return;
		case StochasticLanguagePackage.GT_STOCHASTIC_FUNCTION__DISTRIBUTION:
			setDistribution((GTStochasticDistribution) newValue);
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
		case StochasticLanguagePackage.GT_STOCHASTIC_FUNCTION__MEAN:
			setMean((GTArithmetics) null);
			return;
		case StochasticLanguagePackage.GT_STOCHASTIC_FUNCTION__SD:
			setSd((GTArithmetics) null);
			return;
		case StochasticLanguagePackage.GT_STOCHASTIC_FUNCTION__DISTRIBUTION:
			setDistribution(DISTRIBUTION_EDEFAULT);
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
		case StochasticLanguagePackage.GT_STOCHASTIC_FUNCTION__MEAN:
			return mean != null;
		case StochasticLanguagePackage.GT_STOCHASTIC_FUNCTION__SD:
			return sd != null;
		case StochasticLanguagePackage.GT_STOCHASTIC_FUNCTION__DISTRIBUTION:
			return distribution != DISTRIBUTION_EDEFAULT;
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
		result.append(" (distribution: ");
		result.append(distribution);
		result.append(')');
		return result.toString();
	}

} //GTStochasticFunctionImpl
