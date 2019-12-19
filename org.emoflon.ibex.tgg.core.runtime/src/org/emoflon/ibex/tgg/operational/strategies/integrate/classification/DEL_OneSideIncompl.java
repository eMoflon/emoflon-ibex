package org.emoflon.ibex.tgg.operational.strategies.integrate.classification;

import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.Mismatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.AnalysedMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.AnalysedMatch.EltFilter;

import language.DomainType;

public class DEL_OneSideIncompl extends MatchClassificationComponent {

	DEL_OneSideIncompl() {
	}

	private final MCPattern fwd = MCPattern.DEL_ONESIDEINCOMPL_FWD;
	private final MCPattern bwd = MCPattern.DEL_ONESIDEINCOMPL_BWD;

	@Override
	public Mismatch classify(INTEGRATE integrate, AnalysedMatch analysedMatch) {
		DomainType partlySide;
		if (fwd.matches(analysedMatch.getModPattern())) {
			partlySide = DomainType.TRG;
		} else if (bwd.matches(analysedMatch.getModPattern())) {
			partlySide = DomainType.SRC;
		} else
			return null;

		Mismatch mismatch = new Mismatch(analysedMatch, this);

		EltFilter ef = new EltFilter().create();
		classifyElts(integrate, mismatch, analysedMatch.getElts(ef.domains(oppositeOf(partlySide))),
				EltClassifier.PENAL_USE);
		classifyElts(integrate, mismatch, analysedMatch.getElts(ef.domains(partlySide).deleted()),
				EltClassifier.PENAL_USE);
		classifyElts(integrate, mismatch, analysedMatch.getElts(ef.notDeleted()), EltClassifier.REWARDLESS_USE);

		return mismatch;
	}

	@Override
	public boolean isApplicable(AnalysedMatch analysedMatch) {
		return fwd.matches(analysedMatch.getModPattern()) || bwd.matches(analysedMatch.getModPattern());
	}

}
