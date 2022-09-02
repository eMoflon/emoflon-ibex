/**
 */
package org.emoflon.ibex.common.coremodel.IBeXCoreModel;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XRule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRule#getPrecondition <em>Precondition</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRule#getPostcondition <em>Postcondition</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRule#getCreation <em>Creation</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRule#getDeletion <em>Deletion</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRule#getAttributeAssignments <em>Attribute Assignments</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXRule()
 * @model
 * @generated
 */
public interface IBeXRule extends IBeXNamedElement {
	/**
	 * Returns the value of the '<em><b>Precondition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Precondition</em>' reference.
	 * @see #setPrecondition(IBeXPattern)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXRule_Precondition()
	 * @model required="true"
	 * @generated
	 */
	IBeXPattern getPrecondition();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRule#getPrecondition <em>Precondition</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Precondition</em>' reference.
	 * @see #getPrecondition()
	 * @generated
	 */
	void setPrecondition(IBeXPattern value);

	/**
	 * Returns the value of the '<em><b>Postcondition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Postcondition</em>' containment reference.
	 * @see #setPostcondition(IBeXPattern)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXRule_Postcondition()
	 * @model containment="true" required="true"
	 * @generated
	 */
	IBeXPattern getPostcondition();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRule#getPostcondition <em>Postcondition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Postcondition</em>' containment reference.
	 * @see #getPostcondition()
	 * @generated
	 */
	void setPostcondition(IBeXPattern value);

	/**
	 * Returns the value of the '<em><b>Creation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Creation</em>' containment reference.
	 * @see #setCreation(IBeXRuleDelta)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXRule_Creation()
	 * @model containment="true"
	 * @generated
	 */
	IBeXRuleDelta getCreation();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRule#getCreation <em>Creation</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Creation</em>' containment reference.
	 * @see #getCreation()
	 * @generated
	 */
	void setCreation(IBeXRuleDelta value);

	/**
	 * Returns the value of the '<em><b>Deletion</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deletion</em>' containment reference.
	 * @see #setDeletion(IBeXRuleDelta)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXRule_Deletion()
	 * @model containment="true"
	 * @generated
	 */
	IBeXRuleDelta getDeletion();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRule#getDeletion <em>Deletion</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Deletion</em>' containment reference.
	 * @see #getDeletion()
	 * @generated
	 */
	void setDeletion(IBeXRuleDelta value);

	/**
	 * Returns the value of the '<em><b>Attribute Assignments</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeAssignment}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Assignments</em>' containment reference list.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXRule_AttributeAssignments()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBeXAttributeAssignment> getAttributeAssignments();

} // IBeXRule
