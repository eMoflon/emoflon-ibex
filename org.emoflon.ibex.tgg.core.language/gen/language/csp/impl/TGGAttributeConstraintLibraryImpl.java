/**
 */
package language.csp.impl;

import java.util.Collection;

import language.basic.expressions.TGGParamValue;

import language.csp.CspPackage;
import language.csp.TGGAttributeConstraint;
import language.csp.TGGAttributeConstraintLibrary;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TGG Attribute Constraint Library</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link language.csp.impl.TGGAttributeConstraintLibraryImpl#getTggAttributeConstraints <em>Tgg Attribute Constraints</em>}</li>
 *   <li>{@link language.csp.impl.TGGAttributeConstraintLibraryImpl#getParameterValues <em>Parameter Values</em>}</li>
 *   <li>{@link language.csp.impl.TGGAttributeConstraintLibraryImpl#getSorted_BWD <em>Sorted BWD</em>}</li>
 *   <li>{@link language.csp.impl.TGGAttributeConstraintLibraryImpl#getSorted_FWD <em>Sorted FWD</em>}</li>
 *   <li>{@link language.csp.impl.TGGAttributeConstraintLibraryImpl#getSorted_CC <em>Sorted CC</em>}</li>
 *   <li>{@link language.csp.impl.TGGAttributeConstraintLibraryImpl#getSorted_MODELGEN <em>Sorted MODELGEN</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TGGAttributeConstraintLibraryImpl extends EObjectImpl implements TGGAttributeConstraintLibrary {
	/**
	 * The cached value of the '{@link #getTggAttributeConstraints() <em>Tgg Attribute Constraints</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTggAttributeConstraints()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGAttributeConstraint> tggAttributeConstraints;

	/**
	 * The cached value of the '{@link #getParameterValues() <em>Parameter Values</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameterValues()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGParamValue> parameterValues;

	/**
	 * The cached value of the '{@link #getSorted_BWD() <em>Sorted BWD</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSorted_BWD()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGAttributeConstraint> sorted_BWD;

	/**
	 * The cached value of the '{@link #getSorted_FWD() <em>Sorted FWD</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSorted_FWD()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGAttributeConstraint> sorted_FWD;

	/**
	 * The cached value of the '{@link #getSorted_CC() <em>Sorted CC</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSorted_CC()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGAttributeConstraint> sorted_CC;

	/**
	 * The cached value of the '{@link #getSorted_MODELGEN() <em>Sorted MODELGEN</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSorted_MODELGEN()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGAttributeConstraint> sorted_MODELGEN;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TGGAttributeConstraintLibraryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CspPackage.Literals.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TGGAttributeConstraint> getTggAttributeConstraints() {
		if (tggAttributeConstraints == null) {
			tggAttributeConstraints = new EObjectContainmentEList<TGGAttributeConstraint>(TGGAttributeConstraint.class,
					this, CspPackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__TGG_ATTRIBUTE_CONSTRAINTS);
		}
		return tggAttributeConstraints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TGGParamValue> getParameterValues() {
		if (parameterValues == null) {
			parameterValues = new EObjectContainmentEList<TGGParamValue>(TGGParamValue.class, this,
					CspPackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__PARAMETER_VALUES);
		}
		return parameterValues;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TGGAttributeConstraint> getSorted_BWD() {
		if (sorted_BWD == null) {
			sorted_BWD = new EObjectResolvingEList<TGGAttributeConstraint>(TGGAttributeConstraint.class, this,
					CspPackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__SORTED_BWD);
		}
		return sorted_BWD;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TGGAttributeConstraint> getSorted_FWD() {
		if (sorted_FWD == null) {
			sorted_FWD = new EObjectResolvingEList<TGGAttributeConstraint>(TGGAttributeConstraint.class, this,
					CspPackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__SORTED_FWD);
		}
		return sorted_FWD;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TGGAttributeConstraint> getSorted_CC() {
		if (sorted_CC == null) {
			sorted_CC = new EObjectResolvingEList<TGGAttributeConstraint>(TGGAttributeConstraint.class, this,
					CspPackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__SORTED_CC);
		}
		return sorted_CC;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TGGAttributeConstraint> getSorted_MODELGEN() {
		if (sorted_MODELGEN == null) {
			sorted_MODELGEN = new EObjectResolvingEList<TGGAttributeConstraint>(TGGAttributeConstraint.class, this,
					CspPackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__SORTED_MODELGEN);
		}
		return sorted_MODELGEN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case CspPackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__TGG_ATTRIBUTE_CONSTRAINTS:
			return ((InternalEList<?>) getTggAttributeConstraints()).basicRemove(otherEnd, msgs);
		case CspPackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__PARAMETER_VALUES:
			return ((InternalEList<?>) getParameterValues()).basicRemove(otherEnd, msgs);
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
		case CspPackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__TGG_ATTRIBUTE_CONSTRAINTS:
			return getTggAttributeConstraints();
		case CspPackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__PARAMETER_VALUES:
			return getParameterValues();
		case CspPackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__SORTED_BWD:
			return getSorted_BWD();
		case CspPackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__SORTED_FWD:
			return getSorted_FWD();
		case CspPackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__SORTED_CC:
			return getSorted_CC();
		case CspPackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__SORTED_MODELGEN:
			return getSorted_MODELGEN();
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
		case CspPackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__TGG_ATTRIBUTE_CONSTRAINTS:
			getTggAttributeConstraints().clear();
			getTggAttributeConstraints().addAll((Collection<? extends TGGAttributeConstraint>) newValue);
			return;
		case CspPackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__PARAMETER_VALUES:
			getParameterValues().clear();
			getParameterValues().addAll((Collection<? extends TGGParamValue>) newValue);
			return;
		case CspPackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__SORTED_BWD:
			getSorted_BWD().clear();
			getSorted_BWD().addAll((Collection<? extends TGGAttributeConstraint>) newValue);
			return;
		case CspPackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__SORTED_FWD:
			getSorted_FWD().clear();
			getSorted_FWD().addAll((Collection<? extends TGGAttributeConstraint>) newValue);
			return;
		case CspPackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__SORTED_CC:
			getSorted_CC().clear();
			getSorted_CC().addAll((Collection<? extends TGGAttributeConstraint>) newValue);
			return;
		case CspPackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__SORTED_MODELGEN:
			getSorted_MODELGEN().clear();
			getSorted_MODELGEN().addAll((Collection<? extends TGGAttributeConstraint>) newValue);
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
		case CspPackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__TGG_ATTRIBUTE_CONSTRAINTS:
			getTggAttributeConstraints().clear();
			return;
		case CspPackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__PARAMETER_VALUES:
			getParameterValues().clear();
			return;
		case CspPackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__SORTED_BWD:
			getSorted_BWD().clear();
			return;
		case CspPackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__SORTED_FWD:
			getSorted_FWD().clear();
			return;
		case CspPackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__SORTED_CC:
			getSorted_CC().clear();
			return;
		case CspPackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__SORTED_MODELGEN:
			getSorted_MODELGEN().clear();
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
		case CspPackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__TGG_ATTRIBUTE_CONSTRAINTS:
			return tggAttributeConstraints != null && !tggAttributeConstraints.isEmpty();
		case CspPackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__PARAMETER_VALUES:
			return parameterValues != null && !parameterValues.isEmpty();
		case CspPackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__SORTED_BWD:
			return sorted_BWD != null && !sorted_BWD.isEmpty();
		case CspPackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__SORTED_FWD:
			return sorted_FWD != null && !sorted_FWD.isEmpty();
		case CspPackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__SORTED_CC:
			return sorted_CC != null && !sorted_CC.isEmpty();
		case CspPackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__SORTED_MODELGEN:
			return sorted_MODELGEN != null && !sorted_MODELGEN.isEmpty();
		}
		return super.eIsSet(featureID);
	}
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} //TGGAttributeConstraintLibraryImpl
