package org.emoflon.ibex.tgg.operational.strategies.integrate.classification;

import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.Mismatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.AnalysedMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.AnalysedMatch.EltFilter;

import language.DomainType;

public class DEL_PartlyOneSided extends MatchClassificationComponent {

	DEL_PartlyOneSided() {
	}

	private final MCPattern fwd = MCPattern.DEL_PARTLYONESIDED_FWD;
	private final MCPattern bwd = MCPattern.DEL_PARTLYONESIDED_FWD;

	@Override
	public Mismatch classify(INTEGRATE integrate, AnalysedMatch analysedMatch) {
		DomainType delSide;
		if (fwd.matches(analysedMatch.getModPattern())) {
			delSide = DomainType.SRC;
		} else if (bwd.matches(analysedMatch.getModPattern())) {
			delSide = DomainType.TRG;
		} else
			return null;

		Mismatch mismatch = new Mismatch(analysedMatch, this);

		EltFilter ef = new EltFilter().create();
		classifyElts(integrate, mismatch, analysedMatch.getElts(ef.domains(oppositeOf(delSide))),
				EltClassifier.POTENTIAL_USE);
		classifyElts(integrate, mismatch, analysedMatch.getElts(ef.domains(delSide).deleted()),
				EltClassifier.REWARDLESS_USE);
		classifyElts(integrate, mismatch, analysedMatch.getElts(ef.notDeleted()), EltClassifier.POTENTIAL_USE);

		return mismatch;
	}

	@Override
	public boolean isApplicable(AnalysedMatch analysedMatch) {
		return fwd.matches(analysedMatch.getModPattern()) || bwd.matches(analysedMatch.getModPattern());
	}

}
