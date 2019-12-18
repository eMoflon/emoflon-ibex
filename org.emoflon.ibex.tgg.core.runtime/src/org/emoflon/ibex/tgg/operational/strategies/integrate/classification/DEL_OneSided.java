package org.emoflon.ibex.tgg.operational.strategies.integrate.classification;

import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE.INTEGRATE_Op;
import org.emoflon.ibex.tgg.operational.strategies.integrate.Mismatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.AnalysedMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.AnalysedMatch.EltFilter;

import language.DomainType;

public class DEL_OneSided extends MatchClassificationComponent {

	DEL_OneSided() {
	}

	private final MCPattern fwd = MCPattern.DEL_ONESIDED_FWD;
	private final MCPattern bwd = MCPattern.DEL_ONESIDED_BWD;

	@Override
	public Mismatch classify(INTEGRATE_Op integrate, AnalysedMatch analysedMatch) {
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
				EltClassifier.PENAL_USE);
		classifyElts(integrate, mismatch, analysedMatch.getElts(ef.domains(delSide)), EltClassifier.NO_USE);

		return mismatch;
	}

	@Override
	public boolean isApplicable(AnalysedMatch analysedMatch) {
		return fwd.matches(analysedMatch.getModPattern()) || bwd.matches(analysedMatch.getModPattern());
	}

}
