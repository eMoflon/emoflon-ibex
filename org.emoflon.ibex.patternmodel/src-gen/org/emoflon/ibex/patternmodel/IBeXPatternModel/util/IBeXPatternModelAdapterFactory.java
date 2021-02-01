/**
 */
package org.emoflon.ibex.patternmodel.IBeXPatternModel.util;

import java.util.Map;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage
 * @generated
 */
public class IBeXPatternModelAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static IBeXPatternModelPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXPatternModelAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = IBeXPatternModelPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject) object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IBeXPatternModelSwitch<Adapter> modelSwitch = new IBeXPatternModelSwitch<Adapter>() {
		@Override
		public Adapter caseIBeXModel(IBeXModel object) {
			return createIBeXModelAdapter();
		}

		@Override
		public Adapter caseIBeXPatternSet(IBeXPatternSet object) {
			return createIBeXPatternSetAdapter();
		}

		@Override
		public Adapter caseIBeXRuleSet(IBeXRuleSet object) {
			return createIBeXRuleSetAdapter();
		}

		@Override
		public Adapter caseIBeXNodeSet(IBeXNodeSet object) {
			return createIBeXNodeSetAdapter();
		}

		@Override
		public Adapter caseIBeXEdgeSet(IBeXEdgeSet object) {
			return createIBeXEdgeSetAdapter();
		}

		@Override
		public Adapter caseIBeXNamedElement(IBeXNamedElement object) {
			return createIBeXNamedElementAdapter();
		}

		@Override
		public Adapter caseIBeXPattern(IBeXPattern object) {
			return createIBeXPatternAdapter();
		}

		@Override
		public Adapter caseIBeXContext(IBeXContext object) {
			return createIBeXContextAdapter();
		}

		@Override
		public Adapter caseIBeXContextAlternatives(IBeXContextAlternatives object) {
			return createIBeXContextAlternativesAdapter();
		}

		@Override
		public Adapter caseIBeXContextPattern(IBeXContextPattern object) {
			return createIBeXContextPatternAdapter();
		}

		@Override
		public Adapter caseIBeXCreatePattern(IBeXCreatePattern object) {
			return createIBeXCreatePatternAdapter();
		}

		@Override
		public Adapter caseIBeXDeletePattern(IBeXDeletePattern object) {
			return createIBeXDeletePatternAdapter();
		}

		@Override
		public Adapter caseIBeXNode(IBeXNode object) {
			return createIBeXNodeAdapter();
		}

		@Override
		public Adapter caseIBeXEdge(IBeXEdge object) {
			return createIBeXEdgeAdapter();
		}

		@Override
		public Adapter caseIBeXParameter(IBeXParameter object) {
			return createIBeXParameterAdapter();
		}

		@Override
		public Adapter caseIBeXAttribute(IBeXAttribute object) {
			return createIBeXAttributeAdapter();
		}

		@Override
		public Adapter caseIBeXAttributeAssignment(IBeXAttributeAssignment object) {
			return createIBeXAttributeAssignmentAdapter();
		}

		@Override
		public Adapter caseIBeXAttributeConstraint(IBeXAttributeConstraint object) {
			return createIBeXAttributeConstraintAdapter();
		}

		@Override
		public Adapter caseIBeXAttributeValue(IBeXAttributeValue object) {
			return createIBeXAttributeValueAdapter();
		}

		@Override
		public Adapter caseIBeXAttributeParameter(IBeXAttributeParameter object) {
			return createIBeXAttributeParameterAdapter();
		}

		@Override
		public Adapter caseIBeXConstant(IBeXConstant object) {
			return createIBeXConstantAdapter();
		}

		@Override
		public Adapter caseIBeXEnumLiteral(IBeXEnumLiteral object) {
			return createIBeXEnumLiteralAdapter();
		}

		@Override
		public Adapter caseIBeXAttributeExpression(IBeXAttributeExpression object) {
			return createIBeXAttributeExpressionAdapter();
		}

		@Override
		public Adapter caseIBeXStochasticAttributeValue(IBeXStochasticAttributeValue object) {
			return createIBeXStochasticAttributeValueAdapter();
		}

		@Override
		public Adapter caseIBeXArithmeticValue(IBeXArithmeticValue object) {
			return createIBeXArithmeticValueAdapter();
		}

		@Override
		public Adapter caseIBeXInjectivityConstraint(IBeXInjectivityConstraint object) {
			return createIBeXInjectivityConstraintAdapter();
		}

		@Override
		public Adapter caseIBeXPatternInvocation(IBeXPatternInvocation object) {
			return createIBeXPatternInvocationAdapter();
		}

