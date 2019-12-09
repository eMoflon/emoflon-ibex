/**
 */
package IBeXLanguage.impl;

import IBeXLanguage.IBeXEdge;
import IBeXLanguage.IBeXLanguagePackage;
import IBeXLanguage.IBeXNode;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IBe XEdge</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link IBeXLanguage.impl.IBeXEdgeImpl#getSourceNode <em>Source Node</em>}</li>
 *   <li>{@link IBeXLanguage.impl.IBeXEdgeImpl#getTargetNode <em>Target Node</em>}</li>
 *   <li>{@link IBeXLanguage.impl.IBeXEdgeImpl#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class IBeXEdgeImpl extends EObjectImpl implements IBeXEdge {
	/**
	 * The cached value of the '{@link #getSourceNode() <em>Source Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceNode()
	 * @generated
	 * @ordered
	 */
	protected IBeXNode sourceNode;

	/**
	 * The cached value of the '{@link #getTargetNode() <em>Target Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetNode()
	 * @generated
	 * @ordered
	 */
	protected IBeXNode targetNode;

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
		return IBeXLanguagePackage.Literals.IBE_XEDGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXNode getSourceNode() {
		if (sourceNode != null && sourceNode.eIsProxy()) {
			InternalEObject oldSourceNode = (InternalEObject) sourceNode;
			sourceNode = (IBeXNode) eResolveProxy(oldSourceNode);
			if (sourceNode != oldSourceNode) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							IBeXLanguagePackage.IBE_XEDGE__SOURCE_NODE, oldSourceNode, sourceNode));
			}
		}
		return sourceNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXNode basicGetSourceNode() {
		return sourceNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSourceNode(IBeXNode newSourceNode, NotificationChain msgs) {
		IBeXNode oldSourceNode = sourceNode;
		sourceNode = newSourceNode;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXLanguagePackage.IBE_XEDGE__SOURCE_NODE, oldSourceNode, newSourceNode);
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
	public void setSourceNode(IBeXNode newSourceNode) {
		if (newSourceNode != sourceNode) {
			NotificationChain msgs = null;
			if (sourceNode != null)
				msgs = ((InternalEObject) sourceNode).eInverseRemove(this,
						IBeXLanguagePackage.IBE_XNODE__OUTGOING_EDGES, IBeXNode.class, msgs);
			if (newSourceNode != null)
				msgs = ((InternalEObject) newSourceNode).eInverseAdd(this,
						IBeXLanguagePackage.IBE_XNODE__OUTGOING_EDGES, IBeXNode.class, msgs);
			msgs = basicSetSourceNode(newSourceNode, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXLanguagePackage.IBE_XEDGE__SOURCE_NODE,
					newSourceNode, newSourceNode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXNode getTargetNode() {
		if (targetNode != null && targetNode.eIsProxy()) {
			InternalEObject oldTargetNode = (InternalEObject) targetNode;
			targetNode = (IBeXNode) eResolveProxy(oldTargetNode);
			if (targetNode != oldTargetNode) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							IBeXLanguagePackage.IBE_XEDGE__TARGET_NODE, oldTargetNode, targetNode));
			}
		}
		return targetNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXNode basicGetTargetNode() {
		return targetNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTargetNode(IBeXNode newTargetNode, NotificationChain msgs) {
		IBeXNode oldTargetNode = targetNode;
		targetNode = newTargetNode;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXLanguagePackage.IBE_XEDGE__TARGET_NODE, oldTargetNode, newTargetNode);
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
	public void setTargetNode(IBeXNode newTargetNode) {
		if (newTargetNode != targetNode) {
			NotificationChain msgs = null;
			if (targetNode != null)
				msgs = ((InternalEObject) targetNode).eInverseRemove(this,
						IBeXLanguagePackage.IBE_XNODE__INCOMING_EDGES, IBeXNode.class, msgs);
			if (newTargetNode != null)
				msgs = ((InternalEObject) newTargetNode).eInverseAdd(this,
						IBeXLanguagePackage.IBE_XNODE__INCOMING_EDGES, IBeXNode.class, msgs);
			msgs = basicSetTargetNode(newTargetNode, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXLanguagePackage.IBE_XEDGE__TARGET_NODE,
					newTargetNode, newTargetNode));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, IBeXLanguagePackage.IBE_XEDGE__TYPE,
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
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXLanguagePackage.IBE_XEDGE__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IBeXLanguagePackage.IBE_XEDGE__SOURCE_NODE:
			if (sourceNode != null)
				msgs = ((InternalEObject) sourceNode).eInverseRemove(this,
						IBeXLanguagePackage.IBE_XNODE__OUTGOING_EDGES, IBeXNode.class, msgs);
			return basicSetSourceNode((IBeXNode) otherEnd, msgs);
		case IBeXLanguagePackage.IBE_XEDGE__TARGET_NODE:
			if (targetNode != null)
				msgs = ((InternalEObject) targetNode).eInverseRemove(this,
						IBeXLanguagePackage.IBE_XNODE__INCOMING_EDGES, IBeXNode.class, msgs);
			return basicSetTargetNode((IBeXNode) otherEnd, msgs);
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
		case IBeXLanguagePackage.IBE_XEDGE__SOURCE_NODE:
			return basicSetSourceNode(null, msgs);
		case IBeXLanguagePackage.IBE_XEDGE__TARGET_NODE:
			return basicSetTargetNode(null, msgs);
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
		case IBeXLanguagePackage.IBE_XEDGE__SOURCE_NODE:
			if (resolve)
				return getSourceNode();
			return basicGetSourceNode();
		case IBeXLanguagePackage.IBE_XEDGE__TARGET_NODE:
			if (resolve)
				return getTargetNode();
			return basicGetTargetNode();
		case IBeXLanguagePackage.IBE_XEDGE__TYPE:
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
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case IBeXLanguagePackage.IBE_XEDGE__SOURCE_NODE:
			setSourceNode((IBeXNode) newValue);
			return;
		case IBeXLanguagePackage.IBE_XEDGE__TARGET_NODE:
			setTargetNode((IBeXNode) newValue);
			return;
		case IBeXLanguagePackage.IBE_XEDGE__TYPE:
			setType((EReference) newValue);
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
		case IBeXLanguagePackage.IBE_XEDGE__SOURCE_NODE:
			setSourceNode((IBeXNode) null);
			return;
		case IBeXLanguagePackage.IBE_XEDGE__TARGET_NODE:
			setTargetNode((IBeXNode) null);
			return;
		case IBeXLanguagePackage.IBE_XEDGE__TYPE:
			setType((EReference) null);
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
		case IBeXLanguagePackage.IBE_XEDGE__SOURCE_NODE:
			return sourceNode != null;
		case IBeXLanguagePackage.IBE_XEDGE__TARGET_NODE:
			return targetNode != null;
		case IBeXLanguagePackage.IBE_XEDGE__TYPE:
			return type != null;
		}
		return super.eIsSet(featureID);
	}
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} //IBeXEdgeImpl
