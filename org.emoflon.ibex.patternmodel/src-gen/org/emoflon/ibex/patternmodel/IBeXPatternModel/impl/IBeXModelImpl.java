/**
 */
package org.emoflon.ibex.patternmodel.IBeXPatternModel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEdgeSet;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXModel;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNodeSet;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternSet;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRuleSet;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IBe XModel</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXModelImpl#getPatternSet <em>Pattern Set</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXModelImpl#getRuleSet <em>Rule Set</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXModelImpl#getNodeSet <em>Node Set</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXModelImpl#getEdgeSet <em>Edge Set</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IBeXModelImpl extends MinimalEObjectImpl.Container implements IBeXModel {
	/**
	 * The cached value of the '{@link #getPatternSet() <em>Pattern Set</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPatternSet()
	 * @generated
	 * @ordered
	 */
	protected IBeXPatternSet patternSet;

	/**
	 * The cached value of the '{@link #getRuleSet() <em>Rule Set</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRuleSet()
	 * @generated
	 * @ordered
	 */
	protected IBeXRuleSet ruleSet;

	/**
	 * The cached value of the '{@link #getNodeSet() <em>Node Set</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNodeSet()
	 * @generated
	 * @ordered
	 */
	protected IBeXNodeSet nodeSet;

	/**
	 * The cached value of the '{@link #getEdgeSet() <em>Edge Set</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEdgeSet()
	 * @generated
	 * @ordered
	 */
	protected IBeXEdgeSet edgeSet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IBeXModelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXPatternModelPackage.Literals.IBE_XMODEL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXPatternSet getPatternSet() {
		return patternSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPatternSet(IBeXPatternSet newPatternSet, NotificationChain msgs) {
		IBeXPatternSet oldPatternSet = patternSet;
		patternSet = newPatternSet;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXPatternModelPackage.IBE_XMODEL__PATTERN_SET, oldPatternSet, newPatternSet);
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
	public void setPatternSet(IBeXPatternSet newPatternSet) {
		if (newPatternSet != patternSet) {
			NotificationChain msgs = null;
			if (patternSet != null)
				msgs = ((InternalEObject) patternSet).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXPatternModelPackage.IBE_XMODEL__PATTERN_SET, null, msgs);
			if (newPatternSet != null)
				msgs = ((InternalEObject) newPatternSet).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXPatternModelPackage.IBE_XMODEL__PATTERN_SET, null, msgs);
			msgs = basicSetPatternSet(newPatternSet, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXPatternModelPackage.IBE_XMODEL__PATTERN_SET,
					newPatternSet, newPatternSet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXRuleSet getRuleSet() {
		return ruleSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRuleSet(IBeXRuleSet newRuleSet, NotificationChain msgs) {
		IBeXRuleSet oldRuleSet = ruleSet;
		ruleSet = newRuleSet;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXPatternModelPackage.IBE_XMODEL__RULE_SET, oldRuleSet, newRuleSet);
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
	public void setRuleSet(IBeXRuleSet newRuleSet) {
		if (newRuleSet != ruleSet) {
			NotificationChain msgs = null;
			if (ruleSet != null)
				msgs = ((InternalEObject) ruleSet).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXPatternModelPackage.IBE_XMODEL__RULE_SET, null, msgs);
			if (newRuleSet != null)
				msgs = ((InternalEObject) newRuleSet).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXPatternModelPackage.IBE_XMODEL__RULE_SET, null, msgs);
			msgs = basicSetRuleSet(newRuleSet, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXPatternModelPackage.IBE_XMODEL__RULE_SET,
					newRuleSet, newRuleSet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXNodeSet getNodeSet() {
		return nodeSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNodeSet(IBeXNodeSet newNodeSet, NotificationChain msgs) {
		IBeXNodeSet oldNodeSet = nodeSet;
		nodeSet = newNodeSet;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXPatternModelPackage.IBE_XMODEL__NODE_SET, oldNodeSet, newNodeSet);
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
	public void setNodeSet(IBeXNodeSet newNodeSet) {
		if (newNodeSet != nodeSet) {
			NotificationChain msgs = null;
			if (nodeSet != null)
				msgs = ((InternalEObject) nodeSet).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXPatternModelPackage.IBE_XMODEL__NODE_SET, null, msgs);
			if (newNodeSet != null)
				msgs = ((InternalEObject) newNodeSet).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXPatternModelPackage.IBE_XMODEL__NODE_SET, null, msgs);
			msgs = basicSetNodeSet(newNodeSet, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXPatternModelPackage.IBE_XMODEL__NODE_SET,
					newNodeSet, newNodeSet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXEdgeSet getEdgeSet() {
		return edgeSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEdgeSet(IBeXEdgeSet newEdgeSet, NotificationChain msgs) {
		IBeXEdgeSet oldEdgeSet = edgeSet;
		edgeSet = newEdgeSet;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXPatternModelPackage.IBE_XMODEL__EDGE_SET, oldEdgeSet, newEdgeSet);
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
	public void setEdgeSet(IBeXEdgeSet newEdgeSet) {
		if (newEdgeSet != edgeSet) {
			NotificationChain msgs = null;
			if (edgeSet != null)
				msgs = ((InternalEObject) edgeSet).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXPatternModelPackage.IBE_XMODEL__EDGE_SET, null, msgs);
			if (newEdgeSet != null)
				msgs = ((InternalEObject) newEdgeSet).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXPatternModelPackage.IBE_XMODEL__EDGE_SET, null, msgs);
			msgs = basicSetEdgeSet(newEdgeSet, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXPatternModelPackage.IBE_XMODEL__EDGE_SET,
					newEdgeSet, newEdgeSet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IBeXPatternModelPackage.IBE_XMODEL__PATTERN_SET:
			return basicSetPatternSet(null, msgs);
		case IBeXPatternModelPackage.IBE_XMODEL__RULE_SET:
			return basicSetRuleSet(null, msgs);
		case IBeXPatternModelPackage.IBE_XMODEL__NODE_SET:
			return basicSetNodeSet(null, msgs);
		case IBeXPatternModelPackage.IBE_XMODEL__EDGE_SET:
			return basicSetEdgeSet(null, msgs);
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
		case IBeXPatternModelPackage.IBE_XMODEL__PATTERN_SET:
			return getPatternSet();
		case IBeXPatternModelPackage.IBE_XMODEL__RULE_SET:
			return getRuleSet();
		case IBeXPatternModelPackage.IBE_XMODEL__NODE_SET:
			return getNodeSet();
		case IBeXPatternModelPackage.IBE_XMODEL__EDGE_SET:
			return getEdgeSet();
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
		case IBeXPatternModelPackage.IBE_XMODEL__PATTERN_SET:
			setPatternSet((IBeXPatternSet) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XMODEL__RULE_SET:
			setRuleSet((IBeXRuleSet) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XMODEL__NODE_SET:
			setNodeSet((IBeXNodeSet) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XMODEL__EDGE_SET:
			setEdgeSet((IBeXEdgeSet) newValue);
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
		case IBeXPatternModelPackage.IBE_XMODEL__PATTERN_SET:
			setPatternSet((IBeXPatternSet) null);
			return;
		case IBeXPatternModelPackage.IBE_XMODEL__RULE_SET:
			setRuleSet((IBeXRuleSet) null);
			return;
		case IBeXPatternModelPackage.IBE_XMODEL__NODE_SET:
			setNodeSet((IBeXNodeSet) null);
			return;
		case IBeXPatternModelPackage.IBE_XMODEL__EDGE_SET:
			setEdgeSet((IBeXEdgeSet) null);
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
		case IBeXPatternModelPackage.IBE_XMODEL__PATTERN_SET:
			return patternSet != null;
		case IBeXPatternModelPackage.IBE_XMODEL__RULE_SET:
			return ruleSet != null;
		case IBeXPatternModelPackage.IBE_XMODEL__NODE_SET:
			return nodeSet != null;
		case IBeXPatternModelPackage.IBE_XMODEL__EDGE_SET:
			return edgeSet != null;
		}
		return super.eIsSet(featureID);
	}

} //IBeXModelImpl
