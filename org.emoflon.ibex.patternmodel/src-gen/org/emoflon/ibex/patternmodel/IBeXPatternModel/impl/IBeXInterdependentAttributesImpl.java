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
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjointAttribute;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXInterdependentAttributes;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXInterdependentInjectivityConstraints;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IBe XInterdependent Attributes</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXInterdependentAttributesImpl#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXInterdependentAttributesImpl#getInterdependentPatterns <em>Interdependent Patterns</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBeXInterdependentAttributesImpl#getInjectivityConstraints <em>Injectivity Constraints</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IBeXInterdependentAttributesImpl extends MinimalEObjectImpl.Container
		implements IBeXInterdependentAttributes {
	/**
	 * The cached value of the '{@link #getAttributes() <em>Attributes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributes()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXDisjointAttribute> attributes;

	/**
	 * The cached value of the '{@link #getInterdependentPatterns() <em>Interdependent Patterns</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInterdependentPatterns()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXContextPattern> interdependentPatterns;

	/**
	 * The cached value of the '{@link #getInjectivityConstraints() <em>Injectivity Constraints</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInjectivityConstraints()
	 * @generated
	 * @ordered
	 */
	protected IBeXInterdependentInjectivityConstraints injectivityConstraints;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IBeXInterdependentAttributesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXPatternModelPackage.Literals.IBE_XINTERDEPENDENT_ATTRIBUTES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXDisjointAttribute> getAttributes() {
		if (attributes == null) {
			attributes = new EObjectContainmentEList<IBeXDisjointAttribute>(IBeXDisjointAttribute.class, this,
					IBeXPatternModelPackage.IBE_XINTERDEPENDENT_ATTRIBUTES__ATTRIBUTES);
		}
		return attributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXContextPattern> getInterdependentPatterns() {
		if (interdependentPatterns == null) {
			interdependentPatterns = new EObjectResolvingEList<IBeXContextPattern>(IBeXContextPattern.class, this,
					IBeXPatternModelPackage.IBE_XINTERDEPENDENT_ATTRIBUTES__INTERDEPENDENT_PATTERNS);
		}
		return interdependentPatterns;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXInterdependentInjectivityConstraints getInjectivityConstraints() {
		if (injectivityConstraints != null && injectivityConstraints.eIsProxy()) {
			InternalEObject oldInjectivityConstraints = (InternalEObject) injectivityConstraints;
			injectivityConstraints = (IBeXInterdependentInjectivityConstraints) eResolveProxy(
					oldInjectivityConstraints);
			if (injectivityConstraints != oldInjectivityConstraints) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							IBeXPatternModelPackage.IBE_XINTERDEPENDENT_ATTRIBUTES__INJECTIVITY_CONSTRAINTS,
							oldInjectivityConstraints, injectivityConstraints));
			}
		}
		return injectivityConstraints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXInterdependentInjectivityConstraints basicGetInjectivityConstraints() {
		return injectivityConstraints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInjectivityConstraints(
			IBeXInterdependentInjectivityConstraints newInjectivityConstraints, NotificationChain msgs) {
		IBeXInterdependentInjectivityConstraints oldInjectivityConstraints = injectivityConstraints;
		injectivityConstraints = newInjectivityConstraints;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXPatternModelPackage.IBE_XINTERDEPENDENT_ATTRIBUTES__INJECTIVITY_CONSTRAINTS,
					oldInjectivityConstraints, newInjectivityConstraints);
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
	public void setInjectivityConstraints(IBeXInterdependentInjectivityConstraints newInjectivityConstraints) {
		if (newInjectivityConstraints != injectivityConstraints) {
			NotificationChain msgs = null;
			if (injectivityConstraints != null)
				msgs = ((InternalEObject) injectivityConstraints).eInverseRemove(this,
						IBeXPatternModelPackage.IBE_XINTERDEPENDENT_INJECTIVITY_CONSTRAINTS__ATTRIBUTE_CONSTRAINTS,
						IBeXInterdependentInjectivityConstraints.class, msgs);
			if (newInjectivityConstraints != null)
				msgs = ((InternalEObject) newInjectivityConstraints).eInverseAdd(this,
						IBeXPatternModelPackage.IBE_XINTERDEPENDENT_INJECTIVITY_CONSTRAINTS__ATTRIBUTE_CONSTRAINTS,
						IBeXInterdependentInjectivityConstraints.class, msgs);
			msgs = basicSetInjectivityConstraints(newInjectivityConstraints, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXPatternModelPackage.IBE_XINTERDEPENDENT_ATTRIBUTES__INJECTIVITY_CONSTRAINTS,
					newInjectivityConstraints, newInjectivityConstraints));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IBeXPatternModelPackage.IBE_XINTERDEPENDENT_ATTRIBUTES__INJECTIVITY_CONSTRAINTS:
			if (injectivityConstraints != null)
				msgs = ((InternalEObject) injectivityConstraints).eInverseRemove(this,
						IBeXPatternModelPackage.IBE_XINTERDEPENDENT_INJECTIVITY_CONSTRAINTS__ATTRIBUTE_CONSTRAINTS,
						IBeXInterdependentInjectivityConstraints.class, msgs);
			return basicSetInjectivityConstraints((IBeXInterdependentInjectivityConstraints) otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IBeXPatternModelPackage.IBE_XINTERDEPENDENT_ATTRIBUTES__ATTRIBUTES:
			return ((InternalEList<?>) getAttributes()).basicRemove(otherEnd, msgs);
		case IBeXPatternModelPackage.IBE_XINTERDEPENDENT_ATTRIBUTES__INJECTIVITY_CONSTRAINTS:
			return basicSetInjectivityConstraints(null, msgs);
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
		case IBeXPatternModelPackage.IBE_XINTERDEPENDENT_ATTRIBUTES__ATTRIBUTES:
			return getAttributes();
		case IBeXPatternModelPackage.IBE_XINTERDEPENDENT_ATTRIBUTES__INTERDEPENDENT_PATTERNS:
			return getInterdependentPatterns();
		case IBeXPatternModelPackage.IBE_XINTERDEPENDENT_ATTRIBUTES__INJECTIVITY_CONSTRAINTS:
			if (resolve)
				return getInjectivityConstraints();
			return basicGetInjectivityConstraints();
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
		case IBeXPatternModelPackage.IBE_XINTERDEPENDENT_ATTRIBUTES__ATTRIBUTES:
			getAttributes().clear();
			getAttributes().addAll((Collection<? extends IBeXDisjointAttribute>) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XINTERDEPENDENT_ATTRIBUTES__INTERDEPENDENT_PATTERNS:
			getInterdependentPatterns().clear();
			getInterdependentPatterns().addAll((Collection<? extends IBeXContextPattern>) newValue);
			return;
		case IBeXPatternModelPackage.IBE_XINTERDEPENDENT_ATTRIBUTES__INJECTIVITY_CONSTRAINTS:
			setInjectivityConstraints((IBeXInterdependentInjectivityConstraints) newValue);
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
		case IBeXPatternModelPackage.IBE_XINTERDEPENDENT_ATTRIBUTES__ATTRIBUTES:
			getAttributes().clear();
			return;
		case IBeXPatternModelPackage.IBE_XINTERDEPENDENT_ATTRIBUTES__INTERDEPENDENT_PATTERNS:
			getInterdependentPatterns().clear();
			return;
		case IBeXPatternModelPackage.IBE_XINTERDEPENDENT_ATTRIBUTES__INJECTIVITY_CONSTRAINTS:
			setInjectivityConstraints((IBeXInterdependentInjectivityConstraints) null);
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
		case IBeXPatternModelPackage.IBE_XINTERDEPENDENT_ATTRIBUTES__ATTRIBUTES:
			return attributes != null && !attributes.isEmpty();
		case IBeXPatternModelPackage.IBE_XINTERDEPENDENT_ATTRIBUTES__INTERDEPENDENT_PATTERNS:
			return interdependentPatterns != null && !interdependentPatterns.isEmpty();
		case IBeXPatternModelPackage.IBE_XINTERDEPENDENT_ATTRIBUTES__INJECTIVITY_CONSTRAINTS:
			return injectivityConstraints != null;
		}
		return super.eIsSet(featureID);
	}

} //IBeXInterdependentAttributesImpl
