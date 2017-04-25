package org.emoflon.ibex.tgg.operational.strategies.gen;

import java.util.HashMap;

import language.TGG;

public class MODELGENStopCriterion {

	private long startTime = System.currentTimeMillis();

	private TGG tgg;
	
	private long timeOutInMS = -1;

	private int currentSrcCount;
	private int maxSrcCount = -1;

	private int currentTrgCount;
	private int maxTrgCount = -1;

	private HashMap<String, Integer> maxRuleCount = new HashMap<>();

	private HashMap<String, Integer> currentRuleCount = new HashMap<>();

	public MODELGENStopCriterion(TGG tgg) {
		this.tgg = tgg;
	}
	
	public void setTimeOutInMS(long timeOutInMS) {
		this.timeOutInMS = timeOutInMS;
	}

	public void setMaxSrcCount(int maxSrcCount) {
		this.maxSrcCount = maxSrcCount;
	}

	public void setMaxTrgCount(int maxTrgCount) {
		this.maxTrgCount = maxTrgCount;
	}

	public void setMaxRuleCount(String ruleName, int maxNoOfApplications) {
		if(tgg.getRules().stream().noneMatch(r -> r.getName().contentEquals(ruleName)))
			throw new IllegalArgumentException(ruleName + " is not a name of a TGG rule in " + tgg.getName());
			
		maxRuleCount.put(ruleName, maxNoOfApplications);
	}

	protected boolean dont(String ruleName) {

		if (maxRuleCount.containsKey(ruleName) && (maxRuleCount.get(ruleName) == 0 || currentRuleCount.get(ruleName) == maxRuleCount.get(ruleName)))
			return true;
		
		return dont();
	}

	protected boolean dont() {
		if (maxSrcCount != -1 && maxSrcCount <= currentSrcCount)
			return true;

		if (maxTrgCount != -1 && maxTrgCount <= currentTrgCount)
			return true;

		if (timeOutInMS != -1 && System.currentTimeMillis() - startTime > timeOutInMS)
			return true;

		return false;
	}

	protected void update(String ruleName, int srcElts, int trgElts) {
		currentSrcCount += srcElts;
		currentTrgCount += trgElts;
		if (currentRuleCount.containsKey(ruleName))
			currentRuleCount.put(ruleName, currentRuleCount.get(ruleName) + 1);
		else
			currentRuleCount.put(ruleName, 1);
	}
}
