/**
 */
package org.emoflon.ibex.patternmodel.IBeXPatternModel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticExpression;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDistributionType;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXProbabilityDistribution;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IBe XProbability Distribution</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXProbabilityDistributionImpl#getMean <em>Mean</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXProbabilityDistributionImpl#getStddev <em>Stddev</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXProbabilityDistributionImpl#getType <em>Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IBeXProbabilityDistributionImpl extends MinimalEObjectImpl.Container
		implements IBeXProbabilityDistribution {
	/**
	 * The cached value of the '{@link #getMean() <em>Mean</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMean()
	 * @generated
	 * @ordered
	 */
	protected IBeXArithmeticExpression mean;

	/**
	 * The cached value of the '{@link #getStddev() <em>Stddev</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStddev()
	 * @generated
	 * @ordered
	 */
	protected IBeXArithmeticExpression stddev;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final IBeXDistributionType TYPE_EDEFAULT = IBeXDistributionType.NORMAL;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected IBeXDistributionType type = TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IBeXProbabilityDistributionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXPatternModelPackage.Literals.IBE_XPROBABILITY_DISTRIBUTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXArithmeticExpression getMean() {
		return mean;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMean(IBeXArithmeticExpression newMean, NotificationChain msgs) {
		IBeXArithmeticExpression oldMean = mean;
		mean = newMean;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXPatternModelPackage.IBE_XPROBABILITY_DISTRIBUTION__MEAN, oldMean, newMean);
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
	public void setMean(IBeXArithmeticExpression newMean) {
		if (newMean != mean) {
			NotificationChain msgs = null;
			if (mean != null)
				msgs = ((InternalEObject) mean).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXPatternModelPackage.IBE_XPROBABILITY_DISTRIBUTION__MEAN, null,
						msgs);
			if (newMean != null)
				msgs = ((InternalEObject) newMean).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXPatternModelPackage.IBE_XPROBABILITY_DISTRIBUTION__MEAN, null,
						msgs);
			msgs = basicSetMean(newMean, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXPatternModelPackage.IBE_XPROBABILITY_DISTRIBUTION__MEAN, newMean, newMean));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXArithmeticExpression getStddev() {
		return stddev;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStddev(IBeXArithmeticExpression newStddev, NotificationChain msgs) {
		IBeXArithmeticExpression oldStddev = stddev;
		stddev = newStddev;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXPatternModelPackage.IBE_XPROBABILITY_DISTRIBUTION__STDDEV, oldStddev, newStddev);
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
	public void setStddev(IBeXArithmeticExpression newStddev) {
		if (newStddev != stddev) {
			NotificationChain msgs = null;
			if (stddev != null)
				msgs = ((InternalEObject) stddev).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXPatternModelPackage.IBE_XPROBABILITY_DISTRIBUTION__STDDEV, null,
						msgs);
			if (newStddev != null)
				msgs = ((InternalEObject) newStddev).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXPatternModelPackage.IBE_XPROBABILITY_DISTRIBUTION__STDDEV, null,
						msgs);
			msgs = basicSetStddev(newStddev, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXPatternModelPackage.IBE_XPROBABILITY_DISTRIBUTION__STDDEV, newStddev, newStddev));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXDistributionType getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setType(IBeXDistributionType newType) {
		IBeXDistributionType oldType = type;
		type = newType == null ? TYPE_EDEFAULT : newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXPatternModelPackage.IBE_XPROBABILITY_DISTRIBUTION__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IBeXPatternModelPackage.IBE_XPROBABILITY_DISTRIBUTION__MEAN:
			return basicSetMean(null, msgs);
		case IBeXPatternModelPackage.IBE_XPROBABILITY_DISTRIBUTION__STDDEV:
			return basicSetStddev(null, msgs);
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
		case IBeXPatternModelPackage.IBE_XPROBABILITY_DISTRIBUTION__MEAN:
			return getMean();
		case IBeXPatternModelPackage.IBE_XPROBABILITY_DISTRIBUTION__STDDEV:
			return getStddev();
		case IBeXPatternModelPackage.IBE_XPROBABILITY_DISTRIBUTION__TYPE:
			return getType();
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
		case IBeXPatternModelPackage.IBE_XPROBABILITY_DISTRIBUTION__MEAN:
			setMean((IBeXArithmeticExpression) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XPROBABILITY_DISTRIBUTION__STDDEV:
			setStddev((IBeXArithmeticExpression) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XPROBABILITY_DISTRIBUTION__TYPE:
			setType((IBeXDistributionType) newValue);
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
		case IBeXPatternModelPackage.IBE_XPROBABILITY_DISTRIBUTION__MEAN:
			setMean((IBeXArithmeticExpression) null);
			return;
		case IBeXPatternModelPackage.IBE_XPROBABILITY_DISTRIBUTION__STDDEV:
			setStddev((IBeXArithmeticExpression) null);
			return;
		case IBeXPatternModelPackage.IBE_XPROBABILITY_DISTRIBUTION__TYPE:
			setType(TYPE_EDEFAULT);
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
		case IBeXPatternModelPackage.IBE_XPROBABILITY_DISTRIBUTION__MEAN:
			return mean != null;
		case IBeXPatternModelPackage.IBE_XPROBABILITY_DISTRIBUTION__STDDEV:
			return stddev != null;
		case IBeXPatternModelPackage.IBE_XPROBABILITY_DISTRIBUTION__TYPE:
			return type != TYPE_EDEFAULT;
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
		result.append(" (type: ");
		result.append(type);
		result.append(')');
		return result.toString();
	}

} //IBeXProbabilityDistributionImpl
