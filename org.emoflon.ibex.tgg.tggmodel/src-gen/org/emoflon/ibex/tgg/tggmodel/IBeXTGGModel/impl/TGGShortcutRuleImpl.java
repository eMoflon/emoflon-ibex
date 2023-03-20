/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleElement;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGShortcutRule;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGShortcutRuleElementMapping;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TGG Shortcut Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGShortcutRuleImpl#getOriginalRule <em>Original Rule</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGShortcutRuleImpl#getReplacingRule <em>Replacing Rule</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGShortcutRuleImpl#getMappings <em>Mappings</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGShortcutRuleImpl#getUnmappedOriginalElements <em>Unmapped Original Elements</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGShortcutRuleImpl#getUnmappedReplacingElements <em>Unmapped Replacing Elements</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TGGShortcutRuleImpl extends TGGOperationalRuleImpl implements TGGShortcutRule {
	/**
	 * The cached value of the '{@link #getOriginalRule() <em>Original Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOriginalRule()
	 * @generated
	 * @ordered
	 */
	protected TGGRule originalRule;

	/**
	 * The cached value of the '{@link #getReplacingRule() <em>Replacing Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReplacingRule()
	 * @generated
	 * @ordered
	 */
	protected TGGRule replacingRule;

	/**
	 * The cached value of the '{@link #getMappings() <em>Mappings</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMappings()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGShortcutRuleElementMapping> mappings;

	/**
	 * The cached value of the '{@link #getUnmappedOriginalElements() <em>Unmapped Original Elements</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnmappedOriginalElements()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGRuleElement> unmappedOriginalElements;

	/**
	 * The cached value of the '{@link #getUnmappedReplacingElements() <em>Unmapped Replacing Elements</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnmappedReplacingElements()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGRuleElement> unmappedReplacingElements;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TGGShortcutRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXTGGModelPackage.Literals.TGG_SHORTCUT_RULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGRule getOriginalRule() {
		if (originalRule != null && originalRule.eIsProxy()) {
			InternalEObject oldOriginalRule = (InternalEObject) originalRule;
			originalRule = (TGGRule) eResolveProxy(oldOriginalRule);
			if (originalRule != oldOriginalRule) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, IBeXTGGModelPackage.TGG_SHORTCUT_RULE__ORIGINAL_RULE, oldOriginalRule,
							originalRule));
			}
		}
		return originalRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGRule basicGetOriginalRule() {
		return originalRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOriginalRule(TGGRule newOriginalRule) {
		TGGRule oldOriginalRule = originalRule;
		originalRule = newOriginalRule;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXTGGModelPackage.TGG_SHORTCUT_RULE__ORIGINAL_RULE, oldOriginalRule, originalRule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGRule getReplacingRule() {
		if (replacingRule != null && replacingRule.eIsProxy()) {
			InternalEObject oldReplacingRule = (InternalEObject) replacingRule;
			replacingRule = (TGGRule) eResolveProxy(oldReplacingRule);
			if (replacingRule != oldReplacingRule) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, IBeXTGGModelPackage.TGG_SHORTCUT_RULE__REPLACING_RULE, oldReplacingRule,
							replacingRule));
			}
		}
		return replacingRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGRule basicGetReplacingRule() {
		return replacingRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReplacingRule(TGGRule newReplacingRule) {
		TGGRule oldReplacingRule = replacingRule;
		replacingRule = newReplacingRule;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXTGGModelPackage.TGG_SHORTCUT_RULE__REPLACING_RULE, oldReplacingRule, replacingRule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TGGShortcutRuleElementMapping> getMappings() {
		if (mappings == null) {
			mappings = new EObjectResolvingEList<TGGShortcutRuleElementMapping>(TGGShortcutRuleElementMapping.class, this,
					IBeXTGGModelPackage.TGG_SHORTCUT_RULE__MAPPINGS);
		}
		return mappings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TGGRuleElement> getUnmappedOriginalElements() {
		if (unmappedOriginalElements == null) {
			unmappedOriginalElements = new EObjectResolvingEList<TGGRuleElement>(TGGRuleElement.class, this,
					IBeXTGGModelPackage.TGG_SHORTCUT_RULE__UNMAPPED_ORIGINAL_ELEMENTS);
		}
		return unmappedOriginalElements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TGGRuleElement> getUnmappedReplacingElements() {
		if (unmappedReplacingElements == null) {
			unmappedReplacingElements = new EObjectResolvingEList<TGGRuleElement>(TGGRuleElement.class, this,
					IBeXTGGModelPackage.TGG_SHORTCUT_RULE__UNMAPPED_REPLACING_ELEMENTS);
		}
		return unmappedReplacingElements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case IBeXTGGModelPackage.TGG_SHORTCUT_RULE__ORIGINAL_RULE:
				if (resolve)
					return getOriginalRule();
				return basicGetOriginalRule();
			case IBeXTGGModelPackage.TGG_SHORTCUT_RULE__REPLACING_RULE:
				if (resolve)
					return getReplacingRule();
				return basicGetReplacingRule();
			case IBeXTGGModelPackage.TGG_SHORTCUT_RULE__MAPPINGS:
				return getMappings();
			case IBeXTGGModelPackage.TGG_SHORTCUT_RULE__UNMAPPED_ORIGINAL_ELEMENTS:
				return getUnmappedOriginalElements();
			case IBeXTGGModelPackage.TGG_SHORTCUT_RULE__UNMAPPED_REPLACING_ELEMENTS:
				return getUnmappedReplacingElements();
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
			case IBeXTGGModelPackage.TGG_SHORTCUT_RULE__ORIGINAL_RULE:
				setOriginalRule((TGGRule) newValue);
				return;
			case IBeXTGGModelPackage.TGG_SHORTCUT_RULE__REPLACING_RULE:
				setReplacingRule((TGGRule) newValue);
				return;
			case IBeXTGGModelPackage.TGG_SHORTCUT_RULE__MAPPINGS:
				getMappings().clear();
				getMappings().addAll((Collection<? extends TGGShortcutRuleElementMapping>) newValue);
				return;
			case IBeXTGGModelPackage.TGG_SHORTCUT_RULE__UNMAPPED_ORIGINAL_ELEMENTS:
				getUnmappedOriginalElements().clear();
				getUnmappedOriginalElements().addAll((Collection<? extends TGGRuleElement>) newValue);
				return;
			case IBeXTGGModelPackage.TGG_SHORTCUT_RULE__UNMAPPED_REPLACING_ELEMENTS:
				getUnmappedReplacingElements().clear();
				getUnmappedReplacingElements().addAll((Collection<? extends TGGRuleElement>) newValue);
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
			case IBeXTGGModelPackage.TGG_SHORTCUT_RULE__ORIGINAL_RULE:
				setOriginalRule((TGGRule) null);
				return;
			case IBeXTGGModelPackage.TGG_SHORTCUT_RULE__REPLACING_RULE:
				setReplacingRule((TGGRule) null);
				return;
			case IBeXTGGModelPackage.TGG_SHORTCUT_RULE__MAPPINGS:
				getMappings().clear();
				return;
			case IBeXTGGModelPackage.TGG_SHORTCUT_RULE__UNMAPPED_ORIGINAL_ELEMENTS:
				getUnmappedOriginalElements().clear();
				return;
			case IBeXTGGModelPackage.TGG_SHORTCUT_RULE__UNMAPPED_REPLACING_ELEMENTS:
				getUnmappedReplacingElements().clear();
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
			case IBeXTGGModelPackage.TGG_SHORTCUT_RULE__ORIGINAL_RULE:
				return originalRule != null;
			case IBeXTGGModelPackage.TGG_SHORTCUT_RULE__REPLACING_RULE:
				return replacingRule != null;
			case IBeXTGGModelPackage.TGG_SHORTCUT_RULE__MAPPINGS:
				return mappings != null && !mappings.isEmpty();
			case IBeXTGGModelPackage.TGG_SHORTCUT_RULE__UNMAPPED_ORIGINAL_ELEMENTS:
				return unmappedOriginalElements != null && !unmappedOriginalElements.isEmpty();
			case IBeXTGGModelPackage.TGG_SHORTCUT_RULE__UNMAPPED_REPLACING_ELEMENTS:
				return unmappedReplacingElements != null && !unmappedReplacingElements.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //TGGShortcutRuleImpl
