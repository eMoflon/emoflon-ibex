/**
 */
package org.emoflon.ibex.gt.StateModel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.emoflon.ibex.gt.StateModel.AttributeDelta;
import org.emoflon.ibex.gt.StateModel.IBeXMatch;
import org.emoflon.ibex.gt.StateModel.MatchDelta;
import org.emoflon.ibex.gt.StateModel.Parameter;
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
 *   <li>{@link org.emoflon.ibex.gt.StateModel.impl.RuleStateImpl#getStructuralDelta <em>Structural Delta</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.impl.RuleStateImpl#getAttributeDeltas <em>Attribute Deltas</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.impl.RuleStateImpl#getMatch <em>Match</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.impl.RuleStateImpl#getCoMatch <em>Co Match</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.impl.RuleStateImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.impl.RuleStateImpl#getDeletedMatches <em>Deleted Matches</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.impl.RuleStateImpl#getCreatedMatches <em>Created Matches</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.impl.RuleStateImpl#getMatchCount <em>Match Count</em>}</li>
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
	 * The cached value of the '{@link #getMatch() <em>Match</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMatch()
	 * @generated
	 * @ordered
	 */
	protected IBeXMatch match;

	/**
	 * The cached value of the '{@link #getCoMatch() <em>Co Match</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCoMatch()
	 * @generated
	 * @ordered
	 */
	protected IBeXMatch coMatch;

	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<Parameter> parameters;

	/**
	 * The cached value of the '{@link #getDeletedMatches() <em>Deleted Matches</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeletedMatches()
	 * @generated
	 * @ordered
	 */
	protected EList<MatchDelta> deletedMatches;

	/**
	 * The cached value of the '{@link #getCreatedMatches() <em>Created Matches</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreatedMatches()
	 * @generated
	 * @ordered
	 */
	protected EList<MatchDelta> createdMatches;

	/**
	 * The default value of the '{@link #getMatchCount() <em>Match Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMatchCount()
	 * @generated
	 * @ordered
	 */
	protected static final long MATCH_COUNT_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getMatchCount() <em>Match Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMatchCount()
	 * @generated
	 * @ordered
	 */
	protected long matchCount = MATCH_COUNT_EDEFAULT;

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
	public IBeXMatch getMatch() {
		return match;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMatch(IBeXMatch newMatch, NotificationChain msgs) {
		IBeXMatch oldMatch = match;
		match = newMatch;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					StateModelPackage.RULE_STATE__MATCH, oldMatch, newMatch);
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
	public void setMatch(IBeXMatch newMatch) {
		if (newMatch != match) {
			NotificationChain msgs = null;
			if (match != null)
				msgs = ((InternalEObject) match).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - StateModelPackage.RULE_STATE__MATCH, null, msgs);
			if (newMatch != null)
				msgs = ((InternalEObject) newMatch).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - StateModelPackage.RULE_STATE__MATCH, null, msgs);
			msgs = basicSetMatch(newMatch, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateModelPackage.RULE_STATE__MATCH, newMatch,
					newMatch));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXMatch getCoMatch() {
		return coMatch;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCoMatch(IBeXMatch newCoMatch, NotificationChain msgs) {
		IBeXMatch oldCoMatch = coMatch;
		coMatch = newCoMatch;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					StateModelPackage.RULE_STATE__CO_MATCH, oldCoMatch, newCoMatch);
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
	public void setCoMatch(IBeXMatch newCoMatch) {
		if (newCoMatch != coMatch) {
			NotificationChain msgs = null;
			if (coMatch != null)
				msgs = ((InternalEObject) coMatch).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - StateModelPackage.RULE_STATE__CO_MATCH, null, msgs);
			if (newCoMatch != null)
				msgs = ((InternalEObject) newCoMatch).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - StateModelPackage.RULE_STATE__CO_MATCH, null, msgs);
			msgs = basicSetCoMatch(newCoMatch, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateModelPackage.RULE_STATE__CO_MATCH, newCoMatch,
					newCoMatch));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Parameter> getParameters() {
		if (parameters == null) {
			parameters = new EObjectContainmentEList<Parameter>(Parameter.class, this,
					StateModelPackage.RULE_STATE__PARAMETERS);
		}
		return parameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<MatchDelta> getDeletedMatches() {
		if (deletedMatches == null) {
			deletedMatches = new EObjectContainmentEList<MatchDelta>(MatchDelta.class, this,
					StateModelPackage.RULE_STATE__DELETED_MATCHES);
		}
		return deletedMatches;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<MatchDelta> getCreatedMatches() {
		if (createdMatches == null) {
			createdMatches = new EObjectContainmentEList<MatchDelta>(MatchDelta.class, this,
					StateModelPackage.RULE_STATE__CREATED_MATCHES);
		}
		return createdMatches;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public long getMatchCount() {
		return matchCount;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMatchCount(long newMatchCount) {
		long oldMatchCount = matchCount;
		matchCount = newMatchCount;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, StateModelPackage.RULE_STATE__MATCH_COUNT,
					oldMatchCount, matchCount));
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
		case StateModelPackage.RULE_STATE__MATCH:
			return basicSetMatch(null, msgs);
		case StateModelPackage.RULE_STATE__CO_MATCH:
			return basicSetCoMatch(null, msgs);
		case StateModelPackage.RULE_STATE__PARAMETERS:
			return ((InternalEList<?>) getParameters()).basicRemove(otherEnd, msgs);
		case StateModelPackage.RULE_STATE__DELETED_MATCHES:
			return ((InternalEList<?>) getDeletedMatches()).basicRemove(otherEnd, msgs);
		case StateModelPackage.RULE_STATE__CREATED_MATCHES:
			return ((InternalEList<?>) getCreatedMatches()).basicRemove(otherEnd, msgs);
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
		case StateModelPackage.RULE_STATE__STRUCTURAL_DELTA:
			return getStructuralDelta();
		case StateModelPackage.RULE_STATE__ATTRIBUTE_DELTAS:
			return getAttributeDeltas();
		case StateModelPackage.RULE_STATE__MATCH:
			return getMatch();
		case StateModelPackage.RULE_STATE__CO_MATCH:
			return getCoMatch();
		case StateModelPackage.RULE_STATE__PARAMETERS:
			return getParameters();
		case StateModelPackage.RULE_STATE__DELETED_MATCHES:
			return getDeletedMatches();
		case StateModelPackage.RULE_STATE__CREATED_MATCHES:
			return getCreatedMatches();
		case StateModelPackage.RULE_STATE__MATCH_COUNT:
			return getMatchCount();
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
		case StateModelPackage.RULE_STATE__STRUCTURAL_DELTA:
			setStructuralDelta((StructuralDelta) newValue);
			return;
		case StateModelPackage.RULE_STATE__ATTRIBUTE_DELTAS:
			getAttributeDeltas().clear();
			getAttributeDeltas().addAll((Collection<? extends AttributeDelta>) newValue);
			return;
		case StateModelPackage.RULE_STATE__MATCH:
			setMatch((IBeXMatch) newValue);
			return;
		case StateModelPackage.RULE_STATE__CO_MATCH:
			setCoMatch((IBeXMatch) newValue);
			return;
		case StateModelPackage.RULE_STATE__PARAMETERS:
			getParameters().clear();
			getParameters().addAll((Collection<? extends Parameter>) newValue);
			return;
		case StateModelPackage.RULE_STATE__DELETED_MATCHES:
			getDeletedMatches().clear();
			getDeletedMatches().addAll((Collection<? extends MatchDelta>) newValue);
			return;
		case StateModelPackage.RULE_STATE__CREATED_MATCHES:
			getCreatedMatches().clear();
			getCreatedMatches().addAll((Collection<? extends MatchDelta>) newValue);
			return;
		case StateModelPackage.RULE_STATE__MATCH_COUNT:
			setMatchCount((Long) newValue);
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
		case StateModelPackage.RULE_STATE__STRUCTURAL_DELTA:
			setStructuralDelta((StructuralDelta) null);
			return;
		case StateModelPackage.RULE_STATE__ATTRIBUTE_DELTAS:
			getAttributeDeltas().clear();
			return;
		case StateModelPackage.RULE_STATE__MATCH:
			setMatch((IBeXMatch) null);
			return;
		case StateModelPackage.RULE_STATE__CO_MATCH:
			setCoMatch((IBeXMatch) null);
			return;
		case StateModelPackage.RULE_STATE__PARAMETERS:
			getParameters().clear();
			return;
		case StateModelPackage.RULE_STATE__DELETED_MATCHES:
			getDeletedMatches().clear();
			return;
		case StateModelPackage.RULE_STATE__CREATED_MATCHES:
			getCreatedMatches().clear();
			return;
		case StateModelPackage.RULE_STATE__MATCH_COUNT:
			setMatchCount(MATCH_COUNT_EDEFAULT);
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
		case StateModelPackage.RULE_STATE__STRUCTURAL_DELTA:
			return structuralDelta != null;
		case StateModelPackage.RULE_STATE__ATTRIBUTE_DELTAS:
			return attributeDeltas != null && !attributeDeltas.isEmpty();
		case StateModelPackage.RULE_STATE__MATCH:
			return match != null;
		case StateModelPackage.RULE_STATE__CO_MATCH:
			return coMatch != null;
		case StateModelPackage.RULE_STATE__PARAMETERS:
			return parameters != null && !parameters.isEmpty();
		case StateModelPackage.RULE_STATE__DELETED_MATCHES:
			return deletedMatches != null && !deletedMatches.isEmpty();
		case StateModelPackage.RULE_STATE__CREATED_MATCHES:
			return createdMatches != null && !createdMatches.isEmpty();
		case StateModelPackage.RULE_STATE__MATCH_COUNT:
			return matchCount != MATCH_COUNT_EDEFAULT;
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
		result.append(" (matchCount: ");
		result.append(matchCount);
		result.append(')');
		return result.toString();
	}

} //RuleStateImpl
