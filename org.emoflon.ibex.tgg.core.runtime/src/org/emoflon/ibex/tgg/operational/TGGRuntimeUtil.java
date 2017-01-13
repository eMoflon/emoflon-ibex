package org.emoflon.ibex.tgg.operational;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.viatra.query.runtime.api.IPatternMatch;
import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraintContainer;
import org.emoflon.ibex.tgg.operational.csp.constraints.factories.RuntimeTGGAttrConstraintFactory;
import org.emoflon.ibex.tgg.operational.csp.constraints.factories.RuntimeTGGAttrConstraintProvider;
import org.emoflon.ibex.tgg.operational.edge.RuntimeEdge;
import org.emoflon.ibex.tgg.operational.edge.RuntimeEdgeHashingStrategy;
import org.emoflon.ibex.tgg.operational.util.ManipulationUtil;

import gnu.trove.set.hash.TCustomHashSet;
import gnu.trove.set.hash.THashSet;
import language.BindingType;
import language.DomainType;
import language.TGG;
import language.TGGRule;
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;
import language.basic.expressions.TGGAttributeExpression;
import language.csp.TGGAttributeConstraintLibrary;
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
public abstract class TGGRuntimeUtil {

	/*
	 * these hash maps serve as rule info to indicate what variables are
	 * black/green & src/corr/trg in a rule
	 */
	protected HashMap<String, TGGAttributeConstraintLibrary> rule2constraintLibrary = new LinkedHashMap<>();

	protected HashMap<String, Collection<TGGRuleNode>> greenSrcNodes = new LinkedHashMap<>();
	protected HashMap<String, Collection<TGGRuleNode>> greenTrgNodes = new LinkedHashMap<>();
	protected HashMap<String, Collection<TGGRuleEdge>> greenSrcEdges = new LinkedHashMap<>();
	protected HashMap<String, Collection<TGGRuleEdge>> greenTrgEdges = new LinkedHashMap<>();
	protected HashMap<String, Collection<TGGRuleCorr>> greenCorrNodes = new LinkedHashMap<>();

	protected HashMap<String, Collection<TGGRuleNode>> blackSrcNodes = new LinkedHashMap<>();
	protected HashMap<String, Collection<TGGRuleNode>> blackTrgNodes = new LinkedHashMap<>();
	protected HashMap<String, Collection<TGGRuleEdge>> blackSrcEdges = new LinkedHashMap<>();
	protected HashMap<String, Collection<TGGRuleEdge>> blackTrgEdges = new LinkedHashMap<>();
	protected HashMap<String, Collection<TGGRuleNode>> blackCorrNodes = new LinkedHashMap<>();

	protected Resource srcR;
	protected Resource trgR;
	protected Resource corrR;
	protected Resource protocolR;

	private RuntimePackage runtimePackage = RuntimePackage.eINSTANCE;

	private RuntimeTGGAttrConstraintProvider runtimeConstraintProvider;

	private OperationStrategy strategy;

	protected MatchContainer matchContainer;

	protected THashSet<EObject> markedNodes = new THashSet<>();
	protected TCustomHashSet<RuntimeEdge> markedEdges = new TCustomHashSet<>(new RuntimeEdgeHashingStrategy());

	public TGGRuntimeUtil(TGG tgg, Resource srcR, Resource corrR, Resource trgR, Resource protocolR,
			RuntimeTGGAttrConstraintFactory userDefinedConstraintFactory) {
		tgg.getRules().forEach(r -> prepareRuleInfo(r));
		this.srcR = srcR;
		this.corrR = corrR;
		this.trgR = trgR;
		this.protocolR = protocolR;
		this.strategy = getStrategy();
		this.matchContainer = new MatchContainer(tgg);
		this.runtimeConstraintProvider = new RuntimeTGGAttrConstraintProvider();
		this.runtimeConstraintProvider.registerFactory(userDefinedConstraintFactory);
	}

	abstract public OperationMode getMode();

	abstract public OperationStrategy getStrategy();

	// methods for reacting to occurring or broken matches of operational rules
	public void addOperationalRuleMatch(String ruleName, IPatternMatch match) {
		matchContainer.addMatch(ruleName, match);
	}

	public void removeOperationalRuleMatch(IPatternMatch match) {
		matchContainer.removeMatch(match);
	}

