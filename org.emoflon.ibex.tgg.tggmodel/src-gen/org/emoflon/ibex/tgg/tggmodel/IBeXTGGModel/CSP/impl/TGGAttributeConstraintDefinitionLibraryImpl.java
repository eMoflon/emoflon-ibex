/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.CSPPackage;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintDefinition;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintDefinitionLibrary;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TGG Attribute Constraint Definition Library</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGAttributeConstraintDefinitionLibraryImpl#getTggAttributeConstraintDefinitions <em>Tgg Attribute Constraint Definitions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TGGAttributeConstraintDefinitionLibraryImpl extends MinimalEObjectImpl.Container
		implements TGGAttributeConstraintDefinitionLibrary {
	/**
	 * The cached value of the '{@link #getTggAttributeConstraintDefinitions() <em>Tgg Attribute Constraint Definitions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTggAttributeConstraintDefinitions()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGAttributeConstraintDefinition> tggAttributeConstraintDefinitions;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TGGAttributeConstraintDefinitionLibraryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CSPPackage.Literals.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TGGAttributeConstraintDefinition> getTggAttributeConstraintDefinitions() {
		if (tggAttributeConstraintDefinitions == null) {
			tggAttributeConstraintDefinitions = new EObjectContainmentEList<TGGAttributeConstraintDefinition>(
					TGGAttributeConstraintDefinition.class, this,
					CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY__TGG_ATTRIBUTE_CONSTRAINT_DEFINITIONS);
		}
		return tggAttributeConstraintDefinitions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY__TGG_ATTRIBUTE_CONSTRAINT_DEFINITIONS:
			return ((InternalEList<?>) getTggAttributeConstraintDefinitions()).basicRemove(otherEnd, msgs);
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
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY__TGG_ATTRIBUTE_CONSTRAINT_DEFINITIONS:
			return getTggAttributeConstraintDefinitions();
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
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY__TGG_ATTRIBUTE_CONSTRAINT_DEFINITIONS:
			getTggAttributeConstraintDefinitions().clear();
			getTggAttributeConstraintDefinitions()
					.addAll((Collection<? extends TGGAttributeConstraintDefinition>) newValue);
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
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY__TGG_ATTRIBUTE_CONSTRAINT_DEFINITIONS:
			getTggAttributeConstraintDefinitions().clear();
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
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY__TGG_ATTRIBUTE_CONSTRAINT_DEFINITIONS:
			return tggAttributeConstraintDefinitions != null && !tggAttributeConstraintDefinitions.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //TGGAttributeConstraintDefinitionLibraryImpl