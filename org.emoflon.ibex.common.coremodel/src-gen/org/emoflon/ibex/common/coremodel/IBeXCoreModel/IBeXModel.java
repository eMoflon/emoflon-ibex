/**
 */
package org.emoflon.ibex.common.coremodel.IBeXCoreModel;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IBe XModel</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModel#getMetaData <em>Meta Data</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModel#getFeatureConfig <em>Feature Config</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModel#getPatternSet <em>Pattern Set</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModel#getNodeSet <em>Node Set</em>}</li>
 *   <li>{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModel#getEdgeSet <em>Edge Set</em>}</li>
 * </ul>
 *
 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXModel()
 * @model
 * @generated
 */
public interface IBeXModel extends IBeXNamedElement {
	/**
	 * Returns the value of the '<em><b>Meta Data</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Meta Data</em>' containment reference.
	 * @see #setMetaData(IBeXModelMetadata)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXModel_MetaData()
	 * @model containment="true" required="true"
	 * @generated
	 */
	IBeXModelMetadata getMetaData();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModel#getMetaData <em>Meta Data</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Meta Data</em>' containment reference.
	 * @see #getMetaData()
	 * @generated
	 */
	void setMetaData(IBeXModelMetadata value);

	/**
	 * Returns the value of the '<em><b>Feature Config</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Feature Config</em>' reference.
	 * @see #setFeatureConfig(IBeXFeatureConfig)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXModel_FeatureConfig()
	 * @model required="true"
	 * @generated
	 */
	IBeXFeatureConfig getFeatureConfig();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModel#getFeatureConfig <em>Feature Config</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Feature Config</em>' reference.
	 * @see #getFeatureConfig()
	 * @generated
	 */
	void setFeatureConfig(IBeXFeatureConfig value);

	/**
	 * Returns the value of the '<em><b>Pattern Set</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pattern Set</em>' containment reference.
	 * @see #setPatternSet(IBeXPatternSet)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXModel_PatternSet()
	 * @model containment="true" required="true"
	 * @generated
	 */
	IBeXPatternSet getPatternSet();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModel#getPatternSet <em>Pattern Set</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pattern Set</em>' containment reference.
	 * @see #getPatternSet()
	 * @generated
	 */
	void setPatternSet(IBeXPatternSet value);

	/**
	 * Returns the value of the '<em><b>Node Set</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Node Set</em>' containment reference.
	 * @see #setNodeSet(IBeXNodeSet)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXModel_NodeSet()
	 * @model containment="true" required="true"
	 * @generated
	 */
	IBeXNodeSet getNodeSet();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModel#getNodeSet <em>Node Set</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Node Set</em>' containment reference.
	 * @see #getNodeSet()
	 * @generated
	 */
	void setNodeSet(IBeXNodeSet value);

	/**
	 * Returns the value of the '<em><b>Edge Set</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Edge Set</em>' containment reference.
	 * @see #setEdgeSet(IBeXEdgeSet)
	 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXModel_EdgeSet()
	 * @model containment="true" required="true"
	 * @generated
	 */
	IBeXEdgeSet getEdgeSet();

	/**
	 * Sets the value of the '{@link org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModel#getEdgeSet <em>Edge Set</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Edge Set</em>' containment reference.
	 * @see #getEdgeSet()
	 * @generated
	 */
	void setEdgeSet(IBeXEdgeSet value);

} // IBeXModel
