/**
 */
package org.emoflon.ibex.common.coremodel.IBeXCoreModel;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ArithmeticValue;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XMatch Count Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXMatchCountValue#getInvocation <em>Invocation</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXMatchCountValue()
 * @model
 * @generated
 */
public interface IBeXMatchCountValue extends ArithmeticValue {
	/**
	 * Returns the value of the '<em><b>Invocation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Invocation</em>' containment reference.
	 * @see #setInvocation(IBeXPatternInvocation)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXMatchCountValue_Invocation()
	 * @model containment="true" required="true"
	 * @generated
	 */
	IBeXPatternInvocation getInvocation();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXMatchCountValue#getInvocation <em>Invocation</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Invocation</em>' containment reference.
	 * @see #getInvocation()
	 * @generated
	 */
	void setInvocation(IBeXPatternInvocation value);

} // IBeXMatchCountValue
