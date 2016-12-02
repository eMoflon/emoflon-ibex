package org.emoflon.ibex.tgg.operational;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.viatra.query.runtime.api.IPatternMatch;
import org.emoflon.ibex.tgg.operational.util.FromEdgeWrapperToEMFEdgeUtil;
import org.emoflon.ibex.tgg.operational.util.ManipulationUtil;

import language.BindingType;
import language.DomainType;
import language.TGG;
import language.TGGRule;
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;
import runtime.Edge;
import runtime.RuntimePackage;
import runtime.TGGRuleApplication;

/**
 * 
 * The main transformation class which communicates with the pattern matcher and processes its matches by applying rules.
 * The public methods of this class are invoked by the manipulation part of the pattern matcher.
 * 
 * This class is direction-agnostic and abstract in the following sense: 
 * The decision of how to apply rules is left open (create only source, create only target, or both, or none).
 * 
 * Match maintenance is delegated to MatchContainer
 * Rule applications (creating objects and setting their attributes) are delegated to ManipulationUtil
 * 
 * @author leblebici
 * 
 *  */
public abstract class TGGRuntimeUtil {

	/*
	 * these hash maps serve as rule info to indicate what variables are
	 * black/green & src/corr/trg in a rule
	 */
	protected HashMap<String, Collection<TGGRuleNode>> greenSrcNodes = new LinkedHashMap<>();
	protected HashMap<String, Collection<TGGRuleNode>> greenTrgNodes = new LinkedHashMap<>();
	protected HashMap<String, Collection<TGGRuleEdge>> greenSrcEdges = new LinkedHashMap<>();
	protected HashMap<String, Collection<TGGRuleEdge>> greenTrgEdges = new LinkedHashMap<>();
	protected HashMap<String, Collection<TGGRuleCorr>> greenCorrNodes = new LinkedHashMap<>();

	protected HashMap<String, Collection<TGGRuleElement>> blackSrcElements = new LinkedHashMap<>();
	protected HashMap<String, Collection<TGGRuleElement>> blackCorrElements = new LinkedHashMap<>();
	protected HashMap<String, Collection<TGGRuleElement>> blackTrgElements = new LinkedHashMap<>();

	protected Resource srcR;
	protected Resource trgR;
	protected Resource corrR;
	protected Resource protocolR;

	private RuntimePackage runtimePackage = RuntimePackage.eINSTANCE;

	protected ArrayList<Edge> createdEdges = new ArrayList<>();

	private OperationStrategy strategy;

	protected ArrayList<IPatternMatch> pendingEdgeWrapperMatches = new ArrayList<>();

	protected MatchContainer matchContainer;

	public TGGRuntimeUtil(TGG tgg, Resource srcR, Resource corrR, Resource trgR, Resource protocolR) {
		tgg.getRules().forEach(r -> prepareRuleInfo(r));
		this.srcR = srcR;
		this.corrR = corrR;
		this.trgR = trgR;
		this.protocolR = protocolR;
		this.strategy = getStrategy();
		this.matchContainer = new MatchContainer(tgg);
	}

	abstract public OperationMode getMode();

	abstract public OperationStrategy getStrategy();
	
	//methods for reacting to obsolete or missing edge wrapper matches 
	public void deleteEdge(IPatternMatch match) {
		Edge e = (Edge) match.get("e");
		EcoreUtil.delete(e);
		e.setSrc(null);
		e.setTrg(null);
	}

	public void addEdgeWrapperMatch(IPatternMatch match) {
		pendingEdgeWrapperMatches.add(match);
	}

	public void removeEdgeWrapperMatch(IPatternMatch match) {
		pendingEdgeWrapperMatches.remove(match);
	}
	
	//methods for reacting to occurring or broken matches of operational rules
	public void addOperationalRuleMatch(String ruleName, IPatternMatch match) {
		matchContainer.addMatch(ruleName, match);
	}

	public void removeOperationalRuleMatch(IPatternMatch match) {
		matchContainer.removeMatch(match);
	}

	public void revokeOperationalRule(IPatternMatch match) {
		revoke((TGGRuleApplication) match.get("eMoflon_ProtocolNode"));
	}

	protected void revoke(TGGRuleApplication ra) {

		EcoreUtil.delete(ra);

		if (manipulateSrc()) {
			ManipulationUtil.deleteElements(ra.getCreatedSrc());
		}
		if (manipulateTrg()) {
			ManipulationUtil.deleteElements(ra.getCreatedTrg());
		}
		ManipulationUtil.deleteElements(ra.getCreatedCorr());
	}

	//main method and its helpers processing pending matches for missing edge wrappers and applicable operational rules
	public void run() {
		processMissingEdgeWrapperMatches();
		processOperationalRuleMatches();
		finalize();
	}

	protected void processMissingEdgeWrapperMatches() {
		while (!pendingEdgeWrapperMatches.isEmpty()) {
			IPatternMatch match = pendingEdgeWrapperMatches.get(0);
			processMissingEdgeWrapperMatch(match);
			removeEdgeWrapperMatch(match);
		}
	}
	
	private void processMissingEdgeWrapperMatch(IPatternMatch match) {
		
		if (isObsolete(match))
			return;
	
		Edge newEdge = (Edge) EcoreUtil.create(runtimePackage.getEdge());
		protocolR.getContents().add(newEdge);
		newEdge.setSrc((EObject) match.get("s"));
		newEdge.setTrg((EObject) match.get("t"));

		String[] splitPatternName = match.patternName().split("_eMoflonEdgeWrapper_");
		String edgeName = splitPatternName[splitPatternName.length - 1];
		newEdge.setName(edgeName);
	}
	
