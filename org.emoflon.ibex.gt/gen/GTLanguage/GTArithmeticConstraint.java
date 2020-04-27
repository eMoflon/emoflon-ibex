/**
 */
package GTLanguage;

import org.eclipse.emf.ecore.EObject;

import org.emoflon.ibex.gt.SGTPatternModel.GTArithmetics;
import org.emoflon.ibex.gt.SGTPatternModel.GTAttribute;
import org.emoflon.ibex.gt.SGTPatternModel.GTRelation;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>GT Arithmetic Constraint</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link GTLanguage.GTArithmeticConstraint#getParameter <em>Parameter</em>}</li>
 *   <li>{@link GTLanguage.GTArithmeticConstraint#getExpression <em>Expression</em>}</li>
 *   <li>{@link GTLanguage.GTArithmeticConstraint#getRelation <em>Relation</em>}</li>
 * </ul>
 *
 * @see GTLanguage.GTLanguagePackage#getGTArithmeticConstraint()
 * @model
 * @generated
 */
public interface GTArithmeticConstraint extends EObject {
	/**
	 * Returns the value of the '<em><b>Parameter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameter</em>' containment reference.
	 * @see #setParameter(GTAttribute)
	 * @see GTLanguage.GTLanguagePackage#getGTArithmeticConstraint_Parameter()
	 * @model containment="true"
	 * @generated
	 */
	GTAttribute getParameter();

	/**
	 * Sets the value of the '{@link GTLanguage.GTArithmeticConstraint#getParameter <em>Parameter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parameter</em>' containment reference.
	 * @see #getParameter()
	 * @generated
	 */
	void setParameter(GTAttribute value);

	/**
	 * Returns the value of the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expression</em>' containment reference.
	 * @see #setExpression(GTArithmetics)
	 * @see GTLanguage.GTLanguagePackage#getGTArithmeticConstraint_Expression()
	 * @model containment="true"
	 * @generated
	 */
	GTArithmetics getExpression();

	/**
	 * Sets the value of the '{@link GTLanguage.GTArithmeticConstraint#getExpression <em>Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expression</em>' containment reference.
	 * @see #getExpression()
	 * @generated
	 */
	void setExpression(GTArithmetics value);

	/**
	 * Returns the value of the '<em><b>Relation</b></em>' attribute.
	 * The literals are from the enumeration {@link org.emoflon.ibex.gt.SGTPatternModel.GTRelation}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Relation</em>' attribute.
	 * @see org.emoflon.ibex.gt.SGTPatternModel.GTRelation
	 * @see #setRelation(GTRelation)
	 * @see GTLanguage.GTLanguagePackage#getGTArithmeticConstraint_Relation()
	 * @model
	 * @generated
	 */
	GTRelation getRelation();

	/**
	 * Sets the value of the '{@link GTLanguage.GTArithmeticConstraint#getRelation <em>Relation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Relation</em>' attribute.
	 * @see org.emoflon.ibex.gt.SGTPatternModel.GTRelation
	 * @see #getRelation()
	 * @generated
	 */
	void setRelation(GTRelation value);

} // GTArithmeticConstraint
