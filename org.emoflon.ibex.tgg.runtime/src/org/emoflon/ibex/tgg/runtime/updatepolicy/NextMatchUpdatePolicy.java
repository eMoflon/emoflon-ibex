package org.emoflon.ibex.tgg.runtime.updatepolicy;

import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.runtime.matches.container.ImmutableMatchContainer;

public final class NextMatchUpdatePolicy implements IUpdatePolicy {

	@Override
	public ITGGMatch chooseOneMatch(ImmutableMatchContainer matchContainer) {
		return matchContainer.getNext();
	}
}
