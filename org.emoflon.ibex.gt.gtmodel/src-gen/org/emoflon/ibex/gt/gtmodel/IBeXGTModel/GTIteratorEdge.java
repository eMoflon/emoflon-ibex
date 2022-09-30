/**
 */
package org.emoflon.ibex.gt.gtmodel.IBeXGTModel;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>GT Iterator Edge</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTIteratorEdge#getIterator <em>Iterator</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelPackage#getGTIteratorEdge()
 * @model
 * @generated
 */
public interface GTIteratorEdge extends IBeXEdge {
	/**
	 * Returns the value of the '<em><b>Iterator</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Iterator</em>' reference.
	 * @see #setIterator(GTForEachExpression)
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelPackage#getGTIteratorEdge_Iterator()
	 * @model required="true"
	 * @generated
	 */
	GTForEachExpression getIterator();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTIteratorEdge#getIterator <em>Iterator</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Iterator</em>' reference.
	 * @see #getIterator()
	 * @generated
	 */
	void setIterator(GTForEachExpression value);

} // GTIteratorEdge
