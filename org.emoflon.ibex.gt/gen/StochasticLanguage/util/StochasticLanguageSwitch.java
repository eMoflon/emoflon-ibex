/**
 */
package StochasticLanguage.util;

import StochasticLanguage.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see StochasticLanguage.StochasticLanguagePackage
 * @generated
 */
public class StochasticLanguageSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static StochasticLanguagePackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StochasticLanguageSwitch() {
		if (modelPackage == null) {
			modelPackage = StochasticLanguagePackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
		case StochasticLanguagePackage.GT_STOCHASTIC_NAMED_ELEMENT: {
			GTStochasticNamedElement gtStochasticNamedElement = (GTStochasticNamedElement) theEObject;
			T result = caseGTStochasticNamedElement(gtStochasticNamedElement);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case StochasticLanguagePackage.GT_STOCHASTIC_NODE: {
			GTStochasticNode gtStochasticNode = (GTStochasticNode) theEObject;
			T result = caseGTStochasticNode(gtStochasticNode);
			if (result == null)
				result = caseGTStochasticNamedElement(gtStochasticNode);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case StochasticLanguagePackage.GT_ATTRIBUTE: {
			GTAttribute gtAttribute = (GTAttribute) theEObject;
			T result = caseGTAttribute(gtAttribute);
			if (result == null)
				result = caseGTStochasticNode(gtAttribute);
			if (result == null)
				result = caseGTArithmetics(gtAttribute);
			if (result == null)
				result = caseGTStochasticNamedElement(gtAttribute);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case StochasticLanguagePackage.GT_STOCHASTIC_FUNCTION: {
			GTStochasticFunction gtStochasticFunction = (GTStochasticFunction) theEObject;
			T result = caseGTStochasticFunction(gtStochasticFunction);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case StochasticLanguagePackage.GT_NUMBER: {
			GTNumber gtNumber = (GTNumber) theEObject;
			T result = caseGTNumber(gtNumber);
			if (result == null)
				result = caseGTArithmetics(gtNumber);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case StochasticLanguagePackage.GT_ARITHMETICS: {
			GTArithmetics gtArithmetics = (GTArithmetics) theEObject;
			T result = caseGTArithmetics(gtArithmetics);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case StochasticLanguagePackage.GT_TWO_PARAMETER_CALCULATION: {
			GTTwoParameterCalculation gtTwoParameterCalculation = (GTTwoParameterCalculation) theEObject;
			T result = caseGTTwoParameterCalculation(gtTwoParameterCalculation);
			if (result == null)
				result = caseGTArithmetics(gtTwoParameterCalculation);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case StochasticLanguagePackage.GT_ONE_PARAMETER_CALCULATION: {
			GTOneParameterCalculation gtOneParameterCalculation = (GTOneParameterCalculation) theEObject;
			T result = caseGTOneParameterCalculation(gtOneParameterCalculation);
			if (result == null)
				result = caseGTArithmetics(gtOneParameterCalculation);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		default:
			return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>GT Stochastic Named Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>GT Stochastic Named Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGTStochasticNamedElement(GTStochasticNamedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>GT Stochastic Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>GT Stochastic Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGTStochasticNode(GTStochasticNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>GT Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>GT Attribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGTAttribute(GTAttribute object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>GT Stochastic Function</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>GT Stochastic Function</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGTStochasticFunction(GTStochasticFunction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>GT Number</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>GT Number</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGTNumber(GTNumber object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>GT Arithmetics</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>GT Arithmetics</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGTArithmetics(GTArithmetics object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>GT Two Parameter Calculation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>GT Two Parameter Calculation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGTTwoParameterCalculation(GTTwoParameterCalculation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>GT One Parameter Calculation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>GT One Parameter Calculation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGTOneParameterCalculation(GTOneParameterCalculation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //StochasticLanguageSwitch
