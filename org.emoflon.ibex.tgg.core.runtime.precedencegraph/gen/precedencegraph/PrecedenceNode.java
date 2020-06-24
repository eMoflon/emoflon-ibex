/**
 */
package precedencegraph;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Precedence Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link precedencegraph.PrecedenceNode#isBroken <em>Broken</em>}</li>
 *   <li>{@link precedencegraph.PrecedenceNode#getRequires <em>Requires</em>}</li>
 *   <li>{@link precedencegraph.PrecedenceNode#getRequiredBy <em>Required By</em>}</li>
 *   <li>{@link precedencegraph.PrecedenceNode#getMatchAsString <em>Match As String</em>}</li>
 *   <li>{@link precedencegraph.PrecedenceNode#getPrecedencenodecontainer <em>Precedencenodecontainer</em>}</li>
 *   <li>{@link precedencegraph.PrecedenceNode#getRollbackCauses <em>Rollback Causes</em>}</li>
 *   <li>{@link precedencegraph.PrecedenceNode#getRolledback <em>Rolledback</em>}</li>
 * </ul>
 *
 * @see precedencegraph.PrecedencegraphPackage#getPrecedenceNode()
 * @model
 * @generated
 */
public interface PrecedenceNode extends EObject {
	/**
	 * Returns the value of the '<em><b>Broken</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Broken</em>' attribute.
	 * @see #setBroken(boolean)
	 * @see precedencegraph.PrecedencegraphPackage#getPrecedenceNode_Broken()
	 * @model
	 * @generated
	 */
	boolean isBroken();

	/**
	 * Sets the value of the '{@link precedencegraph.PrecedenceNode#isBroken <em>Broken</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Broken</em>' attribute.
	 * @see #isBroken()
	 * @generated
	 */
	void setBroken(boolean value);

	/**
	 * Returns the value of the '<em><b>Requires</b></em>' reference list.
	 * The list contents are of type {@link precedencegraph.PrecedenceNode}.
	 * It is bidirectional and its opposite is '{@link precedencegraph.PrecedenceNode#getRequiredBy <em>Required By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Requires</em>' reference list.
	 * @see precedencegraph.PrecedencegraphPackage#getPrecedenceNode_Requires()
	 * @see precedencegraph.PrecedenceNode#getRequiredBy
	 * @model opposite="requiredBy"
	 * @generated
	 */
	EList<PrecedenceNode> getRequires();

	/**
	 * Returns the value of the '<em><b>Required By</b></em>' reference list.
	 * The list contents are of type {@link precedencegraph.PrecedenceNode}.
	 * It is bidirectional and its opposite is '{@link precedencegraph.PrecedenceNode#getRequires <em>Requires</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Required By</em>' reference list.
	 * @see precedencegraph.PrecedencegraphPackage#getPrecedenceNode_RequiredBy()
	 * @see precedencegraph.PrecedenceNode#getRequires
	 * @model opposite="requires"
	 * @generated
	 */
	EList<PrecedenceNode> getRequiredBy();

	/**
	 * Returns the value of the '<em><b>Match As String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Match As String</em>' attribute.
	 * @see #setMatchAsString(String)
	 * @see precedencegraph.PrecedencegraphPackage#getPrecedenceNode_MatchAsString()
	 * @model
	 * @generated
	 */
	String getMatchAsString();

	/**
	 * Sets the value of the '{@link precedencegraph.PrecedenceNode#getMatchAsString <em>Match As String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Match As String</em>' attribute.
	 * @see #getMatchAsString()
	 * @generated
	 */
	void setMatchAsString(String value);

	/**
	 * Returns the value of the '<em><b>Precedencenodecontainer</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link precedencegraph.PrecedenceNodeContainer#getNodes <em>Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Precedencenodecontainer</em>' container reference.
	 * @see #setPrecedencenodecontainer(PrecedenceNodeContainer)
	 * @see precedencegraph.PrecedencegraphPackage#getPrecedenceNode_Precedencenodecontainer()
	 * @see precedencegraph.PrecedenceNodeContainer#getNodes
	 * @model opposite="nodes" transient="false"
	 * @generated
	 */
	PrecedenceNodeContainer getPrecedencenodecontainer();

	/**
	 * Sets the value of the '{@link precedencegraph.PrecedenceNode#getPrecedencenodecontainer <em>Precedencenodecontainer</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Precedencenodecontainer</em>' container reference.
	 * @see #getPrecedencenodecontainer()
	 * @generated
	 */
	void setPrecedencenodecontainer(PrecedenceNodeContainer value);

	/**
	 * Returns the value of the '<em><b>Rollback Causes</b></em>' reference list.
	 * The list contents are of type {@link precedencegraph.PrecedenceNode}.
	 * It is bidirectional and its opposite is '{@link precedencegraph.PrecedenceNode#getRolledback <em>Rolledback</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rollback Causes</em>' reference list.
	 * @see precedencegraph.PrecedencegraphPackage#getPrecedenceNode_RollbackCauses()
	 * @see precedencegraph.PrecedenceNode#getRolledback
	 * @model opposite="rolledback"
	 * @generated
	 */
	EList<PrecedenceNode> getRollbackCauses();

	/**
	 * Returns the value of the '<em><b>Rolledback</b></em>' reference list.
	 * The list contents are of type {@link precedencegraph.PrecedenceNode}.
	 * It is bidirectional and its opposite is '{@link precedencegraph.PrecedenceNode#getRollbackCauses <em>Rollback Causes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rolledback</em>' reference list.
	 * @see precedencegraph.PrecedencegraphPackage#getPrecedenceNode_Rolledback()
	 * @see precedencegraph.PrecedenceNode#getRollbackCauses
	 * @model opposite="rollbackCauses"
	 * @generated
	 */
	EList<PrecedenceNode> getRolledback();

} // PrecedenceNode
