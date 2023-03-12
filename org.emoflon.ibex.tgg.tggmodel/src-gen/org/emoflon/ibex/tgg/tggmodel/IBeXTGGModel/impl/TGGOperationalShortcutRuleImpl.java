/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRuleDelta;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.OperationalisationMode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalShortcutRule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TGG Operational Shortcut Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGOperationalShortcutRuleImpl#getOperationalisationMode <em>Operationalisation Mode</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGOperationalShortcutRuleImpl#getToBeMarked <em>To Be Marked</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGOperationalShortcutRuleImpl#getAlreadyMarked <em>Already Marked</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TGGOperationalShortcutRuleImpl extends TGGShortcutRuleImpl implements TGGOperationalShortcutRule {
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
	protected TGGOperationalShortcutRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXTGGModelPackage.Literals.TGG_OPERATIONAL_SHORTCUT_RULE;
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
		operationalisationMode = newOperationalisationMode == null ? OPERATIONALISATION_MODE_EDEFAULT : newOperationalisationMode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXTGGModelPackage.TGG_OPERATIONAL_SHORTCUT_RULE__OPERATIONALISATION_MODE,
					oldOperationalisationMode, operationalisationMode));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IBeXTGGModelPackage.TGG_OPERATIONAL_SHORTCUT_RULE__TO_BE_MARKED,
					oldToBeMarked, newToBeMarked);
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
						EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_OPERATIONAL_SHORTCUT_RULE__TO_BE_MARKED, null, msgs);
			if (newToBeMarked != null)
				msgs = ((InternalEObject) newToBeMarked).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_OPERATIONAL_SHORTCUT_RULE__TO_BE_MARKED, null, msgs);
			msgs = basicSetToBeMarked(newToBeMarked, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXTGGModelPackage.TGG_OPERATIONAL_SHORTCUT_RULE__TO_BE_MARKED, newToBeMarked,
					newToBeMarked));
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
					IBeXTGGModelPackage.TGG_OPERATIONAL_SHORTCUT_RULE__ALREADY_MARKED, oldAlreadyMarked, newAlreadyMarked);
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
						EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_OPERATIONAL_SHORTCUT_RULE__ALREADY_MARKED, null, msgs);
			if (newAlreadyMarked != null)
				msgs = ((InternalEObject) newAlreadyMarked).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_OPERATIONAL_SHORTCUT_RULE__ALREADY_MARKED, null, msgs);
			msgs = basicSetAlreadyMarked(newAlreadyMarked, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXTGGModelPackage.TGG_OPERATIONAL_SHORTCUT_RULE__ALREADY_MARKED, newAlreadyMarked,
					newAlreadyMarked));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IBeXTGGModelPackage.TGG_OPERATIONAL_SHORTCUT_RULE__TO_BE_MARKED:
				return basicSetToBeMarked(null, msgs);
			case IBeXTGGModelPackage.TGG_OPERATIONAL_SHORTCUT_RULE__ALREADY_MARKED:
				return basicSetAlreadyMarked(null, msgs);
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
			case IBeXTGGModelPackage.TGG_OPERATIONAL_SHORTCUT_RULE__OPERATIONALISATION_MODE:
				return getOperationalisationMode();
			case IBeXTGGModelPackage.TGG_OPERATIONAL_SHORTCUT_RULE__TO_BE_MARKED:
				return getToBeMarked();
			case IBeXTGGModelPackage.TGG_OPERATIONAL_SHORTCUT_RULE__ALREADY_MARKED:
				return getAlreadyMarked();
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
			case IBeXTGGModelPackage.TGG_OPERATIONAL_SHORTCUT_RULE__OPERATIONALISATION_MODE:
				setOperationalisationMode((OperationalisationMode) newValue);
				return;
			case IBeXTGGModelPackage.TGG_OPERATIONAL_SHORTCUT_RULE__TO_BE_MARKED:
				setToBeMarked((IBeXRuleDelta) newValue);
				return;
			case IBeXTGGModelPackage.TGG_OPERATIONAL_SHORTCUT_RULE__ALREADY_MARKED:
				setAlreadyMarked((IBeXRuleDelta) newValue);
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
			case IBeXTGGModelPackage.TGG_OPERATIONAL_SHORTCUT_RULE__OPERATIONALISATION_MODE:
				setOperationalisationMode(OPERATIONALISATION_MODE_EDEFAULT);
				return;
			case IBeXTGGModelPackage.TGG_OPERATIONAL_SHORTCUT_RULE__TO_BE_MARKED:
				setToBeMarked((IBeXRuleDelta) null);
				return;
			case IBeXTGGModelPackage.TGG_OPERATIONAL_SHORTCUT_RULE__ALREADY_MARKED:
				setAlreadyMarked((IBeXRuleDelta) null);
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
			case IBeXTGGModelPackage.TGG_OPERATIONAL_SHORTCUT_RULE__OPERATIONALISATION_MODE:
				return operationalisationMode != OPERATIONALISATION_MODE_EDEFAULT;
			case IBeXTGGModelPackage.TGG_OPERATIONAL_SHORTCUT_RULE__TO_BE_MARKED:
				return toBeMarked != null;
			case IBeXTGGModelPackage.TGG_OPERATIONAL_SHORTCUT_RULE__ALREADY_MARKED:
				return alreadyMarked != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == TGGOperationalRule.class) {
			switch (derivedFeatureID) {
				case IBeXTGGModelPackage.TGG_OPERATIONAL_SHORTCUT_RULE__OPERATIONALISATION_MODE:
					return IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__OPERATIONALISATION_MODE;
				case IBeXTGGModelPackage.TGG_OPERATIONAL_SHORTCUT_RULE__TO_BE_MARKED:
					return IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__TO_BE_MARKED;
				case IBeXTGGModelPackage.TGG_OPERATIONAL_SHORTCUT_RULE__ALREADY_MARKED:
					return IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__ALREADY_MARKED;
				default:
					return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == TGGOperationalRule.class) {
			switch (baseFeatureID) {
				case IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__OPERATIONALISATION_MODE:
					return IBeXTGGModelPackage.TGG_OPERATIONAL_SHORTCUT_RULE__OPERATIONALISATION_MODE;
				case IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__TO_BE_MARKED:
					return IBeXTGGModelPackage.TGG_OPERATIONAL_SHORTCUT_RULE__TO_BE_MARKED;
				case IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__ALREADY_MARKED:
					return IBeXTGGModelPackage.TGG_OPERATIONAL_SHORTCUT_RULE__ALREADY_MARKED;
				default:
					return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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

} //TGGOperationalShortcutRuleImpl
