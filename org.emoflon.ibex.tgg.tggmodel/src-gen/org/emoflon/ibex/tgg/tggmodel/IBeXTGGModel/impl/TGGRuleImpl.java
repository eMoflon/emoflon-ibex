/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXRuleImpl;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGEdge;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleCorrespondence;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TGG Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleImpl#getRefines <em>Refines</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleImpl#isAbstract <em>Abstract</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleImpl#getNodes <em>Nodes</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleImpl#getEdges <em>Edges</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleImpl#getCorrespondenceNodes <em>Correspondence Nodes</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TGGRuleImpl extends IBeXRuleImpl implements TGGRule {
	/**
	 * The cached value of the '{@link #getRefines() <em>Refines</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRefines()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGRule> refines;

	/**
	 * The default value of the '{@link #isAbstract() <em>Abstract</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAbstract()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ABSTRACT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAbstract() <em>Abstract</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAbstract()
	 * @generated
	 * @ordered
	 */
	protected boolean abstract_ = ABSTRACT_EDEFAULT;

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
	protected EList<TGGRuleCorrespondence> correspondenceNodes;

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
	public EList<TGGRule> getRefines() {
		if (refines == null) {
			refines = new EObjectResolvingEList<TGGRule>(TGGRule.class, this, IBeXTGGModelPackage.TGG_RULE__REFINES);
		}
		return refines;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAbstract() {
		return abstract_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAbstract(boolean newAbstract) {
		boolean oldAbstract = abstract_;
		abstract_ = newAbstract;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXTGGModelPackage.TGG_RULE__ABSTRACT, oldAbstract,
					abstract_));
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
	public EList<TGGRuleCorrespondence> getCorrespondenceNodes() {
		if (correspondenceNodes == null) {
			correspondenceNodes = new EObjectResolvingEList<TGGRuleCorrespondence>(TGGRuleCorrespondence.class, this,
					IBeXTGGModelPackage.TGG_RULE__CORRESPONDENCE_NODES);
		}
		return correspondenceNodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case IBeXTGGModelPackage.TGG_RULE__REFINES:
			return getRefines();
		case IBeXTGGModelPackage.TGG_RULE__ABSTRACT:
			return isAbstract();
		case IBeXTGGModelPackage.TGG_RULE__NODES:
			return getNodes();
		case IBeXTGGModelPackage.TGG_RULE__EDGES:
			return getEdges();
		case IBeXTGGModelPackage.TGG_RULE__CORRESPONDENCE_NODES:
			return getCorrespondenceNodes();
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
		case IBeXTGGModelPackage.TGG_RULE__REFINES:
			getRefines().clear();
			getRefines().addAll((Collection<? extends TGGRule>) newValue);
			return;
		case IBeXTGGModelPackage.TGG_RULE__ABSTRACT:
			setAbstract((Boolean) newValue);
			return;
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
			getCorrespondenceNodes().addAll((Collection<? extends TGGRuleCorrespondence>) newValue);
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
		case IBeXTGGModelPackage.TGG_RULE__REFINES:
			getRefines().clear();
			return;
		case IBeXTGGModelPackage.TGG_RULE__ABSTRACT:
			setAbstract(ABSTRACT_EDEFAULT);
			return;
		case IBeXTGGModelPackage.TGG_RULE__NODES:
			getNodes().clear();
			return;
		case IBeXTGGModelPackage.TGG_RULE__EDGES:
			getEdges().clear();
			return;
		case IBeXTGGModelPackage.TGG_RULE__CORRESPONDENCE_NODES:
			getCorrespondenceNodes().clear();
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
		case IBeXTGGModelPackage.TGG_RULE__REFINES:
			return refines != null && !refines.isEmpty();
		case IBeXTGGModelPackage.TGG_RULE__ABSTRACT:
			return abstract_ != ABSTRACT_EDEFAULT;
		case IBeXTGGModelPackage.TGG_RULE__NODES:
			return nodes != null && !nodes.isEmpty();
		case IBeXTGGModelPackage.TGG_RULE__EDGES:
			return edges != null && !edges.isEmpty();
		case IBeXTGGModelPackage.TGG_RULE__CORRESPONDENCE_NODES:
			return correspondenceNodes != null && !correspondenceNodes.isEmpty();
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
		result.append(" (abstract: ");
		result.append(abstract_);
		result.append(')');
		return result.toString();
	}

} //TGGRuleImpl
