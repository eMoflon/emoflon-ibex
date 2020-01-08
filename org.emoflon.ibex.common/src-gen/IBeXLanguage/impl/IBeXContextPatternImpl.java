/**
 */
package IBeXLanguage.impl;

import IBeXLanguage.IBeXAttributeConstraint;
import IBeXLanguage.IBeXCSP;
import IBeXLanguage.IBeXContextPattern;
import IBeXLanguage.IBeXEdge;
import IBeXLanguage.IBeXLanguagePackage;
import IBeXLanguage.IBeXNode;
import IBeXLanguage.IBeXNodePair;
import IBeXLanguage.IBeXPatternInvocation;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IBe XContext Pattern</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link IBeXLanguage.impl.IBeXContextPatternImpl#getAttributeConstraint <em>Attribute Constraint</em>}</li>
 *   <li>{@link IBeXLanguage.impl.IBeXContextPatternImpl#getInjectivityConstraints <em>Injectivity Constraints</em>}</li>
 *   <li>{@link IBeXLanguage.impl.IBeXContextPatternImpl#getInvocations <em>Invocations</em>}</li>
 *   <li>{@link IBeXLanguage.impl.IBeXContextPatternImpl#getLocalEdges <em>Local Edges</em>}</li>
 *   <li>{@link IBeXLanguage.impl.IBeXContextPatternImpl#getLocalNodes <em>Local Nodes</em>}</li>
 *   <li>{@link IBeXLanguage.impl.IBeXContextPatternImpl#getSignatureNodes <em>Signature Nodes</em>}</li>
 *   <li>{@link IBeXLanguage.impl.IBeXContextPatternImpl#getCsps <em>Csps</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IBeXContextPatternImpl extends IBeXContextImpl implements IBeXContextPattern {
	/**
	 * The cached value of the '{@link #getAttributeConstraint() <em>Attribute Constraint</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeConstraint()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXAttributeConstraint> attributeConstraint;

	/**
	 * The cached value of the '{@link #getInjectivityConstraints() <em>Injectivity Constraints</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInjectivityConstraints()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXNodePair> injectivityConstraints;

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
	 * The cached value of the '{@link #getLocalEdges() <em>Local Edges</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocalEdges()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXEdge> localEdges;

	/**
	 * The cached value of the '{@link #getLocalNodes() <em>Local Nodes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocalNodes()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXNode> localNodes;

	/**
	 * The cached value of the '{@link #getSignatureNodes() <em>Signature Nodes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSignatureNodes()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXNode> signatureNodes;

	/**
	 * The cached value of the '{@link #getCsps() <em>Csps</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCsps()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXCSP> csps;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IBeXContextPatternImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXLanguagePackage.Literals.IBE_XCONTEXT_PATTERN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXAttributeConstraint> getAttributeConstraint() {
		if (attributeConstraint == null) {
			attributeConstraint = new EObjectContainmentEList<IBeXAttributeConstraint>(IBeXAttributeConstraint.class,
					this, IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__ATTRIBUTE_CONSTRAINT);
		}
		return attributeConstraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXNodePair> getInjectivityConstraints() {
		if (injectivityConstraints == null) {
			injectivityConstraints = new EObjectContainmentEList<IBeXNodePair>(IBeXNodePair.class, this,
					IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__INJECTIVITY_CONSTRAINTS);
		}
		return injectivityConstraints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXPatternInvocation> getInvocations() {
		if (invocations == null) {
			invocations = new EObjectContainmentWithInverseEList<IBeXPatternInvocation>(IBeXPatternInvocation.class,
					this, IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__INVOCATIONS,
					IBeXLanguagePackage.IBE_XPATTERN_INVOCATION__INVOKED_BY);
		}
		return invocations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXEdge> getLocalEdges() {
		if (localEdges == null) {
			localEdges = new EObjectContainmentEList<IBeXEdge>(IBeXEdge.class, this,
					IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__LOCAL_EDGES);
		}
		return localEdges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXNode> getLocalNodes() {
		if (localNodes == null) {
			localNodes = new EObjectContainmentEList<IBeXNode>(IBeXNode.class, this,
					IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__LOCAL_NODES);
		}
		return localNodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXNode> getSignatureNodes() {
		if (signatureNodes == null) {
			signatureNodes = new EObjectContainmentEList<IBeXNode>(IBeXNode.class, this,
					IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__SIGNATURE_NODES);
		}
		return signatureNodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXCSP> getCsps() {
		if (csps == null) {
			csps = new EObjectContainmentEList<IBeXCSP>(IBeXCSP.class, this,
					IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__CSPS);
		}
		return csps;
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
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__INVOCATIONS:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getInvocations()).basicAdd(otherEnd, msgs);
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
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__ATTRIBUTE_CONSTRAINT:
			return ((InternalEList<?>) getAttributeConstraint()).basicRemove(otherEnd, msgs);
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__INJECTIVITY_CONSTRAINTS:
			return ((InternalEList<?>) getInjectivityConstraints()).basicRemove(otherEnd, msgs);
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__INVOCATIONS:
			return ((InternalEList<?>) getInvocations()).basicRemove(otherEnd, msgs);
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__LOCAL_EDGES:
			return ((InternalEList<?>) getLocalEdges()).basicRemove(otherEnd, msgs);
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__LOCAL_NODES:
			return ((InternalEList<?>) getLocalNodes()).basicRemove(otherEnd, msgs);
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__SIGNATURE_NODES:
			return ((InternalEList<?>) getSignatureNodes()).basicRemove(otherEnd, msgs);
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__CSPS:
			return ((InternalEList<?>) getCsps()).basicRemove(otherEnd, msgs);
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
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__ATTRIBUTE_CONSTRAINT:
			return getAttributeConstraint();
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__INJECTIVITY_CONSTRAINTS:
			return getInjectivityConstraints();
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__INVOCATIONS:
			return getInvocations();
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__LOCAL_EDGES:
			return getLocalEdges();
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__LOCAL_NODES:
			return getLocalNodes();
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__SIGNATURE_NODES:
			return getSignatureNodes();
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__CSPS:
			return getCsps();
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
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__ATTRIBUTE_CONSTRAINT:
			getAttributeConstraint().clear();
			getAttributeConstraint().addAll((Collection<? extends IBeXAttributeConstraint>) newValue);
			return;
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__INJECTIVITY_CONSTRAINTS:
			getInjectivityConstraints().clear();
			getInjectivityConstraints().addAll((Collection<? extends IBeXNodePair>) newValue);
			return;
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__INVOCATIONS:
			getInvocations().clear();
			getInvocations().addAll((Collection<? extends IBeXPatternInvocation>) newValue);
			return;
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__LOCAL_EDGES:
			getLocalEdges().clear();
			getLocalEdges().addAll((Collection<? extends IBeXEdge>) newValue);
			return;
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__LOCAL_NODES:
			getLocalNodes().clear();
			getLocalNodes().addAll((Collection<? extends IBeXNode>) newValue);
			return;
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__SIGNATURE_NODES:
			getSignatureNodes().clear();
			getSignatureNodes().addAll((Collection<? extends IBeXNode>) newValue);
			return;
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__CSPS:
			getCsps().clear();
			getCsps().addAll((Collection<? extends IBeXCSP>) newValue);
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
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__ATTRIBUTE_CONSTRAINT:
			getAttributeConstraint().clear();
			return;
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__INJECTIVITY_CONSTRAINTS:
			getInjectivityConstraints().clear();
			return;
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__INVOCATIONS:
			getInvocations().clear();
			return;
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__LOCAL_EDGES:
			getLocalEdges().clear();
			return;
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__LOCAL_NODES:
			getLocalNodes().clear();
			return;
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__SIGNATURE_NODES:
			getSignatureNodes().clear();
			return;
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__CSPS:
			getCsps().clear();
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
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__ATTRIBUTE_CONSTRAINT:
			return attributeConstraint != null && !attributeConstraint.isEmpty();
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__INJECTIVITY_CONSTRAINTS:
			return injectivityConstraints != null && !injectivityConstraints.isEmpty();
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__INVOCATIONS:
			return invocations != null && !invocations.isEmpty();
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__LOCAL_EDGES:
			return localEdges != null && !localEdges.isEmpty();
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__LOCAL_NODES:
			return localNodes != null && !localNodes.isEmpty();
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__SIGNATURE_NODES:
			return signatureNodes != null && !signatureNodes.isEmpty();
		case IBeXLanguagePackage.IBE_XCONTEXT_PATTERN__CSPS:
			return csps != null && !csps.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //IBeXContextPatternImpl
