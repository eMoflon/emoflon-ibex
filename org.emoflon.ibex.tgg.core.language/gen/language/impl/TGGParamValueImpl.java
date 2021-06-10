/**
 */
package language.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import language.LanguagePackage;
import language.TGGAttributeConstraintParameterDefinition;
import language.TGGParamValue;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TGG Param Value</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link language.impl.TGGParamValueImpl#getParameterDefinition <em>Parameter Definition</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TGGParamValueImpl extends EObjectImpl implements TGGParamValue {
	/**
	 * The cached value of the '{@link #getParameterDefinition() <em>Parameter Definition</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameterDefinition()
	 * @generated
	 * @ordered
	 */
	protected TGGAttributeConstraintParameterDefinition parameterDefinition;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TGGParamValueImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LanguagePackage.Literals.TGG_PARAM_VALUE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TGGAttributeConstraintParameterDefinition getParameterDefinition() {
		if (parameterDefinition != null && parameterDefinition.eIsProxy()) {
			InternalEObject oldParameterDefinition = (InternalEObject) parameterDefinition;
			parameterDefinition = (TGGAttributeConstraintParameterDefinition) eResolveProxy(oldParameterDefinition);
			if (parameterDefinition != oldParameterDefinition) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LanguagePackage.TGG_PARAM_VALUE__PARAMETER_DEFINITION,
							oldParameterDefinition, parameterDefinition));
			}
		}
		return parameterDefinition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGAttributeConstraintParameterDefinition basicGetParameterDefinition() {
		return parameterDefinition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setParameterDefinition(TGGAttributeConstraintParameterDefinition newParameterDefinition) {
		TGGAttributeConstraintParameterDefinition oldParameterDefinition = parameterDefinition;
		parameterDefinition = newParameterDefinition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LanguagePackage.TGG_PARAM_VALUE__PARAMETER_DEFINITION, oldParameterDefinition,
					parameterDefinition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case LanguagePackage.TGG_PARAM_VALUE__PARAMETER_DEFINITION:
			if (resolve)
				return getParameterDefinition();
			return basicGetParameterDefinition();
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
		case LanguagePackage.TGG_PARAM_VALUE__PARAMETER_DEFINITION:
			setParameterDefinition((TGGAttributeConstraintParameterDefinition) newValue);
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
		case LanguagePackage.TGG_PARAM_VALUE__PARAMETER_DEFINITION:
			setParameterDefinition((TGGAttributeConstraintParameterDefinition) null);
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
		case LanguagePackage.TGG_PARAM_VALUE__PARAMETER_DEFINITION:
			return parameterDefinition != null;
		}
		return super.eIsSet(featureID);
	}

} //TGGParamValueImpl
