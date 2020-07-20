/**
 */
package org.emoflon.ibex.IBeXDisjunctPatternModel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeConstraint;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XDisjunct Attribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctAttribute#getTargetPattern <em>Target Pattern</em>}</li>
 *   <li>{@link org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctAttribute#getDisjunctAttribute <em>Disjunct Attribute</em>}</li>
 *   <li>{@link org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctAttribute#getSourcePattern <em>Source Pattern</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctPatternModelPackage#getIBeXDisjunctAttribute()
 * @model
 * @generated
 */
public interface IBeXDisjunctAttribute extends EObject {
	/**
	 * Returns the value of the '<em><b>Target Pattern</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Pattern</em>' reference.
	 * @see #setTargetPattern(IBeXContextPattern)
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctPatternModelPackage#getIBeXDisjunctAttribute_TargetPattern()
	 * @model
	 * @generated
	 */
	IBeXContextPattern getTargetPattern();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctAttribute#getTargetPattern <em>Target Pattern</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Pattern</em>' reference.
	 * @see #getTargetPattern()
	 * @generated
	 */
	void setTargetPattern(IBeXContextPattern value);

	/**
	 * Returns the value of the '<em><b>Disjunct Attribute</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeConstraint}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Disjunct Attribute</em>' containment reference list.
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctPatternModelPackage#getIBeXDisjunctAttribute_DisjunctAttribute()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBeXAttributeConstraint> getDisjunctAttribute();

	/**
	 * Returns the value of the '<em><b>Source Pattern</b></em>' reference list.
	 * The list contents are of type {@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Pattern</em>' reference list.
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctPatternModelPackage#getIBeXDisjunctAttribute_SourcePattern()
	 * @model
	 * @generated
	 */
	EList<IBeXContextPattern> getSourcePattern();

} // IBeXDisjunctAttribute
