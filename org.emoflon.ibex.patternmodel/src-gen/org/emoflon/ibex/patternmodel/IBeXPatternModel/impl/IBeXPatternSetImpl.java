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
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternSet;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IBe XPattern Set</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternSetImpl#getContextPatterns <em>Context Patterns</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternSetImpl#getExtendedContextPatterns <em>Extended Context Patterns</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IBeXPatternSetImpl extends MinimalEObjectImpl.Container implements IBeXPatternSet {
	/**
	 * The cached value of the '{@link #getContextPatterns() <em>Context Patterns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContextPatterns()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXContext> contextPatterns;

	/**
	 * The cached value of the '{@link #getExtendedContextPatterns() <em>Extended Context Patterns</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtendedContextPatterns()
	 * @generated
	 * @ordered
	 */
	protected IBeXContext extendedContextPatterns;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IBeXPatternSetImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXPatternModelPackage.Literals.IBE_XPATTERN_SET;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXContext> getContextPatterns() {
		if (contextPatterns == null) {
			contextPatterns = new EObjectContainmentEList<IBeXContext>(IBeXContext.class, this,
					IBeXPatternModelPackage.IBE_XPATTERN_SET__CONTEXT_PATTERNS);
		}
		return contextPatterns;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXContext getExtendedContextPatterns() {
		if (extendedContextPatterns != null && extendedContextPatterns.eIsProxy()) {
			InternalEObject oldExtendedContextPatterns = (InternalEObject) extendedContextPatterns;
			extendedContextPatterns = (IBeXContext) eResolveProxy(oldExtendedContextPatterns);
			if (extendedContextPatterns != oldExtendedContextPatterns) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							IBeXPatternModelPackage.IBE_XPATTERN_SET__EXTENDED_CONTEXT_PATTERNS,
							oldExtendedContextPatterns, extendedContextPatterns));
			}
		}
		return extendedContextPatterns;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXContext basicGetExtendedContextPatterns() {
		return extendedContextPatterns;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setExtendedContextPatterns(IBeXContext newExtendedContextPatterns) {
		IBeXContext oldExtendedContextPatterns = extendedContextPatterns;
		extendedContextPatterns = newExtendedContextPatterns;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXPatternModelPackage.IBE_XPATTERN_SET__EXTENDED_CONTEXT_PATTERNS, oldExtendedContextPatterns,
					extendedContextPatterns));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IBeXPatternModelPackage.IBE_XPATTERN_SET__CONTEXT_PATTERNS:
			return ((InternalEList<?>) getContextPatterns()).basicRemove(otherEnd, msgs);
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
		case IBeXPatternModelPackage.IBE_XPATTERN_SET__CONTEXT_PATTERNS:
			return getContextPatterns();
		case IBeXPatternModelPackage.IBE_XPATTERN_SET__EXTENDED_CONTEXT_PATTERNS:
			if (resolve)
				return getExtendedContextPatterns();
			return basicGetExtendedContextPatterns();
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
		case IBeXPatternModelPackage.IBE_XPATTERN_SET__CONTEXT_PATTERNS:
			getContextPatterns().clear();
			getContextPatterns().addAll((Collection<? extends IBeXContext>) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XPATTERN_SET__EXTENDED_CONTEXT_PATTERNS:
			setExtendedContextPatterns((IBeXContext) newValue);
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
		case IBeXPatternModelPackage.IBE_XPATTERN_SET__CONTEXT_PATTERNS:
			getContextPatterns().clear();
			return;
		case IBeXPatternModelPackage.IBE_XPATTERN_SET__EXTENDED_CONTEXT_PATTERNS:
			setExtendedContextPatterns((IBeXContext) null);
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
		case IBeXPatternModelPackage.IBE_XPATTERN_SET__CONTEXT_PATTERNS:
			return contextPatterns != null && !contextPatterns.isEmpty();
		case IBeXPatternModelPackage.IBE_XPATTERN_SET__EXTENDED_CONTEXT_PATTERNS:
			return extendedContextPatterns != null;
		}
		return super.eIsSet(featureID);
	}

} //IBeXPatternSetImpl
