/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel;

import org.eclipse.emf.common.util.EList;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRule;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRuleDelta;
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
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getContext <em>Context</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getContextSource <em>Context Source</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getContextTarget <em>Context Target</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getCreate <em>Create</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getCreateSource <em>Create Source</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getCreateCorrespondence <em>Create Correspondence</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getCreateTarget <em>Create Target</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#isAxiom <em>Axiom</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getSource <em>Source</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getCorrespondence <em>Correspondence</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getTarget <em>Target</em>}</li>
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

	/**
	 * Returns the value of the '<em><b>Context</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Context</em>' containment reference.
	 * @see #setContext(IBeXRuleDelta)
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGRule_Context()
	 * @model containment="true"
	 * @generated
	 */
	IBeXRuleDelta getContext();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getContext <em>Context</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Context</em>' containment reference.
	 * @see #getContext()
	 * @generated
	 */
	void setContext(IBeXRuleDelta value);

	/**
	 * Returns the value of the '<em><b>Context Source</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Context Source</em>' containment reference.
	 * @see #setContextSource(IBeXRuleDelta)
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGRule_ContextSource()
	 * @model containment="true"
	 * @generated
	 */
	IBeXRuleDelta getContextSource();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getContextSource <em>Context Source</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Context Source</em>' containment reference.
	 * @see #getContextSource()
	 * @generated
	 */
	void setContextSource(IBeXRuleDelta value);

	/**
	 * Returns the value of the '<em><b>Context Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Context Target</em>' containment reference.
	 * @see #setContextTarget(IBeXRuleDelta)
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGRule_ContextTarget()
	 * @model containment="true"
	 * @generated
	 */
	IBeXRuleDelta getContextTarget();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getContextTarget <em>Context Target</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Context Target</em>' containment reference.
	 * @see #getContextTarget()
	 * @generated
	 */
	void setContextTarget(IBeXRuleDelta value);

	/**
	 * Returns the value of the '<em><b>Create</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Create</em>' containment reference.
	 * @see #setCreate(IBeXRuleDelta)
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGRule_Create()
	 * @model containment="true"
	 * @generated
	 */
	IBeXRuleDelta getCreate();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getCreate <em>Create</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Create</em>' containment reference.
	 * @see #getCreate()
	 * @generated
	 */
	void setCreate(IBeXRuleDelta value);

	/**
	 * Returns the value of the '<em><b>Create Source</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Create Source</em>' containment reference.
	 * @see #setCreateSource(IBeXRuleDelta)
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGRule_CreateSource()
	 * @model containment="true"
	 * @generated
	 */
	IBeXRuleDelta getCreateSource();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getCreateSource <em>Create Source</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Create Source</em>' containment reference.
	 * @see #getCreateSource()
	 * @generated
	 */
	void setCreateSource(IBeXRuleDelta value);

	/**
	 * Returns the value of the '<em><b>Create Correspondence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Create Correspondence</em>' containment reference.
	 * @see #setCreateCorrespondence(IBeXRuleDelta)
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGRule_CreateCorrespondence()
	 * @model containment="true"
	 * @generated
	 */
	IBeXRuleDelta getCreateCorrespondence();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getCreateCorrespondence <em>Create Correspondence</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Create Correspondence</em>' containment reference.
	 * @see #getCreateCorrespondence()
	 * @generated
	 */
	void setCreateCorrespondence(IBeXRuleDelta value);

	/**
	 * Returns the value of the '<em><b>Create Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Create Target</em>' containment reference.
	 * @see #setCreateTarget(IBeXRuleDelta)
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGRule_CreateTarget()
	 * @model containment="true"
	 * @generated
	 */
	IBeXRuleDelta getCreateTarget();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getCreateTarget <em>Create Target</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Create Target</em>' containment reference.
	 * @see #getCreateTarget()
	 * @generated
	 */
	void setCreateTarget(IBeXRuleDelta value);

	/**
	 * Returns the value of the '<em><b>Axiom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Axiom</em>' attribute.
	 * @see #setAxiom(boolean)
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGRule_Axiom()
	 * @model
	 * @generated
	 */
	boolean isAxiom();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#isAxiom <em>Axiom</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Axiom</em>' attribute.
	 * @see #isAxiom()
	 * @generated
	 */
	void setAxiom(boolean value);

	/**
	 * Returns the value of the '<em><b>Source</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' containment reference.
	 * @see #setSource(IBeXRuleDelta)
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGRule_Source()
	 * @model containment="true"
	 * @generated
	 */
	IBeXRuleDelta getSource();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getSource <em>Source</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' containment reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(IBeXRuleDelta value);

	/**
	 * Returns the value of the '<em><b>Correspondence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Correspondence</em>' containment reference.
	 * @see #setCorrespondence(IBeXRuleDelta)
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGRule_Correspondence()
	 * @model containment="true"
	 * @generated
	 */
	IBeXRuleDelta getCorrespondence();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getCorrespondence <em>Correspondence</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Correspondence</em>' containment reference.
	 * @see #getCorrespondence()
	 * @generated
	 */
	void setCorrespondence(IBeXRuleDelta value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' containment reference.
	 * @see #setTarget(IBeXRuleDelta)
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGRule_Target()
	 * @model containment="true"
	 * @generated
	 */
	IBeXRuleDelta getTarget();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getTarget <em>Target</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' containment reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(IBeXRuleDelta value);

} // TGGRule
