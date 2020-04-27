/**
 */
package GTLanguage.impl;

import GTLanguage.GTLanguagePackage;
import GTLanguage.GTProbability;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.emoflon.ibex.gt.SGTPatternModel.GTArithmetics;
import org.emoflon.ibex.gt.SGTPatternModel.GTStochasticFunction;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>GT Probability</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link GTLanguage.impl.GTProbabilityImpl#getFunction <em>Function</em>}</li>
 *   <li>{@link GTLanguage.impl.GTProbabilityImpl#getParameter <em>Parameter</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GTProbabilityImpl extends EObjectImpl implements GTProbability {
	/**
	 * The cached value of the '{@link #getFunction() <em>Function</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFunction()
	 * @generated
	 * @ordered
	 */
	protected GTStochasticFunction function;

	/**
	 * The cached value of the '{@link #getParameter() <em>Parameter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameter()
	 * @generated
	 * @ordered
	 */
	protected GTArithmetics parameter;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GTProbabilityImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GTLanguagePackage.Literals.GT_PROBABILITY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GTStochasticFunction getFunction() {
		return function;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFunction(GTStochasticFunction newFunction, NotificationChain msgs) {
		GTStochasticFunction oldFunction = function;
		function = newFunction;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					GTLanguagePackage.GT_PROBABILITY__FUNCTION, oldFunction, newFunction);
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
	public void setFunction(GTStochasticFunction newFunction) {
		if (newFunction != function) {
			NotificationChain msgs = null;
			if (function != null)
				msgs = ((InternalEObject) function).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - GTLanguagePackage.GT_PROBABILITY__FUNCTION, null, msgs);
			if (newFunction != null)
				msgs = ((InternalEObject) newFunction).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - GTLanguagePackage.GT_PROBABILITY__FUNCTION, null, msgs);
			msgs = basicSetFunction(newFunction, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GTLanguagePackage.GT_PROBABILITY__FUNCTION,
					newFunction, newFunction));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GTArithmetics getParameter() {
		return parameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParameter(GTArithmetics newParameter, NotificationChain msgs) {
		GTArithmetics oldParameter = parameter;
		parameter = newParameter;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					GTLanguagePackage.GT_PROBABILITY__PARAMETER, oldParameter, newParameter);
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
	public void setParameter(GTArithmetics newParameter) {
		if (newParameter != parameter) {
			NotificationChain msgs = null;
			if (parameter != null)
				msgs = ((InternalEObject) parameter).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - GTLanguagePackage.GT_PROBABILITY__PARAMETER, null, msgs);
			if (newParameter != null)
				msgs = ((InternalEObject) newParameter).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - GTLanguagePackage.GT_PROBABILITY__PARAMETER, null, msgs);
			msgs = basicSetParameter(newParameter, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GTLanguagePackage.GT_PROBABILITY__PARAMETER,
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
		case GTLanguagePackage.GT_PROBABILITY__FUNCTION:
			return basicSetFunction(null, msgs);
		case GTLanguagePackage.GT_PROBABILITY__PARAMETER:
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
		case GTLanguagePackage.GT_PROBABILITY__FUNCTION:
			return getFunction();
		case GTLanguagePackage.GT_PROBABILITY__PARAMETER:
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
		case GTLanguagePackage.GT_PROBABILITY__FUNCTION:
			setFunction((GTStochasticFunction) newValue);
			return;
		case GTLanguagePackage.GT_PROBABILITY__PARAMETER:
			setParameter((GTArithmetics) newValue);
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
		case GTLanguagePackage.GT_PROBABILITY__FUNCTION:
			setFunction((GTStochasticFunction) null);
			return;
		case GTLanguagePackage.GT_PROBABILITY__PARAMETER:
			setParameter((GTArithmetics) null);
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
		case GTLanguagePackage.GT_PROBABILITY__FUNCTION:
			return function != null;
		case GTLanguagePackage.GT_PROBABILITY__PARAMETER:
			return parameter != null;
		}
		return super.eIsSet(featureID);
	}

} //GTProbabilityImpl
