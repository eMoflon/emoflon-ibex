/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintSet;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TGG Pattern</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGPattern#getAttributeConstraints <em>Attribute Constraints</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGPattern()
 * @model
 * @generated
 */
public interface TGGPattern extends IBeXPattern {
	/**
	 * Returns the value of the '<em><b>Attribute Constraints</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Constraints</em>' containment reference.
	 * @see #setAttributeConstraints(TGGAttributeConstraintSet)
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGPattern_AttributeConstraints()
	 * @model containment="true"
	 * @generated
	 */
	TGGAttributeConstraintSet getAttributeConstraints();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGPattern#getAttributeConstraints <em>Attribute Constraints</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute Constraints</em>' containment reference.
	 * @see #getAttributeConstraints()
	 * @generated
	 */
	void setAttributeConstraints(TGGAttributeConstraintSet value);

} // TGGPattern
