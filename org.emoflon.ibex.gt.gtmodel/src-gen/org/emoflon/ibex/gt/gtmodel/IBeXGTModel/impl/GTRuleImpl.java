/**
 */
package org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ArithmeticExpression;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXRuleImpl;

import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTForEachExpression;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTParameter;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTRule;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>GT Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTRuleImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTRuleImpl#getForEachOperations <em>For Each Operations</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTRuleImpl#getProbability <em>Probability</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GTRuleImpl extends IBeXRuleImpl implements GTRule {
	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<GTParameter> parameters;

	/**
	 * The cached value of the '{@link #getForEachOperations() <em>For Each Operations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getForEachOperations()
	 * @generated
	 * @ordered
	 */
	protected EList<GTForEachExpression> forEachOperations;

	/**
	 * The cached value of the '{@link #getProbability() <em>Probability</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProbability()
	 * @generated
	 * @ordered
	 */
	protected ArithmeticExpression probability;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GTRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXGTModelPackage.Literals.GT_RULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<GTParameter> getParameters() {
		if (parameters == null) {
			parameters = new EObjectContainmentEList<GTParameter>(GTParameter.class, this,
					IBeXGTModelPackage.GT_RULE__PARAMETERS);
		}
		return parameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<GTForEachExpression> getForEachOperations() {
		if (forEachOperations == null) {
			forEachOperations = new EObjectContainmentEList<GTForEachExpression>(GTForEachExpression.class, this,
					IBeXGTModelPackage.GT_RULE__FOR_EACH_OPERATIONS);
		}
		return forEachOperations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArithmeticExpression getProbability() {
		return probability;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProbability(ArithmeticExpression newProbability, NotificationChain msgs) {
		ArithmeticExpression oldProbability = probability;
		probability = newProbability;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXGTModelPackage.GT_RULE__PROBABILITY, oldProbability, newProbability);
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
	public void setProbability(ArithmeticExpression newProbability) {
		if (newProbability != probability) {
			NotificationChain msgs = null;
			if (probability != null)
				msgs = ((InternalEObject) probability).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXGTModelPackage.GT_RULE__PROBABILITY, null, msgs);
			if (newProbability != null)
				msgs = ((InternalEObject) newProbability).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXGTModelPackage.GT_RULE__PROBABILITY, null, msgs);
			msgs = basicSetProbability(newProbability, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXGTModelPackage.GT_RULE__PROBABILITY,
					newProbability, newProbability));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IBeXGTModelPackage.GT_RULE__PARAMETERS:
			return ((InternalEList<?>) getParameters()).basicRemove(otherEnd, msgs);
		case IBeXGTModelPackage.GT_RULE__FOR_EACH_OPERATIONS:
			return ((InternalEList<?>) getForEachOperations()).basicRemove(otherEnd, msgs);
		case IBeXGTModelPackage.GT_RULE__PROBABILITY:
			return basicSetProbability(null, msgs);
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
		case IBeXGTModelPackage.GT_RULE__PARAMETERS:
			return getParameters();
		case IBeXGTModelPackage.GT_RULE__FOR_EACH_OPERATIONS:
			return getForEachOperations();
		case IBeXGTModelPackage.GT_RULE__PROBABILITY:
			return getProbability();
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
		case IBeXGTModelPackage.GT_RULE__PARAMETERS:
			getParameters().clear();
			getParameters().addAll((Collection<? extends GTParameter>) newValue);
			return;
		case IBeXGTModelPackage.GT_RULE__FOR_EACH_OPERATIONS:
			getForEachOperations().clear();
			getForEachOperations().addAll((Collection<? extends GTForEachExpression>) newValue);
			return;
		case IBeXGTModelPackage.GT_RULE__PROBABILITY:
			setProbability((ArithmeticExpression) newValue);
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
		case IBeXGTModelPackage.GT_RULE__PARAMETERS:
			getParameters().clear();
			return;
		case IBeXGTModelPackage.GT_RULE__FOR_EACH_OPERATIONS:
			getForEachOperations().clear();
			return;
		case IBeXGTModelPackage.GT_RULE__PROBABILITY:
			setProbability((ArithmeticExpression) null);
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
		case IBeXGTModelPackage.GT_RULE__PARAMETERS:
			return parameters != null && !parameters.isEmpty();
		case IBeXGTModelPackage.GT_RULE__FOR_EACH_OPERATIONS:
			return forEachOperations != null && !forEachOperations.isEmpty();
		case IBeXGTModelPackage.GT_RULE__PROBABILITY:
			return probability != null;
		}
		return super.eIsSet(featureID);
	}

} //GTRuleImpl
