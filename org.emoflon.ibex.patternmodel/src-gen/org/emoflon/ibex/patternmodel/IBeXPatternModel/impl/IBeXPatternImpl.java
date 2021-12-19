/**
 */
package org.emoflon.ibex.patternmodel.IBeXPatternModel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IBe XPattern</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXPatternImpl#isHasCountExpression <em>Has Count Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class IBeXPatternImpl extends IBeXNamedElementImpl implements IBeXPattern {
	/**
	 * The default value of the '{@link #isHasCountExpression() <em>Has Count Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHasCountExpression()
	 * @generated
	 * @ordered
	 */
	protected static final boolean HAS_COUNT_EXPRESSION_EDEFAULT = false;
	/**
	 * The cached value of the '{@link #isHasCountExpression() <em>Has Count Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHasCountExpression()
	 * @generated
	 * @ordered
	 */
	protected boolean hasCountExpression = HAS_COUNT_EXPRESSION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IBeXPatternImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXPatternModelPackage.Literals.IBE_XPATTERN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isHasCountExpression() {
		return hasCountExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setHasCountExpression(boolean newHasCountExpression) {
		boolean oldHasCountExpression = hasCountExpression;
		hasCountExpression = newHasCountExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXPatternModelPackage.IBE_XPATTERN__HAS_COUNT_EXPRESSION, oldHasCountExpression,
					hasCountExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case IBeXPatternModelPackage.IBE_XPATTERN__HAS_COUNT_EXPRESSION:
			return isHasCountExpression();
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
		case IBeXPatternModelPackage.IBE_XPATTERN__HAS_COUNT_EXPRESSION:
			setHasCountExpression((Boolean) newValue);
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
		case IBeXPatternModelPackage.IBE_XPATTERN__HAS_COUNT_EXPRESSION:
			setHasCountExpression(HAS_COUNT_EXPRESSION_EDEFAULT);
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
		case IBeXPatternModelPackage.IBE_XPATTERN__HAS_COUNT_EXPRESSION:
			return hasCountExpression != HAS_COUNT_EXPRESSION_EDEFAULT;
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
		result.append(" (hasCountExpression: ");
		result.append(hasCountExpression);
		result.append(')');
		return result.toString();
	}

} //IBeXPatternImpl
