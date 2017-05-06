/**
 */
package language.inplaceAttributes.impl;

import language.basic.expressions.TGGExpression;

import language.inplaceAttributes.InplaceAttributesPackage;
import language.inplaceAttributes.TGGAttributeConstraintOperators;
import language.inplaceAttributes.TGGInplaceAttributeExpression;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TGG Inplace Attribute Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link language.inplaceAttributes.impl.TGGInplaceAttributeExpressionImpl#getAttribute <em>Attribute</em>}</li>
 *   <li>{@link language.inplaceAttributes.impl.TGGInplaceAttributeExpressionImpl#getValueExpr <em>Value Expr</em>}</li>
 *   <li>{@link language.inplaceAttributes.impl.TGGInplaceAttributeExpressionImpl#getOperator <em>Operator</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TGGInplaceAttributeExpressionImpl extends EObjectImpl implements TGGInplaceAttributeExpression {
	/**
	 * The cached value of the '{@link #getAttribute() <em>Attribute</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttribute()
	 * @generated
	 * @ordered
	 */
	protected EAttribute attribute;

	/**
	 * The cached value of the '{@link #getValueExpr() <em>Value Expr</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValueExpr()
	 * @generated
	 * @ordered
	 */
	protected TGGExpression valueExpr;

	/**
	 * The default value of the '{@link #getOperator() <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperator()
	 * @generated
	 * @ordered
	 */
	protected static final TGGAttributeConstraintOperators OPERATOR_EDEFAULT = TGGAttributeConstraintOperators.EQUAL;

	/**
	 * The cached value of the '{@link #getOperator() <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperator()
	 * @generated
	 * @ordered
	 */
	protected TGGAttributeConstraintOperators operator = OPERATOR_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TGGInplaceAttributeExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return InplaceAttributesPackage.Literals.TGG_INPLACE_ATTRIBUTE_EXPRESSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAttribute() {
		if (attribute != null && attribute.eIsProxy()) {
			InternalEObject oldAttribute = (InternalEObject) attribute;
			attribute = (EAttribute) eResolveProxy(oldAttribute);
			if (attribute != oldAttribute) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							InplaceAttributesPackage.TGG_INPLACE_ATTRIBUTE_EXPRESSION__ATTRIBUTE, oldAttribute,
							attribute));
			}
		}
		return attribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute basicGetAttribute() {
		return attribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAttribute(EAttribute newAttribute) {
		EAttribute oldAttribute = attribute;
		attribute = newAttribute;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					InplaceAttributesPackage.TGG_INPLACE_ATTRIBUTE_EXPRESSION__ATTRIBUTE, oldAttribute, attribute));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGExpression getValueExpr() {
		return valueExpr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetValueExpr(TGGExpression newValueExpr, NotificationChain msgs) {
		TGGExpression oldValueExpr = valueExpr;
		valueExpr = newValueExpr;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					InplaceAttributesPackage.TGG_INPLACE_ATTRIBUTE_EXPRESSION__VALUE_EXPR, oldValueExpr, newValueExpr);
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
	public void setValueExpr(TGGExpression newValueExpr) {
		if (newValueExpr != valueExpr) {
			NotificationChain msgs = null;
			if (valueExpr != null)
				msgs = ((InternalEObject) valueExpr).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - InplaceAttributesPackage.TGG_INPLACE_ATTRIBUTE_EXPRESSION__VALUE_EXPR,
						null, msgs);
			if (newValueExpr != null)
				msgs = ((InternalEObject) newValueExpr).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - InplaceAttributesPackage.TGG_INPLACE_ATTRIBUTE_EXPRESSION__VALUE_EXPR,
						null, msgs);
			msgs = basicSetValueExpr(newValueExpr, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					InplaceAttributesPackage.TGG_INPLACE_ATTRIBUTE_EXPRESSION__VALUE_EXPR, newValueExpr, newValueExpr));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGAttributeConstraintOperators getOperator() {
		return operator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOperator(TGGAttributeConstraintOperators newOperator) {
		TGGAttributeConstraintOperators oldOperator = operator;
		operator = newOperator == null ? OPERATOR_EDEFAULT : newOperator;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					InplaceAttributesPackage.TGG_INPLACE_ATTRIBUTE_EXPRESSION__OPERATOR, oldOperator, operator));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case InplaceAttributesPackage.TGG_INPLACE_ATTRIBUTE_EXPRESSION__VALUE_EXPR:
			return basicSetValueExpr(null, msgs);
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
		case InplaceAttributesPackage.TGG_INPLACE_ATTRIBUTE_EXPRESSION__ATTRIBUTE:
			if (resolve)
				return getAttribute();
			return basicGetAttribute();
		case InplaceAttributesPackage.TGG_INPLACE_ATTRIBUTE_EXPRESSION__VALUE_EXPR:
			return getValueExpr();
		case InplaceAttributesPackage.TGG_INPLACE_ATTRIBUTE_EXPRESSION__OPERATOR:
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
		case InplaceAttributesPackage.TGG_INPLACE_ATTRIBUTE_EXPRESSION__ATTRIBUTE:
			setAttribute((EAttribute) newValue);
			return;
		case InplaceAttributesPackage.TGG_INPLACE_ATTRIBUTE_EXPRESSION__VALUE_EXPR:
			setValueExpr((TGGExpression) newValue);
			return;
		case InplaceAttributesPackage.TGG_INPLACE_ATTRIBUTE_EXPRESSION__OPERATOR:
			setOperator((TGGAttributeConstraintOperators) newValue);
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
		case InplaceAttributesPackage.TGG_INPLACE_ATTRIBUTE_EXPRESSION__ATTRIBUTE:
			setAttribute((EAttribute) null);
			return;
		case InplaceAttributesPackage.TGG_INPLACE_ATTRIBUTE_EXPRESSION__VALUE_EXPR:
			setValueExpr((TGGExpression) null);
			return;
		case InplaceAttributesPackage.TGG_INPLACE_ATTRIBUTE_EXPRESSION__OPERATOR:
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
		case InplaceAttributesPackage.TGG_INPLACE_ATTRIBUTE_EXPRESSION__ATTRIBUTE:
			return attribute != null;
		case InplaceAttributesPackage.TGG_INPLACE_ATTRIBUTE_EXPRESSION__VALUE_EXPR:
			return valueExpr != null;
		case InplaceAttributesPackage.TGG_INPLACE_ATTRIBUTE_EXPRESSION__OPERATOR:
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

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (operator: ");
		result.append(operator);
		result.append(')');
		return result.toString();
	}
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} //TGGInplaceAttributeExpressionImpl
