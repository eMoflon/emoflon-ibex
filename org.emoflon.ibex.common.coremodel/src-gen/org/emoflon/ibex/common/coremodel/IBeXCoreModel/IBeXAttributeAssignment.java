/**
 */
package org.emoflon.ibex.common.coremodel.IBeXCoreModel;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ValueExpression;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XAttribute Assignment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeAssignment#getNode <em>Node</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeAssignment#getAttribute <em>Attribute</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeAssignment#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXAttributeAssignment()
 * @model
 * @generated
 */
public interface IBeXAttributeAssignment extends EObject {
	/**
	 * Returns the value of the '<em><b>Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Node</em>' reference.
	 * @see #setNode(IBeXNode)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXAttributeAssignment_Node()
	 * @model required="true"
	 * @generated
	 */
	IBeXNode getNode();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeAssignment#getNode <em>Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Node</em>' reference.
	 * @see #getNode()
	 * @generated
	 */
	void setNode(IBeXNode value);

	/**
	 * Returns the value of the '<em><b>Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute</em>' reference.
	 * @see #setAttribute(EAttribute)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXAttributeAssignment_Attribute()
	 * @model
	 * @generated
	 */
	EAttribute getAttribute();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeAssignment#getAttribute <em>Attribute</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute</em>' reference.
	 * @see #getAttribute()
	 * @generated
	 */
	void setAttribute(EAttribute value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' containment reference.
	 * @see #setValue(ValueExpression)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXAttributeAssignment_Value()
	 * @model containment="true" required="true"
	 * @generated
	 */
	ValueExpression getValue();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeAssignment#getValue <em>Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' containment reference.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(ValueExpression value);

} // IBeXAttributeAssignment
