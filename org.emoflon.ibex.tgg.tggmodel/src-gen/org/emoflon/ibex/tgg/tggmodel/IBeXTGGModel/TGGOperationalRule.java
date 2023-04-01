/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRuleDelta;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TGG Operational Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule#getOperationalisationMode <em>Operationalisation Mode</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule#getToBeMarked <em>To Be Marked</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule#getAlreadyMarked <em>Already Marked</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule#getTggRule <em>Tgg Rule</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGOperationalRule()
 * @model
 * @generated
 */
public interface TGGOperationalRule extends TGGRule {
	/**
	 * Returns the value of the '<em><b>Operationalisation Mode</b></em>' attribute.
	 * The literals are from the enumeration {@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.OperationalisationMode}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operationalisation Mode</em>' attribute.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.OperationalisationMode
	 * @see #setOperationalisationMode(OperationalisationMode)
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGOperationalRule_OperationalisationMode()
	 * @model
	 * @generated
	 */
	OperationalisationMode getOperationalisationMode();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule#getOperationalisationMode <em>Operationalisation Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operationalisation Mode</em>' attribute.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.OperationalisationMode
	 * @see #getOperationalisationMode()
	 * @generated
	 */
	void setOperationalisationMode(OperationalisationMode value);

	/**
	 * Returns the value of the '<em><b>To Be Marked</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>To Be Marked</em>' containment reference.
	 * @see #setToBeMarked(IBeXRuleDelta)
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGOperationalRule_ToBeMarked()
	 * @model containment="true"
	 * @generated
	 */
	IBeXRuleDelta getToBeMarked();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule#getToBeMarked <em>To Be Marked</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>To Be Marked</em>' containment reference.
	 * @see #getToBeMarked()
	 * @generated
	 */
	void setToBeMarked(IBeXRuleDelta value);

	/**
	 * Returns the value of the '<em><b>Already Marked</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Already Marked</em>' containment reference.
	 * @see #setAlreadyMarked(IBeXRuleDelta)
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGOperationalRule_AlreadyMarked()
	 * @model containment="true"
	 * @generated
	 */
	IBeXRuleDelta getAlreadyMarked();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule#getAlreadyMarked <em>Already Marked</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Already Marked</em>' containment reference.
	 * @see #getAlreadyMarked()
	 * @generated
	 */
	void setAlreadyMarked(IBeXRuleDelta value);

	/**
	 * Returns the value of the '<em><b>Tgg Rule</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getOperationalisations <em>Operationalisations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tgg Rule</em>' container reference.
	 * @see #setTggRule(TGGRule)
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getTGGOperationalRule_TggRule()
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule#getOperationalisations
	 * @model opposite="operationalisations" transient="false"
	 * @generated
	 */
	TGGRule getTggRule();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule#getTggRule <em>Tgg Rule</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tgg Rule</em>' container reference.
	 * @see #getTggRule()
	 * @generated
	 */
	void setTggRule(TGGRule value);

} // TGGOperationalRule
