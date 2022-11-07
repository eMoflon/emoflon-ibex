/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class CSPFactoryImpl extends EFactoryImpl implements CSPFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CSPFactory init() {
		try {
			CSPFactory theCSPFactory = (CSPFactory) EPackage.Registry.INSTANCE.getEFactory(CSPPackage.eNS_URI);
			if (theCSPFactory != null) {
				return theCSPFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new CSPFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CSPFactoryImpl() {
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
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION:
			return createTGGAttributeConstraintDefinition();
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY:
			return createTGGAttributeConstraintDefinitionLibrary();
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_DEFINITION:
			return createTGGAttributeConstraintParameterDefinition();
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_BINDING:
			return createTGGAttributeConstraintBinding();
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_SET:
			return createTGGAttributeConstraintSet();
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT:
			return createTGGAttributeConstraint();
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_VARIABLE:
			return createTGGAttributeConstraintVariable();
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_VALUE:
			return createTGGAttributeConstraintParameterValue();
		case CSPPackage.TGGCSP:
			return createTGGCSP();
		case CSPPackage.TGG_LOCAL_VARIABLE:
			return createTGGLocalVariable();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
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
	public TGGAttributeConstraintDefinitionLibrary createTGGAttributeConstraintDefinitionLibrary() {
		TGGAttributeConstraintDefinitionLibraryImpl tggAttributeConstraintDefinitionLibrary = new TGGAttributeConstraintDefinitionLibraryImpl();
		return tggAttributeConstraintDefinitionLibrary;
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
	public TGGAttributeConstraintBinding createTGGAttributeConstraintBinding() {
		TGGAttributeConstraintBindingImpl tggAttributeConstraintBinding = new TGGAttributeConstraintBindingImpl();
		return tggAttributeConstraintBinding;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGAttributeConstraintSet createTGGAttributeConstraintSet() {
		TGGAttributeConstraintSetImpl tggAttributeConstraintSet = new TGGAttributeConstraintSetImpl();
		return tggAttributeConstraintSet;
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
	public TGGAttributeConstraintVariable createTGGAttributeConstraintVariable() {
		TGGAttributeConstraintVariableImpl tggAttributeConstraintVariable = new TGGAttributeConstraintVariableImpl();
		return tggAttributeConstraintVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGAttributeConstraintParameterValue createTGGAttributeConstraintParameterValue() {
		TGGAttributeConstraintParameterValueImpl tggAttributeConstraintParameterValue = new TGGAttributeConstraintParameterValueImpl();
		return tggAttributeConstraintParameterValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGCSP createTGGCSP() {
		TGGCSPImpl tggcsp = new TGGCSPImpl();
		return tggcsp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGLocalVariable createTGGLocalVariable() {
		TGGLocalVariableImpl tggLocalVariable = new TGGLocalVariableImpl();
		return tggLocalVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CSPPackage getCSPPackage() {
		return (CSPPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static CSPPackage getPackage() {
		return CSPPackage.eINSTANCE;
	}

} //CSPFactoryImpl
