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
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternSet#getContextPatterns <em>Context Patterns</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternSet#getExtendedContextPatterns <em>Extended Context Patterns</em>}</li>
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
	 * Returns the value of the '<em><b>Extended Context Patterns</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extended Context Patterns</em>' reference.
	 * @see #setExtendedContextPatterns(IBeXContext)
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXPatternSet_ExtendedContextPatterns()
	 * @model
	 * @generated
	 */
	IBeXContext getExtendedContextPatterns();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternSet#getExtendedContextPatterns <em>Extended Context Patterns</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extended Context Patterns</em>' reference.
	 * @see #getExtendedContextPatterns()
	 * @generated
	 */
	void setExtendedContextPatterns(IBeXContext value);

} // IBeXPatternSet
