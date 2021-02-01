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
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDependentDisjunctAttribute;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDependentInjectivityConstraints;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBexDisjunctInjectivityConstraint;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IBe XDependent Injectivity Constraints</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXDependentInjectivityConstraintsImpl#getInjectivityConstraints <em>Injectivity Constraints</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXDependentInjectivityConstraintsImpl#getPatterns <em>Patterns</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXDependentInjectivityConstraintsImpl#getAttributeConstraints <em>Attribute Constraints</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IBeXDependentInjectivityConstraintsImpl extends MinimalEObjectImpl.Container
		implements IBeXDependentInjectivityConstraints {
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
	 * The cached value of the '{@link #getPatterns() <em>Patterns</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPatterns()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXContextPattern> patterns;

	/**
	 * The cached value of the '{@link #getAttributeConstraints() <em>Attribute Constraints</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeConstraints()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXDependentDisjunctAttribute> attributeConstraints;

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
		return IBeXPatternModelPackage.Literals.IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBexDisjunctInjectivityConstraint> getInjectivityConstraints() {
		if (injectivityConstraints == null) {
			injectivityConstraints = new EObjectContainmentEList<IBexDisjunctInjectivityConstraint>(
					IBexDisjunctInjectivityConstraint.class, this,
					IBeXPatternModelPackage.IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS__INJECTIVITY_CONSTRAINTS);
		}
		return injectivityConstraints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXContextPattern> getPatterns() {
		if (patterns == null) {
			patterns = new EObjectResolvingEList<IBeXContextPattern>(IBeXContextPattern.class, this,
					IBeXPatternModelPackage.IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS__PATTERNS);
		}
		return patterns;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXDependentDisjunctAttribute> getAttributeConstraints() {
		if (attributeConstraints == null) {
			attributeConstraints = new EObjectWithInverseResolvingEList<IBeXDependentDisjunctAttribute>(
					IBeXDependentDisjunctAttribute.class, this,
					IBeXPatternModelPackage.IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS__ATTRIBUTE_CONSTRAINTS,
					IBeXPatternModelPackage.IBE_XDEPENDENT_DISJUNCT_ATTRIBUTE__INJECTIVITY_CONSTRAINTS);
		}
		return attributeConstraints;
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
		case IBeXPatternModelPackage.IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS__ATTRIBUTE_CONSTRAINTS:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getAttributeConstraints()).basicAdd(otherEnd,
					msgs);
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
		case IBeXPatternModelPackage.IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS__INJECTIVITY_CONSTRAINTS:
			return ((InternalEList<?>) getInjectivityConstraints()).basicRemove(otherEnd, msgs);
		case IBeXPatternModelPackage.IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS__ATTRIBUTE_CONSTRAINTS:
			return ((InternalEList<?>) getAttributeConstraints()).basicRemove(otherEnd, msgs);
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
		case IBeXPatternModelPackage.IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS__INJECTIVITY_CONSTRAINTS:
			return getInjectivityConstraints();
		case IBeXPatternModelPackage.IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS__PATTERNS:
			return getPatterns();
		case IBeXPatternModelPackage.IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS__ATTRIBUTE_CONSTRAINTS:
			return getAttributeConstraints();
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
		case IBeXPatternModelPackage.IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS__INJECTIVITY_CONSTRAINTS:
			getInjectivityConstraints().clear();
			getInjectivityConstraints().addAll((Collection<? extends IBexDisjunctInjectivityConstraint>) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS__PATTERNS:
			getPatterns().clear();
			getPatterns().addAll((Collection<? extends IBeXContextPattern>) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS__ATTRIBUTE_CONSTRAINTS:
			getAttributeConstraints().clear();
			getAttributeConstraints().addAll((Collection<? extends IBeXDependentDisjunctAttribute>) newValue);
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
		case IBeXPatternModelPackage.IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS__INJECTIVITY_CONSTRAINTS:
			getInjectivityConstraints().clear();
			return;
		case IBeXPatternModelPackage.IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS__PATTERNS:
			getPatterns().clear();
			return;
		case IBeXPatternModelPackage.IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS__ATTRIBUTE_CONSTRAINTS:
			getAttributeConstraints().clear();
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
		case IBeXPatternModelPackage.IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS__INJECTIVITY_CONSTRAINTS:
			return injectivityConstraints != null && !injectivityConstraints.isEmpty();
		case IBeXPatternModelPackage.IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS__PATTERNS:
			return patterns != null && !patterns.isEmpty();
		case IBeXPatternModelPackage.IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS__ATTRIBUTE_CONSTRAINTS:
			return attributeConstraints != null && !attributeConstraints.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //IBeXDependentInjectivityConstraintsImpl
