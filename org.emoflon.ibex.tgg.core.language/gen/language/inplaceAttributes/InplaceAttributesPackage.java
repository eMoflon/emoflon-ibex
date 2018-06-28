/**
 */
package language.inplaceAttributes;

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
 * @see language.inplaceAttributes.InplaceAttributesFactory
 * @model kind="package"
 * @generated
 */
public interface InplaceAttributesPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "inplaceAttributes";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "platform:/plugin/org.emoflon.ibex.tgg.core.language/model/Language.ecore#//inplaceAttributes";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "org.emoflon.ibex.tgg.core.language.inplaceAttributes";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	InplaceAttributesPackage eINSTANCE = language.inplaceAttributes.impl.InplaceAttributesPackageImpl.init();

	/**
	 * The meta object id for the '{@link language.inplaceAttributes.impl.TGGInplaceAttributeExpressionImpl <em>TGG Inplace Attribute Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see language.inplaceAttributes.impl.TGGInplaceAttributeExpressionImpl
	 * @see language.inplaceAttributes.impl.InplaceAttributesPackageImpl#getTGGInplaceAttributeExpression()
	 * @generated
	 */
	int TGG_INPLACE_ATTRIBUTE_EXPRESSION = 0;

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
	 * The meta object id for the '{@link language.inplaceAttributes.TGGAttributeConstraintOperators <em>TGG Attribute Constraint Operators</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see language.inplaceAttributes.TGGAttributeConstraintOperators
	 * @see language.inplaceAttributes.impl.InplaceAttributesPackageImpl#getTGGAttributeConstraintOperators()
	 * @generated
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_OPERATORS = 1;

	/**
	 * Returns the meta object for class '{@link language.inplaceAttributes.TGGInplaceAttributeExpression <em>TGG Inplace Attribute Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Inplace Attribute Expression</em>'.
	 * @see language.inplaceAttributes.TGGInplaceAttributeExpression
	 * @generated
	 */
	EClass getTGGInplaceAttributeExpression();

	/**
	 * Returns the meta object for the reference '{@link language.inplaceAttributes.TGGInplaceAttributeExpression#getAttribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Attribute</em>'.
	 * @see language.inplaceAttributes.TGGInplaceAttributeExpression#getAttribute()
	 * @see #getTGGInplaceAttributeExpression()
	 * @generated
	 */
	EReference getTGGInplaceAttributeExpression_Attribute();

	/**
	 * Returns the meta object for the containment reference '{@link language.inplaceAttributes.TGGInplaceAttributeExpression#getValueExpr <em>Value Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value Expr</em>'.
	 * @see language.inplaceAttributes.TGGInplaceAttributeExpression#getValueExpr()
	 * @see #getTGGInplaceAttributeExpression()
	 * @generated
	 */
	EReference getTGGInplaceAttributeExpression_ValueExpr();

	/**
	 * Returns the meta object for the attribute '{@link language.inplaceAttributes.TGGInplaceAttributeExpression#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see language.inplaceAttributes.TGGInplaceAttributeExpression#getOperator()
	 * @see #getTGGInplaceAttributeExpression()
	 * @generated
	 */
	EAttribute getTGGInplaceAttributeExpression_Operator();

	/**
	 * Returns the meta object for enum '{@link language.inplaceAttributes.TGGAttributeConstraintOperators <em>TGG Attribute Constraint Operators</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>TGG Attribute Constraint Operators</em>'.
	 * @see language.inplaceAttributes.TGGAttributeConstraintOperators
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
	InplaceAttributesFactory getInplaceAttributesFactory();

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
		 * The meta object literal for the '{@link language.inplaceAttributes.impl.TGGInplaceAttributeExpressionImpl <em>TGG Inplace Attribute Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see language.inplaceAttributes.impl.TGGInplaceAttributeExpressionImpl
		 * @see language.inplaceAttributes.impl.InplaceAttributesPackageImpl#getTGGInplaceAttributeExpression()
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
		EReference TGG_INPLACE_ATTRIBUTE_EXPRESSION__VALUE_EXPR = eINSTANCE.getTGGInplaceAttributeExpression_ValueExpr();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TGG_INPLACE_ATTRIBUTE_EXPRESSION__OPERATOR = eINSTANCE.getTGGInplaceAttributeExpression_Operator();

		/**
		 * The meta object literal for the '{@link language.inplaceAttributes.TGGAttributeConstraintOperators <em>TGG Attribute Constraint Operators</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see language.inplaceAttributes.TGGAttributeConstraintOperators
		 * @see language.inplaceAttributes.impl.InplaceAttributesPackageImpl#getTGGAttributeConstraintOperators()
		 * @generated
		 */
		EEnum TGG_ATTRIBUTE_CONSTRAINT_OPERATORS = eINSTANCE.getTGGAttributeConstraintOperators();

	}

} //InplaceAttributesPackage
