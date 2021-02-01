/**
 */
package org.emoflon.ibex.patternmodel.IBeXPatternModel;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XDisjunct Context Pattern</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjunctContextPattern#getSubpatterns <em>Subpatterns</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjunctContextPattern#getInjectivityConstraints <em>Injectivity Constraints</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjunctContextPattern#getAttributesConstraints <em>Attributes Constraints</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjunctContextPattern#getNonOptimizedPattern <em>Non Optimized Pattern</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXDisjunctContextPattern()
 * @model
 * @generated
 */
public interface IBeXDisjunctContextPattern extends IBeXContext {
	/**
	 * Returns the value of the '<em><b>Subpatterns</b></em>' reference list.
	 * The list contents are of type {@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subpatterns</em>' reference list.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXDisjunctContextPattern_Subpatterns()
	 * @model
	 * @generated
	 */
	EList<IBeXContextPattern> getSubpatterns();

	/**
	 * Returns the value of the '<em><b>Injectivity Constraints</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDependentInjectivityConstraints}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Injectivity Constraints</em>' containment reference list.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXDisjunctContextPattern_InjectivityConstraints()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBeXDependentInjectivityConstraints> getInjectivityConstraints();

	/**
	 * Returns the value of the '<em><b>Attributes Constraints</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDependentDisjunctAttribute}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attributes Constraints</em>' containment reference list.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXDisjunctContextPattern_AttributesConstraints()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBeXDependentDisjunctAttribute> getAttributesConstraints();

	/**
	 * Returns the value of the '<em><b>Non Optimized Pattern</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Non Optimized Pattern</em>' containment reference.
	 * @see #setNonOptimizedPattern(IBeXContextPattern)
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXDisjunctContextPattern_NonOptimizedPattern()
	 * @model containment="true"
	 * @generated
	 */
	IBeXContextPattern getNonOptimizedPattern();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjunctContextPattern#getNonOptimizedPattern <em>Non Optimized Pattern</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Non Optimized Pattern</em>' containment reference.
	 * @see #getNonOptimizedPattern()
	 * @generated
	 */
	void setNonOptimizedPattern(IBeXContextPattern value);

} // IBeXDisjunctContextPattern
