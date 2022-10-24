/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TGG Attribute Constraint Definition Library</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintDefinitionLibrary#getTggAttributeConstraintDefinitions <em>Tgg Attribute Constraint Definitions</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.CSPPackage#getTGGAttributeConstraintDefinitionLibrary()
 * @model
 * @generated
 */
public interface TGGAttributeConstraintDefinitionLibrary extends EObject {
	/**
	 * Returns the value of the '<em><b>Tgg Attribute Constraint Definitions</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintDefinition}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tgg Attribute Constraint Definitions</em>' containment reference list.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.CSPPackage#getTGGAttributeConstraintDefinitionLibrary_TggAttributeConstraintDefinitions()
	 * @model containment="true"
	 * @generated
	 */
	EList<TGGAttributeConstraintDefinition> getTggAttributeConstraintDefinitions();

} // TGGAttributeConstraintDefinitionLibrary
