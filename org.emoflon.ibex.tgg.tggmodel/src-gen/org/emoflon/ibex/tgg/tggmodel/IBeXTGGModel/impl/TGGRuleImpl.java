/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXRuleImpl;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintSet;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGCorrespondence;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGEdge;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TGG Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleImpl#getNodes <em>Nodes</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleImpl#getEdges <em>Edges</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleImpl#getCorrespondenceNodes <em>Correspondence Nodes</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleImpl#getOperationalisations <em>Operationalisations</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleImpl#getAttributeConstraints <em>Attribute Constraints</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TGGRuleImpl extends IBeXRuleImpl implements TGGRule {
	/**
	 * The cached value of the '{@link #getNodes() <em>Nodes</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNodes()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGNode> nodes;

	/**
	 * The cached value of the '{@link #getEdges() <em>Edges</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEdges()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGEdge> edges;

	/**
	 * The cached value of the '{@link #getCorrespondenceNodes() <em>Correspondence Nodes</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCorrespondenceNodes()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGCorrespondence> correspondenceNodes;

	/**
	 * The cached value of the '{@link #getOperationalisations() <em>Operationalisations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperationalisations()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGOperationalRule> operationalisations;

	/**
	 * The cached value of the '{@link #getAttributeConstraints() <em>Attribute Constraints</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeConstraints()
	 * @generated
	 * @ordered
	 */
	protected TGGAttributeConstraintSet attributeConstraints;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TGGRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXTGGModelPackage.Literals.TGG_RULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TGGNode> getNodes() {
		if (nodes == null) {
			nodes = new EObjectResolvingEList<TGGNode>(TGGNode.class, this, IBeXTGGModelPackage.TGG_RULE__NODES);
		}
		return nodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TGGEdge> getEdges() {
		if (edges == null) {
			edges = new EObjectResolvingEList<TGGEdge>(TGGEdge.class, this, IBeXTGGModelPackage.TGG_RULE__EDGES);
		}
		return edges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TGGCorrespondence> getCorrespondenceNodes() {
		if (correspondenceNodes == null) {
			correspondenceNodes = new EObjectResolvingEList<TGGCorrespondence>(TGGCorrespondence.class, this,
					IBeXTGGModelPackage.TGG_RULE__CORRESPONDENCE_NODES);
		}
		return correspondenceNodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TGGOperationalRule> getOperationalisations() {
		if (operationalisations == null) {
			operationalisations = new EObjectContainmentEList<TGGOperationalRule>(TGGOperationalRule.class, this,
					IBeXTGGModelPackage.TGG_RULE__OPERATIONALISATIONS);
		}
		return operationalisations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGAttributeConstraintSet getAttributeConstraints() {
		return attributeConstraints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAttributeConstraints(TGGAttributeConstraintSet newAttributeConstraints,
			NotificationChain msgs) {
		TGGAttributeConstraintSet oldAttributeConstraints = attributeConstraints;
		attributeConstraints = newAttributeConstraints;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXTGGModelPackage.TGG_RULE__ATTRIBUTE_CONSTRAINTS, oldAttributeConstraints,
					newAttributeConstraints);
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
	public void setAttributeConstraints(TGGAttributeConstraintSet newAttributeConstraints) {
		if (newAttributeConstraints != attributeConstraints) {
			NotificationChain msgs = null;
			if (attributeConstraints != null)
				msgs = ((InternalEObject) attributeConstraints).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_RULE__ATTRIBUTE_CONSTRAINTS, null, msgs);
			if (newAttributeConstraints != null)
				msgs = ((InternalEObject) newAttributeConstraints).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_RULE__ATTRIBUTE_CONSTRAINTS, null, msgs);
			msgs = basicSetAttributeConstraints(newAttributeConstraints, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXTGGModelPackage.TGG_RULE__ATTRIBUTE_CONSTRAINTS,
					newAttributeConstraints, newAttributeConstraints));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IBeXTGGModelPackage.TGG_RULE__OPERATIONALISATIONS:
			return ((InternalEList<?>) getOperationalisations()).basicRemove(otherEnd, msgs);
		case IBeXTGGModelPackage.TGG_RULE__ATTRIBUTE_CONSTRAINTS:
			return basicSetAttributeConstraints(null, msgs);
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
		case IBeXTGGModelPackage.TGG_RULE__NODES:
			return getNodes();
		case IBeXTGGModelPackage.TGG_RULE__EDGES:
			return getEdges();
		case IBeXTGGModelPackage.TGG_RULE__CORRESPONDENCE_NODES:
			return getCorrespondenceNodes();
		case IBeXTGGModelPackage.TGG_RULE__OPERATIONALISATIONS:
			return getOperationalisations();
		case IBeXTGGModelPackage.TGG_RULE__ATTRIBUTE_CONSTRAINTS:
			return getAttributeConstraints();
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
		case IBeXTGGModelPackage.TGG_RULE__NODES:
			getNodes().clear();
			getNodes().addAll((Collection<? extends TGGNode>) newValue);
			return;
		case IBeXTGGModelPackage.TGG_RULE__EDGES:
			getEdges().clear();
			getEdges().addAll((Collection<? extends TGGEdge>) newValue);
			return;
		case IBeXTGGModelPackage.TGG_RULE__CORRESPONDENCE_NODES:
			getCorrespondenceNodes().clear();
			getCorrespondenceNodes().addAll((Collection<? extends TGGCorrespondence>) newValue);
			return;
		case IBeXTGGModelPackage.TGG_RULE__OPERATIONALISATIONS:
			getOperationalisations().clear();
			getOperationalisations().addAll((Collection<? extends TGGOperationalRule>) newValue);
			return;
		case IBeXTGGModelPackage.TGG_RULE__ATTRIBUTE_CONSTRAINTS:
			setAttributeConstraints((TGGAttributeConstraintSet) newValue);
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
		case IBeXTGGModelPackage.TGG_RULE__NODES:
			getNodes().clear();
			return;
		case IBeXTGGModelPackage.TGG_RULE__EDGES:
			getEdges().clear();
			return;
		case IBeXTGGModelPackage.TGG_RULE__CORRESPONDENCE_NODES:
			getCorrespondenceNodes().clear();
			return;
		case IBeXTGGModelPackage.TGG_RULE__OPERATIONALISATIONS:
			getOperationalisations().clear();
			return;
		case IBeXTGGModelPackage.TGG_RULE__ATTRIBUTE_CONSTRAINTS:
			setAttributeConstraints((TGGAttributeConstraintSet) null);
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
		case IBeXTGGModelPackage.TGG_RULE__NODES:
			return nodes != null && !nodes.isEmpty();
		case IBeXTGGModelPackage.TGG_RULE__EDGES:
			return edges != null && !edges.isEmpty();
		case IBeXTGGModelPackage.TGG_RULE__CORRESPONDENCE_NODES:
			return correspondenceNodes != null && !correspondenceNodes.isEmpty();
		case IBeXTGGModelPackage.TGG_RULE__OPERATIONALISATIONS:
			return operationalisations != null && !operationalisations.isEmpty();
		case IBeXTGGModelPackage.TGG_RULE__ATTRIBUTE_CONSTRAINTS:
			return attributeConstraints != null;
		}
		return super.eIsSet(featureID);
	}

} //TGGRuleImpl
