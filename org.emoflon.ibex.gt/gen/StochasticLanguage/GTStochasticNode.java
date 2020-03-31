/**
 */
package StochasticLanguage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>GT Stochastic Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A typed node can reference other nodes via edges.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link StochasticLanguage.GTStochasticNode#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see StochasticLanguage.StochasticLanguagePackage#getGTStochasticNode()
 * @model
 * @generated
 */
public interface GTStochasticNode extends GTStochasticNamedElement {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(EClass)
	 * @see StochasticLanguage.StochasticLanguagePackage#getGTStochasticNode_Type()
	 * @model
	 * @generated
	 */
	EClass getType();

	/**
	 * Sets the value of the '{@link StochasticLanguage.GTStochasticNode#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(EClass value);

} // GTStochasticNode
