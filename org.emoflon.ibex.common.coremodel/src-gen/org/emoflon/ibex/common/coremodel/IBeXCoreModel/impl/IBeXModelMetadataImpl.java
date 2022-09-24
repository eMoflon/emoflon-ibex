/**
 */
package org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.EPackageDependency;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModelMetadata;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IBe XModel Metadata</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXModelMetadataImpl#getProject <em>Project</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXModelMetadataImpl#getProjectPath <em>Project Path</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXModelMetadataImpl#getPackage <em>Package</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXModelMetadataImpl#getPackagePath <em>Package Path</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXModelMetadataImpl#getDependencies <em>Dependencies</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXModelMetadataImpl#getName2package <em>Name2package</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IBeXModelMetadataImpl extends MinimalEObjectImpl.Container implements IBeXModelMetadata {
	/**
	 * The default value of the '{@link #getProject() <em>Project</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProject()
	 * @generated
	 * @ordered
	 */
	protected static final String PROJECT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProject() <em>Project</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProject()
	 * @generated
	 * @ordered
	 */
	protected String project = PROJECT_EDEFAULT;

	/**
	 * The default value of the '{@link #getProjectPath() <em>Project Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjectPath()
	 * @generated
	 * @ordered
	 */
	protected static final String PROJECT_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProjectPath() <em>Project Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjectPath()
	 * @generated
	 * @ordered
	 */
	protected String projectPath = PROJECT_PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #getPackage() <em>Package</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackage()
	 * @generated
	 * @ordered
	 */
	protected static final String PACKAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPackage() <em>Package</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackage()
	 * @generated
	 * @ordered
	 */
	protected String package_ = PACKAGE_EDEFAULT;

	/**
	 * The default value of the '{@link #getPackagePath() <em>Package Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackagePath()
	 * @generated
	 * @ordered
	 */
	protected static final String PACKAGE_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPackagePath() <em>Package Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackagePath()
	 * @generated
	 * @ordered
	 */
	protected String packagePath = PACKAGE_PATH_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDependencies() <em>Dependencies</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDependencies()
	 * @generated
	 * @ordered
	 */
	protected EList<EPackageDependency> dependencies;

	/**
	 * The cached value of the '{@link #getName2package() <em>Name2package</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName2package()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, EPackageDependency> name2package;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IBeXModelMetadataImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXCoreModelPackage.Literals.IBE_XMODEL_METADATA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getProject() {
		return project;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProject(String newProject) {
		String oldProject = project;
		project = newProject;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXCoreModelPackage.IBE_XMODEL_METADATA__PROJECT,
					oldProject, project));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getProjectPath() {
		return projectPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProjectPath(String newProjectPath) {
		String oldProjectPath = projectPath;
		projectPath = newProjectPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXCoreModelPackage.IBE_XMODEL_METADATA__PROJECT_PATH, oldProjectPath, projectPath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPackage() {
		return package_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPackage(String newPackage) {
		String oldPackage = package_;
		package_ = newPackage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXCoreModelPackage.IBE_XMODEL_METADATA__PACKAGE,
					oldPackage, package_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPackagePath() {
		return packagePath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPackagePath(String newPackagePath) {
		String oldPackagePath = packagePath;
		packagePath = newPackagePath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXCoreModelPackage.IBE_XMODEL_METADATA__PACKAGE_PATH, oldPackagePath, packagePath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EPackageDependency> getDependencies() {
		if (dependencies == null) {
			dependencies = new EObjectContainmentEList<EPackageDependency>(EPackageDependency.class, this,
					IBeXCoreModelPackage.IBE_XMODEL_METADATA__DEPENDENCIES);
		}
		return dependencies;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, EPackageDependency> getName2package() {
		if (name2package == null) {
			name2package = new EcoreEMap<String, EPackageDependency>(
					IBeXCoreModelPackage.Literals.IMPORT_NAME_TO_PACKAGE_DEPENDENCY_MAPPING,
					ImportNameToPackageDependencyMappingImpl.class, this,
					IBeXCoreModelPackage.IBE_XMODEL_METADATA__NAME2PACKAGE);
		}
		return name2package;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IBeXCoreModelPackage.IBE_XMODEL_METADATA__DEPENDENCIES:
			return ((InternalEList<?>) getDependencies()).basicRemove(otherEnd, msgs);
		case IBeXCoreModelPackage.IBE_XMODEL_METADATA__NAME2PACKAGE:
			return ((InternalEList<?>) getName2package()).basicRemove(otherEnd, msgs);
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
		case IBeXCoreModelPackage.IBE_XMODEL_METADATA__PROJECT:
			return getProject();
		case IBeXCoreModelPackage.IBE_XMODEL_METADATA__PROJECT_PATH:
			return getProjectPath();
		case IBeXCoreModelPackage.IBE_XMODEL_METADATA__PACKAGE:
			return getPackage();
		case IBeXCoreModelPackage.IBE_XMODEL_METADATA__PACKAGE_PATH:
			return getPackagePath();
		case IBeXCoreModelPackage.IBE_XMODEL_METADATA__DEPENDENCIES:
			return getDependencies();
		case IBeXCoreModelPackage.IBE_XMODEL_METADATA__NAME2PACKAGE:
			if (coreType)
				return getName2package();
			else
				return getName2package().map();
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
		case IBeXCoreModelPackage.IBE_XMODEL_METADATA__PROJECT:
			setProject((String) newValue);
			return;
		case IBeXCoreModelPackage.IBE_XMODEL_METADATA__PROJECT_PATH:
			setProjectPath((String) newValue);
			return;
		case IBeXCoreModelPackage.IBE_XMODEL_METADATA__PACKAGE:
			setPackage((String) newValue);
			return;
		case IBeXCoreModelPackage.IBE_XMODEL_METADATA__PACKAGE_PATH:
			setPackagePath((String) newValue);
			return;
		case IBeXCoreModelPackage.IBE_XMODEL_METADATA__DEPENDENCIES:
			getDependencies().clear();
			getDependencies().addAll((Collection<? extends EPackageDependency>) newValue);
			return;
		case IBeXCoreModelPackage.IBE_XMODEL_METADATA__NAME2PACKAGE:
			((EStructuralFeature.Setting) getName2package()).set(newValue);
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
		case IBeXCoreModelPackage.IBE_XMODEL_METADATA__PROJECT:
			setProject(PROJECT_EDEFAULT);
			return;
		case IBeXCoreModelPackage.IBE_XMODEL_METADATA__PROJECT_PATH:
			setProjectPath(PROJECT_PATH_EDEFAULT);
			return;
		case IBeXCoreModelPackage.IBE_XMODEL_METADATA__PACKAGE:
			setPackage(PACKAGE_EDEFAULT);
			return;
		case IBeXCoreModelPackage.IBE_XMODEL_METADATA__PACKAGE_PATH:
			setPackagePath(PACKAGE_PATH_EDEFAULT);
			return;
		case IBeXCoreModelPackage.IBE_XMODEL_METADATA__DEPENDENCIES:
			getDependencies().clear();
			return;
		case IBeXCoreModelPackage.IBE_XMODEL_METADATA__NAME2PACKAGE:
			getName2package().clear();
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
		case IBeXCoreModelPackage.IBE_XMODEL_METADATA__PROJECT:
			return PROJECT_EDEFAULT == null ? project != null : !PROJECT_EDEFAULT.equals(project);
		case IBeXCoreModelPackage.IBE_XMODEL_METADATA__PROJECT_PATH:
			return PROJECT_PATH_EDEFAULT == null ? projectPath != null : !PROJECT_PATH_EDEFAULT.equals(projectPath);
		case IBeXCoreModelPackage.IBE_XMODEL_METADATA__PACKAGE:
			return PACKAGE_EDEFAULT == null ? package_ != null : !PACKAGE_EDEFAULT.equals(package_);
		case IBeXCoreModelPackage.IBE_XMODEL_METADATA__PACKAGE_PATH:
			return PACKAGE_PATH_EDEFAULT == null ? packagePath != null : !PACKAGE_PATH_EDEFAULT.equals(packagePath);
		case IBeXCoreModelPackage.IBE_XMODEL_METADATA__DEPENDENCIES:
			return dependencies != null && !dependencies.isEmpty();
		case IBeXCoreModelPackage.IBE_XMODEL_METADATA__NAME2PACKAGE:
			return name2package != null && !name2package.isEmpty();
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
		result.append(" (project: ");
		result.append(project);
		result.append(", projectPath: ");
		result.append(projectPath);
		result.append(", package: ");
		result.append(package_);
		result.append(", packagePath: ");
		result.append(packagePath);
		result.append(')');
		return result.toString();
	}

} //IBeXModelMetadataImpl
