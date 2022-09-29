/**
 */
package org.emoflon.ibex.gt.gtmodel.IBeXGTModel;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.IBeXCoreArithmeticPackage;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage;

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
 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelFactory
 * @model kind="package"
 * @generated
 */
public interface IBeXGTModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "IBeXGTModel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "platform:/resource/org.emoflon.ibex.gt.gtmodel/model/IBeXGTModel.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "IBeXGTModel";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	IBeXGTModelPackage eINSTANCE = org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.IBeXGTModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTModelImpl <em>GT Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTModelImpl
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.IBeXGTModelPackageImpl#getGTModel()
	 * @generated
	 */
	int GT_MODEL = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_MODEL__NAME = IBeXCoreModelPackage.IBE_XMODEL__NAME;

	/**
	 * The feature id for the '<em><b>Meta Data</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_MODEL__META_DATA = IBeXCoreModelPackage.IBE_XMODEL__META_DATA;

	/**
	 * The feature id for the '<em><b>Feature Config</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_MODEL__FEATURE_CONFIG = IBeXCoreModelPackage.IBE_XMODEL__FEATURE_CONFIG;

	/**
	 * The feature id for the '<em><b>Pattern Set</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_MODEL__PATTERN_SET = IBeXCoreModelPackage.IBE_XMODEL__PATTERN_SET;

	/**
	 * The feature id for the '<em><b>Node Set</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_MODEL__NODE_SET = IBeXCoreModelPackage.IBE_XMODEL__NODE_SET;

	/**
	 * The feature id for the '<em><b>Edge Set</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_MODEL__EDGE_SET = IBeXCoreModelPackage.IBE_XMODEL__EDGE_SET;

	/**
	 * The feature id for the '<em><b>Rule Set</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_MODEL__RULE_SET = IBeXCoreModelPackage.IBE_XMODEL_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>GT Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_MODEL_FEATURE_COUNT = IBeXCoreModelPackage.IBE_XMODEL_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>GT Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_MODEL_OPERATION_COUNT = IBeXCoreModelPackage.IBE_XMODEL_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTRuleSetImpl <em>GT Rule Set</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTRuleSetImpl
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.IBeXGTModelPackageImpl#getGTRuleSet()
	 * @generated
	 */
	int GT_RULE_SET = 1;

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
	 * The meta object id for the '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTPatternImpl <em>GT Pattern</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTPatternImpl
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.IBeXGTModelPackageImpl#getGTPattern()
	 * @generated
	 */
	int GT_PATTERN = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_PATTERN__NAME = IBeXCoreModelPackage.IBE_XPATTERN__NAME;

	/**
	 * The feature id for the '<em><b>Empty</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_PATTERN__EMPTY = IBeXCoreModelPackage.IBE_XPATTERN__EMPTY;

	/**
	 * The feature id for the '<em><b>Dependencies</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_PATTERN__DEPENDENCIES = IBeXCoreModelPackage.IBE_XPATTERN__DEPENDENCIES;

	/**
	 * The feature id for the '<em><b>Signature Nodes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_PATTERN__SIGNATURE_NODES = IBeXCoreModelPackage.IBE_XPATTERN__SIGNATURE_NODES;

	/**
	 * The feature id for the '<em><b>Local Nodes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_PATTERN__LOCAL_NODES = IBeXCoreModelPackage.IBE_XPATTERN__LOCAL_NODES;

	/**
	 * The feature id for the '<em><b>Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_PATTERN__EDGES = IBeXCoreModelPackage.IBE_XPATTERN__EDGES;

	/**
	 * The feature id for the '<em><b>Conditions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_PATTERN__CONDITIONS = IBeXCoreModelPackage.IBE_XPATTERN__CONDITIONS;

	/**
	 * The feature id for the '<em><b>Invocations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_PATTERN__INVOCATIONS = IBeXCoreModelPackage.IBE_XPATTERN__INVOCATIONS;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_PATTERN__PARAMETERS = IBeXCoreModelPackage.IBE_XPATTERN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Watch Dogs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_PATTERN__WATCH_DOGS = IBeXCoreModelPackage.IBE_XPATTERN_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>GT Pattern</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_PATTERN_FEATURE_COUNT = IBeXCoreModelPackage.IBE_XPATTERN_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>GT Pattern</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_PATTERN_OPERATION_COUNT = IBeXCoreModelPackage.IBE_XPATTERN_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTRuleImpl <em>GT Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTRuleImpl
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.IBeXGTModelPackageImpl#getGTRule()
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
	int GT_RULE__NAME = IBeXCoreModelPackage.IBE_XRULE__NAME;

	/**
	 * The feature id for the '<em><b>Precondition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_RULE__PRECONDITION = IBeXCoreModelPackage.IBE_XRULE__PRECONDITION;

	/**
	 * The feature id for the '<em><b>Postcondition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_RULE__POSTCONDITION = IBeXCoreModelPackage.IBE_XRULE__POSTCONDITION;

	/**
	 * The feature id for the '<em><b>Creation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_RULE__CREATION = IBeXCoreModelPackage.IBE_XRULE__CREATION;

	/**
	 * The feature id for the '<em><b>Deletion</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_RULE__DELETION = IBeXCoreModelPackage.IBE_XRULE__DELETION;

	/**
	 * The feature id for the '<em><b>Attribute Assignments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_RULE__ATTRIBUTE_ASSIGNMENTS = IBeXCoreModelPackage.IBE_XRULE__ATTRIBUTE_ASSIGNMENTS;

	/**
	 * The feature id for the '<em><b>All Nodes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_RULE__ALL_NODES = IBeXCoreModelPackage.IBE_XRULE__ALL_NODES;

	/**
	 * The feature id for the '<em><b>All Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_RULE__ALL_EDGES = IBeXCoreModelPackage.IBE_XRULE__ALL_EDGES;

	/**
	 * The feature id for the '<em><b>For Each Operations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_RULE__FOR_EACH_OPERATIONS = IBeXCoreModelPackage.IBE_XRULE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Probability</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_RULE__PROBABILITY = IBeXCoreModelPackage.IBE_XRULE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>GT Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_RULE_FEATURE_COUNT = IBeXCoreModelPackage.IBE_XRULE_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>GT Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_RULE_OPERATION_COUNT = IBeXCoreModelPackage.IBE_XRULE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTForEachExpressionImpl <em>GT For Each Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTForEachExpressionImpl
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.IBeXGTModelPackageImpl#getGTForEachExpression()
	 * @generated
	 */
	int GT_FOR_EACH_EXPRESSION = 4;

	/**
	 * The feature id for the '<em><b>Create</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_FOR_EACH_EXPRESSION__CREATE = 0;

	/**
	 * The feature id for the '<em><b>Delete</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_FOR_EACH_EXPRESSION__DELETE = 1;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_FOR_EACH_EXPRESSION__SOURCE = 2;

	/**
	 * The feature id for the '<em><b>Trg Iterator</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_FOR_EACH_EXPRESSION__TRG_ITERATOR = 3;

	/**
	 * The feature id for the '<em><b>Edge</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_FOR_EACH_EXPRESSION__EDGE = 4;

	/**
	 * The number of structural features of the '<em>GT For Each Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_FOR_EACH_EXPRESSION_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>GT For Each Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_FOR_EACH_EXPRESSION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTParameterImpl <em>GT Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTParameterImpl
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.IBeXGTModelPackageImpl#getGTParameter()
	 * @generated
	 */
	int GT_PARAMETER = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_PARAMETER__NAME = IBeXCoreModelPackage.IBE_XNAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_PARAMETER__TYPE = IBeXCoreModelPackage.IBE_XNAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>GT Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_PARAMETER_FEATURE_COUNT = IBeXCoreModelPackage.IBE_XNAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>GT Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_PARAMETER_OPERATION_COUNT = IBeXCoreModelPackage.IBE_XNAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTParameterValueImpl <em>GT Parameter Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTParameterValueImpl
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.IBeXGTModelPackageImpl#getGTParameterValue()
	 * @generated
	 */
	int GT_PARAMETER_VALUE = 6;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_PARAMETER_VALUE__TYPE = IBeXCoreArithmeticPackage.ARITHMETIC_VALUE__TYPE;

	/**
	 * The feature id for the '<em><b>Parameter</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_PARAMETER_VALUE__PARAMETER = IBeXCoreArithmeticPackage.ARITHMETIC_VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>GT Parameter Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_PARAMETER_VALUE_FEATURE_COUNT = IBeXCoreArithmeticPackage.ARITHMETIC_VALUE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>GT Parameter Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_PARAMETER_VALUE_OPERATION_COUNT = IBeXCoreArithmeticPackage.ARITHMETIC_VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTWatchDogImpl <em>GT Watch Dog</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTWatchDogImpl
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.IBeXGTModelPackageImpl#getGTWatchDog()
	 * @generated
	 */
	int GT_WATCH_DOG = 7;

	/**
	 * The feature id for the '<em><b>Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_WATCH_DOG__NODE = 0;

	/**
	 * The feature id for the '<em><b>Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_WATCH_DOG__ATTRIBUTE = 1;

	/**
	 * The number of structural features of the '<em>GT Watch Dog</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_WATCH_DOG_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>GT Watch Dog</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_WATCH_DOG_OPERATION_COUNT = 0;

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTModel <em>GT Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>GT Model</em>'.
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTModel
	 * @generated
	 */
	EClass getGTModel();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTModel#getRuleSet <em>Rule Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rule Set</em>'.
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTModel#getRuleSet()
	 * @see #getGTModel()
	 * @generated
	 */
	EReference getGTModel_RuleSet();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTRuleSet <em>GT Rule Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>GT Rule Set</em>'.
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTRuleSet
	 * @generated
	 */
	EClass getGTRuleSet();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTRuleSet#getRules <em>Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Rules</em>'.
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTRuleSet#getRules()
	 * @see #getGTRuleSet()
	 * @generated
	 */
	EReference getGTRuleSet_Rules();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern <em>GT Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>GT Pattern</em>'.
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern
	 * @generated
	 */
	EClass getGTPattern();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern#getParameters()
	 * @see #getGTPattern()
	 * @generated
	 */
	EReference getGTPattern_Parameters();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern#getWatchDogs <em>Watch Dogs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Watch Dogs</em>'.
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern#getWatchDogs()
	 * @see #getGTPattern()
	 * @generated
	 */
	EReference getGTPattern_WatchDogs();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTRule <em>GT Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>GT Rule</em>'.
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTRule
	 * @generated
	 */
	EClass getGTRule();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTRule#getForEachOperations <em>For Each Operations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>For Each Operations</em>'.
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTRule#getForEachOperations()
	 * @see #getGTRule()
	 * @generated
	 */
	EReference getGTRule_ForEachOperations();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTRule#getProbability <em>Probability</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Probability</em>'.
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTRule#getProbability()
	 * @see #getGTRule()
	 * @generated
	 */
	EReference getGTRule_Probability();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTForEachExpression <em>GT For Each Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>GT For Each Expression</em>'.
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTForEachExpression
	 * @generated
	 */
	EClass getGTForEachExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTForEachExpression#getCreate <em>Create</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Create</em>'.
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTForEachExpression#getCreate()
	 * @see #getGTForEachExpression()
	 * @generated
	 */
	EReference getGTForEachExpression_Create();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTForEachExpression#getDelete <em>Delete</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Delete</em>'.
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTForEachExpression#getDelete()
	 * @see #getGTForEachExpression()
	 * @generated
	 */
	EReference getGTForEachExpression_Delete();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTForEachExpression#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTForEachExpression#getSource()
	 * @see #getGTForEachExpression()
	 * @generated
	 */
	EReference getGTForEachExpression_Source();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTForEachExpression#getTrgIterator <em>Trg Iterator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Trg Iterator</em>'.
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTForEachExpression#getTrgIterator()
	 * @see #getGTForEachExpression()
	 * @generated
	 */
	EReference getGTForEachExpression_TrgIterator();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTForEachExpression#getEdge <em>Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Edge</em>'.
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTForEachExpression#getEdge()
	 * @see #getGTForEachExpression()
	 * @generated
	 */
	EReference getGTForEachExpression_Edge();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTParameter <em>GT Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>GT Parameter</em>'.
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTParameter
	 * @generated
	 */
	EClass getGTParameter();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTParameter#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTParameter#getType()
	 * @see #getGTParameter()
	 * @generated
	 */
	EReference getGTParameter_Type();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTParameterValue <em>GT Parameter Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>GT Parameter Value</em>'.
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTParameterValue
	 * @generated
	 */
	EClass getGTParameterValue();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTParameterValue#getParameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parameter</em>'.
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTParameterValue#getParameter()
	 * @see #getGTParameterValue()
	 * @generated
	 */
	EReference getGTParameterValue_Parameter();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTWatchDog <em>GT Watch Dog</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>GT Watch Dog</em>'.
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTWatchDog
	 * @generated
	 */
	EClass getGTWatchDog();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTWatchDog#getNode <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Node</em>'.
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTWatchDog#getNode()
	 * @see #getGTWatchDog()
	 * @generated
	 */
	EReference getGTWatchDog_Node();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTWatchDog#getAttribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Attribute</em>'.
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTWatchDog#getAttribute()
	 * @see #getGTWatchDog()
	 * @generated
	 */
	EReference getGTWatchDog_Attribute();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	IBeXGTModelFactory getIBeXGTModelFactory();

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
		 * The meta object literal for the '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTModelImpl <em>GT Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTModelImpl
		 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.IBeXGTModelPackageImpl#getGTModel()
		 * @generated
		 */
		EClass GT_MODEL = eINSTANCE.getGTModel();

		/**
		 * The meta object literal for the '<em><b>Rule Set</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GT_MODEL__RULE_SET = eINSTANCE.getGTModel_RuleSet();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTRuleSetImpl <em>GT Rule Set</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTRuleSetImpl
		 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.IBeXGTModelPackageImpl#getGTRuleSet()
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

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTPatternImpl <em>GT Pattern</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTPatternImpl
		 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.IBeXGTModelPackageImpl#getGTPattern()
		 * @generated
		 */
		EClass GT_PATTERN = eINSTANCE.getGTPattern();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GT_PATTERN__PARAMETERS = eINSTANCE.getGTPattern_Parameters();

		/**
		 * The meta object literal for the '<em><b>Watch Dogs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GT_PATTERN__WATCH_DOGS = eINSTANCE.getGTPattern_WatchDogs();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTRuleImpl <em>GT Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTRuleImpl
		 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.IBeXGTModelPackageImpl#getGTRule()
		 * @generated
		 */
		EClass GT_RULE = eINSTANCE.getGTRule();

		/**
		 * The meta object literal for the '<em><b>For Each Operations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GT_RULE__FOR_EACH_OPERATIONS = eINSTANCE.getGTRule_ForEachOperations();

		/**
		 * The meta object literal for the '<em><b>Probability</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GT_RULE__PROBABILITY = eINSTANCE.getGTRule_Probability();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTForEachExpressionImpl <em>GT For Each Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTForEachExpressionImpl
		 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.IBeXGTModelPackageImpl#getGTForEachExpression()
		 * @generated
		 */
		EClass GT_FOR_EACH_EXPRESSION = eINSTANCE.getGTForEachExpression();

		/**
		 * The meta object literal for the '<em><b>Create</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GT_FOR_EACH_EXPRESSION__CREATE = eINSTANCE.getGTForEachExpression_Create();

		/**
		 * The meta object literal for the '<em><b>Delete</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GT_FOR_EACH_EXPRESSION__DELETE = eINSTANCE.getGTForEachExpression_Delete();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GT_FOR_EACH_EXPRESSION__SOURCE = eINSTANCE.getGTForEachExpression_Source();

		/**
		 * The meta object literal for the '<em><b>Trg Iterator</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GT_FOR_EACH_EXPRESSION__TRG_ITERATOR = eINSTANCE.getGTForEachExpression_TrgIterator();

		/**
		 * The meta object literal for the '<em><b>Edge</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GT_FOR_EACH_EXPRESSION__EDGE = eINSTANCE.getGTForEachExpression_Edge();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTParameterImpl <em>GT Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTParameterImpl
		 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.IBeXGTModelPackageImpl#getGTParameter()
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
		 * The meta object literal for the '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTParameterValueImpl <em>GT Parameter Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTParameterValueImpl
		 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.IBeXGTModelPackageImpl#getGTParameterValue()
		 * @generated
		 */
		EClass GT_PARAMETER_VALUE = eINSTANCE.getGTParameterValue();

		/**
		 * The meta object literal for the '<em><b>Parameter</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GT_PARAMETER_VALUE__PARAMETER = eINSTANCE.getGTParameterValue_Parameter();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTWatchDogImpl <em>GT Watch Dog</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTWatchDogImpl
		 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.IBeXGTModelPackageImpl#getGTWatchDog()
		 * @generated
		 */
		EClass GT_WATCH_DOG = eINSTANCE.getGTWatchDog();

		/**
		 * The meta object literal for the '<em><b>Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GT_WATCH_DOG__NODE = eINSTANCE.getGTWatchDog_Node();

		/**
		 * The meta object literal for the '<em><b>Attribute</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GT_WATCH_DOG__ATTRIBUTE = eINSTANCE.getGTWatchDog_Attribute();

	}

} //IBeXGTModelPackage
