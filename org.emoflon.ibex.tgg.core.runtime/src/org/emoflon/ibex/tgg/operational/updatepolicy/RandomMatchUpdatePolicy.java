package org.emoflon.ibex.tgg.operational.updatepolicy;

import java.util.ArrayList;

import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.matches.ImmutableMatchContainer;

/**
 * If scalability is an issue then use {@link NextMatchUpdatePolicy} instead or
 * implement your own (efficient) strategy of randomising match selection.
 * 
 * @author anthonyanjorin
 */
public class RandomMatchUpdatePolicy extends UpdatePolicy {
	private ArrayList<IMatch> cache = new ArrayList<>();
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
	public IMatch chooseOneMatch(ImmutableMatchContainer matchContainer) {
		if (COUNT > REFRESH || cache.isEmpty()) {
			cache.clear();
			cache.addAll(matchContainer.getMatches());
			COUNT = 0;
		}

		int randomIndex = (int) (Math.random() * cache.size());
		IMatch m = cache.get(randomIndex);
		COUNT++;

		if (matchContainer.getMatches().contains(m))
			return m;
		else {
			cache.remove(m);
			return chooseOneMatch(matchContainer);
		}

	}

}