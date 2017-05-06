/**
 */
package language.csp.definition.impl;

import language.csp.definition.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DefinitionFactoryImpl extends EFactoryImpl implements DefinitionFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DefinitionFactory init() {
		try {
			DefinitionFactory theDefinitionFactory = (DefinitionFactory) EPackage.Registry.INSTANCE
					.getEFactory(DefinitionPackage.eNS_URI);
			if (theDefinitionFactory != null) {
				return theDefinitionFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new DefinitionFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DefinitionFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case DefinitionPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY:
			return createTGGAttributeConstraintDefinitionLibrary();
		case DefinitionPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION:
			return createTGGAttributeConstraintDefinition();
		case DefinitionPackage.TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_DEFINITION:
			return createTGGAttributeConstraintParameterDefinition();
		case DefinitionPackage.TGG_ATTRIBUTE_CONSTRAINT_ADORNMENT:
			return createTGGAttributeConstraintAdornment();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGAttributeConstraintDefinitionLibrary createTGGAttributeConstraintDefinitionLibrary() {
		TGGAttributeConstraintDefinitionLibraryImpl tggAttributeConstraintDefinitionLibrary = new TGGAttributeConstraintDefinitionLibraryImpl();
		return tggAttributeConstraintDefinitionLibrary;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGAttributeConstraintDefinition createTGGAttributeConstraintDefinition() {
		TGGAttributeConstraintDefinitionImpl tggAttributeConstraintDefinition = new TGGAttributeConstraintDefinitionImpl();
		return tggAttributeConstraintDefinition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGAttributeConstraintParameterDefinition createTGGAttributeConstraintParameterDefinition() {
		TGGAttributeConstraintParameterDefinitionImpl tggAttributeConstraintParameterDefinition = new TGGAttributeConstraintParameterDefinitionImpl();
		return tggAttributeConstraintParameterDefinition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGAttributeConstraintAdornment createTGGAttributeConstraintAdornment() {
		TGGAttributeConstraintAdornmentImpl tggAttributeConstraintAdornment = new TGGAttributeConstraintAdornmentImpl();
		return tggAttributeConstraintAdornment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DefinitionPackage getDefinitionPackage() {
		return (DefinitionPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static DefinitionPackage getPackage() {
		return DefinitionPackage.eINSTANCE;
	}

} //DefinitionFactoryImpl
