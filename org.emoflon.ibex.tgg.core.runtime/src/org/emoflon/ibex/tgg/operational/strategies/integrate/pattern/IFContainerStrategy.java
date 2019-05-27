package org.emoflon.ibex.tgg.operational.strategies.integrate.pattern;

import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.fragments.MatchIntegrationFragment;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.AnalysedMatch;

public interface IFContainerStrategy {
	
	boolean hasNextMatch(INTEGRATE integrate);

	IMatch next(INTEGRATE integrate);
	
	boolean isIFApplicable(INTEGRATE integrate, MatchIntegrationFragment iF, AnalysedMatch analysedMatch);
	
}
