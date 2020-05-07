/**
 */
package org.emoflon.ibex.patternmodel.IBeXPatternModel;

import org.emoflon.ibex.gt.SGTPatternModel.GTStochasticFunction;
import org.emoflon.ibex.gt.SGTPatternModel.GTStochasticRange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XStochastic Attribute Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXStochasticAttributeValue#getRange <em>Range</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXStochasticAttributeValue#getFunction <em>Function</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXStochasticAttributeValue()
 * @model
 * @generated
 */
public interface IBeXStochasticAttributeValue extends IBeXAttributeValue {
	/**
	 * Returns the value of the '<em><b>Range</b></em>' attribute.
	 * The literals are from the enumeration {@link org.emoflon.ibex.gt.SGTPatternModel.GTStochasticRange}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Range</em>' attribute.
	 * @see org.emoflon.ibex.gt.SGTPatternModel.GTStochasticRange
	 * @see #setRange(GTStochasticRange)
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXStochasticAttributeValue_Range()
	 * @model
	 * @generated
	 */
	GTStochasticRange getRange();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXStochasticAttributeValue#getRange <em>Range</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Range</em>' attribute.
	 * @see org.emoflon.ibex.gt.SGTPatternModel.GTStochasticRange
	 * @see #getRange()
	 * @generated
	 */
	void setRange(GTStochasticRange value);

	/**
	 * Returns the value of the '<em><b>Function</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Function</em>' containment reference.
	 * @see #setFunction(GTStochasticFunction)
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXStochasticAttributeValue_Function()
	 * @model containment="true"
	 * @generated
	 */
	GTStochasticFunction getFunction();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXStochasticAttributeValue#getFunction <em>Function</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Function</em>' containment reference.
	 * @see #getFunction()
	 * @generated
	 */
	void setFunction(GTStochasticFunction value);

} // IBeXStochasticAttributeValue
