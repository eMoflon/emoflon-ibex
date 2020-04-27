/**
 */
package org.emoflon.ibex.gt.SGTPatternModel;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>GT Number</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * The value of the parameter if it has a static value
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.gt.SGTPatternModel.GTNumber#getNumber <em>Number</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.gt.SGTPatternModel.SGTPatternModelPackage#getGTNumber()
 * @model
 * @generated
 */
public interface GTNumber extends GTArithmetics {
	/**
	 * Returns the value of the '<em><b>Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Number</em>' attribute.
	 * @see #setNumber(double)
	 * @see org.emoflon.ibex.gt.SGTPatternModel.SGTPatternModelPackage#getGTNumber_Number()
	 * @model
	 * @generated
	 */
	double getNumber();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.gt.SGTPatternModel.GTNumber#getNumber <em>Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Number</em>' attribute.
	 * @see #getNumber()
	 * @generated
	 */
	void setNumber(double value);

} // GTNumber
