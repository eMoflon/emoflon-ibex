package org.emoflon.ibex.tgg.operational.strategies.sync;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.benchmark.BenchmarkLogger;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.repair.ShortcutRepairStrategy;
import org.emoflon.ibex.tgg.operational.strategies.PropagatingOperationalStrategy;


public class SYNC extends PropagatingOperationalStrategy {

	/***** Constructors 
	 * @param sync *****/

	public SYNC(IbexOptions options) throws IOException {
		super(options);
	}

	/***** Sync algorithm *****/

	@Override
	public void run() throws IOException {
		options.getBenchmarkLogger().startNewRun();
		BenchmarkLogger.startTimer();

		repair();
		rollBack();
		translate();
		logCreatedAndDeletedNumbers();

		collectDataToBeLogged();
		options.getBenchmarkLogger().addToExecutionTime(BenchmarkLogger.stopTimer());
	}

	public void forward() throws IOException {
		syncStrategy = new FWD_Strategy();
		run();
	}

	public void backward() throws IOException {
		syncStrategy = new BWD_Strategy();
		run();
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

	private void logCreatedAndDeletedNumbers() {
		if (options.debug()) {
			Optional<ShortcutRepairStrategy> scStrategy = repairStrategies.stream() //
					.filter(rStr -> rStr instanceof ShortcutRepairStrategy) //
					.map(rStr -> (ShortcutRepairStrategy) rStr) //
					.findFirst();
			logger.info("Created elements: " + greenInterpreter.getNumOfCreatedElements());
			logger.info("Deleted elements: " + (redInterpreter.getNumOfDeletedElements()
					+ (scStrategy.isPresent() ? scStrategy.get().countDeletedElements() : 0)));
		}
	}

	@Override
	public Collection<PatternType> getPatternRelevantForCompiler() {
		return PatternType.getSYNCTypes();
	}
}
