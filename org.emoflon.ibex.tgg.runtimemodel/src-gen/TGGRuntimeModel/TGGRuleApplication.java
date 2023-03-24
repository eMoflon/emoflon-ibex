/**
 */
package TGGRuntimeModel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TGG Rule Application</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link TGGRuntimeModel.TGGRuleApplication#getProtocol <em>Protocol</em>}</li>
 * </ul>
 *
 * @see TGGRuntimeModel.TGGRuntimeModelPackage#getTGGRuleApplication()
 * @model
 * @generated
 */
public interface TGGRuleApplication extends EObject {
	/**
	 * Returns the value of the '<em><b>Protocol</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link TGGRuntimeModel.Protocol#getSteps <em>Steps</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Protocol</em>' container reference.
	 * @see #setProtocol(Protocol)
	 * @see TGGRuntimeModel.TGGRuntimeModelPackage#getTGGRuleApplication_Protocol()
	 * @see TGGRuntimeModel.Protocol#getSteps
	 * @model opposite="steps" transient="false"
	 * @generated
	 */
	Protocol getProtocol();

	/**
	 * Sets the value of the '{@link TGGRuntimeModel.TGGRuleApplication#getProtocol <em>Protocol</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Protocol</em>' container reference.
	 * @see #getProtocol()
	 * @generated
	 */
	void setProtocol(Protocol value);

} // TGGRuleApplication
