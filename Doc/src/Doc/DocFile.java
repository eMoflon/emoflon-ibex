/**
 */
package Doc;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>File</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link Doc.DocFile#getAllSuperRefs <em>All Super Refs</em>}</li>
 *   <li>{@link Doc.DocFile#getSuperRef <em>Super Ref</em>}</li>
 * </ul>
 *
 * @see Doc.DocPackage#getDocFile()
 * @model
 * @generated
 */
public interface DocFile extends File {
	/**
	 * Returns the value of the '<em><b>All Super Refs</b></em>' reference list.
	 * The list contents are of type {@link Doc.DocFile}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>All Super Refs</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>All Super Refs</em>' reference list.
	 * @see Doc.DocPackage#getDocFile_AllSuperRefs()
	 * @model
	 * @generated
	 */
	EList<DocFile> getAllSuperRefs();

	/**
	 * Returns the value of the '<em><b>Super Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Super Ref</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Super Ref</em>' reference.
	 * @see #setSuperRef(DocFile)
	 * @see Doc.DocPackage#getDocFile_SuperRef()
	 * @model
	 * @generated
	 */
	DocFile getSuperRef();

	/**
	 * Sets the value of the '{@link Doc.DocFile#getSuperRef <em>Super Ref</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Super Ref</em>' reference.
	 * @see #getSuperRef()
	 * @generated
	 */
	void setSuperRef(DocFile value);

} // DocFile
