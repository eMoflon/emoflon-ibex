/**
 */
package org.emoflon.ibex.IBeXDisjunctPatternModel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XDependent Disjunct Attribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDependentDisjunctAttribute#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDependentDisjunctAttribute#getDependentPatterns <em>Dependent Patterns</em>}</li>
 *   <li>{@link org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDependentDisjunctAttribute#getInjectivityConstraints <em>Injectivity Constraints</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctPatternModelPackage#getIBeXDependentDisjunctAttribute()
 * @model
 * @generated
 */
public interface IBeXDependentDisjunctAttribute extends EObject {
	/**
	 * Returns the value of the '<em><b>Attributes</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctAttribute}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attributes</em>' containment reference list.
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctPatternModelPackage#getIBeXDependentDisjunctAttribute_Attributes()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBeXDisjunctAttribute> getAttributes();

	/**
	 * Returns the value of the '<em><b>Dependent Patterns</b></em>' reference list.
	 * The list contents are of type {@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dependent Patterns</em>' reference list.
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctPatternModelPackage#getIBeXDependentDisjunctAttribute_DependentPatterns()
	 * @model
	 * @generated
	 */
	EList<IBeXContextPattern> getDependentPatterns();

	/**
	 * Returns the value of the '<em><b>Injectivity Constraints</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDependentInjectivityConstraints#getAttributeConstraints <em>Attribute Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Injectivity Constraints</em>' reference.
	 * @see #setInjectivityConstraints(IBeXDependentInjectivityConstraints)
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctPatternModelPackage#getIBeXDependentDisjunctAttribute_InjectivityConstraints()
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDependentInjectivityConstraints#getAttributeConstraints
	 * @model opposite="attributeConstraints"
	 * @generated
	 */
	IBeXDependentInjectivityConstraints getInjectivityConstraints();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDependentDisjunctAttribute#getInjectivityConstraints <em>Injectivity Constraints</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Injectivity Constraints</em>' reference.
	 * @see #getInjectivityConstraints()
	 * @generated
	 */
	void setInjectivityConstraints(IBeXDependentInjectivityConstraints value);

} // IBeXDependentDisjunctAttribute
