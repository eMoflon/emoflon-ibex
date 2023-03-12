package org.emoflon.ibex.tgg.runtime.repair.strategies;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.TGGRuleApplication;

public interface RepairStrategy {

	Collection<ITGGMatch> repair(RepairApplicationPoint applPoint);

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
