/**
 */
package org.emoflon.ibex.IBeXDisjunctPatternModel.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.emoflon.ibex.IBeXDisjunctPatternModel.*;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNamedElement;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPattern;

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
 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctPatternModelPackage
 * @generated
 */
public class IBeXDisjunctPatternModelSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static IBeXDisjunctPatternModelPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXDisjunctPatternModelSwitch() {
		if (modelPackage == null) {
			modelPackage = IBeXDisjunctPatternModelPackage.eINSTANCE;
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
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_CONTEXT_PATTERN: {
				IBeXDisjunctContextPattern iBeXDisjunctContextPattern = (IBeXDisjunctContextPattern)theEObject;
				T result = caseIBeXDisjunctContextPattern(iBeXDisjunctContextPattern);
				if (result == null) result = caseIBeXContext(iBeXDisjunctContextPattern);
				if (result == null) result = caseIBeXPattern(iBeXDisjunctContextPattern);
				if (result == null) result = caseIBeXNamedElement(iBeXDisjunctContextPattern);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTE: {
				IBeXDisjunctAttribute iBeXDisjunctAttribute = (IBeXDisjunctAttribute)theEObject;
				T result = caseIBeXDisjunctAttribute(iBeXDisjunctAttribute);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IBeXDisjunctPatternModelPackage.IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS: {
				IBeXDependentInjectivityConstraints iBeXDependentInjectivityConstraints = (IBeXDependentInjectivityConstraints)theEObject;
				T result = caseIBeXDependentInjectivityConstraints(iBeXDependentInjectivityConstraints);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IBeXDisjunctPatternModelPackage.IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT: {
				IBexDisjunctInjectivityConstraint iBexDisjunctInjectivityConstraint = (IBexDisjunctInjectivityConstraint)theEObject;
				T result = caseIBexDisjunctInjectivityConstraint(iBexDisjunctInjectivityConstraint);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IBeXDisjunctPatternModelPackage.IBE_XDEPENDENT_DISJUNCT_ATTRIBUTE: {
				IBeXDependentDisjunctAttribute iBeXDependentDisjunctAttribute = (IBeXDependentDisjunctAttribute)theEObject;
				T result = caseIBeXDependentDisjunctAttribute(iBeXDependentDisjunctAttribute);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XDisjunct Context Pattern</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XDisjunct Context Pattern</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXDisjunctContextPattern(IBeXDisjunctContextPattern object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XDisjunct Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XDisjunct Attribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXDisjunctAttribute(IBeXDisjunctAttribute object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XDependent Injectivity Constraints</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XDependent Injectivity Constraints</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXDependentInjectivityConstraints(IBeXDependentInjectivityConstraints object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBex Disjunct Injectivity Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBex Disjunct Injectivity Constraint</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBexDisjunctInjectivityConstraint(IBexDisjunctInjectivityConstraint object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XDependent Disjunct Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XDependent Disjunct Attribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXDependentDisjunctAttribute(IBeXDependentDisjunctAttribute object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>IBe XContext</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XContext</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXContext(IBeXContext object) {
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

} //IBeXDisjunctPatternModelSwitch
