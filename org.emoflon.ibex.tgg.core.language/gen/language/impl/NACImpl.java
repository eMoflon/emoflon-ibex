/**
 */
package language.impl;

import java.util.Collection;

import language.LanguagePackage;
import language.NAC;
import language.TGGRuleEdge;
import language.TGGRuleNode;

import language.basic.impl.TGGNamedElementImpl;

import language.csp.TGGAttributeConstraintLibrary;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>NAC</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link language.impl.NACImpl#getNodes <em>Nodes</em>}</li>
 *   <li>{@link language.impl.NACImpl#getEdges <em>Edges</em>}</li>
 *   <li>{@link language.impl.NACImpl#getAttributeConditionLibrary <em>Attribute Condition Library</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NACImpl extends TGGNamedElementImpl implements NAC {
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NACImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LanguagePackage.Literals.NAC;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TGGRuleNode> getNodes() {
		if (nodes == null) {
			nodes = new EObjectContainmentEList<TGGRuleNode>(TGGRuleNode.class, this, LanguagePackage.NAC__NODES);
		}
		return nodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TGGRuleEdge> getEdges() {
		if (edges == null) {
			edges = new EObjectContainmentEList<TGGRuleEdge>(TGGRuleEdge.class, this, LanguagePackage.NAC__EDGES);
		}
		return edges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGAttributeConstraintLibrary getAttributeConditionLibrary() {
		return attributeConditionLibrary;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAttributeConditionLibrary(
			TGGAttributeConstraintLibrary newAttributeConditionLibrary, NotificationChain msgs) {
		TGGAttributeConstraintLibrary oldAttributeConditionLibrary = attributeConditionLibrary;
		attributeConditionLibrary = newAttributeConditionLibrary;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					LanguagePackage.NAC__ATTRIBUTE_CONDITION_LIBRARY, oldAttributeConditionLibrary,
					newAttributeConditionLibrary);
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
	public void setAttributeConditionLibrary(TGGAttributeConstraintLibrary newAttributeConditionLibrary) {
		if (newAttributeConditionLibrary != attributeConditionLibrary) {
			NotificationChain msgs = null;
			if (attributeConditionLibrary != null)
				msgs = ((InternalEObject) attributeConditionLibrary).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - LanguagePackage.NAC__ATTRIBUTE_CONDITION_LIBRARY, null, msgs);
			if (newAttributeConditionLibrary != null)
				msgs = ((InternalEObject) newAttributeConditionLibrary).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - LanguagePackage.NAC__ATTRIBUTE_CONDITION_LIBRARY, null, msgs);
			msgs = basicSetAttributeConditionLibrary(newAttributeConditionLibrary, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LanguagePackage.NAC__ATTRIBUTE_CONDITION_LIBRARY,
					newAttributeConditionLibrary, newAttributeConditionLibrary));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case LanguagePackage.NAC__NODES:
			return ((InternalEList<?>) getNodes()).basicRemove(otherEnd, msgs);
		case LanguagePackage.NAC__EDGES:
			return ((InternalEList<?>) getEdges()).basicRemove(otherEnd, msgs);
		case LanguagePackage.NAC__ATTRIBUTE_CONDITION_LIBRARY:
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
		case LanguagePackage.NAC__NODES:
			return getNodes();
		case LanguagePackage.NAC__EDGES:
			return getEdges();
		case LanguagePackage.NAC__ATTRIBUTE_CONDITION_LIBRARY:
			return getAttributeConditionLibrary();
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
		case LanguagePackage.NAC__NODES:
			getNodes().clear();
			getNodes().addAll((Collection<? extends TGGRuleNode>) newValue);
			return;
		case LanguagePackage.NAC__EDGES:
			getEdges().clear();
			getEdges().addAll((Collection<? extends TGGRuleEdge>) newValue);
			return;
		case LanguagePackage.NAC__ATTRIBUTE_CONDITION_LIBRARY:
			setAttributeConditionLibrary((TGGAttributeConstraintLibrary) newValue);
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
		case LanguagePackage.NAC__NODES:
			getNodes().clear();
			return;
		case LanguagePackage.NAC__EDGES:
			getEdges().clear();
			return;
		case LanguagePackage.NAC__ATTRIBUTE_CONDITION_LIBRARY:
			setAttributeConditionLibrary((TGGAttributeConstraintLibrary) null);
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
		case LanguagePackage.NAC__NODES:
			return nodes != null && !nodes.isEmpty();
		case LanguagePackage.NAC__EDGES:
			return edges != null && !edges.isEmpty();
		case LanguagePackage.NAC__ATTRIBUTE_CONDITION_LIBRARY:
			return attributeConditionLibrary != null;
		}
		return super.eIsSet(featureID);
	}
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} //NACImpl
