/**
 */
package org.emoflon.ibex.patternmodel.IBeXPatternModel;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XPattern</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPattern#isHasCountExpression <em>Has Count Expression</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXPattern()
 * @model abstract="true"
 * @generated
 */
public interface IBeXPattern extends IBeXNamedElement {
	/**
	 * Returns the value of the '<em><b>Has Count Expression</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Has Count Expression</em>' attribute.
	 * @see #setHasCountExpression(boolean)
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXPattern_HasCountExpression()
	 * @model default="false"
	 * @generated
	 */
	boolean isHasCountExpression();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPattern#isHasCountExpression <em>Has Count Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Has Count Expression</em>' attribute.
	 * @see #isHasCountExpression()
	 * @generated
	 */
	void setHasCountExpression(boolean value);

} // IBeXPattern
