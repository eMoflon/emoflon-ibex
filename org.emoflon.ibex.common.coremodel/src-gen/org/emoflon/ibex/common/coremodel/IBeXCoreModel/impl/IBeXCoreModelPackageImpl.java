/**
 */
package org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl;

import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.EPackageDependency;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeAssignment;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXBooleanValue;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.IBeXCoreArithmeticPackage;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.impl.IBeXCoreArithmeticPackageImpl;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelFactory;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdgeSet;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEnumValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXFeatureConfig;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXInjectivityConstraint;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXMatchCountValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModel;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModelMetadata;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNamedElement;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNodeSet;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternInvocation;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternSet;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRule;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRuleDelta;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXStringValue;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class IBeXCoreModelPackageImpl extends EPackageImpl implements IBeXCoreModelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXNamedElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXModelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXModelMetadataEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass importNameToPackageDependencyMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ePackageDependencyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nameToEClassifierMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXFeatureConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXPatternSetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXNodeSetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXEdgeSetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXEdgeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXPatternEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXInjectivityConstraintEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXPatternInvocationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXNodeMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXEnumValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXStringValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXBooleanValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXAttributeValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXMatchCountValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXRuleDeltaEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXAttributeAssignmentEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private IBeXCoreModelPackageImpl() {
		super(eNS_URI, IBeXCoreModelFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link IBeXCoreModelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static IBeXCoreModelPackage init() {
		if (isInited)
			return (IBeXCoreModelPackage) EPackage.Registry.INSTANCE.getEPackage(IBeXCoreModelPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredIBeXCoreModelPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		IBeXCoreModelPackageImpl theIBeXCoreModelPackage = registeredIBeXCoreModelPackage instanceof IBeXCoreModelPackageImpl
				? (IBeXCoreModelPackageImpl) registeredIBeXCoreModelPackage
				: new IBeXCoreModelPackageImpl();

		isInited = true;

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(IBeXCoreArithmeticPackage.eNS_URI);
		IBeXCoreArithmeticPackageImpl theIBeXCoreArithmeticPackage = (IBeXCoreArithmeticPackageImpl) (registeredPackage instanceof IBeXCoreArithmeticPackageImpl
				? registeredPackage
				: IBeXCoreArithmeticPackage.eINSTANCE);

		// Create package meta-data objects
		theIBeXCoreModelPackage.createPackageContents();
		theIBeXCoreArithmeticPackage.createPackageContents();

		// Initialize created meta-data
		theIBeXCoreModelPackage.initializePackageContents();
		theIBeXCoreArithmeticPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theIBeXCoreModelPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(IBeXCoreModelPackage.eNS_URI, theIBeXCoreModelPackage);
		return theIBeXCoreModelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIBeXNamedElement() {
		return iBeXNamedElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIBeXNamedElement_Name() {
		return (EAttribute) iBeXNamedElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIBeXModel() {
		return iBeXModelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXModel_MetaData() {
		return (EReference) iBeXModelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXModel_PatternSet() {
		return (EReference) iBeXModelEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXModel_NodeSet() {
		return (EReference) iBeXModelEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXModel_EdgeSet() {
		return (EReference) iBeXModelEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIBeXModelMetadata() {
		return iBeXModelMetadataEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIBeXModelMetadata_Project() {
		return (EAttribute) iBeXModelMetadataEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIBeXModelMetadata_ProjectPath() {
		return (EAttribute) iBeXModelMetadataEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIBeXModelMetadata_Package() {
		return (EAttribute) iBeXModelMetadataEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIBeXModelMetadata_PackagePath() {
		return (EAttribute) iBeXModelMetadataEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXModelMetadata_Dependencies() {
		return (EReference) iBeXModelMetadataEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXModelMetadata_Name2package() {
		return (EReference) iBeXModelMetadataEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getImportNameToPackageDependencyMapping() {
		return importNameToPackageDependencyMappingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getImportNameToPackageDependencyMapping_Key() {
		return (EAttribute) importNameToPackageDependencyMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getImportNameToPackageDependencyMapping_Value() {
		return (EReference) importNameToPackageDependencyMappingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEPackageDependency() {
		return ePackageDependencyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEPackageDependency_SimpleName() {
		return (EAttribute) ePackageDependencyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEPackageDependency_HasAlias() {
		return (EAttribute) ePackageDependencyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEPackageDependency_Alias() {
		return (EAttribute) ePackageDependencyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEPackageDependency_FullyQualifiedName() {
		return (EAttribute) ePackageDependencyEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEPackageDependency_Package() {
		return (EReference) ePackageDependencyEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEPackageDependency_PackageURI() {
		return (EAttribute) ePackageDependencyEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEPackageDependency_PackageHasProject() {
		return (EAttribute) ePackageDependencyEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEPackageDependency_ProjectLocation() {
		return (EAttribute) ePackageDependencyEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEPackageDependency_FactoryClassName() {
		return (EAttribute) ePackageDependencyEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEPackageDependency_PackageClassName() {
		return (EAttribute) ePackageDependencyEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEPackageDependency_EcoreURI() {
		return (EAttribute) ePackageDependencyEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEPackageDependency_EcoreHasLocation() {
		return (EAttribute) ePackageDependencyEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEPackageDependency_EcoreLocation() {
		return (EAttribute) ePackageDependencyEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEPackageDependency_GenmodelURI() {
		return (EAttribute) ePackageDependencyEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEPackageDependency_GenmodelHasLocation() {
		return (EAttribute) ePackageDependencyEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEPackageDependency_GenmodelLocation() {
		return (EAttribute) ePackageDependencyEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEPackageDependency_Name2classifier() {
		return (EReference) ePackageDependencyEClass.getEStructuralFeatures().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNameToEClassifierMapping() {
		return nameToEClassifierMappingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNameToEClassifierMapping_Key() {
		return (EAttribute) nameToEClassifierMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNameToEClassifierMapping_Value() {
		return (EReference) nameToEClassifierMappingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXModel_FeatureConfig() {
		return (EReference) iBeXModelEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIBeXFeatureConfig() {
		return iBeXFeatureConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIBeXFeatureConfig_CountExpressions() {
		return (EAttribute) iBeXFeatureConfigEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIBeXFeatureConfig_ArithmeticExpressions() {
		return (EAttribute) iBeXFeatureConfigEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIBeXPatternSet() {
		return iBeXPatternSetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXPatternSet_Patterns() {
		return (EReference) iBeXPatternSetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIBeXNodeSet() {
		return iBeXNodeSetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXNodeSet_Nodes() {
		return (EReference) iBeXNodeSetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIBeXEdgeSet() {
		return iBeXEdgeSetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXEdgeSet_Edges() {
		return (EReference) iBeXEdgeSetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIBeXNode() {
		return iBeXNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXNode_Type() {
		return (EReference) iBeXNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXNode_IncomingEdges() {
		return (EReference) iBeXNodeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXNode_OutgoingEdges() {
		return (EReference) iBeXNodeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIBeXEdge() {
		return iBeXEdgeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXEdge_Type() {
		return (EReference) iBeXEdgeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXEdge_Source() {
		return (EReference) iBeXEdgeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXEdge_Target() {
		return (EReference) iBeXEdgeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIBeXPattern() {
		return iBeXPatternEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXPattern_Dependencies() {
		return (EReference) iBeXPatternEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXPattern_SignatureNodes() {
		return (EReference) iBeXPatternEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXPattern_LocalNodes() {
		return (EReference) iBeXPatternEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXPattern_Edges() {
		return (EReference) iBeXPatternEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXPattern_AttributeConstraints() {
		return (EReference) iBeXPatternEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXPattern_InjectivityConstraints() {
		return (EReference) iBeXPatternEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXPattern_Invocations() {
		return (EReference) iBeXPatternEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIBeXInjectivityConstraint() {
		return iBeXInjectivityConstraintEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXInjectivityConstraint_Nodes() {
		return (EReference) iBeXInjectivityConstraintEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIBeXPatternInvocation() {
		return iBeXPatternInvocationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIBeXPatternInvocation_Positive() {
		return (EAttribute) iBeXPatternInvocationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXPatternInvocation_InvokedBy() {
		return (EReference) iBeXPatternInvocationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXPatternInvocation_Invocation() {
		return (EReference) iBeXPatternInvocationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXPatternInvocation_Mapping() {
		return (EReference) iBeXPatternInvocationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIBeXNodeMapping() {
		return iBeXNodeMappingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXNodeMapping_Key() {
		return (EReference) iBeXNodeMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXNodeMapping_Value() {
		return (EReference) iBeXNodeMappingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIBeXEnumValue() {
		return iBeXEnumValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXEnumValue_Literal() {
		return (EReference) iBeXEnumValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIBeXStringValue() {
		return iBeXStringValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIBeXStringValue_Value() {
		return (EAttribute) iBeXStringValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIBeXBooleanValue() {
		return iBeXBooleanValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIBeXBooleanValue_Value() {
		return (EAttribute) iBeXBooleanValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIBeXAttributeValue() {
		return iBeXAttributeValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXAttributeValue_Node() {
		return (EReference) iBeXAttributeValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXAttributeValue_Attribute() {
		return (EReference) iBeXAttributeValueEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIBeXMatchCountValue() {
		return iBeXMatchCountValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXMatchCountValue_Invocation() {
		return (EReference) iBeXMatchCountValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIBeXRule() {
		return iBeXRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXRule_Precondition() {
		return (EReference) iBeXRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXRule_Postcondition() {
		return (EReference) iBeXRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXRule_Creation() {
		return (EReference) iBeXRuleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXRule_Deletion() {
		return (EReference) iBeXRuleEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXRule_AttributeAssignments() {
		return (EReference) iBeXRuleEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXRule_AllNodes() {
		return (EReference) iBeXRuleEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXRule_AllEdges() {
		return (EReference) iBeXRuleEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIBeXRuleDelta() {
		return iBeXRuleDeltaEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXRuleDelta_Nodes() {
		return (EReference) iBeXRuleDeltaEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXRuleDelta_Edges() {
		return (EReference) iBeXRuleDeltaEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIBeXAttributeAssignment() {
		return iBeXAttributeAssignmentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXAttributeAssignment_Node() {
		return (EReference) iBeXAttributeAssignmentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXAttributeAssignment_Attribute() {
		return (EReference) iBeXAttributeAssignmentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIBeXAttributeAssignment_Value() {
		return (EReference) iBeXAttributeAssignmentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXCoreModelFactory getIBeXCoreModelFactory() {
		return (IBeXCoreModelFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		iBeXNamedElementEClass = createEClass(IBE_XNAMED_ELEMENT);
		createEAttribute(iBeXNamedElementEClass, IBE_XNAMED_ELEMENT__NAME);

		iBeXModelEClass = createEClass(IBE_XMODEL);
		createEReference(iBeXModelEClass, IBE_XMODEL__META_DATA);
		createEReference(iBeXModelEClass, IBE_XMODEL__FEATURE_CONFIG);
		createEReference(iBeXModelEClass, IBE_XMODEL__PATTERN_SET);
		createEReference(iBeXModelEClass, IBE_XMODEL__NODE_SET);
		createEReference(iBeXModelEClass, IBE_XMODEL__EDGE_SET);

		iBeXModelMetadataEClass = createEClass(IBE_XMODEL_METADATA);
		createEAttribute(iBeXModelMetadataEClass, IBE_XMODEL_METADATA__PROJECT);
		createEAttribute(iBeXModelMetadataEClass, IBE_XMODEL_METADATA__PROJECT_PATH);
		createEAttribute(iBeXModelMetadataEClass, IBE_XMODEL_METADATA__PACKAGE);
		createEAttribute(iBeXModelMetadataEClass, IBE_XMODEL_METADATA__PACKAGE_PATH);
		createEReference(iBeXModelMetadataEClass, IBE_XMODEL_METADATA__DEPENDENCIES);
		createEReference(iBeXModelMetadataEClass, IBE_XMODEL_METADATA__NAME2PACKAGE);

		importNameToPackageDependencyMappingEClass = createEClass(IMPORT_NAME_TO_PACKAGE_DEPENDENCY_MAPPING);
		createEAttribute(importNameToPackageDependencyMappingEClass, IMPORT_NAME_TO_PACKAGE_DEPENDENCY_MAPPING__KEY);
		createEReference(importNameToPackageDependencyMappingEClass, IMPORT_NAME_TO_PACKAGE_DEPENDENCY_MAPPING__VALUE);

		ePackageDependencyEClass = createEClass(EPACKAGE_DEPENDENCY);
		createEAttribute(ePackageDependencyEClass, EPACKAGE_DEPENDENCY__SIMPLE_NAME);
		createEAttribute(ePackageDependencyEClass, EPACKAGE_DEPENDENCY__HAS_ALIAS);
		createEAttribute(ePackageDependencyEClass, EPACKAGE_DEPENDENCY__ALIAS);
		createEAttribute(ePackageDependencyEClass, EPACKAGE_DEPENDENCY__FULLY_QUALIFIED_NAME);
		createEReference(ePackageDependencyEClass, EPACKAGE_DEPENDENCY__PACKAGE);
		createEAttribute(ePackageDependencyEClass, EPACKAGE_DEPENDENCY__PACKAGE_URI);
		createEAttribute(ePackageDependencyEClass, EPACKAGE_DEPENDENCY__PACKAGE_HAS_PROJECT);
		createEAttribute(ePackageDependencyEClass, EPACKAGE_DEPENDENCY__PROJECT_LOCATION);
		createEAttribute(ePackageDependencyEClass, EPACKAGE_DEPENDENCY__FACTORY_CLASS_NAME);
		createEAttribute(ePackageDependencyEClass, EPACKAGE_DEPENDENCY__PACKAGE_CLASS_NAME);
		createEAttribute(ePackageDependencyEClass, EPACKAGE_DEPENDENCY__ECORE_URI);
		createEAttribute(ePackageDependencyEClass, EPACKAGE_DEPENDENCY__ECORE_HAS_LOCATION);
		createEAttribute(ePackageDependencyEClass, EPACKAGE_DEPENDENCY__ECORE_LOCATION);
		createEAttribute(ePackageDependencyEClass, EPACKAGE_DEPENDENCY__GENMODEL_URI);
		createEAttribute(ePackageDependencyEClass, EPACKAGE_DEPENDENCY__GENMODEL_HAS_LOCATION);
		createEAttribute(ePackageDependencyEClass, EPACKAGE_DEPENDENCY__GENMODEL_LOCATION);
		createEReference(ePackageDependencyEClass, EPACKAGE_DEPENDENCY__NAME2CLASSIFIER);

		nameToEClassifierMappingEClass = createEClass(NAME_TO_ECLASSIFIER_MAPPING);
		createEAttribute(nameToEClassifierMappingEClass, NAME_TO_ECLASSIFIER_MAPPING__KEY);
		createEReference(nameToEClassifierMappingEClass, NAME_TO_ECLASSIFIER_MAPPING__VALUE);

		iBeXFeatureConfigEClass = createEClass(IBE_XFEATURE_CONFIG);
		createEAttribute(iBeXFeatureConfigEClass, IBE_XFEATURE_CONFIG__COUNT_EXPRESSIONS);
		createEAttribute(iBeXFeatureConfigEClass, IBE_XFEATURE_CONFIG__ARITHMETIC_EXPRESSIONS);

		iBeXPatternSetEClass = createEClass(IBE_XPATTERN_SET);
		createEReference(iBeXPatternSetEClass, IBE_XPATTERN_SET__PATTERNS);

		iBeXNodeSetEClass = createEClass(IBE_XNODE_SET);
		createEReference(iBeXNodeSetEClass, IBE_XNODE_SET__NODES);

		iBeXEdgeSetEClass = createEClass(IBE_XEDGE_SET);
		createEReference(iBeXEdgeSetEClass, IBE_XEDGE_SET__EDGES);

		iBeXNodeEClass = createEClass(IBE_XNODE);
		createEReference(iBeXNodeEClass, IBE_XNODE__TYPE);
		createEReference(iBeXNodeEClass, IBE_XNODE__INCOMING_EDGES);
		createEReference(iBeXNodeEClass, IBE_XNODE__OUTGOING_EDGES);

		iBeXEdgeEClass = createEClass(IBE_XEDGE);
		createEReference(iBeXEdgeEClass, IBE_XEDGE__TYPE);
		createEReference(iBeXEdgeEClass, IBE_XEDGE__SOURCE);
		createEReference(iBeXEdgeEClass, IBE_XEDGE__TARGET);

		iBeXPatternEClass = createEClass(IBE_XPATTERN);
		createEReference(iBeXPatternEClass, IBE_XPATTERN__DEPENDENCIES);
		createEReference(iBeXPatternEClass, IBE_XPATTERN__SIGNATURE_NODES);
		createEReference(iBeXPatternEClass, IBE_XPATTERN__LOCAL_NODES);
		createEReference(iBeXPatternEClass, IBE_XPATTERN__EDGES);
		createEReference(iBeXPatternEClass, IBE_XPATTERN__ATTRIBUTE_CONSTRAINTS);
		createEReference(iBeXPatternEClass, IBE_XPATTERN__INJECTIVITY_CONSTRAINTS);
		createEReference(iBeXPatternEClass, IBE_XPATTERN__INVOCATIONS);

		iBeXInjectivityConstraintEClass = createEClass(IBE_XINJECTIVITY_CONSTRAINT);
		createEReference(iBeXInjectivityConstraintEClass, IBE_XINJECTIVITY_CONSTRAINT__NODES);

		iBeXPatternInvocationEClass = createEClass(IBE_XPATTERN_INVOCATION);
		createEAttribute(iBeXPatternInvocationEClass, IBE_XPATTERN_INVOCATION__POSITIVE);
		createEReference(iBeXPatternInvocationEClass, IBE_XPATTERN_INVOCATION__INVOKED_BY);
		createEReference(iBeXPatternInvocationEClass, IBE_XPATTERN_INVOCATION__INVOCATION);
		createEReference(iBeXPatternInvocationEClass, IBE_XPATTERN_INVOCATION__MAPPING);

		iBeXNodeMappingEClass = createEClass(IBE_XNODE_MAPPING);
		createEReference(iBeXNodeMappingEClass, IBE_XNODE_MAPPING__KEY);
		createEReference(iBeXNodeMappingEClass, IBE_XNODE_MAPPING__VALUE);

		iBeXEnumValueEClass = createEClass(IBE_XENUM_VALUE);
		createEReference(iBeXEnumValueEClass, IBE_XENUM_VALUE__LITERAL);

		iBeXStringValueEClass = createEClass(IBE_XSTRING_VALUE);
		createEAttribute(iBeXStringValueEClass, IBE_XSTRING_VALUE__VALUE);

		iBeXBooleanValueEClass = createEClass(IBE_XBOOLEAN_VALUE);
		createEAttribute(iBeXBooleanValueEClass, IBE_XBOOLEAN_VALUE__VALUE);

		iBeXAttributeValueEClass = createEClass(IBE_XATTRIBUTE_VALUE);
		createEReference(iBeXAttributeValueEClass, IBE_XATTRIBUTE_VALUE__NODE);
		createEReference(iBeXAttributeValueEClass, IBE_XATTRIBUTE_VALUE__ATTRIBUTE);

		iBeXMatchCountValueEClass = createEClass(IBE_XMATCH_COUNT_VALUE);
		createEReference(iBeXMatchCountValueEClass, IBE_XMATCH_COUNT_VALUE__INVOCATION);

		iBeXRuleEClass = createEClass(IBE_XRULE);
		createEReference(iBeXRuleEClass, IBE_XRULE__PRECONDITION);
		createEReference(iBeXRuleEClass, IBE_XRULE__POSTCONDITION);
		createEReference(iBeXRuleEClass, IBE_XRULE__CREATION);
		createEReference(iBeXRuleEClass, IBE_XRULE__DELETION);
		createEReference(iBeXRuleEClass, IBE_XRULE__ATTRIBUTE_ASSIGNMENTS);
		createEReference(iBeXRuleEClass, IBE_XRULE__ALL_NODES);
		createEReference(iBeXRuleEClass, IBE_XRULE__ALL_EDGES);

		iBeXRuleDeltaEClass = createEClass(IBE_XRULE_DELTA);
		createEReference(iBeXRuleDeltaEClass, IBE_XRULE_DELTA__NODES);
		createEReference(iBeXRuleDeltaEClass, IBE_XRULE_DELTA__EDGES);

		iBeXAttributeAssignmentEClass = createEClass(IBE_XATTRIBUTE_ASSIGNMENT);
		createEReference(iBeXAttributeAssignmentEClass, IBE_XATTRIBUTE_ASSIGNMENT__NODE);
		createEReference(iBeXAttributeAssignmentEClass, IBE_XATTRIBUTE_ASSIGNMENT__ATTRIBUTE);
		createEReference(iBeXAttributeAssignmentEClass, IBE_XATTRIBUTE_ASSIGNMENT__VALUE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		IBeXCoreArithmeticPackage theIBeXCoreArithmeticPackage = (IBeXCoreArithmeticPackage) EPackage.Registry.INSTANCE
				.getEPackage(IBeXCoreArithmeticPackage.eNS_URI);

		// Add subpackages
		getESubpackages().add(theIBeXCoreArithmeticPackage);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		iBeXModelEClass.getESuperTypes().add(this.getIBeXNamedElement());
		iBeXNodeEClass.getESuperTypes().add(this.getIBeXNamedElement());
		iBeXEdgeEClass.getESuperTypes().add(this.getIBeXNamedElement());
		iBeXPatternEClass.getESuperTypes().add(this.getIBeXNamedElement());
		iBeXEnumValueEClass.getESuperTypes().add(theIBeXCoreArithmeticPackage.getValueExpression());
		iBeXStringValueEClass.getESuperTypes().add(theIBeXCoreArithmeticPackage.getValueExpression());
		iBeXBooleanValueEClass.getESuperTypes().add(theIBeXCoreArithmeticPackage.getValueExpression());
		iBeXBooleanValueEClass.getESuperTypes().add(theIBeXCoreArithmeticPackage.getBooleanValue());
		iBeXAttributeValueEClass.getESuperTypes().add(theIBeXCoreArithmeticPackage.getArithmeticValue());
		iBeXAttributeValueEClass.getESuperTypes().add(theIBeXCoreArithmeticPackage.getBooleanValue());
		iBeXMatchCountValueEClass.getESuperTypes().add(theIBeXCoreArithmeticPackage.getArithmeticValue());
		iBeXRuleEClass.getESuperTypes().add(this.getIBeXNamedElement());

		// Initialize classes, features, and operations; add parameters
		initEClass(iBeXNamedElementEClass, IBeXNamedElement.class, "IBeXNamedElement", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIBeXNamedElement_Name(), ecorePackage.getEString(), "name", null, 0, 1,
				IBeXNamedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(iBeXModelEClass, IBeXModel.class, "IBeXModel", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXModel_MetaData(), this.getIBeXModelMetadata(), null, "metaData", null, 1, 1,
				IBeXModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXModel_FeatureConfig(), this.getIBeXFeatureConfig(), null, "featureConfig", null, 1, 1,
				IBeXModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXModel_PatternSet(), this.getIBeXPatternSet(), null, "patternSet", null, 1, 1,
				IBeXModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXModel_NodeSet(), this.getIBeXNodeSet(), null, "nodeSet", null, 1, 1, IBeXModel.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXModel_EdgeSet(), this.getIBeXEdgeSet(), null, "edgeSet", null, 1, 1, IBeXModel.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXModelMetadataEClass, IBeXModelMetadata.class, "IBeXModelMetadata", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIBeXModelMetadata_Project(), ecorePackage.getEString(), "project", null, 0, 1,
				IBeXModelMetadata.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getIBeXModelMetadata_ProjectPath(), ecorePackage.getEString(), "projectPath", null, 0, 1,
				IBeXModelMetadata.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getIBeXModelMetadata_Package(), ecorePackage.getEString(), "package", null, 0, 1,
				IBeXModelMetadata.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getIBeXModelMetadata_PackagePath(), ecorePackage.getEString(), "packagePath", null, 0, 1,
				IBeXModelMetadata.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXModelMetadata_Dependencies(), this.getEPackageDependency(), null, "dependencies", null, 0,
				-1, IBeXModelMetadata.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXModelMetadata_Name2package(), this.getImportNameToPackageDependencyMapping(), null,
				"name2package", null, 0, -1, IBeXModelMetadata.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(importNameToPackageDependencyMappingEClass, Map.Entry.class, "ImportNameToPackageDependencyMapping",
				!IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getImportNameToPackageDependencyMapping_Key(), ecorePackage.getEString(), "key", null, 0, 1,
				Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getImportNameToPackageDependencyMapping_Value(), this.getEPackageDependency(), null, "value",
				null, 1, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ePackageDependencyEClass, EPackageDependency.class, "EPackageDependency", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEPackageDependency_SimpleName(), ecorePackage.getEString(), "simpleName", null, 0, 1,
				EPackageDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getEPackageDependency_HasAlias(), ecorePackage.getEBoolean(), "hasAlias", null, 0, 1,
				EPackageDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getEPackageDependency_Alias(), ecorePackage.getEString(), "alias", null, 0, 1,
				EPackageDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getEPackageDependency_FullyQualifiedName(), ecorePackage.getEString(), "fullyQualifiedName",
				null, 0, 1, EPackageDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEPackageDependency_Package(), ecorePackage.getEPackage(), null, "package", null, 1, 1,
				EPackageDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEPackageDependency_PackageURI(), ecorePackage.getEString(), "packageURI", null, 0, 1,
				EPackageDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getEPackageDependency_PackageHasProject(), ecorePackage.getEBoolean(), "packageHasProject", null,
				0, 1, EPackageDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEPackageDependency_ProjectLocation(), ecorePackage.getEString(), "projectLocation", null, 0,
				1, EPackageDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEPackageDependency_FactoryClassName(), ecorePackage.getEString(), "factoryClassName", null, 0,
				1, EPackageDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEPackageDependency_PackageClassName(), ecorePackage.getEString(), "packageClassName", null, 0,
				1, EPackageDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEPackageDependency_EcoreURI(), ecorePackage.getEString(), "ecoreURI", null, 0, 1,
				EPackageDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getEPackageDependency_EcoreHasLocation(), ecorePackage.getEBoolean(), "ecoreHasLocation", null,
				0, 1, EPackageDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEPackageDependency_EcoreLocation(), ecorePackage.getEString(), "ecoreLocation", null, 0, 1,
				EPackageDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getEPackageDependency_GenmodelURI(), ecorePackage.getEString(), "genmodelURI", null, 0, 1,
				EPackageDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getEPackageDependency_GenmodelHasLocation(), ecorePackage.getEBoolean(), "genmodelHasLocation",
				null, 0, 1, EPackageDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEPackageDependency_GenmodelLocation(), ecorePackage.getEString(), "genmodelLocation", null, 0,
				1, EPackageDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEPackageDependency_Name2classifier(), this.getNameToEClassifierMapping(), null,
				"name2classifier", null, 0, -1, EPackageDependency.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(nameToEClassifierMappingEClass, Map.Entry.class, "NameToEClassifierMapping", !IS_ABSTRACT,
				!IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNameToEClassifierMapping_Key(), ecorePackage.getEString(), "key", null, 0, 1, Map.Entry.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNameToEClassifierMapping_Value(), ecorePackage.getEClassifier(), null, "value", null, 1, 1,
				Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXFeatureConfigEClass, IBeXFeatureConfig.class, "IBeXFeatureConfig", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIBeXFeatureConfig_CountExpressions(), ecorePackage.getEBoolean(), "countExpressions", "false",
				0, 1, IBeXFeatureConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIBeXFeatureConfig_ArithmeticExpressions(), ecorePackage.getEBoolean(),
				"arithmeticExpressions", "false", 0, 1, IBeXFeatureConfig.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXPatternSetEClass, IBeXPatternSet.class, "IBeXPatternSet", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXPatternSet_Patterns(), this.getIBeXPattern(), null, "patterns", null, 0, -1,
				IBeXPatternSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXNodeSetEClass, IBeXNodeSet.class, "IBeXNodeSet", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXNodeSet_Nodes(), this.getIBeXNode(), null, "nodes", null, 0, -1, IBeXNodeSet.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXEdgeSetEClass, IBeXEdgeSet.class, "IBeXEdgeSet", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXEdgeSet_Edges(), this.getIBeXEdge(), null, "edges", null, 0, -1, IBeXEdgeSet.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXNodeEClass, IBeXNode.class, "IBeXNode", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXNode_Type(), ecorePackage.getEClass(), null, "type", null, 0, 1, IBeXNode.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXNode_IncomingEdges(), this.getIBeXEdge(), this.getIBeXEdge_Target(), "incomingEdges",
				null, 0, -1, IBeXNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXNode_OutgoingEdges(), this.getIBeXEdge(), this.getIBeXEdge_Source(), "outgoingEdges",
				null, 0, -1, IBeXNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXEdgeEClass, IBeXEdge.class, "IBeXEdge", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXEdge_Type(), ecorePackage.getEReference(), null, "type", null, 0, 1, IBeXEdge.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXEdge_Source(), this.getIBeXNode(), this.getIBeXNode_OutgoingEdges(), "source", null, 1, 1,
				IBeXEdge.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXEdge_Target(), this.getIBeXNode(), this.getIBeXNode_IncomingEdges(), "target", null, 1, 1,
				IBeXEdge.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXPatternEClass, IBeXPattern.class, "IBeXPattern", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXPattern_Dependencies(), this.getIBeXPattern(), null, "dependencies", null, 0, -1,
				IBeXPattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXPattern_SignatureNodes(), this.getIBeXNode(), null, "signatureNodes", null, 0, -1,
				IBeXPattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXPattern_LocalNodes(), this.getIBeXNode(), null, "localNodes", null, 0, -1,
				IBeXPattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXPattern_Edges(), this.getIBeXEdge(), null, "edges", null, 0, -1, IBeXPattern.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXPattern_AttributeConstraints(), theIBeXCoreArithmeticPackage.getRelationalExpression(),
				null, "attributeConstraints", null, 0, -1, IBeXPattern.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXPattern_InjectivityConstraints(), this.getIBeXInjectivityConstraint(), null,
				"injectivityConstraints", null, 0, -1, IBeXPattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXPattern_Invocations(), this.getIBeXPatternInvocation(), null, "invocations", null, 0, -1,
				IBeXPattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXInjectivityConstraintEClass, IBeXInjectivityConstraint.class, "IBeXInjectivityConstraint",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXInjectivityConstraint_Nodes(), this.getIBeXNode(), null, "nodes", null, 2, 2,
				IBeXInjectivityConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXPatternInvocationEClass, IBeXPatternInvocation.class, "IBeXPatternInvocation", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIBeXPatternInvocation_Positive(), ecorePackage.getEBoolean(), "positive", null, 0, 1,
				IBeXPatternInvocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXPatternInvocation_InvokedBy(), this.getIBeXPattern(), null, "invokedBy", null, 1, 1,
				IBeXPatternInvocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXPatternInvocation_Invocation(), this.getIBeXPattern(), null, "invocation", null, 1, 1,
				IBeXPatternInvocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXPatternInvocation_Mapping(), this.getIBeXNodeMapping(), null, "mapping", null, 0, -1,
				IBeXPatternInvocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXNodeMappingEClass, Map.Entry.class, "IBeXNodeMapping", !IS_ABSTRACT, !IS_INTERFACE,
				!IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXNodeMapping_Key(), this.getIBeXNode(), null, "key", null, 1, 1, Map.Entry.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXNodeMapping_Value(), this.getIBeXNode(), null, "value", null, 1, 1, Map.Entry.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXEnumValueEClass, IBeXEnumValue.class, "IBeXEnumValue", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXEnumValue_Literal(), ecorePackage.getEEnumLiteral(), null, "literal", null, 0, 1,
				IBeXEnumValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXStringValueEClass, IBeXStringValue.class, "IBeXStringValue", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIBeXStringValue_Value(), ecorePackage.getEString(), "value", null, 0, 1,
				IBeXStringValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(iBeXBooleanValueEClass, IBeXBooleanValue.class, "IBeXBooleanValue", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIBeXBooleanValue_Value(), ecorePackage.getEBoolean(), "value", null, 0, 1,
				IBeXBooleanValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(iBeXAttributeValueEClass, IBeXAttributeValue.class, "IBeXAttributeValue", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXAttributeValue_Node(), this.getIBeXNode(), null, "node", null, 1, 1,
				IBeXAttributeValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXAttributeValue_Attribute(), ecorePackage.getEAttribute(), null, "attribute", null, 0, 1,
				IBeXAttributeValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXMatchCountValueEClass, IBeXMatchCountValue.class, "IBeXMatchCountValue", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXMatchCountValue_Invocation(), this.getIBeXPatternInvocation(), null, "invocation", null,
				1, 1, IBeXMatchCountValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXRuleEClass, IBeXRule.class, "IBeXRule", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXRule_Precondition(), this.getIBeXPattern(), null, "precondition", null, 1, 1,
				IBeXRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXRule_Postcondition(), this.getIBeXPattern(), null, "postcondition", null, 1, 1,
				IBeXRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXRule_Creation(), this.getIBeXRuleDelta(), null, "creation", null, 0, 1, IBeXRule.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXRule_Deletion(), this.getIBeXRuleDelta(), null, "deletion", null, 0, 1, IBeXRule.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXRule_AttributeAssignments(), this.getIBeXAttributeAssignment(), null,
				"attributeAssignments", null, 0, -1, IBeXRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXRule_AllNodes(), this.getIBeXNode(), null, "allNodes", null, 0, -1, IBeXRule.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXRule_AllEdges(), this.getIBeXEdge(), null, "allEdges", null, 0, -1, IBeXRule.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXRuleDeltaEClass, IBeXRuleDelta.class, "IBeXRuleDelta", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXRuleDelta_Nodes(), this.getIBeXNode(), null, "nodes", null, 0, -1, IBeXRuleDelta.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXRuleDelta_Edges(), this.getIBeXEdge(), null, "edges", null, 0, -1, IBeXRuleDelta.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXAttributeAssignmentEClass, IBeXAttributeAssignment.class, "IBeXAttributeAssignment",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXAttributeAssignment_Node(), this.getIBeXNode(), null, "node", null, 1, 1,
				IBeXAttributeAssignment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXAttributeAssignment_Attribute(), ecorePackage.getEAttribute(), null, "attribute", null, 0,
				1, IBeXAttributeAssignment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXAttributeAssignment_Value(), theIBeXCoreArithmeticPackage.getValueExpression(), null,
				"value", null, 1, 1, IBeXAttributeAssignment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //IBeXCoreModelPackageImpl
