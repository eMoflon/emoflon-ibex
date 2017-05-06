/**
 */
package language.inplaceAttributes;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see language.inplaceAttributes.InplaceAttributesPackage
 * @generated
 */
public interface InplaceAttributesFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	InplaceAttributesFactory eINSTANCE = language.inplaceAttributes.impl.InplaceAttributesFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>TGG Inplace Attribute Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Inplace Attribute Expression</em>'.
	 * @generated
	 */
	TGGInplaceAttributeExpression createTGGInplaceAttributeExpression();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	InplaceAttributesPackage getInplaceAttributesPackage();

} //InplaceAttributesFactory
