/**
 */
package org.emoflon.ibex.gt.StateModel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.emoflon.ibex.gt.StateModel.AttributeDelta;
import org.emoflon.ibex.gt.StateModel.StateModelPackage;
import org.emoflon.ibex.gt.StateModel.Value;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Attribute Delta</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.impl.AttributeDeltaImpl#getAttribute <em>Attribute</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.impl.AttributeDeltaImpl#getObject <em>Object</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.impl.AttributeDeltaImpl#getOldValue <em>Old Value</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.impl.AttributeDeltaImpl#getNewValue <em>New Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AttributeDeltaImpl extends MinimalEObjectImpl.Container implements AttributeDelta {
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
	 * The cached value of the '{@link #getObject() <em>Object</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObject()
	 * @generated
	 * @ordered
	 */
	protected EObject object;

	/**
	 * The cached value of the '{@link #getOldValue() <em>Old Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOldValue()
	 * @generated
	 * @ordered
	 */
	protected Value oldValue;

	/**
	 * The cached value of the '{@link #getNewValue() <em>New Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNewValue()
	 * @generated
	 * @ordered
	 */
	protected Value newValue;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AttributeDeltaImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StateModelPackage.Literals.ATTRIBUTE_DELTA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAttribute() {
		if (attribute != null && attribute.eIsProxy()) {
			InternalEObject oldAttribute = (InternalEObject) attribute;
			attribute = (EAttribute) eResolveProxy(oldAttribute);
			if (attribute != oldAttribute) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							StateModelPackage.ATTRIBUTE_DELTA__ATTRIBUTE, oldAttribute, attribute));
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
	@Override
	public void setAttribute(EAttribute newAttribute) {
		EAttribute oldAttribute = attribute;
		attribute = newAttribute;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateModelPackage.ATTRIBUTE_DELTA__ATTRIBUTE,
					oldAttribute, attribute));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject getObject() {
		if (object != null && object.eIsProxy()) {
			InternalEObject oldObject = (InternalEObject) object;
			object = eResolveProxy(oldObject);
			if (object != oldObject) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, StateModelPackage.ATTRIBUTE_DELTA__OBJECT,
							oldObject, object));
			}
		}
		return object;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetObject() {
		return object;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setObject(EObject newObject) {
		EObject oldObject = object;
		object = newObject;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateModelPackage.ATTRIBUTE_DELTA__OBJECT, oldObject,
					object));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Value getOldValue() {
		return oldValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOldValue(Value newOldValue, NotificationChain msgs) {
		Value oldOldValue = oldValue;
		oldValue = newOldValue;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					StateModelPackage.ATTRIBUTE_DELTA__OLD_VALUE, oldOldValue, newOldValue);
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
	public void setOldValue(Value newOldValue) {
		if (newOldValue != oldValue) {
			NotificationChain msgs = null;
			if (oldValue != null)
				msgs = ((InternalEObject) oldValue).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - StateModelPackage.ATTRIBUTE_DELTA__OLD_VALUE, null, msgs);
			if (newOldValue != null)
				msgs = ((InternalEObject) newOldValue).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - StateModelPackage.ATTRIBUTE_DELTA__OLD_VALUE, null, msgs);
			msgs = basicSetOldValue(newOldValue, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateModelPackage.ATTRIBUTE_DELTA__OLD_VALUE,
					newOldValue, newOldValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Value getNewValue() {
		return newValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNewValue(Value newNewValue, NotificationChain msgs) {
		Value oldNewValue = newValue;
		newValue = newNewValue;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					StateModelPackage.ATTRIBUTE_DELTA__NEW_VALUE, oldNewValue, newNewValue);
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
	public void setNewValue(Value newNewValue) {
		if (newNewValue != newValue) {
			NotificationChain msgs = null;
			if (newValue != null)
				msgs = ((InternalEObject) newValue).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - StateModelPackage.ATTRIBUTE_DELTA__NEW_VALUE, null, msgs);
			if (newNewValue != null)
				msgs = ((InternalEObject) newNewValue).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - StateModelPackage.ATTRIBUTE_DELTA__NEW_VALUE, null, msgs);
			msgs = basicSetNewValue(newNewValue, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateModelPackage.ATTRIBUTE_DELTA__NEW_VALUE,
					newNewValue, newNewValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case StateModelPackage.ATTRIBUTE_DELTA__OLD_VALUE:
			return basicSetOldValue(null, msgs);
		case StateModelPackage.ATTRIBUTE_DELTA__NEW_VALUE:
			return basicSetNewValue(null, msgs);
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
		case StateModelPackage.ATTRIBUTE_DELTA__ATTRIBUTE:
			if (resolve)
				return getAttribute();
			return basicGetAttribute();
		case StateModelPackage.ATTRIBUTE_DELTA__OBJECT:
			if (resolve)
				return getObject();
			return basicGetObject();
		case StateModelPackage.ATTRIBUTE_DELTA__OLD_VALUE:
			return getOldValue();
		case StateModelPackage.ATTRIBUTE_DELTA__NEW_VALUE:
			return getNewValue();
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
		case StateModelPackage.ATTRIBUTE_DELTA__ATTRIBUTE:
			setAttribute((EAttribute) newValue);
			return;
		case StateModelPackage.ATTRIBUTE_DELTA__OBJECT:
			setObject((EObject) newValue);
			return;
		case StateModelPackage.ATTRIBUTE_DELTA__OLD_VALUE:
			setOldValue((Value) newValue);
			return;
		case StateModelPackage.ATTRIBUTE_DELTA__NEW_VALUE:
			setNewValue((Value) newValue);
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
		case StateModelPackage.ATTRIBUTE_DELTA__ATTRIBUTE:
			setAttribute((EAttribute) null);
			return;
		case StateModelPackage.ATTRIBUTE_DELTA__OBJECT:
			setObject((EObject) null);
			return;
		case StateModelPackage.ATTRIBUTE_DELTA__OLD_VALUE:
			setOldValue((Value) null);
			return;
		case StateModelPackage.ATTRIBUTE_DELTA__NEW_VALUE:
			setNewValue((Value) null);
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
		case StateModelPackage.ATTRIBUTE_DELTA__ATTRIBUTE:
			return attribute != null;
		case StateModelPackage.ATTRIBUTE_DELTA__OBJECT:
			return object != null;
		case StateModelPackage.ATTRIBUTE_DELTA__OLD_VALUE:
			return oldValue != null;
		case StateModelPackage.ATTRIBUTE_DELTA__NEW_VALUE:
			return newValue != null;
		}
		return super.eIsSet(featureID);
	}

} //AttributeDeltaImpl
