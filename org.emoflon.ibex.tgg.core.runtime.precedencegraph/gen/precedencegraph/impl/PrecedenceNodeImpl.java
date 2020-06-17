/**
 */
package precedencegraph.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import precedencegraph.PrecedenceNode;
import precedencegraph.PrecedencegraphPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Precedence Node</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link precedencegraph.impl.PrecedenceNodeImpl#isBroken <em>Broken</em>}</li>
 *   <li>{@link precedencegraph.impl.PrecedenceNodeImpl#getRequires <em>Requires</em>}</li>
 *   <li>{@link precedencegraph.impl.PrecedenceNodeImpl#getRequiredBy <em>Required By</em>}</li>
 *   <li>{@link precedencegraph.impl.PrecedenceNodeImpl#getMatchAsString <em>Match As String</em>}</li>
 *   <li>{@link precedencegraph.impl.PrecedenceNodeImpl#getRollbackCauses <em>Rollback Causes</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PrecedenceNodeImpl extends EObjectImpl implements PrecedenceNode {
	/**
	 * The default value of the '{@link #isBroken() <em>Broken</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isBroken()
	 * @generated
	 * @ordered
	 */
	protected static final boolean BROKEN_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isBroken() <em>Broken</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isBroken()
	 * @generated
	 * @ordered
	 */
	protected boolean broken = BROKEN_EDEFAULT;

	/**
	 * The cached value of the '{@link #getRequires() <em>Requires</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRequires()
	 * @generated
	 * @ordered
	 */
	protected EList<PrecedenceNode> requires;

	/**
	 * The cached value of the '{@link #getRequiredBy() <em>Required By</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRequiredBy()
	 * @generated
	 * @ordered
	 */
	protected EList<PrecedenceNode> requiredBy;

	/**
	 * The default value of the '{@link #getMatchAsString() <em>Match As String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMatchAsString()
	 * @generated
	 * @ordered
	 */
	protected static final String MATCH_AS_STRING_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMatchAsString() <em>Match As String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMatchAsString()
	 * @generated
	 * @ordered
	 */
	protected String matchAsString = MATCH_AS_STRING_EDEFAULT;

	/**
	 * The cached value of the '{@link #getRollbackCauses() <em>Rollback Causes</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRollbackCauses()
	 * @generated
	 * @ordered
	 */
	protected EList<PrecedenceNode> rollbackCauses;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PrecedenceNodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PrecedencegraphPackage.Literals.PRECEDENCE_NODE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isBroken() {
		return broken;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setBroken(boolean newBroken) {
		boolean oldBroken = broken;
		broken = newBroken;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PrecedencegraphPackage.PRECEDENCE_NODE__BROKEN, oldBroken,
					broken));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<PrecedenceNode> getRequires() {
		if (requires == null) {
			requires = new EObjectWithInverseResolvingEList.ManyInverse<PrecedenceNode>(PrecedenceNode.class, this,
					PrecedencegraphPackage.PRECEDENCE_NODE__REQUIRES, PrecedencegraphPackage.PRECEDENCE_NODE__REQUIRED_BY);
		}
		return requires;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<PrecedenceNode> getRequiredBy() {
		if (requiredBy == null) {
			requiredBy = new EObjectWithInverseResolvingEList.ManyInverse<PrecedenceNode>(PrecedenceNode.class, this,
					PrecedencegraphPackage.PRECEDENCE_NODE__REQUIRED_BY, PrecedencegraphPackage.PRECEDENCE_NODE__REQUIRES);
		}
		return requiredBy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getMatchAsString() {
		return matchAsString;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMatchAsString(String newMatchAsString) {
		String oldMatchAsString = matchAsString;
		matchAsString = newMatchAsString;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PrecedencegraphPackage.PRECEDENCE_NODE__MATCH_AS_STRING,
					oldMatchAsString, matchAsString));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<PrecedenceNode> getRollbackCauses() {
		if (rollbackCauses == null) {
			rollbackCauses = new EObjectResolvingEList<PrecedenceNode>(PrecedenceNode.class, this,
					PrecedencegraphPackage.PRECEDENCE_NODE__ROLLBACK_CAUSES);
		}
		return rollbackCauses;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case PrecedencegraphPackage.PRECEDENCE_NODE__REQUIRES:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getRequires()).basicAdd(otherEnd, msgs);
		case PrecedencegraphPackage.PRECEDENCE_NODE__REQUIRED_BY:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getRequiredBy()).basicAdd(otherEnd, msgs);
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
		case PrecedencegraphPackage.PRECEDENCE_NODE__REQUIRES:
			return ((InternalEList<?>) getRequires()).basicRemove(otherEnd, msgs);
		case PrecedencegraphPackage.PRECEDENCE_NODE__REQUIRED_BY:
			return ((InternalEList<?>) getRequiredBy()).basicRemove(otherEnd, msgs);
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
		case PrecedencegraphPackage.PRECEDENCE_NODE__BROKEN:
			return isBroken();
		case PrecedencegraphPackage.PRECEDENCE_NODE__REQUIRES:
			return getRequires();
		case PrecedencegraphPackage.PRECEDENCE_NODE__REQUIRED_BY:
			return getRequiredBy();
		case PrecedencegraphPackage.PRECEDENCE_NODE__MATCH_AS_STRING:
			return getMatchAsString();
		case PrecedencegraphPackage.PRECEDENCE_NODE__ROLLBACK_CAUSES:
			return getRollbackCauses();
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
		case PrecedencegraphPackage.PRECEDENCE_NODE__BROKEN:
			setBroken((Boolean) newValue);
			return;
		case PrecedencegraphPackage.PRECEDENCE_NODE__REQUIRES:
			getRequires().clear();
			getRequires().addAll((Collection<? extends PrecedenceNode>) newValue);
			return;
		case PrecedencegraphPackage.PRECEDENCE_NODE__REQUIRED_BY:
			getRequiredBy().clear();
			getRequiredBy().addAll((Collection<? extends PrecedenceNode>) newValue);
			return;
		case PrecedencegraphPackage.PRECEDENCE_NODE__MATCH_AS_STRING:
			setMatchAsString((String) newValue);
			return;
		case PrecedencegraphPackage.PRECEDENCE_NODE__ROLLBACK_CAUSES:
			getRollbackCauses().clear();
			getRollbackCauses().addAll((Collection<? extends PrecedenceNode>) newValue);
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
		case PrecedencegraphPackage.PRECEDENCE_NODE__BROKEN:
			setBroken(BROKEN_EDEFAULT);
			return;
		case PrecedencegraphPackage.PRECEDENCE_NODE__REQUIRES:
			getRequires().clear();
			return;
		case PrecedencegraphPackage.PRECEDENCE_NODE__REQUIRED_BY:
			getRequiredBy().clear();
			return;
		case PrecedencegraphPackage.PRECEDENCE_NODE__MATCH_AS_STRING:
			setMatchAsString(MATCH_AS_STRING_EDEFAULT);
			return;
		case PrecedencegraphPackage.PRECEDENCE_NODE__ROLLBACK_CAUSES:
			getRollbackCauses().clear();
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
		case PrecedencegraphPackage.PRECEDENCE_NODE__BROKEN:
			return broken != BROKEN_EDEFAULT;
		case PrecedencegraphPackage.PRECEDENCE_NODE__REQUIRES:
			return requires != null && !requires.isEmpty();
		case PrecedencegraphPackage.PRECEDENCE_NODE__REQUIRED_BY:
			return requiredBy != null && !requiredBy.isEmpty();
		case PrecedencegraphPackage.PRECEDENCE_NODE__MATCH_AS_STRING:
			return MATCH_AS_STRING_EDEFAULT == null ? matchAsString != null
					: !MATCH_AS_STRING_EDEFAULT.equals(matchAsString);
		case PrecedencegraphPackage.PRECEDENCE_NODE__ROLLBACK_CAUSES:
			return rollbackCauses != null && !rollbackCauses.isEmpty();
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
		result.append(" (broken: ");
		result.append(broken);
		result.append(", matchAsString: ");
		result.append(matchAsString);
		result.append(')');
		return result.toString();
	}

} //PrecedenceNodeImpl
