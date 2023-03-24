package org.emoflon.ibex.tgg.runtime.strategies.sync;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.emoflon.ibex.tgg.patterns.PatternType;
import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
import org.emoflon.ibex.tgg.runtime.repair.SeqRepair;
import org.emoflon.ibex.tgg.runtime.strategies.PropagatingOperationalStrategy;
import org.emoflon.ibex.tgg.runtime.strategies.PropagationDirectionHolder;
import org.emoflon.ibex.tgg.runtime.strategies.PropagationDirectionHolder.PropagationDirection;
import org.emoflon.ibex.tgg.runtime.strategies.StrategyMode;

public class SYNC extends PropagatingOperationalStrategy {

	// Forward or backward sync
	protected PropagationDirectionHolder propagationDirectionHolder;

	// Repair
	protected SeqRepair repairer;

	public SYNC(IbexOptions options) throws IOException {
		super(options);
	}

	@Override
	protected void initializeAdditionalModules(IbexOptions options) throws IOException {
		super.initializeAdditionalModules(options);
		this.propagationDirectionHolder = new PropagationDirectionHolder();
		this.repairer = new SeqRepair(this, propagationDirectionHolder);
	}

	/***** Sync algorithm *****/

	@Override
	public void run() throws IOException {
		options.debug.benchmarkLogger().startNewRun();

		repair();
		rollBack();
		translate();
		logCreatedAndDeletedNumbers();

		collectDataToBeLogged();
	}

	public void forward() throws IOException {
		propagationDirectionHolder.set(PropagationDirection.FORWARD);
		run();
	}

	public void backward() throws IOException {
		propagationDirectionHolder.set(PropagationDirection.BACKWARD);
		run();
	}

	protected void repair() {
		// TODO loop this together with roll back
		translate();
		repairer.repairBrokenMatches();
	}

	/***** Match and pattern management *****/

	@Override
	public boolean isPatternRelevantForInterpreter(PatternType type) {
		return propagationDirectionHolder.get().getPatternType() == type;
	}

	@Override
	protected Set<PatternType> getRelevantOperationalPatterns() {
		return new HashSet<>(Arrays.asList(PatternType.FWD, PatternType.BWD));
	}

	private void logCreatedAndDeletedNumbers() {
		if (options.debug.ibexDebug()) {
			logger.info("Created elements: " + greenInterpreter.getNumOfCreatedNodes());
			logger.info("Deleted elements: " + redInterpreter.getNumOfDeletedNodes());
		}
	}
	
	@Override
	public StrategyMode getStrategyMode() {
		return StrategyMode.SYNC;
	}
}
