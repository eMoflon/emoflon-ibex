package org.emoflon.ibex.tgg.operational.monitoring;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.emoflon.ibex.tgg.operational.matches.IMatch;

public class VictoryMatch {

    private static int currentStep;
    private static Map<String, List<VictoryMatch>> ruleSets = new HashMap<>();

    public static void startMatchCreation(int pBirthStep) {
	currentStep = pBirthStep;
	ruleSets.clear();
    }

    public static VictoryMatch newMatch(IMatch pMatch) {

	VictoryMatch vMatch = new VictoryMatch(pMatch, currentStep);

	List<VictoryMatch> ruleSet = ruleSets.get(pMatch.getRuleName());
	if (ruleSet == null) {
	    ruleSet = new LinkedList<VictoryMatch>();
	    ruleSets.put(pMatch.getRuleName(), ruleSet);
	}

	ruleSet.add(vMatch);

	return vMatch;
    }

    public static void finishMatchCreation() {
	ruleSets.forEach((ruleName, matches) -> {
	    if (matches.size() > 1)
		for (int i = 0; i < matches.size(); i++)
		    matches.get(i).setName(ruleName + " #" + currentStep + "." + i);
	});
    }

    private IMatch match;
    private String matchName;
    private String blockingReason;

    private VictoryMatch(IMatch pMatch, int pBirthStep) {
	match = pMatch;
	matchName = match.getRuleName() + " #" + pBirthStep;
    }

    public IMatch getIMatch() {
	return match;
    }

    public String getName() {
	return matchName;
    }

    private void setName(String pName) {
	matchName = pName;
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
