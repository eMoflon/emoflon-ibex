/**
 */
package language;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TGG</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link language.TGG#getSrc <em>Src</em>}</li>
 *   <li>{@link language.TGG#getTrg <em>Trg</em>}</li>
 *   <li>{@link language.TGG#getCorr <em>Corr</em>}</li>
 *   <li>{@link language.TGG#getRules <em>Rules</em>}</li>
 *   <li>{@link language.TGG#getAttributeConstraintDefinitionLibrary <em>Attribute Constraint Definition Library</em>}</li>
 * </ul>
 *
 * @see language.LanguagePackage#getTGG()
 * @model
 * @generated
 */
public interface TGG extends TGGNamedElement {
	/**
	 * Returns the value of the '<em><b>Src</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EPackage}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Src</em>' reference list.
	 * @see language.LanguagePackage#getTGG_Src()
	 * @model
	 * @generated
	 */
	EList<EPackage> getSrc();

	/**
	 * Returns the value of the '<em><b>Trg</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EPackage}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Trg</em>' reference list.
	 * @see language.LanguagePackage#getTGG_Trg()
	 * @model
	 * @generated
	 */
	EList<EPackage> getTrg();

	/**
	 * Returns the value of the '<em><b>Corr</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Corr</em>' reference.
	 * @see #setCorr(EPackage)
	 * @see language.LanguagePackage#getTGG_Corr()
	 * @model
	 * @generated
	 */
	EPackage getCorr();

	/**
	 * Sets the value of the '{@link language.TGG#getCorr <em>Corr</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Corr</em>' reference.
	 * @see #getCorr()
	 * @generated
	 */
	void setCorr(EPackage value);

	/**
	 * Returns the value of the '<em><b>Rules</b></em>' containment reference list.
	 * The list contents are of type {@link language.TGGRule}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rules</em>' containment reference list.
	 * @see language.LanguagePackage#getTGG_Rules()
	 * @model containment="true"
	 * @generated
	 */
	EList<TGGRule> getRules();

	/**
	 * Returns the value of the '<em><b>Attribute Constraint Definition Library</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Constraint Definition Library</em>' containment reference.
	 * @see #setAttributeConstraintDefinitionLibrary(TGGAttributeConstraintDefinitionLibrary)
	 * @see language.LanguagePackage#getTGG_AttributeConstraintDefinitionLibrary()
	 * @model containment="true" required="true"
	 * @generated
	 */
	TGGAttributeConstraintDefinitionLibrary getAttributeConstraintDefinitionLibrary();

	/**
	 * Sets the value of the '{@link language.TGG#getAttributeConstraintDefinitionLibrary <em>Attribute Constraint Definition Library</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute Constraint Definition Library</em>' containment reference.
	 * @see #getAttributeConstraintDefinitionLibrary()
	 * @generated
	 */
	void setAttributeConstraintDefinitionLibrary(TGGAttributeConstraintDefinitionLibrary value);

} // TGG
