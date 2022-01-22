/**
 */
package org.emoflon.ibex.patternmodel.IBeXPatternModel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeConstraint;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeValue;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRelation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IBe XAttribute Constraint</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXAttributeConstraintImpl#getRelation <em>Relation</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXAttributeConstraintImpl#getLhs <em>Lhs</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXAttributeConstraintImpl#getRhs <em>Rhs</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IBeXAttributeConstraintImpl extends IBeXAttributeImpl implements IBeXAttributeConstraint {
	/**
	 * The default value of the '{@link #getRelation() <em>Relation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRelation()
	 * @generated
	 * @ordered
	 */
	protected static final IBeXRelation RELATION_EDEFAULT = IBeXRelation.GREATER_OR_EQUAL;

	/**
	 * The cached value of the '{@link #getRelation() <em>Relation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRelation()
	 * @generated
	 * @ordered
	 */
	protected IBeXRelation relation = RELATION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getLhs() <em>Lhs</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLhs()
	 * @generated
	 * @ordered
	 */
	protected IBeXAttributeValue lhs;

	/**
	 * The cached value of the '{@link #getRhs() <em>Rhs</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRhs()
	 * @generated
	 * @ordered
	 */
	protected IBeXAttributeValue rhs;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IBeXAttributeConstraintImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXPatternModelPackage.Literals.IBE_XATTRIBUTE_CONSTRAINT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXRelation getRelation() {
		return relation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRelation(IBeXRelation newRelation) {
		IBeXRelation oldRelation = relation;
		relation = newRelation == null ? RELATION_EDEFAULT : newRelation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXPatternModelPackage.IBE_XATTRIBUTE_CONSTRAINT__RELATION, oldRelation, relation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXAttributeValue getLhs() {
		return lhs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLhs(IBeXAttributeValue newLhs, NotificationChain msgs) {
		IBeXAttributeValue oldLhs = lhs;
		lhs = newLhs;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXPatternModelPackage.IBE_XATTRIBUTE_CONSTRAINT__LHS, oldLhs, newLhs);
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
	public void setLhs(IBeXAttributeValue newLhs) {
		if (newLhs != lhs) {
			NotificationChain msgs = null;
			if (lhs != null)
				msgs = ((InternalEObject) lhs).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXPatternModelPackage.IBE_XATTRIBUTE_CONSTRAINT__LHS, null, msgs);
			if (newLhs != null)
				msgs = ((InternalEObject) newLhs).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXPatternModelPackage.IBE_XATTRIBUTE_CONSTRAINT__LHS, null, msgs);
			msgs = basicSetLhs(newLhs, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXPatternModelPackage.IBE_XATTRIBUTE_CONSTRAINT__LHS, newLhs, newLhs));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXAttributeValue getRhs() {
		return rhs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRhs(IBeXAttributeValue newRhs, NotificationChain msgs) {
		IBeXAttributeValue oldRhs = rhs;
		rhs = newRhs;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXPatternModelPackage.IBE_XATTRIBUTE_CONSTRAINT__RHS, oldRhs, newRhs);
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
	public void setRhs(IBeXAttributeValue newRhs) {
		if (newRhs != rhs) {
			NotificationChain msgs = null;
			if (rhs != null)
				msgs = ((InternalEObject) rhs).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXPatternModelPackage.IBE_XATTRIBUTE_CONSTRAINT__RHS, null, msgs);
			if (newRhs != null)
				msgs = ((InternalEObject) newRhs).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXPatternModelPackage.IBE_XATTRIBUTE_CONSTRAINT__RHS, null, msgs);
			msgs = basicSetRhs(newRhs, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXPatternModelPackage.IBE_XATTRIBUTE_CONSTRAINT__RHS, newRhs, newRhs));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IBeXPatternModelPackage.IBE_XATTRIBUTE_CONSTRAINT__LHS:
			return basicSetLhs(null, msgs);
		case IBeXPatternModelPackage.IBE_XATTRIBUTE_CONSTRAINT__RHS:
			return basicSetRhs(null, msgs);
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
		case IBeXPatternModelPackage.IBE_XATTRIBUTE_CONSTRAINT__RELATION:
			return getRelation();
		case IBeXPatternModelPackage.IBE_XATTRIBUTE_CONSTRAINT__LHS:
			return getLhs();
		case IBeXPatternModelPackage.IBE_XATTRIBUTE_CONSTRAINT__RHS:
			return getRhs();
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
		case IBeXPatternModelPackage.IBE_XATTRIBUTE_CONSTRAINT__RELATION:
			setRelation((IBeXRelation) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XATTRIBUTE_CONSTRAINT__LHS:
			setLhs((IBeXAttributeValue) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XATTRIBUTE_CONSTRAINT__RHS:
			setRhs((IBeXAttributeValue) newValue);
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
		case IBeXPatternModelPackage.IBE_XATTRIBUTE_CONSTRAINT__RELATION:
			setRelation(RELATION_EDEFAULT);
			return;
		case IBeXPatternModelPackage.IBE_XATTRIBUTE_CONSTRAINT__LHS:
			setLhs((IBeXAttributeValue) null);
			return;
		case IBeXPatternModelPackage.IBE_XATTRIBUTE_CONSTRAINT__RHS:
			setRhs((IBeXAttributeValue) null);
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
		case IBeXPatternModelPackage.IBE_XATTRIBUTE_CONSTRAINT__RELATION:
			return relation != RELATION_EDEFAULT;
		case IBeXPatternModelPackage.IBE_XATTRIBUTE_CONSTRAINT__LHS:
			return lhs != null;
		case IBeXPatternModelPackage.IBE_XATTRIBUTE_CONSTRAINT__RHS:
			return rhs != null;
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

} //IBeXAttributeConstraintImpl
