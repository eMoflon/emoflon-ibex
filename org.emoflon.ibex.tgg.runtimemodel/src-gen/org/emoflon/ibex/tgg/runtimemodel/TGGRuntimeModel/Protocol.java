/**
 */
package org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Protocol</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.Protocol#getSteps <em>Steps</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.TGGRuntimeModelPackage#getProtocol()
 * @model
 * @generated
 */
public interface Protocol extends EObject {
	/**
	 * Returns the value of the '<em><b>Steps</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.TGGRuleApplication}.
	 * It is bidirectional and its opposite is '{@link org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.TGGRuleApplication#getProtocol <em>Protocol</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Steps</em>' containment reference list.
	 * @see org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.TGGRuntimeModelPackage#getProtocol_Steps()
	 * @see org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.TGGRuleApplication#getProtocol
	 * @model opposite="protocol" containment="true"
	 * @generated
	 */
	EList<TGGRuleApplication> getSteps();

} // Protocol
