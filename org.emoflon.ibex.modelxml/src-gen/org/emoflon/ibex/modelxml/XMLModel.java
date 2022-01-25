/**
 */
package org.emoflon.ibex.modelxml;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>XML Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.modelXML.XMLModel#getRoot <em>Root</em>}</li>
 *   <li>{@link org.emoflon.ibex.modelXML.XMLModel#getHeader <em>Header</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.modelXML.ModelxmlPackage#getXMLModel()
 * @model
 * @generated
 */
public interface XMLModel extends EObject {
	/**
	 * Returns the value of the '<em><b>Root</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Root</em>' containment reference.
	 * @see #setRoot(Node)
	 * @see org.emoflon.ibex.modelXML.ModelxmlPackage#getXMLModel_Root()
	 * @model containment="true"
	 * @generated
	 */
	Node getRoot();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.modelXML.XMLModel#getRoot <em>Root</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Root</em>' containment reference.
	 * @see #getRoot()
	 * @generated
	 */
	void setRoot(Node value);

	/**
	 * Returns the value of the '<em><b>Header</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Header</em>' attribute.
	 * @see #setHeader(String)
	 * @see org.emoflon.ibex.modelXML.ModelxmlPackage#getXMLModel_Header()
	 * @model
	 * @generated
	 */
	String getHeader();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.modelXML.XMLModel#getHeader <em>Header</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Header</em>' attribute.
	 * @see #getHeader()
	 * @generated
	 */
	void setHeader(String value);

} // XMLModel
