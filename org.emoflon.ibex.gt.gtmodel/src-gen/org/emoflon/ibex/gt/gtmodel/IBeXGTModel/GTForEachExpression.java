/**
 */
package org.emoflon.ibex.gt.gtmodel.IBeXGTModel;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRuleDelta;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>GT For Each Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTForEachExpression#getCreate <em>Create</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTForEachExpression#getDelete <em>Delete</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTForEachExpression#getSource <em>Source</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTForEachExpression#getReference <em>Reference</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTForEachExpression#getIterator <em>Iterator</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelPackage#getGTForEachExpression()
 * @model
 * @generated
 */
public interface GTForEachExpression extends EObject {
	/**
	 * Returns the value of the '<em><b>Create</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Create</em>' containment reference.
	 * @see #setCreate(IBeXRuleDelta)
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelPackage#getGTForEachExpression_Create()
	 * @model containment="true"
	 * @generated
	 */
	IBeXRuleDelta getCreate();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTForEachExpression#getCreate <em>Create</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Create</em>' containment reference.
	 * @see #getCreate()
	 * @generated
	 */
	void setCreate(IBeXRuleDelta value);

	/**
	 * Returns the value of the '<em><b>Delete</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Delete</em>' containment reference.
	 * @see #setDelete(IBeXRuleDelta)
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelPackage#getGTForEachExpression_Delete()
	 * @model containment="true"
	 * @generated
	 */
	IBeXRuleDelta getDelete();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTForEachExpression#getDelete <em>Delete</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Delete</em>' containment reference.
	 * @see #getDelete()
	 * @generated
	 */
	void setDelete(IBeXRuleDelta value);

	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(IBeXNode)
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelPackage#getGTForEachExpression_Source()
	 * @model required="true"
	 * @generated
	 */
	IBeXNode getSource();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTForEachExpression#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(IBeXNode value);

	/**
	 * Returns the value of the '<em><b>Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reference</em>' reference.
	 * @see #setReference(EReference)
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelPackage#getGTForEachExpression_Reference()
	 * @model required="true"
	 * @generated
	 */
	EReference getReference();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTForEachExpression#getReference <em>Reference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reference</em>' reference.
	 * @see #getReference()
	 * @generated
	 */
	void setReference(EReference value);

	/**
	 * Returns the value of the '<em><b>Iterator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Iterator</em>' containment reference.
	 * @see #setIterator(IBeXNode)
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelPackage#getGTForEachExpression_Iterator()
	 * @model containment="true" required="true"
	 * @generated
	 */
	IBeXNode getIterator();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTForEachExpression#getIterator <em>Iterator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Iterator</em>' containment reference.
	 * @see #getIterator()
	 * @generated
	 */
	void setIterator(IBeXNode value);

} // GTForEachExpression
