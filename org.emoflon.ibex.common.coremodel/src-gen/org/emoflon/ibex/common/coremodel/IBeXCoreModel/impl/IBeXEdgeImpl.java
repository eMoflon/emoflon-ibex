/**
 */
package org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IBe XEdge</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXEdgeImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXEdgeImpl#getSrc <em>Src</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXEdgeImpl#getTrg <em>Trg</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IBeXEdgeImpl extends IBeXNamedElementImpl implements IBeXEdge {
	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected EReference type;

	/**
	 * The cached value of the '{@link #getSrc() <em>Src</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSrc()
	 * @generated
	 * @ordered
	 */
	protected IBeXNode src;

	/**
	 * The cached value of the '{@link #getTrg() <em>Trg</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTrg()
	 * @generated
	 * @ordered
	 */
	protected IBeXNode trg;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IBeXEdgeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXCoreModelPackage.Literals.IBE_XEDGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getType() {
		if (type != null && type.eIsProxy()) {
			InternalEObject oldType = (InternalEObject) type;
			type = (EReference) eResolveProxy(oldType);
			if (type != oldType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, IBeXCoreModelPackage.IBE_XEDGE__TYPE,
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
	public EReference basicGetType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(EReference newType) {
		EReference oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXCoreModelPackage.IBE_XEDGE__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXNode getSrc() {
		if (src != null && src.eIsProxy()) {
			InternalEObject oldSrc = (InternalEObject) src;
			src = (IBeXNode) eResolveProxy(oldSrc);
			if (src != oldSrc) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, IBeXCoreModelPackage.IBE_XEDGE__SRC,
							oldSrc, src));
			}
		}
		return src;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXNode basicGetSrc() {
		return src;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSrc(IBeXNode newSrc, NotificationChain msgs) {
		IBeXNode oldSrc = src;
		src = newSrc;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXCoreModelPackage.IBE_XEDGE__SRC, oldSrc, newSrc);
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
	public void setSrc(IBeXNode newSrc) {
		if (newSrc != src) {
			NotificationChain msgs = null;
			if (src != null)
				msgs = ((InternalEObject) src).eInverseRemove(this, IBeXCoreModelPackage.IBE_XNODE__OUTGOING_EDGES,
						IBeXNode.class, msgs);
			if (newSrc != null)
				msgs = ((InternalEObject) newSrc).eInverseAdd(this, IBeXCoreModelPackage.IBE_XNODE__OUTGOING_EDGES,
						IBeXNode.class, msgs);
			msgs = basicSetSrc(newSrc, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXCoreModelPackage.IBE_XEDGE__SRC, newSrc, newSrc));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXNode getTrg() {
		if (trg != null && trg.eIsProxy()) {
			InternalEObject oldTrg = (InternalEObject) trg;
			trg = (IBeXNode) eResolveProxy(oldTrg);
			if (trg != oldTrg) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, IBeXCoreModelPackage.IBE_XEDGE__TRG,
							oldTrg, trg));
			}
		}
		return trg;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXNode basicGetTrg() {
		return trg;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTrg(IBeXNode newTrg, NotificationChain msgs) {
		IBeXNode oldTrg = trg;
		trg = newTrg;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXCoreModelPackage.IBE_XEDGE__TRG, oldTrg, newTrg);
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
	public void setTrg(IBeXNode newTrg) {
		if (newTrg != trg) {
			NotificationChain msgs = null;
			if (trg != null)
				msgs = ((InternalEObject) trg).eInverseRemove(this, IBeXCoreModelPackage.IBE_XNODE__INCOMING_EDGES,
						IBeXNode.class, msgs);
			if (newTrg != null)
				msgs = ((InternalEObject) newTrg).eInverseAdd(this, IBeXCoreModelPackage.IBE_XNODE__INCOMING_EDGES,
						IBeXNode.class, msgs);
			msgs = basicSetTrg(newTrg, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXCoreModelPackage.IBE_XEDGE__TRG, newTrg, newTrg));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IBeXCoreModelPackage.IBE_XEDGE__SRC:
			if (src != null)
				msgs = ((InternalEObject) src).eInverseRemove(this, IBeXCoreModelPackage.IBE_XNODE__OUTGOING_EDGES,
						IBeXNode.class, msgs);
			return basicSetSrc((IBeXNode) otherEnd, msgs);
		case IBeXCoreModelPackage.IBE_XEDGE__TRG:
			if (trg != null)
				msgs = ((InternalEObject) trg).eInverseRemove(this, IBeXCoreModelPackage.IBE_XNODE__INCOMING_EDGES,
						IBeXNode.class, msgs);
			return basicSetTrg((IBeXNode) otherEnd, msgs);
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
		case IBeXCoreModelPackage.IBE_XEDGE__SRC:
			return basicSetSrc(null, msgs);
		case IBeXCoreModelPackage.IBE_XEDGE__TRG:
			return basicSetTrg(null, msgs);
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
		case IBeXCoreModelPackage.IBE_XEDGE__TYPE:
			if (resolve)
				return getType();
			return basicGetType();
		case IBeXCoreModelPackage.IBE_XEDGE__SRC:
			if (resolve)
				return getSrc();
			return basicGetSrc();
		case IBeXCoreModelPackage.IBE_XEDGE__TRG:
			if (resolve)
				return getTrg();
			return basicGetTrg();
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
		case IBeXCoreModelPackage.IBE_XEDGE__TYPE:
			setType((EReference) newValue);
			return;
		case IBeXCoreModelPackage.IBE_XEDGE__SRC:
			setSrc((IBeXNode) newValue);
			return;
		case IBeXCoreModelPackage.IBE_XEDGE__TRG:
			setTrg((IBeXNode) newValue);
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
		case IBeXCoreModelPackage.IBE_XEDGE__TYPE:
			setType((EReference) null);
			return;
		case IBeXCoreModelPackage.IBE_XEDGE__SRC:
			setSrc((IBeXNode) null);
			return;
		case IBeXCoreModelPackage.IBE_XEDGE__TRG:
			setTrg((IBeXNode) null);
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
		case IBeXCoreModelPackage.IBE_XEDGE__TYPE:
			return type != null;
		case IBeXCoreModelPackage.IBE_XEDGE__SRC:
			return src != null;
		case IBeXCoreModelPackage.IBE_XEDGE__TRG:
			return trg != null;
		}
		return super.eIsSet(featureID);
	}

} //IBeXEdgeImpl
