package org.emoflon.ibex.tgg.operational.strategies.sync;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.sync.ConsistencyPattern;
import org.emoflon.ibex.tgg.operational.IBlackInterpreter;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;
import org.emoflon.ibex.tgg.operational.strategies.sync.repair.AbstractRepairStrategy;
import org.emoflon.ibex.tgg.operational.strategies.sync.repair.strategies.AttributeRepairStrategy;
import org.emoflon.ibex.tgg.util.MAUtil;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import language.TGGComplementRule;
import runtime.TGGRuleApplication;

public abstract class SYNC extends OperationalStrategy {

	protected HashMap<TGGRuleApplication, Integer> protocolNodeToID = new HashMap<TGGRuleApplication, Integer>();
	protected HashMap<EObject, Integer> nodeToProtocolID = new HashMap<>();
	private int idCounter = 0;
	private boolean multiamalgamatedTgg;
	private AbstractRepairStrategy repairStrategy;

	protected Map<TGGRuleApplication, IMatch> brokenRuleApplications = new Object2ObjectOpenHashMap<>();

	private SYNC_Strategy strategy;

	public SYNC(IbexOptions options) throws IOException {
		super(options);
	}

	@Override
	public void run() throws IOException {
		repair();
		rollBack();
		translate();
	}

	private void translate() {
		do
			blackInterpreter.updateMatches();
		while (processOneOperationalRuleMatch());
	}

	private void rollBack() {
		do
			blackInterpreter.updateMatches();
		while (revokeBrokenMatches());
	}

	private void repair() {
		initializeRepairStrategy(options);

		do
			blackInterpreter.updateMatches();
		while (repairOneBrokenMatch());
	}

	private boolean repairOneBrokenMatch() {
		return repairStrategy//
				.chooseOneMatch(brokenRuleApplications)//
				.map(repairStrategy::repair)//
				.orElse(false);
	}

	private void initializeRepairStrategy(IbexOptions options) {
		if (options.repairAttributes()) {
			repairStrategy = new AttributeRepairStrategy(this);
		}
	}

	@Override
	protected boolean addConsistencyMatch(IMatch match) {
		if (super.addConsistencyMatch(match)) {
			TGGRuleApplication ruleAppNode = getRuleApplicationNode(match);
			if (brokenRuleApplications.containsKey(ruleAppNode)) {
				logger.debug(match.getPatternName() + " appears to be fixed.");
				brokenRuleApplications.remove(ruleAppNode);
			}
			
			return true;
		}
		
		return false;
	}
	
	/***** Methods for reacting to broken matches of consistency patterns ******/

	@Override
	public void removeMatch(org.emoflon.ibex.common.operational.IMatch match) {
		super.removeMatch(match);
		
		if (match.getPatternName().endsWith(PatternSuffixes.CONSISTENCY)) {
			this.addBrokenMatch((IMatch) match);
		}
	}
	
	public void addBrokenMatch(IMatch match) {
		TGGRuleApplication ra = getRuleApplicationNode(match);
		brokenRuleApplications.put(ra, match);
	}

	protected boolean revokeBrokenMatches() {
		if (brokenRuleApplications.isEmpty())
			return false;

		revokeAllMatches();

		return true;
	}

	private void revokeAllMatches() {
		while (!brokenRuleApplications.isEmpty()) {
			ObjectOpenHashSet<TGGRuleApplication> revoked = new ObjectOpenHashSet<>();
			for (TGGRuleApplication ra : brokenRuleApplications.keySet()) {
				redInterpreter.revokeOperationalRule(brokenRuleApplications.get(ra));
				revoked.add(ra);
			}
			for (TGGRuleApplication revokedRA : revoked)
				brokenRuleApplications.remove(revokedRA);
		}
	}


	@Override
	public void registerBlackInterpreter(IBlackInterpreter blackInterpreter) throws IOException {
		super.registerBlackInterpreter(blackInterpreter);
		multiamalgamatedTgg = tggContainsComplementRules();
	}

	@Override
	public boolean isPatternRelevantForCompiler(String patternName) {
		return patternName.endsWith(PatternSuffixes.BWD) || patternName.endsWith(PatternSuffixes.FWD)
				|| patternName.endsWith(PatternSuffixes.CONSISTENCY);
	}

	@Override
	public boolean isPatternRelevantForInterpreter(String patternName) {
		return strategy.isPatternRelevantForInterpreter(patternName);
	}

	@Override
	public void saveModels() throws IOException {
		s.save(null);
		t.save(null);
		c.save(null);
		p.save(null);
	}

	@Override
	public void loadModels() throws IOException {
		s = loadResource(options.projectPath() + "/instances/src.xmi");
		t = loadResource(options.projectPath() + "/instances/trg.xmi");
		c = loadResource(options.projectPath() + "/instances/corr.xmi");
		p = loadResource(options.projectPath() + "/instances/protocol.xmi");

		EcoreUtil.resolveAll(rs);
	}

	public void forward() throws IOException {
		strategy = new FWD_Strategy();
		run();
	}

	public void backward() throws IOException {
		strategy = new BWD_Strategy();
		run();
	}

	@Override
	public IGreenPattern revokes(IMatch match) {
		String ruleName = getRuleApplicationNode(match).getName();
		return strategy.revokes(getGreenFactory(ruleName), match.getPatternName(), ruleName);
	}

