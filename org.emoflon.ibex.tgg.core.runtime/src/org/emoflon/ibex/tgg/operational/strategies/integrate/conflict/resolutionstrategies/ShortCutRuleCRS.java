package org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.resolutionstrategies;

import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE.INTEGRATE_Op;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.Conflict.ConflResStratToken;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.DeleteConflict;

public class ShortCutRuleCRS extends DeleteConflictResStrategy {

	public ShortCutRuleCRS(DeleteConflict conflict, ConflResStratToken token) {
		super(conflict, token);
	}

	@Override
	public void apply(INTEGRATE_Op integrate) {
		// TODO adrianm: implement
	}

}
