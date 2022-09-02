/**
 */
package org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeAssignment;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRule;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRuleDelta;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IBe XRule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXRuleImpl#getPrecondition <em>Precondition</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXRuleImpl#getPostcondition <em>Postcondition</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXRuleImpl#getCreation <em>Creation</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXRuleImpl#getDeletion <em>Deletion</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXRuleImpl#getAttributeAssignments <em>Attribute Assignments</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IBeXRuleImpl extends IBeXNamedElementImpl implements IBeXRule {
	/**
	 * The cached value of the '{@link #getPrecondition() <em>Precondition</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrecondition()
	 * @generated
	 * @ordered
	 */
	protected IBeXPattern precondition;

	/**
	 * The cached value of the '{@link #getPostcondition() <em>Postcondition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPostcondition()
	 * @generated
	 * @ordered
	 */
	protected IBeXPattern postcondition;

	/**
	 * The cached value of the '{@link #getCreation() <em>Creation</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreation()
	 * @generated
	 * @ordered
	 */
	protected IBeXRuleDelta creation;

	/**
	 * The cached value of the '{@link #getDeletion() <em>Deletion</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeletion()
	 * @generated
	 * @ordered
	 */
	protected IBeXRuleDelta deletion;

	/**
	 * The cached value of the '{@link #getAttributeAssignments() <em>Attribute Assignments</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeAssignments()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXAttributeAssignment> attributeAssignments;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IBeXRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXCoreModelPackage.Literals.IBE_XRULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXPattern getPrecondition() {
		if (precondition != null && precondition.eIsProxy()) {
			InternalEObject oldPrecondition = (InternalEObject) precondition;
			precondition = (IBeXPattern) eResolveProxy(oldPrecondition);
			if (precondition != oldPrecondition) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							IBeXCoreModelPackage.IBE_XRULE__PRECONDITION, oldPrecondition, precondition));
			}
		}
		return precondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXPattern basicGetPrecondition() {
		return precondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPrecondition(IBeXPattern newPrecondition) {
		IBeXPattern oldPrecondition = precondition;
		precondition = newPrecondition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXCoreModelPackage.IBE_XRULE__PRECONDITION,
					oldPrecondition, precondition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXPattern getPostcondition() {
		return postcondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPostcondition(IBeXPattern newPostcondition, NotificationChain msgs) {
		IBeXPattern oldPostcondition = postcondition;
		postcondition = newPostcondition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXCoreModelPackage.IBE_XRULE__POSTCONDITION, oldPostcondition, newPostcondition);
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
	public void setPostcondition(IBeXPattern newPostcondition) {
		if (newPostcondition != postcondition) {
			NotificationChain msgs = null;
			if (postcondition != null)
				msgs = ((InternalEObject) postcondition).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXCoreModelPackage.IBE_XRULE__POSTCONDITION, null, msgs);
			if (newPostcondition != null)
				msgs = ((InternalEObject) newPostcondition).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXCoreModelPackage.IBE_XRULE__POSTCONDITION, null, msgs);
			msgs = basicSetPostcondition(newPostcondition, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXCoreModelPackage.IBE_XRULE__POSTCONDITION,
					newPostcondition, newPostcondition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXRuleDelta getCreation() {
		return creation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCreation(IBeXRuleDelta newCreation, NotificationChain msgs) {
		IBeXRuleDelta oldCreation = creation;
		creation = newCreation;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXCoreModelPackage.IBE_XRULE__CREATION, oldCreation, newCreation);
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
	public void setCreation(IBeXRuleDelta newCreation) {
		if (newCreation != creation) {
			NotificationChain msgs = null;
			if (creation != null)
				msgs = ((InternalEObject) creation).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXCoreModelPackage.IBE_XRULE__CREATION, null, msgs);
			if (newCreation != null)
				msgs = ((InternalEObject) newCreation).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXCoreModelPackage.IBE_XRULE__CREATION, null, msgs);
			msgs = basicSetCreation(newCreation, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXCoreModelPackage.IBE_XRULE__CREATION, newCreation,
					newCreation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXRuleDelta getDeletion() {
		return deletion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDeletion(IBeXRuleDelta newDeletion, NotificationChain msgs) {
		IBeXRuleDelta oldDeletion = deletion;
		deletion = newDeletion;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXCoreModelPackage.IBE_XRULE__DELETION, oldDeletion, newDeletion);
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
	public void setDeletion(IBeXRuleDelta newDeletion) {
		if (newDeletion != deletion) {
			NotificationChain msgs = null;
			if (deletion != null)
				msgs = ((InternalEObject) deletion).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXCoreModelPackage.IBE_XRULE__DELETION, null, msgs);
			if (newDeletion != null)
				msgs = ((InternalEObject) newDeletion).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXCoreModelPackage.IBE_XRULE__DELETION, null, msgs);
			msgs = basicSetDeletion(newDeletion, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXCoreModelPackage.IBE_XRULE__DELETION, newDeletion,
					newDeletion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<IBeXAttributeAssignment> getAttributeAssignments() {
		if (attributeAssignments == null) {
			attributeAssignments = new EObjectContainmentEList<IBeXAttributeAssignment>(IBeXAttributeAssignment.class,
					this, IBeXCoreModelPackage.IBE_XRULE__ATTRIBUTE_ASSIGNMENTS);
		}
		return attributeAssignments;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IBeXCoreModelPackage.IBE_XRULE__POSTCONDITION:
			return basicSetPostcondition(null, msgs);
		case IBeXCoreModelPackage.IBE_XRULE__CREATION:
			return basicSetCreation(null, msgs);
		case IBeXCoreModelPackage.IBE_XRULE__DELETION:
			return basicSetDeletion(null, msgs);
		case IBeXCoreModelPackage.IBE_XRULE__ATTRIBUTE_ASSIGNMENTS:
			return ((InternalEList<?>) getAttributeAssignments()).basicRemove(otherEnd, msgs);
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
		case IBeXCoreModelPackage.IBE_XRULE__PRECONDITION:
			if (resolve)
				return getPrecondition();
			return basicGetPrecondition();
		case IBeXCoreModelPackage.IBE_XRULE__POSTCONDITION:
			return getPostcondition();
		case IBeXCoreModelPackage.IBE_XRULE__CREATION:
			return getCreation();
		case IBeXCoreModelPackage.IBE_XRULE__DELETION:
			return getDeletion();
		case IBeXCoreModelPackage.IBE_XRULE__ATTRIBUTE_ASSIGNMENTS:
			return getAttributeAssignments();
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
		case IBeXCoreModelPackage.IBE_XRULE__PRECONDITION:
			setPrecondition((IBeXPattern) newValue);
			return;
		case IBeXCoreModelPackage.IBE_XRULE__POSTCONDITION:
			setPostcondition((IBeXPattern) newValue);
			return;
		case IBeXCoreModelPackage.IBE_XRULE__CREATION:
			setCreation((IBeXRuleDelta) newValue);
			return;
		case IBeXCoreModelPackage.IBE_XRULE__DELETION:
			setDeletion((IBeXRuleDelta) newValue);
			return;
		case IBeXCoreModelPackage.IBE_XRULE__ATTRIBUTE_ASSIGNMENTS:
			getAttributeAssignments().clear();
			getAttributeAssignments().addAll((Collection<? extends IBeXAttributeAssignment>) newValue);
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
		case IBeXCoreModelPackage.IBE_XRULE__PRECONDITION:
			setPrecondition((IBeXPattern) null);
			return;
		case IBeXCoreModelPackage.IBE_XRULE__POSTCONDITION:
			setPostcondition((IBeXPattern) null);
			return;
		case IBeXCoreModelPackage.IBE_XRULE__CREATION:
			setCreation((IBeXRuleDelta) null);
			return;
		case IBeXCoreModelPackage.IBE_XRULE__DELETION:
			setDeletion((IBeXRuleDelta) null);
			return;
		case IBeXCoreModelPackage.IBE_XRULE__ATTRIBUTE_ASSIGNMENTS:
			getAttributeAssignments().clear();
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
		case IBeXCoreModelPackage.IBE_XRULE__PRECONDITION:
			return precondition != null;
		case IBeXCoreModelPackage.IBE_XRULE__POSTCONDITION:
			return postcondition != null;
		case IBeXCoreModelPackage.IBE_XRULE__CREATION:
			return creation != null;
		case IBeXCoreModelPackage.IBE_XRULE__DELETION:
			return deletion != null;
		case IBeXCoreModelPackage.IBE_XRULE__ATTRIBUTE_ASSIGNMENTS:
			return attributeAssignments != null && !attributeAssignments.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //IBeXRuleImpl
