/**
 */
package TGGRuntimeModel;

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
 *   <li>{@link TGGRuntimeModel.Protocol#getSteps <em>Steps</em>}</li>
 * </ul>
 *
 * @see TGGRuntimeModel.TGGRuntimeModelPackage#getProtocol()
 * @model
 * @generated
 */
public interface Protocol extends EObject {
	/**
	 * Returns the value of the '<em><b>Steps</b></em>' containment reference list.
	 * The list contents are of type {@link TGGRuntimeModel.TGGRuleApplication}.
	 * It is bidirectional and its opposite is '{@link TGGRuntimeModel.TGGRuleApplication#getProtocol <em>Protocol</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Steps</em>' containment reference list.
	 * @see TGGRuntimeModel.TGGRuntimeModelPackage#getProtocol_Steps()
	 * @see TGGRuntimeModel.TGGRuleApplication#getProtocol
	 * @model opposite="protocol" containment="true"
	 * @generated
	 */
	EList<TGGRuleApplication> getSteps();

} // Protocol
