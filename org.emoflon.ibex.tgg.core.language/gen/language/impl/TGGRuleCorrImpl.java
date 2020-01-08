/**
 */
package language.impl;

import language.LanguagePackage;
import language.TGGRuleCorr;
import language.TGGRuleNode;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TGG Rule Corr</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link language.impl.TGGRuleCorrImpl#getSource <em>Source</em>}</li>
 *   <li>{@link language.impl.TGGRuleCorrImpl#getTarget <em>Target</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TGGRuleCorrImpl extends TGGRuleNodeImpl implements TGGRuleCorr {
	/**
	 * The cached value of the '{@link #getSource() <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected TGGRuleNode source;

	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected TGGRuleNode target;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TGGRuleCorrImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LanguagePackage.Literals.TGG_RULE_CORR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TGGRuleNode getSource() {
		if (source != null && source.eIsProxy()) {
			InternalEObject oldSource = (InternalEObject) source;
			source = (TGGRuleNode) eResolveProxy(oldSource);
			if (source != oldSource) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LanguagePackage.TGG_RULE_CORR__SOURCE,
							oldSource, source));
			}
		}
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGRuleNode basicGetSource() {
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSource(TGGRuleNode newSource, NotificationChain msgs) {
		TGGRuleNode oldSource = source;
		source = newSource;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					LanguagePackage.TGG_RULE_CORR__SOURCE, oldSource, newSource);
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
	@Override
	public void setSource(TGGRuleNode newSource) {
		if (newSource != source) {
			NotificationChain msgs = null;
			if (source != null)
				msgs = ((InternalEObject) source).eInverseRemove(this,
						LanguagePackage.TGG_RULE_NODE__INCOMING_CORRS_SOURCE, TGGRuleNode.class, msgs);
			if (newSource != null)
				msgs = ((InternalEObject) newSource).eInverseAdd(this,
						LanguagePackage.TGG_RULE_NODE__INCOMING_CORRS_SOURCE, TGGRuleNode.class, msgs);
			msgs = basicSetSource(newSource, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LanguagePackage.TGG_RULE_CORR__SOURCE, newSource,
					newSource));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TGGRuleNode getTarget() {
		if (target != null && target.eIsProxy()) {
			InternalEObject oldTarget = (InternalEObject) target;
			target = (TGGRuleNode) eResolveProxy(oldTarget);
			if (target != oldTarget) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LanguagePackage.TGG_RULE_CORR__TARGET,
							oldTarget, target));
			}
		}
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGRuleNode basicGetTarget() {
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTarget(TGGRuleNode newTarget, NotificationChain msgs) {
		TGGRuleNode oldTarget = target;
		target = newTarget;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					LanguagePackage.TGG_RULE_CORR__TARGET, oldTarget, newTarget);
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
	@Override
	public void setTarget(TGGRuleNode newTarget) {
		if (newTarget != target) {
			NotificationChain msgs = null;
			if (target != null)
				msgs = ((InternalEObject) target).eInverseRemove(this,
						LanguagePackage.TGG_RULE_NODE__INCOMING_CORRS_TARGET, TGGRuleNode.class, msgs);
			if (newTarget != null)
				msgs = ((InternalEObject) newTarget).eInverseAdd(this,
						LanguagePackage.TGG_RULE_NODE__INCOMING_CORRS_TARGET, TGGRuleNode.class, msgs);
			msgs = basicSetTarget(newTarget, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LanguagePackage.TGG_RULE_CORR__TARGET, newTarget,
					newTarget));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case LanguagePackage.TGG_RULE_CORR__SOURCE:
			if (source != null)
				msgs = ((InternalEObject) source).eInverseRemove(this,
						LanguagePackage.TGG_RULE_NODE__INCOMING_CORRS_SOURCE, TGGRuleNode.class, msgs);
			return basicSetSource((TGGRuleNode) otherEnd, msgs);
		case LanguagePackage.TGG_RULE_CORR__TARGET:
			if (target != null)
				msgs = ((InternalEObject) target).eInverseRemove(this,
						LanguagePackage.TGG_RULE_NODE__INCOMING_CORRS_TARGET, TGGRuleNode.class, msgs);
			return basicSetTarget((TGGRuleNode) otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case LanguagePackage.TGG_RULE_CORR__SOURCE:
			return basicSetSource(null, msgs);
		case LanguagePackage.TGG_RULE_CORR__TARGET:
			return basicSetTarget(null, msgs);
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
		case LanguagePackage.TGG_RULE_CORR__SOURCE:
			if (resolve)
				return getSource();
			return basicGetSource();
		case LanguagePackage.TGG_RULE_CORR__TARGET:
			if (resolve)
				return getTarget();
			return basicGetTarget();
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
		case LanguagePackage.TGG_RULE_CORR__SOURCE:
			setSource((TGGRuleNode) newValue);
			return;
		case LanguagePackage.TGG_RULE_CORR__TARGET:
			setTarget((TGGRuleNode) newValue);
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
		case LanguagePackage.TGG_RULE_CORR__SOURCE:
			setSource((TGGRuleNode) null);
			return;
		case LanguagePackage.TGG_RULE_CORR__TARGET:
			setTarget((TGGRuleNode) null);
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
		case LanguagePackage.TGG_RULE_CORR__SOURCE:
			return source != null;
		case LanguagePackage.TGG_RULE_CORR__TARGET:
			return target != null;
		}
		return super.eIsSet(featureID);
	}

} //TGGRuleCorrImpl
