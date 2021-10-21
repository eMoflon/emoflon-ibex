package org.emoflon.ibex.tgg.operational.strategies;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.IRedInterpreter;
import org.emoflon.ibex.tgg.operational.benchmark.EmptyBenchmarkLogger;
import org.emoflon.ibex.tgg.operational.benchmark.Timer;
import org.emoflon.ibex.tgg.operational.debug.LoggerConfig;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.defaults.IbexRedInterpreter;
import org.emoflon.ibex.tgg.operational.matches.IMatchContainer;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.matches.MarkingMatchContainer;
import org.emoflon.ibex.tgg.operational.matches.PrecedenceMatchContainer;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.util.ConsoleUtil;

import runtime.TGGRuleApplication;

public abstract class PropagatingOperationalStrategy extends OperationalStrategy {

	protected Map<TGGRuleApplication, ITGGMatch> brokenRuleApplications = cfactory.createObjectToObjectHashMap();
	protected IRedInterpreter redInterpreter;

	/***** Constructors *****/

	public PropagatingOperationalStrategy(IbexOptions options) throws IOException {
		super(options);
		redInterpreter = new IbexRedInterpreter(this);
	}

	public void registerRedInterpeter(IRedInterpreter redInterpreter) {
		this.redInterpreter = redInterpreter;
	}

	/***** Algorithm *****/

	protected void translate() {
		Timer.start();

		if (options.propagate.applyConcurrently()) {
			matchDistributor.updateMatches();

			while (true) {
				while (processOneOperationalRuleMatch()) {

				}
				matchDistributor.updateMatches();
				if (!processOneOperationalRuleMatch()) {
					times.addTo("translate", Timer.stop());
					return;
				}
			}
		} else {
			do {
				matchDistributor.updateMatches();
			} while (processOneOperationalRuleMatch());
		}

		times.addTo("translate", Timer.stop());
	}

	protected void rollBack() {
		Timer.start();

		do
			matchDistributor.updateMatches();
		while (revokeBrokenMatches());

		times.addTo("revoke", Timer.stop());
	}

	protected boolean revokeBrokenMatches() {
		// clear pending elements since every element that has not been repaired until
		// now has to be revoked
		if (operationalMatchContainer instanceof PrecedenceMatchContainer)
			((PrecedenceMatchContainer) operationalMatchContainer).clearPendingElements();

		if (brokenRuleApplications.isEmpty())
			return false;

		revokeAllMatches();

		return true;
	}

	protected void revokeAllMatches() {
		while (!brokenRuleApplications.isEmpty()) {
			Set<TGGRuleApplication> revoked = cfactory.createObjectSet();

			for (TGGRuleApplication ra : brokenRuleApplications.keySet()) {
				ITGGMatch match = brokenRuleApplications.get(ra);
				redInterpreter.revokeOperationalRule(match);
				revoked.add(ra);
				LoggerConfig.log(LoggerConfig.log_ruleApplication(),
						() -> "Rule application: rolled back " + match.getPatternName() + "(" + match.hashCode() + ")\n" //
								+ ConsoleUtil.indent(ConsoleUtil.printMatchParameter(match), 18, true));
			}
			for (TGGRuleApplication revokedRA : revoked)
				brokenRuleApplications.remove(revokedRA);

			options.debug.benchmarkLogger().addToNumOfMatchesRevoked(revoked.size());
		}
	}

	/***** Marker Handling *******/

	/**
	 * Override in subclass if markers for protocol are not required (this can speed up the translation
	 * process).
	 */
	@Override
	protected void handleSuccessfulRuleApplication(ITGGMatch cm, String ruleName, IGreenPattern greenPattern) {
		createMarkers(greenPattern, cm, ruleName);
	}

	/***** Match and pattern management *****/

	@Override
	protected IMatchContainer createMatchContainer() {
		if (options.propagate.usePrecedenceGraph())
			return new PrecedenceMatchContainer(this);
		else
			return new MarkingMatchContainer(this);
	}

	@Override
	protected void addConsistencyMatch(ITGGMatch match) {
		super.addConsistencyMatch(match);

		TGGRuleApplication ruleAppNode = match.getRuleApplicationNode();
		if (brokenRuleApplications.containsKey(ruleAppNode)) {
			LoggerConfig.log(LoggerConfig.log_ruleApplication(),
					() -> "Repair confirmation: " + match.getPatternName() + "(" + match.hashCode() + ") appears to be fixed.");
			brokenRuleApplications.remove(ruleAppNode);
			options.debug.benchmarkLogger().addToNumOfMatchesRepaired(1);
		}

		operationalMatchContainer.matchApplied(match);
	}

	@Override
	public boolean removeOperationalRuleMatch(ITGGMatch match) {
		if (match.getType() == PatternType.CONSISTENCY)
			addConsistencyBrokenMatch((ITGGMatch) match);

		return super.removeOperationalRuleMatch(match);
	}

	protected void addConsistencyBrokenMatch(ITGGMatch match) {
		TGGRuleApplication ra = match.getRuleApplicationNode();
		brokenRuleApplications.put(ra, match);

		consistencyMatches.remove(ra);
		operationalMatchContainer.removeMatch(match);
	}

	public IRedInterpreter getRedInterpreter() {
		return redInterpreter;
	}

	public Map<TGGRuleApplication, ITGGMatch> getBrokenRuleApplications() {
		return brokenRuleApplications;
	}

	@Override
	protected void collectDataToBeLogged() {
		if (options.debug.benchmarkLogger() instanceof EmptyBenchmarkLogger)
			return;

		super.collectDataToBeLogged();

		options.debug.benchmarkLogger().setNumOfElementsCreated(greenInterpreter.getNumOfCreatedNodes());
		options.debug.benchmarkLogger().setNumOfElementsDeleted(redInterpreter.getNumOfDeletedNodes());
	}

}
