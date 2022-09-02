/**
 */
package org.emoflon.ibex.common.coremodel.IBeXCoreModel;

import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XEdge</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge#getType <em>Type</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge#getSource <em>Source</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge#getTarget <em>Target</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXEdge()
 * @model
 * @generated
 */
public interface IBeXEdge extends IBeXNamedElement {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(EReference)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXEdge_Type()
	 * @model
	 * @generated
	 */
	EReference getType();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(EReference value);

	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode#getOutgoingEdges <em>Outgoing Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(IBeXNode)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXEdge_Source()
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode#getOutgoingEdges
	 * @model opposite="outgoingEdges" required="true"
	 * @generated
	 */
	IBeXNode getSource();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(IBeXNode value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode#getIncomingEdges <em>Incoming Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(IBeXNode)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXEdge_Target()
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode#getIncomingEdges
	 * @model opposite="incomingEdges" required="true"
	 * @generated
	 */
	IBeXNode getTarget();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(IBeXNode value);

} // IBeXEdge
