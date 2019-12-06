/**
 */
package language.repair.impl;

import language.repair.*;

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
public class RepairFactoryImpl extends EFactoryImpl implements RepairFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static RepairFactory init() {
		try {
			RepairFactory theRepairFactory = (RepairFactory) EPackage.Registry.INSTANCE.getEFactory(RepairPackage.eNS_URI);
			if (theRepairFactory != null) {
				return theRepairFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new RepairFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RepairFactoryImpl() {
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
		case RepairPackage.EXTERNAL_SHORTCUT_RULE:
			return createExternalShortcutRule();
		case RepairPackage.TGG_RULE_ELEMENT_MAPPING:
			return createTGGRuleElementMapping();
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
	public ExternalShortcutRule createExternalShortcutRule() {
		ExternalShortcutRuleImpl externalShortcutRule = new ExternalShortcutRuleImpl();
		return externalShortcutRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TGGRuleElementMapping createTGGRuleElementMapping() {
		TGGRuleElementMappingImpl tggRuleElementMapping = new TGGRuleElementMappingImpl();
		return tggRuleElementMapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RepairPackage getRepairPackage() {
		return (RepairPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static RepairPackage getPackage() {
		return RepairPackage.eINSTANCE;
	}

} //RepairFactoryImpl
