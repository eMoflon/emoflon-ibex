/**
 */
package org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRuleDelta;

import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTForEachExpression;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>GT For Each Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTForEachExpressionImpl#getCreate <em>Create</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTForEachExpressionImpl#getDelete <em>Delete</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTForEachExpressionImpl#getSource <em>Source</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTForEachExpressionImpl#getReference <em>Reference</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTForEachExpressionImpl#getIterator <em>Iterator</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GTForEachExpressionImpl extends MinimalEObjectImpl.Container implements GTForEachExpression {
	/**
	 * The cached value of the '{@link #getCreate() <em>Create</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreate()
	 * @generated
	 * @ordered
	 */
	protected IBeXRuleDelta create;

	/**
	 * The cached value of the '{@link #getDelete() <em>Delete</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDelete()
	 * @generated
	 * @ordered
	 */
	protected IBeXRuleDelta delete;

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
	public IBeXRuleDelta getCreate() {
		return create;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCreate(IBeXRuleDelta newCreate, NotificationChain msgs) {
		IBeXRuleDelta oldCreate = create;
		create = newCreate;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__CREATE, oldCreate, newCreate);
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
	public void setCreate(IBeXRuleDelta newCreate) {
		if (newCreate != create) {
			NotificationChain msgs = null;
			if (create != null)
				msgs = ((InternalEObject) create).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__CREATE, null, msgs);
			if (newCreate != null)
				msgs = ((InternalEObject) newCreate).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__CREATE, null, msgs);
			msgs = basicSetCreate(newCreate, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__CREATE,
					newCreate, newCreate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXRuleDelta getDelete() {
		return delete;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDelete(IBeXRuleDelta newDelete, NotificationChain msgs) {
		IBeXRuleDelta oldDelete = delete;
		delete = newDelete;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__DELETE, oldDelete, newDelete);
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
	public void setDelete(IBeXRuleDelta newDelete) {
		if (newDelete != delete) {
			NotificationChain msgs = null;
			if (delete != null)
				msgs = ((InternalEObject) delete).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__DELETE, null, msgs);
			if (newDelete != null)
				msgs = ((InternalEObject) newDelete).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__DELETE, null, msgs);
			msgs = basicSetDelete(newDelete, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__DELETE,
					newDelete, newDelete));
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
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__CREATE:
			return basicSetCreate(null, msgs);
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__DELETE:
			return basicSetDelete(null, msgs);
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
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__CREATE:
			return getCreate();
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__DELETE:
			return getDelete();
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
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__CREATE:
			setCreate((IBeXRuleDelta) newValue);
			return;
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__DELETE:
			setDelete((IBeXRuleDelta) newValue);
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
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__CREATE:
			setCreate((IBeXRuleDelta) null);
			return;
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__DELETE:
			setDelete((IBeXRuleDelta) null);
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
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__CREATE:
			return create != null;
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__DELETE:
			return delete != null;
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
