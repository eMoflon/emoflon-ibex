/**
 */
package GTLanguage.impl;

import GTLanguage.GTArithmeticConstraint;
import GTLanguage.GTLanguagePackage;
import GTLanguage.GTNode;
import GTLanguage.GTParameter;
import GTLanguage.GTProbability;
import GTLanguage.GTRule;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>GT Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link GTLanguage.impl.GTRuleImpl#getDocumentation <em>Documentation</em>}</li>
 *   <li>{@link GTLanguage.impl.GTRuleImpl#isExecutable <em>Executable</em>}</li>
 *   <li>{@link GTLanguage.impl.GTRuleImpl#getNodes <em>Nodes</em>}</li>
 *   <li>{@link GTLanguage.impl.GTRuleImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link GTLanguage.impl.GTRuleImpl#getRuleNodes <em>Rule Nodes</em>}</li>
 *   <li>{@link GTLanguage.impl.GTRuleImpl#getConstraints <em>Constraints</em>}</li>
 *   <li>{@link GTLanguage.impl.GTRuleImpl#getProbability <em>Probability</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GTRuleImpl extends GTNamedElementImpl implements GTRule {
	/**
	 * The default value of the '{@link #getDocumentation() <em>Documentation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDocumentation()
	 * @generated
	 * @ordered
	 */
	protected static final String DOCUMENTATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDocumentation() <em>Documentation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDocumentation()
	 * @generated
	 * @ordered
	 */
	protected String documentation = DOCUMENTATION_EDEFAULT;

	/**
	 * The default value of the '{@link #isExecutable() <em>Executable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isExecutable()
	 * @generated
	 * @ordered
	 */
	protected static final boolean EXECUTABLE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isExecutable() <em>Executable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isExecutable()
	 * @generated
	 * @ordered
	 */
	protected boolean executable = EXECUTABLE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getNodes() <em>Nodes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNodes()
	 * @generated
	 * @ordered
	 */
	protected EList<GTNode> nodes;

	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<GTParameter> parameters;

	/**
	 * The cached value of the '{@link #getRuleNodes() <em>Rule Nodes</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRuleNodes()
	 * @generated
	 * @ordered
	 */
	protected EList<GTNode> ruleNodes;

	/**
	 * The cached value of the '{@link #getConstraints() <em>Constraints</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstraints()
	 * @generated
	 * @ordered
	 */
	protected EList<GTArithmeticConstraint> constraints;

	/**
	 * The cached value of the '{@link #getProbability() <em>Probability</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProbability()
	 * @generated
	 * @ordered
	 */
	protected GTProbability probability;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GTRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GTLanguagePackage.Literals.GT_RULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getDocumentation() {
		return documentation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDocumentation(String newDocumentation) {
		String oldDocumentation = documentation;
		documentation = newDocumentation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GTLanguagePackage.GT_RULE__DOCUMENTATION,
					oldDocumentation, documentation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isExecutable() {
		return executable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setExecutable(boolean newExecutable) {
		boolean oldExecutable = executable;
		executable = newExecutable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GTLanguagePackage.GT_RULE__EXECUTABLE, oldExecutable,
					executable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<GTNode> getNodes() {
		if (nodes == null) {
			nodes = new EObjectContainmentEList<GTNode>(GTNode.class, this, GTLanguagePackage.GT_RULE__NODES);
		}
		return nodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<GTParameter> getParameters() {
		if (parameters == null) {
			parameters = new EObjectContainmentEList<GTParameter>(GTParameter.class, this,
					GTLanguagePackage.GT_RULE__PARAMETERS);
		}
		return parameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<GTNode> getRuleNodes() {
		if (ruleNodes == null) {
			ruleNodes = new EObjectResolvingEList<GTNode>(GTNode.class, this, GTLanguagePackage.GT_RULE__RULE_NODES);
		}
		return ruleNodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<GTArithmeticConstraint> getConstraints() {
		if (constraints == null) {
			constraints = new EObjectContainmentEList<GTArithmeticConstraint>(GTArithmeticConstraint.class, this,
					GTLanguagePackage.GT_RULE__CONSTRAINTS);
		}
		return constraints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GTProbability getProbability() {
		return probability;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProbability(GTProbability newProbability, NotificationChain msgs) {
		GTProbability oldProbability = probability;
		probability = newProbability;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					GTLanguagePackage.GT_RULE__PROBABILITY, oldProbability, newProbability);
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
	public void setProbability(GTProbability newProbability) {
		if (newProbability != probability) {
			NotificationChain msgs = null;
			if (probability != null)
				msgs = ((InternalEObject) probability).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - GTLanguagePackage.GT_RULE__PROBABILITY, null, msgs);
			if (newProbability != null)
				msgs = ((InternalEObject) newProbability).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - GTLanguagePackage.GT_RULE__PROBABILITY, null, msgs);
			msgs = basicSetProbability(newProbability, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GTLanguagePackage.GT_RULE__PROBABILITY,
					newProbability, newProbability));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case GTLanguagePackage.GT_RULE__NODES:
			return ((InternalEList<?>) getNodes()).basicRemove(otherEnd, msgs);
		case GTLanguagePackage.GT_RULE__PARAMETERS:
			return ((InternalEList<?>) getParameters()).basicRemove(otherEnd, msgs);
		case GTLanguagePackage.GT_RULE__CONSTRAINTS:
			return ((InternalEList<?>) getConstraints()).basicRemove(otherEnd, msgs);
		case GTLanguagePackage.GT_RULE__PROBABILITY:
			return basicSetProbability(null, msgs);
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
		case GTLanguagePackage.GT_RULE__DOCUMENTATION:
			return getDocumentation();
		case GTLanguagePackage.GT_RULE__EXECUTABLE:
			return isExecutable();
		case GTLanguagePackage.GT_RULE__NODES:
			return getNodes();
		case GTLanguagePackage.GT_RULE__PARAMETERS:
			return getParameters();
		case GTLanguagePackage.GT_RULE__RULE_NODES:
			return getRuleNodes();
		case GTLanguagePackage.GT_RULE__CONSTRAINTS:
			return getConstraints();
		case GTLanguagePackage.GT_RULE__PROBABILITY:
			return getProbability();
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
		case GTLanguagePackage.GT_RULE__DOCUMENTATION:
			setDocumentation((String) newValue);
			return;
		case GTLanguagePackage.GT_RULE__EXECUTABLE:
			setExecutable((Boolean) newValue);
			return;
		case GTLanguagePackage.GT_RULE__NODES:
			getNodes().clear();
			getNodes().addAll((Collection<? extends GTNode>) newValue);
			return;
		case GTLanguagePackage.GT_RULE__PARAMETERS:
			getParameters().clear();
			getParameters().addAll((Collection<? extends GTParameter>) newValue);
			return;
		case GTLanguagePackage.GT_RULE__RULE_NODES:
			getRuleNodes().clear();
			getRuleNodes().addAll((Collection<? extends GTNode>) newValue);
			return;
		case GTLanguagePackage.GT_RULE__CONSTRAINTS:
			getConstraints().clear();
			getConstraints().addAll((Collection<? extends GTArithmeticConstraint>) newValue);
			return;
		case GTLanguagePackage.GT_RULE__PROBABILITY:
			setProbability((GTProbability) newValue);
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
		case GTLanguagePackage.GT_RULE__DOCUMENTATION:
			setDocumentation(DOCUMENTATION_EDEFAULT);
			return;
		case GTLanguagePackage.GT_RULE__EXECUTABLE:
			setExecutable(EXECUTABLE_EDEFAULT);
			return;
		case GTLanguagePackage.GT_RULE__NODES:
			getNodes().clear();
			return;
		case GTLanguagePackage.GT_RULE__PARAMETERS:
			getParameters().clear();
			return;
		case GTLanguagePackage.GT_RULE__RULE_NODES:
			getRuleNodes().clear();
			return;
		case GTLanguagePackage.GT_RULE__CONSTRAINTS:
			getConstraints().clear();
			return;
		case GTLanguagePackage.GT_RULE__PROBABILITY:
			setProbability((GTProbability) null);
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
		case GTLanguagePackage.GT_RULE__DOCUMENTATION:
			return DOCUMENTATION_EDEFAULT == null ? documentation != null
					: !DOCUMENTATION_EDEFAULT.equals(documentation);
		case GTLanguagePackage.GT_RULE__EXECUTABLE:
			return executable != EXECUTABLE_EDEFAULT;
		case GTLanguagePackage.GT_RULE__NODES:
			return nodes != null && !nodes.isEmpty();
		case GTLanguagePackage.GT_RULE__PARAMETERS:
			return parameters != null && !parameters.isEmpty();
		case GTLanguagePackage.GT_RULE__RULE_NODES:
			return ruleNodes != null && !ruleNodes.isEmpty();
		case GTLanguagePackage.GT_RULE__CONSTRAINTS:
			return constraints != null && !constraints.isEmpty();
		case GTLanguagePackage.GT_RULE__PROBABILITY:
			return probability != null;
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
		result.append(" (documentation: ");
		result.append(documentation);
		result.append(", executable: ");
		result.append(executable);
		result.append(')');
		return result.toString();
	}

} //GTRuleImpl
