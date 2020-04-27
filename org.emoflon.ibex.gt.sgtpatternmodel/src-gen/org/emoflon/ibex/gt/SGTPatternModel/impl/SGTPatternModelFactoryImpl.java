/**
 */
package org.emoflon.ibex.gt.SGTPatternModel.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.emoflon.ibex.gt.SGTPatternModel.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SGTPatternModelFactoryImpl extends EFactoryImpl implements SGTPatternModelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SGTPatternModelFactory init() {
		try {
			SGTPatternModelFactory theSGTPatternModelFactory = (SGTPatternModelFactory) EPackage.Registry.INSTANCE
					.getEFactory(SGTPatternModelPackage.eNS_URI);
			if (theSGTPatternModelFactory != null) {
				return theSGTPatternModelFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new SGTPatternModelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SGTPatternModelFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case SGTPatternModelPackage.GT_STOCHASTIC_NODE:
			return createGTStochasticNode();
		case SGTPatternModelPackage.GT_ATTRIBUTE:
			return createGTAttribute();
		case SGTPatternModelPackage.GT_STOCHASTIC_FUNCTION:
			return createGTStochasticFunction();
		case SGTPatternModelPackage.GT_NUMBER:
			return createGTNumber();
		case SGTPatternModelPackage.GT_TWO_PARAMETER_CALCULATION:
			return createGTTwoParameterCalculation();
		case SGTPatternModelPackage.GT_ONE_PARAMETER_CALCULATION:
			return createGTOneParameterCalculation();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
		case SGTPatternModelPackage.GT_STOCHASTIC_DISTRIBUTION:
			return createGTStochasticDistributionFromString(eDataType, initialValue);
		case SGTPatternModelPackage.GT_STOCHASTIC_RANGE:
			return createGTStochasticRangeFromString(eDataType, initialValue);
		case SGTPatternModelPackage.TWO_PARAMETER_OPERATOR:
			return createTwoParameterOperatorFromString(eDataType, initialValue);
		case SGTPatternModelPackage.ONE_PARAMETER_OPERATOR:
			return createOneParameterOperatorFromString(eDataType, initialValue);
		case SGTPatternModelPackage.GT_RELATION:
			return createGTRelationFromString(eDataType, initialValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
		case SGTPatternModelPackage.GT_STOCHASTIC_DISTRIBUTION:
			return convertGTStochasticDistributionToString(eDataType, instanceValue);
		case SGTPatternModelPackage.GT_STOCHASTIC_RANGE:
			return convertGTStochasticRangeToString(eDataType, instanceValue);
		case SGTPatternModelPackage.TWO_PARAMETER_OPERATOR:
			return convertTwoParameterOperatorToString(eDataType, instanceValue);
		case SGTPatternModelPackage.ONE_PARAMETER_OPERATOR:
			return convertOneParameterOperatorToString(eDataType, instanceValue);
		case SGTPatternModelPackage.GT_RELATION:
			return convertGTRelationToString(eDataType, instanceValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GTStochasticNode createGTStochasticNode() {
		GTStochasticNodeImpl gtStochasticNode = new GTStochasticNodeImpl();
		return gtStochasticNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GTAttribute createGTAttribute() {
		GTAttributeImpl gtAttribute = new GTAttributeImpl();
		return gtAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GTStochasticFunction createGTStochasticFunction() {
		GTStochasticFunctionImpl gtStochasticFunction = new GTStochasticFunctionImpl();
		return gtStochasticFunction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GTNumber createGTNumber() {
		GTNumberImpl gtNumber = new GTNumberImpl();
		return gtNumber;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GTTwoParameterCalculation createGTTwoParameterCalculation() {
		GTTwoParameterCalculationImpl gtTwoParameterCalculation = new GTTwoParameterCalculationImpl();
		return gtTwoParameterCalculation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GTOneParameterCalculation createGTOneParameterCalculation() {
		GTOneParameterCalculationImpl gtOneParameterCalculation = new GTOneParameterCalculationImpl();
		return gtOneParameterCalculation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GTStochasticDistribution createGTStochasticDistributionFromString(EDataType eDataType, String initialValue) {
		GTStochasticDistribution result = GTStochasticDistribution.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertGTStochasticDistributionToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GTStochasticRange createGTStochasticRangeFromString(EDataType eDataType, String initialValue) {
		GTStochasticRange result = GTStochasticRange.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertGTStochasticRangeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TwoParameterOperator createTwoParameterOperatorFromString(EDataType eDataType, String initialValue) {
		TwoParameterOperator result = TwoParameterOperator.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTwoParameterOperatorToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OneParameterOperator createOneParameterOperatorFromString(EDataType eDataType, String initialValue) {
		OneParameterOperator result = OneParameterOperator.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertOneParameterOperatorToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GTRelation createGTRelationFromString(EDataType eDataType, String initialValue) {
		GTRelation result = GTRelation.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertGTRelationToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SGTPatternModelPackage getSGTPatternModelPackage() {
		return (SGTPatternModelPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static SGTPatternModelPackage getPackage() {
		return SGTPatternModelPackage.eINSTANCE;
	}

} //SGTPatternModelFactoryImpl
