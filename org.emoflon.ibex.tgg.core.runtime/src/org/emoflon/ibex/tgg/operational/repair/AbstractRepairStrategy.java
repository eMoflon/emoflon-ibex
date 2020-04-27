package org.emoflon.ibex.tgg.operational.repair;

import java.util.Collection;
import java.util.Map;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.PropagationDirection;

import runtime.TGGRuleApplication;

public interface AbstractRepairStrategy {
	
	ITGGMatch repair(ITGGMatch repairCandidate);
	
	ITGGMatch repair(ITGGMatch repairCandidate, PropagationDirection direction);

	Collection<ITGGMatch> chooseMatches(Map<TGGRuleApplication, ITGGMatch> brokenRuleApplications);
	
}
