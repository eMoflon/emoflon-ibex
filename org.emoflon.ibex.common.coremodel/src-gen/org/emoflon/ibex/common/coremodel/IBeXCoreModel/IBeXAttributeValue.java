/**
 */
package org.emoflon.ibex.common.coremodel.IBeXCoreModel;

import org.eclipse.emf.ecore.EAttribute;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ArithmeticValue;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XAttribute Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeValue#getNode <em>Node</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeValue#getAttribute <em>Attribute</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXAttributeValue()
 * @model
 * @generated
 */
public interface IBeXAttributeValue extends ArithmeticValue {
	/**
	 * Returns the value of the '<em><b>Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Node</em>' reference.
	 * @see #setNode(IBeXNode)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXAttributeValue_Node()
	 * @model required="true"
	 * @generated
	 */
	IBeXNode getNode();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeValue#getNode <em>Node</em>}' reference.
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
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXAttributeValue_Attribute()
	 * @model
	 * @generated
	 */
	EAttribute getAttribute();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeValue#getAttribute <em>Attribute</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute</em>' reference.
	 * @see #getAttribute()
	 * @generated
	 */
	void setAttribute(EAttribute value);

} // IBeXAttributeValue
