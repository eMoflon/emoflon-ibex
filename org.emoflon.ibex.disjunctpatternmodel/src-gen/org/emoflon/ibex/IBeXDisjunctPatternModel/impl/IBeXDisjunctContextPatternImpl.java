/**
 */
package org.emoflon.ibex.IBeXDisjunctPatternModel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDependentInjectivityConstraints;
import org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctAttributes;
import org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctContextPattern;
import org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctPatternModelPackage;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXContextImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IBe XDisjunct Context Pattern</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.IBeXDisjunctPatternModel.impl.IBeXDisjunctContextPatternImpl#getSubpatterns <em>Subpatterns</em>}</li>
 *   <li>{@link org.emoflon.ibex.IBeXDisjunctPatternModel.impl.IBeXDisjunctContextPatternImpl#getInjectivityConstraints <em>Injectivity Constraints</em>}</li>
 *   <li>{@link org.emoflon.ibex.IBeXDisjunctPatternModel.impl.IBeXDisjunctContextPatternImpl#getAttributesConstraints <em>Attributes Constraints</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IBeXDisjunctContextPatternImpl extends IBeXContextImpl implements IBeXDisjunctContextPattern {
	/**
	 * The cached value of the '{@link #getSubpatterns() <em>Subpatterns</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubpatterns()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXContextPattern> subpatterns;

	/**
	 * The cached value of the '{@link #getInjectivityConstraints() <em>Injectivity Constraints</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInjectivityConstraints()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXDependentInjectivityConstraints> injectivityConstraints;

	/**
	 * The cached value of the '{@link #getAttributesConstraints() <em>Attributes Constraints</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributesConstraints()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXDisjunctAttributes> attributesConstraints;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IBeXDisjunctContextPatternImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXDisjunctPatternModelPackage.Literals.IBE_XDISJUNCT_CONTEXT_PATTERN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXContextPattern> getSubpatterns() {
		if (subpatterns == null) {
			subpatterns = new EObjectResolvingEList<IBeXContextPattern>(IBeXContextPattern.class, this, IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_CONTEXT_PATTERN__SUBPATTERNS);
		}
		return subpatterns;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXDependentInjectivityConstraints> getInjectivityConstraints() {
		if (injectivityConstraints == null) {
			injectivityConstraints = new EObjectContainmentEList<IBeXDependentInjectivityConstraints>(IBeXDependentInjectivityConstraints.class, this, IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_CONTEXT_PATTERN__INJECTIVITY_CONSTRAINTS);
		}
		return injectivityConstraints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXDisjunctAttributes> getAttributesConstraints() {
		if (attributesConstraints == null) {
			attributesConstraints = new EObjectContainmentEList<IBeXDisjunctAttributes>(IBeXDisjunctAttributes.class, this, IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_CONTEXT_PATTERN__ATTRIBUTES_CONSTRAINTS);
		}
		return attributesConstraints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_CONTEXT_PATTERN__INJECTIVITY_CONSTRAINTS:
				return ((InternalEList<?>)getInjectivityConstraints()).basicRemove(otherEnd, msgs);
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_CONTEXT_PATTERN__ATTRIBUTES_CONSTRAINTS:
				return ((InternalEList<?>)getAttributesConstraints()).basicRemove(otherEnd, msgs);
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
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_CONTEXT_PATTERN__SUBPATTERNS:
				return getSubpatterns();
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_CONTEXT_PATTERN__INJECTIVITY_CONSTRAINTS:
				return getInjectivityConstraints();
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_CONTEXT_PATTERN__ATTRIBUTES_CONSTRAINTS:
				return getAttributesConstraints();
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
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_CONTEXT_PATTERN__SUBPATTERNS:
				getSubpatterns().clear();
				getSubpatterns().addAll((Collection<? extends IBeXContextPattern>)newValue);
				return;
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_CONTEXT_PATTERN__INJECTIVITY_CONSTRAINTS:
				getInjectivityConstraints().clear();
				getInjectivityConstraints().addAll((Collection<? extends IBeXDependentInjectivityConstraints>)newValue);
				return;
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_CONTEXT_PATTERN__ATTRIBUTES_CONSTRAINTS:
				getAttributesConstraints().clear();
				getAttributesConstraints().addAll((Collection<? extends IBeXDisjunctAttributes>)newValue);
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
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_CONTEXT_PATTERN__SUBPATTERNS:
				getSubpatterns().clear();
				return;
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_CONTEXT_PATTERN__INJECTIVITY_CONSTRAINTS:
				getInjectivityConstraints().clear();
				return;
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_CONTEXT_PATTERN__ATTRIBUTES_CONSTRAINTS:
				getAttributesConstraints().clear();
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
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_CONTEXT_PATTERN__SUBPATTERNS:
				return subpatterns != null && !subpatterns.isEmpty();
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_CONTEXT_PATTERN__INJECTIVITY_CONSTRAINTS:
				return injectivityConstraints != null && !injectivityConstraints.isEmpty();
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_CONTEXT_PATTERN__ATTRIBUTES_CONSTRAINTS:
				return attributesConstraints != null && !attributesConstraints.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //IBeXDisjunctContextPatternImpl
