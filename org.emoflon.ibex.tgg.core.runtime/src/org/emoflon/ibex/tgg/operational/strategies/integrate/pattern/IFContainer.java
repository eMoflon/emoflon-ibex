package org.emoflon.ibex.tgg.operational.strategies.integrate.pattern;

import java.util.LinkedList;

import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.fragments.MatchIntegrationFragment;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.AnalysedMatch;

public class IFContainer extends LinkedList<MatchIntegrationFragment> implements IPComponent {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private IFContainerStrategy strategy;

	public IFContainer(IFContainerStrategy strategy) {
		this.strategy = strategy;
	}

	@Override
	public boolean apply(INTEGRATE integrate) {
		classifyElements(integrate);
		return true;
	}
	
	private void classifyElements(INTEGRATE integrate) {
		integrate.getBlackInterpreter().updateMatches();
		for(IMatch brokenMatch : integrate.getBrokenMatches()) {
			AnalysedMatch analysedMatch = integrate.getMatchAnalyser().analyse(brokenMatch);
			for(MatchIntegrationFragment iF : this) {
				if(iF.softApply(analysedMatch, integrate))
					break;
			}
			integrate.getAnalysedMatches().put(brokenMatch, analysedMatch);
		}
	}
}
