/**
 */
package org.emoflon.ibex.common.coremodel.IBeXCoreModel.util;

import java.util.Map;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.*;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ArithmeticExpression;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ArithmeticValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ValueExpression;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage
 * @generated
 */
public class IBeXCoreModelAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static IBeXCoreModelPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXCoreModelAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = IBeXCoreModelPackage.eINSTANCE;
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
	protected IBeXCoreModelSwitch<Adapter> modelSwitch = new IBeXCoreModelSwitch<Adapter>() {
		@Override
		public Adapter caseIBeXNamedElement(IBeXNamedElement object) {
			return createIBeXNamedElementAdapter();
		}

		@Override
		public Adapter caseIBeXModel(IBeXModel object) {
			return createIBeXModelAdapter();
		}

		@Override
		public Adapter caseIBeXPatternSet(IBeXPatternSet object) {
			return createIBeXPatternSetAdapter();
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
		public Adapter caseIBeXNode(IBeXNode object) {
			return createIBeXNodeAdapter();
		}

		@Override
		public Adapter caseIBeXEdge(IBeXEdge object) {
			return createIBeXEdgeAdapter();
		}

		@Override
		public Adapter caseIBeXPattern(IBeXPattern object) {
			return createIBeXPatternAdapter();
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
		public Adapter caseIBeXNodeMapping(Map.Entry<IBeXNode, IBeXNode> object) {
			return createIBeXNodeMappingAdapter();
		}

		@Override
		public Adapter caseIBeXEnumValue(IBeXEnumValue object) {
			return createIBeXEnumValueAdapter();
		}

		@Override
		public Adapter caseIBeXStringValue(IBeXStringValue object) {
			return createIBeXStringValueAdapter();
		}

		@Override
		public Adapter caseIBeXBooleanValue(IBeXBooleanValue object) {
			return createIBeXBooleanValueAdapter();
		}

		@Override
		public Adapter caseIBeXAttributeValue(IBeXAttributeValue object) {
			return createIBeXAttributeValueAdapter();
		}

		@Override
		public Adapter caseIBeXMatchCountValue(IBeXMatchCountValue object) {
			return createIBeXMatchCountValueAdapter();
		}

		@Override
		public Adapter caseIBeXRule(IBeXRule object) {
			return createIBeXRuleAdapter();
		}

		@Override
		public Adapter caseIBeXRuleDelta(IBeXRuleDelta object) {
			return createIBeXRuleDeltaAdapter();
		}

		@Override
		public Adapter caseIBeXAttributeAssignment(IBeXAttributeAssignment object) {
			return createIBeXAttributeAssignmentAdapter();
		}

		@Override
		public Adapter caseValueExpression(ValueExpression object) {
			return createValueExpressionAdapter();
		}

		@Override
		public Adapter caseArithmeticExpression(ArithmeticExpression object) {
			return createArithmeticExpressionAdapter();
		}

		@Override
		public Adapter caseArithmeticValue(ArithmeticValue object) {
			return createArithmeticValueAdapter();
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
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNamedElement <em>IBe XNamed Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNamedElement
	 * @generated
	 */
	public Adapter createIBeXNamedElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModel <em>IBe XModel</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModel
	 * @generated
	 */
	public Adapter createIBeXModelAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternSet <em>IBe XPattern Set</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternSet
	 * @generated
	 */
	public Adapter createIBeXPatternSetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNodeSet <em>IBe XNode Set</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNodeSet
	 * @generated
	 */
	public Adapter createIBeXNodeSetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdgeSet <em>IBe XEdge Set</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdgeSet
	 * @generated
	 */
	public Adapter createIBeXEdgeSetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode <em>IBe XNode</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode
	 * @generated
	 */
	public Adapter createIBeXNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge <em>IBe XEdge</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge
	 * @generated
	 */
	public Adapter createIBeXEdgeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern <em>IBe XPattern</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern
	 * @generated
	 */
	public Adapter createIBeXPatternAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXInjectivityConstraint <em>IBe XInjectivity Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXInjectivityConstraint
	 * @generated
	 */
	public Adapter createIBeXInjectivityConstraintAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternInvocation <em>IBe XPattern Invocation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternInvocation
	 * @generated
	 */
	public Adapter createIBeXPatternInvocationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link java.util.Map.Entry <em>IBe XNode Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see java.util.Map.Entry
	 * @generated
	 */
	public Adapter createIBeXNodeMappingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEnumValue <em>IBe XEnum Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEnumValue
	 * @generated
	 */
	public Adapter createIBeXEnumValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXStringValue <em>IBe XString Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXStringValue
	 * @generated
	 */
	public Adapter createIBeXStringValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXBooleanValue <em>IBe XBoolean Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXBooleanValue
	 * @generated
	 */
	public Adapter createIBeXBooleanValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeValue <em>IBe XAttribute Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeValue
	 * @generated
	 */
	public Adapter createIBeXAttributeValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXMatchCountValue <em>IBe XMatch Count Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXMatchCountValue
	 * @generated
	 */
	public Adapter createIBeXMatchCountValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRule <em>IBe XRule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRule
	 * @generated
	 */
	public Adapter createIBeXRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRuleDelta <em>IBe XRule Delta</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRuleDelta
	 * @generated
	 */
	public Adapter createIBeXRuleDeltaAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeAssignment <em>IBe XAttribute Assignment</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeAssignment
	 * @generated
	 */
	public Adapter createIBeXAttributeAssignmentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ValueExpression <em>Value Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ValueExpression
	 * @generated
	 */
	public Adapter createValueExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ArithmeticExpression <em>Arithmetic Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ArithmeticExpression
	 * @generated
	 */
	public Adapter createArithmeticExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ArithmeticValue <em>Arithmetic Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ArithmeticValue
	 * @generated
	 */
	public Adapter createArithmeticValueAdapter() {
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

} //IBeXCoreModelAdapterFactory
