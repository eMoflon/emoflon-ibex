/**
 */
package CD;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Package</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link CD.Package#getClazzs <em>Clazzs</em>}</li>
 *   <li>{@link CD.Package#getPackages <em>Packages</em>}</li>
 *   <li>{@link CD.Package#getName <em>Name</em>}</li>
 *   <li>{@link CD.Package#getIndex <em>Index</em>}</li>
 *   <li>{@link CD.Package#getPEnum <em>PEnum</em>}</li>
 * </ul>
 *
 * @see CD.CDPackage#getPackage()
 * @model
 * @generated
 */
public interface Package extends EObject {
	/**
	 * Returns the value of the '<em><b>Clazzs</b></em>' containment reference list.
	 * The list contents are of type {@link CD.Clazz}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Clazzs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Clazzs</em>' containment reference list.
	 * @see CD.CDPackage#getPackage_Clazzs()
	 * @model containment="true"
	 * @generated
	 */
	EList<Clazz> getClazzs();

	/**
	 * Returns the value of the '<em><b>Packages</b></em>' containment reference list.
	 * The list contents are of type {@link CD.Package}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Packages</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Packages</em>' containment reference list.
	 * @see CD.CDPackage#getPackage_Packages()
	 * @model containment="true"
	 * @generated
	 */
	EList<Package> getPackages();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see CD.CDPackage#getPackage_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link CD.Package#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Index</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Index</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Index</em>' attribute.
	 * @see #setIndex(int)
	 * @see CD.CDPackage#getPackage_Index()
	 * @model default="0"
	 * @generated
	 */
	int getIndex();

	/**
	 * Sets the value of the '{@link CD.Package#getIndex <em>Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Index</em>' attribute.
	 * @see #getIndex()
	 * @generated
	 */
	void setIndex(int value);

	/**
	 * Returns the value of the '<em><b>PEnum</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * The literals are from the enumeration {@link CD.PackageEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>PEnum</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>PEnum</em>' attribute.
	 * @see CD.PackageEnum
	 * @see #setPEnum(PackageEnum)
	 * @see CD.CDPackage#getPackage_PEnum()
	 * @model default=""
	 * @generated
	 */
	PackageEnum getPEnum();

	/**
	 * Sets the value of the '{@link CD.Package#getPEnum <em>PEnum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>PEnum</em>' attribute.
	 * @see CD.PackageEnum
	 * @see #getPEnum()
	 * @generated
	 */
	void setPEnum(PackageEnum value);

} // Package
