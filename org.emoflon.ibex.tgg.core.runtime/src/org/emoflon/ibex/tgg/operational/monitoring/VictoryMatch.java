package org.emoflon.ibex.tgg.operational.monitoring;

import org.emoflon.ibex.tgg.operational.matches.IMatch;

public class VictoryMatch {

    private IMatch match;
    private String matchName;
    private String blockingReason;

    public VictoryMatch(IMatch pMatch) {
	match = pMatch;
	matchName = match.getPatternName();
    }

    public IMatch getIMatch() {
	return match;
    }

    public String getName() {
	return matchName;
    }

    public void setBlockingReason(String pBlockingReason) {
	blockingReason = pBlockingReason;
    }

    public boolean isBlocked() {
	return blockingReason != null;
    }

    public String getBlockingReason() {
	return blockingReason;
    }
}
