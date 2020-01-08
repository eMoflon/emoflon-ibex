/**
 */
package runtime;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Protocol</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link runtime.Protocol#getSteps <em>Steps</em>}</li>
 * </ul>
 * </p>
 *
 * @see runtime.RuntimePackage#getProtocol()
 * @model
 * @generated
 */
public interface Protocol extends EObject {
	/**
	 * Returns the value of the '<em><b>Steps</b></em>' containment reference list.
	 * The list contents are of type {@link runtime.TGGRuleApplication}.
	 * It is bidirectional and its opposite is '{@link runtime.TGGRuleApplication#getProtocol <em>Protocol</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Steps</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Steps</em>' containment reference list.
	 * @see runtime.RuntimePackage#getProtocol_Steps()
	 * @see runtime.TGGRuleApplication#getProtocol
	 * @model opposite="protocol" containment="true"
	 * @generated
	 */
	EList<TGGRuleApplication> getSteps();
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} // Protocol
