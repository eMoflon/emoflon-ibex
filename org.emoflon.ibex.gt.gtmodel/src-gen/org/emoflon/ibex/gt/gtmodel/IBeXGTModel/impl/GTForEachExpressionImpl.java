/**
 */
package org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeAssignment;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTForEachExpression;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTIteratorEdge;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>GT For Each Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTForEachExpressionImpl#getCreated <em>Created</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTForEachExpressionImpl#getDeleted <em>Deleted</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTForEachExpressionImpl#getAttributeAssignments <em>Attribute Assignments</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTForEachExpressionImpl#getSource <em>Source</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTForEachExpressionImpl#getReference <em>Reference</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTForEachExpressionImpl#getIterator <em>Iterator</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GTForEachExpressionImpl extends MinimalEObjectImpl.Container implements GTForEachExpression {
	/**
	 * The cached value of the '{@link #getCreated() <em>Created</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreated()
	 * @generated
	 * @ordered
	 */
	protected EList<GTIteratorEdge> created;

	/**
	 * The cached value of the '{@link #getDeleted() <em>Deleted</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeleted()
	 * @generated
	 * @ordered
	 */
	protected EList<GTIteratorEdge> deleted;

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
	 * The cached value of the '{@link #getSource() <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected IBeXNode source;

	/**
	 * The cached value of the '{@link #getReference() <em>Reference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReference()
	 * @generated
	 * @ordered
	 */
	protected EReference reference;

	/**
	 * The cached value of the '{@link #getIterator() <em>Iterator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIterator()
	 * @generated
	 * @ordered
	 */
	protected IBeXNode iterator;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GTForEachExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXGTModelPackage.Literals.GT_FOR_EACH_EXPRESSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<GTIteratorEdge> getCreated() {
		if (created == null) {
			created = new EObjectContainmentEList<GTIteratorEdge>(GTIteratorEdge.class, this,
					IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__CREATED);
		}
		return created;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<GTIteratorEdge> getDeleted() {
		if (deleted == null) {
			deleted = new EObjectContainmentEList<GTIteratorEdge>(GTIteratorEdge.class, this,
					IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__DELETED);
		}
		return deleted;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<IBeXAttributeAssignment> getAttributeAssignments() {
		if (attributeAssignments == null) {
			attributeAssignments = new EObjectContainmentEList<IBeXAttributeAssignment>(IBeXAttributeAssignment.class,
					this, IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__ATTRIBUTE_ASSIGNMENTS);
		}
		return attributeAssignments;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXNode getSource() {
		if (source != null && source.eIsProxy()) {
			InternalEObject oldSource = (InternalEObject) source;
			source = (IBeXNode) eResolveProxy(oldSource);
			if (source != oldSource) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__SOURCE, oldSource, source));
			}
		}
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXNode basicGetSource() {
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSource(IBeXNode newSource) {
		IBeXNode oldSource = source;
		source = newSource;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__SOURCE,
					oldSource, source));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getReference() {
		if (reference != null && reference.eIsProxy()) {
			InternalEObject oldReference = (InternalEObject) reference;
			reference = (EReference) eResolveProxy(oldReference);
			if (reference != oldReference) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__REFERENCE, oldReference, reference));
			}
		}
		return reference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference basicGetReference() {
		return reference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReference(EReference newReference) {
		EReference oldReference = reference;
		reference = newReference;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__REFERENCE,
					oldReference, reference));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXNode getIterator() {
		return iterator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIterator(IBeXNode newIterator, NotificationChain msgs) {
		IBeXNode oldIterator = iterator;
		iterator = newIterator;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__ITERATOR, oldIterator, newIterator);
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
	public void setIterator(IBeXNode newIterator) {
		if (newIterator != iterator) {
			NotificationChain msgs = null;
			if (iterator != null)
				msgs = ((InternalEObject) iterator).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__ITERATOR, null, msgs);
			if (newIterator != null)
				msgs = ((InternalEObject) newIterator).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__ITERATOR, null, msgs);
			msgs = basicSetIterator(newIterator, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__ITERATOR,
					newIterator, newIterator));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__CREATED:
			return ((InternalEList<?>) getCreated()).basicRemove(otherEnd, msgs);
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__DELETED:
			return ((InternalEList<?>) getDeleted()).basicRemove(otherEnd, msgs);
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__ATTRIBUTE_ASSIGNMENTS:
			return ((InternalEList<?>) getAttributeAssignments()).basicRemove(otherEnd, msgs);
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__ITERATOR:
			return basicSetIterator(null, msgs);
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
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__CREATED:
			return getCreated();
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__DELETED:
			return getDeleted();
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__ATTRIBUTE_ASSIGNMENTS:
			return getAttributeAssignments();
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__SOURCE:
			if (resolve)
				return getSource();
			return basicGetSource();
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__REFERENCE:
			if (resolve)
				return getReference();
			return basicGetReference();
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__ITERATOR:
			return getIterator();
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
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__CREATED:
			getCreated().clear();
			getCreated().addAll((Collection<? extends GTIteratorEdge>) newValue);
			return;
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__DELETED:
			getDeleted().clear();
			getDeleted().addAll((Collection<? extends GTIteratorEdge>) newValue);
			return;
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__ATTRIBUTE_ASSIGNMENTS:
			getAttributeAssignments().clear();
			getAttributeAssignments().addAll((Collection<? extends IBeXAttributeAssignment>) newValue);
			return;
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__SOURCE:
			setSource((IBeXNode) newValue);
			return;
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__REFERENCE:
			setReference((EReference) newValue);
			return;
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__ITERATOR:
			setIterator((IBeXNode) newValue);
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
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__CREATED:
			getCreated().clear();
			return;
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__DELETED:
			getDeleted().clear();
			return;
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__ATTRIBUTE_ASSIGNMENTS:
			getAttributeAssignments().clear();
			return;
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__SOURCE:
			setSource((IBeXNode) null);
			return;
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__REFERENCE:
			setReference((EReference) null);
			return;
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__ITERATOR:
			setIterator((IBeXNode) null);
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
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__CREATED:
			return created != null && !created.isEmpty();
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__DELETED:
			return deleted != null && !deleted.isEmpty();
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__ATTRIBUTE_ASSIGNMENTS:
			return attributeAssignments != null && !attributeAssignments.isEmpty();
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__SOURCE:
			return source != null;
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__REFERENCE:
			return reference != null;
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__ITERATOR:
			return iterator != null;
		}
		return super.eIsSet(featureID);
	}

} //GTForEachExpressionImpl
