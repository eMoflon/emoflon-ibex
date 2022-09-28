/**
 */
package org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXOperationType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IBe XNode</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXNodeImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXNodeImpl#getOperationType <em>Operation Type</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXNodeImpl#getIncomingEdges <em>Incoming Edges</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXNodeImpl#getOutgoingEdges <em>Outgoing Edges</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IBeXNodeImpl extends IBeXNamedElementImpl implements IBeXNode {
	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected EClass type;

	/**
	 * The default value of the '{@link #getOperationType() <em>Operation Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperationType()
	 * @generated
	 * @ordered
	 */
	protected static final IBeXOperationType OPERATION_TYPE_EDEFAULT = IBeXOperationType.CONTEXT;

	/**
	 * The cached value of the '{@link #getOperationType() <em>Operation Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperationType()
	 * @generated
	 * @ordered
	 */
	protected IBeXOperationType operationType = OPERATION_TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getIncomingEdges() <em>Incoming Edges</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIncomingEdges()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXEdge> incomingEdges;

	/**
	 * The cached value of the '{@link #getOutgoingEdges() <em>Outgoing Edges</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutgoingEdges()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXEdge> outgoingEdges;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IBeXNodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXCoreModelPackage.Literals.IBE_XNODE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getType() {
		if (type != null && type.eIsProxy()) {
			InternalEObject oldType = (InternalEObject) type;
			type = (EClass) eResolveProxy(oldType);
			if (type != oldType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, IBeXCoreModelPackage.IBE_XNODE__TYPE,
							oldType, type));
			}
		}
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass basicGetType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(EClass newType) {
		EClass oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXCoreModelPackage.IBE_XNODE__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXOperationType getOperationType() {
		return operationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOperationType(IBeXOperationType newOperationType) {
		IBeXOperationType oldOperationType = operationType;
		operationType = newOperationType == null ? OPERATION_TYPE_EDEFAULT : newOperationType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXCoreModelPackage.IBE_XNODE__OPERATION_TYPE,
					oldOperationType, operationType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<IBeXEdge> getIncomingEdges() {
		if (incomingEdges == null) {
			incomingEdges = new EObjectWithInverseResolvingEList<IBeXEdge>(IBeXEdge.class, this,
					IBeXCoreModelPackage.IBE_XNODE__INCOMING_EDGES, IBeXCoreModelPackage.IBE_XEDGE__TARGET);
		}
		return incomingEdges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<IBeXEdge> getOutgoingEdges() {
		if (outgoingEdges == null) {
			outgoingEdges = new EObjectWithInverseResolvingEList<IBeXEdge>(IBeXEdge.class, this,
					IBeXCoreModelPackage.IBE_XNODE__OUTGOING_EDGES, IBeXCoreModelPackage.IBE_XEDGE__SOURCE);
		}
		return outgoingEdges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IBeXCoreModelPackage.IBE_XNODE__INCOMING_EDGES:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getIncomingEdges()).basicAdd(otherEnd, msgs);
		case IBeXCoreModelPackage.IBE_XNODE__OUTGOING_EDGES:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getOutgoingEdges()).basicAdd(otherEnd, msgs);
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
		case IBeXCoreModelPackage.IBE_XNODE__INCOMING_EDGES:
			return ((InternalEList<?>) getIncomingEdges()).basicRemove(otherEnd, msgs);
		case IBeXCoreModelPackage.IBE_XNODE__OUTGOING_EDGES:
			return ((InternalEList<?>) getOutgoingEdges()).basicRemove(otherEnd, msgs);
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
		case IBeXCoreModelPackage.IBE_XNODE__TYPE:
			if (resolve)
				return getType();
			return basicGetType();
		case IBeXCoreModelPackage.IBE_XNODE__OPERATION_TYPE:
			return getOperationType();
		case IBeXCoreModelPackage.IBE_XNODE__INCOMING_EDGES:
			return getIncomingEdges();
		case IBeXCoreModelPackage.IBE_XNODE__OUTGOING_EDGES:
			return getOutgoingEdges();
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
		case IBeXCoreModelPackage.IBE_XNODE__TYPE:
			setType((EClass) newValue);
			return;
		case IBeXCoreModelPackage.IBE_XNODE__OPERATION_TYPE:
			setOperationType((IBeXOperationType) newValue);
			return;
		case IBeXCoreModelPackage.IBE_XNODE__INCOMING_EDGES:
			getIncomingEdges().clear();
			getIncomingEdges().addAll((Collection<? extends IBeXEdge>) newValue);
			return;
		case IBeXCoreModelPackage.IBE_XNODE__OUTGOING_EDGES:
			getOutgoingEdges().clear();
			getOutgoingEdges().addAll((Collection<? extends IBeXEdge>) newValue);
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
		case IBeXCoreModelPackage.IBE_XNODE__TYPE:
			setType((EClass) null);
			return;
		case IBeXCoreModelPackage.IBE_XNODE__OPERATION_TYPE:
			setOperationType(OPERATION_TYPE_EDEFAULT);
			return;
		case IBeXCoreModelPackage.IBE_XNODE__INCOMING_EDGES:
			getIncomingEdges().clear();
			return;
		case IBeXCoreModelPackage.IBE_XNODE__OUTGOING_EDGES:
			getOutgoingEdges().clear();
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
		case IBeXCoreModelPackage.IBE_XNODE__TYPE:
			return type != null;
		case IBeXCoreModelPackage.IBE_XNODE__OPERATION_TYPE:
			return operationType != OPERATION_TYPE_EDEFAULT;
		case IBeXCoreModelPackage.IBE_XNODE__INCOMING_EDGES:
			return incomingEdges != null && !incomingEdges.isEmpty();
		case IBeXCoreModelPackage.IBE_XNODE__OUTGOING_EDGES:
			return outgoingEdges != null && !outgoingEdges.isEmpty();
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
		result.append(" (operationType: ");
		result.append(operationType);
		result.append(')');
		return result.toString();
	}

} //IBeXNodeImpl
