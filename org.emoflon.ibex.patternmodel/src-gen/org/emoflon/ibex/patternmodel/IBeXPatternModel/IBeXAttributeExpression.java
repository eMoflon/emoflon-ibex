/**
 */
package org.emoflon.ibex.patternmodel.IBeXPatternModel;

import org.eclipse.emf.ecore.EAttribute;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XAttribute Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * An attribute expression points to the attribute value of a node.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeExpression#getAttribute <em>Attribute</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeExpression#getNode <em>Node</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXAttributeExpression()
 * @model
 * @generated
 */
public interface IBeXAttributeExpression extends IBeXAttributeValue {
	/**
	 * Returns the value of the '<em><b>Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute</em>' reference.
	 * @see #setAttribute(EAttribute)
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXAttributeExpression_Attribute()
	 * @model
	 * @generated
	 */
	EAttribute getAttribute();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeExpression#getAttribute <em>Attribute</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute</em>' reference.
	 * @see #getAttribute()
	 * @generated
	 */
	void setAttribute(EAttribute value);

	/**
	 * Returns the value of the '<em><b>Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Node</em>' reference.
	 * @see #setNode(IBeXNode)
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXAttributeExpression_Node()
	 * @model
	 * @generated
	 */
	IBeXNode getNode();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeExpression#getNode <em>Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Node</em>' reference.
	 * @see #getNode()
	 * @generated
	 */
	void setNode(IBeXNode value);

} // IBeXAttributeExpression
