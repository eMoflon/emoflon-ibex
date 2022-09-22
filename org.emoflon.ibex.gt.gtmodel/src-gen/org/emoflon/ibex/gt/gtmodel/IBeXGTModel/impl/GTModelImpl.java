/**
 */
package org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXModelImpl;

import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTModel;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTRuleSet;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>GT Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTModelImpl#getPackage <em>Package</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTModelImpl#getProject <em>Project</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTModelImpl#getRuleSet <em>Rule Set</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GTModelImpl extends IBeXModelImpl implements GTModel {
	/**
	 * The default value of the '{@link #getPackage() <em>Package</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackage()
	 * @generated
	 * @ordered
	 */
	protected static final String PACKAGE_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getPackage() <em>Package</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackage()
	 * @generated
	 * @ordered
	 */
	protected String package_ = PACKAGE_EDEFAULT;
	/**
	 * The default value of the '{@link #getProject() <em>Project</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProject()
	 * @generated
	 * @ordered
	 */
	protected static final String PROJECT_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getProject() <em>Project</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProject()
	 * @generated
	 * @ordered
	 */
	protected String project = PROJECT_EDEFAULT;
	/**
	 * The cached value of the '{@link #getRuleSet() <em>Rule Set</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRuleSet()
	 * @generated
	 * @ordered
	 */
	protected GTRuleSet ruleSet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GTModelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXGTModelPackage.Literals.GT_MODEL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPackage() {
		return package_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPackage(String newPackage) {
		String oldPackage = package_;
		package_ = newPackage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXGTModelPackage.GT_MODEL__PACKAGE, oldPackage,
					package_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getProject() {
		return project;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProject(String newProject) {
		String oldProject = project;
		project = newProject;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXGTModelPackage.GT_MODEL__PROJECT, oldProject,
					project));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GTRuleSet getRuleSet() {
		return ruleSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRuleSet(GTRuleSet newRuleSet, NotificationChain msgs) {
		GTRuleSet oldRuleSet = ruleSet;
		ruleSet = newRuleSet;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXGTModelPackage.GT_MODEL__RULE_SET, oldRuleSet, newRuleSet);
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
	public void setRuleSet(GTRuleSet newRuleSet) {
		if (newRuleSet != ruleSet) {
			NotificationChain msgs = null;
			if (ruleSet != null)
				msgs = ((InternalEObject) ruleSet).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXGTModelPackage.GT_MODEL__RULE_SET, null, msgs);
			if (newRuleSet != null)
				msgs = ((InternalEObject) newRuleSet).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXGTModelPackage.GT_MODEL__RULE_SET, null, msgs);
			msgs = basicSetRuleSet(newRuleSet, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXGTModelPackage.GT_MODEL__RULE_SET, newRuleSet,
					newRuleSet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IBeXGTModelPackage.GT_MODEL__RULE_SET:
			return basicSetRuleSet(null, msgs);
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
		case IBeXGTModelPackage.GT_MODEL__PACKAGE:
			return getPackage();
		case IBeXGTModelPackage.GT_MODEL__PROJECT:
			return getProject();
		case IBeXGTModelPackage.GT_MODEL__RULE_SET:
			return getRuleSet();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case IBeXGTModelPackage.GT_MODEL__PACKAGE:
			setPackage((String) newValue);
			return;
		case IBeXGTModelPackage.GT_MODEL__PROJECT:
			setProject((String) newValue);
			return;
		case IBeXGTModelPackage.GT_MODEL__RULE_SET:
			setRuleSet((GTRuleSet) newValue);
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
		case IBeXGTModelPackage.GT_MODEL__PACKAGE:
			setPackage(PACKAGE_EDEFAULT);
			return;
		case IBeXGTModelPackage.GT_MODEL__PROJECT:
			setProject(PROJECT_EDEFAULT);
			return;
		case IBeXGTModelPackage.GT_MODEL__RULE_SET:
			setRuleSet((GTRuleSet) null);
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
		case IBeXGTModelPackage.GT_MODEL__PACKAGE:
			return PACKAGE_EDEFAULT == null ? package_ != null : !PACKAGE_EDEFAULT.equals(package_);
		case IBeXGTModelPackage.GT_MODEL__PROJECT:
			return PROJECT_EDEFAULT == null ? project != null : !PROJECT_EDEFAULT.equals(project);
		case IBeXGTModelPackage.GT_MODEL__RULE_SET:
			return ruleSet != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (package: ");
		result.append(package_);
		result.append(", project: ");
		result.append(project);
		result.append(')');
		return result.toString();
	}

} //GTModelImpl
