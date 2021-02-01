/**
 */
package org.emoflon.ibex.patternmodel.IBeXPatternModel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBexDisjunctInjectivityConstraint;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IBex Disjunct Injectivity Constraint</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBexDisjunctInjectivityConstraintImpl#getPattern1 <em>Pattern1</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBexDisjunctInjectivityConstraintImpl#getPattern2 <em>Pattern2</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBexDisjunctInjectivityConstraintImpl#getNode1 <em>Node1</em>}</li>
 *   <li>{@link org.emoflon.ibex.patternmodel.IBeXPatternModel.impl.IBexDisjunctInjectivityConstraintImpl#getNode2 <em>Node2</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IBexDisjunctInjectivityConstraintImpl extends MinimalEObjectImpl.Container
		implements IBexDisjunctInjectivityConstraint {
	/**
	 * The cached value of the '{@link #getPattern1() <em>Pattern1</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPattern1()
	 * @generated
	 * @ordered
	 */
	protected IBeXContextPattern pattern1;

	/**
	 * The cached value of the '{@link #getPattern2() <em>Pattern2</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPattern2()
	 * @generated
	 * @ordered
	 */
	protected IBeXContextPattern pattern2;

	/**
	 * The cached value of the '{@link #getNode1() <em>Node1</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNode1()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXNode> node1;

	/**
	 * The cached value of the '{@link #getNode2() <em>Node2</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNode2()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXNode> node2;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IBexDisjunctInjectivityConstraintImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXPatternModelPackage.Literals.IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXContextPattern getPattern1() {
		if (pattern1 != null && pattern1.eIsProxy()) {
			InternalEObject oldPattern1 = (InternalEObject) pattern1;
			pattern1 = (IBeXContextPattern) eResolveProxy(oldPattern1);
			if (pattern1 != oldPattern1) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							IBeXPatternModelPackage.IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT__PATTERN1, oldPattern1,
							pattern1));
			}
		}
		return pattern1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXContextPattern basicGetPattern1() {
		return pattern1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPattern1(IBeXContextPattern newPattern1) {
		IBeXContextPattern oldPattern1 = pattern1;
		pattern1 = newPattern1;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXPatternModelPackage.IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT__PATTERN1, oldPattern1, pattern1));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBeXContextPattern getPattern2() {
		if (pattern2 != null && pattern2.eIsProxy()) {
			InternalEObject oldPattern2 = (InternalEObject) pattern2;
			pattern2 = (IBeXContextPattern) eResolveProxy(oldPattern2);
			if (pattern2 != oldPattern2) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							IBeXPatternModelPackage.IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT__PATTERN2, oldPattern2,
							pattern2));
			}
		}
		return pattern2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXContextPattern basicGetPattern2() {
		return pattern2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPattern2(IBeXContextPattern newPattern2) {
		IBeXContextPattern oldPattern2 = pattern2;
		pattern2 = newPattern2;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXPatternModelPackage.IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT__PATTERN2, oldPattern2, pattern2));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXNode> getNode1() {
		if (node1 == null) {
			node1 = new EObjectResolvingEList<IBeXNode>(IBeXNode.class, this,
					IBeXPatternModelPackage.IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT__NODE1);
		}
		return node1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXNode> getNode2() {
		if (node2 == null) {
			node2 = new EObjectResolvingEList<IBeXNode>(IBeXNode.class, this,
					IBeXPatternModelPackage.IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT__NODE2);
		}
		return node2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case IBeXPatternModelPackage.IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT__PATTERN1:
			if (resolve)
				return getPattern1();
			return basicGetPattern1();
		case IBeXPatternModelPackage.IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT__PATTERN2:
			if (resolve)
				return getPattern2();
			return basicGetPattern2();
		case IBeXPatternModelPackage.IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT__NODE1:
			return getNode1();
		case IBeXPatternModelPackage.IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT__NODE2:
			return getNode2();
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
		case IBeXPatternModelPackage.IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT__PATTERN1:
			setPattern1((IBeXContextPattern) newValue);
			return;
		case IBeXPatternModelPackage.IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT__PATTERN2:
			setPattern2((IBeXContextPattern) newValue);
			return;
		case IBeXPatternModelPackage.IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT__NODE1:
			getNode1().clear();
			getNode1().addAll((Collection<? extends IBeXNode>) newValue);
			return;
		case IBeXPatternModelPackage.IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT__NODE2:
			getNode2().clear();
			getNode2().addAll((Collection<? extends IBeXNode>) newValue);
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
		case IBeXPatternModelPackage.IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT__PATTERN1:
			setPattern1((IBeXContextPattern) null);
			return;
		case IBeXPatternModelPackage.IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT__PATTERN2:
			setPattern2((IBeXContextPattern) null);
			return;
		case IBeXPatternModelPackage.IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT__NODE1:
			getNode1().clear();
			return;
		case IBeXPatternModelPackage.IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT__NODE2:
			getNode2().clear();
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
		case IBeXPatternModelPackage.IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT__PATTERN1:
			return pattern1 != null;
		case IBeXPatternModelPackage.IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT__PATTERN2:
			return pattern2 != null;
		case IBeXPatternModelPackage.IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT__NODE1:
			return node1 != null && !node1.isEmpty();
		case IBeXPatternModelPackage.IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT__NODE2:
			return node2 != null && !node2.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //IBexDisjunctInjectivityConstraintImpl
