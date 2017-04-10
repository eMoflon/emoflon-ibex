/**
 */
package CD.impl;

import CD.CDPackage;
import CD.Clazz;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Clazz</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link CD.impl.ClazzImpl#getName <em>Name</em>}</li>
 *   <li>{@link CD.impl.ClazzImpl#getSuperClazz <em>Super Clazz</em>}</li>
 *   <li>{@link CD.impl.ClazzImpl#isGenerateDoc <em>Generate Doc</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ClazzImpl extends MinimalEObjectImpl.Container implements Clazz {
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
	 * The cached value of the '{@link #getSuperClazz() <em>Super Clazz</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSuperClazz()
	 * @generated
	 * @ordered
	 */
	protected EList<Clazz> superClazz;

	/**
	 * The default value of the '{@link #isGenerateDoc() <em>Generate Doc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerateDoc()
	 * @generated
	 * @ordered
	 */
	protected static final boolean GENERATE_DOC_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isGenerateDoc() <em>Generate Doc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerateDoc()
	 * @generated
	 * @ordered
	 */
	protected boolean generateDoc = GENERATE_DOC_EDEFAULT;

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
		return CDPackage.Literals.CLAZZ;
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
			eNotify(new ENotificationImpl(this, Notification.SET, CDPackage.CLAZZ__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Clazz> getSuperClazz() {
		if (superClazz == null) {
			superClazz = new EObjectResolvingEList<Clazz>(Clazz.class, this, CDPackage.CLAZZ__SUPER_CLAZZ);
		}
		return superClazz;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isGenerateDoc() {
		return generateDoc;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGenerateDoc(boolean newGenerateDoc) {
		boolean oldGenerateDoc = generateDoc;
		generateDoc = newGenerateDoc;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CDPackage.CLAZZ__GENERATE_DOC, oldGenerateDoc, generateDoc));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CDPackage.CLAZZ__NAME:
				return getName();
			case CDPackage.CLAZZ__SUPER_CLAZZ:
				return getSuperClazz();
			case CDPackage.CLAZZ__GENERATE_DOC:
				return isGenerateDoc();
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
			case CDPackage.CLAZZ__NAME:
				setName((String)newValue);
				return;
			case CDPackage.CLAZZ__SUPER_CLAZZ:
				getSuperClazz().clear();
				getSuperClazz().addAll((Collection<? extends Clazz>)newValue);
				return;
			case CDPackage.CLAZZ__GENERATE_DOC:
				setGenerateDoc((Boolean)newValue);
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
			case CDPackage.CLAZZ__NAME:
				setName(NAME_EDEFAULT);
				return;
			case CDPackage.CLAZZ__SUPER_CLAZZ:
				getSuperClazz().clear();
				return;
			case CDPackage.CLAZZ__GENERATE_DOC:
				setGenerateDoc(GENERATE_DOC_EDEFAULT);
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
			case CDPackage.CLAZZ__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case CDPackage.CLAZZ__SUPER_CLAZZ:
				return superClazz != null && !superClazz.isEmpty();
			case CDPackage.CLAZZ__GENERATE_DOC:
				return generateDoc != GENERATE_DOC_EDEFAULT;
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
		result.append(", generateDoc: ");
		result.append(generateDoc);
		result.append(')');
		return result.toString();
	}

} //ClazzImpl
