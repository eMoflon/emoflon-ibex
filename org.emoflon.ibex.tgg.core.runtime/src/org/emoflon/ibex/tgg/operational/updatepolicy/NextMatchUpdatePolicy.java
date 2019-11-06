package org.emoflon.ibex.tgg.operational.updatepolicy;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.matches.ImmutableMatchContainer;

public class NextMatchUpdatePolicy implements IUpdatePolicy {

	@Override
	public ITGGMatch chooseOneMatch(ImmutableMatchContainer matchContainer) {
		return matchContainer.getNext();
	}
}
