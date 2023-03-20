/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel;

import org.eclipse.emf.common.util.EList;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeAssignment;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.BooleanExpression;
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
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode#getAttributeAssignments <em>Attribute Assignments</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode#getReferencedByConditions <em>Referenced By Conditions</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGNode()
 * @model
 * @generated
 */
public interface TGGNode extends IBeXNode, TGGRuleElement {
	/**
	 * Returns the value of the '<em><b>Incoming Correspondence</b></em>' reference list.
	 * The list contents are of type {@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGCorrespondence}.
	 * It is bidirectional and its opposite is '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGCorrespondence#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Incoming Correspondence</em>' reference list.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGNode_IncomingCorrespondence()
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGCorrespondence#getTarget
	 * @model opposite="target"
	 * @generated
	 */
	EList<TGGCorrespondence> getIncomingCorrespondence();

	/**
	 * Returns the value of the '<em><b>Outgoing Correspondence</b></em>' reference list.
	 * The list contents are of type {@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGCorrespondence}.
	 * It is bidirectional and its opposite is '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGCorrespondence#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outgoing Correspondence</em>' reference list.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGNode_OutgoingCorrespondence()
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGCorrespondence#getSource
	 * @model opposite="source"
	 * @generated
	 */
	EList<TGGCorrespondence> getOutgoingCorrespondence();

	/**
	 * Returns the value of the '<em><b>Attribute Assignments</b></em>' reference list.
	 * The list contents are of type {@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeAssignment}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Assignments</em>' reference list.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGNode_AttributeAssignments()
	 * @model
	 * @generated
	 */
	EList<IBeXAttributeAssignment> getAttributeAssignments();

	/**
	 * Returns the value of the '<em><b>Referenced By Conditions</b></em>' reference list.
	 * The list contents are of type {@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.BooleanExpression}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Referenced By Conditions</em>' reference list.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGNode_ReferencedByConditions()
	 * @model
	 * @generated
	 */
	EList<BooleanExpression> getReferencedByConditions();

} // TGGNode
