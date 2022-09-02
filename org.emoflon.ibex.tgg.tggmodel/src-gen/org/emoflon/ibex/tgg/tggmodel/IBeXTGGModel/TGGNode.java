/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel;

import org.eclipse.emf.common.util.EList;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TGG Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode#getIncomingCorrespondence <em>Incoming Correspondence</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode#getOutgoingCorrespondence <em>Outgoing Correspondence</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGNode()
 * @model
 * @generated
 */
public interface TGGNode extends IBeXNode, TGGRuleElement {
	/**
	 * Returns the value of the '<em><b>Incoming Correspondence</b></em>' reference list.
	 * The list contents are of type {@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleCorrespondence}.
	 * It is bidirectional and its opposite is '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleCorrespondence#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Incoming Correspondence</em>' reference list.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGNode_IncomingCorrespondence()
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleCorrespondence#getTarget
	 * @model opposite="target"
	 * @generated
	 */
	EList<TGGRuleCorrespondence> getIncomingCorrespondence();

	/**
	 * Returns the value of the '<em><b>Outgoing Correspondence</b></em>' reference list.
	 * The list contents are of type {@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleCorrespondence}.
	 * It is bidirectional and its opposite is '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleCorrespondence#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outgoing Correspondence</em>' reference list.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGNode_OutgoingCorrespondence()
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleCorrespondence#getSource
	 * @model opposite="source"
	 * @generated
	 */
	EList<TGGRuleCorrespondence> getOutgoingCorrespondence();

} // TGGNode
