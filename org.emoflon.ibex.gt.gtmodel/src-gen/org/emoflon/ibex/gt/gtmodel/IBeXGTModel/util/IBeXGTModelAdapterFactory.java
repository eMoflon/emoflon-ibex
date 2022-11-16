/**
 */
package org.emoflon.ibex.gt.gtmodel.IBeXGTModel.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeAssignment;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ArithmeticExpression;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ArithmeticValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.BooleanExpression;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.BooleanValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ValueExpression;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModel;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNamedElement;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRule;

import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelPackage
 * @generated
 */
public class IBeXGTModelAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static IBeXGTModelPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXGTModelAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = IBeXGTModelPackage.eINSTANCE;
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
	protected IBeXGTModelSwitch<Adapter> modelSwitch = new IBeXGTModelSwitch<Adapter>() {
		@Override
		public Adapter caseGTModel(GTModel object) {
			return createGTModelAdapter();
		}

		@Override
		public Adapter caseGTRuleSet(GTRuleSet object) {
			return createGTRuleSetAdapter();
		}

		@Override
		public Adapter caseGTPattern(GTPattern object) {
			return createGTPatternAdapter();
		}

		@Override
		public Adapter caseGTRule(GTRule object) {
			return createGTRuleAdapter();
		}

		@Override
		public Adapter caseGTForEachExpression(GTForEachExpression object) {
			return createGTForEachExpressionAdapter();
		}

		@Override
		public Adapter caseGTParameter(GTParameter object) {
			return createGTParameterAdapter();
		}

		@Override
		public Adapter caseGTParameterValue(GTParameterValue object) {
			return createGTParameterValueAdapter();
		}

		@Override
		public Adapter caseGTIteratorEdge(GTIteratorEdge object) {
			return createGTIteratorEdgeAdapter();
		}

		@Override
		public Adapter caseGTIteratorAttributeAssignment(GTIteratorAttributeAssignment object) {
			return createGTIteratorAttributeAssignmentAdapter();
		}

		@Override
		public Adapter caseGTIteratorAttributeReference(GTIteratorAttributeReference object) {
			return createGTIteratorAttributeReferenceAdapter();
		}

		@Override
		public Adapter caseGTWatchDog(GTWatchDog object) {
			return createGTWatchDogAdapter();
		}

		@Override
		public Adapter caseIBeXNamedElement(IBeXNamedElement object) {
			return createIBeXNamedElementAdapter();
		}

		@Override
		public Adapter caseIBeXModel(IBeXModel object) {
			return createIBeXModelAdapter();
		}

		@Override
		public Adapter caseIBeXPattern(IBeXPattern object) {
			return createIBeXPatternAdapter();
		}

		@Override
		public Adapter caseIBeXRule(IBeXRule object) {
			return createIBeXRuleAdapter();
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
		public Adapter caseIBeXEdge(IBeXEdge object) {
			return createIBeXEdgeAdapter();
		}

		@Override
		public Adapter caseIBeXAttributeAssignment(IBeXAttributeAssignment object) {
			return createIBeXAttributeAssignmentAdapter();
		}

		@Override
		public Adapter caseBooleanExpression(BooleanExpression object) {
			return createBooleanExpressionAdapter();
		}

		@Override
		public Adapter caseBooleanValue(BooleanValue object) {
			return createBooleanValueAdapter();
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
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTModel <em>GT Model</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTModel
	 * @generated
	 */
	public Adapter createGTModelAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTRuleSet <em>GT Rule Set</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTRuleSet
	 * @generated
	 */
	public Adapter createGTRuleSetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern <em>GT Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern
	 * @generated
	 */
	public Adapter createGTPatternAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTRule <em>GT Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTRule
	 * @generated
	 */
	public Adapter createGTRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTForEachExpression <em>GT For Each Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTForEachExpression
	 * @generated
	 */
	public Adapter createGTForEachExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTParameter <em>GT Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTParameter
	 * @generated
	 */
	public Adapter createGTParameterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTParameterValue <em>GT Parameter Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTParameterValue
	 * @generated
	 */
	public Adapter createGTParameterValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTIteratorEdge <em>GT Iterator Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTIteratorEdge
	 * @generated
	 */
	public Adapter createGTIteratorEdgeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTIteratorAttributeAssignment <em>GT Iterator Attribute Assignment</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTIteratorAttributeAssignment
	 * @generated
	 */
	public Adapter createGTIteratorAttributeAssignmentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTIteratorAttributeReference <em>GT Iterator Attribute Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTIteratorAttributeReference
	 * @generated
	 */
	public Adapter createGTIteratorAttributeReferenceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTWatchDog <em>GT Watch Dog</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTWatchDog
	 * @generated
	 */
	public Adapter createGTWatchDogAdapter() {
		return null;
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
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.BooleanExpression <em>Boolean Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.BooleanExpression
	 * @generated
	 */
	public Adapter createBooleanExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.BooleanValue <em>Boolean Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.BooleanValue
	 * @generated
	 */
	public Adapter createBooleanValueAdapter() {
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

} //IBeXGTModelAdapterFactory
