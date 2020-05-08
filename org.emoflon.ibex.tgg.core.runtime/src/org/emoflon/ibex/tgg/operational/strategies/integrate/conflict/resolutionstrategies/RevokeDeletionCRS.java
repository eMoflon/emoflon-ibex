package org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.resolutionstrategies;

import java.util.HashSet;
import java.util.Set;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.DeleteConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer.IntegrateMatchContainer;

/**
 * A {@link DeleteConflictResStrategy} that completely revokes the deletion to
 * resolve the conflict.
 *
 */
public class RevokeDeletionCRS extends DeleteConflictResStrategy {

	protected Set<ITGGMatch> restored;

	public RevokeDeletionCRS(DeleteConflict conflict) {
		super(conflict);
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
		IntegrateMatchContainer matchContainer = integrate.getIntegrMatchContainer();
		matchContainer.getNode(match).getBaseFor().forEach(n -> {
			if (n.isBroken()) {
				ITGGMatch m = matchContainer.getMatch(n);
				if (!restored.contains(m)) {
					restoreMatch(integrate, m);
					restored.add(m);
					restoreMatchesBasedOn(integrate, m);
				}
			}
		});
	}

}
