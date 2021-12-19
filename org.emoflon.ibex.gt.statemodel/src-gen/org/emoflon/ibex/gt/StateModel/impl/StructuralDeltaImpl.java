/**
 */
package org.emoflon.ibex.gt.StateModel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.emoflon.ibex.gt.StateModel.Link;
import org.emoflon.ibex.gt.StateModel.StateModelPackage;
import org.emoflon.ibex.gt.StateModel.StructuralDelta;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Structural Delta</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.impl.StructuralDeltaImpl#getCreatedObjects <em>Created Objects</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.impl.StructuralDeltaImpl#getDeletedObjects <em>Deleted Objects</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.impl.StructuralDeltaImpl#getCreatedLinks <em>Created Links</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.impl.StructuralDeltaImpl#getDeletedLinks <em>Deleted Links</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.impl.StructuralDeltaImpl#getDeletedRootLevelObjects <em>Deleted Root Level Objects</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.impl.StructuralDeltaImpl#getResource2EObjectContainment <em>Resource2 EObject Containment</em>}</li>
 * </ul>
 *
 * @generated
 */
public class StructuralDeltaImpl extends MinimalEObjectImpl.Container implements StructuralDelta {
	/**
	 * The cached value of the '{@link #getCreatedObjects() <em>Created Objects</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreatedObjects()
	 * @generated
	 * @ordered
	 */
	protected EList<EObject> createdObjects;

	/**
	 * The cached value of the '{@link #getDeletedObjects() <em>Deleted Objects</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeletedObjects()
	 * @generated
	 * @ordered
	 */
	protected EList<EObject> deletedObjects;

	/**
	 * The cached value of the '{@link #getCreatedLinks() <em>Created Links</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreatedLinks()
	 * @generated
	 * @ordered
	 */
	protected EList<Link> createdLinks;

	/**
	 * The cached value of the '{@link #getDeletedLinks() <em>Deleted Links</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeletedLinks()
	 * @generated
	 * @ordered
	 */
	protected EList<Link> deletedLinks;

	/**
	 * The cached value of the '{@link #getDeletedRootLevelObjects() <em>Deleted Root Level Objects</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeletedRootLevelObjects()
	 * @generated
	 * @ordered
	 */
	protected EList<EObject> deletedRootLevelObjects;

