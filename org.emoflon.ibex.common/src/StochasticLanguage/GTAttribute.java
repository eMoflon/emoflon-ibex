/**
 */
package StochasticLanguage;

import org.eclipse.emf.ecore.EAttribute;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>GT Attribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Attributes can be used as parameters for stochastic functions.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link StochasticLanguage.GTAttribute#getAttribute <em>Attribute</em>}</li>
 *   <li>{@link StochasticLanguage.GTAttribute#isNegative <em>Negative</em>}</li>
 * </ul>
 *
 * @see StochasticLanguage.StochasticLanguagePackage#getGTAttribute()
 * @model
 * @generated
 */
public interface GTAttribute extends GTStochasticNode, GTArithmetics {
	/**
	 * Returns the value of the '<em><b>Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute</em>' reference.
	 * @see #setAttribute(EAttribute)
	 * @see StochasticLanguage.StochasticLanguagePackage#getGTAttribute_Attribute()
	 * @model
	 * @generated
	 */
	EAttribute getAttribute();

	/**
	 * Sets the value of the '{@link StochasticLanguage.GTAttribute#getAttribute <em>Attribute</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute</em>' reference.
	 * @see #getAttribute()
	 * @generated
	 */
	void setAttribute(EAttribute value);

	/**
	 * Returns the value of the '<em><b>Negative</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Negative</em>' attribute.
	 * @see #setNegative(boolean)
	 * @see StochasticLanguage.StochasticLanguagePackage#getGTAttribute_Negative()
	 * @model
	 * @generated
	 */
	boolean isNegative();

	/**
	 * Sets the value of the '{@link StochasticLanguage.GTAttribute#isNegative <em>Negative</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Negative</em>' attribute.
	 * @see #isNegative()
	 * @generated
	 */
	void setNegative(boolean value);

} // GTAttribute
