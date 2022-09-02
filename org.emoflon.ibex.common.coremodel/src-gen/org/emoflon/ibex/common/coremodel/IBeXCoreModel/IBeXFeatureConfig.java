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
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXFeatureConfig#isCountRequired <em>Count Required</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXFeatureConfig()
 * @model
 * @generated
 */
public interface IBeXFeatureConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Count Required</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Count Required</em>' attribute.
	 * @see #setCountRequired(boolean)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXFeatureConfig_CountRequired()
	 * @model default="false"
	 * @generated
	 */
	boolean isCountRequired();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXFeatureConfig#isCountRequired <em>Count Required</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Count Required</em>' attribute.
	 * @see #isCountRequired()
	 * @generated
	 */
	void setCountRequired(boolean value);

} // IBeXFeatureConfig
