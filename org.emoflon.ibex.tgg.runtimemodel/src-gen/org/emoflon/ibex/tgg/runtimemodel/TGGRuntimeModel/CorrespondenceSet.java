/**
 */
package org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Correspondence Set</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.CorrespondenceSet#getCorrespondences <em>Correspondences</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.TGGRuntimeModelPackage#getCorrespondenceSet()
 * @model
 * @generated
 */
public interface CorrespondenceSet extends EObject {
	/**
	 * Returns the value of the '<em><b>Correspondences</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.Correspondence}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Correspondences</em>' containment reference list.
	 * @see org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.TGGRuntimeModelPackage#getCorrespondenceSet_Correspondences()
	 * @model containment="true"
	 * @generated
	 */
	EList<Correspondence> getCorrespondences();

} // CorrespondenceSet
