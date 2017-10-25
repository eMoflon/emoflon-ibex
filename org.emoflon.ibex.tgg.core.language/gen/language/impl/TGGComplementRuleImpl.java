/**
 */
package language.impl;

import language.LanguagePackage;
import language.TGGComplementRule;

import language.TGGRule;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TGG Complement Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link language.impl.TGGComplementRuleImpl#isBounded <em>Bounded</em>}</li>
 *   <li>{@link language.impl.TGGComplementRuleImpl#getKernel <em>Kernel</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TGGComplementRuleImpl extends TGGRuleImpl implements TGGComplementRule {
	/**
	 * The default value of the '{@link #isBounded() <em>Bounded</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isBounded()
	 * @generated
	 * @ordered
	 */
	protected static final boolean BOUNDED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isBounded() <em>Bounded</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isBounded()
	 * @generated
	 * @ordered
	 */
	protected boolean bounded = BOUNDED_EDEFAULT;

	/**
	 * The cached value of the '{@link #getKernel() <em>Kernel</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKernel()
	 * @generated
	 * @ordered
	 */
	protected TGGRule kernel;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TGGComplementRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LanguagePackage.Literals.TGG_COMPLEMENT_RULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isBounded() {
		return bounded;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBounded(boolean newBounded) {
		boolean oldBounded = bounded;
		bounded = newBounded;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LanguagePackage.TGG_COMPLEMENT_RULE__BOUNDED,
					oldBounded, bounded));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGRule getKernel() {
		if (kernel != null && kernel.eIsProxy()) {
			InternalEObject oldKernel = (InternalEObject) kernel;
			kernel = (TGGRule) eResolveProxy(oldKernel);
			if (kernel != oldKernel) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							LanguagePackage.TGG_COMPLEMENT_RULE__KERNEL, oldKernel, kernel));
			}
		}
		return kernel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGRule basicGetKernel() {
		return kernel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKernel(TGGRule newKernel) {
		TGGRule oldKernel = kernel;
		kernel = newKernel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LanguagePackage.TGG_COMPLEMENT_RULE__KERNEL,
					oldKernel, kernel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case LanguagePackage.TGG_COMPLEMENT_RULE__BOUNDED:
			return isBounded();
		case LanguagePackage.TGG_COMPLEMENT_RULE__KERNEL:
			if (resolve)
				return getKernel();
			return basicGetKernel();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case LanguagePackage.TGG_COMPLEMENT_RULE__BOUNDED:
			setBounded((Boolean) newValue);
			return;
		case LanguagePackage.TGG_COMPLEMENT_RULE__KERNEL:
			setKernel((TGGRule) newValue);
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
		case LanguagePackage.TGG_COMPLEMENT_RULE__BOUNDED:
			setBounded(BOUNDED_EDEFAULT);
			return;
		case LanguagePackage.TGG_COMPLEMENT_RULE__KERNEL:
			setKernel((TGGRule) null);
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
		case LanguagePackage.TGG_COMPLEMENT_RULE__BOUNDED:
			return bounded != BOUNDED_EDEFAULT;
		case LanguagePackage.TGG_COMPLEMENT_RULE__KERNEL:
			return kernel != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (bounded: ");
		result.append(bounded);
		result.append(')');
		return result.toString();
	}

} //TGGComplementRuleImpl
