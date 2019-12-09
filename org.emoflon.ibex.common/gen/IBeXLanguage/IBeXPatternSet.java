/**
 */
package IBeXLanguage;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;
// <-- [user defined imports]
// [user defined imports] -->

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XPattern Set</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link IBeXLanguage.IBeXPatternSet#getContextPatterns <em>Context Patterns</em>}</li>
 *   <li>{@link IBeXLanguage.IBeXPatternSet#getCreatePatterns <em>Create Patterns</em>}</li>
 *   <li>{@link IBeXLanguage.IBeXPatternSet#getDeletePatterns <em>Delete Patterns</em>}</li>
 * </ul>
 * </p>
 *
 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXPatternSet()
 * @model
 * @generated
 */
public interface IBeXPatternSet extends EObject {
	/**
	 * Returns the value of the '<em><b>Context Patterns</b></em>' containment reference list.
	 * The list contents are of type {@link IBeXLanguage.IBeXContext}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Context Patterns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Context Patterns</em>' containment reference list.
	 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXPatternSet_ContextPatterns()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBeXContext> getContextPatterns();

	/**
	 * Returns the value of the '<em><b>Create Patterns</b></em>' containment reference list.
	 * The list contents are of type {@link IBeXLanguage.IBeXCreatePattern}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Create Patterns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Create Patterns</em>' containment reference list.
	 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXPatternSet_CreatePatterns()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBeXCreatePattern> getCreatePatterns();

	/**
	 * Returns the value of the '<em><b>Delete Patterns</b></em>' containment reference list.
	 * The list contents are of type {@link IBeXLanguage.IBeXDeletePattern}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Delete Patterns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Delete Patterns</em>' containment reference list.
	 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXPatternSet_DeletePatterns()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBeXDeletePattern> getDeletePatterns();
	// <-- [user code injected with eMoflon]

	// [user code injected with eMoflon] -->
} // IBeXPatternSet