	/**
	 * The cached value of the '{@link #getResource2EObjectContainment() <em>Resource2 EObject Containment</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResource2EObjectContainment()
	 * @generated
	 * @ordered
	 */
	protected EList<EObject> resource2EObjectContainment;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StructuralDeltaImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StateModelPackage.Literals.STRUCTURAL_DELTA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<EObject> getCreatedObjects() {
		if (createdObjects == null) {
			createdObjects = new EObjectResolvingEList<EObject>(EObject.class, this,
					StateModelPackage.STRUCTURAL_DELTA__CREATED_OBJECTS);
		}
		return createdObjects;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<EObject> getDeletedObjects() {
		if (deletedObjects == null) {
			deletedObjects = new EObjectResolvingEList<EObject>(EObject.class, this,
					StateModelPackage.STRUCTURAL_DELTA__DELETED_OBJECTS);
		}
		return deletedObjects;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Link> getCreatedLinks() {
		if (createdLinks == null) {
			createdLinks = new EObjectContainmentEList<Link>(Link.class, this,
					StateModelPackage.STRUCTURAL_DELTA__CREATED_LINKS);
		}
		return createdLinks;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Link> getDeletedLinks() {
		if (deletedLinks == null) {
			deletedLinks = new EObjectContainmentEList<Link>(Link.class, this,
					StateModelPackage.STRUCTURAL_DELTA__DELETED_LINKS);
		}
		return deletedLinks;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<EObject> getDeletedRootLevelObjects() {
		if (deletedRootLevelObjects == null) {
			deletedRootLevelObjects = new EObjectResolvingEList<EObject>(EObject.class, this,
					StateModelPackage.STRUCTURAL_DELTA__DELETED_ROOT_LEVEL_OBJECTS);
		}
		return deletedRootLevelObjects;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<EObject> getResource2EObjectContainment() {
		if (resource2EObjectContainment == null) {
			resource2EObjectContainment = new EObjectResolvingEList<EObject>(EObject.class, this,
					StateModelPackage.STRUCTURAL_DELTA__RESOURCE2_EOBJECT_CONTAINMENT);
		}
		return resource2EObjectContainment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case StateModelPackage.STRUCTURAL_DELTA__CREATED_LINKS:
			return ((InternalEList<?>) getCreatedLinks()).basicRemove(otherEnd, msgs);
		case StateModelPackage.STRUCTURAL_DELTA__DELETED_LINKS:
			return ((InternalEList<?>) getDeletedLinks()).basicRemove(otherEnd, msgs);
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
		case StateModelPackage.STRUCTURAL_DELTA__CREATED_OBJECTS:
			return getCreatedObjects();
		case StateModelPackage.STRUCTURAL_DELTA__DELETED_OBJECTS:
			return getDeletedObjects();
		case StateModelPackage.STRUCTURAL_DELTA__CREATED_LINKS:
			return getCreatedLinks();
		case StateModelPackage.STRUCTURAL_DELTA__DELETED_LINKS:
			return getDeletedLinks();
		case StateModelPackage.STRUCTURAL_DELTA__DELETED_ROOT_LEVEL_OBJECTS:
			return getDeletedRootLevelObjects();
		case StateModelPackage.STRUCTURAL_DELTA__RESOURCE2_EOBJECT_CONTAINMENT:
			return getResource2EObjectContainment();
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
		case StateModelPackage.STRUCTURAL_DELTA__CREATED_OBJECTS:
			getCreatedObjects().clear();
			getCreatedObjects().addAll((Collection<? extends EObject>) newValue);
			return;
		case StateModelPackage.STRUCTURAL_DELTA__DELETED_OBJECTS:
			getDeletedObjects().clear();
			getDeletedObjects().addAll((Collection<? extends EObject>) newValue);
			return;
		case StateModelPackage.STRUCTURAL_DELTA__CREATED_LINKS:
			getCreatedLinks().clear();
			getCreatedLinks().addAll((Collection<? extends Link>) newValue);
			return;
		case StateModelPackage.STRUCTURAL_DELTA__DELETED_LINKS:
			getDeletedLinks().clear();
			getDeletedLinks().addAll((Collection<? extends Link>) newValue);
			return;
		case StateModelPackage.STRUCTURAL_DELTA__DELETED_ROOT_LEVEL_OBJECTS:
			getDeletedRootLevelObjects().clear();
			getDeletedRootLevelObjects().addAll((Collection<? extends EObject>) newValue);
			return;
		case StateModelPackage.STRUCTURAL_DELTA__RESOURCE2_EOBJECT_CONTAINMENT:
			getResource2EObjectContainment().clear();
			getResource2EObjectContainment().addAll((Collection<? extends EObject>) newValue);
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
		case StateModelPackage.STRUCTURAL_DELTA__CREATED_OBJECTS:
			getCreatedObjects().clear();
			return;
		case StateModelPackage.STRUCTURAL_DELTA__DELETED_OBJECTS:
			getDeletedObjects().clear();
			return;
		case StateModelPackage.STRUCTURAL_DELTA__CREATED_LINKS:
			getCreatedLinks().clear();
			return;
		case StateModelPackage.STRUCTURAL_DELTA__DELETED_LINKS:
			getDeletedLinks().clear();
			return;
		case StateModelPackage.STRUCTURAL_DELTA__DELETED_ROOT_LEVEL_OBJECTS:
			getDeletedRootLevelObjects().clear();
			return;
		case StateModelPackage.STRUCTURAL_DELTA__RESOURCE2_EOBJECT_CONTAINMENT:
			getResource2EObjectContainment().clear();
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
		case StateModelPackage.STRUCTURAL_DELTA__CREATED_OBJECTS:
			return createdObjects != null && !createdObjects.isEmpty();
		case StateModelPackage.STRUCTURAL_DELTA__DELETED_OBJECTS:
			return deletedObjects != null && !deletedObjects.isEmpty();
		case StateModelPackage.STRUCTURAL_DELTA__CREATED_LINKS:
			return createdLinks != null && !createdLinks.isEmpty();
		case StateModelPackage.STRUCTURAL_DELTA__DELETED_LINKS:
			return deletedLinks != null && !deletedLinks.isEmpty();
		case StateModelPackage.STRUCTURAL_DELTA__DELETED_ROOT_LEVEL_OBJECTS:
			return deletedRootLevelObjects != null && !deletedRootLevelObjects.isEmpty();
		case StateModelPackage.STRUCTURAL_DELTA__RESOURCE2_EOBJECT_CONTAINMENT:
			return resource2EObjectContainment != null && !resource2EObjectContainment.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //StructuralDeltaImpl
