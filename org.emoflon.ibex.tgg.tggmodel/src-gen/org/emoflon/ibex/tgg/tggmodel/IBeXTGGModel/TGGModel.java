/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EPackage;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModel;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintDefinitionLibrary;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TGG Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel#getSource <em>Source</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel#getTarget <em>Target</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel#getCorrespondence <em>Correspondence</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel#getRuleSet <em>Rule Set</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel#getAttributeConstraintDefinitionLibraries <em>Attribute Constraint Definition Libraries</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGModel()
 * @model
 * @generated
 */
public interface TGGModel extends IBeXModel {
	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EPackage}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference list.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGModel_Source()
	 * @model
	 * @generated
	 */
	EList<EPackage> getSource();

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EPackage}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference list.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGModel_Target()
	 * @model
	 * @generated
	 */
	EList<EPackage> getTarget();

	/**
	 * Returns the value of the '<em><b>Correspondence</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Correspondence</em>' reference.
	 * @see #setCorrespondence(EPackage)
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGModel_Correspondence()
	 * @model
	 * @generated
	 */
	EPackage getCorrespondence();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel#getCorrespondence <em>Correspondence</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Correspondence</em>' reference.
	 * @see #getCorrespondence()
	 * @generated
	 */
	void setCorrespondence(EPackage value);

	/**
	 * Returns the value of the '<em><b>Rule Set</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule Set</em>' containment reference.
	 * @see #setRuleSet(TGGRuleSet)
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGModel_RuleSet()
	 * @model containment="true" required="true"
	 * @generated
	 */
	TGGRuleSet getRuleSet();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel#getRuleSet <em>Rule Set</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule Set</em>' containment reference.
	 * @see #getRuleSet()
	 * @generated
	 */
	void setRuleSet(TGGRuleSet value);

	/**
	 * Returns the value of the '<em><b>Attribute Constraint Definition Libraries</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintDefinitionLibrary}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Constraint Definition Libraries</em>' containment reference list.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGModel_AttributeConstraintDefinitionLibraries()
	 * @model containment="true"
	 * @generated
	 */
	EList<TGGAttributeConstraintDefinitionLibrary> getAttributeConstraintDefinitionLibraries();

} // TGGModel
