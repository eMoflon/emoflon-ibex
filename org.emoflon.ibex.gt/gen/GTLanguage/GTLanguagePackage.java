/**
 */
package GTLanguage;

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
 * <!-- begin-model-doc -->
 * Model for the Graph Transformation API.
 * <!-- end-model-doc -->
 * @see GTLanguage.GTLanguageFactory
 * @model kind="package"
 * @generated
 */
public interface GTLanguagePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "GTLanguage";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "platform:/resource/org.emoflon.ibex.gt/model/Gt.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "GTLanguage";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	GTLanguagePackage eINSTANCE = GTLanguage.impl.GTLanguagePackageImpl.init();

	/**
	 * The meta object id for the '{@link GTLanguage.impl.GTNamedElementImpl <em>GT Named Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see GTLanguage.impl.GTNamedElementImpl
	 * @see GTLanguage.impl.GTLanguagePackageImpl#getGTNamedElement()
	 * @generated
	 */
	int GT_NAMED_ELEMENT = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_NAMED_ELEMENT__NAME = 0;

	/**
	 * The number of structural features of the '<em>GT Named Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_NAMED_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>GT Named Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_NAMED_ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link GTLanguage.impl.GTNodeImpl <em>GT Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see GTLanguage.impl.GTNodeImpl
	 * @see GTLanguage.impl.GTLanguagePackageImpl#getGTNode()
	 * @generated
	 */
	int GT_NODE = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_NODE__NAME = GT_NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_NODE__TYPE = GT_NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>GT Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_NODE_FEATURE_COUNT = GT_NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>GT Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_NODE_OPERATION_COUNT = GT_NAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link GTLanguage.impl.GTParameterImpl <em>GT Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see GTLanguage.impl.GTParameterImpl
	 * @see GTLanguage.impl.GTLanguagePackageImpl#getGTParameter()
	 * @generated
	 */
	int GT_PARAMETER = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_PARAMETER__NAME = GT_NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_PARAMETER__TYPE = GT_NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>GT Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_PARAMETER_FEATURE_COUNT = GT_NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>GT Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_PARAMETER_OPERATION_COUNT = GT_NAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link GTLanguage.impl.GTRuleImpl <em>GT Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see GTLanguage.impl.GTRuleImpl
	 * @see GTLanguage.impl.GTLanguagePackageImpl#getGTRule()
	 * @generated
	 */
	int GT_RULE = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_RULE__NAME = GT_NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_RULE__DOCUMENTATION = GT_NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Executable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_RULE__EXECUTABLE = GT_NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_RULE__NODES = GT_NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_RULE__PARAMETERS = GT_NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Rule Nodes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_RULE__RULE_NODES = GT_NAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>GT Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_RULE_FEATURE_COUNT = GT_NAMED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The number of operations of the '<em>GT Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_RULE_OPERATION_COUNT = GT_NAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link GTLanguage.impl.GTRuleSetImpl <em>GT Rule Set</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see GTLanguage.impl.GTRuleSetImpl
	 * @see GTLanguage.impl.GTLanguagePackageImpl#getGTRuleSet()
	 * @generated
	 */
	int GT_RULE_SET = 4;

	/**
	 * The feature id for the '<em><b>Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_RULE_SET__RULES = 0;

	/**
	 * The number of structural features of the '<em>GT Rule Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_RULE_SET_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>GT Rule Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_RULE_SET_OPERATION_COUNT = 0;

	/**
	 * Returns the meta object for class '{@link GTLanguage.GTNamedElement <em>GT Named Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>GT Named Element</em>'.
	 * @see GTLanguage.GTNamedElement
	 * @generated
	 */
	EClass getGTNamedElement();

	/**
	 * Returns the meta object for the attribute '{@link GTLanguage.GTNamedElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see GTLanguage.GTNamedElement#getName()
	 * @see #getGTNamedElement()
	 * @generated
	 */
	EAttribute getGTNamedElement_Name();

	/**
	 * Returns the meta object for class '{@link GTLanguage.GTNode <em>GT Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>GT Node</em>'.
	 * @see GTLanguage.GTNode
	 * @generated
	 */
	EClass getGTNode();

	/**
	 * Returns the meta object for the reference '{@link GTLanguage.GTNode#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see GTLanguage.GTNode#getType()
	 * @see #getGTNode()
	 * @generated
	 */
	EReference getGTNode_Type();

	/**
	 * Returns the meta object for class '{@link GTLanguage.GTParameter <em>GT Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>GT Parameter</em>'.
	 * @see GTLanguage.GTParameter
	 * @generated
	 */
	EClass getGTParameter();

	/**
	 * Returns the meta object for the reference '{@link GTLanguage.GTParameter#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see GTLanguage.GTParameter#getType()
	 * @see #getGTParameter()
	 * @generated
	 */
	EReference getGTParameter_Type();

	/**
	 * Returns the meta object for class '{@link GTLanguage.GTRule <em>GT Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>GT Rule</em>'.
	 * @see GTLanguage.GTRule
	 * @generated
	 */
	EClass getGTRule();

	/**
	 * Returns the meta object for the attribute '{@link GTLanguage.GTRule#getDocumentation <em>Documentation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Documentation</em>'.
	 * @see GTLanguage.GTRule#getDocumentation()
	 * @see #getGTRule()
	 * @generated
	 */
	EAttribute getGTRule_Documentation();

	/**
	 * Returns the meta object for the attribute '{@link GTLanguage.GTRule#isExecutable <em>Executable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Executable</em>'.
	 * @see GTLanguage.GTRule#isExecutable()
	 * @see #getGTRule()
	 * @generated
	 */
	EAttribute getGTRule_Executable();

	/**
	 * Returns the meta object for the containment reference list '{@link GTLanguage.GTRule#getNodes <em>Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Nodes</em>'.
	 * @see GTLanguage.GTRule#getNodes()
	 * @see #getGTRule()
	 * @generated
	 */
	EReference getGTRule_Nodes();

	/**
	 * Returns the meta object for the containment reference list '{@link GTLanguage.GTRule#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see GTLanguage.GTRule#getParameters()
	 * @see #getGTRule()
	 * @generated
	 */
	EReference getGTRule_Parameters();

	/**
	 * Returns the meta object for the reference list '{@link GTLanguage.GTRule#getRuleNodes <em>Rule Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Rule Nodes</em>'.
	 * @see GTLanguage.GTRule#getRuleNodes()
	 * @see #getGTRule()
	 * @generated
	 */
	EReference getGTRule_RuleNodes();

	/**
	 * Returns the meta object for class '{@link GTLanguage.GTRuleSet <em>GT Rule Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>GT Rule Set</em>'.
	 * @see GTLanguage.GTRuleSet
	 * @generated
	 */
	EClass getGTRuleSet();

	/**
	 * Returns the meta object for the containment reference list '{@link GTLanguage.GTRuleSet#getRules <em>Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Rules</em>'.
	 * @see GTLanguage.GTRuleSet#getRules()
	 * @see #getGTRuleSet()
	 * @generated
	 */
	EReference getGTRuleSet_Rules();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	GTLanguageFactory getGTLanguageFactory();

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
		 * The meta object literal for the '{@link GTLanguage.impl.GTNamedElementImpl <em>GT Named Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see GTLanguage.impl.GTNamedElementImpl
		 * @see GTLanguage.impl.GTLanguagePackageImpl#getGTNamedElement()
		 * @generated
		 */
		EClass GT_NAMED_ELEMENT = eINSTANCE.getGTNamedElement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GT_NAMED_ELEMENT__NAME = eINSTANCE.getGTNamedElement_Name();

		/**
		 * The meta object literal for the '{@link GTLanguage.impl.GTNodeImpl <em>GT Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see GTLanguage.impl.GTNodeImpl
		 * @see GTLanguage.impl.GTLanguagePackageImpl#getGTNode()
		 * @generated
		 */
		EClass GT_NODE = eINSTANCE.getGTNode();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GT_NODE__TYPE = eINSTANCE.getGTNode_Type();

		/**
		 * The meta object literal for the '{@link GTLanguage.impl.GTParameterImpl <em>GT Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see GTLanguage.impl.GTParameterImpl
		 * @see GTLanguage.impl.GTLanguagePackageImpl#getGTParameter()
		 * @generated
		 */
		EClass GT_PARAMETER = eINSTANCE.getGTParameter();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GT_PARAMETER__TYPE = eINSTANCE.getGTParameter_Type();

		/**
		 * The meta object literal for the '{@link GTLanguage.impl.GTRuleImpl <em>GT Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see GTLanguage.impl.GTRuleImpl
		 * @see GTLanguage.impl.GTLanguagePackageImpl#getGTRule()
		 * @generated
		 */
		EClass GT_RULE = eINSTANCE.getGTRule();

		/**
		 * The meta object literal for the '<em><b>Documentation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GT_RULE__DOCUMENTATION = eINSTANCE.getGTRule_Documentation();

		/**
		 * The meta object literal for the '<em><b>Executable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GT_RULE__EXECUTABLE = eINSTANCE.getGTRule_Executable();

		/**
		 * The meta object literal for the '<em><b>Nodes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GT_RULE__NODES = eINSTANCE.getGTRule_Nodes();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GT_RULE__PARAMETERS = eINSTANCE.getGTRule_Parameters();

		/**
		 * The meta object literal for the '<em><b>Rule Nodes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GT_RULE__RULE_NODES = eINSTANCE.getGTRule_RuleNodes();

		/**
		 * The meta object literal for the '{@link GTLanguage.impl.GTRuleSetImpl <em>GT Rule Set</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see GTLanguage.impl.GTRuleSetImpl
		 * @see GTLanguage.impl.GTLanguagePackageImpl#getGTRuleSet()
		 * @generated
		 */
		EClass GT_RULE_SET = eINSTANCE.getGTRuleSet();

		/**
		 * The meta object literal for the '<em><b>Rules</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GT_RULE_SET__RULES = eINSTANCE.getGTRuleSet_Rules();

	}

} //GTLanguagePackage
