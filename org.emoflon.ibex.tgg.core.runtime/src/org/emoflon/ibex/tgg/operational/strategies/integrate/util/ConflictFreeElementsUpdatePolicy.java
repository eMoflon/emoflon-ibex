package org.emoflon.ibex.tgg.operational.strategies.integrate.util;

import java.util.Map;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.matches.ImmutableMatchContainer;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.DeleteConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.extprecedencegraph.IntegrateMatchContainer;
import org.emoflon.ibex.tgg.operational.updatepolicy.IUpdatePolicy;

import precedencegraph.PrecedenceNode;

public class ConflictFreeElementsUpdatePolicy implements IUpdatePolicy {

	private IntegrateMatchContainer imc;
	private Map<ITGGMatch, DeleteConflict> conflicts;

	public ConflictFreeElementsUpdatePolicy(INTEGRATE opStrat) {
		this.imc = opStrat.getIntegrMatchContainer();
		this.conflicts = opStrat.getConflicts();
	}

	@Override
	public ITGGMatch chooseOneMatch(ImmutableMatchContainer matchContainer) {
		imc.update();
		for (ITGGMatch match : matchContainer.getMatches()) {
			if (match.getType() != PatternType.FWD && match.getType() != PatternType.BWD)
				continue;

			PrecedenceNode node = imc.getNode(match);
			if (node == null)
				throw new RuntimeException("There has to be a PrecedenceNode for each Match in the MatchContainer!");

			boolean conflicted = false;
			for (PrecedenceNode requiredNode : node.getRequires())
				if (conflicts.containsKey(imc.getMatch(requiredNode))) {
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
