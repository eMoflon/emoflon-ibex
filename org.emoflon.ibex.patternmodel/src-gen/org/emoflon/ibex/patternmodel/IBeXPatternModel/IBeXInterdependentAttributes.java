/**
 */
package org.emoflon.ibex.patternmodel.IBeXPatternModel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XInterdependent Attributes</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXInterdependentAttributes#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXInterdependentAttributes#getInterdependentPatterns <em>Interdependent Patterns</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXInterdependentAttributes#getInjectivityConstraints <em>Injectivity Constraints</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXInterdependentAttributes()
 * @model
 * @generated
 */
public interface IBeXInterdependentAttributes extends EObject {
	/**
	 * Returns the value of the '<em><b>Attributes</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjointAttribute}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attributes</em>' containment reference list.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXInterdependentAttributes_Attributes()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBeXDisjointAttribute> getAttributes();

	/**
	 * Returns the value of the '<em><b>Interdependent Patterns</b></em>' reference list.
	 * The list contents are of type {@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Interdependent Patterns</em>' reference list.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXInterdependentAttributes_InterdependentPatterns()
	 * @model
	 * @generated
	 */
	EList<IBeXContextPattern> getInterdependentPatterns();

	/**
	 * Returns the value of the '<em><b>Injectivity Constraints</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXInterdependentInjectivityConstraints#getAttributeConstraints <em>Attribute Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Injectivity Constraints</em>' reference.
	 * @see #setInjectivityConstraints(IBeXInterdependentInjectivityConstraints)
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXInterdependentAttributes_InjectivityConstraints()
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXInterdependentInjectivityConstraints#getAttributeConstraints
	 * @model opposite="attributeConstraints"
	 * @generated
	 */
	IBeXInterdependentInjectivityConstraints getInjectivityConstraints();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXInterdependentAttributes#getInjectivityConstraints <em>Injectivity Constraints</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Injectivity Constraints</em>' reference.
	 * @see #getInjectivityConstraints()
	 * @generated
	 */
	void setInjectivityConstraints(IBeXInterdependentInjectivityConstraints value);

} // IBeXInterdependentAttributes
