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
import org.eclipse.emf.ecore.util.InternalEList;

import org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctAttributes;
import org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctPatternModelPackage;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeConstraint;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IBe XDisjunct Attributes</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.IBeXDisjunctPatternModel.impl.IBeXDisjunctAttributesImpl#getSourcePattern <em>Source Pattern</em>}</li>
 *   <li>{@link org.emoflon.ibex.IBeXDisjunctPatternModel.impl.IBeXDisjunctAttributesImpl#getTargetPattern <em>Target Pattern</em>}</li>
 *   <li>{@link org.emoflon.ibex.IBeXDisjunctPatternModel.impl.IBeXDisjunctAttributesImpl#getDisjunctAttributes <em>Disjunct Attributes</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IBeXDisjunctAttributesImpl extends MinimalEObjectImpl.Container implements IBeXDisjunctAttributes {
	/**
	 * The default value of the '{@link #getSourcePattern() <em>Source Pattern</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourcePattern()
	 * @generated
	 * @ordered
	 */
	protected static final String SOURCE_PATTERN_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSourcePattern() <em>Source Pattern</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourcePattern()
	 * @generated
	 * @ordered
	 */
	protected String sourcePattern = SOURCE_PATTERN_EDEFAULT;

	/**
	 * The default value of the '{@link #getTargetPattern() <em>Target Pattern</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetPattern()
	 * @generated
	 * @ordered
	 */
	protected static final String TARGET_PATTERN_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTargetPattern() <em>Target Pattern</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetPattern()
	 * @generated
	 * @ordered
	 */
	protected String targetPattern = TARGET_PATTERN_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDisjunctAttributes() <em>Disjunct Attributes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisjunctAttributes()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXAttributeConstraint> disjunctAttributes;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IBeXDisjunctAttributesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXDisjunctPatternModelPackage.Literals.IBE_XDISJUNCT_ATTRIBUTES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSourcePattern() {
		return sourcePattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSourcePattern(String newSourcePattern) {
		String oldSourcePattern = sourcePattern;
		sourcePattern = newSourcePattern;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTES__SOURCE_PATTERN, oldSourcePattern, sourcePattern));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getTargetPattern() {
		return targetPattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTargetPattern(String newTargetPattern) {
		String oldTargetPattern = targetPattern;
		targetPattern = newTargetPattern;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTES__TARGET_PATTERN, oldTargetPattern, targetPattern));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXAttributeConstraint> getDisjunctAttributes() {
		if (disjunctAttributes == null) {
			disjunctAttributes = new EObjectContainmentEList<IBeXAttributeConstraint>(IBeXAttributeConstraint.class, this, IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTES__DISJUNCT_ATTRIBUTES);
		}
		return disjunctAttributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTES__DISJUNCT_ATTRIBUTES:
				return ((InternalEList<?>)getDisjunctAttributes()).basicRemove(otherEnd, msgs);
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
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTES__SOURCE_PATTERN:
				return getSourcePattern();
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTES__TARGET_PATTERN:
				return getTargetPattern();
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTES__DISJUNCT_ATTRIBUTES:
				return getDisjunctAttributes();
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
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTES__SOURCE_PATTERN:
				setSourcePattern((String)newValue);
				return;
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTES__TARGET_PATTERN:
				setTargetPattern((String)newValue);
				return;
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTES__DISJUNCT_ATTRIBUTES:
				getDisjunctAttributes().clear();
				getDisjunctAttributes().addAll((Collection<? extends IBeXAttributeConstraint>)newValue);
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
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTES__SOURCE_PATTERN:
				setSourcePattern(SOURCE_PATTERN_EDEFAULT);
				return;
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTES__TARGET_PATTERN:
				setTargetPattern(TARGET_PATTERN_EDEFAULT);
				return;
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTES__DISJUNCT_ATTRIBUTES:
				getDisjunctAttributes().clear();
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
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTES__SOURCE_PATTERN:
				return SOURCE_PATTERN_EDEFAULT == null ? sourcePattern != null : !SOURCE_PATTERN_EDEFAULT.equals(sourcePattern);
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTES__TARGET_PATTERN:
				return TARGET_PATTERN_EDEFAULT == null ? targetPattern != null : !TARGET_PATTERN_EDEFAULT.equals(targetPattern);
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTES__DISJUNCT_ATTRIBUTES:
				return disjunctAttributes != null && !disjunctAttributes.isEmpty();
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
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (sourcePattern: ");
		result.append(sourcePattern);
		result.append(", targetPattern: ");
		result.append(targetPattern);
		result.append(')');
		return result.toString();
	}

} //IBeXDisjunctAttributesImpl
