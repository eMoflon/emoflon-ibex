/**
 */
package org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRuleDelta;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IBe XRule Delta</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXRuleDeltaImpl#getNodes <em>Nodes</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXRuleDeltaImpl#getEdges <em>Edges</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXRuleDeltaImpl#isEmpty <em>Empty</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IBeXRuleDeltaImpl extends MinimalEObjectImpl.Container implements IBeXRuleDelta {
	/**
	 * The cached value of the '{@link #getNodes() <em>Nodes</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNodes()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXNode> nodes;

	/**
	 * The cached value of the '{@link #getEdges() <em>Edges</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEdges()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXEdge> edges;

	/**
	 * The default value of the '{@link #isEmpty() <em>Empty</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEmpty()
	 * @generated
	 * @ordered
	 */
	protected static final boolean EMPTY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isEmpty() <em>Empty</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEmpty()
	 * @generated
	 * @ordered
	 */
	protected boolean empty = EMPTY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IBeXRuleDeltaImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXCoreModelPackage.Literals.IBE_XRULE_DELTA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<IBeXNode> getNodes() {
		if (nodes == null) {
			nodes = new EObjectResolvingEList<IBeXNode>(IBeXNode.class, this,
					IBeXCoreModelPackage.IBE_XRULE_DELTA__NODES);
		}
		return nodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<IBeXEdge> getEdges() {
		if (edges == null) {
			edges = new EObjectResolvingEList<IBeXEdge>(IBeXEdge.class, this,
					IBeXCoreModelPackage.IBE_XRULE_DELTA__EDGES);
		}
		return edges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isEmpty() {
		return empty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEmpty(boolean newEmpty) {
		boolean oldEmpty = empty;
		empty = newEmpty;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXCoreModelPackage.IBE_XRULE_DELTA__EMPTY, oldEmpty,
					empty));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case IBeXCoreModelPackage.IBE_XRULE_DELTA__NODES:
			return getNodes();
		case IBeXCoreModelPackage.IBE_XRULE_DELTA__EDGES:
			return getEdges();
		case IBeXCoreModelPackage.IBE_XRULE_DELTA__EMPTY:
			return isEmpty();
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
		case IBeXCoreModelPackage.IBE_XRULE_DELTA__NODES:
			getNodes().clear();
			getNodes().addAll((Collection<? extends IBeXNode>) newValue);
			return;
		case IBeXCoreModelPackage.IBE_XRULE_DELTA__EDGES:
			getEdges().clear();
			getEdges().addAll((Collection<? extends IBeXEdge>) newValue);
			return;
		case IBeXCoreModelPackage.IBE_XRULE_DELTA__EMPTY:
			setEmpty((Boolean) newValue);
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
		case IBeXCoreModelPackage.IBE_XRULE_DELTA__NODES:
			getNodes().clear();
			return;
		case IBeXCoreModelPackage.IBE_XRULE_DELTA__EDGES:
			getEdges().clear();
			return;
		case IBeXCoreModelPackage.IBE_XRULE_DELTA__EMPTY:
			setEmpty(EMPTY_EDEFAULT);
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
		case IBeXCoreModelPackage.IBE_XRULE_DELTA__NODES:
			return nodes != null && !nodes.isEmpty();
		case IBeXCoreModelPackage.IBE_XRULE_DELTA__EDGES:
			return edges != null && !edges.isEmpty();
		case IBeXCoreModelPackage.IBE_XRULE_DELTA__EMPTY:
			return empty != EMPTY_EDEFAULT;
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
		result.append(" (empty: ");
		result.append(empty);
		result.append(')');
		return result.toString();
	}

} //IBeXRuleDeltaImpl