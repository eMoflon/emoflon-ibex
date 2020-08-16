package org.emoflon.ibex.tgg.operational.defaults.options;

import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

public class PatternInvocationOptions extends IbexSubOptions {
	
	private boolean usePatternInvocation;
	private boolean useGreenCorrPattern;
	
	public PatternInvocationOptions(IbexOptions options) {
		super(options);
		
		usePatternInvocation = false;
		useGreenCorrPattern = false;
	}
	
	public boolean usePatternInvocation() {
		return usePatternInvocation;
	}

	public IbexOptions usePatternInvocation(boolean value) {
		usePatternInvocation = value;
		return options;
	}
	
	public boolean useGreenCorrPattern() {
		return useGreenCorrPattern;
	}
	
	public IbexOptions useGreenCorrPattern(boolean value) {
		useGreenCorrPattern = value;
		return options;
	}
}
