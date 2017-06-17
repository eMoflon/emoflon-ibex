/**
 */
package runtime.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import runtime.Protocol;
import runtime.RuntimePackage;
import runtime.TGGRuleApplication;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TGG Rule Application</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link runtime.impl.TGGRuleApplicationImpl#getCreatedSrc <em>Created Src</em>}</li>
 *   <li>{@link runtime.impl.TGGRuleApplicationImpl#getCreatedTrg <em>Created Trg</em>}</li>
 *   <li>{@link runtime.impl.TGGRuleApplicationImpl#getCreatedCorr <em>Created Corr</em>}</li>
 *   <li>{@link runtime.impl.TGGRuleApplicationImpl#getContextSrc <em>Context Src</em>}</li>
 *   <li>{@link runtime.impl.TGGRuleApplicationImpl#getContextTrg <em>Context Trg</em>}</li>
 *   <li>{@link runtime.impl.TGGRuleApplicationImpl#getName <em>Name</em>}</li>
 *   <li>{@link runtime.impl.TGGRuleApplicationImpl#getProtocol <em>Protocol</em>}</li>
 *   <li>{@link runtime.impl.TGGRuleApplicationImpl#isFinal <em>Final</em>}</li>
 *   <li>{@link runtime.impl.TGGRuleApplicationImpl#getNodeMappings <em>Node Mappings</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TGGRuleApplicationImpl extends EObjectImpl implements TGGRuleApplication {
	/**
	 * The cached value of the '{@link #getCreatedSrc() <em>Created Src</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreatedSrc()
	 * @generated
	 * @ordered
	 */
	protected EList<EObject> createdSrc;

	/**
	 * The cached value of the '{@link #getCreatedTrg() <em>Created Trg</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreatedTrg()
	 * @generated
	 * @ordered
	 */
	protected EList<EObject> createdTrg;

	/**
	 * The cached value of the '{@link #getCreatedCorr() <em>Created Corr</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreatedCorr()
	 * @generated
	 * @ordered
	 */
	protected EList<EObject> createdCorr;

	/**
	 * The cached value of the '{@link #getContextSrc() <em>Context Src</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContextSrc()
	 * @generated
	 * @ordered
	 */
	protected EList<EObject> contextSrc;

	/**
	 * The cached value of the '{@link #getContextTrg() <em>Context Trg</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContextTrg()
	 * @generated
	 * @ordered
	 */
	protected EList<EObject> contextTrg;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isFinal() <em>Final</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFinal()
	 * @generated
	 * @ordered
	 */
	protected static final boolean FINAL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isFinal() <em>Final</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFinal()
	 * @generated
	 * @ordered
	 */
	protected boolean final_ = FINAL_EDEFAULT;

	/**
	 * The cached value of the '{@link #getNodeMappings() <em>Node Mappings</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNodeMappings()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, EObject> nodeMappings;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TGGRuleApplicationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RuntimePackage.Literals.TGG_RULE_APPLICATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EObject> getCreatedSrc() {
		if (createdSrc == null) {
			createdSrc = new EObjectResolvingEList<EObject>(EObject.class, this,
					RuntimePackage.TGG_RULE_APPLICATION__CREATED_SRC);
		}
		return createdSrc;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EObject> getCreatedTrg() {
		if (createdTrg == null) {
			createdTrg = new EObjectResolvingEList<EObject>(EObject.class, this,
					RuntimePackage.TGG_RULE_APPLICATION__CREATED_TRG);
		}
		return createdTrg;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EObject> getCreatedCorr() {
		if (createdCorr == null) {
			createdCorr = new EObjectResolvingEList<EObject>(EObject.class, this,
					RuntimePackage.TGG_RULE_APPLICATION__CREATED_CORR);
		}
		return createdCorr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EObject> getContextSrc() {
		if (contextSrc == null) {
			contextSrc = new EObjectResolvingEList<EObject>(EObject.class, this,
					RuntimePackage.TGG_RULE_APPLICATION__CONTEXT_SRC);
		}
		return contextSrc;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EObject> getContextTrg() {
		if (contextTrg == null) {
			contextTrg = new EObjectResolvingEList<EObject>(EObject.class, this,
					RuntimePackage.TGG_RULE_APPLICATION__CONTEXT_TRG);
		}
		return contextTrg;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RuntimePackage.TGG_RULE_APPLICATION__NAME, oldName,
					name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Protocol getProtocol() {
		if (eContainerFeatureID() != RuntimePackage.TGG_RULE_APPLICATION__PROTOCOL)
			return null;
		return (Protocol) eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProtocol(Protocol newProtocol, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject) newProtocol, RuntimePackage.TGG_RULE_APPLICATION__PROTOCOL, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProtocol(Protocol newProtocol) {
		if (newProtocol != eInternalContainer()
				|| (eContainerFeatureID() != RuntimePackage.TGG_RULE_APPLICATION__PROTOCOL && newProtocol != null)) {
			if (EcoreUtil.isAncestor(this, newProtocol))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newProtocol != null)
				msgs = ((InternalEObject) newProtocol).eInverseAdd(this, RuntimePackage.PROTOCOL__STEPS, Protocol.class,
						msgs);
			msgs = basicSetProtocol(newProtocol, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RuntimePackage.TGG_RULE_APPLICATION__PROTOCOL,
					newProtocol, newProtocol));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isFinal() {
		return final_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFinal(boolean newFinal) {
		boolean oldFinal = final_;
		final_ = newFinal;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RuntimePackage.TGG_RULE_APPLICATION__FINAL, oldFinal,
					final_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, EObject> getNodeMappings() {
		if (nodeMappings == null) {
			nodeMappings = new EcoreEMap<String, EObject>(RuntimePackage.Literals.NODE_MAPPING, NodeMappingImpl.class,
					this, RuntimePackage.TGG_RULE_APPLICATION__NODE_MAPPINGS);
		}
		return nodeMappings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case RuntimePackage.TGG_RULE_APPLICATION__PROTOCOL:
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			return basicSetProtocol((Protocol) otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case RuntimePackage.TGG_RULE_APPLICATION__PROTOCOL:
			return basicSetProtocol(null, msgs);
		case RuntimePackage.TGG_RULE_APPLICATION__NODE_MAPPINGS:
			return ((InternalEList<?>) getNodeMappings()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
		case RuntimePackage.TGG_RULE_APPLICATION__PROTOCOL:
			return eInternalContainer().eInverseRemove(this, RuntimePackage.PROTOCOL__STEPS, Protocol.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case RuntimePackage.TGG_RULE_APPLICATION__CREATED_SRC:
			return getCreatedSrc();
		case RuntimePackage.TGG_RULE_APPLICATION__CREATED_TRG:
			return getCreatedTrg();
		case RuntimePackage.TGG_RULE_APPLICATION__CREATED_CORR:
			return getCreatedCorr();
		case RuntimePackage.TGG_RULE_APPLICATION__CONTEXT_SRC:
			return getContextSrc();
		case RuntimePackage.TGG_RULE_APPLICATION__CONTEXT_TRG:
			return getContextTrg();
		case RuntimePackage.TGG_RULE_APPLICATION__NAME:
			return getName();
		case RuntimePackage.TGG_RULE_APPLICATION__PROTOCOL:
			return getProtocol();
		case RuntimePackage.TGG_RULE_APPLICATION__FINAL:
			return isFinal();
		case RuntimePackage.TGG_RULE_APPLICATION__NODE_MAPPINGS:
			if (coreType)
				return getNodeMappings();
			else
				return getNodeMappings().map();
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
		case RuntimePackage.TGG_RULE_APPLICATION__CREATED_SRC:
			getCreatedSrc().clear();
			getCreatedSrc().addAll((Collection<? extends EObject>) newValue);
			return;
		case RuntimePackage.TGG_RULE_APPLICATION__CREATED_TRG:
			getCreatedTrg().clear();
			getCreatedTrg().addAll((Collection<? extends EObject>) newValue);
			return;
		case RuntimePackage.TGG_RULE_APPLICATION__CREATED_CORR:
			getCreatedCorr().clear();
			getCreatedCorr().addAll((Collection<? extends EObject>) newValue);
			return;
		case RuntimePackage.TGG_RULE_APPLICATION__CONTEXT_SRC:
			getContextSrc().clear();
			getContextSrc().addAll((Collection<? extends EObject>) newValue);
			return;
		case RuntimePackage.TGG_RULE_APPLICATION__CONTEXT_TRG:
			getContextTrg().clear();
			getContextTrg().addAll((Collection<? extends EObject>) newValue);
			return;
		case RuntimePackage.TGG_RULE_APPLICATION__NAME:
			setName((String) newValue);
			return;
		case RuntimePackage.TGG_RULE_APPLICATION__PROTOCOL:
			setProtocol((Protocol) newValue);
			return;
		case RuntimePackage.TGG_RULE_APPLICATION__FINAL:
			setFinal((Boolean) newValue);
			return;
		case RuntimePackage.TGG_RULE_APPLICATION__NODE_MAPPINGS:
			((EStructuralFeature.Setting) getNodeMappings()).set(newValue);
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
		case RuntimePackage.TGG_RULE_APPLICATION__CREATED_SRC:
			getCreatedSrc().clear();
			return;
		case RuntimePackage.TGG_RULE_APPLICATION__CREATED_TRG:
			getCreatedTrg().clear();
			return;
		case RuntimePackage.TGG_RULE_APPLICATION__CREATED_CORR:
			getCreatedCorr().clear();
			return;
		case RuntimePackage.TGG_RULE_APPLICATION__CONTEXT_SRC:
			getContextSrc().clear();
			return;
		case RuntimePackage.TGG_RULE_APPLICATION__CONTEXT_TRG:
			getContextTrg().clear();
			return;
		case RuntimePackage.TGG_RULE_APPLICATION__NAME:
			setName(NAME_EDEFAULT);
			return;
		case RuntimePackage.TGG_RULE_APPLICATION__PROTOCOL:
			setProtocol((Protocol) null);
			return;
		case RuntimePackage.TGG_RULE_APPLICATION__FINAL:
			setFinal(FINAL_EDEFAULT);
			return;
		case RuntimePackage.TGG_RULE_APPLICATION__NODE_MAPPINGS:
			getNodeMappings().clear();
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
		case RuntimePackage.TGG_RULE_APPLICATION__CREATED_SRC:
			return createdSrc != null && !createdSrc.isEmpty();
		case RuntimePackage.TGG_RULE_APPLICATION__CREATED_TRG:
			return createdTrg != null && !createdTrg.isEmpty();
		case RuntimePackage.TGG_RULE_APPLICATION__CREATED_CORR:
			return createdCorr != null && !createdCorr.isEmpty();
		case RuntimePackage.TGG_RULE_APPLICATION__CONTEXT_SRC:
			return contextSrc != null && !contextSrc.isEmpty();
		case RuntimePackage.TGG_RULE_APPLICATION__CONTEXT_TRG:
			return contextTrg != null && !contextTrg.isEmpty();
		case RuntimePackage.TGG_RULE_APPLICATION__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case RuntimePackage.TGG_RULE_APPLICATION__PROTOCOL:
			return getProtocol() != null;
		case RuntimePackage.TGG_RULE_APPLICATION__FINAL:
			return final_ != FINAL_EDEFAULT;
		case RuntimePackage.TGG_RULE_APPLICATION__NODE_MAPPINGS:
			return nodeMappings != null && !nodeMappings.isEmpty();
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

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", final: ");
		result.append(final_);
		result.append(')');
		return result.toString();
	}
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} //TGGRuleApplicationImpl
