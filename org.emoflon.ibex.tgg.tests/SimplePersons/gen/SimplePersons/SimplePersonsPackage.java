/**
 */
package SimplePersons;

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
 * @see SimplePersons.SimplePersonsFactory
 * @model kind="package"
 * @generated
 */
public interface SimplePersonsPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "SimplePersons";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "platform:/resources/SimplePersons/model/SimplePersons.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "SimplePersons";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SimplePersonsPackage eINSTANCE = SimplePersons.impl.SimplePersonsPackageImpl.init();

	/**
	 * The meta object id for the '{@link SimplePersons.impl.PersonRegisterImpl <em>Person Register</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see SimplePersons.impl.PersonRegisterImpl
	 * @see SimplePersons.impl.SimplePersonsPackageImpl#getPersonRegister()
	 * @generated
	 */
	int PERSON_REGISTER = 0;

	/**
	 * The feature id for the '<em><b>Persons</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSON_REGISTER__PERSONS = 0;

	/**
	 * The number of structural features of the '<em>Person Register</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSON_REGISTER_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Person Register</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSON_REGISTER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link SimplePersons.impl.PersonImpl <em>Person</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see SimplePersons.impl.PersonImpl
	 * @see SimplePersons.impl.SimplePersonsPackageImpl#getPerson()
	 * @generated
	 */
	int PERSON = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSON__NAME = 0;

	/**
	 * The number of structural features of the '<em>Person</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSON_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Person</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERSON_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link SimplePersons.impl.MaleImpl <em>Male</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see SimplePersons.impl.MaleImpl
	 * @see SimplePersons.impl.SimplePersonsPackageImpl#getMale()
	 * @generated
	 */
	int MALE = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MALE__NAME = PERSON__NAME;

	/**
	 * The number of structural features of the '<em>Male</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MALE_FEATURE_COUNT = PERSON_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Male</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MALE_OPERATION_COUNT = PERSON_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link SimplePersons.impl.FemaleImpl <em>Female</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see SimplePersons.impl.FemaleImpl
	 * @see SimplePersons.impl.SimplePersonsPackageImpl#getFemale()
	 * @generated
	 */
	int FEMALE = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEMALE__NAME = PERSON__NAME;

	/**
	 * The number of structural features of the '<em>Female</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEMALE_FEATURE_COUNT = PERSON_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Female</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEMALE_OPERATION_COUNT = PERSON_OPERATION_COUNT + 0;

	/**
	 * Returns the meta object for class '{@link SimplePersons.PersonRegister <em>Person Register</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Person Register</em>'.
	 * @see SimplePersons.PersonRegister
	 * @generated
	 */
	EClass getPersonRegister();

	/**
	 * Returns the meta object for the containment reference list '{@link SimplePersons.PersonRegister#getPersons <em>Persons</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Persons</em>'.
	 * @see SimplePersons.PersonRegister#getPersons()
	 * @see #getPersonRegister()
	 * @generated
	 */
	EReference getPersonRegister_Persons();

	/**
	 * Returns the meta object for class '{@link SimplePersons.Person <em>Person</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Person</em>'.
	 * @see SimplePersons.Person
	 * @generated
	 */
	EClass getPerson();

	/**
	 * Returns the meta object for the attribute '{@link SimplePersons.Person#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see SimplePersons.Person#getName()
	 * @see #getPerson()
	 * @generated
	 */
	EAttribute getPerson_Name();

	/**
	 * Returns the meta object for class '{@link SimplePersons.Male <em>Male</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Male</em>'.
	 * @see SimplePersons.Male
	 * @generated
	 */
	EClass getMale();

	/**
	 * Returns the meta object for class '{@link SimplePersons.Female <em>Female</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Female</em>'.
	 * @see SimplePersons.Female
	 * @generated
	 */
	EClass getFemale();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SimplePersonsFactory getSimplePersonsFactory();

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
		 * The meta object literal for the '{@link SimplePersons.impl.PersonRegisterImpl <em>Person Register</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see SimplePersons.impl.PersonRegisterImpl
		 * @see SimplePersons.impl.SimplePersonsPackageImpl#getPersonRegister()
		 * @generated
		 */
		EClass PERSON_REGISTER = eINSTANCE.getPersonRegister();

		/**
		 * The meta object literal for the '<em><b>Persons</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PERSON_REGISTER__PERSONS = eINSTANCE.getPersonRegister_Persons();

		/**
		 * The meta object literal for the '{@link SimplePersons.impl.PersonImpl <em>Person</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see SimplePersons.impl.PersonImpl
		 * @see SimplePersons.impl.SimplePersonsPackageImpl#getPerson()
		 * @generated
		 */
		EClass PERSON = eINSTANCE.getPerson();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PERSON__NAME = eINSTANCE.getPerson_Name();

		/**
		 * The meta object literal for the '{@link SimplePersons.impl.MaleImpl <em>Male</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see SimplePersons.impl.MaleImpl
		 * @see SimplePersons.impl.SimplePersonsPackageImpl#getMale()
		 * @generated
		 */
		EClass MALE = eINSTANCE.getMale();

		/**
		 * The meta object literal for the '{@link SimplePersons.impl.FemaleImpl <em>Female</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see SimplePersons.impl.FemaleImpl
		 * @see SimplePersons.impl.SimplePersonsPackageImpl#getFemale()
		 * @generated
		 */
		EClass FEMALE = eINSTANCE.getFemale();

	}

} //SimplePersonsPackage