	@Override
	protected Optional<IMatch> processOperationalRuleMatch(String ruleName, IMatch match) {
		Optional<IMatch> comatch;

		// if TGG does not contain complement rules, bookkeeping is not necessary
		if (!multiamalgamatedTgg) {
			comatch = super.processOperationalRuleMatch(ruleName, match);
		}

		else {
			if (isComplementMatch(ruleName))
				if (!isComplementRuleApplicable(match, ruleName))
					return Optional.empty();

			comatch = super.processOperationalRuleMatch(ruleName, match);

			comatch.ifPresent(cm -> {
				doBookkeepingForKernelAndComplementMatches(ruleName, cm);
				if (MAUtil.isFusedPatternMatch(ruleName)) {
					removeDuplicatedComplementMatches(ruleName, cm);
				}

			});
		}
		return comatch;
	}

	/***** Methods for handling kernels, complements and fused matches ******/

	/*
	 * removes complement match that was already applied together in kernel in the
	 * fused match
	 */
	private void removeDuplicatedComplementMatches(String ruleName, IMatch comatch) {
		blackInterpreter.updateMatches();
		Set<IMatch> complementMatches = findAllComplementRuleMatches();

		for (IMatch match : complementMatches) {
			if (!isComplementMatchRelevant(match, comatch))
				continue;
			ObjectOpenHashSet<EObject> fusedNodes = comatch.getParameterNames().stream()
					.map(n -> (EObject) comatch.get(n))
					.collect(Collectors.toCollection(ObjectOpenHashSet<EObject>::new));
			ObjectOpenHashSet<EObject> complementNodes = match.getParameterNames().stream()
					.map(n -> (EObject) match.get(n)).collect(Collectors.toCollection(ObjectOpenHashSet<EObject>::new));

			if (fusedNodes.containsAll(complementNodes))
				removeOperationalRuleMatch(match);
		}

	}

	/*
	 * removes complement matches that were not part of the fused match, and also
	 * irrelevant complement matches
	 */
	private boolean isComplementMatchRelevant(IMatch match, IMatch comatch) {
		if (!match.getPatternName().contains(PatternSuffixes.removeSuffix(match.getPatternName()))
				|| !isPatternRelevantForInterpreter(match.getPatternName()))
			return false;
		return true;
	}

	private void doBookkeepingForKernelAndComplementMatches(String ruleName, IMatch comatch) {
		TGGRuleApplication protocolNode;
		int localCounter;

		if (MAUtil.isFusedPatternMatch(ruleName)) {
			protocolNode = (TGGRuleApplication) comatch
					.get(ConsistencyPattern.getProtocolNodeName(MAUtil.getKernelName(ruleName)));
		} else {
			// if it is a kernel or a complement rule
			protocolNode = (TGGRuleApplication) comatch.get(
					ConsistencyPattern.getProtocolNodeName(PatternSuffixes.removeSuffix(comatch.getPatternName())));
		}

		if (isComplementMatch(ruleName)) {
			// complement protocol has to have same ID as its kernel protocol
			TGGRuleApplication kernelProtocolNode = (TGGRuleApplication) comatch.get(
					ConsistencyPattern.getProtocolNodeName(getComplementRule(ruleName).get().getKernel().getName()));
			localCounter = protocolNodeToID.get(kernelProtocolNode);
		} else {
			idCounter++;
			localCounter = idCounter;
		}

		protocolNodeToID.put(protocolNode, localCounter);
		fillInProtocolData(protocolNode, localCounter);

		if (MAUtil.isFusedPatternMatch(ruleName)) {
			TGGRuleApplication complProtocolNode = ((TGGRuleApplication) comatch
					.get(ConsistencyPattern.getProtocolNodeName(MAUtil.getComplementName(ruleName))));
			fillInProtocolData(complProtocolNode, localCounter);
		}

	}

	private void fillInProtocolData(TGGRuleApplication protocolNode, int protocolNodeID) {
		protocolNode.getCreatedSrc().stream().forEach(n -> nodeToProtocolID.put(n, protocolNodeID));
		protocolNode.getCreatedTrg().stream().forEach(n -> nodeToProtocolID.put(n, protocolNodeID));
		protocolNode.getCreatedCorr().stream().forEach(n -> nodeToProtocolID.put(n, protocolNodeID));
	}

	private boolean isComplementRuleApplicable(IMatch match, String ruleName) {
		if (!isPatternRelevantForInterpreter(match.getPatternName())) {
			return false;
		}

		TGGComplementRule cr = getComplementRule(ruleName).get();
		if (!cr.isBounded())
			return true;

		EObject kernelProtocol = (EObject) match.get(ConsistencyPattern.getProtocolNodeName(cr.getKernel().getName()));
		ObjectOpenHashSet<EObject> contextNodes = getContextNodesWithoutProtocolNode(match);

		// If any node from bounded CR context was created after
		// its kernel was applied, CR is not applicable!
		for (EObject contextNode : contextNodes) {
			if (nodeToProtocolID.get(contextNode) != null // The context might not even have been created yet???
					&& nodeToProtocolID.get(contextNode) > protocolNodeToID.get(kernelProtocol))
				return false;
		}
		return true;
	}

	private ObjectOpenHashSet<EObject> getContextNodesWithoutProtocolNode(IMatch match) {
		ObjectOpenHashSet<EObject> contextNodes = match.getParameterNames().stream()
				.filter(n -> !(match.get(n) instanceof TGGRuleApplication)).map(n -> (EObject) match.get(n))
				.collect(Collectors.toCollection(ObjectOpenHashSet<EObject>::new));
		return contextNodes;
	}

	private Set<IMatch> findAllComplementRuleMatches() {
		Set<IMatch> allComplementRuleMatches = operationalMatchContainer.getMatches().stream()
				.filter(m -> getComplementRulesNames().contains(PatternSuffixes.removeSuffix(m.getPatternName())))
				.collect(Collectors.toSet());

		return allComplementRuleMatches;
	}

	public SYNC_Strategy getStrategy() {
		return strategy;
	}

}