	public void revokeOperationalRule(IPatternMatch match) {
		TGGRuleApplication protocol = (TGGRuleApplication) match.get("eMoflon_ProtocolNode");
		revokeEdges(match, protocol.getName());
		revokeNodes(protocol);
	}

	private void revokeEdges(IPatternMatch match, String ruleName) {
		revokeEdges(greenSrcEdges.get(ruleName), match, manipulateSrc());
		revokeEdges(greenTrgEdges.get(ruleName), match, manipulateTrg());
	}

	private void revokeEdges(Collection<TGGRuleEdge> specificationEdges, IPatternMatch match, boolean delete) {
		specificationEdges.forEach(se -> {
			RuntimeEdge runtimeEdge = getRuntimeEdge(match, se);
			markedEdges.remove(runtimeEdge);
			if(delete)
				ManipulationUtil.deleteEdge(runtimeEdge.getSrc(), runtimeEdge.getTrg(), runtimeEdge.getRef());		
		});
	}

	protected void revokeNodes(TGGRuleApplication ra) {
		EcoreUtil.delete(ra);
		revokeNodes(ra.getCreatedSrc(), manipulateSrc());
		revokeNodes(ra.getContextTrg(), manipulateTrg());
		revokeNodes(ra.getCreatedCorr(), manipulateCorr());
	}
	
	private void revokeNodes(Collection<EObject> nodes, boolean delete){
		markedNodes.removeAll(nodes);
		if(delete)
			ManipulationUtil.deleteNodes(nodes);
	}
	

	public void registerRuleApplication(IPatternMatch match) {
		registerProtocol((TGGRuleApplication) match.get("eMoflon_ProtocolNode"), match);
	}

	private void registerProtocol(TGGRuleApplication ra, IPatternMatch match) {
		if (markingSrc()) {
			registerMarkedEdges(greenSrcEdges.get(ra.getName()), match);
			registerMarkedNodes(greenSrcNodes.get(ra.getName()), match);
		}
		if (markingTrg()) {
			registerMarkedEdges(greenTrgEdges.get(ra.getName()), match);
			registerMarkedNodes(greenTrgNodes.get(ra.getName()), match);
		}
	}

	private void registerMarkedNodes(Collection<TGGRuleNode> specificationNodes, IPatternMatch match) {
		specificationNodes.forEach(gn -> markedNodes.add((EObject) match.get(gn.getName())));
	}

	private void registerMarkedEdges(Collection<TGGRuleEdge> specificationEdges, IPatternMatch match) {
		specificationEdges.forEach(ge -> {
			RuntimeEdge edge = getRuntimeEdge(match, ge);
			markedEdges.add(edge);
		});
	}

	private RuntimeEdge getRuntimeEdge(IPatternMatch match, TGGRuleEdge specificationEdge) {
		EObject src = (EObject) match.get(specificationEdge.getSrcNode().getName());
		EObject trg = (EObject) match.get(specificationEdge.getTrgNode().getName());
		EReference ref = specificationEdge.getType();
		RuntimeEdge edge = new RuntimeEdge(src, trg, ref);
		return edge;
	}

	// main method and its helpers processing pending matches for missing edge
	// wrappers and applicable operational rules
	public void run() {
		processOperationalRuleMatches();
		finalize();
	}

	protected void processOperationalRuleMatches() {
		while (!matchContainer.isEmpty()) {
			IPatternMatch match = matchContainer.getNext();
			String ruleName = matchContainer.getRuleName(match);
			processOperationalRuleMatch(ruleName, match);
			removeOperationalRuleMatch(match);
		}
	}

