/**
 */
package org.emoflon.ibex.gt.gtmodel.IBeXGTModel;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>GT Watch Dog</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTWatchDog#getNode <em>Node</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTWatchDog#getAttribute <em>Attribute</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelPackage#getGTWatchDog()
 * @model
 * @generated
 */
public interface GTWatchDog extends EObject {
	/**
	 * Returns the value of the '<em><b>Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Node</em>' reference.
	 * @see #setNode(IBeXNode)
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelPackage#getGTWatchDog_Node()
	 * @model required="true"
	 * @generated
	 */
	IBeXNode getNode();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTWatchDog#getNode <em>Node</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Node</em>' reference.
	 * @see #getNode()
	 * @generated
	 */
	void setNode(IBeXNode value);

	/**
	 * Returns the value of the '<em><b>Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute</em>' reference.
	 * @see #setAttribute(EAttribute)
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelPackage#getGTWatchDog_Attribute()
	 * @model required="true"
	 * @generated
	 */
	EAttribute getAttribute();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTWatchDog#getAttribute <em>Attribute</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute</em>' reference.
	 * @see #getAttribute()
	 * @generated
	 */
	void setAttribute(EAttribute value);

} // GTWatchDog
