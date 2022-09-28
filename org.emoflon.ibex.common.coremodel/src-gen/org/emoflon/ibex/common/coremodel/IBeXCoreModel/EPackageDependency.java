/**
 */
package org.emoflon.ibex.common.coremodel.IBeXCoreModel;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EPackage Dependency</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.EPackageDependency#getSimpleName <em>Simple Name</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.EPackageDependency#isHasAlias <em>Has Alias</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.EPackageDependency#getAlias <em>Alias</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.EPackageDependency#getFullyQualifiedName <em>Fully Qualified Name</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.EPackageDependency#getPackage <em>Package</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.EPackageDependency#getPackageURI <em>Package URI</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.EPackageDependency#isPackageHasProject <em>Package Has Project</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.EPackageDependency#getProjectLocation <em>Project Location</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.EPackageDependency#getFactoryClassName <em>Factory Class Name</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.EPackageDependency#getPackageClassName <em>Package Class Name</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.EPackageDependency#getEcoreURI <em>Ecore URI</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.EPackageDependency#isEcoreHasLocation <em>Ecore Has Location</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.EPackageDependency#getEcoreLocation <em>Ecore Location</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.EPackageDependency#getGenmodelURI <em>Genmodel URI</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.EPackageDependency#isGenmodelHasLocation <em>Genmodel Has Location</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.EPackageDependency#getGenmodelLocation <em>Genmodel Location</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.EPackageDependency#getClassifierName2FQN <em>Classifier Name2 FQN</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getEPackageDependency()
 * @model
 * @generated
 */
public interface EPackageDependency extends EObject {
	/**
	 * Returns the value of the '<em><b>Simple Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Simple Name</em>' attribute.
	 * @see #setSimpleName(String)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getEPackageDependency_SimpleName()
	 * @model
	 * @generated
	 */
	String getSimpleName();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.EPackageDependency#getSimpleName <em>Simple Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Simple Name</em>' attribute.
	 * @see #getSimpleName()
	 * @generated
	 */
	void setSimpleName(String value);

	/**
	 * Returns the value of the '<em><b>Has Alias</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Has Alias</em>' attribute.
	 * @see #setHasAlias(boolean)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getEPackageDependency_HasAlias()
	 * @model
	 * @generated
	 */
	boolean isHasAlias();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.EPackageDependency#isHasAlias <em>Has Alias</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Has Alias</em>' attribute.
	 * @see #isHasAlias()
	 * @generated
	 */
	void setHasAlias(boolean value);

	/**
	 * Returns the value of the '<em><b>Alias</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Alias</em>' attribute.
	 * @see #setAlias(String)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getEPackageDependency_Alias()
	 * @model
	 * @generated
	 */
	String getAlias();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.EPackageDependency#getAlias <em>Alias</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Alias</em>' attribute.
	 * @see #getAlias()
	 * @generated
	 */
	void setAlias(String value);

	/**
	 * Returns the value of the '<em><b>Fully Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fully Qualified Name</em>' attribute.
	 * @see #setFullyQualifiedName(String)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getEPackageDependency_FullyQualifiedName()
	 * @model
	 * @generated
	 */
	String getFullyQualifiedName();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.EPackageDependency#getFullyQualifiedName <em>Fully Qualified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fully Qualified Name</em>' attribute.
	 * @see #getFullyQualifiedName()
	 * @generated
	 */
	void setFullyQualifiedName(String value);

	/**
	 * Returns the value of the '<em><b>Package</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Package</em>' reference.
	 * @see #setPackage(EPackage)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getEPackageDependency_Package()
	 * @model required="true"
	 * @generated
	 */
	EPackage getPackage();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.EPackageDependency#getPackage <em>Package</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Package</em>' reference.
	 * @see #getPackage()
	 * @generated
	 */
	void setPackage(EPackage value);

	/**
	 * Returns the value of the '<em><b>Package URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Package URI</em>' attribute.
	 * @see #setPackageURI(String)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getEPackageDependency_PackageURI()
	 * @model
	 * @generated
	 */
	String getPackageURI();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.EPackageDependency#getPackageURI <em>Package URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Package URI</em>' attribute.
	 * @see #getPackageURI()
	 * @generated
	 */
	void setPackageURI(String value);

	/**
	 * Returns the value of the '<em><b>Package Has Project</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Package Has Project</em>' attribute.
	 * @see #setPackageHasProject(boolean)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getEPackageDependency_PackageHasProject()
	 * @model
	 * @generated
	 */
	boolean isPackageHasProject();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.EPackageDependency#isPackageHasProject <em>Package Has Project</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Package Has Project</em>' attribute.
	 * @see #isPackageHasProject()
	 * @generated
	 */
	void setPackageHasProject(boolean value);

