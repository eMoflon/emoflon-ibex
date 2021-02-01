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
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjointContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXInterdependentAttributes;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXInterdependentInjectivityConstraints;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IBe XDisjoint Context Pattern</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXDisjointContextPatternImpl#getSubpatterns <em>Subpatterns</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXDisjointContextPatternImpl#getInjectivityConstraints <em>Injectivity Constraints</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXDisjointContextPatternImpl#getAttributeConstraints <em>Attribute Constraints</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXDisjointContextPatternImpl#getNonOptimizedPattern <em>Non Optimized Pattern</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IBeXDisjointContextPatternImpl extends IBeXContextImpl implements IBeXDisjointContextPattern {
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
	protected EList<IBeXInterdependentInjectivityConstraints> injectivityConstraints;

	/**
	 * The cached value of the '{@link #getAttributeConstraints() <em>Attribute Constraints</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeConstraints()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXInterdependentAttributes> attributeConstraints;

	/**
	 * The cached value of the '{@link #getNonOptimizedPattern() <em>Non Optimized Pattern</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNonOptimizedPattern()
	 * @generated
	 * @ordered
	 */
	protected IBeXContextPattern nonOptimizedPattern;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IBeXDisjointContextPatternImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXPatternModelPackage.Literals.IBE_XDISJOINT_CONTEXT_PATTERN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXContextPattern> getSubpatterns() {
		if (subpatterns == null) {
			subpatterns = new EObjectResolvingEList<IBeXContextPattern>(IBeXContextPattern.class, this,
					IBeXPatternModelPackage.IBE_XDISJOINT_CONTEXT_PATTERN__SUBPATTERNS);
		}
		return subpatterns;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXInterdependentInjectivityConstraints> getInjectivityConstraints() {
		if (injectivityConstraints == null) {
			injectivityConstraints = new EObjectContainmentEList<IBeXInterdependentInjectivityConstraints>(
					IBeXInterdependentInjectivityConstraints.class, this,
					IBeXPatternModelPackage.IBE_XDISJOINT_CONTEXT_PATTERN__INJECTIVITY_CONSTRAINTS);
		}
		return injectivityConstraints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXInterdependentAttributes> getAttributeConstraints() {
		if (attributeConstraints == null) {
			attributeConstraints = new EObjectContainmentEList<IBeXInterdependentAttributes>(
					IBeXInterdependentAttributes.class, this,
					IBeXPatternModelPackage.IBE_XDISJOINT_CONTEXT_PATTERN__ATTRIBUTE_CONSTRAINTS);
		}
		return attributeConstraints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXContextPattern getNonOptimizedPattern() {
		return nonOptimizedPattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNonOptimizedPattern(IBeXContextPattern newNonOptimizedPattern,
			NotificationChain msgs) {
		IBeXContextPattern oldNonOptimizedPattern = nonOptimizedPattern;
		nonOptimizedPattern = newNonOptimizedPattern;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXPatternModelPackage.IBE_XDISJOINT_CONTEXT_PATTERN__NON_OPTIMIZED_PATTERN,
					oldNonOptimizedPattern, newNonOptimizedPattern);
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
	public void setNonOptimizedPattern(IBeXContextPattern newNonOptimizedPattern) {
		if (newNonOptimizedPattern != nonOptimizedPattern) {
			NotificationChain msgs = null;
			if (nonOptimizedPattern != null)
				msgs = ((InternalEObject) nonOptimizedPattern).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- IBeXPatternModelPackage.IBE_XDISJOINT_CONTEXT_PATTERN__NON_OPTIMIZED_PATTERN,
						null, msgs);
			if (newNonOptimizedPattern != null)
				msgs = ((InternalEObject) newNonOptimizedPattern).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- IBeXPatternModelPackage.IBE_XDISJOINT_CONTEXT_PATTERN__NON_OPTIMIZED_PATTERN,
						null, msgs);
			msgs = basicSetNonOptimizedPattern(newNonOptimizedPattern, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXPatternModelPackage.IBE_XDISJOINT_CONTEXT_PATTERN__NON_OPTIMIZED_PATTERN,
					newNonOptimizedPattern, newNonOptimizedPattern));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IBeXPatternModelPackage.IBE_XDISJOINT_CONTEXT_PATTERN__INJECTIVITY_CONSTRAINTS:
			return ((InternalEList<?>) getInjectivityConstraints()).basicRemove(otherEnd, msgs);
		case IBeXPatternModelPackage.IBE_XDISJOINT_CONTEXT_PATTERN__ATTRIBUTE_CONSTRAINTS:
			return ((InternalEList<?>) getAttributeConstraints()).basicRemove(otherEnd, msgs);
		case IBeXPatternModelPackage.IBE_XDISJOINT_CONTEXT_PATTERN__NON_OPTIMIZED_PATTERN:
			return basicSetNonOptimizedPattern(null, msgs);
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
		case IBeXPatternModelPackage.IBE_XDISJOINT_CONTEXT_PATTERN__SUBPATTERNS:
			return getSubpatterns();
		case IBeXPatternModelPackage.IBE_XDISJOINT_CONTEXT_PATTERN__INJECTIVITY_CONSTRAINTS:
			return getInjectivityConstraints();
		case IBeXPatternModelPackage.IBE_XDISJOINT_CONTEXT_PATTERN__ATTRIBUTE_CONSTRAINTS:
			return getAttributeConstraints();
		case IBeXPatternModelPackage.IBE_XDISJOINT_CONTEXT_PATTERN__NON_OPTIMIZED_PATTERN:
			return getNonOptimizedPattern();
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
		case IBeXPatternModelPackage.IBE_XDISJOINT_CONTEXT_PATTERN__SUBPATTERNS:
			getSubpatterns().clear();
			getSubpatterns().addAll((Collection<? extends IBeXContextPattern>) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XDISJOINT_CONTEXT_PATTERN__INJECTIVITY_CONSTRAINTS:
			getInjectivityConstraints().clear();
			getInjectivityConstraints()
					.addAll((Collection<? extends IBeXInterdependentInjectivityConstraints>) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XDISJOINT_CONTEXT_PATTERN__ATTRIBUTE_CONSTRAINTS:
			getAttributeConstraints().clear();
			getAttributeConstraints().addAll((Collection<? extends IBeXInterdependentAttributes>) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XDISJOINT_CONTEXT_PATTERN__NON_OPTIMIZED_PATTERN:
			setNonOptimizedPattern((IBeXContextPattern) newValue);
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
		case IBeXPatternModelPackage.IBE_XDISJOINT_CONTEXT_PATTERN__SUBPATTERNS:
			getSubpatterns().clear();
			return;
		case IBeXPatternModelPackage.IBE_XDISJOINT_CONTEXT_PATTERN__INJECTIVITY_CONSTRAINTS:
			getInjectivityConstraints().clear();
			return;
		case IBeXPatternModelPackage.IBE_XDISJOINT_CONTEXT_PATTERN__ATTRIBUTE_CONSTRAINTS:
			getAttributeConstraints().clear();
			return;
		case IBeXPatternModelPackage.IBE_XDISJOINT_CONTEXT_PATTERN__NON_OPTIMIZED_PATTERN:
			setNonOptimizedPattern((IBeXContextPattern) null);
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
		case IBeXPatternModelPackage.IBE_XDISJOINT_CONTEXT_PATTERN__SUBPATTERNS:
			return subpatterns != null && !subpatterns.isEmpty();
		case IBeXPatternModelPackage.IBE_XDISJOINT_CONTEXT_PATTERN__INJECTIVITY_CONSTRAINTS:
			return injectivityConstraints != null && !injectivityConstraints.isEmpty();
		case IBeXPatternModelPackage.IBE_XDISJOINT_CONTEXT_PATTERN__ATTRIBUTE_CONSTRAINTS:
			return attributeConstraints != null && !attributeConstraints.isEmpty();
		case IBeXPatternModelPackage.IBE_XDISJOINT_CONTEXT_PATTERN__NON_OPTIMIZED_PATTERN:
			return nonOptimizedPattern != null;
		}
		return super.eIsSet(featureID);
	}

} //IBeXDisjointContextPatternImpl
