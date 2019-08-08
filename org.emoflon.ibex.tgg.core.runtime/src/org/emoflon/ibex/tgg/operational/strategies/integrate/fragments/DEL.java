package org.emoflon.ibex.tgg.operational.strategies.integrate.fragments;

import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.AnalysedMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.IFPattern;

public class DEL extends MatchIntegrationFragment {

	private final IFPattern pattern = IFPattern.DEL;

	@Override
	public boolean softApply(AnalysedMatch analysedMatch, INTEGRATE integrate) {
		if (!pattern.matches(analysedMatch.getModPattern()))
			return false;
	
		delGreenCorr(analysedMatch, getIbexRedInterpreter(integrate));
		return true;
	}

	@Override
	public boolean hardApply(AnalysedMatch analysedMatch, INTEGRATE integrate) {
		return softApply(analysedMatch, integrate);
	}

	@Override
	public boolean isApplicable(AnalysedMatch analysedMatch) {
		return pattern.matches(analysedMatch.getModPattern());
	}

}
