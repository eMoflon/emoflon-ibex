/**
 */
package org.emoflon.ibex.patternmodel.IBeXPatternModel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XPattern Set</b></em>'.
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
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternSet#getContextPatterns <em>Context Patterns</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternSet#getCreatePatterns <em>Create Patterns</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternSet#getDeletePatterns <em>Delete Patterns</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXPatternSet()
 * @model
 * @generated
 */
public interface IBeXPatternSet extends EObject {
	/**
	 * Returns the value of the '<em><b>Context Patterns</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Context Patterns</em>' containment reference list.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXPatternSet_ContextPatterns()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBeXContext> getContextPatterns();

	/**
	 * Returns the value of the '<em><b>Create Patterns</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCreatePattern}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Create Patterns</em>' containment reference list.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXPatternSet_CreatePatterns()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBeXCreatePattern> getCreatePatterns();

	/**
	 * Returns the value of the '<em><b>Delete Patterns</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDeletePattern}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Delete Patterns</em>' containment reference list.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXPatternSet_DeletePatterns()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBeXDeletePattern> getDeletePatterns();

} // IBeXPatternSet
