/**
 */
package CD.impl;

import CD.CDPackage;
import CD.Clazz;
import CD.PackageEnum;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Package</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link CD.impl.PackageImpl#getClazzs <em>Clazzs</em>}</li>
 *   <li>{@link CD.impl.PackageImpl#getPackages <em>Packages</em>}</li>
 *   <li>{@link CD.impl.PackageImpl#getName <em>Name</em>}</li>
 *   <li>{@link CD.impl.PackageImpl#getIndex <em>Index</em>}</li>
 *   <li>{@link CD.impl.PackageImpl#getPEnum <em>PEnum</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PackageImpl extends MinimalEObjectImpl.Container implements CD.Package {
	/**
	 * The cached value of the '{@link #getClazzs() <em>Clazzs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClazzs()
	 * @generated
	 * @ordered
	 */
	protected EList<Clazz> clazzs;

	/**
	 * The cached value of the '{@link #getPackages() <em>Packages</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackages()
	 * @generated
	 * @ordered
	 */
	protected EList<CD.Package> packages;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getIndex() <em>Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIndex()
	 * @generated
	 * @ordered
	 */
	protected static final int INDEX_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getIndex() <em>Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIndex()
	 * @generated
	 * @ordered
	 */
	protected int index = INDEX_EDEFAULT;

	/**
	 * The default value of the '{@link #getPEnum() <em>PEnum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPEnum()
	 * @generated
	 * @ordered
	 */
	protected static final PackageEnum PENUM_EDEFAULT = PackageEnum.FIRST_ENUM;

	/**
	 * The cached value of the '{@link #getPEnum() <em>PEnum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPEnum()
	 * @generated
	 * @ordered
	 */
	protected PackageEnum pEnum = PENUM_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PackageImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CDPackage.Literals.PACKAGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Clazz> getClazzs() {
		if (clazzs == null) {
			clazzs = new EObjectContainmentEList<Clazz>(Clazz.class, this, CDPackage.PACKAGE__CLAZZS);
		}
		return clazzs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<CD.Package> getPackages() {
		if (packages == null) {
			packages = new EObjectContainmentEList<CD.Package>(CD.Package.class, this, CDPackage.PACKAGE__PACKAGES);
		}
		return packages;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CDPackage.PACKAGE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIndex(int newIndex) {
		int oldIndex = index;
		index = newIndex;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CDPackage.PACKAGE__INDEX, oldIndex, index));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PackageEnum getPEnum() {
		return pEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPEnum(PackageEnum newPEnum) {
		PackageEnum oldPEnum = pEnum;
		pEnum = newPEnum == null ? PENUM_EDEFAULT : newPEnum;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CDPackage.PACKAGE__PENUM, oldPEnum, pEnum));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CDPackage.PACKAGE__CLAZZS:
				return ((InternalEList<?>)getClazzs()).basicRemove(otherEnd, msgs);
			case CDPackage.PACKAGE__PACKAGES:
				return ((InternalEList<?>)getPackages()).basicRemove(otherEnd, msgs);
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
			case CDPackage.PACKAGE__CLAZZS:
				return getClazzs();
			case CDPackage.PACKAGE__PACKAGES:
				return getPackages();
			case CDPackage.PACKAGE__NAME:
				return getName();
			case CDPackage.PACKAGE__INDEX:
				return getIndex();
			case CDPackage.PACKAGE__PENUM:
				return getPEnum();
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
			case CDPackage.PACKAGE__CLAZZS:
				getClazzs().clear();
				getClazzs().addAll((Collection<? extends Clazz>)newValue);
				return;
			case CDPackage.PACKAGE__PACKAGES:
				getPackages().clear();
				getPackages().addAll((Collection<? extends CD.Package>)newValue);
				return;
			case CDPackage.PACKAGE__NAME:
				setName((String)newValue);
				return;
			case CDPackage.PACKAGE__INDEX:
				setIndex((Integer)newValue);
				return;
			case CDPackage.PACKAGE__PENUM:
				setPEnum((PackageEnum)newValue);
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
			case CDPackage.PACKAGE__CLAZZS:
				getClazzs().clear();
				return;
			case CDPackage.PACKAGE__PACKAGES:
				getPackages().clear();
				return;
			case CDPackage.PACKAGE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case CDPackage.PACKAGE__INDEX:
				setIndex(INDEX_EDEFAULT);
				return;
			case CDPackage.PACKAGE__PENUM:
				setPEnum(PENUM_EDEFAULT);
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
			case CDPackage.PACKAGE__CLAZZS:
				return clazzs != null && !clazzs.isEmpty();
			case CDPackage.PACKAGE__PACKAGES:
				return packages != null && !packages.isEmpty();
			case CDPackage.PACKAGE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case CDPackage.PACKAGE__INDEX:
				return index != INDEX_EDEFAULT;
			case CDPackage.PACKAGE__PENUM:
				return pEnum != PENUM_EDEFAULT;
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
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", index: ");
		result.append(index);
		result.append(", pEnum: ");
		result.append(pEnum);
		result.append(')');
		return result.toString();
	}

} //PackageImpl
