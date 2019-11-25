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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.common.collections.CollectionFactory;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
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

import language.BindingType;
import language.DomainType;
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
				}
			}
		}
		return !alreadyProcessed.isEmpty();
	}

	protected void translate() {
		if(options.applyConcurrently()) {
			blackInterpreter.updateMatches();
			
			while(true) {
				while (processOneOperationalRuleMatch()) {
					
				}
				blackInterpreter.updateMatches();
				if(!processOneOperationalRuleMatch())
					return;
			}
		}
		else {
			do {
				blackInterpreter.updateMatches();
			}while (processOneOperationalRuleMatch());
		}
		
	}

	protected void rollBack() {
		do
			blackInterpreter.updateMatches();
		while (revokeBrokenMatches());
	}

	protected boolean revokeBrokenMatches() {
		// clear pending elements since every element that has not been repaired until now has to be revoked
		if(operationalMatchContainer instanceof PrecedenceGraph)
			((PrecedenceGraph) operationalMatchContainer).clearPendingElements();

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
		return new PrecedenceGraph(this);
	}

	@Override
	protected void addConsistencyMatch(IMatch match) {
		super.addConsistencyMatch(match);

		TGGRuleApplication ruleAppNode = getRuleApplicationNode(match);
		if (brokenRuleApplications.containsKey(ruleAppNode)) {
			logger.info(match.getPatternName() + " (" + match.hashCode() + ") appears to be fixed.");
			brokenRuleApplications.remove(ruleAppNode);
		}
		
		operationalMatchContainer.matchApplied(match);
	}

	@Override
	public void removeMatch(org.emoflon.ibex.common.operational.IMatch match) {
		super.removeMatch(match);

		if (match.getPatternName().endsWith(PatternSuffixes.CONSISTENCY))
			addConsistencyBrokenMatch((IMatch) match);
	}

	protected void addConsistencyBrokenMatch(IMatch match) {
		TGGRuleApplication ra = getRuleApplicationNode(match);
		brokenRuleApplications.put(ra, match);

		operationalMatchContainer.removeMatch(match);
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
		Optional<IMatch> comatch = super.processOperationalRuleMatch(ruleName, match);
		return comatch;
	}

	public IRuntimeTGGAttrConstrContainer determineCSP(IGreenPatternFactory factory, IMatch m) {
		return strategy.determineCSP(factory, m);
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
