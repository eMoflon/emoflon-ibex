/**
 */
package language.repair;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see language.repair.RepairFactory
 * @model kind="package"
 * @generated
 */
public interface RepairPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "repair";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "platform:/plugin/org.emoflon.ibex.tgg.language/model/Language.ecore#/repair";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "repair";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RepairPackage eINSTANCE = language.repair.impl.RepairPackageImpl.init();

	/**
	 * The meta object id for the '{@link language.repair.impl.ExternalShortcutRuleImpl <em>External Shortcut Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see language.repair.impl.ExternalShortcutRuleImpl
	 * @see language.repair.impl.RepairPackageImpl#getExternalShortcutRule()
	 * @generated
	 */
	int EXTERNAL_SHORTCUT_RULE = 0;

	/**
	 * The feature id for the '<em><b>Source Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_SHORTCUT_RULE__SOURCE_RULE = 0;

	/**
	 * The feature id for the '<em><b>Target Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_SHORTCUT_RULE__TARGET_RULE = 1;

	/**
	 * The feature id for the '<em><b>Deletions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_SHORTCUT_RULE__DELETIONS = 2;

	/**
	 * The feature id for the '<em><b>Creations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_SHORTCUT_RULE__CREATIONS = 3;

	/**
	 * The feature id for the '<em><b>Unbound Src Context</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_SHORTCUT_RULE__UNBOUND_SRC_CONTEXT = 4;

	/**
	 * The feature id for the '<em><b>Unbound Trg Context</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_SHORTCUT_RULE__UNBOUND_TRG_CONTEXT = 5;

	/**
	 * The feature id for the '<em><b>Mapping</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_SHORTCUT_RULE__MAPPING = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_SHORTCUT_RULE__NAME = 7;

	/**
	 * The number of structural features of the '<em>External Shortcut Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_SHORTCUT_RULE_FEATURE_COUNT = 8;

	/**
	 * The number of operations of the '<em>External Shortcut Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_SHORTCUT_RULE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link language.repair.impl.TGGRuleElementMappingImpl <em>TGG Rule Element Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see language.repair.impl.TGGRuleElementMappingImpl
	 * @see language.repair.impl.RepairPackageImpl#getTGGRuleElementMapping()
	 * @generated
	 */
	int TGG_RULE_ELEMENT_MAPPING = 1;

	/**
	 * The feature id for the '<em><b>Source Rule Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_ELEMENT_MAPPING__SOURCE_RULE_ELEMENT = 0;

	/**
	 * The feature id for the '<em><b>Target Rule Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_ELEMENT_MAPPING__TARGET_RULE_ELEMENT = 1;

	/**
	 * The number of structural features of the '<em>TGG Rule Element Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_ELEMENT_MAPPING_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>TGG Rule Element Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TGG_RULE_ELEMENT_MAPPING_OPERATION_COUNT = 0;

	/**
	 * Returns the meta object for class '{@link language.repair.ExternalShortcutRule <em>External Shortcut Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>External Shortcut Rule</em>'.
	 * @see language.repair.ExternalShortcutRule
	 * @generated
	 */
	EClass getExternalShortcutRule();

	/**
	 * Returns the meta object for the reference '{@link language.repair.ExternalShortcutRule#getSourceRule <em>Source Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source Rule</em>'.
	 * @see language.repair.ExternalShortcutRule#getSourceRule()
	 * @see #getExternalShortcutRule()
	 * @generated
	 */
	EReference getExternalShortcutRule_SourceRule();

	/**
	 * Returns the meta object for the reference '{@link language.repair.ExternalShortcutRule#getTargetRule <em>Target Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target Rule</em>'.
	 * @see language.repair.ExternalShortcutRule#getTargetRule()
	 * @see #getExternalShortcutRule()
	 * @generated
	 */
	EReference getExternalShortcutRule_TargetRule();

	/**
	 * Returns the meta object for the reference list '{@link language.repair.ExternalShortcutRule#getDeletions <em>Deletions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Deletions</em>'.
	 * @see language.repair.ExternalShortcutRule#getDeletions()
	 * @see #getExternalShortcutRule()
	 * @generated
	 */
	EReference getExternalShortcutRule_Deletions();

	/**
	 * Returns the meta object for the reference list '{@link language.repair.ExternalShortcutRule#getCreations <em>Creations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Creations</em>'.
	 * @see language.repair.ExternalShortcutRule#getCreations()
	 * @see #getExternalShortcutRule()
	 * @generated
	 */
	EReference getExternalShortcutRule_Creations();

	/**
	 * Returns the meta object for the reference list '{@link language.repair.ExternalShortcutRule#getUnboundSrcContext <em>Unbound Src Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Unbound Src Context</em>'.
	 * @see language.repair.ExternalShortcutRule#getUnboundSrcContext()
	 * @see #getExternalShortcutRule()
	 * @generated
	 */
	EReference getExternalShortcutRule_UnboundSrcContext();

	/**
	 * Returns the meta object for the reference list '{@link language.repair.ExternalShortcutRule#getUnboundTrgContext <em>Unbound Trg Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Unbound Trg Context</em>'.
	 * @see language.repair.ExternalShortcutRule#getUnboundTrgContext()
	 * @see #getExternalShortcutRule()
	 * @generated
	 */
	EReference getExternalShortcutRule_UnboundTrgContext();

	/**
	 * Returns the meta object for the containment reference list '{@link language.repair.ExternalShortcutRule#getMapping <em>Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Mapping</em>'.
	 * @see language.repair.ExternalShortcutRule#getMapping()
	 * @see #getExternalShortcutRule()
	 * @generated
	 */
	EReference getExternalShortcutRule_Mapping();

	/**
	 * Returns the meta object for the attribute '{@link language.repair.ExternalShortcutRule#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see language.repair.ExternalShortcutRule#getName()
	 * @see #getExternalShortcutRule()
	 * @generated
	 */
	EAttribute getExternalShortcutRule_Name();

	/**
	 * Returns the meta object for class '{@link language.repair.TGGRuleElementMapping <em>TGG Rule Element Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>TGG Rule Element Mapping</em>'.
	 * @see language.repair.TGGRuleElementMapping
	 * @generated
	 */
	EClass getTGGRuleElementMapping();

	/**
	 * Returns the meta object for the reference '{@link language.repair.TGGRuleElementMapping#getSourceRuleElement <em>Source Rule Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source Rule Element</em>'.
	 * @see language.repair.TGGRuleElementMapping#getSourceRuleElement()
	 * @see #getTGGRuleElementMapping()
	 * @generated
	 */
	EReference getTGGRuleElementMapping_SourceRuleElement();

	/**
	 * Returns the meta object for the reference '{@link language.repair.TGGRuleElementMapping#getTargetRuleElement <em>Target Rule Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target Rule Element</em>'.
	 * @see language.repair.TGGRuleElementMapping#getTargetRuleElement()
	 * @see #getTGGRuleElementMapping()
	 * @generated
	 */
	EReference getTGGRuleElementMapping_TargetRuleElement();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	RepairFactory getRepairFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link language.repair.impl.ExternalShortcutRuleImpl <em>External Shortcut Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see language.repair.impl.ExternalShortcutRuleImpl
		 * @see language.repair.impl.RepairPackageImpl#getExternalShortcutRule()
		 * @generated
		 */
		EClass EXTERNAL_SHORTCUT_RULE = eINSTANCE.getExternalShortcutRule();

		/**
		 * The meta object literal for the '<em><b>Source Rule</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXTERNAL_SHORTCUT_RULE__SOURCE_RULE = eINSTANCE.getExternalShortcutRule_SourceRule();

		/**
		 * The meta object literal for the '<em><b>Target Rule</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXTERNAL_SHORTCUT_RULE__TARGET_RULE = eINSTANCE.getExternalShortcutRule_TargetRule();

		/**
		 * The meta object literal for the '<em><b>Deletions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXTERNAL_SHORTCUT_RULE__DELETIONS = eINSTANCE.getExternalShortcutRule_Deletions();

		/**
		 * The meta object literal for the '<em><b>Creations</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXTERNAL_SHORTCUT_RULE__CREATIONS = eINSTANCE.getExternalShortcutRule_Creations();

		/**
		 * The meta object literal for the '<em><b>Unbound Src Context</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXTERNAL_SHORTCUT_RULE__UNBOUND_SRC_CONTEXT = eINSTANCE.getExternalShortcutRule_UnboundSrcContext();

		/**
		 * The meta object literal for the '<em><b>Unbound Trg Context</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXTERNAL_SHORTCUT_RULE__UNBOUND_TRG_CONTEXT = eINSTANCE.getExternalShortcutRule_UnboundTrgContext();

		/**
		 * The meta object literal for the '<em><b>Mapping</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXTERNAL_SHORTCUT_RULE__MAPPING = eINSTANCE.getExternalShortcutRule_Mapping();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTERNAL_SHORTCUT_RULE__NAME = eINSTANCE.getExternalShortcutRule_Name();

		/**
		 * The meta object literal for the '{@link language.repair.impl.TGGRuleElementMappingImpl <em>TGG Rule Element Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see language.repair.impl.TGGRuleElementMappingImpl
		 * @see language.repair.impl.RepairPackageImpl#getTGGRuleElementMapping()
		 * @generated
		 */
		EClass TGG_RULE_ELEMENT_MAPPING = eINSTANCE.getTGGRuleElementMapping();

		/**
		 * The meta object literal for the '<em><b>Source Rule Element</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE_ELEMENT_MAPPING__SOURCE_RULE_ELEMENT = eINSTANCE
				.getTGGRuleElementMapping_SourceRuleElement();

		/**
		 * The meta object literal for the '<em><b>Target Rule Element</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TGG_RULE_ELEMENT_MAPPING__TARGET_RULE_ELEMENT = eINSTANCE
				.getTGGRuleElementMapping_TargetRuleElement();

	}

} //RepairPackage
