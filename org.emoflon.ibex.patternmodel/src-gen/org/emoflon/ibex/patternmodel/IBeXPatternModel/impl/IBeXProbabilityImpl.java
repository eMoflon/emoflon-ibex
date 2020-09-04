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
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXProbability;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXProbabilityDistribution;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IBe XProbability</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXProbabilityImpl#getDistribution <em>Distribution</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXProbabilityImpl#getParameter <em>Parameter</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IBeXProbabilityImpl extends MinimalEObjectImpl.Container implements IBeXProbability {
	/**
	 * The cached value of the '{@link #getDistribution() <em>Distribution</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDistribution()
	 * @generated
	 * @ordered
	 */
	protected IBeXProbabilityDistribution distribution;

	/**
	 * The cached value of the '{@link #getParameter() <em>Parameter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameter()
	 * @generated
	 * @ordered
	 */
	protected IBeXArithmeticExpression parameter;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IBeXProbabilityImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXPatternModelPackage.Literals.IBE_XPROBABILITY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXProbabilityDistribution getDistribution() {
		return distribution;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDistribution(IBeXProbabilityDistribution newDistribution, NotificationChain msgs) {
		IBeXProbabilityDistribution oldDistribution = distribution;
		distribution = newDistribution;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXPatternModelPackage.IBE_XPROBABILITY__DISTRIBUTION, oldDistribution, newDistribution);
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
	public void setDistribution(IBeXProbabilityDistribution newDistribution) {
		if (newDistribution != distribution) {
			NotificationChain msgs = null;
			if (distribution != null)
				msgs = ((InternalEObject) distribution).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXPatternModelPackage.IBE_XPROBABILITY__DISTRIBUTION, null, msgs);
			if (newDistribution != null)
				msgs = ((InternalEObject) newDistribution).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXPatternModelPackage.IBE_XPROBABILITY__DISTRIBUTION, null, msgs);
			msgs = basicSetDistribution(newDistribution, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXPatternModelPackage.IBE_XPROBABILITY__DISTRIBUTION, newDistribution, newDistribution));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXArithmeticExpression getParameter() {
		return parameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParameter(IBeXArithmeticExpression newParameter, NotificationChain msgs) {
		IBeXArithmeticExpression oldParameter = parameter;
		parameter = newParameter;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXPatternModelPackage.IBE_XPROBABILITY__PARAMETER, oldParameter, newParameter);
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
	public void setParameter(IBeXArithmeticExpression newParameter) {
		if (newParameter != parameter) {
			NotificationChain msgs = null;
			if (parameter != null)
				msgs = ((InternalEObject) parameter).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXPatternModelPackage.IBE_XPROBABILITY__PARAMETER, null, msgs);
			if (newParameter != null)
				msgs = ((InternalEObject) newParameter).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXPatternModelPackage.IBE_XPROBABILITY__PARAMETER, null, msgs);
			msgs = basicSetParameter(newParameter, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXPatternModelPackage.IBE_XPROBABILITY__PARAMETER,
					newParameter, newParameter));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IBeXPatternModelPackage.IBE_XPROBABILITY__DISTRIBUTION:
			return basicSetDistribution(null, msgs);
		case IBeXPatternModelPackage.IBE_XPROBABILITY__PARAMETER:
			return basicSetParameter(null, msgs);
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
		case IBeXPatternModelPackage.IBE_XPROBABILITY__DISTRIBUTION:
			return getDistribution();
		case IBeXPatternModelPackage.IBE_XPROBABILITY__PARAMETER:
			return getParameter();
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
		case IBeXPatternModelPackage.IBE_XPROBABILITY__DISTRIBUTION:
			setDistribution((IBeXProbabilityDistribution) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XPROBABILITY__PARAMETER:
			setParameter((IBeXArithmeticExpression) newValue);
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
		case IBeXPatternModelPackage.IBE_XPROBABILITY__DISTRIBUTION:
			setDistribution((IBeXProbabilityDistribution) null);
			return;
		case IBeXPatternModelPackage.IBE_XPROBABILITY__PARAMETER:
			setParameter((IBeXArithmeticExpression) null);
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
		case IBeXPatternModelPackage.IBE_XPROBABILITY__DISTRIBUTION:
			return distribution != null;
		case IBeXPatternModelPackage.IBE_XPROBABILITY__PARAMETER:
			return parameter != null;
		}
		return super.eIsSet(featureID);
	}

} //IBeXProbabilityImpl
