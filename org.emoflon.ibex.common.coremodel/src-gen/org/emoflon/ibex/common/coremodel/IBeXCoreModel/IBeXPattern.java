/**
 */
package org.emoflon.ibex.common.coremodel.IBeXCoreModel;

import org.eclipse.emf.common.util.EList;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.RelationalExpression;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XPattern</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern#isCountRequired <em>Count Required</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern#getDependencies <em>Dependencies</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern#getSignatureNodes <em>Signature Nodes</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern#getLocalNodes <em>Local Nodes</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern#getEdges <em>Edges</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern#getAttributeConstraints <em>Attribute Constraints</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern#getInjectivityConstraints <em>Injectivity Constraints</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXPattern()
 * @model
 * @generated
 */
public interface IBeXPattern extends IBeXNamedElement {
	/**
	 * Returns the value of the '<em><b>Count Required</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Count Required</em>' attribute.
	 * @see #setCountRequired(boolean)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXPattern_CountRequired()
	 * @model default="false"
	 * @generated
	 */
	boolean isCountRequired();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern#isCountRequired <em>Count Required</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Count Required</em>' attribute.
	 * @see #isCountRequired()
	 * @generated
	 */
	void setCountRequired(boolean value);

	/**
	 * Returns the value of the '<em><b>Dependencies</b></em>' reference list.
	 * The list contents are of type {@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dependencies</em>' reference list.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXPattern_Dependencies()
	 * @model
	 * @generated
	 */
	EList<IBeXPattern> getDependencies();

	/**
	 * Returns the value of the '<em><b>Signature Nodes</b></em>' reference list.
	 * The list contents are of type {@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Signature Nodes</em>' reference list.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXPattern_SignatureNodes()
	 * @model
	 * @generated
	 */
	EList<IBeXNode> getSignatureNodes();

	/**
	 * Returns the value of the '<em><b>Local Nodes</b></em>' reference list.
	 * The list contents are of type {@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Local Nodes</em>' reference list.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXPattern_LocalNodes()
	 * @model
	 * @generated
	 */
	EList<IBeXNode> getLocalNodes();

	/**
	 * Returns the value of the '<em><b>Edges</b></em>' reference list.
	 * The list contents are of type {@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Edges</em>' reference list.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXPattern_Edges()
	 * @model
	 * @generated
	 */
	EList<IBeXEdge> getEdges();

	/**
	 * Returns the value of the '<em><b>Attribute Constraints</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.RelationalExpression}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Constraints</em>' containment reference list.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXPattern_AttributeConstraints()
	 * @model containment="true"
	 * @generated
	 */
	EList<RelationalExpression> getAttributeConstraints();

	/**
	 * Returns the value of the '<em><b>Injectivity Constraints</b></em>' containment reference list.
	 * The list contents are of type {@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXInjectivityConstraint}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Injectivity Constraints</em>' containment reference list.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXPattern_InjectivityConstraints()
	 * @model containment="true"
	 * @generated
	 */
	EList<IBeXInjectivityConstraint> getInjectivityConstraints();

} // IBeXPattern
