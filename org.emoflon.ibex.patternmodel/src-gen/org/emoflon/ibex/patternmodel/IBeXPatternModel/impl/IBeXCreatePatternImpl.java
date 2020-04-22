/**
 */
package org.emoflon.ibex.patternmodel.IBeXPatternModel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeAssignment;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCreatePattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEdge;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IBe XCreate Pattern</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXCreatePatternImpl#getAttributeAssignments <em>Attribute Assignments</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXCreatePatternImpl#getContextNodes <em>Context Nodes</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXCreatePatternImpl#getCreatedEdges <em>Created Edges</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXCreatePatternImpl#getCreatedNodes <em>Created Nodes</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IBeXCreatePatternImpl extends IBeXPatternImpl implements IBeXCreatePattern {
	/**
	 * The cached value of the '{@link #getAttributeAssignments() <em>Attribute Assignments</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeAssignments()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXAttributeAssignment> attributeAssignments;

	/**
	 * The cached value of the '{@link #getContextNodes() <em>Context Nodes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContextNodes()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXNode> contextNodes;

	/**
	 * The cached value of the '{@link #getCreatedEdges() <em>Created Edges</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreatedEdges()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXEdge> createdEdges;

	/**
	 * The cached value of the '{@link #getCreatedNodes() <em>Created Nodes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreatedNodes()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXNode> createdNodes;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IBeXCreatePatternImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXPatternModelPackage.Literals.IBE_XCREATE_PATTERN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXAttributeAssignment> getAttributeAssignments() {
		if (attributeAssignments == null) {
			attributeAssignments = new EObjectContainmentEList<IBeXAttributeAssignment>(IBeXAttributeAssignment.class,
					this, IBeXPatternModelPackage.IBE_XCREATE_PATTERN__ATTRIBUTE_ASSIGNMENTS);
		}
		return attributeAssignments;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXNode> getContextNodes() {
		if (contextNodes == null) {
			contextNodes = new EObjectContainmentEList<IBeXNode>(IBeXNode.class, this,
					IBeXPatternModelPackage.IBE_XCREATE_PATTERN__CONTEXT_NODES);
		}
		return contextNodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXEdge> getCreatedEdges() {
		if (createdEdges == null) {
			createdEdges = new EObjectContainmentEList<IBeXEdge>(IBeXEdge.class, this,
					IBeXPatternModelPackage.IBE_XCREATE_PATTERN__CREATED_EDGES);
		}
		return createdEdges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXNode> getCreatedNodes() {
		if (createdNodes == null) {
			createdNodes = new EObjectContainmentEList<IBeXNode>(IBeXNode.class, this,
					IBeXPatternModelPackage.IBE_XCREATE_PATTERN__CREATED_NODES);
		}
		return createdNodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IBeXPatternModelPackage.IBE_XCREATE_PATTERN__ATTRIBUTE_ASSIGNMENTS:
			return ((InternalEList<?>) getAttributeAssignments()).basicRemove(otherEnd, msgs);
		case IBeXPatternModelPackage.IBE_XCREATE_PATTERN__CONTEXT_NODES:
			return ((InternalEList<?>) getContextNodes()).basicRemove(otherEnd, msgs);
		case IBeXPatternModelPackage.IBE_XCREATE_PATTERN__CREATED_EDGES:
			return ((InternalEList<?>) getCreatedEdges()).basicRemove(otherEnd, msgs);
		case IBeXPatternModelPackage.IBE_XCREATE_PATTERN__CREATED_NODES:
			return ((InternalEList<?>) getCreatedNodes()).basicRemove(otherEnd, msgs);
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
		case IBeXPatternModelPackage.IBE_XCREATE_PATTERN__ATTRIBUTE_ASSIGNMENTS:
			return getAttributeAssignments();
		case IBeXPatternModelPackage.IBE_XCREATE_PATTERN__CONTEXT_NODES:
			return getContextNodes();
		case IBeXPatternModelPackage.IBE_XCREATE_PATTERN__CREATED_EDGES:
			return getCreatedEdges();
		case IBeXPatternModelPackage.IBE_XCREATE_PATTERN__CREATED_NODES:
			return getCreatedNodes();
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
		case IBeXPatternModelPackage.IBE_XCREATE_PATTERN__ATTRIBUTE_ASSIGNMENTS:
			getAttributeAssignments().clear();
			getAttributeAssignments().addAll((Collection<? extends IBeXAttributeAssignment>) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XCREATE_PATTERN__CONTEXT_NODES:
			getContextNodes().clear();
			getContextNodes().addAll((Collection<? extends IBeXNode>) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XCREATE_PATTERN__CREATED_EDGES:
			getCreatedEdges().clear();
			getCreatedEdges().addAll((Collection<? extends IBeXEdge>) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XCREATE_PATTERN__CREATED_NODES:
			getCreatedNodes().clear();
			getCreatedNodes().addAll((Collection<? extends IBeXNode>) newValue);
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
		case IBeXPatternModelPackage.IBE_XCREATE_PATTERN__ATTRIBUTE_ASSIGNMENTS:
			getAttributeAssignments().clear();
			return;
		case IBeXPatternModelPackage.IBE_XCREATE_PATTERN__CONTEXT_NODES:
			getContextNodes().clear();
			return;
		case IBeXPatternModelPackage.IBE_XCREATE_PATTERN__CREATED_EDGES:
			getCreatedEdges().clear();
			return;
		case IBeXPatternModelPackage.IBE_XCREATE_PATTERN__CREATED_NODES:
			getCreatedNodes().clear();
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
		case IBeXPatternModelPackage.IBE_XCREATE_PATTERN__ATTRIBUTE_ASSIGNMENTS:
			return attributeAssignments != null && !attributeAssignments.isEmpty();
		case IBeXPatternModelPackage.IBE_XCREATE_PATTERN__CONTEXT_NODES:
			return contextNodes != null && !contextNodes.isEmpty();
		case IBeXPatternModelPackage.IBE_XCREATE_PATTERN__CREATED_EDGES:
			return createdEdges != null && !createdEdges.isEmpty();
		case IBeXPatternModelPackage.IBE_XCREATE_PATTERN__CREATED_NODES:
			return createdNodes != null && !createdNodes.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //IBeXCreatePatternImpl
