package org.emoflon.ibex.tgg.operational.strategies.sync;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;
import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.getNodes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.common.collections.CollectionFactory;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.operational.IRedInterpreter;
import org.emoflon.ibex.tgg.operational.csp.IRuntimeTGGAttrConstrContainer;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.defaults.IbexRedInterpreter;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.matches.IMatchContainer;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPatternFactory;
import org.emoflon.ibex.tgg.operational.repair.strategies.ShortcutRepairStrategy;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;
import org.emoflon.ibex.tgg.operational.strategies.sync.repair.AbstractRepairStrategy;
import org.emoflon.ibex.tgg.operational.strategies.sync.repair.strategies.AttributeRepairStrategy;
import org.emoflon.ibex.tgg.util.MAUtil;

import language.BindingType;
import language.DomainType;
import language.TGGComplementRule;
import language.TGGRuleEdge;
import runtime.TGGRuleApplication;

public abstract class SYNC extends OperationalStrategy {

	// Multi-Amalgamation
	protected HashMap<TGGRuleApplication, Integer> protocolNodeToID = new HashMap<TGGRuleApplication, Integer>();
	protected HashMap<EObject, Integer> nodeToProtocolID = new HashMap<>();
	protected int idCounter = 0;

	// Repair
	protected Collection<AbstractRepairStrategy> repairStrategies = new ArrayList<>();
	protected Map<TGGRuleApplication, IMatch> brokenRuleApplications = CollectionFactory.cfactory
			.createObjectToObjectHashMap();
	protected IRedInterpreter redInterpreter;

	// Forward or backward sync
	protected SYNC_Strategy strategy;

	// All translated elements
	private Collection<Object> translated = cfactory.createObjectSet();
	private Map<IMatch, Collection<Object>> consistencyToTranslated = cfactory.createObjectToObjectHashMap();

	// Pending elements that are to be deleted but have not yet
	private Collection<Object> pending = cfactory.createObjectSet();

	/***** Constructors *****/

	public SYNC(IbexOptions options) throws IOException {
		super(options);
		redInterpreter = new IbexRedInterpreter(this);
	}

	public void registerRedInterpeter(IRedInterpreter redInterpreter) {
		this.redInterpreter = redInterpreter;
	}

	/***** Resource management *****/

	@Override
	public void saveModels() throws IOException {
		s.save(null);
		t.save(null);
		c.save(null);
		p.save(null);
	}

	@Override
	public void loadModels() throws IOException {
		long tic = System.currentTimeMillis();
		s = loadResource(options.projectPath() + "/instances/src.xmi");
		t = loadResource(options.projectPath() + "/instances/trg.xmi");
		c = loadResource(options.projectPath() + "/instances/corr.xmi");
		p = loadResource(options.projectPath() + "/instances/protocol.xmi");
		EcoreUtil.resolveAll(rs);
		long toc = System.currentTimeMillis();

		logger.info("Loaded all models in: " + (toc - tic) / 1000.0 + "s");
	}

	/***** Sync algorithm *****/

	@Override
	public void run() throws IOException {
		repair();
		rollBack();
		translate();
		logCreatedAndDeletedNumbers();
	}

	protected void repair() {
		initializeRepairStrategy(options);

		// TODO loop this together with roll back
		translate();
		repairBrokenMatches();
	}

	protected void initializeRepairStrategy(IbexOptions options) {
		if(!repairStrategies.isEmpty())
			return;
		
		if (options.repairUsingShortcutRules()) {
			repairStrategies.add(new ShortcutRepairStrategy(this));
		}
		if (options.repairAttributes()) {
			repairStrategies.add(new AttributeRepairStrategy(this));
		}
	}

