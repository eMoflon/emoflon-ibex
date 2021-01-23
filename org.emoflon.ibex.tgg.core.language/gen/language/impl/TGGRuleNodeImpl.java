/**
 */
package language.impl;

import java.util.Collection;

import language.LanguagePackage;
import language.TGGInplaceAttributeExpression;
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TGG Rule Node</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link language.impl.TGGRuleNodeImpl#getIncomingEdges <em>Incoming Edges</em>}</li>
 *   <li>{@link language.impl.TGGRuleNodeImpl#getOutgoingEdges <em>Outgoing Edges</em>}</li>
 *   <li>{@link language.impl.TGGRuleNodeImpl#getType <em>Type</em>}</li>
 *   <li>{@link language.impl.TGGRuleNodeImpl#getIncomingCorrsSource <em>Incoming Corrs Source</em>}</li>
 *   <li>{@link language.impl.TGGRuleNodeImpl#getIncomingCorrsTarget <em>Incoming Corrs Target</em>}</li>
 *   <li>{@link language.impl.TGGRuleNodeImpl#getAttrExpr <em>Attr Expr</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TGGRuleNodeImpl extends TGGRuleElementImpl implements TGGRuleNode {
	/**
	 * The cached value of the '{@link #getIncomingEdges() <em>Incoming Edges</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIncomingEdges()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGRuleEdge> incomingEdges;

	/**
	 * The cached value of the '{@link #getOutgoingEdges() <em>Outgoing Edges</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutgoingEdges()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGRuleEdge> outgoingEdges;

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
	 * The cached value of the '{@link #getIncomingCorrsSource() <em>Incoming Corrs Source</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIncomingCorrsSource()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGRuleCorr> incomingCorrsSource;

	/**
	 * The cached value of the '{@link #getIncomingCorrsTarget() <em>Incoming Corrs Target</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIncomingCorrsTarget()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGRuleCorr> incomingCorrsTarget;

	/**
	 * The cached value of the '{@link #getAttrExpr() <em>Attr Expr</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttrExpr()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGInplaceAttributeExpression> attrExpr;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TGGRuleNodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LanguagePackage.Literals.TGG_RULE_NODE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TGGRuleEdge> getIncomingEdges() {
		if (incomingEdges == null) {
			incomingEdges = new EObjectWithInverseResolvingEList<TGGRuleEdge>(TGGRuleEdge.class, this, LanguagePackage.TGG_RULE_NODE__INCOMING_EDGES,
					LanguagePackage.TGG_RULE_EDGE__TRG_NODE);
		}
		return incomingEdges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TGGRuleEdge> getOutgoingEdges() {
		if (outgoingEdges == null) {
			outgoingEdges = new EObjectWithInverseResolvingEList<TGGRuleEdge>(TGGRuleEdge.class, this, LanguagePackage.TGG_RULE_NODE__OUTGOING_EDGES,
					LanguagePackage.TGG_RULE_EDGE__SRC_NODE);
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LanguagePackage.TGG_RULE_NODE__TYPE, oldType, type));
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
			eNotify(new ENotificationImpl(this, Notification.SET, LanguagePackage.TGG_RULE_NODE__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TGGRuleCorr> getIncomingCorrsSource() {
		if (incomingCorrsSource == null) {
			incomingCorrsSource = new EObjectWithInverseResolvingEList<TGGRuleCorr>(TGGRuleCorr.class, this,
					LanguagePackage.TGG_RULE_NODE__INCOMING_CORRS_SOURCE, LanguagePackage.TGG_RULE_CORR__SOURCE);
		}
		return incomingCorrsSource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TGGRuleCorr> getIncomingCorrsTarget() {
		if (incomingCorrsTarget == null) {
			incomingCorrsTarget = new EObjectWithInverseResolvingEList<TGGRuleCorr>(TGGRuleCorr.class, this,
					LanguagePackage.TGG_RULE_NODE__INCOMING_CORRS_TARGET, LanguagePackage.TGG_RULE_CORR__TARGET);
		}
		return incomingCorrsTarget;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TGGInplaceAttributeExpression> getAttrExpr() {
		if (attrExpr == null) {
			attrExpr = new EObjectContainmentEList<TGGInplaceAttributeExpression>(TGGInplaceAttributeExpression.class, this,
					LanguagePackage.TGG_RULE_NODE__ATTR_EXPR);
		}
		return attrExpr;
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
		case LanguagePackage.TGG_RULE_NODE__INCOMING_EDGES:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getIncomingEdges()).basicAdd(otherEnd, msgs);
		case LanguagePackage.TGG_RULE_NODE__OUTGOING_EDGES:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getOutgoingEdges()).basicAdd(otherEnd, msgs);
		case LanguagePackage.TGG_RULE_NODE__INCOMING_CORRS_SOURCE:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getIncomingCorrsSource()).basicAdd(otherEnd, msgs);
		case LanguagePackage.TGG_RULE_NODE__INCOMING_CORRS_TARGET:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getIncomingCorrsTarget()).basicAdd(otherEnd, msgs);
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
		case LanguagePackage.TGG_RULE_NODE__INCOMING_EDGES:
			return ((InternalEList<?>) getIncomingEdges()).basicRemove(otherEnd, msgs);
		case LanguagePackage.TGG_RULE_NODE__OUTGOING_EDGES:
			return ((InternalEList<?>) getOutgoingEdges()).basicRemove(otherEnd, msgs);
		case LanguagePackage.TGG_RULE_NODE__INCOMING_CORRS_SOURCE:
			return ((InternalEList<?>) getIncomingCorrsSource()).basicRemove(otherEnd, msgs);
		case LanguagePackage.TGG_RULE_NODE__INCOMING_CORRS_TARGET:
			return ((InternalEList<?>) getIncomingCorrsTarget()).basicRemove(otherEnd, msgs);
		case LanguagePackage.TGG_RULE_NODE__ATTR_EXPR:
			return ((InternalEList<?>) getAttrExpr()).basicRemove(otherEnd, msgs);
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
		case LanguagePackage.TGG_RULE_NODE__INCOMING_EDGES:
			return getIncomingEdges();
		case LanguagePackage.TGG_RULE_NODE__OUTGOING_EDGES:
			return getOutgoingEdges();
		case LanguagePackage.TGG_RULE_NODE__TYPE:
			if (resolve)
				return getType();
			return basicGetType();
		case LanguagePackage.TGG_RULE_NODE__INCOMING_CORRS_SOURCE:
			return getIncomingCorrsSource();
		case LanguagePackage.TGG_RULE_NODE__INCOMING_CORRS_TARGET:
			return getIncomingCorrsTarget();
		case LanguagePackage.TGG_RULE_NODE__ATTR_EXPR:
			return getAttrExpr();
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
		case LanguagePackage.TGG_RULE_NODE__INCOMING_EDGES:
			getIncomingEdges().clear();
			getIncomingEdges().addAll((Collection<? extends TGGRuleEdge>) newValue);
			return;
		case LanguagePackage.TGG_RULE_NODE__OUTGOING_EDGES:
			getOutgoingEdges().clear();
			getOutgoingEdges().addAll((Collection<? extends TGGRuleEdge>) newValue);
			return;
		case LanguagePackage.TGG_RULE_NODE__TYPE:
			setType((EClass) newValue);
			return;
		case LanguagePackage.TGG_RULE_NODE__INCOMING_CORRS_SOURCE:
			getIncomingCorrsSource().clear();
			getIncomingCorrsSource().addAll((Collection<? extends TGGRuleCorr>) newValue);
			return;
		case LanguagePackage.TGG_RULE_NODE__INCOMING_CORRS_TARGET:
			getIncomingCorrsTarget().clear();
			getIncomingCorrsTarget().addAll((Collection<? extends TGGRuleCorr>) newValue);
			return;
		case LanguagePackage.TGG_RULE_NODE__ATTR_EXPR:
			getAttrExpr().clear();
			getAttrExpr().addAll((Collection<? extends TGGInplaceAttributeExpression>) newValue);
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
		case LanguagePackage.TGG_RULE_NODE__INCOMING_EDGES:
			getIncomingEdges().clear();
			return;
		case LanguagePackage.TGG_RULE_NODE__OUTGOING_EDGES:
			getOutgoingEdges().clear();
			return;
		case LanguagePackage.TGG_RULE_NODE__TYPE:
			setType((EClass) null);
			return;
		case LanguagePackage.TGG_RULE_NODE__INCOMING_CORRS_SOURCE:
			getIncomingCorrsSource().clear();
			return;
		case LanguagePackage.TGG_RULE_NODE__INCOMING_CORRS_TARGET:
			getIncomingCorrsTarget().clear();
			return;
		case LanguagePackage.TGG_RULE_NODE__ATTR_EXPR:
			getAttrExpr().clear();
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
		case LanguagePackage.TGG_RULE_NODE__INCOMING_EDGES:
			return incomingEdges != null && !incomingEdges.isEmpty();
		case LanguagePackage.TGG_RULE_NODE__OUTGOING_EDGES:
			return outgoingEdges != null && !outgoingEdges.isEmpty();
		case LanguagePackage.TGG_RULE_NODE__TYPE:
			return type != null;
		case LanguagePackage.TGG_RULE_NODE__INCOMING_CORRS_SOURCE:
			return incomingCorrsSource != null && !incomingCorrsSource.isEmpty();
		case LanguagePackage.TGG_RULE_NODE__INCOMING_CORRS_TARGET:
			return incomingCorrsTarget != null && !incomingCorrsTarget.isEmpty();
		case LanguagePackage.TGG_RULE_NODE__ATTR_EXPR:
			return attrExpr != null && !attrExpr.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //TGGRuleNodeImpl
