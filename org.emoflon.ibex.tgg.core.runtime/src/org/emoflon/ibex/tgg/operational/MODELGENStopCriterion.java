package org.emoflon.ibex.tgg.operational;

import java.util.HashMap;

import runtime.TGGRuleApplication;

public class MODELGENStopCriterion {

	private long startTime = System.currentTimeMillis();

	private long timeOutInMS = -1;

	private int currentSrcCount;
	private int maxSrcCount = -1;

	private int currentTrgCount;
	private int maxTrgCount = -1;

	private HashMap<String, Integer> maxRuleCount = new HashMap<>();

	private HashMap<String, Integer> currentRuleCount = new HashMap<>();

	public void setTimeOutInMS(long timeOutInMS) {
		this.timeOutInMS = timeOutInMS;
	}

	public void setMaxSrcCount(int maxSrcCount) {
		this.maxSrcCount = maxSrcCount;
	}

	public void setMaxTrgCount(int maxTrgCount) {
		this.maxTrgCount = maxTrgCount;
	}

	public void setMaxRuleCount(HashMap<String, Integer> maxRuleCount) {
		this.maxRuleCount = maxRuleCount;
	}

	protected boolean dont(String ruleName) {

		if (maxRuleCount.containsKey(ruleName) && currentRuleCount.get(ruleName) == maxRuleCount.get(ruleName))
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

	protected void update(TGGRuleApplication ra) {
		currentSrcCount += ra.getCreatedSrc().size();
		currentTrgCount += ra.getCreatedTrg().size();
		if (currentRuleCount.containsKey(ra.getName()))
			currentRuleCount.put(ra.getName(), currentRuleCount.get(ra.getName()) + 1);
		else
			currentRuleCount.put(ra.getName(), 1);
	}
}
