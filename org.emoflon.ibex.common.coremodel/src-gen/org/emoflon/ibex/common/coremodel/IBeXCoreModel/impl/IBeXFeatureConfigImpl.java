/**
 */
package org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXFeatureConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IBe XFeature Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXFeatureConfigImpl#isCountExpressions <em>Count Expressions</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXFeatureConfigImpl#isArithmeticExpressions <em>Arithmetic Expressions</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXFeatureConfigImpl#isBooleanExpressions <em>Boolean Expressions</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXFeatureConfigImpl#isParameterExpressions <em>Parameter Expressions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IBeXFeatureConfigImpl extends MinimalEObjectImpl.Container implements IBeXFeatureConfig {
	/**
	 * The default value of the '{@link #isCountExpressions() <em>Count Expressions</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCountExpressions()
	 * @generated
	 * @ordered
	 */
	protected static final boolean COUNT_EXPRESSIONS_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isCountExpressions() <em>Count Expressions</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCountExpressions()
	 * @generated
	 * @ordered
	 */
	protected boolean countExpressions = COUNT_EXPRESSIONS_EDEFAULT;

	/**
	 * The default value of the '{@link #isArithmeticExpressions() <em>Arithmetic Expressions</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isArithmeticExpressions()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ARITHMETIC_EXPRESSIONS_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isArithmeticExpressions() <em>Arithmetic Expressions</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isArithmeticExpressions()
	 * @generated
	 * @ordered
	 */
	protected boolean arithmeticExpressions = ARITHMETIC_EXPRESSIONS_EDEFAULT;

	/**
	 * The default value of the '{@link #isBooleanExpressions() <em>Boolean Expressions</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isBooleanExpressions()
	 * @generated
	 * @ordered
	 */
	protected static final boolean BOOLEAN_EXPRESSIONS_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isBooleanExpressions() <em>Boolean Expressions</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isBooleanExpressions()
	 * @generated
	 * @ordered
	 */
	protected boolean booleanExpressions = BOOLEAN_EXPRESSIONS_EDEFAULT;

	/**
	 * The default value of the '{@link #isParameterExpressions() <em>Parameter Expressions</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isParameterExpressions()
	 * @generated
	 * @ordered
	 */
	protected static final boolean PARAMETER_EXPRESSIONS_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isParameterExpressions() <em>Parameter Expressions</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isParameterExpressions()
	 * @generated
	 * @ordered
	 */
	protected boolean parameterExpressions = PARAMETER_EXPRESSIONS_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IBeXFeatureConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXCoreModelPackage.Literals.IBE_XFEATURE_CONFIG;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isCountExpressions() {
		return countExpressions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCountExpressions(boolean newCountExpressions) {
		boolean oldCountExpressions = countExpressions;
		countExpressions = newCountExpressions;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXCoreModelPackage.IBE_XFEATURE_CONFIG__COUNT_EXPRESSIONS, oldCountExpressions,
					countExpressions));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isArithmeticExpressions() {
		return arithmeticExpressions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setArithmeticExpressions(boolean newArithmeticExpressions) {
		boolean oldArithmeticExpressions = arithmeticExpressions;
		arithmeticExpressions = newArithmeticExpressions;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXCoreModelPackage.IBE_XFEATURE_CONFIG__ARITHMETIC_EXPRESSIONS, oldArithmeticExpressions,
					arithmeticExpressions));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isBooleanExpressions() {
		return booleanExpressions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBooleanExpressions(boolean newBooleanExpressions) {
		boolean oldBooleanExpressions = booleanExpressions;
		booleanExpressions = newBooleanExpressions;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXCoreModelPackage.IBE_XFEATURE_CONFIG__BOOLEAN_EXPRESSIONS, oldBooleanExpressions,
					booleanExpressions));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isParameterExpressions() {
		return parameterExpressions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParameterExpressions(boolean newParameterExpressions) {
		boolean oldParameterExpressions = parameterExpressions;
		parameterExpressions = newParameterExpressions;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXCoreModelPackage.IBE_XFEATURE_CONFIG__PARAMETER_EXPRESSIONS, oldParameterExpressions,
					parameterExpressions));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case IBeXCoreModelPackage.IBE_XFEATURE_CONFIG__COUNT_EXPRESSIONS:
			return isCountExpressions();
		case IBeXCoreModelPackage.IBE_XFEATURE_CONFIG__ARITHMETIC_EXPRESSIONS:
			return isArithmeticExpressions();
		case IBeXCoreModelPackage.IBE_XFEATURE_CONFIG__BOOLEAN_EXPRESSIONS:
			return isBooleanExpressions();
		case IBeXCoreModelPackage.IBE_XFEATURE_CONFIG__PARAMETER_EXPRESSIONS:
			return isParameterExpressions();
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
		case IBeXCoreModelPackage.IBE_XFEATURE_CONFIG__COUNT_EXPRESSIONS:
			setCountExpressions((Boolean) newValue);
			return;
		case IBeXCoreModelPackage.IBE_XFEATURE_CONFIG__ARITHMETIC_EXPRESSIONS:
			setArithmeticExpressions((Boolean) newValue);
			return;
		case IBeXCoreModelPackage.IBE_XFEATURE_CONFIG__BOOLEAN_EXPRESSIONS:
			setBooleanExpressions((Boolean) newValue);
			return;
		case IBeXCoreModelPackage.IBE_XFEATURE_CONFIG__PARAMETER_EXPRESSIONS:
			setParameterExpressions((Boolean) newValue);
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
		case IBeXCoreModelPackage.IBE_XFEATURE_CONFIG__COUNT_EXPRESSIONS:
			setCountExpressions(COUNT_EXPRESSIONS_EDEFAULT);
			return;
		case IBeXCoreModelPackage.IBE_XFEATURE_CONFIG__ARITHMETIC_EXPRESSIONS:
			setArithmeticExpressions(ARITHMETIC_EXPRESSIONS_EDEFAULT);
			return;
		case IBeXCoreModelPackage.IBE_XFEATURE_CONFIG__BOOLEAN_EXPRESSIONS:
			setBooleanExpressions(BOOLEAN_EXPRESSIONS_EDEFAULT);
			return;
		case IBeXCoreModelPackage.IBE_XFEATURE_CONFIG__PARAMETER_EXPRESSIONS:
			setParameterExpressions(PARAMETER_EXPRESSIONS_EDEFAULT);
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
		case IBeXCoreModelPackage.IBE_XFEATURE_CONFIG__COUNT_EXPRESSIONS:
			return countExpressions != COUNT_EXPRESSIONS_EDEFAULT;
		case IBeXCoreModelPackage.IBE_XFEATURE_CONFIG__ARITHMETIC_EXPRESSIONS:
			return arithmeticExpressions != ARITHMETIC_EXPRESSIONS_EDEFAULT;
		case IBeXCoreModelPackage.IBE_XFEATURE_CONFIG__BOOLEAN_EXPRESSIONS:
			return booleanExpressions != BOOLEAN_EXPRESSIONS_EDEFAULT;
		case IBeXCoreModelPackage.IBE_XFEATURE_CONFIG__PARAMETER_EXPRESSIONS:
			return parameterExpressions != PARAMETER_EXPRESSIONS_EDEFAULT;
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
		result.append(" (countExpressions: ");
		result.append(countExpressions);
		result.append(", arithmeticExpressions: ");
		result.append(arithmeticExpressions);
		result.append(", booleanExpressions: ");
		result.append(booleanExpressions);
		result.append(", parameterExpressions: ");
		result.append(parameterExpressions);
		result.append(')');
		return result.toString();
	}

} //IBeXFeatureConfigImpl