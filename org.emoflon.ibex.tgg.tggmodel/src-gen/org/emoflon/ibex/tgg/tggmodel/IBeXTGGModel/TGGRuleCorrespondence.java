/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TGG Rule Correspondence</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleCorrespondence#getSource <em>Source</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleCorrespondence#getTarget <em>Target</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGRuleCorrespondence()
 * @model
 * @generated
 */
public interface TGGRuleCorrespondence extends TGGNode {
	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode#getOutgoingCorrespondence <em>Outgoing Correspondence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(TGGNode)
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGRuleCorrespondence_Source()
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode#getOutgoingCorrespondence
	 * @model opposite="outgoingCorrespondence" required="true"
	 * @generated
	 */
	TGGNode getSource();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleCorrespondence#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(TGGNode value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode#getIncomingCorrespondence <em>Incoming Correspondence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(TGGNode)
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGRuleCorrespondence_Target()
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode#getIncomingCorrespondence
	 * @model opposite="incomingCorrespondence" required="true"
	 * @generated
	 */
	TGGNode getTarget();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleCorrespondence#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(TGGNode value);

} // TGGRuleCorrespondence
