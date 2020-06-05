/**
 */
package org.emoflon.ibex.IBeXDisjunctPatternModel.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.emoflon.ibex.IBeXDisjunctPatternModel.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class IBeXDisjunctPatternModelFactoryImpl extends EFactoryImpl implements IBeXDisjunctPatternModelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static IBeXDisjunctPatternModelFactory init() {
		try {
			IBeXDisjunctPatternModelFactory theIBeXDisjunctPatternModelFactory = (IBeXDisjunctPatternModelFactory)EPackage.Registry.INSTANCE.getEFactory(IBeXDisjunctPatternModelPackage.eNS_URI);
			if (theIBeXDisjunctPatternModelFactory != null) {
				return theIBeXDisjunctPatternModelFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new IBeXDisjunctPatternModelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXDisjunctPatternModelFactoryImpl() {
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
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_CONTEXT_PATTERN: return createIBeXDisjunctContextPattern();
			case IBeXDisjunctPatternModelPackage.IBE_XDISJUNCT_ATTRIBUTES: return createIBeXDisjunctAttributes();
			case IBeXDisjunctPatternModelPackage.IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS: return createIBeXDependentInjectivityConstraints();
			case IBeXDisjunctPatternModelPackage.IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT: return createIBexDisjunctInjectivityConstraint();
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
	public IBeXDisjunctContextPattern createIBeXDisjunctContextPattern() {
		IBeXDisjunctContextPatternImpl iBeXDisjunctContextPattern = new IBeXDisjunctContextPatternImpl();
		return iBeXDisjunctContextPattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXDisjunctAttributes createIBeXDisjunctAttributes() {
		IBeXDisjunctAttributesImpl iBeXDisjunctAttributes = new IBeXDisjunctAttributesImpl();
		return iBeXDisjunctAttributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXDependentInjectivityConstraints createIBeXDependentInjectivityConstraints() {
		IBeXDependentInjectivityConstraintsImpl iBeXDependentInjectivityConstraints = new IBeXDependentInjectivityConstraintsImpl();
		return iBeXDependentInjectivityConstraints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBexDisjunctInjectivityConstraint createIBexDisjunctInjectivityConstraint() {
		IBexDisjunctInjectivityConstraintImpl iBexDisjunctInjectivityConstraint = new IBexDisjunctInjectivityConstraintImpl();
		return iBexDisjunctInjectivityConstraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXDisjunctPatternModelPackage getIBeXDisjunctPatternModelPackage() {
		return (IBeXDisjunctPatternModelPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static IBeXDisjunctPatternModelPackage getPackage() {
		return IBeXDisjunctPatternModelPackage.eINSTANCE;
	}

} //IBeXDisjunctPatternModelFactoryImpl
