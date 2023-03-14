/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage
 * @generated
 */
public interface IBeXTGGModelFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	IBeXTGGModelFactory eINSTANCE = org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.IBeXTGGModelFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>TGG Model</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Model</em>'.
	 * @generated
	 */
	TGGModel createTGGModel();

	/**
	 * Returns a new object of class '<em>TGG Rule Set</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Rule Set</em>'.
	 * @generated
	 */
	TGGRuleSet createTGGRuleSet();

	/**
	 * Returns a new object of class '<em>TGG Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Rule</em>'.
	 * @generated
	 */
	TGGRule createTGGRule();

	/**
	 * Returns a new object of class '<em>TGG Operational Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Operational Rule</em>'.
	 * @generated
	 */
	TGGOperationalRule createTGGOperationalRule();

	/**
	 * Returns a new object of class '<em>TGG Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Node</em>'.
	 * @generated
	 */
	TGGNode createTGGNode();

	/**
	 * Returns a new object of class '<em>TGG Correspondence</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Correspondence</em>'.
	 * @generated
	 */
	TGGCorrespondence createTGGCorrespondence();

	/**
	 * Returns a new object of class '<em>TGG Edge</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Edge</em>'.
	 * @generated
	 */
	TGGEdge createTGGEdge();

	/**
	 * Returns a new object of class '<em>TGG Shortcut Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Shortcut Rule</em>'.
	 * @generated
	 */
	TGGShortcutRule createTGGShortcutRule();

	/**
	 * Returns a new object of class '<em>TGG Shortcut Rule Element Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>TGG Shortcut Rule Element Mapping</em>'.
	 * @generated
	 */
	TGGShortcutRuleElementMapping createTGGShortcutRuleElementMapping();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	IBeXTGGModelPackage getIBeXTGGModelPackage();

} //IBeXTGGModelFactory
