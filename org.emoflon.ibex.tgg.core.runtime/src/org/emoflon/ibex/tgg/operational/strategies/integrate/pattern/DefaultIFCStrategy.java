package org.emoflon.ibex.tgg.operational.strategies.integrate.pattern;

import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.fragments.MatchIntegrationFragment;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.AnalysedMatch;

public class DefaultIFCStrategy implements IFContainerStrategy {

	@Override
	public boolean hasNextMatch(INTEGRATE integrate) {
		return !integrate.getBrokenMatches().isEmpty();
	}

	@Override
	public IMatch next(INTEGRATE integrate) {
		return integrate.getBrokenMatches().stream().findFirst().get();
	}

	@Override
	public boolean isIFApplicable(INTEGRATE integrate, MatchIntegrationFragment iF, AnalysedMatch analysedMatch) {
		return true;
	}

}
