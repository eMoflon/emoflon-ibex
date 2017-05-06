/**
 */
package language.csp.impl;

import language.csp.*;

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
public class CspFactoryImpl extends EFactoryImpl implements CspFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CspFactory init() {
		try {
			CspFactory theCspFactory = (CspFactory) EPackage.Registry.INSTANCE.getEFactory(CspPackage.eNS_URI);
			if (theCspFactory != null) {
				return theCspFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new CspFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CspFactoryImpl() {
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
		case CspPackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY:
			return createTGGAttributeConstraintLibrary();
		case CspPackage.TGG_ATTRIBUTE_CONSTRAINT:
			return createTGGAttributeConstraint();
		case CspPackage.TGG_ATTRIBUTE_VARIABLE:
			return createTGGAttributeVariable();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGAttributeConstraintLibrary createTGGAttributeConstraintLibrary() {
		TGGAttributeConstraintLibraryImpl tggAttributeConstraintLibrary = new TGGAttributeConstraintLibraryImpl();
		return tggAttributeConstraintLibrary;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGAttributeConstraint createTGGAttributeConstraint() {
		TGGAttributeConstraintImpl tggAttributeConstraint = new TGGAttributeConstraintImpl();
		return tggAttributeConstraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGAttributeVariable createTGGAttributeVariable() {
		TGGAttributeVariableImpl tggAttributeVariable = new TGGAttributeVariableImpl();
		return tggAttributeVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CspPackage getCspPackage() {
		return (CspPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static CspPackage getPackage() {
		return CspPackage.eINSTANCE;
	}

} //CspFactoryImpl
