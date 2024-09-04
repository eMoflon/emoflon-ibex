package org.emoflon.ibex.tgg.runtime.updatepolicy;

import java.util.ArrayList;

import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.runtime.matches.container.ImmutableMatchContainer;

/**
 * If scalability is an issue then use {@link NextMatchUpdatePolicy} instead or
 * implement your own (efficient) strategy of randomising match selection.
 * 
 * @author anthonyanjorin
 */
public class RandomMatchUpdatePolicy extends UpdatePolicy {
	private ArrayList<ITGGMatch> cache = new ArrayList<>();
	private final int REFRESH;
	private int COUNT = 0;

	/**
	 * @param cacheSize Larger values increase efficiency, smaller values imply
	 *                  better randomised models.
	 */
	public RandomMatchUpdatePolicy(int cacheSize) {
		REFRESH = cacheSize;
	}

	@Override
	public ITGGMatch chooseOneMatch(ImmutableMatchContainer matchContainer) {
		if (COUNT > REFRESH || cache.isEmpty()) {
			cache.clear();
			cache.addAll(matchContainer.getMatches());
			COUNT = 0;
		}

		int randomIndex = (int) (Math.random() * cache.size());
		ITGGMatch m = cache.get(randomIndex);
		COUNT++;

		if (matchContainer.getMatches().contains(m))
			return m;
		else {
			cache.remove(m);
			return chooseOneMatch(matchContainer);
		}

	}

}