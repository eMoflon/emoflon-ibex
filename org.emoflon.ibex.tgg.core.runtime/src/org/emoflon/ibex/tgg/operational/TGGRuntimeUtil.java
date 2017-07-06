package org.emoflon.ibex.tgg.operational;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.viatra.query.runtime.api.IPatternMatch;
import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraintContainer;
import org.emoflon.ibex.tgg.operational.csp.constraints.factories.RuntimeTGGAttrConstraintProvider;
import org.emoflon.ibex.tgg.operational.edge.RuntimeEdge;
import org.emoflon.ibex.tgg.operational.edge.RuntimeEdgeHashingStrategy;
import org.emoflon.ibex.tgg.operational.util.ManipulationUtil;

import gnu.trove.map.hash.THashMap;
import gnu.trove.set.hash.TCustomHashSet;
import gnu.trove.set.hash.THashSet;
import language.DomainType;
import language.TGG;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;
import language.basic.expressions.TGGAttributeExpression;
import runtime.RuntimePackage;
import runtime.TGGRuleApplication;

/**
 * 
 * The main transformation class which communicates with the pattern matcher and
 * processes its matches by applying rules. The public methods of this class are
 * invoked by the manipulation part of the pattern matcher.
 * 
 * This class is direction-agnostic and abstract in the following sense: The
 * decision of how to apply rules is left open (create only source, create only
 * target, or both, or none).
 * 
 * Match maintenance is delegated to MatchContainer Rule applications (creating
 * objects and setting their attributes) are delegated to ManipulationUtil
 * 
 * @author leblebici
 * 
 */
public class TGGRuntimeUtil {

	protected Resource srcR;
	protected Resource trgR;
	protected Resource corrR;
	protected Resource protocolR;

	protected RuleInfos ruleInfos;
	protected MatchContainer operationalMatchContainer;

	private RuntimeTGGAttrConstraintProvider runtimeConstraintProvider = new RuntimeTGGAttrConstraintProvider();

	private OperationStrategy strategy;
	private OperationMode mode;
	protected String currentPluginID=null;
	protected boolean isManipulated;
	protected TCustomHashSet<RuntimeEdge> markedEdges = new TCustomHashSet<>(new RuntimeEdgeHashingStrategy());
	protected THashMap<TGGRuleApplication, IPatternMatch> brokenRuleApplications = new THashMap<>();

	boolean domainCheckNecessary = false;
	
	public TGGRuntimeUtil(TGG tgg, Resource srcR, Resource corrR, Resource trgR, Resource protocolR, String pluginID) {
		ruleInfos = new RuleInfos(tgg);
		this.srcR = srcR;
		this.corrR = corrR;
		this.trgR = trgR;
		this.protocolR = protocolR;
		this.isManipulated = true;
		this.currentPluginID = pluginID;
		this.strategy = getStrategy();
		this.operationalMatchContainer = new MatchContainer();
		this.domainCheckNecessary = isDomainCheckNecessary(tgg);
	}

	public TGGRuntimeUtil(TGG tgg, Resource srcR, Resource corrR, Resource trgR, Resource protocolR) {
		ruleInfos = new RuleInfos(tgg);
		this.srcR = srcR;
		this.corrR = corrR;
		this.trgR = trgR;
		this.protocolR = protocolR;
		this.strategy = getStrategy();
		this.operationalMatchContainer = new MatchContainer();
		this.domainCheckNecessary = isDomainCheckNecessary(tgg);
		this.isManipulated = false;
	}
	
	public void run() {
		processBrokenMatches();
		processOperationalRuleMatches();
		finalize();
	}

	// methods for reacting to occurring matches of operational rules
	public void addOperationalRuleMatch(String ruleName, IPatternMatch match) {
		
		if(matchedNodesConformToTheirDomains(ruleName, match))
			operationalMatchContainer.addMatch(ruleName, match);
	}



	public void removeOperationalRuleMatch(IPatternMatch match) {
		operationalMatchContainer.removeMatch(match);
	}

