/**
 */
package IBeXLanguage;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XContext Alternatives</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link IBeXLanguage.IBeXContextAlternatives#getAlternativePatterns <em>Alternative Patterns</em>}</li>
 * </ul>
 * </p>
 *
 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXContextAlternatives()
 * @model
 * @generated
 */
public interface IBeXContextAlternatives extends EObject, IBeXContext {
	/**
	 * Returns the value of the '<em><b>Alternative Patterns</b></em>' containment reference list.
	 * The list contents are of type {@link IBeXLanguage.IBeXContextPattern}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Alternative Patterns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Alternative Patterns</em>' containment reference list.
	 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXContextAlternatives_AlternativePatterns()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBeXContextPattern> getAlternativePatterns();
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} // IBeXContextAlternatives
