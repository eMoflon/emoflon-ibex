package org.emoflon.ibex.tgg.operational.util;

import java.util.HashMap;
import java.util.Set;

public abstract class UpdatePolicy implements IUpdatePolicy{
	
	private int defaultBound = 0;
	private HashMap<String, Integer> complementRulesBounds = new HashMap<String, Integer>();

	public void setFixedBoundForComplementRule(String name, int bound) {
		complementRulesBounds.put(name, bound);
	}
	
	@Override
	public HashMap<String, Integer> getNumberOfApplications(Set<String> complementRules){
		HashMap<String, Integer> upperBounds = new HashMap<String, Integer>();
		complementRules.stream()
				        .forEach( name -> {
				                    if (complementRulesBounds.containsKey(name)) {
				                    	upperBounds.put(name, complementRulesBounds.get(name));
				                    } else {
				                    	upperBounds.put(name, defaultBound);
				                    }
				                });
		return upperBounds;
	}
	
	
/*	
	public int getRandomRuleApplicationUpperBound(String ruleName, TGGComplementRule rule) {
		int upperBound = rule.getRuleApplicationUpperBound();
		int lowerBound = rule.getRuleApplicationLowerBound();
		
		if(upperBound == -1)
			upperBound = getDefaultRuleApplicationUpperBound();
		
		if (complementRuleApplicationBounds.containsKey(ruleName)) {
			upperBound = Math.min(upperBound, complementRuleApplicationBounds.get(ruleName).get("upperBound"));
			lowerBound = Math.max(lowerBound, complementRuleApplicationBounds.get(ruleName).get("lowerBound"));
		}
		Random random = new Random();
		
		return lowerBound + random.nextInt(upperBound - lowerBound + 1);
	}
*/	
	
	public int getDefaultComplementRuleApplicationBound() {
		return defaultBound;
	}

	public void setDefaultComplementRuleApplicationBound(int defaultBound) {
		this.defaultBound = defaultBound;
	}

}
