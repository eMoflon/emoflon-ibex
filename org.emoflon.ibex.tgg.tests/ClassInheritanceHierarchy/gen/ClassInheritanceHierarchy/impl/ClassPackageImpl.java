/**
 */
package ClassInheritanceHierarchy.impl;

import ClassInheritanceHierarchy.ClassInheritanceHierarchyPackage;
import ClassInheritanceHierarchy.ClassPackage;
import ClassInheritanceHierarchy.Clazz;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Class Package</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link ClassInheritanceHierarchy.impl.ClassPackageImpl#getClasses <em>Classes</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ClassPackageImpl extends NamedElementImpl implements ClassPackage {
	/**
	 * The cached value of the '{@link #getClasses() <em>Classes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClasses()
	 * @generated
	 * @ordered
	 */
	protected EList<Clazz> classes;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ClassPackageImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ClassInheritanceHierarchyPackage.Literals.CLASS_PACKAGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Clazz> getClasses() {
		if (classes == null) {
			classes = new EObjectContainmentWithInverseEList<Clazz>(Clazz.class, this,
					ClassInheritanceHierarchyPackage.CLASS_PACKAGE__CLASSES,
					ClassInheritanceHierarchyPackage.CLAZZ__PACKAGE);
		}
		return classes;
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
		case ClassInheritanceHierarchyPackage.CLASS_PACKAGE__CLASSES:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getClasses()).basicAdd(otherEnd, msgs);
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
		case ClassInheritanceHierarchyPackage.CLASS_PACKAGE__CLASSES:
			return ((InternalEList<?>) getClasses()).basicRemove(otherEnd, msgs);
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
		case ClassInheritanceHierarchyPackage.CLASS_PACKAGE__CLASSES:
			return getClasses();
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
		case ClassInheritanceHierarchyPackage.CLASS_PACKAGE__CLASSES:
			getClasses().clear();
			getClasses().addAll((Collection<? extends Clazz>) newValue);
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
		case ClassInheritanceHierarchyPackage.CLASS_PACKAGE__CLASSES:
			getClasses().clear();
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
		case ClassInheritanceHierarchyPackage.CLASS_PACKAGE__CLASSES:
			return classes != null && !classes.isEmpty();
		}
		return super.eIsSet(featureID);
	}
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} //ClassPackageImpl
