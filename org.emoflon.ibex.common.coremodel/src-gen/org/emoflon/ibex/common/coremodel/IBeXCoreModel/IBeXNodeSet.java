/**
 */
package org.emoflon.ibex.common.coremodel.IBeXCoreModel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XNode Set</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNodeSet#getNodes <em>Nodes</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXNodeSet()
 * @model
 * @generated
 */
public interface IBeXNodeSet extends EObject {
	/**
	 * Returns the value of the '<em><b>Nodes</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nodes</em>' containment reference list.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXNodeSet_Nodes()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBeXNode> getNodes();

} // IBeXNodeSet
