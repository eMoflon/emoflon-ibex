/**
 */
package org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge;
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
 *   <li>{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTForEachExpressionImpl#getTrgIterator <em>Trg Iterator</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTForEachExpressionImpl#getEdge <em>Edge</em>}</li>
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
	 * The cached value of the '{@link #getTrgIterator() <em>Trg Iterator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTrgIterator()
	 * @generated
	 * @ordered
	 */
	protected IBeXNode trgIterator;

	/**
	 * The cached value of the '{@link #getEdge() <em>Edge</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEdge()
	 * @generated
	 * @ordered
	 */
	protected IBeXEdge edge;

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
	public IBeXNode getTrgIterator() {
		return trgIterator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTrgIterator(IBeXNode newTrgIterator, NotificationChain msgs) {
		IBeXNode oldTrgIterator = trgIterator;
		trgIterator = newTrgIterator;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__TRG_ITERATOR, oldTrgIterator, newTrgIterator);
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
	public void setTrgIterator(IBeXNode newTrgIterator) {
		if (newTrgIterator != trgIterator) {
			NotificationChain msgs = null;
			if (trgIterator != null)
				msgs = ((InternalEObject) trgIterator).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__TRG_ITERATOR, null, msgs);
			if (newTrgIterator != null)
				msgs = ((InternalEObject) newTrgIterator).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__TRG_ITERATOR, null, msgs);
			msgs = basicSetTrgIterator(newTrgIterator, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__TRG_ITERATOR, newTrgIterator, newTrgIterator));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXEdge getEdge() {
		return edge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEdge(IBeXEdge newEdge, NotificationChain msgs) {
		IBeXEdge oldEdge = edge;
		edge = newEdge;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__EDGE, oldEdge, newEdge);
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
	public void setEdge(IBeXEdge newEdge) {
		if (newEdge != edge) {
			NotificationChain msgs = null;
			if (edge != null)
				msgs = ((InternalEObject) edge).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__EDGE, null, msgs);
			if (newEdge != null)
				msgs = ((InternalEObject) newEdge).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__EDGE, null, msgs);
			msgs = basicSetEdge(newEdge, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__EDGE,
					newEdge, newEdge));
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
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__TRG_ITERATOR:
			return basicSetTrgIterator(null, msgs);
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__EDGE:
			return basicSetEdge(null, msgs);
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
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__TRG_ITERATOR:
			return getTrgIterator();
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__EDGE:
			return getEdge();
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
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__TRG_ITERATOR:
			setTrgIterator((IBeXNode) newValue);
			return;
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__EDGE:
			setEdge((IBeXEdge) newValue);
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
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__TRG_ITERATOR:
			setTrgIterator((IBeXNode) null);
			return;
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__EDGE:
			setEdge((IBeXEdge) null);
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
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__TRG_ITERATOR:
			return trgIterator != null;
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION__EDGE:
			return edge != null;
		}
		return super.eIsSet(featureID);
	}

} //GTForEachExpressionImpl
