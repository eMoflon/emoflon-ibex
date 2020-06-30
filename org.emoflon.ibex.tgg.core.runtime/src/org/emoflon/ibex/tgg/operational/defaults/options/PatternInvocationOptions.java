package org.emoflon.ibex.tgg.operational.defaults.options;

import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

public class PatternInvocationOptions extends IbexSubOptions {
	
	private boolean usePatternInvocation;
	
	public PatternInvocationOptions(IbexOptions options) {
		super(options);
		
		usePatternInvocation = false;
	}
	
	public boolean usePatternInvocation() {
		return usePatternInvocation;
	}

	public IbexOptions usePatternInvocation(boolean value) {
		usePatternInvocation = value;
		return options;
	}
}
