/**
 */
package IBeXLanguage.util;

import IBeXLanguage.*;

import java.util.Map;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see IBeXLanguage.IBeXLanguagePackage
 * @generated
 */
public class IBeXLanguageAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static IBeXLanguagePackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXLanguageAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = IBeXLanguagePackage.eINSTANCE;
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
	protected IBeXLanguageSwitch<Adapter> modelSwitch = new IBeXLanguageSwitch<Adapter>() {
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
	 * Creates a new adapter for an object of class '{@link IBeXLanguage.IBeXAttribute <em>IBe XAttribute</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see IBeXLanguage.IBeXAttribute
	 * @generated
	 */
	public Adapter createIBeXAttributeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link IBeXLanguage.IBeXAttributeAssignment <em>IBe XAttribute Assignment</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see IBeXLanguage.IBeXAttributeAssignment
	 * @generated
	 */
	public Adapter createIBeXAttributeAssignmentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link IBeXLanguage.IBeXAttributeConstraint <em>IBe XAttribute Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see IBeXLanguage.IBeXAttributeConstraint
	 * @generated
	 */
	public Adapter createIBeXAttributeConstraintAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link IBeXLanguage.IBeXAttributeParameter <em>IBe XAttribute Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see IBeXLanguage.IBeXAttributeParameter
	 * @generated
	 */
	public Adapter createIBeXAttributeParameterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link IBeXLanguage.IBeXAttributeValue <em>IBe XAttribute Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see IBeXLanguage.IBeXAttributeValue
	 * @generated
	 */
	public Adapter createIBeXAttributeValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link IBeXLanguage.IBeXAttributeExpression <em>IBe XAttribute Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see IBeXLanguage.IBeXAttributeExpression
	 * @generated
	 */
	public Adapter createIBeXAttributeExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link IBeXLanguage.IBeXConstant <em>IBe XConstant</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see IBeXLanguage.IBeXConstant
	 * @generated
	 */
	public Adapter createIBeXConstantAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link IBeXLanguage.IBeXContext <em>IBe XContext</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see IBeXLanguage.IBeXContext
	 * @generated
	 */
	public Adapter createIBeXContextAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link IBeXLanguage.IBeXContextAlternatives <em>IBe XContext Alternatives</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see IBeXLanguage.IBeXContextAlternatives
	 * @generated
	 */
	public Adapter createIBeXContextAlternativesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link IBeXLanguage.IBeXContextPattern <em>IBe XContext Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see IBeXLanguage.IBeXContextPattern
	 * @generated
	 */
	public Adapter createIBeXContextPatternAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link IBeXLanguage.IBeXCreatePattern <em>IBe XCreate Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see IBeXLanguage.IBeXCreatePattern
	 * @generated
	 */
	public Adapter createIBeXCreatePatternAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link IBeXLanguage.IBeXDeletePattern <em>IBe XDelete Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see IBeXLanguage.IBeXDeletePattern
	 * @generated
	 */
	public Adapter createIBeXDeletePatternAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link IBeXLanguage.IBeXEdge <em>IBe XEdge</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see IBeXLanguage.IBeXEdge
	 * @generated
	 */
	public Adapter createIBeXEdgeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link IBeXLanguage.IBeXEnumLiteral <em>IBe XEnum Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see IBeXLanguage.IBeXEnumLiteral
	 * @generated
	 */
	public Adapter createIBeXEnumLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link IBeXLanguage.IBeXNamedElement <em>IBe XNamed Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see IBeXLanguage.IBeXNamedElement
	 * @generated
	 */
	public Adapter createIBeXNamedElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link IBeXLanguage.IBeXNode <em>IBe XNode</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see IBeXLanguage.IBeXNode
	 * @generated
	 */
	public Adapter createIBeXNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link IBeXLanguage.IBeXNodePair <em>IBe XNode Pair</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see IBeXLanguage.IBeXNodePair
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
	 * Creates a new adapter for an object of class '{@link IBeXLanguage.IBeXPattern <em>IBe XPattern</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see IBeXLanguage.IBeXPattern
	 * @generated
	 */
	public Adapter createIBeXPatternAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link IBeXLanguage.IBeXPatternInvocation <em>IBe XPattern Invocation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see IBeXLanguage.IBeXPatternInvocation
	 * @generated
	 */
	public Adapter createIBeXPatternInvocationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link IBeXLanguage.IBeXPatternSet <em>IBe XPattern Set</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see IBeXLanguage.IBeXPatternSet
	 * @generated
	 */
	public Adapter createIBeXPatternSetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link IBeXLanguage.IBeXCSP <em>IBe XCSP</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see IBeXLanguage.IBeXCSP
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

} //IBeXLanguageAdapterFactory
