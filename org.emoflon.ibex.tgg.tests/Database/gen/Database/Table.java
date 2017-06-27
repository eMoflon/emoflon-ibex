/**
 */
package Database;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Table</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link Database.Table#getColumns <em>Columns</em>}</li>
 *   <li>{@link Database.Table#getHeading <em>Heading</em>}</li>
 * </ul>
 *
 * @see Database.DatabasePackage#getTable()
 * @model
 * @generated
 */
public interface Table extends EObject {
	/**
	 * Returns the value of the '<em><b>Columns</b></em>' containment reference list.
	 * The list contents are of type {@link Database.Column}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Columns</em>' containment reference list.
	 * @see Database.DatabasePackage#getTable_Columns()
	 * @model containment="true"
	 * @generated
	 */
	EList<Column> getColumns();

	/**
	 * Returns the value of the '<em><b>Heading</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Heading</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Heading</em>' attribute.
	 * @see #setHeading(String)
	 * @see Database.DatabasePackage#getTable_Heading()
	 * @model required="true" ordered="false"
	 * @generated
	 */
	String getHeading();

	/**
	 * Sets the value of the '{@link Database.Table#getHeading <em>Heading</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Heading</em>' attribute.
	 * @see #getHeading()
	 * @generated
	 */
	void setHeading(String value);

} // Table
