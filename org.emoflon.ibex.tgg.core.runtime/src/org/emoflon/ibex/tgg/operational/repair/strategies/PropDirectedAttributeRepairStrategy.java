package org.emoflon.ibex.tgg.operational.repair.strategies;

import java.util.Collection;
import java.util.Collections;

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
	public Collection<ITGGMatch> repair(ITGGMatch repairCandidate) {
		return Collections.singletonList( //
				repair(repairCandidate, determineCSP(propDirHolder.get(), opStrat.getGreenFactories().get(repairCandidate), repairCandidate)) //
		);
	}

}
