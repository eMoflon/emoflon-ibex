/**
 */
package language;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see language.LanguagePackage
 * @generated
 */
public interface LanguageFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	LanguageFactory eINSTANCE = language.impl.LanguageFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>TGG</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG</em>'.
	 * @generated
	 */
	TGG createTGG();

	/**
	 * Returns a new object of class '<em>TGG Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Rule</em>'.
	 * @generated
	 */
	TGGRule createTGGRule();

	/**
	 * Returns a new object of class '<em>TGG Rule Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Rule Node</em>'.
	 * @generated
	 */
	TGGRuleNode createTGGRuleNode();

	/**
	 * Returns a new object of class '<em>TGG Rule Corr</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Rule Corr</em>'.
	 * @generated
	 */
	TGGRuleCorr createTGGRuleCorr();

	/**
	 * Returns a new object of class '<em>TGG Rule Edge</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Rule Edge</em>'.
	 * @generated
	 */
	TGGRuleEdge createTGGRuleEdge();

	/**
	 * Returns a new object of class '<em>NAC</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>NAC</em>'.
	 * @generated
	 */
	NAC createNAC();

	/**
	 * Returns a new object of class '<em>TGG Inplace Attribute Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Inplace Attribute Expression</em>'.
	 * @generated
	 */
	TGGInplaceAttributeExpression createTGGInplaceAttributeExpression();

	/**
	 * Returns a new object of class '<em>TGG Attribute Constraint Library</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Attribute Constraint Library</em>'.
	 * @generated
	 */
	TGGAttributeConstraintLibrary createTGGAttributeConstraintLibrary();

	/**
	 * Returns a new object of class '<em>TGG Attribute Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Attribute Constraint</em>'.
	 * @generated
	 */
	TGGAttributeConstraint createTGGAttributeConstraint();

	/**
	 * Returns a new object of class '<em>TGG Attribute Variable</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Attribute Variable</em>'.
	 * @generated
	 */
	TGGAttributeVariable createTGGAttributeVariable();

	/**
	 * Returns a new object of class '<em>TGG Attribute Constraint Definition Library</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Attribute Constraint Definition Library</em>'.
	 * @generated
	 */
	TGGAttributeConstraintDefinitionLibrary createTGGAttributeConstraintDefinitionLibrary();

	/**
	 * Returns a new object of class '<em>TGG Attribute Constraint Definition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Attribute Constraint Definition</em>'.
	 * @generated
	 */
	TGGAttributeConstraintDefinition createTGGAttributeConstraintDefinition();

	/**
	 * Returns a new object of class '<em>TGG Attribute Constraint Parameter Definition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Attribute Constraint Parameter Definition</em>'.
	 * @generated
	 */
	TGGAttributeConstraintParameterDefinition createTGGAttributeConstraintParameterDefinition();

	/**
	 * Returns a new object of class '<em>TGG Attribute Constraint Adornment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Attribute Constraint Adornment</em>'.
	 * @generated
	 */
	TGGAttributeConstraintAdornment createTGGAttributeConstraintAdornment();

	/**
	 * Returns a new object of class '<em>TGG Named Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Named Element</em>'.
	 * @generated
	 */
	TGGNamedElement createTGGNamedElement();

	/**
	 * Returns a new object of class '<em>TGG Param Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Param Value</em>'.
	 * @generated
	 */
	TGGParamValue createTGGParamValue();

	/**
	 * Returns a new object of class '<em>TGG Literal Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Literal Expression</em>'.
	 * @generated
	 */
	TGGLiteralExpression createTGGLiteralExpression();

	/**
	 * Returns a new object of class '<em>TGG Enum Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Enum Expression</em>'.
	 * @generated
	 */
	TGGEnumExpression createTGGEnumExpression();

	/**
	 * Returns a new object of class '<em>TGG Attribute Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Attribute Expression</em>'.
	 * @generated
	 */
	TGGAttributeExpression createTGGAttributeExpression();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	LanguagePackage getLanguagePackage();

} //LanguageFactory
