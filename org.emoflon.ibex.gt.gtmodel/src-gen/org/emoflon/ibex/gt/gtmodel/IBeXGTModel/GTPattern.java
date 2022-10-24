/**
 */
package org.emoflon.ibex.gt.gtmodel.IBeXGTModel;

import org.eclipse.emf.common.util.EList;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXFeatureConfig;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>GT Pattern</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern#getParameters <em>Parameters</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern#getWatchDogs <em>Watch Dogs</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern#getUsedFeatures <em>Used Features</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelPackage#getGTPattern()
 * @model
 * @generated
 */
public interface GTPattern extends IBeXPattern {
	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTParameter}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' containment reference list.
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelPackage#getGTPattern_Parameters()
	 * @model containment="true"
	 * @generated
	 */
	EList<GTParameter> getParameters();

	/**
	 * Returns the value of the '<em><b>Watch Dogs</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTWatchDog}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Watch Dogs</em>' containment reference list.
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelPackage#getGTPattern_WatchDogs()
	 * @model containment="true"
	 * @generated
	 */
	EList<GTWatchDog> getWatchDogs();

	/**
	 * Returns the value of the '<em><b>Used Features</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Used Features</em>' containment reference.
	 * @see #setUsedFeatures(IBeXFeatureConfig)
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelPackage#getGTPattern_UsedFeatures()
	 * @model containment="true" required="true"
	 * @generated
	 */
	IBeXFeatureConfig getUsedFeatures();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern#getUsedFeatures <em>Used Features</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Used Features</em>' containment reference.
	 * @see #getUsedFeatures()
	 * @generated
	 */
	void setUsedFeatures(IBeXFeatureConfig value);

} // GTPattern
