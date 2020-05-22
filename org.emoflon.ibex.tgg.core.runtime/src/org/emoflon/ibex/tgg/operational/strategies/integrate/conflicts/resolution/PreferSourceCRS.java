package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.AttributeConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChangeUtil;

public class PreferSourceCRS extends AttributeCRS {

	public PreferSourceCRS(AttributeConflict conflict) {
		super(conflict);
	}

	@Override
	public void apply(INTEGRATE integrate) {
		ModelChangeUtil.revertAttributeChange(conflict.getTrgChange());
		integrate.repairOneMatch(integrate.getAttributeRepairStrategy(), conflict.getMatch(), PatternType.FWD);
	}

}