	protected void processOperationalRuleMatches() {
		while (!operationalMatchContainer.isEmpty()) {
			IPatternMatch match = operationalMatchContainer.getNext();
			String ruleName = operationalMatchContainer.getRuleName(match);
			processOperationalRuleMatch(ruleName, match);
			removeOperationalRuleMatch(match);
		}
	}

	public boolean processOperationalRuleMatch(String ruleName, IPatternMatch match) {

		if (match.patternName().endsWith("FWD") && !markingSrc())
			return false;
		
		if (match.patternName().endsWith("BWD") && !markingTrg())
			return false;
		
		if (someElementsAlreadyProcessed(ruleName, match))
			return false;

		RuntimeTGGAttributeConstraintContainer cspContainer = new RuntimeTGGAttributeConstraintContainer(
				ruleInfos.getRuleCSPConstraintLibrary(ruleName), match, getMode(), runtimeConstraintProvider);
		if (!cspContainer.solve())
			return false;

		if (!conformTypesOfGreenNodes(match, ruleName))
			return false;

		// TODO: what if some context elements are not processed?
		// the match is then "pending" (and waiting for its context)
		if (!allContextElementsalreadyProcessed(match, ruleName))
			return false;

		/*
		 * this hash map complements the match to a comatch of an original
		 * triple rule application
		 */
		HashMap<String, EObject> comatch = new HashMap<>();

		if (manipulateSrc()) {
			ManipulationUtil.getInstance().createNonCorrNodes(match, comatch, ruleInfos.getGreenSrcNodes(ruleName), srcR, this.isManipulated, this.currentPluginID);
		}
		Collection<RuntimeEdge> srcEdges = ManipulationUtil.getInstance().createEdges(match, comatch,
				ruleInfos.getGreenSrcEdges(ruleName), manipulateSrc(), this.isManipulated, this.currentPluginID);

		if (manipulateTrg()) {
			ManipulationUtil.getInstance().createNonCorrNodes(match, comatch, ruleInfos.getGreenTrgNodes(ruleName), trgR, this.isManipulated, this.currentPluginID);
		}
		Collection<RuntimeEdge> trgEdges = ManipulationUtil.getInstance().createEdges(match, comatch,
				ruleInfos.getGreenTrgEdges(ruleName), manipulateTrg(), this.isManipulated, this.currentPluginID);

		Collection<Pair<TGGAttributeExpression, Object>> cspValues = cspContainer.getBoundAttributeExpValues();
		applyCSPValues(comatch, cspValues);

		ManipulationUtil.getInstance().createCorrs(match, comatch, ruleInfos.getGreenCorrNodes(ruleName), corrR, this.isManipulated, this.currentPluginID);

		markedEdges.addAll(srcEdges);
		markedEdges.addAll(trgEdges);

		if (protocol()) {
			prepareProtocol(ruleName, match, comatch);
		}

		return true;
	}

	protected void prepareProtocol(String ruleName, IPatternMatch match,
			HashMap<String, EObject> createdElements) {
		RuntimePackage runtimePackage = RuntimePackage.eINSTANCE;

		TGGRuleApplication ra = (TGGRuleApplication) EcoreUtil.create(runtimePackage.getTGGRuleApplication());
		protocolR.getContents().add(ra);

		ra.setName(ruleName);
		ra.setFinal(true);

		fillProtocolInfo(ruleInfos.getGreenSrcNodes(ruleName), ra, runtimePackage.getTGGRuleApplication_CreatedSrc(),
				createdElements, match);
		fillProtocolInfo(ruleInfos.getGreenTrgNodes(ruleName), ra, runtimePackage.getTGGRuleApplication_CreatedTrg(),
				createdElements, match);
		fillProtocolInfo(ruleInfos.getGreenCorrNodes(ruleName), ra, runtimePackage.getTGGRuleApplication_CreatedCorr(),
				createdElements, match);

		ra.getNodeMappings().putAll(createdElements);
		match.parameterNames().forEach(n -> {
			ra.getNodeMappings().put(n, (EObject) match.get(n));
		});
	}

	private void fillProtocolInfo(Collection<? extends TGGRuleElement> ruleInfos, TGGRuleApplication protocol,
			EStructuralFeature feature, HashMap<String, EObject> createdElements, IPatternMatch match) {
		ruleInfos.forEach(e -> {
			((EList) protocol.eGet(feature))
					.add(ManipulationUtil.getInstance().getVariableByName(e.getName(), createdElements, match));
		});
	}

