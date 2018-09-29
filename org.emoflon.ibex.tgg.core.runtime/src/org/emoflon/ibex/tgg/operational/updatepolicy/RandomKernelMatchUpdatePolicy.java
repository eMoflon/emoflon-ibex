package org.emoflon.ibex.tgg.operational.updatepolicy;

import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.matches.ImmutableMatchContainer;

public class RandomKernelMatchUpdatePolicy extends UpdatePolicy {
	
	IbexOptions options;
	
	@Override
	public IMatch chooseOneMatch(ImmutableMatchContainer matchContainer) {
		return matchContainer.getNextKernel();
	}
	
	public void setOptions(IbexOptions options) {
		this.options = options;
	}
}