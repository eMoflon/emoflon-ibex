/**
 */
package org.emoflon.ibex.gt.SGTPatternModel.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.emoflon.ibex.gt.SGTPatternModel.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.emoflon.ibex.gt.SGTPatternModel.SGTPatternModelPackage
 * @generated
 */
public class SGTPatternModelAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static SGTPatternModelPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGTPatternModelAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = SGTPatternModelPackage.eINSTANCE;
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
	protected SGTPatternModelSwitch<Adapter> modelSwitch = new SGTPatternModelSwitch<Adapter>() {
		@Override
		public Adapter caseGTStochasticNamedElement(GTStochasticNamedElement object) {
			return createGTStochasticNamedElementAdapter();
		}

		@Override
		public Adapter caseGTStochasticNode(GTStochasticNode object) {
			return createGTStochasticNodeAdapter();
		}

		@Override
		public Adapter caseGTAttribute(GTAttribute object) {
			return createGTAttributeAdapter();
		}

		@Override
		public Adapter caseGTStochasticFunction(GTStochasticFunction object) {
			return createGTStochasticFunctionAdapter();
		}

		@Override
		public Adapter caseGTNumber(GTNumber object) {
			return createGTNumberAdapter();
		}

		@Override
		public Adapter caseGTArithmetics(GTArithmetics object) {
			return createGTArithmeticsAdapter();
		}

		@Override
		public Adapter caseGTTwoParameterCalculation(GTTwoParameterCalculation object) {
			return createGTTwoParameterCalculationAdapter();
		}

		@Override
		public Adapter caseGTOneParameterCalculation(GTOneParameterCalculation object) {
			return createGTOneParameterCalculationAdapter();
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
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.gt.SGTPatternModel.GTStochasticNamedElement <em>GT Stochastic Named Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.gt.SGTPatternModel.GTStochasticNamedElement
	 * @generated
	 */
	public Adapter createGTStochasticNamedElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.gt.SGTPatternModel.GTStochasticNode <em>GT Stochastic Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.gt.SGTPatternModel.GTStochasticNode
	 * @generated
	 */
	public Adapter createGTStochasticNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.gt.SGTPatternModel.GTAttribute <em>GT Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.gt.SGTPatternModel.GTAttribute
	 * @generated
	 */
	public Adapter createGTAttributeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.gt.SGTPatternModel.GTStochasticFunction <em>GT Stochastic Function</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.gt.SGTPatternModel.GTStochasticFunction
	 * @generated
	 */
	public Adapter createGTStochasticFunctionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.gt.SGTPatternModel.GTNumber <em>GT Number</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.gt.SGTPatternModel.GTNumber
	 * @generated
	 */
	public Adapter createGTNumberAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.gt.SGTPatternModel.GTArithmetics <em>GT Arithmetics</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.gt.SGTPatternModel.GTArithmetics
	 * @generated
	 */
	public Adapter createGTArithmeticsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.gt.SGTPatternModel.GTTwoParameterCalculation <em>GT Two Parameter Calculation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.gt.SGTPatternModel.GTTwoParameterCalculation
	 * @generated
	 */
	public Adapter createGTTwoParameterCalculationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.gt.SGTPatternModel.GTOneParameterCalculation <em>GT One Parameter Calculation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.gt.SGTPatternModel.GTOneParameterCalculation
	 * @generated
	 */
	public Adapter createGTOneParameterCalculationAdapter() {
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

} //SGTPatternModelAdapterFactory
