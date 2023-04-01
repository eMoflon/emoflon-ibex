/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ArithmeticExpression;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ValueExpression;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNamedElement;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.*;

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
 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.CSPPackage
 * @generated
 */
public class CSPSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static CSPPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CSPSwitch() {
		if (modelPackage == null) {
			modelPackage = CSPPackage.eINSTANCE;
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
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION: {
			TGGAttributeConstraintDefinition tggAttributeConstraintDefinition = (TGGAttributeConstraintDefinition) theEObject;
			T result = caseTGGAttributeConstraintDefinition(tggAttributeConstraintDefinition);
			if (result == null)
				result = caseIBeXNamedElement(tggAttributeConstraintDefinition);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY: {
			TGGAttributeConstraintDefinitionLibrary tggAttributeConstraintDefinitionLibrary = (TGGAttributeConstraintDefinitionLibrary) theEObject;
			T result = caseTGGAttributeConstraintDefinitionLibrary(tggAttributeConstraintDefinitionLibrary);
			if (result == null)
				result = caseIBeXNamedElement(tggAttributeConstraintDefinitionLibrary);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_DEFINITION: {
			TGGAttributeConstraintParameterDefinition tggAttributeConstraintParameterDefinition = (TGGAttributeConstraintParameterDefinition) theEObject;
			T result = caseTGGAttributeConstraintParameterDefinition(tggAttributeConstraintParameterDefinition);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_BINDING: {
			TGGAttributeConstraintBinding tggAttributeConstraintBinding = (TGGAttributeConstraintBinding) theEObject;
			T result = caseTGGAttributeConstraintBinding(tggAttributeConstraintBinding);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_SET: {
			TGGAttributeConstraintSet tggAttributeConstraintSet = (TGGAttributeConstraintSet) theEObject;
			T result = caseTGGAttributeConstraintSet(tggAttributeConstraintSet);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT: {
			TGGAttributeConstraint tggAttributeConstraint = (TGGAttributeConstraint) theEObject;
			T result = caseTGGAttributeConstraint(tggAttributeConstraint);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_VARIABLE: {
			TGGAttributeConstraintVariable tggAttributeConstraintVariable = (TGGAttributeConstraintVariable) theEObject;
			T result = caseTGGAttributeConstraintVariable(tggAttributeConstraintVariable);
			if (result == null)
				result = caseValueExpression(tggAttributeConstraintVariable);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case CSPPackage.TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_VALUE: {
			TGGAttributeConstraintParameterValue tggAttributeConstraintParameterValue = (TGGAttributeConstraintParameterValue) theEObject;
			T result = caseTGGAttributeConstraintParameterValue(tggAttributeConstraintParameterValue);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case CSPPackage.TGGCSP: {
			TGGCSP tggcsp = (TGGCSP) theEObject;
			T result = caseTGGCSP(tggcsp);
			if (result == null)
				result = caseIBeXNamedElement(tggcsp);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case CSPPackage.TGG_LOCAL_VARIABLE: {
			TGGLocalVariable tggLocalVariable = (TGGLocalVariable) theEObject;
			T result = caseTGGLocalVariable(tggLocalVariable);
			if (result == null)
				result = caseIBeXNamedElement(tggLocalVariable);
			if (result == null)
				result = caseArithmeticExpression(tggLocalVariable);
			if (result == null)
				result = caseValueExpression(tggLocalVariable);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		default:
			return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TGG Attribute Constraint Definition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TGG Attribute Constraint Definition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTGGAttributeConstraintDefinition(TGGAttributeConstraintDefinition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TGG Attribute Constraint Definition Library</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TGG Attribute Constraint Definition Library</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTGGAttributeConstraintDefinitionLibrary(TGGAttributeConstraintDefinitionLibrary object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TGG Attribute Constraint Parameter Definition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TGG Attribute Constraint Parameter Definition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTGGAttributeConstraintParameterDefinition(TGGAttributeConstraintParameterDefinition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TGG Attribute Constraint Binding</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TGG Attribute Constraint Binding</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTGGAttributeConstraintBinding(TGGAttributeConstraintBinding object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TGG Attribute Constraint Set</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TGG Attribute Constraint Set</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTGGAttributeConstraintSet(TGGAttributeConstraintSet object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TGG Attribute Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TGG Attribute Constraint</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTGGAttributeConstraint(TGGAttributeConstraint object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TGG Attribute Constraint Variable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TGG Attribute Constraint Variable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTGGAttributeConstraintVariable(TGGAttributeConstraintVariable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TGG Attribute Constraint Parameter Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TGG Attribute Constraint Parameter Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTGGAttributeConstraintParameterValue(TGGAttributeConstraintParameterValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TGGCSP</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TGGCSP</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTGGCSP(TGGCSP object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TGG Local Variable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TGG Local Variable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTGGLocalVariable(TGGLocalVariable object) {
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

} //CSPSwitch
