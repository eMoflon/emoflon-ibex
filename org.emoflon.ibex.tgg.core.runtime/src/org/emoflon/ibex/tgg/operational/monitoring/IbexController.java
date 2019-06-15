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
	
	public void register(OperationalStrategy pOperationalStrategy) {
		pOperationalStrategy.registerObserver(this);
		pOperationalStrategy.setUpdatePolicy(this);
    }

    @Override
    public final IMatch chooseOneMatch(ImmutableMatchContainer matchContainer) {
    	this.processMatches(matchContainer);
    	HashMap<IMatch, Integer> sortedMatches = this.sortByAge(processedMatches);
    	Map<IMatch, String> matches = this.assignUniqueName(sortedMatches);
    	
    	for(Map.Entry<IMatch, String> en: matches.entrySet()) {
    		System.out.println("Match: " + en.getKey().getPatternName() + " value: " + en.getValue());
    	} 
    	
		return chooseOneMatch(new VictoryDataPackage(matches, null)); // TODO add protocol here
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
		
		for(IMatch match : matches) {
            if (!processedMatches.isEmpty()) {
                if(processedMatches.containsKey(match)) {
                    int count = processedMatches.get(match);
                    count += 1;
                    processedMatches.put(match, count);
                } else {
                	processedMatches.put(match, 1);
                }
               
            } else {
            	processedMatches.put(match, 1);
            }
        }
	}
	
	public HashMap<IMatch, Integer> sortByAge(HashMap<IMatch, Integer> pm) { 
        List<Map.Entry<IMatch, Integer>> list = new LinkedList<Map.Entry<IMatch, Integer> >(pm.entrySet()); 

        Collections.sort(list, new Comparator<Map.Entry<IMatch, Integer> >() { 
            public int compare(Map.Entry<IMatch, Integer> o1,  
                               Map.Entry<IMatch, Integer> o2) 
            { 
                return (o1.getValue()).compareTo(o2.getValue()); 
            } 
        }); 

        HashMap<IMatch, Integer> temp = new LinkedHashMap<IMatch, Integer>(); 
        for (Map.Entry<IMatch, Integer> aa : list) { 
            temp.put(aa.getKey(), aa.getValue()); 
        } 
        return temp; 
    } 
	
	public Map<IMatch, String> assignUniqueName(HashMap<IMatch, Integer> hm) {
		Map<IMatch, String> newMap = new LinkedHashMap<>();
		
		for (Map.Entry<IMatch, Integer> en : hm.entrySet()) {
			newMap.put(en.getKey(), (en.getKey().getRuleName()+en.getValue()));
        }
		
		return newMap;
	} 
}
