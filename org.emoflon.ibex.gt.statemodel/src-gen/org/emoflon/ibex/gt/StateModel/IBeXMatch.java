/**
 */
package org.emoflon.ibex.gt.StateModel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XMatch</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.IBeXMatch#getPatternName <em>Pattern Name</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.IBeXMatch#getParameters <em>Parameters</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getIBeXMatch()
 * @model
 * @generated
 */
public interface IBeXMatch extends EObject {
	/**
	 * Returns the value of the '<em><b>Pattern Name</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pattern Name</em>' attribute.
	 * @see #setPatternName(String)
	 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getIBeXMatch_PatternName()
	 * @model default="" ordered="false"
	 * @generated
	 */
	String getPatternName();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.gt.StateModel.IBeXMatch#getPatternName <em>Pattern Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pattern Name</em>' attribute.
	 * @see #getPatternName()
	 * @generated
	 */
	void setPatternName(String value);

	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.gt.StateModel.Parameter}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' containment reference list.
	 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getIBeXMatch_Parameters()
	 * @model containment="true"
	 * @generated
	 */
	EList<Parameter> getParameters();

} // IBeXMatch