	protected boolean repairBrokenMatches() {
		Collection<IMatch> alreadyProcessed = cfactory.createObjectSet();
		for(AbstractRepairStrategy rStrategy : repairStrategies) {
			for (IMatch repairCandidate : rStrategy.chooseMatches(brokenRuleApplications)) {
				if(alreadyProcessed.contains(repairCandidate))
					continue;
			
				IMatch repairedMatch = rStrategy.repair(repairCandidate);
				if(repairedMatch != null) {
					alreadyProcessed.add(repairCandidate);

					TGGRuleApplication oldRa = getRuleApplicationNode(repairCandidate);
					brokenRuleApplications.remove(oldRa);
					
					TGGRuleApplication newRa = getRuleApplicationNode(repairedMatch);
					brokenRuleApplications.put(newRa, repairedMatch);
					alreadyProcessed.add(repairedMatch);
					
//					// Add translated elements
//					IGreenPatternFactory gFactory = getGreenFactory(repairedMatch.getRuleName());
//					Collection<Object> translatedElts = cfactory.createObjectSet();
//
//					gFactory.getGreenSrcNodesInRule().forEach(n -> translatedElts.add(repairedMatch.get(n.getName())));
//					gFactory.getGreenTrgNodesInRule().forEach(n -> translatedElts.add(repairedMatch.get(n.getName())));
//					gFactory.getGreenSrcEdgesInRule().forEach(e -> translatedElts.add(getRuntimeEdge(repairedMatch, e)));
//					gFactory.getGreenTrgEdgesInRule().forEach(e -> translatedElts.add(getRuntimeEdge(repairedMatch, e)));
//					
//					translated.addAll(translatedElts);
				}
			}
		}
		return !alreadyProcessed.isEmpty();
	}

	protected void translate() {
		do
			blackInterpreter.updateMatches();
		while (processOneOperationalRuleMatch());
	}

	protected void rollBack() {
		do
			blackInterpreter.updateMatches();
		while (revokeBrokenMatches());
	}

	protected boolean revokeBrokenMatches() {
		pending.clear();

		if (brokenRuleApplications.isEmpty())
			return false;
		
		revokeAllMatches();

		return true;
	}

	protected void revokeAllMatches() {
		while (!brokenRuleApplications.isEmpty()) {
			Set<TGGRuleApplication> revoked = cfactory.createObjectSet();
			
			for (TGGRuleApplication ra : brokenRuleApplications.keySet()) {	
				redInterpreter.revokeOperationalRule(brokenRuleApplications.get(ra));
				revoked.add(ra);
				
			
			}
			for (TGGRuleApplication revokedRA : revoked)
				brokenRuleApplications.remove(revokedRA);
		}
	}

	public void forward() throws IOException {
		strategy = new FWD_Strategy();
		run();
	}

	public void backward() throws IOException {
		strategy = new BWD_Strategy();
		run();
	}

	/***** Marker Handling *******/

	/**
	 * Override in subclass if markers for protocol are not required (this can speed
	 * up the translation process).
	 */
	@Override
	protected void handleSuccessfulRuleApplication(IMatch cm, String ruleName, IGreenPattern greenPattern) {
		createMarkers(greenPattern, cm, ruleName);
	}

	protected void fillInProtocolData(TGGRuleApplication protocolNode, int protocolNodeID) {
		getNodes(protocolNode, BindingType.CREATE, DomainType.SRC).stream()
				.forEach(n -> nodeToProtocolID.put(n, protocolNodeID));
		getNodes(protocolNode, BindingType.CREATE, DomainType.TRG).stream()
				.forEach(n -> nodeToProtocolID.put(n, protocolNodeID));
		getNodes(protocolNode, BindingType.CREATE, DomainType.CORR).stream()
				.forEach(n -> nodeToProtocolID.put(n, protocolNodeID));
	}

	/***** Match and pattern management *****/

	public EMFEdge getRuntimeEdge(IMatch match, TGGRuleEdge specificationEdge) {
		EObject src = (EObject) match.get(specificationEdge.getSrcNode().getName());
		EObject trg = (EObject) match.get(specificationEdge.getTrgNode().getName());
		EReference ref = specificationEdge.getType();
		return new EMFEdge(src, trg, ref);
	}

	@Override
	protected IMatchContainer createMatchContainer() {
		return new PrecedenceGraph(this, translated, pending);
	}

	@Override
	protected void addConsistencyMatch(IMatch match) {
		super.addConsistencyMatch(match);

		TGGRuleApplication ruleAppNode = getRuleApplicationNode(match);
		if (brokenRuleApplications.containsKey(ruleAppNode)) {
			logger.info(match.getPatternName() + " (" + match.hashCode() + ") appears to be fixed.");
			brokenRuleApplications.remove(ruleAppNode);
		}

		// Add translated elements
		IGreenPatternFactory gFactory = getGreenFactory(match.getRuleName());
		Collection<Object> translatedElts = cfactory.createObjectSet();

		gFactory.getGreenSrcNodesInRule().forEach(n -> translatedElts.add(match.get(n.getName())));
		gFactory.getGreenTrgNodesInRule().forEach(n -> translatedElts.add(match.get(n.getName())));
		gFactory.getGreenSrcEdgesInRule().forEach(e -> translatedElts.add(getRuntimeEdge(match, e)));
		gFactory.getGreenTrgEdgesInRule().forEach(e -> translatedElts.add(getRuntimeEdge(match, e)));

		consistencyToTranslated.put(match, translatedElts);

		pending.removeAll(translatedElts);
		translated.addAll(translatedElts);
	}

