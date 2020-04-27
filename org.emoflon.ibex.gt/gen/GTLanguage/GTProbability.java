/**
 */
package GTLanguage;

import org.eclipse.emf.ecore.EObject;

import org.emoflon.ibex.gt.SGTPatternModel.GTArithmetics;
import org.emoflon.ibex.gt.SGTPatternModel.GTStochasticFunction;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>GT Probability</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link GTLanguage.GTProbability#getFunction <em>Function</em>}</li>
 *   <li>{@link GTLanguage.GTProbability#getParameter <em>Parameter</em>}</li>
 * </ul>
 *
 * @see GTLanguage.GTLanguagePackage#getGTProbability()
 * @model
 * @generated
 */
public interface GTProbability extends EObject {
	/**
	 * Returns the value of the '<em><b>Function</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Function</em>' containment reference.
	 * @see #setFunction(GTStochasticFunction)
	 * @see GTLanguage.GTLanguagePackage#getGTProbability_Function()
	 * @model containment="true"
	 * @generated
	 */
	GTStochasticFunction getFunction();

	/**
	 * Sets the value of the '{@link GTLanguage.GTProbability#getFunction <em>Function</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Function</em>' containment reference.
	 * @see #getFunction()
	 * @generated
	 */
	void setFunction(GTStochasticFunction value);

	/**
	 * Returns the value of the '<em><b>Parameter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameter</em>' containment reference.
	 * @see #setParameter(GTArithmetics)
	 * @see GTLanguage.GTLanguagePackage#getGTProbability_Parameter()
	 * @model containment="true"
	 * @generated
	 */
	GTArithmetics getParameter();

	/**
	 * Sets the value of the '{@link GTLanguage.GTProbability#getParameter <em>Parameter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parameter</em>' containment reference.
	 * @see #getParameter()
	 * @generated
	 */
	void setParameter(GTArithmetics value);

} // GTProbability
