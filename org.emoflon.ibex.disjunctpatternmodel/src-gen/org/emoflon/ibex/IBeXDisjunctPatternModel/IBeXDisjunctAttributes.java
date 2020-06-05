/**
 */
package org.emoflon.ibex.IBeXDisjunctPatternModel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeConstraint;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XDisjunct Attributes</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctAttributes#getSourcePattern <em>Source Pattern</em>}</li>
 *   <li>{@link org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctAttributes#getTargetPattern <em>Target Pattern</em>}</li>
 *   <li>{@link org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctAttributes#getDisjunctAttributes <em>Disjunct Attributes</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctPatternModelPackage#getIBeXDisjunctAttributes()
 * @model
 * @generated
 */
public interface IBeXDisjunctAttributes extends EObject {
	/**
	 * Returns the value of the '<em><b>Source Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Pattern</em>' attribute.
	 * @see #setSourcePattern(String)
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctPatternModelPackage#getIBeXDisjunctAttributes_SourcePattern()
	 * @model
	 * @generated
	 */
	String getSourcePattern();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctAttributes#getSourcePattern <em>Source Pattern</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Pattern</em>' attribute.
	 * @see #getSourcePattern()
	 * @generated
	 */
	void setSourcePattern(String value);

	/**
	 * Returns the value of the '<em><b>Target Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Pattern</em>' attribute.
	 * @see #setTargetPattern(String)
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctPatternModelPackage#getIBeXDisjunctAttributes_TargetPattern()
	 * @model
	 * @generated
	 */
	String getTargetPattern();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctAttributes#getTargetPattern <em>Target Pattern</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Pattern</em>' attribute.
	 * @see #getTargetPattern()
	 * @generated
	 */
	void setTargetPattern(String value);

	/**
	 * Returns the value of the '<em><b>Disjunct Attributes</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeConstraint}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Disjunct Attributes</em>' containment reference list.
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctPatternModelPackage#getIBeXDisjunctAttributes_DisjunctAttributes()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBeXAttributeConstraint> getDisjunctAttributes();

} // IBeXDisjunctAttributes
