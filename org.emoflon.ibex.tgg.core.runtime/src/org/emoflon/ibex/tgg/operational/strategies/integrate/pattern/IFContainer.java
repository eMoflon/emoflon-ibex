package org.emoflon.ibex.tgg.operational.strategies.integrate.pattern;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.fragments.MatchIntegrationFragment;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.AnalysedMatch;

public class IFContainer extends LinkedList<MatchIntegrationFragment> implements IPComponent {

	private static final long serialVersionUID = 1L;

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
		List<IMatch> processed = new ArrayList<>();

		integrate.getBlackInterpreter().updateMatches();
		IMatch nextMatch = null;
		nextMatch = getNextMatch(integrate, processed);

		while (nextMatch != null) {
			AnalysedMatch analysedMatch = integrate.getMatchAnalyser().analyse(nextMatch);
			for (MatchIntegrationFragment iF : this) {
				if (iF.apply(analysedMatch, integrate))
					break;
			}
			nextMatch = getNextMatch(integrate, processed);
		}
	}

	private IMatch getNextMatch(INTEGRATE integrate, List<IMatch> processedMatches) {
		for (IMatch match : integrate.getBrokenMatches()) {
			if (!processedMatches.contains(match))
				return match;
		}
		return null;
	}
}
