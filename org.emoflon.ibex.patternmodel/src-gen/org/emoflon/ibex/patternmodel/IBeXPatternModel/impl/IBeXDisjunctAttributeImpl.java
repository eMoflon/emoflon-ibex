/**
 */
package org.emoflon.ibex.patternmodel.IBeXPatternModel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXConstraint;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjunctAttribute;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IBe XDisjunct Attribute</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXDisjunctAttributeImpl#getTargetPattern <em>Target Pattern</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXDisjunctAttributeImpl#getSourcePattern <em>Source Pattern</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXDisjunctAttributeImpl#getDisjunctAttribute <em>Disjunct Attribute</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IBeXDisjunctAttributeImpl extends MinimalEObjectImpl.Container implements IBeXDisjunctAttribute {
	/**
	 * The cached value of the '{@link #getTargetPattern() <em>Target Pattern</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetPattern()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXContextPattern> targetPattern;

	/**
	 * The cached value of the '{@link #getSourcePattern() <em>Source Pattern</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourcePattern()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXContextPattern> sourcePattern;

	/**
	 * The cached value of the '{@link #getDisjunctAttribute() <em>Disjunct Attribute</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisjunctAttribute()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXConstraint> disjunctAttribute;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IBeXDisjunctAttributeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXPatternModelPackage.Literals.IBE_XDISJUNCT_ATTRIBUTE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXContextPattern> getTargetPattern() {
		if (targetPattern == null) {
			targetPattern = new EObjectResolvingEList<IBeXContextPattern>(IBeXContextPattern.class, this,
					IBeXPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTE__TARGET_PATTERN);
		}
		return targetPattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXContextPattern> getSourcePattern() {
		if (sourcePattern == null) {
			sourcePattern = new EObjectResolvingEList<IBeXContextPattern>(IBeXContextPattern.class, this,
					IBeXPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTE__SOURCE_PATTERN);
		}
		return sourcePattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXConstraint> getDisjunctAttribute() {
		if (disjunctAttribute == null) {
			disjunctAttribute = new EObjectContainmentEList<IBeXConstraint>(IBeXConstraint.class, this,
					IBeXPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTE__DISJUNCT_ATTRIBUTE);
		}
		return disjunctAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IBeXPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTE__DISJUNCT_ATTRIBUTE:
			return ((InternalEList<?>) getDisjunctAttribute()).basicRemove(otherEnd, msgs);
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
		case IBeXPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTE__TARGET_PATTERN:
			return getTargetPattern();
		case IBeXPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTE__SOURCE_PATTERN:
			return getSourcePattern();
		case IBeXPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTE__DISJUNCT_ATTRIBUTE:
			return getDisjunctAttribute();
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
		case IBeXPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTE__TARGET_PATTERN:
			getTargetPattern().clear();
			getTargetPattern().addAll((Collection<? extends IBeXContextPattern>) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTE__SOURCE_PATTERN:
			getSourcePattern().clear();
			getSourcePattern().addAll((Collection<? extends IBeXContextPattern>) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTE__DISJUNCT_ATTRIBUTE:
			getDisjunctAttribute().clear();
			getDisjunctAttribute().addAll((Collection<? extends IBeXConstraint>) newValue);
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
		case IBeXPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTE__TARGET_PATTERN:
			getTargetPattern().clear();
			return;
		case IBeXPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTE__SOURCE_PATTERN:
			getSourcePattern().clear();
			return;
		case IBeXPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTE__DISJUNCT_ATTRIBUTE:
			getDisjunctAttribute().clear();
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
		case IBeXPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTE__TARGET_PATTERN:
			return targetPattern != null && !targetPattern.isEmpty();
		case IBeXPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTE__SOURCE_PATTERN:
			return sourcePattern != null && !sourcePattern.isEmpty();
		case IBeXPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTE__DISJUNCT_ATTRIBUTE:
			return disjunctAttribute != null && !disjunctAttribute.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //IBeXDisjunctAttributeImpl
