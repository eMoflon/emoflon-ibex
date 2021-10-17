package org.emoflon.ibex.tgg.operational.repair.strategies;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;

public interface PropagationDirectedRepairStrategy extends RepairStrategy {
	
	ITGGMatch repair(ITGGMatch repairCandidate);
	
}
