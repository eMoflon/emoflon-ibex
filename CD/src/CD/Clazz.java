/**
 */
package CD;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Clazz</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link CD.Clazz#getName <em>Name</em>}</li>
 *   <li>{@link CD.Clazz#getSuperClazz <em>Super Clazz</em>}</li>
 *   <li>{@link CD.Clazz#isGenerateDoc <em>Generate Doc</em>}</li>
 * </ul>
 *
 * @see CD.CDPackage#getClazz()
 * @model
 * @generated
 */
public interface Clazz extends EObject {
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
	 * @see CD.CDPackage#getClazz_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link CD.Clazz#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Super Clazz</b></em>' reference list.
	 * The list contents are of type {@link CD.Clazz}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Super Clazz</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Super Clazz</em>' reference list.
	 * @see CD.CDPackage#getClazz_SuperClazz()
	 * @model
	 * @generated
	 */
	EList<Clazz> getSuperClazz();

	/**
	 * Returns the value of the '<em><b>Generate Doc</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Generate Doc</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Generate Doc</em>' attribute.
	 * @see #setGenerateDoc(boolean)
	 * @see CD.CDPackage#getClazz_GenerateDoc()
	 * @model default="false"
	 * @generated
	 */
	boolean isGenerateDoc();

	/**
	 * Sets the value of the '{@link CD.Clazz#isGenerateDoc <em>Generate Doc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Generate Doc</em>' attribute.
	 * @see #isGenerateDoc()
	 * @generated
	 */
	void setGenerateDoc(boolean value);

} // Clazz
