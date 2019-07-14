package org.emoflon.ibex.tgg.operational.monitoring;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.matches.ImmutableMatchContainer;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;
import org.emoflon.ibex.tgg.operational.updatepolicy.IUpdatePolicy;

public abstract class IbexController implements IbexObserver, IUpdatePolicy {

	HashMap<IMatch, Integer> processedMatches = new HashMap<IMatch, Integer>();
	Map<IMatch, String> newMatches = new HashMap<IMatch, String>();
	int step = 0;
	
	public void register(OperationalStrategy pOperationalStrategy) {
		pOperationalStrategy.registerObserver(this);
		pOperationalStrategy.setUpdatePolicy(this);
    }

    @Override
    public final IMatch chooseOneMatch(ImmutableMatchContainer matchContainer) {

    	this.processMatches(matchContainer);
    	
    	for (Map.Entry<IMatch, Integer> item : processedMatches.entrySet()) {
			newMatches.put(item.getKey(), (item.getValue().toString()));
        }

    	return chooseOneMatch(new VictoryDataPackage(newMatches, null)); // TODO add protocol here
    }
    
    public Map<IMatch,Collection<IMatch>> getMoreMatches(int amount) {
	
	/*
	 * TODO implement
	 * This method needs to provide the specified number of additional matches.
	 * This method will be called by the UI when the user requests additional
	 * 	matches beyond those that were given to the UI in the method above.
	 * The structure of the map is the same as described above.
	 */
	
	return null;
    }

    public abstract IMatch chooseOneMatch(VictoryDataPackage pDataPackage);
    
    protected abstract int getRequestedMatchCount();
   
    public void processMatches(ImmutableMatchContainer matchContainer) {
    	Set<IMatch> matches = matchContainer.getMatches();
    	step = step + 1;
    	for(IMatch match : matches) {
            if (!processedMatches.isEmpty()) {
                if(!processedMatches.containsKey(match)) {
                    processedMatches.put(match, step);
                }
            } else {
            	processedMatches.put(match, step);
            }
        }
    }
}
