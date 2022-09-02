/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXModelImpl;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TGG Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGModelImpl#getSource <em>Source</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGModelImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGModelImpl#getCorrespondence <em>Correspondence</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGModelImpl#getRules <em>Rules</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TGGModelImpl extends IBeXModelImpl implements TGGModel {
	/**
	 * The cached value of the '{@link #getSource() <em>Source</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected EList<EPackage> source;

	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected EList<EPackage> target;

	/**
	 * The cached value of the '{@link #getCorrespondence() <em>Correspondence</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCorrespondence()
	 * @generated
	 * @ordered
	 */
	protected EPackage correspondence;

	/**
	 * The cached value of the '{@link #getRules() <em>Rules</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRules()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGRule> rules;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TGGModelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXTGGModelPackage.Literals.TGG_MODEL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EPackage> getSource() {
		if (source == null) {
			source = new EObjectResolvingEList<EPackage>(EPackage.class, this, IBeXTGGModelPackage.TGG_MODEL__SOURCE);
		}
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EPackage> getTarget() {
		if (target == null) {
			target = new EObjectResolvingEList<EPackage>(EPackage.class, this, IBeXTGGModelPackage.TGG_MODEL__TARGET);
		}
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EPackage getCorrespondence() {
		if (correspondence != null && correspondence.eIsProxy()) {
			InternalEObject oldCorrespondence = (InternalEObject) correspondence;
			correspondence = (EPackage) eResolveProxy(oldCorrespondence);
			if (correspondence != oldCorrespondence) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							IBeXTGGModelPackage.TGG_MODEL__CORRESPONDENCE, oldCorrespondence, correspondence));
			}
		}
		return correspondence;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EPackage basicGetCorrespondence() {
		return correspondence;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCorrespondence(EPackage newCorrespondence) {
		EPackage oldCorrespondence = correspondence;
		correspondence = newCorrespondence;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXTGGModelPackage.TGG_MODEL__CORRESPONDENCE,
					oldCorrespondence, correspondence));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TGGRule> getRules() {
		if (rules == null) {
			rules = new EObjectContainmentEList<TGGRule>(TGGRule.class, this, IBeXTGGModelPackage.TGG_MODEL__RULES);
		}
		return rules;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IBeXTGGModelPackage.TGG_MODEL__RULES:
			return ((InternalEList<?>) getRules()).basicRemove(otherEnd, msgs);
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
		case IBeXTGGModelPackage.TGG_MODEL__SOURCE:
			return getSource();
		case IBeXTGGModelPackage.TGG_MODEL__TARGET:
			return getTarget();
		case IBeXTGGModelPackage.TGG_MODEL__CORRESPONDENCE:
			if (resolve)
				return getCorrespondence();
			return basicGetCorrespondence();
		case IBeXTGGModelPackage.TGG_MODEL__RULES:
			return getRules();
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
		case IBeXTGGModelPackage.TGG_MODEL__SOURCE:
			getSource().clear();
			getSource().addAll((Collection<? extends EPackage>) newValue);
			return;
		case IBeXTGGModelPackage.TGG_MODEL__TARGET:
			getTarget().clear();
			getTarget().addAll((Collection<? extends EPackage>) newValue);
			return;
		case IBeXTGGModelPackage.TGG_MODEL__CORRESPONDENCE:
			setCorrespondence((EPackage) newValue);
			return;
		case IBeXTGGModelPackage.TGG_MODEL__RULES:
			getRules().clear();
			getRules().addAll((Collection<? extends TGGRule>) newValue);
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
		case IBeXTGGModelPackage.TGG_MODEL__SOURCE:
			getSource().clear();
			return;
		case IBeXTGGModelPackage.TGG_MODEL__TARGET:
			getTarget().clear();
			return;
		case IBeXTGGModelPackage.TGG_MODEL__CORRESPONDENCE:
			setCorrespondence((EPackage) null);
			return;
		case IBeXTGGModelPackage.TGG_MODEL__RULES:
			getRules().clear();
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
		case IBeXTGGModelPackage.TGG_MODEL__SOURCE:
			return source != null && !source.isEmpty();
		case IBeXTGGModelPackage.TGG_MODEL__TARGET:
			return target != null && !target.isEmpty();
		case IBeXTGGModelPackage.TGG_MODEL__CORRESPONDENCE:
			return correspondence != null;
		case IBeXTGGModelPackage.TGG_MODEL__RULES:
			return rules != null && !rules.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //TGGModelImpl
