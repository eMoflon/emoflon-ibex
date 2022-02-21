/**
 */
package language.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import language.LanguagePackage;
import language.TGG;
import language.TGGAttributeConstraintDefinitionLibrary;
import language.TGGRule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TGG</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link language.impl.TGGImpl#getSrc <em>Src</em>}</li>
 *   <li>{@link language.impl.TGGImpl#getTrg <em>Trg</em>}</li>
 *   <li>{@link language.impl.TGGImpl#getCorr <em>Corr</em>}</li>
 *   <li>{@link language.impl.TGGImpl#getRules <em>Rules</em>}</li>
 *   <li>{@link language.impl.TGGImpl#getAttributeConstraintDefinitionLibrary <em>Attribute Constraint Definition Library</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TGGImpl extends TGGNamedElementImpl implements TGG {
	/**
	 * The cached value of the '{@link #getSrc() <em>Src</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSrc()
	 * @generated
	 * @ordered
	 */
	protected EList<EPackage> src;

	/**
	 * The cached value of the '{@link #getTrg() <em>Trg</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTrg()
	 * @generated
	 * @ordered
	 */
	protected EList<EPackage> trg;

	/**
	 * The cached value of the '{@link #getCorr() <em>Corr</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCorr()
	 * @generated
	 * @ordered
	 */
	protected EPackage corr;

	/**
	 * The cached value of the '{@link #getRules() <em>Rules</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRules()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGRule> rules;

	/**
	 * The cached value of the '{@link #getAttributeConstraintDefinitionLibrary() <em>Attribute Constraint Definition Library</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeConstraintDefinitionLibrary()
	 * @generated
	 * @ordered
	 */
	protected TGGAttributeConstraintDefinitionLibrary attributeConstraintDefinitionLibrary;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TGGImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LanguagePackage.Literals.TGG;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<EPackage> getSrc() {
		if (src == null) {
			src = new EObjectResolvingEList<EPackage>(EPackage.class, this, LanguagePackage.TGG__SRC);
		}
		return src;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<EPackage> getTrg() {
		if (trg == null) {
			trg = new EObjectResolvingEList<EPackage>(EPackage.class, this, LanguagePackage.TGG__TRG);
		}
		return trg;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EPackage getCorr() {
		if (corr != null && corr.eIsProxy()) {
			InternalEObject oldCorr = (InternalEObject) corr;
			corr = (EPackage) eResolveProxy(oldCorr);
			if (corr != oldCorr) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, LanguagePackage.TGG__CORR, oldCorr, corr));
			}
		}
		return corr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EPackage basicGetCorr() {
		return corr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCorr(EPackage newCorr) {
		EPackage oldCorr = corr;
		corr = newCorr;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LanguagePackage.TGG__CORR, oldCorr, corr));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TGGRule> getRules() {
		if (rules == null) {
			rules = new EObjectContainmentEList<TGGRule>(TGGRule.class, this, LanguagePackage.TGG__RULES);
		}
		return rules;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TGGAttributeConstraintDefinitionLibrary getAttributeConstraintDefinitionLibrary() {
		return attributeConstraintDefinitionLibrary;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAttributeConstraintDefinitionLibrary(TGGAttributeConstraintDefinitionLibrary newAttributeConstraintDefinitionLibrary,
			NotificationChain msgs) {
		TGGAttributeConstraintDefinitionLibrary oldAttributeConstraintDefinitionLibrary = attributeConstraintDefinitionLibrary;
		attributeConstraintDefinitionLibrary = newAttributeConstraintDefinitionLibrary;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, LanguagePackage.TGG__ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY,
					oldAttributeConstraintDefinitionLibrary, newAttributeConstraintDefinitionLibrary);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAttributeConstraintDefinitionLibrary(TGGAttributeConstraintDefinitionLibrary newAttributeConstraintDefinitionLibrary) {
		if (newAttributeConstraintDefinitionLibrary != attributeConstraintDefinitionLibrary) {
			NotificationChain msgs = null;
			if (attributeConstraintDefinitionLibrary != null)
				msgs = ((InternalEObject) attributeConstraintDefinitionLibrary).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - LanguagePackage.TGG__ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY, null, msgs);
			if (newAttributeConstraintDefinitionLibrary != null)
				msgs = ((InternalEObject) newAttributeConstraintDefinitionLibrary).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - LanguagePackage.TGG__ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY, null, msgs);
			msgs = basicSetAttributeConstraintDefinitionLibrary(newAttributeConstraintDefinitionLibrary, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, LanguagePackage.TGG__ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY,
					newAttributeConstraintDefinitionLibrary, newAttributeConstraintDefinitionLibrary));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case LanguagePackage.TGG__RULES:
			return ((InternalEList<?>) getRules()).basicRemove(otherEnd, msgs);
		case LanguagePackage.TGG__ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY:
			return basicSetAttributeConstraintDefinitionLibrary(null, msgs);
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
		case LanguagePackage.TGG__SRC:
			return getSrc();
		case LanguagePackage.TGG__TRG:
			return getTrg();
		case LanguagePackage.TGG__CORR:
			if (resolve)
				return getCorr();
			return basicGetCorr();
		case LanguagePackage.TGG__RULES:
			return getRules();
		case LanguagePackage.TGG__ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY:
			return getAttributeConstraintDefinitionLibrary();
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
		case LanguagePackage.TGG__SRC:
			getSrc().clear();
			getSrc().addAll((Collection<? extends EPackage>) newValue);
			return;
		case LanguagePackage.TGG__TRG:
			getTrg().clear();
			getTrg().addAll((Collection<? extends EPackage>) newValue);
			return;
		case LanguagePackage.TGG__CORR:
			setCorr((EPackage) newValue);
			return;
		case LanguagePackage.TGG__RULES:
			getRules().clear();
			getRules().addAll((Collection<? extends TGGRule>) newValue);
			return;
		case LanguagePackage.TGG__ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY:
			setAttributeConstraintDefinitionLibrary((TGGAttributeConstraintDefinitionLibrary) newValue);
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
		case LanguagePackage.TGG__SRC:
			getSrc().clear();
			return;
		case LanguagePackage.TGG__TRG:
			getTrg().clear();
			return;
		case LanguagePackage.TGG__CORR:
			setCorr((EPackage) null);
			return;
		case LanguagePackage.TGG__RULES:
			getRules().clear();
			return;
		case LanguagePackage.TGG__ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY:
			setAttributeConstraintDefinitionLibrary((TGGAttributeConstraintDefinitionLibrary) null);
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
		case LanguagePackage.TGG__SRC:
			return src != null && !src.isEmpty();
		case LanguagePackage.TGG__TRG:
			return trg != null && !trg.isEmpty();
		case LanguagePackage.TGG__CORR:
			return corr != null;
		case LanguagePackage.TGG__RULES:
			return rules != null && !rules.isEmpty();
		case LanguagePackage.TGG__ATTRIBUTE_CONSTRAINT_DEFINITION_LIBRARY:
			return attributeConstraintDefinitionLibrary != null;
		}
		return super.eIsSet(featureID);
	}

} //TGGImpl
