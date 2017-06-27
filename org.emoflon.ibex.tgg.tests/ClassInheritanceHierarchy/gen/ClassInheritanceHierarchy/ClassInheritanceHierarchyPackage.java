/**
 */
package ClassInheritanceHierarchy;

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
 * @see ClassInheritanceHierarchy.ClassInheritanceHierarchyFactory
 * @model kind="package"
 * @generated
 */
public interface ClassInheritanceHierarchyPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "ClassInheritanceHierarchy";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "platform:/plugin/ClassInheritanceHierarchy/model/ClassInheritanceHierarchy.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "ClassInheritanceHierarchy";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ClassInheritanceHierarchyPackage eINSTANCE = ClassInheritanceHierarchy.impl.ClassInheritanceHierarchyPackageImpl
			.init();

	/**
	 * The meta object id for the '{@link ClassInheritanceHierarchy.impl.NamedElementImpl <em>Named Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see ClassInheritanceHierarchy.impl.NamedElementImpl
	 * @see ClassInheritanceHierarchy.impl.ClassInheritanceHierarchyPackageImpl#getNamedElement()
	 * @generated
	 */
	int NAMED_ELEMENT = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT__NAME = 0;

	/**
	 * The number of structural features of the '<em>Named Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Named Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link ClassInheritanceHierarchy.impl.AttributeImpl <em>Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see ClassInheritanceHierarchy.impl.AttributeImpl
	 * @see ClassInheritanceHierarchy.impl.ClassInheritanceHierarchyPackageImpl#getAttribute()
	 * @generated
	 */
	int ATTRIBUTE = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Clazz</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__CLAZZ = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__TYPE = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link ClassInheritanceHierarchy.impl.ClassPackageImpl <em>Class Package</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see ClassInheritanceHierarchy.impl.ClassPackageImpl
	 * @see ClassInheritanceHierarchy.impl.ClassInheritanceHierarchyPackageImpl#getClassPackage()
	 * @generated
	 */
	int CLASS_PACKAGE = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_PACKAGE__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Classes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_PACKAGE__CLASSES = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Class Package</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_PACKAGE_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Class Package</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_PACKAGE_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link ClassInheritanceHierarchy.impl.ClazzImpl <em>Clazz</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see ClassInheritanceHierarchy.impl.ClazzImpl
	 * @see ClassInheritanceHierarchy.impl.ClassInheritanceHierarchyPackageImpl#getClazz()
	 * @generated
	 */
	int CLAZZ = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLAZZ__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Super Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLAZZ__SUPER_CLASS = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Sub Class</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLAZZ__SUB_CLASS = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Package</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLAZZ__PACKAGE = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLAZZ__ATTRIBUTES = NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Clazz</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLAZZ_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Clazz</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLAZZ_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * Returns the meta object for class '{@link ClassInheritanceHierarchy.Attribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute</em>'.
	 * @see ClassInheritanceHierarchy.Attribute
	 * @generated
	 */
	EClass getAttribute();

	/**
	 * Returns the meta object for the container reference '{@link ClassInheritanceHierarchy.Attribute#getClazz <em>Clazz</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Clazz</em>'.
	 * @see ClassInheritanceHierarchy.Attribute#getClazz()
	 * @see #getAttribute()
	 * @generated
	 */
	EReference getAttribute_Clazz();

	/**
	 * Returns the meta object for the attribute '{@link ClassInheritanceHierarchy.Attribute#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see ClassInheritanceHierarchy.Attribute#getType()
	 * @see #getAttribute()
	 * @generated
	 */
	EAttribute getAttribute_Type();

	/**
	 * Returns the meta object for class '{@link ClassInheritanceHierarchy.ClassPackage <em>Class Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Class Package</em>'.
	 * @see ClassInheritanceHierarchy.ClassPackage
	 * @generated
	 */
	EClass getClassPackage();

	/**
	 * Returns the meta object for the containment reference list '{@link ClassInheritanceHierarchy.ClassPackage#getClasses <em>Classes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Classes</em>'.
	 * @see ClassInheritanceHierarchy.ClassPackage#getClasses()
	 * @see #getClassPackage()
	 * @generated
	 */
	EReference getClassPackage_Classes();

	/**
	 * Returns the meta object for class '{@link ClassInheritanceHierarchy.Clazz <em>Clazz</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Clazz</em>'.
	 * @see ClassInheritanceHierarchy.Clazz
	 * @generated
	 */
	EClass getClazz();

	/**
	 * Returns the meta object for the reference '{@link ClassInheritanceHierarchy.Clazz#getSuperClass <em>Super Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Super Class</em>'.
	 * @see ClassInheritanceHierarchy.Clazz#getSuperClass()
	 * @see #getClazz()
	 * @generated
	 */
	EReference getClazz_SuperClass();

	/**
	 * Returns the meta object for the reference list '{@link ClassInheritanceHierarchy.Clazz#getSubClass <em>Sub Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Sub Class</em>'.
	 * @see ClassInheritanceHierarchy.Clazz#getSubClass()
	 * @see #getClazz()
	 * @generated
	 */
	EReference getClazz_SubClass();

	/**
	 * Returns the meta object for the container reference '{@link ClassInheritanceHierarchy.Clazz#getPackage <em>Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Package</em>'.
	 * @see ClassInheritanceHierarchy.Clazz#getPackage()
	 * @see #getClazz()
	 * @generated
	 */
	EReference getClazz_Package();

	/**
	 * Returns the meta object for the containment reference list '{@link ClassInheritanceHierarchy.Clazz#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attributes</em>'.
	 * @see ClassInheritanceHierarchy.Clazz#getAttributes()
	 * @see #getClazz()
	 * @generated
	 */
	EReference getClazz_Attributes();

	/**
	 * Returns the meta object for class '{@link ClassInheritanceHierarchy.NamedElement <em>Named Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Named Element</em>'.
	 * @see ClassInheritanceHierarchy.NamedElement
	 * @generated
	 */
	EClass getNamedElement();

	/**
	 * Returns the meta object for the attribute '{@link ClassInheritanceHierarchy.NamedElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see ClassInheritanceHierarchy.NamedElement#getName()
	 * @see #getNamedElement()
	 * @generated
	 */
	EAttribute getNamedElement_Name();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ClassInheritanceHierarchyFactory getClassInheritanceHierarchyFactory();

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
		 * The meta object literal for the '{@link ClassInheritanceHierarchy.impl.AttributeImpl <em>Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see ClassInheritanceHierarchy.impl.AttributeImpl
		 * @see ClassInheritanceHierarchy.impl.ClassInheritanceHierarchyPackageImpl#getAttribute()
		 * @generated
		 */
		EClass ATTRIBUTE = eINSTANCE.getAttribute();

		/**
		 * The meta object literal for the '<em><b>Clazz</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE__CLAZZ = eINSTANCE.getAttribute_Clazz();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE__TYPE = eINSTANCE.getAttribute_Type();

		/**
		 * The meta object literal for the '{@link ClassInheritanceHierarchy.impl.ClassPackageImpl <em>Class Package</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see ClassInheritanceHierarchy.impl.ClassPackageImpl
		 * @see ClassInheritanceHierarchy.impl.ClassInheritanceHierarchyPackageImpl#getClassPackage()
		 * @generated
		 */
		EClass CLASS_PACKAGE = eINSTANCE.getClassPackage();

		/**
		 * The meta object literal for the '<em><b>Classes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLASS_PACKAGE__CLASSES = eINSTANCE.getClassPackage_Classes();

		/**
		 * The meta object literal for the '{@link ClassInheritanceHierarchy.impl.ClazzImpl <em>Clazz</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see ClassInheritanceHierarchy.impl.ClazzImpl
		 * @see ClassInheritanceHierarchy.impl.ClassInheritanceHierarchyPackageImpl#getClazz()
		 * @generated
		 */
		EClass CLAZZ = eINSTANCE.getClazz();

		/**
		 * The meta object literal for the '<em><b>Super Class</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLAZZ__SUPER_CLASS = eINSTANCE.getClazz_SuperClass();

		/**
		 * The meta object literal for the '<em><b>Sub Class</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLAZZ__SUB_CLASS = eINSTANCE.getClazz_SubClass();

		/**
		 * The meta object literal for the '<em><b>Package</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLAZZ__PACKAGE = eINSTANCE.getClazz_Package();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLAZZ__ATTRIBUTES = eINSTANCE.getClazz_Attributes();

		/**
		 * The meta object literal for the '{@link ClassInheritanceHierarchy.impl.NamedElementImpl <em>Named Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see ClassInheritanceHierarchy.impl.NamedElementImpl
		 * @see ClassInheritanceHierarchy.impl.ClassInheritanceHierarchyPackageImpl#getNamedElement()
		 * @generated
		 */
		EClass NAMED_ELEMENT = eINSTANCE.getNamedElement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMED_ELEMENT__NAME = eINSTANCE.getNamedElement_Name();

	}

} //ClassInheritanceHierarchyPackage
