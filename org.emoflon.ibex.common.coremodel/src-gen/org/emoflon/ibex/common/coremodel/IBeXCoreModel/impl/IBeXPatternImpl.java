/**
 */
package org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl;

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

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.BooleanExpression;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternInvocation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IBe XPattern</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXPatternImpl#isEmpty <em>Empty</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXPatternImpl#getDependencies <em>Dependencies</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXPatternImpl#getSignatureNodes <em>Signature Nodes</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXPatternImpl#getLocalNodes <em>Local Nodes</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXPatternImpl#getEdges <em>Edges</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXPatternImpl#getConditions <em>Conditions</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXPatternImpl#getInvocations <em>Invocations</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IBeXPatternImpl extends IBeXNamedElementImpl implements IBeXPattern {
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
	 * The cached value of the '{@link #getDependencies() <em>Dependencies</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDependencies()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXPattern> dependencies;

	/**
	 * The cached value of the '{@link #getSignatureNodes() <em>Signature Nodes</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSignatureNodes()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXNode> signatureNodes;

	/**
	 * The cached value of the '{@link #getLocalNodes() <em>Local Nodes</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocalNodes()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXNode> localNodes;

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
	 * The cached value of the '{@link #getConditions() <em>Conditions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConditions()
	 * @generated
	 * @ordered
	 */
	protected EList<BooleanExpression> conditions;

	/**
	 * The cached value of the '{@link #getInvocations() <em>Invocations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInvocations()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXPatternInvocation> invocations;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IBeXPatternImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXCoreModelPackage.Literals.IBE_XPATTERN;
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
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXCoreModelPackage.IBE_XPATTERN__EMPTY, oldEmpty,
					empty));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<IBeXPattern> getDependencies() {
		if (dependencies == null) {
			dependencies = new EObjectResolvingEList<IBeXPattern>(IBeXPattern.class, this,
					IBeXCoreModelPackage.IBE_XPATTERN__DEPENDENCIES);
		}
		return dependencies;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<IBeXNode> getSignatureNodes() {
		if (signatureNodes == null) {
			signatureNodes = new EObjectResolvingEList<IBeXNode>(IBeXNode.class, this,
					IBeXCoreModelPackage.IBE_XPATTERN__SIGNATURE_NODES);
		}
		return signatureNodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<IBeXNode> getLocalNodes() {
		if (localNodes == null) {
			localNodes = new EObjectResolvingEList<IBeXNode>(IBeXNode.class, this,
					IBeXCoreModelPackage.IBE_XPATTERN__LOCAL_NODES);
		}
		return localNodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<IBeXEdge> getEdges() {
		if (edges == null) {
			edges = new EObjectResolvingEList<IBeXEdge>(IBeXEdge.class, this, IBeXCoreModelPackage.IBE_XPATTERN__EDGES);
		}
		return edges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<BooleanExpression> getConditions() {
		if (conditions == null) {
			conditions = new EObjectContainmentEList<BooleanExpression>(BooleanExpression.class, this,
					IBeXCoreModelPackage.IBE_XPATTERN__CONDITIONS);
		}
		return conditions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<IBeXPatternInvocation> getInvocations() {
		if (invocations == null) {
			invocations = new EObjectContainmentEList<IBeXPatternInvocation>(IBeXPatternInvocation.class, this,
					IBeXCoreModelPackage.IBE_XPATTERN__INVOCATIONS);
		}
		return invocations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IBeXCoreModelPackage.IBE_XPATTERN__CONDITIONS:
			return ((InternalEList<?>) getConditions()).basicRemove(otherEnd, msgs);
		case IBeXCoreModelPackage.IBE_XPATTERN__INVOCATIONS:
			return ((InternalEList<?>) getInvocations()).basicRemove(otherEnd, msgs);
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
		case IBeXCoreModelPackage.IBE_XPATTERN__EMPTY:
			return isEmpty();
		case IBeXCoreModelPackage.IBE_XPATTERN__DEPENDENCIES:
			return getDependencies();
		case IBeXCoreModelPackage.IBE_XPATTERN__SIGNATURE_NODES:
			return getSignatureNodes();
		case IBeXCoreModelPackage.IBE_XPATTERN__LOCAL_NODES:
			return getLocalNodes();
		case IBeXCoreModelPackage.IBE_XPATTERN__EDGES:
			return getEdges();
		case IBeXCoreModelPackage.IBE_XPATTERN__CONDITIONS:
			return getConditions();
		case IBeXCoreModelPackage.IBE_XPATTERN__INVOCATIONS:
			return getInvocations();
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
		case IBeXCoreModelPackage.IBE_XPATTERN__EMPTY:
			setEmpty((Boolean) newValue);
			return;
		case IBeXCoreModelPackage.IBE_XPATTERN__DEPENDENCIES:
			getDependencies().clear();
			getDependencies().addAll((Collection<? extends IBeXPattern>) newValue);
			return;
		case IBeXCoreModelPackage.IBE_XPATTERN__SIGNATURE_NODES:
			getSignatureNodes().clear();
			getSignatureNodes().addAll((Collection<? extends IBeXNode>) newValue);
			return;
		case IBeXCoreModelPackage.IBE_XPATTERN__LOCAL_NODES:
			getLocalNodes().clear();
			getLocalNodes().addAll((Collection<? extends IBeXNode>) newValue);
			return;
		case IBeXCoreModelPackage.IBE_XPATTERN__EDGES:
			getEdges().clear();
			getEdges().addAll((Collection<? extends IBeXEdge>) newValue);
			return;
		case IBeXCoreModelPackage.IBE_XPATTERN__CONDITIONS:
			getConditions().clear();
			getConditions().addAll((Collection<? extends BooleanExpression>) newValue);
			return;
		case IBeXCoreModelPackage.IBE_XPATTERN__INVOCATIONS:
			getInvocations().clear();
			getInvocations().addAll((Collection<? extends IBeXPatternInvocation>) newValue);
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
		case IBeXCoreModelPackage.IBE_XPATTERN__EMPTY:
			setEmpty(EMPTY_EDEFAULT);
			return;
		case IBeXCoreModelPackage.IBE_XPATTERN__DEPENDENCIES:
			getDependencies().clear();
			return;
		case IBeXCoreModelPackage.IBE_XPATTERN__SIGNATURE_NODES:
			getSignatureNodes().clear();
			return;
		case IBeXCoreModelPackage.IBE_XPATTERN__LOCAL_NODES:
			getLocalNodes().clear();
			return;
		case IBeXCoreModelPackage.IBE_XPATTERN__EDGES:
			getEdges().clear();
			return;
		case IBeXCoreModelPackage.IBE_XPATTERN__CONDITIONS:
			getConditions().clear();
			return;
		case IBeXCoreModelPackage.IBE_XPATTERN__INVOCATIONS:
			getInvocations().clear();
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
		case IBeXCoreModelPackage.IBE_XPATTERN__EMPTY:
			return empty != EMPTY_EDEFAULT;
		case IBeXCoreModelPackage.IBE_XPATTERN__DEPENDENCIES:
			return dependencies != null && !dependencies.isEmpty();
		case IBeXCoreModelPackage.IBE_XPATTERN__SIGNATURE_NODES:
			return signatureNodes != null && !signatureNodes.isEmpty();
		case IBeXCoreModelPackage.IBE_XPATTERN__LOCAL_NODES:
			return localNodes != null && !localNodes.isEmpty();
		case IBeXCoreModelPackage.IBE_XPATTERN__EDGES:
			return edges != null && !edges.isEmpty();
		case IBeXCoreModelPackage.IBE_XPATTERN__CONDITIONS:
			return conditions != null && !conditions.isEmpty();
		case IBeXCoreModelPackage.IBE_XPATTERN__INVOCATIONS:
			return invocations != null && !invocations.isEmpty();
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

} //IBeXPatternImpl
