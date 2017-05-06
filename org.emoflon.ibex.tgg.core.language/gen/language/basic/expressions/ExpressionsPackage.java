/**
 */
package language.basic.expressions;

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
 * @see language.basic.expressions.ExpressionsFactory
 * @model kind="package"
 * @generated
 */
public interface ExpressionsPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "expressions";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "platform:/plugin/org.emoflon.ibex.tgg.core.language/model/Language.ecore#/misc/expressions";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "org.emoflon.ibex.tgg.core.language.misc.expressions";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ExpressionsPackage eINSTANCE = language.basic.expressions.impl.ExpressionsPackageImpl.init();

	/**
	 * The meta object id for the '{@link language.basic.expressions.impl.TGGParamValueImpl <em>TGG Param Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see language.basic.expressions.impl.TGGParamValueImpl
	 * @see language.basic.expressions.impl.ExpressionsPackageImpl#getTGGParamValue()
	 * @generated
	 */
	int TGG_PARAM_VALUE = 0;

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
	 * The meta object id for the '{@link language.basic.expressions.impl.TGGExpressionImpl <em>TGG Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see language.basic.expressions.impl.TGGExpressionImpl
	 * @see language.basic.expressions.impl.ExpressionsPackageImpl#getTGGExpression()
	 * @generated
	 */
	int TGG_EXPRESSION = 1;

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
	 * The meta object id for the '{@link language.basic.expressions.impl.TGGLiteralExpressionImpl <em>TGG Literal Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see language.basic.expressions.impl.TGGLiteralExpressionImpl
	 * @see language.basic.expressions.impl.ExpressionsPackageImpl#getTGGLiteralExpression()
	 * @generated
	 */
	int TGG_LITERAL_EXPRESSION = 2;

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
	 * The meta object id for the '{@link language.basic.expressions.impl.TGGAttributeExpressionImpl <em>TGG Attribute Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see language.basic.expressions.impl.TGGAttributeExpressionImpl
	 * @see language.basic.expressions.impl.ExpressionsPackageImpl#getTGGAttributeExpression()
	 * @generated
	 */
	int TGG_ATTRIBUTE_EXPRESSION = 3;

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
	 * The meta object id for the '{@link language.basic.expressions.impl.TGGEnumExpressionImpl <em>TGG Enum Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see language.basic.expressions.impl.TGGEnumExpressionImpl
	 * @see language.basic.expressions.impl.ExpressionsPackageImpl#getTGGEnumExpression()
	 * @generated
	 */
	int TGG_ENUM_EXPRESSION = 4;

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
	 * Returns the meta object for class '{@link language.basic.expressions.TGGParamValue <em>TGG Param Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Param Value</em>'.
	 * @see language.basic.expressions.TGGParamValue
	 * @generated
	 */
	EClass getTGGParamValue();

	/**
	 * Returns the meta object for the reference '{@link language.basic.expressions.TGGParamValue#getParameterDefinition <em>Parameter Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parameter Definition</em>'.
	 * @see language.basic.expressions.TGGParamValue#getParameterDefinition()
	 * @see #getTGGParamValue()
	 * @generated
	 */
	EReference getTGGParamValue_ParameterDefinition();

	/**
	 * Returns the meta object for class '{@link language.basic.expressions.TGGExpression <em>TGG Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Expression</em>'.
	 * @see language.basic.expressions.TGGExpression
	 * @generated
	 */
	EClass getTGGExpression();

	/**
	 * Returns the meta object for class '{@link language.basic.expressions.TGGLiteralExpression <em>TGG Literal Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Literal Expression</em>'.
	 * @see language.basic.expressions.TGGLiteralExpression
	 * @generated
	 */
	EClass getTGGLiteralExpression();

	/**
	 * Returns the meta object for the attribute '{@link language.basic.expressions.TGGLiteralExpression#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see language.basic.expressions.TGGLiteralExpression#getValue()
	 * @see #getTGGLiteralExpression()
	 * @generated
	 */
	EAttribute getTGGLiteralExpression_Value();

	/**
	 * Returns the meta object for class '{@link language.basic.expressions.TGGAttributeExpression <em>TGG Attribute Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Attribute Expression</em>'.
	 * @see language.basic.expressions.TGGAttributeExpression
	 * @generated
	 */
	EClass getTGGAttributeExpression();

	/**
	 * Returns the meta object for the reference '{@link language.basic.expressions.TGGAttributeExpression#getObjectVar <em>Object Var</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Object Var</em>'.
	 * @see language.basic.expressions.TGGAttributeExpression#getObjectVar()
	 * @see #getTGGAttributeExpression()
	 * @generated
	 */
	EReference getTGGAttributeExpression_ObjectVar();

	/**
	 * Returns the meta object for the reference '{@link language.basic.expressions.TGGAttributeExpression#getAttribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Attribute</em>'.
	 * @see language.basic.expressions.TGGAttributeExpression#getAttribute()
	 * @see #getTGGAttributeExpression()
	 * @generated
	 */
	EReference getTGGAttributeExpression_Attribute();

	/**
	 * Returns the meta object for class '{@link language.basic.expressions.TGGEnumExpression <em>TGG Enum Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Enum Expression</em>'.
	 * @see language.basic.expressions.TGGEnumExpression
	 * @generated
	 */
	EClass getTGGEnumExpression();

	/**
	 * Returns the meta object for the reference '{@link language.basic.expressions.TGGEnumExpression#getEenum <em>Eenum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Eenum</em>'.
	 * @see language.basic.expressions.TGGEnumExpression#getEenum()
	 * @see #getTGGEnumExpression()
	 * @generated
	 */
	EReference getTGGEnumExpression_Eenum();

	/**
	 * Returns the meta object for the reference '{@link language.basic.expressions.TGGEnumExpression#getLiteral <em>Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Literal</em>'.
	 * @see language.basic.expressions.TGGEnumExpression#getLiteral()
	 * @see #getTGGEnumExpression()
	 * @generated
	 */
	EReference getTGGEnumExpression_Literal();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ExpressionsFactory getExpressionsFactory();

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
		 * The meta object literal for the '{@link language.basic.expressions.impl.TGGParamValueImpl <em>TGG Param Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see language.basic.expressions.impl.TGGParamValueImpl
		 * @see language.basic.expressions.impl.ExpressionsPackageImpl#getTGGParamValue()
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
		 * The meta object literal for the '{@link language.basic.expressions.impl.TGGExpressionImpl <em>TGG Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see language.basic.expressions.impl.TGGExpressionImpl
		 * @see language.basic.expressions.impl.ExpressionsPackageImpl#getTGGExpression()
		 * @generated
		 */
		EClass TGG_EXPRESSION = eINSTANCE.getTGGExpression();

		/**
		 * The meta object literal for the '{@link language.basic.expressions.impl.TGGLiteralExpressionImpl <em>TGG Literal Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see language.basic.expressions.impl.TGGLiteralExpressionImpl
		 * @see language.basic.expressions.impl.ExpressionsPackageImpl#getTGGLiteralExpression()
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
		 * The meta object literal for the '{@link language.basic.expressions.impl.TGGAttributeExpressionImpl <em>TGG Attribute Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see language.basic.expressions.impl.TGGAttributeExpressionImpl
		 * @see language.basic.expressions.impl.ExpressionsPackageImpl#getTGGAttributeExpression()
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
		 * The meta object literal for the '{@link language.basic.expressions.impl.TGGEnumExpressionImpl <em>TGG Enum Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see language.basic.expressions.impl.TGGEnumExpressionImpl
		 * @see language.basic.expressions.impl.ExpressionsPackageImpl#getTGGEnumExpression()
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

	}

} //ExpressionsPackage
