/**
 */
package IBeXLanguage;

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
 * Model for IBeXPatterns.
 * <!-- end-model-doc -->
 * @see IBeXLanguage.IBeXLanguageFactory
 * @model kind="package"
 * @generated
 */
public interface IBeXLanguagePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "IBeXLanguage";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "platform:/resource/org.emoflon.ibex.common/model/Common.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "IBeXLanguage";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	IBeXLanguagePackage eINSTANCE = IBeXLanguage.impl.IBeXLanguagePackageImpl.init();

	/**
	 * The meta object id for the '{@link IBeXLanguage.impl.IBeXAttributeImpl <em>IBe XAttribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see IBeXLanguage.impl.IBeXAttributeImpl
	 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXAttribute()
	 * @generated
	 */
	int IBE_XATTRIBUTE = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE__TYPE = 0;

	/**
	 * The feature id for the '<em><b>Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE__NODE = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE__VALUE = 2;

	/**
	 * The number of structural features of the '<em>IBe XAttribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>IBe XAttribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link IBeXLanguage.impl.IBeXAttributeAssignmentImpl <em>IBe XAttribute Assignment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see IBeXLanguage.impl.IBeXAttributeAssignmentImpl
	 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXAttributeAssignment()
	 * @generated
	 */
	int IBE_XATTRIBUTE_ASSIGNMENT = 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_ASSIGNMENT__TYPE = IBE_XATTRIBUTE__TYPE;

	/**
	 * The feature id for the '<em><b>Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_ASSIGNMENT__NODE = IBE_XATTRIBUTE__NODE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_ASSIGNMENT__VALUE = IBE_XATTRIBUTE__VALUE;

	/**
	 * The number of structural features of the '<em>IBe XAttribute Assignment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_ASSIGNMENT_FEATURE_COUNT = IBE_XATTRIBUTE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>IBe XAttribute Assignment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_ASSIGNMENT_OPERATION_COUNT = IBE_XATTRIBUTE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link IBeXLanguage.impl.IBeXAttributeConstraintImpl <em>IBe XAttribute Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see IBeXLanguage.impl.IBeXAttributeConstraintImpl
	 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXAttributeConstraint()
	 * @generated
	 */
	int IBE_XATTRIBUTE_CONSTRAINT = 2;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_CONSTRAINT__TYPE = IBE_XATTRIBUTE__TYPE;

	/**
	 * The feature id for the '<em><b>Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_CONSTRAINT__NODE = IBE_XATTRIBUTE__NODE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_CONSTRAINT__VALUE = IBE_XATTRIBUTE__VALUE;

	/**
	 * The feature id for the '<em><b>Relation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_CONSTRAINT__RELATION = IBE_XATTRIBUTE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>IBe XAttribute Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_CONSTRAINT_FEATURE_COUNT = IBE_XATTRIBUTE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>IBe XAttribute Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_CONSTRAINT_OPERATION_COUNT = IBE_XATTRIBUTE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link IBeXLanguage.impl.IBeXAttributeValueImpl <em>IBe XAttribute Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see IBeXLanguage.impl.IBeXAttributeValueImpl
	 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXAttributeValue()
	 * @generated
	 */
	int IBE_XATTRIBUTE_VALUE = 4;

	/**
	 * The number of structural features of the '<em>IBe XAttribute Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_VALUE_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>IBe XAttribute Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_VALUE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link IBeXLanguage.impl.IBeXAttributeParameterImpl <em>IBe XAttribute Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see IBeXLanguage.impl.IBeXAttributeParameterImpl
	 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXAttributeParameter()
	 * @generated
	 */
	int IBE_XATTRIBUTE_PARAMETER = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_PARAMETER__NAME = IBE_XATTRIBUTE_VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>IBe XAttribute Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_PARAMETER_FEATURE_COUNT = IBE_XATTRIBUTE_VALUE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>IBe XAttribute Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_PARAMETER_OPERATION_COUNT = IBE_XATTRIBUTE_VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link IBeXLanguage.impl.IBeXAttributeExpressionImpl <em>IBe XAttribute Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see IBeXLanguage.impl.IBeXAttributeExpressionImpl
	 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXAttributeExpression()
	 * @generated
	 */
	int IBE_XATTRIBUTE_EXPRESSION = 5;

	/**
	 * The feature id for the '<em><b>Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_EXPRESSION__ATTRIBUTE = IBE_XATTRIBUTE_VALUE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_EXPRESSION__NODE = IBE_XATTRIBUTE_VALUE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>IBe XAttribute Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_EXPRESSION_FEATURE_COUNT = IBE_XATTRIBUTE_VALUE_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>IBe XAttribute Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XATTRIBUTE_EXPRESSION_OPERATION_COUNT = IBE_XATTRIBUTE_VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link IBeXLanguage.impl.IBeXConstantImpl <em>IBe XConstant</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see IBeXLanguage.impl.IBeXConstantImpl
	 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXConstant()
	 * @generated
	 */
	int IBE_XCONSTANT = 6;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONSTANT__VALUE = IBE_XATTRIBUTE_VALUE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>String Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONSTANT__STRING_VALUE = IBE_XATTRIBUTE_VALUE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>IBe XConstant</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONSTANT_FEATURE_COUNT = IBE_XATTRIBUTE_VALUE_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>IBe XConstant</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONSTANT_OPERATION_COUNT = IBE_XATTRIBUTE_VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link IBeXLanguage.impl.IBeXNamedElementImpl <em>IBe XNamed Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see IBeXLanguage.impl.IBeXNamedElementImpl
	 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXNamedElement()
	 * @generated
	 */
	int IBE_XNAMED_ELEMENT = 14;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XNAMED_ELEMENT__NAME = 0;

	/**
	 * The number of structural features of the '<em>IBe XNamed Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XNAMED_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>IBe XNamed Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XNAMED_ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link IBeXLanguage.impl.IBeXPatternImpl <em>IBe XPattern</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see IBeXLanguage.impl.IBeXPatternImpl
	 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXPattern()
	 * @generated
	 */
	int IBE_XPATTERN = 18;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPATTERN__NAME = IBE_XNAMED_ELEMENT__NAME;

	/**
	 * The number of structural features of the '<em>IBe XPattern</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPATTERN_FEATURE_COUNT = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>IBe XPattern</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPATTERN_OPERATION_COUNT = IBE_XNAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link IBeXLanguage.impl.IBeXContextImpl <em>IBe XContext</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see IBeXLanguage.impl.IBeXContextImpl
	 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXContext()
	 * @generated
	 */
	int IBE_XCONTEXT = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONTEXT__NAME = IBE_XPATTERN__NAME;

	/**
	 * The number of structural features of the '<em>IBe XContext</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONTEXT_FEATURE_COUNT = IBE_XPATTERN_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>IBe XContext</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONTEXT_OPERATION_COUNT = IBE_XPATTERN_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link IBeXLanguage.impl.IBeXContextAlternativesImpl <em>IBe XContext Alternatives</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see IBeXLanguage.impl.IBeXContextAlternativesImpl
	 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXContextAlternatives()
	 * @generated
	 */
	int IBE_XCONTEXT_ALTERNATIVES = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONTEXT_ALTERNATIVES__NAME = IBE_XCONTEXT__NAME;

	/**
	 * The feature id for the '<em><b>Alternative Patterns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONTEXT_ALTERNATIVES__ALTERNATIVE_PATTERNS = IBE_XCONTEXT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>IBe XContext Alternatives</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONTEXT_ALTERNATIVES_FEATURE_COUNT = IBE_XCONTEXT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>IBe XContext Alternatives</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONTEXT_ALTERNATIVES_OPERATION_COUNT = IBE_XCONTEXT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link IBeXLanguage.impl.IBeXContextPatternImpl <em>IBe XContext Pattern</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see IBeXLanguage.impl.IBeXContextPatternImpl
	 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXContextPattern()
	 * @generated
	 */
	int IBE_XCONTEXT_PATTERN = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONTEXT_PATTERN__NAME = IBE_XCONTEXT__NAME;

	/**
	 * The feature id for the '<em><b>Attribute Constraint</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONTEXT_PATTERN__ATTRIBUTE_CONSTRAINT = IBE_XCONTEXT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Injectivity Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONTEXT_PATTERN__INJECTIVITY_CONSTRAINTS = IBE_XCONTEXT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Invocations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONTEXT_PATTERN__INVOCATIONS = IBE_XCONTEXT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Local Edges</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONTEXT_PATTERN__LOCAL_EDGES = IBE_XCONTEXT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Local Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONTEXT_PATTERN__LOCAL_NODES = IBE_XCONTEXT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Signature Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONTEXT_PATTERN__SIGNATURE_NODES = IBE_XCONTEXT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Csps</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONTEXT_PATTERN__CSPS = IBE_XCONTEXT_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>IBe XContext Pattern</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONTEXT_PATTERN_FEATURE_COUNT = IBE_XCONTEXT_FEATURE_COUNT + 7;

	/**
	 * The number of operations of the '<em>IBe XContext Pattern</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCONTEXT_PATTERN_OPERATION_COUNT = IBE_XCONTEXT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link IBeXLanguage.impl.IBeXCreatePatternImpl <em>IBe XCreate Pattern</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see IBeXLanguage.impl.IBeXCreatePatternImpl
	 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXCreatePattern()
	 * @generated
	 */
	int IBE_XCREATE_PATTERN = 10;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCREATE_PATTERN__NAME = IBE_XPATTERN__NAME;

	/**
	 * The feature id for the '<em><b>Attribute Assignments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCREATE_PATTERN__ATTRIBUTE_ASSIGNMENTS = IBE_XPATTERN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Context Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCREATE_PATTERN__CONTEXT_NODES = IBE_XPATTERN_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Created Edges</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCREATE_PATTERN__CREATED_EDGES = IBE_XPATTERN_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Created Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCREATE_PATTERN__CREATED_NODES = IBE_XPATTERN_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>IBe XCreate Pattern</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCREATE_PATTERN_FEATURE_COUNT = IBE_XPATTERN_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>IBe XCreate Pattern</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCREATE_PATTERN_OPERATION_COUNT = IBE_XPATTERN_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link IBeXLanguage.impl.IBeXDeletePatternImpl <em>IBe XDelete Pattern</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see IBeXLanguage.impl.IBeXDeletePatternImpl
	 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXDeletePattern()
	 * @generated
	 */
	int IBE_XDELETE_PATTERN = 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDELETE_PATTERN__NAME = IBE_XPATTERN__NAME;

	/**
	 * The feature id for the '<em><b>Context Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDELETE_PATTERN__CONTEXT_NODES = IBE_XPATTERN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Deleted Edges</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDELETE_PATTERN__DELETED_EDGES = IBE_XPATTERN_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Deleted Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDELETE_PATTERN__DELETED_NODES = IBE_XPATTERN_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>IBe XDelete Pattern</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDELETE_PATTERN_FEATURE_COUNT = IBE_XPATTERN_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>IBe XDelete Pattern</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDELETE_PATTERN_OPERATION_COUNT = IBE_XPATTERN_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link IBeXLanguage.impl.IBeXEdgeImpl <em>IBe XEdge</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see IBeXLanguage.impl.IBeXEdgeImpl
	 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXEdge()
	 * @generated
	 */
	int IBE_XEDGE = 12;

	/**
	 * The feature id for the '<em><b>Source Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XEDGE__SOURCE_NODE = 0;

	/**
	 * The feature id for the '<em><b>Target Node</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XEDGE__TARGET_NODE = 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XEDGE__TYPE = 2;

	/**
	 * The number of structural features of the '<em>IBe XEdge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XEDGE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>IBe XEdge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XEDGE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link IBeXLanguage.impl.IBeXEnumLiteralImpl <em>IBe XEnum Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see IBeXLanguage.impl.IBeXEnumLiteralImpl
	 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXEnumLiteral()
	 * @generated
	 */
	int IBE_XENUM_LITERAL = 13;

	/**
	 * The feature id for the '<em><b>Literal</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XENUM_LITERAL__LITERAL = IBE_XATTRIBUTE_VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>IBe XEnum Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XENUM_LITERAL_FEATURE_COUNT = IBE_XATTRIBUTE_VALUE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>IBe XEnum Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XENUM_LITERAL_OPERATION_COUNT = IBE_XATTRIBUTE_VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link IBeXLanguage.impl.IBeXNodeImpl <em>IBe XNode</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see IBeXLanguage.impl.IBeXNodeImpl
	 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXNode()
	 * @generated
	 */
	int IBE_XNODE = 15;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XNODE__NAME = IBE_XNAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Incoming Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XNODE__INCOMING_EDGES = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Outgoing Edges</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XNODE__OUTGOING_EDGES = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XNODE__TYPE = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>IBe XNode</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XNODE_FEATURE_COUNT = IBE_XNAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>IBe XNode</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XNODE_OPERATION_COUNT = IBE_XNAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link IBeXLanguage.impl.IBeXNodePairImpl <em>IBe XNode Pair</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see IBeXLanguage.impl.IBeXNodePairImpl
	 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXNodePair()
	 * @generated
	 */
	int IBE_XNODE_PAIR = 16;

	/**
	 * The feature id for the '<em><b>Values</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XNODE_PAIR__VALUES = 0;

	/**
	 * The number of structural features of the '<em>IBe XNode Pair</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XNODE_PAIR_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>IBe XNode Pair</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XNODE_PAIR_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link IBeXLanguage.impl.IBeXNodeToNodeMappingImpl <em>IBe XNode To Node Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see IBeXLanguage.impl.IBeXNodeToNodeMappingImpl
	 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXNodeToNodeMapping()
	 * @generated
	 */
	int IBE_XNODE_TO_NODE_MAPPING = 17;

	/**
	 * The feature id for the '<em><b>Key</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XNODE_TO_NODE_MAPPING__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XNODE_TO_NODE_MAPPING__VALUE = 1;

	/**
	 * The number of structural features of the '<em>IBe XNode To Node Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XNODE_TO_NODE_MAPPING_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>IBe XNode To Node Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XNODE_TO_NODE_MAPPING_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link IBeXLanguage.impl.IBeXPatternInvocationImpl <em>IBe XPattern Invocation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see IBeXLanguage.impl.IBeXPatternInvocationImpl
	 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXPatternInvocation()
	 * @generated
	 */
	int IBE_XPATTERN_INVOCATION = 19;

	/**
	 * The feature id for the '<em><b>Positive</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPATTERN_INVOCATION__POSITIVE = 0;

	/**
	 * The feature id for the '<em><b>Invoked By</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPATTERN_INVOCATION__INVOKED_BY = 1;

	/**
	 * The feature id for the '<em><b>Invoked Pattern</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPATTERN_INVOCATION__INVOKED_PATTERN = 2;

	/**
	 * The feature id for the '<em><b>Mapping</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPATTERN_INVOCATION__MAPPING = 3;

	/**
	 * The number of structural features of the '<em>IBe XPattern Invocation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPATTERN_INVOCATION_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>IBe XPattern Invocation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPATTERN_INVOCATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link IBeXLanguage.impl.IBeXPatternSetImpl <em>IBe XPattern Set</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see IBeXLanguage.impl.IBeXPatternSetImpl
	 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXPatternSet()
	 * @generated
	 */
	int IBE_XPATTERN_SET = 20;

	/**
	 * The feature id for the '<em><b>Context Patterns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPATTERN_SET__CONTEXT_PATTERNS = 0;

	/**
	 * The feature id for the '<em><b>Create Patterns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPATTERN_SET__CREATE_PATTERNS = 1;

	/**
	 * The feature id for the '<em><b>Delete Patterns</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPATTERN_SET__DELETE_PATTERNS = 2;

	/**
	 * The number of structural features of the '<em>IBe XPattern Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPATTERN_SET_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>IBe XPattern Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XPATTERN_SET_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link IBeXLanguage.impl.IBeXCSPImpl <em>IBe XCSP</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see IBeXLanguage.impl.IBeXCSPImpl
	 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXCSP()
	 * @generated
	 */
	int IBE_XCSP = 21;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCSP__NAME = 0;

	/**
	 * The feature id for the '<em><b>Package</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCSP__PACKAGE = 1;

	/**
	 * The feature id for the '<em><b>Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCSP__VALUES = 2;

	/**
	 * The number of structural features of the '<em>IBe XCSP</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCSP_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>IBe XCSP</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XCSP_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link IBeXLanguage.IBeXRelation <em>IBe XRelation</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see IBeXLanguage.IBeXRelation
	 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXRelation()
	 * @generated
	 */
	int IBE_XRELATION = 22;

	/**
	 * Returns the meta object for class '{@link IBeXLanguage.IBeXAttribute <em>IBe XAttribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XAttribute</em>'.
	 * @see IBeXLanguage.IBeXAttribute
	 * @generated
	 */
	EClass getIBeXAttribute();

	/**
	 * Returns the meta object for the reference '{@link IBeXLanguage.IBeXAttribute#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see IBeXLanguage.IBeXAttribute#getType()
	 * @see #getIBeXAttribute()
	 * @generated
	 */
	EReference getIBeXAttribute_Type();

	/**
	 * Returns the meta object for the reference '{@link IBeXLanguage.IBeXAttribute#getNode <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Node</em>'.
	 * @see IBeXLanguage.IBeXAttribute#getNode()
	 * @see #getIBeXAttribute()
	 * @generated
	 */
	EReference getIBeXAttribute_Node();

	/**
	 * Returns the meta object for the containment reference '{@link IBeXLanguage.IBeXAttribute#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see IBeXLanguage.IBeXAttribute#getValue()
	 * @see #getIBeXAttribute()
	 * @generated
	 */
	EReference getIBeXAttribute_Value();

	/**
	 * Returns the meta object for class '{@link IBeXLanguage.IBeXAttributeAssignment <em>IBe XAttribute Assignment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XAttribute Assignment</em>'.
	 * @see IBeXLanguage.IBeXAttributeAssignment
	 * @generated
	 */
	EClass getIBeXAttributeAssignment();

	/**
	 * Returns the meta object for class '{@link IBeXLanguage.IBeXAttributeConstraint <em>IBe XAttribute Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XAttribute Constraint</em>'.
	 * @see IBeXLanguage.IBeXAttributeConstraint
	 * @generated
	 */
	EClass getIBeXAttributeConstraint();

	/**
	 * Returns the meta object for the attribute '{@link IBeXLanguage.IBeXAttributeConstraint#getRelation <em>Relation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Relation</em>'.
	 * @see IBeXLanguage.IBeXAttributeConstraint#getRelation()
	 * @see #getIBeXAttributeConstraint()
	 * @generated
	 */
	EAttribute getIBeXAttributeConstraint_Relation();

	/**
	 * Returns the meta object for class '{@link IBeXLanguage.IBeXAttributeParameter <em>IBe XAttribute Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XAttribute Parameter</em>'.
	 * @see IBeXLanguage.IBeXAttributeParameter
	 * @generated
	 */
	EClass getIBeXAttributeParameter();

	/**
	 * Returns the meta object for class '{@link IBeXLanguage.IBeXAttributeValue <em>IBe XAttribute Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XAttribute Value</em>'.
	 * @see IBeXLanguage.IBeXAttributeValue
	 * @generated
	 */
	EClass getIBeXAttributeValue();

	/**
	 * Returns the meta object for class '{@link IBeXLanguage.IBeXAttributeExpression <em>IBe XAttribute Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XAttribute Expression</em>'.
	 * @see IBeXLanguage.IBeXAttributeExpression
	 * @generated
	 */
	EClass getIBeXAttributeExpression();

	/**
	 * Returns the meta object for the reference '{@link IBeXLanguage.IBeXAttributeExpression#getAttribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Attribute</em>'.
	 * @see IBeXLanguage.IBeXAttributeExpression#getAttribute()
	 * @see #getIBeXAttributeExpression()
	 * @generated
	 */
	EReference getIBeXAttributeExpression_Attribute();

	/**
	 * Returns the meta object for the reference '{@link IBeXLanguage.IBeXAttributeExpression#getNode <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Node</em>'.
	 * @see IBeXLanguage.IBeXAttributeExpression#getNode()
	 * @see #getIBeXAttributeExpression()
	 * @generated
	 */
	EReference getIBeXAttributeExpression_Node();

	/**
	 * Returns the meta object for class '{@link IBeXLanguage.IBeXConstant <em>IBe XConstant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XConstant</em>'.
	 * @see IBeXLanguage.IBeXConstant
	 * @generated
	 */
	EClass getIBeXConstant();

	/**
	 * Returns the meta object for the attribute '{@link IBeXLanguage.IBeXConstant#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see IBeXLanguage.IBeXConstant#getValue()
	 * @see #getIBeXConstant()
	 * @generated
	 */
	EAttribute getIBeXConstant_Value();

	/**
	 * Returns the meta object for the attribute '{@link IBeXLanguage.IBeXConstant#getStringValue <em>String Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>String Value</em>'.
	 * @see IBeXLanguage.IBeXConstant#getStringValue()
	 * @see #getIBeXConstant()
	 * @generated
	 */
	EAttribute getIBeXConstant_StringValue();

	/**
	 * Returns the meta object for class '{@link IBeXLanguage.IBeXContext <em>IBe XContext</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XContext</em>'.
	 * @see IBeXLanguage.IBeXContext
	 * @generated
	 */
	EClass getIBeXContext();

	/**
	 * Returns the meta object for class '{@link IBeXLanguage.IBeXContextAlternatives <em>IBe XContext Alternatives</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XContext Alternatives</em>'.
	 * @see IBeXLanguage.IBeXContextAlternatives
	 * @generated
	 */
	EClass getIBeXContextAlternatives();

	/**
	 * Returns the meta object for the containment reference list '{@link IBeXLanguage.IBeXContextAlternatives#getAlternativePatterns <em>Alternative Patterns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Alternative Patterns</em>'.
	 * @see IBeXLanguage.IBeXContextAlternatives#getAlternativePatterns()
	 * @see #getIBeXContextAlternatives()
	 * @generated
	 */
	EReference getIBeXContextAlternatives_AlternativePatterns();

	/**
	 * Returns the meta object for class '{@link IBeXLanguage.IBeXContextPattern <em>IBe XContext Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XContext Pattern</em>'.
	 * @see IBeXLanguage.IBeXContextPattern
	 * @generated
	 */
	EClass getIBeXContextPattern();

	/**
	 * Returns the meta object for the containment reference list '{@link IBeXLanguage.IBeXContextPattern#getAttributeConstraint <em>Attribute Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attribute Constraint</em>'.
	 * @see IBeXLanguage.IBeXContextPattern#getAttributeConstraint()
	 * @see #getIBeXContextPattern()
	 * @generated
	 */
	EReference getIBeXContextPattern_AttributeConstraint();

	/**
	 * Returns the meta object for the containment reference list '{@link IBeXLanguage.IBeXContextPattern#getInjectivityConstraints <em>Injectivity Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Injectivity Constraints</em>'.
	 * @see IBeXLanguage.IBeXContextPattern#getInjectivityConstraints()
	 * @see #getIBeXContextPattern()
	 * @generated
	 */
	EReference getIBeXContextPattern_InjectivityConstraints();

	/**
	 * Returns the meta object for the containment reference list '{@link IBeXLanguage.IBeXContextPattern#getInvocations <em>Invocations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Invocations</em>'.
	 * @see IBeXLanguage.IBeXContextPattern#getInvocations()
	 * @see #getIBeXContextPattern()
	 * @generated
	 */
	EReference getIBeXContextPattern_Invocations();

	/**
	 * Returns the meta object for the containment reference list '{@link IBeXLanguage.IBeXContextPattern#getLocalEdges <em>Local Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Local Edges</em>'.
	 * @see IBeXLanguage.IBeXContextPattern#getLocalEdges()
	 * @see #getIBeXContextPattern()
	 * @generated
	 */
	EReference getIBeXContextPattern_LocalEdges();

	/**
	 * Returns the meta object for the containment reference list '{@link IBeXLanguage.IBeXContextPattern#getLocalNodes <em>Local Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Local Nodes</em>'.
	 * @see IBeXLanguage.IBeXContextPattern#getLocalNodes()
	 * @see #getIBeXContextPattern()
	 * @generated
	 */
	EReference getIBeXContextPattern_LocalNodes();

	/**
	 * Returns the meta object for the containment reference list '{@link IBeXLanguage.IBeXContextPattern#getSignatureNodes <em>Signature Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Signature Nodes</em>'.
	 * @see IBeXLanguage.IBeXContextPattern#getSignatureNodes()
	 * @see #getIBeXContextPattern()
	 * @generated
	 */
	EReference getIBeXContextPattern_SignatureNodes();

	/**
	 * Returns the meta object for the containment reference list '{@link IBeXLanguage.IBeXContextPattern#getCsps <em>Csps</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Csps</em>'.
	 * @see IBeXLanguage.IBeXContextPattern#getCsps()
	 * @see #getIBeXContextPattern()
	 * @generated
	 */
	EReference getIBeXContextPattern_Csps();

	/**
	 * Returns the meta object for class '{@link IBeXLanguage.IBeXCreatePattern <em>IBe XCreate Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XCreate Pattern</em>'.
	 * @see IBeXLanguage.IBeXCreatePattern
	 * @generated
	 */
	EClass getIBeXCreatePattern();

	/**
	 * Returns the meta object for the containment reference list '{@link IBeXLanguage.IBeXCreatePattern#getAttributeAssignments <em>Attribute Assignments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attribute Assignments</em>'.
	 * @see IBeXLanguage.IBeXCreatePattern#getAttributeAssignments()
	 * @see #getIBeXCreatePattern()
	 * @generated
	 */
	EReference getIBeXCreatePattern_AttributeAssignments();

	/**
	 * Returns the meta object for the containment reference list '{@link IBeXLanguage.IBeXCreatePattern#getContextNodes <em>Context Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Context Nodes</em>'.
	 * @see IBeXLanguage.IBeXCreatePattern#getContextNodes()
	 * @see #getIBeXCreatePattern()
	 * @generated
	 */
	EReference getIBeXCreatePattern_ContextNodes();

	/**
	 * Returns the meta object for the containment reference list '{@link IBeXLanguage.IBeXCreatePattern#getCreatedEdges <em>Created Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Created Edges</em>'.
	 * @see IBeXLanguage.IBeXCreatePattern#getCreatedEdges()
	 * @see #getIBeXCreatePattern()
	 * @generated
	 */
	EReference getIBeXCreatePattern_CreatedEdges();

	/**
	 * Returns the meta object for the containment reference list '{@link IBeXLanguage.IBeXCreatePattern#getCreatedNodes <em>Created Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Created Nodes</em>'.
	 * @see IBeXLanguage.IBeXCreatePattern#getCreatedNodes()
	 * @see #getIBeXCreatePattern()
	 * @generated
	 */
	EReference getIBeXCreatePattern_CreatedNodes();

	/**
	 * Returns the meta object for class '{@link IBeXLanguage.IBeXDeletePattern <em>IBe XDelete Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XDelete Pattern</em>'.
	 * @see IBeXLanguage.IBeXDeletePattern
	 * @generated
	 */
	EClass getIBeXDeletePattern();

	/**
	 * Returns the meta object for the containment reference list '{@link IBeXLanguage.IBeXDeletePattern#getContextNodes <em>Context Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Context Nodes</em>'.
	 * @see IBeXLanguage.IBeXDeletePattern#getContextNodes()
	 * @see #getIBeXDeletePattern()
	 * @generated
	 */
	EReference getIBeXDeletePattern_ContextNodes();

	/**
	 * Returns the meta object for the containment reference list '{@link IBeXLanguage.IBeXDeletePattern#getDeletedEdges <em>Deleted Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Deleted Edges</em>'.
	 * @see IBeXLanguage.IBeXDeletePattern#getDeletedEdges()
	 * @see #getIBeXDeletePattern()
	 * @generated
	 */
	EReference getIBeXDeletePattern_DeletedEdges();

	/**
	 * Returns the meta object for the containment reference list '{@link IBeXLanguage.IBeXDeletePattern#getDeletedNodes <em>Deleted Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Deleted Nodes</em>'.
	 * @see IBeXLanguage.IBeXDeletePattern#getDeletedNodes()
	 * @see #getIBeXDeletePattern()
	 * @generated
	 */
	EReference getIBeXDeletePattern_DeletedNodes();

	/**
	 * Returns the meta object for class '{@link IBeXLanguage.IBeXEdge <em>IBe XEdge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XEdge</em>'.
	 * @see IBeXLanguage.IBeXEdge
	 * @generated
	 */
	EClass getIBeXEdge();

	/**
	 * Returns the meta object for the reference '{@link IBeXLanguage.IBeXEdge#getSourceNode <em>Source Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source Node</em>'.
	 * @see IBeXLanguage.IBeXEdge#getSourceNode()
	 * @see #getIBeXEdge()
	 * @generated
	 */
	EReference getIBeXEdge_SourceNode();

	/**
	 * Returns the meta object for the reference '{@link IBeXLanguage.IBeXEdge#getTargetNode <em>Target Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target Node</em>'.
	 * @see IBeXLanguage.IBeXEdge#getTargetNode()
	 * @see #getIBeXEdge()
	 * @generated
	 */
	EReference getIBeXEdge_TargetNode();

	/**
	 * Returns the meta object for the reference '{@link IBeXLanguage.IBeXEdge#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see IBeXLanguage.IBeXEdge#getType()
	 * @see #getIBeXEdge()
	 * @generated
	 */
	EReference getIBeXEdge_Type();

	/**
	 * Returns the meta object for class '{@link IBeXLanguage.IBeXEnumLiteral <em>IBe XEnum Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XEnum Literal</em>'.
	 * @see IBeXLanguage.IBeXEnumLiteral
	 * @generated
	 */
	EClass getIBeXEnumLiteral();

	/**
	 * Returns the meta object for the reference '{@link IBeXLanguage.IBeXEnumLiteral#getLiteral <em>Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Literal</em>'.
	 * @see IBeXLanguage.IBeXEnumLiteral#getLiteral()
	 * @see #getIBeXEnumLiteral()
	 * @generated
	 */
	EReference getIBeXEnumLiteral_Literal();

	/**
	 * Returns the meta object for class '{@link IBeXLanguage.IBeXNamedElement <em>IBe XNamed Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XNamed Element</em>'.
	 * @see IBeXLanguage.IBeXNamedElement
	 * @generated
	 */
	EClass getIBeXNamedElement();

	/**
	 * Returns the meta object for the attribute '{@link IBeXLanguage.IBeXNamedElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see IBeXLanguage.IBeXNamedElement#getName()
	 * @see #getIBeXNamedElement()
	 * @generated
	 */
	EAttribute getIBeXNamedElement_Name();

	/**
	 * Returns the meta object for class '{@link IBeXLanguage.IBeXNode <em>IBe XNode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XNode</em>'.
	 * @see IBeXLanguage.IBeXNode
	 * @generated
	 */
	EClass getIBeXNode();

	/**
	 * Returns the meta object for the reference list '{@link IBeXLanguage.IBeXNode#getIncomingEdges <em>Incoming Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Incoming Edges</em>'.
	 * @see IBeXLanguage.IBeXNode#getIncomingEdges()
	 * @see #getIBeXNode()
	 * @generated
	 */
	EReference getIBeXNode_IncomingEdges();

	/**
	 * Returns the meta object for the reference list '{@link IBeXLanguage.IBeXNode#getOutgoingEdges <em>Outgoing Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Outgoing Edges</em>'.
	 * @see IBeXLanguage.IBeXNode#getOutgoingEdges()
	 * @see #getIBeXNode()
	 * @generated
	 */
	EReference getIBeXNode_OutgoingEdges();

	/**
	 * Returns the meta object for the reference '{@link IBeXLanguage.IBeXNode#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see IBeXLanguage.IBeXNode#getType()
	 * @see #getIBeXNode()
	 * @generated
	 */
	EReference getIBeXNode_Type();

	/**
	 * Returns the meta object for class '{@link IBeXLanguage.IBeXNodePair <em>IBe XNode Pair</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XNode Pair</em>'.
	 * @see IBeXLanguage.IBeXNodePair
	 * @generated
	 */
	EClass getIBeXNodePair();

	/**
	 * Returns the meta object for the reference list '{@link IBeXLanguage.IBeXNodePair#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Values</em>'.
	 * @see IBeXLanguage.IBeXNodePair#getValues()
	 * @see #getIBeXNodePair()
	 * @generated
	 */
	EReference getIBeXNodePair_Values();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>IBe XNode To Node Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XNode To Node Mapping</em>'.
	 * @see java.util.Map.Entry
	 * @model keyType="IBeXLanguage.IBeXNode"
	 *        valueType="IBeXLanguage.IBeXNode"
	 * @generated
	 */
	EClass getIBeXNodeToNodeMapping();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getIBeXNodeToNodeMapping()
	 * @generated
	 */
	EReference getIBeXNodeToNodeMapping_Key();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getIBeXNodeToNodeMapping()
	 * @generated
	 */
	EReference getIBeXNodeToNodeMapping_Value();

	/**
	 * Returns the meta object for class '{@link IBeXLanguage.IBeXPattern <em>IBe XPattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XPattern</em>'.
	 * @see IBeXLanguage.IBeXPattern
	 * @generated
	 */
	EClass getIBeXPattern();

	/**
	 * Returns the meta object for class '{@link IBeXLanguage.IBeXPatternInvocation <em>IBe XPattern Invocation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XPattern Invocation</em>'.
	 * @see IBeXLanguage.IBeXPatternInvocation
	 * @generated
	 */
	EClass getIBeXPatternInvocation();

	/**
	 * Returns the meta object for the attribute '{@link IBeXLanguage.IBeXPatternInvocation#isPositive <em>Positive</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Positive</em>'.
	 * @see IBeXLanguage.IBeXPatternInvocation#isPositive()
	 * @see #getIBeXPatternInvocation()
	 * @generated
	 */
	EAttribute getIBeXPatternInvocation_Positive();

	/**
	 * Returns the meta object for the container reference '{@link IBeXLanguage.IBeXPatternInvocation#getInvokedBy <em>Invoked By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Invoked By</em>'.
	 * @see IBeXLanguage.IBeXPatternInvocation#getInvokedBy()
	 * @see #getIBeXPatternInvocation()
	 * @generated
	 */
	EReference getIBeXPatternInvocation_InvokedBy();

	/**
	 * Returns the meta object for the reference '{@link IBeXLanguage.IBeXPatternInvocation#getInvokedPattern <em>Invoked Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Invoked Pattern</em>'.
	 * @see IBeXLanguage.IBeXPatternInvocation#getInvokedPattern()
	 * @see #getIBeXPatternInvocation()
	 * @generated
	 */
	EReference getIBeXPatternInvocation_InvokedPattern();

	/**
	 * Returns the meta object for the map '{@link IBeXLanguage.IBeXPatternInvocation#getMapping <em>Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Mapping</em>'.
	 * @see IBeXLanguage.IBeXPatternInvocation#getMapping()
	 * @see #getIBeXPatternInvocation()
	 * @generated
	 */
	EReference getIBeXPatternInvocation_Mapping();

	/**
	 * Returns the meta object for class '{@link IBeXLanguage.IBeXPatternSet <em>IBe XPattern Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XPattern Set</em>'.
	 * @see IBeXLanguage.IBeXPatternSet
	 * @generated
	 */
	EClass getIBeXPatternSet();

	/**
	 * Returns the meta object for the containment reference list '{@link IBeXLanguage.IBeXPatternSet#getContextPatterns <em>Context Patterns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Context Patterns</em>'.
	 * @see IBeXLanguage.IBeXPatternSet#getContextPatterns()
	 * @see #getIBeXPatternSet()
	 * @generated
	 */
	EReference getIBeXPatternSet_ContextPatterns();

	/**
	 * Returns the meta object for the containment reference list '{@link IBeXLanguage.IBeXPatternSet#getCreatePatterns <em>Create Patterns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Create Patterns</em>'.
	 * @see IBeXLanguage.IBeXPatternSet#getCreatePatterns()
	 * @see #getIBeXPatternSet()
	 * @generated
	 */
	EReference getIBeXPatternSet_CreatePatterns();

	/**
	 * Returns the meta object for the containment reference list '{@link IBeXLanguage.IBeXPatternSet#getDeletePatterns <em>Delete Patterns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Delete Patterns</em>'.
	 * @see IBeXLanguage.IBeXPatternSet#getDeletePatterns()
	 * @see #getIBeXPatternSet()
	 * @generated
	 */
	EReference getIBeXPatternSet_DeletePatterns();

	/**
	 * Returns the meta object for class '{@link IBeXLanguage.IBeXCSP <em>IBe XCSP</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XCSP</em>'.
	 * @see IBeXLanguage.IBeXCSP
	 * @generated
	 */
	EClass getIBeXCSP();

	/**
	 * Returns the meta object for the attribute '{@link IBeXLanguage.IBeXCSP#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see IBeXLanguage.IBeXCSP#getName()
	 * @see #getIBeXCSP()
	 * @generated
	 */
	EAttribute getIBeXCSP_Name();

	/**
	 * Returns the meta object for the attribute '{@link IBeXLanguage.IBeXCSP#getPackage <em>Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Package</em>'.
	 * @see IBeXLanguage.IBeXCSP#getPackage()
	 * @see #getIBeXCSP()
	 * @generated
	 */
	EAttribute getIBeXCSP_Package();

	/**
	 * Returns the meta object for the containment reference list '{@link IBeXLanguage.IBeXCSP#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Values</em>'.
	 * @see IBeXLanguage.IBeXCSP#getValues()
	 * @see #getIBeXCSP()
	 * @generated
	 */
	EReference getIBeXCSP_Values();

	/**
	 * Returns the meta object for enum '{@link IBeXLanguage.IBeXRelation <em>IBe XRelation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>IBe XRelation</em>'.
	 * @see IBeXLanguage.IBeXRelation
	 * @generated
	 */
	EEnum getIBeXRelation();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	IBeXLanguageFactory getIBeXLanguageFactory();

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
		 * The meta object literal for the '{@link IBeXLanguage.impl.IBeXAttributeImpl <em>IBe XAttribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see IBeXLanguage.impl.IBeXAttributeImpl
		 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXAttribute()
		 * @generated
		 */
		EClass IBE_XATTRIBUTE = eINSTANCE.getIBeXAttribute();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XATTRIBUTE__TYPE = eINSTANCE.getIBeXAttribute_Type();

		/**
		 * The meta object literal for the '<em><b>Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XATTRIBUTE__NODE = eINSTANCE.getIBeXAttribute_Node();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XATTRIBUTE__VALUE = eINSTANCE.getIBeXAttribute_Value();

		/**
		 * The meta object literal for the '{@link IBeXLanguage.impl.IBeXAttributeAssignmentImpl <em>IBe XAttribute Assignment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see IBeXLanguage.impl.IBeXAttributeAssignmentImpl
		 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXAttributeAssignment()
		 * @generated
		 */
		EClass IBE_XATTRIBUTE_ASSIGNMENT = eINSTANCE.getIBeXAttributeAssignment();

		/**
		 * The meta object literal for the '{@link IBeXLanguage.impl.IBeXAttributeConstraintImpl <em>IBe XAttribute Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see IBeXLanguage.impl.IBeXAttributeConstraintImpl
		 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXAttributeConstraint()
		 * @generated
		 */
		EClass IBE_XATTRIBUTE_CONSTRAINT = eINSTANCE.getIBeXAttributeConstraint();

		/**
		 * The meta object literal for the '<em><b>Relation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IBE_XATTRIBUTE_CONSTRAINT__RELATION = eINSTANCE.getIBeXAttributeConstraint_Relation();

		/**
		 * The meta object literal for the '{@link IBeXLanguage.impl.IBeXAttributeParameterImpl <em>IBe XAttribute Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see IBeXLanguage.impl.IBeXAttributeParameterImpl
		 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXAttributeParameter()
		 * @generated
		 */
		EClass IBE_XATTRIBUTE_PARAMETER = eINSTANCE.getIBeXAttributeParameter();

		/**
		 * The meta object literal for the '{@link IBeXLanguage.impl.IBeXAttributeValueImpl <em>IBe XAttribute Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see IBeXLanguage.impl.IBeXAttributeValueImpl
		 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXAttributeValue()
		 * @generated
		 */
		EClass IBE_XATTRIBUTE_VALUE = eINSTANCE.getIBeXAttributeValue();

		/**
		 * The meta object literal for the '{@link IBeXLanguage.impl.IBeXAttributeExpressionImpl <em>IBe XAttribute Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see IBeXLanguage.impl.IBeXAttributeExpressionImpl
		 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXAttributeExpression()
		 * @generated
		 */
		EClass IBE_XATTRIBUTE_EXPRESSION = eINSTANCE.getIBeXAttributeExpression();

		/**
		 * The meta object literal for the '<em><b>Attribute</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XATTRIBUTE_EXPRESSION__ATTRIBUTE = eINSTANCE.getIBeXAttributeExpression_Attribute();

		/**
		 * The meta object literal for the '<em><b>Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XATTRIBUTE_EXPRESSION__NODE = eINSTANCE.getIBeXAttributeExpression_Node();

		/**
		 * The meta object literal for the '{@link IBeXLanguage.impl.IBeXConstantImpl <em>IBe XConstant</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see IBeXLanguage.impl.IBeXConstantImpl
		 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXConstant()
		 * @generated
		 */
		EClass IBE_XCONSTANT = eINSTANCE.getIBeXConstant();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IBE_XCONSTANT__VALUE = eINSTANCE.getIBeXConstant_Value();

		/**
		 * The meta object literal for the '<em><b>String Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IBE_XCONSTANT__STRING_VALUE = eINSTANCE.getIBeXConstant_StringValue();

		/**
		 * The meta object literal for the '{@link IBeXLanguage.impl.IBeXContextImpl <em>IBe XContext</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see IBeXLanguage.impl.IBeXContextImpl
		 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXContext()
		 * @generated
		 */
		EClass IBE_XCONTEXT = eINSTANCE.getIBeXContext();

		/**
		 * The meta object literal for the '{@link IBeXLanguage.impl.IBeXContextAlternativesImpl <em>IBe XContext Alternatives</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see IBeXLanguage.impl.IBeXContextAlternativesImpl
		 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXContextAlternatives()
		 * @generated
		 */
		EClass IBE_XCONTEXT_ALTERNATIVES = eINSTANCE.getIBeXContextAlternatives();

		/**
		 * The meta object literal for the '<em><b>Alternative Patterns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XCONTEXT_ALTERNATIVES__ALTERNATIVE_PATTERNS = eINSTANCE
				.getIBeXContextAlternatives_AlternativePatterns();

		/**
		 * The meta object literal for the '{@link IBeXLanguage.impl.IBeXContextPatternImpl <em>IBe XContext Pattern</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see IBeXLanguage.impl.IBeXContextPatternImpl
		 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXContextPattern()
		 * @generated
		 */
		EClass IBE_XCONTEXT_PATTERN = eINSTANCE.getIBeXContextPattern();

		/**
		 * The meta object literal for the '<em><b>Attribute Constraint</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XCONTEXT_PATTERN__ATTRIBUTE_CONSTRAINT = eINSTANCE.getIBeXContextPattern_AttributeConstraint();

		/**
		 * The meta object literal for the '<em><b>Injectivity Constraints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XCONTEXT_PATTERN__INJECTIVITY_CONSTRAINTS = eINSTANCE
				.getIBeXContextPattern_InjectivityConstraints();

		/**
		 * The meta object literal for the '<em><b>Invocations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XCONTEXT_PATTERN__INVOCATIONS = eINSTANCE.getIBeXContextPattern_Invocations();

		/**
		 * The meta object literal for the '<em><b>Local Edges</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XCONTEXT_PATTERN__LOCAL_EDGES = eINSTANCE.getIBeXContextPattern_LocalEdges();

		/**
		 * The meta object literal for the '<em><b>Local Nodes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XCONTEXT_PATTERN__LOCAL_NODES = eINSTANCE.getIBeXContextPattern_LocalNodes();

		/**
		 * The meta object literal for the '<em><b>Signature Nodes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XCONTEXT_PATTERN__SIGNATURE_NODES = eINSTANCE.getIBeXContextPattern_SignatureNodes();

		/**
		 * The meta object literal for the '<em><b>Csps</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XCONTEXT_PATTERN__CSPS = eINSTANCE.getIBeXContextPattern_Csps();

		/**
		 * The meta object literal for the '{@link IBeXLanguage.impl.IBeXCreatePatternImpl <em>IBe XCreate Pattern</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see IBeXLanguage.impl.IBeXCreatePatternImpl
		 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXCreatePattern()
		 * @generated
		 */
		EClass IBE_XCREATE_PATTERN = eINSTANCE.getIBeXCreatePattern();

		/**
		 * The meta object literal for the '<em><b>Attribute Assignments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XCREATE_PATTERN__ATTRIBUTE_ASSIGNMENTS = eINSTANCE.getIBeXCreatePattern_AttributeAssignments();

		/**
		 * The meta object literal for the '<em><b>Context Nodes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XCREATE_PATTERN__CONTEXT_NODES = eINSTANCE.getIBeXCreatePattern_ContextNodes();

		/**
		 * The meta object literal for the '<em><b>Created Edges</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XCREATE_PATTERN__CREATED_EDGES = eINSTANCE.getIBeXCreatePattern_CreatedEdges();

		/**
		 * The meta object literal for the '<em><b>Created Nodes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XCREATE_PATTERN__CREATED_NODES = eINSTANCE.getIBeXCreatePattern_CreatedNodes();

		/**
		 * The meta object literal for the '{@link IBeXLanguage.impl.IBeXDeletePatternImpl <em>IBe XDelete Pattern</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see IBeXLanguage.impl.IBeXDeletePatternImpl
		 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXDeletePattern()
		 * @generated
		 */
		EClass IBE_XDELETE_PATTERN = eINSTANCE.getIBeXDeletePattern();

		/**
		 * The meta object literal for the '<em><b>Context Nodes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XDELETE_PATTERN__CONTEXT_NODES = eINSTANCE.getIBeXDeletePattern_ContextNodes();

		/**
		 * The meta object literal for the '<em><b>Deleted Edges</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XDELETE_PATTERN__DELETED_EDGES = eINSTANCE.getIBeXDeletePattern_DeletedEdges();

		/**
		 * The meta object literal for the '<em><b>Deleted Nodes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XDELETE_PATTERN__DELETED_NODES = eINSTANCE.getIBeXDeletePattern_DeletedNodes();

		/**
		 * The meta object literal for the '{@link IBeXLanguage.impl.IBeXEdgeImpl <em>IBe XEdge</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see IBeXLanguage.impl.IBeXEdgeImpl
		 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXEdge()
		 * @generated
		 */
		EClass IBE_XEDGE = eINSTANCE.getIBeXEdge();

		/**
		 * The meta object literal for the '<em><b>Source Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XEDGE__SOURCE_NODE = eINSTANCE.getIBeXEdge_SourceNode();

		/**
		 * The meta object literal for the '<em><b>Target Node</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XEDGE__TARGET_NODE = eINSTANCE.getIBeXEdge_TargetNode();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XEDGE__TYPE = eINSTANCE.getIBeXEdge_Type();

		/**
		 * The meta object literal for the '{@link IBeXLanguage.impl.IBeXEnumLiteralImpl <em>IBe XEnum Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see IBeXLanguage.impl.IBeXEnumLiteralImpl
		 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXEnumLiteral()
		 * @generated
		 */
		EClass IBE_XENUM_LITERAL = eINSTANCE.getIBeXEnumLiteral();

		/**
		 * The meta object literal for the '<em><b>Literal</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XENUM_LITERAL__LITERAL = eINSTANCE.getIBeXEnumLiteral_Literal();

		/**
		 * The meta object literal for the '{@link IBeXLanguage.impl.IBeXNamedElementImpl <em>IBe XNamed Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see IBeXLanguage.impl.IBeXNamedElementImpl
		 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXNamedElement()
		 * @generated
		 */
		EClass IBE_XNAMED_ELEMENT = eINSTANCE.getIBeXNamedElement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IBE_XNAMED_ELEMENT__NAME = eINSTANCE.getIBeXNamedElement_Name();

		/**
		 * The meta object literal for the '{@link IBeXLanguage.impl.IBeXNodeImpl <em>IBe XNode</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see IBeXLanguage.impl.IBeXNodeImpl
		 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXNode()
		 * @generated
		 */
		EClass IBE_XNODE = eINSTANCE.getIBeXNode();

		/**
		 * The meta object literal for the '<em><b>Incoming Edges</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XNODE__INCOMING_EDGES = eINSTANCE.getIBeXNode_IncomingEdges();

		/**
		 * The meta object literal for the '<em><b>Outgoing Edges</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XNODE__OUTGOING_EDGES = eINSTANCE.getIBeXNode_OutgoingEdges();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XNODE__TYPE = eINSTANCE.getIBeXNode_Type();

		/**
		 * The meta object literal for the '{@link IBeXLanguage.impl.IBeXNodePairImpl <em>IBe XNode Pair</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see IBeXLanguage.impl.IBeXNodePairImpl
		 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXNodePair()
		 * @generated
		 */
		EClass IBE_XNODE_PAIR = eINSTANCE.getIBeXNodePair();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XNODE_PAIR__VALUES = eINSTANCE.getIBeXNodePair_Values();

		/**
		 * The meta object literal for the '{@link IBeXLanguage.impl.IBeXNodeToNodeMappingImpl <em>IBe XNode To Node Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see IBeXLanguage.impl.IBeXNodeToNodeMappingImpl
		 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXNodeToNodeMapping()
		 * @generated
		 */
		EClass IBE_XNODE_TO_NODE_MAPPING = eINSTANCE.getIBeXNodeToNodeMapping();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XNODE_TO_NODE_MAPPING__KEY = eINSTANCE.getIBeXNodeToNodeMapping_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XNODE_TO_NODE_MAPPING__VALUE = eINSTANCE.getIBeXNodeToNodeMapping_Value();

		/**
		 * The meta object literal for the '{@link IBeXLanguage.impl.IBeXPatternImpl <em>IBe XPattern</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see IBeXLanguage.impl.IBeXPatternImpl
		 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXPattern()
		 * @generated
		 */
		EClass IBE_XPATTERN = eINSTANCE.getIBeXPattern();

		/**
		 * The meta object literal for the '{@link IBeXLanguage.impl.IBeXPatternInvocationImpl <em>IBe XPattern Invocation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see IBeXLanguage.impl.IBeXPatternInvocationImpl
		 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXPatternInvocation()
		 * @generated
		 */
		EClass IBE_XPATTERN_INVOCATION = eINSTANCE.getIBeXPatternInvocation();

		/**
		 * The meta object literal for the '<em><b>Positive</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IBE_XPATTERN_INVOCATION__POSITIVE = eINSTANCE.getIBeXPatternInvocation_Positive();

		/**
		 * The meta object literal for the '<em><b>Invoked By</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XPATTERN_INVOCATION__INVOKED_BY = eINSTANCE.getIBeXPatternInvocation_InvokedBy();

		/**
		 * The meta object literal for the '<em><b>Invoked Pattern</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XPATTERN_INVOCATION__INVOKED_PATTERN = eINSTANCE.getIBeXPatternInvocation_InvokedPattern();

		/**
		 * The meta object literal for the '<em><b>Mapping</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XPATTERN_INVOCATION__MAPPING = eINSTANCE.getIBeXPatternInvocation_Mapping();

		/**
		 * The meta object literal for the '{@link IBeXLanguage.impl.IBeXPatternSetImpl <em>IBe XPattern Set</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see IBeXLanguage.impl.IBeXPatternSetImpl
		 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXPatternSet()
		 * @generated
		 */
		EClass IBE_XPATTERN_SET = eINSTANCE.getIBeXPatternSet();

		/**
		 * The meta object literal for the '<em><b>Context Patterns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XPATTERN_SET__CONTEXT_PATTERNS = eINSTANCE.getIBeXPatternSet_ContextPatterns();

		/**
		 * The meta object literal for the '<em><b>Create Patterns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XPATTERN_SET__CREATE_PATTERNS = eINSTANCE.getIBeXPatternSet_CreatePatterns();

		/**
		 * The meta object literal for the '<em><b>Delete Patterns</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XPATTERN_SET__DELETE_PATTERNS = eINSTANCE.getIBeXPatternSet_DeletePatterns();

		/**
		 * The meta object literal for the '{@link IBeXLanguage.impl.IBeXCSPImpl <em>IBe XCSP</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see IBeXLanguage.impl.IBeXCSPImpl
		 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXCSP()
		 * @generated
		 */
		EClass IBE_XCSP = eINSTANCE.getIBeXCSP();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IBE_XCSP__NAME = eINSTANCE.getIBeXCSP_Name();

		/**
		 * The meta object literal for the '<em><b>Package</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IBE_XCSP__PACKAGE = eINSTANCE.getIBeXCSP_Package();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XCSP__VALUES = eINSTANCE.getIBeXCSP_Values();

		/**
		 * The meta object literal for the '{@link IBeXLanguage.IBeXRelation <em>IBe XRelation</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see IBeXLanguage.IBeXRelation
		 * @see IBeXLanguage.impl.IBeXLanguagePackageImpl#getIBeXRelation()
		 * @generated
		 */
		EEnum IBE_XRELATION = eINSTANCE.getIBeXRelation();

	}

} //IBeXLanguagePackage
