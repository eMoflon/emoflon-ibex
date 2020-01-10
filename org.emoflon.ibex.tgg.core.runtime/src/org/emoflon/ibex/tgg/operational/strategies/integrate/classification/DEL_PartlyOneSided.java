package org.emoflon.ibex.tgg.operational.strategies.integrate.classification;

import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.Mismatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.MatchAnalyser.EltFilter;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.MatchAnalysis;

import language.DomainType;

public class DEL_PartlyOneSided extends MatchClassificationComponent {

	DEL_PartlyOneSided() {
	}

	private final MCPattern fwd = MCPattern.DEL_PARTLYONESIDED_FWD;
	private final MCPattern bwd = MCPattern.DEL_PARTLYONESIDED_FWD;

	@Override
	public Mismatch classify(INTEGRATE integrate, MatchAnalysis analysis) {
		DomainType delSide;
		if (fwd.matches(analysis.getModPattern())) {
			delSide = DomainType.SRC;
		} else if (bwd.matches(analysis.getModPattern())) {
			delSide = DomainType.TRG;
		} else
			return null;

		Mismatch mismatch = new Mismatch(analysis.getMatch(), this);

		EltFilter ef = new EltFilter().create();
		classifyElts(integrate, mismatch, analysis.getElts(ef.domains(oppositeOf(delSide))),
				EltClassifier.POTENTIAL_USE);
		classifyElts(integrate, mismatch, analysis.getElts(ef.domains(delSide).deleted()),
				EltClassifier.REWARDLESS_USE);
		classifyElts(integrate, mismatch, analysis.getElts(ef.notDeleted()), EltClassifier.POTENTIAL_USE);

		return mismatch;
	}

	@Override
	public boolean isApplicable(MatchAnalysis analysis) {
		return fwd.matches(analysis.getModPattern()) || bwd.matches(analysis.getModPattern());
	}

}
