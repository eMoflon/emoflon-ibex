/**
 */
package org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.impl.ArithmeticValueImpl;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTForEachExpression;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTIteratorAttributeReference;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.IBeXGTModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>GT Iterator Attribute Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTIteratorAttributeReferenceImpl#getIterator <em>Iterator</em>}</li>
 *   <li>{@link org.emoflon.ibex.gt.gtmodel.IBeXGTModel.impl.GTIteratorAttributeReferenceImpl#getAttribute <em>Attribute</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GTIteratorAttributeReferenceImpl extends ArithmeticValueImpl implements GTIteratorAttributeReference {
	/**
	 * The cached value of the '{@link #getIterator() <em>Iterator</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIterator()
	 * @generated
	 * @ordered
	 */
	protected GTForEachExpression iterator;

	/**
	 * The cached value of the '{@link #getAttribute() <em>Attribute</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttribute()
	 * @generated
	 * @ordered
	 */
	protected EAttribute attribute;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GTIteratorAttributeReferenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXGTModelPackage.Literals.GT_ITERATOR_ATTRIBUTE_REFERENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GTForEachExpression getIterator() {
		if (iterator != null && iterator.eIsProxy()) {
			InternalEObject oldIterator = (InternalEObject) iterator;
			iterator = (GTForEachExpression) eResolveProxy(oldIterator);
			if (iterator != oldIterator) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							IBeXGTModelPackage.GT_ITERATOR_ATTRIBUTE_REFERENCE__ITERATOR, oldIterator, iterator));
			}
		}
		return iterator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GTForEachExpression basicGetIterator() {
		return iterator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIterator(GTForEachExpression newIterator) {
		GTForEachExpression oldIterator = iterator;
		iterator = newIterator;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXGTModelPackage.GT_ITERATOR_ATTRIBUTE_REFERENCE__ITERATOR, oldIterator, iterator));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAttribute() {
		if (attribute != null && attribute.eIsProxy()) {
			InternalEObject oldAttribute = (InternalEObject) attribute;
			attribute = (EAttribute) eResolveProxy(oldAttribute);
			if (attribute != oldAttribute) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							IBeXGTModelPackage.GT_ITERATOR_ATTRIBUTE_REFERENCE__ATTRIBUTE, oldAttribute, attribute));
			}
		}
		return attribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute basicGetAttribute() {
		return attribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAttribute(EAttribute newAttribute) {
		EAttribute oldAttribute = attribute;
		attribute = newAttribute;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXGTModelPackage.GT_ITERATOR_ATTRIBUTE_REFERENCE__ATTRIBUTE, oldAttribute, attribute));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case IBeXGTModelPackage.GT_ITERATOR_ATTRIBUTE_REFERENCE__ITERATOR:
			if (resolve)
				return getIterator();
			return basicGetIterator();
		case IBeXGTModelPackage.GT_ITERATOR_ATTRIBUTE_REFERENCE__ATTRIBUTE:
			if (resolve)
				return getAttribute();
			return basicGetAttribute();
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
		case IBeXGTModelPackage.GT_ITERATOR_ATTRIBUTE_REFERENCE__ITERATOR:
			setIterator((GTForEachExpression) newValue);
			return;
		case IBeXGTModelPackage.GT_ITERATOR_ATTRIBUTE_REFERENCE__ATTRIBUTE:
			setAttribute((EAttribute) newValue);
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
		case IBeXGTModelPackage.GT_ITERATOR_ATTRIBUTE_REFERENCE__ITERATOR:
			setIterator((GTForEachExpression) null);
			return;
		case IBeXGTModelPackage.GT_ITERATOR_ATTRIBUTE_REFERENCE__ATTRIBUTE:
			setAttribute((EAttribute) null);
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
		case IBeXGTModelPackage.GT_ITERATOR_ATTRIBUTE_REFERENCE__ITERATOR:
			return iterator != null;
		case IBeXGTModelPackage.GT_ITERATOR_ATTRIBUTE_REFERENCE__ATTRIBUTE:
			return attribute != null;
		}
		return super.eIsSet(featureID);
	}

} //GTIteratorAttributeReferenceImpl
