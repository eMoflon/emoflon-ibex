package org.emoflon.ibex.tgg.operational.strategies.modules;

import java.io.IOException;

import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;

public abstract class IbexExecutable {

	protected OperationalStrategy strategy;
	
	public void run() throws IOException {
		strategy.run();
	}
	
	public OperationalStrategy getStrategy() {
		return strategy;
	}
}
