/**
 */
package runtime;

import org.eclipse.emf.ecore.EObject;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TGG Rule Application</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link runtime.TGGRuleApplication#getProtocol <em>Protocol</em>}</li>
 * </ul>
 * </p>
 *
 * @see runtime.RuntimePackage#getTGGRuleApplication()
 * @model
 * @generated
 */
public interface TGGRuleApplication extends EObject {
	/**
	 * Returns the value of the '<em><b>Protocol</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link runtime.Protocol#getSteps <em>Steps</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Protocol</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Protocol</em>' container reference.
	 * @see #setProtocol(Protocol)
	 * @see runtime.RuntimePackage#getTGGRuleApplication_Protocol()
	 * @see runtime.Protocol#getSteps
	 * @model opposite="steps" transient="false"
	 * @generated
	 */
	Protocol getProtocol();

	/**
	 * Sets the value of the '{@link runtime.TGGRuleApplication#getProtocol <em>Protocol</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Protocol</em>' container reference.
	 * @see #getProtocol()
	 * @generated
	 */
	void setProtocol(Protocol value);
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} // TGGRuleApplication
