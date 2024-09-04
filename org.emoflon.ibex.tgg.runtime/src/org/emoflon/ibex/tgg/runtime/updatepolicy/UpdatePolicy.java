package org.emoflon.ibex.tgg.runtime.updatepolicy;

import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public abstract class UpdatePolicy implements IUpdatePolicy{
	
	private int defaultBound = 0;
	private HashMap<String, Integer> fixedRulesBounds = new HashMap<String, Integer>();
	private HashMap<String, Integer> randomRulesBounds = new HashMap<String, Integer>();

	public void setBoundForComplementRule(String name, int bound, boolean fixed) {
		if (fixed)
			fixedRulesBounds.put(name, bound);
		else {
			randomRulesBounds.put(name, bound);
		}
	}
	
	@Override
	public HashMap<String, Integer> getNumberOfApplications(Set<String> complementRules){
		HashMap<String, Integer> upperBounds = new HashMap<String, Integer>();
		complementRules.stream()
				        .forEach( name -> {
				                    if (fixedRulesBounds.containsKey(name)) {
				                    	upperBounds.put(name, fixedRulesBounds.get(name));
				                    } 
				                    else if (randomRulesBounds.containsKey(name)) {
				                    	upperBounds.put(name, getRandomUpperBound(name));
				                    }
				                    else {
				                    	upperBounds.put(name, defaultBound);
				                    }
				                });
		return upperBounds;
	}
	
	public int getRandomUpperBound(String name) {	
		Random random = new Random();
		return random.nextInt(randomRulesBounds.get(name) + 1);
	}
	
	public int getDefaultComplementRuleApplicationBound() {
		return defaultBound;
	}

	public void setDefaultComplementRuleApplicationBound(int defaultBound) {
		this.defaultBound = defaultBound;
	}

}
