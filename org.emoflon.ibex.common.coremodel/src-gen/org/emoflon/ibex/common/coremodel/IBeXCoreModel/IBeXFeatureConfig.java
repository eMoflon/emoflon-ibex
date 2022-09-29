/**
 */
package org.emoflon.ibex.common.coremodel.IBeXCoreModel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XFeature Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXFeatureConfig#isCountExpressions <em>Count Expressions</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXFeatureConfig#isArithmeticExpressions <em>Arithmetic Expressions</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXFeatureConfig#isBooleanExpressions <em>Boolean Expressions</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXFeatureConfig()
 * @model
 * @generated
 */
public interface IBeXFeatureConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Count Expressions</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Count Expressions</em>' attribute.
	 * @see #setCountExpressions(boolean)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXFeatureConfig_CountExpressions()
	 * @model default="false"
	 * @generated
	 */
	boolean isCountExpressions();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXFeatureConfig#isCountExpressions <em>Count Expressions</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Count Expressions</em>' attribute.
	 * @see #isCountExpressions()
	 * @generated
	 */
	void setCountExpressions(boolean value);

	/**
	 * Returns the value of the '<em><b>Arithmetic Expressions</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Arithmetic Expressions</em>' attribute.
	 * @see #setArithmeticExpressions(boolean)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXFeatureConfig_ArithmeticExpressions()
	 * @model default="false"
	 * @generated
	 */
	boolean isArithmeticExpressions();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXFeatureConfig#isArithmeticExpressions <em>Arithmetic Expressions</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Arithmetic Expressions</em>' attribute.
	 * @see #isArithmeticExpressions()
	 * @generated
	 */
	void setArithmeticExpressions(boolean value);

	/**
	 * Returns the value of the '<em><b>Boolean Expressions</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Boolean Expressions</em>' attribute.
	 * @see #setBooleanExpressions(boolean)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXFeatureConfig_BooleanExpressions()
	 * @model default="false"
	 * @generated
	 */
	boolean isBooleanExpressions();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXFeatureConfig#isBooleanExpressions <em>Boolean Expressions</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Boolean Expressions</em>' attribute.
	 * @see #isBooleanExpressions()
	 * @generated
	 */
	void setBooleanExpressions(boolean value);

} // IBeXFeatureConfig