		@Override
		public Adapter caseIBeXNodeToNodeMapping(Map.Entry<IBeXNode, IBeXNode> object) {
			return createIBeXNodeToNodeMappingAdapter();
		}

		@Override
		public Adapter caseIBeXCSP(IBeXCSP object) {
			return createIBeXCSPAdapter();
		}

		@Override
		public Adapter caseIBeXRule(IBeXRule object) {
			return createIBeXRuleAdapter();
		}

		@Override
		public Adapter caseIBeXProbability(IBeXProbability object) {
			return createIBeXProbabilityAdapter();
		}

		@Override
		public Adapter caseIBeXArithmeticConstraint(IBeXArithmeticConstraint object) {
			return createIBeXArithmeticConstraintAdapter();
		}

		@Override
		public Adapter caseIBeXArithmeticExpression(IBeXArithmeticExpression object) {
			return createIBeXArithmeticExpressionAdapter();
		}

		@Override
		public Adapter caseIBeXUnaryExpression(IBeXUnaryExpression object) {
			return createIBeXUnaryExpressionAdapter();
		}

		@Override
		public Adapter caseIBeXBinaryExpression(IBeXBinaryExpression object) {
			return createIBeXBinaryExpressionAdapter();
		}

		@Override
		public Adapter caseIBeXArithmeticValueLiteral(IBeXArithmeticValueLiteral object) {
			return createIBeXArithmeticValueLiteralAdapter();
		}

		@Override
		public Adapter caseIBeXProbabilityDistribution(IBeXProbabilityDistribution object) {
			return createIBeXProbabilityDistributionAdapter();
		}

		@Override
		public Adapter caseIBeXArithmeticAttribute(IBeXArithmeticAttribute object) {
			return createIBeXArithmeticAttributeAdapter();
		}

		@Override
		public Adapter caseIBeXMatchCount(IBeXMatchCount object) {
			return createIBeXMatchCountAdapter();
		}

		@Override
		public Adapter caseIBeXConstraint(IBeXConstraint object) {
			return createIBeXConstraintAdapter();
		}

		@Override
		public Adapter caseIBeXDisjointContextPattern(IBeXDisjointContextPattern object) {
			return createIBeXDisjointContextPatternAdapter();
		}

		@Override
		public Adapter caseIBexDisjointInjectivityConstraint(IBexDisjointInjectivityConstraint object) {
			return createIBexDisjointInjectivityConstraintAdapter();
		}

		@Override
		public Adapter caseIBeXDisjointAttribute(IBeXDisjointAttribute object) {
			return createIBeXDisjointAttributeAdapter();
		}

		@Override
		public Adapter caseIBeXInterdependentInjectivityConstraints(IBeXInterdependentInjectivityConstraints object) {
			return createIBeXInterdependentInjectivityConstraintsAdapter();
		}

