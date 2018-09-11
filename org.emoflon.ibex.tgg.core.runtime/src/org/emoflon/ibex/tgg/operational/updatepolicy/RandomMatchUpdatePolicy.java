package org.emoflon.ibex.tgg.operational.updatepolicy;

import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.matches.ImmutableMatchContainer;

/**
 * For efficiency reasons relies on randomness of underlying set of matches. If
 * this is not satisfactory then subclass and implement your own randomness.
 * 
 * @author anthonyanjorin
 */
public class RandomMatchUpdatePolicy extends UpdatePolicy {

	@Override
	public IMatch chooseOneMatch(ImmutableMatchContainer matchContainer) {
		return matchContainer.getNext();
	}

}