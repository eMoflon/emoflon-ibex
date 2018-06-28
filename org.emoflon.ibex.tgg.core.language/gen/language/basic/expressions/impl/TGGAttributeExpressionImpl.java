/**
 */
package language.basic.expressions.impl;

import language.TGGRuleNode;

import language.basic.expressions.ExpressionsPackage;
import language.basic.expressions.TGGAttributeExpression;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TGG Attribute Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link language.basic.expressions.impl.TGGAttributeExpressionImpl#getObjectVar <em>Object Var</em>}</li>
 *   <li>{@link language.basic.expressions.impl.TGGAttributeExpressionImpl#getAttribute <em>Attribute</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TGGAttributeExpressionImpl extends TGGExpressionImpl implements TGGAttributeExpression {
	/**
	 * The cached value of the '{@link #getObjectVar() <em>Object Var</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjectVar()
	 * @generated
	 * @ordered
	 */
	protected TGGRuleNode objectVar;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TGGAttributeExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExpressionsPackage.Literals.TGG_ATTRIBUTE_EXPRESSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGRuleNode getObjectVar() {
		if (objectVar != null && objectVar.eIsProxy()) {
			InternalEObject oldObjectVar = (InternalEObject) objectVar;
			objectVar = (TGGRuleNode) eResolveProxy(oldObjectVar);
			if (objectVar != oldObjectVar) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ExpressionsPackage.TGG_ATTRIBUTE_EXPRESSION__OBJECT_VAR, oldObjectVar, objectVar));
			}
		}
		return objectVar;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGRuleNode basicGetObjectVar() {
		return objectVar;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setObjectVar(TGGRuleNode newObjectVar) {
		TGGRuleNode oldObjectVar = objectVar;
		objectVar = newObjectVar;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ExpressionsPackage.TGG_ATTRIBUTE_EXPRESSION__OBJECT_VAR, oldObjectVar, objectVar));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ExpressionsPackage.TGG_ATTRIBUTE_EXPRESSION__ATTRIBUTE, oldAttribute, attribute));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ExpressionsPackage.TGG_ATTRIBUTE_EXPRESSION__ATTRIBUTE, oldAttribute, attribute));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case ExpressionsPackage.TGG_ATTRIBUTE_EXPRESSION__OBJECT_VAR:
			if (resolve)
				return getObjectVar();
			return basicGetObjectVar();
		case ExpressionsPackage.TGG_ATTRIBUTE_EXPRESSION__ATTRIBUTE:
			if (resolve)
				return getAttribute();
			return basicGetAttribute();
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
		case ExpressionsPackage.TGG_ATTRIBUTE_EXPRESSION__OBJECT_VAR:
			setObjectVar((TGGRuleNode) newValue);
			return;
		case ExpressionsPackage.TGG_ATTRIBUTE_EXPRESSION__ATTRIBUTE:
			setAttribute((EAttribute) newValue);
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
		case ExpressionsPackage.TGG_ATTRIBUTE_EXPRESSION__OBJECT_VAR:
			setObjectVar((TGGRuleNode) null);
			return;
		case ExpressionsPackage.TGG_ATTRIBUTE_EXPRESSION__ATTRIBUTE:
			setAttribute((EAttribute) null);
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
		case ExpressionsPackage.TGG_ATTRIBUTE_EXPRESSION__OBJECT_VAR:
			return objectVar != null;
		case ExpressionsPackage.TGG_ATTRIBUTE_EXPRESSION__ATTRIBUTE:
			return attribute != null;
		}
		return super.eIsSet(featureID);
	}

} //TGGAttributeExpressionImpl
