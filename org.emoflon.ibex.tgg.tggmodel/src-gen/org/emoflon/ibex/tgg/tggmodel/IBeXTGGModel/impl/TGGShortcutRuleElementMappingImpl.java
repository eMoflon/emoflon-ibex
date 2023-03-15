/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleElement;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGShortcutRuleElementMapping;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TGG Shortcut Rule Element Mapping</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGShortcutRuleElementMappingImpl#getOriginal <em>Original</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGShortcutRuleElementMappingImpl#getReplacing <em>Replacing</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TGGShortcutRuleElementMappingImpl extends MinimalEObjectImpl.Container
		implements TGGShortcutRuleElementMapping {
	/**
	 * The cached value of the '{@link #getOriginal() <em>Original</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOriginal()
	 * @generated
	 * @ordered
	 */
	protected TGGRuleElement original;

	/**
	 * The cached value of the '{@link #getReplacing() <em>Replacing</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReplacing()
	 * @generated
	 * @ordered
	 */
	protected TGGRuleElement replacing;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TGGShortcutRuleElementMappingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXTGGModelPackage.Literals.TGG_SHORTCUT_RULE_ELEMENT_MAPPING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGRuleElement getOriginal() {
		if (original != null && original.eIsProxy()) {
			InternalEObject oldOriginal = (InternalEObject) original;
			original = (TGGRuleElement) eResolveProxy(oldOriginal);
			if (original != oldOriginal) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							IBeXTGGModelPackage.TGG_SHORTCUT_RULE_ELEMENT_MAPPING__ORIGINAL, oldOriginal, original));
			}
		}
		return original;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGRuleElement basicGetOriginal() {
		return original;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOriginal(TGGRuleElement newOriginal) {
		TGGRuleElement oldOriginal = original;
		original = newOriginal;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXTGGModelPackage.TGG_SHORTCUT_RULE_ELEMENT_MAPPING__ORIGINAL, oldOriginal, original));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGRuleElement getReplacing() {
		if (replacing != null && replacing.eIsProxy()) {
			InternalEObject oldReplacing = (InternalEObject) replacing;
			replacing = (TGGRuleElement) eResolveProxy(oldReplacing);
			if (replacing != oldReplacing) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							IBeXTGGModelPackage.TGG_SHORTCUT_RULE_ELEMENT_MAPPING__REPLACING, oldReplacing, replacing));
			}
		}
		return replacing;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGRuleElement basicGetReplacing() {
		return replacing;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReplacing(TGGRuleElement newReplacing) {
		TGGRuleElement oldReplacing = replacing;
		replacing = newReplacing;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXTGGModelPackage.TGG_SHORTCUT_RULE_ELEMENT_MAPPING__REPLACING, oldReplacing, replacing));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case IBeXTGGModelPackage.TGG_SHORTCUT_RULE_ELEMENT_MAPPING__ORIGINAL:
			if (resolve)
				return getOriginal();
			return basicGetOriginal();
		case IBeXTGGModelPackage.TGG_SHORTCUT_RULE_ELEMENT_MAPPING__REPLACING:
			if (resolve)
				return getReplacing();
			return basicGetReplacing();
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
		case IBeXTGGModelPackage.TGG_SHORTCUT_RULE_ELEMENT_MAPPING__ORIGINAL:
			setOriginal((TGGRuleElement) newValue);
			return;
		case IBeXTGGModelPackage.TGG_SHORTCUT_RULE_ELEMENT_MAPPING__REPLACING:
			setReplacing((TGGRuleElement) newValue);
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
		case IBeXTGGModelPackage.TGG_SHORTCUT_RULE_ELEMENT_MAPPING__ORIGINAL:
			setOriginal((TGGRuleElement) null);
			return;
		case IBeXTGGModelPackage.TGG_SHORTCUT_RULE_ELEMENT_MAPPING__REPLACING:
			setReplacing((TGGRuleElement) null);
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
		case IBeXTGGModelPackage.TGG_SHORTCUT_RULE_ELEMENT_MAPPING__ORIGINAL:
			return original != null;
		case IBeXTGGModelPackage.TGG_SHORTCUT_RULE_ELEMENT_MAPPING__REPLACING:
			return replacing != null;
		}
		return super.eIsSet(featureID);
	}

} //TGGShortcutRuleElementMappingImpl
