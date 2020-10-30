/**
 */
package org.emoflon.ibex.patternmodel.IBeXPatternModel;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XContext Transitive</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextTransitive#getBasePattern <em>Base Pattern</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextTransitive#getSubPatterns <em>Sub Patterns</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextTransitive#getTransitiveEdges <em>Transitive Edges</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXContextTransitive()
 * @model
 * @generated
 */
public interface IBeXContextTransitive extends IBeXContext {
	/**
	 * Returns the value of the '<em><b>Base Pattern</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Base Pattern</em>' containment reference.
	 * @see #setBasePattern(IBeXContextPattern)
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXContextTransitive_BasePattern()
	 * @model containment="true" required="true"
	 * @generated
	 */
	IBeXContextPattern getBasePattern();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextTransitive#getBasePattern <em>Base Pattern</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Base Pattern</em>' containment reference.
	 * @see #getBasePattern()
	 * @generated
	 */
	void setBasePattern(IBeXContextPattern value);

	/**
	 * Returns the value of the '<em><b>Sub Patterns</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sub Patterns</em>' containment reference list.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXContextTransitive_SubPatterns()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBeXContextPattern> getSubPatterns();

	/**
	 * Returns the value of the '<em><b>Transitive Edges</b></em>' reference list.
	 * The list contents are of type {@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXTransitiveEdge}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transitive Edges</em>' reference list.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXContextTransitive_TransitiveEdges()
	 * @model
	 * @generated
	 */
	EList<IBeXTransitiveEdge> getTransitiveEdges();

} // IBeXContextTransitive
