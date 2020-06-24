/**
 */
package precedencegraph;

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
 * @see precedencegraph.PrecedencegraphFactory
 * @model kind="package"
 * @generated
 */
public interface PrecedencegraphPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "precedencegraph";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.example.org/precedencegraph";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "precedencegraph";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PrecedencegraphPackage eINSTANCE = precedencegraph.impl.PrecedencegraphPackageImpl.init();

	/**
	 * The meta object id for the '{@link precedencegraph.impl.PrecedenceNodeContainerImpl <em>Precedence Node Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see precedencegraph.impl.PrecedenceNodeContainerImpl
	 * @see precedencegraph.impl.PrecedencegraphPackageImpl#getPrecedenceNodeContainer()
	 * @generated
	 */
	int PRECEDENCE_NODE_CONTAINER = 0;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRECEDENCE_NODE_CONTAINER__NODES = 0;

	/**
	 * The number of structural features of the '<em>Precedence Node Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRECEDENCE_NODE_CONTAINER_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Precedence Node Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRECEDENCE_NODE_CONTAINER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link precedencegraph.impl.PrecedenceNodeImpl <em>Precedence Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see precedencegraph.impl.PrecedenceNodeImpl
	 * @see precedencegraph.impl.PrecedencegraphPackageImpl#getPrecedenceNode()
	 * @generated
	 */
	int PRECEDENCE_NODE = 1;

	/**
	 * The feature id for the '<em><b>Broken</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRECEDENCE_NODE__BROKEN = 0;

	/**
	 * The feature id for the '<em><b>Requires</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRECEDENCE_NODE__REQUIRES = 1;

	/**
	 * The feature id for the '<em><b>Required By</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRECEDENCE_NODE__REQUIRED_BY = 2;

	/**
	 * The feature id for the '<em><b>Match As String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRECEDENCE_NODE__MATCH_AS_STRING = 3;

	/**
	 * The feature id for the '<em><b>Precedencenodecontainer</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRECEDENCE_NODE__PRECEDENCENODECONTAINER = 4;

	/**
	 * The feature id for the '<em><b>Rollback Causes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRECEDENCE_NODE__ROLLBACK_CAUSES = 5;

	/**
	 * The feature id for the '<em><b>Rolledback</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRECEDENCE_NODE__ROLLEDBACK = 6;

	/**
	 * The number of structural features of the '<em>Precedence Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRECEDENCE_NODE_FEATURE_COUNT = 7;

	/**
	 * The number of operations of the '<em>Precedence Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRECEDENCE_NODE_OPERATION_COUNT = 0;

	/**
	 * Returns the meta object for class '{@link precedencegraph.PrecedenceNodeContainer <em>Precedence Node Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Precedence Node Container</em>'.
	 * @see precedencegraph.PrecedenceNodeContainer
	 * @generated
	 */
	EClass getPrecedenceNodeContainer();

	/**
	 * Returns the meta object for the containment reference list '{@link precedencegraph.PrecedenceNodeContainer#getNodes <em>Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Nodes</em>'.
	 * @see precedencegraph.PrecedenceNodeContainer#getNodes()
	 * @see #getPrecedenceNodeContainer()
	 * @generated
	 */
	EReference getPrecedenceNodeContainer_Nodes();

	/**
	 * Returns the meta object for class '{@link precedencegraph.PrecedenceNode <em>Precedence Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Precedence Node</em>'.
	 * @see precedencegraph.PrecedenceNode
	 * @generated
	 */
	EClass getPrecedenceNode();

	/**
	 * Returns the meta object for the attribute '{@link precedencegraph.PrecedenceNode#isBroken <em>Broken</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Broken</em>'.
	 * @see precedencegraph.PrecedenceNode#isBroken()
	 * @see #getPrecedenceNode()
	 * @generated
	 */
	EAttribute getPrecedenceNode_Broken();

	/**
	 * Returns the meta object for the reference list '{@link precedencegraph.PrecedenceNode#getRequires <em>Requires</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Requires</em>'.
	 * @see precedencegraph.PrecedenceNode#getRequires()
	 * @see #getPrecedenceNode()
	 * @generated
	 */
	EReference getPrecedenceNode_Requires();

	/**
	 * Returns the meta object for the reference list '{@link precedencegraph.PrecedenceNode#getRequiredBy <em>Required By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Required By</em>'.
	 * @see precedencegraph.PrecedenceNode#getRequiredBy()
	 * @see #getPrecedenceNode()
	 * @generated
	 */
	EReference getPrecedenceNode_RequiredBy();

	/**
	 * Returns the meta object for the attribute '{@link precedencegraph.PrecedenceNode#getMatchAsString <em>Match As String</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Match As String</em>'.
	 * @see precedencegraph.PrecedenceNode#getMatchAsString()
	 * @see #getPrecedenceNode()
	 * @generated
	 */
	EAttribute getPrecedenceNode_MatchAsString();

	/**
	 * Returns the meta object for the container reference '{@link precedencegraph.PrecedenceNode#getPrecedencenodecontainer <em>Precedencenodecontainer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Precedencenodecontainer</em>'.
	 * @see precedencegraph.PrecedenceNode#getPrecedencenodecontainer()
	 * @see #getPrecedenceNode()
	 * @generated
	 */
	EReference getPrecedenceNode_Precedencenodecontainer();

	/**
	 * Returns the meta object for the reference list '{@link precedencegraph.PrecedenceNode#getRollbackCauses <em>Rollback Causes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Rollback Causes</em>'.
	 * @see precedencegraph.PrecedenceNode#getRollbackCauses()
	 * @see #getPrecedenceNode()
	 * @generated
	 */
	EReference getPrecedenceNode_RollbackCauses();

	/**
	 * Returns the meta object for the reference list '{@link precedencegraph.PrecedenceNode#getRolledback <em>Rolledback</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Rolledback</em>'.
	 * @see precedencegraph.PrecedenceNode#getRolledback()
	 * @see #getPrecedenceNode()
	 * @generated
	 */
	EReference getPrecedenceNode_Rolledback();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	PrecedencegraphFactory getPrecedencegraphFactory();

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
		 * The meta object literal for the '{@link precedencegraph.impl.PrecedenceNodeContainerImpl <em>Precedence Node Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see precedencegraph.impl.PrecedenceNodeContainerImpl
		 * @see precedencegraph.impl.PrecedencegraphPackageImpl#getPrecedenceNodeContainer()
		 * @generated
		 */
		EClass PRECEDENCE_NODE_CONTAINER = eINSTANCE.getPrecedenceNodeContainer();

		/**
		 * The meta object literal for the '<em><b>Nodes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PRECEDENCE_NODE_CONTAINER__NODES = eINSTANCE.getPrecedenceNodeContainer_Nodes();

		/**
		 * The meta object literal for the '{@link precedencegraph.impl.PrecedenceNodeImpl <em>Precedence Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see precedencegraph.impl.PrecedenceNodeImpl
		 * @see precedencegraph.impl.PrecedencegraphPackageImpl#getPrecedenceNode()
		 * @generated
		 */
		EClass PRECEDENCE_NODE = eINSTANCE.getPrecedenceNode();

		/**
		 * The meta object literal for the '<em><b>Broken</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PRECEDENCE_NODE__BROKEN = eINSTANCE.getPrecedenceNode_Broken();

		/**
		 * The meta object literal for the '<em><b>Requires</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PRECEDENCE_NODE__REQUIRES = eINSTANCE.getPrecedenceNode_Requires();

		/**
		 * The meta object literal for the '<em><b>Required By</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PRECEDENCE_NODE__REQUIRED_BY = eINSTANCE.getPrecedenceNode_RequiredBy();

		/**
		 * The meta object literal for the '<em><b>Match As String</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PRECEDENCE_NODE__MATCH_AS_STRING = eINSTANCE.getPrecedenceNode_MatchAsString();

		/**
		 * The meta object literal for the '<em><b>Precedencenodecontainer</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PRECEDENCE_NODE__PRECEDENCENODECONTAINER = eINSTANCE.getPrecedenceNode_Precedencenodecontainer();

		/**
		 * The meta object literal for the '<em><b>Rollback Causes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PRECEDENCE_NODE__ROLLBACK_CAUSES = eINSTANCE.getPrecedenceNode_RollbackCauses();

		/**
		 * The meta object literal for the '<em><b>Rolledback</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PRECEDENCE_NODE__ROLLEDBACK = eINSTANCE.getPrecedenceNode_Rolledback();

	}

} //PrecedencegraphPackage
