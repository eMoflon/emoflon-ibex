/**
 */
package org.emoflon.ibex.common.coremodel.IBeXCoreModel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XNode</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode#getType <em>Type</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode#getOperationType <em>Operation Type</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode#getIncomingEdges <em>Incoming Edges</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode#getOutgoingEdges <em>Outgoing Edges</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXNode()
 * @model
 * @generated
 */
public interface IBeXNode extends IBeXNamedElement {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(EClass)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXNode_Type()
	 * @model
	 * @generated
	 */
	EClass getType();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(EClass value);

	/**
	 * Returns the value of the '<em><b>Operation Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXOperationType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operation Type</em>' attribute.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXOperationType
	 * @see #setOperationType(IBeXOperationType)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXNode_OperationType()
	 * @model required="true"
	 * @generated
	 */
	IBeXOperationType getOperationType();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode#getOperationType <em>Operation Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operation Type</em>' attribute.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXOperationType
	 * @see #getOperationType()
	 * @generated
	 */
	void setOperationType(IBeXOperationType value);

	/**
	 * Returns the value of the '<em><b>Incoming Edges</b></em>' reference list.
	 * The list contents are of type {@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge}.
	 * It is bidirectional and its opposite is '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Incoming Edges</em>' reference list.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXNode_IncomingEdges()
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge#getTarget
	 * @model opposite="target"
	 * @generated
	 */
	EList<IBeXEdge> getIncomingEdges();

	/**
	 * Returns the value of the '<em><b>Outgoing Edges</b></em>' reference list.
	 * The list contents are of type {@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge}.
	 * It is bidirectional and its opposite is '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outgoing Edges</em>' reference list.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXNode_OutgoingEdges()
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge#getSource
	 * @model opposite="source"
	 * @generated
	 */
	EList<IBeXEdge> getOutgoingEdges();

} // IBeXNode
