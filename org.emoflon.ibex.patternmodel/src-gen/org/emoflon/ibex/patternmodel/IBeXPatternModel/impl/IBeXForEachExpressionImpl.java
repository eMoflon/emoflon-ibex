/**
 */
package org.emoflon.ibex.patternmodel.IBeXPatternModel.impl;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCreatePattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDeletePattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEdge;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXForEachExpression;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IBe XFor Each Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXForEachExpressionImpl#getCreate <em>Create</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXForEachExpressionImpl#getDelete <em>Delete</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXForEachExpressionImpl#getSource <em>Source</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXForEachExpressionImpl#getTrgIterator <em>Trg Iterator</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXForEachExpressionImpl#getEdge <em>Edge</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXForEachExpressionImpl#getCreatedEdges <em>Created Edges</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXForEachExpressionImpl#getDeletedEdges <em>Deleted Edges</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IBeXForEachExpressionImpl extends MinimalEObjectImpl.Container implements IBeXForEachExpression {
	/**
	 * The cached value of the '{@link #getCreate() <em>Create</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreate()
	 * @generated
	 * @ordered
	 */
	protected IBeXCreatePattern create;

	/**
	 * The cached value of the '{@link #getDelete() <em>Delete</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDelete()
	 * @generated
	 * @ordered
	 */
	protected IBeXDeletePattern delete;

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
	 * The cached value of the '{@link #getCreatedEdges() <em>Created Edges</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreatedEdges()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXEdge> createdEdges;

	/**
	 * The cached value of the '{@link #getDeletedEdges() <em>Deleted Edges</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeletedEdges()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXEdge> deletedEdges;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IBeXForEachExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXPatternModelPackage.Literals.IBE_XFOR_EACH_EXPRESSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXCreatePattern getCreate() {
		return create;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCreate(IBeXCreatePattern newCreate, NotificationChain msgs) {
		IBeXCreatePattern oldCreate = create;
		create = newCreate;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__CREATE, oldCreate, newCreate);
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
	public void setCreate(IBeXCreatePattern newCreate) {
		if (newCreate != create) {
			NotificationChain msgs = null;
			if (create != null)
				msgs = ((InternalEObject) create).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__CREATE, null, msgs);
			if (newCreate != null)
				msgs = ((InternalEObject) newCreate).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__CREATE, null, msgs);
			msgs = basicSetCreate(newCreate, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__CREATE, newCreate, newCreate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXDeletePattern getDelete() {
		return delete;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDelete(IBeXDeletePattern newDelete, NotificationChain msgs) {
		IBeXDeletePattern oldDelete = delete;
		delete = newDelete;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__DELETE, oldDelete, newDelete);
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
	public void setDelete(IBeXDeletePattern newDelete) {
		if (newDelete != delete) {
			NotificationChain msgs = null;
			if (delete != null)
				msgs = ((InternalEObject) delete).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__DELETE, null, msgs);
			if (newDelete != null)
				msgs = ((InternalEObject) newDelete).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__DELETE, null, msgs);
			msgs = basicSetDelete(newDelete, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__DELETE, newDelete, newDelete));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXNode getSource() {
		if (source != null && source.eIsProxy()) {
			InternalEObject oldSource = (InternalEObject) source;
			source = (IBeXNode) eResolveProxy(oldSource);
			if (source != oldSource) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__SOURCE, oldSource, source));
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
	@Override
	public void setSource(IBeXNode newSource) {
		IBeXNode oldSource = source;
		source = newSource;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__SOURCE, oldSource, source));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
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
					IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__TRG_ITERATOR, oldTrgIterator, newTrgIterator);
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
	public void setTrgIterator(IBeXNode newTrgIterator) {
		if (newTrgIterator != trgIterator) {
			NotificationChain msgs = null;
			if (trgIterator != null)
				msgs = ((InternalEObject) trgIterator).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__TRG_ITERATOR, null,
						msgs);
			if (newTrgIterator != null)
				msgs = ((InternalEObject) newTrgIterator).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__TRG_ITERATOR, null,
						msgs);
			msgs = basicSetTrgIterator(newTrgIterator, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__TRG_ITERATOR, newTrgIterator, newTrgIterator));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
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
					IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__EDGE, oldEdge, newEdge);
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
	public void setEdge(IBeXEdge newEdge) {
		if (newEdge != edge) {
			NotificationChain msgs = null;
			if (edge != null)
				msgs = ((InternalEObject) edge).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__EDGE, null, msgs);
			if (newEdge != null)
				msgs = ((InternalEObject) newEdge).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__EDGE, null, msgs);
			msgs = basicSetEdge(newEdge, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__EDGE, newEdge, newEdge));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXEdge> getCreatedEdges() {
		if (createdEdges == null) {
			createdEdges = new EObjectContainmentEList<IBeXEdge>(IBeXEdge.class, this,
					IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__CREATED_EDGES);
		}
		return createdEdges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXEdge> getDeletedEdges() {
		if (deletedEdges == null) {
			deletedEdges = new EObjectContainmentEList<IBeXEdge>(IBeXEdge.class, this,
					IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__DELETED_EDGES);
		}
		return deletedEdges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__CREATE:
			return basicSetCreate(null, msgs);
		case IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__DELETE:
			return basicSetDelete(null, msgs);
		case IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__TRG_ITERATOR:
			return basicSetTrgIterator(null, msgs);
		case IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__EDGE:
			return basicSetEdge(null, msgs);
		case IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__CREATED_EDGES:
			return ((InternalEList<?>) getCreatedEdges()).basicRemove(otherEnd, msgs);
		case IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__DELETED_EDGES:
			return ((InternalEList<?>) getDeletedEdges()).basicRemove(otherEnd, msgs);
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
		case IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__CREATE:
			return getCreate();
		case IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__DELETE:
			return getDelete();
		case IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__SOURCE:
			if (resolve)
				return getSource();
			return basicGetSource();
		case IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__TRG_ITERATOR:
			return getTrgIterator();
		case IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__EDGE:
			return getEdge();
		case IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__CREATED_EDGES:
			return getCreatedEdges();
		case IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__DELETED_EDGES:
			return getDeletedEdges();
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
		case IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__CREATE:
			setCreate((IBeXCreatePattern) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__DELETE:
			setDelete((IBeXDeletePattern) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__SOURCE:
			setSource((IBeXNode) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__TRG_ITERATOR:
			setTrgIterator((IBeXNode) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__EDGE:
			setEdge((IBeXEdge) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__CREATED_EDGES:
			getCreatedEdges().clear();
			getCreatedEdges().addAll((Collection<? extends IBeXEdge>) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__DELETED_EDGES:
			getDeletedEdges().clear();
			getDeletedEdges().addAll((Collection<? extends IBeXEdge>) newValue);
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
		case IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__CREATE:
			setCreate((IBeXCreatePattern) null);
			return;
		case IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__DELETE:
			setDelete((IBeXDeletePattern) null);
			return;
		case IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__SOURCE:
			setSource((IBeXNode) null);
			return;
		case IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__TRG_ITERATOR:
			setTrgIterator((IBeXNode) null);
			return;
		case IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__EDGE:
			setEdge((IBeXEdge) null);
			return;
		case IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__CREATED_EDGES:
			getCreatedEdges().clear();
			return;
		case IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__DELETED_EDGES:
			getDeletedEdges().clear();
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
		case IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__CREATE:
			return create != null;
		case IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__DELETE:
			return delete != null;
		case IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__SOURCE:
			return source != null;
		case IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__TRG_ITERATOR:
			return trgIterator != null;
		case IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__EDGE:
			return edge != null;
		case IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__CREATED_EDGES:
			return createdEdges != null && !createdEdges.isEmpty();
		case IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION__DELETED_EDGES:
			return deletedEdges != null && !deletedEdges.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //IBeXForEachExpressionImpl
