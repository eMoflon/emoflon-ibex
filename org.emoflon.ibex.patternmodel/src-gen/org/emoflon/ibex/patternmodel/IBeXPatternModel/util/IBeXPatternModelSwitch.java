/**
 */
package org.emoflon.ibex.patternmodel.IBeXPatternModel.util;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.emoflon.ibex.gt.SGTPatternModel.GTArithmetics;
import org.emoflon.ibex.gt.SGTPatternModel.GTOneParameterCalculation;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.*;

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
 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage
 * @generated
 */
public class IBeXPatternModelSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static IBeXPatternModelPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXPatternModelSwitch() {
		if (modelPackage == null) {
			modelPackage = IBeXPatternModelPackage.eINSTANCE;
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
		case IBeXPatternModelPackage.IBE_XATTRIBUTE: {
			IBeXAttribute iBeXAttribute = (IBeXAttribute) theEObject;
			T result = caseIBeXAttribute(iBeXAttribute);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBE_XATTRIBUTE_ASSIGNMENT: {
			IBeXAttributeAssignment iBeXAttributeAssignment = (IBeXAttributeAssignment) theEObject;
			T result = caseIBeXAttributeAssignment(iBeXAttributeAssignment);
			if (result == null)
				result = caseIBeXAttribute(iBeXAttributeAssignment);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBE_XATTRIBUTE_CONSTRAINT: {
			IBeXAttributeConstraint iBeXAttributeConstraint = (IBeXAttributeConstraint) theEObject;
			T result = caseIBeXAttributeConstraint(iBeXAttributeConstraint);
			if (result == null)
				result = caseIBeXAttribute(iBeXAttributeConstraint);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBE_XATTRIBUTE_PARAMETER: {
			IBeXAttributeParameter iBeXAttributeParameter = (IBeXAttributeParameter) theEObject;
			T result = caseIBeXAttributeParameter(iBeXAttributeParameter);
			if (result == null)
				result = caseIBeXAttributeValue(iBeXAttributeParameter);
			if (result == null)
				result = caseIBeXNamedElement(iBeXAttributeParameter);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBE_XATTRIBUTE_VALUE: {
			IBeXAttributeValue iBeXAttributeValue = (IBeXAttributeValue) theEObject;
			T result = caseIBeXAttributeValue(iBeXAttributeValue);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBE_XATTRIBUTE_EXPRESSION: {
			IBeXAttributeExpression iBeXAttributeExpression = (IBeXAttributeExpression) theEObject;
			T result = caseIBeXAttributeExpression(iBeXAttributeExpression);
			if (result == null)
				result = caseIBeXAttributeValue(iBeXAttributeExpression);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBE_XCONSTANT: {
			IBeXConstant iBeXConstant = (IBeXConstant) theEObject;
			T result = caseIBeXConstant(iBeXConstant);
			if (result == null)
				result = caseIBeXAttributeValue(iBeXConstant);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBE_XCONTEXT: {
			IBeXContext iBeXContext = (IBeXContext) theEObject;
			T result = caseIBeXContext(iBeXContext);
			if (result == null)
				result = caseIBeXPattern(iBeXContext);
			if (result == null)
				result = caseIBeXNamedElement(iBeXContext);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBE_XCONTEXT_ALTERNATIVES: {
			IBeXContextAlternatives iBeXContextAlternatives = (IBeXContextAlternatives) theEObject;
			T result = caseIBeXContextAlternatives(iBeXContextAlternatives);
			if (result == null)
				result = caseIBeXContext(iBeXContextAlternatives);
			if (result == null)
				result = caseIBeXPattern(iBeXContextAlternatives);
			if (result == null)
				result = caseIBeXNamedElement(iBeXContextAlternatives);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBE_XCONTEXT_PATTERN: {
			IBeXContextPattern iBeXContextPattern = (IBeXContextPattern) theEObject;
			T result = caseIBeXContextPattern(iBeXContextPattern);
			if (result == null)
				result = caseIBeXContext(iBeXContextPattern);
			if (result == null)
				result = caseIBeXPattern(iBeXContextPattern);
			if (result == null)
				result = caseIBeXNamedElement(iBeXContextPattern);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBE_XCREATE_PATTERN: {
			IBeXCreatePattern iBeXCreatePattern = (IBeXCreatePattern) theEObject;
			T result = caseIBeXCreatePattern(iBeXCreatePattern);
			if (result == null)
				result = caseIBeXPattern(iBeXCreatePattern);
			if (result == null)
				result = caseIBeXNamedElement(iBeXCreatePattern);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBE_XDELETE_PATTERN: {
			IBeXDeletePattern iBeXDeletePattern = (IBeXDeletePattern) theEObject;
			T result = caseIBeXDeletePattern(iBeXDeletePattern);
			if (result == null)
				result = caseIBeXPattern(iBeXDeletePattern);
			if (result == null)
				result = caseIBeXNamedElement(iBeXDeletePattern);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBE_XEDGE: {
			IBeXEdge iBeXEdge = (IBeXEdge) theEObject;
			T result = caseIBeXEdge(iBeXEdge);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBE_XENUM_LITERAL: {
			IBeXEnumLiteral iBeXEnumLiteral = (IBeXEnumLiteral) theEObject;
			T result = caseIBeXEnumLiteral(iBeXEnumLiteral);
			if (result == null)
				result = caseIBeXAttributeValue(iBeXEnumLiteral);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBE_XNAMED_ELEMENT: {
			IBeXNamedElement iBeXNamedElement = (IBeXNamedElement) theEObject;
			T result = caseIBeXNamedElement(iBeXNamedElement);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBE_XNODE: {
			IBeXNode iBeXNode = (IBeXNode) theEObject;
			T result = caseIBeXNode(iBeXNode);
			if (result == null)
				result = caseIBeXNamedElement(iBeXNode);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBE_XNODE_PAIR: {
			IBeXNodePair iBeXNodePair = (IBeXNodePair) theEObject;
			T result = caseIBeXNodePair(iBeXNodePair);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBE_XNODE_TO_NODE_MAPPING: {
			@SuppressWarnings("unchecked")
			Map.Entry<IBeXNode, IBeXNode> iBeXNodeToNodeMapping = (Map.Entry<IBeXNode, IBeXNode>) theEObject;
			T result = caseIBeXNodeToNodeMapping(iBeXNodeToNodeMapping);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBE_XPATTERN: {
			IBeXPattern iBeXPattern = (IBeXPattern) theEObject;
			T result = caseIBeXPattern(iBeXPattern);
			if (result == null)
				result = caseIBeXNamedElement(iBeXPattern);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBE_XPATTERN_INVOCATION: {
			IBeXPatternInvocation iBeXPatternInvocation = (IBeXPatternInvocation) theEObject;
			T result = caseIBeXPatternInvocation(iBeXPatternInvocation);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBE_XPATTERN_SET: {
			IBeXPatternSet iBeXPatternSet = (IBeXPatternSet) theEObject;
			T result = caseIBeXPatternSet(iBeXPatternSet);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBE_XCSP: {
			IBeXCSP iBeXCSP = (IBeXCSP) theEObject;
			T result = caseIBeXCSP(iBeXCSP);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBE_XSTOCHASTIC_ATTRIBUTE_VALUE: {
			IBeXStochasticAttributeValue iBeXStochasticAttributeValue = (IBeXStochasticAttributeValue) theEObject;
			T result = caseIBeXStochasticAttributeValue(iBeXStochasticAttributeValue);
			if (result == null)
				result = caseIBeXAttributeValue(iBeXStochasticAttributeValue);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBE_XARITHMETIC_VALUE: {
			IBeXArithmeticValue iBeXArithmeticValue = (IBeXArithmeticValue) theEObject;
			T result = caseIBeXArithmeticValue(iBeXArithmeticValue);
			if (result == null)
				result = caseIBeXAttributeValue(iBeXArithmeticValue);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBE_XMATCH_COUNT: {
			IBeXMatchCount iBeXMatchCount = (IBeXMatchCount) theEObject;
			T result = caseIBeXMatchCount(iBeXMatchCount);
			if (result == null)
				result = caseGTOneParameterCalculation(iBeXMatchCount);
			if (result == null)
				result = caseGTArithmetics(iBeXMatchCount);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		default:
			return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XAttribute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XAttribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXAttribute(IBeXAttribute object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XAttribute Assignment</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XAttribute Assignment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXAttributeAssignment(IBeXAttributeAssignment object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XAttribute Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XAttribute Constraint</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXAttributeConstraint(IBeXAttributeConstraint object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XAttribute Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XAttribute Parameter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXAttributeParameter(IBeXAttributeParameter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XAttribute Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XAttribute Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXAttributeValue(IBeXAttributeValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XAttribute Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XAttribute Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXAttributeExpression(IBeXAttributeExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XConstant</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XConstant</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXConstant(IBeXConstant object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>IBe XContext Alternatives</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XContext Alternatives</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXContextAlternatives(IBeXContextAlternatives object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XContext Pattern</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XContext Pattern</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXContextPattern(IBeXContextPattern object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XCreate Pattern</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XCreate Pattern</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXCreatePattern(IBeXCreatePattern object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XDelete Pattern</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XDelete Pattern</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXDeletePattern(IBeXDeletePattern object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XEdge</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XEdge</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXEdge(IBeXEdge object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XEnum Literal</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XEnum Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXEnumLiteral(IBeXEnumLiteral object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>IBe XNode</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XNode</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXNode(IBeXNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XNode Pair</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XNode Pair</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXNodePair(IBeXNodePair object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XNode To Node Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XNode To Node Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXNodeToNodeMapping(Map.Entry<IBeXNode, IBeXNode> object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>IBe XPattern Invocation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XPattern Invocation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXPatternInvocation(IBeXPatternInvocation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XPattern Set</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XPattern Set</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXPatternSet(IBeXPatternSet object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XCSP</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XCSP</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXCSP(IBeXCSP object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XStochastic Attribute Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XStochastic Attribute Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXStochasticAttributeValue(IBeXStochasticAttributeValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XArithmetic Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XArithmetic Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXArithmeticValue(IBeXArithmeticValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XMatch Count</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XMatch Count</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXMatchCount(IBeXMatchCount object) {
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

} //IBeXPatternModelSwitch
