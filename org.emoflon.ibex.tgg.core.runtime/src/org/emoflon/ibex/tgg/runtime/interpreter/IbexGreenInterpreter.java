package org.emoflon.ibex.tgg.runtime.interpreter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeAssignment;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXBooleanValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEnumValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXOperationType;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRuleDelta;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXStringValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.DoubleLiteral;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.IntegerLiteral;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.common.emf.EMFManipulationUtils;
import org.emoflon.ibex.tgg.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
import org.emoflon.ibex.tgg.runtime.csp.IRuntimeTGGAttrConstrContainer;
import org.emoflon.ibex.tgg.runtime.csp.RuntimeTGGAttributeConstraintContainer;
import org.emoflon.ibex.tgg.runtime.csp.sorting.SearchPlanAction;
import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.util.SCMatch;
import org.emoflon.ibex.tgg.runtime.strategies.OperationalStrategy;
import org.emoflon.ibex.tgg.runtime.strategies.modules.RuleHandler;
import org.emoflon.ibex.tgg.runtime.strategies.modules.TGGResourceHandler;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.OperationalisationMode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGCorrespondence;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGEdge;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGPattern;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraint;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintParameterValue;
import org.emoflon.ibex.tgg.util.TGGModelUtils;
import org.emoflon.ibex.tgg.util.debug.LoggerConfig;

/**
 * Util class for creating EObjects, Edges, and Correspondences for a given set
 * of green TGGRuleElement
 * 
 * @author leblebici, lfritsche
 */
public class IbexGreenInterpreter implements IGreenInterpreter {
	private static final Logger logger = Logger.getLogger(IbexGreenInterpreter.class);

	/**
	 * Number of created source & target nodes
	 */
	private int numOfCreatedNodes = 0;
	private int numOfCreatedCorrNodes = 0;
	private IbexOptions options;
	private RuleHandler ruleHandler;
	
	private Map<IBeXNode, EReference> node2reference = new HashMap<>();

	private TGGResourceHandler resourceHandler;
	
	protected Map<String, List<TGGAttributeConstraint>> rule2sortedAttributeConstraints = new HashMap<>();
	protected Map<String, List<TGGAttributeConstraintParameterValue>> rule2parameters = new HashMap<>();

	public IbexGreenInterpreter(OperationalStrategy operationalStrategy) {
		options = operationalStrategy.getOptions();
		resourceHandler = options.resourceHandler();
		ruleHandler = options.tgg.ruleHandler();
		initializeSortedConstraints();
	}

	private void initializeSortedConstraints() {
		for(var rule : options.tgg.flattenedTGG().getRuleSet().getRules()) {
			for(var operationalRule : rule.getOperationalisations()) {
				registerOperationalRule(operationalRule);
			}
		}
	}

	public void registerOperationalRule(TGGOperationalRule operationalRule) {
		try {
			rule2sortedAttributeConstraints.put(operationalRule.getName(), //
					sortConstraints(
							operationalRule.getName(), //
							operationalRule.getAttributeConstraints().getParameters(), //
							operationalRule.getAttributeConstraints().getTggAttributeConstraints()));
//							((TGGPattern) operationalRule.getPrecondition()).getAttributeConstraints().getParameters(), //
//							((TGGPattern) operationalRule.getPrecondition()).getAttributeConstraints().getTggAttributeConstraints()));
			rule2parameters.put(operationalRule.getName(), operationalRule.getAttributeConstraints().getParameters());
		} catch (Exception e) {
			throw new IllegalStateException("Unable to sort attribute constraints, " + e.getMessage(), e);
		}
	}

	public void createNonCorrNode(ITGGMatch comatch, TGGNode node, Resource nodeResource) {
		comatch.put(node.getName(), createNode(comatch, node));
		numOfCreatedNodes++;
	}

	public Collection<EMFEdge> createEdges(ITGGMatch comatch, Collection<IBeXEdge> greenEdges, boolean createEMFEdge) {
		Collection<EMFEdge> result = new ArrayList<>();
		for (IBeXEdge e : greenEdges) {
			EObject src = (EObject) comatch.get(e.getSource().getName());
			EObject trg = (EObject) comatch.get(e.getTarget().getName());
			if (createEMFEdge) {
				EMFManipulationUtils.createEdge(src, trg, e.getType());
			}
			
			// create actual edge
			var newEdge = new EMFEdge(src, trg, e.getType());

			// add corr caching in case that this is a correspondence edge
			if(e.getSource() instanceof TGGCorrespondence) 
				resourceHandler.addCorrCachingEdge(newEdge);
			
			result.add(newEdge);
		}

		comatch.getCreatedEdges().addAll(result);

		return result;
	}

