package org.emoflon.ibex.tgg.operational.monitoring;

import java.util.Collection;
import java.util.HashSet;

import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.matches.ImmutableMatchContainer;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;
import org.emoflon.ibex.tgg.operational.updatepolicy.IUpdatePolicy;

public abstract class IbexController implements IbexObserver, IUpdatePolicy {

    private OperationalStrategy operationalStrategy;

    public void register(OperationalStrategy pOperationalStrategy) {
	operationalStrategy = pOperationalStrategy;
	pOperationalStrategy.registerObserver(this);
	pOperationalStrategy.setUpdatePolicy(this);
    }

    @Override
    public final IMatch chooseOneMatch(ImmutableMatchContainer matchContainer) {

	Collection<VictoryMatch> matches = new HashSet<>();
	matchContainer.getMatches().forEach(match -> matches.add(new VictoryMatch(match)));
	if (operationalStrategy.getBlockedMatches() != null)
	    operationalStrategy.getBlockedMatches().forEach((match, reason) -> {
		VictoryMatch vMatch = new VictoryMatch(match);
		vMatch.setBlockingReason(reason);
		matches.add(vMatch);
	    });
	return chooseOneMatch(new VictoryDataPackage(matches, null)); // TODO add protocol here
    }

    public Collection<VictoryMatch> getMoreMatches(int amount) {

	/*
	 * TODO implement This method needs to provide the specified number of
	 * additional matches. This method will be called by the UI when the user
	 * requests additional matches beyond those that were given to the UI in the
	 * method above. The structure of the map is the same as described above.
	 */

	return null;
    }

    public abstract IMatch chooseOneMatch(VictoryDataPackage pDataPackage);

    protected abstract int getRequestedMatchCount();
}