	public boolean processOperationalRuleMatch(String ruleName, IPatternMatch match) {

		if (someElementsAlreadyProcessed(ruleName, match))
			return false;

		RuntimeTGGAttributeConstraintContainer cspContainer = new RuntimeTGGAttributeConstraintContainer(
				rule2constraintLibrary.get(ruleName), match, getMode(), runtimeConstraintProvider);
		if (!cspContainer.solve())
			return false;

		if (!conformTypesOfGreenNodes(match, ruleName))
			return false;

		//TODO: what if some context elements are not processed?
		//the match is then "pending" (and waiting for its context)
		if (!allContextElementsalreadyProcessed(match, ruleName))
			return false;

		/*
		 * this hash map complements the match to a comatch of an original
		 * triple rule application
		 */
		HashMap<String, EObject> comatch = new HashMap<>();

		if (manipulateSrc()) {
			ManipulationUtil.createNonCorrNodes(match, comatch, greenSrcNodes.get(ruleName), srcR);
			ManipulationUtil.createEdges(match, comatch, greenSrcEdges.get(ruleName), protocolR);
		}

		if (manipulateTrg()) {
			ManipulationUtil.createNonCorrNodes(match, comatch, greenTrgNodes.get(ruleName), trgR);
			ManipulationUtil.createEdges(match, comatch, greenTrgEdges.get(ruleName), protocolR);
		}

		Collection<Pair<TGGAttributeExpression, Object>> cspValues = cspContainer.getBoundAttributeExpValues();
		applyCSPValues(comatch, cspValues);

		ManipulationUtil.createCorrs(match, comatch, greenCorrNodes.get(ruleName), corrR);

		if (protocol()) {
			prepareProtocol(ruleName, match, comatch);
		}

		return true;
	}

	private boolean allContextElementsalreadyProcessed(IPatternMatch match, String ruleName) {

		if(markingSrc()){
			if(!allNodesAlreadyProcessed(blackSrcNodes.get(ruleName), match))
				return false;
			if(!allEdgesAlreadyProcessed(blackSrcEdges.get(ruleName), match))
				return false;
		}
		
		if(markingTrg()){
			if(!allNodesAlreadyProcessed(blackTrgNodes.get(ruleName), match))
				return false;
			if(!allEdgesAlreadyProcessed(blackTrgEdges.get(ruleName), match))
				return false;
		}
		
		return true;
	}

	protected boolean someElementsAlreadyProcessed(String ruleName, IPatternMatch match) {

		if (markingSrc()) {
			if (someNodesAlreadyProcessed(greenSrcNodes.get(ruleName), match))
				return true;
			if (someEdgesAlreadyProcessed(greenSrcEdges.get(ruleName), match))
				return true;
		}

		if (markingTrg()) {
			if (someNodesAlreadyProcessed(greenTrgNodes.get(ruleName), match))
				return true;
			if (someEdgesAlreadyProcessed(greenTrgEdges.get(ruleName), match))
				return true;
		}
		return false;
	}

