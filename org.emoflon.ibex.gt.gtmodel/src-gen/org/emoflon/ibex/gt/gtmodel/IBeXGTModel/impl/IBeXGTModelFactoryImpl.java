/**
 */
package org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class IBeXGTModelFactoryImpl extends EFactoryImpl implements IBeXGTModelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static IBeXGTModelFactory init() {
		try {
			IBeXGTModelFactory theIBeXGTModelFactory = (IBeXGTModelFactory) EPackage.Registry.INSTANCE
					.getEFactory(IBeXGTModelPackage.eNS_URI);
			if (theIBeXGTModelFactory != null) {
				return theIBeXGTModelFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new IBeXGTModelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXGTModelFactoryImpl() {
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
		case IBeXGTModelPackage.GT_MODEL:
			return createGTModel();
		case IBeXGTModelPackage.GT_RULE_SET:
			return createGTRuleSet();
		case IBeXGTModelPackage.GT_PATTERN:
			return createGTPattern();
		case IBeXGTModelPackage.GT_RULE:
			return createGTRule();
		case IBeXGTModelPackage.GT_FOR_EACH_EXPRESSION:
			return createGTForEachExpression();
		case IBeXGTModelPackage.GT_PARAMETER:
			return createGTParameter();
		case IBeXGTModelPackage.GT_PARAMETER_VALUE:
			return createGTParameterValue();
		case IBeXGTModelPackage.GT_ITERATOR_ATTRIBUTE_REFERENCE:
			return createGTIteratorAttributeReference();
		case IBeXGTModelPackage.GT_WATCH_DOG:
			return createGTWatchDog();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GTModel createGTModel() {
		GTModelImpl gtModel = new GTModelImpl();
		return gtModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GTRuleSet createGTRuleSet() {
		GTRuleSetImpl gtRuleSet = new GTRuleSetImpl();
		return gtRuleSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GTPattern createGTPattern() {
		GTPatternImpl gtPattern = new GTPatternImpl();
		return gtPattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GTRule createGTRule() {
		GTRuleImpl gtRule = new GTRuleImpl();
		return gtRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GTForEachExpression createGTForEachExpression() {
		GTForEachExpressionImpl gtForEachExpression = new GTForEachExpressionImpl();
		return gtForEachExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GTParameter createGTParameter() {
		GTParameterImpl gtParameter = new GTParameterImpl();
		return gtParameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GTParameterValue createGTParameterValue() {
		GTParameterValueImpl gtParameterValue = new GTParameterValueImpl();
		return gtParameterValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GTIteratorAttributeReference createGTIteratorAttributeReference() {
		GTIteratorAttributeReferenceImpl gtIteratorAttributeReference = new GTIteratorAttributeReferenceImpl();
		return gtIteratorAttributeReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GTWatchDog createGTWatchDog() {
		GTWatchDogImpl gtWatchDog = new GTWatchDogImpl();
		return gtWatchDog;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXGTModelPackage getIBeXGTModelPackage() {
		return (IBeXGTModelPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static IBeXGTModelPackage getPackage() {
		return IBeXGTModelPackage.eINSTANCE;
	}

} //IBeXGTModelFactoryImpl
