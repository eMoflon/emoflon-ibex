/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TGG Attribute Constraint Library</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintLibrary#getTggAttributeConstraints <em>Tgg Attribute Constraints</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGAttributeConstraintLibrary()
 * @model
 * @generated
 */
public interface TGGAttributeConstraintLibrary extends EObject {
	/**
	 * Returns the value of the '<em><b>Tgg Attribute Constraints</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraint}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tgg Attribute Constraints</em>' containment reference list.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGAttributeConstraintLibrary_TggAttributeConstraints()
	 * @model containment="true"
	 * @generated
	 */
	EList<TGGAttributeConstraint> getTggAttributeConstraints();

} // TGGAttributeConstraintLibrary
