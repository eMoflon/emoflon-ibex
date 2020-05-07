/**
 */
package org.emoflon.ibex.patternmodel.IBeXPatternModel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCreatePattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDeletePattern;
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
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternSetImpl#getCreatePatterns <em>Create Patterns</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternSetImpl#getDeletePatterns <em>Delete Patterns</em>}</li>
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
	 * The cached value of the '{@link #getCreatePatterns() <em>Create Patterns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreatePatterns()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXCreatePattern> createPatterns;

	/**
	 * The cached value of the '{@link #getDeletePatterns() <em>Delete Patterns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeletePatterns()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXDeletePattern> deletePatterns;

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
	public EList<IBeXCreatePattern> getCreatePatterns() {
		if (createPatterns == null) {
			createPatterns = new EObjectContainmentEList<IBeXCreatePattern>(IBeXCreatePattern.class, this,
					IBeXPatternModelPackage.IBE_XPATTERN_SET__CREATE_PATTERNS);
		}
		return createPatterns;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXDeletePattern> getDeletePatterns() {
		if (deletePatterns == null) {
			deletePatterns = new EObjectContainmentEList<IBeXDeletePattern>(IBeXDeletePattern.class, this,
					IBeXPatternModelPackage.IBE_XPATTERN_SET__DELETE_PATTERNS);
		}
		return deletePatterns;
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
		case IBeXPatternModelPackage.IBE_XPATTERN_SET__CREATE_PATTERNS:
			return ((InternalEList<?>) getCreatePatterns()).basicRemove(otherEnd, msgs);
		case IBeXPatternModelPackage.IBE_XPATTERN_SET__DELETE_PATTERNS:
			return ((InternalEList<?>) getDeletePatterns()).basicRemove(otherEnd, msgs);
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
		case IBeXPatternModelPackage.IBE_XPATTERN_SET__CREATE_PATTERNS:
			return getCreatePatterns();
		case IBeXPatternModelPackage.IBE_XPATTERN_SET__DELETE_PATTERNS:
			return getDeletePatterns();
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
		case IBeXPatternModelPackage.IBE_XPATTERN_SET__CREATE_PATTERNS:
			getCreatePatterns().clear();
			getCreatePatterns().addAll((Collection<? extends IBeXCreatePattern>) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XPATTERN_SET__DELETE_PATTERNS:
			getDeletePatterns().clear();
			getDeletePatterns().addAll((Collection<? extends IBeXDeletePattern>) newValue);
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
		case IBeXPatternModelPackage.IBE_XPATTERN_SET__CREATE_PATTERNS:
			getCreatePatterns().clear();
			return;
		case IBeXPatternModelPackage.IBE_XPATTERN_SET__DELETE_PATTERNS:
			getDeletePatterns().clear();
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
		case IBeXPatternModelPackage.IBE_XPATTERN_SET__CREATE_PATTERNS:
			return createPatterns != null && !createPatterns.isEmpty();
		case IBeXPatternModelPackage.IBE_XPATTERN_SET__DELETE_PATTERNS:
			return deletePatterns != null && !deletePatterns.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //IBeXPatternSetImpl
