/**
 */
package org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ArithmeticExpression;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.IBeXCoreArithmeticPackage;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.UnaryExpression;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.UnaryOperator;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Unary Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.impl.UnaryExpressionImpl#getOperand <em>Operand</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.impl.UnaryExpressionImpl#getOperator <em>Operator</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UnaryExpressionImpl extends ValueExpressionImpl implements UnaryExpression {
	/**
	 * The cached value of the '{@link #getOperand() <em>Operand</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperand()
	 * @generated
	 * @ordered
	 */
	protected ArithmeticExpression operand;

	/**
	 * The default value of the '{@link #getOperator() <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperator()
	 * @generated
	 * @ordered
	 */
	protected static final UnaryOperator OPERATOR_EDEFAULT = UnaryOperator.SQRT;

	/**
	 * The cached value of the '{@link #getOperator() <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperator()
	 * @generated
	 * @ordered
	 */
	protected UnaryOperator operator = OPERATOR_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected UnaryExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXCoreArithmeticPackage.Literals.UNARY_EXPRESSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArithmeticExpression getOperand() {
		return operand;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOperand(ArithmeticExpression newOperand, NotificationChain msgs) {
		ArithmeticExpression oldOperand = operand;
		operand = newOperand;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXCoreArithmeticPackage.UNARY_EXPRESSION__OPERAND, oldOperand, newOperand);
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
	public void setOperand(ArithmeticExpression newOperand) {
		if (newOperand != operand) {
			NotificationChain msgs = null;
			if (operand != null)
				msgs = ((InternalEObject) operand).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXCoreArithmeticPackage.UNARY_EXPRESSION__OPERAND, null, msgs);
			if (newOperand != null)
				msgs = ((InternalEObject) newOperand).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXCoreArithmeticPackage.UNARY_EXPRESSION__OPERAND, null, msgs);
			msgs = basicSetOperand(newOperand, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXCoreArithmeticPackage.UNARY_EXPRESSION__OPERAND,
					newOperand, newOperand));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UnaryOperator getOperator() {
		return operator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOperator(UnaryOperator newOperator) {
		UnaryOperator oldOperator = operator;
		operator = newOperator == null ? OPERATOR_EDEFAULT : newOperator;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXCoreArithmeticPackage.UNARY_EXPRESSION__OPERATOR,
					oldOperator, operator));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IBeXCoreArithmeticPackage.UNARY_EXPRESSION__OPERAND:
			return basicSetOperand(null, msgs);
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
		case IBeXCoreArithmeticPackage.UNARY_EXPRESSION__OPERAND:
			return getOperand();
		case IBeXCoreArithmeticPackage.UNARY_EXPRESSION__OPERATOR:
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
		case IBeXCoreArithmeticPackage.UNARY_EXPRESSION__OPERAND:
			setOperand((ArithmeticExpression) newValue);
			return;
		case IBeXCoreArithmeticPackage.UNARY_EXPRESSION__OPERATOR:
			setOperator((UnaryOperator) newValue);
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
		case IBeXCoreArithmeticPackage.UNARY_EXPRESSION__OPERAND:
			setOperand((ArithmeticExpression) null);
			return;
		case IBeXCoreArithmeticPackage.UNARY_EXPRESSION__OPERATOR:
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
		case IBeXCoreArithmeticPackage.UNARY_EXPRESSION__OPERAND:
			return operand != null;
		case IBeXCoreArithmeticPackage.UNARY_EXPRESSION__OPERATOR:
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

} //UnaryExpressionImpl
