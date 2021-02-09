/**
 */
package org.emoflon.ibex.gt.StateModel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Structural Delta</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.StructuralDelta#getCreatedObjects <em>Created Objects</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.StructuralDelta#getDeletedObjects <em>Deleted Objects</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.StructuralDelta#getCreatedLinks <em>Created Links</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.StructuralDelta#getDeletedLinks <em>Deleted Links</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.StateModel.StructuralDelta#getDeletedRootLevelObjects <em>Deleted Root Level Objects</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getStructuralDelta()
 * @model
 * @generated
 */
public interface StructuralDelta extends EObject {
	/**
	 * Returns the value of the '<em><b>Created Objects</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EObject}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Created Objects</em>' reference list.
	 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getStructuralDelta_CreatedObjects()
	 * @model
	 * @generated
	 */
	EList<EObject> getCreatedObjects();

	/**
	 * Returns the value of the '<em><b>Deleted Objects</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EObject}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deleted Objects</em>' reference list.
	 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getStructuralDelta_DeletedObjects()
	 * @model
	 * @generated
	 */
	EList<EObject> getDeletedObjects();

	/**
	 * Returns the value of the '<em><b>Created Links</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.gt.StateModel.Link}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Created Links</em>' containment reference list.
	 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getStructuralDelta_CreatedLinks()
	 * @model containment="true"
	 * @generated
	 */
	EList<Link> getCreatedLinks();

	/**
	 * Returns the value of the '<em><b>Deleted Links</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.gt.StateModel.Link}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deleted Links</em>' containment reference list.
	 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getStructuralDelta_DeletedLinks()
	 * @model containment="true"
	 * @generated
	 */
	EList<Link> getDeletedLinks();

	/**
	 * Returns the value of the '<em><b>Deleted Root Level Objects</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EObject}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deleted Root Level Objects</em>' reference list.
	 * @see org.emoflon.ibex.gt.StateModel.StateModelPackage#getStructuralDelta_DeletedRootLevelObjects()
	 * @model
	 * @generated
	 */
	EList<EObject> getDeletedRootLevelObjects();

} // StructuralDelta
