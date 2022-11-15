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

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TGG Operational Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGOperationalRuleImpl#getOperationalisationMode <em>Operationalisation Mode</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGOperationalRuleImpl#getMarked <em>Marked</em>}</li>
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
	 * The cached value of the '{@link #getMarked() <em>Marked</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMarked()
	 * @generated
	 * @ordered
	 */
	protected IBeXRuleDelta marked;

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
	public IBeXRuleDelta getMarked() {
		return marked;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMarked(IBeXRuleDelta newMarked, NotificationChain msgs) {
		IBeXRuleDelta oldMarked = marked;
		marked = newMarked;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__MARKED, oldMarked, newMarked);
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
	public void setMarked(IBeXRuleDelta newMarked) {
		if (newMarked != marked) {
			NotificationChain msgs = null;
			if (marked != null)
				msgs = ((InternalEObject) marked).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__MARKED, null, msgs);
			if (newMarked != null)
				msgs = ((InternalEObject) newMarked).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__MARKED, null, msgs);
			msgs = basicSetMarked(newMarked, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__MARKED,
					newMarked, newMarked));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__MARKED:
			return basicSetMarked(null, msgs);
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
		case IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__OPERATIONALISATION_MODE:
			return getOperationalisationMode();
		case IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__MARKED:
			return getMarked();
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
		case IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__MARKED:
			setMarked((IBeXRuleDelta) newValue);
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
		case IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__MARKED:
			setMarked((IBeXRuleDelta) null);
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
		case IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__MARKED:
			return marked != null;
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
