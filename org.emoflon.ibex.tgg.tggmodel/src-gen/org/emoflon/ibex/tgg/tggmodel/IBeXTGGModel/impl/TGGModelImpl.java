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
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintDefinitionLibrary;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleSet;

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
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGModelImpl#getRuleSet <em>Rule Set</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGModelImpl#getAttributeConstraintDefinitionLibraries <em>Attribute Constraint Definition Libraries</em>}</li>
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
	 * The cached value of the '{@link #getRuleSet() <em>Rule Set</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRuleSet()
	 * @generated
	 * @ordered
	 */
	protected TGGRuleSet ruleSet;

	/**
	 * The cached value of the '{@link #getAttributeConstraintDefinitionLibraries() <em>Attribute Constraint Definition Libraries</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeConstraintDefinitionLibraries()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGAttributeConstraintDefinitionLibrary> attributeConstraintDefinitionLibraries;

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
	public TGGRuleSet getRuleSet() {
		return ruleSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRuleSet(TGGRuleSet newRuleSet, NotificationChain msgs) {
		TGGRuleSet oldRuleSet = ruleSet;
		ruleSet = newRuleSet;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXTGGModelPackage.TGG_MODEL__RULE_SET, oldRuleSet, newRuleSet);
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
	public void setRuleSet(TGGRuleSet newRuleSet) {
		if (newRuleSet != ruleSet) {
			NotificationChain msgs = null;
			if (ruleSet != null)
				msgs = ((InternalEObject) ruleSet).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_MODEL__RULE_SET, null, msgs);
			if (newRuleSet != null)
				msgs = ((InternalEObject) newRuleSet).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_MODEL__RULE_SET, null, msgs);
			msgs = basicSetRuleSet(newRuleSet, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXTGGModelPackage.TGG_MODEL__RULE_SET, newRuleSet,
					newRuleSet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TGGAttributeConstraintDefinitionLibrary> getAttributeConstraintDefinitionLibraries() {
		if (attributeConstraintDefinitionLibraries == null) {
			attributeConstraintDefinitionLibraries = new EObjectContainmentEList<TGGAttributeConstraintDefinitionLibrary>(
					TGGAttributeConstraintDefinitionLibrary.class, this,
					IBeXTGGModelPackage.TGG_MODEL__ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARIES);
		}
		return attributeConstraintDefinitionLibraries;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IBeXTGGModelPackage.TGG_MODEL__RULE_SET:
			return basicSetRuleSet(null, msgs);
		case IBeXTGGModelPackage.TGG_MODEL__ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARIES:
			return ((InternalEList<?>) getAttributeConstraintDefinitionLibraries()).basicRemove(otherEnd, msgs);
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
		case IBeXTGGModelPackage.TGG_MODEL__RULE_SET:
			return getRuleSet();
		case IBeXTGGModelPackage.TGG_MODEL__ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARIES:
			return getAttributeConstraintDefinitionLibraries();
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
		case IBeXTGGModelPackage.TGG_MODEL__RULE_SET:
			setRuleSet((TGGRuleSet) newValue);
			return;
		case IBeXTGGModelPackage.TGG_MODEL__ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARIES:
			getAttributeConstraintDefinitionLibraries().clear();
			getAttributeConstraintDefinitionLibraries()
					.addAll((Collection<? extends TGGAttributeConstraintDefinitionLibrary>) newValue);
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
		case IBeXTGGModelPackage.TGG_MODEL__RULE_SET:
			setRuleSet((TGGRuleSet) null);
			return;
		case IBeXTGGModelPackage.TGG_MODEL__ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARIES:
			getAttributeConstraintDefinitionLibraries().clear();
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
		case IBeXTGGModelPackage.TGG_MODEL__RULE_SET:
			return ruleSet != null;
		case IBeXTGGModelPackage.TGG_MODEL__ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARIES:
			return attributeConstraintDefinitionLibraries != null && !attributeConstraintDefinitionLibraries.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //TGGModelImpl
