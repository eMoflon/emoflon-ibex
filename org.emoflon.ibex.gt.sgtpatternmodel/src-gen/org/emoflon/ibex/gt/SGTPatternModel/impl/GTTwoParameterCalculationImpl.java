/**
 */
package org.emoflon.ibex.gt.SGTPatternModel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.emoflon.ibex.gt.SGTPatternModel.GTArithmetics;
import org.emoflon.ibex.gt.SGTPatternModel.GTTwoParameterCalculation;
import org.emoflon.ibex.gt.SGTPatternModel.SGTPatternModelPackage;
import org.emoflon.ibex.gt.SGTPatternModel.TwoParameterOperator;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>GT Two Parameter Calculation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.gt.SGTPatternModel.impl.GTTwoParameterCalculationImpl#getLeft <em>Left</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.SGTPatternModel.impl.GTTwoParameterCalculationImpl#getRight <em>Right</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.SGTPatternModel.impl.GTTwoParameterCalculationImpl#getOperator <em>Operator</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GTTwoParameterCalculationImpl extends MinimalEObjectImpl.Container implements GTTwoParameterCalculation {
	/**
	 * The cached value of the '{@link #getLeft() <em>Left</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLeft()
	 * @generated
	 * @ordered
	 */
	protected GTArithmetics left;

	/**
	 * The cached value of the '{@link #getRight() <em>Right</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRight()
	 * @generated
	 * @ordered
	 */
	protected GTArithmetics right;

	/**
	 * The default value of the '{@link #getOperator() <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperator()
	 * @generated
	 * @ordered
	 */
	protected static final TwoParameterOperator OPERATOR_EDEFAULT = TwoParameterOperator.ADDITION;

	/**
	 * The cached value of the '{@link #getOperator() <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperator()
	 * @generated
	 * @ordered
	 */
	protected TwoParameterOperator operator = OPERATOR_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GTTwoParameterCalculationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SGTPatternModelPackage.Literals.GT_TWO_PARAMETER_CALCULATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GTArithmetics getLeft() {
		return left;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLeft(GTArithmetics newLeft, NotificationChain msgs) {
		GTArithmetics oldLeft = left;
		left = newLeft;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					SGTPatternModelPackage.GT_TWO_PARAMETER_CALCULATION__LEFT, oldLeft, newLeft);
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
	public void setLeft(GTArithmetics newLeft) {
		if (newLeft != left) {
			NotificationChain msgs = null;
			if (left != null)
				msgs = ((InternalEObject) left).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - SGTPatternModelPackage.GT_TWO_PARAMETER_CALCULATION__LEFT, null, msgs);
			if (newLeft != null)
				msgs = ((InternalEObject) newLeft).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - SGTPatternModelPackage.GT_TWO_PARAMETER_CALCULATION__LEFT, null, msgs);
			msgs = basicSetLeft(newLeft, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SGTPatternModelPackage.GT_TWO_PARAMETER_CALCULATION__LEFT, newLeft, newLeft));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GTArithmetics getRight() {
		return right;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRight(GTArithmetics newRight, NotificationChain msgs) {
		GTArithmetics oldRight = right;
		right = newRight;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					SGTPatternModelPackage.GT_TWO_PARAMETER_CALCULATION__RIGHT, oldRight, newRight);
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
	public void setRight(GTArithmetics newRight) {
		if (newRight != right) {
			NotificationChain msgs = null;
			if (right != null)
				msgs = ((InternalEObject) right).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - SGTPatternModelPackage.GT_TWO_PARAMETER_CALCULATION__RIGHT, null,
						msgs);
			if (newRight != null)
				msgs = ((InternalEObject) newRight).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - SGTPatternModelPackage.GT_TWO_PARAMETER_CALCULATION__RIGHT, null,
						msgs);
			msgs = basicSetRight(newRight, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SGTPatternModelPackage.GT_TWO_PARAMETER_CALCULATION__RIGHT, newRight, newRight));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TwoParameterOperator getOperator() {
		return operator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOperator(TwoParameterOperator newOperator) {
		TwoParameterOperator oldOperator = operator;
		operator = newOperator == null ? OPERATOR_EDEFAULT : newOperator;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SGTPatternModelPackage.GT_TWO_PARAMETER_CALCULATION__OPERATOR, oldOperator, operator));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case SGTPatternModelPackage.GT_TWO_PARAMETER_CALCULATION__LEFT:
			return basicSetLeft(null, msgs);
		case SGTPatternModelPackage.GT_TWO_PARAMETER_CALCULATION__RIGHT:
			return basicSetRight(null, msgs);
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
		case SGTPatternModelPackage.GT_TWO_PARAMETER_CALCULATION__LEFT:
			return getLeft();
		case SGTPatternModelPackage.GT_TWO_PARAMETER_CALCULATION__RIGHT:
			return getRight();
		case SGTPatternModelPackage.GT_TWO_PARAMETER_CALCULATION__OPERATOR:
			return getOperator();
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
		case SGTPatternModelPackage.GT_TWO_PARAMETER_CALCULATION__LEFT:
			setLeft((GTArithmetics) newValue);
			return;
		case SGTPatternModelPackage.GT_TWO_PARAMETER_CALCULATION__RIGHT:
			setRight((GTArithmetics) newValue);
			return;
		case SGTPatternModelPackage.GT_TWO_PARAMETER_CALCULATION__OPERATOR:
			setOperator((TwoParameterOperator) newValue);
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
		case SGTPatternModelPackage.GT_TWO_PARAMETER_CALCULATION__LEFT:
			setLeft((GTArithmetics) null);
			return;
		case SGTPatternModelPackage.GT_TWO_PARAMETER_CALCULATION__RIGHT:
			setRight((GTArithmetics) null);
			return;
		case SGTPatternModelPackage.GT_TWO_PARAMETER_CALCULATION__OPERATOR:
			setOperator(OPERATOR_EDEFAULT);
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
		case SGTPatternModelPackage.GT_TWO_PARAMETER_CALCULATION__LEFT:
			return left != null;
		case SGTPatternModelPackage.GT_TWO_PARAMETER_CALCULATION__RIGHT:
			return right != null;
		case SGTPatternModelPackage.GT_TWO_PARAMETER_CALCULATION__OPERATOR:
			return operator != OPERATOR_EDEFAULT;
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
		result.append(')');
		return result.toString();
	}

} //GTTwoParameterCalculationImpl
