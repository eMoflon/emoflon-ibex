/**
 */
package org.emoflon.ibex.patternmodel.IBeXPatternModel;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XConstant</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A wrapper for an object as attribute value.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXConstant#getValue <em>Value</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXConstant#getStringValue <em>String Value</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXConstant()
 * @model
 * @generated
 */
public interface IBeXConstant extends IBeXAttributeValue {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(Object)
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXConstant_Value()
	 * @model
	 * @generated
	 */
	Object getValue();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXConstant#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(Object value);

	/**
	 * Returns the value of the '<em><b>String Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>String Value</em>' attribute.
	 * @see #setStringValue(String)
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXConstant_StringValue()
	 * @model
	 * @generated
	 */
	String getStringValue();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXConstant#getStringValue <em>String Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>String Value</em>' attribute.
	 * @see #getStringValue()
	 * @generated
	 */
	void setStringValue(String value);

} // IBeXConstant
