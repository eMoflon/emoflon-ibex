/**
 */
package org.emoflon.ibex.gt.gtmodel.IBeXGTModel;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelPackage
 * @generated
 */
public interface IBeXGTModelFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	IBeXGTModelFactory eINSTANCE = org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.IBeXGTModelFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>GT Model</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>GT Model</em>'.
	 * @generated
	 */
	GTModel createGTModel();

	/**
	 * Returns a new object of class '<em>GT Rule Set</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>GT Rule Set</em>'.
	 * @generated
	 */
	GTRuleSet createGTRuleSet();

	/**
	 * Returns a new object of class '<em>GT Pattern</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>GT Pattern</em>'.
	 * @generated
	 */
	GTPattern createGTPattern();

	/**
	 * Returns a new object of class '<em>GT Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>GT Rule</em>'.
	 * @generated
	 */
	GTRule createGTRule();

	/**
	 * Returns a new object of class '<em>GT For Each Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>GT For Each Expression</em>'.
	 * @generated
	 */
	GTForEachExpression createGTForEachExpression();

	/**
	 * Returns a new object of class '<em>GT Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>GT Parameter</em>'.
	 * @generated
	 */
	GTParameter createGTParameter();

	/**
	 * Returns a new object of class '<em>GT Parameter Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>GT Parameter Value</em>'.
	 * @generated
	 */
	GTParameterValue createGTParameterValue();

	/**
	 * Returns a new object of class '<em>GT Iterator Edge</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>GT Iterator Edge</em>'.
	 * @generated
	 */
	GTIteratorEdge createGTIteratorEdge();

	/**
	 * Returns a new object of class '<em>GT Iterator Attribute Assignment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>GT Iterator Attribute Assignment</em>'.
	 * @generated
	 */
	GTIteratorAttributeAssignment createGTIteratorAttributeAssignment();

	/**
	 * Returns a new object of class '<em>GT Iterator Attribute Reference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>GT Iterator Attribute Reference</em>'.
	 * @generated
	 */
	GTIteratorAttributeReference createGTIteratorAttributeReference();

	/**
	 * Returns a new object of class '<em>GT Watch Dog</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>GT Watch Dog</em>'.
	 * @generated
	 */
	GTWatchDog createGTWatchDog();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	IBeXGTModelPackage getIBeXGTModelPackage();

} //IBeXGTModelFactory
