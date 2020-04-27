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
		public Adapter caseIBeXAttributeParameter(IBeXAttributeParameter object) {
			return createIBeXAttributeParameterAdapter();
		}

		@Override
		public Adapter caseIBeXAttributeValue(IBeXAttributeValue object) {
			return createIBeXAttributeValueAdapter();
		}

		@Override
		public Adapter caseIBeXAttributeExpression(IBeXAttributeExpression object) {
			return createIBeXAttributeExpressionAdapter();
		}

		@Override
		public Adapter caseIBeXConstant(IBeXConstant object) {
			return createIBeXConstantAdapter();
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
		public Adapter caseIBeXEdge(IBeXEdge object) {
			return createIBeXEdgeAdapter();
		}

		@Override
		public Adapter caseIBeXEnumLiteral(IBeXEnumLiteral object) {
			return createIBeXEnumLiteralAdapter();
		}

		@Override
		public Adapter caseIBeXNamedElement(IBeXNamedElement object) {
			return createIBeXNamedElementAdapter();
		}

		@Override
		public Adapter caseIBeXNode(IBeXNode object) {
			return createIBeXNodeAdapter();
		}

		@Override
		public Adapter caseIBeXNodePair(IBeXNodePair object) {
			return createIBeXNodePairAdapter();
		}

		@Override
		public Adapter caseIBeXNodeToNodeMapping(Map.Entry<IBeXNode, IBeXNode> object) {
			return createIBeXNodeToNodeMappingAdapter();
		}

		@Override
		public Adapter caseIBeXPattern(IBeXPattern object) {
			return createIBeXPatternAdapter();
		}

		@Override
		public Adapter caseIBeXPatternInvocation(IBeXPatternInvocation object) {
			return createIBeXPatternInvocationAdapter();
		}

		@Override
		public Adapter caseIBeXPatternSet(IBeXPatternSet object) {
			return createIBeXPatternSetAdapter();
		}

		@Override
		public Adapter caseIBeXCSP(IBeXCSP object) {
			return createIBeXCSPAdapter();
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
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNodePair <em>IBe XNode Pair</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNodePair
	 * @generated
	 */
	public Adapter createIBeXNodePairAdapter() {
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
