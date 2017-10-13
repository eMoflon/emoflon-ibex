/**
 */
package language.impl;

import language.LanguagePackage;
import language.TGGComplementRule;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TGG Complement Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link language.impl.TGGComplementRuleImpl#isAdditionalContext <em>Additional Context</em>}</li>
 *   <li>{@link language.impl.TGGComplementRuleImpl#getLowerRABound <em>Lower RA Bound</em>}</li>
 *   <li>{@link language.impl.TGGComplementRuleImpl#getUpperRABound <em>Upper RA Bound</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TGGComplementRuleImpl extends TGGRuleImpl implements TGGComplementRule {
	/**
	 * The default value of the '{@link #isAdditionalContext() <em>Additional Context</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAdditionalContext()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ADDITIONAL_CONTEXT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAdditionalContext() <em>Additional Context</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAdditionalContext()
	 * @generated
	 * @ordered
	 */
	protected boolean additionalContext = ADDITIONAL_CONTEXT_EDEFAULT;

	/**
	 * The default value of the '{@link #getLowerRABound() <em>Lower RA Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLowerRABound()
	 * @generated
	 * @ordered
	 */
	protected static final int LOWER_RA_BOUND_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getLowerRABound() <em>Lower RA Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLowerRABound()
	 * @generated
	 * @ordered
	 */
	protected int lowerRABound = LOWER_RA_BOUND_EDEFAULT;

	/**
	 * The default value of the '{@link #getUpperRABound() <em>Upper RA Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpperRABound()
	 * @generated
	 * @ordered
	 */
	protected static final int UPPER_RA_BOUND_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getUpperRABound() <em>Upper RA Bound</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUpperRABound()
	 * @generated
	 * @ordered
	 */
	protected int upperRABound = UPPER_RA_BOUND_EDEFAULT;

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
	public boolean isAdditionalContext() {
		return additionalContext;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAdditionalContext(boolean newAdditionalContext) {
		boolean oldAdditionalContext = additionalContext;
		additionalContext = newAdditionalContext;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					LanguagePackage.TGG_COMPLEMENT_RULE__ADDITIONAL_CONTEXT, oldAdditionalContext, additionalContext));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getLowerRABound() {
		return lowerRABound;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLowerRABound(int newLowerRABound) {
		int oldLowerRABound = lowerRABound;
		lowerRABound = newLowerRABound;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LanguagePackage.TGG_COMPLEMENT_RULE__LOWER_RA_BOUND,
					oldLowerRABound, lowerRABound));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getUpperRABound() {
		return upperRABound;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUpperRABound(int newUpperRABound) {
		int oldUpperRABound = upperRABound;
		upperRABound = newUpperRABound;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LanguagePackage.TGG_COMPLEMENT_RULE__UPPER_RA_BOUND,
					oldUpperRABound, upperRABound));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case LanguagePackage.TGG_COMPLEMENT_RULE__ADDITIONAL_CONTEXT:
			return isAdditionalContext();
		case LanguagePackage.TGG_COMPLEMENT_RULE__LOWER_RA_BOUND:
			return getLowerRABound();
		case LanguagePackage.TGG_COMPLEMENT_RULE__UPPER_RA_BOUND:
			return getUpperRABound();
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
		case LanguagePackage.TGG_COMPLEMENT_RULE__ADDITIONAL_CONTEXT:
			setAdditionalContext((Boolean) newValue);
			return;
		case LanguagePackage.TGG_COMPLEMENT_RULE__LOWER_RA_BOUND:
			setLowerRABound((Integer) newValue);
			return;
		case LanguagePackage.TGG_COMPLEMENT_RULE__UPPER_RA_BOUND:
			setUpperRABound((Integer) newValue);
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
		case LanguagePackage.TGG_COMPLEMENT_RULE__ADDITIONAL_CONTEXT:
			setAdditionalContext(ADDITIONAL_CONTEXT_EDEFAULT);
			return;
		case LanguagePackage.TGG_COMPLEMENT_RULE__LOWER_RA_BOUND:
			setLowerRABound(LOWER_RA_BOUND_EDEFAULT);
			return;
		case LanguagePackage.TGG_COMPLEMENT_RULE__UPPER_RA_BOUND:
			setUpperRABound(UPPER_RA_BOUND_EDEFAULT);
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
		case LanguagePackage.TGG_COMPLEMENT_RULE__ADDITIONAL_CONTEXT:
			return additionalContext != ADDITIONAL_CONTEXT_EDEFAULT;
		case LanguagePackage.TGG_COMPLEMENT_RULE__LOWER_RA_BOUND:
			return lowerRABound != LOWER_RA_BOUND_EDEFAULT;
		case LanguagePackage.TGG_COMPLEMENT_RULE__UPPER_RA_BOUND:
			return upperRABound != UPPER_RA_BOUND_EDEFAULT;
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
		result.append(" (additionalContext: ");
		result.append(additionalContext);
		result.append(", lowerRABound: ");
		result.append(lowerRABound);
		result.append(", upperRABound: ");
		result.append(upperRABound);
		result.append(')');
		return result.toString();
	}

} //TGGComplementRuleImpl
