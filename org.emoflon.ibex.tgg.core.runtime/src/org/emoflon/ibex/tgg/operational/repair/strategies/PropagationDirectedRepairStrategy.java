package org.emoflon.ibex.tgg.operational.repair.strategies;

import java.util.Collection;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;

public interface PropagationDirectedRepairStrategy extends RepairStrategy {
	
	Collection<ITGGMatch> repair(ITGGMatch repairCandidate);
	
}
