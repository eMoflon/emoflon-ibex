/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel;

import org.eclipse.emf.common.util.EList;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNamedElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TGG Attribute Constraint Definition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintDefinition#getParameterDefinitions <em>Parameter Definitions</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintDefinition#getSyncBindings <em>Sync Bindings</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintDefinition#getGenBindings <em>Gen Bindings</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGAttributeConstraintDefinition()
 * @model
 * @generated
 */
public interface TGGAttributeConstraintDefinition extends IBeXNamedElement {
	/**
	 * Returns the value of the '<em><b>Parameter Definitions</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintParameterDefinition}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameter Definitions</em>' containment reference list.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGAttributeConstraintDefinition_ParameterDefinitions()
	 * @model containment="true"
	 * @generated
	 */
	EList<TGGAttributeConstraintParameterDefinition> getParameterDefinitions();

	/**
	 * Returns the value of the '<em><b>Sync Bindings</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintBinding}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sync Bindings</em>' containment reference list.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGAttributeConstraintDefinition_SyncBindings()
	 * @model containment="true"
	 * @generated
	 */
	EList<TGGAttributeConstraintBinding> getSyncBindings();

	/**
	 * Returns the value of the '<em><b>Gen Bindings</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintBinding}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Gen Bindings</em>' containment reference list.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGAttributeConstraintDefinition_GenBindings()
	 * @model containment="true"
	 * @generated
	 */
	EList<TGGAttributeConstraintBinding> getGenBindings();

} // TGGAttributeConstraintDefinition
