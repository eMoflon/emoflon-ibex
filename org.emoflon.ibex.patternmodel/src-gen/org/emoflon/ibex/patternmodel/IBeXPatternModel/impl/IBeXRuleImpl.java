/**
 */
package org.emoflon.ibex.patternmodel.IBeXPatternModel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticConstraint;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCreatePattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDeletePattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXForEachExpression;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXParameter;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXProbability;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IBe XRule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXRuleImpl#getDocumentation <em>Documentation</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXRuleImpl#getLhs <em>Lhs</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXRuleImpl#getRhs <em>Rhs</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXRuleImpl#getCreate <em>Create</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXRuleImpl#getDelete <em>Delete</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXRuleImpl#getForEach <em>For Each</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXRuleImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXRuleImpl#getArithmeticConstraints <em>Arithmetic Constraints</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXRuleImpl#getProbability <em>Probability</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IBeXRuleImpl extends IBeXNamedElementImpl implements IBeXRule {
	/**
	 * The default value of the '{@link #getDocumentation() <em>Documentation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDocumentation()
	 * @generated
	 * @ordered
	 */
	protected static final String DOCUMENTATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDocumentation() <em>Documentation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDocumentation()
	 * @generated
	 * @ordered
	 */
	protected String documentation = DOCUMENTATION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getLhs() <em>Lhs</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLhs()
	 * @generated
	 * @ordered
	 */
	protected IBeXContext lhs;

	/**
	 * The cached value of the '{@link #getRhs() <em>Rhs</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRhs()
	 * @generated
	 * @ordered
	 */
	protected IBeXContextPattern rhs;

	/**
	 * The cached value of the '{@link #getCreate() <em>Create</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreate()
	 * @generated
	 * @ordered
	 */
	protected IBeXCreatePattern create;

	/**
	 * The cached value of the '{@link #getDelete() <em>Delete</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDelete()
	 * @generated
	 * @ordered
	 */
	protected IBeXDeletePattern delete;

	/**
	 * The cached value of the '{@link #getForEach() <em>For Each</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getForEach()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXForEachExpression> forEach;

	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXParameter> parameters;

	/**
	 * The cached value of the '{@link #getArithmeticConstraints() <em>Arithmetic Constraints</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArithmeticConstraints()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXArithmeticConstraint> arithmeticConstraints;

	/**
	 * The cached value of the '{@link #getProbability() <em>Probability</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProbability()
	 * @generated
	 * @ordered
	 */
	protected IBeXProbability probability;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IBeXRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXPatternModelPackage.Literals.IBE_XRULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getDocumentation() {
		return documentation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDocumentation(String newDocumentation) {
		String oldDocumentation = documentation;
		documentation = newDocumentation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXPatternModelPackage.IBE_XRULE__DOCUMENTATION,
					oldDocumentation, documentation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXContext getLhs() {
		if (lhs != null && lhs.eIsProxy()) {
			InternalEObject oldLhs = (InternalEObject) lhs;
			lhs = (IBeXContext) eResolveProxy(oldLhs);
			if (lhs != oldLhs) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, IBeXPatternModelPackage.IBE_XRULE__LHS,
							oldLhs, lhs));
			}
		}
		return lhs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXContext basicGetLhs() {
		return lhs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLhs(IBeXContext newLhs) {
		IBeXContext oldLhs = lhs;
		lhs = newLhs;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXPatternModelPackage.IBE_XRULE__LHS, oldLhs, lhs));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXContextPattern getRhs() {
		return rhs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRhs(IBeXContextPattern newRhs, NotificationChain msgs) {
		IBeXContextPattern oldRhs = rhs;
		rhs = newRhs;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXPatternModelPackage.IBE_XRULE__RHS, oldRhs, newRhs);
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
	public void setRhs(IBeXContextPattern newRhs) {
		if (newRhs != rhs) {
			NotificationChain msgs = null;
			if (rhs != null)
				msgs = ((InternalEObject) rhs).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXPatternModelPackage.IBE_XRULE__RHS, null, msgs);
			if (newRhs != null)
				msgs = ((InternalEObject) newRhs).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXPatternModelPackage.IBE_XRULE__RHS, null, msgs);
			msgs = basicSetRhs(newRhs, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXPatternModelPackage.IBE_XRULE__RHS, newRhs,
					newRhs));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXCreatePattern getCreate() {
		return create;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCreate(IBeXCreatePattern newCreate, NotificationChain msgs) {
		IBeXCreatePattern oldCreate = create;
		create = newCreate;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXPatternModelPackage.IBE_XRULE__CREATE, oldCreate, newCreate);
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
	public void setCreate(IBeXCreatePattern newCreate) {
		if (newCreate != create) {
			NotificationChain msgs = null;
			if (create != null)
				msgs = ((InternalEObject) create).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXPatternModelPackage.IBE_XRULE__CREATE, null, msgs);
			if (newCreate != null)
				msgs = ((InternalEObject) newCreate).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXPatternModelPackage.IBE_XRULE__CREATE, null, msgs);
			msgs = basicSetCreate(newCreate, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXPatternModelPackage.IBE_XRULE__CREATE, newCreate,
					newCreate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXDeletePattern getDelete() {
		return delete;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDelete(IBeXDeletePattern newDelete, NotificationChain msgs) {
		IBeXDeletePattern oldDelete = delete;
		delete = newDelete;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXPatternModelPackage.IBE_XRULE__DELETE, oldDelete, newDelete);
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
	public void setDelete(IBeXDeletePattern newDelete) {
		if (newDelete != delete) {
			NotificationChain msgs = null;
			if (delete != null)
				msgs = ((InternalEObject) delete).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXPatternModelPackage.IBE_XRULE__DELETE, null, msgs);
			if (newDelete != null)
				msgs = ((InternalEObject) newDelete).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXPatternModelPackage.IBE_XRULE__DELETE, null, msgs);
			msgs = basicSetDelete(newDelete, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXPatternModelPackage.IBE_XRULE__DELETE, newDelete,
					newDelete));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXForEachExpression> getForEach() {
		if (forEach == null) {
			forEach = new EObjectContainmentEList<IBeXForEachExpression>(IBeXForEachExpression.class, this,
					IBeXPatternModelPackage.IBE_XRULE__FOR_EACH);
		}
		return forEach;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXParameter> getParameters() {
		if (parameters == null) {
			parameters = new EObjectResolvingEList<IBeXParameter>(IBeXParameter.class, this,
					IBeXPatternModelPackage.IBE_XRULE__PARAMETERS);
		}
		return parameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXArithmeticConstraint> getArithmeticConstraints() {
		if (arithmeticConstraints == null) {
			arithmeticConstraints = new EObjectResolvingEList<IBeXArithmeticConstraint>(IBeXArithmeticConstraint.class,
					this, IBeXPatternModelPackage.IBE_XRULE__ARITHMETIC_CONSTRAINTS);
		}
		return arithmeticConstraints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXProbability getProbability() {
		return probability;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProbability(IBeXProbability newProbability, NotificationChain msgs) {
		IBeXProbability oldProbability = probability;
		probability = newProbability;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXPatternModelPackage.IBE_XRULE__PROBABILITY, oldProbability, newProbability);
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
	public void setProbability(IBeXProbability newProbability) {
		if (newProbability != probability) {
			NotificationChain msgs = null;
			if (probability != null)
				msgs = ((InternalEObject) probability).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXPatternModelPackage.IBE_XRULE__PROBABILITY, null, msgs);
			if (newProbability != null)
				msgs = ((InternalEObject) newProbability).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXPatternModelPackage.IBE_XRULE__PROBABILITY, null, msgs);
			msgs = basicSetProbability(newProbability, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXPatternModelPackage.IBE_XRULE__PROBABILITY,
					newProbability, newProbability));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IBeXPatternModelPackage.IBE_XRULE__RHS:
			return basicSetRhs(null, msgs);
		case IBeXPatternModelPackage.IBE_XRULE__CREATE:
			return basicSetCreate(null, msgs);
		case IBeXPatternModelPackage.IBE_XRULE__DELETE:
			return basicSetDelete(null, msgs);
		case IBeXPatternModelPackage.IBE_XRULE__FOR_EACH:
			return ((InternalEList<?>) getForEach()).basicRemove(otherEnd, msgs);
		case IBeXPatternModelPackage.IBE_XRULE__PROBABILITY:
			return basicSetProbability(null, msgs);
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
		case IBeXPatternModelPackage.IBE_XRULE__DOCUMENTATION:
			return getDocumentation();
		case IBeXPatternModelPackage.IBE_XRULE__LHS:
			if (resolve)
				return getLhs();
			return basicGetLhs();
		case IBeXPatternModelPackage.IBE_XRULE__RHS:
			return getRhs();
		case IBeXPatternModelPackage.IBE_XRULE__CREATE:
			return getCreate();
		case IBeXPatternModelPackage.IBE_XRULE__DELETE:
			return getDelete();
		case IBeXPatternModelPackage.IBE_XRULE__FOR_EACH:
			return getForEach();
		case IBeXPatternModelPackage.IBE_XRULE__PARAMETERS:
			return getParameters();
		case IBeXPatternModelPackage.IBE_XRULE__ARITHMETIC_CONSTRAINTS:
			return getArithmeticConstraints();
		case IBeXPatternModelPackage.IBE_XRULE__PROBABILITY:
			return getProbability();
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
		case IBeXPatternModelPackage.IBE_XRULE__DOCUMENTATION:
			setDocumentation((String) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XRULE__LHS:
			setLhs((IBeXContext) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XRULE__RHS:
			setRhs((IBeXContextPattern) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XRULE__CREATE:
			setCreate((IBeXCreatePattern) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XRULE__DELETE:
			setDelete((IBeXDeletePattern) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XRULE__FOR_EACH:
			getForEach().clear();
			getForEach().addAll((Collection<? extends IBeXForEachExpression>) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XRULE__PARAMETERS:
			getParameters().clear();
			getParameters().addAll((Collection<? extends IBeXParameter>) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XRULE__ARITHMETIC_CONSTRAINTS:
			getArithmeticConstraints().clear();
			getArithmeticConstraints().addAll((Collection<? extends IBeXArithmeticConstraint>) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XRULE__PROBABILITY:
			setProbability((IBeXProbability) newValue);
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
		case IBeXPatternModelPackage.IBE_XRULE__DOCUMENTATION:
			setDocumentation(DOCUMENTATION_EDEFAULT);
			return;
		case IBeXPatternModelPackage.IBE_XRULE__LHS:
			setLhs((IBeXContext) null);
			return;
		case IBeXPatternModelPackage.IBE_XRULE__RHS:
			setRhs((IBeXContextPattern) null);
			return;
		case IBeXPatternModelPackage.IBE_XRULE__CREATE:
			setCreate((IBeXCreatePattern) null);
			return;
		case IBeXPatternModelPackage.IBE_XRULE__DELETE:
			setDelete((IBeXDeletePattern) null);
			return;
		case IBeXPatternModelPackage.IBE_XRULE__FOR_EACH:
			getForEach().clear();
			return;
		case IBeXPatternModelPackage.IBE_XRULE__PARAMETERS:
			getParameters().clear();
			return;
		case IBeXPatternModelPackage.IBE_XRULE__ARITHMETIC_CONSTRAINTS:
			getArithmeticConstraints().clear();
			return;
		case IBeXPatternModelPackage.IBE_XRULE__PROBABILITY:
			setProbability((IBeXProbability) null);
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
		case IBeXPatternModelPackage.IBE_XRULE__DOCUMENTATION:
			return DOCUMENTATION_EDEFAULT == null ? documentation != null
					: !DOCUMENTATION_EDEFAULT.equals(documentation);
		case IBeXPatternModelPackage.IBE_XRULE__LHS:
			return lhs != null;
		case IBeXPatternModelPackage.IBE_XRULE__RHS:
			return rhs != null;
		case IBeXPatternModelPackage.IBE_XRULE__CREATE:
			return create != null;
		case IBeXPatternModelPackage.IBE_XRULE__DELETE:
			return delete != null;
		case IBeXPatternModelPackage.IBE_XRULE__FOR_EACH:
			return forEach != null && !forEach.isEmpty();
		case IBeXPatternModelPackage.IBE_XRULE__PARAMETERS:
			return parameters != null && !parameters.isEmpty();
		case IBeXPatternModelPackage.IBE_XRULE__ARITHMETIC_CONSTRAINTS:
			return arithmeticConstraints != null && !arithmeticConstraints.isEmpty();
		case IBeXPatternModelPackage.IBE_XRULE__PROBABILITY:
			return probability != null;
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

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (documentation: ");
		result.append(documentation);
		result.append(')');
		return result.toString();
	}

} //IBeXRuleImpl
