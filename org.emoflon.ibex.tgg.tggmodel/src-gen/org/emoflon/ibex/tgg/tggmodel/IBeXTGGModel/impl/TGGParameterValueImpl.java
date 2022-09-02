/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ValueExpression;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintParameterDefinition;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGParameterValue;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TGG Parameter Value</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGParameterValueImpl#getParameterDefinition <em>Parameter Definition</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGParameterValueImpl#getExpression <em>Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TGGParameterValueImpl extends MinimalEObjectImpl.Container implements TGGParameterValue {
	/**
	 * The cached value of the '{@link #getParameterDefinition() <em>Parameter Definition</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameterDefinition()
	 * @generated
	 * @ordered
	 */
	protected TGGAttributeConstraintParameterDefinition parameterDefinition;

	/**
	 * The cached value of the '{@link #getExpression() <em>Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExpression()
	 * @generated
	 * @ordered
	 */
	protected ValueExpression expression;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TGGParameterValueImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXTGGModelPackage.Literals.TGG_PARAMETER_VALUE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGAttributeConstraintParameterDefinition getParameterDefinition() {
		if (parameterDefinition != null && parameterDefinition.eIsProxy()) {
			InternalEObject oldParameterDefinition = (InternalEObject) parameterDefinition;
			parameterDefinition = (TGGAttributeConstraintParameterDefinition) eResolveProxy(oldParameterDefinition);
			if (parameterDefinition != oldParameterDefinition) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							IBeXTGGModelPackage.TGG_PARAMETER_VALUE__PARAMETER_DEFINITION, oldParameterDefinition,
							parameterDefinition));
			}
		}
		return parameterDefinition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGAttributeConstraintParameterDefinition basicGetParameterDefinition() {
		return parameterDefinition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParameterDefinition(TGGAttributeConstraintParameterDefinition newParameterDefinition) {
		TGGAttributeConstraintParameterDefinition oldParameterDefinition = parameterDefinition;
		parameterDefinition = newParameterDefinition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXTGGModelPackage.TGG_PARAMETER_VALUE__PARAMETER_DEFINITION, oldParameterDefinition,
					parameterDefinition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ValueExpression getExpression() {
		return expression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExpression(ValueExpression newExpression, NotificationChain msgs) {
		ValueExpression oldExpression = expression;
		expression = newExpression;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXTGGModelPackage.TGG_PARAMETER_VALUE__EXPRESSION, oldExpression, newExpression);
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
	public void setExpression(ValueExpression newExpression) {
		if (newExpression != expression) {
			NotificationChain msgs = null;
			if (expression != null)
				msgs = ((InternalEObject) expression).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_PARAMETER_VALUE__EXPRESSION, null, msgs);
			if (newExpression != null)
				msgs = ((InternalEObject) newExpression).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_PARAMETER_VALUE__EXPRESSION, null, msgs);
			msgs = basicSetExpression(newExpression, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXTGGModelPackage.TGG_PARAMETER_VALUE__EXPRESSION,
					newExpression, newExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IBeXTGGModelPackage.TGG_PARAMETER_VALUE__EXPRESSION:
			return basicSetExpression(null, msgs);
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
		case IBeXTGGModelPackage.TGG_PARAMETER_VALUE__PARAMETER_DEFINITION:
			if (resolve)
				return getParameterDefinition();
			return basicGetParameterDefinition();
		case IBeXTGGModelPackage.TGG_PARAMETER_VALUE__EXPRESSION:
			return getExpression();
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
		case IBeXTGGModelPackage.TGG_PARAMETER_VALUE__PARAMETER_DEFINITION:
			setParameterDefinition((TGGAttributeConstraintParameterDefinition) newValue);
			return;
		case IBeXTGGModelPackage.TGG_PARAMETER_VALUE__EXPRESSION:
			setExpression((ValueExpression) newValue);
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
		case IBeXTGGModelPackage.TGG_PARAMETER_VALUE__PARAMETER_DEFINITION:
			setParameterDefinition((TGGAttributeConstraintParameterDefinition) null);
			return;
		case IBeXTGGModelPackage.TGG_PARAMETER_VALUE__EXPRESSION:
			setExpression((ValueExpression) null);
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
		case IBeXTGGModelPackage.TGG_PARAMETER_VALUE__PARAMETER_DEFINITION:
			return parameterDefinition != null;
		case IBeXTGGModelPackage.TGG_PARAMETER_VALUE__EXPRESSION:
			return expression != null;
		}
		return super.eIsSet(featureID);
	}

} //TGGParameterValueImpl
