/**
 */
package IBeXLanguage.impl;

import IBeXLanguage.IBeXLanguagePackage;
import IBeXLanguage.IBeXNode;
import IBeXLanguage.IBeXNodePair;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IBe XNode Pair</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link IBeXLanguage.impl.IBeXNodePairImpl#getValues <em>Values</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IBeXNodePairImpl extends EObjectImpl implements IBeXNodePair {
	/**
	 * The cached value of the '{@link #getValues() <em>Values</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValues()
	 * @generated
	 * @ordered
	 */
	protected EList<IBeXNode> values;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IBeXNodePairImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXLanguagePackage.Literals.IBE_XNODE_PAIR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBeXNode> getValues() {
		if (values == null) {
			values = new EObjectResolvingEList<IBeXNode>(IBeXNode.class, this,
					IBeXLanguagePackage.IBE_XNODE_PAIR__VALUES);
		}
		return values;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case IBeXLanguagePackage.IBE_XNODE_PAIR__VALUES:
			return getValues();
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
		case IBeXLanguagePackage.IBE_XNODE_PAIR__VALUES:
			getValues().clear();
			getValues().addAll((Collection<? extends IBeXNode>) newValue);
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
		case IBeXLanguagePackage.IBE_XNODE_PAIR__VALUES:
			getValues().clear();
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
		case IBeXLanguagePackage.IBE_XNODE_PAIR__VALUES:
			return values != null && !values.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //IBeXNodePairImpl