	@Override
	public void removeMatch(org.emoflon.ibex.common.operational.IMatch match) {
		super.removeMatch(match);

		if (match.getPatternName().endsWith(PatternSuffixes.CONSISTENCY))
			addBrokenMatch((IMatch) match);
	}

	protected void addBrokenMatch(IMatch match) {
		TGGRuleApplication ra = getRuleApplicationNode(match);
		brokenRuleApplications.put(ra, match);

		// Transfer elements to the pending collection
		translated.removeAll(consistencyToTranslated.get(brokenRuleApplications.get(ra)));
		pending.addAll(consistencyToTranslated.remove(brokenRuleApplications.get(ra)));
	}

	@Override
	public boolean isPatternRelevantForCompiler(String patternName) {
		return patternName.endsWith(PatternSuffixes.BWD)//
				|| patternName.endsWith(PatternSuffixes.FWD)//
				|| patternName.endsWith(PatternSuffixes.CONSISTENCY);
	}

	@Override
	public boolean isPatternRelevantForInterpreter(String patternName) {
		return strategy.isPatternRelevantForInterpreter(patternName);
	}

	@Override
	public IGreenPattern revokes(IMatch match) {
		return strategy.revokes(getGreenFactory(match.getRuleName()), match.getPatternName(), match.getRuleName());
	}

	@Override
	protected Optional<IMatch> processOperationalRuleMatch(String ruleName, IMatch match) {
		if (!tggContainsComplementRules())
			// If TGG does not contain complement rules, bookkeeping is not necessary
			return super.processOperationalRuleMatch(ruleName, match);

		if (isComplementMatch(ruleName))
			if (!isComplementRuleApplicable(match, ruleName)) {
				logger.debug("Complement rule is not applicable: " + match.getPatternName());
				return Optional.empty();
			}

		Optional<IMatch> comatch = super.processOperationalRuleMatch(ruleName, match);

		comatch.ifPresent(cm -> {
			doBookkeepingForKernelAndComplementMatches(ruleName, cm);
			if (MAUtil.isFusedPatternMatch(ruleName))
				removeDuplicatedComplementMatches(ruleName, cm);
		});

		return comatch;
	}

	public IRuntimeTGGAttrConstrContainer determineCSP(IGreenPatternFactory factory, IMatch m) {
		return strategy.determineCSP(factory, m);
	}
	
	/***** Multi-Amalgamation *****/

	/**
	 * Removes complement match that was already applied together in kernel in the
	 * fused match
	 */
	protected void removeDuplicatedComplementMatches(String fusedRuleName, IMatch comatch) {
		blackInterpreter.updateMatches();
		Set<IMatch> complementMatches = findAllComplementRuleMatches();

		for (IMatch match : complementMatches) {
			if (isComplementMatchRelevant(fusedRuleName, match)) {

				Set<EObject> fusedNodes = comatch.getParameterNames().stream()//
						.map(n -> (EObject) comatch.get(n))//
						.collect(Collectors.toSet());
				Set<EObject> complementNodes = match.getParameterNames().stream()//
						.map(n -> (EObject) match.get(n))//
						.collect(Collectors.toSet());

				if (fusedNodes.containsAll(complementNodes)) {
					removeOperationalRuleMatch(match);
					logger.debug(
							"Removed complement rule as it has already been applied as a fused rule application with its kernel:");
					logger.debug("Complement rule:" + match);
					logger.debug("fused rule: " + comatch);
				}
			}
		}
	}

	/**
	 * Removes complement matches that were not part of the fused match, and also
	 * irrelevant complement matches
	 */
	protected boolean isComplementMatchRelevant(String fusedRuleName, IMatch match) {
		String complementName = MAUtil.getComplementName(fusedRuleName);

		return complementName.equals(PatternSuffixes.removeSuffix(match.getPatternName()))
				&& isPatternRelevantForInterpreter(match.getPatternName());
	}

