/**
 */
package org.emoflon.ibex.patternmodel.IBeXPatternModel.util;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

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
		case IBeXPatternModelPackage.IBE_XMODEL: {
			IBeXModel iBeXModel = (IBeXModel) theEObject;
			T result = caseIBeXModel(iBeXModel);
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
		case IBeXPatternModelPackage.IBE_XRULE_SET: {
			IBeXRuleSet iBeXRuleSet = (IBeXRuleSet) theEObject;
			T result = caseIBeXRuleSet(iBeXRuleSet);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBE_XNODE_SET: {
			IBeXNodeSet iBeXNodeSet = (IBeXNodeSet) theEObject;
			T result = caseIBeXNodeSet(iBeXNodeSet);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBE_XEDGE_SET: {
			IBeXEdgeSet iBeXEdgeSet = (IBeXEdgeSet) theEObject;
			T result = caseIBeXEdgeSet(iBeXEdgeSet);
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
		case IBeXPatternModelPackage.IBE_XPATTERN: {
			IBeXPattern iBeXPattern = (IBeXPattern) theEObject;
			T result = caseIBeXPattern(iBeXPattern);
			if (result == null)
				result = caseIBeXNamedElement(iBeXPattern);
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
		case IBeXPatternModelPackage.IBE_XNODE: {
			IBeXNode iBeXNode = (IBeXNode) theEObject;
			T result = caseIBeXNode(iBeXNode);
			if (result == null)
				result = caseIBeXNamedElement(iBeXNode);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBE_XEDGE: {
			IBeXEdge iBeXEdge = (IBeXEdge) theEObject;
			T result = caseIBeXEdge(iBeXEdge);
			if (result == null)
				result = caseIBeXNamedElement(iBeXEdge);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBE_XPARAMETER: {
			IBeXParameter iBeXParameter = (IBeXParameter) theEObject;
			T result = caseIBeXParameter(iBeXParameter);
			if (result == null)
				result = caseIBeXNamedElement(iBeXParameter);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
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
				result = caseIBeXConstraint(iBeXAttributeConstraint);
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
		case IBeXPatternModelPackage.IBE_XCONSTANT: {
			IBeXConstant iBeXConstant = (IBeXConstant) theEObject;
			T result = caseIBeXConstant(iBeXConstant);
			if (result == null)
				result = caseIBeXAttributeValue(iBeXConstant);
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
		case IBeXPatternModelPackage.IBE_XATTRIBUTE_EXPRESSION: {
			IBeXAttributeExpression iBeXAttributeExpression = (IBeXAttributeExpression) theEObject;
			T result = caseIBeXAttributeExpression(iBeXAttributeExpression);
			if (result == null)
				result = caseIBeXAttributeValue(iBeXAttributeExpression);
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
		case IBeXPatternModelPackage.IBE_XINJECTIVITY_CONSTRAINT: {
			IBeXInjectivityConstraint iBeXInjectivityConstraint = (IBeXInjectivityConstraint) theEObject;
			T result = caseIBeXInjectivityConstraint(iBeXInjectivityConstraint);
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
		case IBeXPatternModelPackage.IBE_XNODE_TO_NODE_MAPPING: {
			@SuppressWarnings("unchecked")
			Map.Entry<IBeXNode, IBeXNode> iBeXNodeToNodeMapping = (Map.Entry<IBeXNode, IBeXNode>) theEObject;
			T result = caseIBeXNodeToNodeMapping(iBeXNodeToNodeMapping);
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
		case IBeXPatternModelPackage.IBE_XRULE: {
			IBeXRule iBeXRule = (IBeXRule) theEObject;
			T result = caseIBeXRule(iBeXRule);
			if (result == null)
				result = caseIBeXNamedElement(iBeXRule);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBE_XFOR_EACH_EXPRESSION: {
			IBeXForEachExpression iBeXForEachExpression = (IBeXForEachExpression) theEObject;
			T result = caseIBeXForEachExpression(iBeXForEachExpression);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBE_XPROBABILITY: {
			IBeXProbability iBeXProbability = (IBeXProbability) theEObject;
			T result = caseIBeXProbability(iBeXProbability);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBE_XARITHMETIC_CONSTRAINT: {
			IBeXArithmeticConstraint iBeXArithmeticConstraint = (IBeXArithmeticConstraint) theEObject;
			T result = caseIBeXArithmeticConstraint(iBeXArithmeticConstraint);
			if (result == null)
				result = caseIBeXConstraint(iBeXArithmeticConstraint);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBE_XARITHMETIC_EXPRESSION: {
			IBeXArithmeticExpression iBeXArithmeticExpression = (IBeXArithmeticExpression) theEObject;
			T result = caseIBeXArithmeticExpression(iBeXArithmeticExpression);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBE_XUNARY_EXPRESSION: {
			IBeXUnaryExpression iBeXUnaryExpression = (IBeXUnaryExpression) theEObject;
			T result = caseIBeXUnaryExpression(iBeXUnaryExpression);
			if (result == null)
				result = caseIBeXArithmeticExpression(iBeXUnaryExpression);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBE_XBINARY_EXPRESSION: {
			IBeXBinaryExpression iBeXBinaryExpression = (IBeXBinaryExpression) theEObject;
			T result = caseIBeXBinaryExpression(iBeXBinaryExpression);
			if (result == null)
				result = caseIBeXArithmeticExpression(iBeXBinaryExpression);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBE_XARITHMETIC_VALUE_LITERAL: {
			IBeXArithmeticValueLiteral iBeXArithmeticValueLiteral = (IBeXArithmeticValueLiteral) theEObject;
			T result = caseIBeXArithmeticValueLiteral(iBeXArithmeticValueLiteral);
			if (result == null)
				result = caseIBeXArithmeticExpression(iBeXArithmeticValueLiteral);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBE_XPROBABILITY_DISTRIBUTION: {
			IBeXProbabilityDistribution iBeXProbabilityDistribution = (IBeXProbabilityDistribution) theEObject;
			T result = caseIBeXProbabilityDistribution(iBeXProbabilityDistribution);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBE_XARITHMETIC_ATTRIBUTE: {
			IBeXArithmeticAttribute iBeXArithmeticAttribute = (IBeXArithmeticAttribute) theEObject;
			T result = caseIBeXArithmeticAttribute(iBeXArithmeticAttribute);
			if (result == null)
				result = caseIBeXNode(iBeXArithmeticAttribute);
			if (result == null)
				result = caseIBeXArithmeticExpression(iBeXArithmeticAttribute);
			if (result == null)
				result = caseIBeXNamedElement(iBeXArithmeticAttribute);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBE_XMATCH_COUNT: {
			IBeXMatchCount iBeXMatchCount = (IBeXMatchCount) theEObject;
			T result = caseIBeXMatchCount(iBeXMatchCount);
			if (result == null)
				result = caseIBeXUnaryExpression(iBeXMatchCount);
			if (result == null)
				result = caseIBeXArithmeticExpression(iBeXMatchCount);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBE_XCONSTRAINT: {
			IBeXConstraint iBeXConstraint = (IBeXConstraint) theEObject;
			T result = caseIBeXConstraint(iBeXConstraint);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBE_XDISJOINT_CONTEXT_PATTERN: {
			IBeXDisjointContextPattern iBeXDisjointContextPattern = (IBeXDisjointContextPattern) theEObject;
			T result = caseIBeXDisjointContextPattern(iBeXDisjointContextPattern);
			if (result == null)
				result = caseIBeXContext(iBeXDisjointContextPattern);
			if (result == null)
				result = caseIBeXPattern(iBeXDisjointContextPattern);
			if (result == null)
				result = caseIBeXNamedElement(iBeXDisjointContextPattern);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBEX_DISJOINT_INJECTIVITY_CONSTRAINT: {
			IBexDisjointInjectivityConstraint iBexDisjointInjectivityConstraint = (IBexDisjointInjectivityConstraint) theEObject;
			T result = caseIBexDisjointInjectivityConstraint(iBexDisjointInjectivityConstraint);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBE_XDISJOINT_ATTRIBUTE: {
			IBeXDisjointAttribute iBeXDisjointAttribute = (IBeXDisjointAttribute) theEObject;
			T result = caseIBeXDisjointAttribute(iBeXDisjointAttribute);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBE_XINTERDEPENDENT_INJECTIVITY_CONSTRAINTS: {
			IBeXInterdependentInjectivityConstraints iBeXInterdependentInjectivityConstraints = (IBeXInterdependentInjectivityConstraints) theEObject;
			T result = caseIBeXInterdependentInjectivityConstraints(iBeXInterdependentInjectivityConstraints);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXPatternModelPackage.IBE_XINTERDEPENDENT_ATTRIBUTES: {
			IBeXInterdependentAttributes iBeXInterdependentAttributes = (IBeXInterdependentAttributes) theEObject;
			T result = caseIBeXInterdependentAttributes(iBeXInterdependentAttributes);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		default:
			return defaultCase(theEObject);
		}
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
	 * Returns the result of interpreting the object as an instance of '<em>IBe XRule Set</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XRule Set</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXRuleSet(IBeXRuleSet object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XNode Set</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XNode Set</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXNodeSet(IBeXNodeSet object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XEdge Set</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XEdge Set</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXEdgeSet(IBeXEdgeSet object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>IBe XParameter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XParameter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXParameter(IBeXParameter object) {
		return null;
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
	 * Returns the result of interpreting the object as an instance of '<em>IBe XInjectivity Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XInjectivity Constraint</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXInjectivityConstraint(IBeXInjectivityConstraint object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>IBe XFor Each Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XFor Each Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXForEachExpression(IBeXForEachExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XProbability</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XProbability</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXProbability(IBeXProbability object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XArithmetic Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XArithmetic Constraint</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXArithmeticConstraint(IBeXArithmeticConstraint object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XArithmetic Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XArithmetic Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXArithmeticExpression(IBeXArithmeticExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XUnary Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XUnary Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXUnaryExpression(IBeXUnaryExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XBinary Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XBinary Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXBinaryExpression(IBeXBinaryExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XArithmetic Value Literal</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XArithmetic Value Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXArithmeticValueLiteral(IBeXArithmeticValueLiteral object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XProbability Distribution</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XProbability Distribution</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXProbabilityDistribution(IBeXProbabilityDistribution object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XArithmetic Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XArithmetic Attribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXArithmeticAttribute(IBeXArithmeticAttribute object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>IBe XConstraint</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XConstraint</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXConstraint(IBeXConstraint object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XDisjoint Context Pattern</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XDisjoint Context Pattern</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXDisjointContextPattern(IBeXDisjointContextPattern object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBex Disjoint Injectivity Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBex Disjoint Injectivity Constraint</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBexDisjointInjectivityConstraint(IBexDisjointInjectivityConstraint object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XDisjoint Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XDisjoint Attribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXDisjointAttribute(IBeXDisjointAttribute object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XInterdependent Injectivity Constraints</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XInterdependent Injectivity Constraints</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXInterdependentInjectivityConstraints(IBeXInterdependentInjectivityConstraints object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XInterdependent Attributes</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XInterdependent Attributes</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXInterdependentAttributes(IBeXInterdependentAttributes object) {
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
