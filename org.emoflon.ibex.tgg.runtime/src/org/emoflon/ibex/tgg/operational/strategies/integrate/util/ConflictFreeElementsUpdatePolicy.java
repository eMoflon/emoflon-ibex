package org.emoflon.ibex.tgg.operational.strategies.integrate.util;

import java.util.Map;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.matches.ImmutableMatchContainer;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.ConflictContainer;
import org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer.PrecedenceGraph;
import org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer.PrecedenceNode;
import org.emoflon.ibex.tgg.operational.updatepolicy.IUpdatePolicy;

public class ConflictFreeElementsUpdatePolicy implements IUpdatePolicy {

	private PrecedenceGraph pg;
	private Map<ITGGMatch, ConflictContainer> conflicts;

	public ConflictFreeElementsUpdatePolicy(INTEGRATE integrate) {
		this.pg = integrate.precedenceGraph();
		this.conflicts = integrate.conflictHandler().getConflicts();
	}

	@Override
	public ITGGMatch chooseOneMatch(ImmutableMatchContainer matchContainer) {
		for (ITGGMatch match : matchContainer.getMatches()) {
			if (match.getType() != PatternType.FWD && match.getType() != PatternType.BWD)
				continue;

			boolean conflicted = false;
			for (PrecedenceNode requiredNode : pg.getNode(match).getRequires())
				if (conflicts.containsKey(requiredNode.getMatch())) {
					conflicted = true;
					break;
				}
			if (conflicted)
				continue;

			return match;
		}
		return null;
	}

}
