/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXPatternImpl;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintSet;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGPattern;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TGG Pattern</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGPatternImpl#getAttributeConstraints <em>Attribute Constraints</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TGGPatternImpl extends IBeXPatternImpl implements TGGPattern {
	/**
	 * The cached value of the '{@link #getAttributeConstraints() <em>Attribute Constraints</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeConstraints()
	 * @generated
	 * @ordered
	 */
	protected TGGAttributeConstraintSet attributeConstraints;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TGGPatternImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXTGGModelPackage.Literals.TGG_PATTERN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGAttributeConstraintSet getAttributeConstraints() {
		return attributeConstraints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAttributeConstraints(TGGAttributeConstraintSet newAttributeConstraints,
			NotificationChain msgs) {
		TGGAttributeConstraintSet oldAttributeConstraints = attributeConstraints;
		attributeConstraints = newAttributeConstraints;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXTGGModelPackage.TGG_PATTERN__ATTRIBUTE_CONSTRAINTS, oldAttributeConstraints,
					newAttributeConstraints);
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
	public void setAttributeConstraints(TGGAttributeConstraintSet newAttributeConstraints) {
		if (newAttributeConstraints != attributeConstraints) {
			NotificationChain msgs = null;
			if (attributeConstraints != null)
				msgs = ((InternalEObject) attributeConstraints).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_PATTERN__ATTRIBUTE_CONSTRAINTS, null, msgs);
			if (newAttributeConstraints != null)
				msgs = ((InternalEObject) newAttributeConstraints).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_PATTERN__ATTRIBUTE_CONSTRAINTS, null, msgs);
			msgs = basicSetAttributeConstraints(newAttributeConstraints, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXTGGModelPackage.TGG_PATTERN__ATTRIBUTE_CONSTRAINTS, newAttributeConstraints,
					newAttributeConstraints));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IBeXTGGModelPackage.TGG_PATTERN__ATTRIBUTE_CONSTRAINTS:
			return basicSetAttributeConstraints(null, msgs);
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
		case IBeXTGGModelPackage.TGG_PATTERN__ATTRIBUTE_CONSTRAINTS:
			return getAttributeConstraints();
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
		case IBeXTGGModelPackage.TGG_PATTERN__ATTRIBUTE_CONSTRAINTS:
			setAttributeConstraints((TGGAttributeConstraintSet) newValue);
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
		case IBeXTGGModelPackage.TGG_PATTERN__ATTRIBUTE_CONSTRAINTS:
			setAttributeConstraints((TGGAttributeConstraintSet) null);
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
		case IBeXTGGModelPackage.TGG_PATTERN__ATTRIBUTE_CONSTRAINTS:
			return attributeConstraints != null;
		}
		return super.eIsSet(featureID);
	}

} //TGGPatternImpl
