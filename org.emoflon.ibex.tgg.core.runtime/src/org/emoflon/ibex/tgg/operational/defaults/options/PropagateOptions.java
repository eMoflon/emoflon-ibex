package org.emoflon.ibex.tgg.operational.defaults.options;

import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

public class PropagateOptions extends IbexSubOptions {
	
	private boolean applyConcurrently;
	private boolean usePrecedenceGraph;

	public PropagateOptions(IbexOptions options) {
		super(options);
		
		applyConcurrently = false;
		usePrecedenceGraph = true;
	}
	
	public boolean applyConcurrently() {
		return applyConcurrently;
	}

	public IbexOptions applyConcurrently(boolean applyConcurrently) {
		this.applyConcurrently = applyConcurrently;
		return options;
	}

	public boolean usePrecedenceGraph() {
		return usePrecedenceGraph;
	}

	public IbexOptions usePrecedenceGraph(boolean usePrecedenceGraph) {
		this.usePrecedenceGraph = usePrecedenceGraph;
		return options;
	}

}
