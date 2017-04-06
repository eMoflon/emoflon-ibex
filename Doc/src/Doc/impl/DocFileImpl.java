/**
 */
package Doc.impl;

import Doc.DocFile;
import Doc.DocPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>File</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link Doc.impl.DocFileImpl#getAllSuperRefs <em>All Super Refs</em>}</li>
 *   <li>{@link Doc.impl.DocFileImpl#getSuperRef <em>Super Ref</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DocFileImpl extends FileImpl implements DocFile {
	/**
	 * The cached value of the '{@link #getAllSuperRefs() <em>All Super Refs</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAllSuperRefs()
	 * @generated
	 * @ordered
	 */
	protected EList<DocFile> allSuperRefs;
	/**
	 * The cached value of the '{@link #getSuperRef() <em>Super Ref</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSuperRef()
	 * @generated
	 * @ordered
	 */
	protected DocFile superRef;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DocFileImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DocPackage.Literals.DOC_FILE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DocFile> getAllSuperRefs() {
		if (allSuperRefs == null) {
			allSuperRefs = new EObjectResolvingEList<DocFile>(DocFile.class, this, DocPackage.DOC_FILE__ALL_SUPER_REFS);
		}
		return allSuperRefs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DocFile getSuperRef() {
		if (superRef != null && superRef.eIsProxy()) {
			InternalEObject oldSuperRef = (InternalEObject)superRef;
			superRef = (DocFile)eResolveProxy(oldSuperRef);
			if (superRef != oldSuperRef) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DocPackage.DOC_FILE__SUPER_REF, oldSuperRef, superRef));
			}
		}
		return superRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DocFile basicGetSuperRef() {
		return superRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSuperRef(DocFile newSuperRef) {
		DocFile oldSuperRef = superRef;
		superRef = newSuperRef;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DocPackage.DOC_FILE__SUPER_REF, oldSuperRef, superRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DocPackage.DOC_FILE__ALL_SUPER_REFS:
				return getAllSuperRefs();
			case DocPackage.DOC_FILE__SUPER_REF:
				if (resolve) return getSuperRef();
				return basicGetSuperRef();
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
			case DocPackage.DOC_FILE__ALL_SUPER_REFS:
				getAllSuperRefs().clear();
				getAllSuperRefs().addAll((Collection<? extends DocFile>)newValue);
				return;
			case DocPackage.DOC_FILE__SUPER_REF:
				setSuperRef((DocFile)newValue);
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
			case DocPackage.DOC_FILE__ALL_SUPER_REFS:
				getAllSuperRefs().clear();
				return;
			case DocPackage.DOC_FILE__SUPER_REF:
				setSuperRef((DocFile)null);
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
			case DocPackage.DOC_FILE__ALL_SUPER_REFS:
				return allSuperRefs != null && !allSuperRefs.isEmpty();
			case DocPackage.DOC_FILE__SUPER_REF:
				return superRef != null;
		}
		return super.eIsSet(featureID);
	}

} //DocFileImpl
