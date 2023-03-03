/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel;

import org.eclipse.emf.common.util.EList;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRule;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintSet;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TGG Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getNodes <em>Nodes</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getEdges <em>Edges</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getCorrespondenceNodes <em>Correspondence Nodes</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getOperationalisations <em>Operationalisations</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getAttributeConstraints <em>Attribute Constraints</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#isAbstract <em>Abstract</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGRule()
 * @model
 * @generated
 */
public interface TGGRule extends IBeXRule {
	/**
	 * Returns the value of the '<em><b>Nodes</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nodes</em>' containment reference list.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGRule_Nodes()
	 * @model containment="true"
	 * @generated
	 */
	EList<TGGNode> getNodes();

	/**
	 * Returns the value of the '<em><b>Edges</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGEdge}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Edges</em>' containment reference list.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGRule_Edges()
	 * @model containment="true"
	 * @generated
	 */
	EList<TGGEdge> getEdges();

	/**
	 * Returns the value of the '<em><b>Correspondence Nodes</b></em>' reference list.
	 * The list contents are of type {@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGCorrespondence}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Correspondence Nodes</em>' reference list.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGRule_CorrespondenceNodes()
	 * @model
	 * @generated
	 */
	EList<TGGCorrespondence> getCorrespondenceNodes();

	/**
	 * Returns the value of the '<em><b>Operationalisations</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operationalisations</em>' containment reference list.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGRule_Operationalisations()
	 * @model containment="true"
	 * @generated
	 */
	EList<TGGOperationalRule> getOperationalisations();

	/**
	 * Returns the value of the '<em><b>Attribute Constraints</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Constraints</em>' containment reference.
	 * @see #setAttributeConstraints(TGGAttributeConstraintSet)
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGRule_AttributeConstraints()
	 * @model containment="true"
	 * @generated
	 */
	TGGAttributeConstraintSet getAttributeConstraints();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getAttributeConstraints <em>Attribute Constraints</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute Constraints</em>' containment reference.
	 * @see #getAttributeConstraints()
	 * @generated
	 */
	void setAttributeConstraints(TGGAttributeConstraintSet value);

	/**
	 * Returns the value of the '<em><b>Abstract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Abstract</em>' attribute.
	 * @see #setAbstract(boolean)
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGRule_Abstract()
	 * @model
	 * @generated
	 */
	boolean isAbstract();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#isAbstract <em>Abstract</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Abstract</em>' attribute.
	 * @see #isAbstract()
	 * @generated
	 */
	void setAbstract(boolean value);

} // TGGRule
