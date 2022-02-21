/**
 */
package org.emoflon.ibex.patternmodel.IBeXPatternModel.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDeletePattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEdge;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IBe XDelete Pattern</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXDeletePatternImpl#getContextNodes <em>Context Nodes</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXDeletePatternImpl#getDeletedEdges <em>Deleted Edges</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXDeletePatternImpl#getDeletedNodes <em>Deleted Nodes</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IBeXDeletePatternImpl extends IBeXPatternImpl implements IBeXDeletePattern {
	/**
	 * The cached value of the '{@link #getContextNodes() <em>Context Nodes</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContextNodes()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXNode> contextNodes;

	/**
	 * The cached value of the '{@link #getDeletedEdges() <em>Deleted Edges</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeletedEdges()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXEdge> deletedEdges;

	/**
	 * The cached value of the '{@link #getDeletedNodes() <em>Deleted Nodes</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeletedNodes()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXNode> deletedNodes;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IBeXDeletePatternImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXPatternModelPackage.Literals.IBE_XDELETE_PATTERN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXNode> getContextNodes() {
		if (contextNodes == null) {
			contextNodes = new EObjectResolvingEList<IBeXNode>(IBeXNode.class, this,
					IBeXPatternModelPackage.IBE_XDELETE_PATTERN__CONTEXT_NODES);
		}
		return contextNodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXEdge> getDeletedEdges() {
		if (deletedEdges == null) {
			deletedEdges = new EObjectResolvingEList<IBeXEdge>(IBeXEdge.class, this,
					IBeXPatternModelPackage.IBE_XDELETE_PATTERN__DELETED_EDGES);
		}
		return deletedEdges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXNode> getDeletedNodes() {
		if (deletedNodes == null) {
			deletedNodes = new EObjectResolvingEList<IBeXNode>(IBeXNode.class, this,
					IBeXPatternModelPackage.IBE_XDELETE_PATTERN__DELETED_NODES);
		}
		return deletedNodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case IBeXPatternModelPackage.IBE_XDELETE_PATTERN__CONTEXT_NODES:
			return getContextNodes();
		case IBeXPatternModelPackage.IBE_XDELETE_PATTERN__DELETED_EDGES:
			return getDeletedEdges();
		case IBeXPatternModelPackage.IBE_XDELETE_PATTERN__DELETED_NODES:
			return getDeletedNodes();
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
		case IBeXPatternModelPackage.IBE_XDELETE_PATTERN__CONTEXT_NODES:
			getContextNodes().clear();
			getContextNodes().addAll((Collection<? extends IBeXNode>) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XDELETE_PATTERN__DELETED_EDGES:
			getDeletedEdges().clear();
			getDeletedEdges().addAll((Collection<? extends IBeXEdge>) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XDELETE_PATTERN__DELETED_NODES:
			getDeletedNodes().clear();
			getDeletedNodes().addAll((Collection<? extends IBeXNode>) newValue);
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
		case IBeXPatternModelPackage.IBE_XDELETE_PATTERN__CONTEXT_NODES:
			getContextNodes().clear();
			return;
		case IBeXPatternModelPackage.IBE_XDELETE_PATTERN__DELETED_EDGES:
			getDeletedEdges().clear();
			return;
		case IBeXPatternModelPackage.IBE_XDELETE_PATTERN__DELETED_NODES:
			getDeletedNodes().clear();
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
		case IBeXPatternModelPackage.IBE_XDELETE_PATTERN__CONTEXT_NODES:
			return contextNodes != null && !contextNodes.isEmpty();
		case IBeXPatternModelPackage.IBE_XDELETE_PATTERN__DELETED_EDGES:
			return deletedEdges != null && !deletedEdges.isEmpty();
		case IBeXPatternModelPackage.IBE_XDELETE_PATTERN__DELETED_NODES:
			return deletedNodes != null && !deletedNodes.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //IBeXDeletePatternImpl