	public void createCorr(ITGGMatch comatch, TGGCorrespondence correspondence, Resource correspondenceResource) {
		EObject createCorr = createCorr(comatch, correspondence, comatch.get(correspondence.getSource().getName()), comatch.get(correspondence.getTarget().getName()));
		resourceHandler.addCorrCachingNode(createCorr);
		comatch.put(correspondence.getName(), createCorr);
	}

	private EObject createNode(ITGGMatch match, TGGNode node) {
		EObject newObj = EcoreUtil.create(node.getType());

		applyInPlaceAttributeAssignments(match, node, newObj);
		applyAttributeAssignments(match, node, newObj);

		return newObj;
	}

	private final static Optional<Pair<String, String>> getNodeAndAttrFromVarName(String varName) {
		String[] node_attr = varName.split("__");

		if (node_attr.length != 3)
			return Optional.empty();

		return Optional.of(Pair.of(node_attr[1], node_attr[2]));
	}

	private void applyAttributeAssignments(ITGGMatch match, TGGNode node, EObject newObj) {
		Collection<String> attributeNames = match.getParameterNames().stream() //
				.filter(pname -> {
					Optional<Pair<String, String>> o = getNodeAndAttrFromVarName(pname);
					Optional<Boolean> check = o.map(node_attr -> node_attr.getLeft().equals(node.getName()));
					return check.orElse(false);
				}).collect(Collectors.toList());

		for (String node_attr : attributeNames) {
			Object attributeValue = match.get(node_attr);
			Pair<String, String> node_attr_pair = getNodeAndAttrFromVarName(node_attr)
					.orElseThrow(() -> new IllegalStateException("Missing attribute value"));
			String attributeName = node_attr_pair.getRight();

			EStructuralFeature feature = node.getType().getEStructuralFeature(attributeName);
			newObj.eSet(feature, attributeValue);
		}
	}

	public void applyInPlaceAttributeAssignments(ITGGMatch match, TGGNode node, EObject newObj) {
		for (IBeXAttributeAssignment attributeAssignment : node.getAttributeAssignments()) {
			// Literal Values
			if (attributeAssignment.getValue() instanceof IBeXStringValue stringValue) {
				newObj.eSet(attributeAssignment.getAttribute(), stringValue.getValue());
				continue;
			}
			if (attributeAssignment.getValue() instanceof IBeXBooleanValue boolValue) {
				newObj.eSet(attributeAssignment.getAttribute(), boolValue.isValue());
				continue;
			}
			if (attributeAssignment.getValue() instanceof DoubleLiteral doubleLiteral) {
				newObj.eSet(attributeAssignment.getAttribute(), doubleLiteral.getValue());
				continue;
			}
			if (attributeAssignment.getValue() instanceof IntegerLiteral integerLiteral) {
				newObj.eSet(attributeAssignment.getAttribute(), integerLiteral.getValue());
				continue;
			}
			
			// enums and attributes
			if (attributeAssignment.getValue() instanceof IBeXEnumValue tee) {
				newObj.eSet(attributeAssignment.getAttribute(), tee.getLiteral().getInstance());
				continue;
			}
			if (attributeAssignment.getValue() instanceof IBeXAttributeValue tae) {
				EObject obj = (EObject) match.get(tae.getNode().getName());
				newObj.eSet(attributeAssignment.getAttribute(), obj.eGet(tae.getAttribute()));
				continue;
			}
		}
	}

	private void handlePlacementInResource(TGGNode node, Resource resource, EObject newObj) {
		try {
			if(newObj.eContainer() == null)
				resource.getContents().add(newObj);
		} catch (Exception e) {
			logger.warn("I had problems placing " + newObj + " in a resource: " + e);
		}
	}

