/**
 */
package org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.TGGRuntimeModelPackage;
import org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.TempContainer;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Temp Container</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.impl.TempContainerImpl#getObjects <em>Objects</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TempContainerImpl extends MinimalEObjectImpl.Container implements TempContainer {
	/**
	 * The cached value of the '{@link #getObjects() <em>Objects</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjects()
	 * @generated
	 * @ordered
	 */
	protected EList<EObject> objects;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TempContainerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TGGRuntimeModelPackage.Literals.TEMP_CONTAINER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EObject> getObjects() {
		if (objects == null) {
			objects = new EObjectContainmentEList<EObject>(EObject.class, this,
					TGGRuntimeModelPackage.TEMP_CONTAINER__OBJECTS);
		}
		return objects;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case TGGRuntimeModelPackage.TEMP_CONTAINER__OBJECTS:
			return ((InternalEList<?>) getObjects()).basicRemove(otherEnd, msgs);
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
		case TGGRuntimeModelPackage.TEMP_CONTAINER__OBJECTS:
			return getObjects();
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
		case TGGRuntimeModelPackage.TEMP_CONTAINER__OBJECTS:
			getObjects().clear();
			getObjects().addAll((Collection<? extends EObject>) newValue);
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
		case TGGRuntimeModelPackage.TEMP_CONTAINER__OBJECTS:
			getObjects().clear();
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
		case TGGRuntimeModelPackage.TEMP_CONTAINER__OBJECTS:
			return objects != null && !objects.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //TempContainerImpl
