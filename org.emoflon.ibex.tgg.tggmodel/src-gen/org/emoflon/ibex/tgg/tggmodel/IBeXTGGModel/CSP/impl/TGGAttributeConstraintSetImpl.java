/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.CSPPackage;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraint;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintParameterValue;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintSet;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TGG Attribute Constraint Set</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGAttributeConstraintSetImpl#getTggAttributeConstraints <em>Tgg Attribute Constraints</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGAttributeConstraintSetImpl#getParameters <em>Parameters</em>}</li>
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
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGAttributeConstraintParameterValue> parameters;

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
		return CSPPackage.Literals.TGG_ATTRIBUTE_CONSTRAINT_SET;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TGGAttributeConstraint> getTggAttributeConstraints() {
		if (tggAttributeConstraints == null) {
			tggAttributeConstraints = new EObjectContainmentEList<TGGAttributeConstraint>(TGGAttributeConstraint.class, this,
					CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_SET__TGG_ATTRIBUTE_CONSTRAINTS);
		}
		return tggAttributeConstraints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TGGAttributeConstraintParameterValue> getParameters() {
		if (parameters == null) {
			parameters = new EObjectContainmentEList<TGGAttributeConstraintParameterValue>(TGGAttributeConstraintParameterValue.class, this,
					CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_SET__PARAMETERS);
		}
		return parameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_SET__TGG_ATTRIBUTE_CONSTRAINTS:
				return ((InternalEList<?>) getTggAttributeConstraints()).basicRemove(otherEnd, msgs);
			case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_SET__PARAMETERS:
				return ((InternalEList<?>) getParameters()).basicRemove(otherEnd, msgs);
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
			case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_SET__TGG_ATTRIBUTE_CONSTRAINTS:
				return getTggAttributeConstraints();
			case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_SET__PARAMETERS:
				return getParameters();
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
			case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_SET__TGG_ATTRIBUTE_CONSTRAINTS:
				getTggAttributeConstraints().clear();
				getTggAttributeConstraints().addAll((Collection<? extends TGGAttributeConstraint>) newValue);
				return;
			case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_SET__PARAMETERS:
				getParameters().clear();
				getParameters().addAll((Collection<? extends TGGAttributeConstraintParameterValue>) newValue);
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
			case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_SET__TGG_ATTRIBUTE_CONSTRAINTS:
				getTggAttributeConstraints().clear();
				return;
			case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_SET__PARAMETERS:
				getParameters().clear();
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
			case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_SET__TGG_ATTRIBUTE_CONSTRAINTS:
				return tggAttributeConstraints != null && !tggAttributeConstraints.isEmpty();
			case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_SET__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //TGGAttributeConstraintSetImpl
