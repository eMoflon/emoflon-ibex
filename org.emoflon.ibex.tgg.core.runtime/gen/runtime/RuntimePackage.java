/**
 */
package runtime;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see runtime.RuntimeFactory
 * @model kind="package"
 * @generated
 */
public interface RuntimePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "runtime";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "platform:/plugin/org.emoflon.ibex.tgg.core.runtime/model/Runtime.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "org.emoflon.ibex.tgg.core.runtime";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RuntimePackage eINSTANCE = runtime.impl.RuntimePackageImpl.init();

	/**
	 * The meta object id for the '{@link runtime.impl.ProtocolImpl <em>Protocol</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see runtime.impl.ProtocolImpl
	 * @see runtime.impl.RuntimePackageImpl#getProtocol()
	 * @generated
	 */
	int PROTOCOL = 0;

	/**
	 * The feature id for the '<em><b>Steps</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL__STEPS = 0;

	/**
	 * The number of structural features of the '<em>Protocol</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Protocol</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROTOCOL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link runtime.impl.TGGRuleApplicationImpl <em>TGG Rule Application</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see runtime.impl.TGGRuleApplicationImpl
	 * @see runtime.impl.RuntimePackageImpl#getTGGRuleApplication()
	 * @generated
	 */
	int TGG_RULE_APPLICATION = 1;

	/**
	 * The feature id for the '<em><b>Created Src</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_APPLICATION__CREATED_SRC = 0;

	/**
	 * The feature id for the '<em><b>Created Trg</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_APPLICATION__CREATED_TRG = 1;

	/**
	 * The feature id for the '<em><b>Created Corr</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_APPLICATION__CREATED_CORR = 2;

	/**
	 * The feature id for the '<em><b>Context Src</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_APPLICATION__CONTEXT_SRC = 3;

	/**
	 * The feature id for the '<em><b>Context Trg</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_APPLICATION__CONTEXT_TRG = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_APPLICATION__NAME = 5;

	/**
	 * The feature id for the '<em><b>Protocol</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_APPLICATION__PROTOCOL = 6;

	/**
	 * The feature id for the '<em><b>Final</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_APPLICATION__FINAL = 7;

	/**
	 * The feature id for the '<em><b>Node Mappings</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_APPLICATION__NODE_MAPPINGS = 8;

	/**
	 * The feature id for the '<em><b>Amalgamated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_APPLICATION__AMALGAMATED = 9;

	/**
	 * The number of structural features of the '<em>TGG Rule Application</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_APPLICATION_FEATURE_COUNT = 10;

	/**
	 * The number of operations of the '<em>TGG Rule Application</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_APPLICATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link runtime.impl.NodeMappingImpl <em>Node Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see runtime.impl.NodeMappingImpl
	 * @see runtime.impl.RuntimePackageImpl#getNodeMapping()
	 * @generated
	 */
	int NODE_MAPPING = 2;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_MAPPING__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_MAPPING__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Node Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_MAPPING_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Node Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_MAPPING_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link runtime.impl.TempContainerImpl <em>Temp Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see runtime.impl.TempContainerImpl
	 * @see runtime.impl.RuntimePackageImpl#getTempContainer()
	 * @generated
	 */
	int TEMP_CONTAINER = 3;

	/**
	 * The feature id for the '<em><b>Objects</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMP_CONTAINER__OBJECTS = 0;

	/**
	 * The number of structural features of the '<em>Temp Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMP_CONTAINER_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Temp Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMP_CONTAINER_OPERATION_COUNT = 0;

	/**
	 * Returns the meta object for class '{@link runtime.Protocol <em>Protocol</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Protocol</em>'.
	 * @see runtime.Protocol
	 * @generated
	 */
	EClass getProtocol();

	/**
	 * Returns the meta object for the containment reference list '{@link runtime.Protocol#getSteps <em>Steps</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Steps</em>'.
	 * @see runtime.Protocol#getSteps()
	 * @see #getProtocol()
	 * @generated
	 */
	EReference getProtocol_Steps();

	/**
	 * Returns the meta object for class '{@link runtime.TGGRuleApplication <em>TGG Rule Application</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Rule Application</em>'.
	 * @see runtime.TGGRuleApplication
	 * @generated
	 */
	EClass getTGGRuleApplication();

	/**
	 * Returns the meta object for the reference list '{@link runtime.TGGRuleApplication#getCreatedSrc <em>Created Src</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Created Src</em>'.
	 * @see runtime.TGGRuleApplication#getCreatedSrc()
	 * @see #getTGGRuleApplication()
	 * @generated
	 */
	EReference getTGGRuleApplication_CreatedSrc();

	/**
	 * Returns the meta object for the reference list '{@link runtime.TGGRuleApplication#getCreatedTrg <em>Created Trg</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Created Trg</em>'.
	 * @see runtime.TGGRuleApplication#getCreatedTrg()
	 * @see #getTGGRuleApplication()
	 * @generated
	 */
	EReference getTGGRuleApplication_CreatedTrg();

	/**
	 * Returns the meta object for the reference list '{@link runtime.TGGRuleApplication#getCreatedCorr <em>Created Corr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Created Corr</em>'.
	 * @see runtime.TGGRuleApplication#getCreatedCorr()
	 * @see #getTGGRuleApplication()
	 * @generated
	 */
	EReference getTGGRuleApplication_CreatedCorr();

	/**
	 * Returns the meta object for the reference list '{@link runtime.TGGRuleApplication#getContextSrc <em>Context Src</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Context Src</em>'.
	 * @see runtime.TGGRuleApplication#getContextSrc()
	 * @see #getTGGRuleApplication()
	 * @generated
	 */
	EReference getTGGRuleApplication_ContextSrc();

	/**
	 * Returns the meta object for the reference list '{@link runtime.TGGRuleApplication#getContextTrg <em>Context Trg</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Context Trg</em>'.
	 * @see runtime.TGGRuleApplication#getContextTrg()
	 * @see #getTGGRuleApplication()
	 * @generated
	 */
	EReference getTGGRuleApplication_ContextTrg();

	/**
	 * Returns the meta object for the attribute '{@link runtime.TGGRuleApplication#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see runtime.TGGRuleApplication#getName()
	 * @see #getTGGRuleApplication()
	 * @generated
	 */
	EAttribute getTGGRuleApplication_Name();

	/**
	 * Returns the meta object for the container reference '{@link runtime.TGGRuleApplication#getProtocol <em>Protocol</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Protocol</em>'.
	 * @see runtime.TGGRuleApplication#getProtocol()
	 * @see #getTGGRuleApplication()
	 * @generated
	 */
	EReference getTGGRuleApplication_Protocol();

	/**
	 * Returns the meta object for the attribute '{@link runtime.TGGRuleApplication#isFinal <em>Final</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Final</em>'.
	 * @see runtime.TGGRuleApplication#isFinal()
	 * @see #getTGGRuleApplication()
	 * @generated
	 */
	EAttribute getTGGRuleApplication_Final();

	/**
	 * Returns the meta object for the map '{@link runtime.TGGRuleApplication#getNodeMappings <em>Node Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Node Mappings</em>'.
	 * @see runtime.TGGRuleApplication#getNodeMappings()
	 * @see #getTGGRuleApplication()
	 * @generated
	 */
	EReference getTGGRuleApplication_NodeMappings();

	/**
	 * Returns the meta object for the attribute '{@link runtime.TGGRuleApplication#isAmalgamated <em>Amalgamated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Amalgamated</em>'.
	 * @see runtime.TGGRuleApplication#isAmalgamated()
	 * @see #getTGGRuleApplication()
	 * @generated
	 */
	EAttribute getTGGRuleApplication_Amalgamated();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>Node Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Node Mapping</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.EString"
	 *        valueType="org.eclipse.emf.ecore.EObject"
	 * @generated
	 */
	EClass getNodeMapping();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getNodeMapping()
	 * @generated
	 */
	EAttribute getNodeMapping_Key();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getNodeMapping()
	 * @generated
	 */
	EReference getNodeMapping_Value();

	/**
	 * Returns the meta object for class '{@link runtime.TempContainer <em>Temp Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Temp Container</em>'.
	 * @see runtime.TempContainer
	 * @generated
	 */
	EClass getTempContainer();

	/**
	 * Returns the meta object for the containment reference list '{@link runtime.TempContainer#getObjects <em>Objects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Objects</em>'.
	 * @see runtime.TempContainer#getObjects()
	 * @see #getTempContainer()
	 * @generated
	 */
	EReference getTempContainer_Objects();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	RuntimeFactory getRuntimeFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link runtime.impl.ProtocolImpl <em>Protocol</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see runtime.impl.ProtocolImpl
		 * @see runtime.impl.RuntimePackageImpl#getProtocol()
		 * @generated
		 */
		EClass PROTOCOL = eINSTANCE.getProtocol();

		/**
		 * The meta object literal for the '<em><b>Steps</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROTOCOL__STEPS = eINSTANCE.getProtocol_Steps();

		/**
		 * The meta object literal for the '{@link runtime.impl.TGGRuleApplicationImpl <em>TGG Rule Application</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see runtime.impl.TGGRuleApplicationImpl
		 * @see runtime.impl.RuntimePackageImpl#getTGGRuleApplication()
		 * @generated
		 */
		EClass TGG_RULE_APPLICATION = eINSTANCE.getTGGRuleApplication();

		/**
		 * The meta object literal for the '<em><b>Created Src</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE_APPLICATION__CREATED_SRC = eINSTANCE.getTGGRuleApplication_CreatedSrc();

		/**
		 * The meta object literal for the '<em><b>Created Trg</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE_APPLICATION__CREATED_TRG = eINSTANCE.getTGGRuleApplication_CreatedTrg();

		/**
		 * The meta object literal for the '<em><b>Created Corr</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE_APPLICATION__CREATED_CORR = eINSTANCE.getTGGRuleApplication_CreatedCorr();

		/**
		 * The meta object literal for the '<em><b>Context Src</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE_APPLICATION__CONTEXT_SRC = eINSTANCE.getTGGRuleApplication_ContextSrc();

		/**
		 * The meta object literal for the '<em><b>Context Trg</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE_APPLICATION__CONTEXT_TRG = eINSTANCE.getTGGRuleApplication_ContextTrg();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TGG_RULE_APPLICATION__NAME = eINSTANCE.getTGGRuleApplication_Name();

		/**
		 * The meta object literal for the '<em><b>Protocol</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE_APPLICATION__PROTOCOL = eINSTANCE.getTGGRuleApplication_Protocol();

		/**
		 * The meta object literal for the '<em><b>Final</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TGG_RULE_APPLICATION__FINAL = eINSTANCE.getTGGRuleApplication_Final();

		/**
		 * The meta object literal for the '<em><b>Node Mappings</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE_APPLICATION__NODE_MAPPINGS = eINSTANCE.getTGGRuleApplication_NodeMappings();

		/**
		 * The meta object literal for the '<em><b>Amalgamated</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TGG_RULE_APPLICATION__AMALGAMATED = eINSTANCE.getTGGRuleApplication_Amalgamated();

		/**
		 * The meta object literal for the '{@link runtime.impl.NodeMappingImpl <em>Node Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see runtime.impl.NodeMappingImpl
		 * @see runtime.impl.RuntimePackageImpl#getNodeMapping()
		 * @generated
		 */
		EClass NODE_MAPPING = eINSTANCE.getNodeMapping();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NODE_MAPPING__KEY = eINSTANCE.getNodeMapping_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE_MAPPING__VALUE = eINSTANCE.getNodeMapping_Value();

		/**
		 * The meta object literal for the '{@link runtime.impl.TempContainerImpl <em>Temp Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see runtime.impl.TempContainerImpl
		 * @see runtime.impl.RuntimePackageImpl#getTempContainer()
		 * @generated
		 */
		EClass TEMP_CONTAINER = eINSTANCE.getTempContainer();

		/**
		 * The meta object literal for the '<em><b>Objects</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEMP_CONTAINER__OBJECTS = eINSTANCE.getTempContainer_Objects();

	}

} //RuntimePackage
