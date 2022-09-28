/**
 */
package org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdgeSet;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXFeatureConfig;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModel;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModelMetadata;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNodeSet;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternSet;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IBe XModel</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXModelImpl#getMetaData <em>Meta Data</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXModelImpl#getFeatureConfig <em>Feature Config</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXModelImpl#getPatternSet <em>Pattern Set</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXModelImpl#getNodeSet <em>Node Set</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXModelImpl#getEdgeSet <em>Edge Set</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IBeXModelImpl extends IBeXNamedElementImpl implements IBeXModel {
	/**
	 * The cached value of the '{@link #getMetaData() <em>Meta Data</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMetaData()
	 * @generated
	 * @ordered
	 */
	protected IBeXModelMetadata metaData;

	/**
	 * The cached value of the '{@link #getFeatureConfig() <em>Feature Config</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeatureConfig()
	 * @generated
	 * @ordered
	 */
	protected IBeXFeatureConfig featureConfig;

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
		return IBeXCoreModelPackage.Literals.IBE_XMODEL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXModelMetadata getMetaData() {
		return metaData;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMetaData(IBeXModelMetadata newMetaData, NotificationChain msgs) {
		IBeXModelMetadata oldMetaData = metaData;
		metaData = newMetaData;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXCoreModelPackage.IBE_XMODEL__META_DATA, oldMetaData, newMetaData);
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
	public void setMetaData(IBeXModelMetadata newMetaData) {
		if (newMetaData != metaData) {
			NotificationChain msgs = null;
			if (metaData != null)
				msgs = ((InternalEObject) metaData).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXCoreModelPackage.IBE_XMODEL__META_DATA, null, msgs);
			if (newMetaData != null)
				msgs = ((InternalEObject) newMetaData).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXCoreModelPackage.IBE_XMODEL__META_DATA, null, msgs);
			msgs = basicSetMetaData(newMetaData, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXCoreModelPackage.IBE_XMODEL__META_DATA,
					newMetaData, newMetaData));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXFeatureConfig getFeatureConfig() {
		if (featureConfig != null && featureConfig.eIsProxy()) {
			InternalEObject oldFeatureConfig = (InternalEObject) featureConfig;
			featureConfig = (IBeXFeatureConfig) eResolveProxy(oldFeatureConfig);
			if (featureConfig != oldFeatureConfig) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							IBeXCoreModelPackage.IBE_XMODEL__FEATURE_CONFIG, oldFeatureConfig, featureConfig));
			}
		}
		return featureConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXFeatureConfig basicGetFeatureConfig() {
		return featureConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFeatureConfig(IBeXFeatureConfig newFeatureConfig) {
		IBeXFeatureConfig oldFeatureConfig = featureConfig;
		featureConfig = newFeatureConfig;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXCoreModelPackage.IBE_XMODEL__FEATURE_CONFIG,
					oldFeatureConfig, featureConfig));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
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
					IBeXCoreModelPackage.IBE_XMODEL__PATTERN_SET, oldPatternSet, newPatternSet);
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
	public void setPatternSet(IBeXPatternSet newPatternSet) {
		if (newPatternSet != patternSet) {
			NotificationChain msgs = null;
			if (patternSet != null)
				msgs = ((InternalEObject) patternSet).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXCoreModelPackage.IBE_XMODEL__PATTERN_SET, null, msgs);
			if (newPatternSet != null)
				msgs = ((InternalEObject) newPatternSet).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXCoreModelPackage.IBE_XMODEL__PATTERN_SET, null, msgs);
			msgs = basicSetPatternSet(newPatternSet, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXCoreModelPackage.IBE_XMODEL__PATTERN_SET,
					newPatternSet, newPatternSet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
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
					IBeXCoreModelPackage.IBE_XMODEL__NODE_SET, oldNodeSet, newNodeSet);
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
	public void setNodeSet(IBeXNodeSet newNodeSet) {
		if (newNodeSet != nodeSet) {
			NotificationChain msgs = null;
			if (nodeSet != null)
				msgs = ((InternalEObject) nodeSet).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXCoreModelPackage.IBE_XMODEL__NODE_SET, null, msgs);
			if (newNodeSet != null)
				msgs = ((InternalEObject) newNodeSet).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXCoreModelPackage.IBE_XMODEL__NODE_SET, null, msgs);
			msgs = basicSetNodeSet(newNodeSet, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXCoreModelPackage.IBE_XMODEL__NODE_SET, newNodeSet,
					newNodeSet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
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
					IBeXCoreModelPackage.IBE_XMODEL__EDGE_SET, oldEdgeSet, newEdgeSet);
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
	public void setEdgeSet(IBeXEdgeSet newEdgeSet) {
		if (newEdgeSet != edgeSet) {
			NotificationChain msgs = null;
			if (edgeSet != null)
				msgs = ((InternalEObject) edgeSet).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXCoreModelPackage.IBE_XMODEL__EDGE_SET, null, msgs);
			if (newEdgeSet != null)
				msgs = ((InternalEObject) newEdgeSet).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXCoreModelPackage.IBE_XMODEL__EDGE_SET, null, msgs);
			msgs = basicSetEdgeSet(newEdgeSet, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXCoreModelPackage.IBE_XMODEL__EDGE_SET, newEdgeSet,
					newEdgeSet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IBeXCoreModelPackage.IBE_XMODEL__META_DATA:
			return basicSetMetaData(null, msgs);
		case IBeXCoreModelPackage.IBE_XMODEL__PATTERN_SET:
			return basicSetPatternSet(null, msgs);
		case IBeXCoreModelPackage.IBE_XMODEL__NODE_SET:
			return basicSetNodeSet(null, msgs);
		case IBeXCoreModelPackage.IBE_XMODEL__EDGE_SET:
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
		case IBeXCoreModelPackage.IBE_XMODEL__META_DATA:
			return getMetaData();
		case IBeXCoreModelPackage.IBE_XMODEL__FEATURE_CONFIG:
			if (resolve)
				return getFeatureConfig();
			return basicGetFeatureConfig();
		case IBeXCoreModelPackage.IBE_XMODEL__PATTERN_SET:
			return getPatternSet();
		case IBeXCoreModelPackage.IBE_XMODEL__NODE_SET:
			return getNodeSet();
		case IBeXCoreModelPackage.IBE_XMODEL__EDGE_SET:
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
		case IBeXCoreModelPackage.IBE_XMODEL__META_DATA:
			setMetaData((IBeXModelMetadata) newValue);
			return;
		case IBeXCoreModelPackage.IBE_XMODEL__FEATURE_CONFIG:
			setFeatureConfig((IBeXFeatureConfig) newValue);
			return;
		case IBeXCoreModelPackage.IBE_XMODEL__PATTERN_SET:
			setPatternSet((IBeXPatternSet) newValue);
			return;
		case IBeXCoreModelPackage.IBE_XMODEL__NODE_SET:
			setNodeSet((IBeXNodeSet) newValue);
			return;
		case IBeXCoreModelPackage.IBE_XMODEL__EDGE_SET:
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
		case IBeXCoreModelPackage.IBE_XMODEL__META_DATA:
			setMetaData((IBeXModelMetadata) null);
			return;
		case IBeXCoreModelPackage.IBE_XMODEL__FEATURE_CONFIG:
			setFeatureConfig((IBeXFeatureConfig) null);
			return;
		case IBeXCoreModelPackage.IBE_XMODEL__PATTERN_SET:
			setPatternSet((IBeXPatternSet) null);
			return;
		case IBeXCoreModelPackage.IBE_XMODEL__NODE_SET:
			setNodeSet((IBeXNodeSet) null);
			return;
		case IBeXCoreModelPackage.IBE_XMODEL__EDGE_SET:
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
		case IBeXCoreModelPackage.IBE_XMODEL__META_DATA:
			return metaData != null;
		case IBeXCoreModelPackage.IBE_XMODEL__FEATURE_CONFIG:
			return featureConfig != null;
		case IBeXCoreModelPackage.IBE_XMODEL__PATTERN_SET:
			return patternSet != null;
		case IBeXCoreModelPackage.IBE_XMODEL__NODE_SET:
			return nodeSet != null;
		case IBeXCoreModelPackage.IBE_XMODEL__EDGE_SET:
			return edgeSet != null;
		}
		return super.eIsSet(featureID);
	}

} //IBeXModelImpl
