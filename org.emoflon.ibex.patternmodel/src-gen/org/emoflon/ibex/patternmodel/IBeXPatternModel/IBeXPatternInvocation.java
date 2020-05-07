/**
 */
package org.emoflon.ibex.patternmodel.IBeXPatternModel;

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
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternInvocation#isPositive <em>Positive</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternInvocation#getInvokedBy <em>Invoked By</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternInvocation#getInvokedPattern <em>Invoked Pattern</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternInvocation#getMapping <em>Mapping</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXPatternInvocation()
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
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXPatternInvocation_Positive()
	 * @model
	 * @generated
	 */
	boolean isPositive();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternInvocation#isPositive <em>Positive</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Positive</em>' attribute.
	 * @see #isPositive()
	 * @generated
	 */
	void setPositive(boolean value);

	/**
	 * Returns the value of the '<em><b>Invoked By</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern#getInvocations <em>Invocations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Invoked By</em>' container reference.
	 * @see #setInvokedBy(IBeXContextPattern)
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXPatternInvocation_InvokedBy()
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern#getInvocations
	 * @model opposite="invocations" transient="false"
	 * @generated
	 */
	IBeXContextPattern getInvokedBy();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternInvocation#getInvokedBy <em>Invoked By</em>}' container reference.
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
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXPatternInvocation_InvokedPattern()
	 * @model
	 * @generated
	 */
	IBeXContextPattern getInvokedPattern();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternInvocation#getInvokedPattern <em>Invoked Pattern</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Invoked Pattern</em>' reference.
	 * @see #getInvokedPattern()
	 * @generated
	 */
	void setInvokedPattern(IBeXContextPattern value);

	/**
	 * Returns the value of the '<em><b>Mapping</b></em>' map.
	 * The key is of type {@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode},
	 * and the value is of type {@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode},
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mapping</em>' map.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXPatternInvocation_Mapping()
	 * @model mapType="org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNodeToNodeMapping&lt;org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode, org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode&gt;"
	 * @generated
	 */
	EMap<IBeXNode, IBeXNode> getMapping();

} // IBeXPatternInvocation
