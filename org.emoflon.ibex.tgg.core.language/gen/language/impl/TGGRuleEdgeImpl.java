/**
 */
package language.impl;

import language.LanguagePackage;
import language.TGGRuleEdge;
import language.TGGRuleNode;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TGG Rule Edge</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link language.impl.TGGRuleEdgeImpl#getSrcNode <em>Src Node</em>}</li>
 *   <li>{@link language.impl.TGGRuleEdgeImpl#getTrgNode <em>Trg Node</em>}</li>
 *   <li>{@link language.impl.TGGRuleEdgeImpl#getType <em>Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TGGRuleEdgeImpl extends TGGRuleElementImpl implements TGGRuleEdge {
	/**
	 * The cached value of the '{@link #getSrcNode() <em>Src Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSrcNode()
	 * @generated
	 * @ordered
	 */
	protected TGGRuleNode srcNode;

	/**
	 * The cached value of the '{@link #getTrgNode() <em>Trg Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTrgNode()
	 * @generated
	 * @ordered
	 */
	protected TGGRuleNode trgNode;

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
	protected TGGRuleEdgeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LanguagePackage.Literals.TGG_RULE_EDGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TGGRuleNode getSrcNode() {
		if (srcNode != null && srcNode.eIsProxy()) {
			InternalEObject oldSrcNode = (InternalEObject) srcNode;
			srcNode = (TGGRuleNode) eResolveProxy(oldSrcNode);
			if (srcNode != oldSrcNode) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LanguagePackage.TGG_RULE_EDGE__SRC_NODE,
							oldSrcNode, srcNode));
			}
		}
		return srcNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGRuleNode basicGetSrcNode() {
		return srcNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSrcNode(TGGRuleNode newSrcNode, NotificationChain msgs) {
		TGGRuleNode oldSrcNode = srcNode;
		srcNode = newSrcNode;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					LanguagePackage.TGG_RULE_EDGE__SRC_NODE, oldSrcNode, newSrcNode);
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
	@Override
	public void setSrcNode(TGGRuleNode newSrcNode) {
		if (newSrcNode != srcNode) {
			NotificationChain msgs = null;
			if (srcNode != null)
				msgs = ((InternalEObject) srcNode).eInverseRemove(this, LanguagePackage.TGG_RULE_NODE__OUTGOING_EDGES,
						TGGRuleNode.class, msgs);
			if (newSrcNode != null)
				msgs = ((InternalEObject) newSrcNode).eInverseAdd(this, LanguagePackage.TGG_RULE_NODE__OUTGOING_EDGES,
						TGGRuleNode.class, msgs);
			msgs = basicSetSrcNode(newSrcNode, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LanguagePackage.TGG_RULE_EDGE__SRC_NODE, newSrcNode,
					newSrcNode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TGGRuleNode getTrgNode() {
		if (trgNode != null && trgNode.eIsProxy()) {
			InternalEObject oldTrgNode = (InternalEObject) trgNode;
			trgNode = (TGGRuleNode) eResolveProxy(oldTrgNode);
			if (trgNode != oldTrgNode) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LanguagePackage.TGG_RULE_EDGE__TRG_NODE,
							oldTrgNode, trgNode));
			}
		}
		return trgNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGRuleNode basicGetTrgNode() {
		return trgNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTrgNode(TGGRuleNode newTrgNode, NotificationChain msgs) {
		TGGRuleNode oldTrgNode = trgNode;
		trgNode = newTrgNode;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					LanguagePackage.TGG_RULE_EDGE__TRG_NODE, oldTrgNode, newTrgNode);
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
	@Override
	public void setTrgNode(TGGRuleNode newTrgNode) {
		if (newTrgNode != trgNode) {
			NotificationChain msgs = null;
			if (trgNode != null)
				msgs = ((InternalEObject) trgNode).eInverseRemove(this, LanguagePackage.TGG_RULE_NODE__INCOMING_EDGES,
						TGGRuleNode.class, msgs);
			if (newTrgNode != null)
				msgs = ((InternalEObject) newTrgNode).eInverseAdd(this, LanguagePackage.TGG_RULE_NODE__INCOMING_EDGES,
						TGGRuleNode.class, msgs);
			msgs = basicSetTrgNode(newTrgNode, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LanguagePackage.TGG_RULE_EDGE__TRG_NODE, newTrgNode,
					newTrgNode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getType() {
		if (type != null && type.eIsProxy()) {
			InternalEObject oldType = (InternalEObject) type;
			type = (EReference) eResolveProxy(oldType);
			if (type != oldType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LanguagePackage.TGG_RULE_EDGE__TYPE,
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
	@Override
	public void setType(EReference newType) {
		EReference oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LanguagePackage.TGG_RULE_EDGE__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case LanguagePackage.TGG_RULE_EDGE__SRC_NODE:
			if (srcNode != null)
				msgs = ((InternalEObject) srcNode).eInverseRemove(this, LanguagePackage.TGG_RULE_NODE__OUTGOING_EDGES,
						TGGRuleNode.class, msgs);
			return basicSetSrcNode((TGGRuleNode) otherEnd, msgs);
		case LanguagePackage.TGG_RULE_EDGE__TRG_NODE:
			if (trgNode != null)
				msgs = ((InternalEObject) trgNode).eInverseRemove(this, LanguagePackage.TGG_RULE_NODE__INCOMING_EDGES,
						TGGRuleNode.class, msgs);
			return basicSetTrgNode((TGGRuleNode) otherEnd, msgs);
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
		case LanguagePackage.TGG_RULE_EDGE__SRC_NODE:
			return basicSetSrcNode(null, msgs);
		case LanguagePackage.TGG_RULE_EDGE__TRG_NODE:
			return basicSetTrgNode(null, msgs);
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
		case LanguagePackage.TGG_RULE_EDGE__SRC_NODE:
			if (resolve)
				return getSrcNode();
			return basicGetSrcNode();
		case LanguagePackage.TGG_RULE_EDGE__TRG_NODE:
			if (resolve)
				return getTrgNode();
			return basicGetTrgNode();
		case LanguagePackage.TGG_RULE_EDGE__TYPE:
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
		case LanguagePackage.TGG_RULE_EDGE__SRC_NODE:
			setSrcNode((TGGRuleNode) newValue);
			return;
		case LanguagePackage.TGG_RULE_EDGE__TRG_NODE:
			setTrgNode((TGGRuleNode) newValue);
			return;
		case LanguagePackage.TGG_RULE_EDGE__TYPE:
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
		case LanguagePackage.TGG_RULE_EDGE__SRC_NODE:
			setSrcNode((TGGRuleNode) null);
			return;
		case LanguagePackage.TGG_RULE_EDGE__TRG_NODE:
			setTrgNode((TGGRuleNode) null);
			return;
		case LanguagePackage.TGG_RULE_EDGE__TYPE:
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
		case LanguagePackage.TGG_RULE_EDGE__SRC_NODE:
			return srcNode != null;
		case LanguagePackage.TGG_RULE_EDGE__TRG_NODE:
			return trgNode != null;
		case LanguagePackage.TGG_RULE_EDGE__TYPE:
			return type != null;
		}
		return super.eIsSet(featureID);
	}
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} //TGGRuleEdgeImpl
