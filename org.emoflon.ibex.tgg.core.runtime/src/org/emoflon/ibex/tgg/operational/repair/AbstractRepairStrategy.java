package org.emoflon.ibex.tgg.operational.repair;

import java.util.Collection;
import java.util.Map;

import org.emoflon.ibex.tgg.operational.matches.IMatch;

import runtime.TGGRuleApplication;

public interface AbstractRepairStrategy {
	IMatch repair(IMatch iMatch);

	Collection<IMatch> chooseMatches(Map<TGGRuleApplication, IMatch> brokenRuleApplications);
}
