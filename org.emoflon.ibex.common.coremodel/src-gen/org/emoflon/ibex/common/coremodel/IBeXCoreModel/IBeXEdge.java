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
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge#getSrc <em>Src</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge#getTrg <em>Trg</em>}</li>
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
	 * Returns the value of the '<em><b>Src</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode#getOutgoingEdges <em>Outgoing Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Src</em>' reference.
	 * @see #setSrc(IBeXNode)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXEdge_Src()
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode#getOutgoingEdges
	 * @model opposite="outgoingEdges" required="true"
	 * @generated
	 */
	IBeXNode getSrc();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge#getSrc <em>Src</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Src</em>' reference.
	 * @see #getSrc()
	 * @generated
	 */
	void setSrc(IBeXNode value);

	/**
	 * Returns the value of the '<em><b>Trg</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode#getIncomingEdges <em>Incoming Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Trg</em>' reference.
	 * @see #setTrg(IBeXNode)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXEdge_Trg()
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode#getIncomingEdges
	 * @model opposite="incomingEdges" required="true"
	 * @generated
	 */
	IBeXNode getTrg();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge#getTrg <em>Trg</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Trg</em>' reference.
	 * @see #getTrg()
	 * @generated
	 */
	void setTrg(IBeXNode value);

} // IBeXEdge
