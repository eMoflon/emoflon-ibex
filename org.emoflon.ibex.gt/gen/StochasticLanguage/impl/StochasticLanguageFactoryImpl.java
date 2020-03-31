/**
 */
package StochasticLanguage.impl;

import StochasticLanguage.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class StochasticLanguageFactoryImpl extends EFactoryImpl implements StochasticLanguageFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static StochasticLanguageFactory init() {
		try {
			StochasticLanguageFactory theStochasticLanguageFactory = (StochasticLanguageFactory) EPackage.Registry.INSTANCE
					.getEFactory(StochasticLanguagePackage.eNS_URI);
			if (theStochasticLanguageFactory != null) {
				return theStochasticLanguageFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new StochasticLanguageFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StochasticLanguageFactoryImpl() {
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
		case StochasticLanguagePackage.GT_STOCHASTIC_NODE:
			return createGTStochasticNode();
		case StochasticLanguagePackage.GT_ATTRIBUTE:
			return createGTAttribute();
		case StochasticLanguagePackage.GT_STOCHASTIC_FUNCTION:
			return createGTStochasticFunction();
		case StochasticLanguagePackage.GT_NUMBER:
			return createGTNumber();
		case StochasticLanguagePackage.GT_TWO_PARAMETER_CALCULATION:
			return createGTTwoParameterCalculation();
		case StochasticLanguagePackage.GT_ONE_PARAMETER_CALCULATION:
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
		case StochasticLanguagePackage.GT_STOCHASTIC_DISTRIBUTION:
			return createGTStochasticDistributionFromString(eDataType, initialValue);
		case StochasticLanguagePackage.GT_STOCHASTIC_RANGE:
			return createGTStochasticRangeFromString(eDataType, initialValue);
		case StochasticLanguagePackage.TWO_PARAMETER_OPERATOR:
			return createTwoParameterOperatorFromString(eDataType, initialValue);
		case StochasticLanguagePackage.ONE_PARAMETER_OPERATOR:
			return createOneParameterOperatorFromString(eDataType, initialValue);
		case StochasticLanguagePackage.GT_RELATION:
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
		case StochasticLanguagePackage.GT_STOCHASTIC_DISTRIBUTION:
			return convertGTStochasticDistributionToString(eDataType, instanceValue);
		case StochasticLanguagePackage.GT_STOCHASTIC_RANGE:
			return convertGTStochasticRangeToString(eDataType, instanceValue);
		case StochasticLanguagePackage.TWO_PARAMETER_OPERATOR:
			return convertTwoParameterOperatorToString(eDataType, instanceValue);
		case StochasticLanguagePackage.ONE_PARAMETER_OPERATOR:
			return convertOneParameterOperatorToString(eDataType, instanceValue);
		case StochasticLanguagePackage.GT_RELATION:
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
	public StochasticLanguagePackage getStochasticLanguagePackage() {
		return (StochasticLanguagePackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static StochasticLanguagePackage getPackage() {
		return StochasticLanguagePackage.eINSTANCE;
	}

} //StochasticLanguageFactoryImpl
