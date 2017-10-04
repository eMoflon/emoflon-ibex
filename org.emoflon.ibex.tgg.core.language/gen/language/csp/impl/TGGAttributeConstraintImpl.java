/**
 */
package language.csp.impl;

import java.util.Collection;

import language.basic.expressions.TGGParamValue;

import language.csp.CspPackage;
import language.csp.TGGAttributeConstraint;

import language.csp.definition.TGGAttributeConstraintDefinition;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TGG Attribute Constraint</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link language.csp.impl.TGGAttributeConstraintImpl#getDefinition <em>Definition</em>}</li>
 *   <li>{@link language.csp.impl.TGGAttributeConstraintImpl#getParameters <em>Parameters</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TGGAttributeConstraintImpl extends EObjectImpl implements TGGAttributeConstraint {
	/**
	 * The cached value of the '{@link #getDefinition() <em>Definition</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefinition()
	 * @generated
	 * @ordered
	 */
	protected TGGAttributeConstraintDefinition definition;

	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGParamValue> parameters;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TGGAttributeConstraintImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CspPackage.Literals.TGG_ATTRIBUTE_CONSTRAINT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGAttributeConstraintDefinition getDefinition() {
		if (definition != null && definition.eIsProxy()) {
			InternalEObject oldDefinition = (InternalEObject) definition;
			definition = (TGGAttributeConstraintDefinition) eResolveProxy(oldDefinition);
			if (definition != oldDefinition) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							CspPackage.TGG_ATTRIBUTE_CONSTRAINT__DEFINITION, oldDefinition, definition));
			}
		}
		return definition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGAttributeConstraintDefinition basicGetDefinition() {
		return definition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDefinition(TGGAttributeConstraintDefinition newDefinition) {
		TGGAttributeConstraintDefinition oldDefinition = definition;
		definition = newDefinition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CspPackage.TGG_ATTRIBUTE_CONSTRAINT__DEFINITION,
					oldDefinition, definition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TGGParamValue> getParameters() {
		if (parameters == null) {
			parameters = new EObjectResolvingEList<TGGParamValue>(TGGParamValue.class, this,
					CspPackage.TGG_ATTRIBUTE_CONSTRAINT__PARAMETERS);
		}
		return parameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case CspPackage.TGG_ATTRIBUTE_CONSTRAINT__DEFINITION:
			if (resolve)
				return getDefinition();
			return basicGetDefinition();
		case CspPackage.TGG_ATTRIBUTE_CONSTRAINT__PARAMETERS:
			return getParameters();
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
		case CspPackage.TGG_ATTRIBUTE_CONSTRAINT__DEFINITION:
			setDefinition((TGGAttributeConstraintDefinition) newValue);
			return;
		case CspPackage.TGG_ATTRIBUTE_CONSTRAINT__PARAMETERS:
			getParameters().clear();
			getParameters().addAll((Collection<? extends TGGParamValue>) newValue);
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
		case CspPackage.TGG_ATTRIBUTE_CONSTRAINT__DEFINITION:
			setDefinition((TGGAttributeConstraintDefinition) null);
			return;
		case CspPackage.TGG_ATTRIBUTE_CONSTRAINT__PARAMETERS:
			getParameters().clear();
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
		case CspPackage.TGG_ATTRIBUTE_CONSTRAINT__DEFINITION:
			return definition != null;
		case CspPackage.TGG_ATTRIBUTE_CONSTRAINT__PARAMETERS:
			return parameters != null && !parameters.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //TGGAttributeConstraintImpl
