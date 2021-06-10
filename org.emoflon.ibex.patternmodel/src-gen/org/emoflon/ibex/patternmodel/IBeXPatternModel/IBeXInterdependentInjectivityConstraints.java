/**
 */
package org.emoflon.ibex.patternmodel.IBeXPatternModel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XInterdependent Injectivity Constraints</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXInterdependentInjectivityConstraints#getInjectivityConstraints <em>Injectivity Constraints</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXInterdependentInjectivityConstraints#getPatterns <em>Patterns</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXInterdependentInjectivityConstraints#getAttributeConstraints <em>Attribute Constraints</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXInterdependentInjectivityConstraints()
 * @model
 * @generated
 */
public interface IBeXInterdependentInjectivityConstraints extends EObject {
	/**
	 * Returns the value of the '<em><b>Injectivity Constraints</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBexDisjointInjectivityConstraint}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Injectivity Constraints</em>' containment reference list.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXInterdependentInjectivityConstraints_InjectivityConstraints()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBexDisjointInjectivityConstraint> getInjectivityConstraints();

	/**
	 * Returns the value of the '<em><b>Patterns</b></em>' reference list.
	 * The list contents are of type {@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Patterns</em>' reference list.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXInterdependentInjectivityConstraints_Patterns()
	 * @model
	 * @generated
	 */
	EList<IBeXContextPattern> getPatterns();

	/**
	 * Returns the value of the '<em><b>Attribute Constraints</b></em>' reference list.
	 * The list contents are of type {@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXInterdependentAttributes}.
	 * It is bidirectional and its opposite is '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXInterdependentAttributes#getInjectivityConstraints <em>Injectivity Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Constraints</em>' reference list.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXInterdependentInjectivityConstraints_AttributeConstraints()
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXInterdependentAttributes#getInjectivityConstraints
	 * @model opposite="injectivityConstraints"
	 * @generated
	 */
	EList<IBeXInterdependentAttributes> getAttributeConstraints();

} // IBeXInterdependentInjectivityConstraints
