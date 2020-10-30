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
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextTransitive;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXTransitiveEdge;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IBe XContext Transitive</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXContextTransitiveImpl#getBasePattern <em>Base Pattern</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXContextTransitiveImpl#getSubPatterns <em>Sub Patterns</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXContextTransitiveImpl#getTransitiveEdges <em>Transitive Edges</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IBeXContextTransitiveImpl extends IBeXContextImpl implements IBeXContextTransitive {
	/**
	 * The cached value of the '{@link #getBasePattern() <em>Base Pattern</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBasePattern()
	 * @generated
	 * @ordered
	 */
	protected IBeXContextPattern basePattern;

	/**
	 * The cached value of the '{@link #getSubPatterns() <em>Sub Patterns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubPatterns()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXContextPattern> subPatterns;

	/**
	 * The cached value of the '{@link #getTransitiveEdges() <em>Transitive Edges</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransitiveEdges()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXTransitiveEdge> transitiveEdges;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IBeXContextTransitiveImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXPatternModelPackage.Literals.IBE_XCONTEXT_TRANSITIVE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXContextPattern getBasePattern() {
		return basePattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBasePattern(IBeXContextPattern newBasePattern, NotificationChain msgs) {
		IBeXContextPattern oldBasePattern = basePattern;
		basePattern = newBasePattern;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXPatternModelPackage.IBE_XCONTEXT_TRANSITIVE__BASE_PATTERN, oldBasePattern, newBasePattern);
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
	@Override
	public void setBasePattern(IBeXContextPattern newBasePattern) {
		if (newBasePattern != basePattern) {
			NotificationChain msgs = null;
			if (basePattern != null)
				msgs = ((InternalEObject) basePattern).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXPatternModelPackage.IBE_XCONTEXT_TRANSITIVE__BASE_PATTERN, null,
						msgs);
			if (newBasePattern != null)
				msgs = ((InternalEObject) newBasePattern).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXPatternModelPackage.IBE_XCONTEXT_TRANSITIVE__BASE_PATTERN, null,
						msgs);
			msgs = basicSetBasePattern(newBasePattern, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXPatternModelPackage.IBE_XCONTEXT_TRANSITIVE__BASE_PATTERN, newBasePattern, newBasePattern));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXContextPattern> getSubPatterns() {
		if (subPatterns == null) {
			subPatterns = new EObjectContainmentEList<IBeXContextPattern>(IBeXContextPattern.class, this,
					IBeXPatternModelPackage.IBE_XCONTEXT_TRANSITIVE__SUB_PATTERNS);
		}
		return subPatterns;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXTransitiveEdge> getTransitiveEdges() {
		if (transitiveEdges == null) {
			transitiveEdges = new EObjectResolvingEList<IBeXTransitiveEdge>(IBeXTransitiveEdge.class, this,
					IBeXPatternModelPackage.IBE_XCONTEXT_TRANSITIVE__TRANSITIVE_EDGES);
		}
		return transitiveEdges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IBeXPatternModelPackage.IBE_XCONTEXT_TRANSITIVE__BASE_PATTERN:
			return basicSetBasePattern(null, msgs);
		case IBeXPatternModelPackage.IBE_XCONTEXT_TRANSITIVE__SUB_PATTERNS:
			return ((InternalEList<?>) getSubPatterns()).basicRemove(otherEnd, msgs);
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
		case IBeXPatternModelPackage.IBE_XCONTEXT_TRANSITIVE__BASE_PATTERN:
			return getBasePattern();
		case IBeXPatternModelPackage.IBE_XCONTEXT_TRANSITIVE__SUB_PATTERNS:
			return getSubPatterns();
		case IBeXPatternModelPackage.IBE_XCONTEXT_TRANSITIVE__TRANSITIVE_EDGES:
			return getTransitiveEdges();
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
		case IBeXPatternModelPackage.IBE_XCONTEXT_TRANSITIVE__BASE_PATTERN:
			setBasePattern((IBeXContextPattern) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XCONTEXT_TRANSITIVE__SUB_PATTERNS:
			getSubPatterns().clear();
			getSubPatterns().addAll((Collection<? extends IBeXContextPattern>) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XCONTEXT_TRANSITIVE__TRANSITIVE_EDGES:
			getTransitiveEdges().clear();
			getTransitiveEdges().addAll((Collection<? extends IBeXTransitiveEdge>) newValue);
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
		case IBeXPatternModelPackage.IBE_XCONTEXT_TRANSITIVE__BASE_PATTERN:
			setBasePattern((IBeXContextPattern) null);
			return;
		case IBeXPatternModelPackage.IBE_XCONTEXT_TRANSITIVE__SUB_PATTERNS:
			getSubPatterns().clear();
			return;
		case IBeXPatternModelPackage.IBE_XCONTEXT_TRANSITIVE__TRANSITIVE_EDGES:
			getTransitiveEdges().clear();
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
		case IBeXPatternModelPackage.IBE_XCONTEXT_TRANSITIVE__BASE_PATTERN:
			return basePattern != null;
		case IBeXPatternModelPackage.IBE_XCONTEXT_TRANSITIVE__SUB_PATTERNS:
			return subPatterns != null && !subPatterns.isEmpty();
		case IBeXPatternModelPackage.IBE_XCONTEXT_TRANSITIVE__TRANSITIVE_EDGES:
			return transitiveEdges != null && !transitiveEdges.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //IBeXContextTransitiveImpl
