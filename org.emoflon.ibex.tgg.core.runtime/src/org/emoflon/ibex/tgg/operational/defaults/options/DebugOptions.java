package org.emoflon.ibex.tgg.operational.defaults.options;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.emoflon.ibex.tgg.operational.benchmark.BenchmarkLogger;
import org.emoflon.ibex.tgg.operational.benchmark.EmptyBenchmarkLogger;
import org.emoflon.ibex.tgg.operational.debug.LoggerConfig;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

public class DebugOptions extends IbexSubOptions {
	
	private boolean ibexDebug;
	private LoggerConfig loggerConfig;
	private BenchmarkLogger logger;

	public DebugOptions(IbexOptions options) {
		super(options);
		
		ibexDebug = Logger.getRootLogger().getLevel() == Level.DEBUG;
		loggerConfig = new LoggerConfig();
		logger = new EmptyBenchmarkLogger();
	}
	
	public IbexOptions ibexDebug(boolean ibexDebug) {
		this.ibexDebug = ibexDebug;
		return options;
	}

	public boolean ibexDebug() {
		return ibexDebug;
	}
	
	public LoggerConfig loggerConfig() {
		return loggerConfig;
	}

	public IbexOptions loggerConfig(LoggerConfig loggerConfig) {
		this.loggerConfig = loggerConfig;
		return options;
	}

	public BenchmarkLogger benchmarkLogger() {
		return logger;
	}

	public IbexOptions benchmarkLogger(BenchmarkLogger logger) {
		this.logger = logger;
		return options;
	}

}
