/**
 */
package language;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
 * @see language.LanguageFactory
 * @model kind="package"
 * @generated
 */
public interface LanguagePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "language";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "platform:/plugin/org.emoflon.ibex.tgg.core.language/model/Language.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "language";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	LanguagePackage eINSTANCE = language.impl.LanguagePackageImpl.init();

	/**
	 * The meta object id for the '{@link language.impl.TGGNamedElementImpl <em>TGG Named Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see language.impl.TGGNamedElementImpl
	 * @see language.impl.LanguagePackageImpl#getTGGNamedElement()
	 * @generated
	 */
	int TGG_NAMED_ELEMENT = 16;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_NAMED_ELEMENT__NAME = 0;

	/**
	 * The number of structural features of the '<em>TGG Named Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_NAMED_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>TGG Named Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_NAMED_ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link language.impl.TGGImpl <em>TGG</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see language.impl.TGGImpl
	 * @see language.impl.LanguagePackageImpl#getTGG()
	 * @generated
	 */
	int TGG = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG__NAME = TGG_NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Src</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG__SRC = TGG_NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Trg</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG__TRG = TGG_NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Corr</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG__CORR = TGG_NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG__RULES = TGG_NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Attribute Constraint Definition Library</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG__ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY = TGG_NAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>TGG</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_FEATURE_COUNT = TGG_NAMED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The number of operations of the '<em>TGG</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_OPERATION_COUNT = TGG_NAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link language.impl.TGGRuleImpl <em>TGG Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see language.impl.TGGRuleImpl
	 * @see language.impl.LanguagePackageImpl#getTGGRule()
	 * @generated
	 */
	int TGG_RULE = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__NAME = TGG_NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Refines</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__REFINES = TGG_NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Nacs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__NACS = TGG_NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__NODES = TGG_NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Edges</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__EDGES = TGG_NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Attribute Condition Library</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__ATTRIBUTE_CONDITION_LIBRARY = TGG_NAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Abstract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__ABSTRACT = TGG_NAMED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>TGG Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_FEATURE_COUNT = TGG_NAMED_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The number of operations of the '<em>TGG Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_OPERATION_COUNT = TGG_NAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link language.impl.TGGRuleElementImpl <em>TGG Rule Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see language.impl.TGGRuleElementImpl
	 * @see language.impl.LanguagePackageImpl#getTGGRuleElement()
	 * @generated
	 */
	int TGG_RULE_ELEMENT = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_ELEMENT__NAME = TGG_NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Domain Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_ELEMENT__DOMAIN_TYPE = TGG_NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Binding Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_ELEMENT__BINDING_TYPE = TGG_NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>TGG Rule Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_ELEMENT_FEATURE_COUNT = TGG_NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>TGG Rule Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_ELEMENT_OPERATION_COUNT = TGG_NAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link language.impl.TGGRuleNodeImpl <em>TGG Rule Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see language.impl.TGGRuleNodeImpl
	 * @see language.impl.LanguagePackageImpl#getTGGRuleNode()
	 * @generated
	 */
	int TGG_RULE_NODE = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_NODE__NAME = TGG_RULE_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Domain Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_NODE__DOMAIN_TYPE = TGG_RULE_ELEMENT__DOMAIN_TYPE;

	/**
	 * The feature id for the '<em><b>Binding Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_NODE__BINDING_TYPE = TGG_RULE_ELEMENT__BINDING_TYPE;

	/**
	 * The feature id for the '<em><b>Incoming Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_NODE__INCOMING_EDGES = TGG_RULE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Outgoing Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_NODE__OUTGOING_EDGES = TGG_RULE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_NODE__TYPE = TGG_RULE_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Incoming Corrs Source</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_NODE__INCOMING_CORRS_SOURCE = TGG_RULE_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Incoming Corrs Target</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_NODE__INCOMING_CORRS_TARGET = TGG_RULE_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Attr Expr</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_NODE__ATTR_EXPR = TGG_RULE_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>TGG Rule Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_NODE_FEATURE_COUNT = TGG_RULE_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The number of operations of the '<em>TGG Rule Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_NODE_OPERATION_COUNT = TGG_RULE_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link language.impl.TGGRuleCorrImpl <em>TGG Rule Corr</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see language.impl.TGGRuleCorrImpl
	 * @see language.impl.LanguagePackageImpl#getTGGRuleCorr()
	 * @generated
	 */
	int TGG_RULE_CORR = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_CORR__NAME = TGG_RULE_NODE__NAME;

	/**
	 * The feature id for the '<em><b>Domain Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_CORR__DOMAIN_TYPE = TGG_RULE_NODE__DOMAIN_TYPE;

	/**
	 * The feature id for the '<em><b>Binding Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_CORR__BINDING_TYPE = TGG_RULE_NODE__BINDING_TYPE;

	/**
	 * The feature id for the '<em><b>Incoming Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_CORR__INCOMING_EDGES = TGG_RULE_NODE__INCOMING_EDGES;

	/**
	 * The feature id for the '<em><b>Outgoing Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_CORR__OUTGOING_EDGES = TGG_RULE_NODE__OUTGOING_EDGES;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_CORR__TYPE = TGG_RULE_NODE__TYPE;

	/**
	 * The feature id for the '<em><b>Incoming Corrs Source</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_CORR__INCOMING_CORRS_SOURCE = TGG_RULE_NODE__INCOMING_CORRS_SOURCE;

	/**
	 * The feature id for the '<em><b>Incoming Corrs Target</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_CORR__INCOMING_CORRS_TARGET = TGG_RULE_NODE__INCOMING_CORRS_TARGET;

	/**
	 * The feature id for the '<em><b>Attr Expr</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_CORR__ATTR_EXPR = TGG_RULE_NODE__ATTR_EXPR;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_CORR__SOURCE = TGG_RULE_NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_CORR__TARGET = TGG_RULE_NODE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>TGG Rule Corr</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_CORR_FEATURE_COUNT = TGG_RULE_NODE_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>TGG Rule Corr</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_CORR_OPERATION_COUNT = TGG_RULE_NODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link language.impl.TGGRuleEdgeImpl <em>TGG Rule Edge</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see language.impl.TGGRuleEdgeImpl
	 * @see language.impl.LanguagePackageImpl#getTGGRuleEdge()
	 * @generated
	 */
	int TGG_RULE_EDGE = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_EDGE__NAME = TGG_RULE_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Domain Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_EDGE__DOMAIN_TYPE = TGG_RULE_ELEMENT__DOMAIN_TYPE;

	/**
	 * The feature id for the '<em><b>Binding Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_EDGE__BINDING_TYPE = TGG_RULE_ELEMENT__BINDING_TYPE;

	/**
	 * The feature id for the '<em><b>Src Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_EDGE__SRC_NODE = TGG_RULE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Trg Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_EDGE__TRG_NODE = TGG_RULE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_EDGE__TYPE = TGG_RULE_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>TGG Rule Edge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_EDGE_FEATURE_COUNT = TGG_RULE_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>TGG Rule Edge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_EDGE_OPERATION_COUNT = TGG_RULE_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link language.impl.NACImpl <em>NAC</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see language.impl.NACImpl
	 * @see language.impl.LanguagePackageImpl#getNAC()
	 * @generated
	 */
	int NAC = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAC__NAME = TGG_NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAC__NODES = TGG_NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Edges</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAC__EDGES = TGG_NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Attribute Condition Library</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAC__ATTRIBUTE_CONDITION_LIBRARY = TGG_NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>NAC</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAC_FEATURE_COUNT = TGG_NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>NAC</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAC_OPERATION_COUNT = TGG_NAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link language.impl.TGGComplementRuleImpl <em>TGG Complement Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see language.impl.TGGComplementRuleImpl
	 * @see language.impl.LanguagePackageImpl#getTGGComplementRule()
	 * @generated
	 */
	int TGG_COMPLEMENT_RULE = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_COMPLEMENT_RULE__NAME = TGG_RULE__NAME;

	/**
	 * The feature id for the '<em><b>Refines</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_COMPLEMENT_RULE__REFINES = TGG_RULE__REFINES;

	/**
	 * The feature id for the '<em><b>Nacs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_COMPLEMENT_RULE__NACS = TGG_RULE__NACS;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_COMPLEMENT_RULE__NODES = TGG_RULE__NODES;

	/**
	 * The feature id for the '<em><b>Edges</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_COMPLEMENT_RULE__EDGES = TGG_RULE__EDGES;

	/**
	 * The feature id for the '<em><b>Attribute Condition Library</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_COMPLEMENT_RULE__ATTRIBUTE_CONDITION_LIBRARY = TGG_RULE__ATTRIBUTE_CONDITION_LIBRARY;

	/**
	 * The feature id for the '<em><b>Abstract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_COMPLEMENT_RULE__ABSTRACT = TGG_RULE__ABSTRACT;

	/**
	 * The feature id for the '<em><b>Bounded</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_COMPLEMENT_RULE__BOUNDED = TGG_RULE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Kernel</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_COMPLEMENT_RULE__KERNEL = TGG_RULE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>TGG Complement Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_COMPLEMENT_RULE_FEATURE_COUNT = TGG_RULE_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>TGG Complement Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_COMPLEMENT_RULE_OPERATION_COUNT = TGG_RULE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link language.impl.TGGInplaceAttributeExpressionImpl <em>TGG Inplace Attribute Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see language.impl.TGGInplaceAttributeExpressionImpl
	 * @see language.impl.LanguagePackageImpl#getTGGInplaceAttributeExpression()
	 * @generated
	 */
	int TGG_INPLACE_ATTRIBUTE_EXPRESSION = 8;

	/**
	 * The feature id for the '<em><b>Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_INPLACE_ATTRIBUTE_EXPRESSION__ATTRIBUTE = 0;

	/**
	 * The feature id for the '<em><b>Value Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_INPLACE_ATTRIBUTE_EXPRESSION__VALUE_EXPR = 1;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_INPLACE_ATTRIBUTE_EXPRESSION__OPERATOR = 2;

	/**
	 * The number of structural features of the '<em>TGG Inplace Attribute Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_INPLACE_ATTRIBUTE_EXPRESSION_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>TGG Inplace Attribute Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_INPLACE_ATTRIBUTE_EXPRESSION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link language.impl.TGGAttributeConstraintLibraryImpl <em>TGG Attribute Constraint Library</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see language.impl.TGGAttributeConstraintLibraryImpl
	 * @see language.impl.LanguagePackageImpl#getTGGAttributeConstraintLibrary()
	 * @generated
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_LIBRARY = 9;

	/**
	 * The feature id for the '<em><b>Tgg Attribute Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__TGG_ATTRIBUTE_CONSTRAINTS = 0;

	/**
	 * The feature id for the '<em><b>Parameter Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__PARAMETER_VALUES = 1;

	/**
	 * The number of structural features of the '<em>TGG Attribute Constraint Library</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_LIBRARY_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>TGG Attribute Constraint Library</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_LIBRARY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link language.impl.TGGAttributeConstraintImpl <em>TGG Attribute Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see language.impl.TGGAttributeConstraintImpl
	 * @see language.impl.LanguagePackageImpl#getTGGAttributeConstraint()
	 * @generated
	 */
	int TGG_ATTRIBUTE_CONSTRAINT = 10;

	/**
	 * The feature id for the '<em><b>Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT__DEFINITION = 0;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT__PARAMETERS = 1;

	/**
	 * The number of structural features of the '<em>TGG Attribute Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>TGG Attribute Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link language.impl.TGGParamValueImpl <em>TGG Param Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see language.impl.TGGParamValueImpl
	 * @see language.impl.LanguagePackageImpl#getTGGParamValue()
	 * @generated
	 */
	int TGG_PARAM_VALUE = 17;

	/**
	 * The feature id for the '<em><b>Parameter Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_PARAM_VALUE__PARAMETER_DEFINITION = 0;

	/**
	 * The number of structural features of the '<em>TGG Param Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_PARAM_VALUE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>TGG Param Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_PARAM_VALUE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link language.impl.TGGAttributeVariableImpl <em>TGG Attribute Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see language.impl.TGGAttributeVariableImpl
	 * @see language.impl.LanguagePackageImpl#getTGGAttributeVariable()
	 * @generated
	 */
	int TGG_ATTRIBUTE_VARIABLE = 11;

	/**
	 * The feature id for the '<em><b>Parameter Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_VARIABLE__PARAMETER_DEFINITION = TGG_PARAM_VALUE__PARAMETER_DEFINITION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_VARIABLE__NAME = TGG_PARAM_VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>TGG Attribute Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_VARIABLE_FEATURE_COUNT = TGG_PARAM_VALUE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>TGG Attribute Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_VARIABLE_OPERATION_COUNT = TGG_PARAM_VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link language.impl.TGGAttributeConstraintDefinitionLibraryImpl <em>TGG Attribute Constraint Definition Library</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see language.impl.TGGAttributeConstraintDefinitionLibraryImpl
	 * @see language.impl.LanguagePackageImpl#getTGGAttributeConstraintDefinitionLibrary()
	 * @generated
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY = 12;

	/**
	 * The feature id for the '<em><b>Tgg Attribute Constraint Definitions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY__TGG_ATTRIBUTE_CONSTRAINT_DEFINITIONS = 0;

	/**
	 * The number of structural features of the '<em>TGG Attribute Constraint Definition Library</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>TGG Attribute Constraint Definition Library</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link language.impl.TGGAttributeConstraintDefinitionImpl <em>TGG Attribute Constraint Definition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see language.impl.TGGAttributeConstraintDefinitionImpl
	 * @see language.impl.LanguagePackageImpl#getTGGAttributeConstraintDefinition()
	 * @generated
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION = 13;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__NAME = TGG_NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>User Defined</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__USER_DEFINED = TGG_NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Parameter Definitions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__PARAMETER_DEFINITIONS = TGG_NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Sync Adornments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__SYNC_ADORNMENTS = TGG_NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Gen Adornments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__GEN_ADORNMENTS = TGG_NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>TGG Attribute Constraint Definition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_FEATURE_COUNT = TGG_NAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>TGG Attribute Constraint Definition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_OPERATION_COUNT = TGG_NAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link language.impl.TGGAttributeConstraintParameterDefinitionImpl <em>TGG Attribute Constraint Parameter Definition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see language.impl.TGGAttributeConstraintParameterDefinitionImpl
	 * @see language.impl.LanguagePackageImpl#getTGGAttributeConstraintParameterDefinition()
	 * @generated
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_DEFINITION = 14;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_DEFINITION__TYPE = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_DEFINITION__NAME = 1;

	/**
	 * The number of structural features of the '<em>TGG Attribute Constraint Parameter Definition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_DEFINITION_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>TGG Attribute Constraint Parameter Definition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_DEFINITION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link language.impl.TGGAttributeConstraintAdornmentImpl <em>TGG Attribute Constraint Adornment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see language.impl.TGGAttributeConstraintAdornmentImpl
	 * @see language.impl.LanguagePackageImpl#getTGGAttributeConstraintAdornment()
	 * @generated
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_ADORNMENT = 15;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_ADORNMENT__VALUE = 0;

	/**
	 * The number of structural features of the '<em>TGG Attribute Constraint Adornment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_ADORNMENT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>TGG Attribute Constraint Adornment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_ADORNMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link language.impl.TGGExpressionImpl <em>TGG Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see language.impl.TGGExpressionImpl
	 * @see language.impl.LanguagePackageImpl#getTGGExpression()
	 * @generated
	 */
	int TGG_EXPRESSION = 18;

	/**
	 * The feature id for the '<em><b>Parameter Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_EXPRESSION__PARAMETER_DEFINITION = TGG_PARAM_VALUE__PARAMETER_DEFINITION;

	/**
	 * The number of structural features of the '<em>TGG Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_EXPRESSION_FEATURE_COUNT = TGG_PARAM_VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>TGG Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_EXPRESSION_OPERATION_COUNT = TGG_PARAM_VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link language.impl.TGGLiteralExpressionImpl <em>TGG Literal Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see language.impl.TGGLiteralExpressionImpl
	 * @see language.impl.LanguagePackageImpl#getTGGLiteralExpression()
	 * @generated
	 */
	int TGG_LITERAL_EXPRESSION = 19;

	/**
	 * The feature id for the '<em><b>Parameter Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_LITERAL_EXPRESSION__PARAMETER_DEFINITION = TGG_EXPRESSION__PARAMETER_DEFINITION;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_LITERAL_EXPRESSION__VALUE = TGG_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>TGG Literal Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_LITERAL_EXPRESSION_FEATURE_COUNT = TGG_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>TGG Literal Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_LITERAL_EXPRESSION_OPERATION_COUNT = TGG_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link language.impl.TGGAttributeExpressionImpl <em>TGG Attribute Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see language.impl.TGGAttributeExpressionImpl
	 * @see language.impl.LanguagePackageImpl#getTGGAttributeExpression()
	 * @generated
	 */
	int TGG_ATTRIBUTE_EXPRESSION = 20;

	/**
	 * The feature id for the '<em><b>Parameter Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_EXPRESSION__PARAMETER_DEFINITION = TGG_EXPRESSION__PARAMETER_DEFINITION;

	/**
	 * The feature id for the '<em><b>Object Var</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_EXPRESSION__OBJECT_VAR = TGG_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_EXPRESSION__ATTRIBUTE = TGG_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>TGG Attribute Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_EXPRESSION_FEATURE_COUNT = TGG_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>TGG Attribute Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_EXPRESSION_OPERATION_COUNT = TGG_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link language.impl.TGGEnumExpressionImpl <em>TGG Enum Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see language.impl.TGGEnumExpressionImpl
	 * @see language.impl.LanguagePackageImpl#getTGGEnumExpression()
	 * @generated
	 */
	int TGG_ENUM_EXPRESSION = 21;

	/**
	 * The feature id for the '<em><b>Parameter Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ENUM_EXPRESSION__PARAMETER_DEFINITION = TGG_EXPRESSION__PARAMETER_DEFINITION;

	/**
	 * The feature id for the '<em><b>Eenum</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ENUM_EXPRESSION__EENUM = TGG_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Literal</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ENUM_EXPRESSION__LITERAL = TGG_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>TGG Enum Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ENUM_EXPRESSION_FEATURE_COUNT = TGG_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>TGG Enum Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ENUM_EXPRESSION_OPERATION_COUNT = TGG_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link language.DomainType <em>Domain Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see language.DomainType
	 * @see language.impl.LanguagePackageImpl#getDomainType()
	 * @generated
	 */
	int DOMAIN_TYPE = 22;

	/**
	 * The meta object id for the '{@link language.BindingType <em>Binding Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see language.BindingType
	 * @see language.impl.LanguagePackageImpl#getBindingType()
	 * @generated
	 */
	int BINDING_TYPE = 23;

	/**
	 * The meta object id for the '{@link language.TGGAttributeConstraintOperators <em>TGG Attribute Constraint Operators</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see language.TGGAttributeConstraintOperators
	 * @see language.impl.LanguagePackageImpl#getTGGAttributeConstraintOperators()
	 * @generated
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_OPERATORS = 24;

	/**
	 * Returns the meta object for class '{@link language.TGG <em>TGG</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG</em>'.
	 * @see language.TGG
	 * @generated
	 */
	EClass getTGG();

	/**
	 * Returns the meta object for the reference list '{@link language.TGG#getSrc <em>Src</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Src</em>'.
	 * @see language.TGG#getSrc()
	 * @see #getTGG()
	 * @generated
	 */
	EReference getTGG_Src();

	/**
	 * Returns the meta object for the reference list '{@link language.TGG#getTrg <em>Trg</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Trg</em>'.
	 * @see language.TGG#getTrg()
	 * @see #getTGG()
	 * @generated
	 */
	EReference getTGG_Trg();

	/**
	 * Returns the meta object for the reference '{@link language.TGG#getCorr <em>Corr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Corr</em>'.
	 * @see language.TGG#getCorr()
	 * @see #getTGG()
	 * @generated
	 */
	EReference getTGG_Corr();

	/**
	 * Returns the meta object for the containment reference list '{@link language.TGG#getRules <em>Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Rules</em>'.
	 * @see language.TGG#getRules()
	 * @see #getTGG()
	 * @generated
	 */
	EReference getTGG_Rules();

	/**
	 * Returns the meta object for the containment reference '{@link language.TGG#getAttributeConstraintDefinitionLibrary <em>Attribute Constraint Definition Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Attribute Constraint Definition Library</em>'.
	 * @see language.TGG#getAttributeConstraintDefinitionLibrary()
	 * @see #getTGG()
	 * @generated
	 */
	EReference getTGG_AttributeConstraintDefinitionLibrary();

	/**
	 * Returns the meta object for class '{@link language.TGGRule <em>TGG Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Rule</em>'.
	 * @see language.TGGRule
	 * @generated
	 */
	EClass getTGGRule();

	/**
	 * Returns the meta object for the reference list '{@link language.TGGRule#getRefines <em>Refines</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Refines</em>'.
	 * @see language.TGGRule#getRefines()
	 * @see #getTGGRule()
	 * @generated
	 */
	EReference getTGGRule_Refines();

	/**
	 * Returns the meta object for the containment reference list '{@link language.TGGRule#getNacs <em>Nacs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Nacs</em>'.
	 * @see language.TGGRule#getNacs()
	 * @see #getTGGRule()
	 * @generated
	 */
	EReference getTGGRule_Nacs();

	/**
	 * Returns the meta object for the containment reference list '{@link language.TGGRule#getNodes <em>Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Nodes</em>'.
	 * @see language.TGGRule#getNodes()
	 * @see #getTGGRule()
	 * @generated
	 */
	EReference getTGGRule_Nodes();

	/**
	 * Returns the meta object for the containment reference list '{@link language.TGGRule#getEdges <em>Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Edges</em>'.
	 * @see language.TGGRule#getEdges()
	 * @see #getTGGRule()
	 * @generated
	 */
	EReference getTGGRule_Edges();

	/**
	 * Returns the meta object for the containment reference '{@link language.TGGRule#getAttributeConditionLibrary <em>Attribute Condition Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Attribute Condition Library</em>'.
	 * @see language.TGGRule#getAttributeConditionLibrary()
	 * @see #getTGGRule()
	 * @generated
	 */
	EReference getTGGRule_AttributeConditionLibrary();

	/**
	 * Returns the meta object for the attribute '{@link language.TGGRule#isAbstract <em>Abstract</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Abstract</em>'.
	 * @see language.TGGRule#isAbstract()
	 * @see #getTGGRule()
	 * @generated
	 */
	EAttribute getTGGRule_Abstract();

	/**
	 * Returns the meta object for class '{@link language.TGGRuleElement <em>TGG Rule Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Rule Element</em>'.
	 * @see language.TGGRuleElement
	 * @generated
	 */
	EClass getTGGRuleElement();

	/**
	 * Returns the meta object for the attribute '{@link language.TGGRuleElement#getDomainType <em>Domain Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Domain Type</em>'.
	 * @see language.TGGRuleElement#getDomainType()
	 * @see #getTGGRuleElement()
	 * @generated
	 */
	EAttribute getTGGRuleElement_DomainType();

	/**
	 * Returns the meta object for the attribute '{@link language.TGGRuleElement#getBindingType <em>Binding Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Binding Type</em>'.
	 * @see language.TGGRuleElement#getBindingType()
	 * @see #getTGGRuleElement()
	 * @generated
	 */
	EAttribute getTGGRuleElement_BindingType();

	/**
	 * Returns the meta object for class '{@link language.TGGRuleNode <em>TGG Rule Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Rule Node</em>'.
	 * @see language.TGGRuleNode
	 * @generated
	 */
	EClass getTGGRuleNode();

	/**
	 * Returns the meta object for the reference list '{@link language.TGGRuleNode#getIncomingEdges <em>Incoming Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Incoming Edges</em>'.
	 * @see language.TGGRuleNode#getIncomingEdges()
	 * @see #getTGGRuleNode()
	 * @generated
	 */
	EReference getTGGRuleNode_IncomingEdges();

	/**
	 * Returns the meta object for the reference list '{@link language.TGGRuleNode#getOutgoingEdges <em>Outgoing Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Outgoing Edges</em>'.
	 * @see language.TGGRuleNode#getOutgoingEdges()
	 * @see #getTGGRuleNode()
	 * @generated
	 */
	EReference getTGGRuleNode_OutgoingEdges();

	/**
	 * Returns the meta object for the reference '{@link language.TGGRuleNode#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see language.TGGRuleNode#getType()
	 * @see #getTGGRuleNode()
	 * @generated
	 */
	EReference getTGGRuleNode_Type();

	/**
	 * Returns the meta object for the reference list '{@link language.TGGRuleNode#getIncomingCorrsSource <em>Incoming Corrs Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Incoming Corrs Source</em>'.
	 * @see language.TGGRuleNode#getIncomingCorrsSource()
	 * @see #getTGGRuleNode()
	 * @generated
	 */
	EReference getTGGRuleNode_IncomingCorrsSource();

	/**
	 * Returns the meta object for the reference list '{@link language.TGGRuleNode#getIncomingCorrsTarget <em>Incoming Corrs Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Incoming Corrs Target</em>'.
	 * @see language.TGGRuleNode#getIncomingCorrsTarget()
	 * @see #getTGGRuleNode()
	 * @generated
	 */
	EReference getTGGRuleNode_IncomingCorrsTarget();

	/**
	 * Returns the meta object for the containment reference list '{@link language.TGGRuleNode#getAttrExpr <em>Attr Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attr Expr</em>'.
	 * @see language.TGGRuleNode#getAttrExpr()
	 * @see #getTGGRuleNode()
	 * @generated
	 */
	EReference getTGGRuleNode_AttrExpr();

	/**
	 * Returns the meta object for class '{@link language.TGGRuleCorr <em>TGG Rule Corr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Rule Corr</em>'.
	 * @see language.TGGRuleCorr
	 * @generated
	 */
	EClass getTGGRuleCorr();

	/**
	 * Returns the meta object for the reference '{@link language.TGGRuleCorr#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see language.TGGRuleCorr#getSource()
	 * @see #getTGGRuleCorr()
	 * @generated
	 */
	EReference getTGGRuleCorr_Source();

	/**
	 * Returns the meta object for the reference '{@link language.TGGRuleCorr#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see language.TGGRuleCorr#getTarget()
	 * @see #getTGGRuleCorr()
	 * @generated
	 */
	EReference getTGGRuleCorr_Target();

	/**
	 * Returns the meta object for class '{@link language.TGGRuleEdge <em>TGG Rule Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Rule Edge</em>'.
	 * @see language.TGGRuleEdge
	 * @generated
	 */
	EClass getTGGRuleEdge();

	/**
	 * Returns the meta object for the reference '{@link language.TGGRuleEdge#getSrcNode <em>Src Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Src Node</em>'.
	 * @see language.TGGRuleEdge#getSrcNode()
	 * @see #getTGGRuleEdge()
	 * @generated
	 */
	EReference getTGGRuleEdge_SrcNode();

	/**
	 * Returns the meta object for the reference '{@link language.TGGRuleEdge#getTrgNode <em>Trg Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Trg Node</em>'.
	 * @see language.TGGRuleEdge#getTrgNode()
	 * @see #getTGGRuleEdge()
	 * @generated
	 */
	EReference getTGGRuleEdge_TrgNode();

	/**
	 * Returns the meta object for the reference '{@link language.TGGRuleEdge#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see language.TGGRuleEdge#getType()
	 * @see #getTGGRuleEdge()
	 * @generated
	 */
	EReference getTGGRuleEdge_Type();

	/**
	 * Returns the meta object for class '{@link language.NAC <em>NAC</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>NAC</em>'.
	 * @see language.NAC
	 * @generated
	 */
	EClass getNAC();

	/**
	 * Returns the meta object for the containment reference list '{@link language.NAC#getNodes <em>Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Nodes</em>'.
	 * @see language.NAC#getNodes()
	 * @see #getNAC()
	 * @generated
	 */
	EReference getNAC_Nodes();

	/**
	 * Returns the meta object for the containment reference list '{@link language.NAC#getEdges <em>Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Edges</em>'.
	 * @see language.NAC#getEdges()
	 * @see #getNAC()
	 * @generated
	 */
	EReference getNAC_Edges();

	/**
	 * Returns the meta object for the containment reference '{@link language.NAC#getAttributeConditionLibrary <em>Attribute Condition Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Attribute Condition Library</em>'.
	 * @see language.NAC#getAttributeConditionLibrary()
	 * @see #getNAC()
	 * @generated
	 */
	EReference getNAC_AttributeConditionLibrary();

	/**
	 * Returns the meta object for class '{@link language.TGGComplementRule <em>TGG Complement Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Complement Rule</em>'.
	 * @see language.TGGComplementRule
	 * @generated
	 */
	EClass getTGGComplementRule();

	/**
	 * Returns the meta object for the attribute '{@link language.TGGComplementRule#isBounded <em>Bounded</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Bounded</em>'.
	 * @see language.TGGComplementRule#isBounded()
	 * @see #getTGGComplementRule()
	 * @generated
	 */
	EAttribute getTGGComplementRule_Bounded();

	/**
	 * Returns the meta object for the reference '{@link language.TGGComplementRule#getKernel <em>Kernel</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Kernel</em>'.
	 * @see language.TGGComplementRule#getKernel()
	 * @see #getTGGComplementRule()
	 * @generated
	 */
	EReference getTGGComplementRule_Kernel();

	/**
	 * Returns the meta object for class '{@link language.TGGInplaceAttributeExpression <em>TGG Inplace Attribute Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Inplace Attribute Expression</em>'.
	 * @see language.TGGInplaceAttributeExpression
	 * @generated
	 */
	EClass getTGGInplaceAttributeExpression();

	/**
	 * Returns the meta object for the reference '{@link language.TGGInplaceAttributeExpression#getAttribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Attribute</em>'.
	 * @see language.TGGInplaceAttributeExpression#getAttribute()
	 * @see #getTGGInplaceAttributeExpression()
	 * @generated
	 */
	EReference getTGGInplaceAttributeExpression_Attribute();

	/**
	 * Returns the meta object for the containment reference '{@link language.TGGInplaceAttributeExpression#getValueExpr <em>Value Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value Expr</em>'.
	 * @see language.TGGInplaceAttributeExpression#getValueExpr()
	 * @see #getTGGInplaceAttributeExpression()
	 * @generated
	 */
	EReference getTGGInplaceAttributeExpression_ValueExpr();

	/**
	 * Returns the meta object for the attribute '{@link language.TGGInplaceAttributeExpression#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see language.TGGInplaceAttributeExpression#getOperator()
	 * @see #getTGGInplaceAttributeExpression()
	 * @generated
	 */
	EAttribute getTGGInplaceAttributeExpression_Operator();

	/**
	 * Returns the meta object for class '{@link language.TGGAttributeConstraintLibrary <em>TGG Attribute Constraint Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Attribute Constraint Library</em>'.
	 * @see language.TGGAttributeConstraintLibrary
	 * @generated
	 */
	EClass getTGGAttributeConstraintLibrary();

	/**
	 * Returns the meta object for the containment reference list '{@link language.TGGAttributeConstraintLibrary#getTggAttributeConstraints <em>Tgg Attribute Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Tgg Attribute Constraints</em>'.
	 * @see language.TGGAttributeConstraintLibrary#getTggAttributeConstraints()
	 * @see #getTGGAttributeConstraintLibrary()
	 * @generated
	 */
	EReference getTGGAttributeConstraintLibrary_TggAttributeConstraints();

	/**
	 * Returns the meta object for the containment reference list '{@link language.TGGAttributeConstraintLibrary#getParameterValues <em>Parameter Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameter Values</em>'.
	 * @see language.TGGAttributeConstraintLibrary#getParameterValues()
	 * @see #getTGGAttributeConstraintLibrary()
	 * @generated
	 */
	EReference getTGGAttributeConstraintLibrary_ParameterValues();

	/**
	 * Returns the meta object for class '{@link language.TGGAttributeConstraint <em>TGG Attribute Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Attribute Constraint</em>'.
	 * @see language.TGGAttributeConstraint
	 * @generated
	 */
	EClass getTGGAttributeConstraint();

	/**
	 * Returns the meta object for the reference '{@link language.TGGAttributeConstraint#getDefinition <em>Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Definition</em>'.
	 * @see language.TGGAttributeConstraint#getDefinition()
	 * @see #getTGGAttributeConstraint()
	 * @generated
	 */
	EReference getTGGAttributeConstraint_Definition();

	/**
	 * Returns the meta object for the reference list '{@link language.TGGAttributeConstraint#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Parameters</em>'.
	 * @see language.TGGAttributeConstraint#getParameters()
	 * @see #getTGGAttributeConstraint()
	 * @generated
	 */
	EReference getTGGAttributeConstraint_Parameters();

	/**
	 * Returns the meta object for class '{@link language.TGGAttributeVariable <em>TGG Attribute Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Attribute Variable</em>'.
	 * @see language.TGGAttributeVariable
	 * @generated
	 */
	EClass getTGGAttributeVariable();

	/**
	 * Returns the meta object for the attribute '{@link language.TGGAttributeVariable#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see language.TGGAttributeVariable#getName()
	 * @see #getTGGAttributeVariable()
	 * @generated
	 */
	EAttribute getTGGAttributeVariable_Name();

	/**
	 * Returns the meta object for class '{@link language.TGGAttributeConstraintDefinitionLibrary <em>TGG Attribute Constraint Definition Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Attribute Constraint Definition Library</em>'.
	 * @see language.TGGAttributeConstraintDefinitionLibrary
	 * @generated
	 */
	EClass getTGGAttributeConstraintDefinitionLibrary();

	/**
	 * Returns the meta object for the containment reference list '{@link language.TGGAttributeConstraintDefinitionLibrary#getTggAttributeConstraintDefinitions <em>Tgg Attribute Constraint Definitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Tgg Attribute Constraint Definitions</em>'.
	 * @see language.TGGAttributeConstraintDefinitionLibrary#getTggAttributeConstraintDefinitions()
	 * @see #getTGGAttributeConstraintDefinitionLibrary()
	 * @generated
	 */
	EReference getTGGAttributeConstraintDefinitionLibrary_TggAttributeConstraintDefinitions();

	/**
	 * Returns the meta object for class '{@link language.TGGAttributeConstraintDefinition <em>TGG Attribute Constraint Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Attribute Constraint Definition</em>'.
	 * @see language.TGGAttributeConstraintDefinition
	 * @generated
	 */
	EClass getTGGAttributeConstraintDefinition();

	/**
	 * Returns the meta object for the attribute '{@link language.TGGAttributeConstraintDefinition#isUserDefined <em>User Defined</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>User Defined</em>'.
	 * @see language.TGGAttributeConstraintDefinition#isUserDefined()
	 * @see #getTGGAttributeConstraintDefinition()
	 * @generated
	 */
	EAttribute getTGGAttributeConstraintDefinition_UserDefined();

	/**
	 * Returns the meta object for the containment reference list '{@link language.TGGAttributeConstraintDefinition#getParameterDefinitions <em>Parameter Definitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameter Definitions</em>'.
	 * @see language.TGGAttributeConstraintDefinition#getParameterDefinitions()
	 * @see #getTGGAttributeConstraintDefinition()
	 * @generated
	 */
	EReference getTGGAttributeConstraintDefinition_ParameterDefinitions();

	/**
	 * Returns the meta object for the containment reference list '{@link language.TGGAttributeConstraintDefinition#getSyncAdornments <em>Sync Adornments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sync Adornments</em>'.
	 * @see language.TGGAttributeConstraintDefinition#getSyncAdornments()
	 * @see #getTGGAttributeConstraintDefinition()
	 * @generated
	 */
	EReference getTGGAttributeConstraintDefinition_SyncAdornments();

	/**
	 * Returns the meta object for the containment reference list '{@link language.TGGAttributeConstraintDefinition#getGenAdornments <em>Gen Adornments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Gen Adornments</em>'.
	 * @see language.TGGAttributeConstraintDefinition#getGenAdornments()
	 * @see #getTGGAttributeConstraintDefinition()
	 * @generated
	 */
	EReference getTGGAttributeConstraintDefinition_GenAdornments();

	/**
	 * Returns the meta object for class '{@link language.TGGAttributeConstraintParameterDefinition <em>TGG Attribute Constraint Parameter Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Attribute Constraint Parameter Definition</em>'.
	 * @see language.TGGAttributeConstraintParameterDefinition
	 * @generated
	 */
	EClass getTGGAttributeConstraintParameterDefinition();

	/**
	 * Returns the meta object for the reference '{@link language.TGGAttributeConstraintParameterDefinition#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see language.TGGAttributeConstraintParameterDefinition#getType()
	 * @see #getTGGAttributeConstraintParameterDefinition()
	 * @generated
	 */
	EReference getTGGAttributeConstraintParameterDefinition_Type();

	/**
	 * Returns the meta object for the attribute '{@link language.TGGAttributeConstraintParameterDefinition#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see language.TGGAttributeConstraintParameterDefinition#getName()
	 * @see #getTGGAttributeConstraintParameterDefinition()
	 * @generated
	 */
	EAttribute getTGGAttributeConstraintParameterDefinition_Name();

	/**
	 * Returns the meta object for class '{@link language.TGGAttributeConstraintAdornment <em>TGG Attribute Constraint Adornment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Attribute Constraint Adornment</em>'.
	 * @see language.TGGAttributeConstraintAdornment
	 * @generated
	 */
	EClass getTGGAttributeConstraintAdornment();

	/**
	 * Returns the meta object for the attribute list '{@link language.TGGAttributeConstraintAdornment#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Value</em>'.
	 * @see language.TGGAttributeConstraintAdornment#getValue()
	 * @see #getTGGAttributeConstraintAdornment()
	 * @generated
	 */
	EAttribute getTGGAttributeConstraintAdornment_Value();

	/**
	 * Returns the meta object for class '{@link language.TGGNamedElement <em>TGG Named Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Named Element</em>'.
	 * @see language.TGGNamedElement
	 * @generated
	 */
	EClass getTGGNamedElement();

	/**
	 * Returns the meta object for the attribute '{@link language.TGGNamedElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see language.TGGNamedElement#getName()
	 * @see #getTGGNamedElement()
	 * @generated
	 */
	EAttribute getTGGNamedElement_Name();

	/**
	 * Returns the meta object for class '{@link language.TGGParamValue <em>TGG Param Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Param Value</em>'.
	 * @see language.TGGParamValue
	 * @generated
	 */
	EClass getTGGParamValue();

	/**
	 * Returns the meta object for the reference '{@link language.TGGParamValue#getParameterDefinition <em>Parameter Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parameter Definition</em>'.
	 * @see language.TGGParamValue#getParameterDefinition()
	 * @see #getTGGParamValue()
	 * @generated
	 */
	EReference getTGGParamValue_ParameterDefinition();

	/**
	 * Returns the meta object for class '{@link language.TGGExpression <em>TGG Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Expression</em>'.
	 * @see language.TGGExpression
	 * @generated
	 */
	EClass getTGGExpression();

	/**
	 * Returns the meta object for class '{@link language.TGGLiteralExpression <em>TGG Literal Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Literal Expression</em>'.
	 * @see language.TGGLiteralExpression
	 * @generated
	 */
	EClass getTGGLiteralExpression();

	/**
	 * Returns the meta object for the attribute '{@link language.TGGLiteralExpression#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see language.TGGLiteralExpression#getValue()
	 * @see #getTGGLiteralExpression()
	 * @generated
	 */
	EAttribute getTGGLiteralExpression_Value();

	/**
	 * Returns the meta object for class '{@link language.TGGAttributeExpression <em>TGG Attribute Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Attribute Expression</em>'.
	 * @see language.TGGAttributeExpression
	 * @generated
	 */
	EClass getTGGAttributeExpression();

	/**
	 * Returns the meta object for the reference '{@link language.TGGAttributeExpression#getObjectVar <em>Object Var</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Object Var</em>'.
	 * @see language.TGGAttributeExpression#getObjectVar()
	 * @see #getTGGAttributeExpression()
	 * @generated
	 */
	EReference getTGGAttributeExpression_ObjectVar();

	/**
	 * Returns the meta object for the reference '{@link language.TGGAttributeExpression#getAttribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Attribute</em>'.
	 * @see language.TGGAttributeExpression#getAttribute()
	 * @see #getTGGAttributeExpression()
	 * @generated
	 */
	EReference getTGGAttributeExpression_Attribute();

	/**
	 * Returns the meta object for class '{@link language.TGGEnumExpression <em>TGG Enum Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Enum Expression</em>'.
	 * @see language.TGGEnumExpression
	 * @generated
	 */
	EClass getTGGEnumExpression();

	/**
	 * Returns the meta object for the reference '{@link language.TGGEnumExpression#getEenum <em>Eenum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Eenum</em>'.
	 * @see language.TGGEnumExpression#getEenum()
	 * @see #getTGGEnumExpression()
	 * @generated
	 */
	EReference getTGGEnumExpression_Eenum();

	/**
	 * Returns the meta object for the reference '{@link language.TGGEnumExpression#getLiteral <em>Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Literal</em>'.
	 * @see language.TGGEnumExpression#getLiteral()
	 * @see #getTGGEnumExpression()
	 * @generated
	 */
	EReference getTGGEnumExpression_Literal();

	/**
	 * Returns the meta object for enum '{@link language.DomainType <em>Domain Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Domain Type</em>'.
	 * @see language.DomainType
	 * @generated
	 */
	EEnum getDomainType();

	/**
	 * Returns the meta object for enum '{@link language.BindingType <em>Binding Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Binding Type</em>'.
	 * @see language.BindingType
	 * @generated
	 */
	EEnum getBindingType();

	/**
	 * Returns the meta object for enum '{@link language.TGGAttributeConstraintOperators <em>TGG Attribute Constraint Operators</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>TGG Attribute Constraint Operators</em>'.
	 * @see language.TGGAttributeConstraintOperators
	 * @generated
	 */
	EEnum getTGGAttributeConstraintOperators();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	LanguageFactory getLanguageFactory();

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
		 * The meta object literal for the '{@link language.impl.TGGImpl <em>TGG</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see language.impl.TGGImpl
		 * @see language.impl.LanguagePackageImpl#getTGG()
		 * @generated
		 */
		EClass TGG = eINSTANCE.getTGG();

		/**
		 * The meta object literal for the '<em><b>Src</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG__SRC = eINSTANCE.getTGG_Src();

		/**
		 * The meta object literal for the '<em><b>Trg</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG__TRG = eINSTANCE.getTGG_Trg();

		/**
		 * The meta object literal for the '<em><b>Corr</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG__CORR = eINSTANCE.getTGG_Corr();

		/**
		 * The meta object literal for the '<em><b>Rules</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG__RULES = eINSTANCE.getTGG_Rules();

		/**
		 * The meta object literal for the '<em><b>Attribute Constraint Definition Library</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG__ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY = eINSTANCE
				.getTGG_AttributeConstraintDefinitionLibrary();

		/**
		 * The meta object literal for the '{@link language.impl.TGGRuleImpl <em>TGG Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see language.impl.TGGRuleImpl
		 * @see language.impl.LanguagePackageImpl#getTGGRule()
		 * @generated
		 */
		EClass TGG_RULE = eINSTANCE.getTGGRule();

		/**
		 * The meta object literal for the '<em><b>Refines</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE__REFINES = eINSTANCE.getTGGRule_Refines();

		/**
		 * The meta object literal for the '<em><b>Nacs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE__NACS = eINSTANCE.getTGGRule_Nacs();

		/**
		 * The meta object literal for the '<em><b>Nodes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE__NODES = eINSTANCE.getTGGRule_Nodes();

		/**
		 * The meta object literal for the '<em><b>Edges</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE__EDGES = eINSTANCE.getTGGRule_Edges();

		/**
		 * The meta object literal for the '<em><b>Attribute Condition Library</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE__ATTRIBUTE_CONDITION_LIBRARY = eINSTANCE.getTGGRule_AttributeConditionLibrary();

		/**
		 * The meta object literal for the '<em><b>Abstract</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TGG_RULE__ABSTRACT = eINSTANCE.getTGGRule_Abstract();

		/**
		 * The meta object literal for the '{@link language.impl.TGGRuleElementImpl <em>TGG Rule Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see language.impl.TGGRuleElementImpl
		 * @see language.impl.LanguagePackageImpl#getTGGRuleElement()
		 * @generated
		 */
		EClass TGG_RULE_ELEMENT = eINSTANCE.getTGGRuleElement();

		/**
		 * The meta object literal for the '<em><b>Domain Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TGG_RULE_ELEMENT__DOMAIN_TYPE = eINSTANCE.getTGGRuleElement_DomainType();

		/**
		 * The meta object literal for the '<em><b>Binding Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TGG_RULE_ELEMENT__BINDING_TYPE = eINSTANCE.getTGGRuleElement_BindingType();

		/**
		 * The meta object literal for the '{@link language.impl.TGGRuleNodeImpl <em>TGG Rule Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see language.impl.TGGRuleNodeImpl
		 * @see language.impl.LanguagePackageImpl#getTGGRuleNode()
		 * @generated
		 */
		EClass TGG_RULE_NODE = eINSTANCE.getTGGRuleNode();

		/**
		 * The meta object literal for the '<em><b>Incoming Edges</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE_NODE__INCOMING_EDGES = eINSTANCE.getTGGRuleNode_IncomingEdges();

		/**
		 * The meta object literal for the '<em><b>Outgoing Edges</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE_NODE__OUTGOING_EDGES = eINSTANCE.getTGGRuleNode_OutgoingEdges();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE_NODE__TYPE = eINSTANCE.getTGGRuleNode_Type();

		/**
		 * The meta object literal for the '<em><b>Incoming Corrs Source</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE_NODE__INCOMING_CORRS_SOURCE = eINSTANCE.getTGGRuleNode_IncomingCorrsSource();

		/**
		 * The meta object literal for the '<em><b>Incoming Corrs Target</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE_NODE__INCOMING_CORRS_TARGET = eINSTANCE.getTGGRuleNode_IncomingCorrsTarget();

		/**
		 * The meta object literal for the '<em><b>Attr Expr</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE_NODE__ATTR_EXPR = eINSTANCE.getTGGRuleNode_AttrExpr();

		/**
		 * The meta object literal for the '{@link language.impl.TGGRuleCorrImpl <em>TGG Rule Corr</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see language.impl.TGGRuleCorrImpl
		 * @see language.impl.LanguagePackageImpl#getTGGRuleCorr()
		 * @generated
		 */
		EClass TGG_RULE_CORR = eINSTANCE.getTGGRuleCorr();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE_CORR__SOURCE = eINSTANCE.getTGGRuleCorr_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE_CORR__TARGET = eINSTANCE.getTGGRuleCorr_Target();

		/**
		 * The meta object literal for the '{@link language.impl.TGGRuleEdgeImpl <em>TGG Rule Edge</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see language.impl.TGGRuleEdgeImpl
		 * @see language.impl.LanguagePackageImpl#getTGGRuleEdge()
		 * @generated
		 */
		EClass TGG_RULE_EDGE = eINSTANCE.getTGGRuleEdge();

		/**
		 * The meta object literal for the '<em><b>Src Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE_EDGE__SRC_NODE = eINSTANCE.getTGGRuleEdge_SrcNode();

		/**
		 * The meta object literal for the '<em><b>Trg Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE_EDGE__TRG_NODE = eINSTANCE.getTGGRuleEdge_TrgNode();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE_EDGE__TYPE = eINSTANCE.getTGGRuleEdge_Type();

		/**
		 * The meta object literal for the '{@link language.impl.NACImpl <em>NAC</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see language.impl.NACImpl
		 * @see language.impl.LanguagePackageImpl#getNAC()
		 * @generated
		 */
		EClass NAC = eINSTANCE.getNAC();

		/**
		 * The meta object literal for the '<em><b>Nodes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NAC__NODES = eINSTANCE.getNAC_Nodes();

		/**
		 * The meta object literal for the '<em><b>Edges</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NAC__EDGES = eINSTANCE.getNAC_Edges();

		/**
		 * The meta object literal for the '<em><b>Attribute Condition Library</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NAC__ATTRIBUTE_CONDITION_LIBRARY = eINSTANCE.getNAC_AttributeConditionLibrary();

		/**
		 * The meta object literal for the '{@link language.impl.TGGComplementRuleImpl <em>TGG Complement Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see language.impl.TGGComplementRuleImpl
		 * @see language.impl.LanguagePackageImpl#getTGGComplementRule()
		 * @generated
		 */
		EClass TGG_COMPLEMENT_RULE = eINSTANCE.getTGGComplementRule();

		/**
		 * The meta object literal for the '<em><b>Bounded</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TGG_COMPLEMENT_RULE__BOUNDED = eINSTANCE.getTGGComplementRule_Bounded();

		/**
		 * The meta object literal for the '<em><b>Kernel</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_COMPLEMENT_RULE__KERNEL = eINSTANCE.getTGGComplementRule_Kernel();

		/**
		 * The meta object literal for the '{@link language.impl.TGGInplaceAttributeExpressionImpl <em>TGG Inplace Attribute Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see language.impl.TGGInplaceAttributeExpressionImpl
		 * @see language.impl.LanguagePackageImpl#getTGGInplaceAttributeExpression()
		 * @generated
		 */
		EClass TGG_INPLACE_ATTRIBUTE_EXPRESSION = eINSTANCE.getTGGInplaceAttributeExpression();

		/**
		 * The meta object literal for the '<em><b>Attribute</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_INPLACE_ATTRIBUTE_EXPRESSION__ATTRIBUTE = eINSTANCE.getTGGInplaceAttributeExpression_Attribute();

		/**
		 * The meta object literal for the '<em><b>Value Expr</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_INPLACE_ATTRIBUTE_EXPRESSION__VALUE_EXPR = eINSTANCE
				.getTGGInplaceAttributeExpression_ValueExpr();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TGG_INPLACE_ATTRIBUTE_EXPRESSION__OPERATOR = eINSTANCE.getTGGInplaceAttributeExpression_Operator();

		/**
		 * The meta object literal for the '{@link language.impl.TGGAttributeConstraintLibraryImpl <em>TGG Attribute Constraint Library</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see language.impl.TGGAttributeConstraintLibraryImpl
		 * @see language.impl.LanguagePackageImpl#getTGGAttributeConstraintLibrary()
		 * @generated
		 */
		EClass TGG_ATTRIBUTE_CONSTRAINT_LIBRARY = eINSTANCE.getTGGAttributeConstraintLibrary();

		/**
		 * The meta object literal for the '<em><b>Tgg Attribute Constraints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__TGG_ATTRIBUTE_CONSTRAINTS = eINSTANCE
				.getTGGAttributeConstraintLibrary_TggAttributeConstraints();

		/**
		 * The meta object literal for the '<em><b>Parameter Values</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__PARAMETER_VALUES = eINSTANCE
				.getTGGAttributeConstraintLibrary_ParameterValues();

		/**
		 * The meta object literal for the '{@link language.impl.TGGAttributeConstraintImpl <em>TGG Attribute Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see language.impl.TGGAttributeConstraintImpl
		 * @see language.impl.LanguagePackageImpl#getTGGAttributeConstraint()
		 * @generated
		 */
		EClass TGG_ATTRIBUTE_CONSTRAINT = eINSTANCE.getTGGAttributeConstraint();

		/**
		 * The meta object literal for the '<em><b>Definition</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_ATTRIBUTE_CONSTRAINT__DEFINITION = eINSTANCE.getTGGAttributeConstraint_Definition();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_ATTRIBUTE_CONSTRAINT__PARAMETERS = eINSTANCE.getTGGAttributeConstraint_Parameters();

		/**
		 * The meta object literal for the '{@link language.impl.TGGAttributeVariableImpl <em>TGG Attribute Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see language.impl.TGGAttributeVariableImpl
		 * @see language.impl.LanguagePackageImpl#getTGGAttributeVariable()
		 * @generated
		 */
		EClass TGG_ATTRIBUTE_VARIABLE = eINSTANCE.getTGGAttributeVariable();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TGG_ATTRIBUTE_VARIABLE__NAME = eINSTANCE.getTGGAttributeVariable_Name();

		/**
		 * The meta object literal for the '{@link language.impl.TGGAttributeConstraintDefinitionLibraryImpl <em>TGG Attribute Constraint Definition Library</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see language.impl.TGGAttributeConstraintDefinitionLibraryImpl
		 * @see language.impl.LanguagePackageImpl#getTGGAttributeConstraintDefinitionLibrary()
		 * @generated
		 */
		EClass TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY = eINSTANCE.getTGGAttributeConstraintDefinitionLibrary();

		/**
		 * The meta object literal for the '<em><b>Tgg Attribute Constraint Definitions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY__TGG_ATTRIBUTE_CONSTRAINT_DEFINITIONS = eINSTANCE
				.getTGGAttributeConstraintDefinitionLibrary_TggAttributeConstraintDefinitions();

		/**
		 * The meta object literal for the '{@link language.impl.TGGAttributeConstraintDefinitionImpl <em>TGG Attribute Constraint Definition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see language.impl.TGGAttributeConstraintDefinitionImpl
		 * @see language.impl.LanguagePackageImpl#getTGGAttributeConstraintDefinition()
		 * @generated
		 */
		EClass TGG_ATTRIBUTE_CONSTRAINT_DEFINITION = eINSTANCE.getTGGAttributeConstraintDefinition();

		/**
		 * The meta object literal for the '<em><b>User Defined</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__USER_DEFINED = eINSTANCE
				.getTGGAttributeConstraintDefinition_UserDefined();

		/**
		 * The meta object literal for the '<em><b>Parameter Definitions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__PARAMETER_DEFINITIONS = eINSTANCE
				.getTGGAttributeConstraintDefinition_ParameterDefinitions();

		/**
		 * The meta object literal for the '<em><b>Sync Adornments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__SYNC_ADORNMENTS = eINSTANCE
				.getTGGAttributeConstraintDefinition_SyncAdornments();

		/**
		 * The meta object literal for the '<em><b>Gen Adornments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__GEN_ADORNMENTS = eINSTANCE
				.getTGGAttributeConstraintDefinition_GenAdornments();

		/**
		 * The meta object literal for the '{@link language.impl.TGGAttributeConstraintParameterDefinitionImpl <em>TGG Attribute Constraint Parameter Definition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see language.impl.TGGAttributeConstraintParameterDefinitionImpl
		 * @see language.impl.LanguagePackageImpl#getTGGAttributeConstraintParameterDefinition()
		 * @generated
		 */
		EClass TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_DEFINITION = eINSTANCE.getTGGAttributeConstraintParameterDefinition();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_DEFINITION__TYPE = eINSTANCE
				.getTGGAttributeConstraintParameterDefinition_Type();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_DEFINITION__NAME = eINSTANCE
				.getTGGAttributeConstraintParameterDefinition_Name();

		/**
		 * The meta object literal for the '{@link language.impl.TGGAttributeConstraintAdornmentImpl <em>TGG Attribute Constraint Adornment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see language.impl.TGGAttributeConstraintAdornmentImpl
		 * @see language.impl.LanguagePackageImpl#getTGGAttributeConstraintAdornment()
		 * @generated
		 */
		EClass TGG_ATTRIBUTE_CONSTRAINT_ADORNMENT = eINSTANCE.getTGGAttributeConstraintAdornment();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TGG_ATTRIBUTE_CONSTRAINT_ADORNMENT__VALUE = eINSTANCE.getTGGAttributeConstraintAdornment_Value();

		/**
		 * The meta object literal for the '{@link language.impl.TGGNamedElementImpl <em>TGG Named Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see language.impl.TGGNamedElementImpl
		 * @see language.impl.LanguagePackageImpl#getTGGNamedElement()
		 * @generated
		 */
		EClass TGG_NAMED_ELEMENT = eINSTANCE.getTGGNamedElement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TGG_NAMED_ELEMENT__NAME = eINSTANCE.getTGGNamedElement_Name();

		/**
		 * The meta object literal for the '{@link language.impl.TGGParamValueImpl <em>TGG Param Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see language.impl.TGGParamValueImpl
		 * @see language.impl.LanguagePackageImpl#getTGGParamValue()
		 * @generated
		 */
		EClass TGG_PARAM_VALUE = eINSTANCE.getTGGParamValue();

		/**
		 * The meta object literal for the '<em><b>Parameter Definition</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_PARAM_VALUE__PARAMETER_DEFINITION = eINSTANCE.getTGGParamValue_ParameterDefinition();

		/**
		 * The meta object literal for the '{@link language.impl.TGGExpressionImpl <em>TGG Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see language.impl.TGGExpressionImpl
		 * @see language.impl.LanguagePackageImpl#getTGGExpression()
		 * @generated
		 */
		EClass TGG_EXPRESSION = eINSTANCE.getTGGExpression();

		/**
		 * The meta object literal for the '{@link language.impl.TGGLiteralExpressionImpl <em>TGG Literal Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see language.impl.TGGLiteralExpressionImpl
		 * @see language.impl.LanguagePackageImpl#getTGGLiteralExpression()
		 * @generated
		 */
		EClass TGG_LITERAL_EXPRESSION = eINSTANCE.getTGGLiteralExpression();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TGG_LITERAL_EXPRESSION__VALUE = eINSTANCE.getTGGLiteralExpression_Value();

		/**
		 * The meta object literal for the '{@link language.impl.TGGAttributeExpressionImpl <em>TGG Attribute Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see language.impl.TGGAttributeExpressionImpl
		 * @see language.impl.LanguagePackageImpl#getTGGAttributeExpression()
		 * @generated
		 */
		EClass TGG_ATTRIBUTE_EXPRESSION = eINSTANCE.getTGGAttributeExpression();

		/**
		 * The meta object literal for the '<em><b>Object Var</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_ATTRIBUTE_EXPRESSION__OBJECT_VAR = eINSTANCE.getTGGAttributeExpression_ObjectVar();

		/**
		 * The meta object literal for the '<em><b>Attribute</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_ATTRIBUTE_EXPRESSION__ATTRIBUTE = eINSTANCE.getTGGAttributeExpression_Attribute();

		/**
		 * The meta object literal for the '{@link language.impl.TGGEnumExpressionImpl <em>TGG Enum Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see language.impl.TGGEnumExpressionImpl
		 * @see language.impl.LanguagePackageImpl#getTGGEnumExpression()
		 * @generated
		 */
		EClass TGG_ENUM_EXPRESSION = eINSTANCE.getTGGEnumExpression();

		/**
		 * The meta object literal for the '<em><b>Eenum</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_ENUM_EXPRESSION__EENUM = eINSTANCE.getTGGEnumExpression_Eenum();

		/**
		 * The meta object literal for the '<em><b>Literal</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_ENUM_EXPRESSION__LITERAL = eINSTANCE.getTGGEnumExpression_Literal();

		/**
		 * The meta object literal for the '{@link language.DomainType <em>Domain Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see language.DomainType
		 * @see language.impl.LanguagePackageImpl#getDomainType()
		 * @generated
		 */
		EEnum DOMAIN_TYPE = eINSTANCE.getDomainType();

		/**
		 * The meta object literal for the '{@link language.BindingType <em>Binding Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see language.BindingType
		 * @see language.impl.LanguagePackageImpl#getBindingType()
		 * @generated
		 */
		EEnum BINDING_TYPE = eINSTANCE.getBindingType();

		/**
		 * The meta object literal for the '{@link language.TGGAttributeConstraintOperators <em>TGG Attribute Constraint Operators</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see language.TGGAttributeConstraintOperators
		 * @see language.impl.LanguagePackageImpl#getTGGAttributeConstraintOperators()
		 * @generated
		 */
		EEnum TGG_ATTRIBUTE_CONSTRAINT_OPERATORS = eINSTANCE.getTGGAttributeConstraintOperators();

	}

} //LanguagePackage
