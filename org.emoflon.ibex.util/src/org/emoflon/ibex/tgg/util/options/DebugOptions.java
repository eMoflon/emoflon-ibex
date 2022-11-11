package org.emoflon.ibex.tgg.util.options;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.emoflon.ibex.tgg.operational.benchmark.BenchmarkLogger;
import org.emoflon.ibex.tgg.operational.benchmark.EmptyBenchmarkLogger;
import org.emoflon.ibex.util.config.IbexOptions;

public class DebugOptions extends IbexSubOptions {
	
	private boolean ibexDebug;
	private BenchmarkLogger logger;

	public DebugOptions(IbexOptions options) {
		super(options);
		
		ibexDebug = Logger.getRootLogger().getLevel() == Level.DEBUG;
		logger = new EmptyBenchmarkLogger();
	}
	
	public IbexOptions ibexDebug(boolean ibexDebug) {
		this.ibexDebug = ibexDebug;
		return options;
	}

	public boolean ibexDebug() {
		return ibexDebug;
	}

	public BenchmarkLogger benchmarkLogger() {
		return logger;
	}

	public IbexOptions benchmarkLogger(BenchmarkLogger logger) {
		this.logger = logger;
		return options;
	}

}
