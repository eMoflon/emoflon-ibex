package org.emoflon.ibex.tgg.operational.strategies.gen;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

import org.apache.log4j.Logger;

import language.TGG;
import language.TGGRule;

public class MODELGENStopCriterion {
	private static Logger logger = Logger.getLogger(MODELGENStopCriterion.class); 
	
	private long startTime = System.currentTimeMillis();
	private long currentIntervalStart = startTime;
	private final long INTERVAL_LENGTH = 5000; 

	private TGG tgg;

	private long timeOutInMS = -1;

	private int maxElementCount = -1;

	private int currentSrcCount;
	private int maxSrcCount = -1;

	private int currentTrgCount;
	private int maxTrgCount = -1;

	private HashMap<String, Integer> maxRuleCount = new HashMap<>();

	private HashMap<String, Integer> currentRuleCount = new HashMap<>();

	private HashSet<String> abstractRules = new HashSet<>();

	public MODELGENStopCriterion(TGG tgg) {
		this.tgg = tgg;

		for (TGGRule rule : tgg.getRules()) {
			if (rule.isAbstract())
				abstractRules.add(rule.getName());
		}
	}

	public void setTimeOutInMS(long timeOutInMS) {
		this.timeOutInMS = timeOutInMS;
	}

	public void setMaxElementCount(int maxElementCount) {
		this.maxElementCount = maxElementCount;
	}

	public void setMaxSrcCount(int maxSrcCount) {
		this.maxSrcCount = maxSrcCount;
	}

	public void setMaxTrgCount(int maxTrgCount) {
		this.maxTrgCount = maxTrgCount;
	}

	public void setMaxRuleCount(String ruleName, int maxNoOfApplications) {
		Optional<TGGRule> ruleWithMaxCount = tgg.getRules().stream()//
				.filter(r -> r.getName().contentEquals(ruleName))//
				.findAny();

		TGGRule r = ruleWithMaxCount//
				.orElseThrow(() -> new IllegalArgumentException(
						ruleName + " is not a name of a TGG rule in " + tgg.getName()));

		if (r.isAbstract() && maxNoOfApplications > 0)
			throw new IllegalArgumentException(
					"It makes no sense to set a max count for " + ruleName + ", as it is abstract.");

		maxRuleCount.put(ruleName, maxNoOfApplications);
	}

	protected boolean dont(String ruleName) {
		// prevent abstract rules from being applied
		if (abstractRules.contains(ruleName))
			return true;

		// prevent rule from being applied if max number of applications is reached
		if (maxRuleCount.containsKey(ruleName) && ((maxRuleCount.get(ruleName).equals(Integer.valueOf(0))
				|| maxRuleCount.get(ruleName).equals(currentRuleCount.get(ruleName)))))
			return true;

		return dont();
	}

	protected boolean dont() {
		if (maxSrcCount != -1 && maxSrcCount <= currentSrcCount)
			return true;

		if (maxTrgCount != -1 && maxTrgCount <= currentTrgCount)
			return true;

		if (maxElementCount != -1 && maxElementCount <= currentSrcCount + currentTrgCount)
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
		
		logUpdate();
	}

	private void logUpdate() {
		if(System.currentTimeMillis() - currentIntervalStart > INTERVAL_LENGTH) {
			logger.info("Generated " + (currentSrcCount + currentTrgCount) + " elements...");
			currentIntervalStart = System.currentTimeMillis();
		}
	}
}
