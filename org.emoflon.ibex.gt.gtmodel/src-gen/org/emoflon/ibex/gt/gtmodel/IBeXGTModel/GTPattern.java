/**
 */
package org.emoflon.ibex.gt.gtmodel.IBeXGTModel;

import org.eclipse.emf.common.util.EList;

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
 *   <li>{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern#getWatchDogs <em>Watch Dogs</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelPackage#getGTPattern()
 * @model
 * @generated
 */
public interface GTPattern extends IBeXPattern {
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

} // GTPattern
