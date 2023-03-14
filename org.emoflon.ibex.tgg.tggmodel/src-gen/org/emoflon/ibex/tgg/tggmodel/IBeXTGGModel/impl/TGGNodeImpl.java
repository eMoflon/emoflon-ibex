/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeAssignment;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXNodeImpl;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGCorrespondence;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleElement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TGG Node</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGNodeImpl#getDomainType <em>Domain Type</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGNodeImpl#getBindingType <em>Binding Type</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGNodeImpl#getIncomingCorrespondence <em>Incoming Correspondence</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGNodeImpl#getOutgoingCorrespondence <em>Outgoing Correspondence</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGNodeImpl#getAttributeAssignments <em>Attribute Assignments</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TGGNodeImpl extends IBeXNodeImpl implements TGGNode {
	/**
	 * The default value of the '{@link #getDomainType() <em>Domain Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDomainType()
	 * @generated
	 * @ordered
	 */
	protected static final DomainType DOMAIN_TYPE_EDEFAULT = DomainType.SOURCE;

	/**
	 * The cached value of the '{@link #getDomainType() <em>Domain Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDomainType()
	 * @generated
	 * @ordered
	 */
	protected DomainType domainType = DOMAIN_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getBindingType() <em>Binding Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBindingType()
	 * @generated
	 * @ordered
	 */
	protected static final BindingType BINDING_TYPE_EDEFAULT = BindingType.CONTEXT;

	/**
	 * The cached value of the '{@link #getBindingType() <em>Binding Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBindingType()
	 * @generated
	 * @ordered
	 */
	protected BindingType bindingType = BINDING_TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getIncomingCorrespondence() <em>Incoming Correspondence</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIncomingCorrespondence()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGCorrespondence> incomingCorrespondence;

	/**
	 * The cached value of the '{@link #getOutgoingCorrespondence() <em>Outgoing Correspondence</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutgoingCorrespondence()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGCorrespondence> outgoingCorrespondence;

	/**
	 * The cached value of the '{@link #getAttributeAssignments() <em>Attribute Assignments</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeAssignments()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXAttributeAssignment> attributeAssignments;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TGGNodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXTGGModelPackage.Literals.TGG_NODE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomainType getDomainType() {
		return domainType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDomainType(DomainType newDomainType) {
		DomainType oldDomainType = domainType;
		domainType = newDomainType == null ? DOMAIN_TYPE_EDEFAULT : newDomainType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXTGGModelPackage.TGG_NODE__DOMAIN_TYPE, oldDomainType, domainType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BindingType getBindingType() {
		return bindingType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBindingType(BindingType newBindingType) {
		BindingType oldBindingType = bindingType;
		bindingType = newBindingType == null ? BINDING_TYPE_EDEFAULT : newBindingType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXTGGModelPackage.TGG_NODE__BINDING_TYPE, oldBindingType, bindingType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TGGCorrespondence> getIncomingCorrespondence() {
		if (incomingCorrespondence == null) {
			incomingCorrespondence = new EObjectWithInverseResolvingEList<TGGCorrespondence>(TGGCorrespondence.class, this,
					IBeXTGGModelPackage.TGG_NODE__INCOMING_CORRESPONDENCE, IBeXTGGModelPackage.TGG_CORRESPONDENCE__TARGET);
		}
		return incomingCorrespondence;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TGGCorrespondence> getOutgoingCorrespondence() {
		if (outgoingCorrespondence == null) {
			outgoingCorrespondence = new EObjectWithInverseResolvingEList<TGGCorrespondence>(TGGCorrespondence.class, this,
					IBeXTGGModelPackage.TGG_NODE__OUTGOING_CORRESPONDENCE, IBeXTGGModelPackage.TGG_CORRESPONDENCE__SOURCE);
		}
		return outgoingCorrespondence;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<IBeXAttributeAssignment> getAttributeAssignments() {
		if (attributeAssignments == null) {
			attributeAssignments = new EObjectResolvingEList<IBeXAttributeAssignment>(IBeXAttributeAssignment.class, this,
					IBeXTGGModelPackage.TGG_NODE__ATTRIBUTE_ASSIGNMENTS);
		}
		return attributeAssignments;
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
			case IBeXTGGModelPackage.TGG_NODE__INCOMING_CORRESPONDENCE:
				return ((InternalEList<InternalEObject>) (InternalEList<?>) getIncomingCorrespondence()).basicAdd(otherEnd, msgs);
			case IBeXTGGModelPackage.TGG_NODE__OUTGOING_CORRESPONDENCE:
				return ((InternalEList<InternalEObject>) (InternalEList<?>) getOutgoingCorrespondence()).basicAdd(otherEnd, msgs);
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
			case IBeXTGGModelPackage.TGG_NODE__INCOMING_CORRESPONDENCE:
				return ((InternalEList<?>) getIncomingCorrespondence()).basicRemove(otherEnd, msgs);
			case IBeXTGGModelPackage.TGG_NODE__OUTGOING_CORRESPONDENCE:
				return ((InternalEList<?>) getOutgoingCorrespondence()).basicRemove(otherEnd, msgs);
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
			case IBeXTGGModelPackage.TGG_NODE__DOMAIN_TYPE:
				return getDomainType();
			case IBeXTGGModelPackage.TGG_NODE__BINDING_TYPE:
				return getBindingType();
			case IBeXTGGModelPackage.TGG_NODE__INCOMING_CORRESPONDENCE:
				return getIncomingCorrespondence();
			case IBeXTGGModelPackage.TGG_NODE__OUTGOING_CORRESPONDENCE:
				return getOutgoingCorrespondence();
			case IBeXTGGModelPackage.TGG_NODE__ATTRIBUTE_ASSIGNMENTS:
				return getAttributeAssignments();
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
			case IBeXTGGModelPackage.TGG_NODE__DOMAIN_TYPE:
				setDomainType((DomainType) newValue);
				return;
			case IBeXTGGModelPackage.TGG_NODE__BINDING_TYPE:
				setBindingType((BindingType) newValue);
				return;
			case IBeXTGGModelPackage.TGG_NODE__INCOMING_CORRESPONDENCE:
				getIncomingCorrespondence().clear();
				getIncomingCorrespondence().addAll((Collection<? extends TGGCorrespondence>) newValue);
				return;
			case IBeXTGGModelPackage.TGG_NODE__OUTGOING_CORRESPONDENCE:
				getOutgoingCorrespondence().clear();
				getOutgoingCorrespondence().addAll((Collection<? extends TGGCorrespondence>) newValue);
				return;
			case IBeXTGGModelPackage.TGG_NODE__ATTRIBUTE_ASSIGNMENTS:
				getAttributeAssignments().clear();
				getAttributeAssignments().addAll((Collection<? extends IBeXAttributeAssignment>) newValue);
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
			case IBeXTGGModelPackage.TGG_NODE__DOMAIN_TYPE:
				setDomainType(DOMAIN_TYPE_EDEFAULT);
				return;
			case IBeXTGGModelPackage.TGG_NODE__BINDING_TYPE:
				setBindingType(BINDING_TYPE_EDEFAULT);
				return;
			case IBeXTGGModelPackage.TGG_NODE__INCOMING_CORRESPONDENCE:
				getIncomingCorrespondence().clear();
				return;
			case IBeXTGGModelPackage.TGG_NODE__OUTGOING_CORRESPONDENCE:
				getOutgoingCorrespondence().clear();
				return;
			case IBeXTGGModelPackage.TGG_NODE__ATTRIBUTE_ASSIGNMENTS:
				getAttributeAssignments().clear();
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
			case IBeXTGGModelPackage.TGG_NODE__DOMAIN_TYPE:
				return domainType != DOMAIN_TYPE_EDEFAULT;
			case IBeXTGGModelPackage.TGG_NODE__BINDING_TYPE:
				return bindingType != BINDING_TYPE_EDEFAULT;
			case IBeXTGGModelPackage.TGG_NODE__INCOMING_CORRESPONDENCE:
				return incomingCorrespondence != null && !incomingCorrespondence.isEmpty();
			case IBeXTGGModelPackage.TGG_NODE__OUTGOING_CORRESPONDENCE:
				return outgoingCorrespondence != null && !outgoingCorrespondence.isEmpty();
			case IBeXTGGModelPackage.TGG_NODE__ATTRIBUTE_ASSIGNMENTS:
				return attributeAssignments != null && !attributeAssignments.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == TGGRuleElement.class) {
			switch (derivedFeatureID) {
				case IBeXTGGModelPackage.TGG_NODE__DOMAIN_TYPE:
					return IBeXTGGModelPackage.TGG_RULE_ELEMENT__DOMAIN_TYPE;
				case IBeXTGGModelPackage.TGG_NODE__BINDING_TYPE:
					return IBeXTGGModelPackage.TGG_RULE_ELEMENT__BINDING_TYPE;
				default:
					return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == TGGRuleElement.class) {
			switch (baseFeatureID) {
				case IBeXTGGModelPackage.TGG_RULE_ELEMENT__DOMAIN_TYPE:
					return IBeXTGGModelPackage.TGG_NODE__DOMAIN_TYPE;
				case IBeXTGGModelPackage.TGG_RULE_ELEMENT__BINDING_TYPE:
					return IBeXTGGModelPackage.TGG_NODE__BINDING_TYPE;
				default:
					return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(" (domainType: ");
		result.append(domainType);
		result.append(", bindingType: ");
		result.append(bindingType);
		result.append(')');
		return result.toString();
	}

} //TGGNodeImpl
