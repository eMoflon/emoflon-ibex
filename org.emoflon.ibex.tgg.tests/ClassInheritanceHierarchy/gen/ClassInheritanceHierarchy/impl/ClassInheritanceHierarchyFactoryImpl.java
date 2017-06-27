/**
 */
package ClassInheritanceHierarchy.impl;

import ClassInheritanceHierarchy.*;

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
public class ClassInheritanceHierarchyFactoryImpl extends EFactoryImpl implements ClassInheritanceHierarchyFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ClassInheritanceHierarchyFactory init() {
		try {
			ClassInheritanceHierarchyFactory theClassInheritanceHierarchyFactory = (ClassInheritanceHierarchyFactory) EPackage.Registry.INSTANCE
					.getEFactory(ClassInheritanceHierarchyPackage.eNS_URI);
			if (theClassInheritanceHierarchyFactory != null) {
				return theClassInheritanceHierarchyFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ClassInheritanceHierarchyFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ClassInheritanceHierarchyFactoryImpl() {
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
		case ClassInheritanceHierarchyPackage.ATTRIBUTE:
			return createAttribute();
		case ClassInheritanceHierarchyPackage.CLASS_PACKAGE:
			return createClassPackage();
		case ClassInheritanceHierarchyPackage.CLAZZ:
			return createClazz();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Attribute createAttribute() {
		AttributeImpl attribute = new AttributeImpl();
		return attribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ClassPackage createClassPackage() {
		ClassPackageImpl classPackage = new ClassPackageImpl();
		return classPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Clazz createClazz() {
		ClazzImpl clazz = new ClazzImpl();
		return clazz;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ClassInheritanceHierarchyPackage getClassInheritanceHierarchyPackage() {
		return (ClassInheritanceHierarchyPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ClassInheritanceHierarchyPackage getPackage() {
		return ClassInheritanceHierarchyPackage.eINSTANCE;
	}

} //ClassInheritanceHierarchyFactoryImpl
