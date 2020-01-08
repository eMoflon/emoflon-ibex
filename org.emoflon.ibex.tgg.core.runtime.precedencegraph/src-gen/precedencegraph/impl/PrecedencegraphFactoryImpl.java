/**
 */
package precedencegraph.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import precedencegraph.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PrecedencegraphFactoryImpl extends EFactoryImpl implements PrecedencegraphFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PrecedencegraphFactory init() {
		try {
			PrecedencegraphFactory thePrecedencegraphFactory = (PrecedencegraphFactory)EPackage.Registry.INSTANCE.getEFactory(PrecedencegraphPackage.eNS_URI);
			if (thePrecedencegraphFactory != null) {
				return thePrecedencegraphFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new PrecedencegraphFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PrecedencegraphFactoryImpl() {
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
			case PrecedencegraphPackage.PRECEDENCE_NODE_CONTAINER: return createPrecedenceNodeContainer();
			case PrecedencegraphPackage.PRECEDENCE_NODE: return createPrecedenceNode();
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
	public PrecedenceNodeContainer createPrecedenceNodeContainer() {
		PrecedenceNodeContainerImpl precedenceNodeContainer = new PrecedenceNodeContainerImpl();
		return precedenceNodeContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PrecedenceNode createPrecedenceNode() {
		PrecedenceNodeImpl precedenceNode = new PrecedenceNodeImpl();
		return precedenceNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PrecedencegraphPackage getPrecedencegraphPackage() {
		return (PrecedencegraphPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static PrecedencegraphPackage getPackage() {
		return PrecedencegraphPackage.eINSTANCE;
	}

} //PrecedencegraphFactoryImpl
