/**
 */
package StochasticLanguage;

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
 * <!-- begin-model-doc -->
 * Model for the Stochastic Extension of the EMoflon API.
 * <!-- end-model-doc -->
 * @see StochasticLanguage.StochasticLanguageFactory
 * @model kind="package"
 * @generated
 */
public interface StochasticLanguagePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "StochasticLanguage";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "platform:/resource/org.emoflon.ibex.common/model/StochasticExtension.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "StochasticLangugae";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	StochasticLanguagePackage eINSTANCE = StochasticLanguage.impl.StochasticLanguagePackageImpl.init();

	/**
	 * The meta object id for the '{@link StochasticLanguage.impl.GTStochasticNamedElementImpl <em>GT Stochastic Named Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see StochasticLanguage.impl.GTStochasticNamedElementImpl
	 * @see StochasticLanguage.impl.StochasticLanguagePackageImpl#getGTStochasticNamedElement()
	 * @generated
	 */
	int GT_STOCHASTIC_NAMED_ELEMENT = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_STOCHASTIC_NAMED_ELEMENT__NAME = 0;

	/**
	 * The number of structural features of the '<em>GT Stochastic Named Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_STOCHASTIC_NAMED_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>GT Stochastic Named Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_STOCHASTIC_NAMED_ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link StochasticLanguage.impl.GTStochasticNodeImpl <em>GT Stochastic Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see StochasticLanguage.impl.GTStochasticNodeImpl
	 * @see StochasticLanguage.impl.StochasticLanguagePackageImpl#getGTStochasticNode()
	 * @generated
	 */
	int GT_STOCHASTIC_NODE = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_STOCHASTIC_NODE__NAME = GT_STOCHASTIC_NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_STOCHASTIC_NODE__TYPE = GT_STOCHASTIC_NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>GT Stochastic Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_STOCHASTIC_NODE_FEATURE_COUNT = GT_STOCHASTIC_NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>GT Stochastic Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_STOCHASTIC_NODE_OPERATION_COUNT = GT_STOCHASTIC_NAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link StochasticLanguage.impl.GTAttributeImpl <em>GT Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see StochasticLanguage.impl.GTAttributeImpl
	 * @see StochasticLanguage.impl.StochasticLanguagePackageImpl#getGTAttribute()
	 * @generated
	 */
	int GT_ATTRIBUTE = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_ATTRIBUTE__NAME = GT_STOCHASTIC_NODE__NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_ATTRIBUTE__TYPE = GT_STOCHASTIC_NODE__TYPE;

	/**
	 * The feature id for the '<em><b>Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_ATTRIBUTE__ATTRIBUTE = GT_STOCHASTIC_NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Negative</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_ATTRIBUTE__NEGATIVE = GT_STOCHASTIC_NODE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>GT Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_ATTRIBUTE_FEATURE_COUNT = GT_STOCHASTIC_NODE_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>GT Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_ATTRIBUTE_OPERATION_COUNT = GT_STOCHASTIC_NODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link StochasticLanguage.impl.GTStochasticFunctionImpl <em>GT Stochastic Function</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see StochasticLanguage.impl.GTStochasticFunctionImpl
	 * @see StochasticLanguage.impl.StochasticLanguagePackageImpl#getGTStochasticFunction()
	 * @generated
	 */
	int GT_STOCHASTIC_FUNCTION = 3;

	/**
	 * The feature id for the '<em><b>Mean</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_STOCHASTIC_FUNCTION__MEAN = 0;

	/**
	 * The feature id for the '<em><b>Sd</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_STOCHASTIC_FUNCTION__SD = 1;

	/**
	 * The feature id for the '<em><b>Distribution</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_STOCHASTIC_FUNCTION__DISTRIBUTION = 2;

	/**
	 * The number of structural features of the '<em>GT Stochastic Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_STOCHASTIC_FUNCTION_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>GT Stochastic Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_STOCHASTIC_FUNCTION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link StochasticLanguage.GTArithmetics <em>GT Arithmetics</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see StochasticLanguage.GTArithmetics
	 * @see StochasticLanguage.impl.StochasticLanguagePackageImpl#getGTArithmetics()
	 * @generated
	 */
	int GT_ARITHMETICS = 5;

	/**
	 * The number of structural features of the '<em>GT Arithmetics</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_ARITHMETICS_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>GT Arithmetics</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_ARITHMETICS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link StochasticLanguage.impl.GTNumberImpl <em>GT Number</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see StochasticLanguage.impl.GTNumberImpl
	 * @see StochasticLanguage.impl.StochasticLanguagePackageImpl#getGTNumber()
	 * @generated
	 */
	int GT_NUMBER = 4;

	/**
	 * The feature id for the '<em><b>Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_NUMBER__NUMBER = GT_ARITHMETICS_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>GT Number</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_NUMBER_FEATURE_COUNT = GT_ARITHMETICS_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>GT Number</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_NUMBER_OPERATION_COUNT = GT_ARITHMETICS_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link StochasticLanguage.impl.GTTwoParameterCalculationImpl <em>GT Two Parameter Calculation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see StochasticLanguage.impl.GTTwoParameterCalculationImpl
	 * @see StochasticLanguage.impl.StochasticLanguagePackageImpl#getGTTwoParameterCalculation()
	 * @generated
	 */
	int GT_TWO_PARAMETER_CALCULATION = 6;

	/**
	 * The feature id for the '<em><b>Left</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_TWO_PARAMETER_CALCULATION__LEFT = GT_ARITHMETICS_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Right</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_TWO_PARAMETER_CALCULATION__RIGHT = GT_ARITHMETICS_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_TWO_PARAMETER_CALCULATION__OPERATOR = GT_ARITHMETICS_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>GT Two Parameter Calculation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_TWO_PARAMETER_CALCULATION_FEATURE_COUNT = GT_ARITHMETICS_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>GT Two Parameter Calculation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_TWO_PARAMETER_CALCULATION_OPERATION_COUNT = GT_ARITHMETICS_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link StochasticLanguage.impl.GTOneParameterCalculationImpl <em>GT One Parameter Calculation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see StochasticLanguage.impl.GTOneParameterCalculationImpl
	 * @see StochasticLanguage.impl.StochasticLanguagePackageImpl#getGTOneParameterCalculation()
	 * @generated
	 */
	int GT_ONE_PARAMETER_CALCULATION = 7;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_ONE_PARAMETER_CALCULATION__VALUE = GT_ARITHMETICS_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_ONE_PARAMETER_CALCULATION__OPERATOR = GT_ARITHMETICS_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Negative</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_ONE_PARAMETER_CALCULATION__NEGATIVE = GT_ARITHMETICS_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>GT One Parameter Calculation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_ONE_PARAMETER_CALCULATION_FEATURE_COUNT = GT_ARITHMETICS_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>GT One Parameter Calculation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GT_ONE_PARAMETER_CALCULATION_OPERATION_COUNT = GT_ARITHMETICS_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link StochasticLanguage.GTStochasticDistribution <em>GT Stochastic Distribution</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see StochasticLanguage.GTStochasticDistribution
	 * @see StochasticLanguage.impl.StochasticLanguagePackageImpl#getGTStochasticDistribution()
	 * @generated
	 */
	int GT_STOCHASTIC_DISTRIBUTION = 8;

	/**
	 * The meta object id for the '{@link StochasticLanguage.GTStochasticRange <em>GT Stochastic Range</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see StochasticLanguage.GTStochasticRange
	 * @see StochasticLanguage.impl.StochasticLanguagePackageImpl#getGTStochasticRange()
	 * @generated
	 */
	int GT_STOCHASTIC_RANGE = 9;

	/**
	 * The meta object id for the '{@link StochasticLanguage.TwoParameterOperator <em>Two Parameter Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see StochasticLanguage.TwoParameterOperator
	 * @see StochasticLanguage.impl.StochasticLanguagePackageImpl#getTwoParameterOperator()
	 * @generated
	 */
	int TWO_PARAMETER_OPERATOR = 10;

	/**
	 * The meta object id for the '{@link StochasticLanguage.OneParameterOperator <em>One Parameter Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see StochasticLanguage.OneParameterOperator
	 * @see StochasticLanguage.impl.StochasticLanguagePackageImpl#getOneParameterOperator()
	 * @generated
	 */
	int ONE_PARAMETER_OPERATOR = 11;

	/**
	 * The meta object id for the '{@link StochasticLanguage.GTRelation <em>GT Relation</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see StochasticLanguage.GTRelation
	 * @see StochasticLanguage.impl.StochasticLanguagePackageImpl#getGTRelation()
	 * @generated
	 */
	int GT_RELATION = 12;

	/**
	 * Returns the meta object for class '{@link StochasticLanguage.GTStochasticNamedElement <em>GT Stochastic Named Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>GT Stochastic Named Element</em>'.
	 * @see StochasticLanguage.GTStochasticNamedElement
	 * @generated
	 */
	EClass getGTStochasticNamedElement();

	/**
	 * Returns the meta object for the attribute '{@link StochasticLanguage.GTStochasticNamedElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see StochasticLanguage.GTStochasticNamedElement#getName()
	 * @see #getGTStochasticNamedElement()
	 * @generated
	 */
	EAttribute getGTStochasticNamedElement_Name();

	/**
	 * Returns the meta object for class '{@link StochasticLanguage.GTStochasticNode <em>GT Stochastic Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>GT Stochastic Node</em>'.
	 * @see StochasticLanguage.GTStochasticNode
	 * @generated
	 */
	EClass getGTStochasticNode();

	/**
	 * Returns the meta object for the reference '{@link StochasticLanguage.GTStochasticNode#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see StochasticLanguage.GTStochasticNode#getType()
	 * @see #getGTStochasticNode()
	 * @generated
	 */
	EReference getGTStochasticNode_Type();

	/**
	 * Returns the meta object for class '{@link StochasticLanguage.GTAttribute <em>GT Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>GT Attribute</em>'.
	 * @see StochasticLanguage.GTAttribute
	 * @generated
	 */
	EClass getGTAttribute();

	/**
	 * Returns the meta object for the reference '{@link StochasticLanguage.GTAttribute#getAttribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Attribute</em>'.
	 * @see StochasticLanguage.GTAttribute#getAttribute()
	 * @see #getGTAttribute()
	 * @generated
	 */
	EReference getGTAttribute_Attribute();

	/**
	 * Returns the meta object for the attribute '{@link StochasticLanguage.GTAttribute#isNegative <em>Negative</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Negative</em>'.
	 * @see StochasticLanguage.GTAttribute#isNegative()
	 * @see #getGTAttribute()
	 * @generated
	 */
	EAttribute getGTAttribute_Negative();

	/**
	 * Returns the meta object for class '{@link StochasticLanguage.GTStochasticFunction <em>GT Stochastic Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>GT Stochastic Function</em>'.
	 * @see StochasticLanguage.GTStochasticFunction
	 * @generated
	 */
	EClass getGTStochasticFunction();

	/**
	 * Returns the meta object for the containment reference '{@link StochasticLanguage.GTStochasticFunction#getMean <em>Mean</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Mean</em>'.
	 * @see StochasticLanguage.GTStochasticFunction#getMean()
	 * @see #getGTStochasticFunction()
	 * @generated
	 */
	EReference getGTStochasticFunction_Mean();

	/**
	 * Returns the meta object for the containment reference '{@link StochasticLanguage.GTStochasticFunction#getSd <em>Sd</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Sd</em>'.
	 * @see StochasticLanguage.GTStochasticFunction#getSd()
	 * @see #getGTStochasticFunction()
	 * @generated
	 */
	EReference getGTStochasticFunction_Sd();

	/**
	 * Returns the meta object for the attribute '{@link StochasticLanguage.GTStochasticFunction#getDistribution <em>Distribution</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Distribution</em>'.
	 * @see StochasticLanguage.GTStochasticFunction#getDistribution()
	 * @see #getGTStochasticFunction()
	 * @generated
	 */
	EAttribute getGTStochasticFunction_Distribution();

	/**
	 * Returns the meta object for class '{@link StochasticLanguage.GTNumber <em>GT Number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>GT Number</em>'.
	 * @see StochasticLanguage.GTNumber
	 * @generated
	 */
	EClass getGTNumber();

	/**
	 * Returns the meta object for the attribute '{@link StochasticLanguage.GTNumber#getNumber <em>Number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Number</em>'.
	 * @see StochasticLanguage.GTNumber#getNumber()
	 * @see #getGTNumber()
	 * @generated
	 */
	EAttribute getGTNumber_Number();

	/**
	 * Returns the meta object for class '{@link StochasticLanguage.GTArithmetics <em>GT Arithmetics</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>GT Arithmetics</em>'.
	 * @see StochasticLanguage.GTArithmetics
	 * @generated
	 */
	EClass getGTArithmetics();

	/**
	 * Returns the meta object for class '{@link StochasticLanguage.GTTwoParameterCalculation <em>GT Two Parameter Calculation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>GT Two Parameter Calculation</em>'.
	 * @see StochasticLanguage.GTTwoParameterCalculation
	 * @generated
	 */
	EClass getGTTwoParameterCalculation();

	/**
	 * Returns the meta object for the containment reference '{@link StochasticLanguage.GTTwoParameterCalculation#getLeft <em>Left</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Left</em>'.
	 * @see StochasticLanguage.GTTwoParameterCalculation#getLeft()
	 * @see #getGTTwoParameterCalculation()
	 * @generated
	 */
	EReference getGTTwoParameterCalculation_Left();

	/**
	 * Returns the meta object for the containment reference '{@link StochasticLanguage.GTTwoParameterCalculation#getRight <em>Right</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Right</em>'.
	 * @see StochasticLanguage.GTTwoParameterCalculation#getRight()
	 * @see #getGTTwoParameterCalculation()
	 * @generated
	 */
	EReference getGTTwoParameterCalculation_Right();

	/**
	 * Returns the meta object for the attribute '{@link StochasticLanguage.GTTwoParameterCalculation#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see StochasticLanguage.GTTwoParameterCalculation#getOperator()
	 * @see #getGTTwoParameterCalculation()
	 * @generated
	 */
	EAttribute getGTTwoParameterCalculation_Operator();

	/**
	 * Returns the meta object for class '{@link StochasticLanguage.GTOneParameterCalculation <em>GT One Parameter Calculation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>GT One Parameter Calculation</em>'.
	 * @see StochasticLanguage.GTOneParameterCalculation
	 * @generated
	 */
	EClass getGTOneParameterCalculation();

	/**
	 * Returns the meta object for the containment reference '{@link StochasticLanguage.GTOneParameterCalculation#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see StochasticLanguage.GTOneParameterCalculation#getValue()
	 * @see #getGTOneParameterCalculation()
	 * @generated
	 */
	EReference getGTOneParameterCalculation_Value();

	/**
	 * Returns the meta object for the attribute '{@link StochasticLanguage.GTOneParameterCalculation#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see StochasticLanguage.GTOneParameterCalculation#getOperator()
	 * @see #getGTOneParameterCalculation()
	 * @generated
	 */
	EAttribute getGTOneParameterCalculation_Operator();

	/**
	 * Returns the meta object for the attribute '{@link StochasticLanguage.GTOneParameterCalculation#isNegative <em>Negative</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Negative</em>'.
	 * @see StochasticLanguage.GTOneParameterCalculation#isNegative()
	 * @see #getGTOneParameterCalculation()
	 * @generated
	 */
	EAttribute getGTOneParameterCalculation_Negative();

	/**
	 * Returns the meta object for enum '{@link StochasticLanguage.GTStochasticDistribution <em>GT Stochastic Distribution</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>GT Stochastic Distribution</em>'.
	 * @see StochasticLanguage.GTStochasticDistribution
	 * @generated
	 */
	EEnum getGTStochasticDistribution();

	/**
	 * Returns the meta object for enum '{@link StochasticLanguage.GTStochasticRange <em>GT Stochastic Range</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>GT Stochastic Range</em>'.
	 * @see StochasticLanguage.GTStochasticRange
	 * @generated
	 */
	EEnum getGTStochasticRange();

	/**
	 * Returns the meta object for enum '{@link StochasticLanguage.TwoParameterOperator <em>Two Parameter Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Two Parameter Operator</em>'.
	 * @see StochasticLanguage.TwoParameterOperator
	 * @generated
	 */
	EEnum getTwoParameterOperator();

	/**
	 * Returns the meta object for enum '{@link StochasticLanguage.OneParameterOperator <em>One Parameter Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>One Parameter Operator</em>'.
	 * @see StochasticLanguage.OneParameterOperator
	 * @generated
	 */
	EEnum getOneParameterOperator();

	/**
	 * Returns the meta object for enum '{@link StochasticLanguage.GTRelation <em>GT Relation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>GT Relation</em>'.
	 * @see StochasticLanguage.GTRelation
	 * @generated
	 */
	EEnum getGTRelation();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	StochasticLanguageFactory getStochasticLanguageFactory();

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
		 * The meta object literal for the '{@link StochasticLanguage.impl.GTStochasticNamedElementImpl <em>GT Stochastic Named Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see StochasticLanguage.impl.GTStochasticNamedElementImpl
		 * @see StochasticLanguage.impl.StochasticLanguagePackageImpl#getGTStochasticNamedElement()
		 * @generated
		 */
		EClass GT_STOCHASTIC_NAMED_ELEMENT = eINSTANCE.getGTStochasticNamedElement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GT_STOCHASTIC_NAMED_ELEMENT__NAME = eINSTANCE.getGTStochasticNamedElement_Name();

		/**
		 * The meta object literal for the '{@link StochasticLanguage.impl.GTStochasticNodeImpl <em>GT Stochastic Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see StochasticLanguage.impl.GTStochasticNodeImpl
		 * @see StochasticLanguage.impl.StochasticLanguagePackageImpl#getGTStochasticNode()
		 * @generated
		 */
		EClass GT_STOCHASTIC_NODE = eINSTANCE.getGTStochasticNode();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GT_STOCHASTIC_NODE__TYPE = eINSTANCE.getGTStochasticNode_Type();

		/**
		 * The meta object literal for the '{@link StochasticLanguage.impl.GTAttributeImpl <em>GT Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see StochasticLanguage.impl.GTAttributeImpl
		 * @see StochasticLanguage.impl.StochasticLanguagePackageImpl#getGTAttribute()
		 * @generated
		 */
		EClass GT_ATTRIBUTE = eINSTANCE.getGTAttribute();

		/**
		 * The meta object literal for the '<em><b>Attribute</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GT_ATTRIBUTE__ATTRIBUTE = eINSTANCE.getGTAttribute_Attribute();

		/**
		 * The meta object literal for the '<em><b>Negative</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GT_ATTRIBUTE__NEGATIVE = eINSTANCE.getGTAttribute_Negative();

		/**
		 * The meta object literal for the '{@link StochasticLanguage.impl.GTStochasticFunctionImpl <em>GT Stochastic Function</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see StochasticLanguage.impl.GTStochasticFunctionImpl
		 * @see StochasticLanguage.impl.StochasticLanguagePackageImpl#getGTStochasticFunction()
		 * @generated
		 */
		EClass GT_STOCHASTIC_FUNCTION = eINSTANCE.getGTStochasticFunction();

		/**
		 * The meta object literal for the '<em><b>Mean</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GT_STOCHASTIC_FUNCTION__MEAN = eINSTANCE.getGTStochasticFunction_Mean();

		/**
		 * The meta object literal for the '<em><b>Sd</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GT_STOCHASTIC_FUNCTION__SD = eINSTANCE.getGTStochasticFunction_Sd();

		/**
		 * The meta object literal for the '<em><b>Distribution</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GT_STOCHASTIC_FUNCTION__DISTRIBUTION = eINSTANCE.getGTStochasticFunction_Distribution();

		/**
		 * The meta object literal for the '{@link StochasticLanguage.impl.GTNumberImpl <em>GT Number</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see StochasticLanguage.impl.GTNumberImpl
		 * @see StochasticLanguage.impl.StochasticLanguagePackageImpl#getGTNumber()
		 * @generated
		 */
		EClass GT_NUMBER = eINSTANCE.getGTNumber();

		/**
		 * The meta object literal for the '<em><b>Number</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GT_NUMBER__NUMBER = eINSTANCE.getGTNumber_Number();

		/**
		 * The meta object literal for the '{@link StochasticLanguage.GTArithmetics <em>GT Arithmetics</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see StochasticLanguage.GTArithmetics
		 * @see StochasticLanguage.impl.StochasticLanguagePackageImpl#getGTArithmetics()
		 * @generated
		 */
		EClass GT_ARITHMETICS = eINSTANCE.getGTArithmetics();

		/**
		 * The meta object literal for the '{@link StochasticLanguage.impl.GTTwoParameterCalculationImpl <em>GT Two Parameter Calculation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see StochasticLanguage.impl.GTTwoParameterCalculationImpl
		 * @see StochasticLanguage.impl.StochasticLanguagePackageImpl#getGTTwoParameterCalculation()
		 * @generated
		 */
		EClass GT_TWO_PARAMETER_CALCULATION = eINSTANCE.getGTTwoParameterCalculation();

		/**
		 * The meta object literal for the '<em><b>Left</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GT_TWO_PARAMETER_CALCULATION__LEFT = eINSTANCE.getGTTwoParameterCalculation_Left();

		/**
		 * The meta object literal for the '<em><b>Right</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GT_TWO_PARAMETER_CALCULATION__RIGHT = eINSTANCE.getGTTwoParameterCalculation_Right();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GT_TWO_PARAMETER_CALCULATION__OPERATOR = eINSTANCE.getGTTwoParameterCalculation_Operator();

		/**
		 * The meta object literal for the '{@link StochasticLanguage.impl.GTOneParameterCalculationImpl <em>GT One Parameter Calculation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see StochasticLanguage.impl.GTOneParameterCalculationImpl
		 * @see StochasticLanguage.impl.StochasticLanguagePackageImpl#getGTOneParameterCalculation()
		 * @generated
		 */
		EClass GT_ONE_PARAMETER_CALCULATION = eINSTANCE.getGTOneParameterCalculation();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GT_ONE_PARAMETER_CALCULATION__VALUE = eINSTANCE.getGTOneParameterCalculation_Value();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GT_ONE_PARAMETER_CALCULATION__OPERATOR = eINSTANCE.getGTOneParameterCalculation_Operator();

		/**
		 * The meta object literal for the '<em><b>Negative</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GT_ONE_PARAMETER_CALCULATION__NEGATIVE = eINSTANCE.getGTOneParameterCalculation_Negative();

		/**
		 * The meta object literal for the '{@link StochasticLanguage.GTStochasticDistribution <em>GT Stochastic Distribution</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see StochasticLanguage.GTStochasticDistribution
		 * @see StochasticLanguage.impl.StochasticLanguagePackageImpl#getGTStochasticDistribution()
		 * @generated
		 */
		EEnum GT_STOCHASTIC_DISTRIBUTION = eINSTANCE.getGTStochasticDistribution();

		/**
		 * The meta object literal for the '{@link StochasticLanguage.GTStochasticRange <em>GT Stochastic Range</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see StochasticLanguage.GTStochasticRange
		 * @see StochasticLanguage.impl.StochasticLanguagePackageImpl#getGTStochasticRange()
		 * @generated
		 */
		EEnum GT_STOCHASTIC_RANGE = eINSTANCE.getGTStochasticRange();

		/**
		 * The meta object literal for the '{@link StochasticLanguage.TwoParameterOperator <em>Two Parameter Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see StochasticLanguage.TwoParameterOperator
		 * @see StochasticLanguage.impl.StochasticLanguagePackageImpl#getTwoParameterOperator()
		 * @generated
		 */
		EEnum TWO_PARAMETER_OPERATOR = eINSTANCE.getTwoParameterOperator();

		/**
		 * The meta object literal for the '{@link StochasticLanguage.OneParameterOperator <em>One Parameter Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see StochasticLanguage.OneParameterOperator
		 * @see StochasticLanguage.impl.StochasticLanguagePackageImpl#getOneParameterOperator()
		 * @generated
		 */
		EEnum ONE_PARAMETER_OPERATOR = eINSTANCE.getOneParameterOperator();

		/**
		 * The meta object literal for the '{@link StochasticLanguage.GTRelation <em>GT Relation</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see StochasticLanguage.GTRelation
		 * @see StochasticLanguage.impl.StochasticLanguagePackageImpl#getGTRelation()
		 * @generated
		 */
		EEnum GT_RELATION = eINSTANCE.getGTRelation();

	}

} //StochasticLanguagePackage
