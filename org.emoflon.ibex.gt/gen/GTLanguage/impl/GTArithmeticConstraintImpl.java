/**
 */
package GTLanguage.impl;

import GTLanguage.GTArithmeticConstraint;
import GTLanguage.GTLanguagePackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.emoflon.ibex.gt.SGTPatternModel.GTArithmetics;
import org.emoflon.ibex.gt.SGTPatternModel.GTAttribute;
import org.emoflon.ibex.gt.SGTPatternModel.GTRelation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>GT Arithmetic Constraint</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link GTLanguage.impl.GTArithmeticConstraintImpl#getParameter <em>Parameter</em>}</li>
 *   <li>{@link GTLanguage.impl.GTArithmeticConstraintImpl#getExpression <em>Expression</em>}</li>
 *   <li>{@link GTLanguage.impl.GTArithmeticConstraintImpl#getRelation <em>Relation</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GTArithmeticConstraintImpl extends EObjectImpl implements GTArithmeticConstraint {
	/**
	 * The cached value of the '{@link #getParameter() <em>Parameter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameter()
	 * @generated
	 * @ordered
	 */
	protected GTAttribute parameter;

	/**
	 * The cached value of the '{@link #getExpression() <em>Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExpression()
	 * @generated
	 * @ordered
	 */
	protected GTArithmetics expression;

	/**
	 * The default value of the '{@link #getRelation() <em>Relation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRelation()
	 * @generated
	 * @ordered
	 */
	protected static final GTRelation RELATION_EDEFAULT = GTRelation.GREATER_OR_EQUAL;

	/**
	 * The cached value of the '{@link #getRelation() <em>Relation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRelation()
	 * @generated
	 * @ordered
	 */
	protected GTRelation relation = RELATION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GTArithmeticConstraintImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GTLanguagePackage.Literals.GT_ARITHMETIC_CONSTRAINT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GTAttribute getParameter() {
		return parameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParameter(GTAttribute newParameter, NotificationChain msgs) {
		GTAttribute oldParameter = parameter;
		parameter = newParameter;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					GTLanguagePackage.GT_ARITHMETIC_CONSTRAINT__PARAMETER, oldParameter, newParameter);
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
	@Override
	public void setParameter(GTAttribute newParameter) {
		if (newParameter != parameter) {
			NotificationChain msgs = null;
			if (parameter != null)
				msgs = ((InternalEObject) parameter).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - GTLanguagePackage.GT_ARITHMETIC_CONSTRAINT__PARAMETER, null, msgs);
			if (newParameter != null)
				msgs = ((InternalEObject) newParameter).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - GTLanguagePackage.GT_ARITHMETIC_CONSTRAINT__PARAMETER, null, msgs);
			msgs = basicSetParameter(newParameter, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GTLanguagePackage.GT_ARITHMETIC_CONSTRAINT__PARAMETER,
					newParameter, newParameter));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GTArithmetics getExpression() {
		return expression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExpression(GTArithmetics newExpression, NotificationChain msgs) {
		GTArithmetics oldExpression = expression;
		expression = newExpression;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					GTLanguagePackage.GT_ARITHMETIC_CONSTRAINT__EXPRESSION, oldExpression, newExpression);
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
	@Override
	public void setExpression(GTArithmetics newExpression) {
		if (newExpression != expression) {
			NotificationChain msgs = null;
			if (expression != null)
				msgs = ((InternalEObject) expression).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - GTLanguagePackage.GT_ARITHMETIC_CONSTRAINT__EXPRESSION, null, msgs);
			if (newExpression != null)
				msgs = ((InternalEObject) newExpression).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - GTLanguagePackage.GT_ARITHMETIC_CONSTRAINT__EXPRESSION, null, msgs);
			msgs = basicSetExpression(newExpression, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					GTLanguagePackage.GT_ARITHMETIC_CONSTRAINT__EXPRESSION, newExpression, newExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GTRelation getRelation() {
		return relation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRelation(GTRelation newRelation) {
		GTRelation oldRelation = relation;
		relation = newRelation == null ? RELATION_EDEFAULT : newRelation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GTLanguagePackage.GT_ARITHMETIC_CONSTRAINT__RELATION,
					oldRelation, relation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case GTLanguagePackage.GT_ARITHMETIC_CONSTRAINT__PARAMETER:
			return basicSetParameter(null, msgs);
		case GTLanguagePackage.GT_ARITHMETIC_CONSTRAINT__EXPRESSION:
			return basicSetExpression(null, msgs);
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
		case GTLanguagePackage.GT_ARITHMETIC_CONSTRAINT__PARAMETER:
			return getParameter();
		case GTLanguagePackage.GT_ARITHMETIC_CONSTRAINT__EXPRESSION:
			return getExpression();
		case GTLanguagePackage.GT_ARITHMETIC_CONSTRAINT__RELATION:
			return getRelation();
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
		case GTLanguagePackage.GT_ARITHMETIC_CONSTRAINT__PARAMETER:
			setParameter((GTAttribute) newValue);
			return;
		case GTLanguagePackage.GT_ARITHMETIC_CONSTRAINT__EXPRESSION:
			setExpression((GTArithmetics) newValue);
			return;
		case GTLanguagePackage.GT_ARITHMETIC_CONSTRAINT__RELATION:
			setRelation((GTRelation) newValue);
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
		case GTLanguagePackage.GT_ARITHMETIC_CONSTRAINT__PARAMETER:
			setParameter((GTAttribute) null);
			return;
		case GTLanguagePackage.GT_ARITHMETIC_CONSTRAINT__EXPRESSION:
			setExpression((GTArithmetics) null);
			return;
		case GTLanguagePackage.GT_ARITHMETIC_CONSTRAINT__RELATION:
			setRelation(RELATION_EDEFAULT);
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
		case GTLanguagePackage.GT_ARITHMETIC_CONSTRAINT__PARAMETER:
			return parameter != null;
		case GTLanguagePackage.GT_ARITHMETIC_CONSTRAINT__EXPRESSION:
			return expression != null;
		case GTLanguagePackage.GT_ARITHMETIC_CONSTRAINT__RELATION:
			return relation != RELATION_EDEFAULT;
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
		result.append(" (relation: ");
		result.append(relation);
		result.append(')');
		return result.toString();
	}

} //GTArithmeticConstraintImpl
