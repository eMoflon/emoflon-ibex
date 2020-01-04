/**
 */
package language.impl;

import language.BindingType;
import language.DomainType;
import language.LanguageFactory;
import language.LanguagePackage;
import language.TGGAttributeConstraint;
import language.TGGAttributeConstraintAdornment;
import language.TGGAttributeConstraintDefinition;
import language.TGGAttributeConstraintDefinitionLibrary;
import language.TGGAttributeConstraintLibrary;
import language.TGGAttributeConstraintOperators;
import language.TGGAttributeConstraintParameterDefinition;
import language.TGGAttributeExpression;
import language.TGGAttributeVariable;
import language.TGGEnumExpression;
import language.TGGExpression;
import language.TGGInplaceAttributeExpression;
import language.TGGLiteralExpression;
import language.TGGNamedElement;
import language.TGGParamValue;
import language.TGGRule;
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

import language.repair.RepairPackage;

import language.repair.impl.RepairPackageImpl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class LanguagePackageImpl extends EPackageImpl implements LanguagePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggRuleElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggRuleNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggRuleCorrEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggRuleEdgeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nacEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggInplaceAttributeExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggAttributeConstraintLibraryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggAttributeConstraintEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggAttributeVariableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggAttributeConstraintDefinitionLibraryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggAttributeConstraintDefinitionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggAttributeConstraintParameterDefinitionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggAttributeConstraintAdornmentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggNamedElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggParamValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggLiteralExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggAttributeExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tggEnumExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum domainTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum bindingTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum tggAttributeConstraintOperatorsEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see language.LanguagePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private LanguagePackageImpl() {
		super(eNS_URI, LanguageFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link LanguagePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static LanguagePackage init() {
		if (isInited)
			return (LanguagePackage) EPackage.Registry.INSTANCE.getEPackage(LanguagePackage.eNS_URI);

		// Obtain or create and register package
		Object registeredLanguagePackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		LanguagePackageImpl theLanguagePackage = registeredLanguagePackage instanceof LanguagePackageImpl
				? (LanguagePackageImpl) registeredLanguagePackage
				: new LanguagePackageImpl();

		isInited = true;

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(RepairPackage.eNS_URI);
		RepairPackageImpl theRepairPackage = (RepairPackageImpl) (registeredPackage instanceof RepairPackageImpl
				? registeredPackage
				: RepairPackage.eINSTANCE);

		// Create package meta-data objects
		theLanguagePackage.createPackageContents();
		theRepairPackage.createPackageContents();

		// Initialize created meta-data
		theLanguagePackage.initializePackageContents();
		theRepairPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theLanguagePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(LanguagePackage.eNS_URI, theLanguagePackage);
		return theLanguagePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTGG() {
		return tggEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTGG_Src() {
		return (EReference) tggEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTGG_Trg() {
		return (EReference) tggEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTGG_Corr() {
		return (EReference) tggEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTGG_Rules() {
		return (EReference) tggEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTGG_AttributeConstraintDefinitionLibrary() {
		return (EReference) tggEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTGGRule() {
		return tggRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTGGRule_Refines() {
		return (EReference) tggRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTGGRule_Nacs() {
		return (EReference) tggRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTGGRule_Nodes() {
		return (EReference) tggRuleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTGGRule_Edges() {
		return (EReference) tggRuleEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTGGRule_AttributeConditionLibrary() {
		return (EReference) tggRuleEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTGGRule_Abstract() {
		return (EAttribute) tggRuleEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTGGRuleElement() {
		return tggRuleElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTGGRuleElement_DomainType() {
		return (EAttribute) tggRuleElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTGGRuleElement_BindingType() {
		return (EAttribute) tggRuleElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTGGRuleNode() {
		return tggRuleNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTGGRuleNode_IncomingEdges() {
		return (EReference) tggRuleNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTGGRuleNode_OutgoingEdges() {
		return (EReference) tggRuleNodeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTGGRuleNode_Type() {
		return (EReference) tggRuleNodeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTGGRuleNode_IncomingCorrsSource() {
		return (EReference) tggRuleNodeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTGGRuleNode_IncomingCorrsTarget() {
		return (EReference) tggRuleNodeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTGGRuleNode_AttrExpr() {
		return (EReference) tggRuleNodeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTGGRuleCorr() {
		return tggRuleCorrEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTGGRuleCorr_Source() {
		return (EReference) tggRuleCorrEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTGGRuleCorr_Target() {
		return (EReference) tggRuleCorrEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTGGRuleEdge() {
		return tggRuleEdgeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTGGRuleEdge_SrcNode() {
		return (EReference) tggRuleEdgeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTGGRuleEdge_TrgNode() {
		return (EReference) tggRuleEdgeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTGGRuleEdge_Type() {
		return (EReference) tggRuleEdgeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getNAC() {
		return nacEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getNAC_Nodes() {
		return (EReference) nacEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getNAC_Edges() {
		return (EReference) nacEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getNAC_AttributeConditionLibrary() {
		return (EReference) nacEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTGGInplaceAttributeExpression() {
		return tggInplaceAttributeExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTGGInplaceAttributeExpression_Attribute() {
		return (EReference) tggInplaceAttributeExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTGGInplaceAttributeExpression_ValueExpr() {
		return (EReference) tggInplaceAttributeExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTGGInplaceAttributeExpression_Operator() {
		return (EAttribute) tggInplaceAttributeExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTGGAttributeConstraintLibrary() {
		return tggAttributeConstraintLibraryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTGGAttributeConstraintLibrary_TggAttributeConstraints() {
		return (EReference) tggAttributeConstraintLibraryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTGGAttributeConstraintLibrary_ParameterValues() {
		return (EReference) tggAttributeConstraintLibraryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTGGAttributeConstraint() {
		return tggAttributeConstraintEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTGGAttributeConstraint_Definition() {
		return (EReference) tggAttributeConstraintEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTGGAttributeConstraint_Parameters() {
		return (EReference) tggAttributeConstraintEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTGGAttributeVariable() {
		return tggAttributeVariableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTGGAttributeVariable_Name() {
		return (EAttribute) tggAttributeVariableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTGGAttributeConstraintDefinitionLibrary() {
		return tggAttributeConstraintDefinitionLibraryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTGGAttributeConstraintDefinitionLibrary_TggAttributeConstraintDefinitions() {
		return (EReference) tggAttributeConstraintDefinitionLibraryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTGGAttributeConstraintDefinition() {
		return tggAttributeConstraintDefinitionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTGGAttributeConstraintDefinition_UserDefined() {
		return (EAttribute) tggAttributeConstraintDefinitionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTGGAttributeConstraintDefinition_ParameterDefinitions() {
		return (EReference) tggAttributeConstraintDefinitionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTGGAttributeConstraintDefinition_SyncAdornments() {
		return (EReference) tggAttributeConstraintDefinitionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTGGAttributeConstraintDefinition_GenAdornments() {
		return (EReference) tggAttributeConstraintDefinitionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTGGAttributeConstraintParameterDefinition() {
		return tggAttributeConstraintParameterDefinitionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTGGAttributeConstraintParameterDefinition_Type() {
		return (EReference) tggAttributeConstraintParameterDefinitionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTGGAttributeConstraintParameterDefinition_Name() {
		return (EAttribute) tggAttributeConstraintParameterDefinitionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTGGAttributeConstraintAdornment() {
		return tggAttributeConstraintAdornmentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTGGAttributeConstraintAdornment_Value() {
		return (EAttribute) tggAttributeConstraintAdornmentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTGGNamedElement() {
		return tggNamedElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTGGNamedElement_Name() {
		return (EAttribute) tggNamedElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTGGParamValue() {
		return tggParamValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTGGParamValue_ParameterDefinition() {
		return (EReference) tggParamValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTGGExpression() {
		return tggExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTGGLiteralExpression() {
		return tggLiteralExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getTGGLiteralExpression_Value() {
		return (EAttribute) tggLiteralExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTGGAttributeExpression() {
		return tggAttributeExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTGGAttributeExpression_ObjectVar() {
		return (EReference) tggAttributeExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTGGAttributeExpression_Attribute() {
		return (EReference) tggAttributeExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTGGEnumExpression() {
		return tggEnumExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTGGEnumExpression_Eenum() {
		return (EReference) tggEnumExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getTGGEnumExpression_Literal() {
		return (EReference) tggEnumExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getDomainType() {
		return domainTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getBindingType() {
		return bindingTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getTGGAttributeConstraintOperators() {
		return tggAttributeConstraintOperatorsEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LanguageFactory getLanguageFactory() {
		return (LanguageFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		tggEClass = createEClass(TGG);
		createEReference(tggEClass, TGG__SRC);
		createEReference(tggEClass, TGG__TRG);
		createEReference(tggEClass, TGG__CORR);
		createEReference(tggEClass, TGG__RULES);
		createEReference(tggEClass, TGG__ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY);

		tggRuleEClass = createEClass(TGG_RULE);
		createEReference(tggRuleEClass, TGG_RULE__REFINES);
		createEReference(tggRuleEClass, TGG_RULE__NACS);
		createEReference(tggRuleEClass, TGG_RULE__NODES);
		createEReference(tggRuleEClass, TGG_RULE__EDGES);
		createEReference(tggRuleEClass, TGG_RULE__ATTRIBUTE_CONDITION_LIBRARY);
		createEAttribute(tggRuleEClass, TGG_RULE__ABSTRACT);

		tggRuleElementEClass = createEClass(TGG_RULE_ELEMENT);
		createEAttribute(tggRuleElementEClass, TGG_RULE_ELEMENT__DOMAIN_TYPE);
		createEAttribute(tggRuleElementEClass, TGG_RULE_ELEMENT__BINDING_TYPE);

		tggRuleNodeEClass = createEClass(TGG_RULE_NODE);
		createEReference(tggRuleNodeEClass, TGG_RULE_NODE__INCOMING_EDGES);
		createEReference(tggRuleNodeEClass, TGG_RULE_NODE__OUTGOING_EDGES);
		createEReference(tggRuleNodeEClass, TGG_RULE_NODE__TYPE);
		createEReference(tggRuleNodeEClass, TGG_RULE_NODE__INCOMING_CORRS_SOURCE);
		createEReference(tggRuleNodeEClass, TGG_RULE_NODE__INCOMING_CORRS_TARGET);
		createEReference(tggRuleNodeEClass, TGG_RULE_NODE__ATTR_EXPR);

		tggRuleCorrEClass = createEClass(TGG_RULE_CORR);
		createEReference(tggRuleCorrEClass, TGG_RULE_CORR__SOURCE);
		createEReference(tggRuleCorrEClass, TGG_RULE_CORR__TARGET);

		tggRuleEdgeEClass = createEClass(TGG_RULE_EDGE);
		createEReference(tggRuleEdgeEClass, TGG_RULE_EDGE__SRC_NODE);
		createEReference(tggRuleEdgeEClass, TGG_RULE_EDGE__TRG_NODE);
		createEReference(tggRuleEdgeEClass, TGG_RULE_EDGE__TYPE);

		nacEClass = createEClass(NAC);
		createEReference(nacEClass, NAC__NODES);
		createEReference(nacEClass, NAC__EDGES);
		createEReference(nacEClass, NAC__ATTRIBUTE_CONDITION_LIBRARY);

		tggInplaceAttributeExpressionEClass = createEClass(TGG_INPLACE_ATTRIBUTE_EXPRESSION);
		createEReference(tggInplaceAttributeExpressionEClass, TGG_INPLACE_ATTRIBUTE_EXPRESSION__ATTRIBUTE);
		createEReference(tggInplaceAttributeExpressionEClass, TGG_INPLACE_ATTRIBUTE_EXPRESSION__VALUE_EXPR);
		createEAttribute(tggInplaceAttributeExpressionEClass, TGG_INPLACE_ATTRIBUTE_EXPRESSION__OPERATOR);

		tggAttributeConstraintLibraryEClass = createEClass(TGG_ATTRIBUTE_CONSTRAINT_LIBRARY);
		createEReference(tggAttributeConstraintLibraryEClass,
				TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__TGG_ATTRIBUTE_CONSTRAINTS);
		createEReference(tggAttributeConstraintLibraryEClass, TGG_ATTRIBUTE_CONSTRAINT_LIBRARY__PARAMETER_VALUES);

		tggAttributeConstraintEClass = createEClass(TGG_ATTRIBUTE_CONSTRAINT);
		createEReference(tggAttributeConstraintEClass, TGG_ATTRIBUTE_CONSTRAINT__DEFINITION);
		createEReference(tggAttributeConstraintEClass, TGG_ATTRIBUTE_CONSTRAINT__PARAMETERS);

		tggAttributeVariableEClass = createEClass(TGG_ATTRIBUTE_VARIABLE);
		createEAttribute(tggAttributeVariableEClass, TGG_ATTRIBUTE_VARIABLE__NAME);

		tggAttributeConstraintDefinitionLibraryEClass = createEClass(TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY);
		createEReference(tggAttributeConstraintDefinitionLibraryEClass,
				TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY__TGG_ATTRIBUTE_CONSTRAINT_DEFINITIONS);

		tggAttributeConstraintDefinitionEClass = createEClass(TGG_ATTRIBUTE_CONSTRAINT_DEFINITION);
		createEAttribute(tggAttributeConstraintDefinitionEClass, TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__USER_DEFINED);
		createEReference(tggAttributeConstraintDefinitionEClass,
				TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__PARAMETER_DEFINITIONS);
		createEReference(tggAttributeConstraintDefinitionEClass, TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__SYNC_ADORNMENTS);
		createEReference(tggAttributeConstraintDefinitionEClass, TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__GEN_ADORNMENTS);

		tggAttributeConstraintParameterDefinitionEClass = createEClass(TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_DEFINITION);
		createEReference(tggAttributeConstraintParameterDefinitionEClass,
				TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_DEFINITION__TYPE);
		createEAttribute(tggAttributeConstraintParameterDefinitionEClass,
				TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_DEFINITION__NAME);

		tggAttributeConstraintAdornmentEClass = createEClass(TGG_ATTRIBUTE_CONSTRAINT_ADORNMENT);
		createEAttribute(tggAttributeConstraintAdornmentEClass, TGG_ATTRIBUTE_CONSTRAINT_ADORNMENT__VALUE);

		tggNamedElementEClass = createEClass(TGG_NAMED_ELEMENT);
		createEAttribute(tggNamedElementEClass, TGG_NAMED_ELEMENT__NAME);

		tggParamValueEClass = createEClass(TGG_PARAM_VALUE);
		createEReference(tggParamValueEClass, TGG_PARAM_VALUE__PARAMETER_DEFINITION);

		tggExpressionEClass = createEClass(TGG_EXPRESSION);

		tggLiteralExpressionEClass = createEClass(TGG_LITERAL_EXPRESSION);
		createEAttribute(tggLiteralExpressionEClass, TGG_LITERAL_EXPRESSION__VALUE);

		tggAttributeExpressionEClass = createEClass(TGG_ATTRIBUTE_EXPRESSION);
		createEReference(tggAttributeExpressionEClass, TGG_ATTRIBUTE_EXPRESSION__OBJECT_VAR);
		createEReference(tggAttributeExpressionEClass, TGG_ATTRIBUTE_EXPRESSION__ATTRIBUTE);

		tggEnumExpressionEClass = createEClass(TGG_ENUM_EXPRESSION);
		createEReference(tggEnumExpressionEClass, TGG_ENUM_EXPRESSION__EENUM);
		createEReference(tggEnumExpressionEClass, TGG_ENUM_EXPRESSION__LITERAL);

		// Create enums
		domainTypeEEnum = createEEnum(DOMAIN_TYPE);
		bindingTypeEEnum = createEEnum(BINDING_TYPE);
		tggAttributeConstraintOperatorsEEnum = createEEnum(TGG_ATTRIBUTE_CONSTRAINT_OPERATORS);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		RepairPackage theRepairPackage = (RepairPackage) EPackage.Registry.INSTANCE.getEPackage(RepairPackage.eNS_URI);

		// Add subpackages
		getESubpackages().add(theRepairPackage);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		tggEClass.getESuperTypes().add(this.getTGGNamedElement());
		tggRuleEClass.getESuperTypes().add(this.getTGGNamedElement());
		tggRuleElementEClass.getESuperTypes().add(this.getTGGNamedElement());
		tggRuleNodeEClass.getESuperTypes().add(this.getTGGRuleElement());
		tggRuleCorrEClass.getESuperTypes().add(this.getTGGRuleNode());
		tggRuleEdgeEClass.getESuperTypes().add(this.getTGGRuleElement());
		nacEClass.getESuperTypes().add(this.getTGGNamedElement());
		tggAttributeVariableEClass.getESuperTypes().add(this.getTGGParamValue());
		tggAttributeConstraintDefinitionEClass.getESuperTypes().add(this.getTGGNamedElement());
		tggExpressionEClass.getESuperTypes().add(this.getTGGParamValue());
		tggLiteralExpressionEClass.getESuperTypes().add(this.getTGGExpression());
		tggAttributeExpressionEClass.getESuperTypes().add(this.getTGGExpression());
		tggEnumExpressionEClass.getESuperTypes().add(this.getTGGExpression());

		// Initialize classes, features, and operations; add parameters
		initEClass(tggEClass, language.TGG.class, "TGG", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGG_Src(), ecorePackage.getEPackage(), null, "src", null, 0, -1, language.TGG.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGG_Trg(), ecorePackage.getEPackage(), null, "trg", null, 0, -1, language.TGG.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGG_Corr(), ecorePackage.getEPackage(), null, "corr", null, 0, 1, language.TGG.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGG_Rules(), this.getTGGRule(), null, "rules", null, 0, -1, language.TGG.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getTGG_AttributeConstraintDefinitionLibrary(), this.getTGGAttributeConstraintDefinitionLibrary(),
				null, "attributeConstraintDefinitionLibrary", null, 1, 1, language.TGG.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(tggRuleEClass, TGGRule.class, "TGGRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGRule_Refines(), this.getTGGRule(), null, "refines", null, 0, -1, TGGRule.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRule_Nacs(), this.getNAC(), null, "nacs", null, 0, -1, TGGRule.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getTGGRule_Nodes(), this.getTGGRuleNode(), null, "nodes", null, 0, -1, TGGRule.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRule_Edges(), this.getTGGRuleEdge(), null, "edges", null, 0, -1, TGGRule.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRule_AttributeConditionLibrary(), this.getTGGAttributeConstraintLibrary(), null,
				"attributeConditionLibrary", null, 1, 1, TGGRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTGGRule_Abstract(), ecorePackage.getEBoolean(), "abstract", null, 0, 1, TGGRule.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggRuleElementEClass, TGGRuleElement.class, "TGGRuleElement", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTGGRuleElement_DomainType(), this.getDomainType(), "domainType", null, 0, 1,
				TGGRuleElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getTGGRuleElement_BindingType(), this.getBindingType(), "bindingType", null, 0, 1,
				TGGRuleElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(tggRuleNodeEClass, TGGRuleNode.class, "TGGRuleNode", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGRuleNode_IncomingEdges(), this.getTGGRuleEdge(), this.getTGGRuleEdge_TrgNode(),
				"incomingEdges", null, 0, -1, TGGRuleNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRuleNode_OutgoingEdges(), this.getTGGRuleEdge(), this.getTGGRuleEdge_SrcNode(),
				"outgoingEdges", null, 0, -1, TGGRuleNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRuleNode_Type(), ecorePackage.getEClass(), null, "type", null, 0, 1, TGGRuleNode.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRuleNode_IncomingCorrsSource(), this.getTGGRuleCorr(), this.getTGGRuleCorr_Source(),
				"incomingCorrsSource", null, 0, -1, TGGRuleNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRuleNode_IncomingCorrsTarget(), this.getTGGRuleCorr(), this.getTGGRuleCorr_Target(),
				"incomingCorrsTarget", null, 0, -1, TGGRuleNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRuleNode_AttrExpr(), this.getTGGInplaceAttributeExpression(), null, "attrExpr", null, 0,
				-1, TGGRuleNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggRuleCorrEClass, TGGRuleCorr.class, "TGGRuleCorr", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGRuleCorr_Source(), this.getTGGRuleNode(), this.getTGGRuleNode_IncomingCorrsSource(),
				"source", null, 0, 1, TGGRuleCorr.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRuleCorr_Target(), this.getTGGRuleNode(), this.getTGGRuleNode_IncomingCorrsTarget(),
				"target", null, 0, 1, TGGRuleCorr.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggRuleEdgeEClass, TGGRuleEdge.class, "TGGRuleEdge", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGRuleEdge_SrcNode(), this.getTGGRuleNode(), this.getTGGRuleNode_OutgoingEdges(), "srcNode",
				null, 0, 1, TGGRuleEdge.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRuleEdge_TrgNode(), this.getTGGRuleNode(), this.getTGGRuleNode_IncomingEdges(), "trgNode",
				null, 0, 1, TGGRuleEdge.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGRuleEdge_Type(), ecorePackage.getEReference(), null, "type", null, 0, 1, TGGRuleEdge.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(nacEClass, language.NAC.class, "NAC", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getNAC_Nodes(), this.getTGGRuleNode(), null, "nodes", null, 0, -1, language.NAC.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNAC_Edges(), this.getTGGRuleEdge(), null, "edges", null, 0, -1, language.NAC.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNAC_AttributeConditionLibrary(), this.getTGGAttributeConstraintLibrary(), null,
				"attributeConditionLibrary", null, 1, 1, language.NAC.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggInplaceAttributeExpressionEClass, TGGInplaceAttributeExpression.class,
				"TGGInplaceAttributeExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGInplaceAttributeExpression_Attribute(), ecorePackage.getEAttribute(), null, "attribute",
				null, 1, 1, TGGInplaceAttributeExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGInplaceAttributeExpression_ValueExpr(), this.getTGGExpression(), null, "valueExpr", null,
				0, 1, TGGInplaceAttributeExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTGGInplaceAttributeExpression_Operator(), this.getTGGAttributeConstraintOperators(),
				"operator", "EQUAL", 1, 1, TGGInplaceAttributeExpression.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggAttributeConstraintLibraryEClass, TGGAttributeConstraintLibrary.class,
				"TGGAttributeConstraintLibrary", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGAttributeConstraintLibrary_TggAttributeConstraints(), this.getTGGAttributeConstraint(),
				null, "tggAttributeConstraints", null, 0, -1, TGGAttributeConstraintLibrary.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getTGGAttributeConstraintLibrary_ParameterValues(), this.getTGGParamValue(), null,
				"parameterValues", null, 0, -1, TGGAttributeConstraintLibrary.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggAttributeConstraintEClass, TGGAttributeConstraint.class, "TGGAttributeConstraint", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGAttributeConstraint_Definition(), this.getTGGAttributeConstraintDefinition(), null,
				"definition", null, 0, 1, TGGAttributeConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGAttributeConstraint_Parameters(), this.getTGGParamValue(), null, "parameters", null, 0, -1,
				TGGAttributeConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggAttributeVariableEClass, TGGAttributeVariable.class, "TGGAttributeVariable", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTGGAttributeVariable_Name(), ecorePackage.getEString(), "name", null, 0, 1,
				TGGAttributeVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggAttributeConstraintDefinitionLibraryEClass, TGGAttributeConstraintDefinitionLibrary.class,
				"TGGAttributeConstraintDefinitionLibrary", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGAttributeConstraintDefinitionLibrary_TggAttributeConstraintDefinitions(),
				this.getTGGAttributeConstraintDefinition(), null, "tggAttributeConstraintDefinitions", null, 0, -1,
				TGGAttributeConstraintDefinitionLibrary.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggAttributeConstraintDefinitionEClass, TGGAttributeConstraintDefinition.class,
				"TGGAttributeConstraintDefinition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTGGAttributeConstraintDefinition_UserDefined(), ecorePackage.getEBoolean(), "userDefined",
				"true", 0, 1, TGGAttributeConstraintDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGAttributeConstraintDefinition_ParameterDefinitions(),
				this.getTGGAttributeConstraintParameterDefinition(), null, "parameterDefinitions", null, 0, -1,
				TGGAttributeConstraintDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGAttributeConstraintDefinition_SyncAdornments(), this.getTGGAttributeConstraintAdornment(),
				null, "syncAdornments", null, 0, -1, TGGAttributeConstraintDefinition.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getTGGAttributeConstraintDefinition_GenAdornments(), this.getTGGAttributeConstraintAdornment(),
				null, "genAdornments", null, 0, -1, TGGAttributeConstraintDefinition.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggAttributeConstraintParameterDefinitionEClass, TGGAttributeConstraintParameterDefinition.class,
				"TGGAttributeConstraintParameterDefinition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGAttributeConstraintParameterDefinition_Type(), ecorePackage.getEDataType(), null, "type",
				null, 0, 1, TGGAttributeConstraintParameterDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTGGAttributeConstraintParameterDefinition_Name(), ecorePackage.getEString(), "name", null, 0,
				1, TGGAttributeConstraintParameterDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggAttributeConstraintAdornmentEClass, TGGAttributeConstraintAdornment.class,
				"TGGAttributeConstraintAdornment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTGGAttributeConstraintAdornment_Value(), ecorePackage.getEString(), "value", null, 0, -1,
				TGGAttributeConstraintAdornment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggNamedElementEClass, TGGNamedElement.class, "TGGNamedElement", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTGGNamedElement_Name(), ecorePackage.getEString(), "name", null, 0, 1, TGGNamedElement.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggParamValueEClass, TGGParamValue.class, "TGGParamValue", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGParamValue_ParameterDefinition(), this.getTGGAttributeConstraintParameterDefinition(),
				null, "parameterDefinition", null, 0, 1, TGGParamValue.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggExpressionEClass, TGGExpression.class, "TGGExpression", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(tggLiteralExpressionEClass, TGGLiteralExpression.class, "TGGLiteralExpression", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTGGLiteralExpression_Value(), ecorePackage.getEString(), "value", null, 0, 1,
				TGGLiteralExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggAttributeExpressionEClass, TGGAttributeExpression.class, "TGGAttributeExpression", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGAttributeExpression_ObjectVar(), this.getTGGRuleNode(), null, "objectVar", null, 0, 1,
				TGGAttributeExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGAttributeExpression_Attribute(), ecorePackage.getEAttribute(), null, "attribute", null, 0,
				1, TGGAttributeExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tggEnumExpressionEClass, TGGEnumExpression.class, "TGGEnumExpression", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTGGEnumExpression_Eenum(), ecorePackage.getEEnum(), null, "eenum", null, 0, 1,
				TGGEnumExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTGGEnumExpression_Literal(), ecorePackage.getEEnumLiteral(), null, "literal", null, 0, 1,
				TGGEnumExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(domainTypeEEnum, DomainType.class, "DomainType");
		addEEnumLiteral(domainTypeEEnum, DomainType.SRC);
		addEEnumLiteral(domainTypeEEnum, DomainType.TRG);
		addEEnumLiteral(domainTypeEEnum, DomainType.CORR);

		initEEnum(bindingTypeEEnum, BindingType.class, "BindingType");
		addEEnumLiteral(bindingTypeEEnum, BindingType.CONTEXT);
		addEEnumLiteral(bindingTypeEEnum, BindingType.CREATE);
		addEEnumLiteral(bindingTypeEEnum, BindingType.DELETE);
		addEEnumLiteral(bindingTypeEEnum, BindingType.NEGATIVE);
		addEEnumLiteral(bindingTypeEEnum, BindingType.RELAXED);

		initEEnum(tggAttributeConstraintOperatorsEEnum, TGGAttributeConstraintOperators.class,
				"TGGAttributeConstraintOperators");
		addEEnumLiteral(tggAttributeConstraintOperatorsEEnum, TGGAttributeConstraintOperators.EQUAL);
		addEEnumLiteral(tggAttributeConstraintOperatorsEEnum, TGGAttributeConstraintOperators.UNEQUAL);
		addEEnumLiteral(tggAttributeConstraintOperatorsEEnum, TGGAttributeConstraintOperators.GR_EQUAL);
		addEEnumLiteral(tggAttributeConstraintOperatorsEEnum, TGGAttributeConstraintOperators.LE_EQUAL);
		addEEnumLiteral(tggAttributeConstraintOperatorsEEnum, TGGAttributeConstraintOperators.GREATER);
		addEEnumLiteral(tggAttributeConstraintOperatorsEEnum, TGGAttributeConstraintOperators.LESSER);

		// Create resource
		createResource(eNS_URI);
	}

} //LanguagePackageImpl
