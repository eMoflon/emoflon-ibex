/**
 */
package language.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

import language.LanguagePackage;
import language.NAC;
import language.TGG;
import language.TGGAttributeConstraint;
import language.TGGAttributeConstraintAdornment;
import language.TGGAttributeConstraintDefinition;
import language.TGGAttributeConstraintDefinitionLibrary;
import language.TGGAttributeConstraintLibrary;
import language.TGGAttributeConstraintParameterDefinition;
import language.TGGAttributeExpression;
import language.TGGAttributeVariable;
import language.TGGEnumExpression;
import language.TGGExpression;
import language.TGGInplaceAttributeExpression;
import language.TGGLiteralExpression;
import language.TGGNamedElement;
import language.TGGParamValue;
import language.TGGRule;
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

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
 * @see language.LanguagePackage
 * @generated
 */
public class LanguageSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static LanguagePackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LanguageSwitch() {
		if (modelPackage == null) {
			modelPackage = LanguagePackage.eINSTANCE;
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
		case LanguagePackage.TGG: {
			TGG tgg = (TGG) theEObject;
			T result = caseTGG(tgg);
			if (result == null)
				result = caseTGGNamedElement(tgg);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LanguagePackage.TGG_RULE: {
			TGGRule tggRule = (TGGRule) theEObject;
			T result = caseTGGRule(tggRule);
			if (result == null)
				result = caseTGGNamedElement(tggRule);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LanguagePackage.TGG_RULE_ELEMENT: {
			TGGRuleElement tggRuleElement = (TGGRuleElement) theEObject;
			T result = caseTGGRuleElement(tggRuleElement);
			if (result == null)
				result = caseTGGNamedElement(tggRuleElement);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LanguagePackage.TGG_RULE_NODE: {
			TGGRuleNode tggRuleNode = (TGGRuleNode) theEObject;
			T result = caseTGGRuleNode(tggRuleNode);
			if (result == null)
				result = caseTGGRuleElement(tggRuleNode);
			if (result == null)
				result = caseTGGNamedElement(tggRuleNode);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LanguagePackage.TGG_RULE_CORR: {
			TGGRuleCorr tggRuleCorr = (TGGRuleCorr) theEObject;
			T result = caseTGGRuleCorr(tggRuleCorr);
			if (result == null)
				result = caseTGGRuleNode(tggRuleCorr);
			if (result == null)
				result = caseTGGRuleElement(tggRuleCorr);
			if (result == null)
				result = caseTGGNamedElement(tggRuleCorr);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LanguagePackage.TGG_RULE_EDGE: {
			TGGRuleEdge tggRuleEdge = (TGGRuleEdge) theEObject;
			T result = caseTGGRuleEdge(tggRuleEdge);
			if (result == null)
				result = caseTGGRuleElement(tggRuleEdge);
			if (result == null)
				result = caseTGGNamedElement(tggRuleEdge);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LanguagePackage.NAC: {
			NAC nac = (NAC) theEObject;
			T result = caseNAC(nac);
			if (result == null)
				result = caseTGGNamedElement(nac);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LanguagePackage.TGG_INPLACE_ATTRIBUTE_EXPRESSION: {
			TGGInplaceAttributeExpression tggInplaceAttributeExpression = (TGGInplaceAttributeExpression) theEObject;
			T result = caseTGGInplaceAttributeExpression(tggInplaceAttributeExpression);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LanguagePackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY: {
			TGGAttributeConstraintLibrary tggAttributeConstraintLibrary = (TGGAttributeConstraintLibrary) theEObject;
			T result = caseTGGAttributeConstraintLibrary(tggAttributeConstraintLibrary);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LanguagePackage.TGG_ATTRIBUTE_CONSTRAINT: {
			TGGAttributeConstraint tggAttributeConstraint = (TGGAttributeConstraint) theEObject;
			T result = caseTGGAttributeConstraint(tggAttributeConstraint);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LanguagePackage.TGG_ATTRIBUTE_VARIABLE: {
			TGGAttributeVariable tggAttributeVariable = (TGGAttributeVariable) theEObject;
			T result = caseTGGAttributeVariable(tggAttributeVariable);
			if (result == null)
				result = caseTGGParamValue(tggAttributeVariable);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LanguagePackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY: {
			TGGAttributeConstraintDefinitionLibrary tggAttributeConstraintDefinitionLibrary = (TGGAttributeConstraintDefinitionLibrary) theEObject;
			T result = caseTGGAttributeConstraintDefinitionLibrary(tggAttributeConstraintDefinitionLibrary);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LanguagePackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION: {
			TGGAttributeConstraintDefinition tggAttributeConstraintDefinition = (TGGAttributeConstraintDefinition) theEObject;
			T result = caseTGGAttributeConstraintDefinition(tggAttributeConstraintDefinition);
			if (result == null)
				result = caseTGGNamedElement(tggAttributeConstraintDefinition);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LanguagePackage.TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_DEFINITION: {
			TGGAttributeConstraintParameterDefinition tggAttributeConstraintParameterDefinition = (TGGAttributeConstraintParameterDefinition) theEObject;
			T result = caseTGGAttributeConstraintParameterDefinition(tggAttributeConstraintParameterDefinition);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LanguagePackage.TGG_ATTRIBUTE_CONSTRAINT_ADORNMENT: {
			TGGAttributeConstraintAdornment tggAttributeConstraintAdornment = (TGGAttributeConstraintAdornment) theEObject;
			T result = caseTGGAttributeConstraintAdornment(tggAttributeConstraintAdornment);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LanguagePackage.TGG_NAMED_ELEMENT: {
			TGGNamedElement tggNamedElement = (TGGNamedElement) theEObject;
			T result = caseTGGNamedElement(tggNamedElement);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LanguagePackage.TGG_PARAM_VALUE: {
			TGGParamValue tggParamValue = (TGGParamValue) theEObject;
			T result = caseTGGParamValue(tggParamValue);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LanguagePackage.TGG_EXPRESSION: {
			TGGExpression tggExpression = (TGGExpression) theEObject;
			T result = caseTGGExpression(tggExpression);
			if (result == null)
				result = caseTGGParamValue(tggExpression);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case LanguagePackage.TGG_LITERAL_EXPRESSION: {
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
		case LanguagePackage.TGG_ENUM_EXPRESSION: {
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
		case LanguagePackage.TGG_ATTRIBUTE_EXPRESSION: {
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
		default:
			return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TGG</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TGG</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTGG(TGG object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TGG Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TGG Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTGGRule(TGGRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TGG Rule Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TGG Rule Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTGGRuleElement(TGGRuleElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TGG Rule Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TGG Rule Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTGGRuleNode(TGGRuleNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TGG Rule Corr</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TGG Rule Corr</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTGGRuleCorr(TGGRuleCorr object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TGG Rule Edge</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TGG Rule Edge</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTGGRuleEdge(TGGRuleEdge object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>NAC</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>NAC</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNAC(NAC object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TGG Inplace Attribute Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TGG Inplace Attribute Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTGGInplaceAttributeExpression(TGGInplaceAttributeExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TGG Attribute Constraint Library</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TGG Attribute Constraint Library</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTGGAttributeConstraintLibrary(TGGAttributeConstraintLibrary object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>TGG Attribute Variable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TGG Attribute Variable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTGGAttributeVariable(TGGAttributeVariable object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>TGG Attribute Constraint Adornment</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TGG Attribute Constraint Adornment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTGGAttributeConstraintAdornment(TGGAttributeConstraintAdornment object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TGG Named Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TGG Named Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTGGNamedElement(TGGNamedElement object) {
		return null;
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

} //LanguageSwitch
