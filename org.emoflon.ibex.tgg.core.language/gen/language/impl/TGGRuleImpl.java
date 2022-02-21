/**
 */
package language.impl;

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

import language.LanguagePackage;
import language.NAC;
import language.TGGAttributeConstraintLibrary;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TGG Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link language.impl.TGGRuleImpl#getRefines <em>Refines</em>}</li>
 *   <li>{@link language.impl.TGGRuleImpl#getNacs <em>Nacs</em>}</li>
 *   <li>{@link language.impl.TGGRuleImpl#getNodes <em>Nodes</em>}</li>
 *   <li>{@link language.impl.TGGRuleImpl#getEdges <em>Edges</em>}</li>
 *   <li>{@link language.impl.TGGRuleImpl#getAttributeConditionLibrary <em>Attribute Condition Library</em>}</li>
 *   <li>{@link language.impl.TGGRuleImpl#isAbstract <em>Abstract</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TGGRuleImpl extends TGGNamedElementImpl implements TGGRule {
	/**
	 * The cached value of the '{@link #getRefines() <em>Refines</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRefines()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGRule> refines;

	/**
	 * The cached value of the '{@link #getNacs() <em>Nacs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNacs()
	 * @generated
	 * @ordered
	 */
	protected EList<NAC> nacs;

	/**
	 * The cached value of the '{@link #getNodes() <em>Nodes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNodes()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGRuleNode> nodes;

	/**
	 * The cached value of the '{@link #getEdges() <em>Edges</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEdges()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGRuleEdge> edges;

	/**
	 * The cached value of the '{@link #getAttributeConditionLibrary() <em>Attribute Condition Library</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeConditionLibrary()
	 * @generated
	 * @ordered
	 */
	protected TGGAttributeConstraintLibrary attributeConditionLibrary;

	/**
	 * The default value of the '{@link #isAbstract() <em>Abstract</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAbstract()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ABSTRACT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAbstract() <em>Abstract</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAbstract()
	 * @generated
	 * @ordered
	 */
	protected boolean abstract_ = ABSTRACT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TGGRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LanguagePackage.Literals.TGG_RULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TGGRule> getRefines() {
		if (refines == null) {
			refines = new EObjectResolvingEList<TGGRule>(TGGRule.class, this, LanguagePackage.TGG_RULE__REFINES);
		}
		return refines;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<NAC> getNacs() {
		if (nacs == null) {
			nacs = new EObjectContainmentEList<NAC>(NAC.class, this, LanguagePackage.TGG_RULE__NACS);
		}
		return nacs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TGGRuleNode> getNodes() {
		if (nodes == null) {
			nodes = new EObjectContainmentEList<TGGRuleNode>(TGGRuleNode.class, this, LanguagePackage.TGG_RULE__NODES);
		}
		return nodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TGGRuleEdge> getEdges() {
		if (edges == null) {
			edges = new EObjectContainmentEList<TGGRuleEdge>(TGGRuleEdge.class, this, LanguagePackage.TGG_RULE__EDGES);
		}
		return edges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TGGAttributeConstraintLibrary getAttributeConditionLibrary() {
		return attributeConditionLibrary;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAttributeConditionLibrary(TGGAttributeConstraintLibrary newAttributeConditionLibrary, NotificationChain msgs) {
		TGGAttributeConstraintLibrary oldAttributeConditionLibrary = attributeConditionLibrary;
		attributeConditionLibrary = newAttributeConditionLibrary;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LanguagePackage.TGG_RULE__ATTRIBUTE_CONDITION_LIBRARY,
					oldAttributeConditionLibrary, newAttributeConditionLibrary);
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
	public void setAttributeConditionLibrary(TGGAttributeConstraintLibrary newAttributeConditionLibrary) {
		if (newAttributeConditionLibrary != attributeConditionLibrary) {
			NotificationChain msgs = null;
			if (attributeConditionLibrary != null)
				msgs = ((InternalEObject) attributeConditionLibrary).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - LanguagePackage.TGG_RULE__ATTRIBUTE_CONDITION_LIBRARY, null, msgs);
			if (newAttributeConditionLibrary != null)
				msgs = ((InternalEObject) newAttributeConditionLibrary).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - LanguagePackage.TGG_RULE__ATTRIBUTE_CONDITION_LIBRARY, null, msgs);
			msgs = basicSetAttributeConditionLibrary(newAttributeConditionLibrary, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LanguagePackage.TGG_RULE__ATTRIBUTE_CONDITION_LIBRARY, newAttributeConditionLibrary,
					newAttributeConditionLibrary));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isAbstract() {
		return abstract_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAbstract(boolean newAbstract) {
		boolean oldAbstract = abstract_;
		abstract_ = newAbstract;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LanguagePackage.TGG_RULE__ABSTRACT, oldAbstract, abstract_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case LanguagePackage.TGG_RULE__NACS:
			return ((InternalEList<?>) getNacs()).basicRemove(otherEnd, msgs);
		case LanguagePackage.TGG_RULE__NODES:
			return ((InternalEList<?>) getNodes()).basicRemove(otherEnd, msgs);
		case LanguagePackage.TGG_RULE__EDGES:
			return ((InternalEList<?>) getEdges()).basicRemove(otherEnd, msgs);
		case LanguagePackage.TGG_RULE__ATTRIBUTE_CONDITION_LIBRARY:
			return basicSetAttributeConditionLibrary(null, msgs);
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
		case LanguagePackage.TGG_RULE__REFINES:
			return getRefines();
		case LanguagePackage.TGG_RULE__NACS:
			return getNacs();
		case LanguagePackage.TGG_RULE__NODES:
			return getNodes();
		case LanguagePackage.TGG_RULE__EDGES:
			return getEdges();
		case LanguagePackage.TGG_RULE__ATTRIBUTE_CONDITION_LIBRARY:
			return getAttributeConditionLibrary();
		case LanguagePackage.TGG_RULE__ABSTRACT:
			return isAbstract();
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
		case LanguagePackage.TGG_RULE__REFINES:
			getRefines().clear();
			getRefines().addAll((Collection<? extends TGGRule>) newValue);
			return;
		case LanguagePackage.TGG_RULE__NACS:
			getNacs().clear();
			getNacs().addAll((Collection<? extends NAC>) newValue);
			return;
		case LanguagePackage.TGG_RULE__NODES:
			getNodes().clear();
			getNodes().addAll((Collection<? extends TGGRuleNode>) newValue);
			return;
		case LanguagePackage.TGG_RULE__EDGES:
			getEdges().clear();
			getEdges().addAll((Collection<? extends TGGRuleEdge>) newValue);
			return;
		case LanguagePackage.TGG_RULE__ATTRIBUTE_CONDITION_LIBRARY:
			setAttributeConditionLibrary((TGGAttributeConstraintLibrary) newValue);
			return;
		case LanguagePackage.TGG_RULE__ABSTRACT:
			setAbstract((Boolean) newValue);
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
		case LanguagePackage.TGG_RULE__REFINES:
			getRefines().clear();
			return;
		case LanguagePackage.TGG_RULE__NACS:
			getNacs().clear();
			return;
		case LanguagePackage.TGG_RULE__NODES:
			getNodes().clear();
			return;
		case LanguagePackage.TGG_RULE__EDGES:
			getEdges().clear();
			return;
		case LanguagePackage.TGG_RULE__ATTRIBUTE_CONDITION_LIBRARY:
			setAttributeConditionLibrary((TGGAttributeConstraintLibrary) null);
			return;
		case LanguagePackage.TGG_RULE__ABSTRACT:
			setAbstract(ABSTRACT_EDEFAULT);
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
		case LanguagePackage.TGG_RULE__REFINES:
			return refines != null && !refines.isEmpty();
		case LanguagePackage.TGG_RULE__NACS:
			return nacs != null && !nacs.isEmpty();
		case LanguagePackage.TGG_RULE__NODES:
			return nodes != null && !nodes.isEmpty();
		case LanguagePackage.TGG_RULE__EDGES:
			return edges != null && !edges.isEmpty();
		case LanguagePackage.TGG_RULE__ATTRIBUTE_CONDITION_LIBRARY:
			return attributeConditionLibrary != null;
		case LanguagePackage.TGG_RULE__ABSTRACT:
			return abstract_ != ABSTRACT_EDEFAULT;
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
		result.append(" (abstract: ");
		result.append(abstract_);
		result.append(')');
		return result.toString();
	}

} //TGGRuleImpl
