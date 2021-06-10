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
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjointAttribute;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IBe XDisjoint Attribute</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXDisjointAttributeImpl#getTargetPattern <em>Target Pattern</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXDisjointAttributeImpl#getSourcePattern <em>Source Pattern</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXDisjointAttributeImpl#getDisjointAttribute <em>Disjoint Attribute</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IBeXDisjointAttributeImpl extends MinimalEObjectImpl.Container implements IBeXDisjointAttribute {
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
	 * The cached value of the '{@link #getDisjointAttribute() <em>Disjoint Attribute</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisjointAttribute()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXConstraint> disjointAttribute;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IBeXDisjointAttributeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXPatternModelPackage.Literals.IBE_XDISJOINT_ATTRIBUTE;
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
					IBeXPatternModelPackage.IBE_XDISJOINT_ATTRIBUTE__TARGET_PATTERN);
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
					IBeXPatternModelPackage.IBE_XDISJOINT_ATTRIBUTE__SOURCE_PATTERN);
		}
		return sourcePattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXConstraint> getDisjointAttribute() {
		if (disjointAttribute == null) {
			disjointAttribute = new EObjectContainmentEList<IBeXConstraint>(IBeXConstraint.class, this,
					IBeXPatternModelPackage.IBE_XDISJOINT_ATTRIBUTE__DISJOINT_ATTRIBUTE);
		}
		return disjointAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IBeXPatternModelPackage.IBE_XDISJOINT_ATTRIBUTE__DISJOINT_ATTRIBUTE:
			return ((InternalEList<?>) getDisjointAttribute()).basicRemove(otherEnd, msgs);
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
		case IBeXPatternModelPackage.IBE_XDISJOINT_ATTRIBUTE__TARGET_PATTERN:
			return getTargetPattern();
		case IBeXPatternModelPackage.IBE_XDISJOINT_ATTRIBUTE__SOURCE_PATTERN:
			return getSourcePattern();
		case IBeXPatternModelPackage.IBE_XDISJOINT_ATTRIBUTE__DISJOINT_ATTRIBUTE:
			return getDisjointAttribute();
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
		case IBeXPatternModelPackage.IBE_XDISJOINT_ATTRIBUTE__TARGET_PATTERN:
			getTargetPattern().clear();
			getTargetPattern().addAll((Collection<? extends IBeXContextPattern>) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XDISJOINT_ATTRIBUTE__SOURCE_PATTERN:
			getSourcePattern().clear();
			getSourcePattern().addAll((Collection<? extends IBeXContextPattern>) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XDISJOINT_ATTRIBUTE__DISJOINT_ATTRIBUTE:
			getDisjointAttribute().clear();
			getDisjointAttribute().addAll((Collection<? extends IBeXConstraint>) newValue);
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
		case IBeXPatternModelPackage.IBE_XDISJOINT_ATTRIBUTE__TARGET_PATTERN:
			getTargetPattern().clear();
			return;
		case IBeXPatternModelPackage.IBE_XDISJOINT_ATTRIBUTE__SOURCE_PATTERN:
			getSourcePattern().clear();
			return;
		case IBeXPatternModelPackage.IBE_XDISJOINT_ATTRIBUTE__DISJOINT_ATTRIBUTE:
			getDisjointAttribute().clear();
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
		case IBeXPatternModelPackage.IBE_XDISJOINT_ATTRIBUTE__TARGET_PATTERN:
			return targetPattern != null && !targetPattern.isEmpty();
		case IBeXPatternModelPackage.IBE_XDISJOINT_ATTRIBUTE__SOURCE_PATTERN:
			return sourcePattern != null && !sourcePattern.isEmpty();
		case IBeXPatternModelPackage.IBE_XDISJOINT_ATTRIBUTE__DISJOINT_ATTRIBUTE:
			return disjointAttribute != null && !disjointAttribute.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //IBeXDisjointAttributeImpl
