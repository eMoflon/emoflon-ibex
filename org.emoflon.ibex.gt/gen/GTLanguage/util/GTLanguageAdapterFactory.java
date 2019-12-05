/**
 */
package GTLanguage.util;

import GTLanguage.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see GTLanguage.GTLanguagePackage
 * @generated
 */
public class GTLanguageAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static GTLanguagePackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GTLanguageAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = GTLanguagePackage.eINSTANCE;
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
	protected GTLanguageSwitch<Adapter> modelSwitch = new GTLanguageSwitch<Adapter>() {
		@Override
		public Adapter caseGTNamedElement(GTNamedElement object) {
			return createGTNamedElementAdapter();
		}

		@Override
		public Adapter caseGTNode(GTNode object) {
			return createGTNodeAdapter();
		}

		@Override
		public Adapter caseGTParameter(GTParameter object) {
			return createGTParameterAdapter();
		}

		@Override
		public Adapter caseGTRule(GTRule object) {
			return createGTRuleAdapter();
		}

		@Override
		public Adapter caseGTRuleSet(GTRuleSet object) {
			return createGTRuleSetAdapter();
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
	 * Creates a new adapter for an object of class '{@link GTLanguage.GTNamedElement <em>GT Named Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see GTLanguage.GTNamedElement
	 * @generated
	 */
	public Adapter createGTNamedElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link GTLanguage.GTNode <em>GT Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see GTLanguage.GTNode
	 * @generated
	 */
	public Adapter createGTNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link GTLanguage.GTParameter <em>GT Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see GTLanguage.GTParameter
	 * @generated
	 */
	public Adapter createGTParameterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link GTLanguage.GTRule <em>GT Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see GTLanguage.GTRule
	 * @generated
	 */
	public Adapter createGTRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link GTLanguage.GTRuleSet <em>GT Rule Set</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see GTLanguage.GTRuleSet
	 * @generated
	 */
	public Adapter createGTRuleSetAdapter() {
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

} //GTLanguageAdapterFactory
