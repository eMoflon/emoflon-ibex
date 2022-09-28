/**
 */
package org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.EPackageDependency;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EPackage Dependency</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.EPackageDependencyImpl#getSimpleName <em>Simple Name</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.EPackageDependencyImpl#isHasAlias <em>Has Alias</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.EPackageDependencyImpl#getAlias <em>Alias</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.EPackageDependencyImpl#getFullyQualifiedName <em>Fully Qualified Name</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.EPackageDependencyImpl#getPackage <em>Package</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.EPackageDependencyImpl#getPackageURI <em>Package URI</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.EPackageDependencyImpl#isPackageHasProject <em>Package Has Project</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.EPackageDependencyImpl#getProjectLocation <em>Project Location</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.EPackageDependencyImpl#getFactoryClassName <em>Factory Class Name</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.EPackageDependencyImpl#getPackageClassName <em>Package Class Name</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.EPackageDependencyImpl#getEcoreURI <em>Ecore URI</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.EPackageDependencyImpl#isEcoreHasLocation <em>Ecore Has Location</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.EPackageDependencyImpl#getEcoreLocation <em>Ecore Location</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.EPackageDependencyImpl#getGenmodelURI <em>Genmodel URI</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.EPackageDependencyImpl#isGenmodelHasLocation <em>Genmodel Has Location</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.EPackageDependencyImpl#getGenmodelLocation <em>Genmodel Location</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.EPackageDependencyImpl#getClassifierName2FQN <em>Classifier Name2 FQN</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EPackageDependencyImpl extends MinimalEObjectImpl.Container implements EPackageDependency {
	/**
	 * The default value of the '{@link #getSimpleName() <em>Simple Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSimpleName()
	 * @generated
	 * @ordered
	 */
	protected static final String SIMPLE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSimpleName() <em>Simple Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSimpleName()
	 * @generated
	 * @ordered
	 */
	protected String simpleName = SIMPLE_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isHasAlias() <em>Has Alias</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHasAlias()
	 * @generated
	 * @ordered
	 */
	protected static final boolean HAS_ALIAS_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isHasAlias() <em>Has Alias</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isHasAlias()
	 * @generated
	 * @ordered
	 */
	protected boolean hasAlias = HAS_ALIAS_EDEFAULT;

	/**
	 * The default value of the '{@link #getAlias() <em>Alias</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlias()
	 * @generated
	 * @ordered
	 */
	protected static final String ALIAS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAlias() <em>Alias</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlias()
	 * @generated
	 * @ordered
	 */
	protected String alias = ALIAS_EDEFAULT;

	/**
	 * The default value of the '{@link #getFullyQualifiedName() <em>Fully Qualified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFullyQualifiedName()
	 * @generated
	 * @ordered
	 */
	protected static final String FULLY_QUALIFIED_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFullyQualifiedName() <em>Fully Qualified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFullyQualifiedName()
	 * @generated
	 * @ordered
	 */
	protected String fullyQualifiedName = FULLY_QUALIFIED_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPackage() <em>Package</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackage()
	 * @generated
	 * @ordered
	 */
	protected EPackage package_;

	/**
	 * The default value of the '{@link #getPackageURI() <em>Package URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackageURI()
	 * @generated
	 * @ordered
	 */
	protected static final String PACKAGE_URI_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPackageURI() <em>Package URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackageURI()
	 * @generated
	 * @ordered
	 */
	protected String packageURI = PACKAGE_URI_EDEFAULT;

	/**
	 * The default value of the '{@link #isPackageHasProject() <em>Package Has Project</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPackageHasProject()
	 * @generated
	 * @ordered
	 */
	protected static final boolean PACKAGE_HAS_PROJECT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isPackageHasProject() <em>Package Has Project</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPackageHasProject()
	 * @generated
	 * @ordered
	 */
	protected boolean packageHasProject = PACKAGE_HAS_PROJECT_EDEFAULT;

	/**
	 * The default value of the '{@link #getProjectLocation() <em>Project Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjectLocation()
	 * @generated
	 * @ordered
	 */
	protected static final String PROJECT_LOCATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProjectLocation() <em>Project Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjectLocation()
	 * @generated
	 * @ordered
	 */
	protected String projectLocation = PROJECT_LOCATION_EDEFAULT;

	/**
	 * The default value of the '{@link #getFactoryClassName() <em>Factory Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFactoryClassName()
	 * @generated
	 * @ordered
	 */
	protected static final String FACTORY_CLASS_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFactoryClassName() <em>Factory Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFactoryClassName()
	 * @generated
	 * @ordered
	 */
	protected String factoryClassName = FACTORY_CLASS_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getPackageClassName() <em>Package Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackageClassName()
	 * @generated
	 * @ordered
	 */
	protected static final String PACKAGE_CLASS_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPackageClassName() <em>Package Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackageClassName()
	 * @generated
	 * @ordered
	 */
	protected String packageClassName = PACKAGE_CLASS_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getEcoreURI() <em>Ecore URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEcoreURI()
	 * @generated
	 * @ordered
	 */
	protected static final String ECORE_URI_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEcoreURI() <em>Ecore URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEcoreURI()
	 * @generated
	 * @ordered
	 */
	protected String ecoreURI = ECORE_URI_EDEFAULT;

	/**
	 * The default value of the '{@link #isEcoreHasLocation() <em>Ecore Has Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEcoreHasLocation()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ECORE_HAS_LOCATION_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isEcoreHasLocation() <em>Ecore Has Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEcoreHasLocation()
	 * @generated
	 * @ordered
	 */
	protected boolean ecoreHasLocation = ECORE_HAS_LOCATION_EDEFAULT;

	/**
	 * The default value of the '{@link #getEcoreLocation() <em>Ecore Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEcoreLocation()
	 * @generated
	 * @ordered
	 */
	protected static final String ECORE_LOCATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEcoreLocation() <em>Ecore Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEcoreLocation()
	 * @generated
	 * @ordered
	 */
	protected String ecoreLocation = ECORE_LOCATION_EDEFAULT;

	/**
	 * The default value of the '{@link #getGenmodelURI() <em>Genmodel URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGenmodelURI()
	 * @generated
	 * @ordered
	 */
	protected static final String GENMODEL_URI_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getGenmodelURI() <em>Genmodel URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGenmodelURI()
	 * @generated
	 * @ordered
	 */
	protected String genmodelURI = GENMODEL_URI_EDEFAULT;

	/**
	 * The default value of the '{@link #isGenmodelHasLocation() <em>Genmodel Has Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenmodelHasLocation()
	 * @generated
	 * @ordered
	 */
	protected static final boolean GENMODEL_HAS_LOCATION_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isGenmodelHasLocation() <em>Genmodel Has Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenmodelHasLocation()
	 * @generated
	 * @ordered
	 */
	protected boolean genmodelHasLocation = GENMODEL_HAS_LOCATION_EDEFAULT;

	/**
	 * The default value of the '{@link #getGenmodelLocation() <em>Genmodel Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGenmodelLocation()
	 * @generated
	 * @ordered
	 */
	protected static final String GENMODEL_LOCATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getGenmodelLocation() <em>Genmodel Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGenmodelLocation()
	 * @generated
	 * @ordered
	 */
	protected String genmodelLocation = GENMODEL_LOCATION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getClassifierName2FQN() <em>Classifier Name2 FQN</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClassifierName2FQN()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> classifierName2FQN;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EPackageDependencyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXCoreModelPackage.Literals.EPACKAGE_DEPENDENCY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSimpleName() {
		return simpleName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSimpleName(String newSimpleName) {
		String oldSimpleName = simpleName;
		simpleName = newSimpleName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__SIMPLE_NAME,
					oldSimpleName, simpleName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isHasAlias() {
		return hasAlias;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHasAlias(boolean newHasAlias) {
		boolean oldHasAlias = hasAlias;
		hasAlias = newHasAlias;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__HAS_ALIAS,
					oldHasAlias, hasAlias));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAlias(String newAlias) {
		String oldAlias = alias;
		alias = newAlias;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__ALIAS,
					oldAlias, alias));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFullyQualifiedName() {
		return fullyQualifiedName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFullyQualifiedName(String newFullyQualifiedName) {
		String oldFullyQualifiedName = fullyQualifiedName;
		fullyQualifiedName = newFullyQualifiedName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__FULLY_QUALIFIED_NAME, oldFullyQualifiedName,
					fullyQualifiedName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EPackage getPackage() {
		if (package_ != null && package_.eIsProxy()) {
			InternalEObject oldPackage = (InternalEObject) package_;
			package_ = (EPackage) eResolveProxy(oldPackage);
			if (package_ != oldPackage) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__PACKAGE, oldPackage, package_));
			}
		}
		return package_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EPackage basicGetPackage() {
		return package_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPackage(EPackage newPackage) {
		EPackage oldPackage = package_;
		package_ = newPackage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__PACKAGE,
					oldPackage, package_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPackageURI() {
		return packageURI;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPackageURI(String newPackageURI) {
		String oldPackageURI = packageURI;
		packageURI = newPackageURI;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__PACKAGE_URI,
					oldPackageURI, packageURI));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isPackageHasProject() {
		return packageHasProject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPackageHasProject(boolean newPackageHasProject) {
		boolean oldPackageHasProject = packageHasProject;
		packageHasProject = newPackageHasProject;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__PACKAGE_HAS_PROJECT, oldPackageHasProject,
					packageHasProject));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getProjectLocation() {
		return projectLocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProjectLocation(String newProjectLocation) {
		String oldProjectLocation = projectLocation;
		projectLocation = newProjectLocation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__PROJECT_LOCATION, oldProjectLocation, projectLocation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFactoryClassName() {
		return factoryClassName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFactoryClassName(String newFactoryClassName) {
		String oldFactoryClassName = factoryClassName;
		factoryClassName = newFactoryClassName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__FACTORY_CLASS_NAME, oldFactoryClassName,
					factoryClassName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPackageClassName() {
		return packageClassName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPackageClassName(String newPackageClassName) {
		String oldPackageClassName = packageClassName;
		packageClassName = newPackageClassName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__PACKAGE_CLASS_NAME, oldPackageClassName,
					packageClassName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEcoreURI() {
		return ecoreURI;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEcoreURI(String newEcoreURI) {
		String oldEcoreURI = ecoreURI;
		ecoreURI = newEcoreURI;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__ECORE_URI,
					oldEcoreURI, ecoreURI));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isEcoreHasLocation() {
		return ecoreHasLocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEcoreHasLocation(boolean newEcoreHasLocation) {
		boolean oldEcoreHasLocation = ecoreHasLocation;
		ecoreHasLocation = newEcoreHasLocation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__ECORE_HAS_LOCATION, oldEcoreHasLocation,
					ecoreHasLocation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEcoreLocation() {
		return ecoreLocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEcoreLocation(String newEcoreLocation) {
		String oldEcoreLocation = ecoreLocation;
		ecoreLocation = newEcoreLocation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__ECORE_LOCATION, oldEcoreLocation, ecoreLocation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getGenmodelURI() {
		return genmodelURI;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGenmodelURI(String newGenmodelURI) {
		String oldGenmodelURI = genmodelURI;
		genmodelURI = newGenmodelURI;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__GENMODEL_URI, oldGenmodelURI, genmodelURI));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isGenmodelHasLocation() {
		return genmodelHasLocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGenmodelHasLocation(boolean newGenmodelHasLocation) {
		boolean oldGenmodelHasLocation = genmodelHasLocation;
		genmodelHasLocation = newGenmodelHasLocation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__GENMODEL_HAS_LOCATION, oldGenmodelHasLocation,
					genmodelHasLocation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getGenmodelLocation() {
		return genmodelLocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGenmodelLocation(String newGenmodelLocation) {
		String oldGenmodelLocation = genmodelLocation;
		genmodelLocation = newGenmodelLocation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__GENMODEL_LOCATION, oldGenmodelLocation,
					genmodelLocation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, String> getClassifierName2FQN() {
		if (classifierName2FQN == null) {
			classifierName2FQN = new EcoreEMap<String, String>(IBeXCoreModelPackage.Literals.CLASSIFIER_NAME_TO_FQN,
					ClassifierNameToFQNImpl.class, this,
					IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__CLASSIFIER_NAME2_FQN);
		}
		return classifierName2FQN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__CLASSIFIER_NAME2_FQN:
			return ((InternalEList<?>) getClassifierName2FQN()).basicRemove(otherEnd, msgs);
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
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__SIMPLE_NAME:
			return getSimpleName();
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__HAS_ALIAS:
			return isHasAlias();
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__ALIAS:
			return getAlias();
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__FULLY_QUALIFIED_NAME:
			return getFullyQualifiedName();
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__PACKAGE:
			if (resolve)
				return getPackage();
			return basicGetPackage();
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__PACKAGE_URI:
			return getPackageURI();
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__PACKAGE_HAS_PROJECT:
			return isPackageHasProject();
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__PROJECT_LOCATION:
			return getProjectLocation();
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__FACTORY_CLASS_NAME:
			return getFactoryClassName();
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__PACKAGE_CLASS_NAME:
			return getPackageClassName();
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__ECORE_URI:
			return getEcoreURI();
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__ECORE_HAS_LOCATION:
			return isEcoreHasLocation();
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__ECORE_LOCATION:
			return getEcoreLocation();
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__GENMODEL_URI:
			return getGenmodelURI();
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__GENMODEL_HAS_LOCATION:
			return isGenmodelHasLocation();
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__GENMODEL_LOCATION:
			return getGenmodelLocation();
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__CLASSIFIER_NAME2_FQN:
			if (coreType)
				return getClassifierName2FQN();
			else
				return getClassifierName2FQN().map();
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
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__SIMPLE_NAME:
			setSimpleName((String) newValue);
			return;
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__HAS_ALIAS:
			setHasAlias((Boolean) newValue);
			return;
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__ALIAS:
			setAlias((String) newValue);
			return;
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__FULLY_QUALIFIED_NAME:
			setFullyQualifiedName((String) newValue);
			return;
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__PACKAGE:
			setPackage((EPackage) newValue);
			return;
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__PACKAGE_URI:
			setPackageURI((String) newValue);
			return;
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__PACKAGE_HAS_PROJECT:
			setPackageHasProject((Boolean) newValue);
			return;
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__PROJECT_LOCATION:
			setProjectLocation((String) newValue);
			return;
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__FACTORY_CLASS_NAME:
			setFactoryClassName((String) newValue);
			return;
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__PACKAGE_CLASS_NAME:
			setPackageClassName((String) newValue);
			return;
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__ECORE_URI:
			setEcoreURI((String) newValue);
			return;
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__ECORE_HAS_LOCATION:
			setEcoreHasLocation((Boolean) newValue);
			return;
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__ECORE_LOCATION:
			setEcoreLocation((String) newValue);
			return;
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__GENMODEL_URI:
			setGenmodelURI((String) newValue);
			return;
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__GENMODEL_HAS_LOCATION:
			setGenmodelHasLocation((Boolean) newValue);
			return;
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__GENMODEL_LOCATION:
			setGenmodelLocation((String) newValue);
			return;
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__CLASSIFIER_NAME2_FQN:
			((EStructuralFeature.Setting) getClassifierName2FQN()).set(newValue);
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
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__SIMPLE_NAME:
			setSimpleName(SIMPLE_NAME_EDEFAULT);
			return;
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__HAS_ALIAS:
			setHasAlias(HAS_ALIAS_EDEFAULT);
			return;
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__ALIAS:
			setAlias(ALIAS_EDEFAULT);
			return;
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__FULLY_QUALIFIED_NAME:
			setFullyQualifiedName(FULLY_QUALIFIED_NAME_EDEFAULT);
			return;
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__PACKAGE:
			setPackage((EPackage) null);
			return;
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__PACKAGE_URI:
			setPackageURI(PACKAGE_URI_EDEFAULT);
			return;
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__PACKAGE_HAS_PROJECT:
			setPackageHasProject(PACKAGE_HAS_PROJECT_EDEFAULT);
			return;
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__PROJECT_LOCATION:
			setProjectLocation(PROJECT_LOCATION_EDEFAULT);
			return;
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__FACTORY_CLASS_NAME:
			setFactoryClassName(FACTORY_CLASS_NAME_EDEFAULT);
			return;
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__PACKAGE_CLASS_NAME:
			setPackageClassName(PACKAGE_CLASS_NAME_EDEFAULT);
			return;
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__ECORE_URI:
			setEcoreURI(ECORE_URI_EDEFAULT);
			return;
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__ECORE_HAS_LOCATION:
			setEcoreHasLocation(ECORE_HAS_LOCATION_EDEFAULT);
			return;
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__ECORE_LOCATION:
			setEcoreLocation(ECORE_LOCATION_EDEFAULT);
			return;
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__GENMODEL_URI:
			setGenmodelURI(GENMODEL_URI_EDEFAULT);
			return;
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__GENMODEL_HAS_LOCATION:
			setGenmodelHasLocation(GENMODEL_HAS_LOCATION_EDEFAULT);
			return;
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__GENMODEL_LOCATION:
			setGenmodelLocation(GENMODEL_LOCATION_EDEFAULT);
			return;
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__CLASSIFIER_NAME2_FQN:
			getClassifierName2FQN().clear();
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
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__SIMPLE_NAME:
			return SIMPLE_NAME_EDEFAULT == null ? simpleName != null : !SIMPLE_NAME_EDEFAULT.equals(simpleName);
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__HAS_ALIAS:
			return hasAlias != HAS_ALIAS_EDEFAULT;
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__ALIAS:
			return ALIAS_EDEFAULT == null ? alias != null : !ALIAS_EDEFAULT.equals(alias);
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__FULLY_QUALIFIED_NAME:
			return FULLY_QUALIFIED_NAME_EDEFAULT == null ? fullyQualifiedName != null
					: !FULLY_QUALIFIED_NAME_EDEFAULT.equals(fullyQualifiedName);
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__PACKAGE:
			return package_ != null;
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__PACKAGE_URI:
			return PACKAGE_URI_EDEFAULT == null ? packageURI != null : !PACKAGE_URI_EDEFAULT.equals(packageURI);
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__PACKAGE_HAS_PROJECT:
			return packageHasProject != PACKAGE_HAS_PROJECT_EDEFAULT;
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__PROJECT_LOCATION:
			return PROJECT_LOCATION_EDEFAULT == null ? projectLocation != null
					: !PROJECT_LOCATION_EDEFAULT.equals(projectLocation);
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__FACTORY_CLASS_NAME:
			return FACTORY_CLASS_NAME_EDEFAULT == null ? factoryClassName != null
					: !FACTORY_CLASS_NAME_EDEFAULT.equals(factoryClassName);
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__PACKAGE_CLASS_NAME:
			return PACKAGE_CLASS_NAME_EDEFAULT == null ? packageClassName != null
					: !PACKAGE_CLASS_NAME_EDEFAULT.equals(packageClassName);
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__ECORE_URI:
			return ECORE_URI_EDEFAULT == null ? ecoreURI != null : !ECORE_URI_EDEFAULT.equals(ecoreURI);
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__ECORE_HAS_LOCATION:
			return ecoreHasLocation != ECORE_HAS_LOCATION_EDEFAULT;
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__ECORE_LOCATION:
			return ECORE_LOCATION_EDEFAULT == null ? ecoreLocation != null
					: !ECORE_LOCATION_EDEFAULT.equals(ecoreLocation);
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__GENMODEL_URI:
			return GENMODEL_URI_EDEFAULT == null ? genmodelURI != null : !GENMODEL_URI_EDEFAULT.equals(genmodelURI);
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__GENMODEL_HAS_LOCATION:
			return genmodelHasLocation != GENMODEL_HAS_LOCATION_EDEFAULT;
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__GENMODEL_LOCATION:
			return GENMODEL_LOCATION_EDEFAULT == null ? genmodelLocation != null
					: !GENMODEL_LOCATION_EDEFAULT.equals(genmodelLocation);
		case IBeXCoreModelPackage.EPACKAGE_DEPENDENCY__CLASSIFIER_NAME2_FQN:
			return classifierName2FQN != null && !classifierName2FQN.isEmpty();
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
		result.append(" (simpleName: ");
		result.append(simpleName);
		result.append(", hasAlias: ");
		result.append(hasAlias);
		result.append(", alias: ");
		result.append(alias);
		result.append(", fullyQualifiedName: ");
		result.append(fullyQualifiedName);
		result.append(", packageURI: ");
		result.append(packageURI);
		result.append(", packageHasProject: ");
		result.append(packageHasProject);
		result.append(", projectLocation: ");
		result.append(projectLocation);
		result.append(", factoryClassName: ");
		result.append(factoryClassName);
		result.append(", packageClassName: ");
		result.append(packageClassName);
		result.append(", ecoreURI: ");
		result.append(ecoreURI);
		result.append(", ecoreHasLocation: ");
		result.append(ecoreHasLocation);
		result.append(", ecoreLocation: ");
		result.append(ecoreLocation);
		result.append(", genmodelURI: ");
		result.append(genmodelURI);
		result.append(", genmodelHasLocation: ");
		result.append(genmodelHasLocation);
		result.append(", genmodelLocation: ");
		result.append(genmodelLocation);
		result.append(')');
		return result.toString();
	}

} //EPackageDependencyImpl
