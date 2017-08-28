/**
 */
package language;

import language.basic.BasicPackage;

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
	String eNS_PREFIX = "org.emoflon.ibex.tgg.core.language";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	LanguagePackage eINSTANCE = language.impl.LanguagePackageImpl.init();

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
	int TGG__NAME = BasicPackage.TGG_NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Src</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG__SRC = BasicPackage.TGG_NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Trg</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG__TRG = BasicPackage.TGG_NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Corr</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG__CORR = BasicPackage.TGG_NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG__RULES = BasicPackage.TGG_NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Attribute Constraint Definition Library</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG__ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY = BasicPackage.TGG_NAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>TGG</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_FEATURE_COUNT = BasicPackage.TGG_NAMED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The number of operations of the '<em>TGG</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_OPERATION_COUNT = BasicPackage.TGG_NAMED_ELEMENT_OPERATION_COUNT + 0;

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
	int TGG_RULE__NAME = BasicPackage.TGG_NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Refines</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__REFINES = BasicPackage.TGG_NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Complements</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__COMPLEMENTS = BasicPackage.TGG_NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Nacs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__NACS = BasicPackage.TGG_NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__NODES = BasicPackage.TGG_NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Edges</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__EDGES = BasicPackage.TGG_NAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Attribute Condition Library</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__ATTRIBUTE_CONDITION_LIBRARY = BasicPackage.TGG_NAMED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Abstract</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE__ABSTRACT = BasicPackage.TGG_NAMED_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>TGG Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_FEATURE_COUNT = BasicPackage.TGG_NAMED_ELEMENT_FEATURE_COUNT + 7;

	/**
	 * The number of operations of the '<em>TGG Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_OPERATION_COUNT = BasicPackage.TGG_NAMED_ELEMENT_OPERATION_COUNT + 0;

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
	int TGG_RULE_ELEMENT__NAME = BasicPackage.TGG_NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Domain Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_ELEMENT__DOMAIN_TYPE = BasicPackage.TGG_NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Binding Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_ELEMENT__BINDING_TYPE = BasicPackage.TGG_NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>TGG Rule Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_ELEMENT_FEATURE_COUNT = BasicPackage.TGG_NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>TGG Rule Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_ELEMENT_OPERATION_COUNT = BasicPackage.TGG_NAMED_ELEMENT_OPERATION_COUNT + 0;

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
	int NAC__NAME = BasicPackage.TGG_NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAC__NODES = BasicPackage.TGG_NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Edges</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAC__EDGES = BasicPackage.TGG_NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Attribute Condition Library</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAC__ATTRIBUTE_CONDITION_LIBRARY = BasicPackage.TGG_NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>NAC</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAC_FEATURE_COUNT = BasicPackage.TGG_NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>NAC</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAC_OPERATION_COUNT = BasicPackage.TGG_NAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link language.DomainType <em>Domain Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see language.DomainType
	 * @see language.impl.LanguagePackageImpl#getDomainType()
	 * @generated
	 */
	int DOMAIN_TYPE = 7;

	/**
	 * The meta object id for the '{@link language.BindingType <em>Binding Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see language.BindingType
	 * @see language.impl.LanguagePackageImpl#getBindingType()
	 * @generated
	 */
	int BINDING_TYPE = 8;

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
	 * Returns the meta object for the reference '{@link language.TGGRule#getComplements <em>Complements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Complements</em>'.
	 * @see language.TGGRule#getComplements()
	 * @see #getTGGRule()
	 * @generated
	 */
	EReference getTGGRule_Complements();

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
		 * The meta object literal for the '<em><b>Complements</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE__COMPLEMENTS = eINSTANCE.getTGGRule_Complements();

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

	}

} //LanguagePackage
