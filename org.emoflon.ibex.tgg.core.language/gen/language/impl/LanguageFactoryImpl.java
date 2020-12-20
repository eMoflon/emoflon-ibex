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
			LanguageFactory theLanguageFactory = (LanguageFactory) EPackage.Registry.INSTANCE.getEFactory(LanguagePackage.eNS_URI);
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
		case LanguagePackage.TGG_INPLACE_ATTRIBUTE_EXPRESSION:
			return createTGGInplaceAttributeExpression();
		case LanguagePackage.TGG_ATTRIBUTE_CONSTRAINT_LIBRARY:
			return createTGGAttributeConstraintLibrary();
		case LanguagePackage.TGG_ATTRIBUTE_CONSTRAINT:
			return createTGGAttributeConstraint();
		case LanguagePackage.TGG_ATTRIBUTE_VARIABLE:
			return createTGGAttributeVariable();
		case LanguagePackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY:
			return createTGGAttributeConstraintDefinitionLibrary();
		case LanguagePackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION:
			return createTGGAttributeConstraintDefinition();
		case LanguagePackage.TGG_ATTRIBUTE_CONSTRAINT_PARAMETER_DEFINITION:
			return createTGGAttributeConstraintParameterDefinition();
		case LanguagePackage.TGG_ATTRIBUTE_CONSTRAINT_ADORNMENT:
			return createTGGAttributeConstraintAdornment();
		case LanguagePackage.TGG_NAMED_ELEMENT:
			return createTGGNamedElement();
		case LanguagePackage.TGG_PARAM_VALUE:
			return createTGGParamValue();
		case LanguagePackage.TGG_LITERAL_EXPRESSION:
			return createTGGLiteralExpression();
		case LanguagePackage.TGG_ENUM_EXPRESSION:
			return createTGGEnumExpression();
		case LanguagePackage.TGG_ATTRIBUTE_EXPRESSION:
			return createTGGAttributeExpression();
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
		case LanguagePackage.TGG_ATTRIBUTE_CONSTRAINT_OPERATORS:
			return createTGGAttributeConstraintOperatorsFromString(eDataType, initialValue);
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
		case LanguagePackage.TGG_ATTRIBUTE_CONSTRAINT_OPERATORS:
			return convertTGGAttributeConstraintOperatorsToString(eDataType, instanceValue);
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
	public TGG createTGG() {
		TGGImpl tgg = new TGGImpl();
		return tgg;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TGGRule createTGGRule() {
		TGGRuleImpl tggRule = new TGGRuleImpl();
		return tggRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TGGRuleNode createTGGRuleNode() {
		TGGRuleNodeImpl tggRuleNode = new TGGRuleNodeImpl();
		return tggRuleNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TGGRuleCorr createTGGRuleCorr() {
		TGGRuleCorrImpl tggRuleCorr = new TGGRuleCorrImpl();
		return tggRuleCorr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TGGRuleEdge createTGGRuleEdge() {
		TGGRuleEdgeImpl tggRuleEdge = new TGGRuleEdgeImpl();
		return tggRuleEdge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NAC createNAC() {
		NACImpl nac = new NACImpl();
		return nac;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TGGInplaceAttributeExpression createTGGInplaceAttributeExpression() {
		TGGInplaceAttributeExpressionImpl tggInplaceAttributeExpression = new TGGInplaceAttributeExpressionImpl();
		return tggInplaceAttributeExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TGGAttributeConstraintLibrary createTGGAttributeConstraintLibrary() {
		TGGAttributeConstraintLibraryImpl tggAttributeConstraintLibrary = new TGGAttributeConstraintLibraryImpl();
		return tggAttributeConstraintLibrary;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TGGAttributeConstraint createTGGAttributeConstraint() {
		TGGAttributeConstraintImpl tggAttributeConstraint = new TGGAttributeConstraintImpl();
		return tggAttributeConstraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TGGAttributeVariable createTGGAttributeVariable() {
		TGGAttributeVariableImpl tggAttributeVariable = new TGGAttributeVariableImpl();
		return tggAttributeVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TGGAttributeConstraintDefinitionLibrary createTGGAttributeConstraintDefinitionLibrary() {
		TGGAttributeConstraintDefinitionLibraryImpl tggAttributeConstraintDefinitionLibrary = new TGGAttributeConstraintDefinitionLibraryImpl();
		return tggAttributeConstraintDefinitionLibrary;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TGGAttributeConstraintDefinition createTGGAttributeConstraintDefinition() {
		TGGAttributeConstraintDefinitionImpl tggAttributeConstraintDefinition = new TGGAttributeConstraintDefinitionImpl();
		return tggAttributeConstraintDefinition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TGGAttributeConstraintParameterDefinition createTGGAttributeConstraintParameterDefinition() {
		TGGAttributeConstraintParameterDefinitionImpl tggAttributeConstraintParameterDefinition = new TGGAttributeConstraintParameterDefinitionImpl();
		return tggAttributeConstraintParameterDefinition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TGGAttributeConstraintAdornment createTGGAttributeConstraintAdornment() {
		TGGAttributeConstraintAdornmentImpl tggAttributeConstraintAdornment = new TGGAttributeConstraintAdornmentImpl();
		return tggAttributeConstraintAdornment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TGGNamedElement createTGGNamedElement() {
		TGGNamedElementImpl tggNamedElement = new TGGNamedElementImpl();
		return tggNamedElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TGGParamValue createTGGParamValue() {
		TGGParamValueImpl tggParamValue = new TGGParamValueImpl();
		return tggParamValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TGGLiteralExpression createTGGLiteralExpression() {
		TGGLiteralExpressionImpl tggLiteralExpression = new TGGLiteralExpressionImpl();
		return tggLiteralExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TGGEnumExpression createTGGEnumExpression() {
		TGGEnumExpressionImpl tggEnumExpression = new TGGEnumExpressionImpl();
		return tggEnumExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TGGAttributeExpression createTGGAttributeExpression() {
		TGGAttributeExpressionImpl tggAttributeExpression = new TGGAttributeExpressionImpl();
		return tggAttributeExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomainType createDomainTypeFromString(EDataType eDataType, String initialValue) {
		DomainType result = DomainType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
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
			throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
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
	public TGGAttributeConstraintOperators createTGGAttributeConstraintOperatorsFromString(EDataType eDataType, String initialValue) {
		TGGAttributeConstraintOperators result = TGGAttributeConstraintOperators.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTGGAttributeConstraintOperatorsToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
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
