/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRuleDelta;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.impl.IBeXRuleImpl;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintSet;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGCorrespondence;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGEdge;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>TGG Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleImpl#getNodes <em>Nodes</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleImpl#getEdges <em>Edges</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleImpl#getCorrespondenceNodes <em>Correspondence Nodes</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleImpl#getOperationalisations <em>Operationalisations</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleImpl#getAttributeConstraints <em>Attribute Constraints</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleImpl#isAbstract <em>Abstract</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleImpl#isAxiom <em>Axiom</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleImpl#getContext <em>Context</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleImpl#getContextSource <em>Context Source</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleImpl#getContextCorrespondence <em>Context Correspondence</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleImpl#getContextTarget <em>Context Target</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleImpl#getCreateSourceAndTarget <em>Create Source And Target</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleImpl#getCreate <em>Create</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleImpl#getCreateSource <em>Create Source</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleImpl#getCreateCorrespondence <em>Create Correspondence</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleImpl#getCreateTarget <em>Create Target</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleImpl#getSource <em>Source</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleImpl#getCorrespondence <em>Correspondence</em>}</li>
 *   <li>{@link org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.impl.TGGRuleImpl#getTarget <em>Target</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TGGRuleImpl extends IBeXRuleImpl implements TGGRule {
	/**
	 * The cached value of the '{@link #getNodes() <em>Nodes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNodes()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGNode> nodes;

	/**
	 * The cached value of the '{@link #getEdges() <em>Edges</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEdges()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGEdge> edges;

	/**
	 * The cached value of the '{@link #getCorrespondenceNodes() <em>Correspondence Nodes</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCorrespondenceNodes()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGCorrespondence> correspondenceNodes;

	/**
	 * The cached value of the '{@link #getOperationalisations() <em>Operationalisations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperationalisations()
	 * @generated
	 * @ordered
	 */
	protected EList<TGGOperationalRule> operationalisations;

	/**
	 * The cached value of the '{@link #getAttributeConstraints() <em>Attribute Constraints</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeConstraints()
	 * @generated
	 * @ordered
	 */
	protected TGGAttributeConstraintSet attributeConstraints;

	/**
	 * The default value of the '{@link #isAbstract() <em>Abstract</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAbstract()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ABSTRACT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAbstract() <em>Abstract</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAbstract()
	 * @generated
	 * @ordered
	 */
	protected boolean abstract_ = ABSTRACT_EDEFAULT;

	/**
	 * The default value of the '{@link #isAxiom() <em>Axiom</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAxiom()
	 * @generated
	 * @ordered
	 */
	protected static final boolean AXIOM_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAxiom() <em>Axiom</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAxiom()
	 * @generated
	 * @ordered
	 */
	protected boolean axiom = AXIOM_EDEFAULT;

	/**
	 * The cached value of the '{@link #getContext() <em>Context</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContext()
	 * @generated
	 * @ordered
	 */
	protected IBeXRuleDelta context;

	/**
	 * The cached value of the '{@link #getContextSource() <em>Context Source</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContextSource()
	 * @generated
	 * @ordered
	 */
	protected IBeXRuleDelta contextSource;

	/**
	 * The cached value of the '{@link #getContextCorrespondence() <em>Context Correspondence</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContextCorrespondence()
	 * @generated
	 * @ordered
	 */
	protected IBeXRuleDelta contextCorrespondence;

	/**
	 * The cached value of the '{@link #getContextTarget() <em>Context Target</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContextTarget()
	 * @generated
	 * @ordered
	 */
	protected IBeXRuleDelta contextTarget;

	/**
	 * The cached value of the '{@link #getCreateSourceAndTarget() <em>Create Source And Target</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreateSourceAndTarget()
	 * @generated
	 * @ordered
	 */
	protected IBeXRuleDelta createSourceAndTarget;

	/**
	 * The cached value of the '{@link #getCreate() <em>Create</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreate()
	 * @generated
	 * @ordered
	 */
	protected IBeXRuleDelta create;

	/**
	 * The cached value of the '{@link #getCreateSource() <em>Create Source</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreateSource()
	 * @generated
	 * @ordered
	 */
	protected IBeXRuleDelta createSource;

	/**
	 * The cached value of the '{@link #getCreateCorrespondence() <em>Create Correspondence</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreateCorrespondence()
	 * @generated
	 * @ordered
	 */
	protected IBeXRuleDelta createCorrespondence;

	/**
	 * The cached value of the '{@link #getCreateTarget() <em>Create Target</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreateTarget()
	 * @generated
	 * @ordered
	 */
	protected IBeXRuleDelta createTarget;

	/**
	 * The cached value of the '{@link #getSource() <em>Source</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected IBeXRuleDelta source;

	/**
	 * The cached value of the '{@link #getCorrespondence() <em>Correspondence</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCorrespondence()
	 * @generated
	 * @ordered
	 */
	protected IBeXRuleDelta correspondence;

	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected IBeXRuleDelta target;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TGGRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IBeXTGGModelPackage.Literals.TGG_RULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TGGNode> getNodes() {
		if (nodes == null) {
			nodes = new EObjectContainmentEList<TGGNode>(TGGNode.class, this, IBeXTGGModelPackage.TGG_RULE__NODES);
		}
		return nodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TGGEdge> getEdges() {
		if (edges == null) {
			edges = new EObjectContainmentEList<TGGEdge>(TGGEdge.class, this, IBeXTGGModelPackage.TGG_RULE__EDGES);
		}
		return edges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TGGCorrespondence> getCorrespondenceNodes() {
		if (correspondenceNodes == null) {
			correspondenceNodes = new EObjectResolvingEList<TGGCorrespondence>(TGGCorrespondence.class, this,
					IBeXTGGModelPackage.TGG_RULE__CORRESPONDENCE_NODES);
		}
		return correspondenceNodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<TGGOperationalRule> getOperationalisations() {
		if (operationalisations == null) {
			operationalisations = new EObjectContainmentEList<TGGOperationalRule>(TGGOperationalRule.class, this,
					IBeXTGGModelPackage.TGG_RULE__OPERATIONALISATIONS);
		}
		return operationalisations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TGGAttributeConstraintSet getAttributeConstraints() {
		return attributeConstraints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAttributeConstraints(TGGAttributeConstraintSet newAttributeConstraints, NotificationChain msgs) {
		TGGAttributeConstraintSet oldAttributeConstraints = attributeConstraints;
		attributeConstraints = newAttributeConstraints;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IBeXTGGModelPackage.TGG_RULE__ATTRIBUTE_CONSTRAINTS,
					oldAttributeConstraints, newAttributeConstraints);
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
	public void setAttributeConstraints(TGGAttributeConstraintSet newAttributeConstraints) {
		if (newAttributeConstraints != attributeConstraints) {
			NotificationChain msgs = null;
			if (attributeConstraints != null)
				msgs = ((InternalEObject) attributeConstraints).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_RULE__ATTRIBUTE_CONSTRAINTS, null, msgs);
			if (newAttributeConstraints != null)
				msgs = ((InternalEObject) newAttributeConstraints).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_RULE__ATTRIBUTE_CONSTRAINTS, null, msgs);
			msgs = basicSetAttributeConstraints(newAttributeConstraints, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXTGGModelPackage.TGG_RULE__ATTRIBUTE_CONSTRAINTS, newAttributeConstraints,
					newAttributeConstraints));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAbstract() {
		return abstract_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAbstract(boolean newAbstract) {
		boolean oldAbstract = abstract_;
		abstract_ = newAbstract;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXTGGModelPackage.TGG_RULE__ABSTRACT, oldAbstract, abstract_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXRuleDelta getContext() {
		return context;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetContext(IBeXRuleDelta newContext, NotificationChain msgs) {
		IBeXRuleDelta oldContext = context;
		context = newContext;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IBeXTGGModelPackage.TGG_RULE__CONTEXT, oldContext, newContext);
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
	public void setContext(IBeXRuleDelta newContext) {
		if (newContext != context) {
			NotificationChain msgs = null;
			if (context != null)
				msgs = ((InternalEObject) context).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_RULE__CONTEXT, null, msgs);
			if (newContext != null)
				msgs = ((InternalEObject) newContext).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_RULE__CONTEXT, null, msgs);
			msgs = basicSetContext(newContext, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXTGGModelPackage.TGG_RULE__CONTEXT, newContext, newContext));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXRuleDelta getContextSource() {
		return contextSource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetContextSource(IBeXRuleDelta newContextSource, NotificationChain msgs) {
		IBeXRuleDelta oldContextSource = contextSource;
		contextSource = newContextSource;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IBeXTGGModelPackage.TGG_RULE__CONTEXT_SOURCE, oldContextSource,
					newContextSource);
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
	public void setContextSource(IBeXRuleDelta newContextSource) {
		if (newContextSource != contextSource) {
			NotificationChain msgs = null;
			if (contextSource != null)
				msgs = ((InternalEObject) contextSource).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_RULE__CONTEXT_SOURCE, null,
						msgs);
			if (newContextSource != null)
				msgs = ((InternalEObject) newContextSource).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_RULE__CONTEXT_SOURCE, null,
						msgs);
			msgs = basicSetContextSource(newContextSource, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXTGGModelPackage.TGG_RULE__CONTEXT_SOURCE, newContextSource, newContextSource));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXRuleDelta getContextCorrespondence() {
		return contextCorrespondence;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetContextCorrespondence(IBeXRuleDelta newContextCorrespondence, NotificationChain msgs) {
		IBeXRuleDelta oldContextCorrespondence = contextCorrespondence;
		contextCorrespondence = newContextCorrespondence;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IBeXTGGModelPackage.TGG_RULE__CONTEXT_CORRESPONDENCE,
					oldContextCorrespondence, newContextCorrespondence);
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
	public void setContextCorrespondence(IBeXRuleDelta newContextCorrespondence) {
		if (newContextCorrespondence != contextCorrespondence) {
			NotificationChain msgs = null;
			if (contextCorrespondence != null)
				msgs = ((InternalEObject) contextCorrespondence).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_RULE__CONTEXT_CORRESPONDENCE, null, msgs);
			if (newContextCorrespondence != null)
				msgs = ((InternalEObject) newContextCorrespondence).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_RULE__CONTEXT_CORRESPONDENCE, null, msgs);
			msgs = basicSetContextCorrespondence(newContextCorrespondence, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXTGGModelPackage.TGG_RULE__CONTEXT_CORRESPONDENCE, newContextCorrespondence,
					newContextCorrespondence));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXRuleDelta getContextTarget() {
		return contextTarget;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetContextTarget(IBeXRuleDelta newContextTarget, NotificationChain msgs) {
		IBeXRuleDelta oldContextTarget = contextTarget;
		contextTarget = newContextTarget;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IBeXTGGModelPackage.TGG_RULE__CONTEXT_TARGET, oldContextTarget,
					newContextTarget);
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
	public void setContextTarget(IBeXRuleDelta newContextTarget) {
		if (newContextTarget != contextTarget) {
			NotificationChain msgs = null;
			if (contextTarget != null)
				msgs = ((InternalEObject) contextTarget).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_RULE__CONTEXT_TARGET, null,
						msgs);
			if (newContextTarget != null)
				msgs = ((InternalEObject) newContextTarget).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_RULE__CONTEXT_TARGET, null,
						msgs);
			msgs = basicSetContextTarget(newContextTarget, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXTGGModelPackage.TGG_RULE__CONTEXT_TARGET, newContextTarget, newContextTarget));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXRuleDelta getCreateSourceAndTarget() {
		return createSourceAndTarget;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCreateSourceAndTarget(IBeXRuleDelta newCreateSourceAndTarget,
			NotificationChain msgs) {
		IBeXRuleDelta oldCreateSourceAndTarget = createSourceAndTarget;
		createSourceAndTarget = newCreateSourceAndTarget;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IBeXTGGModelPackage.TGG_RULE__CREATE_SOURCE_AND_TARGET, oldCreateSourceAndTarget,
					newCreateSourceAndTarget);
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
	public void setCreateSourceAndTarget(IBeXRuleDelta newCreateSourceAndTarget) {
		if (newCreateSourceAndTarget != createSourceAndTarget) {
			NotificationChain msgs = null;
			if (createSourceAndTarget != null)
				msgs = ((InternalEObject) createSourceAndTarget).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_RULE__CREATE_SOURCE_AND_TARGET, null, msgs);
			if (newCreateSourceAndTarget != null)
				msgs = ((InternalEObject) newCreateSourceAndTarget).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_RULE__CREATE_SOURCE_AND_TARGET, null, msgs);
			msgs = basicSetCreateSourceAndTarget(newCreateSourceAndTarget, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IBeXTGGModelPackage.TGG_RULE__CREATE_SOURCE_AND_TARGET, newCreateSourceAndTarget,
					newCreateSourceAndTarget));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXRuleDelta getCreate() {
		return create;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCreate(IBeXRuleDelta newCreate, NotificationChain msgs) {
		IBeXRuleDelta oldCreate = create;
		create = newCreate;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IBeXTGGModelPackage.TGG_RULE__CREATE, oldCreate, newCreate);
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
	public void setCreate(IBeXRuleDelta newCreate) {
		if (newCreate != create) {
			NotificationChain msgs = null;
			if (create != null)
				msgs = ((InternalEObject) create).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_RULE__CREATE, null, msgs);
			if (newCreate != null)
				msgs = ((InternalEObject) newCreate).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_RULE__CREATE, null, msgs);
			msgs = basicSetCreate(newCreate, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXTGGModelPackage.TGG_RULE__CREATE, newCreate, newCreate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXRuleDelta getCreateSource() {
		return createSource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCreateSource(IBeXRuleDelta newCreateSource, NotificationChain msgs) {
		IBeXRuleDelta oldCreateSource = createSource;
		createSource = newCreateSource;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IBeXTGGModelPackage.TGG_RULE__CREATE_SOURCE, oldCreateSource,
					newCreateSource);
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
	public void setCreateSource(IBeXRuleDelta newCreateSource) {
		if (newCreateSource != createSource) {
			NotificationChain msgs = null;
			if (createSource != null)
				msgs = ((InternalEObject) createSource).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_RULE__CREATE_SOURCE, null,
						msgs);
			if (newCreateSource != null)
				msgs = ((InternalEObject) newCreateSource).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_RULE__CREATE_SOURCE, null,
						msgs);
			msgs = basicSetCreateSource(newCreateSource, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXTGGModelPackage.TGG_RULE__CREATE_SOURCE, newCreateSource, newCreateSource));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXRuleDelta getCreateCorrespondence() {
		return createCorrespondence;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCreateCorrespondence(IBeXRuleDelta newCreateCorrespondence, NotificationChain msgs) {
		IBeXRuleDelta oldCreateCorrespondence = createCorrespondence;
		createCorrespondence = newCreateCorrespondence;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IBeXTGGModelPackage.TGG_RULE__CREATE_CORRESPONDENCE,
					oldCreateCorrespondence, newCreateCorrespondence);
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
	public void setCreateCorrespondence(IBeXRuleDelta newCreateCorrespondence) {
		if (newCreateCorrespondence != createCorrespondence) {
			NotificationChain msgs = null;
			if (createCorrespondence != null)
				msgs = ((InternalEObject) createCorrespondence).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_RULE__CREATE_CORRESPONDENCE, null, msgs);
			if (newCreateCorrespondence != null)
				msgs = ((InternalEObject) newCreateCorrespondence).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_RULE__CREATE_CORRESPONDENCE, null, msgs);
			msgs = basicSetCreateCorrespondence(newCreateCorrespondence, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXTGGModelPackage.TGG_RULE__CREATE_CORRESPONDENCE, newCreateCorrespondence,
					newCreateCorrespondence));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXRuleDelta getCreateTarget() {
		return createTarget;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCreateTarget(IBeXRuleDelta newCreateTarget, NotificationChain msgs) {
		IBeXRuleDelta oldCreateTarget = createTarget;
		createTarget = newCreateTarget;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IBeXTGGModelPackage.TGG_RULE__CREATE_TARGET, oldCreateTarget,
					newCreateTarget);
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
	public void setCreateTarget(IBeXRuleDelta newCreateTarget) {
		if (newCreateTarget != createTarget) {
			NotificationChain msgs = null;
			if (createTarget != null)
				msgs = ((InternalEObject) createTarget).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_RULE__CREATE_TARGET, null,
						msgs);
			if (newCreateTarget != null)
				msgs = ((InternalEObject) newCreateTarget).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_RULE__CREATE_TARGET, null,
						msgs);
			msgs = basicSetCreateTarget(newCreateTarget, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXTGGModelPackage.TGG_RULE__CREATE_TARGET, newCreateTarget, newCreateTarget));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAxiom() {
		return axiom;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAxiom(boolean newAxiom) {
		boolean oldAxiom = axiom;
		axiom = newAxiom;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXTGGModelPackage.TGG_RULE__AXIOM, oldAxiom, axiom));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXRuleDelta getSource() {
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSource(IBeXRuleDelta newSource, NotificationChain msgs) {
		IBeXRuleDelta oldSource = source;
		source = newSource;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IBeXTGGModelPackage.TGG_RULE__SOURCE, oldSource, newSource);
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
	public void setSource(IBeXRuleDelta newSource) {
		if (newSource != source) {
			NotificationChain msgs = null;
			if (source != null)
				msgs = ((InternalEObject) source).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_RULE__SOURCE, null, msgs);
			if (newSource != null)
				msgs = ((InternalEObject) newSource).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_RULE__SOURCE, null, msgs);
			msgs = basicSetSource(newSource, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXTGGModelPackage.TGG_RULE__SOURCE, newSource, newSource));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXRuleDelta getCorrespondence() {
		return correspondence;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCorrespondence(IBeXRuleDelta newCorrespondence, NotificationChain msgs) {
		IBeXRuleDelta oldCorrespondence = correspondence;
		correspondence = newCorrespondence;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IBeXTGGModelPackage.TGG_RULE__CORRESPONDENCE, oldCorrespondence,
					newCorrespondence);
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
	public void setCorrespondence(IBeXRuleDelta newCorrespondence) {
		if (newCorrespondence != correspondence) {
			NotificationChain msgs = null;
			if (correspondence != null)
				msgs = ((InternalEObject) correspondence).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_RULE__CORRESPONDENCE, null,
						msgs);
			if (newCorrespondence != null)
				msgs = ((InternalEObject) newCorrespondence).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_RULE__CORRESPONDENCE, null,
						msgs);
			msgs = basicSetCorrespondence(newCorrespondence, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXTGGModelPackage.TGG_RULE__CORRESPONDENCE, newCorrespondence, newCorrespondence));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXRuleDelta getTarget() {
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTarget(IBeXRuleDelta newTarget, NotificationChain msgs) {
		IBeXRuleDelta oldTarget = target;
		target = newTarget;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IBeXTGGModelPackage.TGG_RULE__TARGET, oldTarget, newTarget);
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
	public void setTarget(IBeXRuleDelta newTarget) {
		if (newTarget != target) {
			NotificationChain msgs = null;
			if (target != null)
				msgs = ((InternalEObject) target).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_RULE__TARGET, null, msgs);
			if (newTarget != null)
				msgs = ((InternalEObject) newTarget).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IBeXTGGModelPackage.TGG_RULE__TARGET, null, msgs);
			msgs = basicSetTarget(newTarget, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IBeXTGGModelPackage.TGG_RULE__TARGET, newTarget, newTarget));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IBeXTGGModelPackage.TGG_RULE__NODES:
			return ((InternalEList<?>) getNodes()).basicRemove(otherEnd, msgs);
		case IBeXTGGModelPackage.TGG_RULE__EDGES:
			return ((InternalEList<?>) getEdges()).basicRemove(otherEnd, msgs);
		case IBeXTGGModelPackage.TGG_RULE__OPERATIONALISATIONS:
			return ((InternalEList<?>) getOperationalisations()).basicRemove(otherEnd, msgs);
		case IBeXTGGModelPackage.TGG_RULE__ATTRIBUTE_CONSTRAINTS:
			return basicSetAttributeConstraints(null, msgs);
		case IBeXTGGModelPackage.TGG_RULE__CONTEXT:
			return basicSetContext(null, msgs);
		case IBeXTGGModelPackage.TGG_RULE__CONTEXT_SOURCE:
			return basicSetContextSource(null, msgs);
		case IBeXTGGModelPackage.TGG_RULE__CONTEXT_CORRESPONDENCE:
			return basicSetContextCorrespondence(null, msgs);
		case IBeXTGGModelPackage.TGG_RULE__CONTEXT_TARGET:
			return basicSetContextTarget(null, msgs);
		case IBeXTGGModelPackage.TGG_RULE__CREATE_SOURCE_AND_TARGET:
			return basicSetCreateSourceAndTarget(null, msgs);
		case IBeXTGGModelPackage.TGG_RULE__CREATE:
			return basicSetCreate(null, msgs);
		case IBeXTGGModelPackage.TGG_RULE__CREATE_SOURCE:
			return basicSetCreateSource(null, msgs);
		case IBeXTGGModelPackage.TGG_RULE__CREATE_CORRESPONDENCE:
			return basicSetCreateCorrespondence(null, msgs);
		case IBeXTGGModelPackage.TGG_RULE__CREATE_TARGET:
			return basicSetCreateTarget(null, msgs);
		case IBeXTGGModelPackage.TGG_RULE__SOURCE:
			return basicSetSource(null, msgs);
		case IBeXTGGModelPackage.TGG_RULE__CORRESPONDENCE:
			return basicSetCorrespondence(null, msgs);
		case IBeXTGGModelPackage.TGG_RULE__TARGET:
			return basicSetTarget(null, msgs);
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
		case IBeXTGGModelPackage.TGG_RULE__NODES:
			return getNodes();
		case IBeXTGGModelPackage.TGG_RULE__EDGES:
			return getEdges();
		case IBeXTGGModelPackage.TGG_RULE__CORRESPONDENCE_NODES:
			return getCorrespondenceNodes();
		case IBeXTGGModelPackage.TGG_RULE__OPERATIONALISATIONS:
			return getOperationalisations();
		case IBeXTGGModelPackage.TGG_RULE__ATTRIBUTE_CONSTRAINTS:
			return getAttributeConstraints();
		case IBeXTGGModelPackage.TGG_RULE__ABSTRACT:
			return isAbstract();
		case IBeXTGGModelPackage.TGG_RULE__AXIOM:
			return isAxiom();
		case IBeXTGGModelPackage.TGG_RULE__CONTEXT:
			return getContext();
		case IBeXTGGModelPackage.TGG_RULE__CONTEXT_SOURCE:
			return getContextSource();
		case IBeXTGGModelPackage.TGG_RULE__CONTEXT_CORRESPONDENCE:
			return getContextCorrespondence();
		case IBeXTGGModelPackage.TGG_RULE__CONTEXT_TARGET:
			return getContextTarget();
		case IBeXTGGModelPackage.TGG_RULE__CREATE_SOURCE_AND_TARGET:
			return getCreateSourceAndTarget();
		case IBeXTGGModelPackage.TGG_RULE__CREATE:
			return getCreate();
		case IBeXTGGModelPackage.TGG_RULE__CREATE_SOURCE:
			return getCreateSource();
		case IBeXTGGModelPackage.TGG_RULE__CREATE_CORRESPONDENCE:
			return getCreateCorrespondence();
		case IBeXTGGModelPackage.TGG_RULE__CREATE_TARGET:
			return getCreateTarget();
		case IBeXTGGModelPackage.TGG_RULE__SOURCE:
			return getSource();
		case IBeXTGGModelPackage.TGG_RULE__CORRESPONDENCE:
			return getCorrespondence();
		case IBeXTGGModelPackage.TGG_RULE__TARGET:
			return getTarget();
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
		case IBeXTGGModelPackage.TGG_RULE__NODES:
			getNodes().clear();
			getNodes().addAll((Collection<? extends TGGNode>) newValue);
			return;
		case IBeXTGGModelPackage.TGG_RULE__EDGES:
			getEdges().clear();
			getEdges().addAll((Collection<? extends TGGEdge>) newValue);
			return;
		case IBeXTGGModelPackage.TGG_RULE__CORRESPONDENCE_NODES:
			getCorrespondenceNodes().clear();
			getCorrespondenceNodes().addAll((Collection<? extends TGGCorrespondence>) newValue);
			return;
		case IBeXTGGModelPackage.TGG_RULE__OPERATIONALISATIONS:
			getOperationalisations().clear();
			getOperationalisations().addAll((Collection<? extends TGGOperationalRule>) newValue);
			return;
		case IBeXTGGModelPackage.TGG_RULE__ATTRIBUTE_CONSTRAINTS:
			setAttributeConstraints((TGGAttributeConstraintSet) newValue);
			return;
		case IBeXTGGModelPackage.TGG_RULE__ABSTRACT:
			setAbstract((Boolean) newValue);
			return;
		case IBeXTGGModelPackage.TGG_RULE__AXIOM:
			setAxiom((Boolean) newValue);
			return;
		case IBeXTGGModelPackage.TGG_RULE__CONTEXT:
			setContext((IBeXRuleDelta) newValue);
			return;
		case IBeXTGGModelPackage.TGG_RULE__CONTEXT_SOURCE:
			setContextSource((IBeXRuleDelta) newValue);
			return;
		case IBeXTGGModelPackage.TGG_RULE__CONTEXT_CORRESPONDENCE:
			setContextCorrespondence((IBeXRuleDelta) newValue);
			return;
		case IBeXTGGModelPackage.TGG_RULE__CONTEXT_TARGET:
			setContextTarget((IBeXRuleDelta) newValue);
			return;
		case IBeXTGGModelPackage.TGG_RULE__CREATE_SOURCE_AND_TARGET:
			setCreateSourceAndTarget((IBeXRuleDelta) newValue);
			return;
		case IBeXTGGModelPackage.TGG_RULE__CREATE:
			setCreate((IBeXRuleDelta) newValue);
			return;
		case IBeXTGGModelPackage.TGG_RULE__CREATE_SOURCE:
			setCreateSource((IBeXRuleDelta) newValue);
			return;
		case IBeXTGGModelPackage.TGG_RULE__CREATE_CORRESPONDENCE:
			setCreateCorrespondence((IBeXRuleDelta) newValue);
			return;
		case IBeXTGGModelPackage.TGG_RULE__CREATE_TARGET:
			setCreateTarget((IBeXRuleDelta) newValue);
			return;
		case IBeXTGGModelPackage.TGG_RULE__SOURCE:
			setSource((IBeXRuleDelta) newValue);
			return;
		case IBeXTGGModelPackage.TGG_RULE__CORRESPONDENCE:
			setCorrespondence((IBeXRuleDelta) newValue);
			return;
		case IBeXTGGModelPackage.TGG_RULE__TARGET:
			setTarget((IBeXRuleDelta) newValue);
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
		case IBeXTGGModelPackage.TGG_RULE__NODES:
			getNodes().clear();
			return;
		case IBeXTGGModelPackage.TGG_RULE__EDGES:
			getEdges().clear();
			return;
		case IBeXTGGModelPackage.TGG_RULE__CORRESPONDENCE_NODES:
			getCorrespondenceNodes().clear();
			return;
		case IBeXTGGModelPackage.TGG_RULE__OPERATIONALISATIONS:
			getOperationalisations().clear();
			return;
		case IBeXTGGModelPackage.TGG_RULE__ATTRIBUTE_CONSTRAINTS:
			setAttributeConstraints((TGGAttributeConstraintSet) null);
			return;
		case IBeXTGGModelPackage.TGG_RULE__ABSTRACT:
			setAbstract(ABSTRACT_EDEFAULT);
			return;
		case IBeXTGGModelPackage.TGG_RULE__AXIOM:
			setAxiom(AXIOM_EDEFAULT);
			return;
		case IBeXTGGModelPackage.TGG_RULE__CONTEXT:
			setContext((IBeXRuleDelta) null);
			return;
		case IBeXTGGModelPackage.TGG_RULE__CONTEXT_SOURCE:
			setContextSource((IBeXRuleDelta) null);
			return;
		case IBeXTGGModelPackage.TGG_RULE__CONTEXT_CORRESPONDENCE:
			setContextCorrespondence((IBeXRuleDelta) null);
			return;
		case IBeXTGGModelPackage.TGG_RULE__CONTEXT_TARGET:
			setContextTarget((IBeXRuleDelta) null);
			return;
		case IBeXTGGModelPackage.TGG_RULE__CREATE_SOURCE_AND_TARGET:
			setCreateSourceAndTarget((IBeXRuleDelta) null);
			return;
		case IBeXTGGModelPackage.TGG_RULE__CREATE:
			setCreate((IBeXRuleDelta) null);
			return;
		case IBeXTGGModelPackage.TGG_RULE__CREATE_SOURCE:
			setCreateSource((IBeXRuleDelta) null);
			return;
		case IBeXTGGModelPackage.TGG_RULE__CREATE_CORRESPONDENCE:
			setCreateCorrespondence((IBeXRuleDelta) null);
			return;
		case IBeXTGGModelPackage.TGG_RULE__CREATE_TARGET:
			setCreateTarget((IBeXRuleDelta) null);
			return;
		case IBeXTGGModelPackage.TGG_RULE__SOURCE:
			setSource((IBeXRuleDelta) null);
			return;
		case IBeXTGGModelPackage.TGG_RULE__CORRESPONDENCE:
			setCorrespondence((IBeXRuleDelta) null);
			return;
		case IBeXTGGModelPackage.TGG_RULE__TARGET:
			setTarget((IBeXRuleDelta) null);
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
		case IBeXTGGModelPackage.TGG_RULE__NODES:
			return nodes != null && !nodes.isEmpty();
		case IBeXTGGModelPackage.TGG_RULE__EDGES:
			return edges != null && !edges.isEmpty();
		case IBeXTGGModelPackage.TGG_RULE__CORRESPONDENCE_NODES:
			return correspondenceNodes != null && !correspondenceNodes.isEmpty();
		case IBeXTGGModelPackage.TGG_RULE__OPERATIONALISATIONS:
			return operationalisations != null && !operationalisations.isEmpty();
		case IBeXTGGModelPackage.TGG_RULE__ATTRIBUTE_CONSTRAINTS:
			return attributeConstraints != null;
		case IBeXTGGModelPackage.TGG_RULE__ABSTRACT:
			return abstract_ != ABSTRACT_EDEFAULT;
		case IBeXTGGModelPackage.TGG_RULE__AXIOM:
			return axiom != AXIOM_EDEFAULT;
		case IBeXTGGModelPackage.TGG_RULE__CONTEXT:
			return context != null;
		case IBeXTGGModelPackage.TGG_RULE__CONTEXT_SOURCE:
			return contextSource != null;
		case IBeXTGGModelPackage.TGG_RULE__CONTEXT_CORRESPONDENCE:
			return contextCorrespondence != null;
		case IBeXTGGModelPackage.TGG_RULE__CONTEXT_TARGET:
			return contextTarget != null;
		case IBeXTGGModelPackage.TGG_RULE__CREATE_SOURCE_AND_TARGET:
			return createSourceAndTarget != null;
		case IBeXTGGModelPackage.TGG_RULE__CREATE:
			return create != null;
		case IBeXTGGModelPackage.TGG_RULE__CREATE_SOURCE:
			return createSource != null;
		case IBeXTGGModelPackage.TGG_RULE__CREATE_CORRESPONDENCE:
			return createCorrespondence != null;
		case IBeXTGGModelPackage.TGG_RULE__CREATE_TARGET:
			return createTarget != null;
		case IBeXTGGModelPackage.TGG_RULE__SOURCE:
			return source != null;
		case IBeXTGGModelPackage.TGG_RULE__CORRESPONDENCE:
			return correspondence != null;
		case IBeXTGGModelPackage.TGG_RULE__TARGET:
			return target != null;
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
		result.append(" (abstract: ");
		result.append(abstract_);
		result.append(", axiom: ");
		result.append(axiom);
		result.append(')');
		return result.toString();
	}

} //TGGRuleImpl
