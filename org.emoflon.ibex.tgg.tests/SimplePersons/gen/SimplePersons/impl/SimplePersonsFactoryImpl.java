/**
 */
package SimplePersons.impl;

import SimplePersons.*;

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
public class SimplePersonsFactoryImpl extends EFactoryImpl implements SimplePersonsFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SimplePersonsFactory init() {
		try {
			SimplePersonsFactory theSimplePersonsFactory = (SimplePersonsFactory) EPackage.Registry.INSTANCE
					.getEFactory(SimplePersonsPackage.eNS_URI);
			if (theSimplePersonsFactory != null) {
				return theSimplePersonsFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new SimplePersonsFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimplePersonsFactoryImpl() {
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
		case SimplePersonsPackage.PERSON_REGISTER:
			return createPersonRegister();
		case SimplePersonsPackage.MALE:
			return createMale();
		case SimplePersonsPackage.FEMALE:
			return createFemale();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PersonRegister createPersonRegister() {
		PersonRegisterImpl personRegister = new PersonRegisterImpl();
		return personRegister;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Male createMale() {
		MaleImpl male = new MaleImpl();
		return male;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Female createFemale() {
		FemaleImpl female = new FemaleImpl();
		return female;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimplePersonsPackage getSimplePersonsPackage() {
		return (SimplePersonsPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static SimplePersonsPackage getPackage() {
		return SimplePersonsPackage.eINSTANCE;
	}

} //SimplePersonsFactoryImpl
