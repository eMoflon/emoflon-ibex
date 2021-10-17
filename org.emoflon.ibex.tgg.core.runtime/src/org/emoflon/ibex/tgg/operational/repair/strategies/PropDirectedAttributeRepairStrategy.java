package org.emoflon.ibex.tgg.operational.repair.strategies;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.PropagatingOperationalStrategy;
import org.emoflon.ibex.tgg.operational.strategies.PropagationDirectionHolder;

public class PropDirectedAttributeRepairStrategy extends AttributeRepairStrategy implements PropagationDirectedRepairStrategy {

	private final PropagationDirectionHolder propDirHolder;

	public PropDirectedAttributeRepairStrategy(PropagatingOperationalStrategy opStrat, PropagationDirectionHolder propDirHolder) {
		super(opStrat);
		this.propDirHolder = propDirHolder;
	}

	@Override
	public ITGGMatch repair(ITGGMatch repairCandidate) {
		return repair(repairCandidate, determineCSP(propDirHolder.get(), getFactory(repairCandidate), repairCandidate));
	}

}
