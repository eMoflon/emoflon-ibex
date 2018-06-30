package org.emoflon.ibex.tgg.operational.strategies.sync.repair;

import java.util.Map;
import java.util.Optional;

import org.emoflon.ibex.tgg.operational.matches.IMatch;

import runtime.TGGRuleApplication;

public interface AbstractRepairStrategy {
	boolean repair(IMatch iMatch);

	Optional<IMatch> chooseOneMatch(Map<TGGRuleApplication, IMatch> brokenRuleApplications);
}
