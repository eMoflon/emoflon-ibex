/**
 */
package org.emoflon.ibex.common.coremodel.IBeXCoreModel;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ValueExpression;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XBoolean Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXBooleanValue#isValue <em>Value</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXBooleanValue()
 * @model
 * @generated
 */
public interface IBeXBooleanValue extends ValueExpression {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(boolean)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXBooleanValue_Value()
	 * @model
	 * @generated
	 */
	boolean isValue();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXBooleanValue#isValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #isValue()
	 * @generated
	 */
	void setValue(boolean value);

} // IBeXBooleanValue