	private EObject createCorr(ITGGMatch comatch, TGGNode node, Object src, Object trg) {
		EObject corr = createNode(comatch, node);
		corr.eSet(corr.eClass().getEStructuralFeature("source"), src);
		corr.eSet(corr.eClass().getEStructuralFeature("target"), trg);
		numOfCreatedCorrNodes++;
		return corr;
	}

	@Override
	public Optional<ITGGMatch> apply(TGGOperationalRule operationRule, ITGGMatch match) {
		// Check if match is valid
		// TODO lfritsche, amoeller: this can maybe make problems? here the problem is that we create before deleting 
		if (matchIsInvalid(operationRule, match) && !(options.repair.disableInjectivity() && match instanceof SCMatch)) {
			LoggerConfig.log(LoggerConfig.log_ruleApplication(), () -> "Blocking application as match is invalid.");
			return Optional.empty();
		}

		// Check if all attribute values provided match are as expected
		IRuntimeTGGAttrConstrContainer cspContainer = getAttributeConstraintContainer(match);
		if (!cspContainer.solve()) {
			LoggerConfig.log(LoggerConfig.log_ruleApplication(), () -> "Blocking application as attribute conditions don't hold.");
			return Optional.empty();
		}

		ITGGMatch comatch = match.copy();
		IBeXRuleDelta createDelta = operationRule.getCreate();
		
		for(var createdNode : createDelta.getNodes()) {
			if(createdNode instanceof TGGCorrespondence correspondence)
				createCorr(comatch, correspondence, resourceHandler.getCorrResource());
			else if(createdNode instanceof TGGNode node) {
				if(node.getDomainType() == DomainType.SOURCE)
					createNonCorrNode(comatch, node, resourceHandler.getSourceResource());
				else 
					createNonCorrNode(comatch, node, resourceHandler.getTargetResource());
			}
		}
		
		cspContainer.applyCSPValues(comatch);

		createEdges(comatch, operationRule.getCreation().getEdges(), true);
		
		for(var greenElement : createDelta.getNodes()) {
			handlePlacementInResource((TGGNode) greenElement, resourceHandler.getSourceResource(), (EObject) comatch.get(greenElement.getName()));
		}

		return Optional.of(comatch);
	}
	
	public IRuntimeTGGAttrConstrContainer getAttributeConstraintContainer(ITGGMatch match) {
		return new RuntimeTGGAttributeConstraintContainer(
				rule2parameters.get(match.getRuleName()), 
				rule2sortedAttributeConstraints.get(match.getRuleName()),
				match,
				options.csp.constraintProvider());
	}

	protected List<TGGAttributeConstraint> sortConstraints(String ruleName, List<TGGAttributeConstraintParameterValue> variables, List<TGGAttributeConstraint> constraints) {
		TGGOperationalRule operationalRule = ruleHandler.getOperationalRule(ruleName);
		SearchPlanAction spa = new SearchPlanAction(variables, constraints, operationalRule.getOperationalisationMode() == OperationalisationMode.GENERATE,  operationalRule.getCreateSourceAndTarget().getNodes());
		return spa.sortConstraints();
	}
	
	private boolean matchIsInvalid(TGGOperationalRule operationalRule, ITGGMatch match) {
		return violatesConformTypesOfGreenNodes(match, operationalRule)
				|| violatesUpperBounds(operationalRule, match)
				|| violatesContainerSemantics(operationalRule, match)
				|| createsDoubleEdge(operationalRule, match)
				|| createsCyclicContainment(operationalRule, match);
	}

	private boolean createsCyclicContainment(TGGOperationalRule operationalRule, ITGGMatch match) {
		for (IBeXEdge edge : operationalRule.getCreate().getEdges()) {
			TGGEdge tggEdge = (TGGEdge) edge;

			// we only search for cyclic containments on the source and target side
			if(tggEdge.getDomainType() == DomainType.CORRESPONDENCE)
				continue;
			
			if (canCreateCyclicContainment(operationalRule, tggEdge)) {
				EObject src = (EObject) match.get(tggEdge.getSource().getName());
				EObject trg = (EObject) match.get(tggEdge.getTarget().getName());

				Iterator<?> itr = trg.eAllContents();
				while (itr.hasNext()) {
					if (itr.next().equals(src))
						return true;
				}
			}
		}

		return false;
	}

