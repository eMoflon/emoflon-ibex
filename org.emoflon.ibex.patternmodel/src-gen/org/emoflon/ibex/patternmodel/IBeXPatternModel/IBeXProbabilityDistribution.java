/**
 */
package org.emoflon.ibex.patternmodel.IBeXPatternModel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XProbability Distribution</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXProbabilityDistribution#getMean <em>Mean</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXProbabilityDistribution#getStddev <em>Stddev</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXProbabilityDistribution#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXProbabilityDistribution()
 * @model
 * @generated
 */
public interface IBeXProbabilityDistribution extends EObject {
	/**
	 * Returns the value of the '<em><b>Mean</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mean</em>' containment reference.
	 * @see #setMean(IBeXArithmeticExpression)
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXProbabilityDistribution_Mean()
	 * @model containment="true"
	 * @generated
	 */
	IBeXArithmeticExpression getMean();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXProbabilityDistribution#getMean <em>Mean</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mean</em>' containment reference.
	 * @see #getMean()
	 * @generated
	 */
	void setMean(IBeXArithmeticExpression value);

	/**
	 * Returns the value of the '<em><b>Stddev</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Stddev</em>' containment reference.
	 * @see #setStddev(IBeXArithmeticExpression)
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXProbabilityDistribution_Stddev()
	 * @model containment="true"
	 * @generated
	 */
	IBeXArithmeticExpression getStddev();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXProbabilityDistribution#getStddev <em>Stddev</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Stddev</em>' containment reference.
	 * @see #getStddev()
	 * @generated
	 */
	void setStddev(IBeXArithmeticExpression value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDistributionType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDistributionType
	 * @see #setType(IBeXDistributionType)
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXProbabilityDistribution_Type()
	 * @model required="true"
	 * @generated
	 */
	IBeXDistributionType getType();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXProbabilityDistribution#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDistributionType
	 * @see #getType()
	 * @generated
	 */
	void setType(IBeXDistributionType value);

} // IBeXProbabilityDistribution