	protected void doBookkeepingForKernelAndComplementMatches(String ruleName, IMatch comatch) {
		TGGRuleApplication protocolNode;
		int localCounter;

		if (MAUtil.isFusedPatternMatch(ruleName)) {
			protocolNode = (TGGRuleApplication) comatch
					.get(TGGPatternUtil.getProtocolNodeName(MAUtil.getKernelName(ruleName)));
		} else {
			// if it is a kernel or a complement rule
			protocolNode = (TGGRuleApplication) comatch
					.get(TGGPatternUtil.getProtocolNodeName(PatternSuffixes.removeSuffix(comatch.getPatternName())));
		}

		if (isComplementMatch(ruleName)) {
			// complement protocol has to have same ID as its kernel protocol
			TGGRuleApplication kernelProtocolNode = (TGGRuleApplication) comatch
					.get(TGGPatternUtil.getProtocolNodeName(getComplementRule(ruleName).get().getKernel().getName()));
			localCounter = protocolNodeToID.get(kernelProtocolNode);
		} else {
			idCounter++;
			localCounter = idCounter;
		}

		protocolNodeToID.put(protocolNode, localCounter);
		fillInProtocolData(protocolNode, localCounter);

		if (MAUtil.isFusedPatternMatch(ruleName)) {
			TGGRuleApplication complProtocolNode = ((TGGRuleApplication) comatch
					.get(TGGPatternUtil.getProtocolNodeName(MAUtil.getComplementName(ruleName))));
			protocolNodeToID.put(complProtocolNode, localCounter);
			fillInProtocolData(complProtocolNode, localCounter);
		}
	}

	protected boolean isComplementRuleApplicable(IMatch match, String ruleName) {
		if (!isPatternRelevantForInterpreter(match.getPatternName())) {
			logger.debug("Not relevant for interpreter: " + match.getPatternName());
			return false;
		}

		TGGComplementRule cr = getComplementRule(ruleName).get();
		if (!cr.isBounded()) {
			return true;
		}

		EObject kernelProtocol = (EObject) match.get(TGGPatternUtil.getProtocolNodeName(cr.getKernel().getName()));
		Set<EObject> contextNodes = getContextNodesWithoutProtocolNode(match);

		// If any node from bounded CR context was created after
		// its kernel was applied, CR is not applicable!
		for (EObject contextNode : contextNodes) {
			if (nodeToProtocolID.get(contextNode) != null // The context might not even have been created yet???
					&& nodeToProtocolID.get(contextNode) > protocolNodeToID.get(kernelProtocol)) {
				logger.debug("Complement match " + match.getPatternName() + " is not applicable as " + contextNode
						+ " (id = " + nodeToProtocolID.get(contextNode) + ")" + " was created after " + kernelProtocol
						+ " (id = " + protocolNodeToID.get(kernelProtocol) + ")");
				return false;
			}
		}
		return true;
	}

	protected Set<EObject> getContextNodesWithoutProtocolNode(IMatch match) {
		Set<EObject> contextNodes = match.getParameterNames().stream()
				.filter(n -> !(match.get(n) instanceof TGGRuleApplication)).map(n -> (EObject) match.get(n))
				.collect(Collectors.toSet());
		return contextNodes;
	}

	protected Set<IMatch> findAllComplementRuleMatches() {
		Set<IMatch> allComplementRuleMatches = operationalMatchContainer.getMatches().stream()
				.filter(m -> getComplementRulesNames().contains(PatternSuffixes.removeSuffix(m.getPatternName())))
				.collect(Collectors.toSet());

		return allComplementRuleMatches;
	}
	
	public SYNC_Strategy getStrategy() {
		return strategy;
	}
	
	private void logCreatedAndDeletedNumbers() {
		if(options.debug()) {
			Optional<ShortcutRepairStrategy> scStrategy = repairStrategies.stream().filter(rStr -> rStr instanceof ShortcutRepairStrategy).map(rStr -> (ShortcutRepairStrategy) rStr).findFirst();
			logger.info("Created elements: " + greenInterpreter.getNumOfCreatedElements());
			logger.info("Deleted elements: " + (redInterpreter.getNumOfDeletedElements() + (scStrategy.isPresent() ? scStrategy.get().countDeletedElements() : 0)));
		}
	}
}
