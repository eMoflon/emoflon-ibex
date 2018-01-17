package org.emoflon.ibex.tgg.operational.updatepolicy;

import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.matches.ImmutableMatchContainer;

public class NextMatchUpdatePolicy implements IUpdatePolicy {

	@Override
	public IMatch chooseOneMatch(ImmutableMatchContainer matchContainer) {
		return matchContainer.getNext();
	}
}
