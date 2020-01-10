package org.emoflon.ibex.tgg.operational.strategies.integrate.classification;

import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.Mismatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.MatchAnalyser.EltFilter;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.MatchAnalysis;

public class DEL_Partly extends MatchClassificationComponent {

	DEL_Partly() {
	}

	private final MCPattern pattern = MCPattern.DEL_PARTLY;

	@Override
	public Mismatch classify(INTEGRATE integrate, MatchAnalysis analysis) {
		Mismatch mismatch = new Mismatch(analysis.getMatch(), this);

		EltFilter ef = new EltFilter().srcAndTrg().create();
		classifyElts(integrate, mismatch, analysis.getElts(ef.deleted()), EltClassifier.REWARDLESS_USE);
		classifyElts(integrate, mismatch, analysis.getElts(ef.notDeleted()), EltClassifier.POTENTIAL_USE);

		return mismatch;
	}

	@Override
	public boolean isApplicable(MatchAnalysis analysis) {
		return pattern.matches(analysis.getModPattern());
	}

}
