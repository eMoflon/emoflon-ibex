package org.emoflon.ibex.tgg.operational.monitoring;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.matches.ImmutableMatchContainer;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;
import org.emoflon.ibex.tgg.operational.updatepolicy.IUpdatePolicy;

public abstract class IbexController implements IbexObserver, IUpdatePolicy {

    private Map<IMatch, VictoryMatch> matchMapping = new HashMap<>();
    
    public void register(OperationalStrategy pOperationalStrategy) {
	pOperationalStrategy.registerObserver(this);
	pOperationalStrategy.setUpdatePolicy(this);
    }

    @Override
    public final IMatch chooseOneMatch(ImmutableMatchContainer matchContainer) {

	updateMatchMapping(matchContainer.getMatches());
	
	return chooseOneMatch(new VictoryDataPackage(matchMapping.values(), null));
    }
    
    public Collection<VictoryMatch> getMoreMatches(int amount) {
	
	/*
	 * TODO implement
	 * This method needs to provide the specified number of additional matches.
	 * This method will be called by the UI when the user requests additional
	 * 	matches beyond those that were given to the UI in the method above.
	 * The structure of the map is the same as described above.
	 */
	
	return null;
    }
    
    private void updateMatchMapping(Collection<IMatch> pMatches) {
	
	for(Iterator<IMatch> iterator = matchMapping.keySet().iterator(); iterator.hasNext();)
	    if(!pMatches.contains(iterator.next()))
		iterator.remove();
	
	for(IMatch match : pMatches) 
	    if(!matchMapping.containsKey(match))
		matchMapping.put(match, new VictoryMatch(match));
    }

    public abstract IMatch chooseOneMatch(VictoryDataPackage pDataPackage);
    
    protected abstract int getRequestedMatchCount();
}
