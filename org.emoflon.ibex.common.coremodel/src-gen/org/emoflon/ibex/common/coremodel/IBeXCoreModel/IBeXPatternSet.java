/**
 */
package org.emoflon.ibex.common.coremodel.IBeXCoreModel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XPattern Set</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternSet#getPatterns <em>Patterns</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXPatternSet()
 * @model
 * @generated
 */
public interface IBeXPatternSet extends EObject {
	/**
	 * Returns the value of the '<em><b>Patterns</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Patterns</em>' containment reference list.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXPatternSet_Patterns()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBeXPattern> getPatterns();

} // IBeXPatternSet
