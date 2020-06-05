/**
 */
package org.emoflon.ibex.IBeXDisjunctPatternModel.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.emoflon.ibex.IBeXDisjunctPatternModel.*;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNamedElement;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPattern;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctPatternModelPackage
 * @generated
 */
public class IBeXDisjunctPatternModelAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static IBeXDisjunctPatternModelPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXDisjunctPatternModelAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = IBeXDisjunctPatternModelPackage.eINSTANCE;
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
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IBeXDisjunctPatternModelSwitch<Adapter> modelSwitch =
		new IBeXDisjunctPatternModelSwitch<Adapter>() {
			@Override
			public Adapter caseIBeXDisjunctContextPattern(IBeXDisjunctContextPattern object) {
				return createIBeXDisjunctContextPatternAdapter();
			}
			@Override
			public Adapter caseIBeXDisjunctAttributes(IBeXDisjunctAttributes object) {
				return createIBeXDisjunctAttributesAdapter();
			}
			@Override
			public Adapter caseIBeXDependentInjectivityConstraints(IBeXDependentInjectivityConstraints object) {
				return createIBeXDependentInjectivityConstraintsAdapter();
			}
			@Override
			public Adapter caseIBexDisjunctInjectivityConstraint(IBexDisjunctInjectivityConstraint object) {
				return createIBexDisjunctInjectivityConstraintAdapter();
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
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctContextPattern <em>IBe XDisjunct Context Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctContextPattern
	 * @generated
	 */
	public Adapter createIBeXDisjunctContextPatternAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctAttributes <em>IBe XDisjunct Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctAttributes
	 * @generated
	 */
	public Adapter createIBeXDisjunctAttributesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDependentInjectivityConstraints <em>IBe XDependent Injectivity Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDependentInjectivityConstraints
	 * @generated
	 */
	public Adapter createIBeXDependentInjectivityConstraintsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.IBeXDisjunctPatternModel.IBexDisjunctInjectivityConstraint <em>IBex Disjunct Injectivity Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBexDisjunctInjectivityConstraint
	 * @generated
	 */
	public Adapter createIBexDisjunctInjectivityConstraintAdapter() {
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

} //IBeXDisjunctPatternModelAdapterFactory
