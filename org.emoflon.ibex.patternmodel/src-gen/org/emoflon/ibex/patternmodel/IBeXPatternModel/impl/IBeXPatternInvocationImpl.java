/**
 */
package org.emoflon.ibex.patternmodel.IBeXPatternModel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternInvocation;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IBe XPattern Invocation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternInvocationImpl#isPositive <em>Positive</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternInvocationImpl#getInvokedBy <em>Invoked By</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternInvocationImpl#getInvokedPattern <em>Invoked Pattern</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternInvocationImpl#getMapping <em>Mapping</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IBeXPatternInvocationImpl extends MinimalEObjectImpl.Container implements IBeXPatternInvocation {
	/**
	 * The default value of the '{@link #isPositive() <em>Positive</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPositive()
	 * @generated
	 * @ordered
	 */
	protected static final boolean POSITIVE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isPositive() <em>Positive</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPositive()
	 * @generated
	 * @ordered
	 */
	protected boolean positive = POSITIVE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getInvokedPattern() <em>Invoked Pattern</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInvokedPattern()
	 * @generated
	 * @ordered
	 */
	protected IBeXContextPattern invokedPattern;

	/**
	 * The cached value of the '{@link #getMapping() <em>Mapping</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapping()
	 * @generated
	 * @ordered
	 */
	protected EMap<IBeXNode, IBeXNode> mapping;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IBeXPatternInvocationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXPatternModelPackage.Literals.IBE_XPATTERN_INVOCATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isPositive() {
		return positive;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPositive(boolean newPositive) {
		boolean oldPositive = positive;
		positive = newPositive;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXPatternModelPackage.IBE_XPATTERN_INVOCATION__POSITIVE, oldPositive, positive));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXContextPattern getInvokedBy() {
		if (eContainerFeatureID() != IBeXPatternModelPackage.IBE_XPATTERN_INVOCATION__INVOKED_BY)
			return null;
		return (IBeXContextPattern) eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInvokedBy(IBeXContextPattern newInvokedBy, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject) newInvokedBy,
				IBeXPatternModelPackage.IBE_XPATTERN_INVOCATION__INVOKED_BY, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setInvokedBy(IBeXContextPattern newInvokedBy) {
		if (newInvokedBy != eInternalContainer()
				|| (eContainerFeatureID() != IBeXPatternModelPackage.IBE_XPATTERN_INVOCATION__INVOKED_BY
						&& newInvokedBy != null)) {
			if (EcoreUtil.isAncestor(this, newInvokedBy))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newInvokedBy != null)
				msgs = ((InternalEObject) newInvokedBy).eInverseAdd(this,
						IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__INVOCATIONS, IBeXContextPattern.class, msgs);
			msgs = basicSetInvokedBy(newInvokedBy, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXPatternModelPackage.IBE_XPATTERN_INVOCATION__INVOKED_BY, newInvokedBy, newInvokedBy));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXContextPattern getInvokedPattern() {
		if (invokedPattern != null && invokedPattern.eIsProxy()) {
			InternalEObject oldInvokedPattern = (InternalEObject) invokedPattern;
			invokedPattern = (IBeXContextPattern) eResolveProxy(oldInvokedPattern);
			if (invokedPattern != oldInvokedPattern) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							IBeXPatternModelPackage.IBE_XPATTERN_INVOCATION__INVOKED_PATTERN, oldInvokedPattern,
							invokedPattern));
			}
		}
		return invokedPattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXContextPattern basicGetInvokedPattern() {
		return invokedPattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setInvokedPattern(IBeXContextPattern newInvokedPattern) {
		IBeXContextPattern oldInvokedPattern = invokedPattern;
		invokedPattern = newInvokedPattern;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXPatternModelPackage.IBE_XPATTERN_INVOCATION__INVOKED_PATTERN, oldInvokedPattern,
					invokedPattern));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EMap<IBeXNode, IBeXNode> getMapping() {
		if (mapping == null) {
			mapping = new EcoreEMap<IBeXNode, IBeXNode>(IBeXPatternModelPackage.Literals.IBE_XNODE_TO_NODE_MAPPING,
					IBeXNodeToNodeMappingImpl.class, this, IBeXPatternModelPackage.IBE_XPATTERN_INVOCATION__MAPPING);
		}
		return mapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IBeXPatternModelPackage.IBE_XPATTERN_INVOCATION__INVOKED_BY:
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			return basicSetInvokedBy((IBeXContextPattern) otherEnd, msgs);
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
		case IBeXPatternModelPackage.IBE_XPATTERN_INVOCATION__INVOKED_BY:
			return basicSetInvokedBy(null, msgs);
		case IBeXPatternModelPackage.IBE_XPATTERN_INVOCATION__MAPPING:
			return ((InternalEList<?>) getMapping()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
		case IBeXPatternModelPackage.IBE_XPATTERN_INVOCATION__INVOKED_BY:
			return eInternalContainer().eInverseRemove(this, IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN__INVOCATIONS,
					IBeXContextPattern.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case IBeXPatternModelPackage.IBE_XPATTERN_INVOCATION__POSITIVE:
			return isPositive();
		case IBeXPatternModelPackage.IBE_XPATTERN_INVOCATION__INVOKED_BY:
			return getInvokedBy();
		case IBeXPatternModelPackage.IBE_XPATTERN_INVOCATION__INVOKED_PATTERN:
			if (resolve)
				return getInvokedPattern();
			return basicGetInvokedPattern();
		case IBeXPatternModelPackage.IBE_XPATTERN_INVOCATION__MAPPING:
			if (coreType)
				return getMapping();
			else
				return getMapping().map();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case IBeXPatternModelPackage.IBE_XPATTERN_INVOCATION__POSITIVE:
			setPositive((Boolean) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XPATTERN_INVOCATION__INVOKED_BY:
			setInvokedBy((IBeXContextPattern) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XPATTERN_INVOCATION__INVOKED_PATTERN:
			setInvokedPattern((IBeXContextPattern) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XPATTERN_INVOCATION__MAPPING:
			((EStructuralFeature.Setting) getMapping()).set(newValue);
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
		case IBeXPatternModelPackage.IBE_XPATTERN_INVOCATION__POSITIVE:
			setPositive(POSITIVE_EDEFAULT);
			return;
		case IBeXPatternModelPackage.IBE_XPATTERN_INVOCATION__INVOKED_BY:
			setInvokedBy((IBeXContextPattern) null);
			return;
		case IBeXPatternModelPackage.IBE_XPATTERN_INVOCATION__INVOKED_PATTERN:
			setInvokedPattern((IBeXContextPattern) null);
			return;
		case IBeXPatternModelPackage.IBE_XPATTERN_INVOCATION__MAPPING:
			getMapping().clear();
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
		case IBeXPatternModelPackage.IBE_XPATTERN_INVOCATION__POSITIVE:
			return positive != POSITIVE_EDEFAULT;
		case IBeXPatternModelPackage.IBE_XPATTERN_INVOCATION__INVOKED_BY:
			return getInvokedBy() != null;
		case IBeXPatternModelPackage.IBE_XPATTERN_INVOCATION__INVOKED_PATTERN:
			return invokedPattern != null;
		case IBeXPatternModelPackage.IBE_XPATTERN_INVOCATION__MAPPING:
			return mapping != null && !mapping.isEmpty();
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
		result.append(" (positive: ");
		result.append(positive);
		result.append(')');
		return result.toString();
	}

} //IBeXPatternInvocationImpl
