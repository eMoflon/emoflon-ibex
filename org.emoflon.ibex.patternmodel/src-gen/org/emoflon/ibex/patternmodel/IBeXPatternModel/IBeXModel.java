/**
 */
package org.emoflon.ibex.patternmodel.IBeXPatternModel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XModel</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A set of IBeXPatterns. Each pattern in a set must have an unique name.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXModel#getPatternSet <em>Pattern Set</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXModel#getRuleSet <em>Rule Set</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXModel#getNodeSet <em>Node Set</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXModel#getEdgeSet <em>Edge Set</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXModel()
 * @model
 * @generated
 */
public interface IBeXModel extends EObject {
	/**
	 * Returns the value of the '<em><b>Pattern Set</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pattern Set</em>' containment reference.
	 * @see #setPatternSet(IBeXPatternSet)
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXModel_PatternSet()
	 * @model containment="true" required="true"
	 * @generated
	 */
	IBeXPatternSet getPatternSet();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXModel#getPatternSet <em>Pattern Set</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pattern Set</em>' containment reference.
	 * @see #getPatternSet()
	 * @generated
	 */
	void setPatternSet(IBeXPatternSet value);

	/**
	 * Returns the value of the '<em><b>Rule Set</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule Set</em>' containment reference.
	 * @see #setRuleSet(IBeXRuleSet)
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXModel_RuleSet()
	 * @model containment="true" required="true"
	 * @generated
	 */
	IBeXRuleSet getRuleSet();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXModel#getRuleSet <em>Rule Set</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule Set</em>' containment reference.
	 * @see #getRuleSet()
	 * @generated
	 */
	void setRuleSet(IBeXRuleSet value);

	/**
	 * Returns the value of the '<em><b>Node Set</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Node Set</em>' containment reference.
	 * @see #setNodeSet(IBeXNodeSet)
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXModel_NodeSet()
	 * @model containment="true" required="true"
	 * @generated
	 */
	IBeXNodeSet getNodeSet();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXModel#getNodeSet <em>Node Set</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Node Set</em>' containment reference.
	 * @see #getNodeSet()
	 * @generated
	 */
	void setNodeSet(IBeXNodeSet value);

	/**
	 * Returns the value of the '<em><b>Edge Set</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Edge Set</em>' containment reference.
	 * @see #setEdgeSet(IBeXEdgeSet)
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXModel_EdgeSet()
	 * @model containment="true" required="true"
	 * @generated
	 */
	IBeXEdgeSet getEdgeSet();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXModel#getEdgeSet <em>Edge Set</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Edge Set</em>' containment reference.
	 * @see #getEdgeSet()
	 * @generated
	 */
	void setEdgeSet(IBeXEdgeSet value);

} // IBeXModel
