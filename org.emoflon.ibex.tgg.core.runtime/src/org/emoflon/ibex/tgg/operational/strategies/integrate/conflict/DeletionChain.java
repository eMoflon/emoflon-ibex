package org.emoflon.ibex.tgg.operational.strategies.integrate.conflict;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.extprecedencegraph.ExtPrecedenceGraph;

import precedencegraph.Node;

public class DeletionChain {

	private ExtPrecedenceGraph epg;
	
	private Map<IMatch, Set<IMatch>> chain;
	private IMatch first;
	private Set<IMatch> last;

	DeletionChain(INTEGRATE integrate, IMatch brokenMatch) {
		this.epg = integrate.getEPG();
		this.chain = new LinkedHashMap<>();
		this.first = brokenMatch;
		this.last = new HashSet<>();
		concludeDeletionChain(brokenMatch);
		chain.forEach((m, s) -> {
			if(s.isEmpty())
				last.add(m);
		});
	}

	private void concludeDeletionChain(IMatch currentMatch) {
		chain.computeIfAbsent(currentMatch, m -> {
			Set<IMatch> set = new HashSet<>();
			Node currentNode = epg.getNode(currentMatch);
			currentNode.getBasedOn().forEach(n -> {
				if (n.isBroken())
					set.add(epg.getMatch(n));
			});
			return set;
		});

		chain.get(currentMatch).forEach(m -> concludeDeletionChain(m));
	}

	public IMatch getFirst() {
		return first;
	}

	public Set<IMatch> getLast() {
		return last;
	}
	
	public Set<IMatch> getNext(IMatch match) {
		return chain.get(match);
	}
	
	public void foreach(Consumer<? super IMatch> action) {
		chain.keySet().forEach(action);
	}
 
}