	private boolean canCreateCyclicContainment(TGGOperationalRule operationRule, TGGEdge edge) {
		return isBlackNode(edge.getSource()) && isBlackNode(edge.getTarget())
				&& edge.getType().isContainment();
	}

	private boolean createsDoubleEdge(TGGOperationalRule operationRule, ITGGMatch match) {
		for (IBeXEdge edge : operationRule.getCreate().getEdges()) {
			if (canCreateDoubleEdge(edge)) {
				EObject src = (EObject) match.get(edge.getSource().getName());
				EObject trg = (EObject) match.get(edge.getTarget().getName());

				EReference ref = edge.getType();

				if (ref.isMany()) {
					Collection<?> objects = (Collection<?>) src.eGet(ref);
					if (objects.contains(trg))
						return true;
				} else {
					if (trg.equals(src.eGet(ref)))
						return true;
				}
			}
		}

		return false;
	}

	private boolean canCreateDoubleEdge(IBeXEdge edge) {
		return isBlackNode(edge.getSource()) && isBlackNode(edge.getTarget());
	}

	private boolean isBlackNode(IBeXNode srcNode) {
		return srcNode.getOperationType() == IBeXOperationType.CONTEXT;
	}

	private boolean violatesContainerSemantics(TGGOperationalRule operationalRule, ITGGMatch match) {
		// TODO larsF, adrianM: fix für short cut framework
		// GreenSCPattern do not need this check since it is allowed in order to repair a model
//		if (greenPattern instanceof GreenSCPattern)
//			return false;

		for (IBeXEdge greenEdge : operationalRule.getCreate().getEdges()) {
			if (violationOfContainerSemanticsIsPossible(operationalRule, greenEdge)) {
				EObject trgObj = (EObject) match.get(greenEdge.getTarget().getName());
				if (trgObj.eContainer() != null)
					return true;
			}
		}

		return false;
	}

	private boolean violationOfContainerSemanticsIsPossible(TGGOperationalRule operationalRule, IBeXEdge greenEdge) {
		return greenEdge.getType().isContainment() && isBlackNode(greenEdge.getTarget());
	}

	private boolean violatesUpperBounds(TGGOperationalRule operationalRule, ITGGMatch match) {
		for (IBeXEdge greenEdge : operationalRule.getCreate().getEdges()) {
			if (violationIsPossible(greenEdge)) {
				if (violatesUpperBounds(greenEdge, match, operationalRule))
					return true;
			}
		}

		return false;
	}

	/**
	 * A violation is only possible if the upper bound of the multiplicity is not *,
	 * and if the source node already exists.
	 * 
	 * @param greenPattern
	 * @param greenEdge
	 * @return
	 */
	private boolean violationIsPossible(IBeXEdge greenEdge) {
		return greenEdge.getType().getUpperBound() != -1
				&& isBlackNode(greenEdge.getSource());
	}

	private boolean violatesUpperBounds(IBeXEdge greenEdge, ITGGMatch match, TGGOperationalRule operationalRule) {
		EObject matchedSrcNode = (EObject) match.get(greenEdge.getSource().getName());
		int upperBound = greenEdge.getType().getUpperBound();

		if (greenEdge.getType().isMany()) {
			Collection<?> existingObjects = (Collection<?>) matchedSrcNode.eGet(greenEdge.getType());
			return existingObjects.size() + edgesOfThisTypeCreatedByRule(greenEdge.getSource(), greenEdge.getType(), operationalRule) > upperBound;
		} else {
			assert (upperBound == 1);
			return matchedSrcNode.eGet(greenEdge.getType()) != null;
		}
	}

	private long edgesOfThisTypeCreatedByRule(IBeXNode srcOfEdge, EReference ref, TGGOperationalRule operationalRule) {
		return operationalRule.getCreate().getEdges().stream()//
				.filter(e -> e.getSource().equals(srcOfEdge))//
				.filter(e -> e.getType().equals(ref))//
				.count();
	}

