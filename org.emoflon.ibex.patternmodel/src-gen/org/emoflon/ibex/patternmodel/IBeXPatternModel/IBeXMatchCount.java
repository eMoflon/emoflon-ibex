/**
 */
package org.emoflon.ibex.patternmodel.IBeXPatternModel;

import org.emoflon.ibex.gt.SGTPatternModel.GTOneParameterCalculation;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XMatch Count</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXMatchCount#getInvocation <em>Invocation</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXMatchCount()
 * @model
 * @generated
 */
public interface IBeXMatchCount extends GTOneParameterCalculation {
	/**
	 * Returns the value of the '<em><b>Invocation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Invocation</em>' containment reference.
	 * @see #setInvocation(IBeXPatternInvocation)
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXMatchCount_Invocation()
	 * @model containment="true" required="true"
	 * @generated
	 */
	IBeXPatternInvocation getInvocation();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXMatchCount#getInvocation <em>Invocation</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Invocation</em>' containment reference.
	 * @see #getInvocation()
	 * @generated
	 */
	void setInvocation(IBeXPatternInvocation value);

} // IBeXMatchCount
