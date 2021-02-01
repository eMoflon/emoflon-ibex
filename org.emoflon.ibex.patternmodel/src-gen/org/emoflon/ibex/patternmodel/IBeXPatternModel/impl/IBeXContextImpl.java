/**
 */
package org.emoflon.ibex.patternmodel.IBeXPatternModel.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IBe XContext</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXContextImpl#getApiPatternDependencies <em>Api Pattern Dependencies</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class IBeXContextImpl extends IBeXPatternImpl implements IBeXContext {
	/**
	 * The cached value of the '{@link #getApiPatternDependencies() <em>Api Pattern Dependencies</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getApiPatternDependencies()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXContext> apiPatternDependencies;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IBeXContextImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXPatternModelPackage.Literals.IBE_XCONTEXT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXContext> getApiPatternDependencies() {
		if (apiPatternDependencies == null) {
			apiPatternDependencies = new EObjectResolvingEList<IBeXContext>(IBeXContext.class, this,
					IBeXPatternModelPackage.IBE_XCONTEXT__API_PATTERN_DEPENDENCIES);
		}
		return apiPatternDependencies;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case IBeXPatternModelPackage.IBE_XCONTEXT__API_PATTERN_DEPENDENCIES:
			return getApiPatternDependencies();
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
		case IBeXPatternModelPackage.IBE_XCONTEXT__API_PATTERN_DEPENDENCIES:
			getApiPatternDependencies().clear();
			getApiPatternDependencies().addAll((Collection<? extends IBeXContext>) newValue);
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
		case IBeXPatternModelPackage.IBE_XCONTEXT__API_PATTERN_DEPENDENCIES:
			getApiPatternDependencies().clear();
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
		case IBeXPatternModelPackage.IBE_XCONTEXT__API_PATTERN_DEPENDENCIES:
			return apiPatternDependencies != null && !apiPatternDependencies.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //IBeXContextImpl
