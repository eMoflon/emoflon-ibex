package org.emoflon.ibex.tgg.operational.strategies.integrate.classification;

import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE.INTEGRATE_Op;
import org.emoflon.ibex.tgg.operational.strategies.integrate.Mismatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.AnalysedMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.AnalysedMatch.EltFilter;

public class DEL_Partly extends MatchClassificationComponent {

	DEL_Partly() {
	}

	private final MCPattern pattern = MCPattern.DEL_PARTLY;

	@Override
	public Mismatch classify(INTEGRATE_Op integrate, AnalysedMatch analysedMatch) {
		Mismatch mismatch = new Mismatch(analysedMatch, this);

		EltFilter ef = new EltFilter().srcAndTrg().create();
		classifyElts(integrate, mismatch, analysedMatch.getElts(ef.deleted()), EltClassifier.REWARDLESS_USE);
		classifyElts(integrate, mismatch, analysedMatch.getElts(ef.notDeleted()), EltClassifier.POTENTIAL_USE);

		return mismatch;
	}

	@Override
	public boolean isApplicable(AnalysedMatch analysedMatch) {
		return pattern.matches(analysedMatch.getModPattern());
	}

}
