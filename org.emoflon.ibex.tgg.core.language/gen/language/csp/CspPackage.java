/**
 */
package language.csp;

import language.basic.expressions.ExpressionsPackage;

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
 * @see language.csp.CspFactory
 * @model kind="package"
 * @generated
 */
public interface CspPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "csp";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "platform:/plugin/org.emoflon.ibex.tgg.core.language/model/Language.ecore#//csp";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "org.emoflon.ibex.tgg.core.language.csp";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CspPackage eINSTANCE = language.csp.impl.CspPackageImpl.init();

	/**
	 * The meta object id for the '{@link language.csp.impl.TGGAttributeConstraintLibraryImpl <em>TGG Attribute Constraint Library</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see language.csp.impl.TGGAttributeConstraintLibraryImpl
	 * @see language.csp.impl.CspPackageImpl#getTGGAttributeConstraintLibrary()
	 * @generated
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_LIBRARY = 0;

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
	 * The meta object id for the '{@link language.csp.impl.TGGAttributeConstraintImpl <em>TGG Attribute Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see language.csp.impl.TGGAttributeConstraintImpl
	 * @see language.csp.impl.CspPackageImpl#getTGGAttributeConstraint()
	 * @generated
	 */
	int TGG_ATTRIBUTE_CONSTRAINT = 1;

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
	 * The meta object id for the '{@link language.csp.impl.TGGAttributeVariableImpl <em>TGG Attribute Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see language.csp.impl.TGGAttributeVariableImpl
	 * @see language.csp.impl.CspPackageImpl#getTGGAttributeVariable()
	 * @generated
	 */
	int TGG_ATTRIBUTE_VARIABLE = 2;

	/**
	 * The feature id for the '<em><b>Parameter Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_VARIABLE__PARAMETER_DEFINITION = ExpressionsPackage.TGG_PARAM_VALUE__PARAMETER_DEFINITION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_VARIABLE__NAME = ExpressionsPackage.TGG_PARAM_VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>TGG Attribute Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_VARIABLE_FEATURE_COUNT = ExpressionsPackage.TGG_PARAM_VALUE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>TGG Attribute Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_VARIABLE_OPERATION_COUNT = ExpressionsPackage.TGG_PARAM_VALUE_OPERATION_COUNT + 0;

	/**
	 * Returns the meta object for class '{@link language.csp.TGGAttributeConstraintLibrary <em>TGG Attribute Constraint Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Attribute Constraint Library</em>'.
	 * @see language.csp.TGGAttributeConstraintLibrary
	 * @generated
	 */
	EClass getTGGAttributeConstraintLibrary();

	/**
	 * Returns the meta object for the containment reference list '{@link language.csp.TGGAttributeConstraintLibrary#getTggAttributeConstraints <em>Tgg Attribute Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Tgg Attribute Constraints</em>'.
	 * @see language.csp.TGGAttributeConstraintLibrary#getTggAttributeConstraints()
	 * @see #getTGGAttributeConstraintLibrary()
	 * @generated
	 */
	EReference getTGGAttributeConstraintLibrary_TggAttributeConstraints();

	/**
	 * Returns the meta object for the containment reference list '{@link language.csp.TGGAttributeConstraintLibrary#getParameterValues <em>Parameter Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameter Values</em>'.
	 * @see language.csp.TGGAttributeConstraintLibrary#getParameterValues()
	 * @see #getTGGAttributeConstraintLibrary()
	 * @generated
	 */
	EReference getTGGAttributeConstraintLibrary_ParameterValues();

	/**
	 * Returns the meta object for class '{@link language.csp.TGGAttributeConstraint <em>TGG Attribute Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Attribute Constraint</em>'.
	 * @see language.csp.TGGAttributeConstraint
	 * @generated
	 */
	EClass getTGGAttributeConstraint();

	/**
	 * Returns the meta object for the reference '{@link language.csp.TGGAttributeConstraint#getDefinition <em>Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Definition</em>'.
	 * @see language.csp.TGGAttributeConstraint#getDefinition()
	 * @see #getTGGAttributeConstraint()
	 * @generated
	 */
	EReference getTGGAttributeConstraint_Definition();

	/**
	 * Returns the meta object for the reference list '{@link language.csp.TGGAttributeConstraint#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Parameters</em>'.
	 * @see language.csp.TGGAttributeConstraint#getParameters()
	 * @see #getTGGAttributeConstraint()
	 * @generated
	 */
	EReference getTGGAttributeConstraint_Parameters();

	/**
	 * Returns the meta object for class '{@link language.csp.TGGAttributeVariable <em>TGG Attribute Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Attribute Variable</em>'.
	 * @see language.csp.TGGAttributeVariable
	 * @generated
	 */
	EClass getTGGAttributeVariable();

	/**
	 * Returns the meta object for the attribute '{@link language.csp.TGGAttributeVariable#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see language.csp.TGGAttributeVariable#getName()
	 * @see #getTGGAttributeVariable()
	 * @generated
	 */
	EAttribute getTGGAttributeVariable_Name();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	CspFactory getCspFactory();

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
		 * The meta object literal for the '{@link language.csp.impl.TGGAttributeConstraintLibraryImpl <em>TGG Attribute Constraint Library</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see language.csp.impl.TGGAttributeConstraintLibraryImpl
		 * @see language.csp.impl.CspPackageImpl#getTGGAttributeConstraintLibrary()
		 * @generated
		 */
		EClass TGG_ATTRIBUTE_CONSTRAINT_LIBRARY = eINSTANCE.getTGGAttributeConstraintLibrary();

		/**
		 * The meta object literal for the '<em><b>Tgg Attribute Constraints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__TGG_ATTRIBUTE_CONSTRAINTS = eINSTANCE.getTGGAttributeConstraintLibrary_TggAttributeConstraints();

		/**
		 * The meta object literal for the '<em><b>Parameter Values</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__PARAMETER_VALUES = eINSTANCE.getTGGAttributeConstraintLibrary_ParameterValues();

		/**
		 * The meta object literal for the '{@link language.csp.impl.TGGAttributeConstraintImpl <em>TGG Attribute Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see language.csp.impl.TGGAttributeConstraintImpl
		 * @see language.csp.impl.CspPackageImpl#getTGGAttributeConstraint()
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
		 * The meta object literal for the '{@link language.csp.impl.TGGAttributeVariableImpl <em>TGG Attribute Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see language.csp.impl.TGGAttributeVariableImpl
		 * @see language.csp.impl.CspPackageImpl#getTGGAttributeVariable()
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

	}

} //CspPackage
