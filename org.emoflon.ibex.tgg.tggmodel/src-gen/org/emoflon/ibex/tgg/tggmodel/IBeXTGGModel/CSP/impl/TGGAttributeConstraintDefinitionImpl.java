/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXNamedElementImpl;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.CSPPackage;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintBinding;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintDefinition;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintParameterDefinition;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TGG Attribute Constraint Definition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGAttributeConstraintDefinitionImpl#getParameterDefinitions <em>Parameter Definitions</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGAttributeConstraintDefinitionImpl#getSyncBindings <em>Sync Bindings</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGAttributeConstraintDefinitionImpl#getGenBindings <em>Gen Bindings</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TGGAttributeConstraintDefinitionImpl extends IBeXNamedElementImpl implements TGGAttributeConstraintDefinition {
	/**
	 * The cached value of the '{@link #getParameterDefinitions() <em>Parameter Definitions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameterDefinitions()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGAttributeConstraintParameterDefinition> parameterDefinitions;

	/**
	 * The cached value of the '{@link #getSyncBindings() <em>Sync Bindings</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSyncBindings()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGAttributeConstraintBinding> syncBindings;

	/**
	 * The cached value of the '{@link #getGenBindings() <em>Gen Bindings</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGenBindings()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGAttributeConstraintBinding> genBindings;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TGGAttributeConstraintDefinitionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CSPPackage.Literals.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TGGAttributeConstraintParameterDefinition> getParameterDefinitions() {
		if (parameterDefinitions == null) {
			parameterDefinitions = new EObjectContainmentEList<TGGAttributeConstraintParameterDefinition>(
					TGGAttributeConstraintParameterDefinition.class, this, CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__PARAMETER_DEFINITIONS);
		}
		return parameterDefinitions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TGGAttributeConstraintBinding> getSyncBindings() {
		if (syncBindings == null) {
			syncBindings = new EObjectContainmentEList<TGGAttributeConstraintBinding>(TGGAttributeConstraintBinding.class, this,
					CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__SYNC_BINDINGS);
		}
		return syncBindings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TGGAttributeConstraintBinding> getGenBindings() {
		if (genBindings == null) {
			genBindings = new EObjectContainmentEList<TGGAttributeConstraintBinding>(TGGAttributeConstraintBinding.class, this,
					CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__GEN_BINDINGS);
		}
		return genBindings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__PARAMETER_DEFINITIONS:
				return ((InternalEList<?>) getParameterDefinitions()).basicRemove(otherEnd, msgs);
			case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__SYNC_BINDINGS:
				return ((InternalEList<?>) getSyncBindings()).basicRemove(otherEnd, msgs);
			case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__GEN_BINDINGS:
				return ((InternalEList<?>) getGenBindings()).basicRemove(otherEnd, msgs);
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
			case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__PARAMETER_DEFINITIONS:
				return getParameterDefinitions();
			case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__SYNC_BINDINGS:
				return getSyncBindings();
			case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__GEN_BINDINGS:
				return getGenBindings();
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
			case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__PARAMETER_DEFINITIONS:
				getParameterDefinitions().clear();
				getParameterDefinitions().addAll((Collection<? extends TGGAttributeConstraintParameterDefinition>) newValue);
				return;
			case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__SYNC_BINDINGS:
				getSyncBindings().clear();
				getSyncBindings().addAll((Collection<? extends TGGAttributeConstraintBinding>) newValue);
				return;
			case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__GEN_BINDINGS:
				getGenBindings().clear();
				getGenBindings().addAll((Collection<? extends TGGAttributeConstraintBinding>) newValue);
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
			case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__PARAMETER_DEFINITIONS:
				getParameterDefinitions().clear();
				return;
			case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__SYNC_BINDINGS:
				getSyncBindings().clear();
				return;
			case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__GEN_BINDINGS:
				getGenBindings().clear();
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
			case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__PARAMETER_DEFINITIONS:
				return parameterDefinitions != null && !parameterDefinitions.isEmpty();
			case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__SYNC_BINDINGS:
				return syncBindings != null && !syncBindings.isEmpty();
			case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__GEN_BINDINGS:
				return genBindings != null && !genBindings.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //TGGAttributeConstraintDefinitionImpl
