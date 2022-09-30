/**
 */
package org.emoflon.ibex.gt.gtmodel.IBeXGTModel;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeAssignment;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>GT Iterator Attribute Assignment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTIteratorAttributeAssignment#getIterator <em>Iterator</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelPackage#getGTIteratorAttributeAssignment()
 * @model
 * @generated
 */
public interface GTIteratorAttributeAssignment extends IBeXAttributeAssignment {
	/**
	 * Returns the value of the '<em><b>Iterator</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Iterator</em>' reference.
	 * @see #setIterator(GTForEachExpression)
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelPackage#getGTIteratorAttributeAssignment_Iterator()
	 * @model required="true"
	 * @generated
	 */
	GTForEachExpression getIterator();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTIteratorAttributeAssignment#getIterator <em>Iterator</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Iterator</em>' reference.
	 * @see #getIterator()
	 * @generated
	 */
	void setIterator(GTForEachExpression value);

} // GTIteratorAttributeAssignment
