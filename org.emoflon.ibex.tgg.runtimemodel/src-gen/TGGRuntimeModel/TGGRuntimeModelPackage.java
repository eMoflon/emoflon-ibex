/**
 */
package TGGRuntimeModel;

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
 * @see TGGRuntimeModel.TGGRuntimeModelFactory
 * @model kind="package"
 * @generated
 */
public interface TGGRuntimeModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "TGGRuntimeModel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eMoflon.org/TGGRuntimeModel";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "TGGRuntimeModel";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TGGRuntimeModelPackage eINSTANCE = TGGRuntimeModel.impl.TGGRuntimeModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link TGGRuntimeModel.impl.TempContainerImpl <em>Temp Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see TGGRuntimeModel.impl.TempContainerImpl
	 * @see TGGRuntimeModel.impl.TGGRuntimeModelPackageImpl#getTempContainer()
	 * @generated
	 */
	int TEMP_CONTAINER = 0;

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
	 * The meta object id for the '{@link TGGRuntimeModel.impl.ProtocolImpl <em>Protocol</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see TGGRuntimeModel.impl.ProtocolImpl
	 * @see TGGRuntimeModel.impl.TGGRuntimeModelPackageImpl#getProtocol()
	 * @generated
	 */
	int PROTOCOL = 1;

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
	 * The meta object id for the '{@link TGGRuntimeModel.impl.TGGRuleApplicationImpl <em>TGG Rule Application</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see TGGRuntimeModel.impl.TGGRuleApplicationImpl
	 * @see TGGRuntimeModel.impl.TGGRuntimeModelPackageImpl#getTGGRuleApplication()
	 * @generated
	 */
	int TGG_RULE_APPLICATION = 2;

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
	 * The meta object id for the '{@link TGGRuntimeModel.impl.CorrespondenceSetImpl <em>Correspondence Set</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see TGGRuntimeModel.impl.CorrespondenceSetImpl
	 * @see TGGRuntimeModel.impl.TGGRuntimeModelPackageImpl#getCorrespondenceSet()
	 * @generated
	 */
	int CORRESPONDENCE_SET = 3;

	/**
	 * The feature id for the '<em><b>Correspondences</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORRESPONDENCE_SET__CORRESPONDENCES = 0;

	/**
	 * The number of structural features of the '<em>Correspondence Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORRESPONDENCE_SET_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Correspondence Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORRESPONDENCE_SET_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link TGGRuntimeModel.impl.CorrespondenceImpl <em>Correspondence</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see TGGRuntimeModel.impl.CorrespondenceImpl
	 * @see TGGRuntimeModel.impl.TGGRuntimeModelPackageImpl#getCorrespondence()
	 * @generated
	 */
	int CORRESPONDENCE = 4;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORRESPONDENCE__SOURCE = 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORRESPONDENCE__TARGET = 1;

	/**
	 * The number of structural features of the '<em>Correspondence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORRESPONDENCE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Correspondence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CORRESPONDENCE_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link TGGRuntimeModel.TempContainer <em>Temp Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Temp Container</em>'.
	 * @see TGGRuntimeModel.TempContainer
	 * @generated
	 */
	EClass getTempContainer();

	/**
	 * Returns the meta object for the containment reference list '{@link TGGRuntimeModel.TempContainer#getObjects <em>Objects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Objects</em>'.
	 * @see TGGRuntimeModel.TempContainer#getObjects()
	 * @see #getTempContainer()
	 * @generated
	 */
	EReference getTempContainer_Objects();

	/**
	 * Returns the meta object for class '{@link TGGRuntimeModel.Protocol <em>Protocol</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Protocol</em>'.
	 * @see TGGRuntimeModel.Protocol
	 * @generated
	 */
	EClass getProtocol();

	/**
	 * Returns the meta object for the containment reference list '{@link TGGRuntimeModel.Protocol#getSteps <em>Steps</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Steps</em>'.
	 * @see TGGRuntimeModel.Protocol#getSteps()
	 * @see #getProtocol()
	 * @generated
	 */
	EReference getProtocol_Steps();

	/**
	 * Returns the meta object for class '{@link TGGRuntimeModel.TGGRuleApplication <em>TGG Rule Application</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Rule Application</em>'.
	 * @see TGGRuntimeModel.TGGRuleApplication
	 * @generated
	 */
	EClass getTGGRuleApplication();

	/**
	 * Returns the meta object for the container reference '{@link TGGRuntimeModel.TGGRuleApplication#getProtocol <em>Protocol</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Protocol</em>'.
	 * @see TGGRuntimeModel.TGGRuleApplication#getProtocol()
	 * @see #getTGGRuleApplication()
	 * @generated
	 */
	EReference getTGGRuleApplication_Protocol();

	/**
	 * Returns the meta object for class '{@link TGGRuntimeModel.CorrespondenceSet <em>Correspondence Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Correspondence Set</em>'.
	 * @see TGGRuntimeModel.CorrespondenceSet
	 * @generated
	 */
	EClass getCorrespondenceSet();

	/**
	 * Returns the meta object for the containment reference list '{@link TGGRuntimeModel.CorrespondenceSet#getCorrespondences <em>Correspondences</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Correspondences</em>'.
	 * @see TGGRuntimeModel.CorrespondenceSet#getCorrespondences()
	 * @see #getCorrespondenceSet()
	 * @generated
	 */
	EReference getCorrespondenceSet_Correspondences();

	/**
	 * Returns the meta object for class '{@link TGGRuntimeModel.Correspondence <em>Correspondence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Correspondence</em>'.
	 * @see TGGRuntimeModel.Correspondence
	 * @generated
	 */
	EClass getCorrespondence();

	/**
	 * Returns the meta object for the reference '{@link TGGRuntimeModel.Correspondence#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see TGGRuntimeModel.Correspondence#getSource()
	 * @see #getCorrespondence()
	 * @generated
	 */
	EReference getCorrespondence_Source();

	/**
	 * Returns the meta object for the reference '{@link TGGRuntimeModel.Correspondence#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see TGGRuntimeModel.Correspondence#getTarget()
	 * @see #getCorrespondence()
	 * @generated
	 */
	EReference getCorrespondence_Target();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TGGRuntimeModelFactory getTGGRuntimeModelFactory();

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
		 * The meta object literal for the '{@link TGGRuntimeModel.impl.TempContainerImpl <em>Temp Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see TGGRuntimeModel.impl.TempContainerImpl
		 * @see TGGRuntimeModel.impl.TGGRuntimeModelPackageImpl#getTempContainer()
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
		 * The meta object literal for the '{@link TGGRuntimeModel.impl.ProtocolImpl <em>Protocol</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see TGGRuntimeModel.impl.ProtocolImpl
		 * @see TGGRuntimeModel.impl.TGGRuntimeModelPackageImpl#getProtocol()
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
		 * The meta object literal for the '{@link TGGRuntimeModel.impl.TGGRuleApplicationImpl <em>TGG Rule Application</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see TGGRuntimeModel.impl.TGGRuleApplicationImpl
		 * @see TGGRuntimeModel.impl.TGGRuntimeModelPackageImpl#getTGGRuleApplication()
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
		 * The meta object literal for the '{@link TGGRuntimeModel.impl.CorrespondenceSetImpl <em>Correspondence Set</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see TGGRuntimeModel.impl.CorrespondenceSetImpl
		 * @see TGGRuntimeModel.impl.TGGRuntimeModelPackageImpl#getCorrespondenceSet()
		 * @generated
		 */
		EClass CORRESPONDENCE_SET = eINSTANCE.getCorrespondenceSet();

		/**
		 * The meta object literal for the '<em><b>Correspondences</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CORRESPONDENCE_SET__CORRESPONDENCES = eINSTANCE.getCorrespondenceSet_Correspondences();

		/**
		 * The meta object literal for the '{@link TGGRuntimeModel.impl.CorrespondenceImpl <em>Correspondence</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see TGGRuntimeModel.impl.CorrespondenceImpl
		 * @see TGGRuntimeModel.impl.TGGRuntimeModelPackageImpl#getCorrespondence()
		 * @generated
		 */
		EClass CORRESPONDENCE = eINSTANCE.getCorrespondence();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CORRESPONDENCE__SOURCE = eINSTANCE.getCorrespondence_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CORRESPONDENCE__TARGET = eINSTANCE.getCorrespondence_Target();

	}

} //TGGRuntimeModelPackage
