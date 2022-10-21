/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage
 * @generated
 */
public interface IBeXTGGModelFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	IBeXTGGModelFactory eINSTANCE = org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>TGG Model</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Model</em>'.
	 * @generated
	 */
	TGGModel createTGGModel();

	/**
	 * Returns a new object of class '<em>TGG Rule Set</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Rule Set</em>'.
	 * @generated
	 */
	TGGRuleSet createTGGRuleSet();

	/**
	 * Returns a new object of class '<em>TGG Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Rule</em>'.
	 * @generated
	 */
	TGGRule createTGGRule();

	/**
	 * Returns a new object of class '<em>Operational TGG Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Operational TGG Rule</em>'.
	 * @generated
	 */
	OperationalTGGRule createOperationalTGGRule();

	/**
	 * Returns a new object of class '<em>TGG Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Node</em>'.
	 * @generated
	 */
	TGGNode createTGGNode();

	/**
	 * Returns a new object of class '<em>TGG Correspondence</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Correspondence</em>'.
	 * @generated
	 */
	TGGCorrespondence createTGGCorrespondence();

	/**
	 * Returns a new object of class '<em>TGG Edge</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Edge</em>'.
	 * @generated
	 */
	TGGEdge createTGGEdge();

	/**
	 * Returns a new object of class '<em>TGG Attribute Constraint Set</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Attribute Constraint Set</em>'.
	 * @generated
	 */
	TGGAttributeConstraintSet createTGGAttributeConstraintSet();

	/**
	 * Returns a new object of class '<em>TGG Attribute Constraint Definition Library</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Attribute Constraint Definition Library</em>'.
	 * @generated
	 */
	TGGAttributeConstraintDefinitionLibrary createTGGAttributeConstraintDefinitionLibrary();

	/**
	 * Returns a new object of class '<em>TGG Attribute Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Attribute Constraint</em>'.
	 * @generated
	 */
	TGGAttributeConstraint createTGGAttributeConstraint();

	/**
	 * Returns a new object of class '<em>TGG Parameter Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Parameter Value</em>'.
	 * @generated
	 */
	TGGParameterValue createTGGParameterValue();

	/**
	 * Returns a new object of class '<em>TGG Attribute Constraint Parameter Definition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Attribute Constraint Parameter Definition</em>'.
	 * @generated
	 */
	TGGAttributeConstraintParameterDefinition createTGGAttributeConstraintParameterDefinition();

	/**
	 * Returns a new object of class '<em>TGG Attribute Constraint Binding</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Attribute Constraint Binding</em>'.
	 * @generated
	 */
	TGGAttributeConstraintBinding createTGGAttributeConstraintBinding();

	/**
	 * Returns a new object of class '<em>TGG Attribute Constraint Definition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Attribute Constraint Definition</em>'.
	 * @generated
	 */
	TGGAttributeConstraintDefinition createTGGAttributeConstraintDefinition();

	/**
	 * Returns a new object of class '<em>TGGCSP</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGGCSP</em>'.
	 * @generated
	 */
	TGGCSP createTGGCSP();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	IBeXTGGModelPackage getIBeXTGGModelPackage();

} //IBeXTGGModelFactory
