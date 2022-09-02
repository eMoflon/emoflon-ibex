/**
 */
package org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXInjectivityConstraint;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IBe XInjectivity Constraint</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXInjectivityConstraintImpl#getNodes <em>Nodes</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IBeXInjectivityConstraintImpl extends MinimalEObjectImpl.Container implements IBeXInjectivityConstraint {
	/**
	 * The cached value of the '{@link #getNodes() <em>Nodes</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNodes()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXNode> nodes;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IBeXInjectivityConstraintImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXCoreModelPackage.Literals.IBE_XINJECTIVITY_CONSTRAINT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<IBeXNode> getNodes() {
		if (nodes == null) {
			nodes = new EObjectResolvingEList<IBeXNode>(IBeXNode.class, this,
					IBeXCoreModelPackage.IBE_XINJECTIVITY_CONSTRAINT__NODES);
		}
		return nodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case IBeXCoreModelPackage.IBE_XINJECTIVITY_CONSTRAINT__NODES:
			return getNodes();
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
		case IBeXCoreModelPackage.IBE_XINJECTIVITY_CONSTRAINT__NODES:
			getNodes().clear();
			getNodes().addAll((Collection<? extends IBeXNode>) newValue);
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
		case IBeXCoreModelPackage.IBE_XINJECTIVITY_CONSTRAINT__NODES:
			getNodes().clear();
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
		case IBeXCoreModelPackage.IBE_XINJECTIVITY_CONSTRAINT__NODES:
			return nodes != null && !nodes.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //IBeXInjectivityConstraintImpl
