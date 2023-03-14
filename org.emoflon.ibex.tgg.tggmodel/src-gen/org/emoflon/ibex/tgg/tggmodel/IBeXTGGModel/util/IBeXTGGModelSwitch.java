/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModel;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNamedElement;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRule;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.*;

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
 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage
 * @generated
 */
public class IBeXTGGModelSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static IBeXTGGModelPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXTGGModelSwitch() {
		if (modelPackage == null) {
			modelPackage = IBeXTGGModelPackage.eINSTANCE;
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
			case IBeXTGGModelPackage.TGG_MODEL: {
				TGGModel tggModel = (TGGModel) theEObject;
				T result = caseTGGModel(tggModel);
				if (result == null)
					result = caseIBeXModel(tggModel);
				if (result == null)
					result = caseIBeXNamedElement(tggModel);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case IBeXTGGModelPackage.TGG_RULE_SET: {
				TGGRuleSet tggRuleSet = (TGGRuleSet) theEObject;
				T result = caseTGGRuleSet(tggRuleSet);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case IBeXTGGModelPackage.TGG_RULE: {
				TGGRule tggRule = (TGGRule) theEObject;
				T result = caseTGGRule(tggRule);
				if (result == null)
					result = caseIBeXRule(tggRule);
				if (result == null)
					result = caseIBeXNamedElement(tggRule);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case IBeXTGGModelPackage.TGG_OPERATIONAL_RULE: {
				TGGOperationalRule tggOperationalRule = (TGGOperationalRule) theEObject;
				T result = caseTGGOperationalRule(tggOperationalRule);
				if (result == null)
					result = caseTGGRule(tggOperationalRule);
				if (result == null)
					result = caseIBeXRule(tggOperationalRule);
				if (result == null)
					result = caseIBeXNamedElement(tggOperationalRule);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case IBeXTGGModelPackage.TGG_RULE_ELEMENT: {
				TGGRuleElement tggRuleElement = (TGGRuleElement) theEObject;
				T result = caseTGGRuleElement(tggRuleElement);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case IBeXTGGModelPackage.TGG_NODE: {
				TGGNode tggNode = (TGGNode) theEObject;
				T result = caseTGGNode(tggNode);
				if (result == null)
					result = caseIBeXNode(tggNode);
				if (result == null)
					result = caseTGGRuleElement(tggNode);
				if (result == null)
					result = caseIBeXNamedElement(tggNode);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case IBeXTGGModelPackage.TGG_CORRESPONDENCE: {
				TGGCorrespondence tggCorrespondence = (TGGCorrespondence) theEObject;
				T result = caseTGGCorrespondence(tggCorrespondence);
				if (result == null)
					result = caseTGGNode(tggCorrespondence);
				if (result == null)
					result = caseIBeXNode(tggCorrespondence);
				if (result == null)
					result = caseTGGRuleElement(tggCorrespondence);
				if (result == null)
					result = caseIBeXNamedElement(tggCorrespondence);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case IBeXTGGModelPackage.TGG_EDGE: {
				TGGEdge tggEdge = (TGGEdge) theEObject;
				T result = caseTGGEdge(tggEdge);
				if (result == null)
					result = caseIBeXEdge(tggEdge);
				if (result == null)
					result = caseTGGRuleElement(tggEdge);
				if (result == null)
					result = caseIBeXNamedElement(tggEdge);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case IBeXTGGModelPackage.TGG_SHORTCUT_RULE: {
				TGGShortcutRule tggShortcutRule = (TGGShortcutRule) theEObject;
				T result = caseTGGShortcutRule(tggShortcutRule);
				if (result == null)
					result = caseTGGOperationalRule(tggShortcutRule);
				if (result == null)
					result = caseTGGRule(tggShortcutRule);
				if (result == null)
					result = caseIBeXRule(tggShortcutRule);
				if (result == null)
					result = caseIBeXNamedElement(tggShortcutRule);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case IBeXTGGModelPackage.TGG_SHORTCUT_RULE_ELEMENT_MAPPING: {
				TGGShortcutRuleElementMapping tggShortcutRuleElementMapping = (TGGShortcutRuleElementMapping) theEObject;
				T result = caseTGGShortcutRuleElementMapping(tggShortcutRuleElementMapping);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			default:
				return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TGG Model</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TGG Model</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTGGModel(TGGModel object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TGG Rule Set</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TGG Rule Set</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTGGRuleSet(TGGRuleSet object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>TGG Operational Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TGG Operational Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTGGOperationalRule(TGGOperationalRule object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>TGG Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TGG Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTGGNode(TGGNode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TGG Correspondence</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TGG Correspondence</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTGGCorrespondence(TGGCorrespondence object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TGG Edge</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TGG Edge</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTGGEdge(TGGEdge object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TGG Shortcut Rule</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TGG Shortcut Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTGGShortcutRule(TGGShortcutRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>TGG Shortcut Rule Element Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>TGG Shortcut Rule Element Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTGGShortcutRuleElementMapping(TGGShortcutRuleElementMapping object) {
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

} //IBeXTGGModelSwitch
