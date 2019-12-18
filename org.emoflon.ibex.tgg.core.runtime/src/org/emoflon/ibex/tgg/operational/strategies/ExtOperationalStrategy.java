package org.emoflon.ibex.tgg.operational.strategies;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.emoflon.ibex.common.collections.CollectionFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.operational.IRedInterpreter;
import org.emoflon.ibex.tgg.operational.benchmark.EmptyBenchmarkLogger;
import org.emoflon.ibex.tgg.operational.csp.IRuntimeTGGAttrConstrContainer;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.defaults.IbexRedInterpreter;
import org.emoflon.ibex.tgg.operational.matches.IMatchContainer;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPatternFactory;
import org.emoflon.ibex.tgg.operational.repair.AbstractRepairStrategy;
import org.emoflon.ibex.tgg.operational.repair.AttributeRepairStrategy;
import org.emoflon.ibex.tgg.operational.repair.ShortcutRepairStrategy;
import org.emoflon.ibex.tgg.operational.strategies.modules.IbexExecutable;
import org.emoflon.ibex.tgg.operational.strategies.sync.PrecedenceGraph;
import org.emoflon.ibex.tgg.operational.strategies.sync.SYNC_Strategy;

import runtime.TGGRuleApplication;

public abstract class ExtOperationalStrategy extends OperationalStrategy {

	// Forward or backward sync
	protected SYNC_Strategy syncStrategy;
	
	// Repair
	protected Collection<AbstractRepairStrategy> repairStrategies = new ArrayList<>();
	protected Map<TGGRuleApplication, ITGGMatch> brokenRuleApplications = CollectionFactory.cfactory
			.createObjectToObjectHashMap();
	protected IRedInterpreter redInterpreter;

	/***** Constructors *****/

	public ExtOperationalStrategy(IbexExecutable executable, IbexOptions options) throws IOException {
		super(executable, options);
		redInterpreter = new IbexRedInterpreter(this);
	}

	public void registerRedInterpeter(IRedInterpreter redInterpreter) {
		this.redInterpreter = redInterpreter;
	}

	/***** Sync algorithm *****/

	protected void repair() {
		initializeRepairStrategy(options);

		// TODO loop this together with roll back
		translate();
		repairBrokenMatches();
	}

	protected void initializeRepairStrategy(IbexOptions options) {
		if (!repairStrategies.isEmpty())
			return;

		if (options.repairUsingShortcutRules()) {
			repairStrategies.add(new ShortcutRepairStrategy(this));
		}
		if (options.repairAttributes()) {
			repairStrategies.add(new AttributeRepairStrategy(this));
		}
	}

	protected boolean repairBrokenMatches() {
		Collection<ITGGMatch> alreadyProcessed = cfactory.createObjectSet();
		for (AbstractRepairStrategy rStrategy : repairStrategies) {
			for (ITGGMatch repairCandidate : rStrategy.chooseMatches(brokenRuleApplications)) {
				if (alreadyProcessed.contains(repairCandidate))
					continue;

				ITGGMatch repairedMatch = rStrategy.repair(repairCandidate);
				if (repairedMatch != null) {
					alreadyProcessed.add(repairCandidate);

					TGGRuleApplication oldRa = getRuleApplicationNode(repairCandidate);
					brokenRuleApplications.remove(oldRa);

					TGGRuleApplication newRa = getRuleApplicationNode(repairedMatch);
					brokenRuleApplications.put(newRa, repairedMatch);
					alreadyProcessed.add(repairedMatch);

					options.getBenchmarkLogger().addToNumOfMatchesRepaired(1);
				}
			}
		}
		return !alreadyProcessed.isEmpty();
	}

	protected void translate() {
		if(options.applyConcurrently()) {
			matchDistributor.updateMatches();
			
			while(true) {
				while (processOneOperationalRuleMatch()) {
					
				}
				matchDistributor.updateMatches();
				if(!processOneOperationalRuleMatch())
					return;
			}
		}
		else {
			do {
				matchDistributor.updateMatches();
			}while (processOneOperationalRuleMatch());
		}
		
	}

	protected void rollBack() {
		do
			matchDistributor.updateMatches();
		while (revokeBrokenMatches());
	}

	protected boolean revokeBrokenMatches() {
		// clear pending elements since every element that has not been repaired until
		// now has to be revoked
		if (operationalMatchContainer instanceof PrecedenceGraph)
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

			options.getBenchmarkLogger().addToNumOfMatchesRevoked(revoked.size());
		}
	}
	
	public SYNC_Strategy getSyncStrategy() {
		return syncStrategy;
	}
	
	public IRuntimeTGGAttrConstrContainer determineCSP(IGreenPatternFactory factory, ITGGMatch m) {
		return syncStrategy.determineCSP(factory, m);
	}

	/***** Marker Handling *******/

	/**
	 * Override in subclass if markers for protocol are not required (this can speed
	 * up the translation process).
	 */
	@Override
	protected void handleSuccessfulRuleApplication(ITGGMatch cm, String ruleName, IGreenPattern greenPattern) {
		createMarkers(greenPattern, cm, ruleName);
	}

	/***** Match and pattern management *****/

	@Override
	protected IMatchContainer createMatchContainer() {
		return new PrecedenceGraph(this);
	}

	@Override
	protected void addConsistencyMatch(ITGGMatch match) {
		super.addConsistencyMatch(match);

		TGGRuleApplication ruleAppNode = getRuleApplicationNode(match);
		if (brokenRuleApplications.containsKey(ruleAppNode)) {
			logger.info(match.getPatternName() + " (" + match.hashCode() + ") appears to be fixed.");
			brokenRuleApplications.remove(ruleAppNode);
		}

		operationalMatchContainer.matchApplied(match);
	}

	@Override
	public boolean removeOperationalRuleMatch(ITGGMatch match) {
		if (match.getPatternName().endsWith(PatternSuffixes.CONSISTENCY))
			addConsistencyBrokenMatch((ITGGMatch) match);

		return super.removeOperationalRuleMatch(match);
	}

	protected void addConsistencyBrokenMatch(ITGGMatch match) {
		TGGRuleApplication ra = getRuleApplicationNode(match);
		brokenRuleApplications.put(ra, match);

		consistencyMatches.remove(ra);
		operationalMatchContainer.removeMatch(match);
	}

	@Override
	protected Optional<ITGGMatch> processOperationalRuleMatch(String ruleName, ITGGMatch match) {
		Optional<ITGGMatch> comatch = super.processOperationalRuleMatch(ruleName, match);
		return comatch;
	}

	@Override
	protected void collectDataToBeLogged() {
		if (options.getBenchmarkLogger() instanceof EmptyBenchmarkLogger)
			return;

		super.collectDataToBeLogged();

		int repStratDeletions = 0;
		if (!(options.getBenchmarkLogger() instanceof EmptyBenchmarkLogger))
			repStratDeletions = repairStrategies.stream() //
					.filter(rStr -> rStr instanceof ShortcutRepairStrategy) //
					.map(rStr -> (ShortcutRepairStrategy) rStr) //
					.findFirst() //
					.map(srStr -> srStr.countDeletedElements()) //
					.orElse(0);

		options.getBenchmarkLogger().setNumOfElementsCreated(greenInterpreter.getNumOfCreatedElements());
		options.getBenchmarkLogger().setNumOfElementsDeleted(redInterpreter.getNumOfDeletedElements() + //
				repStratDeletions);
	}
}
