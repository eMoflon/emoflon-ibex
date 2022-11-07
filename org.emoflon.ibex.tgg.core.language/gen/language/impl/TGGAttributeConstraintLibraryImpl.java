/**
 */
package language.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import language.LanguagePackage;
import language.TGGAttributeConstraint;
import language.TGGAttributeConstraintLibrary;
import language.TGGParamValue;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TGG Attribute Constraint Library</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link language.impl.TGGAttributeConstraintLibraryImpl#getTggAttributeConstraints <em>Tgg Attribute Constraints</em>}</li>
 *   <li>{@link language.impl.TGGAttributeConstraintLibraryImpl#getParameterValues <em>Parameter Values</em>}</li>
 * </ul>
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
		return LanguagePackage.Literals.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TGGAttributeConstraint> getTggAttributeConstraints() {
		if (tggAttributeConstraints == null) {
			tggAttributeConstraints = new EObjectContainmentEList<TGGAttributeConstraint>(TGGAttributeConstraint.class,
					this, LanguagePackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__TGG_ATTRIBUTE_CONSTRAINTS);
		}
		return tggAttributeConstraints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TGGParamValue> getParameterValues() {
		if (parameterValues == null) {
			parameterValues = new EObjectContainmentEList<TGGParamValue>(TGGParamValue.class, this,
					LanguagePackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__PARAMETER_VALUES);
		}
		return parameterValues;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case LanguagePackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__TGG_ATTRIBUTE_CONSTRAINTS:
			return ((InternalEList<?>) getTggAttributeConstraints()).basicRemove(otherEnd, msgs);
		case LanguagePackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__PARAMETER_VALUES:
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
		case LanguagePackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__TGG_ATTRIBUTE_CONSTRAINTS:
			return getTggAttributeConstraints();
		case LanguagePackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__PARAMETER_VALUES:
			return getParameterValues();
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
		case LanguagePackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__TGG_ATTRIBUTE_CONSTRAINTS:
			getTggAttributeConstraints().clear();
			getTggAttributeConstraints().addAll((Collection<? extends TGGAttributeConstraint>) newValue);
			return;
		case LanguagePackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__PARAMETER_VALUES:
			getParameterValues().clear();
			getParameterValues().addAll((Collection<? extends TGGParamValue>) newValue);
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
		case LanguagePackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__TGG_ATTRIBUTE_CONSTRAINTS:
			getTggAttributeConstraints().clear();
			return;
		case LanguagePackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__PARAMETER_VALUES:
			getParameterValues().clear();
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
		case LanguagePackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__TGG_ATTRIBUTE_CONSTRAINTS:
			return tggAttributeConstraints != null && !tggAttributeConstraints.isEmpty();
		case LanguagePackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__PARAMETER_VALUES:
			return parameterValues != null && !parameterValues.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //TGGAttributeConstraintLibraryImpl
