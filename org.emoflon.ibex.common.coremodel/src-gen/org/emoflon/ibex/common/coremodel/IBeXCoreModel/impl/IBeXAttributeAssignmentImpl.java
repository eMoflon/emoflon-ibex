/**
 */
package org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeAssignment;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ValueExpression;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IBe XAttribute Assignment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXAttributeAssignmentImpl#getNode <em>Node</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXAttributeAssignmentImpl#getAttribute <em>Attribute</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXAttributeAssignmentImpl#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IBeXAttributeAssignmentImpl extends MinimalEObjectImpl.Container implements IBeXAttributeAssignment {
	/**
	 * The cached value of the '{@link #getNode() <em>Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNode()
	 * @generated
	 * @ordered
	 */
	protected IBeXNode node;

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
	 * The cached value of the '{@link #getValue() <em>Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected ValueExpression value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IBeXAttributeAssignmentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXCoreModelPackage.Literals.IBE_XATTRIBUTE_ASSIGNMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXNode getNode() {
		if (node != null && node.eIsProxy()) {
			InternalEObject oldNode = (InternalEObject) node;
			node = (IBeXNode) eResolveProxy(oldNode);
			if (node != oldNode) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							IBeXCoreModelPackage.IBE_XATTRIBUTE_ASSIGNMENT__NODE, oldNode, node));
			}
		}
		return node;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXNode basicGetNode() {
		return node;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNode(IBeXNode newNode) {
		IBeXNode oldNode = node;
		node = newNode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXCoreModelPackage.IBE_XATTRIBUTE_ASSIGNMENT__NODE,
					oldNode, node));
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
							IBeXCoreModelPackage.IBE_XATTRIBUTE_ASSIGNMENT__ATTRIBUTE, oldAttribute, attribute));
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
					IBeXCoreModelPackage.IBE_XATTRIBUTE_ASSIGNMENT__ATTRIBUTE, oldAttribute, attribute));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ValueExpression getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetValue(ValueExpression newValue, NotificationChain msgs) {
		ValueExpression oldValue = value;
		value = newValue;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXCoreModelPackage.IBE_XATTRIBUTE_ASSIGNMENT__VALUE, oldValue, newValue);
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
	public void setValue(ValueExpression newValue) {
		if (newValue != value) {
			NotificationChain msgs = null;
			if (value != null)
				msgs = ((InternalEObject) value).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXCoreModelPackage.IBE_XATTRIBUTE_ASSIGNMENT__VALUE, null, msgs);
			if (newValue != null)
				msgs = ((InternalEObject) newValue).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXCoreModelPackage.IBE_XATTRIBUTE_ASSIGNMENT__VALUE, null, msgs);
			msgs = basicSetValue(newValue, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXCoreModelPackage.IBE_XATTRIBUTE_ASSIGNMENT__VALUE,
					newValue, newValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IBeXCoreModelPackage.IBE_XATTRIBUTE_ASSIGNMENT__VALUE:
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
		case IBeXCoreModelPackage.IBE_XATTRIBUTE_ASSIGNMENT__NODE:
			if (resolve)
				return getNode();
			return basicGetNode();
		case IBeXCoreModelPackage.IBE_XATTRIBUTE_ASSIGNMENT__ATTRIBUTE:
			if (resolve)
				return getAttribute();
			return basicGetAttribute();
		case IBeXCoreModelPackage.IBE_XATTRIBUTE_ASSIGNMENT__VALUE:
			return getValue();
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
		case IBeXCoreModelPackage.IBE_XATTRIBUTE_ASSIGNMENT__NODE:
			setNode((IBeXNode) newValue);
			return;
		case IBeXCoreModelPackage.IBE_XATTRIBUTE_ASSIGNMENT__ATTRIBUTE:
			setAttribute((EAttribute) newValue);
			return;
		case IBeXCoreModelPackage.IBE_XATTRIBUTE_ASSIGNMENT__VALUE:
			setValue((ValueExpression) newValue);
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
		case IBeXCoreModelPackage.IBE_XATTRIBUTE_ASSIGNMENT__NODE:
			setNode((IBeXNode) null);
			return;
		case IBeXCoreModelPackage.IBE_XATTRIBUTE_ASSIGNMENT__ATTRIBUTE:
			setAttribute((EAttribute) null);
			return;
		case IBeXCoreModelPackage.IBE_XATTRIBUTE_ASSIGNMENT__VALUE:
			setValue((ValueExpression) null);
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
		case IBeXCoreModelPackage.IBE_XATTRIBUTE_ASSIGNMENT__NODE:
			return node != null;
		case IBeXCoreModelPackage.IBE_XATTRIBUTE_ASSIGNMENT__ATTRIBUTE:
			return attribute != null;
		case IBeXCoreModelPackage.IBE_XATTRIBUTE_ASSIGNMENT__VALUE:
			return value != null;
		}
		return super.eIsSet(featureID);
	}

} //IBeXAttributeAssignmentImpl
