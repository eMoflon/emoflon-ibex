/**
 */
package org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl;

import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class IBeXCoreModelFactoryImpl extends EFactoryImpl implements IBeXCoreModelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static IBeXCoreModelFactory init() {
		try {
			IBeXCoreModelFactory theIBeXCoreModelFactory = (IBeXCoreModelFactory) EPackage.Registry.INSTANCE
					.getEFactory(IBeXCoreModelPackage.eNS_URI);
			if (theIBeXCoreModelFactory != null) {
				return theIBeXCoreModelFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new IBeXCoreModelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXCoreModelFactoryImpl() {
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
		case IBeXCoreModelPackage.IBE_XMODEL:
			return createIBeXModel();
		case IBeXCoreModelPackage.IBE_XMODEL_METADATA:
			return createIBeXModelMetadata();
		case IBeXCoreModelPackage.IMPORT_NAME_TO_PACKAGE_DEPENDENCY_MAPPING:
			return (EObject) createImportNameToPackageDependencyMapping();
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY:
			return createEPackageDependency();
		case IBeXCoreModelPackage.CLASSIFIER_NAME_TO_FQN:
			return (EObject) createClassifierNameToFQN();
		case IBeXCoreModelPackage.IBE_XFEATURE_CONFIG:
			return createIBeXFeatureConfig();
		case IBeXCoreModelPackage.IBE_XPATTERN_SET:
			return createIBeXPatternSet();
		case IBeXCoreModelPackage.IBE_XNODE_SET:
			return createIBeXNodeSet();
		case IBeXCoreModelPackage.IBE_XEDGE_SET:
			return createIBeXEdgeSet();
		case IBeXCoreModelPackage.IBE_XNODE:
			return createIBeXNode();
		case IBeXCoreModelPackage.IBE_XEDGE:
			return createIBeXEdge();
		case IBeXCoreModelPackage.IBE_XPATTERN:
			return createIBeXPattern();
		case IBeXCoreModelPackage.IBE_XINJECTIVITY_CONSTRAINT:
			return createIBeXInjectivityConstraint();
		case IBeXCoreModelPackage.IBE_XPATTERN_INVOCATION:
			return createIBeXPatternInvocation();
		case IBeXCoreModelPackage.IBE_XNODE_MAPPING:
			return (EObject) createIBeXNodeMapping();
		case IBeXCoreModelPackage.IBE_XENUM_VALUE:
			return createIBeXEnumValue();
		case IBeXCoreModelPackage.IBE_XSTRING_VALUE:
			return createIBeXStringValue();
		case IBeXCoreModelPackage.IBE_XBOOLEAN_VALUE:
			return createIBeXBooleanValue();
		case IBeXCoreModelPackage.IBE_XATTRIBUTE_VALUE:
			return createIBeXAttributeValue();
		case IBeXCoreModelPackage.IBE_XNODE_VALUE:
			return createIBeXNodeValue();
		case IBeXCoreModelPackage.IBE_XMATCH_COUNT_VALUE:
			return createIBeXMatchCountValue();
		case IBeXCoreModelPackage.IBE_XRULE:
			return createIBeXRule();
		case IBeXCoreModelPackage.IBE_XRULE_DELTA:
			return createIBeXRuleDelta();
		case IBeXCoreModelPackage.IBE_XATTRIBUTE_ASSIGNMENT:
			return createIBeXAttributeAssignment();
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
		case IBeXCoreModelPackage.IBE_XOPERATION_TYPE:
			return createIBeXOperationTypeFromString(eDataType, initialValue);
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
		case IBeXCoreModelPackage.IBE_XOPERATION_TYPE:
			return convertIBeXOperationTypeToString(eDataType, instanceValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXModel createIBeXModel() {
		IBeXModelImpl iBeXModel = new IBeXModelImpl();
		return iBeXModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXModelMetadata createIBeXModelMetadata() {
		IBeXModelMetadataImpl iBeXModelMetadata = new IBeXModelMetadataImpl();
		return iBeXModelMetadata;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<String, EPackageDependency> createImportNameToPackageDependencyMapping() {
		ImportNameToPackageDependencyMappingImpl importNameToPackageDependencyMapping = new ImportNameToPackageDependencyMappingImpl();
		return importNameToPackageDependencyMapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EPackageDependency createEPackageDependency() {
		EPackageDependencyImpl ePackageDependency = new EPackageDependencyImpl();
		return ePackageDependency;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<String, String> createClassifierNameToFQN() {
		ClassifierNameToFQNImpl classifierNameToFQN = new ClassifierNameToFQNImpl();
		return classifierNameToFQN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXFeatureConfig createIBeXFeatureConfig() {
		IBeXFeatureConfigImpl iBeXFeatureConfig = new IBeXFeatureConfigImpl();
		return iBeXFeatureConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXPatternSet createIBeXPatternSet() {
		IBeXPatternSetImpl iBeXPatternSet = new IBeXPatternSetImpl();
		return iBeXPatternSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXNodeSet createIBeXNodeSet() {
		IBeXNodeSetImpl iBeXNodeSet = new IBeXNodeSetImpl();
		return iBeXNodeSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXEdgeSet createIBeXEdgeSet() {
		IBeXEdgeSetImpl iBeXEdgeSet = new IBeXEdgeSetImpl();
		return iBeXEdgeSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXNode createIBeXNode() {
		IBeXNodeImpl iBeXNode = new IBeXNodeImpl();
		return iBeXNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXEdge createIBeXEdge() {
		IBeXEdgeImpl iBeXEdge = new IBeXEdgeImpl();
		return iBeXEdge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXPattern createIBeXPattern() {
		IBeXPatternImpl iBeXPattern = new IBeXPatternImpl();
		return iBeXPattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXInjectivityConstraint createIBeXInjectivityConstraint() {
		IBeXInjectivityConstraintImpl iBeXInjectivityConstraint = new IBeXInjectivityConstraintImpl();
		return iBeXInjectivityConstraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXPatternInvocation createIBeXPatternInvocation() {
		IBeXPatternInvocationImpl iBeXPatternInvocation = new IBeXPatternInvocationImpl();
		return iBeXPatternInvocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<IBeXNode, IBeXNode> createIBeXNodeMapping() {
		IBeXNodeMappingImpl iBeXNodeMapping = new IBeXNodeMappingImpl();
		return iBeXNodeMapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXEnumValue createIBeXEnumValue() {
		IBeXEnumValueImpl iBeXEnumValue = new IBeXEnumValueImpl();
		return iBeXEnumValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXStringValue createIBeXStringValue() {
		IBeXStringValueImpl iBeXStringValue = new IBeXStringValueImpl();
		return iBeXStringValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXBooleanValue createIBeXBooleanValue() {
		IBeXBooleanValueImpl iBeXBooleanValue = new IBeXBooleanValueImpl();
		return iBeXBooleanValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXAttributeValue createIBeXAttributeValue() {
		IBeXAttributeValueImpl iBeXAttributeValue = new IBeXAttributeValueImpl();
		return iBeXAttributeValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXNodeValue createIBeXNodeValue() {
		IBeXNodeValueImpl iBeXNodeValue = new IBeXNodeValueImpl();
		return iBeXNodeValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXMatchCountValue createIBeXMatchCountValue() {
		IBeXMatchCountValueImpl iBeXMatchCountValue = new IBeXMatchCountValueImpl();
		return iBeXMatchCountValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXRule createIBeXRule() {
		IBeXRuleImpl iBeXRule = new IBeXRuleImpl();
		return iBeXRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXRuleDelta createIBeXRuleDelta() {
		IBeXRuleDeltaImpl iBeXRuleDelta = new IBeXRuleDeltaImpl();
		return iBeXRuleDelta;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXAttributeAssignment createIBeXAttributeAssignment() {
		IBeXAttributeAssignmentImpl iBeXAttributeAssignment = new IBeXAttributeAssignmentImpl();
		return iBeXAttributeAssignment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXOperationType createIBeXOperationTypeFromString(EDataType eDataType, String initialValue) {
		IBeXOperationType result = IBeXOperationType.get(initialValue);
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
	public String convertIBeXOperationTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXCoreModelPackage getIBeXCoreModelPackage() {
		return (IBeXCoreModelPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static IBeXCoreModelPackage getPackage() {
		return IBeXCoreModelPackage.eINSTANCE;
	}

} //IBeXCoreModelFactoryImpl
