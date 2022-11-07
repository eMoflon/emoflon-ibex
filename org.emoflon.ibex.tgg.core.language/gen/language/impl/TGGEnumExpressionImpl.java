/**
 */
package language.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import language.LanguagePackage;
import language.TGGEnumExpression;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TGG Enum Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link language.impl.TGGEnumExpressionImpl#getEenum <em>Eenum</em>}</li>
 *   <li>{@link language.impl.TGGEnumExpressionImpl#getLiteral <em>Literal</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TGGEnumExpressionImpl extends TGGExpressionImpl implements TGGEnumExpression {
	/**
	 * The cached value of the '{@link #getEenum() <em>Eenum</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEenum()
	 * @generated
	 * @ordered
	 */
	protected EEnum eenum;

	/**
	 * The cached value of the '{@link #getLiteral() <em>Literal</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLiteral()
	 * @generated
	 * @ordered
	 */
	protected EEnumLiteral literal;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TGGEnumExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LanguagePackage.Literals.TGG_ENUM_EXPRESSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getEenum() {
		if (eenum != null && eenum.eIsProxy()) {
			InternalEObject oldEenum = (InternalEObject) eenum;
			eenum = (EEnum) eResolveProxy(oldEenum);
			if (eenum != oldEenum) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							LanguagePackage.TGG_ENUM_EXPRESSION__EENUM, oldEenum, eenum));
			}
		}
		return eenum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum basicGetEenum() {
		return eenum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEenum(EEnum newEenum) {
		EEnum oldEenum = eenum;
		eenum = newEenum;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LanguagePackage.TGG_ENUM_EXPRESSION__EENUM, oldEenum,
					eenum));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnumLiteral getLiteral() {
		if (literal != null && literal.eIsProxy()) {
			InternalEObject oldLiteral = (InternalEObject) literal;
			literal = (EEnumLiteral) eResolveProxy(oldLiteral);
			if (literal != oldLiteral) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							LanguagePackage.TGG_ENUM_EXPRESSION__LITERAL, oldLiteral, literal));
			}
		}
		return literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnumLiteral basicGetLiteral() {
		return literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLiteral(EEnumLiteral newLiteral) {
		EEnumLiteral oldLiteral = literal;
		literal = newLiteral;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LanguagePackage.TGG_ENUM_EXPRESSION__LITERAL,
					oldLiteral, literal));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case LanguagePackage.TGG_ENUM_EXPRESSION__EENUM:
			if (resolve)
				return getEenum();
			return basicGetEenum();
		case LanguagePackage.TGG_ENUM_EXPRESSION__LITERAL:
			if (resolve)
				return getLiteral();
			return basicGetLiteral();
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
		case LanguagePackage.TGG_ENUM_EXPRESSION__EENUM:
			setEenum((EEnum) newValue);
			return;
		case LanguagePackage.TGG_ENUM_EXPRESSION__LITERAL:
			setLiteral((EEnumLiteral) newValue);
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
		case LanguagePackage.TGG_ENUM_EXPRESSION__EENUM:
			setEenum((EEnum) null);
			return;
		case LanguagePackage.TGG_ENUM_EXPRESSION__LITERAL:
			setLiteral((EEnumLiteral) null);
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
		case LanguagePackage.TGG_ENUM_EXPRESSION__EENUM:
			return eenum != null;
		case LanguagePackage.TGG_ENUM_EXPRESSION__LITERAL:
			return literal != null;
		}
		return super.eIsSet(featureID);
	}

} //TGGEnumExpressionImpl
