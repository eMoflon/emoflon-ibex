/**
 */
package org.emoflon.ibex.gt.StateModel.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.emoflon.ibex.gt.StateModel.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class StateModelFactoryImpl extends EFactoryImpl implements StateModelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static StateModelFactory init() {
		try {
			StateModelFactory theStateModelFactory = (StateModelFactory) EPackage.Registry.INSTANCE
					.getEFactory(StateModelPackage.eNS_URI);
			if (theStateModelFactory != null) {
				return theStateModelFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new StateModelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateModelFactoryImpl() {
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
		case StateModelPackage.STATE_CONTAINER:
			return createStateContainer();
		case StateModelPackage.STATE:
			return createState();
		case StateModelPackage.RULE_STATE:
			return createRuleState();
		case StateModelPackage.ATTRIBUTE_DELTA:
			return createAttributeDelta();
		case StateModelPackage.STRUCTURAL_DELTA:
			return createStructuralDelta();
		case StateModelPackage.LINK:
			return createLink();
		case StateModelPackage.IBE_XMATCH:
			return createIBeXMatch();
		case StateModelPackage.VALUE:
			return createValue();
		case StateModelPackage.MATCH_DELTA:
			return createMatchDelta();
		case StateModelPackage.COMPLEX_PARAMETER:
			return createComplexParameter();
		case StateModelPackage.SIMPLE_PARAMETER:
			return createSimpleParameter();
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
	public StateContainer createStateContainer() {
		StateContainerImpl stateContainer = new StateContainerImpl();
		return stateContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public State createState() {
		StateImpl state = new StateImpl();
		return state;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RuleState createRuleState() {
		RuleStateImpl ruleState = new RuleStateImpl();
		return ruleState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AttributeDelta createAttributeDelta() {
		AttributeDeltaImpl attributeDelta = new AttributeDeltaImpl();
		return attributeDelta;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StructuralDelta createStructuralDelta() {
		StructuralDeltaImpl structuralDelta = new StructuralDeltaImpl();
		return structuralDelta;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Link createLink() {
		LinkImpl link = new LinkImpl();
		return link;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXMatch createIBeXMatch() {
		IBeXMatchImpl iBeXMatch = new IBeXMatchImpl();
		return iBeXMatch;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Value createValue() {
		ValueImpl value = new ValueImpl();
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MatchDelta createMatchDelta() {
		MatchDeltaImpl matchDelta = new MatchDeltaImpl();
		return matchDelta;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ComplexParameter createComplexParameter() {
		ComplexParameterImpl complexParameter = new ComplexParameterImpl();
		return complexParameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SimpleParameter createSimpleParameter() {
		SimpleParameterImpl simpleParameter = new SimpleParameterImpl();
		return simpleParameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StateModelPackage getStateModelPackage() {
		return (StateModelPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static StateModelPackage getPackage() {
		return StateModelPackage.eINSTANCE;
	}

} //StateModelFactoryImpl
