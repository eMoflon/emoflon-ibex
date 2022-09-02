/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class IBeXTGGModelFactoryImpl extends EFactoryImpl implements IBeXTGGModelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static IBeXTGGModelFactory init() {
		try {
			IBeXTGGModelFactory theIBeXTGGModelFactory = (IBeXTGGModelFactory) EPackage.Registry.INSTANCE
					.getEFactory(IBeXTGGModelPackage.eNS_URI);
			if (theIBeXTGGModelFactory != null) {
				return theIBeXTGGModelFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new IBeXTGGModelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXTGGModelFactoryImpl() {
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
		case IBeXTGGModelPackage.TGG_MODEL:
			return createTGGModel();
		case IBeXTGGModelPackage.TGG_RULE:
			return createTGGRule();
		case IBeXTGGModelPackage.TGG_NODE:
			return createTGGNode();
		case IBeXTGGModelPackage.TGG_RULE_CORRESPONDENCE:
			return createTGGRuleCorrespondence();
		case IBeXTGGModelPackage.TGG_EDGE:
			return createTGGEdge();
		case IBeXTGGModelPackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY:
			return createTGGAttributeConstraintLibrary();
		case IBeXTGGModelPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY:
			return createTGGAttributeConstraintDefinitionLibrary();
		case IBeXTGGModelPackage.TGG_ATTRIBUTE_CONSTRAINT:
			return createTGGAttributeConstraint();
		case IBeXTGGModelPackage.TGG_PARAMETER_VALUE:
			return createTGGParameterValue();
		case IBeXTGGModelPackage.TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_DEFINITION:
			return createTGGAttributeConstraintParameterDefinition();
		case IBeXTGGModelPackage.TGG_ATTRIBUTE_CONSTRAINT_BINDING:
			return createTGGAttributeConstraintBinding();
		case IBeXTGGModelPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION:
			return createTGGAttributeConstraintDefinition();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
		case IBeXTGGModelPackage.DOMAIN_TYPE:
			return createDomainTypeFromString(eDataType, initialValue);
		case IBeXTGGModelPackage.BINDING_TYPE:
			return createBindingTypeFromString(eDataType, initialValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
		case IBeXTGGModelPackage.DOMAIN_TYPE:
			return convertDomainTypeToString(eDataType, instanceValue);
		case IBeXTGGModelPackage.BINDING_TYPE:
			return convertBindingTypeToString(eDataType, instanceValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGModel createTGGModel() {
		TGGModelImpl tggModel = new TGGModelImpl();
		return tggModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGRule createTGGRule() {
		TGGRuleImpl tggRule = new TGGRuleImpl();
		return tggRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGNode createTGGNode() {
		TGGNodeImpl tggNode = new TGGNodeImpl();
		return tggNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGRuleCorrespondence createTGGRuleCorrespondence() {
		TGGRuleCorrespondenceImpl tggRuleCorrespondence = new TGGRuleCorrespondenceImpl();
		return tggRuleCorrespondence;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGEdge createTGGEdge() {
		TGGEdgeImpl tggEdge = new TGGEdgeImpl();
		return tggEdge;
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
	public TGGAttributeConstraintDefinitionLibrary createTGGAttributeConstraintDefinitionLibrary() {
		TGGAttributeConstraintDefinitionLibraryImpl tggAttributeConstraintDefinitionLibrary = new TGGAttributeConstraintDefinitionLibraryImpl();
		return tggAttributeConstraintDefinitionLibrary;
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
	public TGGParameterValue createTGGParameterValue() {
		TGGParameterValueImpl tggParameterValue = new TGGParameterValueImpl();
		return tggParameterValue;
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
	public TGGAttributeConstraintDefinition createTGGAttributeConstraintDefinition() {
		TGGAttributeConstraintDefinitionImpl tggAttributeConstraintDefinition = new TGGAttributeConstraintDefinitionImpl();
		return tggAttributeConstraintDefinition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomainType createDomainTypeFromString(EDataType eDataType, String initialValue) {
		DomainType result = DomainType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDomainTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BindingType createBindingTypeFromString(EDataType eDataType, String initialValue) {
		BindingType result = BindingType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertBindingTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXTGGModelPackage getIBeXTGGModelPackage() {
		return (IBeXTGGModelPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static IBeXTGGModelPackage getPackage() {
		return IBeXTGGModelPackage.eINSTANCE;
	}

} //IBeXTGGModelFactoryImpl
