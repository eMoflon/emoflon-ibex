/**
 */
package IBeXLanguage;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XPattern Invocation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * An invocation of an IBeXPattern. Invocations can be positive or negative.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link IBeXLanguage.IBeXPatternInvocation#isPositive <em>Positive</em>}</li>
 *   <li>{@link IBeXLanguage.IBeXPatternInvocation#getInvokedBy <em>Invoked By</em>}</li>
 *   <li>{@link IBeXLanguage.IBeXPatternInvocation#getInvokedPattern <em>Invoked Pattern</em>}</li>
 *   <li>{@link IBeXLanguage.IBeXPatternInvocation#getMapping <em>Mapping</em>}</li>
 * </ul>
 *
 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXPatternInvocation()
 * @model
 * @generated
 */
public interface IBeXPatternInvocation extends EObject {
	/**
	 * Returns the value of the '<em><b>Positive</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Positive</em>' attribute.
	 * @see #setPositive(boolean)
	 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXPatternInvocation_Positive()
	 * @model
	 * @generated
	 */
	boolean isPositive();

	/**
	 * Sets the value of the '{@link IBeXLanguage.IBeXPatternInvocation#isPositive <em>Positive</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Positive</em>' attribute.
	 * @see #isPositive()
	 * @generated
	 */
	void setPositive(boolean value);

	/**
	 * Returns the value of the '<em><b>Invoked By</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link IBeXLanguage.IBeXContextPattern#getInvocations <em>Invocations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Invoked By</em>' container reference.
	 * @see #setInvokedBy(IBeXContextPattern)
	 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXPatternInvocation_InvokedBy()
	 * @see IBeXLanguage.IBeXContextPattern#getInvocations
	 * @model opposite="invocations" transient="false"
	 * @generated
	 */
	IBeXContextPattern getInvokedBy();

	/**
	 * Sets the value of the '{@link IBeXLanguage.IBeXPatternInvocation#getInvokedBy <em>Invoked By</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Invoked By</em>' container reference.
	 * @see #getInvokedBy()
	 * @generated
	 */
	void setInvokedBy(IBeXContextPattern value);

	/**
	 * Returns the value of the '<em><b>Invoked Pattern</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Invoked Pattern</em>' reference.
	 * @see #setInvokedPattern(IBeXContextPattern)
	 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXPatternInvocation_InvokedPattern()
	 * @model
	 * @generated
	 */
	IBeXContextPattern getInvokedPattern();

	/**
	 * Sets the value of the '{@link IBeXLanguage.IBeXPatternInvocation#getInvokedPattern <em>Invoked Pattern</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Invoked Pattern</em>' reference.
	 * @see #getInvokedPattern()
	 * @generated
	 */
	void setInvokedPattern(IBeXContextPattern value);

	/**
	 * Returns the value of the '<em><b>Mapping</b></em>' map.
	 * The key is of type {@link IBeXLanguage.IBeXNode},
	 * and the value is of type {@link IBeXLanguage.IBeXNode},
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mapping</em>' map.
	 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXPatternInvocation_Mapping()
	 * @model mapType="IBeXLanguage.IBeXNodeToNodeMapping&lt;IBeXLanguage.IBeXNode, IBeXLanguage.IBeXNode&gt;"
	 * @generated
	 */
	EMap<IBeXNode, IBeXNode> getMapping();

} // IBeXPatternInvocation
