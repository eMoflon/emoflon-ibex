/**
 */
package IBeXLanguage.impl;

import IBeXLanguage.IBeXEdge;
import IBeXLanguage.IBeXLanguagePackage;
import IBeXLanguage.IBeXNode;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IBe XNode</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link IBeXLanguage.impl.IBeXNodeImpl#getIncomingEdges <em>Incoming Edges</em>}</li>
 *   <li>{@link IBeXLanguage.impl.IBeXNodeImpl#getOutgoingEdges <em>Outgoing Edges</em>}</li>
 *   <li>{@link IBeXLanguage.impl.IBeXNodeImpl#getType <em>Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IBeXNodeImpl extends IBeXNamedElementImpl implements IBeXNode {
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
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected EClass type;

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
		return IBeXLanguagePackage.Literals.IBE_XNODE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXEdge> getIncomingEdges() {
		if (incomingEdges == null) {
			incomingEdges = new EObjectWithInverseResolvingEList<IBeXEdge>(IBeXEdge.class, this,
					IBeXLanguagePackage.IBE_XNODE__INCOMING_EDGES, IBeXLanguagePackage.IBE_XEDGE__TARGET_NODE);
		}
		return incomingEdges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXEdge> getOutgoingEdges() {
		if (outgoingEdges == null) {
			outgoingEdges = new EObjectWithInverseResolvingEList<IBeXEdge>(IBeXEdge.class, this,
					IBeXLanguagePackage.IBE_XNODE__OUTGOING_EDGES, IBeXLanguagePackage.IBE_XEDGE__SOURCE_NODE);
		}
		return outgoingEdges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getType() {
		if (type != null && type.eIsProxy()) {
			InternalEObject oldType = (InternalEObject) type;
			type = (EClass) eResolveProxy(oldType);
			if (type != oldType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, IBeXLanguagePackage.IBE_XNODE__TYPE,
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
	@Override
	public void setType(EClass newType) {
		EClass oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXLanguagePackage.IBE_XNODE__TYPE, oldType, type));
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
		case IBeXLanguagePackage.IBE_XNODE__INCOMING_EDGES:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getIncomingEdges()).basicAdd(otherEnd, msgs);
		case IBeXLanguagePackage.IBE_XNODE__OUTGOING_EDGES:
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
		case IBeXLanguagePackage.IBE_XNODE__INCOMING_EDGES:
			return ((InternalEList<?>) getIncomingEdges()).basicRemove(otherEnd, msgs);
		case IBeXLanguagePackage.IBE_XNODE__OUTGOING_EDGES:
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
		case IBeXLanguagePackage.IBE_XNODE__INCOMING_EDGES:
			return getIncomingEdges();
		case IBeXLanguagePackage.IBE_XNODE__OUTGOING_EDGES:
			return getOutgoingEdges();
		case IBeXLanguagePackage.IBE_XNODE__TYPE:
			if (resolve)
				return getType();
			return basicGetType();
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
		case IBeXLanguagePackage.IBE_XNODE__INCOMING_EDGES:
			getIncomingEdges().clear();
			getIncomingEdges().addAll((Collection<? extends IBeXEdge>) newValue);
			return;
		case IBeXLanguagePackage.IBE_XNODE__OUTGOING_EDGES:
			getOutgoingEdges().clear();
			getOutgoingEdges().addAll((Collection<? extends IBeXEdge>) newValue);
			return;
		case IBeXLanguagePackage.IBE_XNODE__TYPE:
			setType((EClass) newValue);
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
		case IBeXLanguagePackage.IBE_XNODE__INCOMING_EDGES:
			getIncomingEdges().clear();
			return;
		case IBeXLanguagePackage.IBE_XNODE__OUTGOING_EDGES:
			getOutgoingEdges().clear();
			return;
		case IBeXLanguagePackage.IBE_XNODE__TYPE:
			setType((EClass) null);
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
		case IBeXLanguagePackage.IBE_XNODE__INCOMING_EDGES:
			return incomingEdges != null && !incomingEdges.isEmpty();
		case IBeXLanguagePackage.IBE_XNODE__OUTGOING_EDGES:
			return outgoingEdges != null && !outgoingEdges.isEmpty();
		case IBeXLanguagePackage.IBE_XNODE__TYPE:
			return type != null;
		}
		return super.eIsSet(featureID);
	}

} //IBeXNodeImpl
