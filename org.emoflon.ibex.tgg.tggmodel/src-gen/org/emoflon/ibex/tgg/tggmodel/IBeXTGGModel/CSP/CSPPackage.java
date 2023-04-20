/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP;

import org.eclipse.emf.ecore.EAttribute;
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
 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.CSPFactory
 * @model kind="package"
 * @generated
 */
public interface CSPPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "CSP";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "platform:/resource/org.emoflon.ibex.tgg.tggmodel/model/IBeXTGGModel.ecore/CSP";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "CSP";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CSPPackage eINSTANCE = org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.CSPPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGAttributeConstraintDefinitionImpl <em>TGG Attribute Constraint Definition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGAttributeConstraintDefinitionImpl
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.CSPPackageImpl#getTGGAttributeConstraintDefinition()
	 * @generated
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__NAME = IBeXCoreModelPackage.IBE_XNAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Parameter Definitions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__PARAMETER_DEFINITIONS = IBeXCoreModelPackage.IBE_XNAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Sync Bindings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__SYNC_BINDINGS = IBeXCoreModelPackage.IBE_XNAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Gen Bindings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__GEN_BINDINGS = IBeXCoreModelPackage.IBE_XNAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Library</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__LIBRARY = IBeXCoreModelPackage.IBE_XNAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>TGG Attribute Constraint Definition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_FEATURE_COUNT = IBeXCoreModelPackage.IBE_XNAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>TGG Attribute Constraint Definition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_OPERATION_COUNT = IBeXCoreModelPackage.IBE_XNAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGAttributeConstraintDefinitionLibraryImpl <em>TGG Attribute Constraint Definition Library</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGAttributeConstraintDefinitionLibraryImpl
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.CSPPackageImpl#getTGGAttributeConstraintDefinitionLibrary()
	 * @generated
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY__NAME = IBeXCoreModelPackage.IBE_XNAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Package Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY__PACKAGE_NAME = IBeXCoreModelPackage.IBE_XNAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Tgg Attribute Constraint Definitions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY__TGG_ATTRIBUTE_CONSTRAINT_DEFINITIONS = IBeXCoreModelPackage.IBE_XNAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>TGG Attribute Constraint Definition Library</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY_FEATURE_COUNT = IBeXCoreModelPackage.IBE_XNAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>TGG Attribute Constraint Definition Library</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY_OPERATION_COUNT = IBeXCoreModelPackage.IBE_XNAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGAttributeConstraintParameterDefinitionImpl <em>TGG Attribute Constraint Parameter Definition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGAttributeConstraintParameterDefinitionImpl
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.CSPPackageImpl#getTGGAttributeConstraintParameterDefinition()
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
	 * The meta object id for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGAttributeConstraintBindingImpl <em>TGG Attribute Constraint Binding</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGAttributeConstraintBindingImpl
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.CSPPackageImpl#getTGGAttributeConstraintBinding()
	 * @generated
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_BINDING = 3;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_BINDING__VALUE = 0;

	/**
	 * The number of structural features of the '<em>TGG Attribute Constraint Binding</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_BINDING_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>TGG Attribute Constraint Binding</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_BINDING_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGAttributeConstraintSetImpl <em>TGG Attribute Constraint Set</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGAttributeConstraintSetImpl
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.CSPPackageImpl#getTGGAttributeConstraintSet()
	 * @generated
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_SET = 4;

	/**
	 * The feature id for the '<em><b>Tgg Attribute Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_SET__TGG_ATTRIBUTE_CONSTRAINTS = 0;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_SET__PARAMETERS = 1;

	/**
	 * The number of structural features of the '<em>TGG Attribute Constraint Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_SET_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>TGG Attribute Constraint Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_SET_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGAttributeConstraintImpl <em>TGG Attribute Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGAttributeConstraintImpl
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.CSPPackageImpl#getTGGAttributeConstraint()
	 * @generated
	 */
	int TGG_ATTRIBUTE_CONSTRAINT = 5;

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
	 * The meta object id for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGAttributeConstraintVariableImpl <em>TGG Attribute Constraint Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGAttributeConstraintVariableImpl
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.CSPPackageImpl#getTGGAttributeConstraintVariable()
	 * @generated
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_VARIABLE = 6;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_VARIABLE__TYPE = IBeXCoreArithmeticPackage.VALUE_EXPRESSION__TYPE;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_VARIABLE__NAME = IBeXCoreArithmeticPackage.VALUE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>TGG Attribute Constraint Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_VARIABLE_FEATURE_COUNT = IBeXCoreArithmeticPackage.VALUE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>TGG Attribute Constraint Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_VARIABLE_OPERATION_COUNT = IBeXCoreArithmeticPackage.VALUE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGAttributeConstraintParameterValueImpl <em>TGG Attribute Constraint Parameter Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGAttributeConstraintParameterValueImpl
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.CSPPackageImpl#getTGGAttributeConstraintParameterValue()
	 * @generated
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_VALUE = 7;

	/**
	 * The feature id for the '<em><b>Parameter Definition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_VALUE__PARAMETER_DEFINITION = 0;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_VALUE__EXPRESSION = 1;

	/**
	 * The feature id for the '<em><b>Derived</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_VALUE__DERIVED = 2;

	/**
	 * The number of structural features of the '<em>TGG Attribute Constraint Parameter Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_VALUE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>TGG Attribute Constraint Parameter Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_VALUE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGCSPImpl <em>TGGCSP</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGCSPImpl
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.CSPPackageImpl#getTGGCSP()
	 * @generated
	 */
	int TGGCSP = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGGCSP__NAME = IBeXCoreModelPackage.IBE_XNAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Package</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGGCSP__PACKAGE = IBeXCoreModelPackage.IBE_XNAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGGCSP__VALUES = IBeXCoreModelPackage.IBE_XNAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>TGGCSP</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGGCSP_FEATURE_COUNT = IBeXCoreModelPackage.IBE_XNAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>TGGCSP</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGGCSP_OPERATION_COUNT = IBeXCoreModelPackage.IBE_XNAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGLocalVariableImpl <em>TGG Local Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGLocalVariableImpl
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.CSPPackageImpl#getTGGLocalVariable()
	 * @generated
	 */
	int TGG_LOCAL_VARIABLE = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_LOCAL_VARIABLE__NAME = IBeXCoreModelPackage.IBE_XNAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_LOCAL_VARIABLE__TYPE = IBeXCoreModelPackage.IBE_XNAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>TGG Local Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_LOCAL_VARIABLE_FEATURE_COUNT = IBeXCoreModelPackage.IBE_XNAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>TGG Local Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_LOCAL_VARIABLE_OPERATION_COUNT = IBeXCoreModelPackage.IBE_XNAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintDefinition <em>TGG Attribute Constraint Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Attribute Constraint Definition</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintDefinition
	 * @generated
	 */
	EClass getTGGAttributeConstraintDefinition();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintDefinition#getParameterDefinitions <em>Parameter Definitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameter Definitions</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintDefinition#getParameterDefinitions()
	 * @see #getTGGAttributeConstraintDefinition()
	 * @generated
	 */
	EReference getTGGAttributeConstraintDefinition_ParameterDefinitions();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintDefinition#getSyncBindings <em>Sync Bindings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sync Bindings</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintDefinition#getSyncBindings()
	 * @see #getTGGAttributeConstraintDefinition()
	 * @generated
	 */
	EReference getTGGAttributeConstraintDefinition_SyncBindings();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintDefinition#getGenBindings <em>Gen Bindings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Gen Bindings</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintDefinition#getGenBindings()
	 * @see #getTGGAttributeConstraintDefinition()
	 * @generated
	 */
	EReference getTGGAttributeConstraintDefinition_GenBindings();

	/**
	 * Returns the meta object for the container reference '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintDefinition#getLibrary <em>Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Library</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintDefinition#getLibrary()
	 * @see #getTGGAttributeConstraintDefinition()
	 * @generated
	 */
	EReference getTGGAttributeConstraintDefinition_Library();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintDefinitionLibrary <em>TGG Attribute Constraint Definition Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Attribute Constraint Definition Library</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintDefinitionLibrary
	 * @generated
	 */
	EClass getTGGAttributeConstraintDefinitionLibrary();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintDefinitionLibrary#getTggAttributeConstraintDefinitions <em>Tgg Attribute Constraint Definitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Tgg Attribute Constraint Definitions</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintDefinitionLibrary#getTggAttributeConstraintDefinitions()
	 * @see #getTGGAttributeConstraintDefinitionLibrary()
	 * @generated
	 */
	EReference getTGGAttributeConstraintDefinitionLibrary_TggAttributeConstraintDefinitions();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintDefinitionLibrary#getPackageName <em>Package Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Package Name</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintDefinitionLibrary#getPackageName()
	 * @see #getTGGAttributeConstraintDefinitionLibrary()
	 * @generated
	 */
	EAttribute getTGGAttributeConstraintDefinitionLibrary_PackageName();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintParameterDefinition <em>TGG Attribute Constraint Parameter Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Attribute Constraint Parameter Definition</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintParameterDefinition
	 * @generated
	 */
	EClass getTGGAttributeConstraintParameterDefinition();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintParameterDefinition#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintParameterDefinition#getType()
	 * @see #getTGGAttributeConstraintParameterDefinition()
	 * @generated
	 */
	EReference getTGGAttributeConstraintParameterDefinition_Type();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintParameterDefinition#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintParameterDefinition#getName()
	 * @see #getTGGAttributeConstraintParameterDefinition()
	 * @generated
	 */
	EAttribute getTGGAttributeConstraintParameterDefinition_Name();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintBinding <em>TGG Attribute Constraint Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Attribute Constraint Binding</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintBinding
	 * @generated
	 */
	EClass getTGGAttributeConstraintBinding();

	/**
	 * Returns the meta object for the attribute list '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintBinding#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Value</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintBinding#getValue()
	 * @see #getTGGAttributeConstraintBinding()
	 * @generated
	 */
	EAttribute getTGGAttributeConstraintBinding_Value();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintSet <em>TGG Attribute Constraint Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Attribute Constraint Set</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintSet
	 * @generated
	 */
	EClass getTGGAttributeConstraintSet();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintSet#getTggAttributeConstraints <em>Tgg Attribute Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Tgg Attribute Constraints</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintSet#getTggAttributeConstraints()
	 * @see #getTGGAttributeConstraintSet()
	 * @generated
	 */
	EReference getTGGAttributeConstraintSet_TggAttributeConstraints();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintSet#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintSet#getParameters()
	 * @see #getTGGAttributeConstraintSet()
	 * @generated
	 */
	EReference getTGGAttributeConstraintSet_Parameters();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraint <em>TGG Attribute Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Attribute Constraint</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraint
	 * @generated
	 */
	EClass getTGGAttributeConstraint();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraint#getDefinition <em>Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Definition</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraint#getDefinition()
	 * @see #getTGGAttributeConstraint()
	 * @generated
	 */
	EReference getTGGAttributeConstraint_Definition();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraint#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Parameters</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraint#getParameters()
	 * @see #getTGGAttributeConstraint()
	 * @generated
	 */
	EReference getTGGAttributeConstraint_Parameters();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintVariable <em>TGG Attribute Constraint Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Attribute Constraint Variable</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintVariable
	 * @generated
	 */
	EClass getTGGAttributeConstraintVariable();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintVariable#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintVariable#getName()
	 * @see #getTGGAttributeConstraintVariable()
	 * @generated
	 */
	EAttribute getTGGAttributeConstraintVariable_Name();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintParameterValue <em>TGG Attribute Constraint Parameter Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Attribute Constraint Parameter Value</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintParameterValue
	 * @generated
	 */
	EClass getTGGAttributeConstraintParameterValue();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintParameterValue#getParameterDefinition <em>Parameter Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parameter Definition</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintParameterValue#getParameterDefinition()
	 * @see #getTGGAttributeConstraintParameterValue()
	 * @generated
	 */
	EReference getTGGAttributeConstraintParameterValue_ParameterDefinition();

	/**
	 * Returns the meta object for the containment reference '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintParameterValue#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintParameterValue#getExpression()
	 * @see #getTGGAttributeConstraintParameterValue()
	 * @generated
	 */
	EReference getTGGAttributeConstraintParameterValue_Expression();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintParameterValue#isDerived <em>Derived</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Derived</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintParameterValue#isDerived()
	 * @see #getTGGAttributeConstraintParameterValue()
	 * @generated
	 */
	EAttribute getTGGAttributeConstraintParameterValue_Derived();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGCSP <em>TGGCSP</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGGCSP</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGCSP
	 * @generated
	 */
	EClass getTGGCSP();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGCSP#getPackage <em>Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Package</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGCSP#getPackage()
	 * @see #getTGGCSP()
	 * @generated
	 */
	EAttribute getTGGCSP_Package();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGCSP#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Values</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGCSP#getValues()
	 * @see #getTGGCSP()
	 * @generated
	 */
	EReference getTGGCSP_Values();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGLocalVariable <em>TGG Local Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Local Variable</em>'.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGLocalVariable
	 * @generated
	 */
	EClass getTGGLocalVariable();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	CSPFactory getCSPFactory();

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
		 * The meta object literal for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGAttributeConstraintDefinitionImpl <em>TGG Attribute Constraint Definition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGAttributeConstraintDefinitionImpl
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.CSPPackageImpl#getTGGAttributeConstraintDefinition()
		 * @generated
		 */
		EClass TGG_ATTRIBUTE_CONSTRAINT_DEFINITION = eINSTANCE.getTGGAttributeConstraintDefinition();

		/**
		 * The meta object literal for the '<em><b>Parameter Definitions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__PARAMETER_DEFINITIONS = eINSTANCE.getTGGAttributeConstraintDefinition_ParameterDefinitions();

		/**
		 * The meta object literal for the '<em><b>Sync Bindings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__SYNC_BINDINGS = eINSTANCE.getTGGAttributeConstraintDefinition_SyncBindings();

		/**
		 * The meta object literal for the '<em><b>Gen Bindings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__GEN_BINDINGS = eINSTANCE.getTGGAttributeConstraintDefinition_GenBindings();

		/**
		 * The meta object literal for the '<em><b>Library</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__LIBRARY = eINSTANCE.getTGGAttributeConstraintDefinition_Library();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGAttributeConstraintDefinitionLibraryImpl <em>TGG Attribute Constraint Definition Library</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGAttributeConstraintDefinitionLibraryImpl
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.CSPPackageImpl#getTGGAttributeConstraintDefinitionLibrary()
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
		 * The meta object literal for the '<em><b>Package Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY__PACKAGE_NAME = eINSTANCE.getTGGAttributeConstraintDefinitionLibrary_PackageName();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGAttributeConstraintParameterDefinitionImpl <em>TGG Attribute Constraint Parameter Definition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGAttributeConstraintParameterDefinitionImpl
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.CSPPackageImpl#getTGGAttributeConstraintParameterDefinition()
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
		 * The meta object literal for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGAttributeConstraintBindingImpl <em>TGG Attribute Constraint Binding</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGAttributeConstraintBindingImpl
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.CSPPackageImpl#getTGGAttributeConstraintBinding()
		 * @generated
		 */
		EClass TGG_ATTRIBUTE_CONSTRAINT_BINDING = eINSTANCE.getTGGAttributeConstraintBinding();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TGG_ATTRIBUTE_CONSTRAINT_BINDING__VALUE = eINSTANCE.getTGGAttributeConstraintBinding_Value();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGAttributeConstraintSetImpl <em>TGG Attribute Constraint Set</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGAttributeConstraintSetImpl
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.CSPPackageImpl#getTGGAttributeConstraintSet()
		 * @generated
		 */
		EClass TGG_ATTRIBUTE_CONSTRAINT_SET = eINSTANCE.getTGGAttributeConstraintSet();

		/**
		 * The meta object literal for the '<em><b>Tgg Attribute Constraints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_ATTRIBUTE_CONSTRAINT_SET__TGG_ATTRIBUTE_CONSTRAINTS = eINSTANCE.getTGGAttributeConstraintSet_TggAttributeConstraints();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_ATTRIBUTE_CONSTRAINT_SET__PARAMETERS = eINSTANCE.getTGGAttributeConstraintSet_Parameters();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGAttributeConstraintImpl <em>TGG Attribute Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGAttributeConstraintImpl
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.CSPPackageImpl#getTGGAttributeConstraint()
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
		 * The meta object literal for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGAttributeConstraintVariableImpl <em>TGG Attribute Constraint Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGAttributeConstraintVariableImpl
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.CSPPackageImpl#getTGGAttributeConstraintVariable()
		 * @generated
		 */
		EClass TGG_ATTRIBUTE_CONSTRAINT_VARIABLE = eINSTANCE.getTGGAttributeConstraintVariable();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TGG_ATTRIBUTE_CONSTRAINT_VARIABLE__NAME = eINSTANCE.getTGGAttributeConstraintVariable_Name();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGAttributeConstraintParameterValueImpl <em>TGG Attribute Constraint Parameter Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGAttributeConstraintParameterValueImpl
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.CSPPackageImpl#getTGGAttributeConstraintParameterValue()
		 * @generated
		 */
		EClass TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_VALUE = eINSTANCE.getTGGAttributeConstraintParameterValue();

		/**
		 * The meta object literal for the '<em><b>Parameter Definition</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_VALUE__PARAMETER_DEFINITION = eINSTANCE
				.getTGGAttributeConstraintParameterValue_ParameterDefinition();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_VALUE__EXPRESSION = eINSTANCE.getTGGAttributeConstraintParameterValue_Expression();

		/**
		 * The meta object literal for the '<em><b>Derived</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_VALUE__DERIVED = eINSTANCE.getTGGAttributeConstraintParameterValue_Derived();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGCSPImpl <em>TGGCSP</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGCSPImpl
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.CSPPackageImpl#getTGGCSP()
		 * @generated
		 */
		EClass TGGCSP = eINSTANCE.getTGGCSP();

		/**
		 * The meta object literal for the '<em><b>Package</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TGGCSP__PACKAGE = eINSTANCE.getTGGCSP_Package();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGGCSP__VALUES = eINSTANCE.getTGGCSP_Values();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGLocalVariableImpl <em>TGG Local Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.TGGLocalVariableImpl
		 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.impl.CSPPackageImpl#getTGGLocalVariable()
		 * @generated
		 */
		EClass TGG_LOCAL_VARIABLE = eINSTANCE.getTGGLocalVariable();

	}

} //CSPPackage
