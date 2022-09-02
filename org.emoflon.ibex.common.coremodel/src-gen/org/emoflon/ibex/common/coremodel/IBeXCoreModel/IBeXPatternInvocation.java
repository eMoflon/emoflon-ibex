/**
 */
package org.emoflon.ibex.common.coremodel.IBeXCoreModel;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XPattern Invocation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternInvocation#isPositive <em>Positive</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternInvocation#getInvokedBy <em>Invoked By</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternInvocation#getInvocation <em>Invocation</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternInvocation#getMapping <em>Mapping</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXPatternInvocation()
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
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXPatternInvocation_Positive()
	 * @model
	 * @generated
	 */
	boolean isPositive();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternInvocation#isPositive <em>Positive</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Positive</em>' attribute.
	 * @see #isPositive()
	 * @generated
	 */
	void setPositive(boolean value);

	/**
	 * Returns the value of the '<em><b>Invoked By</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Invoked By</em>' reference.
	 * @see #setInvokedBy(IBeXPattern)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXPatternInvocation_InvokedBy()
	 * @model required="true"
	 * @generated
	 */
	IBeXPattern getInvokedBy();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternInvocation#getInvokedBy <em>Invoked By</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Invoked By</em>' reference.
	 * @see #getInvokedBy()
	 * @generated
	 */
	void setInvokedBy(IBeXPattern value);

	/**
	 * Returns the value of the '<em><b>Invocation</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Invocation</em>' reference.
	 * @see #setInvocation(IBeXPattern)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXPatternInvocation_Invocation()
	 * @model required="true"
	 * @generated
	 */
	IBeXPattern getInvocation();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternInvocation#getInvocation <em>Invocation</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Invocation</em>' reference.
	 * @see #getInvocation()
	 * @generated
	 */
	void setInvocation(IBeXPattern value);

	/**
	 * Returns the value of the '<em><b>Mapping</b></em>' map.
	 * The key is of type {@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode},
	 * and the value is of type {@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode},
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mapping</em>' map.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXPatternInvocation_Mapping()
	 * @model mapType="org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNodeMapping&lt;org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode, org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode&gt;"
	 * @generated
	 */
	EMap<IBeXNode, IBeXNode> getMapping();

} // IBeXPatternInvocation
