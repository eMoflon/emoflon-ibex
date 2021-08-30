/**
 */
package runtime;

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
 * <!-- begin-model-doc -->
 * Runtime TGG Protocol Model to track the Transformation Process
 * <!-- end-model-doc -->
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
	String eNS_URI = "platform:/plugin/org.emoflon.ibex.tgg.runtime/model/Runtime.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "org.emoflon.ibex.tgg.runtime";

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
	 * The feature id for the '<em><b>Protocol</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_APPLICATION__PROTOCOL = 0;

	/**
	 * The number of structural features of the '<em>TGG Rule Application</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_APPLICATION_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>TGG Rule Application</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_APPLICATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link runtime.impl.TempContainerImpl <em>Temp Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see runtime.impl.TempContainerImpl
	 * @see runtime.impl.RuntimePackageImpl#getTempContainer()
	 * @generated
	 */
	int TEMP_CONTAINER = 2;

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
	 * The meta object id for the '{@link runtime.impl.CorrespondenceNodeImpl <em>Correspondence Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see runtime.impl.CorrespondenceNodeImpl
	 * @see runtime.impl.RuntimePackageImpl#getCorrespondenceNode()
	 * @generated
	 */
	int CORRESPONDENCE_NODE = 3;

	/**
	 * The number of structural features of the '<em>Correspondence Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORRESPONDENCE_NODE_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Correspondence Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORRESPONDENCE_NODE_OPERATION_COUNT = 0;

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
	 * Returns the meta object for class '{@link runtime.CorrespondenceNode <em>Correspondence Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Correspondence Node</em>'.
	 * @see runtime.CorrespondenceNode
	 * @generated
	 */
	EClass getCorrespondenceNode();

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
		 * The meta object literal for the '<em><b>Protocol</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE_APPLICATION__PROTOCOL = eINSTANCE.getTGGRuleApplication_Protocol();

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

		/**
		 * The meta object literal for the '{@link runtime.impl.CorrespondenceNodeImpl <em>Correspondence Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see runtime.impl.CorrespondenceNodeImpl
		 * @see runtime.impl.RuntimePackageImpl#getCorrespondenceNode()
		 * @generated
		 */
		EClass CORRESPONDENCE_NODE = eINSTANCE.getCorrespondenceNode();

	}

} //RuntimePackage
