/**
 */
package org.emoflon.ibex.patternmodel.IBeXPatternModel.impl;

import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticAttribute;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticConstraint;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticExpression;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticValue;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticValueLiteral;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttribute;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeAssignment;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeConstraint;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeExpression;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeParameter;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeValue;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXBinaryExpression;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXBinaryOperator;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCSP;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXConstant;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXConstraint;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextAlternatives;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCreatePattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDeletePattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjointAttribute;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjointContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDistributionRange;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDistributionType;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEdge;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEdgeSet;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEnumLiteral;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXInjectivityConstraint;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXInterdependentAttributes;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXInterdependentInjectivityConstraints;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXMatchCount;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXModel;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNamedElement;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNodeSet;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXParameter;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternInvocation;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelFactory;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternSet;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXProbability;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXProbabilityDistribution;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRelation;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRuleSet;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXStochasticAttributeValue;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXUnaryExpression;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXUnaryOperator;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBexDisjointInjectivityConstraint;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class IBeXPatternModelPackageImpl extends EPackageImpl implements IBeXPatternModelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXModelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXPatternSetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXRuleSetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXNodeSetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXEdgeSetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXNamedElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXPatternEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXContextEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXContextAlternativesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXContextPatternEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXCreatePatternEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXDeletePatternEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXEdgeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXParameterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXAttributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXAttributeAssignmentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXAttributeConstraintEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXAttributeValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXAttributeParameterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXConstantEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXEnumLiteralEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXAttributeExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXStochasticAttributeValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXArithmeticValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXInjectivityConstraintEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXPatternInvocationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXNodeToNodeMappingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXCSPEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXProbabilityEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXArithmeticConstraintEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXArithmeticExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXUnaryExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXBinaryExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXArithmeticValueLiteralEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXProbabilityDistributionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXArithmeticAttributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXMatchCountEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXConstraintEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXDisjointContextPatternEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBexDisjointInjectivityConstraintEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXDisjointAttributeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXInterdependentInjectivityConstraintsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBeXInterdependentAttributesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum iBeXRelationEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum iBeXBinaryOperatorEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum iBeXUnaryOperatorEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum iBeXDistributionTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum iBeXDistributionRangeEEnum = null;

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
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private IBeXPatternModelPackageImpl() {
		super(eNS_URI, IBeXPatternModelFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link IBeXPatternModelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static IBeXPatternModelPackage init() {
		if (isInited)
			return (IBeXPatternModelPackage) EPackage.Registry.INSTANCE.getEPackage(IBeXPatternModelPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredIBeXPatternModelPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		IBeXPatternModelPackageImpl theIBeXPatternModelPackage = registeredIBeXPatternModelPackage instanceof IBeXPatternModelPackageImpl
				? (IBeXPatternModelPackageImpl) registeredIBeXPatternModelPackage
				: new IBeXPatternModelPackageImpl();

		isInited = true;

		// Create package meta-data objects
		theIBeXPatternModelPackage.createPackageContents();

		// Initialize created meta-data
		theIBeXPatternModelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theIBeXPatternModelPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(IBeXPatternModelPackage.eNS_URI, theIBeXPatternModelPackage);
		return theIBeXPatternModelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXModel() {
		return iBeXModelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXModel_PatternSet() {
		return (EReference) iBeXModelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXModel_RuleSet() {
		return (EReference) iBeXModelEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXModel_NodeSet() {
		return (EReference) iBeXModelEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXModel_EdgeSet() {
		return (EReference) iBeXModelEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXPatternSet() {
		return iBeXPatternSetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXPatternSet_ContextPatterns() {
		return (EReference) iBeXPatternSetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXRuleSet() {
		return iBeXRuleSetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXRuleSet_Rules() {
		return (EReference) iBeXRuleSetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXNodeSet() {
		return iBeXNodeSetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXNodeSet_Nodes() {
		return (EReference) iBeXNodeSetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXEdgeSet() {
		return iBeXEdgeSetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXEdgeSet_Edges() {
		return (EReference) iBeXEdgeSetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXNamedElement() {
		return iBeXNamedElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIBeXNamedElement_Name() {
		return (EAttribute) iBeXNamedElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXPattern() {
		return iBeXPatternEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXContext() {
		return iBeXContextEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXContext_ApiPatternDependencies() {
		return (EReference) iBeXContextEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXContextAlternatives() {
		return iBeXContextAlternativesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXContextAlternatives_AlternativePatterns() {
		return (EReference) iBeXContextAlternativesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXContextAlternatives_Context() {
		return (EReference) iBeXContextAlternativesEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXContextPattern() {
		return iBeXContextPatternEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIBeXContextPattern_Documentation() {
		return (EAttribute) iBeXContextPatternEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXContextPattern_AttributeConstraint() {
		return (EReference) iBeXContextPatternEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXContextPattern_ArithmeticConstraints() {
		return (EReference) iBeXContextPatternEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXContextPattern_InjectivityConstraints() {
		return (EReference) iBeXContextPatternEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXContextPattern_Invocations() {
		return (EReference) iBeXContextPatternEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXContextPattern_LocalEdges() {
		return (EReference) iBeXContextPatternEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXContextPattern_LocalNodes() {
		return (EReference) iBeXContextPatternEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXContextPattern_SignatureNodes() {
		return (EReference) iBeXContextPatternEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXContextPattern_Csps() {
		return (EReference) iBeXContextPatternEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXContextPattern_Parameters() {
		return (EReference) iBeXContextPatternEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIBeXContextPattern_OptimizedDisjoint() {
		return (EAttribute) iBeXContextPatternEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIBeXContextPattern_Subpattern() {
		return (EAttribute) iBeXContextPatternEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXCreatePattern() {
		return iBeXCreatePatternEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXCreatePattern_AttributeAssignments() {
		return (EReference) iBeXCreatePatternEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXCreatePattern_ContextNodes() {
		return (EReference) iBeXCreatePatternEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXCreatePattern_CreatedEdges() {
		return (EReference) iBeXCreatePatternEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXCreatePattern_CreatedNodes() {
		return (EReference) iBeXCreatePatternEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXDeletePattern() {
		return iBeXDeletePatternEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXDeletePattern_ContextNodes() {
		return (EReference) iBeXDeletePatternEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXDeletePattern_DeletedEdges() {
		return (EReference) iBeXDeletePatternEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXDeletePattern_DeletedNodes() {
		return (EReference) iBeXDeletePatternEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXNode() {
		return iBeXNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXNode_IncomingEdges() {
		return (EReference) iBeXNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXNode_OutgoingEdges() {
		return (EReference) iBeXNodeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXNode_Type() {
		return (EReference) iBeXNodeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXEdge() {
		return iBeXEdgeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXEdge_SourceNode() {
		return (EReference) iBeXEdgeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXEdge_TargetNode() {
		return (EReference) iBeXEdgeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXEdge_Type() {
		return (EReference) iBeXEdgeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXParameter() {
		return iBeXParameterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXParameter_Type() {
		return (EReference) iBeXParameterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXAttribute() {
		return iBeXAttributeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXAttributeAssignment() {
		return iBeXAttributeAssignmentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXAttributeAssignment_Value() {
		return (EReference) iBeXAttributeAssignmentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXAttributeAssignment_Node() {
		return (EReference) iBeXAttributeAssignmentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXAttributeAssignment_Type() {
		return (EReference) iBeXAttributeAssignmentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXAttributeConstraint() {
		return iBeXAttributeConstraintEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIBeXAttributeConstraint_Relation() {
		return (EAttribute) iBeXAttributeConstraintEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXAttributeConstraint_Lhs() {
		return (EReference) iBeXAttributeConstraintEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXAttributeConstraint_Rhs() {
		return (EReference) iBeXAttributeConstraintEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXAttributeValue() {
		return iBeXAttributeValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXAttributeParameter() {
		return iBeXAttributeParameterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXConstant() {
		return iBeXConstantEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIBeXConstant_Value() {
		return (EAttribute) iBeXConstantEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIBeXConstant_StringValue() {
		return (EAttribute) iBeXConstantEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXEnumLiteral() {
		return iBeXEnumLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXEnumLiteral_Literal() {
		return (EReference) iBeXEnumLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXAttributeExpression() {
		return iBeXAttributeExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXAttributeExpression_Attribute() {
		return (EReference) iBeXAttributeExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXAttributeExpression_Node() {
		return (EReference) iBeXAttributeExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXStochasticAttributeValue() {
		return iBeXStochasticAttributeValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIBeXStochasticAttributeValue_Range() {
		return (EAttribute) iBeXStochasticAttributeValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXStochasticAttributeValue_Function() {
		return (EReference) iBeXStochasticAttributeValueEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXArithmeticValue() {
		return iBeXArithmeticValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXArithmeticValue_Expression() {
		return (EReference) iBeXArithmeticValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXInjectivityConstraint() {
		return iBeXInjectivityConstraintEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXInjectivityConstraint_Values() {
		return (EReference) iBeXInjectivityConstraintEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXPatternInvocation() {
		return iBeXPatternInvocationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIBeXPatternInvocation_Positive() {
		return (EAttribute) iBeXPatternInvocationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXPatternInvocation_InvokedBy() {
		return (EReference) iBeXPatternInvocationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXPatternInvocation_InvokedPattern() {
		return (EReference) iBeXPatternInvocationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXPatternInvocation_Mapping() {
		return (EReference) iBeXPatternInvocationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXNodeToNodeMapping() {
		return iBeXNodeToNodeMappingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXNodeToNodeMapping_Key() {
		return (EReference) iBeXNodeToNodeMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXNodeToNodeMapping_Value() {
		return (EReference) iBeXNodeToNodeMappingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXCSP() {
		return iBeXCSPEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIBeXCSP_Name() {
		return (EAttribute) iBeXCSPEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIBeXCSP_Package() {
		return (EAttribute) iBeXCSPEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXCSP_Values() {
		return (EReference) iBeXCSPEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXRule() {
		return iBeXRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIBeXRule_Documentation() {
		return (EAttribute) iBeXRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXRule_Lhs() {
		return (EReference) iBeXRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXRule_Rhs() {
		return (EReference) iBeXRuleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXRule_Create() {
		return (EReference) iBeXRuleEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXRule_Delete() {
		return (EReference) iBeXRuleEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXRule_Parameters() {
		return (EReference) iBeXRuleEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXRule_ArithmeticConstraints() {
		return (EReference) iBeXRuleEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXRule_Probability() {
		return (EReference) iBeXRuleEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXProbability() {
		return iBeXProbabilityEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXProbability_Distribution() {
		return (EReference) iBeXProbabilityEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXProbability_Parameter() {
		return (EReference) iBeXProbabilityEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXArithmeticConstraint() {
		return iBeXArithmeticConstraintEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXArithmeticConstraint_Lhs() {
		return (EReference) iBeXArithmeticConstraintEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIBeXArithmeticConstraint_Relation() {
		return (EAttribute) iBeXArithmeticConstraintEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXArithmeticConstraint_Rhs() {
		return (EReference) iBeXArithmeticConstraintEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXArithmeticExpression() {
		return iBeXArithmeticExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXUnaryExpression() {
		return iBeXUnaryExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXUnaryExpression_Operand() {
		return (EReference) iBeXUnaryExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIBeXUnaryExpression_Operator() {
		return (EAttribute) iBeXUnaryExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIBeXUnaryExpression_Negative() {
		return (EAttribute) iBeXUnaryExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXBinaryExpression() {
		return iBeXBinaryExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXBinaryExpression_Left() {
		return (EReference) iBeXBinaryExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXBinaryExpression_Right() {
		return (EReference) iBeXBinaryExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIBeXBinaryExpression_Operator() {
		return (EAttribute) iBeXBinaryExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXArithmeticValueLiteral() {
		return iBeXArithmeticValueLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIBeXArithmeticValueLiteral_Value() {
		return (EAttribute) iBeXArithmeticValueLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXProbabilityDistribution() {
		return iBeXProbabilityDistributionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXProbabilityDistribution_Mean() {
		return (EReference) iBeXProbabilityDistributionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXProbabilityDistribution_Stddev() {
		return (EReference) iBeXProbabilityDistributionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIBeXProbabilityDistribution_Type() {
		return (EAttribute) iBeXProbabilityDistributionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXArithmeticAttribute() {
		return iBeXArithmeticAttributeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXArithmeticAttribute_Attribute() {
		return (EReference) iBeXArithmeticAttributeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIBeXArithmeticAttribute_Negative() {
		return (EAttribute) iBeXArithmeticAttributeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXMatchCount() {
		return iBeXMatchCountEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXMatchCount_Invocation() {
		return (EReference) iBeXMatchCountEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXConstraint() {
		return iBeXConstraintEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXDisjointContextPattern() {
		return iBeXDisjointContextPatternEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXDisjointContextPattern_Subpatterns() {
		return (EReference) iBeXDisjointContextPatternEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXDisjointContextPattern_InjectivityConstraints() {
		return (EReference) iBeXDisjointContextPatternEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXDisjointContextPattern_AttributeConstraints() {
		return (EReference) iBeXDisjointContextPatternEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXDisjointContextPattern_NonOptimizedPattern() {
		return (EReference) iBeXDisjointContextPatternEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBexDisjointInjectivityConstraint() {
		return iBexDisjointInjectivityConstraintEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBexDisjointInjectivityConstraint_Pattern1() {
		return (EReference) iBexDisjointInjectivityConstraintEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBexDisjointInjectivityConstraint_Pattern2() {
		return (EReference) iBexDisjointInjectivityConstraintEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBexDisjointInjectivityConstraint_Node1() {
		return (EReference) iBexDisjointInjectivityConstraintEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBexDisjointInjectivityConstraint_Node2() {
		return (EReference) iBexDisjointInjectivityConstraintEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXDisjointAttribute() {
		return iBeXDisjointAttributeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXDisjointAttribute_TargetPattern() {
		return (EReference) iBeXDisjointAttributeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXDisjointAttribute_SourcePattern() {
		return (EReference) iBeXDisjointAttributeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXDisjointAttribute_DisjointAttribute() {
		return (EReference) iBeXDisjointAttributeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXInterdependentInjectivityConstraints() {
		return iBeXInterdependentInjectivityConstraintsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXInterdependentInjectivityConstraints_InjectivityConstraints() {
		return (EReference) iBeXInterdependentInjectivityConstraintsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXInterdependentInjectivityConstraints_Patterns() {
		return (EReference) iBeXInterdependentInjectivityConstraintsEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXInterdependentInjectivityConstraints_AttributeConstraints() {
		return (EReference) iBeXInterdependentInjectivityConstraintsEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBeXInterdependentAttributes() {
		return iBeXInterdependentAttributesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXInterdependentAttributes_Attributes() {
		return (EReference) iBeXInterdependentAttributesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXInterdependentAttributes_InterdependentPatterns() {
		return (EReference) iBeXInterdependentAttributesEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXInterdependentAttributes_InjectivityConstraints() {
		return (EReference) iBeXInterdependentAttributesEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getIBeXRelation() {
		return iBeXRelationEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getIBeXBinaryOperator() {
		return iBeXBinaryOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getIBeXUnaryOperator() {
		return iBeXUnaryOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getIBeXDistributionType() {
		return iBeXDistributionTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getIBeXDistributionRange() {
		return iBeXDistributionRangeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXPatternModelFactory getIBeXPatternModelFactory() {
		return (IBeXPatternModelFactory) getEFactoryInstance();
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
		iBeXModelEClass = createEClass(IBE_XMODEL);
		createEReference(iBeXModelEClass, IBE_XMODEL__PATTERN_SET);
		createEReference(iBeXModelEClass, IBE_XMODEL__RULE_SET);
		createEReference(iBeXModelEClass, IBE_XMODEL__NODE_SET);
		createEReference(iBeXModelEClass, IBE_XMODEL__EDGE_SET);

		iBeXPatternSetEClass = createEClass(IBE_XPATTERN_SET);
		createEReference(iBeXPatternSetEClass, IBE_XPATTERN_SET__CONTEXT_PATTERNS);

		iBeXRuleSetEClass = createEClass(IBE_XRULE_SET);
		createEReference(iBeXRuleSetEClass, IBE_XRULE_SET__RULES);

		iBeXNodeSetEClass = createEClass(IBE_XNODE_SET);
		createEReference(iBeXNodeSetEClass, IBE_XNODE_SET__NODES);

		iBeXEdgeSetEClass = createEClass(IBE_XEDGE_SET);
		createEReference(iBeXEdgeSetEClass, IBE_XEDGE_SET__EDGES);

		iBeXNamedElementEClass = createEClass(IBE_XNAMED_ELEMENT);
		createEAttribute(iBeXNamedElementEClass, IBE_XNAMED_ELEMENT__NAME);

		iBeXPatternEClass = createEClass(IBE_XPATTERN);

		iBeXContextEClass = createEClass(IBE_XCONTEXT);
		createEReference(iBeXContextEClass, IBE_XCONTEXT__API_PATTERN_DEPENDENCIES);

		iBeXContextAlternativesEClass = createEClass(IBE_XCONTEXT_ALTERNATIVES);
		createEReference(iBeXContextAlternativesEClass, IBE_XCONTEXT_ALTERNATIVES__ALTERNATIVE_PATTERNS);
		createEReference(iBeXContextAlternativesEClass, IBE_XCONTEXT_ALTERNATIVES__CONTEXT);

		iBeXContextPatternEClass = createEClass(IBE_XCONTEXT_PATTERN);
		createEAttribute(iBeXContextPatternEClass, IBE_XCONTEXT_PATTERN__DOCUMENTATION);
		createEReference(iBeXContextPatternEClass, IBE_XCONTEXT_PATTERN__ATTRIBUTE_CONSTRAINT);
		createEReference(iBeXContextPatternEClass, IBE_XCONTEXT_PATTERN__ARITHMETIC_CONSTRAINTS);
		createEReference(iBeXContextPatternEClass, IBE_XCONTEXT_PATTERN__INJECTIVITY_CONSTRAINTS);
		createEReference(iBeXContextPatternEClass, IBE_XCONTEXT_PATTERN__INVOCATIONS);
		createEReference(iBeXContextPatternEClass, IBE_XCONTEXT_PATTERN__LOCAL_EDGES);
		createEReference(iBeXContextPatternEClass, IBE_XCONTEXT_PATTERN__LOCAL_NODES);
		createEReference(iBeXContextPatternEClass, IBE_XCONTEXT_PATTERN__SIGNATURE_NODES);
		createEReference(iBeXContextPatternEClass, IBE_XCONTEXT_PATTERN__CSPS);
		createEReference(iBeXContextPatternEClass, IBE_XCONTEXT_PATTERN__PARAMETERS);
		createEAttribute(iBeXContextPatternEClass, IBE_XCONTEXT_PATTERN__OPTIMIZED_DISJOINT);
		createEAttribute(iBeXContextPatternEClass, IBE_XCONTEXT_PATTERN__SUBPATTERN);

		iBeXCreatePatternEClass = createEClass(IBE_XCREATE_PATTERN);
		createEReference(iBeXCreatePatternEClass, IBE_XCREATE_PATTERN__ATTRIBUTE_ASSIGNMENTS);
		createEReference(iBeXCreatePatternEClass, IBE_XCREATE_PATTERN__CONTEXT_NODES);
		createEReference(iBeXCreatePatternEClass, IBE_XCREATE_PATTERN__CREATED_EDGES);
		createEReference(iBeXCreatePatternEClass, IBE_XCREATE_PATTERN__CREATED_NODES);

		iBeXDeletePatternEClass = createEClass(IBE_XDELETE_PATTERN);
		createEReference(iBeXDeletePatternEClass, IBE_XDELETE_PATTERN__CONTEXT_NODES);
		createEReference(iBeXDeletePatternEClass, IBE_XDELETE_PATTERN__DELETED_EDGES);
		createEReference(iBeXDeletePatternEClass, IBE_XDELETE_PATTERN__DELETED_NODES);

		iBeXNodeEClass = createEClass(IBE_XNODE);
		createEReference(iBeXNodeEClass, IBE_XNODE__INCOMING_EDGES);
		createEReference(iBeXNodeEClass, IBE_XNODE__OUTGOING_EDGES);
		createEReference(iBeXNodeEClass, IBE_XNODE__TYPE);

		iBeXEdgeEClass = createEClass(IBE_XEDGE);
		createEReference(iBeXEdgeEClass, IBE_XEDGE__SOURCE_NODE);
		createEReference(iBeXEdgeEClass, IBE_XEDGE__TARGET_NODE);
		createEReference(iBeXEdgeEClass, IBE_XEDGE__TYPE);

		iBeXParameterEClass = createEClass(IBE_XPARAMETER);
		createEReference(iBeXParameterEClass, IBE_XPARAMETER__TYPE);

		iBeXAttributeEClass = createEClass(IBE_XATTRIBUTE);

		iBeXAttributeAssignmentEClass = createEClass(IBE_XATTRIBUTE_ASSIGNMENT);
		createEReference(iBeXAttributeAssignmentEClass, IBE_XATTRIBUTE_ASSIGNMENT__VALUE);
		createEReference(iBeXAttributeAssignmentEClass, IBE_XATTRIBUTE_ASSIGNMENT__NODE);
		createEReference(iBeXAttributeAssignmentEClass, IBE_XATTRIBUTE_ASSIGNMENT__TYPE);

		iBeXAttributeConstraintEClass = createEClass(IBE_XATTRIBUTE_CONSTRAINT);
		createEAttribute(iBeXAttributeConstraintEClass, IBE_XATTRIBUTE_CONSTRAINT__RELATION);
		createEReference(iBeXAttributeConstraintEClass, IBE_XATTRIBUTE_CONSTRAINT__LHS);
		createEReference(iBeXAttributeConstraintEClass, IBE_XATTRIBUTE_CONSTRAINT__RHS);

		iBeXAttributeValueEClass = createEClass(IBE_XATTRIBUTE_VALUE);

		iBeXAttributeParameterEClass = createEClass(IBE_XATTRIBUTE_PARAMETER);

		iBeXConstantEClass = createEClass(IBE_XCONSTANT);
		createEAttribute(iBeXConstantEClass, IBE_XCONSTANT__VALUE);
		createEAttribute(iBeXConstantEClass, IBE_XCONSTANT__STRING_VALUE);

		iBeXEnumLiteralEClass = createEClass(IBE_XENUM_LITERAL);
		createEReference(iBeXEnumLiteralEClass, IBE_XENUM_LITERAL__LITERAL);

		iBeXAttributeExpressionEClass = createEClass(IBE_XATTRIBUTE_EXPRESSION);
		createEReference(iBeXAttributeExpressionEClass, IBE_XATTRIBUTE_EXPRESSION__ATTRIBUTE);
		createEReference(iBeXAttributeExpressionEClass, IBE_XATTRIBUTE_EXPRESSION__NODE);

		iBeXStochasticAttributeValueEClass = createEClass(IBE_XSTOCHASTIC_ATTRIBUTE_VALUE);
		createEAttribute(iBeXStochasticAttributeValueEClass, IBE_XSTOCHASTIC_ATTRIBUTE_VALUE__RANGE);
		createEReference(iBeXStochasticAttributeValueEClass, IBE_XSTOCHASTIC_ATTRIBUTE_VALUE__FUNCTION);

		iBeXArithmeticValueEClass = createEClass(IBE_XARITHMETIC_VALUE);
		createEReference(iBeXArithmeticValueEClass, IBE_XARITHMETIC_VALUE__EXPRESSION);

		iBeXInjectivityConstraintEClass = createEClass(IBE_XINJECTIVITY_CONSTRAINT);
		createEReference(iBeXInjectivityConstraintEClass, IBE_XINJECTIVITY_CONSTRAINT__VALUES);

		iBeXPatternInvocationEClass = createEClass(IBE_XPATTERN_INVOCATION);
		createEAttribute(iBeXPatternInvocationEClass, IBE_XPATTERN_INVOCATION__POSITIVE);
		createEReference(iBeXPatternInvocationEClass, IBE_XPATTERN_INVOCATION__INVOKED_BY);
		createEReference(iBeXPatternInvocationEClass, IBE_XPATTERN_INVOCATION__INVOKED_PATTERN);
		createEReference(iBeXPatternInvocationEClass, IBE_XPATTERN_INVOCATION__MAPPING);

		iBeXNodeToNodeMappingEClass = createEClass(IBE_XNODE_TO_NODE_MAPPING);
		createEReference(iBeXNodeToNodeMappingEClass, IBE_XNODE_TO_NODE_MAPPING__KEY);
		createEReference(iBeXNodeToNodeMappingEClass, IBE_XNODE_TO_NODE_MAPPING__VALUE);

		iBeXCSPEClass = createEClass(IBE_XCSP);
		createEAttribute(iBeXCSPEClass, IBE_XCSP__NAME);
		createEAttribute(iBeXCSPEClass, IBE_XCSP__PACKAGE);
		createEReference(iBeXCSPEClass, IBE_XCSP__VALUES);

		iBeXRuleEClass = createEClass(IBE_XRULE);
		createEAttribute(iBeXRuleEClass, IBE_XRULE__DOCUMENTATION);
		createEReference(iBeXRuleEClass, IBE_XRULE__LHS);
		createEReference(iBeXRuleEClass, IBE_XRULE__RHS);
		createEReference(iBeXRuleEClass, IBE_XRULE__CREATE);
		createEReference(iBeXRuleEClass, IBE_XRULE__DELETE);
		createEReference(iBeXRuleEClass, IBE_XRULE__PARAMETERS);
		createEReference(iBeXRuleEClass, IBE_XRULE__ARITHMETIC_CONSTRAINTS);
		createEReference(iBeXRuleEClass, IBE_XRULE__PROBABILITY);

		iBeXProbabilityEClass = createEClass(IBE_XPROBABILITY);
		createEReference(iBeXProbabilityEClass, IBE_XPROBABILITY__DISTRIBUTION);
		createEReference(iBeXProbabilityEClass, IBE_XPROBABILITY__PARAMETER);

		iBeXArithmeticConstraintEClass = createEClass(IBE_XARITHMETIC_CONSTRAINT);
		createEReference(iBeXArithmeticConstraintEClass, IBE_XARITHMETIC_CONSTRAINT__LHS);
		createEAttribute(iBeXArithmeticConstraintEClass, IBE_XARITHMETIC_CONSTRAINT__RELATION);
		createEReference(iBeXArithmeticConstraintEClass, IBE_XARITHMETIC_CONSTRAINT__RHS);

		iBeXArithmeticExpressionEClass = createEClass(IBE_XARITHMETIC_EXPRESSION);

		iBeXUnaryExpressionEClass = createEClass(IBE_XUNARY_EXPRESSION);
		createEReference(iBeXUnaryExpressionEClass, IBE_XUNARY_EXPRESSION__OPERAND);
		createEAttribute(iBeXUnaryExpressionEClass, IBE_XUNARY_EXPRESSION__OPERATOR);
		createEAttribute(iBeXUnaryExpressionEClass, IBE_XUNARY_EXPRESSION__NEGATIVE);

		iBeXBinaryExpressionEClass = createEClass(IBE_XBINARY_EXPRESSION);
		createEReference(iBeXBinaryExpressionEClass, IBE_XBINARY_EXPRESSION__LEFT);
		createEReference(iBeXBinaryExpressionEClass, IBE_XBINARY_EXPRESSION__RIGHT);
		createEAttribute(iBeXBinaryExpressionEClass, IBE_XBINARY_EXPRESSION__OPERATOR);

		iBeXArithmeticValueLiteralEClass = createEClass(IBE_XARITHMETIC_VALUE_LITERAL);
		createEAttribute(iBeXArithmeticValueLiteralEClass, IBE_XARITHMETIC_VALUE_LITERAL__VALUE);

		iBeXProbabilityDistributionEClass = createEClass(IBE_XPROBABILITY_DISTRIBUTION);
		createEReference(iBeXProbabilityDistributionEClass, IBE_XPROBABILITY_DISTRIBUTION__MEAN);
		createEReference(iBeXProbabilityDistributionEClass, IBE_XPROBABILITY_DISTRIBUTION__STDDEV);
		createEAttribute(iBeXProbabilityDistributionEClass, IBE_XPROBABILITY_DISTRIBUTION__TYPE);

		iBeXArithmeticAttributeEClass = createEClass(IBE_XARITHMETIC_ATTRIBUTE);
		createEReference(iBeXArithmeticAttributeEClass, IBE_XARITHMETIC_ATTRIBUTE__ATTRIBUTE);
		createEAttribute(iBeXArithmeticAttributeEClass, IBE_XARITHMETIC_ATTRIBUTE__NEGATIVE);

		iBeXMatchCountEClass = createEClass(IBE_XMATCH_COUNT);
		createEReference(iBeXMatchCountEClass, IBE_XMATCH_COUNT__INVOCATION);

		iBeXConstraintEClass = createEClass(IBE_XCONSTRAINT);

		iBeXDisjointContextPatternEClass = createEClass(IBE_XDISJOINT_CONTEXT_PATTERN);
		createEReference(iBeXDisjointContextPatternEClass, IBE_XDISJOINT_CONTEXT_PATTERN__SUBPATTERNS);
		createEReference(iBeXDisjointContextPatternEClass, IBE_XDISJOINT_CONTEXT_PATTERN__INJECTIVITY_CONSTRAINTS);
		createEReference(iBeXDisjointContextPatternEClass, IBE_XDISJOINT_CONTEXT_PATTERN__ATTRIBUTE_CONSTRAINTS);
		createEReference(iBeXDisjointContextPatternEClass, IBE_XDISJOINT_CONTEXT_PATTERN__NON_OPTIMIZED_PATTERN);

		iBexDisjointInjectivityConstraintEClass = createEClass(IBEX_DISJOINT_INJECTIVITY_CONSTRAINT);
		createEReference(iBexDisjointInjectivityConstraintEClass, IBEX_DISJOINT_INJECTIVITY_CONSTRAINT__PATTERN1);
		createEReference(iBexDisjointInjectivityConstraintEClass, IBEX_DISJOINT_INJECTIVITY_CONSTRAINT__PATTERN2);
		createEReference(iBexDisjointInjectivityConstraintEClass, IBEX_DISJOINT_INJECTIVITY_CONSTRAINT__NODE1);
		createEReference(iBexDisjointInjectivityConstraintEClass, IBEX_DISJOINT_INJECTIVITY_CONSTRAINT__NODE2);

		iBeXDisjointAttributeEClass = createEClass(IBE_XDISJOINT_ATTRIBUTE);
		createEReference(iBeXDisjointAttributeEClass, IBE_XDISJOINT_ATTRIBUTE__TARGET_PATTERN);
		createEReference(iBeXDisjointAttributeEClass, IBE_XDISJOINT_ATTRIBUTE__SOURCE_PATTERN);
		createEReference(iBeXDisjointAttributeEClass, IBE_XDISJOINT_ATTRIBUTE__DISJOINT_ATTRIBUTE);

		iBeXInterdependentInjectivityConstraintsEClass = createEClass(IBE_XINTERDEPENDENT_INJECTIVITY_CONSTRAINTS);
		createEReference(iBeXInterdependentInjectivityConstraintsEClass,
				IBE_XINTERDEPENDENT_INJECTIVITY_CONSTRAINTS__INJECTIVITY_CONSTRAINTS);
		createEReference(iBeXInterdependentInjectivityConstraintsEClass,
				IBE_XINTERDEPENDENT_INJECTIVITY_CONSTRAINTS__PATTERNS);
		createEReference(iBeXInterdependentInjectivityConstraintsEClass,
				IBE_XINTERDEPENDENT_INJECTIVITY_CONSTRAINTS__ATTRIBUTE_CONSTRAINTS);

		iBeXInterdependentAttributesEClass = createEClass(IBE_XINTERDEPENDENT_ATTRIBUTES);
		createEReference(iBeXInterdependentAttributesEClass, IBE_XINTERDEPENDENT_ATTRIBUTES__ATTRIBUTES);
		createEReference(iBeXInterdependentAttributesEClass, IBE_XINTERDEPENDENT_ATTRIBUTES__INTERDEPENDENT_PATTERNS);
		createEReference(iBeXInterdependentAttributesEClass, IBE_XINTERDEPENDENT_ATTRIBUTES__INJECTIVITY_CONSTRAINTS);

		// Create enums
		iBeXRelationEEnum = createEEnum(IBE_XRELATION);
		iBeXBinaryOperatorEEnum = createEEnum(IBE_XBINARY_OPERATOR);
		iBeXUnaryOperatorEEnum = createEEnum(IBE_XUNARY_OPERATOR);
		iBeXDistributionTypeEEnum = createEEnum(IBE_XDISTRIBUTION_TYPE);
		iBeXDistributionRangeEEnum = createEEnum(IBE_XDISTRIBUTION_RANGE);
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

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		iBeXPatternEClass.getESuperTypes().add(this.getIBeXNamedElement());
		iBeXContextEClass.getESuperTypes().add(this.getIBeXPattern());
		iBeXContextAlternativesEClass.getESuperTypes().add(this.getIBeXContext());
		iBeXContextPatternEClass.getESuperTypes().add(this.getIBeXContext());
		iBeXCreatePatternEClass.getESuperTypes().add(this.getIBeXPattern());
		iBeXDeletePatternEClass.getESuperTypes().add(this.getIBeXPattern());
		iBeXNodeEClass.getESuperTypes().add(this.getIBeXNamedElement());
		iBeXEdgeEClass.getESuperTypes().add(this.getIBeXNamedElement());
		iBeXParameterEClass.getESuperTypes().add(this.getIBeXNamedElement());
		iBeXAttributeAssignmentEClass.getESuperTypes().add(this.getIBeXAttribute());
		iBeXAttributeConstraintEClass.getESuperTypes().add(this.getIBeXAttribute());
		iBeXAttributeConstraintEClass.getESuperTypes().add(this.getIBeXConstraint());
		iBeXAttributeParameterEClass.getESuperTypes().add(this.getIBeXAttributeValue());
		iBeXAttributeParameterEClass.getESuperTypes().add(this.getIBeXNamedElement());
		iBeXConstantEClass.getESuperTypes().add(this.getIBeXAttributeValue());
		iBeXEnumLiteralEClass.getESuperTypes().add(this.getIBeXAttributeValue());
		iBeXAttributeExpressionEClass.getESuperTypes().add(this.getIBeXAttributeValue());
		iBeXStochasticAttributeValueEClass.getESuperTypes().add(this.getIBeXAttributeValue());
		iBeXArithmeticValueEClass.getESuperTypes().add(this.getIBeXAttributeValue());
		iBeXRuleEClass.getESuperTypes().add(this.getIBeXNamedElement());
		iBeXArithmeticConstraintEClass.getESuperTypes().add(this.getIBeXConstraint());
		iBeXUnaryExpressionEClass.getESuperTypes().add(this.getIBeXArithmeticExpression());
		iBeXBinaryExpressionEClass.getESuperTypes().add(this.getIBeXArithmeticExpression());
		iBeXArithmeticValueLiteralEClass.getESuperTypes().add(this.getIBeXArithmeticExpression());
		iBeXArithmeticAttributeEClass.getESuperTypes().add(this.getIBeXNode());
		iBeXArithmeticAttributeEClass.getESuperTypes().add(this.getIBeXArithmeticExpression());
		iBeXMatchCountEClass.getESuperTypes().add(this.getIBeXUnaryExpression());
		iBeXDisjointContextPatternEClass.getESuperTypes().add(this.getIBeXContext());

		// Initialize classes, features, and operations; add parameters
		initEClass(iBeXModelEClass, IBeXModel.class, "IBeXModel", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXModel_PatternSet(), this.getIBeXPatternSet(), null, "patternSet", null, 1, 1,
				IBeXModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXModel_RuleSet(), this.getIBeXRuleSet(), null, "ruleSet", null, 1, 1, IBeXModel.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXModel_NodeSet(), this.getIBeXNodeSet(), null, "nodeSet", null, 1, 1, IBeXModel.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXModel_EdgeSet(), this.getIBeXEdgeSet(), null, "edgeSet", null, 1, 1, IBeXModel.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXPatternSetEClass, IBeXPatternSet.class, "IBeXPatternSet", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXPatternSet_ContextPatterns(), this.getIBeXContext(), null, "contextPatterns", null, 0, -1,
				IBeXPatternSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXRuleSetEClass, IBeXRuleSet.class, "IBeXRuleSet", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXRuleSet_Rules(), this.getIBeXRule(), null, "rules", null, 0, -1, IBeXRuleSet.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXNodeSetEClass, IBeXNodeSet.class, "IBeXNodeSet", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXNodeSet_Nodes(), this.getIBeXNode(), null, "nodes", null, 0, -1, IBeXNodeSet.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXEdgeSetEClass, IBeXEdgeSet.class, "IBeXEdgeSet", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXEdgeSet_Edges(), this.getIBeXEdge(), null, "edges", null, 0, -1, IBeXEdgeSet.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXNamedElementEClass, IBeXNamedElement.class, "IBeXNamedElement", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIBeXNamedElement_Name(), ecorePackage.getEString(), "name", null, 0, 1,
				IBeXNamedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(iBeXPatternEClass, IBeXPattern.class, "IBeXPattern", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(iBeXContextEClass, IBeXContext.class, "IBeXContext", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXContext_ApiPatternDependencies(), this.getIBeXContext(), null, "apiPatternDependencies",
				null, 0, -1, IBeXContext.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXContextAlternativesEClass, IBeXContextAlternatives.class, "IBeXContextAlternatives",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXContextAlternatives_AlternativePatterns(), this.getIBeXContextPattern(), null,
				"alternativePatterns", null, 0, -1, IBeXContextAlternatives.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXContextAlternatives_Context(), this.getIBeXContextPattern(), null, "context", null, 1, 1,
				IBeXContextAlternatives.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXContextPatternEClass, IBeXContextPattern.class, "IBeXContextPattern", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIBeXContextPattern_Documentation(), ecorePackage.getEString(), "documentation", null, 0, 1,
				IBeXContextPattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXContextPattern_AttributeConstraint(), this.getIBeXAttributeConstraint(), null,
				"attributeConstraint", null, 0, -1, IBeXContextPattern.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXContextPattern_ArithmeticConstraints(), this.getIBeXArithmeticConstraint(), null,
				"arithmeticConstraints", null, 0, -1, IBeXContextPattern.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXContextPattern_InjectivityConstraints(), this.getIBeXInjectivityConstraint(), null,
				"injectivityConstraints", null, 0, -1, IBeXContextPattern.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXContextPattern_Invocations(), this.getIBeXPatternInvocation(),
				this.getIBeXPatternInvocation_InvokedBy(), "invocations", null, 0, -1, IBeXContextPattern.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXContextPattern_LocalEdges(), this.getIBeXEdge(), null, "localEdges", null, 0, -1,
				IBeXContextPattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXContextPattern_LocalNodes(), this.getIBeXNode(), null, "localNodes", null, 0, -1,
				IBeXContextPattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXContextPattern_SignatureNodes(), this.getIBeXNode(), null, "signatureNodes", null, 0, -1,
				IBeXContextPattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXContextPattern_Csps(), this.getIBeXCSP(), null, "csps", null, 0, -1,
				IBeXContextPattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXContextPattern_Parameters(), this.getIBeXParameter(), null, "parameters", null, 0, -1,
				IBeXContextPattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIBeXContextPattern_OptimizedDisjoint(), ecorePackage.getEBoolean(), "optimizedDisjoint",
				"FALSE", 0, 1, IBeXContextPattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIBeXContextPattern_Subpattern(), ecorePackage.getEBoolean(), "subpattern", null, 0, 1,
				IBeXContextPattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(iBeXCreatePatternEClass, IBeXCreatePattern.class, "IBeXCreatePattern", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXCreatePattern_AttributeAssignments(), this.getIBeXAttributeAssignment(), null,
				"attributeAssignments", null, 0, -1, IBeXCreatePattern.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXCreatePattern_ContextNodes(), this.getIBeXNode(), null, "contextNodes", null, 0, -1,
				IBeXCreatePattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXCreatePattern_CreatedEdges(), this.getIBeXEdge(), null, "createdEdges", null, 0, -1,
				IBeXCreatePattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXCreatePattern_CreatedNodes(), this.getIBeXNode(), null, "createdNodes", null, 0, -1,
				IBeXCreatePattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXDeletePatternEClass, IBeXDeletePattern.class, "IBeXDeletePattern", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXDeletePattern_ContextNodes(), this.getIBeXNode(), null, "contextNodes", null, 0, -1,
				IBeXDeletePattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXDeletePattern_DeletedEdges(), this.getIBeXEdge(), null, "deletedEdges", null, 0, -1,
				IBeXDeletePattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXDeletePattern_DeletedNodes(), this.getIBeXNode(), null, "deletedNodes", null, 0, -1,
				IBeXDeletePattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXNodeEClass, IBeXNode.class, "IBeXNode", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXNode_IncomingEdges(), this.getIBeXEdge(), this.getIBeXEdge_TargetNode(), "incomingEdges",
				null, 0, -1, IBeXNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXNode_OutgoingEdges(), this.getIBeXEdge(), this.getIBeXEdge_SourceNode(), "outgoingEdges",
				null, 0, -1, IBeXNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXNode_Type(), ecorePackage.getEClass(), null, "type", null, 0, 1, IBeXNode.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXEdgeEClass, IBeXEdge.class, "IBeXEdge", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXEdge_SourceNode(), this.getIBeXNode(), this.getIBeXNode_OutgoingEdges(), "sourceNode",
				null, 0, 1, IBeXEdge.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXEdge_TargetNode(), this.getIBeXNode(), this.getIBeXNode_IncomingEdges(), "targetNode",
				null, 0, 1, IBeXEdge.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXEdge_Type(), ecorePackage.getEReference(), null, "type", null, 0, 1, IBeXEdge.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXParameterEClass, IBeXParameter.class, "IBeXParameter", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXParameter_Type(), ecorePackage.getEDataType(), null, "type", null, 0, 1,
				IBeXParameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXAttributeEClass, IBeXAttribute.class, "IBeXAttribute", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(iBeXAttributeAssignmentEClass, IBeXAttributeAssignment.class, "IBeXAttributeAssignment",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXAttributeAssignment_Value(), this.getIBeXAttributeValue(), null, "value", null, 0, 1,
				IBeXAttributeAssignment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXAttributeAssignment_Node(), this.getIBeXNode(), null, "node", null, 0, 1,
				IBeXAttributeAssignment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXAttributeAssignment_Type(), ecorePackage.getEAttribute(), null, "type", null, 0, 1,
				IBeXAttributeAssignment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXAttributeConstraintEClass, IBeXAttributeConstraint.class, "IBeXAttributeConstraint",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIBeXAttributeConstraint_Relation(), this.getIBeXRelation(), "relation", null, 0, 1,
				IBeXAttributeConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXAttributeConstraint_Lhs(), this.getIBeXAttributeValue(), null, "lhs", null, 1, 1,
				IBeXAttributeConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXAttributeConstraint_Rhs(), this.getIBeXAttributeValue(), null, "rhs", null, 1, 1,
				IBeXAttributeConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXAttributeValueEClass, IBeXAttributeValue.class, "IBeXAttributeValue", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(iBeXAttributeParameterEClass, IBeXAttributeParameter.class, "IBeXAttributeParameter", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(iBeXConstantEClass, IBeXConstant.class, "IBeXConstant", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIBeXConstant_Value(), ecorePackage.getEJavaObject(), "value", null, 0, 1, IBeXConstant.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIBeXConstant_StringValue(), ecorePackage.getEString(), "stringValue", null, 0, 1,
				IBeXConstant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(iBeXEnumLiteralEClass, IBeXEnumLiteral.class, "IBeXEnumLiteral", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXEnumLiteral_Literal(), ecorePackage.getEEnumLiteral(), null, "literal", null, 0, 1,
				IBeXEnumLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXAttributeExpressionEClass, IBeXAttributeExpression.class, "IBeXAttributeExpression",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXAttributeExpression_Attribute(), ecorePackage.getEAttribute(), null, "attribute", null, 0,
				1, IBeXAttributeExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXAttributeExpression_Node(), this.getIBeXNode(), null, "node", null, 0, 1,
				IBeXAttributeExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXStochasticAttributeValueEClass, IBeXStochasticAttributeValue.class,
				"IBeXStochasticAttributeValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIBeXStochasticAttributeValue_Range(), this.getIBeXDistributionRange(), "range", null, 0, 1,
				IBeXStochasticAttributeValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXStochasticAttributeValue_Function(), this.getIBeXProbabilityDistribution(), null,
				"function", null, 0, 1, IBeXStochasticAttributeValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXArithmeticValueEClass, IBeXArithmeticValue.class, "IBeXArithmeticValue", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXArithmeticValue_Expression(), this.getIBeXArithmeticExpression(), null, "expression",
				null, 0, 1, IBeXArithmeticValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXInjectivityConstraintEClass, IBeXInjectivityConstraint.class, "IBeXInjectivityConstraint",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXInjectivityConstraint_Values(), this.getIBeXNode(), null, "values", null, 2, 2,
				IBeXInjectivityConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXPatternInvocationEClass, IBeXPatternInvocation.class, "IBeXPatternInvocation", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIBeXPatternInvocation_Positive(), ecorePackage.getEBoolean(), "positive", null, 0, 1,
				IBeXPatternInvocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXPatternInvocation_InvokedBy(), this.getIBeXContextPattern(),
				this.getIBeXContextPattern_Invocations(), "invokedBy", null, 0, 1, IBeXPatternInvocation.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXPatternInvocation_InvokedPattern(), this.getIBeXContextPattern(), null, "invokedPattern",
				null, 0, 1, IBeXPatternInvocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXPatternInvocation_Mapping(), this.getIBeXNodeToNodeMapping(), null, "mapping", null, 0,
				-1, IBeXPatternInvocation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXNodeToNodeMappingEClass, Map.Entry.class, "IBeXNodeToNodeMapping", !IS_ABSTRACT, !IS_INTERFACE,
				!IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXNodeToNodeMapping_Key(), this.getIBeXNode(), null, "key", null, 0, 1, Map.Entry.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXNodeToNodeMapping_Value(), this.getIBeXNode(), null, "value", null, 0, 1, Map.Entry.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXCSPEClass, IBeXCSP.class, "IBeXCSP", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIBeXCSP_Name(), ecorePackage.getEString(), "name", null, 0, 1, IBeXCSP.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIBeXCSP_Package(), ecorePackage.getEString(), "package", null, 0, 1, IBeXCSP.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXCSP_Values(), this.getIBeXAttributeValue(), null, "values", null, 0, -1, IBeXCSP.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXRuleEClass, IBeXRule.class, "IBeXRule", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIBeXRule_Documentation(), ecorePackage.getEString(), "documentation", null, 0, 1,
				IBeXRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXRule_Lhs(), this.getIBeXContext(), null, "lhs", null, 0, 1, IBeXRule.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getIBeXRule_Rhs(), this.getIBeXContextPattern(), null, "rhs", null, 0, 1, IBeXRule.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXRule_Create(), this.getIBeXCreatePattern(), null, "create", null, 0, 1, IBeXRule.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXRule_Delete(), this.getIBeXDeletePattern(), null, "delete", null, 0, 1, IBeXRule.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXRule_Parameters(), this.getIBeXParameter(), null, "parameters", null, 0, -1,
				IBeXRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXRule_ArithmeticConstraints(), this.getIBeXArithmeticConstraint(), null,
				"arithmeticConstraints", null, 0, -1, IBeXRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXRule_Probability(), this.getIBeXProbability(), null, "probability", null, 0, 1,
				IBeXRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXProbabilityEClass, IBeXProbability.class, "IBeXProbability", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXProbability_Distribution(), this.getIBeXProbabilityDistribution(), null, "distribution",
				null, 1, 1, IBeXProbability.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXProbability_Parameter(), this.getIBeXArithmeticExpression(), null, "parameter", null, 1,
				1, IBeXProbability.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXArithmeticConstraintEClass, IBeXArithmeticConstraint.class, "IBeXArithmeticConstraint",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXArithmeticConstraint_Lhs(), this.getIBeXArithmeticExpression(), null, "lhs", null, 1, 1,
				IBeXArithmeticConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIBeXArithmeticConstraint_Relation(), this.getIBeXRelation(), "relation", null, 1, 1,
				IBeXArithmeticConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXArithmeticConstraint_Rhs(), this.getIBeXArithmeticExpression(), null, "rhs", null, 1, 1,
				IBeXArithmeticConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXArithmeticExpressionEClass, IBeXArithmeticExpression.class, "IBeXArithmeticExpression",
				IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(iBeXUnaryExpressionEClass, IBeXUnaryExpression.class, "IBeXUnaryExpression", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXUnaryExpression_Operand(), this.getIBeXArithmeticExpression(), null, "operand", null, 1,
				1, IBeXUnaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIBeXUnaryExpression_Operator(), this.getIBeXUnaryOperator(), "operator", null, 1, 1,
				IBeXUnaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIBeXUnaryExpression_Negative(), ecorePackage.getEBoolean(), "negative", null, 1, 1,
				IBeXUnaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXBinaryExpressionEClass, IBeXBinaryExpression.class, "IBeXBinaryExpression", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXBinaryExpression_Left(), this.getIBeXArithmeticExpression(), null, "left", null, 1, 1,
				IBeXBinaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXBinaryExpression_Right(), this.getIBeXArithmeticExpression(), null, "right", null, 1, 1,
				IBeXBinaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIBeXBinaryExpression_Operator(), this.getIBeXBinaryOperator(), "operator", null, 1, 1,
				IBeXBinaryExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXArithmeticValueLiteralEClass, IBeXArithmeticValueLiteral.class, "IBeXArithmeticValueLiteral",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIBeXArithmeticValueLiteral_Value(), ecorePackage.getEDouble(), "value", null, 1, 1,
				IBeXArithmeticValueLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXProbabilityDistributionEClass, IBeXProbabilityDistribution.class, "IBeXProbabilityDistribution",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXProbabilityDistribution_Mean(), this.getIBeXArithmeticExpression(), null, "mean", null, 0,
				1, IBeXProbabilityDistribution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXProbabilityDistribution_Stddev(), this.getIBeXArithmeticExpression(), null, "stddev",
				null, 0, 1, IBeXProbabilityDistribution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIBeXProbabilityDistribution_Type(), this.getIBeXDistributionType(), "type", null, 1, 1,
				IBeXProbabilityDistribution.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXArithmeticAttributeEClass, IBeXArithmeticAttribute.class, "IBeXArithmeticAttribute",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXArithmeticAttribute_Attribute(), ecorePackage.getEAttribute(), null, "attribute", null, 1,
				1, IBeXArithmeticAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIBeXArithmeticAttribute_Negative(), ecorePackage.getEBoolean(), "negative", null, 1, 1,
				IBeXArithmeticAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXMatchCountEClass, IBeXMatchCount.class, "IBeXMatchCount", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXMatchCount_Invocation(), this.getIBeXPatternInvocation(), null, "invocation", null, 1, 1,
				IBeXMatchCount.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXConstraintEClass, IBeXConstraint.class, "IBeXConstraint", IS_ABSTRACT, IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(iBeXDisjointContextPatternEClass, IBeXDisjointContextPattern.class, "IBeXDisjointContextPattern",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXDisjointContextPattern_Subpatterns(), this.getIBeXContextPattern(), null, "subpatterns",
				null, 0, -1, IBeXDisjointContextPattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXDisjointContextPattern_InjectivityConstraints(),
				this.getIBeXInterdependentInjectivityConstraints(), null, "injectivityConstraints", null, 0, -1,
				IBeXDisjointContextPattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXDisjointContextPattern_AttributeConstraints(), this.getIBeXInterdependentAttributes(),
				null, "attributeConstraints", null, 0, -1, IBeXDisjointContextPattern.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getIBeXDisjointContextPattern_NonOptimizedPattern(), this.getIBeXContextPattern(), null,
				"nonOptimizedPattern", null, 0, 1, IBeXDisjointContextPattern.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBexDisjointInjectivityConstraintEClass, IBexDisjointInjectivityConstraint.class,
				"IBexDisjointInjectivityConstraint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBexDisjointInjectivityConstraint_Pattern1(), this.getIBeXContextPattern(), null, "pattern1",
				null, 0, 1, IBexDisjointInjectivityConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBexDisjointInjectivityConstraint_Pattern2(), this.getIBeXContextPattern(), null, "pattern2",
				null, 0, 1, IBexDisjointInjectivityConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBexDisjointInjectivityConstraint_Node1(), this.getIBeXNode(), null, "node1", null, 0, -1,
				IBexDisjointInjectivityConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBexDisjointInjectivityConstraint_Node2(), this.getIBeXNode(), null, "node2", null, 0, -1,
				IBexDisjointInjectivityConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXDisjointAttributeEClass, IBeXDisjointAttribute.class, "IBeXDisjointAttribute", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXDisjointAttribute_TargetPattern(), this.getIBeXContextPattern(), null, "targetPattern",
				null, 0, -1, IBeXDisjointAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXDisjointAttribute_SourcePattern(), this.getIBeXContextPattern(), null, "sourcePattern",
				null, 0, -1, IBeXDisjointAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXDisjointAttribute_DisjointAttribute(), this.getIBeXConstraint(), null,
				"disjointAttribute", null, 0, -1, IBeXDisjointAttribute.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXInterdependentInjectivityConstraintsEClass, IBeXInterdependentInjectivityConstraints.class,
				"IBeXInterdependentInjectivityConstraints", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXInterdependentInjectivityConstraints_InjectivityConstraints(),
				this.getIBexDisjointInjectivityConstraint(), null, "injectivityConstraints", null, 0, -1,
				IBeXInterdependentInjectivityConstraints.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXInterdependentInjectivityConstraints_Patterns(), this.getIBeXContextPattern(), null,
				"patterns", null, 0, -1, IBeXInterdependentInjectivityConstraints.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXInterdependentInjectivityConstraints_AttributeConstraints(),
				this.getIBeXInterdependentAttributes(), this.getIBeXInterdependentAttributes_InjectivityConstraints(),
				"attributeConstraints", null, 0, -1, IBeXInterdependentInjectivityConstraints.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(iBeXInterdependentAttributesEClass, IBeXInterdependentAttributes.class,
				"IBeXInterdependentAttributes", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXInterdependentAttributes_Attributes(), this.getIBeXDisjointAttribute(), null,
				"attributes", null, 0, -1, IBeXInterdependentAttributes.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXInterdependentAttributes_InterdependentPatterns(), this.getIBeXContextPattern(), null,
				"interdependentPatterns", null, 0, -1, IBeXInterdependentAttributes.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXInterdependentAttributes_InjectivityConstraints(),
				this.getIBeXInterdependentInjectivityConstraints(),
				this.getIBeXInterdependentInjectivityConstraints_AttributeConstraints(), "injectivityConstraints", null,
				0, 1, IBeXInterdependentAttributes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(iBeXRelationEEnum, IBeXRelation.class, "IBeXRelation");
		addEEnumLiteral(iBeXRelationEEnum, IBeXRelation.GREATER_OR_EQUAL);
		addEEnumLiteral(iBeXRelationEEnum, IBeXRelation.GREATER);
		addEEnumLiteral(iBeXRelationEEnum, IBeXRelation.EQUAL);
		addEEnumLiteral(iBeXRelationEEnum, IBeXRelation.UNEQUAL);
		addEEnumLiteral(iBeXRelationEEnum, IBeXRelation.SMALLER_OR_EQUAL);
		addEEnumLiteral(iBeXRelationEEnum, IBeXRelation.SMALLER);

		initEEnum(iBeXBinaryOperatorEEnum, IBeXBinaryOperator.class, "IBeXBinaryOperator");
		addEEnumLiteral(iBeXBinaryOperatorEEnum, IBeXBinaryOperator.ADDITION);
		addEEnumLiteral(iBeXBinaryOperatorEEnum, IBeXBinaryOperator.MULTIPLICATION);
		addEEnumLiteral(iBeXBinaryOperatorEEnum, IBeXBinaryOperator.DIVISION);
		addEEnumLiteral(iBeXBinaryOperatorEEnum, IBeXBinaryOperator.MODULUS);
		addEEnumLiteral(iBeXBinaryOperatorEEnum, IBeXBinaryOperator.EXPONENTIATION);
		addEEnumLiteral(iBeXBinaryOperatorEEnum, IBeXBinaryOperator.SUBTRACTION);
		addEEnumLiteral(iBeXBinaryOperatorEEnum, IBeXBinaryOperator.MINIMUM);
		addEEnumLiteral(iBeXBinaryOperatorEEnum, IBeXBinaryOperator.MAXIMUM);

		initEEnum(iBeXUnaryOperatorEEnum, IBeXUnaryOperator.class, "IBeXUnaryOperator");
		addEEnumLiteral(iBeXUnaryOperatorEEnum, IBeXUnaryOperator.SQRT);
		addEEnumLiteral(iBeXUnaryOperatorEEnum, IBeXUnaryOperator.ABSOLUTE);
		addEEnumLiteral(iBeXUnaryOperatorEEnum, IBeXUnaryOperator.SIN);
		addEEnumLiteral(iBeXUnaryOperatorEEnum, IBeXUnaryOperator.COS);
		addEEnumLiteral(iBeXUnaryOperatorEEnum, IBeXUnaryOperator.TAN);
		addEEnumLiteral(iBeXUnaryOperatorEEnum, IBeXUnaryOperator.EEXPONENTIAL);
		addEEnumLiteral(iBeXUnaryOperatorEEnum, IBeXUnaryOperator.LOG);
		addEEnumLiteral(iBeXUnaryOperatorEEnum, IBeXUnaryOperator.LG);
		addEEnumLiteral(iBeXUnaryOperatorEEnum, IBeXUnaryOperator.BRACKET);
		addEEnumLiteral(iBeXUnaryOperatorEEnum, IBeXUnaryOperator.COUNT);

		initEEnum(iBeXDistributionTypeEEnum, IBeXDistributionType.class, "IBeXDistributionType");
		addEEnumLiteral(iBeXDistributionTypeEEnum, IBeXDistributionType.NORMAL);
		addEEnumLiteral(iBeXDistributionTypeEEnum, IBeXDistributionType.UNIFORM);
		addEEnumLiteral(iBeXDistributionTypeEEnum, IBeXDistributionType.EXPONENTIAL);
		addEEnumLiteral(iBeXDistributionTypeEEnum, IBeXDistributionType.STATIC);

		initEEnum(iBeXDistributionRangeEEnum, IBeXDistributionRange.class, "IBeXDistributionRange");
		addEEnumLiteral(iBeXDistributionRangeEEnum, IBeXDistributionRange.ALL);
		addEEnumLiteral(iBeXDistributionRangeEEnum, IBeXDistributionRange.POSITIVE_ONLY);
		addEEnumLiteral(iBeXDistributionRangeEEnum, IBeXDistributionRange.NEGATIVE_ONLY);

		// Create resource
		createResource(eNS_URI);
	}

} //IBeXPatternModelPackageImpl
