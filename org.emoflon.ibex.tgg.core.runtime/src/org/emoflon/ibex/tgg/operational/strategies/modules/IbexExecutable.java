package org.emoflon.ibex.tgg.operational.strategies.modules;

import java.io.IOException;

import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;

public abstract class IbexExecutable {

	protected OperationalStrategy strategy;
	protected IbexOptions options;
	
	public IbexExecutable(IbexOptions options) {
		this.options = options;
	}
	
	public void run() throws IOException {
		initializeBlackInterpreter();
		strategy.run();
	}
	
	public OperationalStrategy getStrategy() {
		return strategy;
	}
	
	private void initializeBlackInterpreter() throws IOException {
		options.getMatchDistributor().initialiseBlackInterpreter(this);
	}
}
