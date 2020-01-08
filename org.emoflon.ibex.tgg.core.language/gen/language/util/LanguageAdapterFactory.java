/**
 */
package language.util;

import language.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see language.LanguagePackage
 * @generated
 */
public class LanguageAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static LanguagePackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LanguageAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = LanguagePackage.eINSTANCE;
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
	protected LanguageSwitch<Adapter> modelSwitch = new LanguageSwitch<Adapter>() {
		@Override
		public Adapter caseTGG(TGG object) {
			return createTGGAdapter();
		}

		@Override
		public Adapter caseTGGRule(TGGRule object) {
			return createTGGRuleAdapter();
		}

		@Override
		public Adapter caseTGGRuleElement(TGGRuleElement object) {
			return createTGGRuleElementAdapter();
		}

		@Override
		public Adapter caseTGGRuleNode(TGGRuleNode object) {
			return createTGGRuleNodeAdapter();
		}

		@Override
		public Adapter caseTGGRuleCorr(TGGRuleCorr object) {
			return createTGGRuleCorrAdapter();
		}

		@Override
		public Adapter caseTGGRuleEdge(TGGRuleEdge object) {
			return createTGGRuleEdgeAdapter();
		}

		@Override
		public Adapter caseNAC(NAC object) {
			return createNACAdapter();
		}

		@Override
		public Adapter caseTGGInplaceAttributeExpression(TGGInplaceAttributeExpression object) {
			return createTGGInplaceAttributeExpressionAdapter();
		}

		@Override
		public Adapter caseTGGAttributeConstraintLibrary(TGGAttributeConstraintLibrary object) {
			return createTGGAttributeConstraintLibraryAdapter();
		}

		@Override
		public Adapter caseTGGAttributeConstraint(TGGAttributeConstraint object) {
			return createTGGAttributeConstraintAdapter();
		}

		@Override
		public Adapter caseTGGAttributeVariable(TGGAttributeVariable object) {
			return createTGGAttributeVariableAdapter();
		}

		@Override
		public Adapter caseTGGAttributeConstraintDefinitionLibrary(TGGAttributeConstraintDefinitionLibrary object) {
			return createTGGAttributeConstraintDefinitionLibraryAdapter();
		}

		@Override
		public Adapter caseTGGAttributeConstraintDefinition(TGGAttributeConstraintDefinition object) {
			return createTGGAttributeConstraintDefinitionAdapter();
		}

		@Override
		public Adapter caseTGGAttributeConstraintParameterDefinition(TGGAttributeConstraintParameterDefinition object) {
			return createTGGAttributeConstraintParameterDefinitionAdapter();
		}

		@Override
		public Adapter caseTGGAttributeConstraintAdornment(TGGAttributeConstraintAdornment object) {
			return createTGGAttributeConstraintAdornmentAdapter();
		}

		@Override
		public Adapter caseTGGNamedElement(TGGNamedElement object) {
			return createTGGNamedElementAdapter();
		}

		@Override
		public Adapter caseTGGParamValue(TGGParamValue object) {
			return createTGGParamValueAdapter();
		}

		@Override
		public Adapter caseTGGExpression(TGGExpression object) {
			return createTGGExpressionAdapter();
		}

		@Override
		public Adapter caseTGGLiteralExpression(TGGLiteralExpression object) {
			return createTGGLiteralExpressionAdapter();
		}

		@Override
		public Adapter caseTGGEnumExpression(TGGEnumExpression object) {
			return createTGGEnumExpressionAdapter();
		}

		@Override
		public Adapter caseTGGAttributeExpression(TGGAttributeExpression object) {
			return createTGGAttributeExpressionAdapter();
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
	 * Creates a new adapter for an object of class '{@link language.TGG <em>TGG</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see language.TGG
	 * @generated
	 */
	public Adapter createTGGAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link language.TGGRule <em>TGG Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see language.TGGRule
	 * @generated
	 */
	public Adapter createTGGRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link language.TGGRuleElement <em>TGG Rule Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see language.TGGRuleElement
	 * @generated
	 */
	public Adapter createTGGRuleElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link language.TGGRuleNode <em>TGG Rule Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see language.TGGRuleNode
	 * @generated
	 */
	public Adapter createTGGRuleNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link language.TGGRuleCorr <em>TGG Rule Corr</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see language.TGGRuleCorr
	 * @generated
	 */
	public Adapter createTGGRuleCorrAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link language.TGGRuleEdge <em>TGG Rule Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see language.TGGRuleEdge
	 * @generated
	 */
	public Adapter createTGGRuleEdgeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link language.NAC <em>NAC</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see language.NAC
	 * @generated
	 */
	public Adapter createNACAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link language.TGGInplaceAttributeExpression <em>TGG Inplace Attribute Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see language.TGGInplaceAttributeExpression
	 * @generated
	 */
	public Adapter createTGGInplaceAttributeExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link language.TGGAttributeConstraintLibrary <em>TGG Attribute Constraint Library</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see language.TGGAttributeConstraintLibrary
	 * @generated
	 */
	public Adapter createTGGAttributeConstraintLibraryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link language.TGGAttributeConstraint <em>TGG Attribute Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see language.TGGAttributeConstraint
	 * @generated
	 */
	public Adapter createTGGAttributeConstraintAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link language.TGGAttributeVariable <em>TGG Attribute Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see language.TGGAttributeVariable
	 * @generated
	 */
	public Adapter createTGGAttributeVariableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link language.TGGAttributeConstraintDefinitionLibrary <em>TGG Attribute Constraint Definition Library</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see language.TGGAttributeConstraintDefinitionLibrary
	 * @generated
	 */
	public Adapter createTGGAttributeConstraintDefinitionLibraryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link language.TGGAttributeConstraintDefinition <em>TGG Attribute Constraint Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see language.TGGAttributeConstraintDefinition
	 * @generated
	 */
	public Adapter createTGGAttributeConstraintDefinitionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link language.TGGAttributeConstraintParameterDefinition <em>TGG Attribute Constraint Parameter Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see language.TGGAttributeConstraintParameterDefinition
	 * @generated
	 */
	public Adapter createTGGAttributeConstraintParameterDefinitionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link language.TGGAttributeConstraintAdornment <em>TGG Attribute Constraint Adornment</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see language.TGGAttributeConstraintAdornment
	 * @generated
	 */
	public Adapter createTGGAttributeConstraintAdornmentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link language.TGGNamedElement <em>TGG Named Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see language.TGGNamedElement
	 * @generated
	 */
	public Adapter createTGGNamedElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link language.TGGParamValue <em>TGG Param Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see language.TGGParamValue
	 * @generated
	 */
	public Adapter createTGGParamValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link language.TGGExpression <em>TGG Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see language.TGGExpression
	 * @generated
	 */
	public Adapter createTGGExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link language.TGGLiteralExpression <em>TGG Literal Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see language.TGGLiteralExpression
	 * @generated
	 */
	public Adapter createTGGLiteralExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link language.TGGEnumExpression <em>TGG Enum Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see language.TGGEnumExpression
	 * @generated
	 */
	public Adapter createTGGEnumExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link language.TGGAttributeExpression <em>TGG Attribute Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see language.TGGAttributeExpression
	 * @generated
	 */
	public Adapter createTGGAttributeExpressionAdapter() {
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

} //LanguageAdapterFactory
