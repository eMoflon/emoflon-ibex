/**
 */
package runtime;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Temp Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link runtime.TempContainer#getObjects <em>Objects</em>}</li>
 * </ul>
 *
 * @see runtime.RuntimePackage#getTempContainer()
 * @model
 * @generated
 */
public interface TempContainer extends EObject {
	/**
	 * Returns the value of the '<em><b>Objects</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EObject}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Objects</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Objects</em>' containment reference list.
	 * @see runtime.RuntimePackage#getTempContainer_Objects()
	 * @model containment="true"
	 * @generated
	 */
	EList<EObject> getObjects();
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} // TempContainer
