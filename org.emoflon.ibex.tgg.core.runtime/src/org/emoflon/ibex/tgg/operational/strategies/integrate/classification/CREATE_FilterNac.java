package org.emoflon.ibex.tgg.operational.strategies.integrate.classification;

import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.Mismatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.MatchAnalyser.EltFilter;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.MatchAnalysis;

public class CREATE_FilterNac extends MatchClassificationComponent {

	CREATE_FilterNac() {
	}

	@Override
	public Mismatch classify(INTEGRATE integrate, MatchAnalysis analysis) {
		Mismatch mismatch = new Mismatch(analysis.getMatch(), this);
		
		EltFilter ef = new EltFilter().srcAndTrg().create();
		classifyElts(integrate, mismatch, analysis.getElts(ef), EltClassifier.USE);

		return mismatch;
	}

	@Override
	public boolean isApplicable(MatchAnalysis analysis) {
		return !analysis.getFilterNacViolations().isEmpty();
	}

}