	private boolean allContextElementsalreadyProcessed(IPatternMatch match, String ruleName) {
		
		if(getStrategy() == OperationStrategy.PROTOCOL_NACS){
			if (markingSrc()) {
				if (!allEdgesAlreadyProcessed(ruleInfos.getBlackSrcEdges(ruleName), match))
					return false;
			}

			if (markingTrg()) {
				if (!allEdgesAlreadyProcessed(ruleInfos.getBlackTrgEdges(ruleName), match))
					return false;
			}
		}
		return true;
	}

	protected boolean someElementsAlreadyProcessed(String ruleName, IPatternMatch match) {

		if(getStrategy() == OperationStrategy.PROTOCOL_NACS){
			if (markingSrc()) {
				if (someEdgesAlreadyProcessed(ruleInfos.getGreenSrcEdges(ruleName), match))
					return true;
			}

			if (markingTrg()) {
				if (someEdgesAlreadyProcessed(ruleInfos.getGreenTrgEdges(ruleName), match))
					return true;
			}
		}
		return false;
	}

	private boolean someEdgesAlreadyProcessed(Collection<TGGRuleEdge> specificationEdges, IPatternMatch match) {
		for (TGGRuleEdge edge : specificationEdges) {
			EObject src = (EObject) match.get(edge.getSrcNode().getName());
			EObject trg = (EObject) match.get(edge.getTrgNode().getName());
			EReference ref = edge.getType();
			if (markedEdges.contains(new RuntimeEdge(src, trg, ref)))
				return true;
		}
		return false;
	}

	private boolean allEdgesAlreadyProcessed(Collection<TGGRuleEdge> specificationEdges, IPatternMatch match) {
		for (TGGRuleEdge edge : specificationEdges) {
			EObject src = (EObject) match.get(edge.getSrcNode().getName());
			EObject trg = (EObject) match.get(edge.getTrgNode().getName());
			EReference ref = edge.getType();
			if (!markedEdges.contains(new RuntimeEdge(src, trg, ref)))
				return false;
		}
		return true;
	}

	protected void applyCSPValues(HashMap<String, EObject> comatch,
			Collection<Pair<TGGAttributeExpression, Object>> cspValues) {
		for (Pair<TGGAttributeExpression, Object> cspVal : cspValues) {
			EObject entry = comatch.get(cspVal.getLeft().getObjectVar().getName());
			if (entry != null) {
				entry.eSet(cspVal.getLeft().getAttribute(), cspVal.getRight());
			}
		}
	}

	protected boolean conformTypesOfGreenNodes(IPatternMatch match, String ruleName) {
		if (markingSrc()) {
			for (TGGRuleNode gsn : ruleInfos.getGreenSrcNodes(ruleName)) {
				if (gsn.getType() != ((EObject) match.get(gsn.getName())).eClass())
					return false;
			}
		}
		if (markingTrg()) {
			for (TGGRuleNode gtn : ruleInfos.getGreenTrgNodes(ruleName)) {
				if (gtn.getType() != ((EObject) match.get(gtn.getName())).eClass())
					return false;
			}
		}
		return true;
	}

	// methods for reacting to broken matches of src/trg patterns

	public void addBrokenMatch(IPatternMatch match) {
		TGGRuleApplication ra = (TGGRuleApplication) match.get("eMoflon_ProtocolNode");
		// does the broken match really belong to the rule application?
		if (isCompatibleWith(ra, match))
			brokenRuleApplications.put(ra, match);
	}

	private boolean isCompatibleWith(TGGRuleApplication ra, IPatternMatch match) {
		for (String name : match.parameterNames()) {
			if (!name.equals("eMoflon_ProtocolNode"))
				if (match.get(name) != ra.getNodeMappings().get(name))
					return false;
		}
		return true;
	}

