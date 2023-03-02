/**
 */
package org.emoflon.ibex.common.coremodel.IBeXCoreModel.util;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.*;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ArithmeticExpression;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ArithmeticValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.BooleanExpression;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.BooleanValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ValueExpression;

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
 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage
 * @generated
 */
public class IBeXCoreModelSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static IBeXCoreModelPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXCoreModelSwitch() {
		if (modelPackage == null) {
			modelPackage = IBeXCoreModelPackage.eINSTANCE;
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
		case IBeXCoreModelPackage.IBE_XNAMED_ELEMENT: {
			IBeXNamedElement iBeXNamedElement = (IBeXNamedElement) theEObject;
			T result = caseIBeXNamedElement(iBeXNamedElement);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXCoreModelPackage.IBE_XMODEL: {
			IBeXModel iBeXModel = (IBeXModel) theEObject;
			T result = caseIBeXModel(iBeXModel);
			if (result == null)
				result = caseIBeXNamedElement(iBeXModel);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXCoreModelPackage.IBE_XMODEL_METADATA: {
			IBeXModelMetadata iBeXModelMetadata = (IBeXModelMetadata) theEObject;
			T result = caseIBeXModelMetadata(iBeXModelMetadata);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXCoreModelPackage.IMPORT_NAME_TO_PACKAGE_DEPENDENCY_MAPPING: {
			@SuppressWarnings("unchecked")
			Map.Entry<String, EPackageDependency> importNameToPackageDependencyMapping = (Map.Entry<String, EPackageDependency>) theEObject;
			T result = caseImportNameToPackageDependencyMapping(importNameToPackageDependencyMapping);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY: {
			EPackageDependency ePackageDependency = (EPackageDependency) theEObject;
			T result = caseEPackageDependency(ePackageDependency);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXCoreModelPackage.CLASSIFIER_NAME_TO_FQN: {
			@SuppressWarnings("unchecked")
			Map.Entry<String, String> classifierNameToFQN = (Map.Entry<String, String>) theEObject;
			T result = caseClassifierNameToFQN(classifierNameToFQN);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXCoreModelPackage.IBE_XFEATURE_CONFIG: {
			IBeXFeatureConfig iBeXFeatureConfig = (IBeXFeatureConfig) theEObject;
			T result = caseIBeXFeatureConfig(iBeXFeatureConfig);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXCoreModelPackage.IBE_XPATTERN_SET: {
			IBeXPatternSet iBeXPatternSet = (IBeXPatternSet) theEObject;
			T result = caseIBeXPatternSet(iBeXPatternSet);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXCoreModelPackage.IBE_XNODE_SET: {
			IBeXNodeSet iBeXNodeSet = (IBeXNodeSet) theEObject;
			T result = caseIBeXNodeSet(iBeXNodeSet);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXCoreModelPackage.IBE_XEDGE_SET: {
			IBeXEdgeSet iBeXEdgeSet = (IBeXEdgeSet) theEObject;
			T result = caseIBeXEdgeSet(iBeXEdgeSet);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXCoreModelPackage.IBE_XNODE: {
			IBeXNode iBeXNode = (IBeXNode) theEObject;
			T result = caseIBeXNode(iBeXNode);
			if (result == null)
				result = caseIBeXNamedElement(iBeXNode);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXCoreModelPackage.IBE_XEDGE: {
			IBeXEdge iBeXEdge = (IBeXEdge) theEObject;
			T result = caseIBeXEdge(iBeXEdge);
			if (result == null)
				result = caseIBeXNamedElement(iBeXEdge);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXCoreModelPackage.IBE_XPATTERN: {
			IBeXPattern iBeXPattern = (IBeXPattern) theEObject;
			T result = caseIBeXPattern(iBeXPattern);
			if (result == null)
				result = caseIBeXNamedElement(iBeXPattern);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXCoreModelPackage.IBE_XPATTERN_INVOCATION: {
			IBeXPatternInvocation iBeXPatternInvocation = (IBeXPatternInvocation) theEObject;
			T result = caseIBeXPatternInvocation(iBeXPatternInvocation);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXCoreModelPackage.IBE_XNODE_MAPPING: {
			@SuppressWarnings("unchecked")
			Map.Entry<IBeXNode, IBeXNode> iBeXNodeMapping = (Map.Entry<IBeXNode, IBeXNode>) theEObject;
			T result = caseIBeXNodeMapping(iBeXNodeMapping);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXCoreModelPackage.IBE_XENUM_VALUE: {
			IBeXEnumValue iBeXEnumValue = (IBeXEnumValue) theEObject;
			T result = caseIBeXEnumValue(iBeXEnumValue);
			if (result == null)
				result = caseValueExpression(iBeXEnumValue);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXCoreModelPackage.IBE_XSTRING_VALUE: {
			IBeXStringValue iBeXStringValue = (IBeXStringValue) theEObject;
			T result = caseIBeXStringValue(iBeXStringValue);
			if (result == null)
				result = caseArithmeticExpression(iBeXStringValue);
			if (result == null)
				result = caseValueExpression(iBeXStringValue);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXCoreModelPackage.IBE_XBOOLEAN_VALUE: {
			IBeXBooleanValue iBeXBooleanValue = (IBeXBooleanValue) theEObject;
			T result = caseIBeXBooleanValue(iBeXBooleanValue);
			if (result == null)
				result = caseValueExpression(iBeXBooleanValue);
			if (result == null)
				result = caseBooleanValue(iBeXBooleanValue);
			if (result == null)
				result = caseBooleanExpression(iBeXBooleanValue);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXCoreModelPackage.IBE_XNULL_VALUE: {
			IBeXNullValue iBeXNullValue = (IBeXNullValue) theEObject;
			T result = caseIBeXNullValue(iBeXNullValue);
			if (result == null)
				result = caseValueExpression(iBeXNullValue);
			if (result == null)
				result = caseBooleanExpression(iBeXNullValue);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXCoreModelPackage.IBE_XATTRIBUTE_VALUE: {
			IBeXAttributeValue iBeXAttributeValue = (IBeXAttributeValue) theEObject;
			T result = caseIBeXAttributeValue(iBeXAttributeValue);
			if (result == null)
				result = caseArithmeticValue(iBeXAttributeValue);
			if (result == null)
				result = caseBooleanValue(iBeXAttributeValue);
			if (result == null)
				result = caseArithmeticExpression(iBeXAttributeValue);
			if (result == null)
				result = caseBooleanExpression(iBeXAttributeValue);
			if (result == null)
				result = caseValueExpression(iBeXAttributeValue);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXCoreModelPackage.IBE_XNODE_VALUE: {
			IBeXNodeValue iBeXNodeValue = (IBeXNodeValue) theEObject;
			T result = caseIBeXNodeValue(iBeXNodeValue);
			if (result == null)
				result = caseArithmeticValue(iBeXNodeValue);
			if (result == null)
				result = caseArithmeticExpression(iBeXNodeValue);
			if (result == null)
				result = caseValueExpression(iBeXNodeValue);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXCoreModelPackage.IBE_XMATCH_COUNT_VALUE: {
			IBeXMatchCountValue iBeXMatchCountValue = (IBeXMatchCountValue) theEObject;
			T result = caseIBeXMatchCountValue(iBeXMatchCountValue);
			if (result == null)
				result = caseArithmeticValue(iBeXMatchCountValue);
			if (result == null)
				result = caseArithmeticExpression(iBeXMatchCountValue);
			if (result == null)
				result = caseValueExpression(iBeXMatchCountValue);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXCoreModelPackage.IBE_XRULE: {
			IBeXRule iBeXRule = (IBeXRule) theEObject;
			T result = caseIBeXRule(iBeXRule);
			if (result == null)
				result = caseIBeXNamedElement(iBeXRule);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXCoreModelPackage.IBE_XRULE_DELTA: {
			IBeXRuleDelta iBeXRuleDelta = (IBeXRuleDelta) theEObject;
			T result = caseIBeXRuleDelta(iBeXRuleDelta);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case IBeXCoreModelPackage.IBE_XATTRIBUTE_ASSIGNMENT: {
			IBeXAttributeAssignment iBeXAttributeAssignment = (IBeXAttributeAssignment) theEObject;
			T result = caseIBeXAttributeAssignment(iBeXAttributeAssignment);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		default:
			return defaultCase(theEObject);
		}
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
	 * Returns the result of interpreting the object as an instance of '<em>IBe XModel Metadata</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XModel Metadata</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXModelMetadata(IBeXModelMetadata object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Import Name To Package Dependency Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Import Name To Package Dependency Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseImportNameToPackageDependencyMapping(Map.Entry<String, EPackageDependency> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EPackage Dependency</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EPackage Dependency</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEPackageDependency(EPackageDependency object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Classifier Name To FQN</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Classifier Name To FQN</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseClassifierNameToFQN(Map.Entry<String, String> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XFeature Config</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XFeature Config</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXFeatureConfig(IBeXFeatureConfig object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>IBe XNode Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XNode Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXNodeMapping(Map.Entry<IBeXNode, IBeXNode> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XEnum Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XEnum Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXEnumValue(IBeXEnumValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XString Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XString Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXStringValue(IBeXStringValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XBoolean Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XBoolean Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXBooleanValue(IBeXBooleanValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XNull Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XNull Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXNullValue(IBeXNullValue object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>IBe XNode Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XNode Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXNodeValue(IBeXNodeValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBe XMatch Count Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XMatch Count Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXMatchCountValue(IBeXMatchCountValue object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>IBe XRule Delta</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBe XRule Delta</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBeXRuleDelta(IBeXRuleDelta object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>Boolean Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Boolean Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBooleanExpression(BooleanExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Boolean Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Boolean Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBooleanValue(BooleanValue object) {
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

} //IBeXCoreModelSwitch
