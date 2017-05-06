/**
 */
package language.basic.expressions.util;

import language.basic.expressions.*;

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
 * @see language.basic.expressions.ExpressionsPackage
 * @generated
 */
public class ExpressionsSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ExpressionsPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExpressionsSwitch() {
		if (modelPackage == null) {
			modelPackage = ExpressionsPackage.eINSTANCE;
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
		case ExpressionsPackage.TGG_PARAM_VALUE: {
			TGGParamValue tggParamValue = (TGGParamValue) theEObject;
			T result = caseTGGParamValue(tggParamValue);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ExpressionsPackage.TGG_EXPRESSION: {
			TGGExpression tggExpression = (TGGExpression) theEObject;
			T result = caseTGGExpression(tggExpression);
			if (result == null)
				result = caseTGGParamValue(tggExpression);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ExpressionsPackage.TGG_LITERAL_EXPRESSION: {
			TGGLiteralExpression tggLiteralExpression = (TGGLiteralExpression) theEObject;
			T result = caseTGGLiteralExpression(tggLiteralExpression);
			if (result == null)
				result = caseTGGExpression(tggLiteralExpression);
			if (result == null)
				result = caseTGGParamValue(tggLiteralExpression);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ExpressionsPackage.TGG_ATTRIBUTE_EXPRESSION: {
			TGGAttributeExpression tggAttributeExpression = (TGGAttributeExpression) theEObject;
			T result = caseTGGAttributeExpression(tggAttributeExpression);
			if (result == null)
				result = caseTGGExpression(tggAttributeExpression);
			if (result == null)
				result = caseTGGParamValue(tggAttributeExpression);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case ExpressionsPackage.TGG_ENUM_EXPRESSION: {
			TGGEnumExpression tggEnumExpression = (TGGEnumExpression) theEObject;
			T result = caseTGGEnumExpression(tggEnumExpression);
			if (result == null)
				result = caseTGGExpression(tggEnumExpression);
			if (result == null)
				result = caseTGGParamValue(tggEnumExpression);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		default:
			return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TGG Param Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TGG Param Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTGGParamValue(TGGParamValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TGG Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TGG Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTGGExpression(TGGExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TGG Literal Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TGG Literal Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTGGLiteralExpression(TGGLiteralExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TGG Attribute Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TGG Attribute Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTGGAttributeExpression(TGGAttributeExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TGG Enum Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TGG Enum Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTGGEnumExpression(TGGEnumExpression object) {
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

} //ExpressionsSwitch