	protected void processBrokenMatches() {
		while(!brokenRuleApplications.isEmpty()){
			THashSet<TGGRuleApplication> revoked = new THashSet<>();
			for(TGGRuleApplication ra : brokenRuleApplications.keySet()){
				revokeOperationalRule(ra, brokenRuleApplications.get(ra));
				revoked.add(ra);
			}
			for(TGGRuleApplication revokedRA : revoked)
				brokenRuleApplications.remove(revokedRA);
		}
	}

	protected void revokeOperationalRule(TGGRuleApplication ruleApplication, IPatternMatch match) {
		revokeEdges(ruleApplication, match);
		revokeNodes(ruleApplication);
		EcoreUtil.delete(ruleApplication);
	}

	private void revokeEdges(TGGRuleApplication ruleApplication, IPatternMatch match) {
		revokeEdges(ruleInfos.getGreenSrcEdges(ruleApplication.getName()), match, manipulateSrc());
		revokeEdges(ruleInfos.getGreenTrgEdges(ruleApplication.getName()), match, manipulateTrg());
	}

	private void revokeEdges(Collection<TGGRuleEdge> specificationEdges, IPatternMatch match,
			boolean delete) {
		specificationEdges.forEach(se -> {
			RuntimeEdge runtimeEdge = getRuntimeEdge(match, se);
			markedEdges.remove(runtimeEdge);
			if (delete)
				ManipulationUtil.getInstance().deleteEdge(runtimeEdge.getSrc(), runtimeEdge.getTrg(), runtimeEdge.getRef());
		});
	}

	protected void revokeNodes(TGGRuleApplication ra) {
		revokeNodes(ra.getCreatedSrc(), manipulateSrc());
		revokeNodes(ra.getCreatedTrg(), manipulateTrg());
		revokeNodes(ra.getCreatedCorr(), manipulateCorr());
	}

	private void revokeNodes(Collection<EObject> nodes, boolean delete) {
		if (delete)
			ManipulationUtil.getInstance().deleteNodes(new THashSet<>(nodes));
	}

	private RuntimeEdge getRuntimeEdge(IPatternMatch match, TGGRuleEdge specificationEdge) {
		EObject src = (EObject) match.get(specificationEdge.getSrcNode().getName());
		EObject trg = (EObject) match.get(specificationEdge.getTrgNode().getName());
		EReference ref = specificationEdge.getType();
		RuntimeEdge edge = new RuntimeEdge(src, trg, ref);
		return edge;
	}

	public RuntimeTGGAttrConstraintProvider getCSPProvider() {
		return runtimeConstraintProvider;
	}

	protected void finalize() {
	}

	public OperationMode getMode() {
		return mode;
	};

	public void setMode(OperationMode mode) {
		this.mode = mode;
	}

	protected boolean protocol() {
		return true;
	}

	protected boolean manipulateSrc() {
		return getMode() == OperationMode.BWD || getMode() == OperationMode.MODELGEN;
	}

	protected boolean manipulateTrg() {
		return getMode() == OperationMode.FWD || getMode() == OperationMode.MODELGEN;
	}

	protected boolean manipulateCorr() {
		return true;
	}

	protected boolean markingSrc() {
		return !manipulateSrc();
	}

	protected boolean markingTrg() {
		return !manipulateTrg();
	}

	protected boolean markingCorr() {
		return !manipulateCorr();
	}

	public OperationStrategy getStrategy() {
		return OperationStrategy.PROTOCOL_NACS;
	}
	
	private boolean isDomainCheckNecessary(TGG tgg) {
		HashSet<EPackage> intersectionOfSrcAndTrg = new HashSet<>(tgg.getSrc());
		intersectionOfSrcAndTrg.retainAll(tgg.getTrg());
		return intersectionOfSrcAndTrg.size() > 0;
	}
	
	private boolean matchedNodesConformToTheirDomains(String ruleName, IPatternMatch match) {
		if(!domainCheckNecessary)
			return true;
		for(String nodeName : match.parameterNames()){
			EObject node = (EObject) match.get(nodeName);
			DomainType domainOfTheNode = ruleInfos.getDomainType(ruleName, nodeName);
			if(domainOfTheNode == DomainType.SRC && node.eResource() != srcR)
				return false;
			if(domainOfTheNode == DomainType.TRG && node.eResource() != trgR)
				return false;
		}
		return true;
	}

}
