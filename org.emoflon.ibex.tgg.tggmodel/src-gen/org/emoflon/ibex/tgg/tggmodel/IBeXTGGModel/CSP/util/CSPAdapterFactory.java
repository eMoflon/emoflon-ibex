/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ValueExpression;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNamedElement;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.CSPPackage
 * @generated
 */
public class CSPAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static CSPPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CSPAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = CSPPackage.eINSTANCE;
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
	protected CSPSwitch<Adapter> modelSwitch = new CSPSwitch<Adapter>() {
		@Override
		public Adapter caseTGGAttributeConstraintDefinition(TGGAttributeConstraintDefinition object) {
			return createTGGAttributeConstraintDefinitionAdapter();
		}

		@Override
		public Adapter caseTGGAttributeConstraintDefinitionLibrary(TGGAttributeConstraintDefinitionLibrary object) {
			return createTGGAttributeConstraintDefinitionLibraryAdapter();
		}

		@Override
		public Adapter caseTGGAttributeConstraintParameterDefinition(TGGAttributeConstraintParameterDefinition object) {
			return createTGGAttributeConstraintParameterDefinitionAdapter();
		}

		@Override
		public Adapter caseTGGAttributeConstraintBinding(TGGAttributeConstraintBinding object) {
			return createTGGAttributeConstraintBindingAdapter();
		}

		@Override
		public Adapter caseTGGAttributeConstraintSet(TGGAttributeConstraintSet object) {
			return createTGGAttributeConstraintSetAdapter();
		}

		@Override
		public Adapter caseTGGAttributeConstraint(TGGAttributeConstraint object) {
			return createTGGAttributeConstraintAdapter();
		}

		@Override
		public Adapter caseTGGAttributeConstraintVariable(TGGAttributeConstraintVariable object) {
			return createTGGAttributeConstraintVariableAdapter();
		}

		@Override
		public Adapter caseTGGAttributeConstraintParameterValue(TGGAttributeConstraintParameterValue object) {
			return createTGGAttributeConstraintParameterValueAdapter();
		}

		@Override
		public Adapter caseTGGCSP(TGGCSP object) {
			return createTGGCSPAdapter();
		}

		@Override
		public Adapter caseTGGLocalVariable(TGGLocalVariable object) {
			return createTGGLocalVariableAdapter();
		}

		@Override
		public Adapter caseIBeXNamedElement(IBeXNamedElement object) {
			return createIBeXNamedElementAdapter();
		}

		@Override
		public Adapter caseValueExpression(ValueExpression object) {
			return createValueExpressionAdapter();
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
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintDefinition <em>TGG Attribute Constraint Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintDefinition
	 * @generated
	 */
	public Adapter createTGGAttributeConstraintDefinitionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintDefinitionLibrary <em>TGG Attribute Constraint Definition Library</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintDefinitionLibrary
	 * @generated
	 */
	public Adapter createTGGAttributeConstraintDefinitionLibraryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintParameterDefinition <em>TGG Attribute Constraint Parameter Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintParameterDefinition
	 * @generated
	 */
	public Adapter createTGGAttributeConstraintParameterDefinitionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintBinding <em>TGG Attribute Constraint Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintBinding
	 * @generated
	 */
	public Adapter createTGGAttributeConstraintBindingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintSet <em>TGG Attribute Constraint Set</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintSet
	 * @generated
	 */
	public Adapter createTGGAttributeConstraintSetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraint <em>TGG Attribute Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraint
	 * @generated
	 */
	public Adapter createTGGAttributeConstraintAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintVariable <em>TGG Attribute Constraint Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintVariable
	 * @generated
	 */
	public Adapter createTGGAttributeConstraintVariableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintParameterValue <em>TGG Attribute Constraint Parameter Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintParameterValue
	 * @generated
	 */
	public Adapter createTGGAttributeConstraintParameterValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGCSP <em>TGGCSP</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGCSP
	 * @generated
	 */
	public Adapter createTGGCSPAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGLocalVariable <em>TGG Local Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGLocalVariable
	 * @generated
	 */
	public Adapter createTGGLocalVariableAdapter() {
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

} //CSPAdapterFactory
