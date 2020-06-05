/**
 */
package org.emoflon.ibex.IBeXDisjunctPatternModel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XDisjunct Context Pattern</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctContextPattern#getSubpatterns <em>Subpatterns</em>}</li>
 *   <li>{@link org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctContextPattern#getInjectivityConstraints <em>Injectivity Constraints</em>}</li>
 *   <li>{@link org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctContextPattern#getAttributesConstraints <em>Attributes Constraints</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctPatternModelPackage#getIBeXDisjunctContextPattern()
 * @model
 * @generated
 */
public interface IBeXDisjunctContextPattern extends EObject, IBeXContext {
	/**
	 * Returns the value of the '<em><b>Subpatterns</b></em>' reference list.
	 * The list contents are of type {@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subpatterns</em>' reference list.
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctPatternModelPackage#getIBeXDisjunctContextPattern_Subpatterns()
	 * @model
	 * @generated
	 */
	EList<IBeXContextPattern> getSubpatterns();

	/**
	 * Returns the value of the '<em><b>Injectivity Constraints</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDependentInjectivityConstraints}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Injectivity Constraints</em>' containment reference list.
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctPatternModelPackage#getIBeXDisjunctContextPattern_InjectivityConstraints()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBeXDependentInjectivityConstraints> getInjectivityConstraints();

	/**
	 * Returns the value of the '<em><b>Attributes Constraints</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctAttributes}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attributes Constraints</em>' containment reference list.
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctPatternModelPackage#getIBeXDisjunctContextPattern_AttributesConstraints()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBeXDisjunctAttributes> getAttributesConstraints();

} // IBeXDisjunctContextPattern
