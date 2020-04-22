/**
 */
package org.emoflon.ibex.patternmodel.IBeXPatternModel;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XDelete Pattern</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A pattern for deletion defines which nodes and edges are deleted. Nodes which are not deleted, but source or target node of a deleted edge are context nodes.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDeletePattern#getContextNodes <em>Context Nodes</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDeletePattern#getDeletedEdges <em>Deleted Edges</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDeletePattern#getDeletedNodes <em>Deleted Nodes</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXDeletePattern()
 * @model
 * @generated
 */
public interface IBeXDeletePattern extends IBeXPattern {
	/**
	 * Returns the value of the '<em><b>Context Nodes</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Context Nodes</em>' containment reference list.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXDeletePattern_ContextNodes()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBeXNode> getContextNodes();

	/**
	 * Returns the value of the '<em><b>Deleted Edges</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEdge}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deleted Edges</em>' containment reference list.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXDeletePattern_DeletedEdges()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBeXEdge> getDeletedEdges();

	/**
	 * Returns the value of the '<em><b>Deleted Nodes</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deleted Nodes</em>' containment reference list.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXDeletePattern_DeletedNodes()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBeXNode> getDeletedNodes();

} // IBeXDeletePattern
