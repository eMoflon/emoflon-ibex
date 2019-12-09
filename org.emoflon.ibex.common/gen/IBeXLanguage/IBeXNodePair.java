/**
 */
package IBeXLanguage;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XNode Pair</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link IBeXLanguage.IBeXNodePair#getValues <em>Values</em>}</li>
 * </ul>
 * </p>
 *
 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXNodePair()
 * @model
 * @generated
 */
public interface IBeXNodePair extends EObject {
	/**
	 * Returns the value of the '<em><b>Values</b></em>' reference list.
	 * The list contents are of type {@link IBeXLanguage.IBeXNode}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Values</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Values</em>' reference list.
	 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXNodePair_Values()
	 * @model lower="2" upper="2"
	 * @generated
	 */
	EList<IBeXNode> getValues();
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} // IBeXNodePair
