/**
 */
package org.emoflon.ibex.gt.StateModel;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage
 * @generated
 */
public interface StateModelFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	StateModelFactory eINSTANCE = org.emoflon.ibex.gt.StateModel.impl.StateModelFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>State Container</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>State Container</em>'.
	 * @generated
	 */
	StateContainer createStateContainer();

	/**
	 * Returns a new object of class '<em>State</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>State</em>'.
	 * @generated
	 */
	State createState();

	/**
	 * Returns a new object of class '<em>Rule State</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Rule State</em>'.
	 * @generated
	 */
	RuleState createRuleState();

	/**
	 * Returns a new object of class '<em>Attribute Delta</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Attribute Delta</em>'.
	 * @generated
	 */
	AttributeDelta createAttributeDelta();

	/**
	 * Returns a new object of class '<em>Structural Delta</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Structural Delta</em>'.
	 * @generated
	 */
	StructuralDelta createStructuralDelta();

	/**
	 * Returns a new object of class '<em>Link</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Link</em>'.
	 * @generated
	 */
	Link createLink();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	StateModelPackage getStateModelPackage();

} //StateModelFactory
