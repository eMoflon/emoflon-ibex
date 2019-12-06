/**
 */
package language.repair.impl;

import java.util.Collection;

import language.TGGRule;
import language.TGGRuleElement;

import language.repair.ExternalShortcutRule;
import language.repair.RepairPackage;
import language.repair.TGGRuleElementMapping;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>External Shortcut Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link language.repair.impl.ExternalShortcutRuleImpl#getSourceRule <em>Source Rule</em>}</li>
 *   <li>{@link language.repair.impl.ExternalShortcutRuleImpl#getTargetRule <em>Target Rule</em>}</li>
 *   <li>{@link language.repair.impl.ExternalShortcutRuleImpl#getDeletions <em>Deletions</em>}</li>
 *   <li>{@link language.repair.impl.ExternalShortcutRuleImpl#getCreations <em>Creations</em>}</li>
 *   <li>{@link language.repair.impl.ExternalShortcutRuleImpl#getUnboundSrcContext <em>Unbound Src Context</em>}</li>
 *   <li>{@link language.repair.impl.ExternalShortcutRuleImpl#getUnboundTrgContext <em>Unbound Trg Context</em>}</li>
 *   <li>{@link language.repair.impl.ExternalShortcutRuleImpl#getMapping <em>Mapping</em>}</li>
 *   <li>{@link language.repair.impl.ExternalShortcutRuleImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ExternalShortcutRuleImpl extends EObjectImpl implements ExternalShortcutRule {
	/**
	 * The cached value of the '{@link #getSourceRule() <em>Source Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceRule()
	 * @generated
	 * @ordered
	 */
	protected TGGRule sourceRule;

	/**
	 * The cached value of the '{@link #getTargetRule() <em>Target Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetRule()
	 * @generated
	 * @ordered
	 */
	protected TGGRule targetRule;

	/**
	 * The cached value of the '{@link #getDeletions() <em>Deletions</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeletions()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGRuleElement> deletions;

	/**
	 * The cached value of the '{@link #getCreations() <em>Creations</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreations()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGRuleElement> creations;

	/**
	 * The cached value of the '{@link #getUnboundSrcContext() <em>Unbound Src Context</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnboundSrcContext()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGRuleElement> unboundSrcContext;

	/**
	 * The cached value of the '{@link #getUnboundTrgContext() <em>Unbound Trg Context</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnboundTrgContext()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGRuleElement> unboundTrgContext;

	/**
	 * The cached value of the '{@link #getMapping() <em>Mapping</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapping()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGRuleElementMapping> mapping;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExternalShortcutRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RepairPackage.Literals.EXTERNAL_SHORTCUT_RULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGRule getSourceRule() {
		if (sourceRule != null && sourceRule.eIsProxy()) {
			InternalEObject oldSourceRule = (InternalEObject) sourceRule;
			sourceRule = (TGGRule) eResolveProxy(oldSourceRule);
			if (sourceRule != oldSourceRule) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RepairPackage.EXTERNAL_SHORTCUT_RULE__SOURCE_RULE, oldSourceRule, sourceRule));
			}
		}
		return sourceRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGRule basicGetSourceRule() {
		return sourceRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSourceRule(TGGRule newSourceRule) {
		TGGRule oldSourceRule = sourceRule;
		sourceRule = newSourceRule;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RepairPackage.EXTERNAL_SHORTCUT_RULE__SOURCE_RULE, oldSourceRule, sourceRule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGRule getTargetRule() {
		if (targetRule != null && targetRule.eIsProxy()) {
			InternalEObject oldTargetRule = (InternalEObject) targetRule;
			targetRule = (TGGRule) eResolveProxy(oldTargetRule);
			if (targetRule != oldTargetRule) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RepairPackage.EXTERNAL_SHORTCUT_RULE__TARGET_RULE, oldTargetRule, targetRule));
			}
		}
		return targetRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGRule basicGetTargetRule() {
		return targetRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTargetRule(TGGRule newTargetRule) {
		TGGRule oldTargetRule = targetRule;
		targetRule = newTargetRule;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RepairPackage.EXTERNAL_SHORTCUT_RULE__TARGET_RULE, oldTargetRule, targetRule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TGGRuleElement> getDeletions() {
		if (deletions == null) {
			deletions = new EObjectResolvingEList<TGGRuleElement>(TGGRuleElement.class, this, RepairPackage.EXTERNAL_SHORTCUT_RULE__DELETIONS);
		}
		return deletions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TGGRuleElement> getCreations() {
		if (creations == null) {
			creations = new EObjectResolvingEList<TGGRuleElement>(TGGRuleElement.class, this, RepairPackage.EXTERNAL_SHORTCUT_RULE__CREATIONS);
		}
		return creations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TGGRuleElement> getUnboundSrcContext() {
		if (unboundSrcContext == null) {
			unboundSrcContext = new EObjectResolvingEList<TGGRuleElement>(TGGRuleElement.class, this, RepairPackage.EXTERNAL_SHORTCUT_RULE__UNBOUND_SRC_CONTEXT);
		}
		return unboundSrcContext;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TGGRuleElement> getUnboundTrgContext() {
		if (unboundTrgContext == null) {
			unboundTrgContext = new EObjectResolvingEList<TGGRuleElement>(TGGRuleElement.class, this, RepairPackage.EXTERNAL_SHORTCUT_RULE__UNBOUND_TRG_CONTEXT);
		}
		return unboundTrgContext;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TGGRuleElementMapping> getMapping() {
		if (mapping == null) {
			mapping = new EObjectContainmentEList<TGGRuleElementMapping>(TGGRuleElementMapping.class, this, RepairPackage.EXTERNAL_SHORTCUT_RULE__MAPPING);
		}
		return mapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RepairPackage.EXTERNAL_SHORTCUT_RULE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case RepairPackage.EXTERNAL_SHORTCUT_RULE__MAPPING:
			return ((InternalEList<?>) getMapping()).basicRemove(otherEnd, msgs);
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
		case RepairPackage.EXTERNAL_SHORTCUT_RULE__SOURCE_RULE:
			if (resolve)
				return getSourceRule();
			return basicGetSourceRule();
		case RepairPackage.EXTERNAL_SHORTCUT_RULE__TARGET_RULE:
			if (resolve)
				return getTargetRule();
			return basicGetTargetRule();
		case RepairPackage.EXTERNAL_SHORTCUT_RULE__DELETIONS:
			return getDeletions();
		case RepairPackage.EXTERNAL_SHORTCUT_RULE__CREATIONS:
			return getCreations();
		case RepairPackage.EXTERNAL_SHORTCUT_RULE__UNBOUND_SRC_CONTEXT:
			return getUnboundSrcContext();
		case RepairPackage.EXTERNAL_SHORTCUT_RULE__UNBOUND_TRG_CONTEXT:
			return getUnboundTrgContext();
		case RepairPackage.EXTERNAL_SHORTCUT_RULE__MAPPING:
			return getMapping();
		case RepairPackage.EXTERNAL_SHORTCUT_RULE__NAME:
			return getName();
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
		case RepairPackage.EXTERNAL_SHORTCUT_RULE__SOURCE_RULE:
			setSourceRule((TGGRule) newValue);
			return;
		case RepairPackage.EXTERNAL_SHORTCUT_RULE__TARGET_RULE:
			setTargetRule((TGGRule) newValue);
			return;
		case RepairPackage.EXTERNAL_SHORTCUT_RULE__DELETIONS:
			getDeletions().clear();
			getDeletions().addAll((Collection<? extends TGGRuleElement>) newValue);
			return;
		case RepairPackage.EXTERNAL_SHORTCUT_RULE__CREATIONS:
			getCreations().clear();
			getCreations().addAll((Collection<? extends TGGRuleElement>) newValue);
			return;
		case RepairPackage.EXTERNAL_SHORTCUT_RULE__UNBOUND_SRC_CONTEXT:
			getUnboundSrcContext().clear();
			getUnboundSrcContext().addAll((Collection<? extends TGGRuleElement>) newValue);
			return;
		case RepairPackage.EXTERNAL_SHORTCUT_RULE__UNBOUND_TRG_CONTEXT:
			getUnboundTrgContext().clear();
			getUnboundTrgContext().addAll((Collection<? extends TGGRuleElement>) newValue);
			return;
		case RepairPackage.EXTERNAL_SHORTCUT_RULE__MAPPING:
			getMapping().clear();
			getMapping().addAll((Collection<? extends TGGRuleElementMapping>) newValue);
			return;
		case RepairPackage.EXTERNAL_SHORTCUT_RULE__NAME:
			setName((String) newValue);
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
		case RepairPackage.EXTERNAL_SHORTCUT_RULE__SOURCE_RULE:
			setSourceRule((TGGRule) null);
			return;
		case RepairPackage.EXTERNAL_SHORTCUT_RULE__TARGET_RULE:
			setTargetRule((TGGRule) null);
			return;
		case RepairPackage.EXTERNAL_SHORTCUT_RULE__DELETIONS:
			getDeletions().clear();
			return;
		case RepairPackage.EXTERNAL_SHORTCUT_RULE__CREATIONS:
			getCreations().clear();
			return;
		case RepairPackage.EXTERNAL_SHORTCUT_RULE__UNBOUND_SRC_CONTEXT:
			getUnboundSrcContext().clear();
			return;
		case RepairPackage.EXTERNAL_SHORTCUT_RULE__UNBOUND_TRG_CONTEXT:
			getUnboundTrgContext().clear();
			return;
		case RepairPackage.EXTERNAL_SHORTCUT_RULE__MAPPING:
			getMapping().clear();
			return;
		case RepairPackage.EXTERNAL_SHORTCUT_RULE__NAME:
			setName(NAME_EDEFAULT);
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
		case RepairPackage.EXTERNAL_SHORTCUT_RULE__SOURCE_RULE:
			return sourceRule != null;
		case RepairPackage.EXTERNAL_SHORTCUT_RULE__TARGET_RULE:
			return targetRule != null;
		case RepairPackage.EXTERNAL_SHORTCUT_RULE__DELETIONS:
			return deletions != null && !deletions.isEmpty();
		case RepairPackage.EXTERNAL_SHORTCUT_RULE__CREATIONS:
			return creations != null && !creations.isEmpty();
		case RepairPackage.EXTERNAL_SHORTCUT_RULE__UNBOUND_SRC_CONTEXT:
			return unboundSrcContext != null && !unboundSrcContext.isEmpty();
		case RepairPackage.EXTERNAL_SHORTCUT_RULE__UNBOUND_TRG_CONTEXT:
			return unboundTrgContext != null && !unboundTrgContext.isEmpty();
		case RepairPackage.EXTERNAL_SHORTCUT_RULE__MAPPING:
			return mapping != null && !mapping.isEmpty();
		case RepairPackage.EXTERNAL_SHORTCUT_RULE__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
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
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} //ExternalShortcutRuleImpl
