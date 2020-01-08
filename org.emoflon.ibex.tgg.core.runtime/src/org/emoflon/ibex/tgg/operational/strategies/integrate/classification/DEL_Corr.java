package org.emoflon.ibex.tgg.operational.strategies.integrate.classification;

import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.Mismatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.AnalysedMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.AnalysedMatch.EltFilter;

public class DEL_Corr extends MatchClassificationComponent {

	DEL_Corr() {
	}

	private final MCPattern pattern = MCPattern.DEL_CORR;

	@Override
	public Mismatch classify(INTEGRATE integrate, AnalysedMatch analysedMatch) {
		Mismatch mismatch = new Mismatch(analysedMatch, this);

		EltFilter ef = new EltFilter().srcAndTrg().create();
		classifyElts(integrate, mismatch, analysedMatch.getElts(ef), EltClassifier.USE);

		return mismatch;
	}

	@Override
	public boolean isApplicable(AnalysedMatch analysedMatch) {
		return pattern.matches(analysedMatch.getModPattern());
	}

}