/**
 */
package language.csp.definition;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see language.csp.definition.DefinitionPackage
 * @generated
 */
public interface DefinitionFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DefinitionFactory eINSTANCE = language.csp.definition.impl.DefinitionFactoryImpl.init();

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
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	DefinitionPackage getDefinitionPackage();

} //DefinitionFactory
