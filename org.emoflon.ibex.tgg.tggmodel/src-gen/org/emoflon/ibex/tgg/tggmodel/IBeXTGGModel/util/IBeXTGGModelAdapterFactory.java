/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModel;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNamedElement;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRule;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage
 * @generated
 */
public class IBeXTGGModelAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static IBeXTGGModelPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXTGGModelAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = IBeXTGGModelPackage.eINSTANCE;
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
	protected IBeXTGGModelSwitch<Adapter> modelSwitch = new IBeXTGGModelSwitch<Adapter>() {
		@Override
		public Adapter caseTGGModel(TGGModel object) {
			return createTGGModelAdapter();
		}

		@Override
		public Adapter caseTGGRuleSet(TGGRuleSet object) {
			return createTGGRuleSetAdapter();
		}

		@Override
		public Adapter caseTGGRule(TGGRule object) {
			return createTGGRuleAdapter();
		}

		@Override
		public Adapter caseTGGNode(TGGNode object) {
			return createTGGNodeAdapter();
		}

		@Override
		public Adapter caseTGGRuleCorrespondence(TGGRuleCorrespondence object) {
			return createTGGRuleCorrespondenceAdapter();
		}

		@Override
		public Adapter caseTGGEdge(TGGEdge object) {
			return createTGGEdgeAdapter();
		}

		@Override
		public Adapter caseTGGRuleElement(TGGRuleElement object) {
			return createTGGRuleElementAdapter();
		}

		@Override
		public Adapter caseTGGAttributeConstraintLibrary(TGGAttributeConstraintLibrary object) {
			return createTGGAttributeConstraintLibraryAdapter();
		}

		@Override
		public Adapter caseTGGAttributeConstraintDefinitionLibrary(TGGAttributeConstraintDefinitionLibrary object) {
			return createTGGAttributeConstraintDefinitionLibraryAdapter();
		}

		@Override
		public Adapter caseTGGAttributeConstraint(TGGAttributeConstraint object) {
			return createTGGAttributeConstraintAdapter();
		}

		@Override
		public Adapter caseTGGParameterValue(TGGParameterValue object) {
			return createTGGParameterValueAdapter();
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
		public Adapter caseTGGAttributeConstraintDefinition(TGGAttributeConstraintDefinition object) {
			return createTGGAttributeConstraintDefinitionAdapter();
		}

		@Override
		public Adapter caseTGGCSP(TGGCSP object) {
			return createTGGCSPAdapter();
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
		public Adapter caseIBeXRule(IBeXRule object) {
			return createIBeXRuleAdapter();
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
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel <em>TGG Model</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel
	 * @generated
	 */
	public Adapter createTGGModelAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleSet <em>TGG Rule Set</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleSet
	 * @generated
	 */
	public Adapter createTGGRuleSetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule <em>TGG Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule
	 * @generated
	 */
	public Adapter createTGGRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode <em>TGG Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode
	 * @generated
	 */
	public Adapter createTGGNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleCorrespondence <em>TGG Rule Correspondence</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleCorrespondence
	 * @generated
	 */
	public Adapter createTGGRuleCorrespondenceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGEdge <em>TGG Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGEdge
	 * @generated
	 */
	public Adapter createTGGEdgeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleElement <em>TGG Rule Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleElement
	 * @generated
	 */
	public Adapter createTGGRuleElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintLibrary <em>TGG Attribute Constraint Library</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintLibrary
	 * @generated
	 */
	public Adapter createTGGAttributeConstraintLibraryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintDefinitionLibrary <em>TGG Attribute Constraint Definition Library</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintDefinitionLibrary
	 * @generated
	 */
	public Adapter createTGGAttributeConstraintDefinitionLibraryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraint <em>TGG Attribute Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraint
	 * @generated
	 */
	public Adapter createTGGAttributeConstraintAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGParameterValue <em>TGG Parameter Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGParameterValue
	 * @generated
	 */
	public Adapter createTGGParameterValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintParameterDefinition <em>TGG Attribute Constraint Parameter Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintParameterDefinition
	 * @generated
	 */
	public Adapter createTGGAttributeConstraintParameterDefinitionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintBinding <em>TGG Attribute Constraint Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintBinding
	 * @generated
	 */
	public Adapter createTGGAttributeConstraintBindingAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintDefinition <em>TGG Attribute Constraint Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGAttributeConstraintDefinition
	 * @generated
	 */
	public Adapter createTGGAttributeConstraintDefinitionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGCSP <em>TGGCSP</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGCSP
	 * @generated
	 */
	public Adapter createTGGCSPAdapter() {
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

} //IBeXTGGModelAdapterFactory
