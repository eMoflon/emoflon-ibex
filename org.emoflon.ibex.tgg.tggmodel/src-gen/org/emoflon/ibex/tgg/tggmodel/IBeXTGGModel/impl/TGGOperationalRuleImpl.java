/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRuleDelta;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.OperationalisationMode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TGG Operational Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGOperationalRuleImpl#getOperationalisationMode <em>Operationalisation Mode</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGOperationalRuleImpl#getToBeMarked <em>To Be Marked</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGOperationalRuleImpl#getAlreadyMarked <em>Already Marked</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGOperationalRuleImpl#getTggRule <em>Tgg Rule</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TGGOperationalRuleImpl extends TGGRuleImpl implements TGGOperationalRule {
	/**
	 * The default value of the '{@link #getOperationalisationMode() <em>Operationalisation Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperationalisationMode()
	 * @generated
	 * @ordered
	 */
	protected static final OperationalisationMode OPERATIONALISATION_MODE_EDEFAULT = OperationalisationMode.GENERATE;

	/**
	 * The cached value of the '{@link #getOperationalisationMode() <em>Operationalisation Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperationalisationMode()
	 * @generated
	 * @ordered
	 */
	protected OperationalisationMode operationalisationMode = OPERATIONALISATION_MODE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getToBeMarked() <em>To Be Marked</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getToBeMarked()
	 * @generated
	 * @ordered
	 */
	protected IBeXRuleDelta toBeMarked;

	/**
	 * The cached value of the '{@link #getAlreadyMarked() <em>Already Marked</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlreadyMarked()
	 * @generated
	 * @ordered
	 */
	protected IBeXRuleDelta alreadyMarked;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TGGOperationalRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXTGGModelPackage.Literals.TGG_OPERATIONAL_RULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OperationalisationMode getOperationalisationMode() {
		return operationalisationMode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOperationalisationMode(OperationalisationMode newOperationalisationMode) {
		OperationalisationMode oldOperationalisationMode = operationalisationMode;
		operationalisationMode = newOperationalisationMode == null ? OPERATIONALISATION_MODE_EDEFAULT
				: newOperationalisationMode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__OPERATIONALISATION_MODE, oldOperationalisationMode,
					operationalisationMode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXRuleDelta getToBeMarked() {
		return toBeMarked;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetToBeMarked(IBeXRuleDelta newToBeMarked, NotificationChain msgs) {
		IBeXRuleDelta oldToBeMarked = toBeMarked;
		toBeMarked = newToBeMarked;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__TO_BE_MARKED, oldToBeMarked, newToBeMarked);
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
	public void setToBeMarked(IBeXRuleDelta newToBeMarked) {
		if (newToBeMarked != toBeMarked) {
			NotificationChain msgs = null;
			if (toBeMarked != null)
				msgs = ((InternalEObject) toBeMarked).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__TO_BE_MARKED, null, msgs);
			if (newToBeMarked != null)
				msgs = ((InternalEObject) newToBeMarked).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__TO_BE_MARKED, null, msgs);
			msgs = basicSetToBeMarked(newToBeMarked, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__TO_BE_MARKED, newToBeMarked, newToBeMarked));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXRuleDelta getAlreadyMarked() {
		return alreadyMarked;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAlreadyMarked(IBeXRuleDelta newAlreadyMarked, NotificationChain msgs) {
		IBeXRuleDelta oldAlreadyMarked = alreadyMarked;
		alreadyMarked = newAlreadyMarked;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__ALREADY_MARKED, oldAlreadyMarked, newAlreadyMarked);
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
	public void setAlreadyMarked(IBeXRuleDelta newAlreadyMarked) {
		if (newAlreadyMarked != alreadyMarked) {
			NotificationChain msgs = null;
			if (alreadyMarked != null)
				msgs = ((InternalEObject) alreadyMarked).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__ALREADY_MARKED, null, msgs);
			if (newAlreadyMarked != null)
				msgs = ((InternalEObject) newAlreadyMarked).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__ALREADY_MARKED, null, msgs);
			msgs = basicSetAlreadyMarked(newAlreadyMarked, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__ALREADY_MARKED, newAlreadyMarked, newAlreadyMarked));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGRule getTggRule() {
		if (eContainerFeatureID() != IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__TGG_RULE)
			return null;
		return (TGGRule) eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTggRule(TGGRule newTggRule, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject) newTggRule, IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__TGG_RULE,
				msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTggRule(TGGRule newTggRule) {
		if (newTggRule != eInternalContainer()
				|| (eContainerFeatureID() != IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__TGG_RULE
						&& newTggRule != null)) {
			if (EcoreUtil.isAncestor(this, newTggRule))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newTggRule != null)
				msgs = ((InternalEObject) newTggRule).eInverseAdd(this,
						IBeXTGGModelPackage.TGG_RULE__OPERATIONALISATIONS, TGGRule.class, msgs);
			msgs = basicSetTggRule(newTggRule, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__TGG_RULE,
					newTggRule, newTggRule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__TGG_RULE:
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			return basicSetTggRule((TGGRule) otherEnd, msgs);
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
		case IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__TO_BE_MARKED:
			return basicSetToBeMarked(null, msgs);
		case IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__ALREADY_MARKED:
			return basicSetAlreadyMarked(null, msgs);
		case IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__TGG_RULE:
			return basicSetTggRule(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
		case IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__TGG_RULE:
			return eInternalContainer().eInverseRemove(this, IBeXTGGModelPackage.TGG_RULE__OPERATIONALISATIONS,
					TGGRule.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__OPERATIONALISATION_MODE:
			return getOperationalisationMode();
		case IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__TO_BE_MARKED:
			return getToBeMarked();
		case IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__ALREADY_MARKED:
			return getAlreadyMarked();
		case IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__TGG_RULE:
			return getTggRule();
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
		case IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__OPERATIONALISATION_MODE:
			setOperationalisationMode((OperationalisationMode) newValue);
			return;
		case IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__TO_BE_MARKED:
			setToBeMarked((IBeXRuleDelta) newValue);
			return;
		case IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__ALREADY_MARKED:
			setAlreadyMarked((IBeXRuleDelta) newValue);
			return;
		case IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__TGG_RULE:
			setTggRule((TGGRule) newValue);
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
		case IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__OPERATIONALISATION_MODE:
			setOperationalisationMode(OPERATIONALISATION_MODE_EDEFAULT);
			return;
		case IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__TO_BE_MARKED:
			setToBeMarked((IBeXRuleDelta) null);
			return;
		case IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__ALREADY_MARKED:
			setAlreadyMarked((IBeXRuleDelta) null);
			return;
		case IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__TGG_RULE:
			setTggRule((TGGRule) null);
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
		case IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__OPERATIONALISATION_MODE:
			return operationalisationMode != OPERATIONALISATION_MODE_EDEFAULT;
		case IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__TO_BE_MARKED:
			return toBeMarked != null;
		case IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__ALREADY_MARKED:
			return alreadyMarked != null;
		case IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__TGG_RULE:
			return getTggRule() != null;
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

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (operationalisationMode: ");
		result.append(operationalisationMode);
		result.append(')');
		return result.toString();
	}

} //TGGOperationalRuleImpl
