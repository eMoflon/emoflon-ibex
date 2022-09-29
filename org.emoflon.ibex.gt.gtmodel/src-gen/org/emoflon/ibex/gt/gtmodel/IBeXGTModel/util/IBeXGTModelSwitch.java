/**
 */
package org.emoflon.ibex.gt.gtmodel.IBeXGTModel.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ArithmeticExpression;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ArithmeticValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ValueExpression;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModel;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNamedElement;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRule;

import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.*;

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
 * @see org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelPackage
 * @generated
 */
public class IBeXGTModelSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static IBeXGTModelPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXGTModelSwitch() {
		if (modelPackage == null) {
			modelPackage = IBeXGTModelPackage.eINSTANCE;
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
		case IBeXGTModelPackage.GT_MODEL: {
			GTModel gtModel = (GTModel) theEObject;
			T result = caseGTModel(gtModel);
			if (result == null)
				result = caseIBeXModel(gtModel);
			if (result == null)
				result = caseIBeXNamedElement(gtModel);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXGTModelPackage.GT_RULE_SET: {
			GTRuleSet gtRuleSet = (GTRuleSet) theEObject;
			T result = caseGTRuleSet(gtRuleSet);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXGTModelPackage.GT_PATTERN: {
			GTPattern gtPattern = (GTPattern) theEObject;
			T result = caseGTPattern(gtPattern);
			if (result == null)
				result = caseIBeXPattern(gtPattern);
			if (result == null)
				result = caseIBeXNamedElement(gtPattern);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXGTModelPackage.GT_RULE: {
			GTRule gtRule = (GTRule) theEObject;
			T result = caseGTRule(gtRule);
			if (result == null)
				result = caseIBeXRule(gtRule);
			if (result == null)
				result = caseIBeXNamedElement(gtRule);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION: {
			GTForEachExpression gtForEachExpression = (GTForEachExpression) theEObject;
			T result = caseGTForEachExpression(gtForEachExpression);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXGTModelPackage.GT_PARAMETER: {
			GTParameter gtParameter = (GTParameter) theEObject;
			T result = caseGTParameter(gtParameter);
			if (result == null)
				result = caseIBeXNamedElement(gtParameter);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXGTModelPackage.GT_PARAMETER_VALUE: {
			GTParameterValue gtParameterValue = (GTParameterValue) theEObject;
			T result = caseGTParameterValue(gtParameterValue);
			if (result == null)
				result = caseArithmeticValue(gtParameterValue);
			if (result == null)
				result = caseArithmeticExpression(gtParameterValue);
			if (result == null)
				result = caseValueExpression(gtParameterValue);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXGTModelPackage.GT_ITERATOR_ATTRIBUTE_REFERENCE: {
			GTIteratorAttributeReference gtIteratorAttributeReference = (GTIteratorAttributeReference) theEObject;
			T result = caseGTIteratorAttributeReference(gtIteratorAttributeReference);
			if (result == null)
				result = caseValueExpression(gtIteratorAttributeReference);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXGTModelPackage.GT_WATCH_DOG: {
			GTWatchDog gtWatchDog = (GTWatchDog) theEObject;
			T result = caseGTWatchDog(gtWatchDog);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		default:
			return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>GT Model</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>GT Model</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGTModel(GTModel object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>GT Pattern</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>GT Pattern</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGTPattern(GTPattern object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>GT For Each Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>GT For Each Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGTForEachExpression(GTForEachExpression object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>GT Parameter Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>GT Parameter Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGTParameterValue(GTParameterValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>GT Iterator Attribute Reference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>GT Iterator Attribute Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGTIteratorAttributeReference(GTIteratorAttributeReference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>GT Watch Dog</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>GT Watch Dog</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGTWatchDog(GTWatchDog object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XNamed Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XNamed Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXNamedElement(IBeXNamedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XModel</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XModel</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXModel(IBeXModel object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XPattern</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XPattern</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXPattern(IBeXPattern object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XRule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XRule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXRule(IBeXRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Value Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Value Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseValueExpression(ValueExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Arithmetic Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Arithmetic Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseArithmeticExpression(ArithmeticExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Arithmetic Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Arithmetic Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseArithmeticValue(ArithmeticValue object) {
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

} //IBeXGTModelSwitch
