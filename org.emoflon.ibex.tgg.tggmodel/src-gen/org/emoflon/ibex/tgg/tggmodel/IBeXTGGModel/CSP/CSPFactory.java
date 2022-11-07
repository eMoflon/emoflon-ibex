/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.CSPPackage
 * @generated
 */
public interface CSPFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CSPFactory eINSTANCE = org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.CSPFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>TGG Attribute Constraint Definition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Attribute Constraint Definition</em>'.
	 * @generated
	 */
	TGGAttributeConstraintDefinition createTGGAttributeConstraintDefinition();

	/**
	 * Returns a new object of class '<em>TGG Attribute Constraint Definition Library</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Attribute Constraint Definition Library</em>'.
	 * @generated
	 */
	TGGAttributeConstraintDefinitionLibrary createTGGAttributeConstraintDefinitionLibrary();

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
	 * Returns a new object of class '<em>TGG Attribute Constraint Set</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Attribute Constraint Set</em>'.
	 * @generated
	 */
	TGGAttributeConstraintSet createTGGAttributeConstraintSet();

	/**
	 * Returns a new object of class '<em>TGG Attribute Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Attribute Constraint</em>'.
	 * @generated
	 */
	TGGAttributeConstraint createTGGAttributeConstraint();

	/**
	 * Returns a new object of class '<em>TGG Attribute Constraint Variable</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Attribute Constraint Variable</em>'.
	 * @generated
	 */
	TGGAttributeConstraintVariable createTGGAttributeConstraintVariable();

	/**
	 * Returns a new object of class '<em>TGG Attribute Constraint Parameter Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Attribute Constraint Parameter Value</em>'.
	 * @generated
	 */
	TGGAttributeConstraintParameterValue createTGGAttributeConstraintParameterValue();

	/**
	 * Returns a new object of class '<em>TGGCSP</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGGCSP</em>'.
	 * @generated
	 */
	TGGCSP createTGGCSP();

	/**
	 * Returns a new object of class '<em>TGG Local Variable</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Local Variable</em>'.
	 * @generated
	 */
	TGGLocalVariable createTGGLocalVariable();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	CSPPackage getCSPPackage();

} //CSPFactory
