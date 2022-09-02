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

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.RelationalExpression;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXInjectivityConstraint;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IBe XPattern</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXPatternImpl#isCountRequired <em>Count Required</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXPatternImpl#getDependencies <em>Dependencies</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXPatternImpl#getSignatureNodes <em>Signature Nodes</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXPatternImpl#getLocalNodes <em>Local Nodes</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXPatternImpl#getEdges <em>Edges</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXPatternImpl#getAttributeConstraints <em>Attribute Constraints</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXPatternImpl#getInjectivityConstraints <em>Injectivity Constraints</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IBeXPatternImpl extends IBeXNamedElementImpl implements IBeXPattern {
	/**
	 * The default value of the '{@link #isCountRequired() <em>Count Required</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCountRequired()
	 * @generated
	 * @ordered
	 */
	protected static final boolean COUNT_REQUIRED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isCountRequired() <em>Count Required</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCountRequired()
	 * @generated
	 * @ordered
	 */
	protected boolean countRequired = COUNT_REQUIRED_EDEFAULT;

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
	 * The cached value of the '{@link #getAttributeConstraints() <em>Attribute Constraints</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeConstraints()
	 * @generated
	 * @ordered
	 */
	protected EList<RelationalExpression> attributeConstraints;

	/**
	 * The cached value of the '{@link #getInjectivityConstraints() <em>Injectivity Constraints</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInjectivityConstraints()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXInjectivityConstraint> injectivityConstraints;

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
	public boolean isCountRequired() {
		return countRequired;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCountRequired(boolean newCountRequired) {
		boolean oldCountRequired = countRequired;
		countRequired = newCountRequired;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXCoreModelPackage.IBE_XPATTERN__COUNT_REQUIRED,
					oldCountRequired, countRequired));
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
	public EList<RelationalExpression> getAttributeConstraints() {
		if (attributeConstraints == null) {
			attributeConstraints = new EObjectContainmentEList<RelationalExpression>(RelationalExpression.class, this,
					IBeXCoreModelPackage.IBE_XPATTERN__ATTRIBUTE_CONSTRAINTS);
		}
		return attributeConstraints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<IBeXInjectivityConstraint> getInjectivityConstraints() {
		if (injectivityConstraints == null) {
			injectivityConstraints = new EObjectContainmentEList<IBeXInjectivityConstraint>(
					IBeXInjectivityConstraint.class, this, IBeXCoreModelPackage.IBE_XPATTERN__INJECTIVITY_CONSTRAINTS);
		}
		return injectivityConstraints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IBeXCoreModelPackage.IBE_XPATTERN__ATTRIBUTE_CONSTRAINTS:
			return ((InternalEList<?>) getAttributeConstraints()).basicRemove(otherEnd, msgs);
		case IBeXCoreModelPackage.IBE_XPATTERN__INJECTIVITY_CONSTRAINTS:
			return ((InternalEList<?>) getInjectivityConstraints()).basicRemove(otherEnd, msgs);
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
		case IBeXCoreModelPackage.IBE_XPATTERN__COUNT_REQUIRED:
			return isCountRequired();
		case IBeXCoreModelPackage.IBE_XPATTERN__DEPENDENCIES:
			return getDependencies();
		case IBeXCoreModelPackage.IBE_XPATTERN__SIGNATURE_NODES:
			return getSignatureNodes();
		case IBeXCoreModelPackage.IBE_XPATTERN__LOCAL_NODES:
			return getLocalNodes();
		case IBeXCoreModelPackage.IBE_XPATTERN__EDGES:
			return getEdges();
		case IBeXCoreModelPackage.IBE_XPATTERN__ATTRIBUTE_CONSTRAINTS:
			return getAttributeConstraints();
		case IBeXCoreModelPackage.IBE_XPATTERN__INJECTIVITY_CONSTRAINTS:
			return getInjectivityConstraints();
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
		case IBeXCoreModelPackage.IBE_XPATTERN__COUNT_REQUIRED:
			setCountRequired((Boolean) newValue);
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
		case IBeXCoreModelPackage.IBE_XPATTERN__ATTRIBUTE_CONSTRAINTS:
			getAttributeConstraints().clear();
			getAttributeConstraints().addAll((Collection<? extends RelationalExpression>) newValue);
			return;
		case IBeXCoreModelPackage.IBE_XPATTERN__INJECTIVITY_CONSTRAINTS:
			getInjectivityConstraints().clear();
			getInjectivityConstraints().addAll((Collection<? extends IBeXInjectivityConstraint>) newValue);
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
		case IBeXCoreModelPackage.IBE_XPATTERN__COUNT_REQUIRED:
			setCountRequired(COUNT_REQUIRED_EDEFAULT);
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
		case IBeXCoreModelPackage.IBE_XPATTERN__ATTRIBUTE_CONSTRAINTS:
			getAttributeConstraints().clear();
			return;
		case IBeXCoreModelPackage.IBE_XPATTERN__INJECTIVITY_CONSTRAINTS:
			getInjectivityConstraints().clear();
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
		case IBeXCoreModelPackage.IBE_XPATTERN__COUNT_REQUIRED:
			return countRequired != COUNT_REQUIRED_EDEFAULT;
		case IBeXCoreModelPackage.IBE_XPATTERN__DEPENDENCIES:
			return dependencies != null && !dependencies.isEmpty();
		case IBeXCoreModelPackage.IBE_XPATTERN__SIGNATURE_NODES:
			return signatureNodes != null && !signatureNodes.isEmpty();
		case IBeXCoreModelPackage.IBE_XPATTERN__LOCAL_NODES:
			return localNodes != null && !localNodes.isEmpty();
		case IBeXCoreModelPackage.IBE_XPATTERN__EDGES:
			return edges != null && !edges.isEmpty();
		case IBeXCoreModelPackage.IBE_XPATTERN__ATTRIBUTE_CONSTRAINTS:
			return attributeConstraints != null && !attributeConstraints.isEmpty();
		case IBeXCoreModelPackage.IBE_XPATTERN__INJECTIVITY_CONSTRAINTS:
			return injectivityConstraints != null && !injectivityConstraints.isEmpty();
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
		result.append(" (countRequired: ");
		result.append(countRequired);
		result.append(')');
		return result.toString();
	}

} //IBeXPatternImpl
