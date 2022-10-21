/**
 */
package org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Correspondence</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.Correspondence#getSource <em>Source</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.Correspondence#getTarget <em>Target</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.TGGRuntimeModelPackage#getCorrespondence()
 * @model
 * @generated
 */
public interface Correspondence extends EObject {
	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(EObject)
	 * @see org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.TGGRuntimeModelPackage#getCorrespondence_Source()
	 * @model
	 * @generated
	 */
	EObject getSource();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.Correspondence#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(EObject value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(EObject)
	 * @see org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.TGGRuntimeModelPackage#getCorrespondence_Target()
	 * @model
	 * @generated
	 */
	EObject getTarget();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.Correspondence#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(EObject value);

} // Correspondence
