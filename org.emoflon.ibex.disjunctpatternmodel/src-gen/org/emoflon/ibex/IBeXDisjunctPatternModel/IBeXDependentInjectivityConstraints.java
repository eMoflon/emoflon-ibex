/**
 */
package org.emoflon.ibex.IBeXDisjunctPatternModel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XDependent Injectivity Constraints</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDependentInjectivityConstraints#getInjectivityConstraints <em>Injectivity Constraints</em>}</li>
 *   <li>{@link org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDependentInjectivityConstraints#getPatterns <em>Patterns</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctPatternModelPackage#getIBeXDependentInjectivityConstraints()
 * @model
 * @generated
 */
public interface IBeXDependentInjectivityConstraints extends EObject {
	/**
	 * Returns the value of the '<em><b>Patterns</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Patterns</em>' attribute list.
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctPatternModelPackage#getIBeXDependentInjectivityConstraints_Patterns()
	 * @model
	 * @generated
	 */
	EList<String> getPatterns();

	/**
	 * Returns the value of the '<em><b>Injectivity Constraints</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.IBeXDisjunctPatternModel.IBexDisjunctInjectivityConstraint}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Injectivity Constraints</em>' containment reference list.
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctPatternModelPackage#getIBeXDependentInjectivityConstraints_InjectivityConstraints()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBexDisjunctInjectivityConstraint> getInjectivityConstraints();

} // IBeXDependentInjectivityConstraints
