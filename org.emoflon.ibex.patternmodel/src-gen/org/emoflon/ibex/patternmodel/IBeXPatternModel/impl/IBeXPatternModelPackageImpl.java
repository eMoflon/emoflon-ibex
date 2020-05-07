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

import org.emoflon.ibex.gt.SGTPatternModel.SGTPatternModelPackage;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticValue;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttribute;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeAssignment;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeConstraint;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeExpression;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeParameter;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeValue;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCSP;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXConstant;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextAlternatives;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCreatePattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDeletePattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEdge;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEnumLiteral;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNamedElement;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNodePair;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternInvocation;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelFactory;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternSet;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRelation;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXStochasticAttributeValue;

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
	private EClass iBeXAttributeParameterEClass = null;

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
	private EClass iBeXAttributeExpressionEClass = null;

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
	private EClass iBeXEdgeEClass = null;

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
	private EClass iBeXNamedElementEClass = null;

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
	private EClass iBeXNodePairEClass = null;

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
	private EClass iBeXPatternEClass = null;

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
	private EClass iBeXPatternSetEClass = null;

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
	private EEnum iBeXRelationEEnum = null;

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

		// Initialize simple dependencies
		SGTPatternModelPackage.eINSTANCE.eClass();

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
	public EClass getIBeXAttribute() {
		return iBeXAttributeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXAttribute_Type() {
		return (EReference) iBeXAttributeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXAttribute_Node() {
		return (EReference) iBeXAttributeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXAttribute_Value() {
		return (EReference) iBeXAttributeEClass.getEStructuralFeatures().get(2);
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
	public EClass getIBeXAttributeParameter() {
		return iBeXAttributeParameterEClass;
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
	public EClass getIBeXContext() {
		return iBeXContextEClass;
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
	public EClass getIBeXContextPattern() {
		return iBeXContextPatternEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXContextPattern_AttributeConstraint() {
		return (EReference) iBeXContextPatternEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXContextPattern_InjectivityConstraints() {
		return (EReference) iBeXContextPatternEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXContextPattern_Invocations() {
		return (EReference) iBeXContextPatternEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXContextPattern_LocalEdges() {
		return (EReference) iBeXContextPatternEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXContextPattern_LocalNodes() {
		return (EReference) iBeXContextPatternEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXContextPattern_SignatureNodes() {
		return (EReference) iBeXContextPatternEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXContextPattern_Csps() {
		return (EReference) iBeXContextPatternEClass.getEStructuralFeatures().get(6);
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
	public EClass getIBeXNodePair() {
		return iBeXNodePairEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXNodePair_Values() {
		return (EReference) iBeXNodePairEClass.getEStructuralFeatures().get(0);
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
	public EClass getIBeXPattern() {
		return iBeXPatternEClass;
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
	public EReference getIBeXPatternSet_CreatePatterns() {
		return (EReference) iBeXPatternSetEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getIBeXPatternSet_DeletePatterns() {
		return (EReference) iBeXPatternSetEClass.getEStructuralFeatures().get(2);
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
	public EEnum getIBeXRelation() {
		return iBeXRelationEEnum;
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
		iBeXAttributeEClass = createEClass(IBE_XATTRIBUTE);
		createEReference(iBeXAttributeEClass, IBE_XATTRIBUTE__TYPE);
		createEReference(iBeXAttributeEClass, IBE_XATTRIBUTE__NODE);
		createEReference(iBeXAttributeEClass, IBE_XATTRIBUTE__VALUE);

		iBeXAttributeAssignmentEClass = createEClass(IBE_XATTRIBUTE_ASSIGNMENT);

		iBeXAttributeConstraintEClass = createEClass(IBE_XATTRIBUTE_CONSTRAINT);
		createEAttribute(iBeXAttributeConstraintEClass, IBE_XATTRIBUTE_CONSTRAINT__RELATION);

		iBeXAttributeParameterEClass = createEClass(IBE_XATTRIBUTE_PARAMETER);

		iBeXAttributeValueEClass = createEClass(IBE_XATTRIBUTE_VALUE);

		iBeXAttributeExpressionEClass = createEClass(IBE_XATTRIBUTE_EXPRESSION);
		createEReference(iBeXAttributeExpressionEClass, IBE_XATTRIBUTE_EXPRESSION__ATTRIBUTE);
		createEReference(iBeXAttributeExpressionEClass, IBE_XATTRIBUTE_EXPRESSION__NODE);

		iBeXConstantEClass = createEClass(IBE_XCONSTANT);
		createEAttribute(iBeXConstantEClass, IBE_XCONSTANT__VALUE);
		createEAttribute(iBeXConstantEClass, IBE_XCONSTANT__STRING_VALUE);

		iBeXContextEClass = createEClass(IBE_XCONTEXT);

		iBeXContextAlternativesEClass = createEClass(IBE_XCONTEXT_ALTERNATIVES);
		createEReference(iBeXContextAlternativesEClass, IBE_XCONTEXT_ALTERNATIVES__ALTERNATIVE_PATTERNS);

		iBeXContextPatternEClass = createEClass(IBE_XCONTEXT_PATTERN);
		createEReference(iBeXContextPatternEClass, IBE_XCONTEXT_PATTERN__ATTRIBUTE_CONSTRAINT);
		createEReference(iBeXContextPatternEClass, IBE_XCONTEXT_PATTERN__INJECTIVITY_CONSTRAINTS);
		createEReference(iBeXContextPatternEClass, IBE_XCONTEXT_PATTERN__INVOCATIONS);
		createEReference(iBeXContextPatternEClass, IBE_XCONTEXT_PATTERN__LOCAL_EDGES);
		createEReference(iBeXContextPatternEClass, IBE_XCONTEXT_PATTERN__LOCAL_NODES);
		createEReference(iBeXContextPatternEClass, IBE_XCONTEXT_PATTERN__SIGNATURE_NODES);
		createEReference(iBeXContextPatternEClass, IBE_XCONTEXT_PATTERN__CSPS);

		iBeXCreatePatternEClass = createEClass(IBE_XCREATE_PATTERN);
		createEReference(iBeXCreatePatternEClass, IBE_XCREATE_PATTERN__ATTRIBUTE_ASSIGNMENTS);
		createEReference(iBeXCreatePatternEClass, IBE_XCREATE_PATTERN__CONTEXT_NODES);
		createEReference(iBeXCreatePatternEClass, IBE_XCREATE_PATTERN__CREATED_EDGES);
		createEReference(iBeXCreatePatternEClass, IBE_XCREATE_PATTERN__CREATED_NODES);

		iBeXDeletePatternEClass = createEClass(IBE_XDELETE_PATTERN);
		createEReference(iBeXDeletePatternEClass, IBE_XDELETE_PATTERN__CONTEXT_NODES);
		createEReference(iBeXDeletePatternEClass, IBE_XDELETE_PATTERN__DELETED_EDGES);
		createEReference(iBeXDeletePatternEClass, IBE_XDELETE_PATTERN__DELETED_NODES);

		iBeXEdgeEClass = createEClass(IBE_XEDGE);
		createEReference(iBeXEdgeEClass, IBE_XEDGE__SOURCE_NODE);
		createEReference(iBeXEdgeEClass, IBE_XEDGE__TARGET_NODE);
		createEReference(iBeXEdgeEClass, IBE_XEDGE__TYPE);

		iBeXEnumLiteralEClass = createEClass(IBE_XENUM_LITERAL);
		createEReference(iBeXEnumLiteralEClass, IBE_XENUM_LITERAL__LITERAL);

		iBeXNamedElementEClass = createEClass(IBE_XNAMED_ELEMENT);
		createEAttribute(iBeXNamedElementEClass, IBE_XNAMED_ELEMENT__NAME);

		iBeXNodeEClass = createEClass(IBE_XNODE);
		createEReference(iBeXNodeEClass, IBE_XNODE__INCOMING_EDGES);
		createEReference(iBeXNodeEClass, IBE_XNODE__OUTGOING_EDGES);
		createEReference(iBeXNodeEClass, IBE_XNODE__TYPE);

		iBeXNodePairEClass = createEClass(IBE_XNODE_PAIR);
		createEReference(iBeXNodePairEClass, IBE_XNODE_PAIR__VALUES);

		iBeXNodeToNodeMappingEClass = createEClass(IBE_XNODE_TO_NODE_MAPPING);
		createEReference(iBeXNodeToNodeMappingEClass, IBE_XNODE_TO_NODE_MAPPING__KEY);
		createEReference(iBeXNodeToNodeMappingEClass, IBE_XNODE_TO_NODE_MAPPING__VALUE);

		iBeXPatternEClass = createEClass(IBE_XPATTERN);

		iBeXPatternInvocationEClass = createEClass(IBE_XPATTERN_INVOCATION);
		createEAttribute(iBeXPatternInvocationEClass, IBE_XPATTERN_INVOCATION__POSITIVE);
		createEReference(iBeXPatternInvocationEClass, IBE_XPATTERN_INVOCATION__INVOKED_BY);
		createEReference(iBeXPatternInvocationEClass, IBE_XPATTERN_INVOCATION__INVOKED_PATTERN);
		createEReference(iBeXPatternInvocationEClass, IBE_XPATTERN_INVOCATION__MAPPING);

		iBeXPatternSetEClass = createEClass(IBE_XPATTERN_SET);
		createEReference(iBeXPatternSetEClass, IBE_XPATTERN_SET__CONTEXT_PATTERNS);
		createEReference(iBeXPatternSetEClass, IBE_XPATTERN_SET__CREATE_PATTERNS);
		createEReference(iBeXPatternSetEClass, IBE_XPATTERN_SET__DELETE_PATTERNS);

		iBeXCSPEClass = createEClass(IBE_XCSP);
		createEAttribute(iBeXCSPEClass, IBE_XCSP__NAME);
		createEAttribute(iBeXCSPEClass, IBE_XCSP__PACKAGE);
		createEReference(iBeXCSPEClass, IBE_XCSP__VALUES);

		iBeXStochasticAttributeValueEClass = createEClass(IBE_XSTOCHASTIC_ATTRIBUTE_VALUE);
		createEAttribute(iBeXStochasticAttributeValueEClass, IBE_XSTOCHASTIC_ATTRIBUTE_VALUE__RANGE);
		createEReference(iBeXStochasticAttributeValueEClass, IBE_XSTOCHASTIC_ATTRIBUTE_VALUE__FUNCTION);

		iBeXArithmeticValueEClass = createEClass(IBE_XARITHMETIC_VALUE);
		createEReference(iBeXArithmeticValueEClass, IBE_XARITHMETIC_VALUE__EXPRESSION);

		// Create enums
		iBeXRelationEEnum = createEEnum(IBE_XRELATION);
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
		SGTPatternModelPackage theSGTPatternModelPackage = (SGTPatternModelPackage) EPackage.Registry.INSTANCE
				.getEPackage(SGTPatternModelPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		iBeXAttributeAssignmentEClass.getESuperTypes().add(this.getIBeXAttribute());
		iBeXAttributeConstraintEClass.getESuperTypes().add(this.getIBeXAttribute());
		iBeXAttributeParameterEClass.getESuperTypes().add(this.getIBeXAttributeValue());
		iBeXAttributeParameterEClass.getESuperTypes().add(this.getIBeXNamedElement());
		iBeXAttributeExpressionEClass.getESuperTypes().add(this.getIBeXAttributeValue());
		iBeXConstantEClass.getESuperTypes().add(this.getIBeXAttributeValue());
		iBeXContextEClass.getESuperTypes().add(this.getIBeXPattern());
		iBeXContextAlternativesEClass.getESuperTypes().add(this.getIBeXContext());
		iBeXContextPatternEClass.getESuperTypes().add(this.getIBeXContext());
		iBeXCreatePatternEClass.getESuperTypes().add(this.getIBeXPattern());
		iBeXDeletePatternEClass.getESuperTypes().add(this.getIBeXPattern());
		iBeXEnumLiteralEClass.getESuperTypes().add(this.getIBeXAttributeValue());
		iBeXNodeEClass.getESuperTypes().add(this.getIBeXNamedElement());
		iBeXPatternEClass.getESuperTypes().add(this.getIBeXNamedElement());
		iBeXStochasticAttributeValueEClass.getESuperTypes().add(this.getIBeXAttributeValue());
		iBeXArithmeticValueEClass.getESuperTypes().add(this.getIBeXAttributeValue());

		// Initialize classes, features, and operations; add parameters
		initEClass(iBeXAttributeEClass, IBeXAttribute.class, "IBeXAttribute", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXAttribute_Type(), ecorePackage.getEAttribute(), null, "type", null, 0, 1,
				IBeXAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXAttribute_Node(), this.getIBeXNode(), null, "node", null, 0, 1, IBeXAttribute.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXAttribute_Value(), this.getIBeXAttributeValue(), null, "value", null, 0, 1,
				IBeXAttribute.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXAttributeAssignmentEClass, IBeXAttributeAssignment.class, "IBeXAttributeAssignment",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(iBeXAttributeConstraintEClass, IBeXAttributeConstraint.class, "IBeXAttributeConstraint",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIBeXAttributeConstraint_Relation(), this.getIBeXRelation(), "relation", null, 0, 1,
				IBeXAttributeConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXAttributeParameterEClass, IBeXAttributeParameter.class, "IBeXAttributeParameter", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(iBeXAttributeValueEClass, IBeXAttributeValue.class, "IBeXAttributeValue", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(iBeXAttributeExpressionEClass, IBeXAttributeExpression.class, "IBeXAttributeExpression",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXAttributeExpression_Attribute(), ecorePackage.getEAttribute(), null, "attribute", null, 0,
				1, IBeXAttributeExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXAttributeExpression_Node(), this.getIBeXNode(), null, "node", null, 0, 1,
				IBeXAttributeExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXConstantEClass, IBeXConstant.class, "IBeXConstant", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIBeXConstant_Value(), ecorePackage.getEJavaObject(), "value", null, 0, 1, IBeXConstant.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIBeXConstant_StringValue(), ecorePackage.getEString(), "stringValue", null, 0, 1,
				IBeXConstant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(iBeXContextEClass, IBeXContext.class, "IBeXContext", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(iBeXContextAlternativesEClass, IBeXContextAlternatives.class, "IBeXContextAlternatives",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXContextAlternatives_AlternativePatterns(), this.getIBeXContextPattern(), null,
				"alternativePatterns", null, 0, -1, IBeXContextAlternatives.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXContextPatternEClass, IBeXContextPattern.class, "IBeXContextPattern", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXContextPattern_AttributeConstraint(), this.getIBeXAttributeConstraint(), null,
				"attributeConstraint", null, 0, -1, IBeXContextPattern.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXContextPattern_InjectivityConstraints(), this.getIBeXNodePair(), null,
				"injectivityConstraints", null, 0, -1, IBeXContextPattern.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXContextPattern_Invocations(), this.getIBeXPatternInvocation(),
				this.getIBeXPatternInvocation_InvokedBy(), "invocations", null, 0, -1, IBeXContextPattern.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXContextPattern_LocalEdges(), this.getIBeXEdge(), null, "localEdges", null, 0, -1,
				IBeXContextPattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXContextPattern_LocalNodes(), this.getIBeXNode(), null, "localNodes", null, 0, -1,
				IBeXContextPattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXContextPattern_SignatureNodes(), this.getIBeXNode(), null, "signatureNodes", null, 0, -1,
				IBeXContextPattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXContextPattern_Csps(), this.getIBeXCSP(), null, "csps", null, 0, -1,
				IBeXContextPattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXCreatePatternEClass, IBeXCreatePattern.class, "IBeXCreatePattern", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXCreatePattern_AttributeAssignments(), this.getIBeXAttributeAssignment(), null,
				"attributeAssignments", null, 0, -1, IBeXCreatePattern.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXCreatePattern_ContextNodes(), this.getIBeXNode(), null, "contextNodes", null, 0, -1,
				IBeXCreatePattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXCreatePattern_CreatedEdges(), this.getIBeXEdge(), null, "createdEdges", null, 0, -1,
				IBeXCreatePattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXCreatePattern_CreatedNodes(), this.getIBeXNode(), null, "createdNodes", null, 0, -1,
				IBeXCreatePattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXDeletePatternEClass, IBeXDeletePattern.class, "IBeXDeletePattern", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXDeletePattern_ContextNodes(), this.getIBeXNode(), null, "contextNodes", null, 0, -1,
				IBeXDeletePattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXDeletePattern_DeletedEdges(), this.getIBeXEdge(), null, "deletedEdges", null, 0, -1,
				IBeXDeletePattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXDeletePattern_DeletedNodes(), this.getIBeXNode(), null, "deletedNodes", null, 0, -1,
				IBeXDeletePattern.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

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

		initEClass(iBeXEnumLiteralEClass, IBeXEnumLiteral.class, "IBeXEnumLiteral", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXEnumLiteral_Literal(), ecorePackage.getEEnumLiteral(), null, "literal", null, 0, 1,
				IBeXEnumLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXNamedElementEClass, IBeXNamedElement.class, "IBeXNamedElement", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIBeXNamedElement_Name(), ecorePackage.getEString(), "name", null, 0, 1,
				IBeXNamedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

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

		initEClass(iBeXNodePairEClass, IBeXNodePair.class, "IBeXNodePair", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXNodePair_Values(), this.getIBeXNode(), null, "values", null, 2, 2, IBeXNodePair.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXNodeToNodeMappingEClass, Map.Entry.class, "IBeXNodeToNodeMapping", !IS_ABSTRACT, !IS_INTERFACE,
				!IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXNodeToNodeMapping_Key(), this.getIBeXNode(), null, "key", null, 0, 1, Map.Entry.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXNodeToNodeMapping_Value(), this.getIBeXNode(), null, "value", null, 0, 1, Map.Entry.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXPatternEClass, IBeXPattern.class, "IBeXPattern", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

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

		initEClass(iBeXPatternSetEClass, IBeXPatternSet.class, "IBeXPatternSet", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXPatternSet_ContextPatterns(), this.getIBeXContext(), null, "contextPatterns", null, 0, -1,
				IBeXPatternSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXPatternSet_CreatePatterns(), this.getIBeXCreatePattern(), null, "createPatterns", null, 0,
				-1, IBeXPatternSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXPatternSet_DeletePatterns(), this.getIBeXDeletePattern(), null, "deletePatterns", null, 0,
				-1, IBeXPatternSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXCSPEClass, IBeXCSP.class, "IBeXCSP", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIBeXCSP_Name(), ecorePackage.getEString(), "name", null, 0, 1, IBeXCSP.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIBeXCSP_Package(), ecorePackage.getEString(), "package", null, 0, 1, IBeXCSP.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXCSP_Values(), this.getIBeXAttributeValue(), null, "values", null, 0, -1, IBeXCSP.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXStochasticAttributeValueEClass, IBeXStochasticAttributeValue.class,
				"IBeXStochasticAttributeValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIBeXStochasticAttributeValue_Range(), theSGTPatternModelPackage.getGTStochasticRange(),
				"range", null, 0, 1, IBeXStochasticAttributeValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getIBeXStochasticAttributeValue_Function(), theSGTPatternModelPackage.getGTStochasticFunction(),
				null, "function", null, 0, 1, IBeXStochasticAttributeValue.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iBeXArithmeticValueEClass, IBeXArithmeticValue.class, "IBeXArithmeticValue", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIBeXArithmeticValue_Expression(), theSGTPatternModelPackage.getGTArithmetics(), null,
				"expression", null, 0, 1, IBeXArithmeticValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(iBeXRelationEEnum, IBeXRelation.class, "IBeXRelation");
		addEEnumLiteral(iBeXRelationEEnum, IBeXRelation.GREATER_OR_EQUAL);
		addEEnumLiteral(iBeXRelationEEnum, IBeXRelation.GREATER);
		addEEnumLiteral(iBeXRelationEEnum, IBeXRelation.EQUAL);
		addEEnumLiteral(iBeXRelationEEnum, IBeXRelation.UNEQUAL);
		addEEnumLiteral(iBeXRelationEEnum, IBeXRelation.SMALLER_OR_EQUAL);
		addEEnumLiteral(iBeXRelationEEnum, IBeXRelation.SMALLER);

		// Create resource
		createResource(eNS_URI);
	}

} //IBeXPatternModelPackageImpl
