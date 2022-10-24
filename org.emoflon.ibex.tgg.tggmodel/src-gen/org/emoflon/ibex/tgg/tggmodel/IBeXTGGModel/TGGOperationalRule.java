/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel;

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

} // TGGOperationalRule
