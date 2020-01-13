package org.emoflon.ibex.tgg.operational.strategies.integrate.util;

import java.util.Map;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.matches.ImmutableMatchContainer;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.DeleteConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.extprecedencegraph.ExtPrecedenceGraph;
import org.emoflon.ibex.tgg.operational.updatepolicy.IUpdatePolicy;

import precedencegraph.PrecedenceNode;

public class ConflictFreeElementsUpdatePolicy implements IUpdatePolicy {

	private ExtPrecedenceGraph epg;
	private Map<ITGGMatch, DeleteConflict> conflicts;

	public ConflictFreeElementsUpdatePolicy(INTEGRATE opStrat) {
		this.epg = opStrat.getEPG();
		this.conflicts = opStrat.getConflicts();
	}

	@Override
	public ITGGMatch chooseOneMatch(ImmutableMatchContainer matchContainer) {
		epg.update();
		for (ITGGMatch match : matchContainer.getMatches()) {
			if (match.getType() != PatternType.FWD && match.getType() != PatternType.BWD)
				continue;

			PrecedenceNode node = epg.getNode(match);
			if (node == null)
				throw new RuntimeException("There has to be a PrecedenceNode for each Match in the MatchContainer!");

			boolean conflicted = false;
			for (PrecedenceNode requiredNode : node.getRequires())
				if (conflicts.containsKey(epg.getMatch(requiredNode))) {
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
