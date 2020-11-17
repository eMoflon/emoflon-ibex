/**
 */
package org.emoflon.ibex.patternmodel.IBeXPatternModel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticConstraint;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeConstraint;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCSP;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEdge;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXInjectivityConstraint;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXParameter;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternInvocation;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IBe XContext Pattern</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXContextPatternImpl#getDocumentation <em>Documentation</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXContextPatternImpl#getAttributeConstraint <em>Attribute Constraint</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXContextPatternImpl#getArithmeticConstraints <em>Arithmetic Constraints</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXContextPatternImpl#getInjectivityConstraints <em>Injectivity Constraints</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXContextPatternImpl#getInvocations <em>Invocations</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXContextPatternImpl#getLocalEdges <em>Local Edges</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXContextPatternImpl#getLocalNodes <em>Local Nodes</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXContextPatternImpl#getSignatureNodes <em>Signature Nodes</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXContextPatternImpl#getCsps <em>Csps</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXContextPatternImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXContextPatternImpl#isOptimizedDisjoint <em>Optimized Disjoint</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IBeXContextPatternImpl extends IBeXContextImpl implements IBeXContextPattern {
	/**
	 * The default value of the '{@link #getDocumentation() <em>Documentation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDocumentation()
	 * @generated
	 * @ordered
	 */
	protected static final String DOCUMENTATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDocumentation() <em>Documentation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDocumentation()
	 * @generated
	 * @ordered
	 */
	protected String documentation = DOCUMENTATION_EDEFAULT;

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
	 * The cached value of the '{@link #getArithmeticConstraints() <em>Arithmetic Constraints</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArithmeticConstraints()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXArithmeticConstraint> arithmeticConstraints;

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
	 * The cached value of the '{@link #getInvocations() <em>Invocations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInvocations()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXPatternInvocation> invocations;

	/**
	 * The cached value of the '{@link #getLocalEdges() <em>Local Edges</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocalEdges()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXEdge> localEdges;

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
	 * The cached value of the '{@link #getSignatureNodes() <em>Signature Nodes</em>}' reference list.
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
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXParameter> parameters;

	/**
	 * The default value of the '{@link #isOptimizedDisjoint() <em>Optimized Disjoint</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isOptimizedDisjoint()
	 * @generated
	 * @ordered
	 */
	protected static final boolean OPTIMIZED_DISJOINT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isOptimizedDisjoint() <em>Optimized Disjoint</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isOptimizedDisjoint()
	 * @generated
	 * @ordered
	 */
	protected boolean optimizedDisjoint = OPTIMIZED_DISJOINT_EDEFAULT;

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
		return IBeXPatternModelPackage.Literals.IBE_XCONTEXT_PATTERN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getDocumentation() {
		return documentation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDocumentation(String newDocumentation) {
		String oldDocumentation = documentation;
		documentation = newDocumentation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__DOCUMENTATION, oldDocumentation, documentation));
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
					this, IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__ATTRIBUTE_CONSTRAINT);
		}
		return attributeConstraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXArithmeticConstraint> getArithmeticConstraints() {
		if (arithmeticConstraints == null) {
			arithmeticConstraints = new EObjectContainmentEList<IBeXArithmeticConstraint>(
					IBeXArithmeticConstraint.class, this,
					IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__ARITHMETIC_CONSTRAINTS);
		}
		return arithmeticConstraints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXInjectivityConstraint> getInjectivityConstraints() {
		if (injectivityConstraints == null) {
			injectivityConstraints = new EObjectContainmentEList<IBeXInjectivityConstraint>(
					IBeXInjectivityConstraint.class, this,
					IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__INJECTIVITY_CONSTRAINTS);
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
					this, IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__INVOCATIONS,
					IBeXPatternModelPackage.IBE_XPATTERN_INVOCATION__INVOKED_BY);
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
			localEdges = new EObjectResolvingEList<IBeXEdge>(IBeXEdge.class, this,
					IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__LOCAL_EDGES);
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
			localNodes = new EObjectResolvingEList<IBeXNode>(IBeXNode.class, this,
					IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__LOCAL_NODES);
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
			signatureNodes = new EObjectResolvingEList<IBeXNode>(IBeXNode.class, this,
					IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__SIGNATURE_NODES);
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
					IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__CSPS);
		}
		return csps;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXParameter> getParameters() {
		if (parameters == null) {
			parameters = new EObjectContainmentEList<IBeXParameter>(IBeXParameter.class, this,
					IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__PARAMETERS);
		}
		return parameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isOptimizedDisjoint() {
		return optimizedDisjoint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOptimizedDisjoint(boolean newOptimizedDisjoint) {
		boolean oldOptimizedDisjoint = optimizedDisjoint;
		optimizedDisjoint = newOptimizedDisjoint;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__OPTIMIZED_DISJOINT, oldOptimizedDisjoint,
					optimizedDisjoint));
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
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__INVOCATIONS:
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
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__ATTRIBUTE_CONSTRAINT:
			return ((InternalEList<?>) getAttributeConstraint()).basicRemove(otherEnd, msgs);
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__ARITHMETIC_CONSTRAINTS:
			return ((InternalEList<?>) getArithmeticConstraints()).basicRemove(otherEnd, msgs);
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__INJECTIVITY_CONSTRAINTS:
			return ((InternalEList<?>) getInjectivityConstraints()).basicRemove(otherEnd, msgs);
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__INVOCATIONS:
			return ((InternalEList<?>) getInvocations()).basicRemove(otherEnd, msgs);
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__CSPS:
			return ((InternalEList<?>) getCsps()).basicRemove(otherEnd, msgs);
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__PARAMETERS:
			return ((InternalEList<?>) getParameters()).basicRemove(otherEnd, msgs);
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
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__DOCUMENTATION:
			return getDocumentation();
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__ATTRIBUTE_CONSTRAINT:
			return getAttributeConstraint();
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__ARITHMETIC_CONSTRAINTS:
			return getArithmeticConstraints();
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__INJECTIVITY_CONSTRAINTS:
			return getInjectivityConstraints();
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__INVOCATIONS:
			return getInvocations();
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__LOCAL_EDGES:
			return getLocalEdges();
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__LOCAL_NODES:
			return getLocalNodes();
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__SIGNATURE_NODES:
			return getSignatureNodes();
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__CSPS:
			return getCsps();
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__PARAMETERS:
			return getParameters();
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__OPTIMIZED_DISJOINT:
			return isOptimizedDisjoint();
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
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__DOCUMENTATION:
			setDocumentation((String) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__ATTRIBUTE_CONSTRAINT:
			getAttributeConstraint().clear();
			getAttributeConstraint().addAll((Collection<? extends IBeXAttributeConstraint>) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__ARITHMETIC_CONSTRAINTS:
			getArithmeticConstraints().clear();
			getArithmeticConstraints().addAll((Collection<? extends IBeXArithmeticConstraint>) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__INJECTIVITY_CONSTRAINTS:
			getInjectivityConstraints().clear();
			getInjectivityConstraints().addAll((Collection<? extends IBeXInjectivityConstraint>) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__INVOCATIONS:
			getInvocations().clear();
			getInvocations().addAll((Collection<? extends IBeXPatternInvocation>) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__LOCAL_EDGES:
			getLocalEdges().clear();
			getLocalEdges().addAll((Collection<? extends IBeXEdge>) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__LOCAL_NODES:
			getLocalNodes().clear();
			getLocalNodes().addAll((Collection<? extends IBeXNode>) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__SIGNATURE_NODES:
			getSignatureNodes().clear();
			getSignatureNodes().addAll((Collection<? extends IBeXNode>) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__CSPS:
			getCsps().clear();
			getCsps().addAll((Collection<? extends IBeXCSP>) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__PARAMETERS:
			getParameters().clear();
			getParameters().addAll((Collection<? extends IBeXParameter>) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__OPTIMIZED_DISJOINT:
			setOptimizedDisjoint((Boolean) newValue);
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
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__DOCUMENTATION:
			setDocumentation(DOCUMENTATION_EDEFAULT);
			return;
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__ATTRIBUTE_CONSTRAINT:
			getAttributeConstraint().clear();
			return;
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__ARITHMETIC_CONSTRAINTS:
			getArithmeticConstraints().clear();
			return;
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__INJECTIVITY_CONSTRAINTS:
			getInjectivityConstraints().clear();
			return;
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__INVOCATIONS:
			getInvocations().clear();
			return;
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__LOCAL_EDGES:
			getLocalEdges().clear();
			return;
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__LOCAL_NODES:
			getLocalNodes().clear();
			return;
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__SIGNATURE_NODES:
			getSignatureNodes().clear();
			return;
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__CSPS:
			getCsps().clear();
			return;
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__PARAMETERS:
			getParameters().clear();
			return;
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__OPTIMIZED_DISJOINT:
			setOptimizedDisjoint(OPTIMIZED_DISJOINT_EDEFAULT);
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
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__DOCUMENTATION:
			return DOCUMENTATION_EDEFAULT == null ? documentation != null
					: !DOCUMENTATION_EDEFAULT.equals(documentation);
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__ATTRIBUTE_CONSTRAINT:
			return attributeConstraint != null && !attributeConstraint.isEmpty();
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__ARITHMETIC_CONSTRAINTS:
			return arithmeticConstraints != null && !arithmeticConstraints.isEmpty();
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__INJECTIVITY_CONSTRAINTS:
			return injectivityConstraints != null && !injectivityConstraints.isEmpty();
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__INVOCATIONS:
			return invocations != null && !invocations.isEmpty();
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__LOCAL_EDGES:
			return localEdges != null && !localEdges.isEmpty();
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__LOCAL_NODES:
			return localNodes != null && !localNodes.isEmpty();
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__SIGNATURE_NODES:
			return signatureNodes != null && !signatureNodes.isEmpty();
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__CSPS:
			return csps != null && !csps.isEmpty();
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__PARAMETERS:
			return parameters != null && !parameters.isEmpty();
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__OPTIMIZED_DISJOINT:
			return optimizedDisjoint != OPTIMIZED_DISJOINT_EDEFAULT;
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
		result.append(" (documentation: ");
		result.append(documentation);
		result.append(", optimizedDisjoint: ");
		result.append(optimizedDisjoint);
		result.append(')');
		return result.toString();
	}

} //IBeXContextPatternImpl
