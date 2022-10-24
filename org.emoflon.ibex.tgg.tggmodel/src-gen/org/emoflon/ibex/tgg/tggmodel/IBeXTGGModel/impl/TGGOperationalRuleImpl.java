/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

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
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case IBeXTGGModelPackage.TGG_OPERATIONAL_RULE__OPERATIONALISATION_MODE:
			return getOperationalisationMode();
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
