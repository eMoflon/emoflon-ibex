/**
 */
package org.emoflon.ibex.gt.StateModel.impl;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.gt.StateModel.AttributeDelta;
import org.emoflon.ibex.gt.StateModel.RuleState;
import org.emoflon.ibex.gt.StateModel.State;
import org.emoflon.ibex.gt.StateModel.StateModelPackage;
import org.emoflon.ibex.gt.StateModel.StructuralDelta;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Rule State</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.impl.RuleStateImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.impl.RuleStateImpl#getRule <em>Rule</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.impl.RuleStateImpl#getParameter <em>Parameter</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.impl.RuleStateImpl#getMatch <em>Match</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.impl.RuleStateImpl#getCoMatch <em>Co Match</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.impl.RuleStateImpl#getStructuralDelta <em>Structural Delta</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.impl.RuleStateImpl#getAttributeDeltas <em>Attribute Deltas</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RuleStateImpl extends StateImpl implements RuleState {
	/**
	 * The cached value of the '{@link #getParent() <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParent()
	 * @generated
	 * @ordered
	 */
	protected State parent;

	/**
	 * The cached value of the '{@link #getRule() <em>Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRule()
	 * @generated
	 * @ordered
	 */
	protected IBeXRule rule;

	/**
	 * The default value of the '{@link #getParameter() <em>Parameter</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameter()
	 * @generated
	 * @ordered
	 */
	protected static final Object PARAMETER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getParameter() <em>Parameter</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameter()
	 * @generated
	 * @ordered
	 */
	protected Object parameter = PARAMETER_EDEFAULT;

	/**
	 * The default value of the '{@link #getMatch() <em>Match</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMatch()
	 * @generated
	 * @ordered
	 */
	protected static final Object MATCH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMatch() <em>Match</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMatch()
	 * @generated
	 * @ordered
	 */
	protected Object match = MATCH_EDEFAULT;

	/**
	 * The default value of the '{@link #getCoMatch() <em>Co Match</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCoMatch()
	 * @generated
	 * @ordered
	 */
	protected static final Object CO_MATCH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCoMatch() <em>Co Match</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCoMatch()
	 * @generated
	 * @ordered
	 */
	protected Object coMatch = CO_MATCH_EDEFAULT;

	/**
	 * The cached value of the '{@link #getStructuralDelta() <em>Structural Delta</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStructuralDelta()
	 * @generated
	 * @ordered
	 */
	protected StructuralDelta structuralDelta;

	/**
	 * The cached value of the '{@link #getAttributeDeltas() <em>Attribute Deltas</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeDeltas()
	 * @generated
	 * @ordered
	 */
	protected EList<AttributeDelta> attributeDeltas;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RuleStateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return StateModelPackage.Literals.RULE_STATE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public State getParent() {
		if (parent != null && parent.eIsProxy()) {
			InternalEObject oldParent = (InternalEObject) parent;
			parent = (State) eResolveProxy(oldParent);
			if (parent != oldParent) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, StateModelPackage.RULE_STATE__PARENT,
							oldParent, parent));
			}
		}
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public State basicGetParent() {
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setParent(State newParent) {
		State oldParent = parent;
		parent = newParent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateModelPackage.RULE_STATE__PARENT, oldParent,
					parent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXRule getRule() {
		if (rule != null && rule.eIsProxy()) {
			InternalEObject oldRule = (InternalEObject) rule;
			rule = (IBeXRule) eResolveProxy(oldRule);
			if (rule != oldRule) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, StateModelPackage.RULE_STATE__RULE,
							oldRule, rule));
			}
		}
		return rule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXRule basicGetRule() {
		return rule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRule(IBeXRule newRule) {
		IBeXRule oldRule = rule;
		rule = newRule;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateModelPackage.RULE_STATE__RULE, oldRule, rule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getParameter() {
		return parameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setParameter(Object newParameter) {
		Object oldParameter = parameter;
		parameter = newParameter;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateModelPackage.RULE_STATE__PARAMETER, oldParameter,
					parameter));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getMatch() {
		return match;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMatch(Object newMatch) {
		Object oldMatch = match;
		match = newMatch;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateModelPackage.RULE_STATE__MATCH, oldMatch,
					match));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getCoMatch() {
		return coMatch;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCoMatch(Object newCoMatch) {
		Object oldCoMatch = coMatch;
		coMatch = newCoMatch;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateModelPackage.RULE_STATE__CO_MATCH, oldCoMatch,
					coMatch));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StructuralDelta getStructuralDelta() {
		return structuralDelta;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStructuralDelta(StructuralDelta newStructuralDelta, NotificationChain msgs) {
		StructuralDelta oldStructuralDelta = structuralDelta;
		structuralDelta = newStructuralDelta;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					StateModelPackage.RULE_STATE__STRUCTURAL_DELTA, oldStructuralDelta, newStructuralDelta);
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
	public void setStructuralDelta(StructuralDelta newStructuralDelta) {
		if (newStructuralDelta != structuralDelta) {
			NotificationChain msgs = null;
			if (structuralDelta != null)
				msgs = ((InternalEObject) structuralDelta).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - StateModelPackage.RULE_STATE__STRUCTURAL_DELTA, null, msgs);
			if (newStructuralDelta != null)
				msgs = ((InternalEObject) newStructuralDelta).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - StateModelPackage.RULE_STATE__STRUCTURAL_DELTA, null, msgs);
			msgs = basicSetStructuralDelta(newStructuralDelta, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateModelPackage.RULE_STATE__STRUCTURAL_DELTA,
					newStructuralDelta, newStructuralDelta));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<AttributeDelta> getAttributeDeltas() {
		if (attributeDeltas == null) {
			attributeDeltas = new EObjectContainmentEList<AttributeDelta>(AttributeDelta.class, this,
					StateModelPackage.RULE_STATE__ATTRIBUTE_DELTAS);
		}
		return attributeDeltas;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case StateModelPackage.RULE_STATE__STRUCTURAL_DELTA:
			return basicSetStructuralDelta(null, msgs);
		case StateModelPackage.RULE_STATE__ATTRIBUTE_DELTAS:
			return ((InternalEList<?>) getAttributeDeltas()).basicRemove(otherEnd, msgs);
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
		case StateModelPackage.RULE_STATE__PARENT:
			if (resolve)
				return getParent();
			return basicGetParent();
		case StateModelPackage.RULE_STATE__RULE:
			if (resolve)
				return getRule();
			return basicGetRule();
		case StateModelPackage.RULE_STATE__PARAMETER:
			return getParameter();
		case StateModelPackage.RULE_STATE__MATCH:
			return getMatch();
		case StateModelPackage.RULE_STATE__CO_MATCH:
			return getCoMatch();
		case StateModelPackage.RULE_STATE__STRUCTURAL_DELTA:
			return getStructuralDelta();
		case StateModelPackage.RULE_STATE__ATTRIBUTE_DELTAS:
			return getAttributeDeltas();
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
		case StateModelPackage.RULE_STATE__PARENT:
			setParent((State) newValue);
			return;
		case StateModelPackage.RULE_STATE__RULE:
			setRule((IBeXRule) newValue);
			return;
		case StateModelPackage.RULE_STATE__PARAMETER:
			setParameter(newValue);
			return;
		case StateModelPackage.RULE_STATE__MATCH:
			setMatch(newValue);
			return;
		case StateModelPackage.RULE_STATE__CO_MATCH:
			setCoMatch(newValue);
			return;
		case StateModelPackage.RULE_STATE__STRUCTURAL_DELTA:
			setStructuralDelta((StructuralDelta) newValue);
			return;
		case StateModelPackage.RULE_STATE__ATTRIBUTE_DELTAS:
			getAttributeDeltas().clear();
			getAttributeDeltas().addAll((Collection<? extends AttributeDelta>) newValue);
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
		case StateModelPackage.RULE_STATE__PARENT:
			setParent((State) null);
			return;
		case StateModelPackage.RULE_STATE__RULE:
			setRule((IBeXRule) null);
			return;
		case StateModelPackage.RULE_STATE__PARAMETER:
			setParameter(PARAMETER_EDEFAULT);
			return;
		case StateModelPackage.RULE_STATE__MATCH:
			setMatch(MATCH_EDEFAULT);
			return;
		case StateModelPackage.RULE_STATE__CO_MATCH:
			setCoMatch(CO_MATCH_EDEFAULT);
			return;
		case StateModelPackage.RULE_STATE__STRUCTURAL_DELTA:
			setStructuralDelta((StructuralDelta) null);
			return;
		case StateModelPackage.RULE_STATE__ATTRIBUTE_DELTAS:
			getAttributeDeltas().clear();
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
		case StateModelPackage.RULE_STATE__PARENT:
			return parent != null;
		case StateModelPackage.RULE_STATE__RULE:
			return rule != null;
		case StateModelPackage.RULE_STATE__PARAMETER:
			return PARAMETER_EDEFAULT == null ? parameter != null : !PARAMETER_EDEFAULT.equals(parameter);
		case StateModelPackage.RULE_STATE__MATCH:
			return MATCH_EDEFAULT == null ? match != null : !MATCH_EDEFAULT.equals(match);
		case StateModelPackage.RULE_STATE__CO_MATCH:
			return CO_MATCH_EDEFAULT == null ? coMatch != null : !CO_MATCH_EDEFAULT.equals(coMatch);
		case StateModelPackage.RULE_STATE__STRUCTURAL_DELTA:
			return structuralDelta != null;
		case StateModelPackage.RULE_STATE__ATTRIBUTE_DELTAS:
			return attributeDeltas != null && !attributeDeltas.isEmpty();
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
		result.append(" (parameter: ");
		result.append(parameter);
		result.append(", match: ");
		result.append(match);
		result.append(", coMatch: ");
		result.append(coMatch);
		result.append(')');
		return result.toString();
	}

	
} //RuleStateImpl
