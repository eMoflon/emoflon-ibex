package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer.IntegrateMatchContainer;

import precedencegraph.PrecedenceNode;

public class DeletionChain {

	private IntegrateMatchContainer matchContainer;

	private LinkedHashMap<ITGGMatch, Set<ITGGMatch>> chain;
	private ITGGMatch first;
	private Set<ITGGMatch> last;

	public DeletionChain(INTEGRATE integrate, ITGGMatch brokenMatch) {
		this.matchContainer = integrate.getIntegrMatchContainer();
		this.chain = new LinkedHashMap<>();
		this.first = brokenMatch;
		this.last = new HashSet<>();
		concludeDeletionChain(brokenMatch);
		chain.forEach((m, s) -> {
			if (s.isEmpty())
				last.add(m);
		});
	}

	private void concludeDeletionChain(ITGGMatch currentMatch) {
		chain.computeIfAbsent(currentMatch, m -> {
			Set<ITGGMatch> set = new HashSet<>();
			PrecedenceNode currentNode = matchContainer.getNode(currentMatch);
			currentNode.getRequires().forEach(n -> {
				if (n.isBroken())
					set.add(matchContainer.getMatch(n));
			});
			return set;
		});

		chain.get(currentMatch).forEach(m -> concludeDeletionChain(m));
	}

	public ITGGMatch getFirst() {
		return first;
	}

	public Set<ITGGMatch> getLast() {
		return last;
	}

	public Set<ITGGMatch> getNext(ITGGMatch match) {
		return chain.get(match);
	}

	public void foreach(Consumer<? super ITGGMatch> action) {
		chain.keySet().forEach(action);
	}

	public void foreachReverse(Consumer<? super ITGGMatch> action) {
		List<ITGGMatch> copiedKeySet = new ArrayList<ITGGMatch>(chain.keySet());
		Collections.reverse(copiedKeySet);
		copiedKeySet.forEach(action);
	}

}
