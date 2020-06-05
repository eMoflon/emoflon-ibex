/**
 */
package org.emoflon.ibex.IBeXDisjunctPatternModel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDependentInjectivityConstraints;
import org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctPatternModelPackage;
import org.emoflon.ibex.IBeXDisjunctPatternModel.IBexDisjunctInjectivityConstraint;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IBe XDependent Injectivity Constraints</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.IBeXDisjunctPatternModel.impl.IBeXDependentInjectivityConstraintsImpl#getInjectivityConstraints <em>Injectivity Constraints</em>}</li>
 *   <li>{@link org.emoflon.ibex.IBeXDisjunctPatternModel.impl.IBeXDependentInjectivityConstraintsImpl#getPatterns <em>Patterns</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IBeXDependentInjectivityConstraintsImpl extends MinimalEObjectImpl.Container implements IBeXDependentInjectivityConstraints {
	/**
	 * The cached value of the '{@link #getInjectivityConstraints() <em>Injectivity Constraints</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInjectivityConstraints()
	 * @generated
	 * @ordered
	 */
	protected EList<IBexDisjunctInjectivityConstraint> injectivityConstraints;

	/**
	 * The cached value of the '{@link #getPatterns() <em>Patterns</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPatterns()
	 * @generated
	 * @ordered
	 */
	protected EList<String> patterns;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IBeXDependentInjectivityConstraintsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXDisjunctPatternModelPackage.Literals.IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getPatterns() {
		if (patterns == null) {
			patterns = new EDataTypeUniqueEList<String>(String.class, this, IBeXDisjunctPatternModelPackage.IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS__PATTERNS);
		}
		return patterns;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBexDisjunctInjectivityConstraint> getInjectivityConstraints() {
		if (injectivityConstraints == null) {
			injectivityConstraints = new EObjectContainmentEList<IBexDisjunctInjectivityConstraint>(IBexDisjunctInjectivityConstraint.class, this, IBeXDisjunctPatternModelPackage.IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS__INJECTIVITY_CONSTRAINTS);
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
			case IBeXDisjunctPatternModelPackage.IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS__INJECTIVITY_CONSTRAINTS:
				return ((InternalEList<?>)getInjectivityConstraints()).basicRemove(otherEnd, msgs);
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
			case IBeXDisjunctPatternModelPackage.IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS__INJECTIVITY_CONSTRAINTS:
				return getInjectivityConstraints();
			case IBeXDisjunctPatternModelPackage.IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS__PATTERNS:
				return getPatterns();
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
			case IBeXDisjunctPatternModelPackage.IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS__INJECTIVITY_CONSTRAINTS:
				getInjectivityConstraints().clear();
				getInjectivityConstraints().addAll((Collection<? extends IBexDisjunctInjectivityConstraint>)newValue);
				return;
			case IBeXDisjunctPatternModelPackage.IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS__PATTERNS:
				getPatterns().clear();
				getPatterns().addAll((Collection<? extends String>)newValue);
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
			case IBeXDisjunctPatternModelPackage.IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS__INJECTIVITY_CONSTRAINTS:
				getInjectivityConstraints().clear();
				return;
			case IBeXDisjunctPatternModelPackage.IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS__PATTERNS:
				getPatterns().clear();
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
			case IBeXDisjunctPatternModelPackage.IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS__INJECTIVITY_CONSTRAINTS:
				return injectivityConstraints != null && !injectivityConstraints.isEmpty();
			case IBeXDisjunctPatternModelPackage.IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS__PATTERNS:
				return patterns != null && !patterns.isEmpty();
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
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (patterns: ");
		result.append(patterns);
		result.append(')');
		return result.toString();
	}

} //IBeXDependentInjectivityConstraintsImpl
