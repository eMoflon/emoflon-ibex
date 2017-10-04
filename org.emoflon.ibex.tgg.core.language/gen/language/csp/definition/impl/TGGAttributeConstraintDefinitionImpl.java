/**
 */
package language.csp.definition.impl;

import java.util.Collection;

import language.basic.impl.TGGNamedElementImpl;

import language.csp.definition.DefinitionPackage;
import language.csp.definition.TGGAttributeConstraintAdornment;
import language.csp.definition.TGGAttributeConstraintDefinition;
import language.csp.definition.TGGAttributeConstraintParameterDefinition;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TGG Attribute Constraint Definition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link language.csp.definition.impl.TGGAttributeConstraintDefinitionImpl#isUserDefined <em>User Defined</em>}</li>
 *   <li>{@link language.csp.definition.impl.TGGAttributeConstraintDefinitionImpl#getParameterDefinitions <em>Parameter Definitions</em>}</li>
 *   <li>{@link language.csp.definition.impl.TGGAttributeConstraintDefinitionImpl#getSyncAdornments <em>Sync Adornments</em>}</li>
 *   <li>{@link language.csp.definition.impl.TGGAttributeConstraintDefinitionImpl#getGenAdornments <em>Gen Adornments</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TGGAttributeConstraintDefinitionImpl extends TGGNamedElementImpl
		implements TGGAttributeConstraintDefinition {
	/**
	 * The default value of the '{@link #isUserDefined() <em>User Defined</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUserDefined()
	 * @generated
	 * @ordered
	 */
	protected static final boolean USER_DEFINED_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isUserDefined() <em>User Defined</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUserDefined()
	 * @generated
	 * @ordered
	 */
	protected boolean userDefined = USER_DEFINED_EDEFAULT;

	/**
	 * The cached value of the '{@link #getParameterDefinitions() <em>Parameter Definitions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameterDefinitions()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGAttributeConstraintParameterDefinition> parameterDefinitions;

	/**
	 * The cached value of the '{@link #getSyncAdornments() <em>Sync Adornments</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSyncAdornments()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGAttributeConstraintAdornment> syncAdornments;

	/**
	 * The cached value of the '{@link #getGenAdornments() <em>Gen Adornments</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGenAdornments()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGAttributeConstraintAdornment> genAdornments;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TGGAttributeConstraintDefinitionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DefinitionPackage.Literals.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isUserDefined() {
		return userDefined;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUserDefined(boolean newUserDefined) {
		boolean oldUserDefined = userDefined;
		userDefined = newUserDefined;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					DefinitionPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__USER_DEFINED, oldUserDefined, userDefined));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TGGAttributeConstraintParameterDefinition> getParameterDefinitions() {
		if (parameterDefinitions == null) {
			parameterDefinitions = new EObjectContainmentEList<TGGAttributeConstraintParameterDefinition>(
					TGGAttributeConstraintParameterDefinition.class, this,
					DefinitionPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__PARAMETER_DEFINITIONS);
		}
		return parameterDefinitions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TGGAttributeConstraintAdornment> getSyncAdornments() {
		if (syncAdornments == null) {
			syncAdornments = new EObjectContainmentEList<TGGAttributeConstraintAdornment>(
					TGGAttributeConstraintAdornment.class, this,
					DefinitionPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__SYNC_ADORNMENTS);
		}
		return syncAdornments;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TGGAttributeConstraintAdornment> getGenAdornments() {
		if (genAdornments == null) {
			genAdornments = new EObjectContainmentEList<TGGAttributeConstraintAdornment>(
					TGGAttributeConstraintAdornment.class, this,
					DefinitionPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__GEN_ADORNMENTS);
		}
		return genAdornments;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case DefinitionPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__PARAMETER_DEFINITIONS:
			return ((InternalEList<?>) getParameterDefinitions()).basicRemove(otherEnd, msgs);
		case DefinitionPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__SYNC_ADORNMENTS:
			return ((InternalEList<?>) getSyncAdornments()).basicRemove(otherEnd, msgs);
		case DefinitionPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__GEN_ADORNMENTS:
			return ((InternalEList<?>) getGenAdornments()).basicRemove(otherEnd, msgs);
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
		case DefinitionPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__USER_DEFINED:
			return isUserDefined();
		case DefinitionPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__PARAMETER_DEFINITIONS:
			return getParameterDefinitions();
		case DefinitionPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__SYNC_ADORNMENTS:
			return getSyncAdornments();
		case DefinitionPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__GEN_ADORNMENTS:
			return getGenAdornments();
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
		case DefinitionPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__USER_DEFINED:
			setUserDefined((Boolean) newValue);
			return;
		case DefinitionPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__PARAMETER_DEFINITIONS:
			getParameterDefinitions().clear();
			getParameterDefinitions()
					.addAll((Collection<? extends TGGAttributeConstraintParameterDefinition>) newValue);
			return;
		case DefinitionPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__SYNC_ADORNMENTS:
			getSyncAdornments().clear();
			getSyncAdornments().addAll((Collection<? extends TGGAttributeConstraintAdornment>) newValue);
			return;
		case DefinitionPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__GEN_ADORNMENTS:
			getGenAdornments().clear();
			getGenAdornments().addAll((Collection<? extends TGGAttributeConstraintAdornment>) newValue);
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
		case DefinitionPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__USER_DEFINED:
			setUserDefined(USER_DEFINED_EDEFAULT);
			return;
		case DefinitionPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__PARAMETER_DEFINITIONS:
			getParameterDefinitions().clear();
			return;
		case DefinitionPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__SYNC_ADORNMENTS:
			getSyncAdornments().clear();
			return;
		case DefinitionPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__GEN_ADORNMENTS:
			getGenAdornments().clear();
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
		case DefinitionPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__USER_DEFINED:
			return userDefined != USER_DEFINED_EDEFAULT;
		case DefinitionPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__PARAMETER_DEFINITIONS:
			return parameterDefinitions != null && !parameterDefinitions.isEmpty();
		case DefinitionPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__SYNC_ADORNMENTS:
			return syncAdornments != null && !syncAdornments.isEmpty();
		case DefinitionPackage.TGG_ATTRIBUTE_CONSTRAINT_DEFINITION__GEN_ADORNMENTS:
			return genAdornments != null && !genAdornments.isEmpty();
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
		result.append(" (userDefined: ");
		result.append(userDefined);
		result.append(')');
		return result.toString();
	}

} //TGGAttributeConstraintDefinitionImpl
