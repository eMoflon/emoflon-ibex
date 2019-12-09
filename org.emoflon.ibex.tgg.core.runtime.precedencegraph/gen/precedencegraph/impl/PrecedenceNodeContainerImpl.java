/**
 */
package precedencegraph.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import precedencegraph.PrecedenceNode;
import precedencegraph.PrecedenceNodeContainer;
import precedencegraph.PrecedencegraphPackage;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Precedence Node Container</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link precedencegraph.impl.PrecedenceNodeContainerImpl#getNodes <em>Nodes</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PrecedenceNodeContainerImpl extends EObjectImpl implements PrecedenceNodeContainer {
	/**
	 * The cached value of the '{@link #getNodes() <em>Nodes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNodes()
	 * @generated
	 * @ordered
	 */
	protected EList<PrecedenceNode> nodes;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PrecedenceNodeContainerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PrecedencegraphPackage.Literals.PRECEDENCE_NODE_CONTAINER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PrecedenceNode> getNodes() {
		if (nodes == null) {
			nodes = new EObjectContainmentEList<PrecedenceNode>(PrecedenceNode.class, this,
					PrecedencegraphPackage.PRECEDENCE_NODE_CONTAINER__NODES);
		}
		return nodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case PrecedencegraphPackage.PRECEDENCE_NODE_CONTAINER__NODES:
			return ((InternalEList<?>) getNodes()).basicRemove(otherEnd, msgs);
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
		case PrecedencegraphPackage.PRECEDENCE_NODE_CONTAINER__NODES:
			return getNodes();
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
		case PrecedencegraphPackage.PRECEDENCE_NODE_CONTAINER__NODES:
			getNodes().clear();
			getNodes().addAll((Collection<? extends PrecedenceNode>) newValue);
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
		case PrecedencegraphPackage.PRECEDENCE_NODE_CONTAINER__NODES:
			getNodes().clear();
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
		case PrecedencegraphPackage.PRECEDENCE_NODE_CONTAINER__NODES:
			return nodes != null && !nodes.isEmpty();
		}
		return super.eIsSet(featureID);
	}
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} //PrecedenceNodeContainerImpl
