/**
 */
package org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TGGRuntimeModelFactoryImpl extends EFactoryImpl implements TGGRuntimeModelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TGGRuntimeModelFactory init() {
		try {
			TGGRuntimeModelFactory theTGGRuntimeModelFactory = (TGGRuntimeModelFactory) EPackage.Registry.INSTANCE
					.getEFactory(TGGRuntimeModelPackage.eNS_URI);
			if (theTGGRuntimeModelFactory != null) {
				return theTGGRuntimeModelFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new TGGRuntimeModelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGRuntimeModelFactoryImpl() {
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
		case TGGRuntimeModelPackage.TEMP_CONTAINER:
			return createTempContainer();
		case TGGRuntimeModelPackage.PROTOCOL:
			return createProtocol();
		case TGGRuntimeModelPackage.TGG_RULE_APPLICATION:
			return createTGGRuleApplication();
		case TGGRuntimeModelPackage.CORRESPONDENCE_SET:
			return createCorrespondenceSet();
		case TGGRuntimeModelPackage.CORRESPONDENCE:
			return createCorrespondence();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Protocol createProtocol() {
		ProtocolImpl protocol = new ProtocolImpl();
		return protocol;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGRuleApplication createTGGRuleApplication() {
		TGGRuleApplicationImpl tggRuleApplication = new TGGRuleApplicationImpl();
		return tggRuleApplication;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TempContainer createTempContainer() {
		TempContainerImpl tempContainer = new TempContainerImpl();
		return tempContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CorrespondenceSet createCorrespondenceSet() {
		CorrespondenceSetImpl correspondenceSet = new CorrespondenceSetImpl();
		return correspondenceSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Correspondence createCorrespondence() {
		CorrespondenceImpl correspondence = new CorrespondenceImpl();
		return correspondence;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGRuntimeModelPackage getTGGRuntimeModelPackage() {
		return (TGGRuntimeModelPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static TGGRuntimeModelPackage getPackage() {
		return TGGRuntimeModelPackage.eINSTANCE;
	}

} //TGGRuntimeModelFactoryImpl
