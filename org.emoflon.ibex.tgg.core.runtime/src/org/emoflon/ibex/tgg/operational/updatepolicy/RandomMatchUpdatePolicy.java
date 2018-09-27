package org.emoflon.ibex.tgg.operational.updatepolicy;

import java.util.ArrayList;

import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.matches.ImmutableMatchContainer;

/**
 * Note: This can be rather inefficient! If scalability is an issue then use
 * {@link NextMatchUpdatePolicy} instead or implement your own (efficient)
 * strategy of randomising match selection.
 * 
 * @author anthonyanjorin
 */
public class RandomMatchUpdatePolicy extends UpdatePolicy {

	@Override
	public IMatch chooseOneMatch(ImmutableMatchContainer matchContainer) {
		ArrayList<IMatch> all = new ArrayList<>();
		all.addAll(matchContainer.getMatches());
		int randomIndex = (int) (Math.random() * all.size());
		return all.get(randomIndex);
	}

}