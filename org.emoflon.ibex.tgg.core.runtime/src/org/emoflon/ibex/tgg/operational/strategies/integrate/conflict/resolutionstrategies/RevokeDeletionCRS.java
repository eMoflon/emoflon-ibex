package org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.resolutionstrategies;

import java.util.HashSet;
import java.util.Set;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.Conflict.ConflResStratToken;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.DeleteConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.extprecedencegraph.ExtPrecedenceGraph;

public class RevokeDeletionCRS extends DeleteConflictResStrategy {

	protected Set<ITGGMatch> restored;

	public RevokeDeletionCRS(DeleteConflict conflict, ConflResStratToken token) {
		super(conflict, token);
	}

	@Override
	public void apply(INTEGRATE integrate) {
		restored = new HashSet<>();
		conflict.getDeletionChain().foreachReverse(match -> {
			if (!restored.contains(match)) {
				restoreMatch(integrate, match);
				restored.add(match);
				restoreMatchesBasedOn(integrate, match);
			}
		});
	}

	protected void restoreMatchesBasedOn(INTEGRATE integrate, ITGGMatch match) {
		ExtPrecedenceGraph epg = integrate.getEPG();
		epg.getNode(match).getBaseFor().forEach(n -> {
			if (n.isBroken()) {
				ITGGMatch m = epg.getMatch(n);
				if (!restored.contains(m)) {
					restoreMatch(integrate, m);
					restored.add(m);
					restoreMatchesBasedOn(integrate, m);
				}
			}
		});
	}

}
