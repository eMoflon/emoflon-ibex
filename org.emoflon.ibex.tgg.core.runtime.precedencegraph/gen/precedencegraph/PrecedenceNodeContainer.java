/**
 */
package precedencegraph;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Precedence Node Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link precedencegraph.PrecedenceNodeContainer#getNodes <em>Nodes</em>}</li>
 * </ul>
 * </p>
 *
 * @see precedencegraph.PrecedencegraphPackage#getPrecedenceNodeContainer()
 * @model
 * @generated
 */
public interface PrecedenceNodeContainer extends EObject {
	/**
	 * Returns the value of the '<em><b>Nodes</b></em>' containment reference list.
	 * The list contents are of type {@link precedencegraph.PrecedenceNode}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nodes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nodes</em>' containment reference list.
	 * @see precedencegraph.PrecedencegraphPackage#getPrecedenceNodeContainer_Nodes()
	 * @model containment="true"
	 * @generated
	 */
	EList<PrecedenceNode> getNodes();
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} // PrecedenceNodeContainer
