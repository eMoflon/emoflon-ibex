package org.emoflon.ibex.tgg.operational.repair;

import java.util.Collection;
import java.util.Map;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;

import runtime.TGGRuleApplication;

public interface AbstractRepairStrategy {
	ITGGMatch repair(ITGGMatch iMatch);

	Collection<ITGGMatch> chooseMatches(Map<TGGRuleApplication, ITGGMatch> brokenRuleApplications);
}
