/**
 */
package StochasticLanguage.impl;

import StochasticLanguage.GTArithmetics;
import StochasticLanguage.GTOneParameterCalculation;
import StochasticLanguage.OneParameterOperator;
import StochasticLanguage.StochasticLanguagePackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>GT One Parameter Calculation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link StochasticLanguage.impl.GTOneParameterCalculationImpl#getValue <em>Value</em>}</li>
 *   <li>{@link StochasticLanguage.impl.GTOneParameterCalculationImpl#getOperator <em>Operator</em>}</li>
 *   <li>{@link StochasticLanguage.impl.GTOneParameterCalculationImpl#isNegative <em>Negative</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GTOneParameterCalculationImpl extends EObjectImpl implements GTOneParameterCalculation {
	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected GTArithmetics value;

	/**
	 * The default value of the '{@link #getOperator() <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperator()
	 * @generated
	 * @ordered
	 */
	protected static final OneParameterOperator OPERATOR_EDEFAULT = OneParameterOperator.ROOT;

	/**
	 * The cached value of the '{@link #getOperator() <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperator()
	 * @generated
	 * @ordered
	 */
	protected OneParameterOperator operator = OPERATOR_EDEFAULT;

	/**
	 * The default value of the '{@link #isNegative() <em>Negative</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isNegative()
	 * @generated
	 * @ordered
	 */
	protected static final boolean NEGATIVE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isNegative() <em>Negative</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isNegative()
	 * @generated
	 * @ordered
	 */
	protected boolean negative = NEGATIVE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GTOneParameterCalculationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StochasticLanguagePackage.Literals.GT_ONE_PARAMETER_CALCULATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GTArithmetics getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetValue(GTArithmetics newValue, NotificationChain msgs) {
		GTArithmetics oldValue = value;
		value = newValue;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					StochasticLanguagePackage.GT_ONE_PARAMETER_CALCULATION__VALUE, oldValue, newValue);
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
	public void setValue(GTArithmetics newValue) {
		if (newValue != value) {
			NotificationChain msgs = null;
			if (value != null)
				msgs = ((InternalEObject) value).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - StochasticLanguagePackage.GT_ONE_PARAMETER_CALCULATION__VALUE, null,
						msgs);
			if (newValue != null)
				msgs = ((InternalEObject) newValue).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - StochasticLanguagePackage.GT_ONE_PARAMETER_CALCULATION__VALUE, null,
						msgs);
			msgs = basicSetValue(newValue, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					StochasticLanguagePackage.GT_ONE_PARAMETER_CALCULATION__VALUE, newValue, newValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public OneParameterOperator getOperator() {
		return operator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOperator(OneParameterOperator newOperator) {
		OneParameterOperator oldOperator = operator;
		operator = newOperator == null ? OPERATOR_EDEFAULT : newOperator;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					StochasticLanguagePackage.GT_ONE_PARAMETER_CALCULATION__OPERATOR, oldOperator, operator));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isNegative() {
		return negative;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setNegative(boolean newNegative) {
		boolean oldNegative = negative;
		negative = newNegative;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					StochasticLanguagePackage.GT_ONE_PARAMETER_CALCULATION__NEGATIVE, oldNegative, negative));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case StochasticLanguagePackage.GT_ONE_PARAMETER_CALCULATION__VALUE:
			return basicSetValue(null, msgs);
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
		case StochasticLanguagePackage.GT_ONE_PARAMETER_CALCULATION__VALUE:
			return getValue();
		case StochasticLanguagePackage.GT_ONE_PARAMETER_CALCULATION__OPERATOR:
			return getOperator();
		case StochasticLanguagePackage.GT_ONE_PARAMETER_CALCULATION__NEGATIVE:
			return isNegative();
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
		case StochasticLanguagePackage.GT_ONE_PARAMETER_CALCULATION__VALUE:
			setValue((GTArithmetics) newValue);
			return;
		case StochasticLanguagePackage.GT_ONE_PARAMETER_CALCULATION__OPERATOR:
			setOperator((OneParameterOperator) newValue);
			return;
		case StochasticLanguagePackage.GT_ONE_PARAMETER_CALCULATION__NEGATIVE:
			setNegative((Boolean) newValue);
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
		case StochasticLanguagePackage.GT_ONE_PARAMETER_CALCULATION__VALUE:
			setValue((GTArithmetics) null);
			return;
		case StochasticLanguagePackage.GT_ONE_PARAMETER_CALCULATION__OPERATOR:
			setOperator(OPERATOR_EDEFAULT);
			return;
		case StochasticLanguagePackage.GT_ONE_PARAMETER_CALCULATION__NEGATIVE:
			setNegative(NEGATIVE_EDEFAULT);
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
		case StochasticLanguagePackage.GT_ONE_PARAMETER_CALCULATION__VALUE:
			return value != null;
		case StochasticLanguagePackage.GT_ONE_PARAMETER_CALCULATION__OPERATOR:
			return operator != OPERATOR_EDEFAULT;
		case StochasticLanguagePackage.GT_ONE_PARAMETER_CALCULATION__NEGATIVE:
			return negative != NEGATIVE_EDEFAULT;
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
		result.append(" (operator: ");
		result.append(operator);
		result.append(", negative: ");
		result.append(negative);
		result.append(')');
		return result.toString();
	}

} //GTOneParameterCalculationImpl
