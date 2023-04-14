/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ValueExpression;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.CSPPackage;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintParameterDefinition;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintParameterValue;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TGG Attribute Constraint Parameter Value</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGAttributeConstraintParameterValueImpl#getParameterDefinition <em>Parameter Definition</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGAttributeConstraintParameterValueImpl#getExpression <em>Expression</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGAttributeConstraintParameterValueImpl#isDerived <em>Derived</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TGGAttributeConstraintParameterValueImpl extends MinimalEObjectImpl.Container
		implements TGGAttributeConstraintParameterValue {
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
	 * The default value of the '{@link #isDerived() <em>Derived</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDerived()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DERIVED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDerived() <em>Derived</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDerived()
	 * @generated
	 * @ordered
	 */
	protected boolean derived = DERIVED_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TGGAttributeConstraintParameterValueImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CSPPackage.Literals.TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_VALUE;
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
							CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_VALUE__PARAMETER_DEFINITION,
							oldParameterDefinition, parameterDefinition));
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
					CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_VALUE__PARAMETER_DEFINITION, oldParameterDefinition,
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
					CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_VALUE__EXPRESSION, oldExpression, newExpression);
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
						EOPPOSITE_FEATURE_BASE - CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_VALUE__EXPRESSION, null,
						msgs);
			if (newExpression != null)
				msgs = ((InternalEObject) newExpression).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_VALUE__EXPRESSION, null,
						msgs);
			msgs = basicSetExpression(newExpression, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_VALUE__EXPRESSION, newExpression, newExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isDerived() {
		return derived;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDerived(boolean newDerived) {
		boolean oldDerived = derived;
		derived = newDerived;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_VALUE__DERIVED, oldDerived, derived));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_VALUE__EXPRESSION:
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
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_VALUE__PARAMETER_DEFINITION:
			if (resolve)
				return getParameterDefinition();
			return basicGetParameterDefinition();
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_VALUE__EXPRESSION:
			return getExpression();
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_VALUE__DERIVED:
			return isDerived();
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
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_VALUE__PARAMETER_DEFINITION:
			setParameterDefinition((TGGAttributeConstraintParameterDefinition) newValue);
			return;
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_VALUE__EXPRESSION:
			setExpression((ValueExpression) newValue);
			return;
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_VALUE__DERIVED:
			setDerived((Boolean) newValue);
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
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_VALUE__PARAMETER_DEFINITION:
			setParameterDefinition((TGGAttributeConstraintParameterDefinition) null);
			return;
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_VALUE__EXPRESSION:
			setExpression((ValueExpression) null);
			return;
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_VALUE__DERIVED:
			setDerived(DERIVED_EDEFAULT);
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
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_VALUE__PARAMETER_DEFINITION:
			return parameterDefinition != null;
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_VALUE__EXPRESSION:
			return expression != null;
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_VALUE__DERIVED:
			return derived != DERIVED_EDEFAULT;
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
		result.append(" (derived: ");
		result.append(derived);
		result.append(')');
		return result.toString();
	}

} //TGGAttributeConstraintParameterValueImpl
