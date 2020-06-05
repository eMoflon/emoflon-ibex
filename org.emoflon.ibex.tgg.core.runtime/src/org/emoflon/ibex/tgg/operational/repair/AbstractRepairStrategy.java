package org.emoflon.ibex.tgg.operational.repair;

import java.util.Collection;
import java.util.Map;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;

import runtime.TGGRuleApplication;

public interface AbstractRepairStrategy {
	
	ITGGMatch repair(ITGGMatch repairCandidate);
	
	ITGGMatch repair(ITGGMatch repairCandidate, PatternType type);

	Collection<ITGGMatch> chooseMatches(Map<TGGRuleApplication, ITGGMatch> brokenRuleApplications);
	
}
