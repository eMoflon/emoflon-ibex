/**
 */
package org.emoflon.ibex.gt.gtmodel.IBeXGTModel;

import org.eclipse.emf.ecore.EAttribute;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ValueExpression;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>GT Iterator Attribute Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTIteratorAttributeReference#getIterator <em>Iterator</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTIteratorAttributeReference#getAttribute <em>Attribute</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelPackage#getGTIteratorAttributeReference()
 * @model
 * @generated
 */
public interface GTIteratorAttributeReference extends ValueExpression {
	/**
	 * Returns the value of the '<em><b>Iterator</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Iterator</em>' reference.
	 * @see #setIterator(GTForEachExpression)
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelPackage#getGTIteratorAttributeReference_Iterator()
	 * @model required="true"
	 * @generated
	 */
	GTForEachExpression getIterator();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTIteratorAttributeReference#getIterator <em>Iterator</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Iterator</em>' reference.
	 * @see #getIterator()
	 * @generated
	 */
	void setIterator(GTForEachExpression value);

	/**
	 * Returns the value of the '<em><b>Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute</em>' reference.
	 * @see #setAttribute(EAttribute)
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelPackage#getGTIteratorAttributeReference_Attribute()
	 * @model required="true"
	 * @generated
	 */
	EAttribute getAttribute();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTIteratorAttributeReference#getAttribute <em>Attribute</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute</em>' reference.
	 * @see #getAttribute()
	 * @generated
	 */
	void setAttribute(EAttribute value);

} // GTIteratorAttributeReference
