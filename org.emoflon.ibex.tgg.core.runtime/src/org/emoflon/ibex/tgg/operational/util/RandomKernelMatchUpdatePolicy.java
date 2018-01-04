package org.emoflon.ibex.tgg.operational.util;

public class RandomKernelMatchUpdatePolicy extends UpdatePolicy {
	
	IbexOptions options;
	
	@Override
	public IMatch chooseOneMatch(ImmutableMatchContainer matchContainer) {
		return matchContainer.getNextRandomKernel(options.tgg().getRules());
	}
	
	public void setOptions(IbexOptions options) {
		this.options = options;
	}
}