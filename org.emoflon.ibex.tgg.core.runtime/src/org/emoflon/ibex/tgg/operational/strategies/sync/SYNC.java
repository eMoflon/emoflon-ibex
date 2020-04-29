package org.emoflon.ibex.tgg.operational.strategies.sync;

import java.io.IOException;
import java.util.Collection;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.benchmark.BenchmarkLogger;
import org.emoflon.ibex.tgg.operational.csp.IRuntimeTGGAttrConstrContainer;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPatternFactory;
import org.emoflon.ibex.tgg.operational.strategies.PropagatingOperationalStrategy;

public class SYNC extends PropagatingOperationalStrategy {

	// Forward or backward sync
	protected SYNC_Strategy syncStrategy;

	/*****
	 * Constructors
	 * 
	 * @param sync
	 *****/

	public SYNC(IbexOptions options) throws IOException {
		super(options);
	}

	/***** Sync algorithm *****/

	@Override
	public void run() throws IOException {
		options.debug.benchmarkLogger().startNewRun();
		BenchmarkLogger.startTimer();

		repair();
		rollBack();
		translate();
		logCreatedAndDeletedNumbers();

		collectDataToBeLogged();
		options.debug.benchmarkLogger().addToExecutionTime(BenchmarkLogger.stopTimer());
	}

	public void forward() throws IOException {
		syncStrategy = new FWD_Strategy();
		run();
	}

	public void backward() throws IOException {
		syncStrategy = new BWD_Strategy();
		run();
	}

	protected void repair() {
		initializeRepairStrategy(options);

		// TODO loop this together with roll back
		translate();
		repairBrokenMatches();
	}

	public SYNC_Strategy getSyncStrategy() {
		return syncStrategy;
	}
	
	public IRuntimeTGGAttrConstrContainer determineCSP(IGreenPatternFactory factory, ITGGMatch m) {
		return syncStrategy.determineCSP(factory, m);
	}
	
	/***** Match and pattern management *****/

	@Override
	public boolean isPatternRelevantForInterpreter(PatternType type) {
		return syncStrategy.isPatternRelevantForInterpreter(type);
	}

	@Override
	public IGreenPattern revokes(ITGGMatch match) {
		return syncStrategy.revokes(getGreenFactory(match.getRuleName()), match.getPatternName(), match.getRuleName());
	}

	@Override
	public Collection<PatternType> getPatternRelevantForCompiler() {
		return PatternType.getSYNCTypes();
	}

	private void logCreatedAndDeletedNumbers() {
		if (options.debug.ibexDebug()) {
			logger.info("Created elements: " + greenInterpreter.getNumOfCreatedNodes());
			logger.info("Deleted elements: " + redInterpreter.getNumOfDeletedNodes());
		}
	}
}
