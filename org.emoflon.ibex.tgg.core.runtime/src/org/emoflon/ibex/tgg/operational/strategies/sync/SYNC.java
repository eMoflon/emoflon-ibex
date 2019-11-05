package org.emoflon.ibex.tgg.operational.strategies.sync;

import java.io.IOException;
import java.util.Optional;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.operational.benchmark.BenchmarkLogger;
import org.emoflon.ibex.tgg.operational.csp.IRuntimeTGGAttrConstrContainer;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPatternFactory;
import org.emoflon.ibex.tgg.operational.repair.strategies.ShortcutRepairStrategy;
import org.emoflon.ibex.tgg.operational.strategies.ExtOperationalStrategy;
import org.emoflon.ibex.tgg.operational.strategies.sync.repair.strategies.AttributeRepairStrategy;

public abstract class SYNC extends ExtOperationalStrategy {

	// Forward or backward sync
	protected SYNC_Strategy strategy;

	/***** Constructors *****/

	public SYNC(IbexOptions options) throws IOException {
		super(options);
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
		options.getBenchmarkLogger().startNewRun();
		BenchmarkLogger.startTimer();

		repair();
		rollBack();
		translate();
		logCreatedAndDeletedNumbers();

		collectDataToBeLogged();
		options.getBenchmarkLogger().addToExecutionTime(BenchmarkLogger.stopTimer());
	}

	@Override
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

	public void forward() throws IOException {
		strategy = new FWD_Strategy();
		run();
	}

	public void backward() throws IOException {
		strategy = new BWD_Strategy();
		run();
	}

	/***** Match and pattern management *****/

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
	public IGreenPattern revokes(ITGGMatch match) {
		return strategy.revokes(getGreenFactory(match.getRuleName()), match.getPatternName(), match.getRuleName());
	}

	public IRuntimeTGGAttrConstrContainer determineCSP(IGreenPatternFactory factory, ITGGMatch m) {
		return strategy.determineCSP(factory, m);
	}

	public SYNC_Strategy getStrategy() {
		return strategy;
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
}
