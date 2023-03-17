package org.emoflon.ibex.tgg.runtime.strategies;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.io.IOException;
import java.util.Set;

import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
import org.emoflon.ibex.tgg.runtime.interpreter.IRedInterpreter;
import org.emoflon.ibex.tgg.runtime.interpreter.IbexRedInterpreter;
import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.runtime.matches.container.IMatchContainer;
import org.emoflon.ibex.tgg.runtime.matches.container.MarkingMatchContainer;
import org.emoflon.ibex.tgg.runtime.matches.container.PrecedenceMatchContainer;
import org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.TGGRuleApplication;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule;
import org.emoflon.ibex.tgg.util.benchmark.EmptyBenchmarkLogger;
import org.emoflon.ibex.tgg.util.benchmark.Timer;
import org.emoflon.ibex.tgg.util.debug.ConsoleUtil;
import org.emoflon.ibex.tgg.util.debug.LoggerConfig;

public abstract class PropagatingOperationalStrategy extends OperationalStrategy {

	protected IRedInterpreter redInterpreter;

	/***** Constructors *****/

	public PropagatingOperationalStrategy(IbexOptions options) throws IOException {
		super(options);
	}

	@Override
	protected void initializeAdditionalModules(IbexOptions options) throws IOException {
		redInterpreter = new IbexRedInterpreter(this);
		matchHandler.handleConsistencyMatches(operationalMatchContainer);
		matchHandler.handleBrokenConsistencyMatches();
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
		if (operationalMatchContainer instanceof PrecedenceMatchContainer precedenceMatchContainer)
			precedenceMatchContainer.clearPendingElements();

		if (matchHandler.noBrokenRuleApplications())
			return false;

		revokeAllMatches();

		return true;
	}

	protected void revokeAllMatches() {
		while (!matchHandler.noBrokenRuleApplications()) {
			Set<TGGRuleApplication> revoked = cfactory.createObjectSet();

			for (TGGRuleApplication ra : matchHandler.getBrokenRuleApplications()) {
				ITGGMatch match = matchHandler.getBrokenMatch(ra);
				redInterpreter.revokeOperationalRule(match);
				revoked.add(ra);
				LoggerConfig.log(LoggerConfig.log_ruleApplication(),
						() -> "Rule application: rolled back " + match.getPatternName() + "(" + match.hashCode() + ")\n" //
								+ ConsoleUtil.indent(ConsoleUtil.printMatchParameter(match), 18, true));
			}
			for (TGGRuleApplication revokedRA : revoked)
				matchHandler.removeBrokenRuleApplication(revokedRA);

			options.debug.benchmarkLogger().addToNumOfMatchesRevoked(revoked.size());
		}
	}

	/***** Marker Handling *******/

	/**
	 * Override in subclass if markers for protocol are not required (this can speed up the translation
	 * process).
	 */
	@Override
	protected void handleSuccessfulRuleApplication(ITGGMatch cm, String ruleName, TGGOperationalRule operationalRule) {
		createMarkers(operationalRule, cm, ruleName);
	}

	/***** Match and pattern management *****/

	@Override
	protected IMatchContainer createMatchContainer() {
		if (options.propagate.usePrecedenceGraph())
			return new PrecedenceMatchContainer(this);
		else
			return new MarkingMatchContainer(this);
	}

	public IRedInterpreter getRedInterpreter() {
		return redInterpreter;
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
