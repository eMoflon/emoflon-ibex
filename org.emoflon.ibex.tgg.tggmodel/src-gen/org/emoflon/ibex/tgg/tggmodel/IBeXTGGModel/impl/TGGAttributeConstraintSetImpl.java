/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraint;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintSet;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TGG Attribute Constraint Set</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGAttributeConstraintSetImpl#getTggAttributeConstraints <em>Tgg Attribute Constraints</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TGGAttributeConstraintSetImpl extends MinimalEObjectImpl.Container implements TGGAttributeConstraintSet {
	/**
	 * The cached value of the '{@link #getTggAttributeConstraints() <em>Tgg Attribute Constraints</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTggAttributeConstraints()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGAttributeConstraint> tggAttributeConstraints;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TGGAttributeConstraintSetImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXTGGModelPackage.Literals.TGG_ATTRIBUTE_CONSTRAINT_SET;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TGGAttributeConstraint> getTggAttributeConstraints() {
		if (tggAttributeConstraints == null) {
			tggAttributeConstraints = new EObjectContainmentEList<TGGAttributeConstraint>(TGGAttributeConstraint.class,
					this, IBeXTGGModelPackage.TGG_ATTRIBUTE_CONSTRAINT_SET__TGG_ATTRIBUTE_CONSTRAINTS);
		}
		return tggAttributeConstraints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IBeXTGGModelPackage.TGG_ATTRIBUTE_CONSTRAINT_SET__TGG_ATTRIBUTE_CONSTRAINTS:
			return ((InternalEList<?>) getTggAttributeConstraints()).basicRemove(otherEnd, msgs);
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
		case IBeXTGGModelPackage.TGG_ATTRIBUTE_CONSTRAINT_SET__TGG_ATTRIBUTE_CONSTRAINTS:
			return getTggAttributeConstraints();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case IBeXTGGModelPackage.TGG_ATTRIBUTE_CONSTRAINT_SET__TGG_ATTRIBUTE_CONSTRAINTS:
			getTggAttributeConstraints().clear();
			getTggAttributeConstraints().addAll((Collection<? extends TGGAttributeConstraint>) newValue);
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
		case IBeXTGGModelPackage.TGG_ATTRIBUTE_CONSTRAINT_SET__TGG_ATTRIBUTE_CONSTRAINTS:
			getTggAttributeConstraints().clear();
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
		case IBeXTGGModelPackage.TGG_ATTRIBUTE_CONSTRAINT_SET__TGG_ATTRIBUTE_CONSTRAINTS:
			return tggAttributeConstraints != null && !tggAttributeConstraints.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //TGGAttributeConstraintSetImpl
