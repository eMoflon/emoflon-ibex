/**
 */
package ClassInheritanceHierarchy.impl;

import ClassInheritanceHierarchy.Attribute;
import ClassInheritanceHierarchy.ClassInheritanceHierarchyPackage;
import ClassInheritanceHierarchy.ClassPackage;
import ClassInheritanceHierarchy.Clazz;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Clazz</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link ClassInheritanceHierarchy.impl.ClazzImpl#getSuperClass <em>Super Class</em>}</li>
 *   <li>{@link ClassInheritanceHierarchy.impl.ClazzImpl#getSubClass <em>Sub Class</em>}</li>
 *   <li>{@link ClassInheritanceHierarchy.impl.ClazzImpl#getPackage <em>Package</em>}</li>
 *   <li>{@link ClassInheritanceHierarchy.impl.ClazzImpl#getAttributes <em>Attributes</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ClazzImpl extends NamedElementImpl implements Clazz {
	/**
	 * The cached value of the '{@link #getSuperClass() <em>Super Class</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSuperClass()
	 * @generated
	 * @ordered
	 */
	protected Clazz superClass;

	/**
	 * The cached value of the '{@link #getSubClass() <em>Sub Class</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubClass()
	 * @generated
	 * @ordered
	 */
	protected EList<Clazz> subClass;

	/**
	 * The cached value of the '{@link #getAttributes() <em>Attributes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributes()
	 * @generated
	 * @ordered
	 */
	protected EList<Attribute> attributes;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ClazzImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ClassInheritanceHierarchyPackage.Literals.CLAZZ;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Clazz getSuperClass() {
		if (superClass != null && superClass.eIsProxy()) {
			InternalEObject oldSuperClass = (InternalEObject) superClass;
			superClass = (Clazz) eResolveProxy(oldSuperClass);
			if (superClass != oldSuperClass) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							ClassInheritanceHierarchyPackage.CLAZZ__SUPER_CLASS, oldSuperClass, superClass));
			}
		}
		return superClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Clazz basicGetSuperClass() {
		return superClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSuperClass(Clazz newSuperClass, NotificationChain msgs) {
		Clazz oldSuperClass = superClass;
		superClass = newSuperClass;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					ClassInheritanceHierarchyPackage.CLAZZ__SUPER_CLASS, oldSuperClass, newSuperClass);
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
	public void setSuperClass(Clazz newSuperClass) {
		if (newSuperClass != superClass) {
			NotificationChain msgs = null;
			if (superClass != null)
				msgs = ((InternalEObject) superClass).eInverseRemove(this,
						ClassInheritanceHierarchyPackage.CLAZZ__SUB_CLASS, Clazz.class, msgs);
			if (newSuperClass != null)
				msgs = ((InternalEObject) newSuperClass).eInverseAdd(this,
						ClassInheritanceHierarchyPackage.CLAZZ__SUB_CLASS, Clazz.class, msgs);
			msgs = basicSetSuperClass(newSuperClass, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ClassInheritanceHierarchyPackage.CLAZZ__SUPER_CLASS,
					newSuperClass, newSuperClass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Clazz> getSubClass() {
		if (subClass == null) {
			subClass = new EObjectWithInverseResolvingEList<Clazz>(Clazz.class, this,
					ClassInheritanceHierarchyPackage.CLAZZ__SUB_CLASS,
					ClassInheritanceHierarchyPackage.CLAZZ__SUPER_CLASS);
		}
		return subClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ClassPackage getPackage() {
		if (eContainerFeatureID() != ClassInheritanceHierarchyPackage.CLAZZ__PACKAGE)
			return null;
		return (ClassPackage) eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPackage(ClassPackage newPackage, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject) newPackage, ClassInheritanceHierarchyPackage.CLAZZ__PACKAGE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPackage(ClassPackage newPackage) {
		if (newPackage != eInternalContainer()
				|| (eContainerFeatureID() != ClassInheritanceHierarchyPackage.CLAZZ__PACKAGE && newPackage != null)) {
			if (EcoreUtil.isAncestor(this, newPackage))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newPackage != null)
				msgs = ((InternalEObject) newPackage).eInverseAdd(this,
						ClassInheritanceHierarchyPackage.CLASS_PACKAGE__CLASSES, ClassPackage.class, msgs);
			msgs = basicSetPackage(newPackage, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ClassInheritanceHierarchyPackage.CLAZZ__PACKAGE,
					newPackage, newPackage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Attribute> getAttributes() {
		if (attributes == null) {
			attributes = new EObjectContainmentWithInverseEList<Attribute>(Attribute.class, this,
					ClassInheritanceHierarchyPackage.CLAZZ__ATTRIBUTES,
					ClassInheritanceHierarchyPackage.ATTRIBUTE__CLAZZ);
		}
		return attributes;
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
		case ClassInheritanceHierarchyPackage.CLAZZ__SUPER_CLASS:
			if (superClass != null)
				msgs = ((InternalEObject) superClass).eInverseRemove(this,
						ClassInheritanceHierarchyPackage.CLAZZ__SUB_CLASS, Clazz.class, msgs);
			return basicSetSuperClass((Clazz) otherEnd, msgs);
		case ClassInheritanceHierarchyPackage.CLAZZ__SUB_CLASS:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getSubClass()).basicAdd(otherEnd, msgs);
		case ClassInheritanceHierarchyPackage.CLAZZ__PACKAGE:
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			return basicSetPackage((ClassPackage) otherEnd, msgs);
		case ClassInheritanceHierarchyPackage.CLAZZ__ATTRIBUTES:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getAttributes()).basicAdd(otherEnd, msgs);
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
		case ClassInheritanceHierarchyPackage.CLAZZ__SUPER_CLASS:
			return basicSetSuperClass(null, msgs);
		case ClassInheritanceHierarchyPackage.CLAZZ__SUB_CLASS:
			return ((InternalEList<?>) getSubClass()).basicRemove(otherEnd, msgs);
		case ClassInheritanceHierarchyPackage.CLAZZ__PACKAGE:
			return basicSetPackage(null, msgs);
		case ClassInheritanceHierarchyPackage.CLAZZ__ATTRIBUTES:
			return ((InternalEList<?>) getAttributes()).basicRemove(otherEnd, msgs);
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
		case ClassInheritanceHierarchyPackage.CLAZZ__PACKAGE:
			return eInternalContainer().eInverseRemove(this, ClassInheritanceHierarchyPackage.CLASS_PACKAGE__CLASSES,
					ClassPackage.class, msgs);
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
		case ClassInheritanceHierarchyPackage.CLAZZ__SUPER_CLASS:
			if (resolve)
				return getSuperClass();
			return basicGetSuperClass();
		case ClassInheritanceHierarchyPackage.CLAZZ__SUB_CLASS:
			return getSubClass();
		case ClassInheritanceHierarchyPackage.CLAZZ__PACKAGE:
			return getPackage();
		case ClassInheritanceHierarchyPackage.CLAZZ__ATTRIBUTES:
			return getAttributes();
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
		case ClassInheritanceHierarchyPackage.CLAZZ__SUPER_CLASS:
			setSuperClass((Clazz) newValue);
			return;
		case ClassInheritanceHierarchyPackage.CLAZZ__SUB_CLASS:
			getSubClass().clear();
			getSubClass().addAll((Collection<? extends Clazz>) newValue);
			return;
		case ClassInheritanceHierarchyPackage.CLAZZ__PACKAGE:
			setPackage((ClassPackage) newValue);
			return;
		case ClassInheritanceHierarchyPackage.CLAZZ__ATTRIBUTES:
			getAttributes().clear();
			getAttributes().addAll((Collection<? extends Attribute>) newValue);
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
		case ClassInheritanceHierarchyPackage.CLAZZ__SUPER_CLASS:
			setSuperClass((Clazz) null);
			return;
		case ClassInheritanceHierarchyPackage.CLAZZ__SUB_CLASS:
			getSubClass().clear();
			return;
		case ClassInheritanceHierarchyPackage.CLAZZ__PACKAGE:
			setPackage((ClassPackage) null);
			return;
		case ClassInheritanceHierarchyPackage.CLAZZ__ATTRIBUTES:
			getAttributes().clear();
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
		case ClassInheritanceHierarchyPackage.CLAZZ__SUPER_CLASS:
			return superClass != null;
		case ClassInheritanceHierarchyPackage.CLAZZ__SUB_CLASS:
			return subClass != null && !subClass.isEmpty();
		case ClassInheritanceHierarchyPackage.CLAZZ__PACKAGE:
			return getPackage() != null;
		case ClassInheritanceHierarchyPackage.CLAZZ__ATTRIBUTES:
			return attributes != null && !attributes.isEmpty();
		}
		return super.eIsSet(featureID);
	}
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} //ClazzImpl
