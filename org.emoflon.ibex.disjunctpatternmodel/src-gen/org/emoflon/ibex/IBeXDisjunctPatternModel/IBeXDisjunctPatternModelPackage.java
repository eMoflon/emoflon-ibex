/**
 */
package org.emoflon.ibex.IBeXDisjunctPatternModel;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * Model for disjunct IBeXPatterns.
 * <!-- end-model-doc -->
 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctPatternModelFactory
 * @model kind="package"
 * @generated
 */
public interface IBeXDisjunctPatternModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "IBeXDisjunctPatternModel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "platform:/resource/org.emoflon.ibex.disjunctpatternmodel/model/IBeXDisjunctPatternModell.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "IBeXDisjunctPatternModel";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	IBeXDisjunctPatternModelPackage eINSTANCE = org.emoflon.ibex.IBeXDisjunctPatternModel.impl.IBeXDisjunctPatternModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.IBeXDisjunctPatternModel.impl.IBeXDisjunctContextPatternImpl <em>IBe XDisjunct Context Pattern</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.impl.IBeXDisjunctContextPatternImpl
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.impl.IBeXDisjunctPatternModelPackageImpl#getIBeXDisjunctContextPattern()
	 * @generated
	 */
	int IBE_XDISJUNCT_CONTEXT_PATTERN = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDISJUNCT_CONTEXT_PATTERN__NAME = IBeXPatternModelPackage.IBE_XCONTEXT__NAME;

	/**
	 * The feature id for the '<em><b>Subpatterns</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDISJUNCT_CONTEXT_PATTERN__SUBPATTERNS = IBeXPatternModelPackage.IBE_XCONTEXT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Injectivity Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDISJUNCT_CONTEXT_PATTERN__INJECTIVITY_CONSTRAINTS = IBeXPatternModelPackage.IBE_XCONTEXT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Attributes Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDISJUNCT_CONTEXT_PATTERN__ATTRIBUTES_CONSTRAINTS = IBeXPatternModelPackage.IBE_XCONTEXT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>IBe XDisjunct Context Pattern</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDISJUNCT_CONTEXT_PATTERN_FEATURE_COUNT = IBeXPatternModelPackage.IBE_XCONTEXT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>IBe XDisjunct Context Pattern</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDISJUNCT_CONTEXT_PATTERN_OPERATION_COUNT = IBeXPatternModelPackage.IBE_XCONTEXT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.IBeXDisjunctPatternModel.impl.IBeXDisjunctAttributesImpl <em>IBe XDisjunct Attributes</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.impl.IBeXDisjunctAttributesImpl
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.impl.IBeXDisjunctPatternModelPackageImpl#getIBeXDisjunctAttributes()
	 * @generated
	 */
	int IBE_XDISJUNCT_ATTRIBUTES = 1;

	/**
	 * The feature id for the '<em><b>Source Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDISJUNCT_ATTRIBUTES__SOURCE_PATTERN = 0;

	/**
	 * The feature id for the '<em><b>Target Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDISJUNCT_ATTRIBUTES__TARGET_PATTERN = 1;

	/**
	 * The feature id for the '<em><b>Disjunct Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDISJUNCT_ATTRIBUTES__DISJUNCT_ATTRIBUTES = 2;

	/**
	 * The number of structural features of the '<em>IBe XDisjunct Attributes</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDISJUNCT_ATTRIBUTES_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>IBe XDisjunct Attributes</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDISJUNCT_ATTRIBUTES_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.IBeXDisjunctPatternModel.impl.IBeXDependentInjectivityConstraintsImpl <em>IBe XDependent Injectivity Constraints</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.impl.IBeXDependentInjectivityConstraintsImpl
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.impl.IBeXDisjunctPatternModelPackageImpl#getIBeXDependentInjectivityConstraints()
	 * @generated
	 */
	int IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS = 2;

	/**
	 * The feature id for the '<em><b>Injectivity Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS__INJECTIVITY_CONSTRAINTS = 0;

	/**
	 * The feature id for the '<em><b>Patterns</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS__PATTERNS = 1;

	/**
	 * The number of structural features of the '<em>IBe XDependent Injectivity Constraints</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>IBe XDependent Injectivity Constraints</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.emoflon.ibex.IBeXDisjunctPatternModel.impl.IBexDisjunctInjectivityConstraintImpl <em>IBex Disjunct Injectivity Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.impl.IBexDisjunctInjectivityConstraintImpl
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.impl.IBeXDisjunctPatternModelPackageImpl#getIBexDisjunctInjectivityConstraint()
	 * @generated
	 */
	int IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT = 3;

	/**
	 * The feature id for the '<em><b>Pattern1</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT__PATTERN1 = 0;

	/**
	 * The feature id for the '<em><b>Pattern2</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT__PATTERN2 = 1;

	/**
	 * The feature id for the '<em><b>Node1</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT__NODE1 = 2;

	/**
	 * The feature id for the '<em><b>Node2</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT__NODE2 = 3;

	/**
	 * The number of structural features of the '<em>IBex Disjunct Injectivity Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>IBex Disjunct Injectivity Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctContextPattern <em>IBe XDisjunct Context Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XDisjunct Context Pattern</em>'.
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctContextPattern
	 * @generated
	 */
	EClass getIBeXDisjunctContextPattern();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctContextPattern#getSubpatterns <em>Subpatterns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Subpatterns</em>'.
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctContextPattern#getSubpatterns()
	 * @see #getIBeXDisjunctContextPattern()
	 * @generated
	 */
	EReference getIBeXDisjunctContextPattern_Subpatterns();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctContextPattern#getInjectivityConstraints <em>Injectivity Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Injectivity Constraints</em>'.
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctContextPattern#getInjectivityConstraints()
	 * @see #getIBeXDisjunctContextPattern()
	 * @generated
	 */
	EReference getIBeXDisjunctContextPattern_InjectivityConstraints();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctContextPattern#getAttributesConstraints <em>Attributes Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attributes Constraints</em>'.
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctContextPattern#getAttributesConstraints()
	 * @see #getIBeXDisjunctContextPattern()
	 * @generated
	 */
	EReference getIBeXDisjunctContextPattern_AttributesConstraints();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctAttributes <em>IBe XDisjunct Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XDisjunct Attributes</em>'.
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctAttributes
	 * @generated
	 */
	EClass getIBeXDisjunctAttributes();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctAttributes#getSourcePattern <em>Source Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source Pattern</em>'.
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctAttributes#getSourcePattern()
	 * @see #getIBeXDisjunctAttributes()
	 * @generated
	 */
	EAttribute getIBeXDisjunctAttributes_SourcePattern();

	/**
	 * Returns the meta object for the attribute '{@link org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctAttributes#getTargetPattern <em>Target Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Target Pattern</em>'.
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctAttributes#getTargetPattern()
	 * @see #getIBeXDisjunctAttributes()
	 * @generated
	 */
	EAttribute getIBeXDisjunctAttributes_TargetPattern();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctAttributes#getDisjunctAttributes <em>Disjunct Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Disjunct Attributes</em>'.
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDisjunctAttributes#getDisjunctAttributes()
	 * @see #getIBeXDisjunctAttributes()
	 * @generated
	 */
	EReference getIBeXDisjunctAttributes_DisjunctAttributes();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDependentInjectivityConstraints <em>IBe XDependent Injectivity Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBe XDependent Injectivity Constraints</em>'.
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDependentInjectivityConstraints
	 * @generated
	 */
	EClass getIBeXDependentInjectivityConstraints();

	/**
	 * Returns the meta object for the attribute list '{@link org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDependentInjectivityConstraints#getPatterns <em>Patterns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Patterns</em>'.
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDependentInjectivityConstraints#getPatterns()
	 * @see #getIBeXDependentInjectivityConstraints()
	 * @generated
	 */
	EAttribute getIBeXDependentInjectivityConstraints_Patterns();

	/**
	 * Returns the meta object for the containment reference list '{@link org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDependentInjectivityConstraints#getInjectivityConstraints <em>Injectivity Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Injectivity Constraints</em>'.
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBeXDependentInjectivityConstraints#getInjectivityConstraints()
	 * @see #getIBeXDependentInjectivityConstraints()
	 * @generated
	 */
	EReference getIBeXDependentInjectivityConstraints_InjectivityConstraints();

	/**
	 * Returns the meta object for class '{@link org.emoflon.ibex.IBeXDisjunctPatternModel.IBexDisjunctInjectivityConstraint <em>IBex Disjunct Injectivity Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBex Disjunct Injectivity Constraint</em>'.
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBexDisjunctInjectivityConstraint
	 * @generated
	 */
	EClass getIBexDisjunctInjectivityConstraint();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.IBeXDisjunctPatternModel.IBexDisjunctInjectivityConstraint#getPattern1 <em>Pattern1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Pattern1</em>'.
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBexDisjunctInjectivityConstraint#getPattern1()
	 * @see #getIBexDisjunctInjectivityConstraint()
	 * @generated
	 */
	EReference getIBexDisjunctInjectivityConstraint_Pattern1();

	/**
	 * Returns the meta object for the reference '{@link org.emoflon.ibex.IBeXDisjunctPatternModel.IBexDisjunctInjectivityConstraint#getPattern2 <em>Pattern2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Pattern2</em>'.
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBexDisjunctInjectivityConstraint#getPattern2()
	 * @see #getIBexDisjunctInjectivityConstraint()
	 * @generated
	 */
	EReference getIBexDisjunctInjectivityConstraint_Pattern2();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.IBeXDisjunctPatternModel.IBexDisjunctInjectivityConstraint#getNode1 <em>Node1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Node1</em>'.
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBexDisjunctInjectivityConstraint#getNode1()
	 * @see #getIBexDisjunctInjectivityConstraint()
	 * @generated
	 */
	EReference getIBexDisjunctInjectivityConstraint_Node1();

	/**
	 * Returns the meta object for the reference list '{@link org.emoflon.ibex.IBeXDisjunctPatternModel.IBexDisjunctInjectivityConstraint#getNode2 <em>Node2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Node2</em>'.
	 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.IBexDisjunctInjectivityConstraint#getNode2()
	 * @see #getIBexDisjunctInjectivityConstraint()
	 * @generated
	 */
	EReference getIBexDisjunctInjectivityConstraint_Node2();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	IBeXDisjunctPatternModelFactory getIBeXDisjunctPatternModelFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.IBeXDisjunctPatternModel.impl.IBeXDisjunctContextPatternImpl <em>IBe XDisjunct Context Pattern</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.impl.IBeXDisjunctContextPatternImpl
		 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.impl.IBeXDisjunctPatternModelPackageImpl#getIBeXDisjunctContextPattern()
		 * @generated
		 */
		EClass IBE_XDISJUNCT_CONTEXT_PATTERN = eINSTANCE.getIBeXDisjunctContextPattern();

		/**
		 * The meta object literal for the '<em><b>Subpatterns</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XDISJUNCT_CONTEXT_PATTERN__SUBPATTERNS = eINSTANCE.getIBeXDisjunctContextPattern_Subpatterns();

		/**
		 * The meta object literal for the '<em><b>Injectivity Constraints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XDISJUNCT_CONTEXT_PATTERN__INJECTIVITY_CONSTRAINTS = eINSTANCE.getIBeXDisjunctContextPattern_InjectivityConstraints();

		/**
		 * The meta object literal for the '<em><b>Attributes Constraints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XDISJUNCT_CONTEXT_PATTERN__ATTRIBUTES_CONSTRAINTS = eINSTANCE.getIBeXDisjunctContextPattern_AttributesConstraints();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.IBeXDisjunctPatternModel.impl.IBeXDisjunctAttributesImpl <em>IBe XDisjunct Attributes</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.impl.IBeXDisjunctAttributesImpl
		 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.impl.IBeXDisjunctPatternModelPackageImpl#getIBeXDisjunctAttributes()
		 * @generated
		 */
		EClass IBE_XDISJUNCT_ATTRIBUTES = eINSTANCE.getIBeXDisjunctAttributes();

		/**
		 * The meta object literal for the '<em><b>Source Pattern</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IBE_XDISJUNCT_ATTRIBUTES__SOURCE_PATTERN = eINSTANCE.getIBeXDisjunctAttributes_SourcePattern();

		/**
		 * The meta object literal for the '<em><b>Target Pattern</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IBE_XDISJUNCT_ATTRIBUTES__TARGET_PATTERN = eINSTANCE.getIBeXDisjunctAttributes_TargetPattern();

		/**
		 * The meta object literal for the '<em><b>Disjunct Attributes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XDISJUNCT_ATTRIBUTES__DISJUNCT_ATTRIBUTES = eINSTANCE.getIBeXDisjunctAttributes_DisjunctAttributes();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.IBeXDisjunctPatternModel.impl.IBeXDependentInjectivityConstraintsImpl <em>IBe XDependent Injectivity Constraints</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.impl.IBeXDependentInjectivityConstraintsImpl
		 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.impl.IBeXDisjunctPatternModelPackageImpl#getIBeXDependentInjectivityConstraints()
		 * @generated
		 */
		EClass IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS = eINSTANCE.getIBeXDependentInjectivityConstraints();

		/**
		 * The meta object literal for the '<em><b>Patterns</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS__PATTERNS = eINSTANCE.getIBeXDependentInjectivityConstraints_Patterns();

		/**
		 * The meta object literal for the '<em><b>Injectivity Constraints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBE_XDEPENDENT_INJECTIVITY_CONSTRAINTS__INJECTIVITY_CONSTRAINTS = eINSTANCE.getIBeXDependentInjectivityConstraints_InjectivityConstraints();

		/**
		 * The meta object literal for the '{@link org.emoflon.ibex.IBeXDisjunctPatternModel.impl.IBexDisjunctInjectivityConstraintImpl <em>IBex Disjunct Injectivity Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.impl.IBexDisjunctInjectivityConstraintImpl
		 * @see org.emoflon.ibex.IBeXDisjunctPatternModel.impl.IBeXDisjunctPatternModelPackageImpl#getIBexDisjunctInjectivityConstraint()
		 * @generated
		 */
		EClass IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT = eINSTANCE.getIBexDisjunctInjectivityConstraint();

		/**
		 * The meta object literal for the '<em><b>Pattern1</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT__PATTERN1 = eINSTANCE.getIBexDisjunctInjectivityConstraint_Pattern1();

		/**
		 * The meta object literal for the '<em><b>Pattern2</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT__PATTERN2 = eINSTANCE.getIBexDisjunctInjectivityConstraint_Pattern2();

		/**
		 * The meta object literal for the '<em><b>Node1</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT__NODE1 = eINSTANCE.getIBexDisjunctInjectivityConstraint_Node1();

		/**
		 * The meta object literal for the '<em><b>Node2</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IBEX_DISJUNCT_INJECTIVITY_CONSTRAINT__NODE2 = eINSTANCE.getIBexDisjunctInjectivityConstraint_Node2();

	}

} //IBeXDisjunctPatternModelPackage