		@Override
		public Adapter caseIBeXInterdependentAttributes(IBeXInterdependentAttributes object) {
			return createIBeXInterdependentAttributesAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject) target);
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXModel <em>IBe XModel</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXModel
	 * @generated
	 */
	public Adapter createIBeXModelAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternSet <em>IBe XPattern Set</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternSet
	 * @generated
	 */
	public Adapter createIBeXPatternSetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRuleSet <em>IBe XRule Set</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRuleSet
	 * @generated
	 */
	public Adapter createIBeXRuleSetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNodeSet <em>IBe XNode Set</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNodeSet
	 * @generated
	 */
	public Adapter createIBeXNodeSetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEdgeSet <em>IBe XEdge Set</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEdgeSet
	 * @generated
	 */
	public Adapter createIBeXEdgeSetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNamedElement <em>IBe XNamed Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNamedElement
	 * @generated
	 */
	public Adapter createIBeXNamedElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPattern <em>IBe XPattern</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPattern
	 * @generated
	 */
	public Adapter createIBeXPatternAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext <em>IBe XContext</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext
	 * @generated
	 */
	public Adapter createIBeXContextAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextAlternatives <em>IBe XContext Alternatives</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextAlternatives
	 * @generated
	 */
	public Adapter createIBeXContextAlternativesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern <em>IBe XContext Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern
	 * @generated
	 */
	public Adapter createIBeXContextPatternAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCreatePattern <em>IBe XCreate Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCreatePattern
	 * @generated
	 */
	public Adapter createIBeXCreatePatternAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDeletePattern <em>IBe XDelete Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDeletePattern
	 * @generated
	 */
	public Adapter createIBeXDeletePatternAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode <em>IBe XNode</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode
	 * @generated
	 */
	public Adapter createIBeXNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEdge <em>IBe XEdge</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEdge
	 * @generated
	 */
	public Adapter createIBeXEdgeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXParameter <em>IBe XParameter</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXParameter
	 * @generated
	 */
	public Adapter createIBeXParameterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttribute <em>IBe XAttribute</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttribute
	 * @generated
	 */
	public Adapter createIBeXAttributeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeAssignment <em>IBe XAttribute Assignment</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeAssignment
	 * @generated
	 */
	public Adapter createIBeXAttributeAssignmentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeConstraint <em>IBe XAttribute Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeConstraint
	 * @generated
	 */
	public Adapter createIBeXAttributeConstraintAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeValue <em>IBe XAttribute Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeValue
	 * @generated
	 */
	public Adapter createIBeXAttributeValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeParameter <em>IBe XAttribute Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeParameter
	 * @generated
	 */
	public Adapter createIBeXAttributeParameterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXConstant <em>IBe XConstant</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXConstant
	 * @generated
	 */
	public Adapter createIBeXConstantAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEnumLiteral <em>IBe XEnum Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEnumLiteral
	 * @generated
	 */
	public Adapter createIBeXEnumLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeExpression <em>IBe XAttribute Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeExpression
	 * @generated
	 */
	public Adapter createIBeXAttributeExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXStochasticAttributeValue <em>IBe XStochastic Attribute Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXStochasticAttributeValue
	 * @generated
	 */
	public Adapter createIBeXStochasticAttributeValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticValue <em>IBe XArithmetic Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticValue
	 * @generated
	 */
	public Adapter createIBeXArithmeticValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXInjectivityConstraint <em>IBe XInjectivity Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXInjectivityConstraint
	 * @generated
	 */
	public Adapter createIBeXInjectivityConstraintAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternInvocation <em>IBe XPattern Invocation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternInvocation
	 * @generated
	 */
	public Adapter createIBeXPatternInvocationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link java.util.Map.Entry <em>IBe XNode To Node Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see java.util.Map.Entry
	 * @generated
	 */
	public Adapter createIBeXNodeToNodeMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCSP <em>IBe XCSP</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCSP
	 * @generated
	 */
	public Adapter createIBeXCSPAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule <em>IBe XRule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule
	 * @generated
	 */
	public Adapter createIBeXRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXProbability <em>IBe XProbability</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXProbability
	 * @generated
	 */
	public Adapter createIBeXProbabilityAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticConstraint <em>IBe XArithmetic Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticConstraint
	 * @generated
	 */
	public Adapter createIBeXArithmeticConstraintAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticExpression <em>IBe XArithmetic Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticExpression
	 * @generated
	 */
	public Adapter createIBeXArithmeticExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXUnaryExpression <em>IBe XUnary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXUnaryExpression
	 * @generated
	 */
	public Adapter createIBeXUnaryExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXBinaryExpression <em>IBe XBinary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXBinaryExpression
	 * @generated
	 */
	public Adapter createIBeXBinaryExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticValueLiteral <em>IBe XArithmetic Value Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticValueLiteral
	 * @generated
	 */
	public Adapter createIBeXArithmeticValueLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXProbabilityDistribution <em>IBe XProbability Distribution</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXProbabilityDistribution
	 * @generated
	 */
	public Adapter createIBeXProbabilityDistributionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticAttribute <em>IBe XArithmetic Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticAttribute
	 * @generated
	 */
	public Adapter createIBeXArithmeticAttributeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXMatchCount <em>IBe XMatch Count</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXMatchCount
	 * @generated
	 */
	public Adapter createIBeXMatchCountAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXConstraint <em>IBe XConstraint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXConstraint
	 * @generated
	 */
	public Adapter createIBeXConstraintAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjointContextPattern <em>IBe XDisjoint Context Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjointContextPattern
	 * @generated
	 */
	public Adapter createIBeXDisjointContextPatternAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBexDisjointInjectivityConstraint <em>IBex Disjoint Injectivity Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBexDisjointInjectivityConstraint
	 * @generated
	 */
	public Adapter createIBexDisjointInjectivityConstraintAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjointAttribute <em>IBe XDisjoint Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjointAttribute
	 * @generated
	 */
	public Adapter createIBeXDisjointAttributeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXInterdependentInjectivityConstraints <em>IBe XInterdependent Injectivity Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXInterdependentInjectivityConstraints
	 * @generated
	 */
	public Adapter createIBeXInterdependentInjectivityConstraintsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXInterdependentAttributes <em>IBe XInterdependent Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXInterdependentAttributes
	 * @generated
	 */
	public Adapter createIBeXInterdependentAttributesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //IBeXPatternModelAdapterFactory