	protected void processOperationalRuleMatches() {
		while (!matchContainer.isEmpty()) {
			IPatternMatch match = matchContainer.getNext();
			String ruleName = matchContainer.getRuleName(match);
			processOperationalRuleMatch(ruleName, match);
			removeOperationalRuleMatch(match);
		}
	}
	
	public void processOperationalRuleMatch(String ruleName, IPatternMatch match) {

		if (isObsolete(match))
			return;
		/*
		 * this hash map complements the match to a comatch of an original
		 * triple rule application
		 */
		HashMap<String, EObject> comatch = new HashMap<>();

		if (manipulateSrc()) {
			ManipulationUtil.createNonCorrNodes(match, comatch, greenSrcNodes.get(ruleName), srcR);
			createdEdges.addAll(ManipulationUtil.createEdges(match, comatch, greenSrcEdges.get(ruleName), protocolR));
		}

		if (manipulateTrg()) {
			ManipulationUtil.createNonCorrNodes(match, comatch, greenTrgNodes.get(ruleName), trgR);
			createdEdges.addAll(ManipulationUtil.createEdges(match, comatch, greenTrgEdges.get(ruleName), protocolR));
		}

		ManipulationUtil.createCorrs(match, comatch, greenCorrNodes.get(ruleName), corrR);

		if (protocol()) {
			prepareProtocol(ruleName, match, comatch);
		}
	}
	
	protected void finalize() {
		FromEdgeWrapperToEMFEdgeUtil.applyEdges(createdEdges);
	}

	private TGGRuleApplication prepareProtocol(String ruleName, IPatternMatch match,
			HashMap<String, EObject> createdElements) {

		TGGRuleApplication protocol = (TGGRuleApplication) EcoreUtil.create(runtimePackage.getTGGRuleApplication());
		protocolR.getContents().add(protocol);

		protocol.setName(ruleName);
		protocol.setFinal(strategy == OperationStrategy.PROTOCOL_NACS);

		fillProtocolInfo(blackSrcElements.get(ruleName), protocol, runtimePackage.getTGGRuleApplication_ContextSrc(),
				createdElements, match);
		fillProtocolInfo(blackTrgElements.get(ruleName), protocol, runtimePackage.getTGGRuleApplication_ContextTrg(),
				createdElements, match);
		fillProtocolInfo(blackCorrElements.get(ruleName), protocol, runtimePackage.getTGGRuleApplication_ContextCorr(),
				createdElements, match);

		fillProtocolInfo(greenSrcNodes.get(ruleName), protocol, runtimePackage.getTGGRuleApplication_CreatedSrc(),
				createdElements, match);
		fillProtocolInfo(greenSrcEdges.get(ruleName), protocol, runtimePackage.getTGGRuleApplication_CreatedSrc(),
				createdElements, match);

		fillProtocolInfo(greenTrgNodes.get(ruleName), protocol, runtimePackage.getTGGRuleApplication_CreatedTrg(),
				createdElements, match);
		fillProtocolInfo(greenTrgEdges.get(ruleName), protocol, runtimePackage.getTGGRuleApplication_CreatedTrg(),
				createdElements, match);

		fillProtocolInfo(greenCorrNodes.get(ruleName), protocol, runtimePackage.getTGGRuleApplication_CreatedCorr(),
				createdElements, match);
		return protocol;

	}

	private void fillProtocolInfo(Collection<? extends TGGRuleElement> ruleInfos, TGGRuleApplication protocol,
			EStructuralFeature feature, HashMap<String, EObject> createdElements, IPatternMatch match) {
		ruleInfos.forEach(e -> {
			((EList) protocol.eGet(feature)).add(ManipulationUtil.getVariableByName(e.getName(), createdElements, match));
		});
	}

	private void prepareRuleInfo(TGGRule r) {
		String ruleName = r.getName();
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

		blackSrcElements.put(ruleName,
				Stream.concat(r.getNodes().stream(), r.getEdges().stream())
						.filter(e -> e.getBindingType() == BindingType.CONTEXT && e.getDomainType() == DomainType.SRC)
						.collect(Collectors.toCollection(LinkedHashSet::new)));

		blackTrgElements.put(ruleName,
				Stream.concat(r.getNodes().stream(), r.getEdges().stream())
						.filter(e -> e.getBindingType() == BindingType.CONTEXT && e.getDomainType() == DomainType.TRG)
						.collect(Collectors.toCollection(LinkedHashSet::new)));

		blackCorrElements.put(ruleName,
				r.getNodes().stream()
						.filter(e -> e.getBindingType() == BindingType.CONTEXT && e.getDomainType() == DomainType.CORR)
						.collect(Collectors.toCollection(LinkedHashSet::new)));

	}

	/**
	 * As the pattern matcher might stop reporting broken matches (as objects do
	 * not live in the resource set anymore), this method checks whether the
	 * match contains dangling objects
	 */
	private boolean isObsolete(IPatternMatch match) {
		for (String parameterName : match.parameterNames()) {
			EObject eobject = (EObject) match.get(parameterName);
			if (eobject.eContainer() == null && eobject.eResource() == null)
				return true;
		}
		return false;

	}
	
	protected boolean protocol() {
		return true;
	}

	protected boolean manipulateTrg() {
		return getMode() == OperationMode.FWD || getMode() == OperationMode.MODELGEN;
	}

	protected boolean manipulateSrc() {
		return getMode() == OperationMode.BWD || getMode() == OperationMode.MODELGEN;
	}

}
