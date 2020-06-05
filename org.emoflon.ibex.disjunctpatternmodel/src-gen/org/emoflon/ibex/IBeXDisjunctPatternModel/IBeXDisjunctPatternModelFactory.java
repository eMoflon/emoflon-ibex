/**
 */
package org.emoflon.ibex.IBeXDisjunctPatternModel;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctPatternModelPackage
 * @generated
 */
public interface IBeXDisjunctPatternModelFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	IBeXDisjunctPatternModelFactory eINSTANCE = org.emoflon.ibex.IBeXDisjunctPatternModel.impl.IBeXDisjunctPatternModelFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>IBe XDisjunct Context Pattern</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>IBe XDisjunct Context Pattern</em>'.
	 * @generated
	 */
	IBeXDisjunctContextPattern createIBeXDisjunctContextPattern();

	/**
	 * Returns a new object of class '<em>IBe XDisjunct Attributes</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>IBe XDisjunct Attributes</em>'.
	 * @generated
	 */
	IBeXDisjunctAttributes createIBeXDisjunctAttributes();

	/**
	 * Returns a new object of class '<em>IBe XDependent Injectivity Constraints</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>IBe XDependent Injectivity Constraints</em>'.
	 * @generated
	 */
	IBeXDependentInjectivityConstraints createIBeXDependentInjectivityConstraints();

	/**
	 * Returns a new object of class '<em>IBex Disjunct Injectivity Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>IBex Disjunct Injectivity Constraint</em>'.
	 * @generated
	 */
	IBexDisjunctInjectivityConstraint createIBexDisjunctInjectivityConstraint();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	IBeXDisjunctPatternModelPackage getIBeXDisjunctPatternModelPackage();

} //IBeXDisjunctPatternModelFactory
