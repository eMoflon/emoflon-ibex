/**
 */
package org.emoflon.ibex.patternmodel.IBeXPatternModel;

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
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDependentInjectivityConstraints#getInjectivityConstraints <em>Injectivity Constraints</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDependentInjectivityConstraints#getPatterns <em>Patterns</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDependentInjectivityConstraints#getAttributeConstraints <em>Attribute Constraints</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXDependentInjectivityConstraints()
 * @model
 * @generated
 */
public interface IBeXDependentInjectivityConstraints extends EObject {
	/**
	 * Returns the value of the '<em><b>Injectivity Constraints</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBexDisjunctInjectivityConstraint}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Injectivity Constraints</em>' containment reference list.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXDependentInjectivityConstraints_InjectivityConstraints()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBexDisjunctInjectivityConstraint> getInjectivityConstraints();

	/**
	 * Returns the value of the '<em><b>Patterns</b></em>' reference list.
	 * The list contents are of type {@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Patterns</em>' reference list.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXDependentInjectivityConstraints_Patterns()
	 * @model
	 * @generated
	 */
	EList<IBeXContextPattern> getPatterns();

	/**
	 * Returns the value of the '<em><b>Attribute Constraints</b></em>' reference list.
	 * The list contents are of type {@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDependentDisjunctAttribute}.
	 * It is bidirectional and its opposite is '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDependentDisjunctAttribute#getInjectivityConstraints <em>Injectivity Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Constraints</em>' reference list.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXDependentInjectivityConstraints_AttributeConstraints()
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDependentDisjunctAttribute#getInjectivityConstraints
	 * @model opposite="injectivityConstraints"
	 * @generated
	 */
	EList<IBeXDependentDisjunctAttribute> getAttributeConstraints();

} // IBeXDependentInjectivityConstraints
