/**
 */
package language.csp.definition;

import language.basic.BasicPackage;

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
 * @see language.csp.definition.DefinitionFactory
 * @model kind="package"
 * @generated
 */
public interface DefinitionPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "definition";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "platform:/plugin/org.emoflon.ibex.tgg.core.language/model/Language.ecore#//csp//definition";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "org.emoflon.ibex.tgg.core.language.csp.definition";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DefinitionPackage eINSTANCE = language.csp.definition.impl.DefinitionPackageImpl.init();

	/**
	 * The meta object id for the '{@link language.csp.definition.impl.TGGAttributeConstraintDefinitionLibraryImpl <em>TGG Attribute Constraint Definition Library</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see language.csp.definition.impl.TGGAttributeConstraintDefinitionLibraryImpl
	 * @see language.csp.definition.impl.DefinitionPackageImpl#getTGGAttributeConstraintDefinitionLibrary()
	 * @generated
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY = 0;

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
	 * The meta object id for the '{@link language.csp.definition.impl.TGGAttributeConstraintDefinitionImpl <em>TGG Attribute Constraint Definition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see language.csp.definition.impl.TGGAttributeConstraintDefinitionImpl
	 * @see language.csp.definition.impl.DefinitionPackageImpl#getTGGAttributeConstraintDefinition()
	 * @generated
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__NAME = BasicPackage.TGG_NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>User Defined</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__USER_DEFINED = BasicPackage.TGG_NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Parameter Definitions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__PARAMETER_DEFINITIONS = BasicPackage.TGG_NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Sync Adornments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__SYNC_ADORNMENTS = BasicPackage.TGG_NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Gen Adornments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__GEN_ADORNMENTS = BasicPackage.TGG_NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>TGG Attribute Constraint Definition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_FEATURE_COUNT = BasicPackage.TGG_NAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>TGG Attribute Constraint Definition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_OPERATION_COUNT = BasicPackage.TGG_NAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link language.csp.definition.impl.TGGAttributeConstraintParameterDefinitionImpl <em>TGG Attribute Constraint Parameter Definition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see language.csp.definition.impl.TGGAttributeConstraintParameterDefinitionImpl
	 * @see language.csp.definition.impl.DefinitionPackageImpl#getTGGAttributeConstraintParameterDefinition()
	 * @generated
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_DEFINITION = 2;

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
	 * The meta object id for the '{@link language.csp.definition.impl.TGGAttributeConstraintAdornmentImpl <em>TGG Attribute Constraint Adornment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see language.csp.definition.impl.TGGAttributeConstraintAdornmentImpl
	 * @see language.csp.definition.impl.DefinitionPackageImpl#getTGGAttributeConstraintAdornment()
	 * @generated
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_ADORNMENT = 3;

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
	 * Returns the meta object for class '{@link language.csp.definition.TGGAttributeConstraintDefinitionLibrary <em>TGG Attribute Constraint Definition Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Attribute Constraint Definition Library</em>'.
	 * @see language.csp.definition.TGGAttributeConstraintDefinitionLibrary
	 * @generated
	 */
	EClass getTGGAttributeConstraintDefinitionLibrary();

	/**
	 * Returns the meta object for the containment reference list '{@link language.csp.definition.TGGAttributeConstraintDefinitionLibrary#getTggAttributeConstraintDefinitions <em>Tgg Attribute Constraint Definitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Tgg Attribute Constraint Definitions</em>'.
	 * @see language.csp.definition.TGGAttributeConstraintDefinitionLibrary#getTggAttributeConstraintDefinitions()
	 * @see #getTGGAttributeConstraintDefinitionLibrary()
	 * @generated
	 */
	EReference getTGGAttributeConstraintDefinitionLibrary_TggAttributeConstraintDefinitions();

	/**
	 * Returns the meta object for class '{@link language.csp.definition.TGGAttributeConstraintDefinition <em>TGG Attribute Constraint Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Attribute Constraint Definition</em>'.
	 * @see language.csp.definition.TGGAttributeConstraintDefinition
	 * @generated
	 */
	EClass getTGGAttributeConstraintDefinition();

	/**
	 * Returns the meta object for the attribute '{@link language.csp.definition.TGGAttributeConstraintDefinition#isUserDefined <em>User Defined</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>User Defined</em>'.
	 * @see language.csp.definition.TGGAttributeConstraintDefinition#isUserDefined()
	 * @see #getTGGAttributeConstraintDefinition()
	 * @generated
	 */
	EAttribute getTGGAttributeConstraintDefinition_UserDefined();

	/**
	 * Returns the meta object for the containment reference list '{@link language.csp.definition.TGGAttributeConstraintDefinition#getParameterDefinitions <em>Parameter Definitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameter Definitions</em>'.
	 * @see language.csp.definition.TGGAttributeConstraintDefinition#getParameterDefinitions()
	 * @see #getTGGAttributeConstraintDefinition()
	 * @generated
	 */
	EReference getTGGAttributeConstraintDefinition_ParameterDefinitions();

	/**
	 * Returns the meta object for the containment reference list '{@link language.csp.definition.TGGAttributeConstraintDefinition#getSyncAdornments <em>Sync Adornments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sync Adornments</em>'.
	 * @see language.csp.definition.TGGAttributeConstraintDefinition#getSyncAdornments()
	 * @see #getTGGAttributeConstraintDefinition()
	 * @generated
	 */
	EReference getTGGAttributeConstraintDefinition_SyncAdornments();

	/**
	 * Returns the meta object for the containment reference list '{@link language.csp.definition.TGGAttributeConstraintDefinition#getGenAdornments <em>Gen Adornments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Gen Adornments</em>'.
	 * @see language.csp.definition.TGGAttributeConstraintDefinition#getGenAdornments()
	 * @see #getTGGAttributeConstraintDefinition()
	 * @generated
	 */
	EReference getTGGAttributeConstraintDefinition_GenAdornments();

	/**
	 * Returns the meta object for class '{@link language.csp.definition.TGGAttributeConstraintParameterDefinition <em>TGG Attribute Constraint Parameter Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Attribute Constraint Parameter Definition</em>'.
	 * @see language.csp.definition.TGGAttributeConstraintParameterDefinition
	 * @generated
	 */
	EClass getTGGAttributeConstraintParameterDefinition();

	/**
	 * Returns the meta object for the reference '{@link language.csp.definition.TGGAttributeConstraintParameterDefinition#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see language.csp.definition.TGGAttributeConstraintParameterDefinition#getType()
	 * @see #getTGGAttributeConstraintParameterDefinition()
	 * @generated
	 */
	EReference getTGGAttributeConstraintParameterDefinition_Type();

	/**
	 * Returns the meta object for the attribute '{@link language.csp.definition.TGGAttributeConstraintParameterDefinition#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see language.csp.definition.TGGAttributeConstraintParameterDefinition#getName()
	 * @see #getTGGAttributeConstraintParameterDefinition()
	 * @generated
	 */
	EAttribute getTGGAttributeConstraintParameterDefinition_Name();

	/**
	 * Returns the meta object for class '{@link language.csp.definition.TGGAttributeConstraintAdornment <em>TGG Attribute Constraint Adornment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Attribute Constraint Adornment</em>'.
	 * @see language.csp.definition.TGGAttributeConstraintAdornment
	 * @generated
	 */
	EClass getTGGAttributeConstraintAdornment();

	/**
	 * Returns the meta object for the attribute list '{@link language.csp.definition.TGGAttributeConstraintAdornment#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Value</em>'.
	 * @see language.csp.definition.TGGAttributeConstraintAdornment#getValue()
	 * @see #getTGGAttributeConstraintAdornment()
	 * @generated
	 */
	EAttribute getTGGAttributeConstraintAdornment_Value();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	DefinitionFactory getDefinitionFactory();

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
		 * The meta object literal for the '{@link language.csp.definition.impl.TGGAttributeConstraintDefinitionLibraryImpl <em>TGG Attribute Constraint Definition Library</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see language.csp.definition.impl.TGGAttributeConstraintDefinitionLibraryImpl
		 * @see language.csp.definition.impl.DefinitionPackageImpl#getTGGAttributeConstraintDefinitionLibrary()
		 * @generated
		 */
		EClass TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY = eINSTANCE.getTGGAttributeConstraintDefinitionLibrary();

		/**
		 * The meta object literal for the '<em><b>Tgg Attribute Constraint Definitions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY__TGG_ATTRIBUTE_CONSTRAINT_DEFINITIONS = eINSTANCE.getTGGAttributeConstraintDefinitionLibrary_TggAttributeConstraintDefinitions();

		/**
		 * The meta object literal for the '{@link language.csp.definition.impl.TGGAttributeConstraintDefinitionImpl <em>TGG Attribute Constraint Definition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see language.csp.definition.impl.TGGAttributeConstraintDefinitionImpl
		 * @see language.csp.definition.impl.DefinitionPackageImpl#getTGGAttributeConstraintDefinition()
		 * @generated
		 */
		EClass TGG_ATTRIBUTE_CONSTRAINT_DEFINITION = eINSTANCE.getTGGAttributeConstraintDefinition();

		/**
		 * The meta object literal for the '<em><b>User Defined</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__USER_DEFINED = eINSTANCE.getTGGAttributeConstraintDefinition_UserDefined();

		/**
		 * The meta object literal for the '<em><b>Parameter Definitions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__PARAMETER_DEFINITIONS = eINSTANCE.getTGGAttributeConstraintDefinition_ParameterDefinitions();

		/**
		 * The meta object literal for the '<em><b>Sync Adornments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__SYNC_ADORNMENTS = eINSTANCE.getTGGAttributeConstraintDefinition_SyncAdornments();

		/**
		 * The meta object literal for the '<em><b>Gen Adornments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__GEN_ADORNMENTS = eINSTANCE.getTGGAttributeConstraintDefinition_GenAdornments();

		/**
		 * The meta object literal for the '{@link language.csp.definition.impl.TGGAttributeConstraintParameterDefinitionImpl <em>TGG Attribute Constraint Parameter Definition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see language.csp.definition.impl.TGGAttributeConstraintParameterDefinitionImpl
		 * @see language.csp.definition.impl.DefinitionPackageImpl#getTGGAttributeConstraintParameterDefinition()
		 * @generated
		 */
		EClass TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_DEFINITION = eINSTANCE.getTGGAttributeConstraintParameterDefinition();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_DEFINITION__TYPE = eINSTANCE.getTGGAttributeConstraintParameterDefinition_Type();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_DEFINITION__NAME = eINSTANCE.getTGGAttributeConstraintParameterDefinition_Name();

		/**
		 * The meta object literal for the '{@link language.csp.definition.impl.TGGAttributeConstraintAdornmentImpl <em>TGG Attribute Constraint Adornment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see language.csp.definition.impl.TGGAttributeConstraintAdornmentImpl
		 * @see language.csp.definition.impl.DefinitionPackageImpl#getTGGAttributeConstraintAdornment()
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

	}

} //DefinitionPackage
