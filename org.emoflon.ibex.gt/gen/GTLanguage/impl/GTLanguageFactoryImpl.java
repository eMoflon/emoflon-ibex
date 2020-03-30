/**
 */
package GTLanguage.impl;

import GTLanguage.*;

import org.eclipse.emf.ecore.EClass;
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
public class GTLanguageFactoryImpl extends EFactoryImpl implements GTLanguageFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static GTLanguageFactory init() {
		try {
			GTLanguageFactory theGTLanguageFactory = (GTLanguageFactory) EPackage.Registry.INSTANCE
					.getEFactory(GTLanguagePackage.eNS_URI);
			if (theGTLanguageFactory != null) {
				return theGTLanguageFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new GTLanguageFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GTLanguageFactoryImpl() {
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
		case GTLanguagePackage.GT_NAMED_ELEMENT:
			return createGTNamedElement();
		case GTLanguagePackage.GT_NODE:
			return createGTNode();
		case GTLanguagePackage.GT_PARAMETER:
			return createGTParameter();
		case GTLanguagePackage.GT_RULE:
			return createGTRule();
		case GTLanguagePackage.GT_RULE_SET:
			return createGTRuleSet();
		case GTLanguagePackage.GT_PROBABILITY:
			return createGTProbability();
		case GTLanguagePackage.GT_ARITHMETIC_CONSTRAINT:
			return createGTArithmeticConstraint();
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
	public GTNamedElement createGTNamedElement() {
		GTNamedElementImpl gtNamedElement = new GTNamedElementImpl();
		return gtNamedElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GTNode createGTNode() {
		GTNodeImpl gtNode = new GTNodeImpl();
		return gtNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GTParameter createGTParameter() {
		GTParameterImpl gtParameter = new GTParameterImpl();
		return gtParameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GTRule createGTRule() {
		GTRuleImpl gtRule = new GTRuleImpl();
		return gtRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GTRuleSet createGTRuleSet() {
		GTRuleSetImpl gtRuleSet = new GTRuleSetImpl();
		return gtRuleSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GTProbability createGTProbability() {
		GTProbabilityImpl gtProbability = new GTProbabilityImpl();
		return gtProbability;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GTArithmeticConstraint createGTArithmeticConstraint() {
		GTArithmeticConstraintImpl gtArithmeticConstraint = new GTArithmeticConstraintImpl();
		return gtArithmeticConstraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GTLanguagePackage getGTLanguagePackage() {
		return (GTLanguagePackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static GTLanguagePackage getPackage() {
		return GTLanguagePackage.eINSTANCE;
	}

} //GTLanguageFactoryImpl
