package org.emoflon.ibex.tgg.operational.repair.strategies;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;

import runtime.TGGRuleApplication;

public interface RepairStrategy {

	ITGGMatch repair(ITGGMatch repairCandidate, PatternType type);

	default Collection<ITGGMatch> chooseMatches(Map<TGGRuleApplication, ITGGMatch> brokenRuleApplications) {
		return brokenRuleApplications.keySet() //
				.stream() //
				.filter(this::noMissingNodes) //
				.map(brokenRuleApplications::get) //
				.collect(Collectors.toList());
	}

	default boolean noMissingNodes(TGGRuleApplication ra) {
		return true;
	}

}
