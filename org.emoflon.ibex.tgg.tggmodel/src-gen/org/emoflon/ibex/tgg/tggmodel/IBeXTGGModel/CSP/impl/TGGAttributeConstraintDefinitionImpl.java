/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXNamedElementImpl;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.CSPPackage;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintBinding;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintDefinition;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintDefinitionLibrary;
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
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGAttributeConstraintDefinitionImpl#getLibrary <em>Library</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TGGAttributeConstraintDefinitionImpl extends IBeXNamedElementImpl
		implements TGGAttributeConstraintDefinition {
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
					TGGAttributeConstraintParameterDefinition.class, this,
					CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__PARAMETER_DEFINITIONS);
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
			syncBindings = new EObjectContainmentEList<TGGAttributeConstraintBinding>(
					TGGAttributeConstraintBinding.class, this,
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
			genBindings = new EObjectContainmentEList<TGGAttributeConstraintBinding>(
					TGGAttributeConstraintBinding.class, this,
					CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__GEN_BINDINGS);
		}
		return genBindings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGAttributeConstraintDefinitionLibrary getLibrary() {
		if (eContainerFeatureID() != CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__LIBRARY)
			return null;
		return (TGGAttributeConstraintDefinitionLibrary) eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLibrary(TGGAttributeConstraintDefinitionLibrary newLibrary,
			NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject) newLibrary, CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__LIBRARY,
				msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLibrary(TGGAttributeConstraintDefinitionLibrary newLibrary) {
		if (newLibrary != eInternalContainer()
				|| (eContainerFeatureID() != CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__LIBRARY
						&& newLibrary != null)) {
			if (EcoreUtil.isAncestor(this, newLibrary))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newLibrary != null)
				msgs = ((InternalEObject) newLibrary).eInverseAdd(this,
						CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY__TGG_ATTRIBUTE_CONSTRAINT_DEFINITIONS,
						TGGAttributeConstraintDefinitionLibrary.class, msgs);
			msgs = basicSetLibrary(newLibrary, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__LIBRARY, newLibrary, newLibrary));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__LIBRARY:
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			return basicSetLibrary((TGGAttributeConstraintDefinitionLibrary) otherEnd, msgs);
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
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__PARAMETER_DEFINITIONS:
			return ((InternalEList<?>) getParameterDefinitions()).basicRemove(otherEnd, msgs);
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__SYNC_BINDINGS:
			return ((InternalEList<?>) getSyncBindings()).basicRemove(otherEnd, msgs);
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__GEN_BINDINGS:
			return ((InternalEList<?>) getGenBindings()).basicRemove(otherEnd, msgs);
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__LIBRARY:
			return basicSetLibrary(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__LIBRARY:
			return eInternalContainer().eInverseRemove(this,
					CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY__TGG_ATTRIBUTE_CONSTRAINT_DEFINITIONS,
					TGGAttributeConstraintDefinitionLibrary.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
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
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__LIBRARY:
			return getLibrary();
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
			getParameterDefinitions()
					.addAll((Collection<? extends TGGAttributeConstraintParameterDefinition>) newValue);
			return;
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__SYNC_BINDINGS:
			getSyncBindings().clear();
			getSyncBindings().addAll((Collection<? extends TGGAttributeConstraintBinding>) newValue);
			return;
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__GEN_BINDINGS:
			getGenBindings().clear();
			getGenBindings().addAll((Collection<? extends TGGAttributeConstraintBinding>) newValue);
			return;
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__LIBRARY:
			setLibrary((TGGAttributeConstraintDefinitionLibrary) newValue);
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
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__LIBRARY:
			setLibrary((TGGAttributeConstraintDefinitionLibrary) null);
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
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__LIBRARY:
			return getLibrary() != null;
		}
		return super.eIsSet(featureID);
	}

} //TGGAttributeConstraintDefinitionImpl
