/**
 */
package org.emoflon.ibex.IBeXDisjunctPatternModel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctAttribute;
import org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctPatternModelPackage;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeConstraint;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IBe XDisjunct Attribute</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.IBeXDisjunctPatternModel.impl.IBeXDisjunctAttributeImpl#getTargetPattern <em>Target Pattern</em>}</li>
 *   <li>{@link org.emoflon.ibex.IBeXDisjunctPatternModel.impl.IBeXDisjunctAttributeImpl#getDisjunctAttribute <em>Disjunct Attribute</em>}</li>
 *   <li>{@link org.emoflon.ibex.IBeXDisjunctPatternModel.impl.IBeXDisjunctAttributeImpl#getSourcePattern <em>Source Pattern</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IBeXDisjunctAttributeImpl extends MinimalEObjectImpl.Container implements IBeXDisjunctAttribute {
	/**
	 * The cached value of the '{@link #getTargetPattern() <em>Target Pattern</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetPattern()
	 * @generated
	 * @ordered
	 */
	protected IBeXContextPattern targetPattern;

	/**
	 * The cached value of the '{@link #getDisjunctAttribute() <em>Disjunct Attribute</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisjunctAttribute()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXAttributeConstraint> disjunctAttribute;

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
		return IBeXDisjunctPatternModelPackage.Literals.IBE_XDISJUNCT_ATTRIBUTE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXContextPattern getTargetPattern() {
		if (targetPattern != null && targetPattern.eIsProxy()) {
			InternalEObject oldTargetPattern = (InternalEObject)targetPattern;
			targetPattern = (IBeXContextPattern)eResolveProxy(oldTargetPattern);
			if (targetPattern != oldTargetPattern) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTE__TARGET_PATTERN, oldTargetPattern, targetPattern));
			}
		}
		return targetPattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXContextPattern basicGetTargetPattern() {
		return targetPattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTargetPattern(IBeXContextPattern newTargetPattern) {
		IBeXContextPattern oldTargetPattern = targetPattern;
		targetPattern = newTargetPattern;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTE__TARGET_PATTERN, oldTargetPattern, targetPattern));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXAttributeConstraint> getDisjunctAttribute() {
		if (disjunctAttribute == null) {
			disjunctAttribute = new EObjectContainmentEList<IBeXAttributeConstraint>(IBeXAttributeConstraint.class, this, IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTE__DISJUNCT_ATTRIBUTE);
		}
		return disjunctAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXContextPattern> getSourcePattern() {
		if (sourcePattern == null) {
			sourcePattern = new EObjectResolvingEList<IBeXContextPattern>(IBeXContextPattern.class, this, IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTE__SOURCE_PATTERN);
		}
		return sourcePattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTE__DISJUNCT_ATTRIBUTE:
				return ((InternalEList<?>)getDisjunctAttribute()).basicRemove(otherEnd, msgs);
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
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTE__TARGET_PATTERN:
				if (resolve) return getTargetPattern();
				return basicGetTargetPattern();
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTE__DISJUNCT_ATTRIBUTE:
				return getDisjunctAttribute();
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTE__SOURCE_PATTERN:
				return getSourcePattern();
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
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTE__TARGET_PATTERN:
				setTargetPattern((IBeXContextPattern)newValue);
				return;
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTE__DISJUNCT_ATTRIBUTE:
				getDisjunctAttribute().clear();
				getDisjunctAttribute().addAll((Collection<? extends IBeXAttributeConstraint>)newValue);
				return;
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTE__SOURCE_PATTERN:
				getSourcePattern().clear();
				getSourcePattern().addAll((Collection<? extends IBeXContextPattern>)newValue);
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
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTE__TARGET_PATTERN:
				setTargetPattern((IBeXContextPattern)null);
				return;
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTE__DISJUNCT_ATTRIBUTE:
				getDisjunctAttribute().clear();
				return;
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTE__SOURCE_PATTERN:
				getSourcePattern().clear();
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
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTE__TARGET_PATTERN:
				return targetPattern != null;
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTE__DISJUNCT_ATTRIBUTE:
				return disjunctAttribute != null && !disjunctAttribute.isEmpty();
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTE__SOURCE_PATTERN:
				return sourcePattern != null && !sourcePattern.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //IBeXDisjunctAttributeImpl
