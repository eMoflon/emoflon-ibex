/**
 */
package TGGRuntimeModel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Temp Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link TGGRuntimeModel.TempContainer#getObjects <em>Objects</em>}</li>
 * </ul>
 *
 * @see TGGRuntimeModel.TGGRuntimeModelPackage#getTempContainer()
 * @model
 * @generated
 */
public interface TempContainer extends EObject {
	/**
	 * Returns the value of the '<em><b>Objects</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EObject}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Objects</em>' containment reference list.
	 * @see TGGRuntimeModel.TGGRuntimeModelPackage#getTempContainer_Objects()
	 * @model containment="true"
	 * @generated
	 */
	EList<EObject> getObjects();

} // TempContainer