	private boolean someNodesAlreadyProcessed(Collection<TGGRuleNode> specificationNodes, IPatternMatch match) {
		for (TGGRuleNode node : specificationNodes)
			if (markedNodes.contains(match.get(node.getName())))
				return true;

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
	
	private boolean allNodesAlreadyProcessed(Collection<TGGRuleNode> specificationNodes, IPatternMatch match) {
		for (TGGRuleNode node : specificationNodes)
			if (!markedNodes.contains(match.get(node.getName())))
				return false;

		return true;
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

	private void applyCSPValues(HashMap<String, EObject> comatch,
			Collection<Pair<TGGAttributeExpression, Object>> cspValues) {
		for (Pair<TGGAttributeExpression, Object> cspVal : cspValues) {
			EObject entry = comatch.get(cspVal.getLeft().getObjectVar().getName());
			if (entry != null) {
				entry.eSet(cspVal.getLeft().getAttribute(), cspVal.getRight());
			}
		}
	}

	private boolean conformTypesOfGreenNodes(IPatternMatch match, String ruleName) {
		if (markingSrc()) {
			for (TGGRuleNode gsn : greenSrcNodes.get(ruleName)) {
				if (gsn.getType() != ((EObject) match.get(gsn.getName())).eClass())
					return false;
			}
		}
		if (markingTrg()) {
			for (TGGRuleNode gtn : greenTrgNodes.get(ruleName)) {
				if (gtn.getType() != ((EObject) match.get(gtn.getName())).eClass())
					return false;
			}
		}
		return true;
	}

	protected void finalize() {
	}

	private TGGRuleApplication prepareProtocol(String ruleName, IPatternMatch match,
			HashMap<String, EObject> createdElements) {

		TGGRuleApplication protocol = (TGGRuleApplication) EcoreUtil.create(runtimePackage.getTGGRuleApplication());
		protocolR.getContents().add(protocol);

		protocol.setName(ruleName);
		protocol.setFinal(strategy == OperationStrategy.PROTOCOL_NACS);

		fillProtocolInfo(blackSrcNodes.get(ruleName), protocol, runtimePackage.getTGGRuleApplication_ContextSrc(),
				createdElements, match);
		fillProtocolInfo(blackTrgNodes.get(ruleName), protocol, runtimePackage.getTGGRuleApplication_ContextTrg(),
				createdElements, match);
		fillProtocolInfo(blackCorrNodes.get(ruleName), protocol, runtimePackage.getTGGRuleApplication_ContextCorr(),
				createdElements, match);
		fillProtocolInfo(greenSrcNodes.get(ruleName), protocol, runtimePackage.getTGGRuleApplication_CreatedSrc(),
				createdElements, match);
		fillProtocolInfo(greenTrgNodes.get(ruleName), protocol, runtimePackage.getTGGRuleApplication_CreatedTrg(),
				createdElements, match);
		fillProtocolInfo(greenCorrNodes.get(ruleName), protocol, runtimePackage.getTGGRuleApplication_CreatedCorr(),
				createdElements, match);
		return protocol;

	}

	private void fillProtocolInfo(Collection<? extends TGGRuleElement> ruleInfos, TGGRuleApplication protocol,
			EStructuralFeature feature, HashMap<String, EObject> createdElements, IPatternMatch match) {
		ruleInfos.forEach(e -> {
			((EList) protocol.eGet(feature))
					.add(ManipulationUtil.getVariableByName(e.getName(), createdElements, match));
		});
	}

	private void prepareRuleInfo(TGGRule r) {
		String ruleName = r.getName();
		rule2constraintLibrary.put(r.getName(), r.getAttributeConditionLibrary());

		greenSrcNodes.put(ruleName,
				r.getNodes().stream()
						.filter(n -> n.getBindingType() == BindingType.CREATE && n.getDomainType() == DomainType.SRC)
						.collect(Collectors.toCollection(LinkedHashSet::new)));
		greenTrgNodes.put(ruleName,
				r.getNodes().stream()
						.filter(n -> n.getBindingType() == BindingType.CREATE && n.getDomainType() == DomainType.TRG)
						.collect(Collectors.toCollection(LinkedHashSet::new)));
		greenCorrNodes.put(ruleName,
				r.getNodes().stream()
						.filter(n -> n.getBindingType() == BindingType.CREATE && n.getDomainType() == DomainType.CORR)
						.map(n -> (TGGRuleCorr) n).collect(Collectors.toCollection(LinkedHashSet::new)));

		greenSrcEdges.put(ruleName,
				r.getEdges().stream()
						.filter(e -> e.getBindingType() == BindingType.CREATE && e.getDomainType() == DomainType.SRC)
						.collect(Collectors.toCollection(LinkedHashSet::new)));

		greenTrgEdges.put(ruleName,
				r.getEdges().stream()
						.filter(e -> e.getBindingType() == BindingType.CREATE && e.getDomainType() == DomainType.TRG)
						.collect(Collectors.toCollection(LinkedHashSet::new)));

		blackSrcNodes.put(ruleName,
				r.getNodes().stream()
						.filter(e -> e.getBindingType() == BindingType.CONTEXT && e.getDomainType() == DomainType.SRC)
						.collect(Collectors.toCollection(LinkedHashSet::new)));

		blackSrcEdges.put(ruleName,
				r.getEdges().stream()
						.filter(e -> e.getBindingType() == BindingType.CONTEXT && e.getDomainType() == DomainType.SRC)
						.collect(Collectors.toCollection(LinkedHashSet::new)));

		blackTrgNodes.put(ruleName,
				r.getNodes().stream()
						.filter(e -> e.getBindingType() == BindingType.CONTEXT && e.getDomainType() == DomainType.TRG)
						.collect(Collectors.toCollection(LinkedHashSet::new)));

		blackTrgEdges.put(ruleName,
				r.getEdges().stream()
						.filter(e -> e.getBindingType() == BindingType.CONTEXT && e.getDomainType() == DomainType.TRG)
						.collect(Collectors.toCollection(LinkedHashSet::new)));

		blackCorrNodes.put(ruleName,
				r.getNodes().stream()
						.filter(e -> e.getBindingType() == BindingType.CONTEXT && e.getDomainType() == DomainType.CORR)
						.collect(Collectors.toCollection(LinkedHashSet::new)));

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

}
