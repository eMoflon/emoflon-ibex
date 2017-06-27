/**
 */
package ClassInheritanceHierarchy.impl;

import ClassInheritanceHierarchy.Attribute;
import ClassInheritanceHierarchy.ClassInheritanceHierarchyPackage;
import ClassInheritanceHierarchy.Clazz;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Attribute</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link ClassInheritanceHierarchy.impl.AttributeImpl#getClazz <em>Clazz</em>}</li>
 *   <li>{@link ClassInheritanceHierarchy.impl.AttributeImpl#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AttributeImpl extends NamedElementImpl implements Attribute {
	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected String type = TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AttributeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ClassInheritanceHierarchyPackage.Literals.ATTRIBUTE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Clazz getClazz() {
		if (eContainerFeatureID() != ClassInheritanceHierarchyPackage.ATTRIBUTE__CLAZZ)
			return null;
		return (Clazz) eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetClazz(Clazz newClazz, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject) newClazz, ClassInheritanceHierarchyPackage.ATTRIBUTE__CLAZZ, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setClazz(Clazz newClazz) {
		if (newClazz != eInternalContainer()
				|| (eContainerFeatureID() != ClassInheritanceHierarchyPackage.ATTRIBUTE__CLAZZ && newClazz != null)) {
			if (EcoreUtil.isAncestor(this, newClazz))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newClazz != null)
				msgs = ((InternalEObject) newClazz).eInverseAdd(this,
						ClassInheritanceHierarchyPackage.CLAZZ__ATTRIBUTES, Clazz.class, msgs);
			msgs = basicSetClazz(newClazz, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ClassInheritanceHierarchyPackage.ATTRIBUTE__CLAZZ,
					newClazz, newClazz));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(String newType) {
		String oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ClassInheritanceHierarchyPackage.ATTRIBUTE__TYPE,
					oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ClassInheritanceHierarchyPackage.ATTRIBUTE__CLAZZ:
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			return basicSetClazz((Clazz) otherEnd, msgs);
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
		case ClassInheritanceHierarchyPackage.ATTRIBUTE__CLAZZ:
			return basicSetClazz(null, msgs);
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
		case ClassInheritanceHierarchyPackage.ATTRIBUTE__CLAZZ:
			return eInternalContainer().eInverseRemove(this, ClassInheritanceHierarchyPackage.CLAZZ__ATTRIBUTES,
					Clazz.class, msgs);
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
		case ClassInheritanceHierarchyPackage.ATTRIBUTE__CLAZZ:
			return getClazz();
		case ClassInheritanceHierarchyPackage.ATTRIBUTE__TYPE:
			return getType();
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
		case ClassInheritanceHierarchyPackage.ATTRIBUTE__CLAZZ:
			setClazz((Clazz) newValue);
			return;
		case ClassInheritanceHierarchyPackage.ATTRIBUTE__TYPE:
			setType((String) newValue);
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
		case ClassInheritanceHierarchyPackage.ATTRIBUTE__CLAZZ:
			setClazz((Clazz) null);
			return;
		case ClassInheritanceHierarchyPackage.ATTRIBUTE__TYPE:
			setType(TYPE_EDEFAULT);
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
		case ClassInheritanceHierarchyPackage.ATTRIBUTE__CLAZZ:
			return getClazz() != null;
		case ClassInheritanceHierarchyPackage.ATTRIBUTE__TYPE:
			return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
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
		result.append(" (type: ");
		result.append(type);
		result.append(')');
		return result.toString();
	}
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} //AttributeImpl
