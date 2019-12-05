/**
 */
package IBeXLanguage.impl;

import IBeXLanguage.IBeXContextAlternatives;
import IBeXLanguage.IBeXContextPattern;
import IBeXLanguage.IBeXLanguagePackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IBe XContext Alternatives</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link IBeXLanguage.impl.IBeXContextAlternativesImpl#getAlternativePatterns <em>Alternative Patterns</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IBeXContextAlternativesImpl extends IBeXContextImpl implements IBeXContextAlternatives {
	/**
	 * The cached value of the '{@link #getAlternativePatterns() <em>Alternative Patterns</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlternativePatterns()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXContextPattern> alternativePatterns;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IBeXContextAlternativesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXLanguagePackage.Literals.IBE_XCONTEXT_ALTERNATIVES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXContextPattern> getAlternativePatterns() {
		if (alternativePatterns == null) {
			alternativePatterns = new EObjectContainmentEList<IBeXContextPattern>(IBeXContextPattern.class, this, IBeXLanguagePackage.IBE_XCONTEXT_ALTERNATIVES__ALTERNATIVE_PATTERNS);
		}
		return alternativePatterns;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IBeXLanguagePackage.IBE_XCONTEXT_ALTERNATIVES__ALTERNATIVE_PATTERNS:
			return ((InternalEList<?>) getAlternativePatterns()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case IBeXLanguagePackage.IBE_XCONTEXT_ALTERNATIVES__ALTERNATIVE_PATTERNS:
			return getAlternativePatterns();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case IBeXLanguagePackage.IBE_XCONTEXT_ALTERNATIVES__ALTERNATIVE_PATTERNS:
			getAlternativePatterns().clear();
			getAlternativePatterns().addAll((Collection<? extends IBeXContextPattern>) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case IBeXLanguagePackage.IBE_XCONTEXT_ALTERNATIVES__ALTERNATIVE_PATTERNS:
			getAlternativePatterns().clear();
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case IBeXLanguagePackage.IBE_XCONTEXT_ALTERNATIVES__ALTERNATIVE_PATTERNS:
			return alternativePatterns != null && !alternativePatterns.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //IBeXContextAlternativesImpl