	/**
	 * Default pattern matching respects Java inheritance and this is usually
	 * desirable behaviour. For TGGs, however, operations should only match for
	 * context elements of the exact same type in the original TGG rule. To avoid
	 * violating correctness, therefore, we have to ensure with this method that
	 * this condition holds for every match.
	 * 
	 * @param match
	 * @param greenPattern
	 * @param ruleName
	 * @return
	 */
	protected boolean violatesConformTypesOfGreenNodes(ITGGMatch match, TGGOperationalRule operationalRule) {
		for (IBeXNode markedNode : operationalRule.getToBeMarked().getNodes()) {
			if (markedNode.getType() != ((EObject) match.get(markedNode.getName())).eClass())
				return true;
		}

		return false;
	}

	@Override
	public int getNumOfCreatedNodes() {
		return numOfCreatedNodes;
	}

	@Override
	public int getNumOfCreatedCorrNodes() {
		return numOfCreatedCorrNodes;
	}
	
	@Override
	public void createMarkers(String ruleName, ITGGMatch match) {
		EPackage corrPackage = options.tgg.corrMetamodel();
		EClass type = (EClass) corrPackage.getEClassifier(TGGModelUtils.getMarkerTypeName(ruleName));
		
		EObject ra = EcoreUtil.create(type);
		TGGRule operationalRule = ruleHandler.getRule(ruleName);
		
		for (IBeXNode n : operationalRule.getCreateSource().getNodes()) {
			EReference ref;
			if(node2reference.containsKey(n))
				ref = node2reference.get(n);
			else {
				String refName = TGGModelUtils.getMarkerRefName(BindingType.CREATE, DomainType.SOURCE, n.getName());
				ref = (EReference) type.getEStructuralFeature(refName);		
				node2reference.put(n, ref);
			}
			ra.eSet(ref, (EObject) match.get(n.getName()));			
		}
		
		for (IBeXNode n : operationalRule.getContextSource().getNodes()) {
			EReference ref;
			if(node2reference.containsKey(n))
				ref = node2reference.get(n);
			else {
				String refName = TGGModelUtils.getMarkerRefName(BindingType.CONTEXT, DomainType.SOURCE, n.getName());
				ref = (EReference) type.getEStructuralFeature(refName);		
				node2reference.put(n, ref);
			}		
			ra.eSet(ref, (EObject) match.get(n.getName()));			
		}

		for (IBeXNode n : operationalRule.getCreateTarget().getNodes()) {
			EReference ref;
			if(node2reference.containsKey(n))
				ref = node2reference.get(n);
			else {
				String refName = TGGModelUtils.getMarkerRefName(BindingType.CREATE, DomainType.TARGET, n.getName());
				ref = (EReference) type.getEStructuralFeature(refName);		
				node2reference.put(n, ref);
			}		
			ra.eSet(ref, (EObject) match.get(n.getName()));			
		}
		
		for (IBeXNode n : operationalRule.getContextTarget().getNodes()) {
			EReference ref;
			if(node2reference.containsKey(n))
				ref = node2reference.get(n);
			else {
				String refName = TGGModelUtils.getMarkerRefName(BindingType.CONTEXT, DomainType.TARGET, n.getName());
				ref = (EReference) type.getEStructuralFeature(refName);		
				node2reference.put(n, ref);
			}		
			ra.eSet(ref, (EObject) match.get(n.getName()));			
		}

		for (IBeXNode n : operationalRule.getCreateCorrespondence().getNodes()) {
			EReference ref;
			if(node2reference.containsKey(n))
				ref = node2reference.get(n);
			else {
				String refName = TGGModelUtils.getMarkerRefName(BindingType.CREATE, DomainType.CORRESPONDENCE, n.getName());
				ref = (EReference) type.getEStructuralFeature(refName);		
				node2reference.put(n, ref);
			}		
			ra.eSet(ref, (EObject) match.get(n.getName()));			
		}
		
		for (IBeXNode n : operationalRule.getContextCorrespondence().getNodes()) {
			EReference ref;
			if(node2reference.containsKey(n))
				ref = node2reference.get(n);
			else {
				String refName = TGGModelUtils.getMarkerRefName(BindingType.CONTEXT, DomainType.CORRESPONDENCE, n.getName());
				ref = (EReference) type.getEStructuralFeature(refName);		
				node2reference.put(n, ref);
			}	
			ra.eSet(ref, (EObject) match.get(n.getName()));			
		}
		
		resourceHandler.getProtocolResource().getContents().add(ra);
		match.put(TGGPatternUtil.getProtocolNodeName(ruleName), ra);
	}
}