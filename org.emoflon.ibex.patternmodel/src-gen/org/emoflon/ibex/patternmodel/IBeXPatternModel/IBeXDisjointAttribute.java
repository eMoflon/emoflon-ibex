/**
 */
package org.emoflon.ibex.patternmodel.IBeXPatternModel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XDisjoint Attribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjointAttribute#getTargetPattern <em>Target Pattern</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjointAttribute#getSourcePattern <em>Source Pattern</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjointAttribute#getDisjointAttribute <em>Disjoint Attribute</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXDisjointAttribute()
 * @model
 * @generated
 */
public interface IBeXDisjointAttribute extends EObject {
	/**
	 * Returns the value of the '<em><b>Target Pattern</b></em>' reference list.
	 * The list contents are of type {@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Pattern</em>' reference list.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXDisjointAttribute_TargetPattern()
	 * @model
	 * @generated
	 */
	EList<IBeXContextPattern> getTargetPattern();

	/**
	 * Returns the value of the '<em><b>Source Pattern</b></em>' reference list.
	 * The list contents are of type {@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Pattern</em>' reference list.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXDisjointAttribute_SourcePattern()
	 * @model
	 * @generated
	 */
	EList<IBeXContextPattern> getSourcePattern();

	/**
	 * Returns the value of the '<em><b>Disjoint Attribute</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXConstraint}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Disjoint Attribute</em>' containment reference list.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXDisjointAttribute_DisjointAttribute()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBeXConstraint> getDisjointAttribute();

} // IBeXDisjointAttribute