	/**
	 * Returns the value of the '<em><b>Project Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Project Location</em>' attribute.
	 * @see #setProjectLocation(String)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getEPackageDependency_ProjectLocation()
	 * @model
	 * @generated
	 */
	String getProjectLocation();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.EPackageDependency#getProjectLocation <em>Project Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Project Location</em>' attribute.
	 * @see #getProjectLocation()
	 * @generated
	 */
	void setProjectLocation(String value);

	/**
	 * Returns the value of the '<em><b>Factory Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Factory Class Name</em>' attribute.
	 * @see #setFactoryClassName(String)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getEPackageDependency_FactoryClassName()
	 * @model
	 * @generated
	 */
	String getFactoryClassName();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.EPackageDependency#getFactoryClassName <em>Factory Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Factory Class Name</em>' attribute.
	 * @see #getFactoryClassName()
	 * @generated
	 */
	void setFactoryClassName(String value);

	/**
	 * Returns the value of the '<em><b>Package Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Package Class Name</em>' attribute.
	 * @see #setPackageClassName(String)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getEPackageDependency_PackageClassName()
	 * @model
	 * @generated
	 */
	String getPackageClassName();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.EPackageDependency#getPackageClassName <em>Package Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Package Class Name</em>' attribute.
	 * @see #getPackageClassName()
	 * @generated
	 */
	void setPackageClassName(String value);

	/**
	 * Returns the value of the '<em><b>Ecore URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ecore URI</em>' attribute.
	 * @see #setEcoreURI(String)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getEPackageDependency_EcoreURI()
	 * @model
	 * @generated
	 */
	String getEcoreURI();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.EPackageDependency#getEcoreURI <em>Ecore URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ecore URI</em>' attribute.
	 * @see #getEcoreURI()
	 * @generated
	 */
	void setEcoreURI(String value);

	/**
	 * Returns the value of the '<em><b>Ecore Has Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ecore Has Location</em>' attribute.
	 * @see #setEcoreHasLocation(boolean)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getEPackageDependency_EcoreHasLocation()
	 * @model
	 * @generated
	 */
	boolean isEcoreHasLocation();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.EPackageDependency#isEcoreHasLocation <em>Ecore Has Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ecore Has Location</em>' attribute.
	 * @see #isEcoreHasLocation()
	 * @generated
	 */
	void setEcoreHasLocation(boolean value);

	/**
	 * Returns the value of the '<em><b>Ecore Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ecore Location</em>' attribute.
	 * @see #setEcoreLocation(String)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getEPackageDependency_EcoreLocation()
	 * @model
	 * @generated
	 */
	String getEcoreLocation();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.EPackageDependency#getEcoreLocation <em>Ecore Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ecore Location</em>' attribute.
	 * @see #getEcoreLocation()
	 * @generated
	 */
	void setEcoreLocation(String value);

	/**
	 * Returns the value of the '<em><b>Genmodel URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Genmodel URI</em>' attribute.
	 * @see #setGenmodelURI(String)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getEPackageDependency_GenmodelURI()
	 * @model
	 * @generated
	 */
	String getGenmodelURI();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.EPackageDependency#getGenmodelURI <em>Genmodel URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Genmodel URI</em>' attribute.
	 * @see #getGenmodelURI()
	 * @generated
	 */
	void setGenmodelURI(String value);

	/**
	 * Returns the value of the '<em><b>Genmodel Has Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Genmodel Has Location</em>' attribute.
	 * @see #setGenmodelHasLocation(boolean)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getEPackageDependency_GenmodelHasLocation()
	 * @model
	 * @generated
	 */
	boolean isGenmodelHasLocation();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.EPackageDependency#isGenmodelHasLocation <em>Genmodel Has Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Genmodel Has Location</em>' attribute.
	 * @see #isGenmodelHasLocation()
	 * @generated
	 */
	void setGenmodelHasLocation(boolean value);

	/**
	 * Returns the value of the '<em><b>Genmodel Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Genmodel Location</em>' attribute.
	 * @see #setGenmodelLocation(String)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getEPackageDependency_GenmodelLocation()
	 * @model
	 * @generated
	 */
	String getGenmodelLocation();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.EPackageDependency#getGenmodelLocation <em>Genmodel Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Genmodel Location</em>' attribute.
	 * @see #getGenmodelLocation()
	 * @generated
	 */
	void setGenmodelLocation(String value);

	/**
	 * Returns the value of the '<em><b>Classifier Name2 FQN</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Classifier Name2 FQN</em>' map.
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getEPackageDependency_ClassifierName2FQN()
	 * @model mapType="org.emoflon.ibex.common.coremodel.IBeXCoreModel.ClassifierNameToFQN&lt;org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString&gt;"
	 * @generated
	 */
	EMap<String, String> getClassifierName2FQN();

} // EPackageDependency
