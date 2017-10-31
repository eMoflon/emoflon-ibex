/**
 */
package runtime;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;

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
 * </p>
 * <ul>
 *   <li>{@link runtime.TGGRuleApplication#getCreatedSrc <em>Created Src</em>}</li>
 *   <li>{@link runtime.TGGRuleApplication#getCreatedTrg <em>Created Trg</em>}</li>
 *   <li>{@link runtime.TGGRuleApplication#getCreatedCorr <em>Created Corr</em>}</li>
 *   <li>{@link runtime.TGGRuleApplication#getContextSrc <em>Context Src</em>}</li>
 *   <li>{@link runtime.TGGRuleApplication#getContextTrg <em>Context Trg</em>}</li>
 *   <li>{@link runtime.TGGRuleApplication#getName <em>Name</em>}</li>
 *   <li>{@link runtime.TGGRuleApplication#getProtocol <em>Protocol</em>}</li>
 *   <li>{@link runtime.TGGRuleApplication#isFinal <em>Final</em>}</li>
 *   <li>{@link runtime.TGGRuleApplication#getNodeMappings <em>Node Mappings</em>}</li>
 *   <li>{@link runtime.TGGRuleApplication#isAmalgamated <em>Amalgamated</em>}</li>
 * </ul>
 *
 * @see runtime.RuntimePackage#getTGGRuleApplication()
 * @model
 * @generated
 */
public interface TGGRuleApplication extends EObject {
	/**
	 * Returns the value of the '<em><b>Created Src</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EObject}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Created Src</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Created Src</em>' reference list.
	 * @see runtime.RuntimePackage#getTGGRuleApplication_CreatedSrc()
	 * @model
	 * @generated
	 */
	EList<EObject> getCreatedSrc();

	/**
	 * Returns the value of the '<em><b>Created Trg</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EObject}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Created Trg</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Created Trg</em>' reference list.
	 * @see runtime.RuntimePackage#getTGGRuleApplication_CreatedTrg()
	 * @model
	 * @generated
	 */
	EList<EObject> getCreatedTrg();

	/**
	 * Returns the value of the '<em><b>Created Corr</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EObject}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Created Corr</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Created Corr</em>' reference list.
	 * @see runtime.RuntimePackage#getTGGRuleApplication_CreatedCorr()
	 * @model
	 * @generated
	 */
	EList<EObject> getCreatedCorr();

	/**
	 * Returns the value of the '<em><b>Context Src</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EObject}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Context Src</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Context Src</em>' reference list.
	 * @see runtime.RuntimePackage#getTGGRuleApplication_ContextSrc()
	 * @model
	 * @generated
	 */
	EList<EObject> getContextSrc();

	/**
	 * Returns the value of the '<em><b>Context Trg</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EObject}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Context Trg</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Context Trg</em>' reference list.
	 * @see runtime.RuntimePackage#getTGGRuleApplication_ContextTrg()
	 * @model
	 * @generated
	 */
	EList<EObject> getContextTrg();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see runtime.RuntimePackage#getTGGRuleApplication_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link runtime.TGGRuleApplication#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

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

	/**
	 * Returns the value of the '<em><b>Final</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Final</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Final</em>' attribute.
	 * @see #setFinal(boolean)
	 * @see runtime.RuntimePackage#getTGGRuleApplication_Final()
	 * @model
	 * @generated
	 */
	boolean isFinal();

	/**
	 * Sets the value of the '{@link runtime.TGGRuleApplication#isFinal <em>Final</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Final</em>' attribute.
	 * @see #isFinal()
	 * @generated
	 */
	void setFinal(boolean value);

	/**
	 * Returns the value of the '<em><b>Node Mappings</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link org.eclipse.emf.ecore.EObject},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Node Mappings</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Node Mappings</em>' map.
	 * @see runtime.RuntimePackage#getTGGRuleApplication_NodeMappings()
	 * @model mapType="runtime.NodeMapping&lt;org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EObject&gt;"
	 * @generated
	 */
	EMap<String, EObject> getNodeMappings();

	/**
	 * Returns the value of the '<em><b>Amalgamated</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Amalgamated</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Amalgamated</em>' attribute.
	 * @see #setAmalgamated(boolean)
	 * @see runtime.RuntimePackage#getTGGRuleApplication_Amalgamated()
	 * @model default="false"
	 * @generated
	 */
	boolean isAmalgamated();

	/**
	 * Sets the value of the '{@link runtime.TGGRuleApplication#isAmalgamated <em>Amalgamated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Amalgamated</em>' attribute.
	 * @see #isAmalgamated()
	 * @generated
	 */
	void setAmalgamated(boolean value);
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} // TGGRuleApplication
