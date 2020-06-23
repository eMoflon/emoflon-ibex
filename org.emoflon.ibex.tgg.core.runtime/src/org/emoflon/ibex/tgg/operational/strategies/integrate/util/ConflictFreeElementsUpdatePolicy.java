package org.emoflon.ibex.tgg.operational.strategies.integrate.util;

import java.util.Map;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.matches.ImmutableMatchContainer;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.ConflictContainer;
import org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer.PrecedenceGraphContainer;
import org.emoflon.ibex.tgg.operational.updatepolicy.IUpdatePolicy;

import precedencegraph.PrecedenceNode;

public class ConflictFreeElementsUpdatePolicy implements IUpdatePolicy {

	private PrecedenceGraphContainer pgc;
	private Map<ITGGMatch, ConflictContainer> conflicts;

	public ConflictFreeElementsUpdatePolicy(INTEGRATE integrate) {
		this.pgc = integrate.getPrecedenceGraphContainer();
		this.conflicts = integrate.getConflicts();
	}

	@Override
	public ITGGMatch chooseOneMatch(ImmutableMatchContainer matchContainer) {
		for (ITGGMatch match : matchContainer.getMatches()) {
			if (match.getType() != PatternType.FWD && match.getType() != PatternType.BWD)
				continue;

			boolean conflicted = false;
			for (PrecedenceNode requiredNode : pgc.getNode(match).getRequires())
				if (conflicts.containsKey(pgc.getMatch(requiredNode))) {
					conflicted = true;
					break;
				}
			if(conflicted)
				continue;

			return match;
		}
		return null;
	}

}
