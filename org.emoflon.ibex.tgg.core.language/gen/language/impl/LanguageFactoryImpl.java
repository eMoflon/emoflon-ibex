/**
 */
package language.impl;

import language.*;

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
public class LanguageFactoryImpl extends EFactoryImpl implements LanguageFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static LanguageFactory init() {
		try {
			LanguageFactory theLanguageFactory = (LanguageFactory) EPackage.Registry.INSTANCE
					.getEFactory(LanguagePackage.eNS_URI);
			if (theLanguageFactory != null) {
				return theLanguageFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new LanguageFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LanguageFactoryImpl() {
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
		case LanguagePackage.TGG:
			return createTGG();
		case LanguagePackage.TGG_RULE:
			return createTGGRule();
		case LanguagePackage.TGG_RULE_NODE:
			return createTGGRuleNode();
		case LanguagePackage.TGG_RULE_CORR:
			return createTGGRuleCorr();
		case LanguagePackage.TGG_RULE_EDGE:
			return createTGGRuleEdge();
		case LanguagePackage.NAC:
			return createNAC();
		case LanguagePackage.TGG_COMPLEMENT_RULE:
			return createTGGComplementRule();
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
		case LanguagePackage.DOMAIN_TYPE:
			return createDomainTypeFromString(eDataType, initialValue);
		case LanguagePackage.BINDING_TYPE:
			return createBindingTypeFromString(eDataType, initialValue);
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
		case LanguagePackage.DOMAIN_TYPE:
			return convertDomainTypeToString(eDataType, instanceValue);
		case LanguagePackage.BINDING_TYPE:
			return convertBindingTypeToString(eDataType, instanceValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGG createTGG() {
		TGGImpl tgg = new TGGImpl();
		return tgg;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGRule createTGGRule() {
		TGGRuleImpl tggRule = new TGGRuleImpl();
		return tggRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGRuleNode createTGGRuleNode() {
		TGGRuleNodeImpl tggRuleNode = new TGGRuleNodeImpl();
		return tggRuleNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGRuleCorr createTGGRuleCorr() {
		TGGRuleCorrImpl tggRuleCorr = new TGGRuleCorrImpl();
		return tggRuleCorr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGRuleEdge createTGGRuleEdge() {
		TGGRuleEdgeImpl tggRuleEdge = new TGGRuleEdgeImpl();
		return tggRuleEdge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NAC createNAC() {
		NACImpl nac = new NACImpl();
		return nac;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGComplementRule createTGGComplementRule() {
		TGGComplementRuleImpl tggComplementRule = new TGGComplementRuleImpl();
		return tggComplementRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomainType createDomainTypeFromString(EDataType eDataType, String initialValue) {
		DomainType result = DomainType.get(initialValue);
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
	public String convertDomainTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BindingType createBindingTypeFromString(EDataType eDataType, String initialValue) {
		BindingType result = BindingType.get(initialValue);
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
	public String convertBindingTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LanguagePackage getLanguagePackage() {
		return (LanguagePackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static LanguagePackage getPackage() {
		return LanguagePackage.eINSTANCE;
	}

} //LanguageFactoryImpl
