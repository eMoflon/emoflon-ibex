package org.emoflon.ibex.tgg.operational.strategies.integrate.classification;

import org.emoflon.ibex.tgg.operational.strategies.integrate.Mismatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.AnalysedMatch;

public class DEL_Complete extends MatchClassificationComponent {

	DEL_Complete() {
	}

	private final MCPattern pattern = MCPattern.DEL_COMPLETE;

	@Override
	public Mismatch classify(AnalysedMatch analysedMatch) {
		return new Mismatch(analysedMatch.getMatch(), this);
	}

	@Override
	public boolean isApplicable(AnalysedMatch analysedMatch) {
		return pattern.matches(analysedMatch.getModPattern());
	}

}
