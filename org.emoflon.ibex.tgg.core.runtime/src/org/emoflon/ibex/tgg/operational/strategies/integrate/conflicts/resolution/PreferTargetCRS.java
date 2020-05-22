package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.AttributeConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChangeUtil;

public class PreferTargetCRS extends AttributeCRS {

	public PreferTargetCRS(AttributeConflict conflict) {
		super(conflict);
	}

	@Override
	public void apply(INTEGRATE integrate) {
		ModelChangeUtil.revertAttributeChange(conflict.getSrcChange());
		integrate.repairOneMatch(integrate.getAttributeRepairStrategy(), conflict.getMatch(), PatternType.BWD);
	}

}
