package org.emoflon.ibex.tgg.operational.strategies.integrate.conflict;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE.INTEGRATE_Op;
import org.emoflon.ibex.tgg.operational.strategies.integrate.extprecedencegraph.ExtPrecedenceGraph;

import precedencegraph.PrecedenceNode;

public class DeletionChain {

	private ExtPrecedenceGraph epg;

	private LinkedHashMap<ITGGMatch, Set<ITGGMatch>> chain;
	private ITGGMatch first;
	private Set<ITGGMatch> last;

	DeletionChain(INTEGRATE_Op integrate, ITGGMatch brokenMatch) {
		this.epg = integrate.getEPG();
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
			PrecedenceNode currentNode = epg.getNode(currentMatch);
			currentNode.getBasedOn().forEach(n -> {
				if (n.isBroken())
					set.add(epg.getMatch(n));
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
