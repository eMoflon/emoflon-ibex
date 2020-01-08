/**
 */
package language.repair.impl;

import language.TGGRuleElement;

import language.repair.RepairPackage;
import language.repair.TGGRuleElementMapping;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TGG Rule Element Mapping</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link language.repair.impl.TGGRuleElementMappingImpl#getSourceRuleElement <em>Source Rule Element</em>}</li>
 *   <li>{@link language.repair.impl.TGGRuleElementMappingImpl#getTargetRuleElement <em>Target Rule Element</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TGGRuleElementMappingImpl extends EObjectImpl implements TGGRuleElementMapping {
	/**
	 * The cached value of the '{@link #getSourceRuleElement() <em>Source Rule Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceRuleElement()
	 * @generated
	 * @ordered
	 */
	protected TGGRuleElement sourceRuleElement;

	/**
	 * The cached value of the '{@link #getTargetRuleElement() <em>Target Rule Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetRuleElement()
	 * @generated
	 * @ordered
	 */
	protected TGGRuleElement targetRuleElement;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TGGRuleElementMappingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RepairPackage.Literals.TGG_RULE_ELEMENT_MAPPING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TGGRuleElement getSourceRuleElement() {
		if (sourceRuleElement != null && sourceRuleElement.eIsProxy()) {
			InternalEObject oldSourceRuleElement = (InternalEObject) sourceRuleElement;
			sourceRuleElement = (TGGRuleElement) eResolveProxy(oldSourceRuleElement);
			if (sourceRuleElement != oldSourceRuleElement) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							RepairPackage.TGG_RULE_ELEMENT_MAPPING__SOURCE_RULE_ELEMENT, oldSourceRuleElement,
							sourceRuleElement));
			}
		}
		return sourceRuleElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGRuleElement basicGetSourceRuleElement() {
		return sourceRuleElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSourceRuleElement(TGGRuleElement newSourceRuleElement) {
		TGGRuleElement oldSourceRuleElement = sourceRuleElement;
		sourceRuleElement = newSourceRuleElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					RepairPackage.TGG_RULE_ELEMENT_MAPPING__SOURCE_RULE_ELEMENT, oldSourceRuleElement,
					sourceRuleElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TGGRuleElement getTargetRuleElement() {
		if (targetRuleElement != null && targetRuleElement.eIsProxy()) {
			InternalEObject oldTargetRuleElement = (InternalEObject) targetRuleElement;
			targetRuleElement = (TGGRuleElement) eResolveProxy(oldTargetRuleElement);
			if (targetRuleElement != oldTargetRuleElement) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							RepairPackage.TGG_RULE_ELEMENT_MAPPING__TARGET_RULE_ELEMENT, oldTargetRuleElement,
							targetRuleElement));
			}
		}
		return targetRuleElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGRuleElement basicGetTargetRuleElement() {
		return targetRuleElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTargetRuleElement(TGGRuleElement newTargetRuleElement) {
		TGGRuleElement oldTargetRuleElement = targetRuleElement;
		targetRuleElement = newTargetRuleElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					RepairPackage.TGG_RULE_ELEMENT_MAPPING__TARGET_RULE_ELEMENT, oldTargetRuleElement,
					targetRuleElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case RepairPackage.TGG_RULE_ELEMENT_MAPPING__SOURCE_RULE_ELEMENT:
			if (resolve)
				return getSourceRuleElement();
			return basicGetSourceRuleElement();
		case RepairPackage.TGG_RULE_ELEMENT_MAPPING__TARGET_RULE_ELEMENT:
			if (resolve)
				return getTargetRuleElement();
			return basicGetTargetRuleElement();
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
		case RepairPackage.TGG_RULE_ELEMENT_MAPPING__SOURCE_RULE_ELEMENT:
			setSourceRuleElement((TGGRuleElement) newValue);
			return;
		case RepairPackage.TGG_RULE_ELEMENT_MAPPING__TARGET_RULE_ELEMENT:
			setTargetRuleElement((TGGRuleElement) newValue);
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
		case RepairPackage.TGG_RULE_ELEMENT_MAPPING__SOURCE_RULE_ELEMENT:
			setSourceRuleElement((TGGRuleElement) null);
			return;
		case RepairPackage.TGG_RULE_ELEMENT_MAPPING__TARGET_RULE_ELEMENT:
			setTargetRuleElement((TGGRuleElement) null);
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
		case RepairPackage.TGG_RULE_ELEMENT_MAPPING__SOURCE_RULE_ELEMENT:
			return sourceRuleElement != null;
		case RepairPackage.TGG_RULE_ELEMENT_MAPPING__TARGET_RULE_ELEMENT:
			return targetRuleElement != null;
		}
		return super.eIsSet(featureID);
	}

} //TGGRuleElementMappingImpl
