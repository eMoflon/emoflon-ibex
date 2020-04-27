/**
 */
package GTLanguage.util;

import GTLanguage.*;

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
 * @see GTLanguage.GTLanguagePackage
 * @generated
 */
public class GTLanguageSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static GTLanguagePackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GTLanguageSwitch() {
		if (modelPackage == null) {
			modelPackage = GTLanguagePackage.eINSTANCE;
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
		case GTLanguagePackage.GT_NAMED_ELEMENT: {
			GTNamedElement gtNamedElement = (GTNamedElement) theEObject;
			T result = caseGTNamedElement(gtNamedElement);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GTLanguagePackage.GT_NODE: {
			GTNode gtNode = (GTNode) theEObject;
			T result = caseGTNode(gtNode);
			if (result == null)
				result = caseGTNamedElement(gtNode);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GTLanguagePackage.GT_PARAMETER: {
			GTParameter gtParameter = (GTParameter) theEObject;
			T result = caseGTParameter(gtParameter);
			if (result == null)
				result = caseGTNamedElement(gtParameter);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GTLanguagePackage.GT_RULE: {
			GTRule gtRule = (GTRule) theEObject;
			T result = caseGTRule(gtRule);
			if (result == null)
				result = caseGTNamedElement(gtRule);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GTLanguagePackage.GT_RULE_SET: {
			GTRuleSet gtRuleSet = (GTRuleSet) theEObject;
			T result = caseGTRuleSet(gtRuleSet);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GTLanguagePackage.GT_PROBABILITY: {
			GTProbability gtProbability = (GTProbability) theEObject;
			T result = caseGTProbability(gtProbability);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case GTLanguagePackage.GT_ARITHMETIC_CONSTRAINT: {
			GTArithmeticConstraint gtArithmeticConstraint = (GTArithmeticConstraint) theEObject;
			T result = caseGTArithmeticConstraint(gtArithmeticConstraint);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		default:
			return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>GT Named Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>GT Named Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGTNamedElement(GTNamedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>GT Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>GT Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGTNode(GTNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>GT Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>GT Parameter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGTParameter(GTParameter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>GT Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>GT Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGTRule(GTRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>GT Rule Set</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>GT Rule Set</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGTRuleSet(GTRuleSet object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>GT Probability</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>GT Probability</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGTProbability(GTProbability object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>GT Arithmetic Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>GT Arithmetic Constraint</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGTArithmeticConstraint(GTArithmeticConstraint object) {
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

} //GTLanguageSwitch
