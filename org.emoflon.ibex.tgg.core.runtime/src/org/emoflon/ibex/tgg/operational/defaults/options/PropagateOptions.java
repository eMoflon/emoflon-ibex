package org.emoflon.ibex.tgg.operational.defaults.options;

import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

public class PropagateOptions extends IbexSubOptions {
	
	private boolean applyConcurrently;
	private boolean optimizeSyncPattern;

	public PropagateOptions(IbexOptions options) {
		super(options);
		
		applyConcurrently = false;
		optimizeSyncPattern = false;
	}
	
	public boolean applyConcurrently() {
		return applyConcurrently;
	}

	public IbexOptions applyConcurrently(boolean applyConcurrently) {
		this.applyConcurrently = applyConcurrently;
		return options;
	}
	
	public boolean optimizeSyncPattern() {
		return optimizeSyncPattern;
	}

	public IbexOptions optimizeSyncPattern(boolean optimizeSyncPattern) {
		this.optimizeSyncPattern = optimizeSyncPattern;
		return options;
	}

}
