/**
 */
package org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternInvocation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IBe XPattern Invocation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXPatternInvocationImpl#isPositive <em>Positive</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXPatternInvocationImpl#getInvokedBy <em>Invoked By</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXPatternInvocationImpl#getInvocation <em>Invocation</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXPatternInvocationImpl#getMapping <em>Mapping</em>}</li>
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
	 * The cached value of the '{@link #getInvokedBy() <em>Invoked By</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInvokedBy()
	 * @generated
	 * @ordered
	 */
	protected IBeXPattern invokedBy;

	/**
	 * The cached value of the '{@link #getInvocation() <em>Invocation</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInvocation()
	 * @generated
	 * @ordered
	 */
	protected IBeXPattern invocation;

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
		return IBeXCoreModelPackage.Literals.IBE_XPATTERN_INVOCATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isPositive() {
		return positive;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPositive(boolean newPositive) {
		boolean oldPositive = positive;
		positive = newPositive;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXCoreModelPackage.IBE_XPATTERN_INVOCATION__POSITIVE, oldPositive, positive));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXPattern getInvokedBy() {
		if (invokedBy != null && invokedBy.eIsProxy()) {
			InternalEObject oldInvokedBy = (InternalEObject) invokedBy;
			invokedBy = (IBeXPattern) eResolveProxy(oldInvokedBy);
			if (invokedBy != oldInvokedBy) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							IBeXCoreModelPackage.IBE_XPATTERN_INVOCATION__INVOKED_BY, oldInvokedBy, invokedBy));
			}
		}
		return invokedBy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXPattern basicGetInvokedBy() {
		return invokedBy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInvokedBy(IBeXPattern newInvokedBy) {
		IBeXPattern oldInvokedBy = invokedBy;
		invokedBy = newInvokedBy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXCoreModelPackage.IBE_XPATTERN_INVOCATION__INVOKED_BY, oldInvokedBy, invokedBy));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXPattern getInvocation() {
		if (invocation != null && invocation.eIsProxy()) {
			InternalEObject oldInvocation = (InternalEObject) invocation;
			invocation = (IBeXPattern) eResolveProxy(oldInvocation);
			if (invocation != oldInvocation) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							IBeXCoreModelPackage.IBE_XPATTERN_INVOCATION__INVOCATION, oldInvocation, invocation));
			}
		}
		return invocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXPattern basicGetInvocation() {
		return invocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInvocation(IBeXPattern newInvocation) {
		IBeXPattern oldInvocation = invocation;
		invocation = newInvocation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXCoreModelPackage.IBE_XPATTERN_INVOCATION__INVOCATION, oldInvocation, invocation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<IBeXNode, IBeXNode> getMapping() {
		if (mapping == null) {
			mapping = new EcoreEMap<IBeXNode, IBeXNode>(IBeXCoreModelPackage.Literals.IBE_XNODE_MAPPING,
					IBeXNodeMappingImpl.class, this, IBeXCoreModelPackage.IBE_XPATTERN_INVOCATION__MAPPING);
		}
		return mapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IBeXCoreModelPackage.IBE_XPATTERN_INVOCATION__MAPPING:
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
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case IBeXCoreModelPackage.IBE_XPATTERN_INVOCATION__POSITIVE:
			return isPositive();
		case IBeXCoreModelPackage.IBE_XPATTERN_INVOCATION__INVOKED_BY:
			if (resolve)
				return getInvokedBy();
			return basicGetInvokedBy();
		case IBeXCoreModelPackage.IBE_XPATTERN_INVOCATION__INVOCATION:
			if (resolve)
				return getInvocation();
			return basicGetInvocation();
		case IBeXCoreModelPackage.IBE_XPATTERN_INVOCATION__MAPPING:
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
		case IBeXCoreModelPackage.IBE_XPATTERN_INVOCATION__POSITIVE:
			setPositive((Boolean) newValue);
			return;
		case IBeXCoreModelPackage.IBE_XPATTERN_INVOCATION__INVOKED_BY:
			setInvokedBy((IBeXPattern) newValue);
			return;
		case IBeXCoreModelPackage.IBE_XPATTERN_INVOCATION__INVOCATION:
			setInvocation((IBeXPattern) newValue);
			return;
		case IBeXCoreModelPackage.IBE_XPATTERN_INVOCATION__MAPPING:
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
		case IBeXCoreModelPackage.IBE_XPATTERN_INVOCATION__POSITIVE:
			setPositive(POSITIVE_EDEFAULT);
			return;
		case IBeXCoreModelPackage.IBE_XPATTERN_INVOCATION__INVOKED_BY:
			setInvokedBy((IBeXPattern) null);
			return;
		case IBeXCoreModelPackage.IBE_XPATTERN_INVOCATION__INVOCATION:
			setInvocation((IBeXPattern) null);
			return;
		case IBeXCoreModelPackage.IBE_XPATTERN_INVOCATION__MAPPING:
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
		case IBeXCoreModelPackage.IBE_XPATTERN_INVOCATION__POSITIVE:
			return positive != POSITIVE_EDEFAULT;
		case IBeXCoreModelPackage.IBE_XPATTERN_INVOCATION__INVOKED_BY:
			return invokedBy != null;
		case IBeXCoreModelPackage.IBE_XPATTERN_INVOCATION__INVOCATION:
			return invocation != null;
		case IBeXCoreModelPackage.IBE_XPATTERN_INVOCATION__MAPPING:
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
