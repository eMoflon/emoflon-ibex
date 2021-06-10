/**
 */
package org.emoflon.ibex.patternmodel.IBeXPatternModel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBex Disjoint Injectivity Constraint</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBexDisjointInjectivityConstraint#getPattern1 <em>Pattern1</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBexDisjointInjectivityConstraint#getPattern2 <em>Pattern2</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBexDisjointInjectivityConstraint#getNode1 <em>Node1</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBexDisjointInjectivityConstraint#getNode2 <em>Node2</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBexDisjointInjectivityConstraint()
 * @model
 * @generated
 */
public interface IBexDisjointInjectivityConstraint extends EObject {
	/**
	 * Returns the value of the '<em><b>Pattern1</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pattern1</em>' reference.
	 * @see #setPattern1(IBeXContextPattern)
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBexDisjointInjectivityConstraint_Pattern1()
	 * @model
	 * @generated
	 */
	IBeXContextPattern getPattern1();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBexDisjointInjectivityConstraint#getPattern1 <em>Pattern1</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pattern1</em>' reference.
	 * @see #getPattern1()
	 * @generated
	 */
	void setPattern1(IBeXContextPattern value);

	/**
	 * Returns the value of the '<em><b>Pattern2</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pattern2</em>' reference.
	 * @see #setPattern2(IBeXContextPattern)
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBexDisjointInjectivityConstraint_Pattern2()
	 * @model
	 * @generated
	 */
	IBeXContextPattern getPattern2();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBexDisjointInjectivityConstraint#getPattern2 <em>Pattern2</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pattern2</em>' reference.
	 * @see #getPattern2()
	 * @generated
	 */
	void setPattern2(IBeXContextPattern value);

	/**
	 * Returns the value of the '<em><b>Node1</b></em>' reference list.
	 * The list contents are of type {@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Node1</em>' reference list.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBexDisjointInjectivityConstraint_Node1()
	 * @model
	 * @generated
	 */
	EList<IBeXNode> getNode1();

	/**
	 * Returns the value of the '<em><b>Node2</b></em>' reference list.
	 * The list contents are of type {@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Node2</em>' reference list.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBexDisjointInjectivityConstraint_Node2()
	 * @model
	 * @generated
	 */
	EList<IBeXNode> getNode2();

} // IBexDisjointInjectivityConstraint
